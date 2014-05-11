package com.akkafun.w5.user.model;

/**
 * Created by liubin on 14-4-8.
 */
public enum UserStatus {

    WAIT_CONFIRM("待审核"),

    NORMAL("正常"),

    FROZEN("已冻结");


    UserStatus(String value) {
        this.value = value;
    }

    public final String value;

    public String getValue() {
        return value;
    }
}
