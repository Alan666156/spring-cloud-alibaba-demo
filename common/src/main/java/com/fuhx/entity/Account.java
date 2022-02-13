package com.fuhx.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * (Account)实体类
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:08
 */
@Accessors(chain = true)
@Data
@Table(name="account_tbl")
public class Account implements Serializable {

    private static final long serialVersionUID = -2507957438718313061L;
    private Integer id;

    private String userId;

    private Integer amount;

}
