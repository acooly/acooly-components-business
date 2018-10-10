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
                姓名: <input type="text" class="text" size="15" name="search_RLIKE_realName"/>
                状态: <select style="width:120px;height:27px;" name="search_EQ_certStatus" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option>
                <c:forEach var="e" items="${allCertStatuss}"><option value="${e.key}" ${param.search_EQ_certStatus == e.key?'selected':''}>${e.value}</option></c:forEach></select>
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
            <th field="realName">姓名</th>
            <th field="certType" formatter="mappingFormatter">证件类型</th>
            <th field="certNo">证件号码</th>
            <th field="certValidityDate" formatter="dateFormatter">有效期</th>
            <th field="certStatus" formatter="mappingFormatter">实名状态</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_memberPersonal_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_memberPersonal_action" style="display: none;">
      <a onclick="$.acooly.framework.show('/manage/component/member/memberPersonal/show.html?id={0}',600,600);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_memberPersonal_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="manage_memberPersonal_verify()"><i class="fa fa-shield fa-lg fa-fw fa-col"></i>实名认证</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="manage_memberPersonal_info()"><i class="fa fa-user fa-lg fa-fw fa-col"></i>设置个人信息</a>
      <%--<a href="#" class="easyui-menubutton" data-options="menu:'#manage_memberPersonal_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>--%>
      <%--<div id="manage_memberPersonal_exports_menu" style="width:150px;">--%>
        <%--<div onclick="$.acooly.framework.exports('/manage/component/member/memberPersonal/exportXls.html','manage_memberPersonal_searchform','会员个人信息')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>--%>
        <%--<div onclick="$.acooly.framework.exports('/manage/component/member/memberPersonal/exportCsv.html','manage_memberPersonal_searchform','会员个人信息')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>--%>
      <%--</div>--%>
      <%--<a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/member/memberPersonal/importView.html',uploader:'manage_memberPersonal_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>--%>
    </div>
  </div>

</div>
<script>
    //个人实名认证
    function manage_memberPersonal_verify(){
        $.acooly.framework.fireSelectRow("manage_memberPersonal_datagrid",function(row){
            $.acooly.framework.edit({
                url:'/manage/component/member/memberPersonal/verify.html',
                id:row.id,
                entity:'memberPersonal',
                width:600,height:600,
                reload:true,
                editButton:'认证',
                title:'实名认证'
            });
        });
    }
    // 设置个人信息
    function manage_memberPersonal_info(){
        $.acooly.framework.fireSelectRow("manage_memberPersonal_datagrid",function(row){
            $.acooly.framework.edit({url:'/manage/component/member/memberPersonal/edit.html',
                id:row.id,entity:'memberPersonal',
                width:600,height:600,
                editButton:'设置',
                title:'设置个人信息'
            });
        });
    }
</script>