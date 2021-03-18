package com.fuhx.entity;

import java.io.Serializable;

/**
 * (TAccount)实体类
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:08
 */
public class Account implements Serializable {
    private static final long serialVersionUID = 395939172856341048L;

    private Integer id;

    private String userId;

    private Object amount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }

}
