package com.acooly.module.member.service.busitype;

import com.acooly.module.member.MemberBusiType;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 默认内置加载器
 *
 * @author zhangpu@acooly.cn
 * @date 2020-12-12 15:27
 */
public class DefaultMemberBusiTypeLoader implements MemberBusiTypeLoader {

    @Override
    public List<MemberBusiType> loadMemberBusiTypes() {
        return Lists.newArrayList(new DefaultMemberBusiType("default", "默认"));
    }
}
