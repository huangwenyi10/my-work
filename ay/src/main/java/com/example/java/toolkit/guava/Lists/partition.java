package com.example.java.toolkit.guava.Lists;




import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * 描述：Guava工具类partition使用
 * @author Ay
 * @date   2017/10/22
 */
public class partition {


    Logger log = LoggerFactory.getLogger(partition.class);


    public static void main(String[] args) {

        List<Integer> numList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

        List<List<Integer>> partList =  Lists.partition(numList,3);
        if(!CollectionUtils.isEmpty(partList)){

            for(List<Integer> list :partList){

                System.out.println(list.toString());
            }

        }

    }


}

//参考文章
//【1】java 将list按照指定数量分成小list（http://blog.csdn.net/liuxiao723846/article/details/75670290）
