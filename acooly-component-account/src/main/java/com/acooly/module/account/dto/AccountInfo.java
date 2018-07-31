package com.acooly.module.account.dto;

import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.common.facade.InfoBase;
import com.acooly.core.utils.Strings;
import com.acooly.module.account.enums.AccountTypeEnum;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

/**
 * 账户信息
 *
 * @author zhangpu@acooly.cn
 * @date 2018-06-19 20:03
 */
@Setter
@Getter
public class AccountInfo extends InfoBase {
    /**
     * 账户ID
     */
    private Long accountId;

    /**
     * 账户编码
     */
    @Size(max = 64)
    private String accountNo;

    /**
     * 用户标志
     */
    private Long userId;

    /**
     * 用户编码
     */
    @Size(max = 64)
    private String userNo;

    @NonNull
    private AccountTypeEnum accountType = AccountTypeEnum.main;

    @Size(max = 32)
    private String username;

    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;


    public AccountInfo() {
    }


    /**
     * 用于定位账户 with Id
     *
     * @param accountId
     */
    public AccountInfo(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 用于定位账户 with No
     *
     * @param accountNo
     */
    public AccountInfo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * 用于创建默认账户
     *
     * <p>
     * accountId = userId
     * accountNo = userNo
     * accountType = main
     *
     * @param userId
     * @param userNo
     */
    public AccountInfo(Long userId, String userNo) {
        this(userId, userNo, null);
    }

    public AccountInfo(Long userId, String userNo, String username) {
        this.userId = userId;
        this.userNo = userNo;
        this.accountId = this.userId;
        this.accountNo = userNo;
        this.username = username;
    }


    /**
     * 用户创建账户：指定所有核心参数
     * <p>
     * 如果不传入accountId则系统生成
     * 如果不传入accountNo则系统生成
     *
     * @param accountId
     * @param accountNo
     * @param userId
     * @param userNo
     * @param accountType
     */
    public AccountInfo(@Nullable Long accountId, @Nullable String accountNo, Long userId, String userNo, AccountTypeEnum accountType) {
        this.accountId = accountId;
        this.accountNo = accountNo;
        this.userId = userId;
        this.userNo = userNo;
        this.accountType = accountType;
    }


    public String getLabel() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAccountId() != null) {
            sb.append("{ID:").append(getAccountId()).append(",");
        }
        if (Strings.isNotBlank(getAccountNo())) {
            sb.append("No:").append(getAccountNo()).append(",");
        }
        if (getAccountId() != null) {
            sb.append("{userId:").append(getUserId()).append(",");
        }
        sb.substring(0, sb.length() - 1);
        sb.append("}");
        return sb.toString();


    }

    @Override
    public void check() throws OrderCheckException {

        if (this.accountId != null) {
            return;
        }
        if (!Strings.isBlank(accountNo)) {
            return;
        }

        if (userId != null && accountType != null) {
            return;
        }
        throw new OrderCheckException("Account唯一标志不能为空", "accountId,accountNo或(userId+accountType)字少一个不能为空");
    }
}
