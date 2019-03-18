<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_redPack_editform" action="${pageContext.request.contextPath}/manage/component/redpack/redPack/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="redPack" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">名称：</th>
				<td><input type="text" name="title" size="48" placeholder="请输入名称..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>用户id：</th>
				<td><input type="text" name="sendUserId" size="48" placeholder="请输入用户id..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>用户名称：</th>
				<td><input type="text" name="sendUserName" size="48" placeholder="请输入用户名称..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
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
				<th>过期时间：</th>
				<td><input type="text" name="overdueTime" size="20" placeholder="请输入过期时间..." class="easyui-validatebox text" value="<fmt:formatDate value="${redPack.overdueTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></td>
			</tr>					
			<tr>
				<th>红包总金额：</th>
				<td><input type="text" name="totalAmount" size="48" placeholder="请输入红包总金额..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>已发送金额：</th>
				<td><input type="text" name="sendOutAmount" size="48" placeholder="请输入已发送金额..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>退款金额：</th>
				<td><input type="text" name="refundAmount" size="48" placeholder="请输入退款金额..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>总数：</th>
				<td><input type="text" name="totalNum" size="48" placeholder="请输入总数..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>已发送数量：</th>
				<td><input type="text" name="sendOutNum" size="48" placeholder="请输入已发送数量..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>红包备注：</th>
				<td><input type="text" name="remark" size="48" placeholder="请输入红包备注..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>业务id：</th>
				<td><input type="text" name="businessId" size="48" placeholder="请输入业务id..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>业务参数：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入业务参数..." style="width:300px;" name="businessData" class="easyui-validatebox" data-options="validType:['length[1,255]']"></textarea></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
