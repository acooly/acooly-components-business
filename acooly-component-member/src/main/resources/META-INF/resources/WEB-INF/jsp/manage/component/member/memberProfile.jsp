<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_memberProfile_searchform','manage_memberProfile_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_memberProfile_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					用户编码: <input type="text" class="text" size="15" name="search_LIKE_userNo"/>
					用户名: <input type="text" class="text" size="15" name="search_LIKE_username"/>
					昵称: <input type="text" class="text" size="15" name="search_LIKE_nickname"/>
				头像类型: <select style="width:80px;height:27px;" name="search_EQ_profilePhotoType" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allProfilePhotoTypes}"><option value="${e.key}" ${param.search_EQ_profilePhotoType == e.key?'selected':''}>${e.value}</option></c:forEach></select>
				实名认证: <select style="width:80px;height:27px;" name="search_EQ_realNameStatus" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allRealNameStatuss}"><option value="${e.key}" ${param.search_EQ_realNameStatus == e.key?'selected':''}>${e.value}</option></c:forEach></select>
				手机认证: <select style="width:80px;height:27px;" name="search_EQ_mobileNoStatus" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allMobileNoStatuss}"><option value="${e.key}" ${param.search_EQ_mobileNoStatus == e.key?'selected':''}>${e.value}</option></c:forEach></select>
				邮箱认证: <select style="width:80px;height:27px;" name="search_EQ_emailStatus" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allEmailStatuss}"><option value="${e.key}" ${param.search_EQ_emailStatus == e.key?'selected':''}>${e.value}</option></c:forEach></select>
				发送短信: <select style="width:80px;height:27px;" name="search_EQ_smsSendStatus" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allSmsSendStatuss}"><option value="${e.key}" ${param.search_EQ_smsSendStatus == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					安全问题设置状态: <input type="text" class="text" size="15" name="search_LIKE_secretQaStatus"/>
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					更新时间: <input size="15" class="text" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_memberProfile_searchform','manage_memberProfile_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_memberProfile_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/member/memberProfile/listJson.html" toolbar="#manage_memberProfile_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">ID</th>
			<th field="userNo">用户编码</th>
			<th field="username">用户名</th>
			<th field="nickname">昵称</th>
			<th field="dailyWords">个性签名</th>
			<th field="profilePhotoType" formatter="mappingFormatter">头像类型</th>
			<th field="profilePhoto">头像</th>
			<th field="realNameStatus" formatter="mappingFormatter">实名认证</th>
			<th field="mobileNoStatus" formatter="mappingFormatter">手机认证</th>
			<th field="emailStatus" formatter="mappingFormatter">邮箱认证</th>
			<th field="smsSendStatus" formatter="mappingFormatter">发送短信</th>
			<th field="secretQaStatus">安全问题设置状态</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_memberProfile_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_memberProfile_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/component/member/memberProfile/edit.html',id:'{0}',entity:'memberProfile',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/component/member/memberProfile/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/component/member/memberProfile/deleteJson.html','{0}','manage_memberProfile_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_memberProfile_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/member/memberProfile/create.html',entity:'memberProfile',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/component/member/memberProfile/deleteJson.html','manage_memberProfile_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_memberProfile_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_memberProfile_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/component/member/memberProfile/exportXls.html','manage_memberProfile_searchform','会员业务信息')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/component/member/memberProfile/exportCsv.html','manage_memberProfile_searchform','会员业务信息')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/member/memberProfile/importView.html',uploader:'manage_memberProfile_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>

</div>
