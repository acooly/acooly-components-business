<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_memberBasic_editform" action="${pageContext.request.contextPath}/manage/component/member/memberBasic/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="memberBasic" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">会员编码：</th>
				<td><input type="text" name="userNo" size="48" placeholder="请输入会员编码..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>用户名：</th>
				<td><input type="text" name="username" size="48" placeholder="请输入用户名..." class="easyui-validatebox text" data-options="validType:['length[1,32]'],required:true"/></td>
			</tr>					
			<tr>
				<th>头像类型：</th>
				<td><select name="profilePhotoType" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allProfilePhotoTypes}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>头像：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入头像..." style="width:300px;" name="profilePhoto" class="easyui-validatebox" data-options="validType:['length[1,256]']"></textarea></td>
			</tr>					
			<tr>
				<th>昵称：</th>
				<td><input type="text" name="nickname" size="48" placeholder="请输入昵称..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>个性签名：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入个性签名..." style="width:300px;" name="dailyWords" class="easyui-validatebox" data-options="validType:['length[1,256]']"></textarea></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
