package com.fuhx.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * (Storage)实体类
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:30
 */
@Accessors(chain = true)
@Data
@Table(name="storage_tbl")
public class Storage implements Serializable {
    private static final long serialVersionUID = -17086095273773481L;

    private Integer id;
    /**
     * 商品编码
     */
    private String commodityCode;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 库存数量
     */
    private Integer count;



}
