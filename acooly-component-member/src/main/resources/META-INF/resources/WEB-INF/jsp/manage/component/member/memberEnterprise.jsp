<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_memberEnterprise_searchform','manage_memberEnterprise_datagrid');
});

function showPicture(value) {
    if ((value != null)&&(value != "")) {
        return '<a class="manage_onlineFile_datagrid_thumbnail" target="_blank"  href="' + value + '"><img src="' + value + '" width="40" height="30""/></a>';
    }
}

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_memberEnterprise_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					编码: <input type="text" class="text" size="15" name="search_EQ_userNo"/>
					用户名: <input type="text" class="text" size="15" name="search_EQ_username"/>
				     企业类型: <select style="width:80px;height:27px;" name="search_EQ_entType" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allEntTypes}"><option value="${e.key}" ${param.search_EQ_entType == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					企业名称: <input type="text" class="text" size="15" name="search_LIKE_entName"/>
					信用代码: <input type="text" class="text" size="15" name="search_EQ_licenceNo"/>
					注册时间: <input size="15" type="text" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至 <input size="15" type="text" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_memberEnterprise_searchform','manage_memberEnterprise_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_memberEnterprise_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/member/memberEnterprise/listJson.html" toolbar="#manage_memberEnterprise_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">主键</th>
			<th field="userNo">用户编码</th>
			<th field="username">用户名</th>
			<th field="entType" formatter="mappingFormatter">企业类型</th>
			<th field="entName">企业名称</th>
			<th field="licenceNo">社会统一信用代码</th>
			<th field="licencePath" data-options="formatter:function(value){return showPicture(value)}">营业执照图片</th>
			<th field="licenceAddress">营业执照地址</th>
			<th field="businessLife" >营业年限</th>
			<th field="legalName">法人姓名</th>
			<th field="legalCertType" formatter="mappingFormatter">法人证件类型</th>
			<th field="legalCertNo">法人证件号码</th>
			<th field="legalCertValidTime">法人证件到期时间</th>
			<th field="legalCertFrontPath" data-options="formatter:function(value){return showPicture(value)}">法人证件正面</th>
			<th field="legalCertBackPath" data-options="formatter:function(value){return showPicture(value)}">法人证件背面</th>
			<th field="businessScope">经营范围</th>
			<th field="holdingEnum" formatter="mappingFormatter">实际控股人或企业类型</th>
			<th field="holdingName">股东或实际控制人真实姓名</th>
			<th field="holdingCertType" formatter="mappingFormatter">股东或实际控制人证件类型</th>
			<th field="holdingCertNo">股东或实际控制人证件号</th>
			<th field="holdingCertValidTime">股东或实际控制人证件到期时间</th>
			
			<th field="holdingCertFrontPath" data-options="formatter:function(value){return showPicture(value)}">实际控制人证件正面</th>
			<th field="holdingCertBackPath" data-options="formatter:function(value){return showPicture(value)}">实际控制人证件背面</th>
			
			<th field="accountLicenseNo">开户许可证号码</th>
			
			<th field="accountLicensePath" data-options="formatter:function(value){return showPicture(value)}">开户许可证图片</th>
			
			<th field="taxAuthorityNo">税务登记证号码</th>
			
			<th field="taxAuthorityPath" data-options="formatter:function(value){return showPicture(value)}">税务登记证图片</th>
			
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_memberEnterprise_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_memberEnterprise_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/component/member/memberEnterprise/edit.html',id:'{0}',entity:'memberEnterprise',width:700,height:650});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/component/member/memberEnterprise/show.html?id={0}',700,650);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_memberEnterprise_toolbar">
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/member/memberEnterprise/create.html',entity:'memberEnterprise',width:700,height:650})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a> -->
<!--       <a href="#" class="easyui-menubutton" data-options="menu:'#manage_memberEnterprise_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a> -->
<!--       <div id="manage_memberEnterprise_exports_menu" style="width:150px;"> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/component/member/memberEnterprise/exportXls.html','manage_memberEnterprise_searchform','企业客户实名认证')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div> -->
<!--         <div onclick="$.acooly.framework.exports('/manage/component/member/memberEnterprise/exportCsv.html','manage_memberEnterprise_searchform','企业客户实名认证')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div> -->
<!--       </div> -->
<!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/member/memberEnterprise/importView.html',uploader:'manage_memberEnterprise_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a> -->
    </div>
  </div>

</div>
