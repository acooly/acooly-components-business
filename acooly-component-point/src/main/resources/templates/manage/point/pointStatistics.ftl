<#if ssoEnable><#include "/manage/common/ssoInclude.ftl"></#if>

<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_pointStatistics_searchform', 'manage_pointStatistics_datagrid');
    });
    
    $('#manage_pointStatistics_datagrid').datagrid({
    	showFooter:true,
    	onLoadSuccess : function() {
    			$('#manage_pointStatistics_datagrid').datagrid('statistics');
    		}
    });
    

    //手动(统计+清零)
    function pointByCountAndClear(){
    	 $.acooly.framework.confirmSubmit('/manage/point/pointStatistics/pointByCountAndClear.html', 1, 'manage_pointStatistics_datagrid',
    		        '警告：正在操作 《手动(统计+清零)》 ',
    		        '<p style="color:red"> 1.统计+清零的时间默认为前一天  <br/> 2. 统计+清零 不可恢复,请小心操作<p>');
    }
    
    
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_pointStatistics_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
          <tr>
          <td align="left">
          <div>
                            用户名: <input type="text" class="text" size="15" name="search_EQ_userName"/>
                            统计时间: <input size="15" class="text" id="search_GTE_startTime" name="search_GTE_startTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                            状态:
              <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox">
                    <option value="">所有</option>
                  <#list allStatuss as k,v><option value="${k}">${v}</option></#list>
               </select>
           <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_pointStatistics_searchform','manage_pointStatistics_datagrid');">
           <i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          </div>
         </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_pointStatistics_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/point/pointStatistics/listJson.html" toolbar="#manage_pointStatistics_toolbar"
               fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="50" pageList="[ 50, 100, 150, 200 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">ID</th>
                <th field="userNo">用户号</th>
                <th field="userName">用户名</th>
                <th field="overdueDate">${pointModuleName}过期时间</th>
                
                <th field="num" sum="true">统计条数</th>
                <th field="totalClearPoint" sum="true">【预计清零${pointModuleName}</th>

                <th field="currentCountOverduePoint" sum="true">+清零前过期总额(累计)</th>
                <th field="currentTotalExpensePoint" sum="true">-清零前账户消费总额</th>
                <th field="currentTotalOverduePoint" sum="true">-清零前过期总额<br/>(执行清零)</th>
                <th field="currentPoint" sum="true">-(清零前总${pointModuleName}</th>
                <th field="currentFreezePoint" sum="true">-清零前冻结总额)</th>
                
                <th field="realClearPoint" sum="true">=本次清零${pointModuleName}】</th>

                
                <th field="endBalancePoint" sum="true">清零后总${pointModuleName}</th>
                <th field="endTotalOverduePoint" sum="true">清零后过期总${pointModuleName}<br/>(执行清零)</th>
                
                <th field="status"data-options="formatter:function(value){ return formatRefrence('manage_pointStatistics_datagrid','allStatuss',value);} ">状态</th>
                <th field="createTime">创建时间</th>
                <th field="updateTime">修改时间</th>
                <th field="comments">备注</th>
<!--                 <th field="rowActions" -->
<!--                     data-options="formatter:function(value, row, index){return formatAction('manage_pointStatistics_action',value,row)}">动作 -->
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_pointStatistics_action" style="display: none;">
            <!--       <a onclick="$.acooly.framework.edit({url:'/manage/point/pointStatistics/edit.html',id:'{0}',entity:'pointStatistics',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a> -->
            <!--       <a onclick="$.acooly.framework.show('/manage/point/pointStatistics/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a> -->
            <!--       <a onclick="$.acooly.framework.remove('/manage/point/pointStatistics/deleteJson.html','{0}','manage_pointStatistics_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a> -->
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_pointStatistics_toolbar">
        
         <shiro:hasPermission name="point:pointByCountAndClear">  
	         <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/point/pointStatistics/clear.html?doType=clearCountByOverdueDate',entity:'pointStatistics',width:650,height:550,addButton:'执行统计${pointModuleName}',reload:true})">
	         <i class="fa fa-plus-circle fa-lg fa-location-arrow fa-col"></i>手动统计 ${pointModuleName}</a>
	         
	         <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/point/pointStatistics/clear.html?doType=clearByOverdueDate',entity:'pointStatistics',width:650,height:550,addButton:'执行清零${pointModuleName}',reload:true})">
	         <i class="fa fa-plus-circle fa-lg fa-location-arrow fa-col"></i>手动清零 ${pointModuleName}</a>
	         
	         <a href="#" class="easyui-linkbutton" plain="true" onclick="pointByCountAndClear()"> <i class="fa fa-plus-circle fa-lg fa-location-arrow fa-col"></i>手动(统计+清零) ${pointModuleName}</a>
         </shiro:hasPermission>
         
         
                                       清零规则:<a id="point_clear_tip" href="javascript:void(0)" title="" class="tooltip-f"><i class="fa fa-question-circle-o fa-lg fa-fw fa-col"></i></a>
         
        
			<!--	   <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/point/pointStatistics/create.html',entity:'pointStatistics',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a> -->
            <!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/point/pointStatistics/deleteJson.html','manage_pointStatistics_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a> -->
            <!--       <a href="#" class="easyui-menubutton" data-options="menu:'#manage_pointStatistics_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a> -->
            <!--       <div id="manage_pointStatistics_exports_menu" style="width:150px;"> -->
            <!--         <div onclick="$.acooly.framework.exports('/manage/point/pointStatistics/exportXls.html','manage_pointStatistics_searchform','${pointModuleName}统计')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div> -->
            <!--         <div onclick="$.acooly.framework.exports('/manage/point/pointStatistics/exportCsv.html','manage_pointStatistics_searchform','${pointModuleName}统计')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div> -->
            <!--       </div> -->
            <!--       <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/point/pointStatistics/importView.html',uploader:'manage_pointStatistics_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a> -->

        </div>
    </div>

</div>


<script type="text/javascript">
    $('#point_clear_tip').tooltip({
        position: 'right',
        content: ' 规则:<br/>A：本次清零${pointModuleName} > 有效${pointModuleName} ==>: 清零[有效${pointModuleName}]；<br/>B:本次清零云豆< 有效${pointModuleName} ==>清零[本次清零${pointModuleName}] <br/> 1:【预计清零${pointModuleName} +清零前过期总额(累计) -清零前过期总额(执行清零)-清零前账户消费总额	 -(清零前总云豆	 -清零前冻结总额) =本次清零云豆】 <br/> '+
				 ' 2：预计清零${pointModuleName}：根据过期时间统计值，不含消费的${pointModuleName} <br/>'+
       			 ' 3：清零前过期总额(累计)：每一次执行清零逻辑时，预计清零${pointModuleName} 累计值 <br/>'+
       			 ' 4：清零前过期总额(执行清零)：每一次执行清零逻辑时，真实清零的${pointModuleName} 累计值 <br/>'+
        		 ' 5：定时清零方案，请参考：README.md ',
        onShow: function(){
            $(this).tooltip('tip').css({
                backgroundColor: '#666',
                borderColor: '#666'
            });
        }
    });

</script>