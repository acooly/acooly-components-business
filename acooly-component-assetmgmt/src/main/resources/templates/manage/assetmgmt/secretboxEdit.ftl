<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_secretbox_editform" class="form-horizontal" action="/manage/assetmgmt/secretbox/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
        <@jodd.form bean="secretbox" scope="request">
            <input name="id" type="hidden"/>
            <div class="card-body">
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">分类</label>
                    <div class="col-sm-10">
                        <select name="typeCode" id="manage_secretbox_editform_typeCode" class="form-control select2bs4"></select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">编码</label>
                    <div class="col-sm-10">
                        <input type="text" name="code" placeholder="请输入唯一编码..." class="easyui-validatebox form-control" data-options="validType:['text','length[1,32]']"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">标题</label>
                    <div class="col-sm-10">
                        <input type="text" name="title" placeholder="请输入标题..." class="easyui-validatebox form-control" data-options="validType:['text','length[1,45]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">服务入口</label>
                    <div class="col-sm-3">
                        <select name="serviceType" class="form-control select2bs4">
                            <#list allServiceTypes as k,v>
                                <option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                    <div class="col-sm-7">
                        <input type="text" name="accessPoint" placeholder="请输入服务入口..." class="easyui-validatebox form-control" data-options="validType:['text','length[1,128]']"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">安全类型</label>
                    <div class="col-sm-10">
                        <select name="secretType" class="form-control select2bs4" data-options="required:true">
                            <#list allSecretTypes as k,v>
                                <option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">账号</label>
                    <div class="col-sm-10">
                        <div class="input-group">
                            <input type="text" id="manage_secretbox_editform_username" name="username" placeholder="请输入账号/访问码/用户标志..." class="easyui-validatebox form-control" data-options="validType:['text','length[1,128]']" required="true"/>
                            <div class="input-group-append">
                                <button type="button" id="manage_secretbox_username_clip_btn" onclick="$.acooly.secretbox.clip('#manage_secretbox_username_clip_btn','#manage_secretbox_editform_username','复制成功');" class="btn btn-sm btn-success"><i class="fa fa-clipboard" aria-hidden="true"></i> 复制</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">密码</label>
                    <div class="col-sm-10">
                        <div class="input-group">
                            <input type="text" id="manage_secretbox_editform_password" name="password" placeholder="请输入密码/安全码/秘钥..." class="easyui-validatebox form-control" data-options="validType:['text','length[1,128]']" required="true"/>
                            <div class="input-group-append">
                                <button type="button" id="manage_secretbox_password_clip_btn" onclick="$.acooly.secretbox.clip('#manage_secretbox_password_clip_btn','#manage_secretbox_editform_password','复制成功');" class="btn btn-sm btn-success"><i class="fa fa-clipboard" aria-hidden="true"></i> 复制</button>
                            </div>
                        </div>

                        <div class="form-group clearfix" style="margin: 1rem 0 0 0;">
                            <div class="d-inline">
                                长度:<input type="text" id="manage_secretbox_editform_password_length" value="16" class="easyui-validatebox form-control form-control-sm" style="width: 120px;height: 30px;display: inline-block; margin-top: -5px;"
                                          invalidMessage="密码长度范围：8-16" data-inputmask="'alias':'integer'" data-mask data-options="validType:['integer[8,16]']" required="true"/>
                            </div>
                            <div class="d-inline" style="margin-left: 5px;">强度:</div>
                            <div class="icheck-default d-inline">
                                <input type="radio" name="passwordStrength" value="simple" id="ps1">
                                <label for="ps1">低</label>
                            </div>
                            <div class="icheck-primary d-inline">
                                <input type="radio" name="passwordStrength" value="usually" id="ps2">
                                <label for="ps2">中</label>
                            </div>
                            <div class="icheck-success d-inline">
                                <input type="radio" name="passwordStrength" value="complex" checked id="ps3">
                                <label for="ps3">高</label>

                            </div>
                            <div class="d-inline" style="margin-left: 10px;">
                                <button type="button" style="margin-top: -5px;" onclick="$.acooly.secretbox.passwordGenerate()" class="btn btn-sm btn-success"><i class="fa fa-key" aria-hidden="true"></i> 生成密码</button>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">绑定手机</label>
                    <div class="col-sm-4">
                        <input type="text" name="bindMobileNo" placeholder="请输入绑定手机号码..." class="easyui-validatebox form-control" data-inputmask="'alias':'mobile'" data-mask data-options="validType:['mobileNo']"/>
                    </div>
                    <label class="col-sm-2 col-form-label">绑定邮箱</label>
                    <div class="col-sm-4">
                        <input type="text" name="bindEmail" placeholder="请输入绑定邮件地址..." class="easyui-validatebox form-control" data-inputmask="'alias':'email'" data-mask data-options="validType:['text','length[1,128]']"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">说明</label>
                    <div class="col-sm-10">
                        <textarea rows="3" cols="40" placeholder="请输入简介或说明..." name="subject" class="easyui-validatebox form-control form-words" data-words="255"></textarea>
                    </div>
                </div>
            </div>
        </@jodd.form>
    </form>
    <script>
        $(function () {
            $.acooly.secretbox.tree.initSelect("manage_secretbox_editform_typeCode", '${secretbox.typeCode}');
        });
    </script>
</div>
