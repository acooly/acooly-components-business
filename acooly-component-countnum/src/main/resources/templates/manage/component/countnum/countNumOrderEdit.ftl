<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_countNumOrder_editform" action="/manage/component/countnum/countNumOrder/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
		<@jodd.form bean="countNumOrder" scope="request">
        <input name="id" type="hidden" />
        <table class="editForm" width="100%">
			<tr>
				<th width="25%">订单号：</th>
				<td><input type="text" name="orderNo" placeholder="请输入订单号..." class="easyui-validatebox" data-options="validType:['length[1,64]'],required:true"/></td>
			</tr>					
			<tr>
				<th>游戏id：</th>
				<td><input type="text" name="countId" placeholder="请输入游戏id..." class="easyui-numberbox" style="height: 30px;width: 260px;line-height: 1.3em;" data-options="validType:['number[0,2147483646]'],required:true"/></td>
			</tr>					
			<tr>
				<th>游戏名称：</th>
				<td><input type="text" name="countTitle" placeholder="请输入游戏名称..." class="easyui-validatebox" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>类型：</th>
				<td><select name="countType" editable="false" panelHeight="auto" class="easyui-combobox" style="min-width: 235px;" >
					<#list allCountTypes as k,v><option value="${k}">${v}</option></#list>
				</select></td>
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
				<th>用户id：</th>
				<td><input type="text" name="userId" placeholder="请输入用户id..." class="easyui-numberbox" style="height: 30px;width: 260px;line-height: 1.3em;" data-options="validType:['number[0,2147483646]'],required:true"/></td>
			</tr>					
			<tr>
				<th>用户名称：</th>
				<td><input type="text" name="userName" placeholder="请输入用户名称..." class="easyui-validatebox" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>是否第一名：</th>
				<td><input type="text" name="isFirst" placeholder="请输入是否第一名..." class="easyui-validatebox" data-options="validType:['length[1,16]']"/></td>
			</tr>					
			<tr>
				<th>有效值：</th>
				<td><input type="text" name="num" placeholder="请输入有效值..." class="easyui-numberbox" style="height: 30px;width: 260px;line-height: 1.3em;" data-options="validType:['number[0,2147483646]']"/></td>
			</tr>					
			<tr>
				<th>备注：</th>
				<td><input type="text" name="comments" placeholder="请输入备注..." class="easyui-validatebox" data-options="validType:['length[1,64]']"/></td>
			</tr>					
        </table>
      </@jodd.form>
    </form>
</div>
