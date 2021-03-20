package com.fuhx.dubbo;

import com.fuhx.api.ApiUserService;
import com.fuhx.dao.AccountDao;
import com.fuhx.entity.Account;
import com.fuhx.util.Result;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;

/**
 * @author fuhongxing
 */
@Service(version = "1.0")
public class ApiUserServiceImpl implements ApiUserService {
    @Resource
    private AccountDao accountDao;
    /**
     * 从用户账户中借出
     */
    @Override
    public Result debit(String userId, int money){
        Example example = new Example(Account.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        Account account = accountDao.selectOneByExample(example);
        if(account == null){
            return Result.failure("用户不存在");
        }
        account.setAmount(account.getAmount() - money);
        accountDao.updateByPrimaryKey(account);
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