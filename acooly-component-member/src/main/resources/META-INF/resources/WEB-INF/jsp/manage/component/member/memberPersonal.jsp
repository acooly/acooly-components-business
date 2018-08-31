<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_memberPersonal_searchform','manage_memberPersonal_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_memberPersonal_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					用户编码: <input type="text" class="text" size="15" name="search_EQ_userNo"/>
					用户名: <input type="text" class="text" size="15" name="search_EQ_username"/>
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					更新时间: <input size="15" class="text" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_memberPersonal_searchform','manage_memberPersonal_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_memberPersonal_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/member/memberPersonal/listJson.html" toolbar="#manage_memberPersonal_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">ID</th>
			<th field="userNo">用户编码</th>
			<th field="username">用户名</th>
			<th field="maritalStatus" formatter="mappingFormatter">婚姻状况</th>
			<th field="childrenCount" formatter="mappingFormatter">子女情况</th>
			<th field="educationLevel" formatter="mappingFormatter">教育背景</th>
			<th field="incomeMonth" formatter="mappingFormatter">月收入</th>
			<th field="socialInsurance" formatter="mappingFormatter">社会保险</th>
			<th field="houseFund" formatter="mappingFormatter">公积金</th>
			<th field="houseStatue" formatter="mappingFormatter">住房情况</th>
			<th field="carStatus" formatter="mappingFormatter">是否购车</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_memberPersonal_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_memberPersonal_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/component/member/memberPersonal/edit.html',id:'{0}',entity:'memberPersonal',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/component/member/memberPersonal/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_memberPersonal_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/member/memberPersonal/create.html',entity:'memberPersonal',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_memberPersonal_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_memberPersonal_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/component/member/memberPersonal/exportXls.html','manage_memberPersonal_searchform','会员个人信息')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/component/member/memberPersonal/exportCsv.html','manage_memberPersonal_searchform','会员个人信息')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/member/memberPersonal/importView.html',uploader:'manage_memberPersonal_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>

</div>
