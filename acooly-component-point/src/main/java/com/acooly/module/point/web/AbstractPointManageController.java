package com.acooly.module.point.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.service.EntityService;
import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.module.point.PointProperties;
import com.acooly.module.point.domain.PointGrade;
import com.acooly.module.point.service.PointGradeService;
import com.google.common.collect.Maps;

public class AbstractPointManageController<T extends AbstractEntity, M extends EntityService<T>>
		extends AbstractJsonEntityController<T, M> {

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

}
