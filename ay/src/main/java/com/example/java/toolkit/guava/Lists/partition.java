package com.example.java.toolkit.guava.Lists;

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
public class partition {


    Logger log = LoggerFactory.getLogger(partition.class);


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
            for (List<Integer> list : partList3) {

                System.out.println(list.toString());
            }

        }

        //注意：partition返回的是原list的subview.视图,即原list改变后,partition之后的结果也会随着改变

        //step.4 List.partition（）在真实项目中的运用
        List<Integer> numList4 = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<List<Integer>> partList4 = Lists.partition(numList4, 3);
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
