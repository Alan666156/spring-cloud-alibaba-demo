package com.fuhx.service;

import com.fuhx.entity.Storage;

import java.util.List;

/**
 * (TStorage)表服务接口
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:30
 */
public interface StorageService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Storage queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Storage> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tStorage 实例对象
     * @return 实例对象
     */
    Storage insert(Storage tStorage);

    /**
     * 修改数据
     *
     * @param tStorage 实例对象
     * @return 实例对象
     */
    Storage update(Storage tStorage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
