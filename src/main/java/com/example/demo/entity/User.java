package com.example.demo.entity;

import java.util.UUID;

public class User {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private String id;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private  String userName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private String realName;
    private String sex;
}
