package com.acooly.module.member.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.*;
import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.core.utils.validate.Validators;
import com.acooly.module.member.MemberProperties;
import com.acooly.module.member.dto.MemberAuthInfo;
import com.acooly.module.member.dto.MemberInfo;
import com.acooly.module.member.entity.MemberAuth;
import com.acooly.module.member.enums.AuthRoleEnum;
import com.acooly.module.member.enums.AuthStatusEnum;
import com.acooly.module.member.enums.LoginStatusEnum;
import com.acooly.module.member.error.MemberErrorEnum;
import com.acooly.module.member.manage.MemberAuthEntityService;
import com.acooly.module.member.service.MemberAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zhangpu
 * @date 2018-10-16 17:18
 */
@Component
@Slf4j
public class MemberAuthServiceImpl implements MemberAuthService {

    public static final int SALT_SIZE = 8;
    public static final int HASH_INTERATIONS = 512;

    @Autowired
    private MemberAuthEntityService memberAuthEntityService;
    @Autowired
    private MemberProperties memberProperties;

    @Override
    public MemberAuth create(MemberAuthInfo memberAuthInfo) {
        try {
            doCreateCheck(memberAuthInfo);
            MemberAuth memberAuth = new MemberAuth();
            BeanCopier.copy(memberAuthInfo, memberAuth);
            memberAuth.setExpireTime(Dates.addDay(new Date(), memberProperties.getAuth().getPasswordExpireDays()));
            doDigestPassword(memberAuth);
            memberAuthEntityService.save(memberAuth);
            log.info("操作员创建 成功 memberAuth:{}", memberAuth.getLabel());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(MemberErrorEnum.MEMBER_PARAMS_ERROR, e.getMessage());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("创建会员操作员账号失败{}", e.getMessage());
            throw new BusinessException(MemberErrorEnum.MEMBER_INTERNAL_ERROR, "创建会员操作员账号失败");
        }
        return null;
    }


    @Override
    public MemberAuth updateStatus(MemberInfo memberInfo, String loginId, AuthStatusEnum authStatus) {
        MemberAuth memberAuth = null;
        try {
            memberAuth = loadAndCheckMemberAuth(memberInfo, loginId);
            AuthStatusEnum originStatus = memberAuth.getAuthStatus();
            if (authStatus == memberAuth.getAuthStatus()) {
                log.info("操作员状态 [成功] 需要修改的目标状态与当前操作员状态一致，直接返回成功。 memberAuth:{}", memberAuth.getLabel());
                return memberAuth;
            }

            if (memberAuth.getAuthStatus() == AuthStatusEnum.invalid_expired || authStatus == AuthStatusEnum.invalid_expired) {
                log.warn("操作员状态 [失败] 密码过期状态不能直接修改，请使用修改密码或找回密码方式。 memberAuth:{}", memberAuth.getLabel());
                throw new BusinessException(MemberErrorEnum.AUTH_UNSUPPORT_OPERATE, "密码过期状态不能直接修改，请使用修改密码或找回密码方式");
            }

            // 解锁
            if (authStatus == AuthStatusEnum.invalid_locked) {
                log.warn("操作员状态 [失败] 不支持直接设置锁定。 memberAuth:{}", memberAuth.getLabel());
                throw new BusinessException(MemberErrorEnum.AUTH_UNSUPPORT_OPERATE, "不支持直接设置锁定");
            }

            if (memberAuth.getAuthStatus() == AuthStatusEnum.invalid_locked) {
                if (authStatus != AuthStatusEnum.enable) {
                    log.warn("操作员状态 [失败] 不支持直接变更锁定状态为非正常状态。 memberAuth:{}", memberAuth.getLabel());
                    throw new BusinessException(MemberErrorEnum.AUTH_UNSUPPORT_OPERATE, "不支持直接变更锁定状态为非正常状态");
                }
                // do 解锁定
                memberAuth.setAuthStatus(AuthStatusEnum.enable);
                memberAuth.setLoginFailTimes(0);
            }

            // 禁用/启用
            memberAuth.setAuthStatus(authStatus);
            memberAuthEntityService.update(memberAuth);
            log.info("操作员状态 [成功] 状态：{} -> {},  memberInfo:{}", originStatus, memberAuth.getAuthStatus(), memberAuth.getLabel());
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("操作员状态 - [失败] - {}", e.getMessage());
            throw new BusinessException();
        }
        return memberAuth;
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updatePassword(Long id, String newPassword) {
        try {
            MemberAuth memberAuth = memberAuthEntityService.get(id);
            if (memberAuth == null) {
                log.error("更新密码 - [失败] - 操作员不存在。id：{}", id);
                throw new BusinessException(MemberErrorEnum.AUTH_OPERATOR_NOT_EXIST);
            }
            memberAuth.setLoginKey(newPassword);
            doDigestPassword(memberAuth);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("更新密码 - [失败] - {}", e.getMessage());
            throw new BusinessException();
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updatePassword(MemberInfo memberInfo, String loginId, String newPassword) {
        try {
            MemberAuth memberAuth = loadAndCheckMemberAuth(memberInfo, loginId);
            doUpdatePassword(memberAuth, newPassword);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("更新密码 - [失败] - {}", e.getMessage());
            throw new BusinessException();
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void changePassword(MemberInfo memberInfo, String loginId, String oldPassword, String newPassword) {
        try {
            MemberAuth memberAuth = loadAndCheckMemberAuth(memberInfo, loginId);
            doValidatePassword(memberAuth, oldPassword);
            doUpdatePassword(memberAuth, newPassword);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("修改密码 - [失败] - {}", e.getMessage());
            throw new BusinessException();
        }
    }

    @Override
    public void auth(MemberInfo memberInfo, String key) {
        auth(memberInfo, memberInfo.getUsername(), key);
    }

    @Override
    public void auth(MemberInfo memberInfo, String loginId, String key) {
        boolean success = false;
        MemberAuth memberAuth = null;
        try {
            memberAuth = loadAndCheckMemberAuth(memberInfo, loginId);

            // 验证基础状态
            doVerifyStatus(memberAuth);
            // 验证是否过期
            doVerifyExpire(memberAuth);
            // 验证是否锁定
            doVerifyLock(memberAuth);
            // 验证密码
            doValidatePassword(memberAuth, key);
            success = true;
            log.info("认证 [成功]  memberInfo:{}", memberAuth.getLabel());
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("修改密码 - [失败] - {}", e.getMessage());
            throw new BusinessException();
        } finally {
            if (memberAuth != null) {
                updateAuthResult(memberAuth, success);
            }
        }
    }

    /**
     * 更新登录认证结果
     *
     * @param memberAuth
     * @param success
     * @return
     */
    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public MemberAuth updateAuthResult(MemberAuth memberAuth, boolean success) {
        if (success) {
            // 登录成功了，则解锁定
            memberAuth.setLoginFailTimes(0);
            memberAuth.setUnlockTime(null);
            memberAuth.setLastLoginTime(new Date());
            memberAuth.setLoginStatus(LoginStatusEnum.loged);
        } else {
            // 登录认证失败

            if (isShouldBeExpired(memberAuth)) {
                memberAuth.setAuthStatus(AuthStatusEnum.invalid_expired);
            }
            memberAuth.setLoginFailTimes(memberAuth.getLoginFailTimes() + 1);
            if (isShouldBeLocked(memberAuth)) {
                memberAuth.setUnlockTime(Dates.addDate(new Date(), memberProperties.getAuth().getLockMinutes() * 60 * 1000));
                memberAuth.setAuthStatus(AuthStatusEnum.invalid_locked);
            }
            memberAuth.setLoginStatus(LoginStatusEnum.notlogin);
        }
        memberAuthEntityService.updateAuthResult(memberAuth);
        return memberAuth;
    }

    protected void doVerifyStatus(MemberAuth memberAuth) {

        // 判断操作员状态
        if (memberAuth.getAuthStatus() == AuthStatusEnum.invalid_disabled) {
            log.error("认证 [失败] - {}, loginId:{},authStatus:{}",
                    MemberErrorEnum.AUTH_STATUS_NOT_ENABLE, memberAuth.getLoginId(), memberAuth.getAuthStatus());
            throw new BusinessException(MemberErrorEnum.AUTH_STATUS_NOT_ENABLE, memberAuth.getAuthStatus().getMessage());
        }
    }

    /**
     * 验证登录状态
     *
     * @param memberAuth
     */
    protected void doVerifyLock(MemberAuth memberAuth) {

        if (!memberProperties.getAuth().isLock()) {
            return;
        }

        if (memberAuth.getAuthStatus() == AuthStatusEnum.invalid_locked || isShouldBeLocked(memberAuth)) {
            log.error("认证 [失败:锁定] - {}, memberAuth:{}", MemberErrorEnum.AUTH_OPERATOR_LOCKED, memberAuth);
            throw new BusinessException(MemberErrorEnum.AUTH_OPERATOR_LOCKED);
        }

    }


    /**
     * 验证密码是否过期
     *
     * @param memberAuth
     */
    protected void doVerifyExpire(MemberAuth memberAuth) {
        // 配置打开是否支持过期特性
        if (!memberProperties.getAuth().isPasswordExpire()) {
            return;
        }

        // 登录认证判断，已过期账号，登录异常
        if (memberAuth.getAuthStatus() == AuthStatusEnum.invalid_expired || isShouldBeExpired(memberAuth)) {
            log.error("认证 [失败:过期] - {}, memberAuth:{}", MemberErrorEnum.AUTH_KEY_EXPIRED, memberAuth);
            throw new BusinessException(MemberErrorEnum.AUTH_KEY_EXPIRED, "请尽快修改或找回密码");
        }
    }


    /**
     * 判断密码是否过期
     *
     * @param memberAuth
     * @return
     */
    protected boolean isShouldBeExpired(MemberAuth memberAuth) {
        Date expire = memberAuth.getExpireTime();
        return (expire != null && dateSub(expire, Calendar.DAY_OF_MONTH) > 0);
    }

    protected boolean isShouldBeLocked(MemberAuth memberAuth) {
        Date unlockTime = memberAuth.getUnlockTime();
        // 判断是否该解锁
        if (unlockTime == null) {
            return false;
        }
        return dateSub(unlockTime, Calendar.MINUTE) < memberProperties.getAuth().getLockMinutes();
    }


    /**
     * 日期减法(分钟)
     *
     * @return
     */
    private long dateSub(Date rightDate, int type) {
        if (rightDate == null) {
            return -1;
        }
        try {
            return Dates.sub(new Date(), rightDate, type);
        } catch (Exception e) {
            return -1;
        }
    }

    protected void doCreateCheck(MemberAuthInfo memberAuthInfo) {
        Validators.assertJSR303(memberAuthInfo);
        List<MemberAuth> memberAuthList = memberAuthEntityService.findByAuthRole(MemberInfo.of(memberAuthInfo.getUsername()), memberAuthInfo.getAuthRole());
        if (Collections3.isNotEmpty(memberAuthList)) {
            // 检查并处理一个会员主体只能有一个主账户
            if (AuthRoleEnum.admin == memberAuthInfo.getAuthRole()) {
                log.info("主体已存在主账户。主体：{}，账号: {}", memberAuthInfo.getLabel(), Collections3.getFirst(memberAuthList).getLabel());
                throw new BusinessException(MemberErrorEnum.AUTH_ADMIN_NOT_UNIQUE);
            } else {
                for (MemberAuth memberAuth : memberAuthList) {
                    if (Strings.equals(memberAuthInfo.getLoginId(), memberAuth.getLoginId())) {
                        log.info("{}，主体：{}，账号: {}", MemberErrorEnum.AUTH_LOGINID_NOT_UNIQUE.message(),
                                memberAuthInfo.getLabel(), memberAuth.getLabel());
                        throw new BusinessException(MemberErrorEnum.AUTH_LOGINID_NOT_UNIQUE);
                    }
                }
            }
        }
    }


    protected void doValidatePassword(MemberAuth memberAuth, String password) {
        String dbSalt = memberAuth.getLoginSalt();
        String enPassword = digestPassword(password, dbSalt);
        String dbPassword = memberAuth.getLoginKey();
        if (!Strings.equals(enPassword, dbPassword)) {
            log.warn("认证 [失败] - {}, memberAuth:{}", MemberErrorEnum.LOGIN_PASSWORD_VERIFY_FAIL, memberAuth);
            throw new BusinessException(MemberErrorEnum.LOGIN_VERIFY_FAIL);
        }
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
            log.error("操作员查询 [失败] - {} 。memberInfo:{}, loginId:{}", MemberErrorEnum.AUTH_OPERATOR_NOT_EXIST,
                    memberInfo, loginId);
            throw new BusinessException(MemberErrorEnum.AUTH_OPERATOR_NOT_EXIST);
        }

        return memberAuth;
    }

    protected void doUpdatePassword(MemberAuth memberAuth, String newPassword) {
        memberAuth.setLoginKey(newPassword);
        doDigestPassword(memberAuth);
    }

    protected void doDigestPassword(MemberAuth memberAuth) {
        String salt = Encodes.encodeHex(Digests.generateSalt(SALT_SIZE));
        memberAuth.setLoginSalt(salt);
        memberAuth.setLoginKey(digestPassword(memberAuth.getLoginKey(), salt));
    }

    protected String digestPassword(String plainPassword, String salt) {
        return Encodes.encodeHex(Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_INTERATIONS));
    }


}
