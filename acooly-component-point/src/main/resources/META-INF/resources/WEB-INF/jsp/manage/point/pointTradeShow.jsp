<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div style="padding: 5px;font-family:微软雅黑;">
    <table class="tableForm" width="100%">
        <tr>
            <th>id:</th>
            <td>${pointTrade.id}</td>
        </tr>
        <tr>
            <th width="25%">交易订单号:</th>
            <td>${pointTrade.tradeNo}</td>
        </tr>
        <tr>
            <th>交易类型:</th>
            <td>${pointTrade.tradeType.message}</td>
        </tr>
        <tr>
            <th>用户名:</th>
            <td>${pointTrade.userName}</td>
        </tr>
        <tr>
            <th>${pointModuleName}账户ID:</th>
            <td>${pointTrade.accountId}</td>
        </tr>
        <tr>
            <th>交易${pointModuleName}:</th>
            <td>${pointTrade.amount}</td>
        </tr>
        <tr>
            <th>交易后冻结${pointModuleName}:</th>
            <td>${pointTrade.endFreeze}</td>
        </tr>
        <tr>
            <th>交易后${pointModuleName}:</th>
            <td>${pointTrade.endBalance}</td>
        </tr>
        <tr>
            <th>交易后有效${pointModuleName}:</th>
            <td>${pointTrade.endAvailable}</td>
        </tr>
        <tr>
            <th>业务Id:</th>
            <td>${pointTrade.busiId}</td>
        </tr>
        <tr>
            <th>业务类型:</th>
            <td>${pointTrade.busiType}</td>
        </tr>
        <tr>
            <th>业务类型描述:</th>
            <td>${pointTrade.busiTypeText}</td>
        </tr>
        <tr>
            <th>相关业务数据:</th>
            <td>${pointTrade.busiData}</td>
        </tr>
        <tr>
            <th>创建时间:</th>
            <td><fmt:formatDate value="${pointTrade.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>修改时间:</th>
            <td><fmt:formatDate value="${pointTrade.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>备注:</th>
            <td>${pointTrade.comments}</td>
        </tr>
    </table>
</div>
