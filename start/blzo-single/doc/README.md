
> 这个库没有任何实际意义，但如果你仅仅是希望将程序运行起来，那么可以在数据库中执行下面的sql

### 建库 

```sql
CREATE DATABASE IF NOT EXISTS blzo_demo DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE USER blzo_demo@'%' identified BY 'blzo_demo';
GRANT ALL PRIVILEGES ON blzo_demo.* TO blzo_demo@'%';
```

### 建表

```sql
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username_KEY` (`username`) USING BTREE,
  KEY `phone_KEY` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='(演示)用户表';
```