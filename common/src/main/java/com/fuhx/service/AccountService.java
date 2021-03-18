package com.fuhx.service;

import com.fuhx.entity.Account;

import java.util.List;

/**
 * (TAccount)表服务接口
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:14
 */
public interface AccountService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Account queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Account> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tAccount 实例对象
     * @return 实例对象
     */
    Account insert(Account tAccount);

    /**
     * 修改数据
     *
     * @param tAccount 实例对象
     * @return 实例对象
     */
    Account update(Account tAccount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
