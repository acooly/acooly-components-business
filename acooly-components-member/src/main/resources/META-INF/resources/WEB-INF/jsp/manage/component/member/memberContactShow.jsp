<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>主键:</th>
		<td>${memberContact.id}</td>
	</tr>					
	<tr>
		<th width="25%">会员编码:</th>
		<td>${memberContact.userNo}</td>
	</tr>					
	<tr>
		<th>用户名:</th>
		<td>${memberContact.username}</td>
	</tr>					
	<tr>
		<th>手机号码:</th>
		<td>${memberContact.mobileNo}</td>
	</tr>					
	<tr>
		<th>电话号码:</th>
		<td>${memberContact.phoneNo}</td>
	</tr>					
	<tr>
		<th>居住地 省:</th>
		<td>${memberContact.province}</td>
	</tr>					
	<tr>
		<th>居住地 市:</th>
		<td>${memberContact.city}</td>
	</tr>					
	<tr>
		<th>居住地 县/区:</th>
		<td>${memberContact.district}</td>
	</tr>					
	<tr>
		<th>详细地址:</th>
		<td>${memberContact.address}</td>
	</tr>					
	<tr>
		<th>邮政编码:</th>
		<td>${memberContact.zip}</td>
	</tr>					
	<tr>
		<th>QQ:</th>
		<td>${memberContact.qq}</td>
	</tr>					
	<tr>
		<th>MSN:</th>
		<td>${memberContact.wechat}</td>
	</tr>					
	<tr>
		<th>旺旺:</th>
		<td>${memberContact.wangwang}</td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${memberContact.google}</td>
	</tr>					
	<tr>
		<th>facebeek:</th>
		<td>${memberContact.facebeek}</td>
	</tr>					
	<tr>
		<th>email:</th>
		<td>${memberContact.email}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${memberContact.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${memberContact.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>备注:</th>
		<td>${memberContact.comments}</td>
	</tr>					
</table>
</div>
