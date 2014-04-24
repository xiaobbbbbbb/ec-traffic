-- phpMyAdmin SQL Dump
-- version 3.3.7
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2013 年 10 月 11 日 18:39
-- 服务器版本: 5.1.65
-- PHP 版本: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `ec_traffic`
--

-- --------------------------------------------------------

--
-- 表的结构 `car_info`
--

CREATE TABLE IF NOT EXISTS `car_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `car_no` varchar(20) NOT NULL,
  `car_frame_no` varchar(17) DEFAULT NULL,
  `car_engine_no` varchar(17) DEFAULT NULL,
  `car_regist_no` varchar(12) DEFAULT NULL COMMENT '机动车登记证书编号',
  `is_valid` int(4) DEFAULT '1' COMMENT '是否有效',
  `traffic_nums` int(11) DEFAULT '0' COMMENT '最后一次违章次数,该值为-1时代表无违章',
  `traffic_objects` text COMMENT '存放traffic_code+traffic_time',
  `last_response_date` date DEFAULT NULL,
  `traffic_times` text,
  `traffic_ids` varchar(255) DEFAULT NULL,
  `ctime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `car_no` (`car_no`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='车辆信息表';

-- --------------------------------------------------------

--
-- 表的结构 `customer_carinfo`
--

CREATE TABLE IF NOT EXISTS `customer_carinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `car_id` bigint(20) DEFAULT NULL,
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `is_expired` tinyint(4) DEFAULT '0',
  `is_disable` tinyint(4) DEFAULT '0',
  `car_nums` int(11) DEFAULT NULL,
  `car_fixed_nums` int(11) DEFAULT NULL,
  `car_allowchange_nums` int(4) DEFAULT '0',
  `ctime` datetime DEFAULT NULL,
  `utime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `car_id` (`car_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `customer_carinfo_log`
--

CREATE TABLE IF NOT EXISTS `customer_carinfo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `car_id` bigint(20) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `type` tinyint(4) NOT NULL COMMENT '0:insert,1:disable,2:enable',
  `ctime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户车辆变更记录';

-- --------------------------------------------------------

--
-- 表的结构 `customer_info`
--

CREATE TABLE IF NOT EXISTS `customer_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` char(6) NOT NULL,
  `auth_code` char(8) NOT NULL,
  `name` varchar(50) NOT NULL,
  `start_date` date NOT NULL COMMENT '合同起时时间',
  `end_date` date NOT NULL,
  `car_nums` int(11) NOT NULL,
  `type` int(11) NOT NULL COMMENT '客户类型1保险，2 4S,3 油品 ，4 其它',
  `is_valid` int(4) NOT NULL DEFAULT '1',
  `is_expired` int(4) NOT NULL DEFAULT '0',
  `op_name` varchar(20) NOT NULL COMMENT '操作员',
  `ctime` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `department_info`
--

CREATE TABLE IF NOT EXISTS `department_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `mark` varchar(200) NOT NULL,
  `is_valid` tinyint(4) NOT NULL DEFAULT '1',
  `ctime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `interface_carinfo`
--

CREATE TABLE IF NOT EXISTS `interface_carinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `car_id` bigint(20) NOT NULL,
  `interface_id` int(11) NOT NULL,
  `reference_count` tinyint(4) NOT NULL COMMENT '引用计数值',
  `last_request_date` date NOT NULL COMMENT '最近一次查询时间',
  `ctime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `interface_info`
--

CREATE TABLE IF NOT EXISTS `interface_info` (
  `id` smallint(11) NOT NULL AUTO_INCREMENT,
  `name` char(50) NOT NULL,
  `is_spider` int(4) NOT NULL DEFAULT '0' COMMENT '蜘蛛',
  `desction` char(50) NOT NULL COMMENT '接口说明',
  `max_num` int(11) NOT NULL COMMENT '最大查询量',
  `is_valid` int(4) NOT NULL DEFAULT '1',
  `work_status` int(4) NOT NULL DEFAULT '0' COMMENT '工作状态',
  `ctime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='违章查询接口';

-- --------------------------------------------------------

--
-- 表的结构 `ral_group`
--

CREATE TABLE IF NOT EXISTS `ral_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '集团ID',
  `name` varchar(30) DEFAULT NULL COMMENT '集团名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '集团ID父id',
  `code` char(255) DEFAULT NULL COMMENT '集团编码',
  `level` int(11) DEFAULT NULL COMMENT '级别(排序)',
  `is_leaf` int(11) DEFAULT NULL COMMENT '是否是叶子，0为叶子节点,1为根节点',
  `message` char(255) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `disabled` int(11) DEFAULT '1' COMMENT '是否有效，默认1为有效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `ral_org`
--

CREATE TABLE IF NOT EXISTS `ral_org` (
  `org_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织ID',
  `name` varchar(30) DEFAULT NULL COMMENT '组织名称',
  `is_leaf` int(4) DEFAULT NULL COMMENT '是否是叶子，0为叶子节点,1为根节点',
  `code` char(255) DEFAULT NULL COMMENT '组织编码',
  `parent_id` int(11) DEFAULT NULL COMMENT '组织父ID',
  `group_id` int(11) DEFAULT NULL COMMENT '所属集团ID',
  `orders` int(11) DEFAULT NULL COMMENT '排序',
  `message` varchar(255) DEFAULT NULL COMMENT '描述',
  `disabled` int(11) DEFAULT '1' COMMENT '是否有效，默认为1有效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `city_id` int(11) DEFAULT NULL COMMENT '所在城市ID',
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `ral_resource`
--

CREATE TABLE IF NOT EXISTS `ral_resource` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '资源名称',
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '资源地址',
  `icon` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图标',
  `parent_id` int(11) DEFAULT NULL COMMENT '父资源id',
  `level` int(11) DEFAULT NULL,
  `is_leaf` int(11) DEFAULT NULL COMMENT '是否是叶子',
  `message` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `type` int(11) DEFAULT NULL COMMENT '0为系统资源，1未用户资源，2即是系统资源又是用户资源',
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `ral_role`
--

CREATE TABLE IF NOT EXISTS `ral_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `org_id` int(11) DEFAULT NULL COMMENT '组织机构ID',
  `message` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT ' 角色描述',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `ral_role_resources`
--

CREATE TABLE IF NOT EXISTS `ral_role_resources` (
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  `resources_id` int(11) NOT NULL DEFAULT '0' COMMENT '资源id',
  PRIMARY KEY (`role_id`,`resources_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `ral_user`
--

CREATE TABLE IF NOT EXISTS `ral_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `login_name` char(50) DEFAULT NULL COMMENT '登录名',
  `password` char(40) DEFAULT NULL COMMENT '密码',
  `name` char(30) DEFAULT NULL COMMENT '姓名',
  `phone` char(255) DEFAULT NULL COMMENT '电话',
  `email` char(255) DEFAULT NULL COMMENT '邮件',
  `org_id` int(11) DEFAULT NULL COMMENT '组织ID(系统管理员未-1)',
  `is_manager` int(11) DEFAULT NULL COMMENT '是否为管理员，0为管理员,1为普通用户',
  `level` int(11) DEFAULT NULL COMMENT '级别(排序)',
  `message` varchar(255) DEFAULT NULL COMMENT '备注',
  `city_id` int(11) DEFAULT NULL COMMENT '所属城市ID',
  `disabled` int(1) DEFAULT '0' COMMENT '账号是否启用，默认为1启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `ral_user_role`
--

CREATE TABLE IF NOT EXISTS `ral_user_role` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `service_city`
--

CREATE TABLE IF NOT EXISTS `service_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` char(8) NOT NULL,
  `name` char(50) NOT NULL,
  `letter` char(1) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `is_hot` tinyint(4) NOT NULL DEFAULT '0',
  `is_car_frame_no` tinyint(4) NOT NULL DEFAULT '0',
  `car_frame_no` int(8) DEFAULT NULL,
  `is_car_engine_no` tinyint(4) NOT NULL DEFAULT '0',
  `car_engine_no` int(8) DEFAULT NULL,
  `is_regist_no` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否需要登记证书',
  `register_no` int(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `service_city_interface`
--

CREATE TABLE IF NOT EXISTS `service_city_interface` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_id` int(11) NOT NULL,
  `interface_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `system_log`
--

CREATE TABLE IF NOT EXISTS `system_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` char(8) NOT NULL,
  `info` varchar(255) NOT NULL,
  `op_name` varchar(20) NOT NULL,
  `ctime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `task_carinfo`
--

CREATE TABLE IF NOT EXISTS `task_carinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NOT NULL,
  `car_id` bigint(20) NOT NULL,
  `traffic_ids` varchar(255) DEFAULT NULL COMMENT 'traffic_items.id',
  `ctime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `car_id` (`car_id`),
  KEY `task_id` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `task_info`
--

CREATE TABLE IF NOT EXISTS `task_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_no` char(19) NOT NULL COMMENT '100001201309200001',
  `interface_id` smallint(11) NOT NULL COMMENT '接口ID',
  `status` enum('ready','processing','done') NOT NULL DEFAULT 'ready',
  `request_nums` int(11) NOT NULL DEFAULT '0',
  `request_time` datetime NOT NULL,
  `response_nums` int(11) NOT NULL DEFAULT '0',
  `response_time` datetime NOT NULL,
  `ctime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `traffic_code_dict`
--

CREATE TABLE IF NOT EXISTS `traffic_code_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` char(10) NOT NULL,
  `koufen` tinyint(4) NOT NULL,
  `mark` int(100) NOT NULL,
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `traffic_items`
--

CREATE TABLE IF NOT EXISTS `traffic_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) NOT NULL,
  `traffic_time` datetime NOT NULL,
  `city_name` varchar(50) DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `item` varchar(255) NOT NULL,
  `code` varchar(10) NOT NULL,
  `money` smallint(11) NOT NULL,
  `points` tinyint(11) DEFAULT NULL,
  `interface_id` smallint(11) NOT NULL COMMENT '接口ID',
  `ctime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `car_id` (`car_id`),
  KEY `interface_id` (`interface_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `user_info`
--

CREATE TABLE IF NOT EXISTS `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `display_name` varchar(20) DEFAULT NULL,
  `login_name` varchar(20) NOT NULL,
  `password` char(32) NOT NULL,
  `email` varchar(20) DEFAULT NULL,
  `is_admin` tinyint(4) NOT NULL DEFAULT '0',
  `is_valid` tinyint(4) NOT NULL DEFAULT '1',
  `last_login_time` datetime DEFAULT NULL,
  `department_id` int(11) NOT NULL,
  `op_name` varchar(20) NOT NULL,
  `ctime` datetime NOT NULL,
  `utime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
