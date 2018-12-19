package com.acooly.module.account.dto;

import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.common.facade.DtoBase;
import com.acooly.core.utils.Strings;
import com.acooly.module.account.enums.AccountTypeEnum;
import lombok.Getter;
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
public class AccountInfo extends DtoBase {
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

    private String accountType = AccountTypeEnum.main.getCode();

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
     * 定位账户 with userNo+accountType
     *
     * @param userNo
     * @param accountType
     */
    public AccountInfo(String userNo, String accountType) {
        this.userNo = userNo;
        this.accountType = accountType;
    }

    /**
     * 快速创建会员子账户
     * <p>
     * accountNo != userNo, accountNo为空，则自动生成
     * accountType = main
     *
     * @param userId
     * @param userNo
     */
    public AccountInfo(Long userId, String userNo) {
        this(userId, userNo, null);
    }

    public AccountInfo(Long userId, String userNo, String username) {
        this(userNo, userId, userNo, username, AccountTypeEnum.main.code());
    }

    public AccountInfo(Long userId, String userNo, String username, String accountType) {
        this(userNo, userId, userNo, username, accountType);
    }

    /**
     * 用户创建账户：指定所有核心参数
     * <p>
     * 如果不传入accountNo则系统生成
     *
     * @param accountNo
     * @param userId
     * @param userNo
     * @param username
     * @param accountType
     */
    public AccountInfo(@Nullable String accountNo, Long userId, String userNo, String username, String accountType) {
        this.accountNo = accountNo;
        this.userId = userId;
        this.userNo = userNo;
        this.username = username;
        this.accountType = accountType;
    }

    /**
     * 暂时保留用于兼容原有接口
     * @param accountId
     * @param accountNo
     * @param userId
     * @param userNo
     * @param accountType
     */
    @Deprecated
    public AccountInfo(@Nullable Long accountId, @Nullable String accountNo, Long userId, String userNo, String accountType) {
        this.accountId = accountId;
        this.accountNo = accountNo;
        this.userId = userId;
        this.userNo = userNo;
        this.accountType = accountType;
    }

    public static AccountInfo withId(Long id) {
        return new AccountInfo(id);
    }

    public static AccountInfo withNo(String accountNo) {
        return new AccountInfo(accountNo);
    }


    public String getLabel() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAccountId() != null) {
            sb.append("ID:").append(getAccountId()).append(",");
        }
        if (Strings.isNotBlank(getAccountNo())) {
            sb.append("No:").append(getAccountNo()).append(",");
        }
        if (getUserId() != null) {
            sb.append("userId:").append(getUserId()).append(",");
        }
        if (Strings.isNotBlank(getUserNo())) {
            sb.append("userNo:").append(getUserNo()).append(",");
        }
        return sb.substring(0, sb.length() - 1) + "}";


    }

    @Override
    public void check() throws OrderCheckException {

        if (this.accountId != null) {
            return;
        }
        if (!Strings.isBlank(accountNo)) {
            return;
        }

        if ((userId != null && accountType != null) || (userNo != null && accountType != null)) {
            return;
        }
        throw new OrderCheckException("Account唯一标志不能为空", "accountId,accountNo或(userId+accountType)字少一个不能为空");
    }
}
