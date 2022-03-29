package com.acooly.module.point.event;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

/**
 * 发布积分事件(积分变动)
 *
 * @author cuifuq7
 */
@Setter
@Getter
public class PointAccountChangeEvent {

	/**
	 * 用户号
	 */
	private String userNo;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 积分余额
	 */
	private long balance = 0l;
	/**
	 * 冻结
	 */
	private long freeze = 0l;
	/**
	 * 可用余额
	 */
	private long available = 0l;
	/**
	 * 总消费积分 *
	 */
	private long totalExpensePoint = 0l;
	/**
	 * 总产生积分 *
	 */
	private long totalProducePoint = 0l;
	/**
	 * 积分等级 *
	 */
	private long gradeId;
	/**
	 * 积分等级标题 *
	 */
	private String gradeTitle;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
