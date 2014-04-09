package com.akkafun.w5.payslip.model;

/**
 * Created by liubin on 14-4-8.
 */
public enum PayslipStatus {

    WAIT_PAY("待支付"),

    PAID("已支付");

    PayslipStatus(String value) {
        this.value = value;
    }

    public final String value;


}
