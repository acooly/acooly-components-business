
<div style="padding: 5px;font-family:微软雅黑;">
    <table class="tableForm" width="100%">
        <tr>
            <th>ID:</th>
            <td>${pointAccount.id}</td>
        </tr>
        <tr>
            <th width="25%">用户名:</th>
            <td>${pointAccount.userName}</td>
        </tr>
        <tr>
            <th>${pointModuleName}余额:</th>
            <td>${pointAccount.balance}</td>
        </tr>
        <tr>
            <th>冻结:</th>
            <td>${pointAccount.freeze}</td>
        </tr>
        <tr>
            <th>状态:</th>
            <td>${pointAccount.status}</td>
        </tr>
<#--        <tr>-->
<#--            <th>用户等级:</th>-->
<#--            <td><img src="${pointAccount.gradePicture}" width="50px" height="50px"></td>-->
<#--        </tr>-->
        <tr>
            <th>创建时间:</th>
            <td>${pointAccount.createTime?string("yyyy-MM-dd HH:mm:ss")} </td>
        </tr>
        <tr>
            <th>修改时间:</th>
            <td>${pointAccount.updateTime?string("yyyy-MM-dd HH:mm:ss")} </td>
        </tr>
        <tr>
            <th>备注:</th>
            <td>${pointAccount.comments}</td>
        </tr>
    </table>
</div>
