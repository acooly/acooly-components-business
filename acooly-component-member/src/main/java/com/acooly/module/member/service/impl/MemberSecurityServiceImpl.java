package com.acooly.module.member.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Strings;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.MemberStatusEnum;
import com.acooly.module.member.exception.MemberErrorEnum;
import com.acooly.module.member.exception.MemberOperationException;
import com.acooly.module.member.service.AbstractMemberService;
import com.acooly.module.member.service.MemberSecurityService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 会员安全服务
 *
 * @author zhangpu@acooly.cn
 * @date 2018-08-04 18:57
 */
@Slf4j
@Component
public class MemberSecurityServiceImpl extends AbstractMemberService implements MemberSecurityService {


    @Override
    public void login(String username, String password) {
        Assert.notNull(username, "用户名不能为空");
        Assert.notNull(password, "密码不能为空");
        try {
            Member member = loadMember(null, null, username);
            if (member == null) {
                log.warn("认证 [失败] 原因:{}, username:{}", MemberErrorEnum.MEMEBER_NOT_EXIST, username);
                throw new MemberOperationException(MemberErrorEnum.LOGIN_VERIFY_FAIL, username);
            }
            if (member.getStatus() != MemberStatusEnum.enable) {
                log.warn("认证 [失败] 原因:{}, username:{}", MemberErrorEnum.MEMEBER_STATUS_NOT_ENABLE, username);
                throw new MemberOperationException(MemberErrorEnum.LOGIN_VERIFY_FAIL);
            }
            doValidatePassword(member, password);
            log.info("认证 成功 username:{}", username);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("认证 失败 内部错误：{}", e);
            throw new MemberOperationException(MemberErrorEnum.MEMBER_INTERNAL_ERROR, "注册内部错误");
        }

    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        doModifyPassword(username, oldPassword, newPassword);
    }

    @Override
    public void resetPassword(String username, String newPassword) {
        doModifyPassword(username, null, newPassword);
    }


    protected void doModifyPassword(String username, String oldPassword, String newPassword) {
        Assert.notNull(username, "用户名不能为空");
        Assert.notNull(newPassword, "新密码不能为空");
        try {
            Member member = loadAndCheckMember(null, null, username);
            if (Strings.isNoneBlank(oldPassword)) {
                doValidatePassword(member, oldPassword);
            }
            member.setPassword(newPassword);
            doDigestPassword(member);
            memberEntityService.update(member);
            log.info("修改密码 成功 username:{}", username);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("修改密码 失败 内部错误：{}", e);
            throw new MemberOperationException(MemberErrorEnum.MEMBER_INTERNAL_ERROR);
        }
    }
}
