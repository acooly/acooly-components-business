/*
 * acooly.cn Inc.
 * Copyright (c) 2021 All Rights Reserved.
 * create by zhangpu@acooly.cn
 * date:2021-08-12
 */
package cn.acooly.component.assetmgmt.service.impl;

import cn.acooly.component.assetmgmt.dao.SecretboxDao;
import cn.acooly.component.assetmgmt.entity.Secretbox;
import cn.acooly.component.assetmgmt.service.SecretboxService;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Strings;
import com.acooly.module.treetype.entity.TreeType;
import com.acooly.module.treetype.service.TreeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 密码箱 Service实现
 *
 * @author zhangpu@acooly.cn
 * @date 2021-08-12 22:44:09
 */
@Service("secretboxService")
public class SecretboxServiceImpl extends EntityServiceImpl<Secretbox, SecretboxDao> implements SecretboxService {

    @Autowired
    private TreeTypeService treeTypeService;

    @Override
    public void save(Secretbox entity) throws BusinessException {
        onSave(entity);
        super.save(entity);
    }

    @Override
    public void update(Secretbox o) throws BusinessException {
        onSave(o);
        super.update(o);
    }


    @Override
    public void saves(List<Secretbox> secretboxes) throws BusinessException {
        // 自动生成15为的唯一编码
        for (Secretbox secretbox : secretboxes) {
            if (Strings.isBlank(secretbox.getCode())) {
                secretbox.setCode(Ids.newId15());
            }
        }
        String typeCode = Collections3.getFirst(secretboxes).getTypeCode();
        if (Strings.isNotBlank(typeCode)) {
            TreeType treeType = treeTypeService.findByCode(Secretbox.TREE_TYPE_THEME, typeCode);
            if (treeType != null) {
                for (Secretbox secretbox : secretboxes) {
                    secretbox.setTypeCode(typeCode);
                    secretbox.setTypeName(treeType.getName());
                    secretbox.setTypePath(treeType.getPath() + treeType.getId());
                }
            }

        }
        super.saves(secretboxes);
    }

    protected void onSave(Secretbox entity) {
        if (Strings.isBlank(entity.getCode())) {
            entity.setCode(Ids.newId15());
        }
        String typeCode = entity.getTypeCode();
        if (Strings.isNotBlank(typeCode)) {
            TreeType treeType = treeTypeService.findByCode(Secretbox.TREE_TYPE_THEME, typeCode);
            if (treeType != null) {
                entity.setTypeName(treeType.getName());
                entity.setTypePath(treeType.getPath() + treeType.getId());
            }
        }
    }


}
