package com.akkafun.w5.order.model;

/**
 * Created by liubin on 14-4-8.
 */
public enum OrderStatus {

    WAIT_CONFIRM("待确认"),

    CONFIRMED("已确认"),

    CANCELLED("已取消"),

    INVALID("已作废");

    OrderStatus(String value) {
        this.value = value;
    }

    public final String value;


}
