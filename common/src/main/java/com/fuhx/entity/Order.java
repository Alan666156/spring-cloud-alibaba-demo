package com.fuhx.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * (TOrder)实体类
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:28
 */
@Accessors(chain = true)
@Data
@Table(name="t_order")
public class Order implements Serializable {
    private static final long serialVersionUID = -48469399210531969L;

    private Integer id;

    private String orderNo;

    private String userId;

    private String commodityCode;

    private Integer count;

    private Object amount;

}
