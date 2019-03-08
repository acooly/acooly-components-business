package com.acooly.module.account;

import com.acooly.core.utils.enums.SimpleStatus;
import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.entity.Account;
import com.acooly.module.account.enums.AccountTypeEnum;
import com.acooly.module.account.service.AccountManageService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 18:19
 */
@Slf4j
public class AccountManageServiceTest extends AbstractAccountTest {


    @Autowired
    private AccountManageService accountManageService;


    /**
     * 测试开户(全参数)
     *
     * <p>
     * 1、accountNo为空，则表示：自动生成accountNo，如果传入userNo这一般表示默认账户userNo=accountNo
     * 2、userId和userNo必输一个,建议都必选
     * 3、accountType不输入则默认为内置的:"main"
     * 4、username为可选
     */
    @Test
    public void testOpenAccountWithFullArgs() {
        UserInfo creatingUser = USER_DEST;
        try {
//            cleanAccountDatabase(creatingUser.getUserNo());
            AccountInfo accountInfo = new AccountInfo(creatingUser.getUserNo(), creatingUser.getId(), creatingUser.getUserNo(),
                    creatingUser.getUsername(), AccountTypeEnum.main.code());
            accountInfo.setComments("创建默认账户类型账户，accountNo=userNo");
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
     * accountNo != userNo, accountNo为空，则自动生成
     * accountType = main
     */
    @Test
    public void testOpenAccountWithUserIdEquelsAccountId() {
        try {
            AccountInfo accountInfo = new AccountInfo(USER_DEST.getId(), USER_DEST.getUserNo(), USER_DEST.getUsername());
            Account account = accountManageService.openAccount(accountInfo);
            log.info("Account:{}", account);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    /**
     * 根据账户No查询账户
     */
    @Test
    public void testloadAccountByNo() {
        try {
            String userFromNo = USER_FROM.getUserNo();
            Account account = accountManageService.loadAccount(new AccountInfo(userFromNo));
            Assert.assertEquals(userFromNo, account.getAccountNo());
            log.info("Account:{}", account);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * 根据userNo+accountType查询
     */
    @Test
    public void testloadAccountByUserNoAndAccountType() {
        try {
            String userDestNo = USER_DEST.getUserNo();
            Account account = accountManageService.loadAccount(new AccountInfo(userDestNo, AccountTypeEnum.main.code()));
            Assert.assertNotNull(account);
            Assert.assertEquals(userDestNo, account.getUserNo());
            Assert.assertEquals(AccountTypeEnum.main.code(), account.getAccountType());
            log.info("\nAccount:{}", JSON.toJSONString(account, true));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * 根据用户userId+accountType查询
     */
    @Test
    public void testloadAccountByUserIdAndAccountType() {
        try {
            Long userId = USER_DEST.getId();
            Account account = accountManageService.loadAccount(new AccountInfo(userId, AccountTypeEnum.main.code()));
            Assert.assertNotNull(account);
            Assert.assertEquals(userId, account.getUserId());
            Assert.assertEquals(AccountTypeEnum.main.code(), account.getAccountType());
            log.info("\nAccount:{}", JSON.toJSONString(account, true));
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
            String accountNo = USER_FROM.getUserNo();
            Account account = accountManageService.loadAccount(new AccountInfo(accountNo));
            log.info("Account:{}", account);
            log.info("初始状态: {}", account.getStatus());
            accountManageService.statusChange(accountNo, SimpleStatus.pause);
            accountManageService.statusChange(accountNo, SimpleStatus.enable);
            accountManageService.statusChange(accountNo, SimpleStatus.disable);
            accountManageService.statusChange(accountNo, SimpleStatus.enable);
            Assert.fail("修改禁用状态通过则测试失败");
        } catch (Exception e) {
            System.out.println("OK");
        }
    }


}
