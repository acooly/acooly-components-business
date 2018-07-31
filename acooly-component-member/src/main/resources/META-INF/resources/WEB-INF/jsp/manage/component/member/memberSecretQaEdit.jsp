<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_memberSecretQa_editform" action="${pageContext.request.contextPath}/manage/component/member/memberSecretQa/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="memberSecretQa" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">用户ID：</th>
				<td><input type="text" name="userid" size="48" placeholder="请输入用户ID..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>用户编码：</th>
				<td><input type="text" name="userNo" size="48" placeholder="请输入用户编码..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>问题：</th>
				<td><input type="text" name="question" size="48" placeholder="请输入问题..." class="easyui-validatebox text" data-options="validType:['length[1,128]'],required:true"/></td>
			</tr>					
			<tr>
				<th>答案：</th>
				<td><input type="text" name="answer" size="48" placeholder="请输入答案..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
