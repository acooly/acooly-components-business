<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>ID:</th>
		<td>${accountBill.id}</td>
	</tr>					
	<tr>
		<th width="25%">账户:</th>
		<td>${accountBill.accountId}/${accountBill.accountNo}</td>
	</tr>
	<tr>
		<th>用户:</th>
		<td>${accountBill.userId}/${accountBill.userNo}</td>
	</tr>
	<tr>
		<th>用户名:</th>
		<td>${accountBill.username}</td>
	</tr>					
	<tr>
		<th>交易金额:</th>
		<td>${accountBill.amount}</td>
	</tr>					
	<tr>
		<th>变动后余额:</th>
		<td>${accountBill.balancePost}</td>
	</tr>					
	<tr>
		<th>资金流向:</th>
		<td>${accountBill.direction.message}</td>
	</tr>					
	<tr>
		<th>交易码:</th>
		<td>${accountBill.tradeCode}/${allTradeCodes[accountBill.tradeCode]}</td>
	</tr>
	<tr>
		<th>相关业务ID:</th>
		<td>${accountBill.busiId}</td>
	</tr>					
	<tr>
		<th>相关业务数据:</th>
		<td>${accountBill.busiData}</td>
	</tr>					
	<tr>
		<th>状态:</th>
		<td>${accountBill.status.message}</td>
	</tr>					
	<tr>
		<th>交易时间:</th>
		<td><fmt:formatDate value="${accountBill.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<th>备注:</th>
		<td>${accountBill.comments}</td>
	</tr>					
</table>
</div>
