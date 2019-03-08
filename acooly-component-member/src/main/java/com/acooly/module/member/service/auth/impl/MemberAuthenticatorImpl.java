package com.acooly.module.member.service.auth.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.utils.Assert;
import com.acooly.core.utils.Strings;
import com.acooly.module.member.dto.MemberAuthInfo;
import com.acooly.module.member.dto.MemberInfo;
import com.acooly.module.member.entity.MemberAuth;
import com.acooly.module.member.enums.AuthStatusEnum;
import com.acooly.module.member.error.MemberErrorEnum;
import com.acooly.module.member.manage.MemberAuthEntityService;
import com.acooly.module.member.service.auth.MemberAuthIntercepter;
import com.acooly.module.member.service.auth.MemberAuthenticator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhangpu
 * @date 2018-10-30 16:53
 */
@Component
@Slf4j
public class MemberAuthenticatorImpl implements MemberAuthenticator {


    @Autowired
    private MemberAuthEntityService memberAuthEntityService;
//
//    @Resource(name = "memberAuthIntercepter")
//    private MemberAuthIntercepter memberAuthIntercepter;

    @Override
    public MemberAuth authentication(MemberAuthInfo memberAuthInfo) {

//        boolean success = false;
//        MemberAuth memberAuth = null;
//        MemberInfo memberInfo = MemberInfo.of(memberAuthInfo.getUserId(), memberAuthInfo.getUserNo(), memberAuthInfo.getUsername());
//        MemberAuthContext memberAuthContext = new MemberAuthContext();
//        try {
//            doParamsCheck(memberAuthInfo);
//            memberAuth = loadAndCheckMemberAuth(memberInfo, memberAuthInfo.getLoginId());
//            memberAuthContext.setMemberAuth(memberAuth);
//            memberAuthContext.setMemberAuthInfo(memberAuthInfo);
//            //同步：前置拦截组
//            memberAuthIntercepter.before(memberAuthContext);
//
//            // 验证是否过期
//            doVerifyExpire(memberAuth);
//            // 验证是否锁定
//            doVerifyLock(memberAuth);
//            // 验证密码
//            doValidatePassword(memberAuth, key);
//            success = true;
//            log.info("认证 [成功]  memberInfo:{}", memberAuth.getLabel());
//        } catch (IllegalArgumentException ae) {
//            throw new BusinessException(MemberErrorEnum.PARAMETER_ERROR, ae.getMessage());
//        } catch (BusinessException be) {
//            throw be;
//        } catch (Exception e) {
//            log.error("修改密码 - [失败] - {}", e.getMessage());
//            throw new BusinessException();
//        } finally {
//            if (memberAuth != null) {
//                updateAuthResult(memberAuth, success);
//            }
//        }


        return null;
    }


    /**
     * 参数合法性检查
     *
     * @param memberAuthInfo
     */
    protected void doParamsCheck(MemberAuthInfo memberAuthInfo) {
        if (memberAuthInfo.getUserId() == null
                && Strings.isBlank(memberAuthInfo.getUserNo())
                && Strings.isBlank(memberAuthInfo.getUsername())) {
            log.warn("认证 [失败] 参数检查未通过 用户标志至少存在一个");
            OrderCheckException.throwIt("会员标志", "userId/userNo/username至少一个不为空");
        }
        Assert.notNull(memberAuthInfo.getAuthRole(), "登录角色不能为空");
        Assert.hasLength(memberAuthInfo.getLoginId(), "登录名不能为空");
        Assert.hasLength(memberAuthInfo.getLoginKey(), "登录密码不能为空");
    }


    /**
     * 加载操作员并做基础验证
     *
     * @param memberInfo
     * @param loginId
     * @return
     */
    protected MemberAuth loadAndCheckMemberAuth(MemberInfo memberInfo, String loginId) {
        MemberAuth memberAuth = memberAuthEntityService.loadMemberAuth(memberInfo, loginId);
        if (memberAuth == null) {
            log.error("认证 [失败：操作员不存在] - {} 。memberInfo:{}, loginId:{}", MemberErrorEnum.AUTH_OPERATOR_NOT_EXIST,
                    memberInfo, loginId);
            throw new BusinessException(MemberErrorEnum.AUTH_OPERATOR_NOT_EXIST);
        }
        // 判断操作员状态
        if (memberAuth.getAuthStatus() == AuthStatusEnum.invalid_disabled) {
            log.error("认证 [失败：状态非法] - {}, loginId:{},authStatus:{}",
                    MemberErrorEnum.AUTH_STATUS_NOT_ENABLE, memberAuth.getLoginId(), memberAuth.getAuthStatus());
            throw new BusinessException(MemberErrorEnum.AUTH_STATUS_NOT_ENABLE, memberAuth.getAuthStatus().getMessage());
        }
        return memberAuth;
    }

    /**
     * 密码认证
     *
     * @param memberAuth
     * @param password
     */
    protected void doValidatePassword(MemberAuth memberAuth, String password) {
        String dbSalt = memberAuth.getLoginSalt();
        String enPassword = digestPassword(password, dbSalt);
        String dbPassword = memberAuth.getLoginKey();
        if (!Strings.equals(enPassword, dbPassword)) {
            log.warn("认证 [失败] - {}, memberAuth:{}", MemberErrorEnum.LOGIN_PASSWORD_VERIFY_FAIL, memberAuth);
            throw new BusinessException(MemberErrorEnum.LOGIN_VERIFY_FAIL);
        }
    }

    protected String digestPassword(String plainPassword, String salt) {
        return null;
//        return Encodes.encodeHex(Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_INTERATIONS));
    }
}
