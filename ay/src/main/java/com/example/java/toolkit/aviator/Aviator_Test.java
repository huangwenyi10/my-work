package com.example.java.toolkit.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述：表达式引擎
 * @author Ay
 * @date   2017/11/10.
 */
public class Aviator_Test {

    @Test
    public void test(){
        //Aviator的数值类型仅支持Long和Double
        Long result = (Long) AviatorEvaluator.execute("1+2+3");
        System.out.println(result);

        String yourName = "ay";
        Map<String, Object> env = new HashMap<>();
        env.put("yourName", yourName);
        String result2 = (String) AviatorEvaluator.execute(" 'hello ' + yourName ", env);
        System.out.println(result2);  // hello ay

        //Aviator 2.2 开始新增加一个exec方法, 可以更方便地传入变量并执行, 而不需要构造env这个map了
        //只要在exec中按照变量在表达式中的出现顺序传入变量值就可以执行, 不需要构建Map了。
        String name = "ay";
        String result3 = (String) AviatorEvaluator.exec(" 'hello ' + yourName ", name); // hello ay
        System.out.println(result3);

        Long result4 = (Long) AviatorEvaluator.execute("string.length('hello')");
        System.out.println(result4);


        //注册函数
        AviatorEvaluator.addFunction(new AddFunction());
        System.out.println(AviatorEvaluator.execute("add(1, 2)"));           // 3.0
        System.out.println(AviatorEvaluator.execute("add(add(1, 2), 100)")); // 103.0

        // 编译表达式
        String expression = "a-(b-c)";

        Expression compiledExp = AviatorEvaluator.compile(expression);
        Map<String, Object> env2 = new HashMap<>();
        env2.put("a", 100.3);
        env2.put("b", 45);
        env2.put("c", -199.100);
        // 执行表达式
        compiledExp.execute(env2);

        //访问数组和集合
        final List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add(" world");
        final int[] array = new int[3];
        array[0] = 0;
        array[1] = 1;
        array[2] = 3;
        final Map<String, Date> map = new HashMap<>();
        map.put("date", new Date());
        Map<String, Object> env3 = new HashMap<>();
        env3.put("list", list);
        env3.put("array", array);
        env3.put("mmap", map);
        System.out.println(AviatorEvaluator.execute("list[0] + list[1]", env3));   // hello world
        System.out.println(AviatorEvaluator.execute("'array[0]+array[1]+array[2]=' + (array[0]+array[1]+array[2])", env3));  // array[0]+array[1]+array[2]=4
        System.out.println(AviatorEvaluator.execute("'today is ' + mmap.date ", env3));  // today is Wed Feb 24 17:31:45 CST 2016

        //三元操作符
        AviatorEvaluator.exec("a>0? 'yes':'no'", 1);


        TestAviator foo = new TestAviator(100, 3.14f, new Date());
        Map<String, Object> env6 = new HashMap<>();
        env6.put("foo", foo);
        System.out.println(AviatorEvaluator.execute("'foo.i = ' + foo.i", env6));   // foo.i = 100
        System.out.println(AviatorEvaluator.execute("'foo.f = ' + foo.f", env6));   // foo.f = 3.14
        System.out.println(AviatorEvaluator.execute("'foo.date.year = ' + (foo.date.year+1990)", env6));  // foo.date.year = 2106

        AviatorEvaluator.execute("nil == nil");   //true
        AviatorEvaluator.execute(" 3> nil");      //true
        AviatorEvaluator.execute(" true!= nil");  //true
        AviatorEvaluator.execute(" ' '>nil ");    //true
        AviatorEvaluator.execute(" a==nil ");     //true, a 是 null

        //日期比较
        Map<String, Object> env7 = new HashMap<>();
        final Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(date);
        env7.put("date", date);
        env7.put("dateStr", dateStr);
        Boolean result8 = (Boolean) AviatorEvaluator.execute("date==dateStr", env7);
        System.out.println(result8);  // true
        result8 = (Boolean) AviatorEvaluator.execute("date > '2010-12-20 00:00:00:00' ", env7);
        System.out.println(result8);  // true
        result8 = (Boolean) AviatorEvaluator.execute("date < '2200-12-20 00:00:00:00' ", env7);
        System.out.println(result8);  // true
        result8 = (Boolean) AviatorEvaluator.execute("date==date ", env);
        System.out.println(result8);  // true

        //强大的 seq 库
        Map<String, Object> env9 = new HashMap<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(20);
        list2.add(10);
        env9.put("list2", list2);
        Object result7 = AviatorEvaluator.execute("count(list2)", env9);
        System.out.println(result7);  // 3
        result7 = AviatorEvaluator.execute("reduce(list2,+,0)", env9);
        System.out.println(result7);  // 33
        result7 = AviatorEvaluator.execute("filter(list2,seq.gt(9))", env9);
        System.out.println(result7);  // [10, 20]
        result7 = AviatorEvaluator.execute("include(list2,10)", env9);
        System.out.println(result7);  // true
        result7 = AviatorEvaluator.execute("sort(list2)", env9);
        System.out.println(result7);  // [3, 10, 20]
        AviatorEvaluator.execute("map(list2,println)", env9);

    }

    class AddFunction extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            Number left = FunctionUtils.getNumberValue(arg1, env);
            Number right = FunctionUtils.getNumberValue(arg2, env);
            return new AviatorDouble(left.doubleValue() + right.doubleValue());
        }
        public String getName() {
            return "add";
        }
    }

    public class TestAviator {
        int i;
        float f;
        Date date;
        // 构造方法
        public TestAviator(int i, float f, Date date) {
            this.i = i;
            this.f = f;
            this.date = date;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public float getF() {
            return f;
        }

        public void setF(float f) {
            this.f = f;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }


}
//参考文章
//【1】http://blog.csdn.net/keda8997110/article/details/50782848
