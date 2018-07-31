<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_memberContact_searchform','manage_memberContact_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_memberContact_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					会员编码: <input type="text" class="text" size="15" name="search_LIKE_userNo"/>
					用户名: <input type="text" class="text" size="15" name="search_LIKE_username"/>
					手机号码: <input type="text" class="text" size="15" name="search_LIKE_mobileNo"/>
					电话号码: <input type="text" class="text" size="15" name="search_LIKE_phoneNo"/>
					居住地 省: <input type="text" class="text" size="15" name="search_LIKE_province"/>
					居住地 市: <input type="text" class="text" size="15" name="search_LIKE_city"/>
					居住地 县/区: <input type="text" class="text" size="15" name="search_LIKE_district"/>
					邮政编码: <input type="text" class="text" size="15" name="search_LIKE_zip"/>
					QQ: <input type="text" class="text" size="15" name="search_LIKE_qq"/>
					MSN: <input type="text" class="text" size="15" name="search_LIKE_wechat"/>
					旺旺: <input type="text" class="text" size="15" name="search_LIKE_wangwang"/>
					备注: <input type="text" class="text" size="15" name="search_LIKE_google"/>
					facebeek: <input type="text" class="text" size="15" name="search_LIKE_facebeek"/>
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					更新时间: <input size="15" class="text" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_memberContact_searchform','manage_memberContact_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_memberContact_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/component/member/memberContact/listJson.html" toolbar="#manage_memberContact_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">主键</th>
			<th field="userNo">会员编码</th>
			<th field="username">用户名</th>
			<th field="mobileNo">手机号码</th>
			<th field="phoneNo">电话号码</th>
			<th field="province">居住地 省</th>
			<th field="city">居住地 市</th>
			<th field="district">居住地 县/区</th>
			<th field="address">详细地址</th>
			<th field="zip">邮政编码</th>
			<th field="qq">QQ</th>
			<th field="wechat">MSN</th>
			<th field="wangwang">旺旺</th>
			<th field="google">备注</th>
			<th field="facebeek">facebeek</th>
			<th field="email">email</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
			<th field="comments">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_memberContact_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_memberContact_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/component/member/memberContact/edit.html',id:'{0}',entity:'memberContact',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/component/member/memberContact/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/component/member/memberContact/deleteJson.html','{0}','manage_memberContact_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_memberContact_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/component/member/memberContact/create.html',entity:'memberContact',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/component/member/memberContact/deleteJson.html','manage_memberContact_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_memberContact_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_memberContact_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/component/member/memberContact/exportXls.html','manage_memberContact_searchform','客户联系信息')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/component/member/memberContact/exportCsv.html','manage_memberContact_searchform','客户联系信息')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/component/member/memberContact/importView.html',uploader:'manage_memberContact_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>

</div>
