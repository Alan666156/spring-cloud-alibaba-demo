package com.fuhx.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (COMMODITY)商品实体类
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:30
 */
@Accessors(chain = true)
@Data
@Table(name="commodity_tbl")
public class Commodity implements Serializable {
    private static final long serialVersionUID = -17086095273773481L;

    private Integer id;
    /**商品编码*/
    private String skuCode;
    /**商品名称*/
    private String name;
    /**库存*/
    private Integer stock;
    /**分类编码*/
    private String category;
    /**分类*/
    private String categoryName;
    /**价格*/
    private Integer price;
    /**品牌*/
    private String brand;
    /**状态*/
    private String status;
    /**创建时间*/
    private Date createTime;

}
