<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_member_editform" action="${pageContext.request.contextPath}/manage/component/member/member/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="member" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">父会员ID：</th>
				<td><input type="text" name="parentid" size="48" placeholder="请输入父会员ID..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>父会员编码：</th>
				<td><input type="text" name="parentUserNo" size="48" placeholder="请输入父会员编码..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>会员编码：</th>
				<td><input type="text" name="userNo" size="48" placeholder="请输入会员编码..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>用户名：</th>
				<td><input type="text" name="username" size="48" placeholder="请输入用户名..." class="easyui-validatebox text" data-options="validType:['length[1,32]'],required:true"/></td>
			</tr>					
			<tr>
				<th>密码：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入密码..." style="width:300px;" name="password" class="easyui-validatebox" data-options="validType:['length[1,256]'],required:true"></textarea></td>
			</tr>					
			<tr>
				<th>密码盐：</th>
				<td><input type="text" name="salt" size="48" placeholder="请输入密码盐..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>用户类型：</th>
				<td><select name="userType" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${allUserTypes}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>手机号码：</th>
				<td><input type="text" name="mobileNo" size="48" placeholder="请输入手机号码..." class="easyui-validatebox text" data-options="validType:['length[1,16]']"/></td>
			</tr>					
			<tr>
				<th>邮件：</th>
				<td><input type="text" name="email" size="48" placeholder="请输入邮件..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
			<tr>
				<th>姓名：</th>
				<td><input type="text" name="realName" size="48" placeholder="请输入姓名..." class="easyui-validatebox text" data-options="validType:['length[1,16]']"/></td>
			</tr>					
			<tr>
				<th>身份证号码：</th>
				<td><input type="text" name="idCardNo" size="48" placeholder="请输入身份证号码..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>用户等级：</th>
				<td><select name="grade" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allGrades}" var="e">
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
				<th>备注：</th>
				<td><input type="text" name="comments" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
