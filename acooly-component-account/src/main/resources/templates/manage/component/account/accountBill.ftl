<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
      <form id="manage_accountBill_searchform" class="form-inline ac-form-search" onsubmit="return false">
          <div class="form-group">
              <label class="col-form-label">订单号：</label>
              <input type="text" class="form-control form-control-sm" name="search_LIKE_bizOrderNo"/>
          </div>
          <div class="form-group">
              <label class="col-form-label">账户编码：</label>
              <input type="text" class="form-control form-control-sm" name="search_EQ_accountNo"/>
          </div>
          <div class="form-group">
              <label class="col-form-label">用户编码：</label>
              <input type="text" class="form-control form-control-sm" name="search_EQ_userNo"/>
          </div>
          <div class="form-group">
              <label class="col-form-label">用户名：</label>
              <input type="text" class="form-control form-control-sm" name="search_LIKE_username"/>
          </div>
          <div class="form-group">
              <label class="col-form-label">交易码：</label>
              <input type="text" class="form-control form-control-sm" name="search_LIKE_tradeCode"/>
          </div>
          <div class="form-group">
              <label class="col-form-label">资金流向：</label>
              <select name="search_EQ_direction" class="form-control input-sm select2bs4">
                  <option value="">所有</option><#list allDirections as k,v><option value="${k}">${v}</option></#list>
              </select>
          </div>
          <div class="form-group">
              <label class="col-form-label">时间：</label>
              <input size="10" type="text" class="form-control form-control-sm" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
               至 <input size="10" type="text" class="form-control form-control-sm" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          </div>
          <div class="form-group">
              <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_accountBill_searchform','manage_accountBill_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</button>
          </div>
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
            <th field="createTime" formatter="dateTimeFormatter">记账时间</th>
            <th field="accountId" data-options="formatter:function(v,r,i){return r.accountId + '/' + r.accountNo}">账户(ID/NO)</th>
            <th field="userNo" data-options="formatter:function(v,r,i){return r.userId + '/' + r.userNo}">用户(ID/NO)</th>
			<th field="username">用户名</th>
            <th field="tradeCodeField" data-options="formatter:function(v,r,i){return r.tradeCode;}">交易码</th>
            <th field="direction" formatter="mappingFormatter">资金流向</th>
			<th field="amount" sum="true" data-options="formatter:function(v,r,i){ if(r.direction == 'keep'){return '/'}else{return centMoneyFormatter(v);} }">交易金额</th>
			<th field="balancePost" sum="true" formatter="centMoneyFormatter">变动后余额</th>
            <th field="freezeAmount" sum="true" data-options="formatter:function(v,r,i){ if(r.direction != 'keep'){return '/'}else{return centMoneyFormatter(v);} }">冻结金额</th>
            <th field="freezePost" sum="true" formatter="centMoneyFormatter">冻结后总额</th>
			<th field="tradeCode" formatter="mappingFormatter">交易名称</th>
            <th field="bizOrderNo">订单号</th>
			<th field="status" formatter="mappingFormatter">状态</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_accountBill_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_accountBill_action" style="display: none;">
      <a onclick="$.acooly.framework.show('/manage/component/account/accountBill/show.html?id={0}',500,600);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
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
    <script type="text/javascript">
        $(function() {
            $.acooly.framework.initPage('manage_accountBill_searchform','manage_accountBill_datagrid');
        });
    </script>
</div>
