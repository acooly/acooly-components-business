<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_accountBill_searchform','manage_accountBill_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_accountBill_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					账户编码: <input type="text" class="text" size="15" name="search_EQ_accountNo"/>
					用户名: <input type="text" class="text" size="15" name="search_LIKE_username"/>
					交易金额: <input type="text" class="text" size="15" name="search_EQ_amount"/>
					变动后余额: <input type="text" class="text" size="15" name="search_EQ_balancePost"/>
				资金流向: <select style="width:80px;height:27px;" name="search_EQ_direction" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allDirections}"><option value="${e.key}" ${param.search_EQ_direction == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					交易码: <input type="text" class="text" size="15" name="search_LIKE_tradeCode"/>
					交易时间: <input size="15" class="text" id="search_GTE_tradeTime" name="search_GTE_tradeTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_tradeTime" name="search_LTE_tradeTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					相关业务ID: <input type="text" class="text" size="15" name="search_EQ_busiId"/>
				状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allStatuss}"><option value="${e.key}" ${param.search_EQ_status == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					更新时间: <input size="15" class="text" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_accountBill_searchform','manage_accountBill_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_accountBill_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/account/accountBill/listJson.html" toolbar="#manage_accountBill_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">ID</th>
			<th field="accountId" sum="true">账户ID</th>
			<th field="userName">用户名</th>
			<th field="amount" sum="true">交易金额</th>
			<th field="balancePost" sum="true">变动后余额</th>
			<th field="direction" formatter="mappingFormatter">资金流向</th>
			<th field="tradeCode">交易码</th>
		    <th field="tradeTime" formatter="dateTimeFormatter">交易时间</th>
			<th field="busiId" sum="true">相关业务ID</th>
			<th field="busiData">相关业务数据</th>
			<th field="status" formatter="mappingFormatter">状态</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_accountBill_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_accountBill_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/component/account/accountBill/edit.html',id:'{0}',entity:'accountBill',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/component/account/accountBill/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/component/account/accountBill/deleteJson.html','{0}','manage_accountBill_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_accountBill_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/account/accountBill/create.html',entity:'accountBill',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/component/account/accountBill/deleteJson.html','manage_accountBill_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_accountBill_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_accountBill_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/component/account/accountBill/exportXls.html','manage_accountBill_searchform','账户进出账')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/component/account/accountBill/exportCsv.html','manage_accountBill_searchform','账户进出账')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/account/accountBill/importView.html',uploader:'manage_accountBill_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>

</div>
