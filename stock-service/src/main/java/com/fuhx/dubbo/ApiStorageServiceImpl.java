package com.fuhx.dubbo;


import com.fuhx.api.ApiStorageService;
import com.fuhx.dao.StorageDao;
import com.fuhx.entity.Storage;
import com.fuhx.util.Result;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.scheduling.annotation.Async;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @author fuhongxing
 */
@Service(version = "1.0")
public class ApiStorageServiceImpl implements ApiStorageService {

    @Resource
    private StorageDao storageDao;

    /**
     * 扣除存储数量
     */
    @Override
    public Result deduct(String commodityCode, int count){
        Example example = new Example(Storage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("commodityCode", commodityCode);
        Storage storage = storageDao.selectOneByExample(example);
        if(storage == null){
            return Result.failure("商品不存在");
        }
        storage.setCount(storage.getCount() - count);
        storageDao.update(storage);
        return Result.success();
    }

    @GlobalTransactional
    @Override
    public int update(Storage storage) {
        return storageDao.update(storage);
    }

    @Override
    public Result<Storage> findByCommodityCode(String commodityCode) {
        Example example = new Example(Storage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("commodityCode", commodityCode);
        Storage storage = storageDao.selectOneByExample(example);
        if(storage == null){
            return Result.failure("商品不存在");
        }
        return Result.success(storage);
    }
}