package com.acooly.module.account.service;

import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.entity.Account;
import com.acooly.core.utils.enums.SimpleStatus;

/**
 * 账户管理服务
 *
 * @author zhangpu@acooly.cn
 * @date 2018-07-16 01:11
 */
public interface AccountManageService {

    /**
     * 独立开户
     *
     * @param accountInfo
     * @return
     */
    Account openAccount(AccountInfo accountInfo);

    /**
     * 查询单个账户信息
     * <p>
     * 组合方式：
     * <p>
     * 1、构造参数（accountId）
     * 2、构造参数（accountNo）
     *
     * @param accountInfo
     * @return
     */
    Account loadAccount(AccountInfo accountInfo);

    /**
     * 状态变更
     * <p>
     * 支持：暂停/启用/禁用
     *
     * @param accountId
     * @param status
     */
    void statusChange(Long accountId, SimpleStatus status);

    void statusChange(String accountNo, SimpleStatus status);

}
