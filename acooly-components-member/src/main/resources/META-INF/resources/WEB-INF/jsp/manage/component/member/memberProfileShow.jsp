<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>ID:</th>
		<td>${memberProfile.id}</td>
	</tr>					
	<tr>
		<th width="25%">用户编码:</th>
		<td>${memberProfile.userNo}</td>
	</tr>					
	<tr>
		<th>用户名:</th>
		<td>${memberProfile.username}</td>
	</tr>					
	<tr>
		<th>昵称:</th>
		<td>${memberProfile.nickname}</td>
	</tr>					
	<tr>
		<th>个性签名:</th>
		<td>${memberProfile.dailyWords}</td>
	</tr>					
	<tr>
		<th>头像类型:</th>
		<td>${memberProfile.profilePhotoType.message}</td>
	</tr>					
	<tr>
		<th>头像:</th>
		<td>${memberProfile.profilePhoto}</td>
	</tr>					
	<tr>
		<th>实名认证:</th>
		<td>${memberProfile.realNameStatus.message}</td>
	</tr>					
	<tr>
		<th>手机认证:</th>
		<td>${memberProfile.mobileNoStatus.message}</td>
	</tr>					
	<tr>
		<th>邮箱认证:</th>
		<td>${memberProfile.emailStatus.message}</td>
	</tr>					
	<tr>
		<th>发送短信:</th>
		<td>${memberProfile.smsSendStatus.message}</td>
	</tr>					
	<tr>
		<th>安全问题设置状态:</th>
		<td>${memberProfile.secretQaStatus}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${memberProfile.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${memberProfile.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${memberProfile.comments}</td>
	</tr>					
</table>
</div>
