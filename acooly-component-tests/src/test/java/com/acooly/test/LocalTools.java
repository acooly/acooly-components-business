package com.acooly.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author zhangpu
 * @date 2018-09-24 01:34
 */
@Slf4j
public class LocalTools {
    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("zhangpu@aliyun.com"));
    }


}
