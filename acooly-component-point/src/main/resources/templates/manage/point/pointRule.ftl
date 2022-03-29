<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_pointRule_searchform','manage_pointRule_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_pointRule_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
				规则标题: <input type="text" class="text" size="15" name="search_LIKE_title"/>
				规则编码: <select style="width:120px;height:27px;" name="search_EQ_busiTypeCode" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><#list allBusiTypeEnumss as k,v><option value="${k}">${v}</option></#list></select>
				
				有效期类型: <select style="width:120px;height:27px;" name="search_EQ_validType" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><#list allValidTypes as k,v><option value="${k}">${v}</option></#list></select>
				状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><#list allStatuss as k,v><option value="${k}">${v}</option></#list></select>

            	创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                    	至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_pointRule_searchform','manage_pointRule_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_pointRule_datagrid" class="easyui-datagrid" url="/manage/point/pointRule/listJson.html" toolbar="#manage_pointRule_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sortable="true" sum="true">id</th>
			<th field="title">规则标题</th>
			<th field="busiTypeCode">规则编码</th>
			<th field="busiTypeText">规则编码描述</th>
			<th field="amount" sortable="true" sum="true">交易金额</th>
			<th field="point" sortable="true" sum="true">获得${pointModuleName}</th>
 			<th field="validType" formatter="mappingFormatter">有效期类型 </th>			
 			<th field="status" formatter="mappingFormatter">状态</th>			
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="comments" formatter="contentFormatter">规则描述</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_pointRule_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_pointRule_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/point/pointRule/edit.html',id:'{0}',entity:'pointRule',width:650,height:550});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <!--<a onclick="$.acooly.framework.show('/manage/point/pointRule/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>-->
      <a onclick="$.acooly.framework.remove('/manage/point/pointRule/deleteJson.html','{0}','manage_pointRule_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_pointRule_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/point/pointRule/create.html',entity:'pointRule',width:650,height:550})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <!--
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/point/pointRule/deleteJson.html','manage_pointRule_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_pointRule_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_pointRule_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/point/pointRule/exportXls.html','manage_pointRule_searchform','积分规则配置')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/point/pointRule/exportCsv.html','manage_pointRule_searchform','积分规则配置')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/point/pointRule/importView.html',uploader:'manage_pointRule_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
      -->
    </div>
  </div>

</div>
