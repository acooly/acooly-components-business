package com.acooly.module.member.service.busitype;

import com.acooly.core.utils.Collections3;
import com.acooly.module.member.MemberBusiType;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 会员业务类型集成服务
 *
 * @author zhangpu
 * @date 2018-06-06
 */
public interface MemberBusiTypeLoader {


    /**
     * 加载业务类型
     *
     * @return
     */
    List<MemberBusiType> loadMemberBusiTypes();

    /**
     * 业务类型的Mapping
     *
     * @return
     */
    default Map<String, String> mapping() {
        Map<String, String> mapping = Maps.newLinkedHashMap();
        List<MemberBusiType> memberBusiTypes = loadMemberBusiTypes();
        if (Collections3.isNotEmpty(memberBusiTypes)) {
            for (MemberBusiType memberBusiType : memberBusiTypes) {
                mapping.put(memberBusiType.getCode(), memberBusiType.getName());
            }
        }
        return mapping;
    }


}
