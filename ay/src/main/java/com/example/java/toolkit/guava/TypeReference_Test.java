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
