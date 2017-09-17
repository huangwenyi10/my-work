package com.example.praise.project.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：说说点赞关联表
 * @author ay
 * @date  2017/9/16
 */
public class MoodPraiseRel implements Serializable{


    private static final long serialVersionUID = 2420938249221563579L;
    /**
     * 主键
     */
    private String id;
    /**
     * 用户id
     */
    private String user_id;
    /**
     * 说说id
     */
    private String mood_id;
    /**
     * 是否点赞
     */
    private String is_parise;
    /**
     * 点赞日期
     */
    private Date praise_time;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMood_id() {
        return mood_id;
    }

    public void setMood_id(String mood_id) {
        this.mood_id = mood_id;
    }

    public String getIs_parise() {
        return is_parise;
    }

    public void setIs_parise(String is_parise) {
        this.is_parise = is_parise;
    }

    public Date getPraise_time() {
        return praise_time;
    }

    public void setPraise_time(Date praise_time) {
        this.praise_time = praise_time;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoodPraiseRel that = (MoodPraiseRel) o;

        if (user_id != null ? !user_id.equals(that.user_id) : that.user_id != null) return false;
        return mood_id != null ? mood_id.equals(that.mood_id) : that.mood_id == null;

    }

    @Override
    public int hashCode() {
        int result = user_id != null ? user_id.hashCode() : 0;
        result = 31 * result + (mood_id != null ? mood_id.hashCode() : 0);
        return result;
    }
}
