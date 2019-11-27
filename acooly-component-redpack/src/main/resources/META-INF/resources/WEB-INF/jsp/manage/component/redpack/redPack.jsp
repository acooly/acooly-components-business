<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_redPack_searchform','manage_redPack_datagrid');

	//统计
	$('#manage_redPack_datagrid').datagrid({
		showFooter:true,
		onLoadSuccess : function() {
        	$('#manage_redPack_datagrid').datagrid('statistics');
    	}
	});
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_redPack_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
          			id: <input type="text" class="text" size="6" name="search_EQ_id"/>
					标题: <input type="text" class="text" size="10" name="search_EQ_title"/>
					用户ID: <input type="text" class="text" size="10" name="search_EQ_sendUserId"/>
					用户名: <input type="text" class="text" size="10" name="search_EQ_sendUserName"/>
					状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allStatuss}"><option value="${e.key}" ${param.search_EQ_status == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					创建时间: <input size="10" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="10" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_redPack_searchform','manage_redPack_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_redPack_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/redpack/redPack/listJson.html" toolbar="#manage_redPack_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id">id</th>
			<th field="title">标题</th>
			<th field="sendUserId">用户id</th>
			<th field="sendUserName">用户名</th>
			<th field="status" formatter="mappingFormatter">状态</th>
		    <th field="overdueTime" formatter="dateTimeFormatter"  sortable="true">过期时间</th>
			<th field="totalAmount" sum="true" formatter="centMoneyFormatter">红包总金额</th>
			<th field="sendOutAmount" sum="true" formatter="centMoneyFormatter">已发送金额</th>
			<th field="refundAmount" sum="true" formatter="centMoneyFormatter">退款金额</th>
			<th field="totalNum" sum="true">总数</th>
			<th field="sendOutNum" sum="true">已发送数量</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="remark">红包备注</th>
			<th field="businessId">业务id</th>
			<th field="businessData">业务参数</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_redPack_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_redPack_action" style="display: none;">
<!--       <a onclick="$.acooly.framework.edit({url:'/manage/component/redpack/redPack/edit.html',id:'{0}',entity:'redPack',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a> -->
      <a onclick="$.acooly.framework.show('/manage/component/redpack/redPack/show.html?id={0}',600,550);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
<!--       <a onclick="$.acooly.framework.remove('/manage/component/redpack/redPack/deleteJson.html','{0}','manage_redPack_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a> -->
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_redPack_toolbar">
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/redpack/redPack/create.html',entity:'redPack',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a> -->
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/component/redpack/redPack/deleteJson.html','manage_redPack_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a> -->
<!--       <a href="#" class="easyui-menubutton" data-options="menu:'#manage_redPack_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a> -->
<!--       <div id="manage_redPack_exports_menu" style="width:150px;"> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/component/redpack/redPack/exportXls.html','manage_redPack_searchform','红包')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/component/redpack/redPack/exportCsv.html','manage_redPack_searchform','红包')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div> -->
<!--       </div> -->
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/redpack/redPack/importView.html',uploader:'manage_redPack_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a> -->
    </div>
  </div>

</div>
