package com.acooly.module.member.dto;

import com.acooly.core.common.facade.LinkedHashMapParameterize;
import com.acooly.core.utils.Strings;
import com.acooly.module.member.enums.AuthRoleEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import javax.validation.constraints.NotEmpty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author zhangpu
 * @date 2018-10-16 16:36
 */
@Getter
@Setter
@Slf4j
public class MemberAuthInfo extends LinkedHashMapParameterize<String, Object> implements Serializable {


    private Long id;

    /**
     * 会员ID
     */
    @NotNull
    private Long userId;

    /**
     * 会员编码
     */
    @Size(max = 64)
    private String userNo;

    /**
     * 会员名
     */
    @NotEmpty
    @Size(max = 32)
    private String username;

    /**
     * 认证角色
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private AuthRoleEnum authRole = AuthRoleEnum.admin;

    /**
     * 登录ID,默认为username
     */
    @Size(max = 32)
    private String loginId;

    /**
     * 登录秘钥/密码
     */
    @NotEmpty
    @Size(max = 256)
    private String loginKey;


    public String getLabel() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getUserId() != null) {
            sb.append("{userId:").append(getUserId()).append(",");
        }
        if (Strings.isNotBlank(getUserNo())) {
            sb.append("userNo:").append(getUserNo()).append(",");
        }
        if (Strings.isNotBlank(getUsername())) {
            sb.append("username:").append(getUsername()).append(",");
        }
        if (Strings.isNotBlank(getLoginId())) {
            sb.append("loginId:").append(getLoginId()).append(",");
        }
        return sb.substring(0, sb.length() - 1) + "}";
    }

}
