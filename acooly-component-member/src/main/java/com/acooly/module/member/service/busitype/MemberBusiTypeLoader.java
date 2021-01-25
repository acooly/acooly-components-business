package com.acooly.module.member.service.busitype;

import com.acooly.module.member.MemberBusiType;

import java.util.List;

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


}
