package com.fuhx.service.impl;

import com.fuhx.entity.Storage;
import com.fuhx.dao.StorageDao;
import com.fuhx.service.StorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TStorage)表服务实现类
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:30
 */
@Service("storageService")
public class StorageServiceImpl implements StorageService {
    @Resource
    private StorageDao storageDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Storage queryById(Integer id) {
        return this.storageDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Storage> queryAllByLimit(int offset, int limit) {
        return this.storageDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tStorage 实例对象
     * @return 实例对象
     */
    @Override
    public Storage insert(Storage tStorage) {
        this.storageDao.insert(tStorage);
        return tStorage;
    }

    /**
     * 修改数据
     *
     * @param tStorage 实例对象
     * @return 实例对象
     */
    @Override
    public Storage update(Storage tStorage) {
        this.storageDao.update(tStorage);
        return this.queryById(tStorage.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.storageDao.deleteById(id) > 0;
    }
}
