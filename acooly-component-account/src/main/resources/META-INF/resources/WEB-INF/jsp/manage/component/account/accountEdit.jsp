<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_account_editform" action="${pageContext.request.contextPath}/manage/component/account/account/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="account" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<c:if test="${action=='edit'}">
				<tr>
					<th>账户编码：</th>
					<td>${account.accountNo}</td>
				</tr>
			</c:if>
			<tr>
				<th width="25%">账户类型：</th>
				<%--<td><select name="accountType" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">--%>
					<%--<c:forEach items="${allAccountTypes}" var="e">--%>
						<%--<option value="${e.key}">${e.value}</option>--%>
					<%--</c:forEach>--%>
				<%--</select></td>--%>
				<td>
					<input type="text" name="accountType" size="48" placeholder="请输入账户类型" style="height: 27px;line-height: 27px;" class="easyui-validatebox text" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>用户ID：</th>
				<td>
					<c:if test="${action=='edit'}">${account.userId}</c:if>
					<c:if test="${action=='create'}">
					<input type="text" name="userId" size="48" placeholder="请输入用户ID..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>用户编码：</th>
				<td>
					<c:if test="${action=='edit'}">${account.userNo}</c:if>
					<c:if test="${action=='create'}">
						<input type="text" name="userNo" size="48" placeholder="请输出用户编码..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>用户名：</th>
				<td><input type="text" name="username" size="48" placeholder="请输入用户名..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>
			<c:if test="${action=='edit'}">
				<tr>
					<th>状态：</th>
					<td>${account.status.message}</td>
				</tr>
			</c:if>
			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>
        </table>
      </jodd:form>
    </form>
</div>
