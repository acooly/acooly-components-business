<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_memberEnterprise_editform" action="${pageContext.request.contextPath}/manage/component/member/memberEnterprise/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="memberEnterprise" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">用户编码：</th>
				<td><input type="text" name="userNo" size="48" placeholder="请输入用户编码..." class="easyui-validatebox text" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>用户名：</th>
				<td><input type="text" name="username" size="48" placeholder="请输入用户名..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>企业类型：</th>
				<td><select name="entType" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allEntTypes}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>企业名称：</th>
				<td><input type="text" name="entName" size="48" placeholder="请输入企业名称..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>社会统一信用代码：</th>
				<td><input type="text" name="licenceNo" size="48" placeholder="请输入社会统一信用代码..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>营业执照图片地址：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入营业执照图片地址..." style="width:300px;" name="licencePath" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
			<tr>
				<th>营业执照地址：</th>
				<td><input type="text" name="licenceAddress" size="48" placeholder="请输入营业执照地址..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
			<tr>
				<th>营业年限：</th>
				<td><input type="text" name="businessLife" size="48" placeholder="请输入营业年限..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,10]']"/></td>
			</tr>					
			<tr>
				<th>法人姓名：</th>
				<td><input type="text" name="legalName" size="48" placeholder="请输入法人姓名..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>法人证件类型: 默认身份证：</th>
				<td><input type="text" name="legalCertType" size="48" placeholder="请输入法人证件类型: 默认身份证..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>法人证件号码：</th>
				<td><input type="text" name="legalCertNo" size="48" placeholder="请输入法人证件号码..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>法人证件到期时间：</th>
				<td><input type="text" name="legalCertValidTime" size="48" placeholder="请输入法人证件到期时间..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>法人证件正面图片：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入法人证件正面图片..." style="width:300px;" name="legalCertFrontPath" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
			<tr>
				<th>法人证件背面图片：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入法人证件背面图片..." style="width:300px;" name="legalCertBackPath" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
			<tr>
				<th>经营范围：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入经营范围..." style="width:300px;" name="businessScope" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
			<tr>
				<th>实际控股人或企业类型：</th>
				<td><select name="holdingEnum" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allHoldingEnums}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>股东或实际控制人真实姓名：</th>
				<td><input type="text" name="holdingName" size="48" placeholder="请输入股东或实际控制人真实姓名..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>股东或实际控制人证件类型：</th>
				<td><select name="holdingCertType" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allHoldingCertTypes}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>股东或实际控制人证件号：</th>
				<td><input type="text" name="holdingCertNo" size="48" placeholder="请输入股东或实际控制人证件号..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>股东或实际控制人证件到期时间：</th>
				<td><input type="text" name="holdingCertValidTime" size="48" placeholder="请输入股东或实际控制人证件到期时间..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>股东或实际控制人证件正面图片：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入股东或实际控制人证件正面图片..." style="width:300px;" name="holdingCertFrontPath" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
			<tr>
				<th>股东或实际控制人证件背面图片：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入股东或实际控制人证件背面图片..." style="width:300px;" name="holdingCertBackPath" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
			<tr>
				<th>开户许可证号码：</th>
				<td><input type="text" name="accountLicenseNo" size="48" placeholder="请输入开户许可证号码..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>开户许可证图片：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入开户许可证图片..." style="width:300px;" name="accountLicensePath" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
			<tr>
				<th>税务登记证号码：</th>
				<td><input type="text" name="taxAuthorityNo" size="48" placeholder="请输入税务登记证号码..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>税务登记证图片：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入税务登记证图片..." style="width:300px;" name="taxAuthorityPath" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入备注..." style="width:300px;" name="comments" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
