<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_memberPersonal_editform" action="${pageContext.request.contextPath}/manage/component/member/memberPersonal/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="memberPersonal" scope="request">
        <input name="id" type="hidden" />
		  <table class="tableForm">
			  <tr>
				  <th width="25%">用户：</th>
				  <td>${memberPersonal.userNo}/${memberPersonal.username}</td>
			  </tr>
		  </table>

        <table class="tableForm">
			<tr>
				<th width="25%">婚姻状况：</th>
				<td><select name="maritalStatus" editable="false" style="height:27px;min-width:200px" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${whetherStatus}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>子女情况：</th>
				<td><select name="childrenCount" editable="false" style="height:27px;min-width:200px" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allChildrenCounts}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>教育背景：</th>
				<td><select name="educationLevel" editable="false" style="height:27px;min-width:200px" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allEducationLevels}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>月收入：</th>
				<td><select name="incomeMonth" editable="false" style="height:27px;min-width:200px" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allIncomeMonths}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>社会保险：</th>
				<td><select name="socialInsurance" editable="false" style="height:27px;min-width:200px" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${whetherStatus}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>公积金：</th>
				<td><select name="houseFund" editable="false" style="height:27px;min-width:200px" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${whetherStatus}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>住房情况：</th>
				<td><select name="houseStatue" editable="false" style="height:27px;min-width:200px" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allHouseStatues}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>是否购车：</th>
				<td><select name="carStatus" editable="false" style="height:27px;min-width:200px" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${whetherStatus}" var="e">
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
