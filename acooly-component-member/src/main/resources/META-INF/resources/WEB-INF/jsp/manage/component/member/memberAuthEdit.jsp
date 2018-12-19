<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_memberAuth_editform" action="${pageContext.request.contextPath}/manage/component/member/memberAuth/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="memberAuth" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">用户ID：</th>
				<td><input type="text" name="userId" size="48" placeholder="请输入用户ID..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>用户编码：</th>
				<td><input type="text" name="userNo" size="48" placeholder="请输入用户编码..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>用户名：</th>
				<td><input type="text" name="username" size="48" placeholder="请输入用户名..." class="easyui-validatebox text" data-options="validType:['length[1,32]'],required:true"/></td>
			</tr>					
			<tr>
				<th>认证类型：</th>
				<td><select name="authType" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${allAuthTypes}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>认证角色：</th>
				<td><select name="authRole" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${allAuthRoles}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>登录ID：</th>
				<td><input type="text" name="loginId" size="48" placeholder="请输入登录ID..." class="easyui-validatebox text" data-options="validType:['length[1,32]'],required:true"/></td>
			</tr>					
			<tr>
				<th>登录秘钥/密码：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入登录秘钥/密码..." style="width:300px;" name="loginKey" class="easyui-validatebox" data-options="validType:['length[1,256]'],required:true"></textarea></td>
			</tr>					
			<tr>
				<th>认证加盐：</th>
				<td><input type="text" name="loginSalt" size="48" placeholder="请输入认证加盐..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>有效期
：</th>
				<td><input type="text" name="expireTime" size="20" placeholder="请输入有效期
..." class="easyui-validatebox text" value="<fmt:formatDate value="${memberAuth.expireTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></td>
			</tr>					
			<tr>
				<th>解锁时间：</th>
				<td><input type="text" name="unlockTime" size="20" placeholder="请输入解锁时间..." class="easyui-validatebox text" value="<fmt:formatDate value="${memberAuth.unlockTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></td>
			</tr>					
			<tr>
				<th>登录失败次数：</th>
				<td><input type="text" name="loginFailTimes" size="48" placeholder="请输入登录失败次数..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,10]']"/></td>
			</tr>					
			<tr>
				<th>最后登录时间：</th>
				<td><input type="text" name="lastLoginTime" size="20" placeholder="请输入最后登录时间..." class="easyui-validatebox text" value="<fmt:formatDate value="${memberAuth.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></td>
			</tr>					
			<tr>
				<th>是否登录：</th>
				<td><select name="loginStatus" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allLoginStatuss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>状态：</th>
				<td><select name="authStatus" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<c:forEach items="${allAuthStatuss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入备注..." style="width:300px;" name="comments" class="easyui-validatebox" data-options="validType:['length[1,255]']"></textarea></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
