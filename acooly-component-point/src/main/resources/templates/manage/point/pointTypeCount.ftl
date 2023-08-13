<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>

<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_pointTypeCount_searchform','manage_pointTypeCount_datagrid');
});

$('#manage_pointTypeCount_datagrid').datagrid({
	showFooter:true,
	onLoadSuccess : function() {
			$('#manage_pointTypeCount_datagrid').datagrid('statistics');
		}
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_pointTypeCount_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					用户号: <input type="text" class="text" size="15" name="search_EQ_userNo"/>
					用户名: <input type="text" class="text" size="15" name="search_LIKE_userName"/>
					业务类型: <input type="text" class="text" size="15" name="search_EQ_busiType"/>
					业务类型描述: <input type="text" class="text" size="15" name="search_EQ_busiTypeText"/>
					修改时间: <input size="15" class="text" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_pointTypeCount_searchform','manage_pointTypeCount_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_pointTypeCount_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/point/pointTypeCount/listJson.html" toolbar="#manage_pointTypeCount_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">ID</th>
			<th field="userNo">用户号</th>
			<th field="userName">用户名</th>
			<th field="num" sum="true">统计次数</th>
			<th field="busiType">业务类型</th>
			<th field="busiTypeText">业务类型描述</th>
			<th field="totalPoint" sum="true">总${pointModuleName}</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">修改时间</th>
			<th field="comments">备注</th>
<!--           	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_pointTypeCount_action',value,row)}">动作</th> -->
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_pointTypeCount_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/point/pointTypeCount/edit.html',id:'{0}',entity:'pointTypeCount',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/point/pointTypeCount/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/point/pointTypeCount/deleteJson.html','{0}','manage_pointTypeCount_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_pointTypeCount_toolbar">
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/point/pointTypeCount/create.html',entity:'pointTypeCount',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a> -->
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/point/pointTypeCount/deleteJson.html','manage_pointTypeCount_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a> -->
<!--       <a href="#" class="easyui-menubutton" data-options="menu:'#manage_pointTypeCount_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a> -->
<!--       <div id="manage_pointTypeCount_exports_menu" style="width:150px;"> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/point/pointTypeCount/exportXls.html','manage_pointTypeCount_searchform','${pointModuleName}业务统计')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/point/pointTypeCount/exportCsv.html','manage_pointTypeCount_searchform','${pointModuleName}业务统计')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div> -->
<!--       </div> -->
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/point/pointTypeCount/importView.html',uploader:'manage_pointTypeCount_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a> -->
    </div>
  </div>

</div>
