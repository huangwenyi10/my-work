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






