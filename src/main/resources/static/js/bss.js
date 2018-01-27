/**
 * Created by Mo on 2017/10/2.
 */
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
(function ($) {

    $.each($.validator.methods, function (key, value) {
        $.validator.methods[key] = function () {
            if(arguments.length > 0) {
                arguments[0] = $.trim(arguments[0]);
            }

            return value.apply(this, arguments);
        };
    });
} (jQuery));

/*---------------公共函数----------------*/
/**
 * 页面跳转
 * @param data 待跳转的页面
 */
function pageJump(data) {
    if (!checkDataEmpty(data)) {
        self.location.href = data;
    }
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
 * 生成表格
 * @param div 待操作的表格
 * @param data 待填充的数据
 * @param 每行数据待设置的操作
 */
function generateTable(div, data, methods) {
    if (!checkElementEmpty(div)) {
        let $div = $("#" + div);
        if (!checkDataEmpty(data)) {
            //设置表头
            if (!checkDataEmpty(data.names))
                $div.append(generateBatchTableHeader(data.names));
            //设置数据
            if (!checkDataEmpty(data.datas) && !checkDataEmpty(data.keys))
                $div.append(generateBatchTableData(div, data.datas, data.keys, methods));
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
    if (!checkDataEmpty(data)) {
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
    if (!checkDataEmpty(data) && !checkDataEmpty(keys)) {
        let strPre = "<tr id='" + div + "TR-" + data.id + "'>";
        let strData = "";
        for (let i = 0; i < keys.length; i++) {
            strData += generateSingleTD(div + "TD-" + data.id + "-" + keys[i], data[keys[i]]);
            // "<td id='" + div + "TD-" + data.id + "-" + keys[i] + "'>" + data[keys[i]] + "</td>";
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
    let strPre = !checkDataEmpty(methods) ? "<td id='" + div + "-" + data.id +  "-Btn" + "'>" : "<td id=' " + div + "-" + data.id + "-Btn" +"' style='text-align: center;line-height: 100%'>";
    let strData = "";
    if (!checkDataEmpty(methods)) {
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
 * @param currentPage 当前页码
 * @param totalPage 总页码
 * @param id 分页的ID
 * @param method 每个分页按钮触发的函数
 */
function initPag(currentPage, totalPage, id, method) {
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
            generateTable(tableDiv, data, methods);
            initPag(data.currentPage, data.totalPage, pagDiv, reloadMethod);
        }
    });
}


/**
 * 显示修改数据的模态框
 * @param pojo 修改的实体
 * @param data 修改的数据
 * @param keys 修改的数据的key
 */
function initModal(pojo, data, keys) {
    let $modal = $("#update-" + pojo + "-modal");
    $modal.on('show.bs.modal', function (event) {
        for (let i = 0; i < keys.length; i++)
            $("#update-" + pojo + "-modal-" + keys[i]).val(data[keys[i]]);
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
    $.ajax({
        type: 'DELETE',
        url: sendUrl,
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (data) {
            if (data === true) {
                $("#" + div + "TR-" + sendData.id).empty();
                window.location.reload();
            }
        }
    });
}

/**
 * 添加指定数据
 * @param pojo 修改的实体
 * @param sendUrl 数据的后台URL
 * @param keys 修改的实体的键
 */
function internalAddData(pojo, sendUrl, keys) {
    if(checkElementEmpty("add-" + pojo + "-form") || $("#add-" + pojo + "-form").valid()){
        //if中的第一个条件是考虑到bicycles页面addTask表单不能设置id的情况，此情况待解决
        let sendData = {};
        for (let i = 0; i < keys.length; i++)
            sendData[keys[i]] = $("#add-" + pojo + "-modal-" + keys[i]).val();
        $.ajax({
            type: 'POST',
            url: sendUrl,
            data: JSON.stringify(sendData),
            contentType: 'application/json',
            success: function (data) {
                if (data === true) {
                    alert("添加数据成功");
                    $("#add-" + pojo + "-modal").modal("hide");
                    window.location.reload();
                }
                else
                    alert("未成功添加数据");
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
function internalUpdateData(pojo, sendUrl, keys) {
    if($("#update-" + pojo + "-form").valid()){
        let sendData = {};
        for (let i = 0; i < keys.length; i++)
            sendData[keys[i]] = $("#update-" + pojo + "-modal-" + keys[i]).val();
        $.ajax({
            type: 'PUT',
            url: sendUrl,
            data: JSON.stringify(sendData),
            contentType: 'application/json',
            success: function (data) {
                if (data === true) {
                    $("#update-" + pojo + "-modal").modal("hide");
                    let $base = "#" + pojo + "TableTD-" + sendData.id + "-";
                    for (let i = 1; i < keys.length; i++)
                        $($base + keys[i]).text(sendData[keys[i]]);
                    window.location.reload();
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
    let $div = $("#" + div);
    let str = "";
    for (let i = 0; i < data.length; i++)
        str += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
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
        if (!checkDataEmpty(val)) {
            let min = Math.floor(val / 60),
                second = val % 60,
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
            if(day > 0) result = day + "天";
            if(hour > 0) result += hour + "小时";
            if(min > 0 ) result += min + "分";
            if(second > 0) result += second + "秒";
        }
        $("#" + div).text(val >0 ? result : emptyDataValue);
    }
}

/**
 * 将ID格式化为对应的文字显示
 * @param div 待格式化的Div
 * @param names ID文字对应的数组
 */
function formateIndexToName(div,names) {
    let result = emptyDataValue;
    if (!checkElementEmpty(div)) {
        let index = "";
        if($("#" + div).text() === 'true')
            index = 1;
        else if($("#" + div).text() === 'false')
            index = 0;
        else
            index = parseInt($("#" + div).text());
        if (!checkDataEmpty(index)) result = names[index].name;
        $("#" + div).text(result);
        $("#" + div).parent().attr("class",names[index].class);
    }
}

/**
 * 将带年月日时分秒的时间格式化
 * @param 待格式化的Div的Id
 */
function formatDateTime(div) {
    if (!checkElementEmpty(div)) {
        let $div = $("#" + div), $val = $div.text();
        if (!checkDataEmpty($val)) {
            $div.text(new Date($val).format("yyyy-MM-dd hh:mm:ss"));
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
    internalLoadDatas(usersPath, page, "userTable", "usersPagination", userMethods, "loadUsers");
    $("#userTableBody").find("[id$='status']").each(function () {
        formateIndexToName($(this).attr("id"),allUserStatus);
    });
}

/**
 * 显示修改用户的modal
 */
function updateUser(data) {
    initModal("user", data, userModalKeys);
}

/**
 * 修改用户
 */
function doUpdateUser() {
    internalUpdateData("user", usersPath, userModalKeys);
}

/**
 * 封禁用户
 */
function blockUser(data) {
    let sendData = {"id": data.id, "status": true};
    $.ajax({
        type: 'PUT',
        url: '/users',
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (data) {
            if (data === true) {
                $("#userTableTD-" + sendData.id + "-status").text(true);
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
    internalAddData("user", usersPath, userModalKeys);
}

/*----------------车辆模块部分-------------------------*/

/**
 * 加载区域列表的数据
 * @param page 请求的页数
 */
function loadAreas(page) {
    internalLoadDatas(areasPath, page, "areaTable", "areasPagination", areaMethods, "loadAreas");
    $("#areaTableBody").find("[id$='type']").each(function () {
        formateIndexToName($(this).attr("id"),allAreaType);
    });
}

/**
 * 显示修改区域的modal
 */
function updateArea(data) {
    initModal("area", data, areaModalKeys);
}

/**
 * 修改区域
 */
function doUpdateArea() {
    internalUpdateData("area", areasPath, areaModalKeys);
}

/**
 * 删除区域
 */
function deleteArea(data) {
    internalDeleteData("areaTable", data.id, areasPath);
}

/**
 * 添加区域
 */
function addArea() {
    internalAddData("area", areasPath, areaModalKeys);
}

/**
 * 加载供应商列表的数据
 * @param page 请求的页数
 */
function loadSuppliers(page) {
    internalLoadDatas(suppliersPath, page, "supplierTable", "suppliersPagination", supplierMethods, "loadSuppliers");
}

/**
 * 显示修改供应商的modal
 */
function updateSupplier(data) {
    initModal("supplier", data, supplierModalKeys);
}

/**
 * 修改供应商
 */
function doUpdateSupplier() {
    internalUpdateData("supplier", suppliersPath, supplierModalKeys);
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
    internalAddData("supplier", suppliersPath, supplierModalKeys);
}

/**
 * 加载车辆列表的数据
 * @param page 请求的页数
 */
function loadBicycles(page) {
    internalLoadDatas(bicyclesPath, page, "bicycleTable", "bicyclesPagination", bicycleMethods, "loadBicycles");
    $("#bicycleTableBody").find("[id$='type']").each(function () {
        formateIndexToName($(this).attr("id"),allBicycleType);
    });
    $("#bicycleTableBody").find("[id$='serviceTime']").each(function () {
        formatSeconds($(this).attr("id"));
    });
    $("#bicycleTableBody").find("[id$='status']").each(function () {
        formateIndexToName($(this).attr("id"),allBicycleStatus);
    });
}

/**
 * 加载移动车辆的函数
 * @param data 车辆的信息
 */
function loadMoveBicycle(data) {
    moveBicycle(data.id);
}
/**
 * 加载维修车辆函数
 * @param data 车辆的信息
 */
function loadRepairBicycle(data){
    repairBicycle(data.id);
}

/**
 * 加载报废车辆函数
 * @param data 车辆的信息
 */
function loadScrapeBicycle(data) {
    scrapeBicycle(data.id);
}

/**
 * 删除车辆
 */
function deleteBicycle(data) {
    internalDeleteData("bicycleTable", data.id, bicyclesPath);
}

/**
 * 添加车辆
 */
function addBicycle() {
    internalAddData("bicycle", bicyclesPath, bicycleModalKeys);
}

/*---------------订单模块-----------------*/
/**
 * 加载行程列表的数据
 * @param page 请求的页数
 */
function loadJourneys(page) {
    internalLoadDatas(journeysPath, page, "journeyTable", "journeysPagination", [], "loadJourneys");
    $("#journeyTableBody").find("[id$='rideTime']").each(function () {
        formatSeconds($(this).attr("id"));
    });
}

/*---------------任务模块----------------*/

/**
 * 加载所有车辆的位置信息
 */
function loadSimpleBicycles() {
    $.ajax({
        type: 'GET',
        url: allBicyclesPath,
        contentType: 'application/json',
        success: function (data) {
            if (!checkDataEmpty(data.datas)) {
                let result = data.datas;
                for (let i = 0; i < result.length; i++) {
                    let marker = new AMap.Marker({
                        map: map,
                        position: [result[i].locationX, result[i].locationY]
                    });
                    let title = "车辆信息", content = [];
                    content.push("编号 : " + result[i].id);
                    let status = "";
                    status = allBicycleStatus[result[i].status].name;
                    content.push("状态 : " + status);
                    content.push("操作 : <button class='Button Button--blue' onclick='moveBicycle(" + result[i].id + ")'>调出</button> <button class=' Button Button--blue' onclick='repairBicycle(" + result[i].id + ")'>维修</button> <button class='Button Button--blue' onclick='scrapeBicycle(" + result[i].id + ")'>报废</button>");
                    let infoWindow = new AMap.InfoWindow({
                        isCustom: true,  //使用自定义窗体
                        content: createInfoWindow(title, content.join("<br/>")),
                        offset: new AMap.Pixel(0, -30)
                    });
                    marker.on('click', function () {
                        infoWindow.open(map, marker.getPosition());
                    })
                }
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
    map.clearInfoWindow();
}

/**
 * 移动车辆
 * @param id 车辆编号
 */
function moveBicycle(id) {
    initAddTaskModal(1, id);
}

/**
 * 维修车辆
 * @param id 车辆编号
 */
function repairBicycle(id) {
    initAddTaskModal(2, id);
}

/**
 * 报废车辆
 * @param id 车辆编号
 */
function scrapeBicycle(id) {
    initAddTaskModal(3, id);
}

/**
 * 加载任务信息
 * @param page 指定的页数
 */
function loadTasks(page) {
    internalLoadDatas(taskPath, page, "taskTable", "tasksPagination", taskMethods, "loadTasks");
    $("#taskTableBody").find("[id$='status']").each(function () {
        if($(this).text() === 'true')
            $(this).parent().find("[id$='Btn']").empty();
        formateIndexToName($(this).attr("id"),allTaskStatus);
    });
    $("#taskTableBody").find("[id$='type']").each(function () {
        formateIndexToName($(this).attr("id"),allTaskType);
    });
}

/**
 * 完成任务
 * @param data 传入的任务数据
 */
function doneTask(data) {
        alert("不是该任务的参与人，无法操作！！");
    let sendData = {"id": data.id, "status": true};
    $.ajax({
        type: 'PUT',
        url: taskPath,
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (data) {
            if (data === true) {
                $("#taskTableTD-" + sendData.id + "-status").text(true);
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
    internalAddData("task", taskPath, taskModalKeys);
}

/**
 * 显示车辆分布页面的添加任务模态框
 * @param type 任务类型
 * @param bid 车辆编号
 */
function initAddTaskModal(type, bid) {
    let $modal = $("#add-task-modal");
    resetModal("add-task-modal");
    let modalCount = 0;
    $modal.on('show.bs.modal', function (event) {
        if (modalCount===0) {
            let typeStr = "";
            switch (type) {
                case 1 :
                    typeStr = "<option value='1'>调出</option>";
                    break;
                case 2 :
                    typeStr = "<option value='2'>维修</option>";
                    break;
                case 3 :
                    typeStr = "<option value='3'>报废</option>";
                    break;
                default :
                    typeStr = "<option>请重新选择合适的任务类型</option>";
                    break;
            }
            $("#add-task-modal-type").append(typeStr);
            $("#add-task-modal-bid").val(bid);
            modalCount++;
        }
    });
    $modal.modal("show");
}




