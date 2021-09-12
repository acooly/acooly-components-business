/**
 * acooly-components-business
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-09-02 22:49
 */
package com.acooly.module.test;

import com.acooly.core.utils.Regexs;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 * @author zhangpu
 * @date 2021-09-02 22:49
 */
@Slf4j
public class RegexTest {

    public static void main(String[] args) {

        String content = "第一行\n<%@ page import=\"com.acooly.core.utils.Strings\"%>\n" +
                "<%@ page contentType=\"text/html;charset=UTF-8\"%>\n最后行\n";

        String regex = "<%.*\n";
        System.out.println(Regexs.finders(Pattern.compile(regex), content));

    }
}
