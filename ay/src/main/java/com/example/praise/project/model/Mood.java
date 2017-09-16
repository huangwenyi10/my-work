package com.example.praise.project.model;

import java.util.Date;

/**
 * 描述：说说
 * Created by Ay on 2017/9/16.
 */
public class Mood {


    /**
     * 主键
     */
    private String id;
    /**
     * 说说内容
     */
    private String content;
    /**
     * 点赞数量
     */
    private Integer praise_num;
    /**
     * 用户id
     */
    private String user_id;
    /**
     * 发表时间
     */
    private Date publishTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(Integer praise_num) {
        this.praise_num = praise_num;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
