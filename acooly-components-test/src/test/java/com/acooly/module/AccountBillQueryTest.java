package com.acooly.module;

import com.acooly.module.entity.AccountBill;
import com.acooly.module.manage.AccountBillService;
import com.acooly.module.service.AccountTradeService;
import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.utils.Money;
import com.acooly.module.test.AppTestBase;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * 进出账明细查询测试
 *
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 18:19
 */
@Slf4j
public class AccountBillQueryTest extends AppTestBase {

    public static final String PROFILE = "sdev";

    //设置环境
    static {
        Apps.setProfileIfNotExists(PROFILE);
    }


    static final int TEST_ACCOUNT_COUNT = 10;
    static final int TEST_ACCOUNT_INIT_BALANCE = 10000000;

    @Autowired
    private AccountBillService accountBillService;

    @Autowired
    private AccountTradeService accountTradeService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Before
    public void init() {
//        log.info("初始化账户和充值");
//        for (long i = 1; i <= TEST_ACCOUNT_COUNT; i++) {
//            accountTradeService.openAccount(new AccountInfo(i, Ids.getDid()));
//            accountTradeService.keepAccount(new AccountKeepInfo(i, CommonTradeCodeEnum.deposit, Money.cent(TEST_ACCOUNT_INIT_BALANCE)));
//        }

//        log.info("构造账户流水");
//        List<TransferInfo> transferInfos = Lists.newArrayList();
//        Pair<Long, Long> pair = null;
//        for (int j = 0; j < 100; j++) {
//            transferInfos.clear();
//            for (int i = 0; i < TEST_ACCOUNT_COUNT * 10; i++) {
//                pair = getPair(1, TEST_ACCOUNT_COUNT + 1);
//                transferInfos.add(new TransferInfo(pair.getFirst(), pair.getSecond(), getRandomAmount()));
//            }
//            accountTradeService.transfer(transferInfos);
//        }


    }

    @After
    public void after() {
        log.info("后置处理");
//        jdbcTemplate.execute("truncate table ac_account_bill");
//        jdbcTemplate.execute("truncate table ac_account");
    }

    /**
     * 根据时间段查询
     */
    @Test
    public void testAccountBillWithTimeSlot() {
        long start = System.currentTimeMillis();

        Map<String, Object> map = Maps.newHashMap();
        map.put("GTE_createTime", "2018-07-14 13:47:25");
        map.put("LT_createTime", "2018-07-14 13:47:26");
        PageInfo<AccountBill> pageInfo = new PageInfo<>();
        accountBillService.query(pageInfo, map, null);
        log.info("totalCount:{}, pageSize:{}", pageInfo.getTotalCount(), pageInfo.getCountOfCurrentPage());
        log.info("分页查询执行时间: {}", System.currentTimeMillis() - start);
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
