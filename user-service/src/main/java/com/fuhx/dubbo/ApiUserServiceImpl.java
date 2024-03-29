package com.fuhx.dubbo;

import com.fuhx.api.ApiUserService;
import com.fuhx.dao.AccountDao;
import com.fuhx.entity.Account;
import com.fuhx.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;

/**
 * @author fuhongxing
 */
@Slf4j
@DubboService(version = "1.0")
public class ApiUserServiceImpl implements ApiUserService {
    @Resource
    private AccountDao accountDao;
    /**
     * 从用户账户中借出
     */
    @Override
    public Result debit(String userId, int money){
        log.info("扣款调用:{}，扣款金额{}", userId, money);
        Example example = new Example(Account.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        Account account = accountDao.selectOneByExample(example);
        if(account == null){
            return Result.failure("用户不存在");
        }
        account.setAmount(account.getAmount() - money);
        accountDao.update(account);
        return Result.success();
    }

    @Override
    public Result getUser(String userId) {
        Example example = new Example(Account.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        Account account = accountDao.selectOneByExample(example);
        if(account == null){
            return Result.failure("用户不存在");
        }
        return Result.success(account);
    }
}