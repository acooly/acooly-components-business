package com.acooly.module.member;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-23 00:07
 */
public class MemberOptionMapping {

    public static Map<Integer, String> allUserTypes = Maps.newLinkedHashMap();
    static {
        allUserTypes.put(1, "个人用户");
        allUserTypes.put(2, "企业用户");
    }
}
