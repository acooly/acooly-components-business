package com.acooly.module.account;

import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.entity.Account;
import com.acooly.module.account.enums.AccountTypeEnum;
import com.acooly.module.account.service.AccountManageService;
import com.acooly.core.common.boot.Apps;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.enums.SimpleStatus;
import com.acooly.module.test.AppTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 18:19
 */
@Slf4j
public class AccountManageServiceTest extends AppTestBase {

    public static final String PROFILE = "sdev";

    //设置环境
    static {
        Apps.setProfileIfNotExists(PROFILE);
    }

    static final long TEST_FROM_ID = 100;
    static final long TEST_TO_ID = 101;

    @Autowired
    private AccountManageService accountManageService;

    /**
     * 测试开户
     * <p>
     * <li>如果唯一标志在Account表存在，则直接返回，否则创建新的账户</li>
     * <li>如果不设置AccountNo，则系统自动生成</li>
     *
     * <p>
     * 用户ID与账户ID一致
     * 用户No与账户No一致
     * 重点：设置AccountId为UserId
     */
    @Test
    public void testOpenAccountWithUserIdEquelsAccountId() {
        try {
            AccountInfo accountInfo = new AccountInfo(TEST_TO_ID, Ids.getDid());
            accountInfo.setUsername("zhangpu");
            accountInfo.setComments("开户");
            Account account = accountManageService.openAccount(accountInfo);
            log.info("Account:{}", account);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    /**
     * 测试开户
     * <p>
     * <li>如果唯一标志在Account表存在，则直接返回，否则创建新的账户</li>
     * <li>如果不设置AccountNo，则系统自动生成</li>
     *
     * <p>
     * 根据用户ID关联开户，用户ID与账户ID不同
     * 重点：不设置AccountId，由系统自己生成
     */
    @Test
    public void testOpenAccountByUserId() {

        try {
            AccountInfo accountInfo = new AccountInfo(TEST_FROM_ID);
            Account account = accountManageService.loadAccount(accountInfo);
            Assert.assertNotNull(account);
            String accountNo = account.getAccountNo();
            account = accountManageService.loadAccount(new AccountInfo(accountNo));
            Assert.assertEquals(accountNo, account.getAccountNo());
            log.info("Account:{}", account);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * 根据账户ID或No查询账户
     */
    @Test
    public void testloadAccountByIdOrNo() {

        try {
            AccountInfo accountInfo = new AccountInfo(null, null, 1000L, Ids.getDid(), AccountTypeEnum.main);
            accountInfo.setUsername("zhangpu");
            accountInfo.setComments("开户");
            Account account = accountManageService.openAccount(accountInfo);
            log.info("Account:{}", account);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * 状态变更测试
     */
    @Test
    public void testAccountStatusChange() {

        try {
            Account account = accountManageService.loadAccount(new AccountInfo(TEST_FROM_ID));
            log.info("Account:{}", account);
            log.info("初始状态: {}", account.getStatus());
            accountManageService.statusChange(TEST_FROM_ID, SimpleStatus.pause);
            accountManageService.statusChange(TEST_FROM_ID, SimpleStatus.enable);
            accountManageService.statusChange(TEST_FROM_ID, SimpleStatus.disable);
            accountManageService.statusChange(TEST_FROM_ID, SimpleStatus.enable);
            Assert.fail("修改禁用状态通过则测试失败");
        } catch (Exception e) {
            System.out.println("OK");
        }
    }


}
