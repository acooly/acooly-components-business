<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${countNumOrder.id}</td>
	</tr>					
	<tr>
		<th width="25%">订单号:</th>
		<td>${countNumOrder.orderNo}</td>
	</tr>					
	<tr>
		<th>游戏id:</th>
		<td>${countNumOrder.countId}</td>
	</tr>					
	<tr>
		<th>游戏名称:</th>
		<td>${countNumOrder.countTitle}</td>
	</tr>					
	<tr>
		<th>类型:</th>
		<td>${countNumOrder.countType.message()}</td>
	</tr>					
	<tr>
		<th>创建者id:</th>
		<td>${countNumOrder.createUserId}</td>
	</tr>					
	<tr>
		<th>创建者用户名:</th>
		<td>${countNumOrder.createUserName}</td>
	</tr>					
	<tr>
		<th>用户id:</th>
		<td>${countNumOrder.userId}</td>
	</tr>					
	<tr>
		<th>用户名称:</th>
		<td>${countNumOrder.userName}</td>
	</tr>					
	<tr>
		<th>有效值:</th>
		<td>${countNumOrder.num}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td>${countNumOrder.createTime?string('yyyy-MM-dd hh:mm:ss')}</td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td>${countNumOrder.updateTime?string('yyyy-MM-dd hh:mm:ss')}</td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${countNumOrder.comments}</td>
	</tr>					
</table>
</div>
