/**
 * Created by Mo on 2017/10/28.
 */
/*-----------------全局变量---------------*/
const emptyDataValue = "-";//数据为空时显示的字符内容

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
    {"name": "调出", "method": "loadMoveBicycle"},
    {"name": "维修", "method": "loadRepairBicycle"},
    {"name": "报废", "method": "loadScrapeBicycle"},
    {"name": "删除", "method": "deleteBicycle"}
];//车辆管理的方法
const taskMethods = [
    {"name": "完成", "method": "doneTask"},
    {"name": "取消", "method": "cancelTask"}
];

const rootPath = "http://localhost:8080";//网站根目录
const usersPath = rootPath + "/users";//获取用户数据的根目录
const areasPath = rootPath + "/areas";//获取区域信息的根目录
const suppliersPath = rootPath + "/suppliers";//获取供应商信息的根目录
const allSuppliersPath = suppliersPath + "/all";//获取所有供应商简略信息的Url
const bicyclesPath = rootPath + "/bicycles";//获取车辆信息的根目录
const allBicyclesPath = bicyclesPath + "/all";//获取所有车辆信息的根目录
const journeysPath = rootPath + "/journeys";//获取行程信息的根目录
const taskPath = rootPath + "/tasks";//获取任务信息的根目录

const userModalKeys = ["id", "name", "credit"];//修改用户模态框的键列表
const areaModalKeys = ["id", "name", "northPoint", "southPoint", "westPoint", "eastPoint", "type"];//修改区域模态框的键列表
const supplierModalKeys = ["id", "name", "address"];//修改供应商模态框的键列表
const bicycleModalKeys = ["id", "type", "batch", "sid"];//添加车辆的键列表
const taskModalKeys = ["id", "name", "type", "status", "uid", "bid"];//添加任务模态框的键列表

const allUserStatus = [
    {"id" : 0, "name" : "使用中", "class" : ""},
    {"id" : 1, "name" : "已封禁", "class" : "danger"}
];

const allBicycleType = [
    {"id" : 0, "name" : "一代经典版", "class" : ""},
    {"id" : 1, "name" : "一代轻骑版", "class" : ""},
    {"id" : 2, "name" : "一代豪华版", "class" : ""}
];//所有车辆类型信息
const allBicycleStatus = [
    {"id" : 0, "name" : "空闲中", "class" : ""},
    {"id" : 1, "name" : "使用中", "class" : "info"},
    {"id" : 2, "name" : "待调出", "class" : "warning"},
    {"id" : 3, "name" : "待维修", "class" : "warning"},
    {"id" : 4, "name" : "待报废", "class" : "danger"}
];//所有车辆状态信息
const allAreaType = [
    {"id" : 0, "name" : "普通区", "class" : ""},
    {"id" : 1, "name" : "红包区", "class" : "success"},
    {"id" : 2, "name" : "禁停区", "class" : "danger"}
];//所有区域类型信息
const allTaskType = [
    {"id" : 0, "name" : "投放", "class" : ""},
    {"id" : 1, "name" : "移动", "class" : "success"},
    {"id" : 2, "name" : "维修" , "class" : "warning"},
    {"id" : 3, "name" : "报废", "class" : "danger"}
];//所有任务类型信息
const allTaskStatus = [
    {"id" : 0, "name" : "未完成", "class" : "danger"},
    {"id" : 1, "name" : "已完成", "class" : "info"}
];