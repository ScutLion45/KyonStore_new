/*
Navicat MySQL Data Transfer

Source Server         : mysql45
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : kyonstore

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2020-06-10 12:07:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for browse
-- ----------------------------
DROP TABLE IF EXISTS `browse`;
CREATE TABLE `browse` (
  `bid` int(10) NOT NULL AUTO_INCREMENT,
  `btime` datetime NOT NULL,
  `goodsid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `userid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `bstay` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`bid`),
  KEY `browse_ibfk_1` (`goodsid`),
  KEY `browse_ibfk_2` (`userid`),
  CONSTRAINT `browse_ibfk_1` FOREIGN KEY (`goodsid`) REFERENCES `goods` (`gid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `browse_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of browse
-- ----------------------------
INSERT INTO `browse` VALUES ('1', '2019-12-14 23:58:58', 'g001', '45', '50');
INSERT INTO `browse` VALUES ('4', '2019-12-15 22:11:24', '1206-0915-a15b467d-46bc-4a07-a62e-855efce40172', '45', '45');
INSERT INTO `browse` VALUES ('5', '2019-12-06 22:47:34', '1206-0913-944d1a57-35cc-43ec-aa7e-93266b44d8a9', '45', '45');
INSERT INTO `browse` VALUES ('6', '2019-12-15 20:57:06', '1206-0914-90bd5075-baa6-438d-abac-611e26b322f5', '45', '45');
INSERT INTO `browse` VALUES ('7', '2019-12-06 17:46:24', '1206-0923-87c91bae-2310-4944-905b-5dda58cafa4c', '45', '45');
INSERT INTO `browse` VALUES ('8', '2019-12-06 17:47:34', '1206-0912-054983ce-0863-4544-a885-3ab471c8e9e8', '45', '45');
INSERT INTO `browse` VALUES ('9', '2019-12-06 18:43:47', '1206-0924-1f0af959-1152-43e8-90c6-f09b6acf0e79', '45', '45');
INSERT INTO `browse` VALUES ('10', '2019-12-15 11:22:43', '1206-0921-c62b8d39-a26f-46e7-a982-0cb29bd21b02', '45', '45');
INSERT INTO `browse` VALUES ('11', '2019-12-15 11:26:26', 'g002', '45', '45');
INSERT INTO `browse` VALUES ('12', '2019-12-14 23:50:54', '1206-0000-cc3f3212-2329-4e28-889b-3d894a4f7237', '45', '45');
INSERT INTO `browse` VALUES ('13', '2019-12-15 20:57:26', '1215-2037-9f4bd7a9-163d-44c4-9ef0-89c320467436', '45', '45');
INSERT INTO `browse` VALUES ('14', '2019-12-15 22:14:13', '1206-0921-6e103823-5f28-49e7-9f9a-4f304475cd7d', '45', '45');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `gid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `gname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ginfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `gtype` int(10) NOT NULL DEFAULT '0',
  `gpubtime` datetime NOT NULL,
  `gprice` float(10,2) NOT NULL DEFAULT '0.00',
  `gbrowse` int(10) NOT NULL DEFAULT '0',
  `gsell` int(10) NOT NULL DEFAULT '0',
  `gstate` int(10) NOT NULL DEFAULT '1',
  `gimg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建商品和修改商品时记录图片后缀格式',
  `gvolume` float(10,2) NOT NULL DEFAULT '0.00',
  `pub` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`gid`),
  KEY `pub` (`pub`) USING BTREE,
  CONSTRAINT `goods_ibfk_1` FOREIGN KEY (`pub`) REFERENCES `publisher` (`puid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1205-1935-b2644e4e-9b7a-46b2-9e0a-832a0c76e110', '【A-TEEN 1】 EP.6 偶尔不会照牌理出牌', '平日里安静的孩子生气的话, 会这么可怕 A-TEEN推荐的和朋友和好的三个方法：一起吃好吃的，一起玩儿游戏，送上精心准备的礼物', '3', '2019-12-05 19:35:47', '45.06', '0', '0', '1', '.png', '0.00', 'playlist1');
INSERT INTO `goods` VALUES ('1205-2358-55aeba38-7117-4f65-9436-c80077f47d8f', '【A-TEEN 1】 Ep.7 他们的世界', '', '3', '2019-12-05 23:58:47', '45.07', '0', '0', '1', '', '0.00', 'playlist1');
INSERT INTO `goods` VALUES ('1206-0000-cc3f3212-2329-4e28-889b-3d894a4f7237', '【A-TEEN 1】 Ep.8 偶数，奇数', '', '3', '2019-12-06 00:00:16', '45.08', '1', '0', '1', '.jpg', '0.00', 'playlist1');
INSERT INTO `goods` VALUES ('1206-0912-054983ce-0863-4544-a885-3ab471c8e9e8', '复仇者联盟1【2012】', '', '1', '2019-12-06 09:12:57', '2012.50', '1', '0', '1', '.jpg', '0.00', '1206-0911-99fa9759-097c-49d5-951d-32ef3b03019e');
INSERT INTO `goods` VALUES ('1206-0913-944d1a57-35cc-43ec-aa7e-93266b44d8a9', '复仇者联盟2-奥创纪元【2015】', '', '1', '2019-12-06 09:13:49', '2015.50', '1', '0', '1', '.jpg', '0.00', '1206-0911-99fa9759-097c-49d5-951d-32ef3b03019e');
INSERT INTO `goods` VALUES ('1206-0914-90bd5075-baa6-438d-abac-611e26b322f5', '复仇者联盟3-无限战争【2018】', '', '1', '2019-12-06 09:14:29', '2018.50', '1', '0', '1', '.jpg', '0.00', '1206-0911-99fa9759-097c-49d5-951d-32ef3b03019e');
INSERT INTO `goods` VALUES ('1206-0915-a15b467d-46bc-4a07-a62e-855efce40172', '复仇者联盟4-终局之战【2019】', '', '1', '2019-12-06 09:15:03', '2019.40', '1', '0', '1', '.jpg', '0.00', '1206-0911-99fa9759-097c-49d5-951d-32ef3b03019e');
INSERT INTO `goods` VALUES ('1206-0921-6e103823-5f28-49e7-9f9a-4f304475cd7d', 'A-TEEN', 'A-TEEN OST Part.3', '5', '2019-12-06 09:21:04', '18.00', '1', '0', '1', '.jpg', '0.00', '1206-0919-e555e205-31fb-4f25-802a-4e7eb3d6b6db');
INSERT INTO `goods` VALUES ('1206-0921-c62b8d39-a26f-46e7-a982-0cb29bd21b02', '9-TEEN', 'A-TEEN2 OST Part.2', '5', '2019-12-06 09:21:44', '19.19', '1', '3', '1', '.jpg', '57.38', '1206-0919-e555e205-31fb-4f25-802a-4e7eb3d6b6db');
INSERT INTO `goods` VALUES ('1206-0923-87c91bae-2310-4944-905b-5dda58cafa4c', '【A-TEEN 1】合集', '喜欢A-TEEN的朋友快入手收藏吧', '2', '2019-12-06 09:23:42', '450.18', '1', '0', '1', '.jpeg', '0.00', 'playlist1');
INSERT INTO `goods` VALUES ('1206-0924-1f0af959-1152-43e8-90c6-f09b6acf0e79', '【A-TEEN 2】合集', '我们的故事还在继续~', '2', '2019-12-06 09:24:36', '450.19', '1', '0', '1', '.jpg', '0.00', 'playlist1');
INSERT INTO `goods` VALUES ('1215-2034-32f1393b-798c-45c0-b40e-4fb9574d14c1', '【A-TEEN 1】 EP.9 老实待着的话，就会被夹在中间面', '老实待着，就会被苦夹在中间 最好的朋友要绝交的时候心情会怎么样？', '3', '2019-12-15 20:34:15', '45.09', '0', '0', '1', '', '0.00', 'playlist1');
INSERT INTO `goods` VALUES ('1215-2037-9f4bd7a9-163d-44c4-9ef0-89c320467436', '【A-TEEN 1】 EP.10 第十集 双面夹击与三角关系', '', '3', '2019-12-15 20:37:32', '45.10', '1', '0', '1', '.jpeg', '0.00', 'playlist1');
INSERT INTO `goods` VALUES ('g001', '【A-TEEN 1】 EP.1 不平凡，其实是不想平凡', '让女人混淆的男人们的行为就是这个！ 你们身边也有让你混淆的男性亲故吗？', '3', '2019-08-31 13:10:07', '45.01', '1', '1', '1', '.png', '45.00', 'playlist1');
INSERT INTO `goods` VALUES ('g002', '【A-TEEN 1】 EP.2 人都有双重面向', '', '3', '2019-08-31 13:10:08', '45.00', '1', '3', '1', '', '135.00', 'playlist1');
INSERT INTO `goods` VALUES ('g003', '【A-TEEN 1】 EP.3 你和我和他', '如果有劈腿的前男友就请这样做吧 告诉我安慰悲伤的朋友的办法', '3', '2019-08-31 13:10:09', '45.00', '0', '3', '1', '', '135.00', 'playlist1');
INSERT INTO `goods` VALUES ('g004', '【A-TEEN 1】 EP.4 今天这种日子就让它过去吧', '在考试期间我想这样被表白 我也想这样被表白????', '3', '2019-11-29 00:19:45', '45.04', '0', '0', '1', '', '0.00', 'playlist1');
INSERT INTO `goods` VALUES ('g005', '【A-TEEN 1】 EP.5', '', '3', '2019-11-30 13:10:09', '45.00', '0', '0', '1', '', '0.00', 'playlist1');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `oid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ostate` int(10) NOT NULL DEFAULT '1',
  `otime` datetime NOT NULL,
  `onum` int(10) NOT NULL DEFAULT '1',
  `goodsid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `goodsprice` float(10,2) NOT NULL DEFAULT '0.00',
  `userid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`oid`),
  KEY `goodsid` (`goodsid`) USING BTREE,
  KEY `userid` (`userid`) USING BTREE,
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`goodsid`) REFERENCES `goods` (`gid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('1207-1004-82c21567-1259-49dc-89e4-19da7bc75f90', '3', '2019-12-07 10:04:56', '1', 'g002', '45.00', '46');
INSERT INTO `order` VALUES ('1207-1006-24c5b825-0b6c-4446-a90e-5d5f7dc947fe', '2', '2019-12-07 10:06:32', '2', 'g002', '45.00', '46');
INSERT INTO `order` VALUES ('1215-1117-5e816b36-262d-4fd7-9638-53593b422741', '3', '2019-12-15 11:19:32', '1', '1206-0921-c62b8d39-a26f-46e7-a982-0cb29bd21b02', '19.00', '45');
INSERT INTO `order` VALUES ('1215-1122-8fa1b714-c4dd-4f7a-89c8-c0715c0da952', '3', '2019-12-15 11:22:50', '2', '1206-0921-c62b8d39-a26f-46e7-a982-0cb29bd21b02', '19.19', '45');
INSERT INTO `order` VALUES ('1215-2038-87213e01-1dd4-437a-a9fe-af374d3cf0c0', '1', '2019-12-15 20:38:44', '1', '1215-2037-9f4bd7a9-163d-44c4-9ef0-89c320467436', '45.10', '45');
INSERT INTO `order` VALUES ('o001', '3', '2019-11-29 22:58:45', '1', 'g001', '45.00', '45');
INSERT INTO `order` VALUES ('o002', '3', '2019-11-29 23:45:00', '2', 'g002', '45.00', '45');
INSERT INTO `order` VALUES ('o003', '3', '2019-11-29 23:45:00', '3', 'g003', '45.00', '45');
INSERT INTO `order` VALUES ('o004', '1', '2019-11-29 23:45:00', '1', 'g004', '45.04', '45');
INSERT INTO `order` VALUES ('o005', '2', '2019-11-29 23:45:00', '1', 'g005', '45.00', '47');
INSERT INTO `order` VALUES ('o006', '1', '2019-11-29 23:45:00', '2', 'g003', '45.06', '46');
INSERT INTO `order` VALUES ('o007', '1', '2019-11-29 23:45:00', '2', 'g003', '45.07', '47');

-- ----------------------------
-- Table structure for publisher
-- ----------------------------
DROP TABLE IF EXISTS `publisher`;
CREATE TABLE `publisher` (
  `puid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `pname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ppwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `pinfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`puid`),
  UNIQUE KEY `pname` (`pname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of publisher
-- ----------------------------
INSERT INTO `publisher` VALUES ('1206-0911-99fa9759-097c-49d5-951d-32ef3b03019e', '漫威影业', 'marvel', 'MARVEL_STUDIO');
INSERT INTO `publisher` VALUES ('1206-0919-e555e205-31fb-4f25-802a-4e7eb3d6b6db', 'seventeen/17', '17', 'with CARAT(克拉)');
INSERT INTO `publisher` VALUES ('playlist1', 'PLAYLIST', '123', 'playlist_official');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `str` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', 'lalala');
INSERT INTO `test` VALUES ('2', 'lalala');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `umail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `uname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `upwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ubalance` float(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uname` (`uname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('45', '45@kyon.com', 'kyon45', '123', '792.63');
INSERT INTO `user` VALUES ('46', '46@kyon.com', 'kyon46', '123', '265.01');
INSERT INTO `user` VALUES ('47', '47@kyon.com', 'kyon47', '123', '445.01');

-- ----------------------------
-- Procedure structure for browse_remove
-- ----------------------------
DROP PROCEDURE IF EXISTS `browse_remove`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `browse_remove`(IN `ggid` varchar(255) character set utf8,IN `uuid` varchar(255) character set utf8)
BEGIN
DECLARE t datetime;
START TRANSACTION;
	SELECT btime INTO t FROM browse WHERE goodsid=ggid AND userid=uuid;
	IF t is not NULL THEN
		DELETE FROM browse
		WHERE goodsid=ggid AND userid=uuid;
	END IF;
COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for browse_update
-- ----------------------------
DROP PROCEDURE IF EXISTS `browse_update`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `browse_update`(IN bt datetime,IN `ggid` varchar(255) character set utf8,IN `uuid` varchar(255) character set utf8)
BEGIN
DECLARE t datetime;
START TRANSACTION;
	SELECT btime INTO t FROM browse WHERE goodsid=ggid AND userid=uuid;
	IF t is NULL THEN
		INSERT INTO browse(btime, goodsid, userid, bstay)
		VALUES(bt, ggid, uuid, 0);
		UPDATE goods SET gbrowse=gbrowse+1
		WHERE gid=ggid;
	ELSE
		UPDATE browse SET btime=bt
		WHERE goodsid=ggid AND userid=uuid;
    # new
		UPDATE goods SET gbrowse=gbrowse+1
		WHERE gid=ggid;
	END IF;
COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for browse_update_stay
-- ----------------------------
DROP PROCEDURE IF EXISTS `browse_update_stay`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `browse_update_stay`(IN bbid int, IN stay_add int)
BEGIN
# DECLARE bst int;
START TRANSACTION;
  # SELECT bstay INTO bst FROM browse WHERE bid=bbid;
  UPDATE browse SET bstay=bstay+stay_add WHERE bid=bbid;
COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for goods_create
-- ----------------------------
DROP PROCEDURE IF EXISTS `goods_create`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `goods_create`(IN `ggid` varchar(255) character set utf8,IN `gnm` varchar(255) character set utf8,IN `gif` varchar(255) character set utf8,IN `gty` int(10),IN `gpt` datetime,IN `gp` float(10,2),IN `gim` varchar(255) character set utf8,IN `pb` varchar(255) character set utf8)
BEGIN
DECLARE t datetime;
START TRANSACTION;
	SELECT gpubtime INTO t FROM goods WHERE gid=ggid;
	IF t is NULL THEN
		INSERT INTO goods
		VALUES(ggid, gnm, gif, gty, gpt, gp, 0, 0, 1, gim, 0, pb);
	END IF;
COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for goods_edit
-- ----------------------------
DROP PROCEDURE IF EXISTS `goods_edit`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `goods_edit`(IN `ggid` varchar(255) character set utf8,IN `gnm` varchar(255) character set utf8,IN `gif` varchar(255) character set utf8,IN `gty` int(10),IN `gp` float(10,2),IN `gim` varchar(255) character set utf8)
BEGIN
DECLARE t datetime;
START TRANSACTION;
	SELECT gpubtime INTO t FROM goods WHERE gid=ggid;
	IF t is not NULL THEN
		UPDATE goods SET
		gname=gnm, ginfo=gif, gtype=gty, gprice=gp
		WHERE gid=ggid;
		UPDATE `order` SET goodsprice=gp WHERE goodsid=ggid AND ostate=1; 
	END IF;
	IF gim<>'' THEN
		UPDATE goods SET gimg=gim WHERE gid=ggid;
	END IF;
COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for goods_off
-- ----------------------------
DROP PROCEDURE IF EXISTS `goods_off`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `goods_off`(IN `ggid` varchar(255) character set utf8)
BEGIN
DECLARE t datetime;
DECLARE s INT(10);
START TRANSACTION;
	SELECT gpubtime,gstate INTO t,s FROM goods WHERE gid=ggid;
	IF t is not NULL AND s=1 THEN
		UPDATE goods SET gstate=2 WHERE gid=ggid;
		UPDATE `order` SET ostate=4 WHERE goodsid=ggid AND ostate=1;
		#DELETE FROM `order` WHERE goodsid=ggid AND ostate=2;
	END IF;
COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for goods_on
-- ----------------------------
DROP PROCEDURE IF EXISTS `goods_on`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `goods_on`(IN `ggid` varchar(255) character set utf8)
BEGIN
DECLARE t datetime;
DECLARE s INT(10);
START TRANSACTION;
	SELECT gpubtime,gstate INTO t,s FROM goods WHERE gid=ggid;
	IF t is not NULL AND s=2 THEN
		UPDATE goods SET gstate=1 WHERE gid=ggid;
		UPDATE `order` SET ostate=1 WHERE goodsid=ggid AND ostate=4;
	END IF;
COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for order_create
-- ----------------------------
DROP PROCEDURE IF EXISTS `order_create`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `order_create`(IN `ooid` varchar(255) character set utf8,IN `ot` datetime,IN `oon` int(10),IN `ggid` varchar(255) character set utf8, IN `gp` float(10,2),IN `uuid` varchar(255) character set utf8)
BEGIN
DECLARE t datetime;
START TRANSACTION;
	SELECT otime INTO t FROM `order` WHERE oid=ooid;
	IF t is NULL THEN
		INSERT INTO `order`
		VALUES(ooid, 1, ot, oon, ggid, gp, uuid);
	END IF;
COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for order_edit
-- ----------------------------
DROP PROCEDURE IF EXISTS `order_edit`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `order_edit`(IN `ooid` varchar(255) character set utf8,IN `oon` int(10))
BEGIN
DECLARE t datetime;
START TRANSACTION;
	SELECT otime INTO t FROM `order` WHERE oid=ooid;
	IF t is not NULL THEN
		UPDATE `order`
		SET onum=oon
		WHERE oid=ooid;
	END IF;
COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for order_remove
-- ----------------------------
DROP PROCEDURE IF EXISTS `order_remove`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `order_remove`(IN `ooid` varchar(255) character set utf8)
BEGIN
DECLARE t datetime;
DECLARE s INT(10);
START TRANSACTION;
	SELECT otime,ostate INTO t,s FROM `order` WHERE oid=ooid;
	IF t is not NULL AND s=1 THEN
		DELETE FROM `order`
		WHERE oid=ooid;
	END IF;
COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for order_update
-- ----------------------------
DROP PROCEDURE IF EXISTS `order_update`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `order_update`(IN `ooid` varchar(255) character set utf8, IN `ot` datetime)
BEGIN
DECLARE t datetime;
DECLARE s INT(10);
DECLARE n INT(10);
DECLARE ggid varchar(255) character set utf8;
DECLARE uuid varchar(255) character set utf8;
DECLARE gp FLOAT(10,2);
START TRANSACTION;
	SELECT otime,ostate,onum,goodsid,userid INTO t,s,n,ggid,uuid FROM `order` WHERE oid=ooid;
	IF t is not NULL AND s<3 THEN
		UPDATE `order` SET ostate=s+1 WHERE oid=ooid;

		IF s=1 THEN
			SELECT gprice into gp FROM goods WHERE gid=ggid;
			UPDATE user
			SET ubalance=ubalance-n*gp
			WHERE uid=uuid;
			UPDATE `order` SET otime=ot WHERE oid=ooid;
		ELSEIF s=2 THEN
			SELECT gprice into gp FROM goods WHERE gid=ggid;
			UPDATE goods
			SET gsell=gsell+n, gvolume=gvolume+n*gp
			WHERE gid=ggid;
			
		END IF;
	END IF;
COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pub_load_order
-- ----------------------------
DROP PROCEDURE IF EXISTS `pub_load_order`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pub_load_order`(IN `puid` varchar(255) character set utf8)
BEGIN
START TRANSACTION;

	SELECT o.*, g.*, u.*
	FROM `order` o LEFT JOIN goods g ON o.goodsid=g.gid
								 LEFT JOIN `user` u ON o.userid=u.uid
	WHERE g.pub=puid AND (o.ostate=2 OR o.ostate=3)
	ORDER BY o.ostate ASC, o.otime ASC;

COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pub_search_goods
-- ----------------------------
DROP PROCEDURE IF EXISTS `pub_search_goods`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pub_search_goods`(IN `puid` varchar(255) character set utf8,IN `gtp` int(10), IN `gpt_begin` datetime, IN `gpt_end` datetime, IN `gs` int(10), IN `gn` varchar(255) character set utf8)
BEGIN
START TRANSACTION;

	IF gtp=0 AND gs=0 THEN
	#	SELECT g.gid, g.gname, g.ginfo, g.gtype, g.gpubtime, g.gprice, g.gbrowse, g.gsell, g.gstate,
	#				 p.puid, p.pname, p.ppwd, p.pinfo
		SELECT g.*, p.*
		FROM goods g LEFT JOIN publisher p 
		ON g.pub=p.puid
		WHERE g.pub=puid
			AND g.gpubtime BETWEEN gpt_begin AND gpt_end
			AND g.gname LIKE gn
		ORDER BY g.gpubtime DESC;
	ELSEIF gtp<>0 AND gs=0 THEN
		SELECT g.*, p.*
		FROM goods g LEFT JOIN publisher p 
		ON g.pub=p.puid
		WHERE g.pub=puid
			AND g.gtype=gtp
			AND g.gpubtime BETWEEN gpt_begin AND gpt_end
			AND g.gname LIKE gn
		ORDER BY g.gpubtime DESC;
	ELSEIF gtp=0 AND gs<>0 THEN
		SELECT g.*, p.*
		FROM goods g LEFT JOIN publisher p 
		ON g.pub=p.puid
		WHERE g.pub=puid
			AND g.gpubtime BETWEEN gpt_begin AND gpt_end
			AND g.gstate=gs
			AND g.gname LIKE gn
		ORDER BY g.gpubtime DESC;
	ELSEIF gtp<>0 AND gs<>0 THEN
		SELECT g.*, p.*
		FROM goods g LEFT JOIN publisher p 
		ON g.pub=p.puid
		WHERE g.pub=puid
			AND g.gtype=gtp
			AND g.gpubtime BETWEEN gpt_begin AND gpt_end
			AND g.gstate=gs
			AND g.gname LIKE gn
		ORDER BY g.gpubtime DESC;
	END IF;

COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for testProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `testProcedure`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `testProcedure`(IN `str_val` varchar(255) character set utf8)
BEGIN
START TRANSACTION;
INSERT INTO test VALUES(default, str_val);
COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for user_load_browse
-- ----------------------------
DROP PROCEDURE IF EXISTS `user_load_browse`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `user_load_browse`(IN `uuid` varchar(255) character set utf8)
BEGIN
START TRANSACTION;

	SELECT b.*, g.*, p.*
	FROM browse b LEFT JOIN goods g ON b.goodsid=g.gid
								LEFT JOIN publisher p ON g.pub=p.puid
	WHERE b.userid=uuid
	ORDER BY b.btime DESC;

COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for user_load_latest_goods
-- ----------------------------
DROP PROCEDURE IF EXISTS `user_load_latest_goods`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `user_load_latest_goods`(IN `gtp` int(10))
BEGIN
START TRANSACTION;

	IF gtp<>0 THEN
		SELECT g.*, p.*
		FROM goods g LEFT JOIN publisher p
		ON g.pub=p.puid
		WHERE g.gtype=gtp
			AND g.gstate=1
		ORDER BY g.gpubtime DESC
		LIMIT 0,8;
	END IF;

COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for user_load_order
-- ----------------------------
DROP PROCEDURE IF EXISTS `user_load_order`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `user_load_order`(IN `uuid` varchar(255) character set utf8, IN `arg` int(10))
BEGIN
START TRANSACTION;

	IF arg=1 THEN
		SELECT o.*, g.*, p.*
		FROM `order` o LEFT JOIN goods g ON o.goodsid=g.gid
									 LEFT JOIN publisher p ON g.pub=p.puid
		WHERE o.userid=uuid AND (o.ostate=1 or o.ostate=4)
		ORDER BY o.otime DESC;
	ELSEIF arg=2 THEN
		SELECT o.*, g.*, p.*
		FROM `order` o LEFT JOIN goods g ON o.goodsid=g.gid
									 LEFT JOIN publisher p ON g.pub=p.puid
		WHERE o.userid=uuid AND (o.ostate=2 or o.ostate=3)
		ORDER BY o.otime DESC;
	END IF;

COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for user_search_goods
-- ----------------------------
DROP PROCEDURE IF EXISTS `user_search_goods`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `user_search_goods`(IN `gtp` int(10), IN `gpt_begin` datetime, IN `gpt_end` datetime,  IN `gn` varchar(255) character set utf8)
BEGIN
START TRANSACTION;

	IF gtp=0 THEN
		SELECT g.*, p.*
		FROM goods g LEFT JOIN publisher p
		ON g.pub=p.puid
		WHERE g.gpubtime BETWEEN gpt_begin AND gpt_end
			AND g.gname LIKE gn
			AND g.gstate=1
		ORDER BY g.gpubtime DESC;
	ELSE
		SELECT g.*, p.*
		FROM goods g LEFT JOIN publisher p
		ON g.pub=p.puid
		WHERE g.gtype=gtp
			AND g.gpubtime BETWEEN gpt_begin AND gpt_end
			AND g.gname LIKE gn
			AND g.gstate=1
		ORDER BY g.gpubtime DESC;
	END IF;

COMMIT;
END
;;
DELIMITER ;
