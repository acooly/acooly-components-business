<!-- title:资产管理组件 -->
<!-- type: business -->
<!-- author: zhangpu -->
<!-- date: 2022-11-21 -->

# 简介

提供简单通用的资产管理工具，主要的功能包括：

* 资产分类定义，无限级树形结构定义（编码+名称）。
* 资产信息管理：（编码，标题，安全类型，服务类型，安全凭证信息，简介，绑定手机和邮箱）；提供基础功能：添加，编辑，删除，详情和查询；
* 资产信息的快捷复制和Copy
* 资产列表的批量导入
* TODO: 资产安全及凭证信息的加密存储（对称入库或Vault）。
* TODO: 资产信息的有效期管理
* TODO: 增加管理人员及分配情况

# 集成

```xml
<dependency>
  <groupId>cn.acooly</groupId>
  <artifactId>acooly-component-assetmgmt</artifactId>
  <version>5.2.0-SNAPSHOT</version>
</dependency>
```
> 默认情况下：最低支持版本`5.2.0-SNAPSHOT`

该组件为应用型组件，无需特殊配置，加入集成启动即可用。

# changelogs

## 5.2.0-SNAPSHOT.20221121

* 2022-11-21 - 完成资产管理的secretbox功能的组件开发, 初次发布 - [zhangpu] 97b3b4a



