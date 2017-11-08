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
