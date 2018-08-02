/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-27 13:53 创建
 */
package com.acooly.module;

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
public abstract class AbstractComponentsTest extends AppTestBase {

    public static final String PROFILE = "sdev";

    //设置环境
    static {
        Apps.setProfileIfNotExists(PROFILE);
    }

    @Autowired
    protected JdbcTemplate jdbcTemplate;


    protected void cleanMemberDatabase(String username) {
        jdbcTemplate.execute("delete from b_member where username = '" + username + "'");
        jdbcTemplate.execute("delete from b_member_profile where username = '" + username + "'");
        jdbcTemplate.execute("delete from b_member_contact where username = '" + username + "'");
        jdbcTemplate.execute("delete from b_member_personal where username = '" + username + "'");
        jdbcTemplate.execute("delete from b_member_enterprise where username = '" + username + "'");
        jdbcTemplate.execute("delete from ac_account where username = '" + username + "'");
    }

    protected void cleanAccountDatabase(Long accountId) {
        jdbcTemplate.execute("delete from ac_account where id = " + accountId);
        jdbcTemplate.execute("delete from ac_account_bill where account_id = " + accountId);
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
