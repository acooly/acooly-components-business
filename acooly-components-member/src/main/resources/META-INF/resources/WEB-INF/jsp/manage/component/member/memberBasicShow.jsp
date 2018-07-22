<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>会员ID:</th>
		<td>${memberBasic.id}</td>
	</tr>					
	<tr>
		<th width="25%">会员编码:</th>
		<td>${memberBasic.userNo}</td>
	</tr>					
	<tr>
		<th>用户名:</th>
		<td>${memberBasic.username}</td>
	</tr>					
	<tr>
		<th>头像类型:</th>
		<td>${memberBasic.profilePhotoType.message}</td>
	</tr>					
	<tr>
		<th>头像:</th>
		<td>${memberBasic.profilePhoto}</td>
	</tr>					
	<tr>
		<th>昵称:</th>
		<td>${memberBasic.nickname}</td>
	</tr>					
	<tr>
		<th>个性签名:</th>
		<td>${memberBasic.dailyWords}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${memberBasic.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${memberBasic.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${memberBasic.comments}</td>
	</tr>					
</table>
</div>
