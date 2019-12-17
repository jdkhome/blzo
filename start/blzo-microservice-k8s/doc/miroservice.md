demo数据源对应库结构

> 这个库没有任何实际意义，但如果你仅仅是希望将程序运行起来，那么可以在数据库中执行下面的sql

### 建库 

```sql
CREATE DATABASE IF NOT EXISTS blzo_user DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE USER blzo_user@'%' identified BY '123456';
GRANT ALL PRIVILEGES ON blzo_user.* TO blzo_user@'%';

CREATE DATABASE IF NOT EXISTS blzo_friend DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE USER blzo_friend@'%' identified BY '123456';
GRANT ALL PRIVILEGES ON blzo_friend.* TO blzo_friend@'%';
```

### 建表

#### blzo_friend 

```sql
-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fname` varchar(100) NOT NULL,
  `fphone` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_fname` (`fname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

#### blzo_user

```sql
-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

#### seata分布式事务额外需要 在业务数据库中增加下面这些表

```sql
-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```

