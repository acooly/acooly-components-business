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
                用户ID: <input type="text" class="text" size="5" name="search_EQ_userId"/>
                账户编码: <input type="text" class="text" size="15" name="search_EQ_userNo"/>
                用户名: <input type="text" class="text" size="15" name="search_LIKE_username"/>
				资金流向: <select style="width:80px;height:27px;" name="search_EQ_direction" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allDirections}"><option value="${e.key}" ${param.search_EQ_direction == e.key?'selected':''}>${e.value}</option></c:forEach></select>
                交易码: <input type="text" class="text" size="15" name="search_LIKE_tradeCode"/>
                交易时间: <input size="10" class="text" id="search_GTE_tradeTime" name="search_GTE_tradeTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
                至<input size="10" class="text" id="search_LTE_tradeTime" name="search_LTE_tradeTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
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
			<th field="id">ID</th>
            <th field="accountId">账户ID</th>
			<th field="accountNo">账号</th>
            <th field="userNo">用户编码</th>
            <th field="userId">用户ID</th>
			<th field="username">用户名</th>
			<th field="amount" sum="true" formatter="centMoneyFormatter">交易金额(元)</th>
			<th field="balancePost" sum="true" formatter="centMoneyFormatter">变动后余额(元)</th>
            <th field="freezeAmount" sum="true" formatter="centMoneyFormatter">冻结金额(元)</th>
            <th field="freezePost" sum="true" formatter="centMoneyFormatter">冻结后总额(元)</th>
			<th field="direction" formatter="mappingFormatter">资金流向</th>
            <th field="tradeCodeField" data-options="formatter:function(v,r,i){return r.tradeCode;}">交易码</th>
			<th field="tradeCode" formatter="mappingFormatter">交易名称</th>
			<th field="status" formatter="mappingFormatter">状态</th>
		    <th field="createTime" formatter="dateTimeFormatter">交易时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_accountBill_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_accountBill_action" style="display: none;">
      <a onclick="$.acooly.framework.show('/manage/component/account/accountBill/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_accountBill_toolbar">
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_accountBill_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_accountBill_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/component/account/accountBill/exportXls.html','manage_accountBill_searchform','账户进出账')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/component/account/accountBill/exportCsv.html','manage_accountBill_searchform','账户进出账')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
    </div>
  </div>

</div>
