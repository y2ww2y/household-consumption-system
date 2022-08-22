/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 80028
Source Host           : localhost:3306
Source Database       : consume

Target Server Type    : MYSQL
Target Server Version : 80028
File Encoding         : 65001

Date: 2022-08-23 02:22:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bill`
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `bid` bigint NOT NULL,
  `bdate` datetime(6) NOT NULL,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `amout` int NOT NULL,
  `consumerid` int NOT NULL,
  `descr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of bill
-- ----------------------------
INSERT INTO `bill` VALUES ('1', '2020-08-11 00:46:21.000000', '衣食住行', '100', '1', null);
INSERT INTO `bill` VALUES ('1559572256726839297', '2020-08-11 00:05:57.000000', '非固定开支', '123', '2', '修改1');
INSERT INTO `bill` VALUES ('1559873192238022658', '2022-08-18 20:01:54.000000', '非固定开支', '456', '4', 'c 21');
INSERT INTO `bill` VALUES ('1560929833062531073', '2022-07-07 18:00:24.000000', '非固定开支', '142', '2', '141');
INSERT INTO `bill` VALUES ('1560929890503524354', '2022-07-15 18:00:49.000000', '水电煤气', '111', '2', '171');
INSERT INTO `bill` VALUES ('1560930386274451457', '2022-08-10 18:02:29.000000', '非固定开支', '47', '1', '146');
INSERT INTO `bill` VALUES ('1560930444747243521', '2022-08-10 18:03:01.000000', '衣食住行', '895', '1', '51');
INSERT INTO `bill` VALUES ('1561763096949710850', '2022-08-11 01:12:43.000000', '非固定开支', '17', '4', '14');
INSERT INTO `bill` VALUES ('1561765236350926850', '2022-08-18 01:19:50.000000', '非固定开支', '123', '4', '1');

-- ----------------------------
-- Table structure for `member`
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` char(30) COLLATE utf8mb4_general_ci NOT NULL,
  `passward` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `role` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('1', '胡英俊', 'hyj', '0');
INSERT INTO `member` VALUES ('2', '张美丽', 'zml', '0');
INSERT INTO `member` VALUES ('4', 'admin', '123', '1');
INSERT INTO `member` VALUES ('18', '胡图图', 'htt', '0');
INSERT INTO `member` VALUES ('20', '小怪', '123', '0');
