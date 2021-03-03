<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<script type="text/javascript">

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
      <form id="manage_account_searchform" class="form-inline ac-form-search" onsubmit="return false">
          <div class="form-group">
              <label class="col-form-label">账户编码：</label>
              <input type="text" class="form-control form-control-sm" name="search_EQ_accountNo"/>
          </div>
          <div class="form-group">
              <label class="col-form-label">用户名：</label>
              <input type="text" class="form-control form-control-sm" name="search_LIKE_username"/>
          </div>
          <div class="form-group">
              <label class="col-form-label">余额：</label>
              <input type="text" class="form-control form-control-sm" name="search_GTE_balance"/> ~
              <input type="text" class="form-control form-control-sm" name="search_LTE_balance"/>
          </div>
          <div class="form-group">
              <label class="col-form-label">类型：</label>
              <select name="search_EQ_accountType" class="form-control input-sm select2bs4">
                  <option value="">所有</option><#list allAccountTypes as k,v><option value="${k}">${v}</option></#list>
              </select>
          </div>
          <div class="form-group">
              <label class="col-form-label">状态：</label>
              <select name="search_EQ_status" class="form-control input-sm select2bs4">
                  <option value="">所有</option><#list allStatuss as k,v><option value="${k}">${v}</option></#list>
              </select>
          </div>
          <div class="form-group">
              <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_account_searchform','manage_account_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</button>
          </div>
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
    <#if isCreateAccount>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/account/account/create.html',entity:'account',width:500,height:450})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>开户</a>
    </#if>
    </div>
  </div>

    <script type="text/javascript">
        $(function() {
            $.acooly.framework.initPage('manage_account_searchform','manage_account_datagrid');
        });
    </script>
</div>
