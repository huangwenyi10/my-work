package com.example.java.toolkit.guava;

import com.google.common.base.Function;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 描述：Multimaps.index使用
 * @author Ay
 * @date   2017-11-16
 */
public class Multimaps_index {

    //作为Maps.uniqueIndex的兄弟方法，Multimaps.index(Iterable, Function)通常针对的场景是：
    //有一组对象，它们有共同的特定属性，我们希望按照这个属性的值查询对象，但属性值不一定是独一无二的。

    @Test
    public void test(){
        ImmutableSet digits = ImmutableSet.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        Function<String, Integer> lengthFunction = new Function<String, Integer>() {
            public Integer apply(String string) {
                return string.length();
            }
        };
        ImmutableListMultimap<Integer, String> digitsByLength= Multimaps.index(digits, lengthFunction);
        List<String> three =  digitsByLength.get(3);
    }

    @Test
    public void test2(){
        List<Person> persons = Lists.newArrayList(
                new Person("zhang", 15),
                new Person("zhang", 16),
                //new Person("wang", 16)
                new Person("lee", 18)
        );

        //Maps.uniqueIndex(Iterable,Function)通常针对的场景是：有一组对象，
        //它们在某个属性上分别有独一无二的值，而我们希望能够按照这个属性值查找对象——
        //译者注：这个方法返回一个Map，键为Function返回的属性值，值为Iterable中相应的元素，因此我们可以反复用这个Map进行查找操作。

        /**
         * 转换后的Map具有唯一键
         */
//        Map<String, Person> map = Maps.uniqueIndex(persons, new Function<Person, String>() {
//            @Override
//            public String apply(Person person) {
//                return person.getName();
//            }
//        });

        /**
         * 转换后的Map有重复键
         */
        Multimap<String, Person> multiMap = Multimaps.index(persons, new Function<Person, String>() {
            public String apply(Person person) {
                return person.getName();
            }
        });
    }

    public class Person {

        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public int getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
//参考文章
//【1】http://blog.csdn.net/foreverling/article/details/52204493