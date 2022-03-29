package com.acooly.module.point.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.acooly.module.point.dao.PointStatisticsExtDao;

@Service("pointStatisticsExtDao")
public class PointStatisticsExtDaoImpl implements PointStatisticsExtDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void pointClearCountByOverdueDate(String overdueDate) {
		String sql = "insert into pt_point_statistics(user_no,user_name,num,total_clear_point,overdue_date,create_time,update_time)"
				+ "(SELECT user_no,user_name,count(1),sum(amount),overdue_date,now(),now() FROM pt_point_trade where trade_type='produce' and overdue_date='"
				+ overdueDate + "' GROUP BY user_no)";
		jdbcTemplate.execute(sql);
	}
}
