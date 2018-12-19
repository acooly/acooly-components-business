package com.acooly.module.account;

import com.acooly.core.common.boot.Apps;
import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.service.AccountVerifyService;
import com.acooly.module.test.AppTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 18:19
 */
@Slf4j
public class AccountVerifyServiceTest extends AbstractAccountTest {

    @Autowired
    private AccountVerifyService accountVerifyService;

    /**
     * 验证账户
     * 余额和流水逻辑
     */
    @Test
    public void testVerifyAccount() {
        accountVerifyService.verifyAccount(AccountInfo.withNo(USER_FROM.getUserNo()));
    }

}
