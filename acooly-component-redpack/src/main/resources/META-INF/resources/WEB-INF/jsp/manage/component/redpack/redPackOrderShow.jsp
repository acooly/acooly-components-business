<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${redPackOrder.id}</td>
	</tr>					
	<tr>
		<th width="25%">订单号:</th>
		<td>${redPackOrder.orderNo}</td>
	</tr>					
	<tr>
		<th>红包id:</th>
		<td>${redPackOrder.redPackId}</td>
	</tr>					
	<tr>
		<th>红包标题:</th>
		<td>${redPackOrder.redPackTitle}</td>
	</tr>					
	<tr>
		<th>发送者ID:</th>
		<td>${redPackOrder.sendUserId}</td>
	</tr>					
	<tr>
		<th>发送者用户名:</th>
		<td>${redPackOrder.sendUserName}</td>
	</tr>					
	<tr>
		<th>领取用户id:</th>
		<td>${redPackOrder.userId}</td>
	</tr>					
	<tr>
		<th>领取用户名:</th>
		<td>${redPackOrder.userName}</td>
	</tr>					
	<tr>
		<th>类型:</th>
		<td>${redPackOrder.type.message}</td>
	</tr>					
	<tr>
		<th>状态:</th>
		<td>${redPackOrder.status.message}</td>
	</tr>					
	<tr>
		<th>金额:</th>
		<td>${redPackOrder.amount/100.00}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${redPackOrder.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${redPackOrder.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${redPackOrder.comments}</td>
	</tr>					
</table>
</div>
