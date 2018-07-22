package com.acooly.module;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Money;
import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.dto.AccountKeepInfo;
import com.acooly.module.account.dto.TransferInfo;
import com.acooly.module.account.service.AccountManageService;
import com.acooly.module.account.service.AccountTradeService;
import com.acooly.module.account.service.AccountVerifyService;
import com.acooly.module.account.service.tradecode.CommonTradeCodeEnum;
import com.acooly.module.test.AppTestBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 18:19
 */
@Slf4j
public class AccountTransferTest extends AppTestBase {

    public static final String PROFILE = "sdev";

    //设置环境
    static {
        Apps.setProfileIfNotExists(PROFILE);
    }


    static final int TEST_ACCOUNT_COUNT = 10;
    static final int TEST_ACCOUNT_INIT_BALANCE = 10000000;

    @Autowired
    private AccountTradeService accountTradeService;
    @Autowired
    private AccountManageService accountManageService;
    @Autowired
    private AccountVerifyService accountVerifyService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Before
    public void init() {
        log.info("初始化账户");
        jdbcTemplate.execute("truncate table ac_account_bill");
        jdbcTemplate.execute("truncate table ac_account");
        for (long i = 1; i <= TEST_ACCOUNT_COUNT; i++) {
            accountManageService.openAccount(new AccountInfo(i, Ids.getDid()));
            accountTradeService.keepAccount(new AccountKeepInfo(i, CommonTradeCodeEnum.deposit, Money.cent(TEST_ACCOUNT_INIT_BALANCE)));
        }
    }

    @After
    public void after() {
        log.info("后置处理");
        for (long i = 1; i <= TEST_ACCOUNT_COUNT; i++) {
            accountVerifyService.verifyAccount(new AccountInfo(i));
        }
    }

    /**
     * 测试单笔转账
     */
    @Test
    public void testTransfer() {
        long start = System.currentTimeMillis();
        List<TransferInfo> transferInfos = Lists.newArrayList();
        Pair<Long, Long> pair = null;
        for (int i = 0; i < TEST_ACCOUNT_COUNT * 10; i++) {
            pair = getPair(1, TEST_ACCOUNT_COUNT + 1);
            transferInfos.add(new TransferInfo(pair.getFirst(), pair.getSecond(), getRandomAmount()));
        }
        accountTradeService.transfer(transferInfos);
        log.info("批量记账执行时间: {}", System.currentTimeMillis() - start);
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
