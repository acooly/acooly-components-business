package com.acooly.module.account;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2018-12-18 17:47
 */
@Slf4j
@Data
public class UserInfo {

    private Long id;
    private String userNo;
    private String username;

    public UserInfo(Long id, String userNo, String username) {
        this.id = id;
        this.userNo = userNo;
        this.username = username;
    }
}
