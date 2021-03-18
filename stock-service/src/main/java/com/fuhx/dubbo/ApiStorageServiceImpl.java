package com.fuhx.dubbo;


import com.fuhx.dao.StorageDao;
import com.fuhx.entity.Account;
import com.fuhx.entity.Storage;
import com.fuhx.util.Result;
import org.apache.dubbo.config.annotation.DubboService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @author fuhongxing
 */
@DubboService(version = "1.0")
public class ApiStorageServiceImpl implements ApiStorageService{
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
        storageDao.selectOneByExample(new Storage().setCommodityCode(commodityCode));
        return Result.success();
    }
}