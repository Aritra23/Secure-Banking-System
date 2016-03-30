-- MySQL dump 10.13  Distrib 5.6.22, for osx10.9 (x86_64)
--
-- Host: localhost    Database: ssgroup5
-- ------------------------------------------------------
-- Server version	5.6.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ss_access_management`
--

DROP TABLE IF EXISTS `ss_access_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_access_management` (
  `access_management_id` int(11) NOT NULL,
  `transaction_group` varchar(45) NOT NULL,
  `user_role` varchar(45) NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`access_management_id`),
  KEY `fk_ss_access_management_1_idx` (`transaction_group`),
  KEY `fk_ss_access_management_1_idx1` (`user_role`),
  CONSTRAINT `fk_ss_access_management_1` FOREIGN KEY (`user_role`) REFERENCES `ss_user_role` (`role`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_access_management`
--

LOCK TABLES `ss_access_management` WRITE;
/*!40000 ALTER TABLE `ss_access_management` DISABLE KEYS */;
/*!40000 ALTER TABLE `ss_access_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ss_account`
--

DROP TABLE IF EXISTS `ss_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `account_type` varchar(45) NOT NULL,
  `balance` float NOT NULL,
  `pending_balance` float NOT NULL,
  `tranaction_limit` float DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`account_id`),
  KEY `fk_SSAccount_1_idx` (`user_id`),
  CONSTRAINT `fk_ss_account_1` FOREIGN KEY (`user_id`) REFERENCES `ss_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1112223341 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_account`
--

LOCK TABLES `ss_account` WRITE;
/*!40000 ALTER TABLE `ss_account` DISABLE KEYS */;
INSERT INTO `ss_account` VALUES (1112223333,1234567890,'SAVING',1000.02,0,500,1,'2015-09-27 06:47:48'),(1112223334,1234567890,'CHECKING',1000.54,0,1200,1,'2015-09-27 06:47:48'),(1112223335,1234567892,'CHECKING',1050,4642,100,1,'2015-10-27 01:43:56'),(1112223336,1234567892,'SAVING',1000,0,100,1,'2015-10-27 01:02:16'),(1112223337,1234567893,'CHECKING',1950,1950,500,1,'2015-10-28 23:05:33'),(1112223338,1234567893,'SAVING',2000,0,500,1,'2015-10-27 01:23:40'),(1112223339,1234567896,'savings',1000,0,2000,1,'2015-10-27 06:57:28'),(1112223340,1234567896,'checking',1000,0,2000,1,'2015-10-27 06:57:31');
/*!40000 ALTER TABLE `ss_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ss_fund_transaction`
--

DROP TABLE IF EXISTS `ss_fund_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_fund_transaction` (
  `fund_transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `transaction_type` varchar(45) NOT NULL,
  `source_account` int(11) NOT NULL,
  `destination_account` int(11) NOT NULL,
  `amount` float NOT NULL,
  `authorization_role` varchar(45) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `comment` varchar(45) DEFAULT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`fund_transaction_id`),
  KEY `ssfktrans_idx` (`transaction_type`),
  CONSTRAINT `ss_fk_transf` FOREIGN KEY (`transaction_type`) REFERENCES `ss_transaction_type` (`transaction_type`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_fund_transaction`
--

LOCK TABLES `ss_fund_transaction` WRITE;
/*!40000 ALTER TABLE `ss_fund_transaction` DISABLE KEYS */;
INSERT INTO `ss_fund_transaction` VALUES (2,0,'CUST_CREDIT',1,1234343,12,'INT_REMP','Pending','trf','2015-10-10 03:02:05'),(3,0,'CUST_CREDIT',1,1234343,134,'INT_REMP','Pending','transfer','2015-10-10 03:19:02'),(4,0,'CUST_CREDIT',1,1234343,22,'INT_REMP','Pending','debit comment','2015-10-10 03:22:04'),(5,0,'CUST_DEBIT',1,1234343,11,'INT_REMP','Pending','testd','2015-10-10 03:24:14'),(6,0,'CUST_CREDIT',0,1112223336,123,'INT_REMP','Pending','test trf','2015-10-10 05:10:15'),(7,0,'CUST_DEBIT',1112223336,0,45,'INT_REMP','Pending','test trf','2015-10-10 05:11:39'),(8,0,'CUST_DEBIT',1112223336,0,45,'INT_REMP','Pending','test trf','2015-10-10 05:12:00'),(9,0,'CUST_DEBIT',1112223336,0,12,'INT_REMP','Pending','test trf','2015-10-10 05:28:55'),(10,0,'CUST_DEBIT',1112223336,0,12,'INT_REMP','Pending','trf','2015-10-10 05:30:41'),(11,0,'CUST_DEBIT',1112223336,0,12,'INT_REMP','Pending','trf','2015-10-10 05:30:59'),(12,0,'CUST_TRANSFER',1112223336,1112223335,123,'INT_REMP','Pending','test trf','2015-10-10 06:40:50'),(13,0,'CUST_TRANSFER',1112223336,1112223335,123,'INT_REMP','Pending','trf','2015-10-10 06:48:19'),(14,0,'CUST_DEBIT',1112223336,0,3123,'INT_REMP','Pending','sdfdgd','2015-10-10 07:01:22'),(15,0,'CUST_DEBIT',1112223336,0,222,'INT_REMP','Pending','sfsfs','2015-10-10 07:07:04'),(16,0,'CUST_DEBIT',1112223336,0,222,'INT_REMP','Pending','sfsfs','2015-10-10 07:10:57'),(17,1234567892,'CUST_TRANSFER',1112223336,1112223335,345,'INT_REMP','Pending','trf','2015-10-10 07:14:02'),(18,1234567892,'CUST_CREDIT',0,1112223336,320,'INT_REMP','Pending','simply debit','2015-10-10 07:14:59'),(19,1234567892,'CUST_TRANSFER',1112223336,1112223335,45,'INT_REMP','Pending','test transaction','2015-10-10 07:18:04'),(20,1234567892,'CUST_TRANSFER',1112223336,1112223335,435,'INT_REMP','Pending','sri','2015-10-10 07:47:58'),(21,1234567892,'CUST_DEBIT',1112223336,0,12,'INT_REMP','Pending','trf','2015-10-10 19:08:03'),(22,1234567892,'CUST_CREDIT',0,1112223336,123,'INT_REMP','Pending','trf','2015-10-10 23:32:28'),(23,1234567892,'CUST_DEBIT',1112223336,0,123,'INT_REMP','Pending','trf','2015-10-10 23:33:06'),(24,1234567892,'CUST_TRANSFER',1112223336,1112223335,125,'INT_REMP','Pending','test','2015-10-10 23:33:54'),(25,1234567892,'CUST_DEBIT',1112223336,0,1212,'INT_REMP','Pending','test test','2015-10-11 00:11:06'),(26,1234567892,'CUST_TRANSFER',1112223336,1112223335,1212,'INT_REMP','Pending','transfer to friend','2015-10-11 00:12:15'),(27,1234567892,'CUST_DEBIT',1112223336,0,123,'INT_REMP','Pending','test','2015-10-11 00:16:31'),(28,1234567892,'CUST_TRANSFER',1112223336,1112223335,1234,'INT_REMP','Pending','test fund transfer','2015-10-11 00:19:22'),(29,1234567895,'CUST_DEBIT',1112223338,0,37,'INT_REMP','Approved','Approved by aritra','2015-10-24 00:25:05'),(30,1234567895,'CUST_DEBIT',1112223338,0,50,'INT_REMP','Pending','','2015-10-25 23:22:59'),(31,1234567895,'CUST_TRANSFER',1112223338,1112223335,50,'INT_REMP','Approved','Approved by Aritra','2015-10-26 00:27:14'),(32,1234567892,'MER_REQUEST_FUNDS',1112223337,1112223335,50,'EXT_CUSTOMER','Approved','Approved by Katie','2015-10-27 01:41:51'),(33,1234567893,'CUST_TRANSFER',1112223337,1112223337,50,'INT_REMP','Pending','','2015-10-28 23:05:33');
/*!40000 ALTER TABLE `ss_fund_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ss_otp`
--

DROP TABLE IF EXISTS `ss_otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_otp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(10) DEFAULT NULL,
  `pin` int(11) DEFAULT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `email` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_otp`
--

LOCK TABLES `ss_otp` WRITE;
/*!40000 ALTER TABLE `ss_otp` DISABLE KEYS */;
/*!40000 ALTER TABLE `ss_otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ss_packets`
--
--
-- Table structure for table `ss_pii`
--

DROP TABLE IF EXISTS `ss_pii`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_pii` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `ssn` varchar(9) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_pii`
--

LOCK TABLES `ss_pii` WRITE;
/*!40000 ALTER TABLE `ss_pii` DISABLE KEYS */;
/*!40000 ALTER TABLE `ss_pii` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ss_profile_transaction`
--

DROP TABLE IF EXISTS `ss_profile_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_profile_transaction` (
  `profile_transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_type` varchar(45) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone_number` varchar(10) NOT NULL,
  `ssn` varchar(9) NOT NULL,
  `state` varchar(45) NOT NULL,
  `authorization_role` varchar(45) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `comment` varchar(45) DEFAULT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `savings_enabled` tinyint(4) NOT NULL DEFAULT '0',
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `zip_code` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  PRIMARY KEY (`profile_transaction_id`),
  KEY `ss_fk_ptrans_idx` (`transaction_type`),
  CONSTRAINT `ss_fk_ptrans` FOREIGN KEY (`transaction_type`) REFERENCES `ss_transaction_type` (`transaction_type`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_profile_transaction`
--

LOCK TABLES `ss_profile_transaction` WRITE;
/*!40000 ALTER TABLE `ss_profile_transaction` DISABLE KEYS */;
INSERT INTO `ss_profile_transaction` VALUES (1,'CUST_Signup',NULL,'asd','ad','ada','ada','ada','1231231234','1231231234','Pending','INT_MEMP','Pending',NULL,'2015-10-04 04:27:14',0,'ada','adsas','',''),(3,'CUST_Signup',NULL,'mflmk','njnjm','mk m',' n kjn ','klmlkn','1231231234','1231231234','CA','INT_MEMP','Pending',NULL,'2015-10-03 22:05:43',0,'saf','dsf','',''),(4,'MER_Signup',NULL,'njknn','jknnn','bjknkn','bjnbjk','kjnjk','1231231234','1231231234','AZ','INT_MEMP','Pending',NULL,'2015-10-03 22:05:43',0,'asf','adasdf','',''),(5,'MER_Signup',NULL,'adjan','adnjak','abdjka','dsfjdk','ddbjk','1231231234','1231231234','WA','INT_MEMP','Approved',NULL,'2015-10-03 22:05:43',0,'asdf','asdf','',''),(6,'CUST_Update',1234567892,'kannan','12345678','asda','dsfs','dassa','1321273123','1231232131','AZ','INT_MEMP','Pending',NULL,'2015-10-04 04:28:29',0,'Kannan','Hari','',''),(7,'CUST_Signup',0,'ktanikella','abcd1234','abcd','Tempe','kruthika.tanikella@asu.edu','1234567890','1234567890','AZ','INT_MEMP','approved','Approved by megha','2015-10-24 00:22:12',1,'Kruthika','Tanikella','85281','USA'),(8,'CUST_Signup',0,'abcabcabc','abcd1234','238 W eru st','Tempe','varshaiyengar177@gmail.com','1234567890','1234563476','AZ','INT_MEMP','approved','Approved by pablo','2015-10-27 06:57:28',1,'abcabcabc','xyz','85281','USA');
/*!40000 ALTER TABLE `ss_profile_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ss_transaction_management`
--

DROP TABLE IF EXISTS `ss_transaction_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_transaction_management` (
  `transaction_id` int(11) NOT NULL,
  `transaction_group` varchar(45) NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_transaction_management`
--

LOCK TABLES `ss_transaction_management` WRITE;
/*!40000 ALTER TABLE `ss_transaction_management` DISABLE KEYS */;
/*!40000 ALTER TABLE `ss_transaction_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ss_transaction_type`
--

DROP TABLE IF EXISTS `ss_transaction_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_transaction_type` (
  `transaction_type` varchar(45) NOT NULL,
  `authorization_role` varchar(45) DEFAULT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaction_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_transaction_type`
--

LOCK TABLES `ss_transaction_type` WRITE;
/*!40000 ALTER TABLE `ss_transaction_type` DISABLE KEYS */;
INSERT INTO `ss_transaction_type` VALUES ('CUST_CREDIT','INT_REMP','2015-10-10 02:59:31'),('CUST_DEBIT','INT_REMP','2015-10-10 23:31:40'),('CUST_PROFILE_DELETE','INT_MEMP','2015-10-29 15:08:01'),('CUST_Signup','INT_MEMP','2015-10-02 05:45:42'),('CUST_TRANSFER','INT_REMP','2015-10-11 00:18:12'),('CUST_Update','INT_MEMP','2015-10-04 03:50:13'),('Internal_Signup','INT_AEMP','2015-10-02 05:45:42'),('INT_Update','INT_AEMP','2015-10-23 22:14:55'),('MER_REQUEST_FUNDS','INT_REMP','2015-10-25 22:40:59'),('MER_PROFILE_DELETE','INT_MEMP','2015-10-29 15:08:15'),('MER_Signup','INT_MEMP','2015-10-02 05:45:42'),('MER_Update','INT_MEMP','2015-10-04 03:50:13');
/*!40000 ALTER TABLE `ss_transaction_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ss_user`
--

DROP TABLE IF EXISTS `ss_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `ssn` varchar(9) NOT NULL,
  `phone_number` varchar(10) NOT NULL,
  `address` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `zip_code` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `enabled` int(11) DEFAULT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `state` varchar(45) NOT NULL,
  `Secret` varchar(65) DEFAULT NULL,
  `forgot_password_enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `userid_UNIQUE` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1234567898 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_user`
--

LOCK TABLES `ss_user` WRITE;
/*!40000 ALTER TABLE `ss_user` DISABLE KEYS */;
INSERT INTO `ss_user` VALUES (1234567890,'Ashwin','Vasani','938482747','4808485834','920 S Orange St 102','Tempe','USA','85281','akvasani@asu.edu','12345678','akvasani',1,'2015-10-29 10:29:08','','4ddc82f907dbf7010238d52d8e33a89c9ae2abd851222d32e7572f522716d2b2',0),(1234567891,'Shivam','Prakash','1234567890','4804804804','123 E Noble','Tempe','USA','85281','shivam@abc.com','12345678','shivam',1,'2015-10-26 02:04:58','AZ',NULL,0),(1234567892,'Kruthika','Tanikella','2836428736','4578887774','7234 W St','Tempe','USA','85281','tkruthika@gmail.com','12345678','tkruthika',1,'2015-10-27 01:27:22','AZ','2a6367efad779021be76d87f25e01eba0c305069f7d4f6a8a0ae05498dbebf75',0),(1234567893,'Katie','T','8233374747','9987654321','3874 S shjd St','Tempe','USA','85281','kruthika.tanikella@asu.edu','abcd1234','katie',1,'2015-10-29 13:58:50','AZ','88c9bbf0fb7b801ea4a4a87e8c523a8fd072291ac66530640860545226f5fc5b',0),(1234567894,'Owen','P','7632387737','7346728734','7465 W ahd','Tempe','USA','85281','owen@abc.com','abcd1234','owen',1,'2015-10-29 10:41:08','AZ','24bf59a8a65d1396506c8941e94e18dabdfb154644040158b8b0e64bc3cf8a51',0),(1234567895,'Pablo','F','3475638475','3745683745','374 W wer','Tempe','USA','85281','kru@abc.com','abcd1234','pablo',1,'2015-10-29 11:05:33','AZ','0f7bbe6ebf2c8681c9e203ef366b4c4bf05a1b08346818ec8be05ad6b7e29998',0),(1234567896,'abcabcabc','xyz','1234563476','1234567890','238 W eru st','Tempe','US','85281','varshaiyengar177@gmail.com','abcd1234','abcabcabc',1,'2015-10-27 06:57:28','AZ',NULL,0);
/*!40000 ALTER TABLE `ss_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ss_user_management`
--

DROP TABLE IF EXISTS `ss_user_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_user_management` (
  `user_id` int(11) NOT NULL,
  `approver_id` int(11) NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `ss_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_user_management`
--

LOCK TABLES `ss_user_management` WRITE;
/*!40000 ALTER TABLE `ss_user_management` DISABLE KEYS */;
INSERT INTO `ss_user_management` VALUES (1234567893,1234567894,'2015-10-27 01:37:11'),(1234567892,1234567894,'2015-10-27 01:37:16'),(1234567896,1234567894,'2015-10-27 06:57:28');
/*!40000 ALTER TABLE `ss_user_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ss_user_role`
--

DROP TABLE IF EXISTS `ss_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_user_role` (
  `user_id` int(11) NOT NULL,
  `role` varchar(45) NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role`,`user_id`),
  KEY `fk_UserRole_1_idx` (`user_id`),
  CONSTRAINT `fk_UserRole_1` FOREIGN KEY (`user_id`) REFERENCES `ss_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ss_user_role`
--

LOCK TABLES `ss_user_role` WRITE;
/*!40000 ALTER TABLE `ss_user_role` DISABLE KEYS */;
INSERT INTO `ss_user_role` VALUES (1234567893,'EXT_CUSTOMER','2015-10-27 01:30:03'),(1234567896,'EXT_CUSTOMER','2015-10-27 06:57:28'),(1234567892,'EXT_MERCHANT','2015-10-27 00:34:56'),(1234567890,'INT_AEMP','2015-09-27 01:48:39'),(1234567891,'INT_AEMP','2015-10-27 00:34:43'),(1234567895,'INT_MEMP','2015-10-27 06:17:39'),(1234567894,'INT_REMP','2015-10-27 01:36:11');
/*!40000 ALTER TABLE `ss_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-29  7:01:57
