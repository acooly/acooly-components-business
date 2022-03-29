package com.acooly.module.point.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.service.EntityService;
import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.module.point.PointProperties;
import com.acooly.module.point.entity.PointGrade;
import com.acooly.module.point.service.PointGradeService;
import com.acooly.module.security.domain.User;
import com.google.common.collect.Maps;

public class AbstractPointManageController<T extends AbstractEntity, M extends EntityService<T>>
		extends AbstractJsonEntityController<T, M> {

	/**
	 * 业务系统枚举类
	 */
	private Map<String, String> busiTypeMap = Maps.newLinkedHashMap();

	@Autowired
	protected PointProperties pointProperties;
	@Autowired
	protected PointGradeService pointGradeService;

	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		super.referenceData(request, model);
		model.put("pointModuleName", pointProperties.getPointModuleName());
	}

	/**
	 * 获取积分等级
	 * 
	 * @return
	 */
	protected Map<Long, String> getPointGradeMap() {
		Map<Long, String> map = Maps.newLinkedHashMap();
		for (PointGrade pointGrade : pointGradeService.getAll()) {
			map.put(pointGrade.getId(), pointGrade.getTitle());
		}
		return map;
	}

	/**
	 * 获取业务枚举
	 * 
	 * @param busiType
	 * @return
	 */
	protected Map<String, String> getBusiTypeMap() {
		if (busiTypeMap.isEmpty()) {
			busiTypeMap = pointProperties.getPointBusiTypeEnum();
		}
		return busiTypeMap;
	}

	/**
	 * 获取枚举描述
	 * 
	 * @param busiType
	 * @return
	 */
	protected String getBusiTypeText(String busiType) {
		String busiTypeText = getBusiTypeMap().get(busiType);
		return busiTypeText;
	}

	/**
	 * 获取当前登录用户
	 * 
	 * @return
	 */
	protected User getBossUser() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		return user;
	}

}
