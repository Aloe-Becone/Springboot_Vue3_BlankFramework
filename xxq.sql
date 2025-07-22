-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: xxq
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` text COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `info` text COLLATE utf8mb4_vi_0900_as_cs,
  `pic` varchar(256) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `statue` varchar(64) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `time` varchar(128) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `place` text COLLATE utf8mb4_vi_0900_as_cs,
  `max` int DEFAULT NULL,
  `now` int DEFAULT NULL,
  `deleted` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_activity_userId_idx` (`user_id`),
  CONSTRAINT `fk_activity_userId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (5,1,'白沙湖一日游','前往帕米尔高原，白沙湖一日游','1752746661862-IMG_4399_1.jpg','报名中','2025-07-25 00:00:00','喀什',50,1,0);
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apply`
--

DROP TABLE IF EXISTS `apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apply` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `activity_id` int NOT NULL,
  `result` varchar(64) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`),
  KEY `activity_id_idx` (`activity_id`),
  CONSTRAINT `fk_apply_activityId` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `fk_apply_userId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apply`
--

LOCK TABLES `apply` WRITE;
/*!40000 ALTER TABLE `apply` DISABLE KEYS */;
INSERT INTO `apply` VALUES (5,5,5,'申请通过');
/*!40000 ALTER TABLE `apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL,
  `user_id` int NOT NULL,
  `content` text COLLATE utf8mb4_vi_0900_ai_ci,
  `time` varchar(64) COLLATE utf8mb4_vi_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`),
  KEY `fk_comment_postId` (`post_id`),
  CONSTRAINT `fk_comment_postId` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_userId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (13,9,5,'666','Thu Jul 17 18:15:32 CST 2025');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` text COLLATE utf8mb4_vi_0900_as_cs,
  `info` text COLLATE utf8mb4_vi_0900_as_cs,
  `time` varchar(64) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (1,'2025 王梓镔小学期项目','欢迎使用基于SpringBoot的校友管理系统','Thu Jul 17 14:39:31 CST 2025');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(128) COLLATE utf8mb4_vi_0900_ai_ci NOT NULL,
  `content` text COLLATE utf8mb4_vi_0900_ai_ci,
  `time` varchar(64) COLLATE utf8mb4_vi_0900_ai_ci DEFAULT NULL,
  `images` text COLLATE utf8mb4_vi_0900_ai_ci,
  `like_count` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_post_userId_idx` (`user_id`),
  CONSTRAINT `fk_post_userId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (9,1,'22级校友聚会','学校礼堂\n\n8.30 不见不散\n','Thu Jul 17 18:02:41 CST 2025','[\"1752746560274-IMG_20210717_141812.png\"]',2);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(32) COLLATE utf8mb4_vi_0900_ai_ci NOT NULL,
  `password` varchar(32) COLLATE utf8mb4_vi_0900_ai_ci NOT NULL,
  `role` varchar(32) COLLATE utf8mb4_vi_0900_ai_ci NOT NULL,
  `avatar` varchar(64) COLLATE utf8mb4_vi_0900_ai_ci DEFAULT NULL,
  `school` varchar(128) COLLATE utf8mb4_vi_0900_ai_ci DEFAULT NULL,
  `name` varchar(128) COLLATE utf8mb4_vi_0900_ai_ci DEFAULT NULL,
  `number` varchar(32) COLLATE utf8mb4_vi_0900_ai_ci DEFAULT NULL,
  `sex` varchar(32) COLLATE utf8mb4_vi_0900_ai_ci DEFAULT NULL,
  `major` varchar(64) COLLATE utf8mb4_vi_0900_ai_ci DEFAULT NULL,
  `grade` varchar(32) COLLATE utf8mb4_vi_0900_ai_ci DEFAULT NULL,
  `phone` varchar(32) COLLATE utf8mb4_vi_0900_ai_ci DEFAULT NULL,
  `info` varchar(1024) COLLATE utf8mb4_vi_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test','123456','USER','1752747518402-bf1-5.jpg','新疆大学','萨尼铁塔','2022114514','女','软件工程','2022','19235647896','150百星A10'),(2,'wzb','123456','ADMIN','1752742305164-02fff5e647fcf598a40f445b39037d4.jpg','新疆大学','王梓镔','20222501408','男','软件工程','2022','17335331562','轮椅高手百星SMG0818'),(5,'test1','666777','USER','1752747244108-bf1-1.jpg','沙依巴克区信息职业技术学院','摸你穷','1145141919810','男','支援兵','2022','18233459962','百星空爆迫击炮');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-22 12:42:46
