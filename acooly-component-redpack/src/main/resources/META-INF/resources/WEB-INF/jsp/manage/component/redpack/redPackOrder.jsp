<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_redPackOrder_searchform','manage_redPackOrder_datagrid');

	//统计
	$('#manage_redPackOrder_datagrid').datagrid({
		showFooter:true,
		onLoadSuccess : function() {
        	$('#manage_redPackOrder_datagrid').datagrid('statistics');
    	}
	});
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_redPackOrder_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					订单号: <input type="text" class="text" size="10" name="search_EQ_orderNo"/>
					红包ID: <input type="text" class="text" size="15" name="search_EQ_redPackId"/>
					红包标题: <input type="text" class="text" size="15" name="search_LIKE_redPackTitle"/>
					发送者ID: <input type="text" class="text" size="15" name="search_EQ_sendUserId"/>
					发送者用户名: <input type="text" class="text" size="15" name="search_EQ_sendUserName"/>
					<br/>
					领取用户id: <input type="text" class="text" size="15" name="search_EQ_userId"/>
					领取用户名: <input type="text" class="text" size="15" name="search_EQ_userName"/>
					状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allStatuss}"><option value="${e.key}" ${param.search_EQ_status == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_redPackOrder_searchform','manage_redPackOrder_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_redPackOrder_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/redpack/redPackOrder/listJson.html" toolbar="#manage_redPackOrder_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">id</th>
			<th field="orderNo">订单号</th>
			<th field="redPackId">红包id</th>
			<th field="redPackTitle">红包标题</th>
			<th field="sendUserId">发送者ID</th>
			<th field="sendUserName">发送者用户名</th>
			<th field="amount" sum="true" formatter="centMoneyFormatter">金额</th>
			<th field="userId">领取用户id</th>
			<th field="userName">领取用户名</th>
			<th field="type" formatter="mappingFormatter">类型</th>
			<th field="status" formatter="mappingFormatter">状态</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_redPackOrder_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_redPackOrder_action" style="display: none;">
<!--       <a onclick="$.acooly.framework.edit({url:'/manage/component/redpack/redPackOrder/edit.html',id:'{0}',entity:'redPackOrder',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a> -->
      <a onclick="$.acooly.framework.show('/manage/component/redpack/redPackOrder/show.html?id={0}',600,500);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
<!--       <a onclick="$.acooly.framework.remove('/manage/component/redpack/redPackOrder/deleteJson.html','{0}','manage_redPackOrder_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a> -->
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_redPackOrder_toolbar">
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/redpack/redPackOrder/create.html',entity:'redPackOrder',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a> -->
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/component/redpack/redPackOrder/deleteJson.html','manage_redPackOrder_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a> -->
<!--       <a href="#" class="easyui-menubutton" data-options="menu:'#manage_redPackOrder_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a> -->
<!--       <div id="manage_redPackOrder_exports_menu" style="width:150px;"> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/component/redpack/redPackOrder/exportXls.html','manage_redPackOrder_searchform','红包订单')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/component/redpack/redPackOrder/exportCsv.html','manage_redPackOrder_searchform','红包订单')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div> -->
<!--       </div> -->
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/redpack/redPackOrder/importView.html',uploader:'manage_redPackOrder_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a> -->
    </div>
  </div>

</div>
