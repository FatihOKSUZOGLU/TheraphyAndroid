package com.project.theraphy.model;

import java.io.Serializable;

public class user implements Serializable {

    private String  name;

    private int age;

    private String  email;

    private String  phone;

    private String  password;

    private String  gender;

    private String  photourl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImg_url() {
        return photourl;
    }

    public void setphotourl(String photourl) {
        this.photourl = photourl;
    }


}