/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-27 13:53 创建
 */
package com.acooly.module.account;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.utils.Money;
import com.acooly.module.test.AppTestBase;
import com.acooly.module.test.Main;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author zhangpu 2018-07-27 13:53
 */
@SpringBootTest(
        classes = Main.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public abstract class AbstractAccountTest extends AppTestBase {

    public static final String PROFILE = "sdev";

    //设置环境
    static {
        Apps.setProfileIfNotExists(PROFILE);
    }

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    static final UserInfo USER_FROM = new UserInfo(1L, "20180000000000000001", "zhangpu");
    static final UserInfo USER_DEST = new UserInfo(2L, "20180000000000000002", "acooly");



    protected void cleanAccountDatabase(String accountNo) {
        jdbcTemplate.execute("delete from ac_account where account_no = '" + accountNo + "'");
        jdbcTemplate.execute("delete from ac_account_bill where account_no = '" + accountNo + "'");
    }


    protected static Money getRandomAmount() {
        return Money.cent(RandomUtils.nextLong(1, 20));
    }

    protected static Pair<Long, Long> getPair(long start, long end) {
        long from = RandomUtils.nextLong(start, end);
        long to = RandomUtils.nextLong(start, end);
        while (from == to) {
            to = RandomUtils.nextLong(start, end);
        }
        return Pair.of(from, to);
    }
}
