package com.example.intellij.use;


import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.io.FileInputStream;

/**
 * 描述：优化导入使用
 * @author Ay
 * @date   2017/10/22
 */
public class Optimize_Imports {


    //optimize import 作用
    //1)删除没用的导入  2）对import的包进行排序，方便查看


    public static void main(String[] args) throws Exception{

        List<String> list = new ArrayList<String>();


        //Map<String,String> map = new HashMap<String,String>();

        File file = new File("");
        FileInputStream fileInputStream = new FileInputStream(file);

    }

}
