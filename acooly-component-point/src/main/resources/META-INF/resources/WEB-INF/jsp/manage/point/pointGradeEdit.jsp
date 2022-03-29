<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div>
    <form id="manage_pointGrade_editform"
          action="${pageContext.request.contextPath}/manage/point/pointGrade/${action=='create'?'saveJson':'updateJson'}.html"
          method="post" enctype="multipart/form-data">
        <jodd:form bean="pointGrade" scope="request">
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
      				<c:if test="${pointGrade.picture!=''}">
      				<br/>
      				<a class="manage_onlineFile_datagrid_Thumbnail" target="_blank"  href="' + value + '"><img src="${pointGrade.picture}" width="120" height="100""/></a>
      				</c:if>
      				</td>
<!--       				<textarea rows="3" cols="40" style="width:300px;" name="picture" class="easyui-validatebox"  validType="byteLength[1,255]"></textarea> -->
      			</tr>					
                <tr>
                    <th>备注：</th>
                    <td><textarea rows="3" cols="40" style="width:300px;" name="comments" class="easyui-validatebox"
                                  validType="byteLength[1,255]"></textarea></td>
                </tr>
            </table>
        </jodd:form>
    </form>
</div>
