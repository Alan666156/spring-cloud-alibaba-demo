package com.fuhx.elk;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品
 * 注解：@Document用来声明Java对象与ElasticSearch索引的关系
 * indexName 索引名称
 * type      索引类型
 * shards    主分区数量，默认5
 * replicas  副本分区数量，默认1
 * createIndex 索引不存在时，是否自动创建索引，默认true
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:30
 */
@Accessors(chain = true)
@Data
@Document(indexName = "commodity")
public class CommodityDTO implements Serializable {

//    private Integer id;
    /**商品编码*/
    @Id
    private String skuCode;
    /**商品名称*/
    @Field(type = FieldType.Keyword)
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
    private Date createTime = new Date();

}
