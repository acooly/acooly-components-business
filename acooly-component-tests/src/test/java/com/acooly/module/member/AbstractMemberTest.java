/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-27 13:53 创建
 */
package com.acooly.module.member;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.utils.Money;
import com.acooly.module.AbstractComponentsTest;
import com.acooly.module.account.UserInfo;
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
public abstract class AbstractMemberTest extends AbstractComponentsTest {

    protected void cleanMemberDatabase(String username) {
        jdbcTemplate.execute("delete from b_member where username = '" + username + "'");
        jdbcTemplate.execute("delete from b_member_profile where username = '" + username + "'");
        jdbcTemplate.execute("delete from b_member_contact where username = '" + username + "'");
        jdbcTemplate.execute("delete from b_member_personal where username = '" + username + "'");
        jdbcTemplate.execute("delete from b_member_enterprise where username = '" + username + "'");
        jdbcTemplate.execute("delete from b_member_auth where username = '" + username + "'");
        jdbcTemplate.execute("delete from ac_account where username = '" + username + "'");
    }

}
