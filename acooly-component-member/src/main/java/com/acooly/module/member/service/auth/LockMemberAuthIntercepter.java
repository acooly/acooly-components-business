package com.acooly.module.member.service.auth;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Dates;
import com.acooly.module.member.MemberProperties;
import com.acooly.module.member.entity.MemberAuth;
import com.acooly.module.member.enums.AuthStatusEnum;
import com.acooly.module.member.error.MemberErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * 会员操作人认证拦截器：操作员锁定处理
 *
 * @author zhangpu
 * @date 2018-10-26 17:13
 */
@Component("lockMemberAuthIntercepter")
@Slf4j
public class LockMemberAuthIntercepter implements MemberAuthIntercepter {

    @Autowired
    protected MemberProperties memberProperties;


    @Override
    public void before(MemberAuthContext memberAuthContext) {
        // 锁定状态，判断是否该解锁定，是则处理解锁，否则抛出异常 --> 认证失败
        if (memberAuthContext.getMemberAuth().getAuthStatus() == AuthStatusEnum.invalid_locked
                || !isShouldBeUnLocked(memberAuthContext.getMemberAuth())) {
            log.error("认证 [失败:锁定] - {}, memberAuth:{}", MemberErrorEnum.AUTH_OPERATOR_LOCKED, memberAuthContext.getMemberAuth());
            throw new BusinessException(MemberErrorEnum.AUTH_OPERATOR_LOCKED);
        }
    }


    @Override
    public void success(MemberAuthContext memberAuthContext) {
        // 登录成功：清理登录失败次数
        if (memberAuthContext.getMemberAuth().getAuthStatus() == AuthStatusEnum.invalid_locked) {
            memberAuthContext.getMemberAuth().setAuthStatus(AuthStatusEnum.enable);
            memberAuthContext.getMemberAuth().setLoginFailTimes(0);
        }
    }

    @Override
    public void error(MemberAuthContext memberAuthContext, BusinessException e) {
        // 登录失败：排除系统错误和锁定错误外，累加登录失败次数
        if (MemberErrorEnum.AUTH_VERIFY_FAIL.code().equals(e.getCode())) {
            memberAuthContext.getMemberAuth().setLoginFailTimes(memberAuthContext.getMemberAuth().getLoginFailTimes() + 1);
        }
    }

    @Override
    public void complete(MemberAuthContext memberAuthContext) {
        // 如果状态为正常：判断处理是否该锁定
        if (memberAuthContext.getMemberAuth().getAuthStatus() == AuthStatusEnum.enable && isShouldBeLock(memberAuthContext.getMemberAuth())) {
            doLock(memberAuthContext.getMemberAuth());
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }


    /**
     * 判断是否应该解锁
     *
     * @param memberAuth
     * @return
     */
    protected boolean isShouldBeUnLocked(MemberAuth memberAuth) {
        Date unlockTime = memberAuth.getUnlockTime();
        // 判断是否该解锁
        if (unlockTime == null) {
            return true;
        }
        return dateSub(unlockTime, Calendar.MINUTE) > 0;
    }

    /**
     * 判断是否应该锁定
     *
     * @param memberAuth
     * @return
     */
    protected boolean isShouldBeLock(MemberAuth memberAuth) {
        // 某个时间段内，认证失败的次数超过阈值则锁定
        if (memberAuth.getLoginFailTimes() >= memberProperties.getAuth().getLockFailTimes()) {
            return true;
        }
        return false;
    }

    /**
     * 锁定
     *
     * @param memberAuth
     */
    protected void doLock(MemberAuth memberAuth) {
        Date unlockTime = Dates.addDate(new Date(), memberProperties.getAuth().getLockMinutes() * 60 * 1000);
        memberAuth.setUnlockTime(unlockTime);
        memberAuth.setAuthStatus(AuthStatusEnum.invalid_locked);
        log.info("认证 [设置锁定] 状态和到期时间。memberAuth:{},lockMinutes:{}, unlockTime:{}",
                memberAuth, memberProperties.getAuth().getLockMinutes(), Dates.format(unlockTime));
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


}
