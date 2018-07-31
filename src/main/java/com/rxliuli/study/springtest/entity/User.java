package com.rxliuli.study.springtest.entity;

import java.io.Serializable;

/**
 * @author rxliuli
 * @date 2018/7/31
 */
public class User implements Serializable {
    private Integer id;
    private String name;
    private Boolean sex;
    private Integer age;

    public User() {
    }

    public User(String name, Boolean sex, Integer age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public User(Integer id, String name, Boolean sex, Integer age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getSex() {
        return sex;
    }

    public User setSex(Boolean sex) {
        this.sex = sex;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }
}
