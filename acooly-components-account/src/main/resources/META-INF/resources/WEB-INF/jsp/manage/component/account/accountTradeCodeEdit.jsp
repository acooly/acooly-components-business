<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_accountTradeCode_editform" action="${pageContext.request.contextPath}/manage/component/account/accountTradeCode/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="accountTradeCode" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">交易编码：</th>
				<td><input type="text" name="tradeCode" size="48" placeholder="请输入交易编码..." class="easyui-validatebox text" data-options="validType:['length[1,16]'],required:true"/></td>
			</tr>
			<tr>
				<th>交易名称：</th>
				<td><input type="text" name="tradeName" size="48" placeholder="请输入交易名称..." class="easyui-validatebox text" data-options="validType:['length[1,32]'],required:true"/></td>
			</tr>
			<tr>
				<th>资金方向：</th>
				<td><select name="direction" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${directions}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" size="48" placeholder="请输入comments..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>
        </table>
      </jodd:form>
    </form>
</div>
