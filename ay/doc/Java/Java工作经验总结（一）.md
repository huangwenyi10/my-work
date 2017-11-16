# **Java工作经验总结（一）**



### **课程介绍**



《Java工作经验总结（一）》主要针对有一定基础的Java学员。本系列课程主要分享自己平时真实的工作经验。

### **课程目标**



帮助学员获得真实的工作经验。



### **课程计划：**



持续更新



### **课程目录：**

- 课程介绍
- Lists.partition集合分割
- TypeReference在Json与对象转换中的使用
- return与try catch之间的执行顺序
- 静态导入与导入的运用
- CollectionUtils.subtract()集合相减在工作中运用
- Lists.transform()的原理及坑点
- 表达式引擎aviator（一）
- 表达式引擎aviator（二）
- 结构化数据、半结构化数据、非结构化数据
- NASA的10大编程规则
  -



##### 课程介绍

##### Lists.partition集合分割



    package com.example.java.toolkit.guava;

    import com.google.common.collect.Lists;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.util.CollectionUtils;
    
    import java.util.List;


    /**
     * 描述：Guava工具类partition使用
     *
     * @author Ay
     * @date 2017/10/22
     */
    public class Lists_Partition {


        Logger log = LoggerFactory.getLogger(Lists_Partition.class);

​    
        public static void main(String[] args) {

            //step.1 集合切割正常逻辑
            List<Integer> numList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
    
            List<List<Integer>> partList = Lists.partition(numList, 3);
            if (!CollectionUtils.isEmpty(partList)) {
                for (List<Integer> list : partList) {
                    System.out.println(list.toString());
                }
            }
    
            //step.2 切割数量大于集合数量
            List<Integer> numList2 = Lists.newArrayList(1);
            List<List<Integer>> partList2 = Lists.partition(numList2, 3);
            if (!CollectionUtils.isEmpty(partList2)) {
                for (List<Integer> list : partList2) {
                    System.out.println(list.toString());
                }
            }


            //step.3 修改切割后的集合，检查原集合是否被修改
            List<Integer> numList3 = Lists.newArrayList(1,2,3,4,5,6,7,89,9);
    
            List<List<Integer>> partList3 = Lists.partition(numList3, 3);
            if (!CollectionUtils.isEmpty(partList3)) {
                for (List<Integer> list : partList3) {
                    for(int i=0,len = list.size();i<len;i++){
                        list.set(i,8);
                    }
                }
                //打印原集合
                System.out.println(numList3.toString());
    
            }
    
            //注意：partition返回的是原list的subview.视图,即原list改变后,partition之后的结果也会随着改变
    
            //step.4 List.partition（）在真实项目中的运用
            List<Integer> numList4 = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    
            List<List<Integer>> partList4 = Lists.partition(numList4, 100);
            if (!CollectionUtils.isEmpty(partList4)) {
    
                for (List<Integer> list : partList4) {
                    //将切割的集合按照固定数量查询数据库
                    //xxxx.findById(list)
                    //select * from user u where u.id in (1,2,3 ....) 这里的id数量不要超过100个
                }    
            }    
        }
       
    }
    
    //参考文章
    //【1】java 将list按照指定数量分成小list（http://blog.csdn.net/liuxiao723846/article/details/75670290）


##### TypeReference在Json与对象转换中的使用



    package com.example.java.toolkit.guava;

​    
    import com.fasterxml.jackson.core.type.TypeReference;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.google.common.collect.Lists;
    import org.junit.Test;
    
    import java.io.Serializable;
    import java.util.List;
    
    /**
     * 描述：
     * @author Ay
     * @date   2017-11-01
     *
     */
    public class TypeReference_Test {


        public static ObjectMapper mapper = new ObjectMapper();

​    
        public static final TypeReference LIST_STRING = new TypeReference<List<String>>() {};

        @Test
        public void test() throws Exception{
            //step.1 数据初始化
            List<Person> persionList = Lists.newArrayList();
            Person ay = new Person();
            ay.setName("ay");
            ay.setPassword("123");
            Person al = new Person();
            al.setName("al");
            al.setPassword("123");
            persionList.add(ay);
            persionList.add(al);
            //对象转换为json的字符串
            String personJsonStr = mapper.writeValueAsString(persionList);
            System.out.println(persionList);
            //字符串转换为对象
            //List<Person> getlistPerson = mapper.readValue(personJsonStr, new TypeReference<List<Person>>() {});
            List<Person> getlistPerson = mapper.readValue(personJsonStr, LIST_STRING);
    
            System.out.println(getlistPerson);


            //================================
            // TypeReference的源码
            /*public class TypeReference<T> {
    
                private final Type type;
    
                public TypeReference() {
                    //获得当前类带有泛型信息的父类
                    Type superClass = getClass().getGenericSuperclass();//1
    
                    //获取父类的泛型信息
                    type = ((ParameterizedType) superClass).getActualTypeArguments()[0];//2
                }
    
                public Type getType() {
                    return type;
                }
                实例，TypeReference的使用方法
                public static final TypeReference LIST_STRING = new TypeReference<List<String>>() {
                }.getType();//3
            }*/
    
        }
    
    }
    
    class Person implements Serializable{
    
        private String name;
        private String password;
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getPassword() {
            return password;
        }
    
        public void setPassword(String password) {
            this.password = password;
        }
    }


##### return与try catch之间的执行顺序


    package com.example.java.test;

​    
    import org.junit.Test;

    /**
     * return 与 try catch的关系
     * @author Ay
     * @date   2017-10-1
     */
    public class ReturnAndTryCatchTest {


        @Test
        public void test() {
    
            try{
    
                System.out.println("try");
            }catch (Exception e){
    
                System.out.println("catch");
            }finally {
                System.out.println("finally");
    
            }
            System.out.println("method body");
            return;
    
            //打印结果
            //try
            //finally
            //method body
        }


        @Test
        public void test2() {
    
            try{
                System.out.println("try");
                String str = null;
                str.split(",");
            }catch (Exception e){
    
                System.out.println("catch");
                System.out.println("return");
                return;
            }finally {
                System.out.println("finally");
    
            }
            System.out.println("method body");
            return;
    
            //打印结果
            //try
            //catch
            //return
            //finally
        }
    
        @Test
        public void test3() {
    
            try{
                System.out.println("try");
                System.out.println("try return");
                String str = null;
                str.split(",");
    
                return;
            }catch (Exception e){
    
                System.out.println("catch");
                System.out.println("catch return");
                return;
            }finally {
                System.out.println("finally");
                return;
            }
    
            //打印结果
            //try
            //try return
            //catch
            //catch return
            //finally
        }
    
        @Test
        public void test4() {


            try{
                System.out.println("try");
                System.out.println("try return");
                return;
            }catch (Exception e){
    
                System.out.println("catch");
                System.out.println("catch return");
                return;
            }finally {
                System.out.println("finally");
                return;
            }
    
            //打印结果
            //try
            //try return
            //finally
        }
    
        @Test
        public void test5(){
    
            System.out.println("最终打印的值是：" + returnTest());
        }
    
        private String returnTest(){
            String msg = "";
    
            try{
                msg = "try";
                return msg;
            }catch (Exception e){
    
                msg = "catch";
                return msg;
            }finally {
                msg = "finally";
                //return msg; //1
            }
    
            //打印结果
            //最终打印的值是：finally
    
            //注释掉 1 打印结果
            //最终打印的值是：try
    
            //结论
            //如果finally语句中没有return语句覆盖返回值，那么原来的保存下来的返回值不一定会因为finally里的修
        }
    
        //结论
        //任何执行try 或者catch中的return语句之前，都会先执行finally语句，如果finally存在的话。
        //如果finally中有return语句，那么程序就return了，所以finally中的return是一定会被return的，
        //编译器把finally中的return实现为一个warning。


​    
​    
    }

    //### 参考文章
    //【1】http://www.cnblogs.com/fery/p/4709841.html
    //

##### 静态导入与导入的运用


    package com.example.java.test;

​    
    //import static org.junit.Assert.assertTrue;
    import static java.lang.Integer.MAX_VALUE;
    //import static java.lang.Long.MAX_VALUE;
    import org.junit.Assert;
    import org.junit.Test;
    import static java.lang.Math.PI;
    
    /**
     * 描述：静态导入 和导入 的区别
     * @author Ay
     * @date   2017-11-08
     */
    public class StaticImportAndImportTest {
    
        //静态导入的运用：


        /**
         * 使用普通导入
         * @param productId
         */
        @Test
        public void test(Integer productId){
    
            Assert.assertTrue(productId >= 0);
            Assert.assertTrue(productId != null);
    
        }


        /**
         * 使用静态导入
         * @param productId
         */
        @Test
        public void test2(Integer productId){
    
            //assertTrue(productId >= 0);
            //Assert.assertTrue(productId != null);
    
        }
    
        /**
         * 错误例子
         * @param productId
         */
        @Test
        public void test3(Integer productId){
            //提防含糊不清的命名static成员
            System.out.println(MAX_VALUE);
    
        }


        /**
         * 正确运用
         * @param productId
         */
        @Test
        public void test4(Integer productId){
            //1.static方法上进行静态导入
            //assertTrue(productId >= 0);
            //Assert.assertTrue(productId != null);
    
            //2.常量
            System.out.println(PI);
    
        }


        //你必须说import static， 不能说static import。
        //提防含糊不清的命名static成员。例如，如果你对Integer类和Long类执行了静态导入，引用MAX_VALUE将导致一个编译器错误，
        // 因为Integer和Long都有一个MAX_VALUE常量，并且Java不会知道你在引用哪个MAX_VALUE。
        //你可以在static对象引用、常量（记住，它们是static 或final）和static方法上进行静态导入。


​    
    }


##### CollectionUtils.subtract()集合相减在工作中运用


    package com.example.java.toolkit.guava;

​    
    import com.google.common.collect.Lists;
    import org.apache.commons.collections4.CollectionUtils;
    import org.junit.Test;
    
    import java.util.List;
    
    /**
     * 描述：集合相减测试
     * @author Ay
     * @data   2017-11-08
     */
    public class CollectionUtils_Subtract_Test {


        //CollectionUtils 出自 commons.collections 这个包

        //需要在pom.xml引入依赖
        //<dependency>
        //    <groupId>org.apache.commons</groupId>
        //    <artifactId>commons-collections4</artifactId>
        //    <version>4.1</version>
        //</dependency>
    
        @Test
        public void test() {
    
            List<Integer> oldList = Lists.newArrayList(1,3,5,7);
    
            List<Integer> newList = Lists.newArrayList(1,3);
    
            //oldList - newLit
            System.out.println(CollectionUtils.subtract(oldList,newList));
    
            //newList - oldList
            System.out.println(CollectionUtils.subtract(newList,oldList));
    
        }


        /**
         * 运用
         */
        @Test
        public void test2(){
    
            //数据库中  项目表  和 负责人表    负责人id：1,3,5,7
    
            List<Integer> newList = Lists.newArrayList(1,3,5,7,9);
    
            List<Integer> oldList = Lists.newArrayList(1,3,5,7);
    
            //newList - oldList
            List<Integer> addList = Lists.newArrayList(CollectionUtils.subtract(newList,oldList));
            System.out.println(addList);
    
            //oldList - newList
            List<Integer> delList = Lists.newArrayList(CollectionUtils.subtract(oldList,newList));
    
            System.out.println(delList);
    
            //addList插入数据库
            //insert(addList)
    
            //删除数据
            //delList(delList)


        }

​    
​    
​    
    }


##### Lists.transform()的原理及坑点

    package com.example.java.toolkit.guava;

    import com.google.common.base.Function;
    import com.google.common.collect.Lists;
    import org.junit.Test;
    
    import java.util.ArrayList;
    import java.util.List;
    
    public class Lists_Transform {


​    
        /*

        Function :
        public interface Function<F, T> {
            @Nullable
            T apply(@Nullable F var1);
    
            boolean equals(@Nullable Object var1);
        }
    
        Function是一个接口，实现该接口后需要实现apply方法，将 F 转化为 T
        Function是一种策略，把一种对象转化为另一种对象的策略
    
        */


        @Test
        public void test(){
    
            List<AyUser> ayUserList = new ArrayList<>();
            ayUserList.add(new AyUser(1,"ay"));
            ayUserList.add(new AyUser(2,"al"));
            ayUserList.add(new AyUser(3, "love"));
            //遍历List 对每个旧类型元素调用 apply方法，转化成另外一种类型的元素，形成一个新List，存放新类型元素
            List<Integer> ids = Lists.transform(ayUserList, AyUserTransformer.AYUSER_ID_TRANS);
            System.out.println(ids);
        }
    
        @Test
        public void test2(){
    
            List<AyUser> ayUserList = new ArrayList<>();
            ayUserList.add(new AyUser(1,"ay"));
            ayUserList.add(new AyUser(2,"al"));
            ayUserList.add(new AyUser(3, "love"));
            //遍历List 对每个旧类型元素调用 apply方法，转化成另外一种类型的元素，形成一个新List，存放新类型元素
            List<NewAyUser> newAyUserList = Lists.transform(ayUserList, AyUserTransformer.AYUSER_TO_NEWAYUSER_TRANS);
            System.out.println(newAyUserList);
        }


        @Test
        public void test3(){
    
            List<AyUser> ayUserList = new ArrayList<>();
            ayUserList.add(new AyUser(1,"ay"));
            ayUserList.add(new AyUser(2,"al"));
            ayUserList.add(new AyUser(3, "love"));
            //遍历List 对每个旧类型元素调用 apply方法，转化成另外一种类型的元素，形成一个新List，存放新类型元素
            List<NewAyUser> newAyUserList = Lists.transform(ayUserList, AyUserTransformer.AYUSER_TO_NEWAYUSER_TRANS);
    
            newAyUserList.add(new NewAyUser(4,"4"));
            newAyUserList.add(new NewAyUser(5,"5"));
    
            System.out.println(newAyUserList);


            //总结：transform函数返回的List是一个特殊类型的List，只支持遍历，不支持任何修改（增删改移）
            //所以，对这个list做的所有修改操作都会抛异常。
        }
    
        //简单看下源码
        //public static <F, T> List<T> transform(List<F> fromList, Function<? super F, ? extends T> function) {
        //    return (List)(fromList instanceof RandomAccess ? new Lists.TransformingRandomAccessList(fromList, function) :
        //                                                     new Lists.TransformingSequentialList(fromList, function));
        //}


​    
​    
​    
    }

    class AyUserTransformer{

        public static final Function<AyUser, Integer> AYUSER_ID_TRANS = new AyUserIDTransfer();

        /**
         * 用户实体转换ID
         */
        public static class AyUserIDTransfer implements Function<AyUser, Integer> {
    
            @Override
            public Integer apply(AyUser input) {
                return input.getId();
            }
        }


        public static final Function<AyUser, NewAyUser> AYUSER_TO_NEWAYUSER_TRANS = new AyUserTONewAyUserTransfer();

        /**
         * 用户实体转换新的用户实体
         */
        public static class AyUserTONewAyUserTransfer implements Function<AyUser, NewAyUser> {
    
            @Override
            public NewAyUser apply(AyUser ayUser) {
                NewAyUser newAyUser = new NewAyUser();
                newAyUser.setNewId(ayUser.getId());
                newAyUser.setNewName(ayUser.getName());
                return newAyUser;
            }
        }
    
    }


    class NewAyUser{

        private String newName;
        private Integer newId;
    
        public NewAyUser(){}
    
        public NewAyUser(Integer newId, String newName){
            this.newId = newId;
            this.newName = newName;
        }
    
        public String getNewName() {
            return newName;
        }
    
        public void setNewName(String newName) {
            this.newName = newName;
        }
    
        public Integer getNewId() {
            return newId;
        }
    
        public void setNewId(Integer newId) {
            this.newId = newId;
        }
    }


​    
    class AyUser{

        private String name;
        private Integer id;
    
        public AyUser(){}
    
        public AyUser(Integer id, String name){
            this.id = id;
            this.name = name;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public Integer getId() {
            return id;
        }
    
        public void setId(Integer id) {
            this.id = id;
        }
    }


##### 表达式引擎aviator（一）
##### 表达式引擎aviator（二）  

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


##### 结构化数据、半结构化数据、非结构化数据

**结构化数据：**
结构化的数据是指可以使用关系型数据库表示和存储，表现为二维形式的数据。




**半结构化数据:**
半结构化数据，属于同一类实体可以有不同的属性，即使他们被组合在一起，这些属性的顺序并不重要。

    <person>
        <name>A</name>
        <age>13</age>
        <gender>female</gender>
    </person>
或者  

    <person>
        <name>B</name>
        <gender>male</gender>
    </person>

从上面的例子中，属性的顺序是不重要的，不同的半结构化数据的属性的个数是不一定一样的。有些人说半结构化数据是以树或者图的数据结构存储的数据，怎么理解呢？上面的例子中，<person>标签是树的根节点，<name>和<gender>标签是子节点。通过这样的数据格式，可以自由地表达很多有用的信息，包括自我描述信息（元数据）。所以，半结构化数据的扩展性是很好的。



半结构数据有：XML和JSON



**非结构化数据：**
就是没有固定结构的数据。各种文档、图片、视频/音频等都属于非结构化数据。对于这类数据，我们一般直接**整体**进行存储，而且一般存储为二进制的数据格式。




##### NASA的10大编程规则

    /**
     * 描述：NASA 的 10 大编程规则
     * @author Ay
     * @date   2017-11-10
     */
    public class NASA_10_Code_Principles {
    
        /*
        美国宇航局（National Aeronautics and Space Administration，缩写为 NASA）是美国联邦政府的一个独立机构，
        负责制定、实施美国的民用太空计划、与开展航空科学暨太空科学的研究。在太空计划之外，美国国家航空航天局还进行长期的民用以及军用航空航天研究。
        在普通人的眼中，NASA 是一个很“高级”的机构，其成员包含大量不同领域的科学家和研究人员。与其他任何组织机构类似，NASA 的日常工作，
        以及所执行的几乎全部项目也离不开计算机的辅助，出于需求的特殊性和重要性，他们所使用的很多计算机软件都是内部自行开发的，在一些重要项目的关键领域发挥着作用。
        去年，一位前 NASA 实习生把美国阿波罗登月项目的 11 号计算机 --- 阿波罗导航计算机 (Apollo Guidance Computer) 系统源代码上传到了 GitHub，此举在开发者群体中引起了极大的热议。
    
        */
    
        public static void main(String[] args) {


       /*
           原则1 – 简化控制流程（Simple Control Flow）
           用非常简单的控制流结构体来编写程序 — 不要用 setjmp 或者 longjmp 结构体，goto 语句，以及直接或间接的递归调用。
           尽量避免：if else  , 递归等函数编写  ,使用卫语句
    
    		if(){
              
    		}else if{
              
    		}else if(){
              
    		}
    		
    		卫语句
    		//
    		if(){
              
    		}
    		
    		if(){
              
    		}
    		
    		if(){
              
    		}
    		
    
           第 2 条规则 — 循环设置固定的上限
           所有的循环必须有一个固定的上限。对于检查工具来说，在给定循环次数的情况下，可以通过静态分析证实循环结果不超过预设的上限。
           如果工具不能静态检测出循环上限，那么这条规则就不适用。
    
           第 3 条规则 — 没有动态内存分配
           初始化之后不要使用动态内存分配。
    
           第 4 条规则 — 没有大函数
           如果以一行一条声明和一行一条语句这样标准的格式来写，那么函数的长度不应该超过一张纸。这也就是说一个函数不应该超过 60 行代码。
           备注：单一职权原则
    
           第 5 条原则 — 低断言使用密度
           代码断言的密度应该低至平均每个函数两个。断言是用来检查现实执行中不会发生的不正常情况。它应该被定义为布尔测试。当断言失败，应当立即采取恢复措施。
           如果静态检测工具证明断言永远不会失败或者条件永远不为真，这条规则就无效。
    	   public void test（Stirng productId）{
             Assert(productId > 0);
             Assert(productId > 0);
             Assert(productId > 0);
             Assert(productId > 0);
             Assert(productId > 0);
    	   }
    	   
    
           第 6 条规则 — 最小范围内声明数据对象
           这条规则支持数据隐藏的基本原则。所有的数据对象必须在尽可能最小范围内声明。
           备注：变量的作用域最小
           
           class A{
             
             //private String a = "a";
             
             public void test(){
             	//String a = "a";
                system.out.println(a);
                
                for(){
                  String a = "a";
                  
                }
               
             }
             
           }
    
           第 7 条规则 — 检查参数和返回值
           当函数的返回值为非空的时候，每次函数调用都应该检查其返回值，并且每个被调用的函数还要检查所带参数的有效性。
           在最严格的模式下，这条规则意味着printf和文件关闭语句的返回值也要检查
    
    		
    		class A{
             
             //private String a = "a";
             
             public List<Stirng> test(){
             	
                system.out.println(a);
                
                for(){
                  String a = "a";
                  
                }
               
             }
             
             public void test2(){
               	List<Stirng> list =  test();
               	//检查返回值
               	if(list == null && list.size() > 0){
                  	//
               	}
               
             }
             
           }
    
           第 8 条规则 — 限制使用预处理器
    
           第 9 条规则 — 限制使用指针
    
           第 10 条规则 — 所有代码必须能编译通过
    
           从开发的第一天起，所有的代码都必须通过编译。所有的编译器警告必须遵循编译器可使用警告。在编译器可使用警告范围内，编译的代码必须没有警告。
           所有代码必须每天至少使用一个（最好多于一个）最新的静态源代码分析器进行检查，而且以0警告通过所有的分析。
    
           备注：1）intellij Idea 软件本身带有警告信息 2）可以使用FindBugs-IDEA检查错误。3）rebuild product下
        */   
        }
    
    }
    
##### Multimaps.index



###参考文章

【1】http://blog.csdn.net/liangyihuai/article/details/54864952
【2】http://blog.jobbole.com/104016/