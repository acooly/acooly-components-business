<script type="text/javascript">
    $(function () {
        $.acooly.framework.createUploadify({
            /** 上传导入的URL */
            url: '/manage/point/pointAccount/importJson.html?_csrf=${_csrf.token}&splitKey=v',
            /** 导入操作消息容器 */
            messager: 'manage_pointAccount_import_uploader_message',
            /** 上传导入文件表单ID */
            uploader: 'manage_pointAccount_import_uploader_file',
            jsessionid:'${Session.id}'
        });
    });

    function onUploadSuccess(file, data, response) {
        var result = $.parseJSON(data);
        if (result.success) {
            $('#manage_pointAccount_import_uploader_message').html(result.message);
        } else {
            $('#manage_pointAccount_import_uploader_message').html('导入失败:' + result.message);
        }
    }
</script>
<div align="center">
    <table class="tableForm" width="100%">
        <tr>
            <th width="30%">操作说明：</th>
            <td>
                1: 手动发放的${pointModuleName}数量，不会使用${pointModuleName}规则，仅处理 ${pointModuleName}的数量<br/><br/>
                2：业务类型对应关系：<br/>
                <#list allBusiTypeEnumss?keys as key>
                    ${key} ${allBusiTypeEnumss[key]}】；
                </#list>
                <br/><br/>
                3：电子表格依次排列<span style="color: red">【用户号；用户名；发放${pointModuleName}数量；业务类型；业务类型描述；${pointModuleName}到期时间；备注】</span>
                		&nbsp;&nbsp;&nbsp; 请<a href="/file/pointTemplate.xls">下载模板文件</a><br/><br/>
                4:例子：<br/><img src="/assets/images/model/files01.png"  alt="上传文件示例" height="120px;" width="550px;"/>
                
            </td>
        </tr>
        <tr>
            <th width="30%" height="15"></th>
            <td>
                <div id="manage_pointAccount_import_uploader_message" style="color: red;"></div>
            </td>
        </tr>
        <tr>
            <th width="30%">文件：</th>
            <td>
                <input type="file" name="manage_pointAccount_import_uploader_file" id="manage_pointAccount_import_uploader_file"/>
            </td>
        </tr>
    </table>
</div>
