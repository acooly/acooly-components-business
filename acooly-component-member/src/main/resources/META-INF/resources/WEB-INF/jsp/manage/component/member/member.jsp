<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_member_searchform','manage_member_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_member_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					父会员编码: <input type="text" class="text" size="15" name="search_LIKE_parentUserNo"/>
					会员编码: <input type="text" class="text" size="15" name="search_EQ_userNo"/>
					用户名: <input type="text" class="text" size="15" name="search_LIKE_username"/>
				    用户类型: <select style="width:80px;height:27px;" name="search_EQ_userType" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allUserTypes}"><option value="${e.key}" ${param.search_EQ_userType == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					手机号码: <input type="text" class="text" size="15" name="search_EQ_mobileNo"/>
					姓名: <input type="text" class="text" size="15" name="search_EQ_realName"/>
					身份证号码: <input type="text" class="text" size="15" name="search_EQ_idCardNo"/>
				    用户等级: <select style="width:80px;height:27px;" name="search_EQ_grade" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allGrades}"><option value="${e.key}" ${param.search_EQ_grade == e.key?'selected':''}>${e.value}</option></c:forEach></select>
				    状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allStatuss}"><option value="${e.key}" ${param.search_EQ_status == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					更新时间: <input size="15" class="text" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_member_searchform','manage_member_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_member_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/member/member/listJson.html" toolbar="#manage_member_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">会员ID</th>
			<th field="parentid" sum="true">父会员ID</th>
			<th field="parentUserNo">父会员编码</th>
			<th field="userNo">会员编码</th>
			<th field="username">用户名</th>
			<th field="userType" formatter="mappingFormatter">用户类型</th>
			<th field="mobileNo">手机号码</th>
			<th field="email">邮件</th>
			<th field="realName">姓名</th>
			<th field="certNo">身份证号码</th>
			<th field="grade" formatter="mappingFormatter">用户等级</th>
			<th field="status" formatter="mappingFormatter">状态</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_member_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_member_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/component/member/member/edit.html',id:'{0}',entity:'member',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/component/member/member/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_member_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/member/member/create.html',entity:'member',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_member_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_member_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/component/member/member/exportXls.html','manage_member_searchform','会员信息')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/component/member/member/exportCsv.html','manage_member_searchform','会员信息')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/member/member/importView.html',uploader:'manage_member_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>

</div>
