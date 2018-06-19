<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_accountBill_editform" action="${pageContext.request.contextPath}/manage/component/account/accountBill/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="accountBill" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">账户ID：</th>
				<td><input type="text" name="accountId" size="48" placeholder="请输入账户ID..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>用户名：</th>
				<td><input type="text" name="userName" size="48" placeholder="请输入用户名..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>交易金额：</th>
				<td><input type="text" name="amount" size="48" placeholder="请输入交易金额..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>变动后余额：</th>
				<td><input type="text" name="balancePost" size="48" placeholder="请输入变动后余额..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>资金流向：</th>
				<td><select name="direction" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${allDirections}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>交易码：</th>
				<td><input type="text" name="tradeCode" size="48" placeholder="请输入交易码..." class="easyui-validatebox text" data-options="validType:['length[1,16]'],required:true"/></td>
			</tr>					
			<tr>
				<th>交易时间：</th>
				<td><input type="text" name="tradeTime" size="20" placeholder="请输入交易时间..." class="easyui-validatebox text" value="<fmt:formatDate value="${accountBill.tradeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="required:true" /></td>
			</tr>					
			<tr>
				<th>相关业务ID：</th>
				<td><input type="text" name="busiId" size="48" placeholder="请输入相关业务ID..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>相关业务数据：</th>
				<td><input type="text" name="busiData" size="48" placeholder="请输入相关业务数据..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
			<tr>
				<th>状态：</th>
				<td><select name="status" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${allStatuss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
