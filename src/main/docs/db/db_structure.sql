create table area
(
	id int auto_increment comment '区域编号'
		primary key,
	name varchar(254) null comment '区域名称',
	north_point decimal(20,17) default '0.00000000000000000' null comment '区域最北部',
	south_point decimal(20,17) default '0.00000000000000000' null comment '区域最南部',
	west_point decimal(20,17) default '0.00000000000000000' null comment '区域最西部',
	east_point decimal(20,17) default '0.00000000000000000' null comment '区域最东部',
	type int default '1' null comment '区域类型：0-未知;1-普通区;2-红包区;3-禁停区'
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
	service_time varchar(50) default '0' null comment '总使用时间',
	investment_time varchar(50) null comment '投产时间',
	mileage int default '0' null comment '行驶里程'
)
	comment '车辆表' engine=InnoDB charset=utf8
;

create table deposit
(
	id int auto_increment
		primary key,
	amount decimal(5,2) null,
	type int null,
	u_id char(11) null,
	status int null,
	start_time varchar(50) null,
	end_time varchar(50) null
)
	comment '押金记录表' engine=InnoDB charset=utf8
;

create table journey
(
	id int auto_increment comment '记录编号'
		primary key,
	u_id char(11) null comment '用户ID',
	b_id int null comment '车辆ID',
	start_time varchar(50) null comment '起始时间',
	ride_time varchar(50) default '0' null comment '骑行时间',
	distance int default '0' null comment '骑行距离',
	amount decimal(5,2) default '0.00' null comment '骑行花费',
	end_time varchar(50) null,
	start_location_x decimal(20,17) default '0.00000000000000000' null comment '起始位置X',
	start_location_y decimal(20,17) default '0.00000000000000000' null comment '起始位置Y',
	end_location_x decimal(20,17) default '0.00000000000000000' null comment '终止位置X',
	end_location_y decimal(20,17) default '0.00000000000000000' null comment '终止位置Y'
)
	comment '行程记录表' engine=InnoDB charset=utf8
;

create table recharge
(
	id int auto_increment comment '充值记录编号'
		primary key,
	u_id char(11) null comment '用户编号',
	type int null comment '充值类型',
	recharge_time varchar(50) null comment '充值时间',
	amount decimal(5,2) null comment '充值金额',
	p_id varchar(254) null comment '支付方订单编号',
	p_url varchar(254) null comment '支付方回调URL',
	p_status int null comment '支付结果'
)
	comment '充值记录表' engine=InnoDB charset=utf8
;

create table supplier
(
	id int auto_increment comment '编号'
		primary key,
	name varchar(254) null comment '供应商名',
	address varchar(254) null comment '供应商地址'
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
