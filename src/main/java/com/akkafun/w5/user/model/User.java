package com.akkafun.w5.user.model;

// Generated 2013-3-11 16:58:30 by Hibernate Tools 3.6.0

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.akkafun.w5.common.model.BusinessData;
import com.akkafun.w5.common.model.Operator;
import com.akkafun.w5.permission.model.Permission;
import com.akkafun.w5.permission.model.Role;

/**
 * Employee generated by hbm2java
 */
public class User implements java.io.Serializable,
        com.akkafun.platform.common.dao.Entity<Long>, BusinessData{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final String SESSION_KEY = "com.akkafun.w5.user.model.User";

    private Long id;
    @NotBlank
    private String username;

    private String password;

    private String gender;

    private Double price;

    private Double balance;

    private String name;

    private String nickname;

    private String yy;

    private String qq;

    private String mobile;

    private String idcard;

    @Email
    @Size(max = 50)
    private String email;

    private String alipay;

    private String bankcard;

    private UserType type;

    private String remark;

    private int discount = 0;

    private int proportion = 0;

    private Operator operator;
    private Long roleId;
    private Role role;

    //用户拥有的权限缓存,key为url,value为Permission对象
    private Map<String, Permission> permissionsCache;

    public Map<String, Permission> getPermissionsCache() {
        return permissionsCache;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getProportion() {
        return proportion;
    }

    public void setProportion(int proportion) {
        this.proportion = proportion;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * 设置用户拥有的权限缓存
     *
     * @param permissions
     */
    public void buildPermissionsCache(Set<Permission> permissions) {
        Map<String, Permission> permissionsCache = new HashMap<>();
        for (Permission p : permissions) {
            permissionsCache.put(p.getOperation().getUrl(), p);
        }
        this.permissionsCache = permissionsCache;
    }

    public void initialize() {
        Hibernate.initialize(this.getRole());
        Hibernate.initialize(this.getOperator());
    }
}