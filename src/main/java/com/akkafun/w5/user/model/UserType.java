package com.akkafun.w5.user.model;

/**
 * Created by liubin on 14-4-8.
 */
public enum UserType {

    CUSTOMER("客户"),

    COACH("陪练");

    UserType(String value) {
        this.value = value;
    }

    public final String value;


}
