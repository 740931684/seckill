--数据库初始化脚本
--查看建表脚本： show create table seckill


CREATE DATABASE seckill;

--使用数据库
use seckill;

--创建秒杀库表
create table seckill(
`seckill_id` bigint not null AUTO_INCREMENT COMMENT '商品id',
`name` varchar(120) not null COMMENT '商品名称',
`number` int not null COMMENT '库存数量',
`start_time` timestamp not null COMMENT '秒杀开始时间',
`end_time` timestamp not null COMMENT '秒杀结束时间',
`create_time` timestamp not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '秒杀商品库存表';

--初始化数据
insert into seckill(name,number,start_time,end_time)
values ('1000元秒杀iphone7',100,'2018-06-10 00:00:00','2018-06-11 00:00:00'),
       ('5000元秒杀MACBOO AIR',100,'2018-06-10 00:00:00','2018-06-11 00:00:00'),
       ('4000元秒杀P20',100,'2018-06-10 00:00:00','2018-06-11 00:00:00'),
       ('1000元秒杀小米MIX',100,'2018-06-10 00:00:00','2018-06-11 00:00:00');

--秒杀明细表
create table success_killed(
`seckill_id` bigint not null COMMENT '秒杀商品id',
`user_phone` bigint not null COMMENT '手机号',
`state` tinyint not null default -1 COMMENT '状态：-1 无效，0 成功，1 已付款',
`create_time` timestamp not null COMMENT '创建时间',
primary key(seckill_id,user_phone),
key inx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '秒杀商品成功明细表';















