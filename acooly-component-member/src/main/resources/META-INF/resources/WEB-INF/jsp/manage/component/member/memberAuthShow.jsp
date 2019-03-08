<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>ID:</th>
		<td>${memberAuth.id}</td>
	</tr>					
	<tr>
		<th width="25%">用户ID:</th>
		<td>${memberAuth.userId}</td>
	</tr>					
	<tr>
		<th>用户编码:</th>
		<td>${memberAuth.userNo}</td>
	</tr>					
	<tr>
		<th>用户名:</th>
		<td>${memberAuth.username}</td>
	</tr>					
	<tr>
		<th>认证类型:</th>
		<td>${memberAuth.authType.message}</td>
	</tr>					
	<tr>
		<th>认证角色:</th>
		<td>${memberAuth.authRole.message}</td>
	</tr>					
	<tr>
		<th>登录ID:</th>
		<td>${memberAuth.loginId}</td>
	</tr>					
	<tr>
		<th>登录秘钥/密码:</th>
		<td>${memberAuth.loginKey}</td>
	</tr>					
	<tr>
		<th>认证加盐:</th>
		<td>${memberAuth.loginSalt}</td>
	</tr>					
	<tr>
		<th>有效期
:</th>
		<td><fmt:formatDate value="${memberAuth.expireTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>解锁时间:</th>
		<td><fmt:formatDate value="${memberAuth.unlockTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>登录失败次数:</th>
		<td>${memberAuth.loginFailTimes}</td>
	</tr>					
	<tr>
		<th>最后登录时间:</th>
		<td><fmt:formatDate value="${memberAuth.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>是否登录:</th>
		<td>${memberAuth.loginStatus.message}</td>
	</tr>					
	<tr>
		<th>状态:</th>
		<td>${memberAuth.authStatus.message}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${memberAuth.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${memberAuth.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${memberAuth.comments}</td>
	</tr>					
</table>
</div>
