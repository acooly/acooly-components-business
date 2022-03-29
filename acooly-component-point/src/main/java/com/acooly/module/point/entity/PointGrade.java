/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.entity;

import com.acooly.core.common.domain.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 积分等级 Entity
 *
 * @author cuifuqiang Date: 2017-02-03 22:47:28
 */
@Setter
@Getter
@Entity
@Table(name = "pt_point_grade")
public class PointGrade extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /** id */
    /**
     * 等级
     */
    private Integer num = 1;
    /**
     * 标题
     */
    private String title;
    /**
     * 积分区间_开始
     */
    private Long startPoint = 0l;
    /**
     * 积分区间_结束
     */
    private Long endPoint = 0l;
    /**
     * 图标
     */
    private String picture;
    /**
     * 备注
     */
    private String comments;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
