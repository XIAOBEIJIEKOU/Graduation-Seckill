/*创建秒杀商品列表表格*/
CREATE TABLE seckill(
seckillId bigint NOT NULL auto_increment ,
name varchar(100) NOT NULL ,
number int NOT NULL COMMENT '库存数量',
startTime timestamp NOT NULL COMMENT '秒杀开始时间',
endTime timestamp NOT NULL COMMENT '秒杀结束时间',
createTime timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
PRIMARY KEY(seckillId),
key idx_start_time(startTime),
key idx_end_time(endTime),
key idx_create_time(createTime))

/*插入数据*/
insert into
	seckill(name,number,startTime,endTime)
values
	('1000元秒杀iPhone7','100','2017-5-2 00:00:00','2017-5-3 00:00:00');

/*创建秒什么成功详情表*/
CREATE TABLE success_killed(
seckillId bigint NOT NULL COMMENT '秒杀商品ID',
userPhone bigint NOT NULL COMMENT '用户手机号',
state tinyint NOT NULL DEFAULT 0 COMMENT '当前状态：0表示nothing，1表示已被秒杀',
createTime timestamp NOT NULL COMMENT '当前记录创建的时间',
PRIMARY KEY(seckillId,userPhone),/*联合主键，id和用户手机号做唯一标示*/
key idx_create_time(createTime)
)