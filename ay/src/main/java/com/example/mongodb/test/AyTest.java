package com.example.mongodb.test;

import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;

/**
 * 描述：
 * @author Ay
 * @date   2017/08/22
 */
@Document
public class AyTest {

    @Id
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
