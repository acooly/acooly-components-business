<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_account_searchform','manage_account_datagrid');
});


    function manage_account_datagrid_action_formatter(datagrid,value,row){
        var html = "<a onclick=\"$.acooly.framework.edit({url:'/manage/component/account/account/edit.html',id:'"+row.id+"',entity:'account',width:500,height:400});\" href=\"#\" title=\"编辑\"><i class=\"fa fa-pencil fa-lg fa-fw fa-col\"></i></a>";
        if(row.status == 'disable'){
            return html;
        }

        if(row.status == 'enable'){
            html += "<a onclick=\"$.acooly.framework.confirmSubmit('/manage/component/account/account/statusChange.html?status=pause','"+row.id+"','manage_account_datagrid','状态控制','请确定要暂停账户？');\" href=\"#\" title=\"暂停\"><i class=\"fa fa-pause-circle fa-lg fa-fw fa-col\"></i></a>"
        }else{
            html += "<a onclick=\"$.acooly.framework.confirmSubmit('/manage/component/account/account/statusChange.html?status=enable','"+row.id+"','manage_account_datagrid','状态控制','请确定要启用账户？');\" href=\"#\" title=\"启用\"><i class=\"fa fa-play-circle fa-lg fa-fw fa-col\"></i></a>"
        }
        html += "<a onclick=\"$.acooly.framework.confirmSubmit('/manage/component/account/account/statusChange.html?status=disable','"+row.id+"','manage_account_datagrid','状态控制','请确定需要账户禁用（一旦禁用无法再启用）？');\" href=\"#\" title=\"禁用\"><i class=\"fa fa-stop-circle fa-lg fa-fw fa-col\"></i></a>"
        return html;
    }


	/**
	 * 账户类型转换
	 */
    function manage_account_datagrid_type_formatter(value){
        var showValues=formatRefrence('manage_account_datagrid','allAccountTypes',value);
		if((showValues==undefined)||(showValues=='')){
			return value;
		}else{
			return showValues;
		}
    }
    

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_account_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
                    账户ID: <input type="text" class="text" size="15" name="search_EQ_id"/>
                    账户编码: <input type="text" class="text" size="15" name="search_EQ_accountNo"/>
					用户ID: <input type="text" class="text" size="15" name="search_EQ_userId"/>
                    用户名: <input type="text" class="text" size="15" name="search_LIKE_username"/>
					余额: <input type="text" class="text" size="8" name="search_GTE_balance"/> ~ <input type="text" class="text" size="8" name="search_LTE_balance"/>
                    类型: <select style="width:80px;height:27px;" name="search_EQ_accountType" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allAccountTypes}"><option value="${e.key}" ${param.search_EQ_accountType == e.key?'selected':''}>${e.value}</option></c:forEach></select>
				    状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allStatuss}"><option value="${e.key}" ${param.search_EQ_status == e.key?'selected':''}>${e.value}</option></c:forEach></select>
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_account_searchform','manage_account_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_account_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/account/account/listJson.html" toolbar="#manage_account_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" >ID</th>
            <th field="accountNo">账号</th>
			<th field="userId">用户ID</th>
            <th field="userNo">用户编码</th>
            <th field="username">用户名</th>
			<th field="balance" sum="true" formatter="centMoneyFormatter">余额</th>
			<th field="freeze" sum="true" formatter="centMoneyFormatter">冻结金额</th>
            <th field="available" sum="true" formatter="centMoneyFormatter">可用金额</th>
            <th field="accountType"  data-options="formatter:function(value, row, index){return manage_account_datagrid_type_formatter(value)}">账户类型</th>
			<th field="status" formatter="mappingFormatter">状态</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return manage_account_datagrid_action_formatter('manage_account_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_account_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/component/account/account/edit.html',id:'{0}',entity:'account',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.confirmSubmit('/manage/component/account/account/status.html','{0}','manage_account_datagrid','状态控制','请确定要变更账户状态？');" href="#" title="停用/启用"><i class="fa fa-pause-circle fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/component/account/account/deleteJson.html','{0}','manage_account_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_account_toolbar">
    <c:if test="${isCreateAccount}">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/account/account/create.html',entity:'account',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>开户</a>
    </c:if> 
      <!--
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/component/account/account/deleteJson.html','manage_account_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_account_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_account_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/component/account/account/exportXls.html','manage_account_searchform','账户信息')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/component/account/account/exportCsv.html','manage_account_searchform','账户信息')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/account/account/importView.html',uploader:'manage_account_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
      -->
    </div>
  </div>

</div>
