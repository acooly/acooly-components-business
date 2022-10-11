<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>
<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_pointAccount_searchform', 'manage_pointAccount_datagrid');
    });

    
    $('#manage_pointAccount_datagrid').datagrid({
    	showFooter:true,
    	onLoadSuccess : function() {
    			$('#manage_pointAccount_datagrid').datagrid('statistics');
    		}
    });

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_pointAccount_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        <div>
                            用户号: <input type="text" class="text" size="15" name="search_EQ_userNo"/>
                            用户名: <input type="text" class="text" size="15" name="search_EQ_userName"/>
                            状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><#list allStatuss as k,v><option value="${k}">${v}</option></#list></select>
                            用户等级: <select style="width:80px;height:27px;" name="search_EQ_gradeId" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><#list allPointGrades as k,v><option value="${k}">${v}</option></#list></select>
                            创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime"
                                         onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                            至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime"
                                    onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
          
          <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false"
                               onclick="$.acooly.framework.search('manage_pointAccount_searchform','manage_pointAccount_datagrid');"><i
                                    class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_pointAccount_datagrid" class="easyui-datagrid"  url="${pageContext.request.contextPath}/manage/point/pointAccount/listJson.html" toolbar="#manage_pointAccount_toolbar"
               fit="true" border="false" fitColumns="false" pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">ID</th>
                <th field="userNo">用户号</th>
                <th field="userName">用户名</th>
                <th field="balance" sum="true"  sortable="true">${pointModuleName}总额</th>
                <th field="freeze" sum="true"  sortable="true">冻结${pointModuleName}</th>
                <th field="available" sum="true" >有效${pointModuleName}</th>
                <th field="debtPoint" sum="true"  sortable="true">负债${pointModuleName}</th>
                <th field="totalExpensePoint" sum="true"  sortable="true">总消费${pointModuleName}</th>
                <th field="totalOverduePoint" sum="true"  sortable="true">总过期${pointModuleName}<br/>(已执行清零)</th>
                <th field="countOverduePoint" sum="true"  sortable="true">总过期${pointModuleName}<br/>(累加统计值)</th>
                <th field="totalProducePoint" sum="true"  sortable="true">总产生${pointModuleName}</th>
                <th field="status" data-options="formatter:function(value){ return formatRefrence('manage_pointAccount_datagrid','allStatuss',value);} ">状态 </th>
                <th field="gradeId" data-options="formatter:function(value){ return formatRefrence('manage_pointAccount_datagrid','allPointGrades',value);} "> 用户等级 </th>
                <th field="createTime" formatter="formatDate">创建时间</th>
                <th field="updateTime" formatter="formatDate">修改时间</th>
                <th field="comments">备注</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_pointAccount_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_pointAccount_action" style="display: none;">
            <a onclick="$.acooly.framework.edit({url:'/manage/point/pointAccount/edit.html',id:'{0}',entity:'pointAccount',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
            
            <a onclick="$.acooly.framework.show('/manage/point/pointAccount/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <!--       <a onclick="$.acooly.framework.remove('/manage/point/pointAccount/deleteJson.html','{0}','manage_pointAccount_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a> -->
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_pointAccount_toolbar">
            <!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/point/pointAccount/create.html',entity:'pointAccount',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a> -->
            <!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/point/pointAccount/deleteJson.html','manage_pointAccount_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a> -->
            <!--       <a href="#" class="easyui-menubutton" data-options="menu:'#manage_pointAccount_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a> -->
            <!--       <div id="manage_pointAccount_exports_menu" style="width:150px;"> -->
            <!--         <div onclick="$.acooly.framework.exports('/manage/point/pointAccount/exportXls.html','manage_pointAccount_searchform','${pointModuleName}账户')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div> -->
            <!--         <div onclick="$.acooly.framework.exports('/manage/point/pointAccount/exportCsv.html','manage_pointAccount_searchform','${pointModuleName}账户')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div> -->
            <!--       </div> -->
            
           <shiro:hasPermission name="point:pointAccount_grant">  
           	 	<a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/point/pointAccount/grant.html',entity:'pointAccount',width:650,height:550,addButton:'${pointModuleName}发放',reload:true})">
            	<i class="fa fa-plus-circle fa-lg fa-location-arrow fa-col"></i>发放${pointModuleName}</a>
              
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/point/pointAccount/importView.html',uploader:'manage_pointAccount_import_uploader_file',width:700,height:550,});">
            <i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量发放${pointModuleName}</a>
           </shiro:hasPermission>
                             
                              账户规则:<a id="point_rule_tip" href="javascript:void(0)" title="" class="tooltip-f"><i class="fa fa-question-circle-o fa-lg fa-fw fa-col"></i></a>
            
            
            <!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/point/pointAccount/clear.html',entity:'pointAccount',width:500,height:400,addButton:'${pointModuleName}清零',reload:true})"><i class="fa fa-plus-circle fa-lg fa-arrow-up fa-col"></i>${pointModuleName}清零</a> -->
        </div>
    </div>

</div>

<script type="text/javascript">
    $('#point_rule_tip').tooltip({
        position: 'right',
        content: ' 1: ${pointModuleName}总额 + 总消费${pointModuleName} + 总过期${pointModuleName}(已执行清零)-负债${pointModuleName} = 总产生${pointModuleName}<br/>'+
        		 ' 2: 冻结${pointModuleName} + 有效${pointModuleName} = ${pointModuleName}总额<br/>'+
        		 ' 3: 总产生${pointModuleName} ：账户${pointModuleName}变动“产生”，持续累加值（及统计值），后期可以计算${pointModuleName}等级 <br/>'+
        		 ' 3: 总消费${pointModuleName} ：账户${pointModuleName}变动“消费，取消”，持续累加值（及统计值） <br/>'+
        		 ' 4: 总过期${pointModuleName} ：${pointModuleName}过期后，当期被扣除的${pointModuleName}的累加值 <br/>'+
        		 ' 5: 负债${pointModuleName} ：用户产生积分被消费后，被产生的积分被业务系统取消，业务平台已自损，标记负债${pointModuleName}后期产生的积分将被填补 <br/>',
        onShow: function(){
            $(this).tooltip('tip').css({
                backgroundColor: '#666',
                borderColor: '#666'
            });
        }
    });

</script>
