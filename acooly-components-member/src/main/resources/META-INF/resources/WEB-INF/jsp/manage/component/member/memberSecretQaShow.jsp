<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${memberSecretQa.id}</td>
	</tr>					
	<tr>
		<th width="25%">用户ID:</th>
		<td>${memberSecretQa.userid}</td>
	</tr>					
	<tr>
		<th>用户编码:</th>
		<td>${memberSecretQa.userNo}</td>
	</tr>					
	<tr>
		<th>问题:</th>
		<td>${memberSecretQa.question}</td>
	</tr>					
	<tr>
		<th>答案:</th>
		<td>${memberSecretQa.answer}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${memberSecretQa.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${memberSecretQa.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${memberSecretQa.comments}</td>
	</tr>					
</table>
</div>
