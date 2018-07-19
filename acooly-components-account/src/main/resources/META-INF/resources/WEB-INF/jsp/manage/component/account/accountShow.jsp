<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>ID:</th>
		<td>${account.id}</td>
	</tr>					
	<tr>
		<th width="25%">账户类型:</th>
		<td>${account.accountType.message}</td>
	</tr>					
	<tr>
		<th>用户编号</th>
		<td>${account.userName}</td>
	</tr>					
	<tr>
		<th>用户ID，外部集成环境用户/客户标志，可选提高性能:</th>
		<td>${account.userId}</td>
	</tr>					
	<tr>
		<th>余额:</th>
		<td>${account.balance}</td>
	</tr>					
	<tr>
		<th>冻结金额:</th>
		<td>${account.freeze}</td>
	</tr>					
	<tr>
		<th>状态:</th>
		<td>${account.status.message}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${account.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${account.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${account.comments}</td>
	</tr>					
</table>
</div>
