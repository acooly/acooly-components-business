<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_countNumOrder_searchform','manage_countNumOrder_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_countNumOrder_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
          			游戏ID: <input type="text" class="text" size="5" name="search_EQ_countId"/>
					订单号: <input type="text" class="text" size="8" name="search_LIKE_orderNo"/>
					游戏名称: <input type="text" class="text" size="8" name="search_LIKE_countTitle"/>
					<!--创建者: <input type="text" class="text" size="8" name="search_LIKE_createUserName"/>-->
					用户名称: <input type="text" class="text" size="8" name="search_LIKE_userName"/>
					类型: <select style="width:60px;height:27px;" name="search_EQ_countType" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><#list allCountTypes as k,v><option value="${k}">${v}</option></#list></select>
					创建时间: <input size="8" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="8" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />

          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_countNumOrder_searchform','manage_countNumOrder_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_countNumOrder_datagrid" class="easyui-datagrid" url="/manage/component/countnum/countNumOrder/listJson.html" toolbar="" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sortable="true" sum="true">id</th>
			<th field="orderNo">订单号</th>
			<th field="countId" sortable="true" sum="true">游戏id</th>
			<th field="countTitle">游戏名称</th>
			<th field="countType" formatter="mappingFormatter">类型</th>
			<th field="createUserId" sortable="true" sum="true">创建者id</th>
			<th field="createUserName">创建者用户名</th>
			<th field="userId" sortable="true" sum="true">用户id</th>
			<th field="userName">用户名称</th>
			<th field="joinNum" sortable="true" sum="true">参与次数</th>
			<th field="num" sortable="true" sum="true">有效值1</th>
			<th field="time" sortable="true" sum="true">有效值2</th>
		    <th field="validTime" formatter="dateTimeFormatter">有效值更新时间</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_countNumOrder_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_countNumOrder_action" style="display: none;">
      <!--<a onclick="$.acooly.framework.edit({url:'/manage/component/countnum/countNumOrder/edit.html',id:'{0}',entity:'countNumOrder',width:500,height:500});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>-->
      <a onclick="$.acooly.framework.show('/manage/component/countnum/countNumOrder/show.html?id={0}',500,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <!--<a onclick="$.acooly.framework.remove('/manage/component/countnum/countNumOrder/deleteJson.html','{0}','manage_countNumOrder_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>-->
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_countNumOrder_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/countnum/countNumOrder/create.html',entity:'countNumOrder',width:500,height:500})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/component/countnum/countNumOrder/deleteJson.html','manage_countNumOrder_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_countNumOrder_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_countNumOrder_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/component/countnum/countNumOrder/exportXls.html','manage_countNumOrder_searchform','游戏-计数用户订单')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/component/countnum/countNumOrder/exportCsv.html','manage_countNumOrder_searchform','游戏-计数用户订单')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/countnum/countNumOrder/importView.html',uploader:'manage_countNumOrder_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>

</div>
