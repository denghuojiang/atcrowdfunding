function generateTree() {
    $.ajax({
        "url": "menu/get/whole/treeNew",
        "type": "post",
        "datatype": "json",
        "success": function (response) {
            var result = response.result;
            if (result === "SUCCESS") {
                var setting = {
                    "view": {
                        "addDiyDom": myAddDiyDom,
                        "addHoverDom": myAddHoverDom,
                        "removeHoverDom":myRemoveHoverDom
                    },
                    "data": {
                        "key": {
                            "url": "cat"
                        }
                    }
                };
                var zNodes = response.data;
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if (result === "FAILED") {
                layer.msg(result);
                return null;
            }
        }
    })
}
function myRemoveHoverDom(treeId, treeNode) {
    var btnGroupId = treeNode.tId + "_btnGrp";
    //移除对应的元素
    $("#" + btnGroupId).remove();
}

//鼠标移入添加按钮组
function myAddHoverDom(treeId, treeNode) {
    //<span><a><i></i></ a><a><i></i></a></ span>按钮组结构
    // 为了在需要移除按钮组的时候能够精确定位到按钮组所在 span，需要给 span 设置有规律的 id
    var btnGroupId = treeNode.tId + "_btnGrp";
// 判断一下以前是否已经添加了按钮组
    if ($("#" + btnGroupId).length > 0) {
        return;
    }
    // 准备各个按钮的HTML标签
    var addBtn = "<a id='" + treeNode.id + "' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn = "<a id='" + treeNode.id + "' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title=' 删 除 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn = "<a id='" + treeNode.id + "' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title=' 修 改 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";


    // 获取当前结点的级别
    var level = treeNode.level;
    // 声明变量存储拼装好的按钮代码
    var btnHTML = "";
    // 判断当前节点的级别
    if (level == 0) {
        // 级别为 0 时是根节点，只能添加子节点
        btnHTML = addBtn;
    }
    if (level == 1) {
        // 级别为 1 时是分支节点，可以添加子节点、修改
        btnHTML = addBtn + " " + editBtn;
        // 获取当前节点的子节点数量
        var length = treeNode.children.length;
        // 如果没有子节点，可以删除
        if (length == 0) {
            btnHTML = btnHTML + " " + removeBtn;
        }
    }
    if (level == 2) {
        // 级别为 2 时是叶子节点，可以修改、删除
        btnHTML = editBtn + " " + removeBtn;
    }
    //找到按钮后的超链接
    var anchorId = treeNode.tId + "_a";
    $("#" + anchorId).after("<span id='" + btnGroupId + "'>" + btnHTML + "</span>");


}

//修改默认图标
function myAddDiyDom(treeId, treeNode) {
    var spanId = treeNode.tId + "_ico";
    console.log(spanId);
    $("#" + spanId).removeClass()
        .addClass(treeNode.icon);
}