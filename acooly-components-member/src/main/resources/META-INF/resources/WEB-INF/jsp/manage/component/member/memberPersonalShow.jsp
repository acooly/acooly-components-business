<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>ID:</th>
		<td>${memberPersonal.id}</td>
	</tr>					
	<tr>
		<th width="25%">用户编码:</th>
		<td>${memberPersonal.userNo}</td>
	</tr>					
	<tr>
		<th>用户名:</th>
		<td>${memberPersonal.username}</td>
	</tr>					
	<tr>
		<th>婚姻状况:</th>
		<td>${memberPersonal.maritalStatus.message}</td>
	</tr>					
	<tr>
		<th>子女情况:</th>
		<td>${allChildrenCounts[memberPersonal.childrenCount]}</td>
	</tr>					
	<tr>
		<th>教育背景:</th>
		<td>${memberPersonal.educationLevel.message}</td>
	</tr>					
	<tr>
		<th>月收入:</th>
		<td>${memberPersonal.incomeMonth.message}</td>
	</tr>					
	<tr>
		<th>社会保险:</th>
		<td>${memberPersonal.socialInsurance.message}</td>
	</tr>					
	<tr>
		<th>公积金:</th>
		<td>${memberPersonal.houseFund.message}</td>
	</tr>					
	<tr>
		<th>住房情况:</th>
		<td>${memberPersonal.houseStatue.message}</td>
	</tr>					
	<tr>
		<th>是否购车:</th>
		<td>${memberPersonal.carStatus.message}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${memberPersonal.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${memberPersonal.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${memberPersonal.comments}</td>
	</tr>					
</table>
</div>
