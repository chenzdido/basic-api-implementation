package com.thoughtworks.rslist.domain;

import com.sun.istack.NotNull;

import javax.validation.constraints.*;

public class NewUser {
    private String user_Name;
    private String user_gender;
    private int user_age;
    private String user_email;
    private String user_phone;
    public NewUser(User user){
        this.user_Name=user.getUserName();
        this.user_gender=user.getGender();
        this.user_age=user.getAge();
        this.user_email=user.getEmail();
        this.user_phone=user.getPhone();
    }


    public int getUser_age() {
        return user_age;
    }

    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
}
