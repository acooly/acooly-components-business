/**
 * 树形多级自定义分类管理
 * @type {{}}
 */
let acooly_secretbox_tree = {

    expandAll: true,

    expandToggle: function (e) {
        this._getZTree().expandAll(!this.expandAll);
        this.expandAll = !this.expandAll;
        if (e) {
            let icon = "<i class=\"fa fa-" + (this.expandAll ? "minus" : "plus") + "-square-o\" aria-hidden=\"true\"></i>";
            $(e).linkbutton({text: icon});
        }
    },

    initSelect: function (treeboxId, selectVal) {
        $.ajax({
            url: '/manage/module/treeType/treeType/loadTree.html?theme=secretbox',
            success: function (data, status) {
                if (!data.success) {
                    return;
                }
                var rows = data.rows;
                $('#' + treeboxId).select2ztree({
                    theme: 'bootstrap4',
                    valueField: 'code',
                    ztree: {
                        setting: {},
                        zNodes: rows
                    }
                });
                if (selectVal) {
                    $('#' + treeboxId).select2ztree('val', [selectVal]);
                }
            }
        });


    },

    select: function (inputId) {
        $.ajax({
            url: '/manage/module/treeType/treeType/loadTree.html?theme=secretbox',
            success: function (data, status) {
                if (!data.success) {
                    return;
                }
                var rows = data.rows;
                $.acooly.secretbox.tree._prevHandleData(rows);
                $.fn.zTree.init($("#manage_secretbox_editform_treeDemo"), {
                    view: {
                        dblClickExpand: false
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    callback: {
                        onClick: function (e, treeId, treeNode) {
                            var zTree = $.fn.zTree.getZTreeObj("manage_secretbox_editform_treeDemo"),
                                nodes = zTree.getSelectedNodes(),
                                v = "";
                            nodes.sort(function compare(a, b) {
                                return a.id - b.id;
                            });
                            for (var i = 0, l = nodes.length; i < l; i++) {
                                v += nodes[i].name + ",";
                            }
                            if (v.length > 0) v = v.substring(0, v.length - 1);
                            var typeCodeObject = $("#manage_secretbox_editform_typeCode");
                            typeCodeObject.attr("value", v);
                        }
                    }
                }, rows);
                var zTree = $.fn.zTree.getZTreeObj("manage_secretbox_editform_treeDemo");
                zTree.expandAll(true);
            }
        });
    },


    hideMenu: function () {
        $("#manage_secretbox_editform_menuContent").fadeOut("fast");
        $("body").unbind("mousedown", $.acooly.secretbox.tree.onBodyDown);
    },

    load: function (defaultNode) {
        $.ajax({
            url: '/manage/module/treeType/treeType/loadTree.html?theme=secretbox',
            data: {sort: 'asc'},
            success: function (data, status) {
                if (!data.success) {
                    return;
                }
                var rows = data.rows;
                $.acooly.secretbox.tree._prevHandleData(rows);
                $.fn.zTree.init($("#manage_secretbox_tree"), {
                    view: {
                        showLine: true,
                        showIcon: true,
                        showTitle: true
                        , addHoverDom: $.acooly.secretbox.tree.addHoverDom,
                        removeHoverDom: $.acooly.secretbox.tree.removeHoverDom,
                    },
                    edit: {
                        enable: false,
                        showRemoveBtn: true,
                        showRenameBtn: false
                    },
                    data: {
                        keep: {
                            parent: true
                        }
                    },
                    callback: {
                        onClick: function (event, treeId, treeNode, clickFlag) {
                            // manage_goods_search(treeNode)
                            $('#manage_secretbox_searchform input[name="search_RLIKE_typePath"]').val(treeNode.path + treeNode.id);
                            $('#manage_secretbox_searchform input[name="selected_typeName"]').val(treeNode.name);
                            $('#manage_secretbox_searchform input[name="selected_typeCode"]').val(treeNode.code);
                            $.acooly.framework.search('manage_secretbox_searchform', 'manage_secretbox_datagrid');
                            // 选择分类后，释放可操作按钮disable
                            $('#manage_secretbox_toolbar_create').linkbutton("enable");
                            $('#manage_secretbox_toolbar_import').linkbutton("enable");
                        },
                        onDrop: function (event, treeId, treeNodes, targetNode, moveType) {
                            // manage_goodsCategory_tree_moveup(treeNodes[0], targetNode, moveType);
                        }
                    }
                }, rows);
                let zTree = $.acooly.secretbox.tree._getZTree();
                zTree.expandAll(true);
                if (defaultNode) {
                    zTree.selectNode(defaultNode);
                }
            }
        });
    },

    add: function (parentId) {
        let url = '/manage/module/treeType/treeType/create.html?theme=secretbox';
        if (parentId) {
            url += '&parentId=' + parentId;
        }
        $.acooly.framework.create({
            url: url,
            form: 'manage_treeType_editformsecretbox',
            datagrid: 'manage_treeType_datagridsecretbox',
            width: 500,
            height: 350,
            onSuccess: function (result) {
                var zTree = $.acooly.secretbox.tree._getZTree();
                var row = result.entity;
                row.icon = null;
                // row.iconSkin = "fa fa-folder";
                let newNode;
                if (parentId) {
                    var parentNode = zTree.getNodeByParam("id", parentId, null);
                    newNode = zTree.addNodes(parentNode, row, true);
                } else {
                    newNode = zTree.addNodes(null, row, true);
                }
                zTree.selectNode(newNode);
            }
        });
    },

    edit: function (id) {
        $.acooly.framework.edit({
            url: '/manage/module/treeType/treeType/edit.html',
            id: id,
            form: 'manage_treeType_editformsecretbox',
            datagrid: 'manage_treeType_datagridsecretbox',
            width: 500,
            height: 350,
            reload: false,
            onSuccess: function (result) {
                console.info(result);
                var zTree = $.acooly.secretbox.tree._getZTree();
                var node = zTree.getNodeByParam("id", id, null);
                node.name = result.entity.name;
                zTree.updateNode(node, true);
            }
        })
    },

    delete: function (id) {
        $.messager.confirm("确认", "你确认删除该类型？	", function (r) {
            if (!r)
                return;
            $.ajax({
                url: '/manage/module/treeType/treeType/deleteJson.html?id=' + id,
                success: function (data, status) {
                    if (data.success) {
                        let zTree = $.acooly.secretbox.tree._getZTree();
                        let node = zTree.getNodeByParam("id", id, null);
                        $.acooly.secretbox.tree.load(node.getParentNode());
                    }
                    if (data.message)
                        $.messager.show({
                            title: '提示',
                            msg: data.message
                        });
                }
            });
        })
    },


    /**
     * 鼠标移动到节点动态添加添加和删除链接。
     */
    addHoverDom: function (treeId, treeNode) {
        var aObj = $("#" + treeNode.tId + "_a");
        // 添加子
        if ($("#manage_secretbox_add_Btn_" + treeNode.id).length == 0) {
            var html = "<a id='manage_secretbox_add_Btn_" + treeNode.id + "' href='javascript:;' onclick='$.acooly.secretbox.tree.add(" + treeNode.id + ");return false;' style='margin:0 0 0 3px;padding-top:2px;'><i class=\"fa fa-plus-circle\" aria-hidden=\"true\"></i></a>";
            aObj.append(html)
        }
        // 编辑
        if ($("#manage_secretbox_edit_Btn_" + treeNode.id).length == 0) {
            var html = "<a id='manage_secretbox_edit_Btn_" + treeNode.id + "' href='javascript:;' onclick='$.acooly.secretbox.tree.edit(" + treeNode.id + ");return false;' style='margin:0 0 0 3px;padding-top:3px;'><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i></a>";
            aObj.append(html)
        }
        // 删除
        if (!treeNode.children || treeNode.children.length == 0) {
            if ($("#manage_secretbox_delete_Btn_" + treeNode.id).length > 0)
                return;
            var html = "<a id='manage_secretbox_delete_Btn_" + treeNode.id + "' href='javascript:;' onclick='$.acooly.secretbox.tree.delete(" + treeNode.id + ");return false;' style='margin:0 0 0 3px;padding-top:2px;'><i class=\"fa fa-minus-circle\" aria-hidden=\"true\"></i></a>";
            aObj.append(html)
        }
    },

    /**
     * 鼠标移出节点处理
     */
    removeHoverDom: function (treeId, treeNode) {
        if ($("#manage_secretbox_add_Btn_" + treeNode.id).length > 0)
            $("#manage_secretbox_add_Btn_" + treeNode.id).unbind().remove();
        if ($("#manage_secretbox_edit_Btn_" + treeNode.id).length > 0)
            $("#manage_secretbox_edit_Btn_" + treeNode.id).unbind().remove();
        if ($("#manage_secretbox_delete_Btn_" + treeNode.id).length > 0)
            $("#manage_secretbox_delete_Btn_" + treeNode.id).unbind().remove();
    },

    _getZTree: function () {
        return $.fn.zTree.getZTreeObj("manage_secretbox_tree");
    },

    _prevHandleData: function (rows) {
        for (var i = 0; i < rows.length; i++) {
            // rows[i].icon = null;
            // rows[i].iconSkin = "fa fa-folder";
            if (rows[i].children != null && rows[i].children.length > 0) {
                $.acooly.secretbox.tree._prevHandleData(rows[i].children);
                rows[i].isParent = true;
            } else {
                rows[i].isParent = false;
            }
        }
    }

}

/**
 * 列表的formatter
 * @type {{}}
 */
let acooly_secretbox_formatter = {

    password: function (value, row, index) {
        if (!value) {
            return "";
        }
        let html = "********(" + value.length + ")"
            + " <a href='javascript:;' style='margin-left: 3px;' id='manage_secretbox_datagrid_password_" + row.id + "' onclick='$.acooly.secretbox.clip(\"#manage_secretbox_datagrid_password_" + row.id + "\",\"" + value + "\",\"复制成功\")'><i class=\"fa fa-clipboard\" aria-hidden=\"true\"></i></a>";
        return html;
    },

    accessPoint: function (value, row, index, data, field) {
        if (!value) {
            return "";
        }
        let html = $.acooly.secretbox.formatter.copyBtn(value, row, this, data, field);
        if (row.serviceType == 'web') {
            html = html + " <a href='" + value + "' target='_blank'>" + value + "</a>";
        } else {
            html = html + " " + value;
        }
        return html;
    },

    copy: function (value, row, index, data, field) {
        if (!value) {
            return "";
        }
        let html = $.acooly.secretbox.formatter.copyBtn(value, row, this, data, field) + " " + value;
        return html;
    },

    copyBtn: function (value, row, that, data, field) {
        let title = (that && that.title) ? that.title : "";
        return "<a href='javascript:;' id='manage_secretbox_datagrid_" + field + "_" + row.id + "' onclick='$.acooly.secretbox.clip(\"#manage_secretbox_datagrid_" + field + "_" + row.id + "\",\"" + value + "\",\"复制" + title + "成功\")'><i class=\"fa fa-clipboard\" aria-hidden=\"true\"></i></a>"
    }

}

/**
 * 安全盒管理
 * @type {{config: {}}}
 */
let acooly_secretbox = {
    config: {},

    tree: acooly_secretbox_tree,
    formatter: acooly_secretbox_formatter,

    create: function () {
        var typeCode = $('#manage_secretbox_searchform input[name="selected_typeCode"]').val();
        if (!typeCode) {
            $.acooly.alert("资产添加", "请先选择左侧的资产分类");
            return;
        }
        $.acooly.framework.create({url: '/manage/assetmgmt/secretbox/create.html?typeCode=' + typeCode, entity: 'secretbox', width: 800, height: 600});
    },

    import: function () {
        var typeCode = $('#manage_secretbox_searchform input[name="selected_typeCode"]').val();
        if (!typeCode) {
            $.acooly.alert("资产导入", "请先选择左侧的资产分类");
            return;
        }
        $.acooly.framework.imports({
            url: '/manage/assetmgmt/secretbox/importView.html?typeCode=' + typeCode,
            uploader: 'manage_secretbox_import_uploader_file',
            datagrid: 'manage_secretbox_datagrid',
            width: 500, height: 400
        });
    },

    /**
     * 编辑
     */
    passwordGenerate: function () {
        let passwordStrength = $("input[name='passwordStrength']:checked").val();
        let passwordLength = $('#manage_secretbox_editform_password_length').val();
        if (!passwordLength) {
            passwordLength = 12;
        }
        $.ajax({
            url: '/manage/assetmgmt/secretbox/passwordGenerate.html',
            data: {"length": passwordLength, "passwordStrength": passwordStrength},
            success: function (result) {
                if (result.success) {
                    $('#manage_secretbox_editform_password').val(result.data.password)
                }
            }
        });
    },


    clipPassword: function (element) {
        $.acooly.framework.fireSelectRow("manage_secretbox_datagrid", function (row) {
            $.acooly.secretbox.clip(element, row.password, "复制密码成功！");
        });
    },


    clipSecretbox: function (element) {
        $.acooly.framework.fireSelectRow("manage_secretbox_datagrid", function (row) {
            let content = "名称：" + row.title
                + "\n入口：" + row.accessPoint
                + "\n账号：" + row.username
                + "\n密码：" + row.password;
            $.acooly.secretbox.clip(element, content, "复制密码成功！");
        });
    },

    /**
     * 复制到剪贴板
     * @param btn
     * @param target
     */
    clip: function (btn, target, msg) {
        if (!ClipboardJS.isSupported()) {
            layer.tips("当前浏览器不支持复制", btn, {tips: 3});
            return;
        }
        let clipboard = new ClipboardJS(btn, {
            text: function (trigger) {
                if (!target.startsWith("#") && !target.startsWith(".")) {
                    return target;
                }
                let content = $(target).text();
                if (!content && content == '') {
                    content = $(target).val();
                }
                return content;
            }
        });
        if (!msg) {
            msg = "<B>copied!</B>";
        }
        clipboard.on('success', function (e) {
            e.clearSelection();
            layer.tips(msg, btn, {tips: 3});
        });
    }
};


/***
 * JQuery静态类前缀处理
 */
(function ($) {
    if (!$.acooly) {
        $.acooly = {};
    }
    if (!$.acooly.secretbox) {
        $.extend($.acooly, {secretbox: acooly_secretbox});
    }
})(jQuery);




