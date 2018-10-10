<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_memberPersonal_editform" action="${pageContext.request.contextPath}/manage/component/member/memberPersonal/verify.html" enctype="multipart/form-data" method="post">
      <jodd:form bean="memberPersonal" scope="request">
        <input name="id" type="hidden" />
		  <table class="tableForm">
			  <tr>
				  <th width="25%">用户：</th>
				  <td>${memberPersonal.username}/${memberPersonal.userNo}</td>
			  </tr>
		  </table>

		  <table class="tableForm">
			  <tr>
				  <th width="25%">实名状态：</th>
				  <td>${memberPersonal.certStatus.message}</td>
			  </tr>
			  <tr>
				  <th width="25%">姓名：</th>
				  <td><input type="text" name="realName" size="48" placeholder="请输入姓名..." class="easyui-validatebox text" data-options="validType:['length[1,16]'],required:true"/></td>
			  </tr>
			  <tr>
				  <th>证件类型：</th>
				  <td><select name="certType" editable="false" style="height:27px;min-width:200px" panelHeight="auto" class="easyui-combobox" >
					  <c:forEach items="${allCertTypes}" var="e">
						  <option value="${e.key}">${e.value}</option>
					  </c:forEach>
				  </select> <span class="acooly-comment">只有身份证才自动进行实名认证。</span></td>
			  </tr>
			  <c:if test="${memberPersonal.certStatus.code == 'yes'}">
				  <tr>
					  <th>生日：</th>
					  <td><fmt:formatDate value="${memberPersonal.birthday}" pattern="yyyy-MM-dd"/> (${memberPersonal.age}）</td>
				  </tr>
				  <tr>
					  <th>性别：</th>
					  <td>${memberPersonal.gender.message}</td>
				  </tr>
			  </c:if>
			  <tr>
				  <th width="25%">证件号码：</th>
				  <td><input type="text" name="certNo" size="48" placeholder="请输入证件号码..." class="easyui-validatebox text" data-options="validType:['length[1,32]'],required:true"/></td>
			  </tr>
			  <tr>
				  <th>有效期：</th>
				  <td><input type="text" name="certValidityDate" size="48" placeholder="请输入证件有效期（yyyy-MM-dd）..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"
							value="<fmt:formatDate value="${memberPersonal.certValidityDate}" pattern="yyyy-MM-dd"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/></td>
			  </tr>

			  <tr>
				  <th width="25%">正面照片：</th>
				  <td>
					  <c:if test="${memberPersonal.certFrontPath != null && memberPersonal.certFrontPath != ''}"><img style="max-width: 300px;" src="${serverRoot}${memberPersonal.certFrontPath}">
					  </c:if>
					  <input type="file" name="certFront" size="48" placeholder="请选择证件正面照片..." class="easyui-filebox text"/>
				  </td>
			  </tr>
			  <tr>
				  <th width="25%">反面照片：</th>
				  <td><c:if test="${memberPersonal.certBackPath != null && memberPersonal.certBackPath != ''}"><img style="max-width: 300px;" src="${serverRoot}${memberPersonal.certBackPath}"></c:if>
					  <input type="file" name="certBack" size="48" placeholder="请选择证件正面照片..." class="easyui-validatebox text"/></td>
			  </tr>
			  <tr>
				  <th width="25%">手持照片：</th>
				  <td><c:if test="${memberPersonal.certHoldPath != null && memberPersonal.certHoldPath != ''}"><img style="max-width: 300px;" src="${serverRoot}${memberPersonal.certHoldPath}"></c:if>
					  <input type="file" name="certHold" size="48" placeholder="请选择证件正面照片..." class="easyui-validatebox text"/></td>
			  </tr>

		  </table>

      </jodd:form>
    </form>
</div>
