/**
 * Created by Mo on 2017/10/28.
 */
/*-----------------全局变量---------------*/
const emptyDataValue = "-";//数据为空时显示的字符内容
const Codes = {
    "successResponse" : 1,
    "task" : {
        "waitSomeOne" : 1,
        "waitComplete" : 2,
        "done" : 3
    }
};//常量

const emptyStaff = {
    "id" : "",
    "name" : "暂不设置"
};

const userMethods = [
    {"name": "更新", "method": "updateUser"},
    {"name": "封禁", "method": "blockUser"},
    {"name": "删除", "method": "deleteUser"}
];//用户管理的方法
const areaMethods = [
    {"name": "更新", "method": "updateArea"},
    {"name": "删除", "method": "deleteArea"}
];//区域管理的方法
const supplierMethods = [
    {"name": "更新", "method": "updateSupplier"},
    {"name": "删除", "method": "deleteSupplier"}
];//供应商管理的方法
const bicycleMethods = [
    {"name": "移动", "method": "moveBicycle"},
    {"name": "修理", "method": "repairBicycle"},
    {"name": "报废", "method": "scrapeBicycle"},
    {"name": "删除", "method": "deleteBicycle"}
];//车辆管理的方法
const taskMethods = [
    {"name" : "分配", "method" : "dispatchTask"},
    {"name": "完成", "method": "doneTask"},
    {"name": "取消", "method": "cancelTask"}
];//任务管理的方法

const rootPath = "http://localhost:8080";//网站根目录
const usersPath = rootPath + "/users";//获取用户数据的根目录
const allStaffsPath = usersPath + "/allStaffs";//获取所有员工简略信息的URL
const areasPath = rootPath + "/areas";//获取区域信息的根目录
const suppliersPath = rootPath + "/suppliers";//获取供应商信息的根目录
const allSuppliersPath = suppliersPath + "/all";//获取所有供应商简略信息的URL
const bicyclesPath = rootPath + "/bicycles";//获取车辆信息的根目录
const allBicyclesPath = bicyclesPath + "/all";//获取所有车辆信息的根目录
const journeysPath = rootPath + "/journeys";//获取行程信息的根目录
const taskPath = rootPath + "/tasks";//获取任务信息的根目录

const allUserStatus = [
    {"id" : 0, "name" : "未知", "class" : "danger"},
    {"id" : 1, "name" : "正常", "class" : ""},
    {"id" : 2, "name" : "封禁", "class" : "warning"}
];

const allBicycleType = [
    {"id" : 0, "name" : "未知", "class" : "danger"},
    {"id" : 1, "name" : "一代经典版", "class" : ""},
    {"id" : 2, "name" : "一代轻骑版", "class" : ""}
];//所有车辆类型信息

const allBicycleStatus = [
    {"id" : 0, "name" : "未知", "class" : "danger"},
    {"id" : 1, "name" : "空闲中", "class" : ""},
    {"id" : 2, "name" : "使用中", "class" : "info"},
    {"id" : 3, "name" : "待移动", "class" : "warning"},
    {"id" : 4, "name" : "待维修", "class" : "danger"},
    {"id" : 5, "name" : "待报废", "class" : "danger"},
    {"id" : 6, "name" : "待删除", "class" : "danger"}
];//所有车辆状态信息

const allAreaType = [
    {"id" : 0, "name" : "未知", "class" : "danger"},
    {"id" : 1, "name" : "普通区", "class" : ""},
    {"id" : 2, "name" : "红包区", "class" : "success"},
    {"id" : 3, "name" : "禁停区", "class" : "warning"}
];//所有区域类型信息

const allTaskType = [
    {"id" : 0, "name" : "未知", "class" : "danger"},
    {"id" : 1, "name" : "移动", "class" : ""},
    {"id" : 2, "name" : "修理" , "class" : "warning"},
    {"id" : 3, "name" : "报废", "class" : "danger"}
];//所有任务类型信息

const allTaskStatus = [
    {"id" : 0, "name" : "未知", "class" : "danger"},
    {"id" : 1, "name" : "待分配", "class" : "warning"},
    {"id" : 2, "name" : "待执行", "class" : ""},
    {"id" : 3, "name" : "已完成", "class" : "info"}
];//所有任务状态信息


