<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_countNum_searchform','manage_countNum_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_countNum_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					名称: <input type="text" class="text" size="10" name="search_LIKE_title"/>
					创建者: <input type="text" class="text" size="10" name="search_LIKE_createUserName"/>
				类型: <select style="width:80px;height:27px;" name="search_EQ_type" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><#list allTypes as k,v><option value="${k}">${v}</option></#list></select>
				状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><#list allStatuss as k,v><option value="${k}">${v}</option></#list></select>
					业务id: <input type="text" class="text" size="10" name="search_LIKE_businessId"/>
				创建时间: <input size="10" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="10" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
	
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_countNum_searchform','manage_countNum_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_countNum_datagrid" class="easyui-datagrid" url="/manage/component/countnum/countNum/listJson.html" toolbar="" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sortable="true" sum="true">id</th>
			<th field="title">名称</th>
			<th field="createUserId" sortable="true" sum="true">创建者id</th>
			<th field="createUserName">创建者用户名</th>
			<th field="type" formatter="mappingFormatter">类型</th>
			<th field="status" formatter="mappingFormatter">状态</th>
			<th field="overdueTime" formatter="dateTimeFormatter" sortable="true">过期时间</th>
			
<!--			<th field="isCover" formatter="mappingFormatter">是否参与覆盖</th>-->
			<th field="maxNum" sortable="true" sum="true">最大参与人数</th>
			<th field="limitNum" sortable="true" sum="true">单人可参与次数</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="businessId"  sortable="true">业务id</th>
			<th field="businessData" formatter="contentFormatter">业务参数</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_countNum_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_countNum_action" style="display: none;">
      <!--<a onclick="$.acooly.framework.edit({url:'/manage/component/countnum/countNum/edit.html',id:'{0}',entity:'countNum',width:500,height:500});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>-->
      <a onclick="$.acooly.framework.show('/manage/component/countnum/countNum/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <!--<a onclick="$.acooly.framework.remove('/manage/component/countnum/countNum/deleteJson.html','{0}','manage_countNum_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>-->
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_countNum_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/countnum/countNum/create.html',entity:'countNum',width:500,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/component/countnum/countNum/deleteJson.html','manage_countNum_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_countNum_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_countNum_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/component/countnum/countNum/exportXls.html','manage_countNum_searchform','游戏-计数')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/component/countnum/countNum/exportCsv.html','manage_countNum_searchform','游戏-计数')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/countnum/countNum/importView.html',uploader:'manage_countNum_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>

</div>
