package com.acooly.module.member.service.busitype;

import com.acooly.core.utils.Collections3;
import com.acooly.module.member.MemberBusiType;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局会员业务分类加载器
 * <p>
 * 来源：
 * 1、默认为：DefaultMemberBusiTypeLoader实现提供一个default值。
 * 2、集成项目中任何实现MemberBusiTypeLoader接口的输出（spring容器内），不得添加@Primary
 *
 * @see com.acooly.module.member.service.busitype.MemberBusiTypeLoader
 * @see com.acooly.module.member.service.busitype.DefaultMemberBusiTypeLoader
 * @author zhangpu@acooly.cn
 * @date 2020-1-22 02:22
 */
@Component("memeberBusiTypeLoader")
@Primary
public class MemberBusiTypeLoaderImpl implements MemberBusiTypeLoader, ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private List<MemberBusiTypeLoader> memeberBusiTypeLoaders = new ArrayList<>();

    @Override
    public List<MemberBusiType> loadMemberBusiTypes() {
        return mergeMemberBusiType();
    }

    private List<MemberBusiType> mergeMemberBusiType() {
        Map<String, MemberBusiType> memberBusiTypeMaps = new HashMap<>();
        if (Collections3.isNotEmpty(memeberBusiTypeLoaders)) {
            List<MemberBusiType> oneLoaderCodes = null;
            for (MemberBusiTypeLoader memeberBusiTypeLoader : memeberBusiTypeLoaders) {
                oneLoaderCodes = memeberBusiTypeLoader.loadMemberBusiTypes();
                for (MemberBusiType memberBusiType : oneLoaderCodes) {
                    memberBusiTypeMaps.put(memberBusiType.getCode(), memberBusiType);
                }
            }
        }
        return Lists.newArrayList(memberBusiTypeMaps.values());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, MemberBusiTypeLoader> memeberBusiTypeLoaderMap = applicationContext.getBeansOfType(MemberBusiTypeLoader.class);
        for (MemberBusiTypeLoader memeberBusiTypeLoader : memeberBusiTypeLoaderMap.values()) {
            if (memeberBusiTypeLoader instanceof MemberBusiTypeLoaderImpl) {
                continue;
            }
            memeberBusiTypeLoaders.add(memeberBusiTypeLoader);
        }
        if(Collections3.isEmpty(memeberBusiTypeLoaders)){
            memeberBusiTypeLoaders.add(new DefaultMemberBusiTypeLoader());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
