<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>

<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_pointGrade_searchform', 'manage_pointGrade_datagrid');
    });


    function showThumbnail(value) {
      return '<a class="manage_onlineFile_datagrid_Thumbnail" target="_blank"  href="' + value + '"><img src="' + value + '" width="40" height="30""/></a>';
    }

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_pointGrade_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        <div>
                            等级: <input type="text" class="text" size="15" name="search_EQ_num"/>
                            标题: <input type="text" class="text" size="15" name="search_EQ_title"/>
                            创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime"
                                         onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                            至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime"
                                    onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false"
                               onclick="$.acooly.framework.search('manage_pointGrade_searchform','manage_pointGrade_datagrid');"><i
                                    class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_pointGrade_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/point/pointGrade/listJson.html" toolbar="#manage_pointGrade_toolbar"
               fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">id</th>
                <th field="num">等级</th>
                <th field="title">标题</th>
                <th field="picture" data-options="formatter:function(value){return showThumbnail(value)}">图标</th>
                <th field="startPoint">${pointModuleName}区间_开始</th>
                <th field="endPoint">${pointModuleName}区间_结束</th>
                <th field="createTime" formatter="formatDate">创建时间</th>
                <th field="updateTime" formatter="formatDate">更新时间</th>
                <th field="comments">备注</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_pointGrade_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_pointGrade_action" style="display: none;">
            <a onclick="$.acooly.framework.edit({url:'/manage/point/pointGrade/edit.html',id:'{0}',entity:'pointGrade',width:550,height:400});"
               href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.show('/manage/point/pointGrade/show.html?id={0}',500,400);" href="#" title="查看"><i
                    class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/point/pointGrade/deleteJson.html','{0}','manage_pointGrade_datagrid');" href="#"
               title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_pointGrade_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/point/pointGrade/create.html',entity:'pointGrade',width:550,height:400})"><i
                    class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
            <!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/point/pointGrade/deleteJson.html','manage_pointGrade_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a> -->
            <!--       <a href="#" class="easyui-menubutton" data-options="menu:'#manage_pointGrade_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a> -->
            <!--       <div id="manage_pointGrade_exports_menu" style="width:150px;"> -->
            <!--         <div onclick="$.acooly.framework.exports('/manage/point/pointGrade/exportXls.html','manage_pointGrade_searchform','${pointModuleName}等级')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div> -->
            <!--         <div onclick="$.acooly.framework.exports('/manage/point/pointGrade/exportCsv.html','manage_pointGrade_searchform','${pointModuleName}等级')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div> -->
            <!--       </div> -->
            <!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/point/pointGrade/importView.html',uploader:'manage_pointGrade_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a> -->
        </div>
    </div>

</div>
