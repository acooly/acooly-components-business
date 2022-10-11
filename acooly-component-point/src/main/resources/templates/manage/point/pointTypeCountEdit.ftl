<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_pointTypeCount_editform" action="${pageContext.request.contextPath}/manage/point/pointTypeCount/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
      <@jodd.form bean="pointTypeCount" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">用户名：</th>
				<td><input type="text" name="userName" size="48" placeholder="请输入用户名..." class="easyui-validatebox text" data-options="validType:['length[1,32]'],required:true"/></td>
			</tr>					
			<tr>
				<th>统计次数：</th>
				<td><input type="text" name="num" size="48" placeholder="请输入统计次数..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>业务类型：</th>
				<td><input type="text" name="busiType" size="48" placeholder="请输入业务类型..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>业务类型描述：</th>
				<td><input type="text" name="busiTypeText" size="48" placeholder="请输入业务类型描述..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>总${pointModuleName}：</th>
				<td><input type="text" name="totalPoint" size="48" placeholder="请输入总${pointModuleName}..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]'],required:true"/></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入备注..." style="width:300px;" name="comments" class="easyui-validatebox" data-options="validType:['length[1,256]']"></textarea></td>
			</tr>					
        </table>
      </@jodd.form>
    </form>
</div>
