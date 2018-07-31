<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${accountTradeCode.id}</td>
	</tr>					
	<tr>
		<th width="25%">交易编码:</th>
		<td>${accountTradeCode.tradeCode}</td>
	</tr>					
	<tr>
		<th>交易名称:</th>
		<td>${accountTradeCode.tradeName}</td>
	</tr>					
	<tr>
		<th>方向:</th>
		<td>${accountTradeCode.direction}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${accountTradeCode.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>update_time:</th>
		<td><fmt:formatDate value="${accountTradeCode.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>comments:</th>
		<td>${accountTradeCode.comments}</td>
	</tr>					
</table>
</div>
