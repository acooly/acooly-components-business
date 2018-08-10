package com.acooly.module.account;

import com.acooly.core.utils.Money;
import com.acooly.module.AbstractComponentsTest;
import com.acooly.module.account.dto.AccountKeepInfo;
import com.acooly.module.account.dto.TransferInfo;
import com.acooly.module.account.enums.DirectionEnum;
import com.acooly.module.account.service.AccountTradeService;
import com.acooly.module.account.enums.CommonTradeCodeEnum;
import com.acooly.module.account.service.tradecode.DefaultTradeCode;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 18:19
 */
@Slf4j
public class AccountTradeServiceTest extends AbstractComponentsTest {


    static final long TEST_FROM_ID = 100;
    static final long TEST_TO_ID = 101;

    @Autowired
    private AccountTradeService accountTradeService;

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
        TransferInfo transferInfo = new TransferInfo(TEST_FROM_ID, TEST_TO_ID, Money.amout("10"));
        accountTradeService.transfer(transferInfo);
    }


    /**
     * 测试单笔冻结和解冻
     */
    @Test
    public void testFreezeAndUnFreeze() {
        accountTradeService.freeze(TEST_FROM_ID, Money.amout("20"), "测试冻结");
        accountTradeService.unfreeze(TEST_FROM_ID, Money.amout("10"), "测试解冻");
    }


    /**
     * 测试批量冻结和解冻
     */
    @Test
    public void testBatchFreezeAndUnFreeze() {
        accountTradeService.freeze(Lists.newArrayList(TEST_FROM_ID, TEST_TO_ID), Money.cent(20), "测试批量冻结");
        accountTradeService.unfreeze(Lists.newArrayList(TEST_FROM_ID, TEST_TO_ID), Money.cent(10), "测试批量解冻");
    }


}
