create table area
(
	id int auto_increment comment '区域编号'
		primary key,
	name varchar(254) null comment '区域名称',
	north_point decimal(20,17) null comment '区域最北部',
	south_point decimal(20,17) null comment '区域最南部',
	west_point decimal(20,17) null comment '区域最西部',
	east_point decimal(20,17) null comment '区域最东部',
	type tinyint(3) default '1' null comment '区域类型：0-普通区;1-红包区;2-禁停区'
)
comment '区域表' engine=InnoDB charset=utf8
;

create table bicycle
(
	id int auto_increment comment '车辆编号'
		primary key,
	status tinyint(3) default '1' null comment '车辆状态',
	type tinyint(3) default '1' null comment '车辆类型',
	location_x decimal(20,17) default '0.00000000000000000' null comment '当前位置X坐标',
	location_y decimal(20,17) default '0.00000000000000000' null comment '当前位置Y坐标',
	batch char(13) not null comment '生产批次',
	s_id smallint(3) null comment '供应商ID',
	service_time bigint default '0' null comment '总使用时间',
	investment_time datetime null comment '投产时间',
	mileage int default '0' null comment '行驶里程'
)
comment '车辆表' engine=InnoDB charset=utf8
;

create table consumption
(
	id int auto_increment comment '消费记录编号'
		primary key,
	o_id int null comment '行程编号',
	u_id char(11) null comment '用户编号',
	con_amount decimal(5,2) null comment '应付金额',
	pay_amount decimal(5,2) null comment '实付金额',
	c_id int null comment '使用的优惠券编号'
)
comment '消费记录表' engine=InnoDB charset=utf8
;

create table coupon_info
(
	id int auto_increment comment '记录编号'
		primary key,
	c_d int null comment '优惠券类型编号',
	u_id char(11) null comment '用户编号'
)
comment '优惠券信息表' engine=InnoDB charset=utf8
;

create table coupon_type
(
	id int auto_increment comment '优惠券类型编号'
		primary key,
	name varchar(50) null comment '优惠券类型名称',
	par_value decimal(5,2) null comment '抵扣金额',
	condition_amount decimal(5,2) null comment '能够使用的最低金额',
	start_time datetime null comment '生效期',
	end_time datetime null comment '失效期'
)
comment '优惠券类型表' engine=InnoDB charset=utf8
;

create table deposit
(
	id int auto_increment
		primary key,
	amount decimal(5,2) null,
	type tinyint(1) null,
	u_id char(11) null,
	status tinyint(1) null,
	start_time datetime null,
	end_time datetime null
)
comment '押金记录表' engine=InnoDB charset=utf8
;

create table journey
(
	id int auto_increment comment '记录编号'
		primary key,
	u_id char(11) null comment '用户ID',
	b_id int null comment '车辆ID',
	start_time datetime null comment '起始时间',
	ride_time int default '0' null comment '骑行时间',
	distance int default '0' null comment '骑行距离',
	amount decimal(5,2) default '0.00' null comment '骑行花费',
	end_time datetime null
)
comment '行程记录表' engine=InnoDB charset=utf8
;

create table login_record
(
	id int auto_increment comment '记录编号'
		primary key,
	u_id char(11) null comment '账户ID',
	login_time datetime null comment '登录时间',
	login_IP varchar(15) null comment '登录IP'
)
comment '登录记录表' engine=InnoDB charset=utf8
;

create table recharge
(
	id int auto_increment comment '充值记录编号'
		primary key,
	u_id char(11) null comment '用户编号',
	type tinyint(1) null comment '充值类型',
	recharge_time datetime null comment '充值时间',
	amount decimal(5,2) null comment '充值金额',
	p_id varchar(254) null comment '支付方订单编号',
	p_url varchar(254) null comment '支付方回调URL',
	p_status tinyint null comment '支付结果'
)
comment '充值记录表' engine=InnoDB charset=utf8
;

create table supplier
(
	id smallint(3) auto_increment comment '编号'
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
	type tinyint(3) null comment '任务类型',
	u_id char(11) null comment '任务处理人',
	status tinyint(3) default '0' null comment '任务状态',
	start_time datetime null comment '任务开始时间',
	end_time datetime null comment '任务结束时间',
	b_id int null comment '车辆编号'
)
comment '调度任务表' engine=InnoDB charset=utf8
;

create table user
(
	id char(11) not null comment '用户编号'
		primary key,
	name varchar(50) not null comment '昵称',
	password varchar(50) not null comment '密码',
	type tinyint(3) default '0' null comment '账户类型',
	avatar varchar(254) default 'http://7xshpr.com1.z0.glb.clouddn.com/default_avatar.png' null comment '头像',
	credit tinyint(3) default '100' null comment '信用分',
	status tinyint(3) default '0' null comment '状态',
	account_balance decimal(5,2) default '0.00' null comment '账户余额',
	red_balance decimal(5,2) default '0.00' null comment '红包余额',
	coupons int default '0' null comment '优惠券数量',
	dates datetime null comment '包月截止时间'
)
comment '用户表' engine=InnoDB charset=utf8
;

