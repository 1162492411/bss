/**
 * Created by Mo on 2017/10/2.
 */
/*---------------公共函数----------------*/
/**
 * 页面跳转
 * @param data 待跳转的页面
 */
function PageJump(data){
    if(! checkDataEmpty(data)){
        location.href(data);
    }
}

/**
 * 检测数据是否为空或未定义
 * @param data 待验证的数据
 * @returns {boolean} 是否为空
 */
function checkDataEmpty(data) {
    return data == "" || data == undefined || data == "[]";
}

function generateBatchTableData(+data){

}

function generateSingleTableData(data){

}


/*----------------------*/
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
            location.href="login";
        }
    });
}

/**
 * 测试单个用户
 */

function loadUsers(){
    $.ajax({
        type: 'GET',
        url: '/users',
        success: function(data) {
            $("#singleUserDiv").append(data.name);
        }
    });
}
