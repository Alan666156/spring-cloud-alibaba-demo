package com.fuhx.dubbo;


import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0")
public class ApiStorageServiceImpl implements ApiStorageService{

    /**
     * 扣除存储数量
     */
    @Override
    public void deduct(String commodityCode, int count){

    }
}