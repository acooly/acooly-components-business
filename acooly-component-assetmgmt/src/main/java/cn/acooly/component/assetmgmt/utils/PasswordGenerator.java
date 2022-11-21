/**
 * acooly-ms
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-08-14 13:27
 */
package cn.acooly.component.assetmgmt.utils;

import com.acooly.module.defence.password.PasswordStrength;
import com.acooly.module.defence.password.Passwords;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author zhangpu
 * @date 2021-08-14 13:27
 */
@Slf4j
public class PasswordGenerator {

    private static final String WORDS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*";

    public static String newRandomPassword() {
        String randomPassword = RandomStringUtils.random(12, WORDS);
        System.out.println(randomPassword);
        return randomPassword;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            newRandomPassword();
        }

        System.out.println();

        for (int i = 0; i < 20; i++) {
            System.out.println(Passwords.generate(PasswordStrength.complex, 12));
        }

    }


}
