<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>会员ID:</th>
		<td>${member.id}</td>
	</tr>					
	<tr>
		<th width="25%">父会员ID:</th>
		<td>${member.parentid}</td>
	</tr>					
	<tr>
		<th>父会员编码:</th>
		<td>${member.parentUserNo}</td>
	</tr>					
	<tr>
		<th>会员编码:</th>
		<td>${member.userNo}</td>
	</tr>					
	<tr>
		<th>用户名:</th>
		<td>${member.username}</td>
	</tr>					
	<tr>
		<th>密码:</th>
		<td>${member.password}</td>
	</tr>					
	<tr>
		<th>密码盐:</th>
		<td>${member.salt}</td>
	</tr>					
	<tr>
		<th>用户类型:</th>
		<td>${allUserTypes[member.userType]}</td>
	</tr>					
	<tr>
		<th>手机号码:</th>
		<td>${member.mobileNo}</td>
	</tr>					
	<tr>
		<th>邮件:</th>
		<td>${member.email}</td>
	</tr>					
	<tr>
		<th>姓名:</th>
		<td>${member.realName}</td>
	</tr>					
	<tr>
		<th>身份证号码:</th>
		<td>${member.idCardNo}</td>
	</tr>					
	<tr>
		<th>用户等级:</th>
		<td>${allGrades[member.grade]}</td>
	</tr>					
	<tr>
		<th>状态:</th>
		<td>${member.status.message}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${member.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${member.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${member.comments}</td>
	</tr>					
</table>
</div>
