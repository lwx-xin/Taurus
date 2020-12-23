/*
 Navicat Premium Data Transfer

 Source Server         : Taurus
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : taurus

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 08/11/2020 18:20:34
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
  `CODE_CREATE_TIME` datetime(0) NOT NULL COMMENT '创建时间',
  `CODE_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CODE_MODIFY_TIME` datetime(0) NOT NULL COMMENT '编辑时间',
  `CODE_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`CODE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统code列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_code
-- ----------------------------
INSERT INTO `m_code` VALUES ('1', 'del_flg', '启用', '1', '0', '1', '2020-11-08 14:40:13', '0', '2020-11-08 14:40:25', '0');
INSERT INTO `m_code` VALUES ('2', 'del_flg', '禁用', '0', '1', '1', '2020-11-08 14:40:13', '0', '2020-11-08 14:40:25', '0');

-- ----------------------------
-- Table structure for s_auth
-- ----------------------------
DROP TABLE IF EXISTS `s_auth`;
CREATE TABLE `s_auth`  (
  `AUTH_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `AUTH_NAME` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `AUTH_LEVEL` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限级别',
  `AUTH_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `AUTH_CREATE_TIME` datetime(0) NOT NULL COMMENT '创建时间',
  `AUTH_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `AUTH_MODIFY_TIME` datetime(0) NOT NULL COMMENT '编辑时间',
  `UAUTH_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`AUTH_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_auth
-- ----------------------------

-- ----------------------------
-- Table structure for s_auth_url
-- ----------------------------
DROP TABLE IF EXISTS `s_auth_url`;
CREATE TABLE `s_auth_url`  (
  `AUTH_URL_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求权限id',
  `AUTH_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `URL_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求id',
  `AUTH_URL_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `AUTH_URL_CREATE_TIME` datetime(0) NOT NULL COMMENT '创建时间',
  `AUTH_URL_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `AUTH_URL_MODIFY_TIME` datetime(0) NOT NULL COMMENT '编辑时间',
  `AUTH_URL_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`AUTH_URL_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '请求权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_auth_url
-- ----------------------------

-- ----------------------------
-- Table structure for s_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `s_auth_user`;
CREATE TABLE `s_auth_user`  (
  `AUTH_USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户权限id',
  `AUTH_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `AUTH_USER_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `AUTH_USER_CREATE_TIME` datetime(0) NOT NULL COMMENT '创建时间',
  `AUTH_USER_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `AUTH_USER_MODIFY_TIME` datetime(0) NOT NULL COMMENT '编辑时间',
  `AUTH_USER_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`AUTH_USER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_auth_user
-- ----------------------------

-- ----------------------------
-- Table structure for s_menu
-- ----------------------------
DROP TABLE IF EXISTS `s_menu`;
CREATE TABLE `s_menu`  (
  `MENU_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  `MENU_TEXT` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文本',
  `MENU_PARENT` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '父菜单',
  `MENU_ORDER` int(11) NOT NULL COMMENT '顺序',
  `MENU_URL` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '点击菜单发出的请求(请求id)',
  `MENU_ICON` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单图标',
  `MENU_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `MENU_CREATE_TIME` datetime(0) NOT NULL COMMENT '创建时间',
  `MENU_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `MENU_MODIFY_TIME` datetime(0) NOT NULL COMMENT '编辑时间',
  `MENU_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_menu
-- ----------------------------

-- ----------------------------
-- Table structure for s_url
-- ----------------------------
DROP TABLE IF EXISTS `s_url`;
CREATE TABLE `s_url`  (
  `URL_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求id',
  `URL_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求地址',
  `URL_TYPE` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方式(get,post)',
  `URL_PARAMS` json NULL COMMENT '请求参数(json格式)',
  `URL_REMARKS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '备注',
  `URL_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `URL_CREATE_TIME` datetime(0) NOT NULL COMMENT '创建时间',
  `URL_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `URL_MODIFY_TIME` datetime(0) NOT NULL COMMENT '编辑时间',
  `URL_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`URL_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统请求' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_url
-- ----------------------------

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user`  (
  `USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `USER_NUMBER` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `USER_PWD` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `USER_NAME` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `USER_HEAD` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像(文件id)',
  `USER_QQ` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ',
  `USER_EMAIL` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `USER_DEL_FLG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `USER_CREATE_TIME` datetime(0) NOT NULL COMMENT '创建时间',
  `USER_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `USER_MODIFY_TIME` datetime(0) NOT NULL COMMENT '编辑时间',
  `USER_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_user
-- ----------------------------

-- ----------------------------
-- Table structure for s_user_login
-- ----------------------------
DROP TABLE IF EXISTS `s_user_login`;
CREATE TABLE `s_user_login`  (
  `LOGIN_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `LOGIN_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `LOGIN_TIME` datetime(0) NOT NULL COMMENT '登录时间',
  `LOGIN_ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录地点',
  `LOGIN_PLATFORM` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录平台(PC端,移动端)',
  PRIMARY KEY (`LOGIN_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登录记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_user_login
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
