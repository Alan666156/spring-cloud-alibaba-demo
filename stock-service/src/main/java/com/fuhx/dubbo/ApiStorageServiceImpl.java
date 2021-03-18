package com.fuhx.dubbo;


import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ApiStorageServiceImpl implements ApiStorageService{

    /**
     * 扣除存储数量
     */
    @Override
    public void deduct(String commodityCode, int count){

    }
}