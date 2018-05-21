create table apply
(
  id int auto_increment comment '自增主键'
    primary key,
  type int default '0' null comment '申请类型',
  status int default '0' null comment '申请状态',
  amount decimal(5,2) default '0.00' null comment '申请金额',
  user_id char(11) null comment '申请人',
  description varchar(255) default '' null comment '申请描述',
  start_time varchar(50) default '' null comment '提交时间',
  end_time varchar(50) default '' null comment '完成时间',
  operator_id char(11) null comment '处理人'
)
  comment '用户申请表' engine=InnoDB
;

create table area
(
  id int auto_increment comment '区域编号'
    primary key,
  name varchar(254) null comment '区域名称',
  north_point decimal(20,17) default '0.00000000000000000' null comment '区域最北部',
  south_point decimal(20,17) default '0.00000000000000000' null comment '区域最南部',
  west_point decimal(20,17) default '0.00000000000000000' null comment '区域最西部',
  east_point decimal(20,17) default '0.00000000000000000' null comment '区域最东部',
  type int default '1' null comment '区域类型：0-未知;1-普通区;2-红包区;3-禁停区',
  city_id int null comment '所属行政区划'
)
  comment '区域表' engine=InnoDB charset=utf8
;

create table bicycle
(
  id int auto_increment comment '车辆编号'
    primary key,
  status int default '1' null comment '车辆状态',
  type int default '1' null comment '车辆类型',
  location_x decimal(20,17) default '0.00000000000000000' null comment '当前位置X坐标',
  location_y decimal(20,17) default '0.00000000000000000' null comment '当前位置Y坐标',
  batch char(13) not null comment '生产批次',
  supplier int null comment '供应商ID',
  service_time int default '0' null comment '总使用时间',
  investment_time varchar(50) null comment '投产时间',
  mileage int default '0' null comment '行驶里程',
  city_id int null comment '当前所在行政区划ID'
)
  comment '车辆表' engine=InnoDB charset=utf8
;

create table city
(
  id int not null comment '主键'
    primary key,
  name varchar(100) null comment '区划名称',
  level int null comment '区划级别',
  code int null comment '区划邮编',
  parent_id int null comment '父ID',
  center_x decimal(20,17) null comment '区域中心X坐标',
  center_y decimal(20,17) null comment '区域中心Y坐标'
)
  comment '全国行政区划' engine=InnoDB charset=utf8
;

create table deposit
(
  id int auto_increment
    primary key,
  amount decimal(5,2) null,
  type int null,
  user_id char(11) null,
  operate_time varchar(50) null
)
  comment '押金记录表' engine=InnoDB charset=utf8
;

create table journey
(
  id int auto_increment comment '记录编号'
    primary key,
  user_id char(11) null comment '用户ID',
  bicycle_id int null comment '车辆ID',
  start_time varchar(50) null comment '起始时间',
  ride_time int default '0' null comment '骑行时间',
  distance int default '0' null comment '骑行距离',
  amount decimal(5,2) default '0.00' null comment '骑行花费',
  end_time varchar(50) null comment '骑行终止时间',
  start_location_x decimal(20,17) default '0.00000000000000000' null comment '起始位置X',
  start_location_y decimal(20,17) default '0.00000000000000000' null comment '起始位置Y',
  end_location_x decimal(20,17) default '0.00000000000000000' null comment '终止位置X',
  end_location_y decimal(20,17) default '0.00000000000000000' null comment '终止位置Y',
  status int default '0' null comment '行程状态',
  path longtext null comment '运动轨迹',
  start_area int null comment '起始区划编号',
  end_area int null comment '终止行政区划编号',
  distance_round int null comment '骑行距离向上取整'
)
  comment '行程记录表' engine=InnoDB charset=utf8
;

create table recharge
(
  id int auto_increment comment '充值记录编号'
    primary key,
  user_id char(11) null comment '用户编号',
  type int null comment '充值类型',
  recharge_time varchar(50) null comment '充值时间',
  amount decimal(5,2) null comment '充值金额'
)
  comment '充值记录表' engine=InnoDB charset=utf8
;

create table supplier
(
  id int auto_increment comment '编号'
    primary key,
  name varchar(254) null comment '供应商名',
  address varchar(254) null comment '供应商地址',
  brand varchar(254) null comment '供应商品牌'
)
  comment '供应商表' engine=InnoDB charset=utf8
;

create table task
(
  id int auto_increment comment '任务编号'
    primary key,
  name varchar(100) null comment '任务名称',
  type int null comment '任务类型',
  user char(11) null comment '任务处理人',
  status int default '0' null comment '任务状态',
  start_time varchar(50) null comment '任务开始时间',
  end_time varchar(50) null comment '任务结束时间',
  bicycle int null comment '车辆编号'
)
  comment '调度任务表' engine=InnoDB charset=utf8
;

create table user
(
  id char(11) not null comment '用户编号'
    primary key,
  name varchar(50) not null comment '昵称',
  password varchar(50) not null comment '密码',
  type int default '0' null comment '账户类型',
  avatar varchar(254) default 'http://7xshpr.com1.z0.glb.clouddn.com/default_avatar.png' null comment '头像',
  credit int default '100' null comment '信用分',
  status int default '0' null comment '状态',
  account_balance decimal(5,2) default '0.00' null comment '账户余额',
  deposit_balance decimal(5,2) default '0.00' null comment '押金余额',
  monthly_time varchar(50) null comment '包月截止时间'
)
  comment '用户表' engine=InnoDB charset=utf8
;

