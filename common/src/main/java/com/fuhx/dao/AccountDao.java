package com.fuhx.dao;

import com.fuhx.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TAccount)表数据库访问层
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:08
 */
public interface AccountDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Account queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Account> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tAccount 实例对象
     * @return 对象列表
     */
    List<Account> queryAll(Account tAccount);

    /**
     * 新增数据
     *
     * @param tAccount 实例对象
     * @return 影响行数
     */
    int insert(Account tAccount);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TAccount> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Account> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TAccount> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Account> entities);

    /**
     * 修改数据
     *
     * @param tAccount 实例对象
     * @return 影响行数
     */
    int update(Account tAccount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

