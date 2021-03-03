<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_account_editform" class="form-horizontal" action="/manage/component/account/account/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
        <@jodd.form bean="account" scope="request">
            <input name="id" type="hidden"/>
            <div class="card-body">
                <#if action=='edit'>
                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label">账户编码</label>
                        <div class="col-sm-9">${account.accountNo}</div>
                    </div>
                </#if>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">账户类型</label>
                    <div class="col-sm-9">
                        <select name="accountType" class="form-control input-sm select2bs4">
                            <#list allAccountTypes as k,v>
                                <option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">用户ID</label>
                    <div class="col-sm-9">
                        <#if action='edit'>
                            ${account.userId}
                        <#else>
                            <input type="text" name="userId" size="48" placeholder="请输入用户ID..." class="easyui-validatebox form-control" data-inputmask="'alias':'integer'" data-mask data-options="validType:['number[1,20]'],required:true"/>
                        </#if>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">用户编码</label>
                    <div class="col-sm-9">
                        <#if action='edit'>
                            ${account.userNo}
                        <#else>
                            <input type="text" name="userNo" size="48" placeholder="请输出用户编码..." class="easyui-validatebox form-control" data-options="validType:['length[1,48]']"/>
                        </#if>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">用户名</label>
                    <div class="col-sm-9">
                        <input type="text" name="username" size="48" placeholder="请输入用户名..." class="easyui-validatebox form-control" data-options="validType:['length[6,64]']"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">账户类型</label>
                    <div class="col-sm-9">
                        <select name="status" class="form-control input-sm select2bs4">
                            <#list allStatuss as k,v>
                                <option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">备注</label>
                    <div class="col-sm-9">
                        <input type="text" name="comments" size="48" placeholder="请输入备注..." class="easyui-validatebox form-control" data-options="validType:['length[1,128]']"/>
                    </div>
                </div>
            </div>
        </@jodd.form>
    </form>
</div>
