<div>
    <form id="manage_pointStatistics_editform" action="${pageContext.request.contextPath}/manage/point/pointStatistics/${doType}.html"  method="post">
        <table class="tableForm" width="100%">
            <tr>
                <th>提示：</th>
                <td>
                <p>1.${pointModuleName}清零不可恢复,请小心操作</p>
                <p>2.${pointModuleName}清零前，请先操作《手动统计 ${pointModuleName}》</p>
                <p>3.完成 ${pointModuleName}清零前，再次提交《手动清零 ${pointModuleName}》操作</p>
                <p>4.清零时间必须小于当前系统时间<br/>（例如：如清零2022-12-01的${pointModuleName},清零时间：2022-12-01；<br/>系统可以执行的时间需要大于2022-12-01 ）</p>
                </td>
            </tr>
            
            <tr>
                <th>清零${pointModuleName}时间：</th>
                <td><input type="text" name="overdueDate" size="20" class="easyui-validatebox text" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" data-options="required:true"/></td>
            </tr>
        </table>
    </form>
</div>
