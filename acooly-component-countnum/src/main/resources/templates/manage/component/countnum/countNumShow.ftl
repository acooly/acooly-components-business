<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${countNum.id}</td>
	</tr>					
	<tr>
		<th width="25%">名称:</th>
		<td>${countNum.title}</td>
	</tr>					
	<tr>
		<th>创建者id:</th>
		<td>${countNum.createUserId}</td>
	</tr>					
	<tr>
		<th>创建者用户名:</th>
		<td>${countNum.createUserName}</td>
	</tr>					
	<tr>
		<th>类型:</th>
		<td>${countNum.type.message()}</td>
	</tr>					
	<tr>
		<th>状态:</th>
		<td>${countNum.status.message()}</td>
	</tr>					
	<tr>
		<th>过期时间:</th>
		<td>${countNum.overdueTime?string('yyyy-MM-dd hh:mm:ss')}</td>
	</tr>	
	<tr>
		<th>最大参与人数:</th>
		<td>${countNum.maxNum}</td>
	</tr>				
	<tr>
		<th>可参与次数:</th>
		<td>${countNum.limitNum}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td>${countNum.createTime?string('yyyy-MM-dd hh:mm:ss')}</td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td>${countNum.updateTime?string('yyyy-MM-dd hh:mm:ss')}</td>
	</tr>					
	<tr>
		<th>业务id:</th>
		<td>${countNum.businessId}</td>
	</tr>					
	<tr>
		<th>业务参数:</th>
		<td>${countNum.businessData}</td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${countNum.comments}</td>
	</tr>					
</table>
</div>
