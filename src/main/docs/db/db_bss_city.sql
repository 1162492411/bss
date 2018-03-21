create table city
(
	id int auto_increment comment '自增主键'
		primary key,
	name varchar(100) null comment '区划名称',
	level int null comment '区划级别',
	code int null comment '区划邮编',
	parent_id int null comment '父ID',
	centerX decimal(20,17) null comment '区域中心X坐标',
	centerY decimal(20,17) null comment '区域中心Y坐标'
)
comment '全国行政区划' engine=InnoDB charset=utf8
;

