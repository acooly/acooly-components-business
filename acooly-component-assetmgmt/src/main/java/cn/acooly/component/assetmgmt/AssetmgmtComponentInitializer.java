/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-02-18 00:37 创建
 */
package cn.acooly.component.assetmgmt;

import com.acooly.core.common.boot.component.ComponentInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author acooly
 * @date 2022-11-19
 */
public class AssetmgmtComponentInitializer implements ComponentInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 资产信息，安全信息相关的数据可Web复制
        setPropertyIfMissing("acooly.framework.plugin.clipboard", true);
        // 设置资产管理的JS自定义文件
        setPropertyIfMissing("acooly.framework.custom-scripts.assetmgmt[0]", "/manage/assert/assetmgmt/secretbox.js");
    }
}
