<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${redPack.id}</td>
	</tr>					
	<tr>
		<th width="25%">标题:</th>
		<td>${redPack.title}</td>
	</tr>					
	<tr>
		<th>用户id:</th>
		<td>${redPack.sendUserId}</td>
	</tr>					
	<tr>
		<th>用户名:</th>
		<td>${redPack.sendUserName}</td>
	</tr>					
	<tr>
		<th>状态:</th>
		<td>${redPack.status.message}</td>
	</tr>					
	<tr>
		<th>过期时间:</th>
		<td><fmt:formatDate value="${redPack.overdueTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>红包总金额:</th>
		<td>${redPack.totalAmount/100}</td>
	</tr>					
	<tr>
		<th>已发送金额:</th>
		<td>${redPack.sendOutAmount/100}</td>
	</tr>					
	<tr>
		<th>退款金额:</th>
		<td>${redPack.refundAmount/100}</td>
	</tr>					
	<tr>
		<th>总数:</th>
		<td>${redPack.totalNum}</td>
	</tr>					
	<tr>
		<th>已发送数量:</th>
		<td>${redPack.sendOutNum}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${redPack.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${redPack.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>红包备注:</th>
		<td>${redPack.remark}</td>
	</tr>					
	<tr>
		<th>业务id:</th>
		<td>${redPack.businessId}</td>
	</tr>					
	<tr>
		<th>业务参数:</th>
		<td>${redPack.businessData}</td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${redPack.comments}</td>
	</tr>					
</table>
</div>
