/**
 * acooly-components-business
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-09-02 00:39
 */
package com.acooly.module.test;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @author zhangpu
 * @date 2021-09-02 00:39
 */
@Slf4j
public class JspToFtl {

    public static void main(String[] args) {
        String jsp = "/Users/zhangpu/workspace/acooly/v5/acooly-components-business/acooly-component-member" +
                "/src/main/resources/META-INF/resources/WEB-INF/jsp" +
                "/manage/component/member/member.jsp";

        String ftl = jspToFtlString(jsp);
        System.out.println(ftl);
    }

    public static String jspToFtlString(String fromJspPath) {
        String jspContent = loadFile(fromJspPath);
        jspContent = handleJspInstruct(jspContent);

        return jspContent;
    }

    /**
     * 处理JSP的@指令
     *
     * @return
     */
    protected static String handleJspInstruct(String content) {
        return Strings.replaceAll(content, "<%.*\n", "");
    }

    protected static String loadFile(String path) {
        try {
            return FileUtils.readFileToString(new File(path), "utf-8");
        } catch (Exception e) {
            throw new BusinessException("JSP_TO_FTL_LOADFILE_ERROR", "文件加载失败", "");
        }
    }

    public static void jspToFtl(String fromJspPath, String toFtlPath) throws Exception {
        String jsp = FileUtils.readFileToString(new File(fromJspPath), "utf-8");

    }

    public static String handleSearchForm(String content) {

        return null;
    }

}
