<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_memberContact_editform" action="${pageContext.request.contextPath}/manage/component/member/memberContact/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="memberContact" scope="request">
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
				<th>手机号码：</th>
				<td><input type="text" name="mobileNo" size="48" placeholder="请输入手机号码..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>电话号码：</th>
				<td><input type="text" name="phoneNo" size="48" placeholder="请输入电话号码..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>居住地 省：</th>
				<td><input type="text" name="province" size="48" placeholder="请输入居住地 省..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>居住地 市：</th>
				<td><input type="text" name="city" size="48" placeholder="请输入居住地 市..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>居住地 县/区：</th>
				<td><input type="text" name="district" size="48" placeholder="请输入居住地 县/区..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>详细地址：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入详细地址..." style="width:300px;" name="address" class="easyui-validatebox" data-options="validType:['length[1,256]']"></textarea></td>
			</tr>					
			<tr>
				<th>邮政编码：</th>
				<td><input type="text" name="zip" size="48" placeholder="请输入邮政编码..." class="easyui-validatebox text" data-options="validType:['length[1,6]']"/></td>
			</tr>					
			<tr>
				<th>QQ：</th>
				<td><input type="text" name="qq" size="48" placeholder="请输入QQ..." class="easyui-validatebox text" data-options="validType:['length[1,16]']"/></td>
			</tr>					
			<tr>
				<th>MSN：</th>
				<td><input type="text" name="wechat" size="48" placeholder="请输入MSN..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>旺旺：</th>
				<td><input type="text" name="wangwang" size="48" placeholder="请输入旺旺..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><input type="text" name="google" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>facebeek：</th>
				<td><input type="text" name="facebeek" size="48" placeholder="请输入facebeek..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>email：</th>
				<td><input type="text" name="email" size="48" placeholder="请输入email..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
