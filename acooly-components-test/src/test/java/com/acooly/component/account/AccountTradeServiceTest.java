package com.acooly.component.account;

import com.acooly.component.account.dto.AccountInfo;
import com.acooly.component.account.dto.AccountKeepInfo;
import com.acooly.component.account.entity.Account;
import com.acooly.component.account.enums.DirectionEnum;
import com.acooly.component.account.service.AccountTradeService;
import com.acooly.component.account.service.tradecode.CommonTradeCodeEnum;
import com.acooly.component.account.service.tradecode.DefaultTradeCode;
import com.acooly.core.common.boot.Apps;
import com.acooly.core.utils.Money;
import com.acooly.module.test.AppTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 18:19
 */
@Slf4j
public class AccountTradeServiceTest extends AppTestBase {

    public static final String PROFILE = "sdev";

    //设置环境
    static {
        Apps.setProfileIfNotExists(PROFILE);
    }

    @Autowired
    private AccountTradeService accountTradeService;

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
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setUserId(13l);
            accountInfo.setUsername("zhangpu");
            accountInfo.setComments("开户");
            Account account = accountTradeService.openAccount(accountInfo);
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
     * 用户ID与账户ID一致
     * 重点：设置AccountId为UserId
     */
    @Test
    public void testOpenAccountWithUserIdEquelsAccountId() {
        try {
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setUserId(14l);
            accountInfo.setAccountId(accountInfo.getUserId());
            accountInfo.setUsername("zhangpu");
            accountInfo.setComments("开户");
            Account account = accountTradeService.openAccount(accountInfo);
            log.info("Account:{}", account);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    /**
     * 测试单笔记账
     */
    @Test
    public void testKeepAccount() {
        AccountKeepInfo accountKeepInfo = new AccountKeepInfo();
        accountKeepInfo.setAccountId(13l);
        accountKeepInfo.setTradeCode(CommonTradeCodeEnum.deposit);
        accountKeepInfo.setAmount(Money.amout("120"));
        accountKeepInfo.setComments("充值");
        accountKeepInfo.setBusiId(1l);
        accountKeepInfo.setBusiData("busiId是充值交易的流水，这里可以做会话参数，可以是JSON格式");
        accountTradeService.keepAccount(accountKeepInfo);
    }


    /**
     * 测试单笔记账
     * <p>
     * 采用外部自定义的TradeCode
     */
    @Test
    public void testKeepAccountWithTradeCode() {
        AccountKeepInfo accountKeepInfo = new AccountKeepInfo();
        accountKeepInfo.setAccountId(13l);
        accountKeepInfo.setTradeCode(new DefaultTradeCode("quickDeposit", "快速充值", DirectionEnum.in));
        accountKeepInfo.setAmount(Money.amout("120"));
        accountKeepInfo.setComments("充值");
        accountKeepInfo.setBusiId(1l);
        accountKeepInfo.setBusiData("busiId是充值交易的流水，这里可以做会话参数，可以是JSON格式");
        accountTradeService.keepAccount(accountKeepInfo);
    }

    /**
     * 测试批量记账
     */
    @Test
    public void testKeepAccounts() {
        // accountId = 13的账户付款给14的账户120
        List<AccountKeepInfo> accountKeepInfos = new ArrayList<>();
        accountKeepInfos.add(new AccountKeepInfo(13l, CommonTradeCodeEnum.transfer_out, Money.amout("320")));
        accountKeepInfos.add(new AccountKeepInfo(14l, CommonTradeCodeEnum.transfer_in, Money.amout("320")));
        accountTradeService.keepAccounts(accountKeepInfos);
    }


    /**
     * 查询所有可用交易码
     */
    @Test
    public void testQueryTradeCodes() {
        // accountId = 13的账户付款给14的账户120
        List<AccountKeepInfo> accountKeepInfos = new ArrayList<>();
        accountKeepInfos.add(new AccountKeepInfo(13l, CommonTradeCodeEnum.transfer_out, Money.amout("320")));
        accountKeepInfos.add(new AccountKeepInfo(14l, CommonTradeCodeEnum.transfer_in, Money.amout("320")));
        accountTradeService.keepAccounts(accountKeepInfos);
    }


}
