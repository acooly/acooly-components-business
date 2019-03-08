<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_member_searchform','manage_member_datagrid');
});

/**
 * 编辑会员配置信息
 */
function manage_member_profile_edit(){
    $.acooly.framework.fireSelectRow("manage_member_datagrid",function(row){
        $.acooly.framework.edit({
            url:'/manage/component/member/memberProfile/edit.html',
            id:row.id,
            form:'manage_memberProfile_editform',
            datagrid:'manage_member_datagrid',
            width:500,height:500
        });
    });
}

/**
 * 编辑会员联系信息
 */
function manage_member_contact_edit(){
    $.acooly.framework.fireSelectRow("manage_member_datagrid",function(row){
        $.acooly.framework.edit({
            url:'/manage/component/member/memberContact/edit.html',
            id:row.id,
            entity:'memberContact',
            width:600,height:500
        });
    });
}

function manage_memeber_profile_formatter(v,r,i,d,property) {
    if(!r.memberProfile || !r.memberProfile[property]){return null;} return d['data'].allWhtherStatuss[r.memberProfile[property]];
}

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_member_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					编码: <input type="text" class="text" size="15" name="search_EQ_userNo"/>
					用户名: <input type="text" class="text" size="15" name="search_EQ_username"/>
					手机: <input type="text" class="text" size="15" name="search_EQ_mobileNo"/>
					姓名: <input type="text" class="text" size="15" name="search_EQ_realName"/>
                    类型: <select style="width:80px;height:27px;" name="search_EQ_userType" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allUserTypes}"><option value="${e.key}" ${param.search_EQ_userType == e.key?'selected':''}>${e.value}</option></c:forEach></select>
                    等级: <select style="width:80px;height:27px;" name="search_EQ_grade" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allGrades}"><option value="${e.key}" ${param.search_EQ_grade == e.key?'selected':''}>${e.value}</option></c:forEach></select>
				    状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allStatuss}"><option value="${e.key}" ${param.search_EQ_status == e.key?'selected':''}>${e.value}</option></c:forEach></select>
				实名状态: <select style="width:80px;height:27px;" name="search_EQ_realNameStatus" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allWhtherStatuss}"><option value="${e.key}" ${param.search_EQ_realNameStatus == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					注册时间: <input size="10" type="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="10" type="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_member_searchform','manage_member_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_member_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/member/member/listJson.html" toolbar="#manage_member_toolbar" fit="true"
           border="false" fitColumns="false" pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]"
           sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sortable="true">会员ID</th>
			<th field="userNo" sortable="true">会员编码</th>
			<th field="username">用户名</th>
            <th field="userType" formatter="mappingFormatter">类型</th>
            <th field="realName">真名</th>
                <th field="memberProfile.realNameStatus" data-options="formatter:function(v,r,i,d){return manage_memeber_profile_formatter(v,r,i,d,'realNameStatus');}">实名
            </th>
			<th field="mobileNo">手机</th>
            <th field="memberProfile.mobileNoStatus"
                data-options="formatter:function(v,r,i,d){if(!r.memberProfile || !r.memberProfile.mobileNoStatus){return null;} return d['data'].allWhtherStatuss[r.memberProfile.mobileNoStatus];}">手机认证</th>
			<th field="email">邮件</th>
            <th field="memberProfile.emailStatus" data-options="formatter:function(v,r,i,d){return manage_memeber_profile_formatter(v,r,i,d,'emailStatus');}">邮件认证</th>
            <th field="memberProfile.broker" data-options="formatter:function(v,r,i,d){return r.memberProfile.broker;}">经纪人</th>
            <th field="memberProfile.inviter" data-options="formatter:function(v,r,i,d){return r.memberProfile.inviter;}">介绍人</th>
			<th field="grade" formatter="mappingFormatter">等级</th>
			<th field="status" formatter="mappingFormatter">状态</th>
		    <th field="createTime" formatter="dateTimeFormatter">注册时间</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_member_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_member_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/component/member/member/edit.html',id:'{0}',entity:'member',width:500,height:600,reload:true});" href="#" title="编辑"><i
              class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <%--<a onclick="$.acooly.framework.show('/manage/component/member/member/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>--%>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_member_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/member/member/create.html',entity:'member',width:600,height:600,reload:true})"><i
              class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="manage_member_profile_edit()"><i class="fa fa-cog fa-lg fa-fw fa-col"></i>会员配置信息</a>
        <a href="#" class="easyui-linkbutton" plain="true" onclick="manage_member_contact_edit()"><i class="fa fa-address-book fa-lg fa-fw fa-col"></i>会员联系信息</a>

      <%--<a href="#" class="easyui-menubutton" data-options="menu:'#manage_member_auth_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>认证</a>--%>
      <%--<div id="manage_member_auth_menu" style="width:150px;">--%>
            <%--<div onclick=""><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>实名认证</div>--%>
            <%--<div onclick=""><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>设置手机认证</div>--%>
            <%--<div onclick=""><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>设置邮箱认证</div>--%>
      <%--</div>--%>
      <%--<a href="#" class="easyui-menubutton" data-options="menu:'#manage_member_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>--%>
      <%--<div id="manage_member_exports_menu" style="width:150px;">--%>
        <%--<div onclick="$.acooly.framework.exports('/manage/component/member/member/exportXls.html','manage_member_searchform','会员信息')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>--%>
        <%--<div onclick="$.acooly.framework.exports('/manage/component/member/member/exportCsv.html','manage_member_searchform','会员信息')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>--%>
      <%--</div>--%>
      <%--<a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/member/member/importView.html',uploader:'manage_member_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>--%>
    </div>
  </div>

</div>
<script>
    // $(function() {
    //     var pager = $('#manage_member_datagrid').datagrid().datagrid('getPager');	// get the pager of datagrid
    //     pager.pagination({
    //         buttons:[{
    //             iconCls:'icon-search',
    //             handler:function(){
    //                 $('#manage_member_datagrid').datagrid('hideColumn','mobileNo');
    //             }
    //         }]
    //     });
    // });
</script>