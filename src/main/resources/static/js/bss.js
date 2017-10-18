/**
 * Created by Mo on 2017/10/2.
 */
/*-----------------全局变量---------------*/
const userMethods = [
    {"name" : "更新","method" : "updateUser"},
    {"name" : "封禁","method" : "blockUser"},
    {"name" : "删除","method" : "deleteUser"}
];//用户管理的方法
const areaMethods = [
    {"name" : "更新" , "method" : "updateArea"},
    {"name" : "删除" , "method" : "deleteArea"}
];//区域管理的方法
const supplierMethods = [
    {"name" : "更新" , "method" : "updateSupplier"},
    {"name" : "删除" , "method" : "deleteSupplier"}
];//供应商管理的方法
const bicycleMethods = [
    {"name" : "封禁" , "method" : "blockBicycle"},
    {"name" : "删除" , "method" : "deleteBicycle"}
];

const rootPath = "http://localhost:8080";//网站根目录
const usersPath = rootPath + "/users";//获取用户数据的根目录
const areasPath = rootPath + "/areas";//获取区域信息的根目录
const suppliersPath = rootPath + "/suppliers";//获取供应商信息的根目录
const bicyclesPath = rootPath + "/bicycles";//获取车辆信息的根目录

const userModalKeys = ["id","name","credit"];//修改用户模态框的键列表
const areaModalKeys = ["id","name","northPoint","southPoint","westPoint","eastPoint","type"];//修改区域模态框的键列表
const supplierModalKeys = ["id","name","address"];//修改供应商模态框的键列表
const bicycleModalKeys = ["id","type","batch","sid"];//添加车辆的键列表
// const

/*---------------公共函数----------------*/
/**
 * 页面跳转
 * @param data 待跳转的页面
 */
function pageJump(data){
    if(! checkDataEmpty(data)){
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
    return data == "" || data == undefined || data == "[]";
}

/**
 * 重置组件
 * @param div
 */
function resetDiv(div) {
   if(!checkElementEmpty(div))
       $("#" + div).empty();
}

/**
 * 生成表格
 * @param div 待操作的表格
 * @param data 待填充的数据
 * @param 每行数据待设置的操作
 */
function generateTable(div,data,methods) {
    if(! checkElementEmpty(div)){
        let $div = $("#" + div);
        if(!checkDataEmpty(data)){
            //设置表头
            if(!checkDataEmpty(data.names))
                $div.append(generateBatchTableHeader(data.names));
            //设置数据
            if(!checkDataEmpty(data.datas) && !checkDataEmpty(data.keys))
                $div.append(generateBatchTableData(div,data.datas,data.keys,methods));
        }
    }
}

/**
 * 生成表格表头
 * @param header 待填充的表头数据
 * @return {string} 设置好的表头
 */
function generateBatchTableHeader(header){
    let strPre = "<thead><tr>";
    let strData = "";
    for(let i = 0; i < header.length; i++){
        strData += "<th>" +  header[i] + "</th>";
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
function generateBatchTableData(div,data,keys,methods){
    let strPre = "<tbody id='" + div + "Body'>";
    let strData = "";
    for(let i = 0; i < data.length; i++){
        strData += generateSingleTableData(div, data[i], keys, methods);
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
function generateSingleTableData(div, data, keys, methods){
    let strPre = "<tr id='" + div + "TR-" + data.id + "'>";
    let strData = "";
    for(let i = 0; i < keys.length; i++){
        strData += "<td id='" + div + "TD-" + data.id + "-" + keys[i] + "'>" + data[keys[i]] + "</td>";
    }
    strData += generateSingleTableMethods(div,data,methods);
    let strSuf = "</tr>";
    return strPre + strData + strSuf;
}

/**
 * 生成单行数据表格的操作列
 * @param div 数据表格ID
 * @param data 单行的数据
 * @param methods 待添加的数据操作方法及按钮名
 * @return {string} 设置好的单行数据的操作列
 */
function generateSingleTableMethods(div,data,methods){
    let strPre = "<td>";
    let strData = "";
    for(let i = 0; i < methods.length; i++){
       strData += "<button class='Button Button--blue' id='" + div + "BlockBtn-" + data.id + "' onclick='" + methods[i].method + "(" + JSON.stringify(data) + ")'>" + methods[i].name + "</button> ";
    }
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
function initPag(currentPage,totalPage,id,method){
    let c = parseInt(currentPage);
    let t = parseInt(totalPage);
    let prevString = "";
    let suffString = "";
    if(t <=1 ) {//总页数过少时移除分页
        $("#" + id).remove();
        return ;
    }
//处理"上一页"
    if(c > 1)  $("#" + id).append("<li id='pagLast'><span onclick=" + method + "(" + (c - 1) + ")> << </span></li>");
//添加当前页码前的页数
    if(c > 3) {
        prevString += "<li><a>...</a></li>";
        for(let i = c - 3; i < c; i++) prevString += "<li><span onclick=" + method + "(" + i  +")>" + i + "</span></li>";
        $("#" + id).append(prevString);
    }
    else{
        for(let i = 1; i < c; i++) prevString += "<li><span onclick=" + method + "(" + i  +")>" + i + "</span></li>";
        $("#" + id).append(prevString);
    }
//添加当前页码
    $("#" + id).append("<li id='pageCurrent' class='active'><span>" + c + "</span></li>");
//添加当前页码后的页数
    if(c < t-3){
        for(let j = c + 1; j <= c + 3; j++) suffString += "<li><span onclick=" + method + "(" + j  +")>" + j + "</span></li>";
        suffString +="<li><a>...</a></li>";
    }
    else
        for(let j = c + 1; j <= t; j++) suffString += "<li><span onclick=" + method + "(" + j  +")>" + j + "</span></li>";
    $("#" + id).append(suffString);
//处理"下一页"
    if(c < t) $("#" + id).append("<li id='pagNext'><span onclick=" + method + "(" + (c + 1)  +")> >> </span></li>");
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
        success: function(data) {
            generateTable(tableDiv,data,methods);
            initPag(data.currentPage,data.totalPage,pagDiv,reloadMethod);
        }
    });
}


/**
 * 显示修改数据的模态框
 * @param pojo 修改的实体
 * @param data 修改的数据
 * @param keys 修改的数据的key
 */
function initModal(pojo,data,keys) {
    let $modal = $("#update-" + pojo + "-modal");
    $modal.on('show.bs.modal', function (event) {
        for(let i =0 ;i < keys.length; i++)
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
function internalDeleteData(div,id,sendUrl){
    let sendData = {"id" : id};
    $.ajax({
        type: 'DELETE',
        url: sendUrl,
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function(data) {
            if(data === true){
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
function internalAddData(pojo,sendUrl,keys){
    let sendData = {};
    for(let i = 0; i < keys.length ; i++)
        sendData[keys[i]] = $("#add-" + pojo + "-modal-" + keys[i]).val();
    $.ajax({
        type: 'POST',
        url: sendUrl,
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function(data) {
            if(data === true){
                alert("添加数据成功");
                $("#add-" + pojo + "-modal").modal("hide");
                window.location.reload();
            }
            else
                alert("未成功添加数据");
        }
    });
}

/**
 * 修改指定数据
 * @param pojo 修改的实体
 * @param sendUrl 修改的数据后台的Url
 * @param keys 修改的实体的键
 */
function internalUpdateData(pojo,sendUrl,keys){
    let sendData = {};
    for(let i = 0; i < keys.length ; i++)
        sendData[keys[i]] = $("#update-" + pojo + "-modal-" + keys[i]).val();
    $.ajax({
        type: 'PUT',
        url: sendUrl,
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function(data) {
            if(data === true){
                $("#update-" + pojo + "-modal").modal("hide");
                let $base = "#" + pojo + "TableTD-" + sendData.id + "-";
                for(let i = 1; i < keys.length ; i++)
                    $($base + keys[i]).text(sendData[keys[i]]);
                window.location.reload();
            }
        }
    });
}



/*-----------登录登出部分-----------*/
/**
 * 用户登录
 */
function login() {
    let data = {
        "id" : $("#id").val(),
        "password" : $("#pass").val()
    };
    $.ajax({
        type: 'POST',
        url: '/login',
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function(data) {
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
    internalLoadDatas(usersPath,page,"userTable","usersPagination",userMethods,"loadUsers");
}

/**
 * 显示修改用户的modal
 */
function updateUser(data) {
    initModal("user",data,userModalKeys);
}

/**
 * 修改用户
 */
function doUpdateUser(){
    internalUpdateData("user",usersPath,userModalKeys);
}

/**
 * 封禁用户
 */
function blockUser(data){
    let sendData = {"id" : data.id,"status" : true};
    $.ajax({
        type: 'PUT',
        url: '/users',
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function(data) {
            if(data === true){
                $("#userTableTD-" + sendData.id + "-status").text(true);
            }
        }
    });
}

/**
 * 删除用户
 */
function deleteUser(data){
    internalDeleteData("userTable",data.id,usersPath);
}

/**
 * 添加用户
 */
function addUser(){
    internalAddData("user",usersPath,userModalKeys);
}

/*----------------车辆模块部分-------------------------*/

/**
 * 加载区域列表的数据
 * @param page 请求的页数
 */
function loadAreas(page){
    internalLoadDatas(areasPath,page,"areaTable","areasPagination",areaMethods,"loadAreas");
}

/**
 * 显示修改区域的modal
 */
function updateArea(data) {
    initModal("area",data,areaModalKeys);
}

/**
 * 修改区域
 */
function doUpdateArea(){
    internalUpdateData("area",areasPath,areaModalKeys);
}

/**
 * 删除区域
 */
function deleteArea(data){
    internalDeleteData("areaTable",data.id,areasPath);
}

/**
 * 添加区域
 */
function addArea(){
    internalAddData("area",areasPath,areaModalKeys);
}

/**
 * 加载供应商列表的数据
 * @param page 请求的页数
 */
function loadSuppliers(page){
    internalLoadDatas(suppliersPath,page,"supplierTable","suppliersPagination",supplierMethods,"loadSuppliers");
}

/**
 * 显示修改供应商的modal
 */
function updateSupplier(data) {
    initModal("supplier",data,supplierModalKeys);
}

/**
 * 修改供应商
 */
function doUpdateSupplier(){
    internalUpdateData("supplier",suppliersPath,supplierModalKeys);
}

/**
 * 加载供应商列表的数据
 * @param page 请求的页数
 */
function loadSuppliers(page){
    internalLoadDatas(suppliersPath,page,"supplierTable","suppliersPagination",supplierMethods,"loadSuppliers");
}

/**
 * 添加供应商
 */
function addSupplier(){
    internalAddData("supplier",suppliersPath,supplierModalKeys);
}

/**
 * 加载车辆列表的数据
 * @param page 请求的页数
 */
function loadBicycles(page){
    internalLoadDatas(bicyclesPath,page,"bicycleTable","bicyclesPagination",bicycleMethods,"loadBicycles");
}

/**
 * 删除车辆
 */
function deleteBicycle(data){
    internalDeleteData("bicycleTable",data.id,bicyclesPath);
}

/**
 * 封禁车辆
 */
function blockBicycle(data){
    let sendData = {"id" : data.id,"status" : true};
    $.ajax({
        type: 'PUT',
        url: bicyclesPath,
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function(data) {
            if(data === true){
                $("#bicycleTableTD-" + sendData.id + "-status").text(true);
            }
        }
    });
}

/**
 * 添加车辆
 */
function addBicycle(){
    internalAddData("bicycle",bicyclesPath,bicycleModalKeys);
}

