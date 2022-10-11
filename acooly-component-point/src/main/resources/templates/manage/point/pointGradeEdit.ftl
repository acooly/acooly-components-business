<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_pointGrade_editform"
          action="${pageContext.request.contextPath}/manage/point/pointGrade/<#if action=='create'>saveJson<#else>updateJson</#if>.html"
          method="post" enctype="multipart/form-data">
        <@jodd.form bean="pointGrade" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th width="35%">等级：</th>
                    <td><input type="text" name="num" size="48" class="easyui-numberbox text" data-options="required:true"
                               validType="byteLength[1,10]"/></td>
                </tr>
                <tr>
                    <th>标题：</th>
                    <td><input type="text" name="title" size="48" class="easyui-validatebox text" data-options="required:true"
                               validType="byteLength[1,63]"/></td>
                </tr>
                <tr>
                    <th width="35%">${pointModuleName}区间_开始：</th>
                    <td><input type="text" name="startPoint" size="40" class="easyui-numberbox text" data-options="required:true"
                               validType="byteLength[1,10]"/></td>
                </tr>
                <tr>
                    <th width="35%">${pointModuleName}区间_结束：</th>
                    <td><input type="text" name="endPoint" size="40" class="easyui-numberbox text" data-options="required:true"
                               validType="byteLength[1,10]"/></td>
                </tr>
      			<tr>
      				<th> 图标：</th>
      				<td>
      				<input type="file" name="pictureFile" />

<#--                        <#if action=='create'>saveJson<#else>updateJson</#if>-->

      				<#if (pointGrade.picture)??>
      				<br/>
      				<a class="manage_onlineFile_datagrid_Thumbnail" target="_blank"  href="' + value + '"><img src="${pointGrade.picture}" width="120" height="100""/></a>
                    </#if>
      				</td>
<!--       				<textarea rows="3" cols="40" style="width:300px;" name="picture" class="easyui-validatebox"  validType="byteLength[1,255]"></textarea> -->
      			</tr>					
                <tr>
                    <th>备注：</th>
                    <td><textarea rows="3" cols="40" style="width:300px;" name="comments" class="easyui-validatebox"
                                  validType="byteLength[1,255]"></textarea></td>
                </tr>
            </table>
        </@jodd.form>
    </form>
</div>
