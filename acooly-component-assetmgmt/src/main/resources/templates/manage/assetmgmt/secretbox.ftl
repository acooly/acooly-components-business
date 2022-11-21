<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_secretbox_searchform" class="form-inline ac-form-search" onsubmit="return false">
            <div class="form-group">
                <label class="col-form-label">分类：</label>
                <input type="text" class="form-control form-control-sm" name="selected_typeName" readonly/>
                <input type="hidden" class="form-control form-control-sm" name="search_RLIKE_typePath"/>
                <input type="hidden" class="form-control form-control-sm" name="selected_typeCode"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">标题：</label>
                <input type="text" class="form-control form-control-sm" name="search_EQ_title"/>
            </div>
            <div class="form-group">
                <label class="col-form-label">账号：</label>
                <input type="text" class="form-control form-control-sm" name="search_LIKE_username"/>
            </div>
            <div class="form-group">
                <button class="btn btn-sm btn-primary" type="button" onclick="$.acooly.framework.search('manage_secretbox_searchform','manage_secretbox_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i> 查询</button>
            </div>
        </form>
    </div>

    <!-- 菜单树 -->
    <div data-options="region:'west',border:true,split:true" style="width: 200px;" align="left">
        <div class="easyui-panel datagrid-toolbar">
            <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="$.acooly.secretbox.tree.expandToggle(this)"><i class="fa fa-minus-square-o" aria-hidden="true"></i></a>
            <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="$.acooly.secretbox.tree.add()"><i class="fa fa-dot-circle-o" aria-hidden="true"></i> 添加根分类</a>
        </div>
        <div id="manage_secretbox_tree" class="ztree"></div>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_secretbox_datagrid" class="easyui-datagrid" url="/manage/assetmgmt/secretbox/listJson.html" toolbar="#manage_secretbox_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true"
            data-options="onCheck:function(rowIndex,rowData){$('#manage_secretbox_toolbar_clipPassword').linkbutton('enable'); $('#manage_secretbox_toolbar_clipSecretbox').linkbutton('enable'); },
            onLoadSuccess:function(){$('#manage_secretbox_toolbar_clipPassword').linkbutton('disable'); $('#manage_secretbox_toolbar_clipSecretbox').linkbutton('disable'); }">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" formatter="idFormatter">编号</th>
                <th field="id" sortable="true">ID</th>
                <th field="code">编码</th>
                <th field="title">标题</th>
                <th field="secretType" formatter="mappingFormatter">安全类型</th>
                <th field="username" formatter="$.acooly.secretbox.formatter.copy">账号</th>
                <th field="password" formatter="$.acooly.secretbox.formatter.password">密码</th>
                <th field="accessPoint" formatter="$.acooly.secretbox.formatter.accessPoint">访问入口</th>
                <th field="serviceType" formatter="mappingFormatter">服务类型</th>
                <th field="updateTime" formatter="dateTimeFormatter">修改时间</th>
                <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_secretbox_action',value,row)}">动作</th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_secretbox_action" style="display: none;">
            <div class="btn-group btn-group-xs">
                <button onclick="$.acooly.framework.edit({url:'/manage/assetmgmt/secretbox/edit.html',id:'{0}',entity:'secretbox',width:800,height:600});" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i>编辑</button>
                <button onclick="$.acooly.framework.show('/manage/assetmgmt/secretbox/show.html?id={0}',800,600);" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i>查看</button>
                <button onclick="$.acooly.framework.remove('/manage/assetmgmt/secretbox/deleteJson.html','{0}','manage_secretbox_datagrid');" class="btn btn-outline-primary btn-xs" type="button"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>删除</button>
            </div>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_secretbox_toolbar">
            <a id="manage_secretbox_toolbar_create" href="#" class="easyui-linkbutton" data-options="disabled:true" plain="true" onclick="$.acooly.secretbox.create()"><i class="fa fa-plus-circle fa-fw fa-col"></i>添加</a>
            <a id="manage_secretbox_toolbar_clipPassword" href="#" class="easyui-linkbutton" data-options="disabled:true" plain="true" onclick="$.acooly.secretbox.clipPassword('#manage_secretbox_datagrid_clipassword_btn');" id="manage_secretbox_datagrid_clipassword_btn"><i class="fa fa-clipboard fa-fw fa-col"></i>复制密码</a>
            <a id="manage_secretbox_toolbar_clipSecretbox" href="#" class="easyui-linkbutton" data-options="disabled:true" plain="true" onclick="$.acooly.secretbox.clipSecretbox('#manage_secretbox_datagrid_clisecretbox_btn')" id="manage_secretbox_datagrid_clisecretbox_btn"><i class="fa fa-clone fa-fw fa-col"></i>复制账户信息</a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/assetmgmt/secretbox/deleteJson.html','manage_secretbox_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
            <a href="#" class="easyui-menubutton" data-options="menu:'#manage_secretbox_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-fw fa-col"></i>批量导出</a>
            <div id="manage_secretbox_exports_menu" style="width:150px;">
                <div onclick="$.acooly.framework.exports('/manage/assetmgmt/secretbox/exportXls.html','manage_secretbox_searchform','资产列表')"><i class="fa fa-file-excel-o fa-fw fa-col"></i>Excel</div>
                <div onclick="$.acooly.framework.exports('/manage/assetmgmt/secretbox/exportCsv.html','manage_secretbox_searchform','资产列表')"><i class="fa fa-file-text-o fa-fw fa-col"></i>CSV</div>
            </div>
            <a id="manage_secretbox_toolbar_import" href="#" data-options="disabled:true" class="easyui-linkbutton" plain="true" onclick="$.acooly.secretbox.import();"><i class="fa fa-cloud-upload fa-fw fa-col"></i>批量导入</a>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.initPage('manage_secretbox_searchform', 'manage_secretbox_datagrid');
            $.acooly.secretbox.tree.load();
        });
    </script>
</div>
