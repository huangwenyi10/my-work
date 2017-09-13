package com.example.dubbo.test;

import java.io.Serializable;

/**
 * Created by Ay on 2017/8/13.
 */
public class AyTest implements Serializable{

    //alt + insert
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
