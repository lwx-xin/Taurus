/*
 Navicat Premium Data Transfer

 Source Server         : Taurus
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : taurus

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 15/01/2021 19:04:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for m_code
-- ----------------------------
DROP TABLE IF EXISTS `m_code`;
CREATE TABLE `m_code`  (
  `CODE_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'code id',
  `CODE_GROUP` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分组',
  `CODE_NAME` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `CODE_VALUE` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '值',
  `CODE_ORDER` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '顺序',
  `CODE_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `CODE_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CODE_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CODE_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `CODE_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`CODE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统code列表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of m_code
-- ----------------------------

-- ----------------------------
-- Table structure for s_auth
-- ----------------------------
DROP TABLE IF EXISTS `s_auth`;
CREATE TABLE `s_auth`  (
  `AUTH_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `AUTH_NAME` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `AUTH_LEVEL` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限级别',
  `AUTH_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `AUTH_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `AUTH_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `AUTH_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `AUTH_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`AUTH_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_auth
-- ----------------------------
INSERT INTO `s_auth` VALUES ('00000000-0000-0000-0000-000000000000', '超级管理员', '99', '1', '2021-01-11 07:21:07', '00000000-0000-0000-0000-000000000000', '2021-01-11 07:21:07', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_auth` VALUES ('2', '游客', '0', '1', '2020-12-25 15:32:35', '1', '2020-12-25 15:32:32', '1');
INSERT INTO `s_auth` VALUES ('3', '测试1', '1', '1', '2020-12-25 15:32:51', '1', '2020-12-25 15:32:48', '1');
INSERT INTO `s_auth` VALUES ('4', '测试2', '2', '1', '2020-12-25 15:33:18', '1', '2020-12-25 15:33:15', '1');
INSERT INTO `s_auth` VALUES ('5', '测试3', '3', '1', '2020-12-25 15:33:37', '1', '2020-12-25 15:33:35', '1');

-- ----------------------------
-- Table structure for s_auth_menu
-- ----------------------------
DROP TABLE IF EXISTS `s_auth_menu`;
CREATE TABLE `s_auth_menu`  (
  `AUTH_MENU_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单权限id',
  `AUTH_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `MENU_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  `AUTH_MENU_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `AUTH_MENU_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `AUTH_MENU_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `AUTH_MENU_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `AUTH_MENU_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`AUTH_MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_auth_menu
-- ----------------------------
INSERT INTO `s_auth_menu` VALUES ('7384d5b3-3ba0-11eb-94ca-000ec6545094', '00000000-0000-0000-0000-000000000000', '1', '1', '2020-12-11 19:03:08', '1', '2020-12-11 19:03:08', '1');
INSERT INTO `s_auth_menu` VALUES ('985868eb-3ba0-11eb-94ca-000ec6545094', '00000000-0000-0000-0000-000000000000', '2', '1', '2020-12-11 19:04:10', '1', '2020-12-11 19:04:10', '1');
INSERT INTO `s_auth_menu` VALUES ('985a44e8-3ba0-11eb-94ca-000ec6545094', '00000000-0000-0000-0000-000000000000', '3', '1', '2020-12-11 19:04:10', '1', '2020-12-11 19:04:10', '1');
INSERT INTO `s_auth_menu` VALUES ('985b4fb4-3ba0-11eb-94ca-000ec6545094', '00000000-0000-0000-0000-000000000000', '4', '1', '2020-12-11 19:04:10', '1', '2020-12-11 19:04:10', '1');
INSERT INTO `s_auth_menu` VALUES ('985c5b8d-3ba0-11eb-94ca-000ec6545094', '00000000-0000-0000-0000-000000000000', '5', '1', '2020-12-11 19:04:10', '1', '2020-12-11 19:04:10', '1');
INSERT INTO `s_auth_menu` VALUES ('985d772e-3ba0-11eb-94ca-000ec6545094', '00000000-0000-0000-0000-000000000000', '6', '1', '2020-12-11 19:04:10', '1', '2020-12-11 19:04:10', '1');
INSERT INTO `s_auth_menu` VALUES ('d54dc9f3-5575-11eb-ab73-309c23fb82e4', '2', '1', '1', '2021-01-13 16:03:57', '1', '2021-01-13 16:03:54', '1');

-- ----------------------------
-- Table structure for s_auth_url
-- ----------------------------
DROP TABLE IF EXISTS `s_auth_url`;
CREATE TABLE `s_auth_url`  (
  `AUTH_URL_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求权限id',
  `AUTH_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `URL_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求id',
  `AUTH_URL_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `AUTH_URL_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `AUTH_URL_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `AUTH_URL_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `AUTH_URL_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`AUTH_URL_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '请求权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_auth_url
-- ----------------------------
INSERT INTO `s_auth_url` VALUES ('097464f6d9644389a37ce29ba061c54b', '00000000-0000-0000-0000-000000000000', '6724c150e3b3473d92968d30497d2f70', '1', '2021-01-04 03:30:12', '1', '2021-01-04 03:30:12', '1');
INSERT INTO `s_auth_url` VALUES ('1834d156a4cf429683a67728351a116c', '00000000-0000-0000-0000-000000000000', '9f3320f86f98407080a1d39550e6434b', '1', '2021-01-14 01:28:02', '00000000-0000-0000-0000-000000000000', '2021-01-14 01:28:02', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_auth_url` VALUES ('29abf173d2564d74846cbf8dd6da1871', '00000000-0000-0000-0000-000000000000', '3ac78b6a8542443dad585a3aef94393b', '1', '2021-01-12 02:05:30', '00000000-0000-0000-0000-000000000000', '2021-01-12 02:05:30', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_auth_url` VALUES ('31272ef23d8747b18d17a1e0998c7320', '00000000-0000-0000-0000-000000000000', '90d077f0a38e4a84b07f032387d6ceff', '1', '2021-01-06 10:39:51', '1', '2021-01-06 10:39:51', '1');
INSERT INTO `s_auth_url` VALUES ('4014ccf2d6df4c4f83ccbe5fe7923f97', '00000000-0000-0000-0000-000000000000', '3796931e3e374ea5a2f5fd9f475ebbdb', '1', '2021-01-12 01:43:39', '00000000-0000-0000-0000-000000000000', '2021-01-12 01:43:39', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_auth_url` VALUES ('4051491d83134e26a58c2e09ccd0f7eb', '00000000-0000-0000-0000-000000000000', 'c6f3c460-4e34-11eb-ab73-309c23fb82e4', '1', '2021-01-04 02:35:10', '1', '2021-01-04 02:35:10', '1');
INSERT INTO `s_auth_url` VALUES ('416fa67f-4673-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', '2a17f0ed-4673-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:37:10', '1', '2020-12-25 13:37:15', '1');
INSERT INTO `s_auth_url` VALUES ('7db816e937d442a19bbdf5b4fe12c80c', '00000000-0000-0000-0000-000000000000', '10ea0a9df7c44f2d98f2e5561500415a', '1', '2021-01-13 07:14:33', '00000000-0000-0000-0000-000000000000', '2021-01-13 07:14:33', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_auth_url` VALUES ('82d244f34ce547de85dec9cc96006300', '00000000-0000-0000-0000-000000000000', 'acba69e5f8a3427f92299b192b31c24a', '1', '2020-12-30 06:48:19', '1', '2020-12-30 06:48:19', '1');
INSERT INTO `s_auth_url` VALUES ('8e00b408a8874987832557659e5d299d', '00000000-0000-0000-0000-000000000000', 'c67f1f19cbce477e9c3c8374c19d7dda', '1', '2020-12-31 07:26:45', '1', '2020-12-31 07:26:45', '1');
INSERT INTO `s_auth_url` VALUES ('8f9527c2-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c7f7f1f2-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8f9661ed-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c7f8e72f-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8f9779bf-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c7fa212d-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8f9878ea-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c7fb0af4-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8f99d6b2-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c7fbeb28-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8f9afc58-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c7fcb9b2-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8f9c0beb-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c7fd8afd-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8f9ce4c7-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c7fe6875-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8f9e0686-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c7ff372b-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8f9ee6f9-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c80012cf-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8fa00e20-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c800eb6a-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8fa0ebea-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c801b570-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8fa20523-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c8028836-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8fa2dddf-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c804bfc0-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8fa3b039-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c805f87d-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8fa495fd-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c806f524-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8fa5a981-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c807cddd-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8fa6868f-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c808bc31-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8fa768d6-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c809e2f9-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('8fa83f25-4670-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', 'c80b2033-466f-11eb-ab73-309c23fb82e4', '1', '2020-12-25 13:18:02', '1', '2020-12-25 13:18:02', '1');
INSERT INTO `s_auth_url` VALUES ('9b68272d92a1426aa7ccb968f049d15b', '00000000-0000-0000-0000-000000000000', '3a2e24fcb1a14a08aa02dec5d24a1818', '1', '2021-01-12 02:06:15', '00000000-0000-0000-0000-000000000000', '2021-01-12 02:06:15', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_auth_url` VALUES ('9ce1e5d5961e4078ad08958bd182d802', '00000000-0000-0000-0000-000000000000', 'f70c620356ce43f1a521a4942f74ba47', '1', '2021-01-08 02:53:00', '1', '2021-01-08 02:53:00', '1');
INSERT INTO `s_auth_url` VALUES ('a845601c8eed414c9cdc87843497d4e4', '00000000-0000-0000-0000-000000000000', '9f6a8503e1d44f83a94d9d010b96e857', '1', '2020-12-31 07:12:57', '1', '2020-12-31 07:12:57', '1');
INSERT INTO `s_auth_url` VALUES ('b8ae2250f14944da8d05b8e8d8d29ec8', '00000000-0000-0000-0000-000000000000', '87fd4d02adbb484c868dd06cc2c93b96', '1', '2021-01-12 02:06:55', '00000000-0000-0000-0000-000000000000', '2021-01-12 02:06:55', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_auth_url` VALUES ('bc98efb5-4a4a-11eb-ab73-309c23fb82e4', '00000000-0000-0000-0000-000000000000', '913bd49b-4a4a-11eb-ab73-309c23fb82e4', '1', '2020-12-30 10:57:03', '1', '2020-12-30 10:57:00', '1');
INSERT INTO `s_auth_url` VALUES ('c01747e06ec24f4d88a8c00e2ae9bd36', '5', 'acba69e5f8a3427f92299b192b31c24a', '1', '2020-12-30 06:43:39', '1', '2020-12-30 06:43:39', '1');
INSERT INTO `s_auth_url` VALUES ('c1cf6d4be641442e9f7fb23c464afb46', '00000000-0000-0000-0000-000000000000', 'f344627d7597405987eae2d33f56650d', '1', '2020-12-30 08:43:40', '1', '2020-12-30 08:43:40', '1');
INSERT INTO `s_auth_url` VALUES ('c57d5bdeb895455a831c808a4eecfe93', '3', 'acba69e5f8a3427f92299b192b31c24a', '0', '2020-12-30 06:43:39', '1', '2020-12-30 06:43:39', '1');
INSERT INTO `s_auth_url` VALUES ('d4d15502ccf145c4a239359ba228c160', '2', 'acba69e5f8a3427f92299b192b31c24a', '1', '2020-12-30 06:43:39', '1', '2020-12-30 06:43:39', '1');
INSERT INTO `s_auth_url` VALUES ('d62afe17f9304ba993b5a342bcdbf043', '00000000-0000-0000-0000-000000000000', '1648ec55ecf54668bd84d1cf9708141b', '1', '2021-01-06 10:38:48', '1', '2021-01-06 10:38:48', '1');
INSERT INTO `s_auth_url` VALUES ('d6e1dc55f9324b968a155c94e8158f4e', '00000000-0000-0000-0000-000000000000', 'e11206fcc4e14ff29015f6f0bf5cd91a', '0', '2021-01-12 08:42:08', '00000000-0000-0000-0000-000000000000', '2021-01-12 08:43:38', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_auth_url` VALUES ('e6631987272048348390180c0eb710b5', '00000000-0000-0000-0000-000000000000', 'e080061e19424980a2aa000c65c63c1c', '1', '2021-01-12 02:07:13', '00000000-0000-0000-0000-000000000000', '2021-01-12 02:07:13', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_auth_url` VALUES ('fd1af22ead274b7c88c5e32fb7dd11d7', '00000000-0000-0000-0000-000000000000', '22411febcbf44647926b20954d5602ac', '1', '2021-01-08 01:50:49', '1', '2021-01-08 01:50:49', '1');

-- ----------------------------
-- Table structure for s_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `s_auth_user`;
CREATE TABLE `s_auth_user`  (
  `AUTH_USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户权限id',
  `AUTH_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `AUTH_USER_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `AUTH_USER_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `AUTH_USER_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `AUTH_USER_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `AUTH_USER_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`AUTH_USER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_auth_user
-- ----------------------------
INSERT INTO `s_auth_user` VALUES ('672723b0ed644fccb2ee7148322e3321', '2', '00000000-0000-0000-0000-000000000000', '1', '2021-01-13 08:37:13', '00000000-0000-0000-0000-000000000000', '2021-01-13 08:37:13', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_auth_user` VALUES ('fa0e9cb7f0534562b77cacdc06840efe', '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000', '1', '2021-01-11 07:48:36', '00000000-0000-0000-0000-000000000000', '2021-01-11 07:48:36', '00000000-0000-0000-0000-000000000000');

-- ----------------------------
-- Table structure for s_file
-- ----------------------------
DROP TABLE IF EXISTS `s_file`;
CREATE TABLE `s_file`  (
  `FILE_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件id',
  `FILE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名',
  `FILE_NAME_TIMESTAMP` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名+时间戳',
  `FILE_SIZE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件大小',
  `FILE_TYPE` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件类型',
  `FILE_FOLDER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件所属文件夹id',
  `FILE_OWNER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件所有者',
  `FILE_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径',
  `FILE_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `FILE_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `FILE_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `FILE_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `FILE_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`FILE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_file
-- ----------------------------
INSERT INTO `s_file` VALUES ('3fa635840dd14d408f0504bc37f678e3', '2021-01-13 16_35_39.log', '2021-01-13 16_35_391610526939313.log', '0.00', '4', '0af6f0a5433c4f5884fea64c246def15', '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000/system/log/2021-01-13 16_35_391610526939313.log', '1', '2021-01-13 08:35:39', '00000000-0000-0000-0000-000000000000', '2021-01-13 08:35:39', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_file` VALUES ('c74d47bdf83d4930a3fea3402cfb478f', 'a5.jpg', 'a51610502474420.jpg', '15.89', '5', '04048b9c3e0f40dc9324dd388dcbfc5e', '00000000-0000-0000-0000-000000000000', '00000000-0000-0000-0000-000000000000/system/headImg/a51610502474420.jpg', '1', '2021-01-13 01:47:54', '00000000-0000-0000-0000-000000000000', '2021-01-13 01:47:54', '00000000-0000-0000-0000-000000000000');

-- ----------------------------
-- Table structure for s_folder
-- ----------------------------
DROP TABLE IF EXISTS `s_folder`;
CREATE TABLE `s_folder`  (
  `FOLDER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件夹ID',
  `FOLDER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件夹名称',
  `FOLDER_PARENT` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父文件夹',
  `FOLDER_OWNER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件夹所有者',
  `FOLDER_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `FOLDER_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `FOLDER_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `FOLDER_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `FOLDER_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`FOLDER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件夹信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_folder
-- ----------------------------
INSERT INTO `s_folder` VALUES ('04048b9c3e0f40dc9324dd388dcbfc5e', 'headImg', '091d6a9f034347f39427b1220103a87a', '00000000-0000-0000-0000-000000000000', '1', '2021-01-12 10:25:46', '00000000-0000-0000-0000-000000000000', '2021-01-12 10:25:46', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_folder` VALUES ('091d6a9f034347f39427b1220103a87a', 'system', '7b13ccda6bd54473b5070f5e3ae1164e', '00000000-0000-0000-0000-000000000000', '1', '2021-01-12 10:25:46', '00000000-0000-0000-0000-000000000000', '2021-01-12 10:25:46', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_folder` VALUES ('0af6f0a5433c4f5884fea64c246def15', 'log', '091d6a9f034347f39427b1220103a87a', '00000000-0000-0000-0000-000000000000', '1', '2021-01-12 10:25:46', '00000000-0000-0000-0000-000000000000', '2021-01-12 10:25:46', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_folder` VALUES ('7b13ccda6bd54473b5070f5e3ae1164e', '00000000-0000-0000-0000-000000000000', '', '00000000-0000-0000-0000-000000000000', '1', '2021-01-12 10:25:46', '00000000-0000-0000-0000-000000000000', '2021-01-12 10:25:46', '00000000-0000-0000-0000-000000000000');

-- ----------------------------
-- Table structure for s_log
-- ----------------------------
DROP TABLE IF EXISTS `s_log`;
CREATE TABLE `s_log`  (
  `LOG_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志id',
  `LOG_HAPPEN_TIME` datetime NOT NULL COMMENT '异常发生时间',
  `LOG_FILE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志文件ID',
  `LOG_STATUS` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常状态(待解决,已解决)',
  `LOG_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '触发异常的用户',
  `LOG_REMARK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `LOG_MODULE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '引发异常的模块',
  PRIMARY KEY (`LOG_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_log
-- ----------------------------

-- ----------------------------
-- Table structure for s_menu
-- ----------------------------
DROP TABLE IF EXISTS `s_menu`;
CREATE TABLE `s_menu`  (
  `MENU_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  `MENU_TEXT` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文本',
  `MENU_PARENT` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父菜单',
  `MENU_ORDER` int(11) NOT NULL COMMENT '顺序',
  `MENU_URL` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点击菜单发出的请求(请求id)',
  `MENU_GROUP` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否为菜单组',
  `MENU_ICON` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `MENU_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `MENU_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `MENU_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `MENU_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `MENU_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_menu
-- ----------------------------
INSERT INTO `s_menu` VALUES ('1', '系统管理', NULL, 1, NULL, NULL, 'fa fa-cog', '1', '2020-12-11 18:53:53', '1', '2020-12-11 18:53:58', '1');
INSERT INTO `s_menu` VALUES ('2', '工具', NULL, 2, NULL, NULL, 'fa fa-wrench', '1', '2020-12-11 18:56:15', '1', '2020-12-11 18:56:13', '1');
INSERT INTO `s_menu` VALUES ('3', '用户管理', '1', 1, 'c7f8e72f-466f-11eb-ab73-309c23fb82e4', NULL, NULL, '1', '2020-12-11 18:57:12', '1', '2020-12-11 18:57:05', '1');
INSERT INTO `s_menu` VALUES ('4', '请求管理', '1', 2, 'c7fb0af4-466f-11eb-ab73-309c23fb82e4', NULL, NULL, '1', '2020-12-11 18:58:25', '1', '2020-12-11 18:58:22', '1');
INSERT INTO `s_menu` VALUES ('5', '权限管理', '1', 3, 'c6f3c460-4e34-11eb-ab73-309c23fb82e4', NULL, NULL, '1', '2020-12-11 18:59:20', '1', '2020-12-11 18:59:24', '1');
INSERT INTO `s_menu` VALUES ('6', '菜单管理', '1', 4, '10ea0a9df7c44f2d98f2e5561500415a', NULL, NULL, '1', '2020-12-11 18:59:55', '1', '2020-12-11 18:59:58', '1');
INSERT INTO `s_menu` VALUES ('7', '游戏', '2', 1, NULL, NULL, 'fa fa-gamepad', '1', '2020-12-11 19:00:23', '1', '2020-12-11 19:00:21', '1');
INSERT INTO `s_menu` VALUES ('8', '数独', '7', 2, NULL, NULL, 'iconfont icon-sudoku', '1', '2020-12-11 19:00:45', '1', '2020-12-11 19:00:42', '1');
INSERT INTO `s_menu` VALUES ('9', '贪吃蛇', '7', 1, NULL, NULL, 'iconfont icon-Snake', '1', '2020-12-15 11:23:46', '1', '2020-12-15 11:23:43', '1');

-- ----------------------------
-- Table structure for s_url
-- ----------------------------
DROP TABLE IF EXISTS `s_url`;
CREATE TABLE `s_url`  (
  `URL_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求id',
  `URL_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求地址',
  `URL_TYPE` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方式(get,post)',
  `URL_PLATFORM` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统平台(pc,移动端)',
  `URL_REMARKS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '备注',
  `URL_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `URL_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `URL_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `URL_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `URL_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`URL_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统请求' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_url
-- ----------------------------
INSERT INTO `s_url` VALUES ('10ea0a9df7c44f2d98f2e5561500415a', '/html/system/menu-list.html', '1', '1', '打开菜单列表页面', '1', '2021-01-13 07:14:33', '00000000-0000-0000-0000-000000000000', '2021-01-13 07:14:33', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('1648ec55ecf54668bd84d1cf9708141b', '/html/system/url-param-list.html', '1', '1', '打开请求参数页面', '1', '2021-01-06 10:38:48', '1', '2021-01-06 10:38:48', '1');
INSERT INTO `s_url` VALUES ('22411febcbf44647926b20954d5602ac', '/html/system/config.html', '1', '', '打开系统配置页面', '1', '2021-01-08 01:50:49', '1', '2021-01-08 01:50:49', '1');
INSERT INTO `s_url` VALUES ('2a17f0ed-4673-11eb-ab73-309c23fb82e4', '/web/menu', '1', '1', '获取菜单列表', '1', '2020-12-25 13:36:20', '1', '2020-12-25 13:36:18', '1');
INSERT INTO `s_url` VALUES ('3796931e3e374ea5a2f5fd9f475ebbdb', '/html/system/url-param-edit.html', '1', '', '打开请求参数编辑页面', '1', '2021-01-12 01:43:39', '00000000-0000-0000-0000-000000000000', '2021-01-12 01:43:39', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('3a2e24fcb1a14a08aa02dec5d24a1818', '/web/url-param/.*', '1', '1', '请求参数详细信息', '1', '2021-01-12 02:06:15', '00000000-0000-0000-0000-000000000000', '2021-01-12 02:07:46', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('3ac78b6a8542443dad585a3aef94393b', '/web/url-param', '2', '1', '添加请求参数', '1', '2021-01-12 02:05:30', '00000000-0000-0000-0000-000000000000', '2021-01-12 02:07:55', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('6724c150e3b3473d92968d30497d2f70', '/html/system/auth-edit.html', '1', '1', '打开编辑权限页面', '1', '2021-01-04 03:30:12', '1', '2021-01-04 03:30:12', '1');
INSERT INTO `s_url` VALUES ('87fd4d02adbb484c868dd06cc2c93b96', '/web/url-param/.*', '4', '1', '编辑请求参数信息', '1', '2021-01-12 02:06:55', '00000000-0000-0000-0000-000000000000', '2021-01-12 02:06:55', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('90d077f0a38e4a84b07f032387d6ceff', '/web/url-param', '1', '1', '获取请求参数列表数据', '1', '2021-01-06 10:39:51', '1', '2021-01-12 02:08:04', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('913bd49b-4a4a-11eb-ab73-309c23fb82e4', '/code', '1', '1', '获取code列表', '1', '2020-12-30 10:55:47', '1', '2021-01-06 05:43:36', '1');
INSERT INTO `s_url` VALUES ('9f3320f86f98407080a1d39550e6434b', '/web/menu/.*', '1', '1', '获取菜单详细信息', '1', '2021-01-14 01:28:02', '00000000-0000-0000-0000-000000000000', '2021-01-14 01:28:02', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('9f6a8503e1d44f83a94d9d010b96e857', '/html/login.html', '1', '1', '打开登录页面', '1', '2020-12-31 07:12:57', '1', '2020-12-31 07:12:57', '1');
INSERT INTO `s_url` VALUES ('c2040094231946d7bad635923bed46da', '/getMenuByUser', '1', '1', '根据用户获取菜单列表(首页菜单)', '1', '2021-01-13 06:07:32', '00000000-0000-0000-0000-000000000000', '2021-01-13 06:07:32', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('c6f3c460-4e34-11eb-ab73-309c23fb82e4', '/html/system/auth-list.html', '1', '1', '打开权限列表界面', '1', '2021-01-04 10:30:54', '1', '2021-01-04 02:35:10', '1');
INSERT INTO `s_url` VALUES ('c7f7f1f2-466f-11eb-ab73-309c23fb82e4', '/html/system/home.html', '1', '1', '打开首页', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c7f8e72f-466f-11eb-ab73-309c23fb82e4', '/html/system/user-list.html', '1', '1', '打开用户列表页面', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c7fa212d-466f-11eb-ab73-309c23fb82e4', '/html/system/user-edit.html', '1', '1', '打开编辑用户页面', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c7fb0af4-466f-11eb-ab73-309c23fb82e4', '/html/system/url-list.html', '1', '1', '打开请求列表页面', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c7fbeb28-466f-11eb-ab73-309c23fb82e4', '/html/system/url-edit.html', '1', '1', '打开编辑请求页面', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c7fcb9b2-466f-11eb-ab73-309c23fb82e4', '/web/user', '1', '1', '获取用户列表的数据', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c7fd8afd-466f-11eb-ab73-309c23fb82e4', '/web/user', '2', '1', '添加新用户', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c7fe6875-466f-11eb-ab73-309c23fb82e4', '/web/user/.*', '1', '1', '获取用户的详细信息', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c7ff372b-466f-11eb-ab73-309c23fb82e4', '/web/user/.*', '4', '1', '编辑用户信息', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c80012cf-466f-11eb-ab73-309c23fb82e4', '/web/user/.*', '3', '1', '禁用-启用 用户账号', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c800eb6a-466f-11eb-ab73-309c23fb82e4', '/web/url', '1', '1', '获取请求列表的数据', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c801b570-466f-11eb-ab73-309c23fb82e4', '/web/url', '2', '1', '添加新请求', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c8028836-466f-11eb-ab73-309c23fb82e4', '/web/url/.*', '1', '1', '获取请求的详细信息', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c804bfc0-466f-11eb-ab73-309c23fb82e4', '/web/url/.*', '4', '1', '编辑请求信息', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c805f87d-466f-11eb-ab73-309c23fb82e4', '/web/url/.*', '3', '1', '禁用-启用 请求', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c806f524-466f-11eb-ab73-309c23fb82e4', '/web/auth', '1', '1', '获取权限列表的数据', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c807cddd-466f-11eb-ab73-309c23fb82e4', '/web/auth', '2', '1', '添加新权限', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c808bc31-466f-11eb-ab73-309c23fb82e4', '/web/auth/.*', '1', '1', '获取权限的详细信息', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c809e2f9-466f-11eb-ab73-309c23fb82e4', '/web/auth/.*', '4', '1', '编辑权限信息', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('c80b2033-466f-11eb-ab73-309c23fb82e4', '/web/auth/.*', '3', '1', '禁用-启用 权限', '1', '2020-12-25 13:12:27', '1', '2020-12-25 13:12:27', '1');
INSERT INTO `s_url` VALUES ('e080061e19424980a2aa000c65c63c1c', '/web/url-param/.*', '3', '1', '禁用启用-请求参数', '1', '2021-01-12 02:07:13', '00000000-0000-0000-0000-000000000000', '2021-01-12 02:07:13', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('e11206fcc4e14ff29015f6f0bf5cd91a', '/getAjaxCheckJson', '1', '1', '获取ajax参数验证所需的json数据', '1', '2021-01-12 08:42:08', '00000000-0000-0000-0000-000000000000', '2021-01-12 08:43:38', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('f344627d7597405987eae2d33f56650d', '/loginUser', '1', '1', '获取当前登录用户信息', '1', '2020-12-30 08:43:40', '1', '2021-01-08 02:46:14', '1');
INSERT INTO `s_url` VALUES ('f70c620356ce43f1a521a4942f74ba47', '/loginUser', '4', '1', '编辑登录用户信息', '1', '2021-01-08 02:53:00', '1', '2021-01-08 02:53:00', '1');

-- ----------------------------
-- Table structure for s_url_param
-- ----------------------------
DROP TABLE IF EXISTS `s_url_param`;
CREATE TABLE `s_url_param`  (
  `URL_PARAM_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求参数id',
  `URL_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求id',
  `URL_PARAM_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数名',
  `URL_PARAM_VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数值，正则表达式',
  `URL_PARAM_REQUIRED` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否为必须参数',
  `URL_PARAM_NULL` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否允许为空值',
  `URL_PARAM_REMARK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数备注',
  `URL_PARAM_ERR_HINT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数异常时候的提示',
  `URL_PARAM_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `URL_PARAM_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `URL_PARAM_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `URL_PARAM_MODIFY_TIME` datetime NOT NULL COMMENT '编辑事件',
  `URL_PARAM_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`URL_PARAM_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '请求参数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_url_param
-- ----------------------------
INSERT INTO `s_url_param` VALUES ('eb685cc1df47418a8df5a96bd56b9473', 'c7ff372b-466f-11eb-ab73-309c23fb82e4', 'userQq', '^[1-9][0-9]{4,9}$', '1', '1', 'QQ号', '请输入正确的QQ号', '1', '2021-01-12 06:08:33', '00000000-0000-0000-0000-000000000000', '2021-01-12 08:54:22', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url_param` VALUES ('ff8e7598-48d3-11eb-ab73-309c23fb82e4', 'c7ff372b-466f-11eb-ab73-309c23fb82e4', 'userEmail', '^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$', '1', '1', '用户邮箱', '请输入正确的邮箱地址', '1', '2020-12-28 14:14:49', '1', '2021-01-12 08:54:37', '00000000-0000-0000-0000-000000000000');

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user`  (
  `USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `USER_NUMBER` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `USER_PWD` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `USER_NAME` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `USER_HEAD` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '头像(文件id)',
  `USER_PLATFORM` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号能登陆的平台(pc,移动端)',
  `USER_QQ` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ',
  `USER_EMAIL` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `USER_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `USER_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `USER_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `USER_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `USER_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES ('00000000-0000-0000-0000-000000000000', 'admin', 'a8abc0eb28fe60c7a568bea0e5cf1ac2', '管理员001', 'c74d47bdf83d4930a3fea3402cfb478f', '1', '123456789', '123@qs.com', '1', '2021-01-12 10:25:46', '00000000-0000-0000-0000-000000000000', '2021-01-13 08:37:13', '00000000-0000-0000-0000-000000000000');

-- ----------------------------
-- Table structure for s_user_login
-- ----------------------------
DROP TABLE IF EXISTS `s_user_login`;
CREATE TABLE `s_user_login`  (
  `LOGIN_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `LOGIN_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `LOGIN_TIME` datetime NOT NULL COMMENT '登录时间',
  `LOGIN_ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录地点',
  `LOGIN_PLATFORM` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录平台(PC端,移动端)',
  PRIMARY KEY (`LOGIN_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登录记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_user_login
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;