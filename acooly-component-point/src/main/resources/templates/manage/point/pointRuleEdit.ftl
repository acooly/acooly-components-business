<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_pointRule_editform" action="/manage/point/pointRule/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
		<@jodd.form bean="pointRule" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">规则标题：</th>
				<td><input type="text" name="title" placeholder="请输入规则标题..." class="easyui-validatebox"  required="true" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			
              <tr>
                <th>业务类型：</th>
                <td>
	                <select name="busiTypeCode" editable="false" panelHeight="auto" class="easyui-combobox"  style="width:150px">
	                	<#list allBusiTypeEnumss as k,v><option value="${k}">${v}</option></#list>
	                </select>
                </td>
            </tr>
            
            <tr>
                <th>有效期类型：</th>
                <td>
	                <select name="validType" editable="false" panelHeight="auto" class="easyui-combobox" style="width:150px">
	                	<#list allValidTypes as k,v>
	                		<option value="${k}"  <#if (k==pointRule.validType.code())>selected </#if>>${v}</option>
	                	</#list>
	                </select>
                </td>
            </tr>					
            										
            <tr>
				<th>配置方案提示：</th>
				<td>
					 1.交易金额 仅支持整数,不支持小数,如无交易金额场景（签到），交易金额设置为0 <br/>
					 2.例如配置：交易金额：1000，${pointModuleName}：200个; <br/>
					   业务真实交易金额4050元，用户获取${pointModuleName}=4050/1000*200=800 <br/>
					 1000元交易金额可获取200积分 
				</td>
			</tr>																				
			
			<tr>
				<th>交易金额：</th>
				<td><input type="text" name="amount" placeholder="请输入金额..." class="easyui-numberbox" style="height: 30px;width: 260px;line-height: 1.3em;"  required="true"  data-options="validType:['number[0,2147483646]']"/></td>
			</tr>
					
			<tr>
				<th>获取${pointModuleName}：</th>
				<td><input type="text" name="point" placeholder="请输入${pointModuleName}..." class="easyui-numberbox" style="height: 30px;width: 260px;line-height: 1.3em;"  required="true" data-options="validType:['number[0,2147483646]']"/></td>
			</tr>	
			
			
			<tr>
                <th>状态：</th>
                <td>
                <select name="status" editable="false" panelHeight="auto" class="easyui-combobox">
                	<#list allStatuss as k,v><option value="${k}">${v}</option></#list>
                </select>
                </td>
            </tr>
							
																			
			<tr>
				<th>规则描述：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入规则描述..." name="comments" class="easyui-validatebox" data-options="validType:['length[1,255]']"></textarea></td>
			</tr>					
        </table>
      </@jodd.form>
    </form>
</div>
