package com.fuhx.service.impl;

import com.fuhx.entity.Account;
import com.fuhx.dao.AccountDao;
import com.fuhx.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TAccount)表服务实现类
 *
 * @author fuhongxing
 * @since 2021-03-18 12:21:18
 */
@Service("tAccountService")
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountDao tAccountDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Account queryById(Integer id) {
        return this.tAccountDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Account> queryAllByLimit(int offset, int limit) {
        return this.tAccountDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tAccount 实例对象
     * @return 实例对象
     */
    @Override
    public Account insert(Account tAccount) {
        this.tAccountDao.insert(tAccount);
        return tAccount;
    }

    /**
     * 修改数据
     *
     * @param tAccount 实例对象
     * @return 实例对象
     */
    @Override
    public Account update(Account tAccount) {
        this.tAccountDao.update(tAccount);
        return this.queryById(tAccount.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tAccountDao.deleteById(id) > 0;
    }
}
