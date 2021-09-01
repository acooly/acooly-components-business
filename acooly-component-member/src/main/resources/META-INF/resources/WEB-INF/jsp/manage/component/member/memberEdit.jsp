<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div>
    <form id="manage_member_editform" action="/manage/component/member/member/${action=='create'?'saveJson':'updateJson'}.html" method="post">
        <jodd:form bean="member" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <c:if test="${action == 'create'}">
                    <tr>
                        <th width="25%">会员编码：</th>
                        <td>
                            <input type="text" name="userNo" size="48" placeholder="请输入会员编码，不填则自动生成..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/>
                        </td>
                    </tr>
                    <tr>
                        <th>用户名：</th>
                        <td><input type="text" name="username" size="48" placeholder="请输入用户名..." class="easyui-validatebox text" data-options="validType:['length[1,32]'],required:true"/></td>
                    </tr>
                    <tr>
                        <th>初始密码：</th>
                        <td><input type="text" name="password" size="48" placeholder="请输入初始密码,如不设置则自动生成随机密码..." class="easyui-passwordbox text" data-options="validType:['length[1,32]']"/></td>
                    </tr>
                    <tr>
                        <th>会员类型</th>
                        <td><select name="memberUserType" id="manage_member_editform_userType" editable="false" style="height:27px;width:200px;" panelHeight="auto" class="easyui-combobox"
                                    data-options="required:true">
                            <c:forEach items="${allUserTypes}" var="e">
                                <option value="${e.key}">${e.value}</option>
                            </c:forEach>
                        </select> <span class="acooly-comment">个人/个体可填写身份证认证</span>
                        </td>
                    </tr>
                    <tr>
                        <th>激活方式</th>
                        <td><select name="memberActiveType" id="manage_member_editform_activeType" editable="false" style="height:27px;min-width:200px;" panelHeight="auto" class="easyui-combobox"
                                    data-options="required:true">
                            <c:forEach items="${allActiveTypes}" var="e">
                                <option value="${e.key}">${e.value}</option>
                            </c:forEach>
                        </select> <span class="acooly-comment" id="manage_member_editform_activeType_comment"></span></td>
                    </tr>
                    <tr>
                        <th>手机号码：</th>
                        <td><input type="text" name="mobileNo" size="48" placeholder="请输入手机号码..." class="easyui-validatebox text" data-options="validType:['length[1,16]']"/></td>
                    </tr>
                    <tr>
                        <th>邮件：</th>
                        <td><input type="text" name="email" size="48" placeholder="请输入邮件..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
                    </tr>

                    <tr class="manage-member-personal-info">
                        <th>真名：</th>
                        <td><input type="text" name="realName" size="48" placeholder="请输入姓名或企业名称..." class="easyui-validatebox text" data-options="validType:['length[1,16]']"/></td>
                    </tr>
                    <tr class="manage-member-personal-info">
                        <th>证件号码：</th>
                        <td><input type="text" name="idCardNo" size="48" placeholder="证件号码[身份证/社会统一信用代码]..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
                    </tr>
                </c:if>


                <c:if test="${member != null && member.parentid != null}">
                    <tr>
                        <th width="25%">父会员：</th>
                        <td>${member.parentid} / ${member.parentUserNo}</td>
                    </tr>
                </c:if>
                <c:if test="${action != 'create'}">
                    <tr>
                        <th>用户编码：</th>
                        <td>${member.userNo}</td>
                    </tr>
                    <tr>
                        <th>用户名：</th>
                        <td>${member.username}</td>
                    </tr>
                    <tr>
                        <th>用户类型：</th>
                        <td>${member.userType.message}</td>
                    </tr>
                    <tr>
                        <th>手机号码：</th>
                        <td>${member.mobileNo}</td>
                    </tr>
                    <tr>
                        <th>邮件：</th>
                        <td>${member.email}</td>
                    </tr>
                    <tr>
                        <th>姓名：</th>
                        <td>${member.realName}</td>
                    </tr>
                    <tr>
                        <th>身份证号码：</th>
                        <td>${member.certNoMask}</td>
                    </tr>
                    <tr>
                        <th>状态：</th>
                        <td><select name="status" editable="false" style="height:27px;min-width:200px;" panelHeight="auto" class="easyui-combobox">
                            <c:forEach items="${allStatuss}" var="e">
                                <option value="${e.key}">${e.value}</option>
                            </c:forEach>
                        </select></td>
                    </tr>
                </c:if>
                <tr>
                    <th>业务分类：</th>
                    <td><select name="busiType" editable="false" style="height:27px;min-width:200px;" panelHeight="auto" class="easyui-combobox">
                        <c:forEach items="${allBusiTypes}" var="e">
                            <option value="${e.key}">${e.value}</option>
                        </c:forEach>
                    </select></td>
                </tr>

                <tr>
                    <th>经纪人：</th>
                    <td><input type="text" name="broker" size="48" placeholder="请输入经纪人..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
                </tr>
                <tr>
                    <th>介绍人：</th>
                    <td><input type="text" name="inviter" size="48" placeholder="请输入介绍人..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
                </tr>
                <tr>
                    <th>注册来源：</th>
                    <td><input type="text" name="registrySource" size="48" placeholder="注册来源为集成端自定义..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
                </tr>
                <tr>
                    <th>用户等级：</th>
                    <td><select name="grade" editable="false" style="height:27px;min-width:200px;" panelHeight="auto" class="easyui-combobox">
                        <c:forEach items="${allGrades}" var="e">
                            <option value="${e.key}">${e.value}</option>
                        </c:forEach>
                    </select></td>
                </tr>

                <tr>
                    <th>备注：</th>
                    <td><input type="text" name="comments" size="48" placeholder="请输入备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
                </tr>
            </table>
        </jodd:form>
    </form>
    <script>

        function manage_member_editform_init_userType(n) {
            if (n != null && n != "enterprise") {
                $('.manage-member-personal-info').show();
            } else {
                $('.manage-member-personal-info').hide();
            }
        }

        function manage_member_editform_init_activeType(n) {
            var memo = "";
            if (n == 'human') memo = '不激活，待后续用户激活';
            if (n == 'auto') memo = '注册完成后自动激活';
            if (n == 'mobileNo') memo = '注册完成自动发送手机验证码';
            if (n == 'email') memo = '注册完成自动发送邮件验证码';
            console.info(memo);
            $('#manage_member_editform_activeType_comment').html(memo);
        }


        /**
         * 扩展验证
         */
        function manage_member_editform_onSubmit() {
            return true;
        }


        $(function () {
            $("#manage_member_editform_userType").combobox({
                onChange: function (n, o) {
                    manage_member_editform_init_userType(n);
                }
            });

            $("#manage_member_editform_activeType").combobox({
                onChange: function (n, o) {
                    manage_member_editform_init_activeType(n)
                }
            });

            manage_member_editform_init_userType($('#manage_member_editform_userType').val());
            manage_member_editform_init_activeType($('#manage_member_editform_activeType').val());

        });


    </script>
</div>
