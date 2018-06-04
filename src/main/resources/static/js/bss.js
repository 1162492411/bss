/**
 * Created by Mo on 2017/10/2.
 */

var rectangleEditor;//添加区域页面待矩形编辑框
var areaMap;//添加区域页面的地图
var bicycleMap;//车辆分布页面的地图
var pathMap;// 行程页面的行程轨迹的地图
var overviewReportOptions;// 报表-使用概况的配置
var overviewReportChart;//报表-使用概况的图表
var taskReportOptions;// 报表-任务概况的配置
var taskReportChart;//报表-任务概况的图表
/*----------------扩展函数--------------*/
Date.prototype.format = function (format) {
    let o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds()
    };
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let k in o)
        if (new RegExp("(" + k + ")").test(format)) format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format
};

/**
 * 扩展jquery Validation,除去每个输入框的空格
 */
// (function ($) {
//
//     $.each($.validator.methods, function (key, value) {
//         $.validator.methods[key] = function () {
//             if(arguments.length > 0) {
//                 arguments[0] = $.trim(arguments[0]);
//             }
//
//             return value.apply(this, arguments);
//         };
//     });
// } (jQuery));

/*---------------公共函数----------------*/
/**
 * 页面跳转
 * @param data 待跳转的页面
 */
function pageJump(data) {
    if (dataNotEmpty(data)) {
        self.location.href = data;
    }
}

/**
 * 显示失败消息
 * @param data 失败消息
 */
function showErrorData(data) {
    alert(data.message + " : " + data.result);
}

/**
 * 检测元素是否存在
 * @param id 待检测的元素的id
 * @return {boolean} 元素是否存在
 */
function checkElementEmpty(id) {
    return $("#" + id).length <= 0;
}

/**
 * 检测数据是否为空或未定义
 * @param data 待验证的数据
 * @returns {boolean} 是否为空
 * TODO:此方法需扩充
 */
function checkDataEmpty(data) {
    return data === "" || data === undefined || data === "[]" || data === NaN || data === emptyDataValue || data === null;
}

/**
 * 数据是否不为空
 * @param data 待检查的数据
 * @returns {boolean} true:数据不为空;false:数据为空
 */
function dataNotEmpty(data) {
    return !checkDataEmpty(data);
}

/**
 * 重置组件
 * @param div
 */
function resetDiv(div) {
    if (!checkElementEmpty(div))
        $("#" + div).empty();
}

/**
 * 重置模态框
 * @param div 模态框的id
 */
function resetModal(div) {
    if (!checkElementEmpty(div)) {
        let $modal = $("#" + div);
        $modal.find("[id]").each(function () {
            $(this).empty();
        });
    }
}

/**
 * 把单个对象放入数组
 * @param data
 */
function castObjectToArray(data) {
    let array = new Array();
    array.push(data);
    return array;
}

/**
 * 生成表格
 * @param div 待操作的表格
 * @param data 待填充的数据
 * @param 每行数据待设置的操作
 */
function generateTable(div, data, methods) {
    if (!checkElementEmpty(div)) {
        let $div = $("#" + div);
        if (dataNotEmpty(data)) {
            //设置表头
            if (dataNotEmpty(data.names))
                $div.append(generateBatchTableHeader(data.names));
            //设置数据
            if (dataNotEmpty(data.records) && dataNotEmpty(data.keys))
                $div.append(generateBatchTableData(div, data.records, data.keys, methods));
        }
    }
}

/**
 * 生成表格表头
 * @param header 待填充的表头数据
 * @return {string} 设置好的表头
 */
function generateBatchTableHeader(header) {
    let strPre = "<thead><tr>";
    let strData = "";
    for (let i = 0; i < header.length; i++) {
        strData += "<th>" + header[i] + "</th>";
    }
    strData += "<th>操作</th>";
    let strSuf = "</tr></thead>";
    return strPre + strData + strSuf;
}

/**
 * 批量生成表格数据
 * @param div 表格ID
 * @param data 表格数据
 * @param keys 表格实体的键列表
 * @param methods 表格每行的方法列
 * @return  {string} 设置好的表格数据
 */
function generateBatchTableData(div, data, keys, methods) {
    let strPre = "<tbody id='" + div + "Body'>";
    let strData = "";
    if (dataNotEmpty(data)) {
        for (let i = 0; i < data.length; i++) {
            strData += generateSingleTableData(div, data[i], keys, methods);
        }
    }
    let strSuf = "</tbody>";
    return strPre + strData + strSuf;
}

/**
 * 生成单行表格数据
 * @param div 表格ID
 * @param data 待设置的数据
 * @param keys 表格实体的键列表
 * @param methods 表格每行的方法列
 * @returns {string} 设置好的单行表格数据
 */
function generateSingleTableData(div, data, keys, methods) {
    if (dataNotEmpty(data) && dataNotEmpty(keys)) {
        let strPre = "<tr id='" + div + "TR-" + data.id + "'>";
        let strData = "";
        for (let i = 0; i < keys.length; i++) {
            strData += generateSingleTD(div + "TD-" + data.id + "-" + keys[i], data[keys[i]]);
        }
        strData += generateSingleTableMethods(div, data, methods);
        let strSuf = "</tr>";
        return strPre + strData + strSuf;
    }
}

/**
 * 生成单个TD数据
 * @param idValue 待插入的ID值
 * @param data 待插入的数据
 * @return {string} 设置好的单个TD数据
 */
function generateSingleTD(idValue, data) {
    let dataEmpty = checkDataEmpty(data);
    let strPre = dataEmpty ? "<td  id='" + idValue + "' style='text-align: center;line-height: 100%'>" : "<td id='" + idValue + "'>";
    let strData = dataEmpty ? emptyDataValue : data;
    let strSuf = "</td>";
    return strPre + strData + strSuf;
}

/**
 * 生成单行数据表格的操作列
 * @param div 数据表格ID
 * @param data 单行的数据
 * @param methods 待添加的数据操作方法及按钮名
 * @return {string} 设置好的单行数据的操作列
 */
function generateSingleTableMethods(div, data, methods) {
    let strPre = dataNotEmpty(methods) ? "<td id='" + div + "-" + data.id + "-Btn" + "'>" : "<td id=' " + div + "-" + data.id + "-Btn" + "' style='text-align: center;line-height: 100%'>";
    let strData = "";
    if (dataNotEmpty(methods)) {
        for (let i = 0; i < methods.length; i++) {
            strData += "<button class='Button Button--blue' id='" + div + "BlockBtn-" + data.id + "' onclick='" + methods[i].method + "(" + JSON.stringify(data) + ")'>" + methods[i].name + "</button> ";
        }
    }
    else
        strData = emptyDataValue;
    let strSuf = "</td>";
    return strPre + strData + strSuf;
}

/**
 * 初始化分页
 * @param total 数据总数
 * @param currentPage 当前页码
 * @param totalPage 总页码
 * @param id 分页的ID
 * @param method 每个分页按钮触发的函数
 */
function initPag(total, currentPage, totalPage, id, method) {
    let c = parseInt(currentPage);
    let t = parseInt(totalPage);
    let prevString = "";
    let suffString = "";
    if (t <= 1) {//总页数过少时移除分页
        $("#" + id).remove();
        return;
    }
//处理"上一页"
    if (c > 1) $("#" + id).append("<li id='pagLast'><span onclick=" + method + "(" + (c - 1) + ")> << </span></li>");
//添加当前页码前的页数
    if (c > 3) {
        prevString += "<li><a>...</a></li>";
        for (let i = c - 3; i < c; i++) prevString += "<li><span onclick=" + method + "(" + i + ")>" + i + "</span></li>";
        $("#" + id).append(prevString);
    }
    else {
        for (let i = 1; i < c; i++) prevString += "<li><span onclick=" + method + "(" + i + ")>" + i + "</span></li>";
        $("#" + id).append(prevString);
    }
//添加当前页码
    $("#" + id).append("<li id='pageCurrent' class='active'><span>" + c + "</span></li>");
//添加当前页码后的页数
    if (c < t - 3) {
        for (let j = c + 1; j <= c + 3; j++) suffString += "<li><span onclick=" + method + "(" + j + ")>" + j + "</span></li>";
        suffString += "<li><a>...</a></li>";
    }
    else
        for (let j = c + 1; j <= t; j++) suffString += "<li><span onclick=" + method + "(" + j + ")>" + j + "</span></li>";
    $("#" + id).append(suffString);
//处理"下一页"
    if (c < t) $("#" + id).append("<li id='pagNext'><span onclick=" + method + "(" + (c + 1) + ")> >> </span></li>");
    $("#" + id).append("<span style='line-height:30px'>共" + total + "条,第" + c + "/" + t + "页<span>");
}


/**
 * 初始化分页-关键字版
 * @param total 数据总数
 * @param currentPage 当前页码
 * @param totalPage 总页码
 * @param id 分页的ID
 * @param method 每个分页按钮触发的函数
 * @param keyword 关键字
 */
function initPagKeyword(total, currentPage, totalPage, id, method, keyword) {
    let c = parseInt(currentPage);
    let t = parseInt(totalPage);
    let prevString = "";
    let suffString = "";
    if (t <= 1) {//总页数过少时移除分页
        $("#" + id).remove();
        return;
    }
//处理"上一页"
    if (c > 1) $("#" + id).append("<li id='pagLast'><span onclick=" + method + "(" + (c - 1) + ")> << </span></li>");
//添加当前页码前的页数
    if (c > 3) {
        prevString += "<li><a>...</a></li>";
        for (let i = c - 3; i < c; i++) prevString += "<li><span onclick=" + method + "('" + keyword + "'," + i + ")>" + i + "</span></li>";
        $("#" + id).append(prevString);
    }
    else {
        for (let i = 1; i < c; i++) prevString += "<li><span onclick=" + method + "('"+ keyword + "'," + i + ")>" + i + "</span></li>";
        $("#" + id).append(prevString);
    }
//添加当前页码
    $("#" + id).append("<li id='pageCurrent' class='active'><span>" + c + "</span></li>");
//添加当前页码后的页数
    if (c < t - 3) {
        for (let j = c + 1; j <= c + 3; j++) suffString += "<li><span onclick=" + method + "('" + keyword + "'," + j + ")>" + j + "</span></li>";
        suffString += "<li><a>...</a></li>";
    }
    else
        for (let j = c + 1; j <= t; j++) suffString += "<li><span onclick=" + method + "('" + keyword + "'," + j + ")>" + j + "</span></li>";
    $("#" + id).append(suffString);
//处理"下一页"
    if (c < t) $("#" + id).append("<li id='pagNext'><span onclick=" + method + "('" + keyword + "'," + (c + 1) + ")> >> </span></li>");
    $("#" + id).append("<span style='line-height:30px'>共" + total + "条,第" + c + "/" + t + "页<span>");
}

/**
 * 从后台获取数据并渲染
 * @param baseUrl 不带页码的后台数据url
 * @param page 请求的页数
 * @param tableDiv 前台展现数据的table的id
 * @param pagDiv 前台展现数据分页的id
 * @param methods 前台展现数据的table的操作方法
 * @param reloadMethod 更换页数时调用的方法
 */
function internalLoadDatas(baseUrl, page, tableDiv, pagDiv, methods, reloadMethod) {
    resetDiv(tableDiv);
    resetDiv(pagDiv);
    $.ajax({
        type: 'GET',
        url: baseUrl + "/" + page,
        async: false,
        success: function (data) {
            if (data.code == Codes.successResponse) {
                generateTable(tableDiv, data.result, methods);
                initPag(data.result.total,data.result.current, data.result.pages, pagDiv, reloadMethod);
            }
            else {
                showErrorData(data);
            }

        }
    });
}

/**
 * 从后台获取数据并渲染-带关键字版
 * @param baseUrl 不带页码的后台数据url
 * @param keyword 关键字
 * @param page 请求的页数
 * @param tableDiv 前台展现数据的table的id
 * @param pagDiv 前台展现数据分页的id
 * @param methods 前台展现数据的table的操作方法
 * @param reloadMethod 更换页数时调用的方法
 */
function internalLoadDatasKeyword(baseUrl, page, tableDiv, pagDiv, methods, reloadMethod, keyword) {
    $.ajax({
        type: 'GET',
        url: baseUrl + "/" + keyword + "/" + page,
        async: false,
        success: function (data) {
            if (data.code == Codes.successResponse) {
                if(dataNotEmpty(data.result)){
                    resetDiv(tableDiv);
                    resetDiv(pagDiv);
                    generateTable(tableDiv, data.result, methods);
                    initPagKeyword(data.result.total, data.result.current, data.result.pages, pagDiv, reloadMethod, keyword);
                }
                else{
                    showErrorData(emptyDataTip);
                }
            }
            else {
                showErrorData(data);
            }

        }
    });
}


/**
 * 显示修改数据的模态框
 * @param pojo 修改的实体
 * @param data 修改的数据
 */
function initUpdateModal(pojo, data) {
    let $modal = $("#update-" + pojo + "-modal");
    $modal.on('show.bs.modal', function () {
        for (let key in data)
            $("#update-" + pojo + "-modal-" + key).val(data[key]);
    });
    $modal.modal("show");
}

/**
 * 删除指定数据
 * @param div 数据所在的表格的ID
 * @param id 数据的主键
 * @param sendUrl 数据的后台URL
 */
function internalDeleteData(div, id, sendUrl) {
    let sendData = {"id": id};
    if (confirm("是否删除数据?")) {
        $.ajax({
            type: 'DELETE',
            url: sendUrl,
            data: JSON.stringify(sendData),
            contentType: 'application/json',
            success: function (data) {
                if (data.code == Codes.successResponse) {
                    window.location.reload();
                }
                else {
                    showErrorData(data);
                }
            }
        });
    }
}

/**
 * 添加指定数据
 * @param pojo 修改的实体
 * @param sendUrl 数据的后台URL
 * @param keys 修改的实体的键
 */
function internalAddData(pojo, sendUrl) {
    if (pojo == "task" || $("#add-" + pojo + "-form").valid()) {
        let sendData = {};
        let subLength = ("add-" + pojo + "-modal-").length;
        $("#add-" + pojo + "-modal").find("[id^=add-" + pojo + "-modal-]").each(function () {
            let originId = $(this).attr("id");
            let key = originId.substring(subLength, originId.length);
            sendData[key] = $(this).val();
        });
        $.ajax({
            type: 'POST',
            url: sendUrl,
            data: JSON.stringify(sendData),
            contentType: 'application/json',
            success: function (data) {
                if (data.code == Codes.successResponse) {
                    alert(data.message);
                    $("#add-" + pojo + "-modal").modal("hide");
                    window.location.reload();
                }
                else
                    showErrorData(data);
            }
        });
    }
}

function internalUserAddData(pojo, sendUrl) {
    if (true) {
        let sendData = {};
        let subLength = ("add-" + pojo + "-modal-").length;
        $("#add-" + pojo + "-modal").find("[id^=add-" + pojo + "-modal-]").each(function () {
            let originId = $(this).attr("id");
            let key = originId.substring(subLength, originId.length);
            sendData[key] = $(this).val();
        });
        $.ajax({
            type: 'POST',
            url: sendUrl,
            data: JSON.stringify(sendData),
            contentType: 'application/json',
            success: function (data) {
                if (data.code == Codes.successResponse) {
                    alert(data.message);
                    window.location.reload();
                }
                else
                    showErrorData(data);
            }
        });
    }
}

/**
 * 修改指定数据
 * @param pojo 修改的实体
 * @param sendUrl 修改的数据后台的Url
 * @param keys 修改的实体的键
 */
function internalUpdateData(pojo, sendUrl) {
    if (pojo == "task" || $("#update-" + pojo + "-form").valid()) {
        let sendData = {};
        let subLength = ("update-" + pojo + "-modal-").length;
        $("#update-" + pojo + "-modal").find("[id^=update-" + pojo + "-modal-]").each(function () {
            let originId = $(this).attr("id");
            let key = originId.substring(subLength, originId.length);
            sendData[key] = $(this).val();
        });
        $.ajax({
            type: 'PUT',
            url: sendUrl,
            data: JSON.stringify(sendData),
            contentType: 'application/json',
            success: function (data) {
                if (data.code == Codes.successResponse) {
                    window.location.reload();
                }
                else {
                    showErrorData(data);
                }
            }
        });
    }
}

/**
 * 为选择框添加数据
 * @param div 选择框的Id
 * @param data 待添加的数据
 */
function initSelection(div, data) {
    resetDiv(div);
    let $div = $("#" + div);
    let str = "";
    if (checkDataEmpty(data) || !data instanceof Array) {
        str = emptyDataValue;
    } else {
        if (data.length == 1) {
            str += "<option value='" + data[0].id + "'>" + data[0].name + "</option>";
        } else {
            for (let i = 1; i < data.length; i++) {
                str += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
            }
        }
    }
    $div.append(str);
}

/**
 * 将秒数格式化为合适的时间
 * @param div 待格式化的Div的Id
 */
function formatSeconds(div) {
    let result = "";
    if (!checkElementEmpty(div)) {
        let val = parseInt($("#" + div).text());
        if (dataNotEmpty(val)) {
            let min = Math.floor(val / 60),
                second = parseInt(val % 60),
                day = 0, hour = 0;

            if (min >= 60) {
                hour = Math.floor(min / 60);
                min = min % 60;
            }
            else {
                hour = 0;
            }
            if (hour >= 24) {
                day = Math.floor(hour / 24);
                hour = hour % 24;
            }
            else {
                day = 0;
            }
            if (day > 0) result = day + "天";
            if (hour > 0) result += hour + "小时";
            if (min > 0) result += min + "分";
            if (second > 0) result += second + "秒";
        }
        $("#" + div).text(val > 0 ? result : emptyDataValue);
    }
}

/**
 * 根据单元格的值对该行进行着色
 * @param div 单元格
 * @param names 单元格的值域
 */
function paintColumn(div, names) {
    if (!checkElementEmpty(div)) {
        $("#" + div).parent().attr("class", names.find((element) => (element.name == $("#" + div).text())).class);
    }
}

/**
 * 将带年月日时分秒的时间格式化
 * @param 待格式化的Div的Id
 */
function formatDateTime(div) {
    if (!checkElementEmpty(div)) {
        let $div = $("#" + div), $val = parseInt($div.text()) * 1000;
        //此处乘1000是因为后台使用jdk8的timer，存储的是10位的数字，即秒数
        if(dataNotEmpty($div.text())){
            $div.text(new Date($val).toLocaleString());
        }
        else
            $div.text(emptyDataValue);
    }
}

/*-----------登录登出部分-----------*/
/**
 * 用户登录
 */
function login() {
    let data = {
        "id": $("#id").val(),
        "password": $("#pass").val()
    };
    $.ajax({
        type: 'POST',
        url: '/login',
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (data) {
            pageJump(data);
        }
    });
}

/*----------用户模块部分--------------*/

/**
 * 加载用户列表的数据
 * @param page 请求的页数
 */
function loadUsers(page) {
    internalLoadDatas(usersPath + "/list", page, "userTable", "usersPagination", userMethods, "loadUsers");
    $("#userTableBody").find("[id$='status']").each(function () {
        paintColumn($(this).attr("id"), allUserStatus);
    });
    $("#userTableBody").find("[id$='monthlyTime']").each(function () {
        formatDateTime($(this).attr("id"));
    });
}

/**
 * 显示修改用户的modal
 */
function updateUser(data) {
    initUpdateModal("user", data);
}

/**
 * 修改用户
 */
function doUpdateUser() {
    internalUpdateData("user", usersPath);
}

/**
 * 封禁用户
 */
function blockUser(data) {
    let sendData;
    if(data.status === Texts.user.ban){
        sendData = {"id": data.id, "status": Codes.user.normal, "name": data.name};
    }else{
        sendData = {"id": data.id, "status": Codes.user.ban, "name": data.name};
    }
    $.ajax({
        type: 'PUT',
        url: '/users',
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (data) {
                // $("#userTableTD-" + sendData.id + "-status").text(true);
                if (data.code == Codes.successResponse) {
                    alert(data.message);
                    window.location.reload();
                }
        }
    });
}

/**
 * 删除用户
 */
function deleteUser(data) {
    internalDeleteData("userTable", data.id, usersPath);
}

/**
 * 添加用户
 */
function addUser() {
    internalAddData("user", usersPath);
}

/**
 * 查询用户
 */
function searchUser(){
    let keyword = $("#search-user-form-input").val();
    if(dataNotEmpty(keyword)){
        internalLoadDatasKeyword(usersPath + "/list", 1, "userTable", "usersPagination", userMethods, "loadSearchUsers", keyword);
        $("#userTableBody").find("[id$='status']").each(function () {
            paintColumn($(this).attr("id"), allUserStatus);
        });
        $("#userTableBody").find("[id$='monthlyTime']").each(function () {
            formatDateTime($(this).attr("id"));
        });
    }
}

/**
 * 加载第二页开始的查询用户表格
 * @param keyword
 * @param page
 */
function loadSearchUsers(keyword,page){
    internalLoadDatasKeyword(usersPath + "/list", page, "userTable", "usersPagination", userMethods, "loadSearchUsers", keyword);
    $("#userTableBody").find("[id$='status']").each(function () {
        paintColumn($(this).attr("id"), allUserStatus);
    });
    $("#userTableBody").find("[id$='monthlyTime']").each(function () {
        formatDateTime($(this).attr("id"));
    });
}

/*----------------车辆模块部分-------------------------*/

/**
 * 加载区域列表的数据
 * @param page 请求的页数
 */
function loadAreas(page) {
    internalLoadDatas(areasPath + "/list", page, "areaTable", "areasPagination", areaMethods, "loadAreas");
    $("#areaTableBody").find("[id$='type']").each(function () {
        paintColumn($(this).attr("id"), allAreaType);
    });
}

/**
 * 显示修改区域的modal
 */
function updateArea(data) {
    initUpdateModal("area", data);
}

/**
 * 修改区域
 */
function doUpdateArea() {
    internalUpdateData("area", areasPath);
}

/**
 * 删除区域
 */
function deleteArea(data) {
    internalDeleteData("areaTable", data.id, areasPath);
}

/**
 * 加载添加区域的地图
 */
function loadAreaMap() {
    areaMap = new AMap.Map('container', {
        resizeEnable: true,
        center: [116.397428, 39.90923],
        zoom: 18
    });
    areaMap.on('click', function (e) {
        let centerPointX = parseFloat(e.lnglat.getLng());
        let centerPointY = parseFloat(e.lnglat.getLat());
        areaMap.setZoomAndCenter(18, [centerPointX, centerPointY]);
        var southWest = new AMap.LngLat(centerPointX, centerPointY);
        var northEast = new AMap.LngLat(parseFloat(centerPointX + 0.0005), parseFloat(centerPointY + 0.0002));
        var rectangle = new AMap.Rectangle({
            map: areaMap,
            bounds: new AMap.Bounds(southWest, northEast),
            strokeColor: 'red',
            strokeWeight: 10,
            strokeOpacity: 0.5,
            strokeDasharray: [30, 10],
            strokeStyle: 'dashed',
            fillColor: 'blue',
            fillOpacity: 0.5,
            zIndex: 10,
            bubble: true,
            cursor: 'pointer',
            bubble: false
        });
        rectangleEditor = new AMap.RectangleEditor(areaMap, rectangle);
    });

}

/**
 * 查询区域
 */
function searchArea(){
    let keyword = $("#search-area-form-input").val();
    if(dataNotEmpty(keyword)){
        internalLoadDatasKeyword(areasPath + "/list", 1, "areaTable", "areasPagination", areaMethods, "loadSearchAreas", keyword);
    }
}

/**
 * 加载第二页开始的查询区域表格
 * @param keyword
 * @param page
 */
function loadSearchAreas(keyword,page){
    internalLoadDatasKeyword(areasPath + "/list", page, "areaTable", "areasPagination", areaMethods, "loadSearchAreas", keyword);
}

/**
 * 开始划定停车区域
 */
function beginAddArea() {
    if (rectangleEditor !== undefined) {
        rectangleEditor.open();
    }
}

/**
 * 在区划内搜索含关键字的地点
 */
function searchCity() {
    let selectCode = $("#add-area-select-code").val();
    let keywords = $("#add-area-search").val();
    if (dataNotEmpty(keywords) && dataNotEmpty(selectCode)) {
        let sendData = {"keywords": keywords, "code": selectCode};
        $.ajax({
            type: 'POST',
            url: inputTipsPath,
            data: JSON.stringify(sendData),
            contentType: 'application/json',
            async: false,
            success: function (data) {
                if (data.code == Codes.successResponse) {
                    initSelection("add-area-search-result", data.result);
                }
                else {
                    showErrorData(data);
                }
            }
        });
    }
}

/**
 * 根据区划内搜索的选择结果更新停车点名称
 */
function refreshAreaName() {
    let selectedDiv = $("#add-area-search-result").find("option:selected");
    let selectedText = selectedDiv.text();
    let selectedValue = selectedDiv.val();
    if (dataNotEmpty(selectedText) && dataNotEmpty(selectedValue)) {
        let locations = selectedValue.replace(" ", "").split(",");
        areaMap.setZoomAndCenter(18, [locations[0], locations[1]]);
        $("#add-area-modal-name").val(selectedText + "停车点");
    }
}

/**
 * 添加区域
 */
function addArea() {
    let positionA = rectangleEditor.Fb[0].Ch.label.content.replace(" ", "").split(',');
    let positionB = rectangleEditor.Fb[1].Ch.label.content.replace("", "").split(',');
    let name = $("#add-area-modal-name").val();
    let cityId = $("#add-area-modal-cityId").val();
    let type = $("#add-area-modal-type").val();
    if (dataNotEmpty(name) && dataNotEmpty(cityId)) {
        let sendData = {
            "name": name,
            "northPoint": parseFloat(positionB[1]),
            "southPoint": parseFloat(positionA[1]),
            "westPoint": parseFloat(positionA[0]),
            "eastPoint": parseFloat(positionB[0]),
            "cityId": cityId,
            "type": type
        };
        $.ajax({
            type: 'POST',
            url: areasPath,
            data: JSON.stringify(sendData),
            async: false,
            contentType: 'application/json',
            success: function (data) {
                if (data.code == Codes.successResponse) {
                    alert(data.message);
                    window.location.reload();
                }
                else
                    showErrorData(data);
            }
        });
    }
}

/**
 * 加载供应商列表的数据
 * @param page 请求的页数
 */
function loadSuppliers(page) {
    internalLoadDatas(suppliersPath + "/list", page, "supplierTable", "suppliersPagination", supplierMethods, "loadSuppliers");
}

/**
 * 显示修改供应商的modal
 */
function updateSupplier(data) {
    initUpdateModal("supplier", data);
}

/**
 * 修改供应商
 */
function doUpdateSupplier() {
    internalUpdateData("supplier", suppliersPath);
}

/**
 * 删除供应商
 */
function deleteSupplier(data) {
    internalDeleteData("supplierTable", data.id, suppliersPath);
}

/**
 * 添加供应商
 */
function addSupplier() {
    internalAddData("supplier", suppliersPath);
}

/**
 * 查询供应商
 */
function searchSupplier(){
    let keyword = $("#search-supplier-form-input").val();
    if(dataNotEmpty(keyword)){
        internalLoadDatasKeyword(suppliersPath + "/list", 1, "supplierTable", "suppliersPagination", supplierMethods, "loadSearchSuppliers", keyword);
    }
}

/**
 * 加载第二页开始的查询供应商表格
 * @param keyword
 * @param page
 */
function loadSearchSuppliers(keyword,page){
    internalLoadDatasKeyword(suppliersPath + "/list", page, "supplierTable", "suppliersPagination", supplierMethods, "loadSearchSuppliers", keyword);
}

/**
 * 加载车辆列表的数据
 * @param page 请求的页数
 */
function loadBicycles(page) {
    internalLoadDatas(bicyclesPath + "/list", page, "bicycleTable", "bicyclesPagination", bicycleMethods, "loadBicycles");
    $("#bicycleTableBody").find("[id$='serviceTime']").each(function () {
        formatSeconds($(this).attr("id"));
    });
    $("#bicycleTableBody").find("[id$='status']").each(function () {
        paintColumn($(this).attr("id"), allBicycleStatus);
    });
    $("#bicycleTableBody").find("[id$='investmentTime']").each(function () {
        formatDateTime($(this).attr("id"));
    });
    initSelection("add-bicycle-modal-type", allBicycleType);
    initBicycleSupplier();
}

/**
 * 加载移动车辆的函数
 * @param data 车辆的信息
 */
function loadMoveBicycle(data) {
    if (checkBicycleStatus("移动", data.status)) {
        moveBicycle(data.id);
    }
}

/**
 * 加载修理车辆函数
 * @param data 车辆的信息
 */
function loadRepairBicycle(data) {
    if (checkBicycleStatus("修理", data.status)) {
        repairBicycle(data.id);
    }
}

/**
 * 加载报废车辆函数
 * @param data 车辆的信息
 */
function loadScrapeBicycle(data) {
    if (checkBicycleStatus("报废", data.status)) {
        scrapeBicycle(data.id);
    }
}

/**
 * 删除车辆
 */
function deleteBicycle(data) {
    if (checkBicycleStatus("删除", data.status)) {
        internalDeleteData("bicycleTable", data.id, bicyclesPath);
    }
}

/**
 * 添加车辆
 */
function addBicycle() {
    internalAddData("bicycle", bicyclesPath);
}

/**
 * 为车辆加载供应商数据
 */
function initBicycleSupplier() {
    $.ajax({
        type: 'GET',
        url: allSuppliersPath,
        sync: false,
        success: function (data) {
            if (data.code == Codes.successResponse && dataNotEmpty(data))
                initSelection("add-bicycle-modal-supplier", data.result);
        }
    });
}

/**
 * 根据车辆状态检测能够执行操作
 * @param type 操作类型
 * @param status 车辆状态
 * @returns {boolean}
 */
function checkBicycleStatus(type, status) {
    // alert("type-->" + type + ", status-->" + status);
    let checkResult = false;
    if (type === "删除" && status === "待删除") {
        checkResult = true;
    }
    if (status === "空闲中") {
        checkResult = true;
    }
    if (!checkResult) {
        alert("无法执行操作");
    }
    return checkResult;
}

/**
 * 查询车辆
 */
function searchBicycle(){
    let keyword = $("#search-bicycle-form-input").val();
    if(dataNotEmpty(keyword)){
        internalLoadDatasKeyword(bicyclesPath + "/list", 1, "bicycleTable", "bicyclesPagination", bicycleMethods, "loadSearchBicycles", keyword);
        $("#bicycleTableBody").find("[id$='serviceTime']").each(function () {
            formatSeconds($(this).attr("id"));
        });
        $("#bicycleTableBody").find("[id$='status']").each(function () {
            paintColumn($(this).attr("id"), allBicycleStatus);
        });
        $("#bicycleTableBody").find("[id$='investmentTime']").each(function () {
            formatDateTime($(this).attr("id"));
        });
        initSelection("add-bicycle-modal-type", allBicycleType);
        initBicycleSupplier();
    }
}

/**
 * 加载第二页开始的查询车辆表格
 * @param keyword
 * @param page
 */
function loadSearchBicycles(keyword,page){
    internalLoadDatasKeyword(bicyclesPath + "/list", page, "bicycleTable", "bicyclesPagination", bicycleMethods, "loadSearchBicycles", keyword);
    $("#bicycleTableBody").find("[id$='serviceTime']").each(function () {
        formatSeconds($(this).attr("id"));
    });
    $("#bicycleTableBody").find("[id$='status']").each(function () {
        paintColumn($(this).attr("id"), allBicycleStatus);
    });
    $("#bicycleTableBody").find("[id$='investmentTime']").each(function () {
        formatDateTime($(this).attr("id"));
    });
    initSelection("add-bicycle-modal-type", allBicycleType);
    initBicycleSupplier();
}


/*---------------订单模块-----------------*/
/**
 * 加载行程列表的数据
 * @param page 请求的页数
 */
function loadJourneys(page) {
    internalLoadDatas(journeysPath + "/list", page, "journeyTable", "journeysPagination", journeyMethods, "loadJourneys");
    $("#journeyTableBody").find("[id$='rideTime']").each(function () {
        formatSeconds($(this).attr("id"));
    });
}

/**
 * 将字符串转换为二维数组,用于高德地图轨迹回放
 * @param data 源数据
 * @returns {any[]} 二维数组
 */
function convertTo2Array(data){
    let datas = data.split(",");
    let result = new Array();
    for(let i = 0; i < data.length / 2;i = i + 2){
        let point = new Array();
        point.push(datas[i]);
        point.push(datas[i+1]);
        result.push(point);
    }
    return result;
}

//加载行车轨迹
function showPath(data) {
    pathMap = new AMap.Map('journeyDiv', {
        resizeEnable: true,
        zoom: 18,
        center: [113.50927, 34.810942]
    });
    AMapUI.load(['ui/misc/PathSimplifier'], function (PathSimplifier) {
        if (!PathSimplifier.supportCanvas) {
            alert('当前环境不支持 Canvas！');
            return;
        }
        //创建组件实例
        let pathSimplifierIns = new PathSimplifier({
            zIndex: 100,
            map: pathMap, //所属的地图实例
            getPath: function (pathData, pathIndex) {
                return pathData.path;
            },
            renderOptions: {
                //轨迹线的样式
                pathLineStyle: {
                    strokeStyle: 'red',
                    lineWidth: 6,
                    dirArrowStyle: true
                }
            }
        });
        pathSimplifierIns.setData([{
            name: '轨迹0',
            path: convertTo2Array(data.path)
        }]);
        let navg0 = pathSimplifierIns.createPathNavigator(0, //关联第1条轨迹
            {
                speed: 200
            });
        navg0.start();
    });
    $("#show-journey-modal").modal("show");

}

/**
 * 查询行程记录
 */
function searchJourney(){
    let keyword = $("#search-journey-form-input").val();
    if(dataNotEmpty(keyword)){
        internalLoadDatasKeyword(journeysPath + "/list", 1, "journeyTable", "journeysPagination", journeyMethods, "loadSearchJourneys", keyword);
        $("#journeyTableBody").find("[id$='rideTime']").each(function () {
            formatSeconds($(this).attr("id"));
        });
    }
}

/**
 * 加载第二页开始的查询行程记录
 * @param keyword
 * @param page
 */
function loadSearchJourneys(keyword,page){
    internalLoadDatasKeyword(journeysPath + "/list", page, "journeyTable", "journeysPagination", journeyMethods, "loadSearchJourneys", keyword);
    $("#journeyTableBody").find("[id$='rideTime']").each(function () {
        formatSeconds($(this).attr("id"));
    });
}

/**
 * 加载充值记录
 * @param page 页码
 */
function loadRecharges(page) {
    internalLoadDatas(rechargesPath, page, "rechargeTable", "rechargesPagination", [], "loadRecharges");
    $("#rechargeTableBody").find("[id$='rechargeTime']").each(function () {
        formatDateTime($(this).attr("id"));
    });
}

/**
 * 查询充值记录
 */
function searchRecharge(){
    let keyword = $("#search-recharge-form-input").val();
    if(dataNotEmpty(keyword)){
        internalLoadDatasKeyword(rechargesPath, 1, "rechargeTable", "rechargesPagination", [], "loadSearchRecharges", keyword);
        $("#rechargeTableBody").find("[id$='rechargeTime']").each(function () {
            formatDateTime($(this).attr("id"));
        });
    }
}

/**
 * 加载第二页开始的查询充值记录
 * @param keyword
 * @param page
 */
function loadSearchRecharges(keyword,page){
    internalLoadDatasKeyword(rechargesPath, page, "rechargeTable", "rechargesPagination", [], "loadSearchRecharges", keyword);
    $("#rechargeTableBody").find("[id$='rechargeTime']").each(function () {
        formatDateTime($(this).attr("id"));
    });
}

/**
 * 加载押金记录
 * @param page 页码
 */
function loadDeposits(page) {
    internalLoadDatas(userDepositPath + "/list", page, "depositTable", "depositsPagination", [], "loadDeposits");
    $("#rechargeTableBody").find("[id$='operateTime']").each(function () {
        formatDateTime($(this).attr("id"));
    });
}

/**
 * 查询押金记录
 */
function searchDeposit(){
    let keyword = $("#search-deposit-form-input").val();
    if(dataNotEmpty(keyword)){
        internalLoadDatasKeyword(userDepositPath + "/list", 1, "depositTable", "depositsPagination", [], "loadSearchDeposits", keyword);
        $("#depositTableBody").find("[id$='operateTime']").each(function () {
            formatDateTime($(this).attr("id"));
        });
    }
}

/**
 * 加载第二页开始的查询押金记录
 * @param keyword
 * @param page
 */
function loadSearchDeposits(keyword,page){
    internalLoadDatasKeyword(userDepositPath + "/list", page, "depositTable", "depositsPagination", [], "loadSearchDeposits", keyword);
    $("#depositTableBody").find("[id$='operateTime']").each(function () {
        formatDateTime($(this).attr("id"));
    });
}

/**
 * 加载包月记录
 * @param page 页码
 */
function loadVips(page) {
    internalLoadDatas(vipPath + "/list", page, "vipTable", "vipsPagination", [], "loadVips");
    $("#vipTableBody").find("[id$='operateTime']").each(function () {
        formatDateTime($(this).attr("id"));
    });
}

/**
 * 查询包月记录
 */
function searchVip(){
    let keyword = $("#search-vip-form-input").val();
    if(dataNotEmpty(keyword)){
        internalLoadDatasKeyword(vipPath + "/list", 1, "vipTable", "vipsPagination", [], "loadSearchVips", keyword);
        $("#vipTableBody").find("[id$='operateTime']").each(function () {
            formatDateTime($(this).attr("id"));
        });
    }
}

/**
 * 加载第二页开始的查询包月记录
 * @param keyword
 * @param page
 */
function loadSearchVips(keyword,page){
    internalLoadDatasKeyword(vipPath + "/list", page, "vipTable", "vipsPagination", [], "loadSearchVips", keyword);
    $("#vipTableBody").find("[id$='operateTime']").each(function () {
        formatDateTime($(this).attr("id"));
    });
}

/*---------------任务模块----------------*/

/**
 * 为任务加载员工信息
 * @param type 待填充的div所属的操作类型 ： add || update
 */
function initTaskUser(type) {
    $.ajax({
        type: 'GET',
        url: allStaffsPath,
        sync: false,
        success: function (data) {
            if (data.code == Codes.successResponse && dataNotEmpty(data.result)) {
                $("#" + type + "-task-modal-user").append("<option value='" + emptyStaff.id + "'>" + emptyStaff.name + "</option>");
                initSelection(type + "-task-modal-user", data.result);
            }
        }
    });
}

/**
 * 加载所有车辆的位置信息
 */
function loadSimpleBicycles() {
    $.ajax({
        type: 'GET',
        url: allBicyclesPath,
        contentType: 'application/json',
        success: function (data) {
            if (data.code == Codes.successResponse && dataNotEmpty(data.result)) {
                let result = data.result;
                let cluster, markers = [];
                bicycleMap = new AMap.Map("container", {
                    resizeEnable: true,
                    center: [105, 34],
                    zoom: 4
                });
                for (let i = 0; i < result.length; i += 1) {
                    let marker = new AMap.Marker({
                        position: [result[i].locationX, result[i].locationY],
                        icon: allBicycleStatus.find((element) => (element.name == result[i].status)).icon
                    });
                    let title = "车辆信息", content = [];
                    content.push("编号 : " + result[i].id);
                    content.push("状态 : " + result[i].status);
                    let infoWindow = new AMap.InfoWindow({
                        isCustom: true,
                        content: createInfoWindow(title, content.join("<br/>")),
                        offset: new AMap.Pixel(0, -30)
                    });
                    marker.on('click', function () {
                        infoWindow.open(bicycleMap, marker.getPosition());
                    });
                    if (result[i].status === "使用中") marker.setAnimation('AMAP_ANIMATION_BOUNCE');
                    markers.push(marker);
                }
                bicycleMap.plugin(["AMap.MarkerClusterer"], function () {
                    cluster = new AMap.MarkerClusterer(bicycleMap, markers);
                });
            }
        }
    });
}

//构建自定义信息窗体
function createInfoWindow(title, content) {
    let info = document.createElement("div");
    info.className = "info";

    //可以通过下面的方式修改自定义窗体的宽高
    //info.style.width = "400px";
    // 定义顶部标题
    let top = document.createElement("div");
    let titleD = document.createElement("div");
    let closeX = document.createElement("img");
    top.className = "info-top";
    titleD.innerHTML = title;
    closeX.src = "http://webapi.amap.com/images/close2.gif";
    closeX.onclick = closeInfoWindow;

    top.appendChild(titleD);
    top.appendChild(closeX);
    info.appendChild(top);

    // 定义中部内容
    let middle = document.createElement("div");
    middle.className = "info-middle";
    middle.style.backgroundColor = 'white';
    middle.innerHTML = content;
    info.appendChild(middle);

    return info;
}

//关闭信息窗体
function closeInfoWindow() {
    bicycleMap.clearInfoWindow();
}

/**
 * 移动车辆
 * @param data 车辆信息
 */
function moveBicycle(data) {
    data.type = "移动";
    if (checkBicycleStatus(data.type, data.status)) {
        initAddTaskModal(data);
    }
}

/**
 * 修理车辆
 * @param data 车辆信息
 */
function repairBicycle(data) {
    data.type = "修理";
    if (checkBicycleStatus(data.type, data.status)) {
        initAddTaskModal(data);
    }
}

/**
 * 报废车辆
 * @param data 车辆信息
 */
function scrapeBicycle(data) {
    data.type = "报废";
    if (checkBicycleStatus(data.type, data.status)) {
        initAddTaskModal(data);
    }
}

/**
 * 加载任务信息
 * @param page 指定的页数
 */
function loadTasks(page) {
    internalLoadDatas(taskPath + "/list", page, "taskTable", "tasksPagination", taskMethods, "loadTasks");
    $("#taskTableBody").find("[id$='status']").each(function () {
        if ($(this).text() === '已完成')
            $(this).parent().find("[id$='Btn']").empty();
        paintColumn($(this).attr("id"), allTaskStatus);
    });
    $("#taskTableBody").find("[id$='taskTime']").each(function () {
        // formatDateTime($(this).attr("id"));
        formatSeconds($(this).attr("id"));
    });
}

/**
 *  显示分配任务
 * @param data
 */
function dispatchTask(data) {
    let $modal = $("#update-task-modal");
    let modalCount = 0;
    $modal.on('show.bs.modal', function () {
        if (modalCount === 0) {
            $("#update-task-modal-id").val(data.id);
            $("#update-task-modal-name").val(data.name);
            initSelection("update-task-modal-type", castObjectToArray(allTaskType.find((element) => (element.name == data.type))));
            initTaskUser("update");
            $("#update-task-modal-bicycle").val(data.bicycle);
            modalCount++;
        }
    });
    $modal.modal("show");
}

function doDispatchTask() {
    internalUpdateData("task", taskPath);
}

/**
 * 完成任务
 * @param data 传入的任务数据
 */
function doneTask(data) {
    let sendData = {
        "id": data.id,
        "type": allTaskType.find((element) => (element.name == data.type)).id,
        "bicycle": data.bicycle
    };
    $.ajax({
        type: 'POST',
        url: taskPath + "/done",
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (data) {
            if (data.code == Codes.successResponse) {
                window.location.reload();
            }
            else {
                showErrorData(data);
            }
        }
    });
}

/**
 * 取消任务
 */
function cancelTask(data) {
    internalDeleteData("taskTable", data.id, taskPath);
}

/**
 * 添加任务
 */
function addTask() {
    internalAddData("task", taskPath);
}

/**
 * 查询任务记录
 */
function searchTask(){
    let keyword = $("#search-task-form-input").val();
    if(dataNotEmpty(keyword)){
        internalLoadDatasKeyword(taskPath + "/list", 1, "taskTable", "tasksPagination", taskMethods, "loadSearchTasks", keyword);
        $("#taskTableBody").find("[id$='status']").each(function () {
            if ($(this).text() === '已完成')
                $(this).parent().find("[id$='Btn']").empty();
            paintColumn($(this).attr("id"), allTaskStatus);
        });
        $("#taskTableBody").find("[id$='taskTime']").each(function () {
            // formatDateTime($(this).attr("id"));
            formatSeconds($(this).attr("id"));
        });
    }
}

/**
 * 加载第二页开始的查询任务记录
 * @param keyword
 * @param page
 */
function loadSearchTasks(keyword,page){
    internalLoadDatasKeyword(taskPath + "/list", page, "taskTable", "tasksPagination", taskMethods, "loadSearchTasks", keyword);
    $("#taskTableBody").find("[id$='status']").each(function () {
        if ($(this).text() === '已完成')
            $(this).parent().find("[id$='Btn']").empty();
        paintColumn($(this).attr("id"), allTaskStatus);
    });
    $("#taskTableBody").find("[id$='taskTime']").each(function () {
        // formatDateTime($(this).attr("id"));
        formatSeconds($(this).attr("id"));
    });
}

/**
 * 显示车辆分布页面的添加任务模态框
 * @param data 车辆信息
 */
function initAddTaskModal(data) {
    let $modal = $("#add-task-modal");
    resetModal("add-task-modal");
    initTaskUser("add");
    let modalCount = 0;
    $modal.on('show.bs.modal', function () {
        if (modalCount === 0) {
            initSelection("add-task-modal-type", castObjectToArray(allTaskType.find((element) => (element.name == data.type))));
            $("#add-task-modal-bicycle").val(data.id);
            modalCount++;
        }
    });
    $modal.modal("show");
}

/**
 * 加载申请信息
 * @param page 指定的页数
 */
function loadApplies(page) {
    internalLoadDatas(appliesPath + "/list", page, "applyTable", "appliesPagination", applyMethods, "loadApplies");
    $("#applyTableBody").find("[id$='Time']").each(function () {
        formatDateTime($(this).attr("id"));
    });
    $("#applyTableBody").find("[id$='status']").each(function () {
        if ($(this).text() === '处理完成')
            $(this).parent().find("[id$='Btn']").empty();
        // paintColumn($(this).attr("id"), allTaskStatus);
    });
}

/**
 * 完成用户申请
 * @param apply 用户申请
 */
function doneApply(apply){
    //todo : put apply
    let sendData = {"id": parseInt(apply.id),"type": apply.type,"userId": apply.userId,"amount": apply.amount};
    $.ajax({
        type: 'PUT',
        url: appliesPath,
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (data) {
            if (data.code == Codes.successResponse) {
                alert(JSON.stringify(data.message));
                window.location.reload();
            }
            else {
                showErrorData(data);
            }
        }
    });
}


/**
 * 查询申请记录
 */
function searchApply(){
    let keyword = $("#search-apply-form-input").val();
    if(dataNotEmpty(keyword)){
        internalLoadDatasKeyword(appliesPath + "/list", 1, "applyTable", "appliesPagination", applyMethods, "loadSearchApplies", keyword);
        $("#applyTableBody").find("[id$='Time']").each(function () {
            formatDateTime($(this).attr("id"));
        });
        $("#applyTableBody").find("[id$='status']").each(function () {
            if ($(this).text() === '处理完成')
                $(this).parent().find("[id$='Btn']").empty();
            // paintColumn($(this).attr("id"), allTaskStatus);
        });
    }
}

/**
 * 加载第二页开始的查询申请记录
 * @param keyword
 * @param page
 */
function loadSearchApplies(keyword,page){
    internalLoadDatasKeyword(appliesPath + "/list", page, "applyTable", "appliesPagination", applyMethods, "loadSearchApplies", keyword);
    $("#applyTableBody").find("[id$='Time']").each(function () {
        formatDateTime($(this).attr("id"));
    });
    $("#applyTableBody").find("[id$='status']").each(function () {
        if ($(this).text() === '处理完成')
            $(this).parent().find("[id$='Btn']").empty();
        // paintColumn($(this).attr("id"), allTaskStatus);
    });
}

/*--------------------------------------------报表模块------------------------------*/

/**
 * 选择城市后提交查询
 * @param type 1-行程;2-任务
 */
function setFilterCity(type){
    let districtCode = $("#select-district").data("code");
    let cityCode = $("#select-city").data("code");
    let provinceCode = $("#select-province").data("code");
    let selectCode;
    if(districtCode !== undefined) selectCode = districtCode;
    else if (cityCode !== undefined) selectCode = cityCode;
    else if(provinceCode !== undefined) selectCode = provinceCode;
    else alert("请选择行政区划!!");
    if(selectCode !== undefined){
        $.ajax({
            type: 'POST',
            url : searchCityPath,
            sync : false,
            contentType : 'application/json',
            data : JSON.stringify(selectCode),
            success : function(data) {
                if(data.code == Codes.successResponse && !checkDataEmpty(data.result)){
                    if(type == 1){
                        $("#overview-report-city-id").val(data.result.id);
                        journeyReportSubmit(true);
                    }else{
                        $("#task-report-city-id").val(data.result.id);
                        taskReportSubmit(true);
                    }
                }
            }
        });
        this.unbind();
        this.$element.removeData(NAMESPACE).removeClass('city-picker-input');
        this.$textspan.remove();
        this.$dropdown.remove();
    }
}


/**
 * 提交条件获取行程概况报表
 */
function journeyReportSubmit(appendMode){
    let statisticalType = parseInt($("#overview-report-statistical-type :selected").val());
    let timeType = parseInt($("#overview-report-group-type :selected").val());
    let chartType = $("#overview-report-chart-type :selected").val();
    let startDate = $("#overview-report-start-date").val();
    let endDate = $("#overview-report-end-date").val();
    let cityId = parseInt($("#overview-report-city-id").val());
    let sendData = {"chartType" : chartType, "statisticalType" : statisticalType, "groupType" : timeType, "startDate" : startDate, "endDate" : endDate, "cityId" : cityId};
    $.ajax({
        type: 'POST',
        url: overviewReportPath,
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (data) {
            if (data.code == Codes.successResponse) {
                let dataChartType = data.result.chartType;
                if(dataChartType === "pie"){
                    overviewReportChart = Highcharts.chart('report-overview-div', {
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false,
                            type: 'pie'
                        },
                        title: {
                            text: data.result.title
                        },
                        tooltip: {
                            pointFormat: '{point.y}: <b>占比{point.percentage:.1f}%</b>'
                        },
                        plotOptions: {
                            pie: {
                                allowPointSelect: true,
                                cursor: 'pointer',
                                showInLegend: true,
                                dataLabels: {
                                    enabled: true,
                                    formatter: function () {
                                        return '<span style="color: ' + this.point.color + '"> 值：' + this.y + '，占比' +  parseFloat(this.percentage).toFixed(2) + '%</span>';
                                    }
                                  }
                                }
                        },
                        series: [{
                            name: 'Brands',
                            colorByPoint: true,
                            data: data.result.seriesData
                        }]
                    });
                }else{
                    let appendData;
                    appendData = Array.from(data.result.seriesData);
                    if(appendMode){
                        appendData.forEach(function(obj){
                           overviewReportOptions.series.push(obj);
                        });
                    }
                    else{
                        overviewReportOptions.series = appendData;
                    }
                    overviewReportOptions.chart.type = dataChartType;
                    overviewReportOptions.xAxis.categories = data.result.xAxis;
                    overviewReportChart = Highcharts.chart('report-overview-div', overviewReportOptions);
                }
            }
            else {
                showErrorData(data);
            }
        }
    });
}

/**
 * 重置图表页面的图表
 * @param opt 图表的options
 * @param chart 图表的chart
 */
function resetChart(opt, chart){
    // overviewReportOptions.series = [];
    // while(overviewReportChart.series.length){
    //     overviewReportChart.series[0].remove();
    // }
    opt.series = [];
    while(chart.series.length){
        for(let i = 0; i < chart.series.length; i++){
            chart.series[i].remove();
        }
    }
}

/**
 * 提交条件获取任务概况报表
 */
function taskReportSubmit(appendMode){
    let statisticalType = parseInt($("#task-report-statistical-type :selected").val());
    let timeType = parseInt($("#task-report-group-type :selected").val());
    let chartType = $("#task-report-chart-type :selected").val();
    let startDate = $("#task-report-start-date").val();
    let endDate = $("#task-report-end-date").val();
    let cityId = parseInt($("#task-report-city-id").val());
    let taskType = parseInt($("#task-report-task-type :selected").val());
    let sendData = {"chartType" : chartType, "statisticalType" : statisticalType, "groupType" : timeType, "taskType" : taskType, "startDate" : startDate, "endDate" : endDate, "cityId" : cityId};
    $.ajax({
        type: 'POST',
        url: taskReportPath,
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (data) {
            if (data.code == Codes.successResponse) {
                let dataChartType = data.result.chartType;
                if(dataChartType === "pie"){
                    taskReportChart = Highcharts.chart('report-task-div', {
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false,
                            type: 'pie'
                        },
                        title: {
                            text: data.result.title
                        },
                        tooltip: {
                            pointFormat: '{point.y}: <b>占比{point.percentage:.1f}%</b>'
                        },
                        plotOptions: {
                            pie: {
                                allowPointSelect: true,
                                cursor: 'pointer',
                                showInLegend: true,
                                dataLabels: {
                                    enabled: true,
                                    formatter: function () {
                                        return '<span style="color: ' + this.point.color + '"> 值：' + this.y + '，占比' +  parseFloat(this.percentage).toFixed(2) + '%</span>';
                                    }
                                }
                            }
                        },
                        series: [{
                            name: 'Brands',
                            colorByPoint: true,
                            data: data.result.seriesData
                        }]
                    });
                }else{
                    let appendData;
                    appendData = Array.from(data.result.seriesData);
                    if(appendMode){
                        appendData.forEach(function(obj){
                            taskReportOptions.series.push(obj);
                        });
                    }
                    else{
                        taskReportOptions.series = appendData;
                    }
                    taskReportOptions.chart.type = dataChartType;
                    taskReportOptions.xAxis.categories = data.result.xAxis;
                    taskReportChart = Highcharts.chart('report-task-div', taskReportOptions);
                }
            }
            else {
                showErrorData(data);
            }
        }
    });
}

/*--------------------------------------------用户版------------------------------*/

/**
 * 用户充值
 */
function userRecharge() {
    internalUserAddData("recharge", userRechargePath);
}

/**
 * 加载用户充值记录
 * @param page 页码
 */
function loadUserRecharges(page) {
    internalLoadDatas(userRechargesPath, page, "rechargeTable", "rechargesPagination", [], "loadUserRecharges");
}

/**
 * 用户操作押金
 */
function userOperateDeposit() {
    internalUserAddData("deposit", userDepositPath);
}

/**
 * 用户提交申请
 */
function userSubmitApply(){
    let sendData = {
        "type": parseInt($("#add-apply-modal-type").val()),
        "status"  : parseInt($("#add-apply-modal-status").val()),
        "amount" : parseFloat($("#add-apply-modal-amount").val()),
        "description" : $("#add-apply-modal-description").val()
    };
    $.ajax({
        type: 'POST',
        url: appliesPath,
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (data) {
            if (data.code == Codes.successResponse) {
                alert(JSON.stringify(data.result));
                window.location.reload();
            }
            else {
                showErrorData(data);
            }
        }
    });
}

/**
 * 用户购买包月
 */
function userVip() {
    let sendData = {"vipTime" : parseInt($("#add-vip-modal-vipTime").val())};
    $.ajax({
        type: 'POST',
        url: vipPath,
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (data) {
            if (data.code == Codes.successResponse) {
                alert(data.message);
                window.location.reload();
            }
            else
                showErrorData(data);
        }
    });
}