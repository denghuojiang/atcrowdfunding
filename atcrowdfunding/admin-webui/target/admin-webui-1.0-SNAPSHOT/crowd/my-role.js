// 声明专门的函数显示确认模态框
function showConfirmModel(roleArray) {
    // 打开模态框
    $("#confirmModal").modal("show");
    // 清除旧数据
    $("#roleNameDiv").empty();
    // 在全局变量范围内创建数组来存放角色id
    window.roleIdArray = [];
    // 遍历roleArray数组
    for (var i = 0; i < roleArray.length; i++) {
        var role = roleArray[i];
        var roleName = role.roleName;
        $("#roleNameDiv").append(roleName + "<br />");
        var roleId = role.roleId;
        console.log(roleId);
        window.roleIdArray.push(roleId);
    }
}

function generatePage() {
    //获取分页数据
    var pageInfo = getPageInfoRemote();
    //填充 表格
    fillTableBody(pageInfo);

}

//远程访问获取信息
function getPageInfoRemote() {
    var pageInfo = null;
    var ajaxResult = $.ajax({
        "url": "role/get/page",
        "type": "post",
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "dataType": "json",
        "async": false,
    });
    console.log(ajaxResult);
    //处理失败
    var statusCode = ajaxResult.status;
    if (statusCode != 200) {
        layer.msg("调用失败响应状态码:" + statusCode + ajaxResult.statusText);
        return null;
    }
    var resultEntity = ajaxResult.responseJSON;
    var result = resultEntity.result;
    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }
    var pageInfo = resultEntity.data;
    return pageInfo;
}

//填充表格
function fillTableBody(pageInfo) {
    //清空
    $("#rolePageBody").empty();
    $("#Pagination").empty();
    if (pageInfo == null || pageInfo == undefined || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4'>抱歉!无查询数据</td></tr>");
        return null;
    }
    //填充tbody
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkboxTd = "<td><input  id='" + roleId + "' class='itemBox' type='checkbox'/></td>";
        var roleNameTd = "<td>" + roleName + "</td>";

        // 通过button标签的id属性把roleId值传递到button按钮的单击响应函数中
        var checkBtn = "<button id='" + roleId + "' type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button id='" + roleId + "' type='button' class='btn btn-primary btn-xs pencilBtn'><i class='glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button id='" + roleId + "' type='button' class='btn btn-danger btn-xs removeBtn'><i class='glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";
        $("#rolePageBody").append(tr);
    }
    generateNavigator(pageInfo);
}

//生成页码导航条
function generateNavigator(pageInfo) {
    var totalRecord = pageInfo.total;
    var properties = {
        "num_edge_entries": 1,
        "num_display_entries": 3,
        "callback": pageinatonCallBack,
        "items_per_page": pageInfo.pageSize, //每页显示1项
        "current_page": pageInfo.pageNum - 1, //当前页数pageIndex从0开始
        "prev_text": "上一页",
        "next_text": "下一页"
    }
    $("#Pagination").pagination(totalRecord,properties);
}

//翻页时回调函数
function pageinatonCallBack(pageIndex, JQuery) {
    window.pageNum = pageIndex+1;
    generatePage();
    return false;
}