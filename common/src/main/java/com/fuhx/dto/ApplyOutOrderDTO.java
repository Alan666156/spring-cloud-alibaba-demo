package com.fuhx.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 申请出库订单
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:28
 */
@Accessors(chain = true)
@Data
public class ApplyOutOrderDTO implements Serializable {
    private static final long serialVersionUID = -48469399210531969L;

    private Integer id;

    private String orderNo;

    private String userId;

    private String commodityCode;

    private Integer count;

    private Integer amount;

    private String status;
}
