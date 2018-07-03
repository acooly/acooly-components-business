package com.acooly.component.account.dto;

import com.acooly.component.account.enums.AccountTypeEnum;
import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.common.facade.InfoBase;
import com.acooly.core.utils.Strings;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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

    public AccountInfo(Long accountId, String accountNo, Long userId, AccountTypeEnum accountType) {
        this.accountId = accountId;
        this.accountNo = accountNo;
        this.userId = userId;
        this.accountType = accountType;
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
