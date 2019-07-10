package com.lhj.activiti.design.model;

import java.math.BigDecimal;

public class User {
    private String id;

    private String name;

    private BigDecimal age;

    private String pwd;

    private String account;

    private String ramk;

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

    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRamk() {
        return ramk;
    }

    public void setRamk(String ramk) {
        this.ramk = ramk;
    }
}