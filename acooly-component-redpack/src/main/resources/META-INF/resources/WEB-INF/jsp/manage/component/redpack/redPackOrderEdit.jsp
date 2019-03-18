<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_redPackOrder_editform" action="${pageContext.request.contextPath}/manage/component/redpack/redPackOrder/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="redPackOrder" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">订单号：</th>
				<td><input type="text" name="orderNo" size="48" placeholder="请输入订单号..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>红包id：</th>
				<td><input type="text" name="redPackId" size="48" placeholder="请输入红包id..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>红包名称：</th>
				<td><input type="text" name="redPackTitle" size="48" placeholder="请输入红包名称..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>红包发送者id：</th>
				<td><input type="text" name="sendUserId" size="48" placeholder="请输入红包发送者id..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>红包发送者名称：</th>
				<td><input type="text" name="sendUserName" size="48" placeholder="请输入红包发送者名称..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>用户id：</th>
				<td><input type="text" name="userId" size="48" placeholder="请输入用户id..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>用户名称：</th>
				<td><input type="text" name="userName" size="48" placeholder="请输入用户名称..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>类型：</th>
				<td><select name="type" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allTypes}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>状态：</th>
				<td><select name="status" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allStatuss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>金额：</th>
				<td><input type="text" name="amount" size="48" placeholder="请输入金额..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
