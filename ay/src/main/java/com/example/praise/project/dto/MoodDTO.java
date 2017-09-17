package com.example.praise.project.dto;

import com.example.praise.project.model.Mood;

/**
 * 描述：说说DTO
 * @author Ay
 * @date   2017/9/17
 */
public class MoodDTO extends Mood{

    /**
     * 用户账号
     */
    private String account;

    private String is_praise;

    public String getIs_praise() {
        return is_praise;
    }

    public void setIs_praise(String is_praise) {
        this.is_praise = is_praise;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
