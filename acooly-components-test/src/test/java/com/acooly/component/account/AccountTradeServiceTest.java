package com.acooly.component.account;

import com.acooly.component.account.dto.AccountInfo;
import com.acooly.component.account.dto.AccountKeepInfo;
import com.acooly.component.account.dto.TransferInfo;
import com.acooly.component.account.entity.Account;
import com.acooly.component.account.enums.AccountTypeEnum;
import com.acooly.component.account.enums.DirectionEnum;
import com.acooly.component.account.service.AccountTradeService;
import com.acooly.component.account.service.tradecode.CommonTradeCodeEnum;
import com.acooly.component.account.service.tradecode.DefaultTradeCode;
import com.acooly.core.common.boot.Apps;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Money;
import com.acooly.module.test.AppTestBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

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

    static final long TEST_FROM_ID = 100;
    static final long TEST_TO_ID = 101;

    @Autowired
    private AccountTradeService accountTradeService;

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
     * 根据用户ID关联开户，用户ID与账户ID不同
     * 重点：不设置AccountId，由系统自己生成
     */
    @Test
    public void testOpenAccountByUserId() {

        try {
            AccountInfo accountInfo = new AccountInfo(null, null, 1000L, Ids.getDid(), AccountTypeEnum.main);
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
        AccountKeepInfo accountKeepInfo = new AccountKeepInfo(TEST_FROM_ID, CommonTradeCodeEnum.deposit, Money.amout("120"), "充值");
        // 可选参数
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
        AccountKeepInfo accountKeepInfo = new AccountKeepInfo(TEST_FROM_ID,
                new DefaultTradeCode("quickDeposit", "快速充值", DirectionEnum.in)
                , Money.amout("120"), "自定义外部交易码");
        accountTradeService.keepAccount(accountKeepInfo);
    }

    /**
     * 测试批量记账
     */
    @Test
    public void testKeepAccounts() {
        // accountId = 13的账户付款给14的账户120
        List<AccountKeepInfo> accountKeepInfos = new ArrayList<>();
        accountKeepInfos.add(new AccountKeepInfo(TEST_FROM_ID, CommonTradeCodeEnum.transfer_out, Money.amout("20")));
        accountKeepInfos.add(new AccountKeepInfo(TEST_TO_ID, CommonTradeCodeEnum.transfer_in, Money.amout("20")));
        accountTradeService.keepAccounts(accountKeepInfos);
    }


    /**
     * 测试单笔转账
     */
    @Test
    public void testTransfer() {
        TransferInfo transferInfo = new TransferInfo(TEST_FROM_ID, TEST_TO_ID, Money.amout("1"));
        accountTradeService.transfer(transferInfo);
    }







    private static Money getRandomAmount() {
        return Money.cent(RandomUtils.nextLong(1, 20));
    }

    private static Pair<Long, Long> getPair(long start, long end) {
        long from = RandomUtils.nextLong(start, end);
        long to = RandomUtils.nextLong(start, end);
        while (from == to) {
            to = RandomUtils.nextLong(start, end);
        }
        return Pair.of(from, to);
    }

}
