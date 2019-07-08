<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_countNum_editform" action="/manage/component/countnum/countNum/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
		<@jodd.form bean="countNum" scope="request">
        <input name="id" type="hidden" />
        <table class="editForm" width="100%">
			<tr>
				<th width="25%">名称：</th>
				<td><input type="text" name="title" placeholder="请输入名称..." class="easyui-validatebox" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>创建者id：</th>
				<td><input type="text" name="createUserId" placeholder="请输入创建者id..." class="easyui-numberbox" style="height: 30px;width: 260px;line-height: 1.3em;" data-options="validType:['number[0,2147483646]'],required:true"/></td>
			</tr>					
			<tr>
				<th>创建者用户名：</th>
				<td><input type="text" name="createUserName" placeholder="请输入创建者用户名..." class="easyui-validatebox" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>类型：</th>
				<td><select name="type" editable="false" panelHeight="auto" class="easyui-combobox" style="min-width: 235px;" >
					<#list allTypes as k,v><option value="${k}">${v}</option></#list>
				</select></td>
			</tr>					
			<tr>
				<th>状态：</th>
				<td><select name="status" editable="false" panelHeight="auto" class="easyui-combobox" style="min-width: 235px;" data-options="required:true">
					<#list allStatuss as k,v><option value="${k}">${v}</option></#list>
				</select></td>
			</tr>					
			<tr>
				<th>是否参与覆盖：</th>
				<td><select name="isCover" editable="false" panelHeight="auto" class="easyui-combobox" style="min-width: 235px;" >
					<#list allIsCovers as k,v><option value="${k}">${v}</option></#list>
				</select></td>
			</tr>					
			<tr>
				<th>最大参与人数：</th>
				<td><input type="text" name="maxNum" placeholder="请输入最大参与人数..." class="easyui-numberbox" style="height: 30px;width: 260px;line-height: 1.3em;" data-options="validType:['number[0,2147483646]']"/></td>
			</tr>					
			<tr>
				<th>可参与次数：</th>
				<td><input type="text" name="limitNum" placeholder="请输入可参与次数..." class="easyui-numberbox" style="height: 30px;width: 260px;line-height: 1.3em;" data-options="validType:['number[0,2147483646]']"/></td>
			</tr>					
			<tr>
				<th>业务id：</th>
				<td><input type="text" name="businessId" placeholder="请输入业务id..." class="easyui-validatebox" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>业务参数：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入业务参数..." name="businessData" class="easyui-validatebox" data-options="validType:['length[1,255]']"></textarea></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" placeholder="请输入备注..." class="easyui-validatebox" data-options="validType:['length[1,64]']"/></td>
			</tr>					
        </table>
      </@jodd.form>
    </form>
</div>
