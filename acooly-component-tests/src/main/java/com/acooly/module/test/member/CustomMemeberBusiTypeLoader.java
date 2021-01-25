/**
 * acooly-components-business
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-01-25 11:17
 */
package com.acooly.module.test.member;

import com.acooly.module.member.MemberBusiType;
import com.acooly.module.member.service.busitype.MemberBusiTypeLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangpu
 * @date 2021-01-25 11:17
 */
@Slf4j
@Component
public class CustomMemeberBusiTypeLoader implements MemberBusiTypeLoader {

    @Override
    public List<MemberBusiType> loadMemberBusiTypes() {
        return CustomBusiType.getAll();
    }


    public static enum CustomBusiType implements MemberBusiType {
        VIP("VIP", "用户"),
        STATION("STATION", "站点"),
        PARTNER("PARTNER", "接入方");

        private final String code;
        private final String name;

        CustomBusiType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public static List<MemberBusiType> getAll() {
            List<MemberBusiType> list = new ArrayList<>();
            for (CustomBusiType status : values()) {
                list.add(status);
            }
            return list;
        }
    }
}
