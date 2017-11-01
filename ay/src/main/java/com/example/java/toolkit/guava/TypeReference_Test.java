package com.dianping.skuadmin.biz;


import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * @author Ay
 * @date   2017-11-01
 *
 */
public class TypeReference_Test {


    public static ObjectMapper mapper = new ObjectMapper();

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

        String personJsonStr = mapper.writeValueAsString(persionList);
        System.out.println(persionList);


        List<Person> getlistPerson = mapper.readValue(personJsonStr, new TypeReference<List<Person>>() {});

        System.out.println(getlistPerson);

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
