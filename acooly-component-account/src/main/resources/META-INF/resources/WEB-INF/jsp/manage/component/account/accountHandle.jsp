<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_account_editform" action="${pageContext.request.contextPath}/manage/component/account/account/handlePageJson.html" method="post">
      <jodd:form bean="account" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">

				<tr>
					<th>账户编码：</th>
					<td>${account.accountNo}</td>
					<input type="hidden" name="id" value="${account.id}"/>

				</tr>

				<tr>
					<th width="25%">账户类型：</th>
					<td>${account.accountType}</td>
				</tr>
			<tr>
				<th>账务方案：</th>
					<td><select name="tradeCode" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${allTradeCodes}" var="e">
					<option value="${e.key}">${e.value}</option>
					</c:forEach>
					</select></td>
			</tr>
			<tr>
				<th>账务金额：</th>
				<td>
					<input type="text" name="amount" size="20" placeholder="请输入金额..." style="height: 27px;line-height: 27px;" class="easyui-validatebox text" validType="money" data-options="validType:['length[1,19]'],required:true"/>
				</td>
			</tr>

			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" size="30" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>
        </table>
      </jodd:form>
    </form>
</div>
