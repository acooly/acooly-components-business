package com.acooly.component.account;

import com.acooly.component.account.dto.AccountInfo;
import com.acooly.component.account.service.AccountVerifyService;
import com.acooly.core.common.boot.Apps;
import com.acooly.module.test.AppTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 18:19
 */
@Slf4j
public class AccountVerifyServiceTest extends AppTestBase {

    public static final String PROFILE = "sdev";

    //设置环境
    static {
        Apps.setProfileIfNotExists(PROFILE);
    }

    static final long TEST_FROM_ID = 100;
    static final long TEST_TO_ID = 101;

    @Autowired
    private AccountVerifyService accountVerifyService;

    /**
     * 验证账户
     * 余额和流水逻辑
     */
    @Test
    public void testVerifyAccount() {
        accountVerifyService.verifyAccount(new AccountInfo(TEST_FROM_ID));
    }

}
