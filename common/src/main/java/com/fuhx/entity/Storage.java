package com.fuhx.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * (TStorage)实体类
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:30
 */
@Accessors(chain = true)
@Data
@Table(name="t_storage")
public class Storage implements Serializable {
    private static final long serialVersionUID = -17086095273773481L;

    private Integer id;

    private String commodityCode;

    private String name;

    private Integer count;



}
