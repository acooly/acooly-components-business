/*
 * acooly.cn Inc.
 * Copyright (c) 2021 All Rights Reserved.
 * create by zhangpu@acooly.cn
 * date:2021-08-12
 */
package cn.acooly.component.assetmgmt.entity;


import cn.acooly.component.assetmgmt.enums.SecretStatus;
import cn.acooly.component.assetmgmt.enums.SecretTypeEnum;
import cn.acooly.component.assetmgmt.enums.ServiceTypeEnum;
import com.acooly.core.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 安全仓（资产） Entity
 *
 * @author zhangpu@acooly.cn
 * @date 2021-08-12 22:44:09
 */
@Entity
@Table(name = "ac_secretbox")
@Getter
@Setter
public class Secretbox extends AbstractEntity {

    /**
     * 默认的类型主题
     */
    public static final String TREE_TYPE_THEME = "secretbox";

    /**
     * 编码
     * 唯一编码，可输入或自动生成
     */
    @NotBlank
    @Size(max = 32)
    private String code;

    /**
     * 标题
     */
    @NotBlank
    @Size(max = 45)
    private String title;

    /**
     * 分类编码
     */
    @Size(max = 64)
    private String typeCode;

    /**
     * 分类名称
     */
    private String typeName;

    /**
     * 所在目录
     */
    private String typePath;

    /**
     * 安全类型
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private SecretTypeEnum secretType;

    /**
     * 账号
     */
    @NotBlank
    @Size(max = 128)
    private String username;

    /**
     * 密码
     */
    @Size(max = 512)
    @ToString.Exclude
    private String password;

    /**
     * 访问入口
     */
    @Size(max = 128)
    private String accessPoint;

    /**
     * 服务类型
     */
    @Enumerated(EnumType.STRING)
    private ServiceTypeEnum serviceType;

    /**
     * 简介
     */
    @Size(max = 256)
    private String subject;

    /**
     * 绑定邮箱地址
     */
    @Size(max = 128)
    private String bindEmail;

    /**
     * 绑定手机号码
     */
    @Size(max = 16)
    private String bindMobileNo;

    /**
     * 有效期（到期时间）
     */
    private Date expired;

    /**
     * 状态
     */
    private SecretStatus secretStatus;

    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;

}
