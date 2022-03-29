<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>ID:</th>
		<td>${pointTypeCount.id}</td>
	</tr>					
	<tr>
		<th width="25%">用户名:</th>
		<td>${pointTypeCount.userName}</td>
	</tr>					
	<tr>
		<th>统计次数:</th>
		<td>${pointTypeCount.num}</td>
	</tr>					
	<tr>
		<th>业务类型:</th>
		<td>${pointTypeCount.busiType}</td>
	</tr>					
	<tr>
		<th>业务类型描述:</th>
		<td>${pointTypeCount.busiTypeText}</td>
	</tr>					
	<tr>
		<th>总${pointModuleName}:</th>
		<td>${pointTypeCount.totalPoint}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${pointTypeCount.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>修改时间:</th>
		<td><fmt:formatDate value="${pointTypeCount.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${pointTypeCount.comments}</td>
	</tr>					
</table>
</div>
