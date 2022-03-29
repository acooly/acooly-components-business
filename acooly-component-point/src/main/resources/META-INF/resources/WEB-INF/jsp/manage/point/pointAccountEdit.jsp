<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div>
    <form id="manage_pointAccount_editform"
          action="${pageContext.request.contextPath}/manage/point/pointAccount/updateStatusJson.html"
          method="post">
        <jodd:form bean="pointAccount" scope="request">
            <input name="id" type="hidden" value="${pointAccount.id }"/>
            <input name="userNo" type="hidden" value="${pointAccount.userNo }"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th width="25%">用户号：</th>
                    <td>${pointAccount.userNo }</td>
                </tr>
                <tr>
                    <th width="25%">用户名：</th>
                    <td>${pointAccount.userName }</td>
                </tr>
                <tr>
                    <th width="25%">${pointModuleName}总额：</th>
                    <td>${pointAccount.balance }</td>
                </tr>
                <tr>
                    <th width="25%">冻结${pointModuleName}：</th>
                    <td>${pointAccount.freeze }</td>
                </tr>
                <tr>
                    <th width="25%">有效${pointModuleName}：</th>
                    <td>${pointAccount.available }</td>
                </tr>
                
                <tr>
                    <th>状态：</th>
                    <td>
	                    <select name="status" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" data-options="required:true">
	                        <c:forEach items="${allStatuss}" var="e">
	                            <option value="${e.key}">${e.value}</option>
	                        </c:forEach>
	                    </select>
	                </td>
                </tr>
                
            </table>
        </jodd:form>
    </form>
</div>
