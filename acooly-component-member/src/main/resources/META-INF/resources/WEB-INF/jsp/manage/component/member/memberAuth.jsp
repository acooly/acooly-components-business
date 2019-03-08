<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_memberAuth_searchform','manage_memberAuth_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_memberAuth_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					用户ID: <input type="text" class="text" size="15" name="search_EQ_userId"/>
					用户编码: <input type="text" class="text" size="15" name="search_LIKE_userNo"/>
					用户名: <input type="text" class="text" size="15" name="search_LIKE_username"/>
				认证类型: <select style="width:80px;height:27px;" name="search_EQ_authType" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allAuthTypes}"><option value="${e.key}" ${param.search_EQ_authType == e.key?'selected':''}>${e.value}</option></c:forEach></select>
				认证角色: <select style="width:80px;height:27px;" name="search_EQ_authRole" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allAuthRoles}"><option value="${e.key}" ${param.search_EQ_authRole == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					登录ID: <input type="text" class="text" size="15" name="search_LIKE_loginId"/>
					认证加盐: <input type="text" class="text" size="15" name="search_LIKE_loginSalt"/>
					有效期
: <input size="15" class="text" id="search_GTE_expireTime" name="search_GTE_expireTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_expireTime" name="search_LTE_expireTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					解锁时间: <input size="15" class="text" id="search_GTE_unlockTime" name="search_GTE_unlockTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_unlockTime" name="search_LTE_unlockTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					登录失败次数: <input type="text" class="text" size="15" name="search_EQ_loginFailTimes"/>
					最后登录时间: <input size="15" class="text" id="search_GTE_lastLoginTime" name="search_GTE_lastLoginTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_lastLoginTime" name="search_LTE_lastLoginTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
				是否登录: <select style="width:80px;height:27px;" name="search_EQ_loginStatus" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allLoginStatuss}"><option value="${e.key}" ${param.search_EQ_loginStatus == e.key?'selected':''}>${e.value}</option></c:forEach></select>
				状态: <select style="width:80px;height:27px;" name="search_EQ_authStatus" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allAuthStatuss}"><option value="${e.key}" ${param.search_EQ_authStatus == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					更新时间: <input size="15" class="text" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_memberAuth_searchform','manage_memberAuth_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_memberAuth_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/member/memberAuth/listJson.html" toolbar="#manage_memberAuth_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">ID</th>
			<th field="userId" sum="true">用户ID</th>
			<th field="userNo">用户编码</th>
			<th field="username">用户名</th>
			<th field="authType" formatter="mappingFormatter">认证类型</th>
			<th field="authRole" formatter="mappingFormatter">认证角色</th>
			<th field="loginId">登录ID</th>
			<th field="loginKey">登录秘钥/密码</th>
			<th field="loginSalt">认证加盐</th>
		    <th field="expireTime" formatter="dateTimeFormatter">有效期
</th>
		    <th field="unlockTime" formatter="dateTimeFormatter">解锁时间</th>
			<th field="loginFailTimes" >登录失败次数</th>
		    <th field="lastLoginTime" formatter="dateTimeFormatter">最后登录时间</th>
			<th field="loginStatus" formatter="mappingFormatter">是否登录</th>
			<th field="authStatus" formatter="mappingFormatter">状态</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_memberAuth_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_memberAuth_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/component/member/memberAuth/edit.html',id:'{0}',entity:'memberAuth',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/component/member/memberAuth/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/component/member/memberAuth/deleteJson.html','{0}','manage_memberAuth_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_memberAuth_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/member/memberAuth/create.html',entity:'memberAuth',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/component/member/memberAuth/deleteJson.html','manage_memberAuth_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_memberAuth_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_memberAuth_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/component/member/memberAuth/exportXls.html','manage_memberAuth_searchform','b_member_auth')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/component/member/memberAuth/exportCsv.html','manage_memberAuth_searchform','b_member_auth')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/member/memberAuth/importView.html',uploader:'manage_memberAuth_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>

</div>
