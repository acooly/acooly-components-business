package com.acooly.module.member.service.auth;

import com.acooly.core.common.exception.BusinessException;
import org.springframework.core.Ordered;

/**
 * 会员操作员认证拦截器
 * <p>
 * 同步调用
 *
 * @author zhangpu
 * @date 2018-10-26 16:59
 */
public interface MemberAuthIntercepter extends Ordered {

    /**
     * 认证前置拦截
     * <p>
     * 基础检查完成，并成功加载了认证操作员信息，待安全认证（密码等）
     * 抛出异常则终止认证，结果为认证失败，被error拦截处理
     *
     * @param memberAuthContext
     */
    default void before(MemberAuthContext memberAuthContext) {

    }


    /**
     * 认证成功
     *
     * @param memberAuthContext
     */
    default void success(MemberAuthContext memberAuthContext) {

    }


    /**
     * 认证失败
     * <p>
     * catch后有异常
     *
     * @param memberAuthContext
     * @param e
     */
    default void error(MemberAuthContext memberAuthContext, BusinessException e) {

    }


    /**
     * 认证完成
     * <p>
     * 独立事务，可修改认证实体数据，并最后会被保存，异常无效。
     *
     * @param memberAuthContext
     */
    default void complete(MemberAuthContext memberAuthContext) {

    }

    /**
     * 日志label
     *
     * @return
     */
    default String label() {
        return getOrder() + ":" + this.getClass().getName();
    }

}
