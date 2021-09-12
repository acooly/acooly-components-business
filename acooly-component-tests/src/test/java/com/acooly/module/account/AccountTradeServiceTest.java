package com.acooly.module.account;

import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Money;
import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.dto.AccountKeepInfo;
import com.acooly.module.account.dto.TransferInfo;
import com.acooly.module.account.enums.CommonTradeCodeEnum;
import com.acooly.module.account.enums.DirectionEnum;
import com.acooly.module.account.service.AccountTradeService;
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
public class AccountTradeServiceTest extends AbstractAccountTest {


    @Autowired
    private AccountTradeService accountTradeService;

    /**
     * 测试单笔（单边）记账(充值)
     */
    @Test
    public void testKeepAccountWithAccountNoForDeposit() {
        String accountNo = USER_FROM.getUserNo();
        AccountKeepInfo accountKeepInfo = new AccountKeepInfo(accountNo, CommonTradeCodeEnum.deposit, Money.amout("120"), "充值");
        // 可选参数
        accountKeepInfo.setBusiId(1l);
        accountKeepInfo.setBusiData("JSON会话参数");
        accountTradeService.keepAccount(accountKeepInfo);
    }


    /**
     * 测试单笔（单边）记账（自定义交易码）
     * <p>
     * 采用外部自定义的TradeCode
     */
    @Test
    public void testKeepAccountWithCustomTradeCode() {
        String accountNo = USER_FROM.getUserNo();
        AccountKeepInfo accountKeepInfo = new AccountKeepInfo(accountNo,
                new DefaultTradeCode("quickDeposit", "快速充值", DirectionEnum.in)
                , Money.amout("120"), "自定义外部交易码");
        accountTradeService.keepAccount(accountKeepInfo);
    }

    /**
     * 测试批量记账(双边记账)
     */
    @Test
    public void testKeepAccounts() {
        // accountId = 13的账户付款给14的账户120
        List<AccountKeepInfo> accountKeepInfos = new ArrayList<>();
        accountKeepInfos.add(new AccountKeepInfo(USER_FROM.getUserNo(), CommonTradeCodeEnum.transfer_out, Money.amout("20")));
        accountKeepInfos.add(new AccountKeepInfo(USER_DEST.getUserNo(), CommonTradeCodeEnum.transfer_in, Money.amout("20")));
        accountTradeService.keepAccounts(accountKeepInfos);
    }


    /**
     * 测试单笔转账
     */
    @Test
    public void testTransfer() {
        String merchOrderNo = Ids.did();
        AccountInfo from = AccountInfo.withNo(USER_FROM.getUserNo());
//        from.setBizOrderNo(Ids.oid());
//        from.setMerchOrderNo(merchOrderNo);
        AccountInfo to = AccountInfo.withNo(USER_DEST.getUserNo());
//        to.setBizOrderNo(Ids.oid());
//        to.setMerchOrderNo(merchOrderNo);
        TransferInfo transferInfo = new TransferInfo(from, to, CommonTradeCodeEnum.transfer_out, CommonTradeCodeEnum.transfer_in, Money.amout("10"));
        transferInfo.setMerchOrderNo(merchOrderNo);
        accountTradeService.transfer(transferInfo);
    }


    /**
     * 测试单笔冻结和解冻
     * (accountNo = userNo)
     */
    @Test
    public void testFreezeAndUnFreeze() {
//        accountTradeService.freeze(AccountInfo.withNo(USER_FROM.getUserNo()), Money.amout("20"), "测试冻结");
        accountTradeService.unfreeze(AccountInfo.withNo(USER_FROM.getUserNo()), Money.amout("20"), "测试解冻");
    }


    /**
     * 测试批量冻结和解冻
     */
    @Test
    public void testBatchFreezeAndUnFreeze() {
        accountTradeService.freezes(
                Lists.newArrayList(AccountInfo.withNo(USER_FROM.getUserNo()), AccountInfo.withNo(USER_DEST.getUserNo())),
                Money.cent(20), "测试批量冻结");
        accountTradeService.unfreezes(
                Lists.newArrayList(AccountInfo.withNo(USER_FROM.getUserNo()), AccountInfo.withNo(USER_DEST.getUserNo())),
                Money.cent(10), "测试批量解冻");
    }


}
