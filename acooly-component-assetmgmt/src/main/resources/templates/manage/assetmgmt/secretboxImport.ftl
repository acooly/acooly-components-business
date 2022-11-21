<div>
    <div class="card-body">
        <div class="form-group row">
            <label class="col-sm-3 col-form-label">模版</label>
            <div class="col-sm-9 col-form-content">
                格式：Excel导入，<a target="_blank" href="/manage/assert/assetmgmt/资产列表_导入模板.xlsx">下载模板文件</a>。
            </div>
        </div>
        <#if typeCode??>
            <div class="form-group row">
                <label class="col-sm-3 col-form-label">分类</label>
                <div class="col-sm-9 col-form-content">${typeName}</div>
            </div>
        </#if>
        <div class="form-group row">
            <label class="col-sm-3 col-form-label"></label>
            <div class="col-sm-9 col-form-content">
                <div id="manage_secretbox_import_uploader_message" style="color: red;"></div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-3 col-form-label">文件</label>
            <div class="col-sm-9">
                <input type="file" name="manage_secretbox_import_uploader_file" id="manage_secretbox_import_uploader_file"/>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.createUploadify({
                /** 上传导入的URL */
                url: '/manage/assetmgmt/secretbox/importJson.html?_csrf=${_csrf.token}&splitKey=v',
                /** 导入操作消息容器 */
                messager: 'manage_secretbox_import_uploader_message',
                /** 上传导入文件表单ID */
                uploader: 'manage_secretbox_import_uploader_file',
                jsessionid: '${Session.id}',
                formData: {typeCode: '${typeCode}', typeName: '${typeName}', importIgnoreTitle: true}
            });
        });
    </script>
</div>
