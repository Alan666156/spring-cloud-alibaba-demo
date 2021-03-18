package com.fuhx.entity;

import java.io.Serializable;

/**
 * (TStorage)实体类
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:30
 */
public class Storage implements Serializable {
    private static final long serialVersionUID = -17086095273773481L;

    private Integer id;

    private String commodityCode;

    private String name;

    private Integer count;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
