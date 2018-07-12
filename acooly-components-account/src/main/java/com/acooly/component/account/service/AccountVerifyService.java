package com.acooly.component.account.service;

import com.acooly.component.account.dto.AccountInfo;

/**
 * 账户验证(自检)服务
 *
 * @author zhangpu@acooly.cn
 * @date 2018-07-10 02:30
 */
public interface AccountVerifyService {

    /**
     * 验证制定账户的数据完整性
     *
     * @param accountInfo
     */
    void verifyAccount(AccountInfo accountInfo);
}
