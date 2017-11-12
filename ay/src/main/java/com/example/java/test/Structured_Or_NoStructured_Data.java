package com.example.java.test;

/**
 * 描述：结构化数据、半结构化数据、非结构化数据
 * Created by Ay on 2017/11/12.
 */
public class Structured_Or_NoStructured_Data {


    //结构化数据：结构化的数据是指可以使用关系型数据库表示和存储，表现为二维形式的数据
    //eg：


    //半结构化数据:半结构化数据，属于同一类实体可以有不同的属性，即使他们被组合在一起，这些属性的顺序并不重要。
    //eg：常见的半结构数据有XML和JSON
//    <person>
//        <name>A</name>
//        <age>13</age>
//        <gender>female</gender>
//    </person>
//
//    <person>
//        <name>B</name>
//        <gender>male</gender>
//    </person>
    //从上面的例子中，属性的顺序是不重要的，不同的半结构化数据的属性的个数是不一定一样的。
    // 有些人说半结构化数据是以树或者图的数据结构存储的数据，怎么理解呢？
    // 上面的例子中，<person>标签是树的根节点，<name>和<gender>标签是子节点。
    // 通过这样的数据格式，可以自由地表达很多有用的信息，包括自我描述信息（元数据）。
    // 所以，半结构化数据的扩展性是很好的。



    //非结构化数据
    //就是没有固定结构的数据。各种文档、图片、视频/音频等都属于非结构化数据。
    // 对于这类数据，我们一般直接整体进行存储，而且一般存储为二进制的数据格式




}

//参考文章
//【1】http://blog.csdn.net/liangyihuai/article/details/54864952