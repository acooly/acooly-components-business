<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_account_editform" action="${pageContext.request.contextPath}/manage/component/account/account/saveJson.html" method="post">
      <jodd:form bean="account" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
        	<tr>
				<th>用户ID：</th>
				<td>${userId }
					<input type="hidden" name="userId" value="${userId }"/>
				</td>
			</tr>
			<tr>
				<th>用户编码：</th>
				<td>${userNo }
					<input type="hidden" name="userNo" value="${userNo }">
				</td>
			</tr>
			<tr>
				<th>用户名：</th>
				<td>${username }	
					<input type="hidden" name="username" value="${username }">
				</td>
			</tr>
			<tr>
				<th width="25%">账户类型：</th>
				<td><select name="accountType" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${allAccountTypes}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>
        </table>
      </jodd:form>
    </form>
</div>
