package com.acooly.module.member.service.auth;

import com.acooly.core.utils.Digests;
import com.acooly.core.utils.Encodes;
import com.acooly.module.member.entity.MemberAuth;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2018-10-31 16:14
 */
@Slf4j
public class PasswordDigester {

    public static final int SALT_SIZE = 8;
    public static final int HASH_INTERATIONS = 512;

    public static String digestPassword(String plainPassword, String salt) {
        return Encodes.encodeHex(Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_INTERATIONS));
    }

    public static String newSalt() {
        return Encodes.encodeHex(Digests.generateSalt(SALT_SIZE));
    }


    public static void digestPassword(MemberAuth memberAuth) {
        String salt = newSalt();
        memberAuth.setLoginSalt(salt);
        memberAuth.setLoginKey(digestPassword(memberAuth.getLoginKey(), salt));
    }

}
