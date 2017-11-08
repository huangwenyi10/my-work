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
- 
- 
- 
- 
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
    
    
    
    
    }
    
    //### 参考文章
    //【1】http://www.cnblogs.com/fery/p/4709841.html
    //
    
##### 静态导入与导入的运用


    package com.example.java.test;
    
    
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
    
    
    
    }


##### CollectionUtils.subtract()集合相减在工作中运用


    package com.example.java.toolkit.guava;
    
    
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
    
    
    
    
    }


##### Lists.transform()的原理及坑点

    package com.example.java.toolkit.guava;
    
    import com.google.common.base.Function;
    import com.google.common.collect.Lists;
    import org.junit.Test;
    
    import java.util.ArrayList;
    import java.util.List;
    
    public class Lists_Transform {
    
    
    
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
    
    
    
    
    
    


    



