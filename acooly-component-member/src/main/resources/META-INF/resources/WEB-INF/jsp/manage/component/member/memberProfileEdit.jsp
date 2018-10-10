<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_memberProfile_editform" action="${pageContext.request.contextPath}/manage/component/member/memberProfile/updateJson.html" method="post">
      <jodd:form bean="memberProfile" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">用户：</th>
				<td>${memberProfile.userNo}/${memberProfile.username}</td>
			</tr>
			<tr>
				<th>昵称：</th>
				<td><input type="text" name="nickname" size="48" style="width:300px;" placeholder="请输入昵称..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>个性签名：</th>
				<td><textarea rows="2" cols="40" placeholder="请输入个性签名..." style="width:300px;" name="dailyWords" class="easyui-validatebox" data-options="validType:['length[1,256]']"></textarea></td>
			</tr>					
			<tr>
				<th>头像类型：</th>
				<td><select name="profilePhotoType" editable="false" style="height:27px;min-width: 100px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allProfilePhotoTypes}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>头像：</th>
				<td><input type="text" placeholder="请输入头像地址..." style="width:300px;" name="profilePhoto" class="easyui-validatebox text" data-options="validType:['length[1,256]']"/></td>
			</tr>
            <tr>
                <th>短信静默：</th>
                <td><select name="smsSendStatus" editable="false" style="height:27px;min-width: 100px;" panelHeight="auto" class="easyui-combobox" >
                    <c:forEach items="${allWhtherStatuss}" var="e">
                        <option value="${e.key}">${e.value}</option>
                    </c:forEach>
                </select><span class="acooly-comment" style="margin-left: 10px;">设置是否对该用户发送通知/营销短信</span></td>
            </tr>
			<tr>
				<th>实名认证：</th>
				<td>${memberProfile.realNameStatus.message} <c:if test="${memberProfile.realNameStatus.code == 'no'}">请通过设置设置实名认证功能设置</c:if></td>
			</tr>					
			<tr>
				<th>手机认证：</th>
                <td>${memberProfile.mobileNoStatus.message} <c:if test="${memberProfile.mobileNoStatus.code == 'no'}"><span class="acooly-comment">请通过设置绑定手机功能设置</span></c:if></td>
			</tr>					
			<tr>
				<th>邮箱认证：</th>
				<td>${memberProfile.emailStatus.message}</td>
			</tr>
			<tr>
				<th>安全问题：</th>
				<td>${memberProfile.secretQaStatus.message}</td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" style="width:300px;" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
