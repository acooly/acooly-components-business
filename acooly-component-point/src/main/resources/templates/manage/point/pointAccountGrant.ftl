<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_pointAccount_editform" action="${pageContext.request.contextPath}/manage/point/pointAccount/grantJson.html"
          method="post">
        <table class="tableForm" width="100%">
            <tr>
                <th width="25%">用户号：</th>
                <td>
                    <textarea rows="5" cols="40" style="width:300px;" name="userNos" data-options="required:true" class="easyui-validatebox" validType="byteLength[1,256]"></textarea>
                    </br>
                    <span style="color: red;">多个用户名使用英文逗号分隔</span>
                </td>
            </tr>
            <tr>
                <th>发放${pointModuleName}：</th>
                <td>
                    <input type="text" name="point" size="23" class="easyui-numberbox text" data-options="required:true"
                           validType="byteLength[1,19]"/>
                    <br/>
                    每一个用户发放数量
                </td>
            </tr>
            
			<tr>
                <th>业务类型：</th>
                <td>
            	<select name="busiType" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox"
                                data-options="required:true">
                    <#list allBusiTypeEnumss as k,v><option value="${k}">${v}</option></#list>
                    </select>
            	</td>
            </tr>
            
            
             <tr>
                <th>${pointModuleName}过期时间：</th>
                <td>
              	  <input size="15" class="text" id="overdueDate" name="overdueDate"  onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
				</td>
            </tr>
            
            
            
            <tr>
                <th>备注：</th>
                <td><input type="text" name="comments" size="23" class="easyui-validatebox text" data-options="required:true"
                           validType="byteLength[1,19]"/></td>
            </tr>
        </table>
    </form>
</div>
