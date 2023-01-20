-- MySQL dump 10.13  Distrib 8.0.31, for Linux (x86_64)
--
-- Host: localhost    Database: inspectordb
-- ------------------------------------------------------
-- Server version	8.0.31-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `agreement`
--

DROP TABLE IF EXISTS `agreement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agreement` (
  `id` int NOT NULL AUTO_INCREMENT,
  `authorisation_number` varchar(255) DEFAULT NULL,
  `issued_date` datetime NOT NULL COMMENT 'agreement issued date',
  `status` int NOT NULL COMMENT 'agreement state 0 if suspended 1 if active',
  `suspension_date` datetime DEFAULT NULL COMMENT 'agreement suspension date',
  `training_center_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5ykil2htkgsqqm9bv7mf9w52y` (`training_center_id`),
  CONSTRAINT `FK5ykil2htkgsqqm9bv7mf9w52y` FOREIGN KEY (`training_center_id`) REFERENCES `training_center` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agreement`
--

LOCK TABLES `agreement` WRITE;
/*!40000 ALTER TABLE `agreement` DISABLE KEYS */;
INSERT INTO `agreement` VALUES (1,'*155#','2021-06-17 00:00:00',1,NULL,4);
/*!40000 ALTER TABLE `agreement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agreement_tracking`
--

DROP TABLE IF EXISTS `agreement_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agreement_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `agreement_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3on4syr88n83mf1guk6tohnl8` (`agreement_id`),
  KEY `FKbtuj9hl01e5vvx41ixpc4h6iv` (`user_id`),
  CONSTRAINT `FK3on4syr88n83mf1guk6tohnl8` FOREIGN KEY (`agreement_id`) REFERENCES `agreement` (`id`),
  CONSTRAINT `FKbtuj9hl01e5vvx41ixpc4h6iv` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agreement_tracking`
--

LOCK TABLES `agreement_tracking` WRITE;
/*!40000 ALTER TABLE `agreement_tracking` DISABLE KEYS */;
INSERT INTO `agreement_tracking` VALUES (1,'REGISTERED THE AGREEMENT','2021-06-17 01:01:03.198000',1,5);
/*!40000 ALTER TABLE `agreement_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `applicant`
--

DROP TABLE IF EXISTS `applicant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applicant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `computerization_date` datetime DEFAULT NULL COMMENT 'computerization date of the applicant',
  `pv_number` int DEFAULT NULL COMMENT 'pv number of the candidate session ',
  `exam_result` varchar(100) DEFAULT NULL COMMENT 'applicant exam result, REGISTERED, PASSED THEORY, FAILED THEORY, FAILED PRACTICALS, PASSED PRACTICALS ',
  `entrance_eligible_center_id` int DEFAULT NULL,
  `person_id` int DEFAULT NULL,
  `speciality_id` bigint DEFAULT NULL,
  `student_id` int DEFAULT NULL,
  `final_result` int DEFAULT NULL COMMENT 'la moyenne obtenu par le candidat apres deliberation ',
  `diplome` varchar(100) DEFAULT NULL COMMENT 'BACC/GCE OR CAPEC ',
  PRIMARY KEY (`id`),
  KEY `FKrl7f4ovr6ylwciryd5ehk30pi` (`entrance_eligible_center_id`),
  KEY `FKnk0vyb7hj2qbqaeb6x0jjw68p` (`person_id`),
  KEY `FKf5sglbnqgigx6ya7yjgjd1njg` (`speciality_id`),
  KEY `FKtgcv2q62weo2fqt99v1ukwmvt` (`student_id`),
  CONSTRAINT `FKf5sglbnqgigx6ya7yjgjd1njg` FOREIGN KEY (`speciality_id`) REFERENCES `speciality` (`id`),
  CONSTRAINT `FKnk0vyb7hj2qbqaeb6x0jjw68p` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `FKrl7f4ovr6ylwciryd5ehk30pi` FOREIGN KEY (`entrance_eligible_center_id`) REFERENCES `entrance_eligible_center` (`id`),
  CONSTRAINT `FKtgcv2q62weo2fqt99v1ukwmvt` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicant`
--

LOCK TABLES `applicant` WRITE;
/*!40000 ALTER TABLE `applicant` DISABLE KEYS */;
INSERT INTO `applicant` VALUES (1,'2021-04-01 00:00:00',1,'PASSED PRACTICALS',1,6,3,19,0,'BACC'),(2,'2021-04-01 00:00:00',1,'PASSED PRACTICALS',2,25,3,21,0,'BACC'),(3,'2021-04-01 00:00:00',1,'FAILED PRACTICALS',3,12,3,23,0,'BACC'),(4,'2021-04-01 00:00:00',1,'PASSED PRACTICALS',3,26,3,24,15,'BACC'),(5,'2021-04-01 00:00:00',1,'PASSED PRACTICALS',4,27,3,25,0,'BACC'),(6,'2021-04-01 00:00:00',2,'PASSED PRACTICALS',4,16,3,26,0,'BACC');
/*!40000 ALTER TABLE `applicant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `applicant_tracking`
--

DROP TABLE IF EXISTS `applicant_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applicant_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `applicant_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7o0vniodsbg51omjji1229irq` (`applicant_id`),
  KEY `FKrpoyukjd7uab386e6bjihav3v` (`user_id`),
  CONSTRAINT `FK7o0vniodsbg51omjji1229irq` FOREIGN KEY (`applicant_id`) REFERENCES `applicant` (`id`),
  CONSTRAINT `FKrpoyukjd7uab386e6bjihav3v` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicant_tracking`
--

LOCK TABLES `applicant_tracking` WRITE;
/*!40000 ALTER TABLE `applicant_tracking` DISABLE KEYS */;
INSERT INTO `applicant_tracking` VALUES (1,'REGISTERED THE APPLICANT','2021-04-01 08:42:53.857000',1,2),(2,'MARKED AS PASSED THEORY','2021-04-01 08:43:01.006000',1,2),(3,'MARKED AS PASSED PRACTICAL','2021-04-01 08:43:09.512000',1,2),(4,'REGISTERED THE APPLICANT','2021-04-01 10:49:23.852000',2,2),(5,'MARKED AS PASSED THEORY','2021-04-01 10:49:27.740000',2,2),(6,'MARKED AS PASSED PRACTICAL','2021-04-01 10:49:32.900000',2,2),(7,'REGISTERED THE APPLICANT','2021-04-01 11:07:34.851000',3,2),(8,'REGISTERED THE APPLICANT','2021-04-01 11:08:13.009000',4,2),(9,'MARKED AS PASSED THEORY','2021-04-01 11:08:21.001000',3,2),(10,'MARKED AS PASSED THEORY','2021-04-01 11:08:23.672000',4,2),(11,'MARKED AS PASSED PRACTICAL','2021-04-01 11:08:29.191000',3,2),(12,'MARKED AS PASSED PRACTICAL','2021-04-01 11:08:43.407000',4,2),(13,'REGISTERED THE APPLICANT','2021-04-01 11:25:29.852000',5,2),(14,'REGISTERED THE APPLICANT','2021-04-01 11:26:20.009000',6,2),(15,'MARKED AS PASSED THEORY','2021-04-01 11:26:49.503000',5,2),(16,'MARKED AS PASSED THEORY','2021-04-01 11:26:53.411000',6,2),(17,'MARKED AS PASSED PRACTICAL','2021-04-01 11:26:58.701000',5,2),(18,'MARKED AS PASSED PRACTICAL','2021-04-01 11:27:01.310000',6,2),(19,' Not Implemented_mark-as-pass-theory','2021-07-13 08:18:22.545000',3,5),(20,'MARKED AS FAILED PRACTICAL','2021-07-13 08:26:32.247000',3,5);
/*!40000 ALTER TABLE `applicant_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application` (
  `id` int NOT NULL AUTO_INCREMENT,
  `computerization_date` datetime NOT NULL COMMENT 'application computerization date',
  `form_serial_number` varchar(255) NOT NULL COMMENT 'form serial number',
  `issue_date` datetime DEFAULT NULL COMMENT 'application issued date ',
  `number` int DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL COMMENT 'application photo ',
  `serial_number` varchar(255) NOT NULL COMMENT 'application serial number',
  `signature` varchar(255) DEFAULT NULL COMMENT 'application signature ',
  `successfuly_rejected_date` datetime DEFAULT NULL COMMENT 'succesfully printed or rejected date ',
  `application_status_id` int DEFAULT NULL,
  `archive_id` int DEFAULT NULL,
  `authority_id` int DEFAULT NULL,
  `certificate_id` int DEFAULT NULL,
  `in_slip_id` int DEFAULT NULL,
  `office_id` int DEFAULT NULL,
  `out_slip_id` int DEFAULT NULL,
  `process_type_id` int DEFAULT NULL,
  `speciality_id` bigint DEFAULT NULL,
  `transcript_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7knml98qacltosq8nw4uvn3n0` (`application_status_id`),
  KEY `FKf13xsm4gadl933evju8lbyp35` (`archive_id`),
  KEY `FKetdbuskgrosu4y63o648evgpt` (`authority_id`),
  KEY `FKdeyhvsbvia4rwj77rrbtfp8jj` (`certificate_id`),
  KEY `FK22il109j95syqj6yndeik9j3y` (`in_slip_id`),
  KEY `FKirvk45p8d0yeqskcf8c8tuucd` (`office_id`),
  KEY `FK3cl0364lwh5lu5wvg7xk6jdmt` (`out_slip_id`),
  KEY `FKdoxjvyqfwtjgljcyqu17n4dnd` (`process_type_id`),
  KEY `FKjt3gmmx1vdnouub1vndq5e35n` (`speciality_id`),
  KEY `FK369n57i5mk0bro9bv843dw58o` (`transcript_id`),
  KEY `FKldca8xj6lqb3rsqawrowmkqbg` (`user_id`),
  CONSTRAINT `FK22il109j95syqj6yndeik9j3y` FOREIGN KEY (`in_slip_id`) REFERENCES `in_slip` (`id`),
  CONSTRAINT `FK369n57i5mk0bro9bv843dw58o` FOREIGN KEY (`transcript_id`) REFERENCES `application_transcript` (`id`),
  CONSTRAINT `FK3cl0364lwh5lu5wvg7xk6jdmt` FOREIGN KEY (`out_slip_id`) REFERENCES `out_slip` (`id`),
  CONSTRAINT `FK7knml98qacltosq8nw4uvn3n0` FOREIGN KEY (`application_status_id`) REFERENCES `application_status` (`id`),
  CONSTRAINT `FKdeyhvsbvia4rwj77rrbtfp8jj` FOREIGN KEY (`certificate_id`) REFERENCES `certificate` (`id`),
  CONSTRAINT `FKdoxjvyqfwtjgljcyqu17n4dnd` FOREIGN KEY (`process_type_id`) REFERENCES `process_type` (`id`),
  CONSTRAINT `FKetdbuskgrosu4y63o648evgpt` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FKf13xsm4gadl933evju8lbyp35` FOREIGN KEY (`archive_id`) REFERENCES `archive` (`id`),
  CONSTRAINT `FKirvk45p8d0yeqskcf8c8tuucd` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`),
  CONSTRAINT `FKjt3gmmx1vdnouub1vndq5e35n` FOREIGN KEY (`speciality_id`) REFERENCES `speciality` (`id`),
  CONSTRAINT `FKldca8xj6lqb3rsqawrowmkqbg` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (3,'2021-02-24 06:50:55','9586',NULL,1,'3.jpg','AD-1-0221-1','3.jpg','2021-02-24 06:51:36',6,NULL,1,NULL,1,1,NULL,1,1,5,2),(4,'2021-02-24 07:33:51','9236',NULL,2,'4.jpg','AD-1-0221-2','4.jpg','2021-02-24 08:55:55',6,NULL,1,NULL,1,1,NULL,4,1,5,2),(5,'2021-02-24 09:00:58','8596',NULL,3,'5.jpg','AD-1-0221-3','5.jpg','2021-02-24 09:27:08',6,NULL,1,1,1,1,NULL,2,NULL,NULL,2),(6,'2021-02-24 09:29:38','568955',NULL,4,'6.jpg','AD-1-0221-4','6.jpg','2021-02-24 09:32:48',6,NULL,1,1,1,1,NULL,5,1,NULL,2),(14,'2021-02-24 10:06:00','2536998',NULL,5,'14.jpg','AD-1-0221-5','14.jpg','2021-02-24 10:06:54',6,NULL,1,1,1,1,NULL,3,NULL,NULL,2),(15,'2021-02-25 01:32:55','3569258',NULL,6,'15.jpg','AD-1-0221-6','15.jpg','2021-02-25 01:33:39',6,NULL,1,1,1,1,NULL,6,1,NULL,2),(16,'2021-02-26 08:06:26','9999',NULL,1,'16.jpg','AD-125-0221-1','16.jpg',NULL,4,NULL,1,NULL,2,1,NULL,4,1,5,2),(17,'2021-02-26 08:06:52','9999',NULL,1,'17.jpg','AD-125-0221-1','17.jpg',NULL,1,NULL,1,NULL,2,1,NULL,4,1,5,2),(18,'2021-02-26 08:12:14','8596',NULL,2,'18.jpg','AD-125-0221-2','18.jpg',NULL,1,NULL,1,NULL,2,1,NULL,1,1,6,2),(19,'2021-02-26 08:12:58','5896',NULL,3,'19.jpg','AD-125-0221-3','19.jpg',NULL,1,NULL,1,2,2,1,NULL,2,NULL,NULL,2);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_file`
--

DROP TABLE IF EXISTS `application_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_file` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_deleted` int DEFAULT NULL COMMENT '1 if the file is deletable and 0 if not',
  `name` varchar(255) DEFAULT NULL COMMENT 'file name ',
  `application_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfi9qq0gbuv9xire1wg0w7bbep` (`application_id`),
  CONSTRAINT `FKfi9qq0gbuv9xire1wg0w7bbep` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_file`
--

LOCK TABLES `application_file` WRITE;
/*!40000 ALTER TABLE `application_file` DISABLE KEYS */;
INSERT INTO `application_file` VALUES (5,0,'3.pdf',3),(6,0,'4.pdf',4),(7,0,'5.pdf',5),(8,0,'6.pdf',6),(10,0,'14.pdf',14),(11,0,'15.pdf',15),(12,0,'16.pdf',16),(13,0,'17.pdf',17),(14,0,'18.pdf',18),(15,0,'19.pdf',19);
/*!40000 ALTER TABLE `application_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_status`
--

DROP TABLE IF EXISTS `application_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL COMMENT 'application status description',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_status`
--

LOCK TABLES `application_status` WRITE;
/*!40000 ALTER TABLE `application_status` DISABLE KEYS */;
INSERT INTO `application_status` VALUES (1,'APPLICATION REGISTERED'),(2,'APPLICATION REJECTED'),(3,'APPLICATION CANCELLED'),(4,'APPLICATION CONFIRMED'),(5,'CAPACITY PRINTED'),(6,'SUCCESSFULLY PRINTED'),(7,'CONFIRMED REJECTED'),(8,'UNSUCCESSFULLY PRINTED'),(9,'DELIVERED'),(10,'DELIVERED WITH REJECT'),(11,'AUTHORIZED REPRINT');
/*!40000 ALTER TABLE `application_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_tracking`
--

DROP TABLE IF EXISTS `application_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `application_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK41hu3qce5e1cmtvy35j3uvv3k` (`application_id`),
  KEY `FK9w9br4x0sv506b9o5dboo7x4` (`user_id`),
  CONSTRAINT `FK41hu3qce5e1cmtvy35j3uvv3k` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`),
  CONSTRAINT `FK9w9br4x0sv506b9o5dboo7x4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_tracking`
--

LOCK TABLES `application_tracking` WRITE;
/*!40000 ALTER TABLE `application_tracking` DISABLE KEYS */;
INSERT INTO `application_tracking` VALUES (12,'REGISTERED THE APPLICATION','2021-02-24 06:50:55.870000',3,2),(13,'CONFIRMED THE APPLICATION','2021-02-24 06:51:08.211000',3,2),(14,'APPLICATION SUCCESSFULLY PRINTED','2021-02-24 06:51:36.445000',3,2),(15,'REGISTERED THE APPLICATION','2021-02-24 07:33:50.845000',4,2),(16,'CONFIRMED THE APPLICATION','2021-02-24 07:49:01.047000',4,2),(17,'APPLICATION SUCCESSFULLY PRINTED','2021-02-24 08:13:36.817000',4,2),(18,'APPLICATION SUCCESSFULLY PRINTED','2021-02-24 08:39:54.674000',4,2),(19,'APPLICATION SUCCESSFULLY PRINTED','2021-02-24 08:55:55.110000',4,2),(20,'REGISTERED THE APPLICATION','2021-02-24 09:00:58.040000',5,2),(21,'CONFIRMED THE APPLICATION','2021-02-24 09:13:18.673000',5,2),(22,'APPLICATION SUCCESSFULLY PRINTED','2021-02-24 09:27:08.311000',5,2),(23,'REGISTERED THE APPLICATION','2021-02-24 09:29:38.426000',6,2),(24,'CONFIRMED THE APPLICATION','2021-02-24 09:32:18.891000',6,2),(25,'APPLICATION SUCCESSFULLY PRINTED','2021-02-24 09:32:47.747000',6,2),(28,'REGISTERED THE APPLICATION','2021-02-24 10:06:01.508000',14,2),(29,'CONFIRMED THE APPLICATION','2021-02-24 10:06:29.753000',14,2),(30,'APPLICATION SUCCESSFULLY PRINTED','2021-02-24 10:06:53.677000',14,2),(31,'REGISTERED THE APPLICATION','2021-02-25 01:32:56.295000',15,2),(32,'CONFIRMED THE APPLICATION','2021-02-25 01:33:09.317000',15,2),(33,'APPLICATION SUCCESSFULLY PRINTED','2021-02-25 01:33:39.458000',15,2),(34,'REGISTERED THE APPLICATION','2021-02-26 08:06:26.088000',16,2),(35,'REGISTERED THE APPLICATION','2021-02-26 08:06:51.792000',17,2),(36,'REGISTERED THE APPLICATION','2021-02-26 08:12:13.895000',18,2),(37,'REGISTERED THE APPLICATION','2021-02-26 08:12:58.211000',19,2),(38,'CANCELLED PROCESSING ON THE APPLICATION','2021-07-14 03:34:56.779000',17,5),(39,'RESETTED THE APPLICATION','2021-07-14 03:35:53.861000',17,5),(40,'CONFIRMED THE APPLICATION','2021-07-14 03:50:35.188000',16,5);
/*!40000 ALTER TABLE `application_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_transcript`
--

DROP TABLE IF EXISTS `application_transcript`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_transcript` (
  `id` int NOT NULL AUTO_INCREMENT,
  `issue_date` datetime(6) DEFAULT NULL,
  `matricule` varchar(255) NOT NULL COMMENT 'matricule',
  `student_session_id` int DEFAULT NULL,
  `is_printed` int DEFAULT NULL COMMENT '1 si imprimer et 0 si non',
  `on_process` int DEFAULT NULL COMMENT 'à valeur > 0 signifie la presence de un processus qui est appliaction id sur la carte professionnel et à 0 si non ',
  PRIMARY KEY (`id`),
  KEY `FK6o3tnuy8qn6w2xhajaxow2i6q` (`student_session_id`),
  CONSTRAINT `FK6o3tnuy8qn6w2xhajaxow2i6q` FOREIGN KEY (`student_session_id`) REFERENCES `student_session` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_transcript`
--

LOCK TABLES `application_transcript` WRITE;
/*!40000 ALTER TABLE `application_transcript` DISABLE KEYS */;
INSERT INTO `application_transcript` VALUES (5,NULL,'TM00005',4,1,17),(6,NULL,'',5,0,18);
/*!40000 ALTER TABLE `application_transcript` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `archive`
--

DROP TABLE IF EXISTS `archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive` (
  `id` int NOT NULL AUTO_INCREMENT,
  `archive_number` varchar(255) DEFAULT NULL,
  `exam_date` datetime(6) DEFAULT NULL,
  `exam_place` varchar(255) DEFAULT NULL,
  `expiry_date` datetime(6) DEFAULT NULL,
  `issued_date` datetime(6) DEFAULT NULL,
  `issued_place` varchar(255) DEFAULT NULL,
  `pv_number` int DEFAULT NULL,
  `registration_date` datetime(6) DEFAULT NULL,
  `status` varchar(11) DEFAULT NULL,
  `issued_country_id` int DEFAULT NULL,
  `person_id` int DEFAULT NULL,
  `authority` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `final_average` float DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `service` varchar(255) DEFAULT NULL,
  `authority_id` int DEFAULT NULL,
  `country_id` int DEFAULT NULL,
  `on_process` int DEFAULT NULL COMMENT 'set with application id if capacity is pending processs and 0 if not',
  `speciality_id` bigint DEFAULT NULL,
  `photo_and_signature` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtn79651dqhwf0a7a0e55qdemm` (`issued_country_id`),
  KEY `FKc25pm5d3ikpoph5jr6vt7sju5` (`person_id`),
  KEY `FKlavny1kamobawnidvux7akfq1` (`authority_id`),
  KEY `FKhci7cxrbx0t80xodod7680a6b` (`country_id`),
  KEY `FKqw2mtv7cpib43n3s41jhldqcj` (`speciality_id`),
  CONSTRAINT `FKc25pm5d3ikpoph5jr6vt7sju5` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `FKhci7cxrbx0t80xodod7680a6b` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FKlavny1kamobawnidvux7akfq1` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FKqw2mtv7cpib43n3s41jhldqcj` FOREIGN KEY (`speciality_id`) REFERENCES `speciality` (`id`),
  CONSTRAINT `FKtn79651dqhwf0a7a0e55qdemm` FOREIGN KEY (`issued_country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archive`
--

LOCK TABLES `archive` WRITE;
/*!40000 ALTER TABLE `archive` DISABLE KEYS */;
INSERT INTO `archive` VALUES (3,'256','2021-02-08 00:00:00.000000','douala',NULL,'2021-02-16 00:00:00.000000','douala',1,'2021-02-26 02:57:10.558000','REGISTERED',NULL,5,NULL,NULL,16,'TB','MINFOPRA',1,31,0,1,NULL),(6,'CE456','2021-03-02 00:00:00.000000','yaounde',NULL,'2021-02-18 00:00:00.000000','yaounde',2,'2021-02-26 04:45:44.713000','VALIDATED',NULL,12,NULL,NULL,18,'TB','MINFOPRA',1,31,0,1,NULL),(7,'15','2021-06-09 00:00:00.000000','LIMBE',NULL,'2021-06-09 00:00:00.000000','LIMBE',1,'2021-06-09 03:33:12.044000','REGISTERED',NULL,34,NULL,NULL,15,'B','MINFOPRA',1,31,0,1,NULL),(8,'2','2021-06-09 00:00:00.000000','LIMBE',NULL,'2021-06-09 00:00:00.000000','LIMBE',2,'2021-06-09 07:06:52.390000','REGISTERED',NULL,35,NULL,NULL,15,'B','MINFOPRA',1,31,0,1,NULL),(9,'3','2021-06-09 00:00:00.000000','LIMBE',NULL,'2021-06-09 00:00:00.000000','LIMBE',3,'2021-06-09 07:11:00.686000','REGISTERED',NULL,36,NULL,NULL,15,'15','MINFOPRA',1,31,0,1,NULL);
/*!40000 ALTER TABLE `archive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `archive_category`
--

DROP TABLE IF EXISTS `archive_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `archive_id` int DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfmu3plwp2uq0slmsmd2nu2ya1` (`archive_id`),
  KEY `FKfg3vynmnxnynkxmwa8j9ej9hy` (`category_id`),
  CONSTRAINT `FKfg3vynmnxnynkxmwa8j9ej9hy` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKfmu3plwp2uq0slmsmd2nu2ya1` FOREIGN KEY (`archive_id`) REFERENCES `archive` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archive_category`
--

LOCK TABLES `archive_category` WRITE;
/*!40000 ALTER TABLE `archive_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `archive_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `archive_file`
--

DROP TABLE IF EXISTS `archive_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive_file` (
  `id` int NOT NULL AUTO_INCREMENT,
  `deleted` int DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `archive_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp60ys9jk4a7g1jps2jk6pykcq` (`archive_id`),
  CONSTRAINT `FKp60ys9jk4a7g1jps2jk6pykcq` FOREIGN KEY (`archive_id`) REFERENCES `archive` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archive_file`
--

LOCK TABLES `archive_file` WRITE;
/*!40000 ALTER TABLE `archive_file` DISABLE KEYS */;
INSERT INTO `archive_file` VALUES (3,0,'3.pdf',3),(6,0,'6.pdf',6),(7,0,'9.pdf',9);
/*!40000 ALTER TABLE `archive_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `archive_file_tracking`
--

DROP TABLE IF EXISTS `archive_file_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive_file_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `archive_file_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbovduongbdo2qtgyd5gefm3t5` (`archive_file_id`),
  KEY `FKnccw6xigvd0pujq9qrmbmdnwr` (`user_id`),
  CONSTRAINT `FKbovduongbdo2qtgyd5gefm3t5` FOREIGN KEY (`archive_file_id`) REFERENCES `archive_file` (`id`),
  CONSTRAINT `FKnccw6xigvd0pujq9qrmbmdnwr` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archive_file_tracking`
--

LOCK TABLES `archive_file_tracking` WRITE;
/*!40000 ALTER TABLE `archive_file_tracking` DISABLE KEYS */;
/*!40000 ALTER TABLE `archive_file_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `archive_tracking`
--

DROP TABLE IF EXISTS `archive_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `archive_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7b50asj0swfaxfatc3a5afsiv` (`archive_id`),
  KEY `FKsp9sp6nj7xveu6n2cis02eoa9` (`user_id`),
  CONSTRAINT `FK7b50asj0swfaxfatc3a5afsiv` FOREIGN KEY (`archive_id`) REFERENCES `archive` (`id`),
  CONSTRAINT `FKsp9sp6nj7xveu6n2cis02eoa9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archive_tracking`
--

LOCK TABLES `archive_tracking` WRITE;
/*!40000 ALTER TABLE `archive_tracking` DISABLE KEYS */;
INSERT INTO `archive_tracking` VALUES (18,'REGISTERED THE ARCHIVE','2021-02-26 02:57:10.566000',3,2),(19,'ARCHIVE CREATED BY USER','2021-02-26 02:57:10.619000',3,2),(24,'ARCHIVE VALIDATED BY USER','2021-02-26 03:58:47.270000',3,2),(25,'ARCHIVE WAS INVALIDATED BY USER','2021-02-26 03:59:04.098000',3,2),(26,'REGISTERED THE ARCHIVE','2021-02-26 04:45:44.724000',6,2),(27,'ARCHIVE CREATED BY USER','2021-02-26 04:45:44.729000',6,2),(28,'ARCHIVE VALIDATED BY USER','2021-06-09 03:23:32.337000',6,5),(29,'REGISTERED THE ARCHIVE','2021-06-09 03:33:12.135000',7,5),(30,'ARCHIVE CREATED BY USER','2021-06-09 03:33:12.319000',7,5),(31,'REGISTERED THE ARCHIVE','2021-06-09 07:06:52.418000',8,5),(32,'ARCHIVE CREATED BY USER','2021-06-09 07:06:52.570000',8,5),(33,'REGISTERED THE ARCHIVE','2021-06-09 07:11:00.694000',9,5),(34,'ARCHIVE CREATED BY USER','2021-06-09 07:11:00.727000',9,5);
/*!40000 ALTER TABLE `archive_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
  `id` int NOT NULL AUTO_INCREMENT,
  `complete_name` varchar(255) NOT NULL COMMENT 'Authority complete Name',
  `position` varchar(255) NOT NULL COMMENT 'Authority position',
  `signature` varchar(255) NOT NULL COMMENT 'Authority signature ',
  `status` int NOT NULL COMMENT ' Status du delegué, 0 si le delegué est inactif et 1 sinon',
  `region_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK543qag5bd92suv2dsp8rcbd3b` (`region_id`),
  CONSTRAINT `FK543qag5bd92suv2dsp8rcbd3b` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES (1,'MINT','xxxx','1.jpeg',1,1);
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `capacity`
--

DROP TABLE IF EXISTS `capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `capacity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `capacity_number` varchar(255) DEFAULT NULL COMMENT 'capacity number',
  `on_process` int DEFAULT NULL COMMENT 'set with application id if capacity is pending processs and 0 if not',
  `status` varchar(255) DEFAULT NULL COMMENT 'capacity status to tells if capacity is suspended or not 1 if suspended and 0 if not ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `capacity`
--

LOCK TABLES `capacity` WRITE;
/*!40000 ALTER TABLE `capacity` DISABLE KEYS */;
/*!40000 ALTER TABLE `capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `distance_from_coast` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certificate`
--

DROP TABLE IF EXISTS `certificate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certificate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `delivery_date` datetime DEFAULT NULL COMMENT 'attestation delivery to owner date ',
  `is_suspended` varchar(255) DEFAULT NULL COMMENT 'attestation status to tells if attestation is suspended or not 1 if suspended and 0 if not ',
  `matricule` varchar(255) DEFAULT NULL COMMENT 'certificate matricule',
  `on_process` int DEFAULT NULL COMMENT 'set with candidate session id if attestation is pending processs and 0 if not',
  `successfuly_printed_date` datetime DEFAULT NULL COMMENT 'succesfully printed date of attestation ',
  `student_session_id` int DEFAULT NULL,
  `jury_number` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `printed_date` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `authority_id` int DEFAULT NULL,
  `is_printed` int DEFAULT NULL COMMENT '1 si imprimer et 0 si non',
  PRIMARY KEY (`id`),
  KEY `FKp1ky0y7eb2bhg8j9qh06vo3x4` (`student_session_id`),
  KEY `FKh38b7iug1op5ypwb53jeymnt8` (`authority_id`),
  CONSTRAINT `FKh38b7iug1op5ypwb53jeymnt8` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FKp1ky0y7eb2bhg8j9qh06vo3x4` FOREIGN KEY (`student_session_id`) REFERENCES `student_session` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certificate`
--

LOCK TABLES `certificate` WRITE;
/*!40000 ALTER TABLE `certificate` DISABLE KEYS */;
INSERT INTO `certificate` VALUES (1,NULL,NULL,NULL,0,NULL,4,NULL,'CE0001','2021-02-24 09:32:27.436','MAE',1,1),(2,NULL,NULL,NULL,19,NULL,5,'CE74455JURY',NULL,NULL,'MAE',1,0);
/*!40000 ALTER TABLE `certificate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `choice_training_center`
--

DROP TABLE IF EXISTS `choice_training_center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `choice_training_center` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_choice` varchar(255) DEFAULT NULL COMMENT 'les choix de applicant',
  `applicant_id` int DEFAULT NULL,
  `training_center_id` int DEFAULT NULL,
  `choice` varchar(255) DEFAULT NULL COMMENT 'les choix de applicant',
  PRIMARY KEY (`id`),
  KEY `FKhes5pibl5lw9q7x7j56vghf68` (`applicant_id`),
  KEY `FK6snva47kxjcyghhwa74ru4o1x` (`training_center_id`),
  CONSTRAINT `FK6snva47kxjcyghhwa74ru4o1x` FOREIGN KEY (`training_center_id`) REFERENCES `training_center` (`id`),
  CONSTRAINT `FKhes5pibl5lw9q7x7j56vghf68` FOREIGN KEY (`applicant_id`) REFERENCES `applicant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choice_training_center`
--

LOCK TABLES `choice_training_center` WRITE;
/*!40000 ALTER TABLE `choice_training_center` DISABLE KEYS */;
INSERT INTO `choice_training_center` VALUES (1,'First',1,1,NULL),(2,'Second',1,1,NULL),(3,'Third',1,1,NULL),(4,'First',2,1,NULL),(5,'Second',2,1,NULL),(6,'Third',2,1,NULL),(7,'First',3,1,NULL),(8,'Second',3,1,NULL),(9,'Third',3,1,NULL),(10,'First',4,1,NULL),(11,'Second',4,1,NULL),(12,'Third',4,1,NULL),(13,'First',5,1,NULL),(14,'Second',5,NULL,NULL),(15,'Third',5,NULL,NULL),(16,'First',6,1,NULL),(17,'Second',6,NULL,NULL),(18,'Third',6,NULL,NULL);
/*!40000 ALTER TABLE `choice_training_center` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country` (
  `id` int NOT NULL AUTO_INCREMENT,
  `abrevation` varchar(255) DEFAULT NULL,
  `country_name` varchar(255) DEFAULT NULL,
  `country_name_french` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'AFG ','Afghan',NULL),(2,'ALB','Albanian',NULL),(3,'DZA','Algerian',NULL),(4,' AND','Andorran',NULL),(5,' AGO','Angolian',NULL),(6,'ATG','Antiguans, Barbudans',NULL),(7,' MDV','Maldivan',NULL),(8,'ARG ','Argentinean',NULL),(9,'ARM','Armenian',NULL),(10,'AUS','Australian',NULL),(11,'AUT','Austrian',NULL),(12,'AZE','Azerbaijani',NULL),(13,'BHS','Bahamian',NULL),(14,' BHR','Bahraini',NULL),(15,'BGD','Bangladeshi',NULL),(16,'BRB','Barbadian',NULL),(17,'BLR','Belarusian',NULL),(18,'BEL','Belgian',NULL),(19,'BLZ','Belizean',NULL),(20,'BEN','Beninese',NULL),(21,'BTN','Bhutanese',NULL),(22,'BOL','Bolivian',NULL),(23,'BIH','Bosnian',NULL),(24,'BWA','Botswana',NULL),(25,'BRA','Brazilian',NULL),(26,'BRN   ','Bruneian',NULL),(27,'BGR','Bulgarian',NULL),(28,'BFA','Burkinabe',NULL),(29,'BDI','Burundian',NULL),(30,'KHM','Cambodian',NULL),(31,'CMR','Cameroonian','Camerounaise'),(32,'CAN','Canadian',NULL),(33,'CPV','Cape Verdian',NULL),(34,'CAF','Central African',NULL),(35,'TCD','Chadian',NULL),(36,'CHL','Chilean',NULL),(37,'CHN','Chinese',NULL),(38,' COL','Colombian',NULL),(39,'COM ','Comoran',NULL),(40,'COG','Congolese',NULL),(41,'COD','Congolese D.R.C',NULL),(42,'CRI','Costa Rican',NULL),(43,'CIV  ','Ivorian',NULL),(44,'HRV','Croatian',NULL),(45,'CUB  ','Cuban',NULL),(46,'CYP','Cypriot',NULL),(47,'CZE','Czech',NULL),(48,'DNK','Danish',NULL),(49,'DJI','Djibouti',NULL),(50,'DMA','Dominican',NULL),(51,'DOM','Republic Dominican',NULL),(52,'TMP','East Timorese',NULL),(53,'ECU','Ecuadorean',NULL),(54,'EGY','Egyptian',NULL),(55,'SLV','Salvadoran',NULL),(56,'GNQ','Equatorial Guinean',NULL),(57,'ERI','Eritrean',NULL),(58,'EST','Estonian',NULL),(59,' ETH ','Ethiopian',NULL),(60,'FJI','Fijian',NULL),(61,'FIN','Finnish',NULL),(62,'FRA','French',NULL),(63,'GAB','Gabonese',NULL),(64,'GMB','Gambian',NULL),(65,'GEO','Georgian',NULL),(66,'DEU','German',NULL),(67,'GHA  ','Ghanaian',NULL),(68,'GRC','Greek',NULL),(69,'GRD','Grenadian',NULL),(70,'GTM','Guatemalan',NULL),(71,'GIN','Guinean',NULL),(72,'GNB','Guinea-Bissauan',NULL),(73,'GUY','Guyanese',NULL),(74,'HTI','Haitian',NULL),(75,'BIH','Herzegovinian',NULL),(76,'HND','Honduran',NULL),(77,'HUN','Hungarian',NULL),(78,'ISL','Icelander',NULL),(79,'IND','Indian',NULL),(80,'IDN','Indonesian',NULL),(81,'IRN','Iranian',NULL),(82,'IRQ','Iraqi',NULL),(83,'IRL','Irish',NULL),(84,'ISR','Israeli',NULL),(85,'ITA','Italian',NULL),(86,'JAM ','Jamaican',NULL),(87,'JPN','Japanese',NULL),(88,' JOR','Jordanian',NULL),(89,'KAZ','Kazakhstani',NULL),(90,'KEN ','Kenyan',NULL),(91,'KIR','I-Kiribati',NULL),(92,'PRK','North Korean',NULL),(93,'KOR','South Korean',NULL),(94,'KWT ','Kuwaiti',NULL),(95,'KGZ','Kirghiz',NULL),(96,'LAO ','Lao or Laotian',NULL),(97,'LVA','Latvian',NULL),(98,'LBN ','Lebanese',NULL),(99,'LSO','Lesotho',NULL),(100,'LBR','Liberian',NULL),(101,'LBY','Libyan',NULL),(102,' LIE','Liechtensteiner',NULL),(103,'LTU','Lithuanian',NULL),(104,'LUX','Luxembourger',NULL),(105,' MKD','Macedonian',NULL),(106,' MDG ','Malagasy',NULL),(107,'MWI','Malawian',NULL),(108,' MYS','Malaysian',NULL),(109,'MLI','Malian',NULL),(110,'MLT','Maltese',NULL),(111,'MHL','Marshallese',NULL),(112,'MRT','Mauritanian',NULL),(113,'MUS','Mauritian',NULL),(114,'MEX','Mexican',NULL),(115,'FSM','Micronesian',NULL),(116,'MDA','Moldovan',NULL),(117,'MCO','Monegasque',NULL),(118,'MNG','Mongolian',NULL),(119,'MAR','Moroccan',NULL),(120,'MOZ ','Mozambican',NULL),(121,'MMR( Myanmar)','Burmese',NULL),(122,'NAM','Namibian',NULL),(123,'NRU','Nauruan',NULL),(124,'NPL','Nepalese',NULL),(125,'NLD','Netherlander',NULL),(126,'NZL','New Zealander',NULL),(127,'NIC','Nicaraguan',NULL),(128,'NER','Nigerien',NULL),(129,'NGA ','Nigerian',NULL),(130,'NOR','Norwegian',NULL),(131,'OMN  ','Omani',NULL),(132,'PAK','Pakistani',NULL),(133,' PLW','Palauan',NULL),(134,'PAN ','Panamanian',NULL),(135,'PNG','Papua New Guinean',NULL),(136,'PRY','Paraguayan',NULL),(137,'PER ','Peruvian',NULL),(138,' PHL','Filipino',NULL),(139,'POL','Polish',NULL),(140,'PRT','Portuguese',NULL),(141,'QAT','Qatari',NULL),(142,'ROM','Romanian',NULL),(143,' RUS','Russian',NULL),(144,'RWA ','Rwandan',NULL),(145,'KNA','Kittian and Nevisian',NULL),(146,' LCA','Saint Lucian',NULL),(147,'WSM','Samoan',NULL),(148,'SMR','San Marinese',NULL),(149,'STP ','Sao Tomean',NULL),(150,'SAU','Saudi Arabian',NULL),(151,'SEN','Senegalese',NULL),(152,'SRB**','Serbian',NULL),(153,'SYC','Seychellois',NULL),(154,'SLE  ','Sierra Leonean',NULL),(155,'SGP ','Singaporean',NULL),(156,'SVK','Slovakian',NULL),(157,'SVN','Slovenian',NULL),(158,'SLB','Solomon Islander',NULL),(159,'SOM','Somali',NULL),(160,'ZAF ','South African',NULL),(161,'ESP','Spanish',NULL),(162,'LKA','Sri Lankan',NULL),(163,'SDN','Sudanese',NULL),(164,'SUR','Surinamer',NULL),(165,'SWZ','Swazi',NULL),(166,'SWE','Swedish',NULL),(167,'CHE','Swiss',NULL),(168,'SYR ','Syrian',NULL),(169,'TWN ','Taiwanese',NULL),(170,'TJK','Tajik',NULL),(171,'TZA ','Tanzanian',NULL),(172,'THA','Thai',NULL),(173,'TGO','Togolese',NULL),(174,'TON','Tongan',NULL),(175,'TTO','Trinidadian',NULL),(176,'TUN','Tunisian',NULL),(177,'TUR','Turkish',NULL),(178,'TKM','Turkmen',NULL),(179,'TUV ','Tuvaluan',NULL),(180,'UGA','Ugandan',NULL),(181,'UKR','Ukrainian',NULL),(182,'ARE','Emirian',NULL),(183,'GBR','British',NULL),(184,'USA','American',NULL),(185,'URY ','Uruguayan',NULL),(186,'UZB','Uzbekistani',NULL),(187,'VUT(Vanuatu)','Ni-Vanuatu',NULL),(188,'VEN ','Venezuelan',NULL),(189,'VNM ','Vietnamese',NULL),(190,'YEM','Yemenite',NULL),(191,'ZMB ','Zambian',NULL),(192,'ZWE ','Zimbabwean',NULL),(193,'YUG','Yugoslavia',NULL),(194,'NRD','Netherlands',NULL);
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `complete_name` varchar(255) NOT NULL COMMENT 'Course complete Name',
  `course_coeff` int DEFAULT NULL COMMENT 'coefficient of course ',
  `course_max_note` int DEFAULT NULL COMMENT 'note maximale du cours  eg 20 over 20',
  `course_min_note` int DEFAULT NULL COMMENT 'note eliminatoire du cours eg 1 over 20 ',
  `module_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgn96pgph0liiif3l57raj2ab` (`module_id`),
  CONSTRAINT `FKgn96pgph0liiif3l57raj2ab` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'Contrôle Continue / Evaluation',1,20,NULL,1),(2,'Assurances / Insurance',2,20,NULL,2),(3,'Bilinguisme / Bilingualism',2,20,NULL,2),(4,'Cartographie / Cartography',2,20,NULL,2),(5,'Civisme et morale / Citizenship and Moral Education',3,20,NULL,2),(6,'Code de la route / Highway cod',5,20,NULL,2),(9,'Connaissance de la route / Road knowledge',2,20,NULL,2),(10,'Connaissance du véhicule / Vehicle knowledge',2,20,NULL,2),(11,'Droit et législation / Law and Legislation',2,20,NULL,2),(12,'Environnement / Environment',2,20,NULL,2),(13,'Gestion des PME et PMI / Management of SMEs and SMIs',3,20,NULL,2),(14,'Informatique - TIC / Computer - ICT',2,20,NULL,2),(15,'Psychopédagogie / Psychopedagogy',4,20,NULL,2),(16,'Réglementation en zone CEMACRegulation in the CEMAC Zone',2,20,NULL,2),(17,'Sécourisme / First AID',2,20,NULL,2),(19,'Sécurité routière / Road Safety',2,20,NULL,2),(20,'Signalisation routière / Road Signs',3,20,NULL,2),(22,'Maîtrise de la conduite des véhicules de tourisme et des motocyclesMastery of the driving of passenger vehicles and motorcycles',6,20,NULL,3),(23,'Maîtrise des techniques et méthodes d’évaluations pratiquesMastery of practical assessment techniques and methods',2,20,NULL,3),(24,'Stage / Intenship',4,20,NULL,3);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_tracking`
--

DROP TABLE IF EXISTS `course_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpveajcf94c74u4mh4wbxnwh4k` (`course_id`),
  KEY `FK4r9fy36ncabgma7b86lpg2atf` (`user_id`),
  CONSTRAINT `FK4r9fy36ncabgma7b86lpg2atf` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKpveajcf94c74u4mh4wbxnwh4k` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_tracking`
--

LOCK TABLES `course_tracking` WRITE;
/*!40000 ALTER TABLE `course_tracking` DISABLE KEYS */;
INSERT INTO `course_tracking` VALUES (1,'EDITED THE COURSE','2020-12-10 08:20:57.070000',1,2),(2,'EDITED THE COURSE ','2020-12-10 08:23:22.676000',1,2);
/*!40000 ALTER TABLE `course_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `district`
--

DROP TABLE IF EXISTS `district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `district` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT 'district name',
  `division_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK78g8m793eebrogjuuey668ihu` (`division_id`),
  CONSTRAINT `FK78g8m793eebrogjuuey668ihu` FOREIGN KEY (`division_id`) REFERENCES `division` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `district`
--

LOCK TABLES `district` WRITE;
/*!40000 ALTER TABLE `district` DISABLE KEYS */;
INSERT INTO `district` VALUES (1,'BANYO',3);
/*!40000 ALTER TABLE `district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `division`
--

DROP TABLE IF EXISTS `division`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `division` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `region_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK48f08js4cl0gx0ad24vhs0uuq` (`region_id`),
  CONSTRAINT `FK48f08js4cl0gx0ad24vhs0uuq` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `division`
--

LOCK TABLES `division` WRITE;
/*!40000 ALTER TABLE `division` DISABLE KEYS */;
INSERT INTO `division` VALUES (1,'DJEREM',1),(2,'FARO ET DEO',1),(3,'MAYO BANYO',1),(4,'MBERE',1),(5,'VINA',1),(6,'HAUTE SANAGA',2),(7,'LEKIE',2),(8,'MBAM ET INOUBOU',2),(9,'MBAM ET KIM',2),(10,'MEFOU ET AFAMBA',2),(11,'MEFOU ET AKONO',2),(12,'MFOUNDI',2),(13,'NYONG ET MFOUMOU',2),(14,'NYONG ET KELLE',2),(15,'NYONG ET SO\'O',2),(16,'DIAMARE',3),(17,'LOGONE ET CHARI',3),(18,'MAYO DANAY',3),(19,'MAYO SAVA',3),(20,'MAYO TSANAGA',3),(21,'MAYO KANI',3),(22,'BOUMBA ET NGOKO',4),(23,'HAUT NYONG',4),(24,'KADEY',4),(25,'LOM ET DJEREM',4),(26,'MOUNGO',5),(27,'NKAM',5),(28,'SANAGA MARITIME',5),(29,'WOURI',5),(30,'BENOUE',6),(31,'FARO',6),(32,'MAYO REY',6),(33,'MAYO LOUTI',6),(34,'BOYO',7),(35,'BUI',7),(36,'DONGA-MANTUNG',7),(37,'MENCHUM',7),(38,'MEZAM',7),(39,'MOMO',7),(40,'NGOKETUNJIA',7),(41,'BAMBOUTOS',8),(42,'HAUT-NKAM',8),(43,'HAUTS-PLATEAUX',8),(44,'KOUNG-KHI',8),(45,'MENOUA',8),(46,'MIFI',8),(47,'NDE',8),(48,'NOUN',8),(49,'DJA ET LOBO',9),(50,'MVILA',9),(51,'OCEAN',9),(52,'VALLEE DU NTEM',9),(53,'FAKO',10),(54,'KUPEMANENGUBA',10),(55,'LEBIALEM',10),(56,'MANYU',10),(57,'MEME',10),(58,'NDIAN',10);
/*!40000 ALTER TABLE `division` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eligible_center`
--

DROP TABLE IF EXISTS `eligible_center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eligible_center` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eligible_center_status_id` int DEFAULT NULL,
  `exam_center_id` int DEFAULT NULL,
  `exam_session_id` int DEFAULT NULL,
  `max_student` int DEFAULT NULL,
  `jury_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrw0x6tf1j9gmero4u7gpycga5` (`eligible_center_status_id`),
  KEY `FKlklaq8957ggs3lk9sqbv3a1et` (`exam_center_id`),
  KEY `FKlacb1534hj26cnd2omwchgmhq` (`exam_session_id`),
  CONSTRAINT `FKlacb1534hj26cnd2omwchgmhq` FOREIGN KEY (`exam_session_id`) REFERENCES `exam_session` (`id`),
  CONSTRAINT `FKlklaq8957ggs3lk9sqbv3a1et` FOREIGN KEY (`exam_center_id`) REFERENCES `exam_center` (`id`),
  CONSTRAINT `FKrw0x6tf1j9gmero4u7gpycga5` FOREIGN KEY (`eligible_center_status_id`) REFERENCES `eligible_center_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eligible_center`
--

LOCK TABLES `eligible_center` WRITE;
/*!40000 ALTER TABLE `eligible_center` DISABLE KEYS */;
INSERT INTO `eligible_center` VALUES (1,4,1,1,10,'JURY201214'),(2,4,1,2,NULL,'CE74455JURY'),(3,4,1,3,NULL,'JURY12345');
/*!40000 ALTER TABLE `eligible_center` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eligible_center_status`
--

DROP TABLE IF EXISTS `eligible_center_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eligible_center_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL COMMENT 'eligible center status',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eligible_center_status`
--

LOCK TABLES `eligible_center_status` WRITE;
/*!40000 ALTER TABLE `eligible_center_status` DISABLE KEYS */;
INSERT INTO `eligible_center_status` VALUES (1,'CREATED'),(2,'OPENED'),(3,'CLOSED'),(4,'VALIDATED'),(5,'PV GENERATED');
/*!40000 ALTER TABLE `eligible_center_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eligible_center_tracking`
--

DROP TABLE IF EXISTS `eligible_center_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eligible_center_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `eligible_center_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqt3e8qsmwt805oonsusf9rrbs` (`eligible_center_id`),
  KEY `FK7k8kxg4wf6ouy1yrpjt5qy257` (`user_id`),
  CONSTRAINT `FK7k8kxg4wf6ouy1yrpjt5qy257` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKqt3e8qsmwt805oonsusf9rrbs` FOREIGN KEY (`eligible_center_id`) REFERENCES `eligible_center` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eligible_center_tracking`
--

LOCK TABLES `eligible_center_tracking` WRITE;
/*!40000 ALTER TABLE `eligible_center_tracking` DISABLE KEYS */;
INSERT INTO `eligible_center_tracking` VALUES (1,'PV WAS CREATED BY USER: MANDENGUE paule aggy','2020-12-09 08:10:31.179000',1,2),(2,'OPEN ELIGIBLE CENTER','2020-12-09 08:12:36.319000',1,2),(3,'PV WAS OPEN BY USER: MANDENGUE paule aggy','2020-12-09 08:12:36.414000',1,2),(4,'CLOSE ELIGIBLE CENTER','2020-12-09 08:27:45.303000',1,2),(5,'PV WAS CLOSED BY USER: MANDENGUE paule aggy','2020-12-09 08:27:45.462000',1,2),(6,'GENERATED PV','2020-12-18 03:07:35.897000',1,2),(7,'PV GENERATED BY USER: MANDENGUE paule aggy','2020-12-18 03:07:35.988000',1,2),(8,'VALIDATE ELIGIBLE CENTER','2020-12-18 03:08:09.118000',1,2),(9,'PV WAS VALIDATED BY USER: MANDENGUE paule aggy','2020-12-18 03:08:09.284000',1,2),(10,'CLOSE ELIGIBLE CENTER','2020-12-19 06:22:57.535000',1,2),(11,'PV WAS CLOSED BY USER: MANDENGUE paule aggy','2020-12-19 06:22:57.666000',1,2),(12,'GENERATED PV','2020-12-22 02:42:57.524000',1,2),(13,'PV GENERATED BY USER: MANDENGUE paule aggy','2020-12-22 02:42:57.725000',1,2),(14,'VALIDATE ELIGIBLE CENTER','2020-12-22 02:43:22.413000',1,2),(15,'PV WAS VALIDATED BY USER: MANDENGUE paule aggy','2020-12-22 02:43:22.606000',1,2),(16,'GENERATED PV','2021-01-06 02:04:25.259000',1,2),(17,'PV GENERATED BY USER: MANDENGUE paule aggy','2021-01-06 02:04:25.422000',1,2),(18,'VALIDATE ELIGIBLE CENTER','2021-01-06 02:09:25.086000',1,2),(19,'PV WAS VALIDATED BY USER: MANDENGUE paule aggy','2021-01-06 02:09:25.338000',1,2),(21,'CLOSE ELIGIBLE CENTER','2021-02-24 01:57:23.793000',1,2),(22,'PV WAS CLOSED BY USER: MANDENGUE paule aggy','2021-02-24 01:57:23.894000',1,2),(23,'RESET ELIGIBLE CENTER','2021-02-24 01:58:58.598000',1,2),(24,'PV WAS RESETTED BY USER: MANDENGUE paule aggy','2021-02-24 01:58:58.677000',1,2),(25,'CLOSE ELIGIBLE CENTER','2021-02-24 02:17:40.372000',1,2),(26,'PV WAS CLOSED BY USER: MANDENGUE paule aggy','2021-02-24 02:17:40.476000',1,2),(27,'GENERATED PV','2021-02-24 02:17:48.698000',1,2),(28,'PV GENERATED BY USER: MANDENGUE paule aggy','2021-02-24 02:17:48.744000',1,2),(29,'GENERATED PV','2021-02-24 02:20:05.493000',1,2),(30,'PV WAS RESETTED BY USER: MANDENGUE paule aggy','2021-02-24 02:20:05.919000',1,2),(31,'GENERATED PV','2021-02-25 05:00:17.513000',1,2),(32,'PV GENERATED BY USER: MANDENGUE paule aggy','2021-02-25 05:00:17.747000',1,2),(33,'VALIDATE ELIGIBLE CENTER','2021-02-25 05:03:01.771000',1,2),(34,'PV WAS VALIDATED BY USER: MANDENGUE paule aggy','2021-02-25 05:03:02.025000',1,2),(35,'PV WAS CREATED BY USER: MANDENGUE paule aggy','2021-02-26 04:50:34.934000',2,2),(36,'CLOSE ELIGIBLE CENTER','2021-02-26 05:02:34.950000',2,2),(37,'PV WAS CLOSED BY USER: MANDENGUE paule aggy','2021-02-26 05:02:35.002000',2,2),(38,'RESET ELIGIBLE CENTER','2021-02-26 05:06:45.678000',2,2),(39,'PV WAS RESETTED BY USER: MANDENGUE paule aggy','2021-02-26 05:06:45.834000',2,2),(40,'CLOSE ELIGIBLE CENTER','2021-02-26 05:07:36.624000',2,2),(41,'PV WAS CLOSED BY USER: MANDENGUE paule aggy','2021-02-26 05:07:36.675000',2,2),(42,'GENERATED PV','2021-02-26 05:15:42.756000',2,2),(43,'PV GENERATED BY USER: MANDENGUE paule aggy','2021-02-26 05:15:42.841000',2,2),(44,'VALIDATE ELIGIBLE CENTER','2021-02-26 05:17:53.328000',2,2),(45,'PV WAS VALIDATED BY USER: MANDENGUE paule aggy','2021-02-26 05:17:53.486000',2,2),(46,'PV WAS CREATED BY USER: MANDENGUE paule aggy','2021-02-27 05:31:24.337000',3,2),(47,'CLOSE ELIGIBLE CENTER','2021-06-01 04:12:30.121000',3,5),(48,'PV WAS CLOSED BY USER: ADMIN NNOKO','2021-06-01 04:12:30.251000',3,5),(49,'GENERATED PV','2021-06-01 04:13:00.772000',3,5),(50,'PV GENERATED BY USER: ADMIN NNOKO','2021-06-01 04:13:00.908000',3,5),(51,'VALIDATE ELIGIBLE CENTER','2021-06-01 04:14:04.149000',3,5),(52,'PV WAS VALIDATED BY USER: ADMIN NNOKO','2021-06-01 04:14:04.259000',3,5),(53,'CLOSE ELIGIBLE CENTER','2021-07-13 07:10:42.294000',3,5),(54,'PV WAS CLOSED BY USER: ADMIN NNOKO','2021-07-13 07:10:42.643000',3,5),(55,'GENERATED PV','2021-07-13 07:16:12.689000',3,5),(56,'PV GENERATED BY USER: ADMIN NNOKO','2021-07-13 07:16:12.785000',3,5),(57,'VALIDATE ELIGIBLE CENTER','2021-07-13 07:18:13.165000',3,5),(58,'PV WAS VALIDATED BY USER: ADMIN NNOKO','2021-07-13 07:18:14.158000',3,5);
/*!40000 ALTER TABLE `eligible_center_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eligible_speciality`
--

DROP TABLE IF EXISTS `eligible_speciality`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eligible_speciality` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `speciality_id` bigint DEFAULT NULL,
  `training_center_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqr0cpqmtkqvmqc50y8kfwoa92` (`speciality_id`),
  KEY `FKa3k5ldh2ct3xy7sfdxluuht8i` (`training_center_id`),
  CONSTRAINT `FKa3k5ldh2ct3xy7sfdxluuht8i` FOREIGN KEY (`training_center_id`) REFERENCES `training_center` (`id`),
  CONSTRAINT `FKqr0cpqmtkqvmqc50y8kfwoa92` FOREIGN KEY (`speciality_id`) REFERENCES `speciality` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eligible_speciality`
--

LOCK TABLES `eligible_speciality` WRITE;
/*!40000 ALTER TABLE `eligible_speciality` DISABLE KEYS */;
INSERT INTO `eligible_speciality` VALUES (29,1,4),(30,2,1),(31,3,1),(32,1,1);
/*!40000 ALTER TABLE `eligible_speciality` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrance_eligible_center`
--

DROP TABLE IF EXISTS `entrance_eligible_center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrance_eligible_center` (
  `id` int NOT NULL AUTO_INCREMENT,
  `entrance_eligible_center_status_id` int DEFAULT NULL,
  `entrance_exam_center_id` int DEFAULT NULL,
  `entrance_exam_session_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgrfndhd19e3qtr8yoo8c231e9` (`entrance_eligible_center_status_id`),
  KEY `FK7lpueryp4j7y6s2yf0fnycw69` (`entrance_exam_center_id`),
  KEY `FKpwo6g2vftinc8rgy810vunx4i` (`entrance_exam_session_id`),
  CONSTRAINT `FK7lpueryp4j7y6s2yf0fnycw69` FOREIGN KEY (`entrance_exam_center_id`) REFERENCES `entrance_exam_center` (`id`),
  CONSTRAINT `FKgrfndhd19e3qtr8yoo8c231e9` FOREIGN KEY (`entrance_eligible_center_status_id`) REFERENCES `entrance_eligible_center_status` (`id`),
  CONSTRAINT `FKpwo6g2vftinc8rgy810vunx4i` FOREIGN KEY (`entrance_exam_session_id`) REFERENCES `entrance_exam_session` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrance_eligible_center`
--

LOCK TABLES `entrance_eligible_center` WRITE;
/*!40000 ALTER TABLE `entrance_eligible_center` DISABLE KEYS */;
INSERT INTO `entrance_eligible_center` VALUES (1,4,1,1),(2,4,1,2),(3,4,1,3),(4,1,1,4),(5,2,1,5);
/*!40000 ALTER TABLE `entrance_eligible_center` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrance_eligible_center_status`
--

DROP TABLE IF EXISTS `entrance_eligible_center_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrance_eligible_center_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL COMMENT 'entrance eligible center status',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrance_eligible_center_status`
--

LOCK TABLES `entrance_eligible_center_status` WRITE;
/*!40000 ALTER TABLE `entrance_eligible_center_status` DISABLE KEYS */;
INSERT INTO `entrance_eligible_center_status` VALUES (1,'CREATED'),(2,'OPENED'),(3,'CLOSED'),(4,'VALIDATED'),(5,'PV GENERATED');
/*!40000 ALTER TABLE `entrance_eligible_center_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrance_eligible_center_tracking`
--

DROP TABLE IF EXISTS `entrance_eligible_center_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrance_eligible_center_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `entrance_eligible_center_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfebj71yqalvlr8vou1urxec53` (`entrance_eligible_center_id`),
  KEY `FKpnp1rtrhpekb2ibg6vnckp76x` (`user_id`),
  CONSTRAINT `FKfebj71yqalvlr8vou1urxec53` FOREIGN KEY (`entrance_eligible_center_id`) REFERENCES `entrance_eligible_center` (`id`),
  CONSTRAINT `FKpnp1rtrhpekb2ibg6vnckp76x` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrance_eligible_center_tracking`
--

LOCK TABLES `entrance_eligible_center_tracking` WRITE;
/*!40000 ALTER TABLE `entrance_eligible_center_tracking` DISABLE KEYS */;
INSERT INTO `entrance_eligible_center_tracking` VALUES (1,'PV WAS CREATED BY USER: MANDENGUE paule aggy','2021-03-17 05:27:46.446000',1,2),(2,'OPEN ELIGIBLE CENTER','2021-03-17 05:31:52.207000',1,2),(3,'PV WAS OPEimport java.util.Collection;N BY USER: MANDENGUE paule aggy','2021-03-17 05:31:52.374000',1,2),(4,'CLOSE ELIGIBLE CENTER','2021-04-01 08:43:24.300000',1,2),(5,'PV WAS CLOSED BY USER: MANDENGUE paule aggy','2021-04-01 08:43:24.529000',1,2),(6,'GENERATED PV','2021-04-01 08:43:29.162000',1,2),(7,'PV GENERATED BY USER: MANDENGUE paule aggy','2021-04-01 08:43:29.815000',1,2),(8,'VALIDATE ELIGIBLE CENTER','2021-04-01 08:43:56.662000',1,2),(9,'PV WAS VALIDATED BY USER: MANDENGUE paule aggy','2021-04-01 08:43:56.742000',1,2),(10,'PV WAS CREATED BY USER: MANDENGUE paule aggy','2021-04-01 10:48:56.420000',2,2),(11,'OPEN ELIGIBLE CENTER','2021-04-01 10:49:00.481000',2,2),(12,'PV WAS OPEimport java.util.Collection;N BY USER: MANDENGUE paule aggy','2021-04-01 10:49:00.530000',2,2),(13,'CLOSE ELIGIBLE CENTER','2021-04-01 10:49:44.955000',2,2),(14,'PV WAS CLOSED BY USER: MANDENGUE paule aggy','2021-04-01 10:49:45.009000',2,2),(15,'GENERATED PV','2021-04-01 10:49:52.273000',2,2),(16,'PV GENERATED BY USER: MANDENGUE paule aggy','2021-04-01 10:49:52.358000',2,2),(17,'VALIDATE ELIGIBLE CENTER','2021-04-01 10:50:09.113000',2,2),(18,'PV WAS VALIDATED BY USER: MANDENGUE paule aggy','2021-04-01 10:50:09.198000',2,2),(19,'PV WAS CREATED BY USER: MANDENGUE paule aggy','2021-04-01 11:06:52.141000',3,2),(20,'OPEN ELIGIBLE CENTER','2021-04-01 11:06:56.970000',3,2),(21,'PV WAS OPEimport java.util.Collection;N BY USER: MANDENGUE paule aggy','2021-04-01 11:06:57.054000',3,2),(22,'CLOSE ELIGIBLE CENTER','2021-04-01 11:09:04.477000',3,2),(23,'PV WAS CLOSED BY USER: MANDENGUE paule aggy','2021-04-01 11:09:04.577000',3,2),(24,'GENERATED PV','2021-04-01 11:09:12.144000',3,2),(25,'PV GENERATED BY USER: MANDENGUE paule aggy','2021-04-01 11:09:12.229000',3,2),(26,'VALIDATE ELIGIBLE CENTER','2021-04-01 11:09:37.075000',3,2),(27,'PV WAS VALIDATED BY USER: MANDENGUE paule aggy','2021-04-01 11:09:37.157000',3,2),(28,'PV WAS CREATED BY USER: MANDENGUE paule aggy','2021-04-01 11:24:36.319000',4,2),(29,'OPEN ELIGIBLE CENTER','2021-04-01 11:25:09.783000',4,2),(30,'PV WAS OPEimport java.util.Collection;N BY USER: MANDENGUE paule aggy','2021-04-01 11:25:09.837000',4,2),(31,'CLOSE ELIGIBLE CENTER','2021-04-01 11:26:40.746000',4,2),(32,'PV WAS CLOSED BY USER: MANDENGUE paule aggy','2021-04-01 11:26:40.897000',4,2),(33,'GENERATED PV','2021-04-01 11:27:12.891000',4,2),(34,'PV GENERATED BY USER: MANDENGUE paule aggy','2021-04-01 11:27:12.939000',4,2),(35,'VALIDATE ELIGIBLE CENTER','2021-04-01 11:27:33.483000',4,2),(36,'PV WAS VALIDATED BY USER: MANDENGUE paule aggy','2021-04-01 11:27:33.584000',4,2),(37,'PV WAS CREATED BY USER: ADMIN NNOKO','2021-06-16 07:22:01.581000',5,5),(38,'CLOSE ELIGIBLE CENTER','2021-07-13 08:30:26.915000',3,5),(39,'PV WAS CLOSED BY USER: ADMIN NNOKO','2021-07-13 08:30:27.133000',3,5),(40,'RESET ELIGIBLE CENTER','2021-07-13 08:31:37.744000',3,5),(41,'PV WAS RESETTED BY USER: ADMIN NNOKO','2021-07-13 08:31:37.978000',3,5),(42,'CLOSE ELIGIBLE CENTER','2021-07-13 08:35:46.025000',3,5),(43,'PV WAS CLOSED BY USER: ADMIN NNOKO','2021-07-13 08:35:46.176000',3,5),(44,'GENERATED PV','2021-07-13 08:46:57.612000',3,5),(45,'PV GENERATED BY USER: ADMIN NNOKO','2021-07-13 08:46:58.146000',3,5),(46,'GENERATED PV','2021-07-13 08:52:22.929000',3,5),(47,'GENERATED PV','2021-07-13 08:55:04.048000',3,5),(48,'PV GENERATED BY USER: ADMIN NNOKO','2021-07-13 08:55:04.514000',3,5),(49,'VALIDATE ELIGIBLE CENTER','2021-07-13 08:55:31.549000',3,5),(50,'PV WAS VALIDATED BY USER: ADMIN NNOKO','2021-07-13 08:55:31.668000',3,5),(51,'OPEN ELIGIBLE CENTER','2021-07-16 06:53:09.800000',5,5),(52,'PV WAS OPEimport java.util.Collection;N BY USER: ADMIN NNOKO','2021-07-16 06:53:10.815000',5,5);
/*!40000 ALTER TABLE `entrance_eligible_center_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrance_exam_center`
--

DROP TABLE IF EXISTS `entrance_exam_center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrance_exam_center` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `division_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5mg3n2dyd7mhd82sbr4sam4p` (`division_id`),
  CONSTRAINT `FK5mg3n2dyd7mhd82sbr4sam4p` FOREIGN KEY (`division_id`) REFERENCES `division` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrance_exam_center`
--

LOCK TABLES `entrance_exam_center` WRITE;
/*!40000 ALTER TABLE `entrance_exam_center` DISABLE KEYS */;
INSERT INTO `entrance_exam_center` VALUES (1,'YAOUNDE',3);
/*!40000 ALTER TABLE `entrance_exam_center` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrance_exam_session`
--

DROP TABLE IF EXISTS `entrance_exam_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrance_exam_session` (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_at` datetime DEFAULT NULL COMMENT 'session creation date',
  `session_date` date DEFAULT NULL COMMENT 'session date',
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrance_exam_session`
--

LOCK TABLES `entrance_exam_session` WRITE;
/*!40000 ALTER TABLE `entrance_exam_session` DISABLE KEYS */;
INSERT INTO `entrance_exam_session` VALUES (1,'2021-03-17 05:27:46','2021-04-02',NULL),(2,'2021-04-01 10:48:56','2021-04-13',NULL),(3,'2021-04-01 11:06:52','2021-04-23',NULL),(4,'2021-04-01 11:24:36','2021-05-08',NULL),(5,'2021-06-16 07:22:02','2021-06-20',NULL);
/*!40000 ALTER TABLE `entrance_exam_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam_center`
--

DROP TABLE IF EXISTS `exam_center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam_center` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `district_id` int DEFAULT NULL,
  `division_id` int DEFAULT NULL,
  `sub_district_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8nn5ai2fmrb0eccfjxs3jmchf` (`district_id`),
  KEY `FKpkrsvjn5b3rkupw9kptlottlo` (`division_id`),
  KEY `FKt64isfjn9n06sedtvc31j6t64` (`sub_district_id`),
  CONSTRAINT `FK8nn5ai2fmrb0eccfjxs3jmchf` FOREIGN KEY (`district_id`) REFERENCES `district` (`id`),
  CONSTRAINT `FKpkrsvjn5b3rkupw9kptlottlo` FOREIGN KEY (`division_id`) REFERENCES `division` (`id`),
  CONSTRAINT `FKt64isfjn9n06sedtvc31j6t64` FOREIGN KEY (`sub_district_id`) REFERENCES `sub_district` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_center`
--

LOCK TABLES `exam_center` WRITE;
/*!40000 ALTER TABLE `exam_center` DISABLE KEYS */;
INSERT INTO `exam_center` VALUES (1,'YAOUNDE',1,3,NULL);
/*!40000 ALTER TABLE `exam_center` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam_session`
--

DROP TABLE IF EXISTS `exam_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam_session` (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_at` datetime DEFAULT NULL COMMENT 'session creation date',
  `session_date` date DEFAULT NULL COMMENT 'session date',
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_session`
--

LOCK TABLES `exam_session` WRITE;
/*!40000 ALTER TABLE `exam_session` DISABLE KEYS */;
INSERT INTO `exam_session` VALUES (1,'2020-12-09 08:10:31','2020-12-01','OPEN'),(2,'2021-02-26 04:50:35','2021-02-01','OPEN'),(3,'2021-02-27 05:31:24','2021-02-10','CLOSE');
/*!40000 ALTER TABLE `exam_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_role`
--

DROP TABLE IF EXISTS `group_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkug2299fgdv9nnv9anu05h7jh` (`group_id`),
  KEY `FKrlbrx4ujb613vbs9rql5uffdi` (`role_id`),
  CONSTRAINT `FKkug2299fgdv9nnv9anu05h7jh` FOREIGN KEY (`group_id`) REFERENCES `user_group` (`id`),
  CONSTRAINT `FKrlbrx4ujb613vbs9rql5uffdi` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_role`
--

LOCK TABLES `group_role` WRITE;
/*!40000 ALTER TABLE `group_role` DISABLE KEYS */;
INSERT INTO `group_role` VALUES (68,9,7),(69,9,27),(107,5,1),(108,5,2),(109,5,3),(110,5,4),(111,5,5),(112,5,6),(113,5,7),(114,5,8),(115,5,9),(116,5,10),(117,5,11),(118,5,12),(119,5,13),(120,5,14),(121,5,15),(122,5,16),(123,5,17),(124,5,18),(125,5,19),(126,5,20),(127,5,21),(128,5,22),(129,5,23),(130,5,24),(131,5,25),(132,5,26),(133,5,27),(134,5,28),(135,5,29),(136,5,30),(137,5,31),(138,5,32),(139,5,33),(140,5,34),(141,5,35),(142,5,36);
/*!40000 ALTER TABLE `group_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `in_slip`
--

DROP TABLE IF EXISTS `in_slip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `in_slip` (
  `id` int NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL COMMENT 'in slip creation date ',
  `not_found` int DEFAULT NULL COMMENT 'total number of applications not found in a in slip',
  `reference_number` varchar(255) DEFAULT NULL COMMENT 'in slip reference number ',
  `total` int DEFAULT NULL COMMENT 'total number of applications in a in slip',
  `in_slip_status_id` int DEFAULT NULL,
  `office_id` int DEFAULT NULL,
  `eligible_center_id` int DEFAULT NULL,
  `inslip_type` varchar(255) DEFAULT NULL COMMENT 'determines the source of the inslip -- Transcript= T, Certificate = C, Professional Card = P  ',
  PRIMARY KEY (`id`),
  KEY `FK23eb6yddp6i4soalliymcsrp5` (`in_slip_status_id`),
  KEY `FK72f8fd6kt42bs2fnlmnfmdg45` (`office_id`),
  KEY `FKi425l9fmgd05lrprqgm6m40sg` (`eligible_center_id`),
  CONSTRAINT `FK23eb6yddp6i4soalliymcsrp5` FOREIGN KEY (`in_slip_status_id`) REFERENCES `in_slip_status` (`id`),
  CONSTRAINT `FK72f8fd6kt42bs2fnlmnfmdg45` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`),
  CONSTRAINT `FKi425l9fmgd05lrprqgm6m40sg` FOREIGN KEY (`eligible_center_id`) REFERENCES `eligible_center` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `in_slip`
--

LOCK TABLES `in_slip` WRITE;
/*!40000 ALTER TABLE `in_slip` DISABLE KEYS */;
INSERT INTO `in_slip` VALUES (1,'2021-02-19 00:00:00',0,'AD-1-0221',15,1,1,NULL,'N'),(2,'2021-02-26 07:54:09',0,'AD-125-0221',20,2,1,NULL,NULL),(3,'2021-06-24 02:53:41',0,'AD-1-0621',2,1,1,NULL,NULL);
/*!40000 ALTER TABLE `in_slip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `in_slip_status`
--

DROP TABLE IF EXISTS `in_slip_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `in_slip_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL COMMENT 'brief description of status',
  `name` varchar(255) DEFAULT NULL COMMENT 'Caption of State/status',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `in_slip_status`
--

LOCK TABLES `in_slip_status` WRITE;
/*!40000 ALTER TABLE `in_slip_status` DISABLE KEYS */;
INSERT INTO `in_slip_status` VALUES (1,'REGISTERED','REGISTERED'),(2,'OPENED','OPENED'),(3,'CLOSED','CLOSED'),(4,'CONTROLLED. INSLIP IS OK OR HAS NO INCONSISTENCIES','CONTROLLED(SLIP OK)'),(5,'CONTROLLED. INSLIP HAS INCONSISTENCIES','CONTROLLED(SLIP NOT OK)');
/*!40000 ALTER TABLE `in_slip_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `in_slip_tracking`
--

DROP TABLE IF EXISTS `in_slip_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `in_slip_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `in_slip_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsl7b2brc87r3cvxb9qnj2cq32` (`in_slip_id`),
  KEY `FKgp1fvh0gg00pqbu4pqrrs3hd0` (`user_id`),
  CONSTRAINT `FKgp1fvh0gg00pqbu4pqrrs3hd0` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKsl7b2brc87r3cvxb9qnj2cq32` FOREIGN KEY (`in_slip_id`) REFERENCES `in_slip` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `in_slip_tracking`
--

LOCK TABLES `in_slip_tracking` WRITE;
/*!40000 ALTER TABLE `in_slip_tracking` DISABLE KEYS */;
INSERT INTO `in_slip_tracking` VALUES (1,'IN SLIP IS OPENED BY USER: MANDENGUE paule aggy','2021-02-26 07:58:15.375000',2,2),(2,'IN SLIP IS RESETTED BY USER: MANDENGUE paule aggy','2021-02-26 07:58:59.319000',2,2),(3,'IN SLIP IS RESETTED BY USER: MANDENGUE paule aggy','2021-05-26 02:09:51.204000',1,2),(4,'IN SLIP IS RESETTED BY USER: ADMIN NNOKO','2021-07-13 09:34:58.646000',2,5),(5,'IN SLIP IS OPENED BY USER: ADMIN NNOKO','2021-07-13 09:35:07.996000',2,5);
/*!40000 ALTER TABLE `in_slip_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `module` (
  `id` int NOT NULL AUTO_INCREMENT,
  `complete_name` varchar(255) NOT NULL COMMENT 'Course category complete Name eg Connaissances et aptitude pedagogiques ou Connaissances et aptitudes pratiques professionnelles',
  `speciality_id` bigint DEFAULT NULL,
  `classification` int DEFAULT NULL,
  `course_min_note` int DEFAULT NULL COMMENT 'note eliminatoire du cours eg 1 over 20 ',
  `module_min_note` int DEFAULT NULL COMMENT 'note eliminatoire du module eg 1 over 20 ',
  `status` int DEFAULT '1' COMMENT 'status of the module 1 if actif and 0 if suspended that means it is not taken in consideration',
  `module_percentage` int DEFAULT '0' COMMENT 'percentage of module ',
  PRIMARY KEY (`id`),
  KEY `FKe2sm04kls4jljpi1gs7j6pjvy` (`speciality_id`),
  CONSTRAINT `FKe2sm04kls4jljpi1gs7j6pjvy` FOREIGN KEY (`speciality_id`) REFERENCES `speciality` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` VALUES (1,'Evaluation Sommative / Summative Evaluation',1,1,NULL,12,0,0),(2,'Connaissance et aptitudes pédagogiques / Educational knowledge and skills',1,2,NULL,8,1,0),(3,'Conaissances et aptitudes pratiques-professionnelles / Professionnal-practical assessment techniques and methods',1,3,NULL,12,1,0);
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_tracking`
--

DROP TABLE IF EXISTS `module_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `module_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `module_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrbci3rmqwqx55qteubxd0qsya` (`module_id`),
  KEY `FKixi93cmaq6kriqekakm5pl1ip` (`user_id`),
  CONSTRAINT `FKixi93cmaq6kriqekakm5pl1ip` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrbci3rmqwqx55qteubxd0qsya` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_tracking`
--

LOCK TABLES `module_tracking` WRITE;
/*!40000 ALTER TABLE `module_tracking` DISABLE KEYS */;
INSERT INTO `module_tracking` VALUES (1,'EDITED THE MODULE','2020-12-10 10:41:07.027000',1,2),(2,'EDITED THE MODULE','2020-12-10 10:44:48.972000',1,2),(3,'EDITED THE MODULE','2020-12-10 10:54:00.723000',1,2),(4,'EDITED THE MODULE','2020-12-10 10:55:02.354000',1,2),(5,'EDITED THE MODULE','2020-12-10 10:56:31.432000',1,2),(6,'EDITED THE MODULE','2020-12-10 11:03:18.476000',1,2),(7,'EDITED THE MODULE','2020-12-10 11:06:12.276000',1,2),(8,'EDITED THE MODULE','2020-12-10 11:07:18.531000',1,2),(9,'EDITED THE MODULE','2020-12-10 11:08:01.265000',1,2),(10,'EDITED THE MODULE','2020-12-10 11:08:12.814000',1,2),(11,'EDITED THE MODULE','2020-12-15 05:26:22.574000',1,2),(12,'EDITED THE MODULE','2020-12-15 05:27:19.824000',1,2);
/*!40000 ALTER TABLE `module_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `office`
--

DROP TABLE IF EXISTS `office`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `office` (
  `id` int NOT NULL AUTO_INCREMENT,
  `abreviation` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `office`
--

LOCK TABLES `office` WRITE;
/*!40000 ALTER TABLE `office` DISABLE KEYS */;
INSERT INTO `office` VALUES (1,'AD','ADAMAOUA'),(2,'CE','CENTRE'),(3,'EN','EXTREME NORD'),(4,'ES','EST'),(5,'LT','LITTORAL'),(6,'NO','NORD'),(7,'NW','NORTH WEST'),(8,'OU','OUEST'),(9,'SU','SUD'),(10,'SW','SOUTH WEST'),(11,'DTR','MINISTERE'),(12,'HQ','LIMBE');
/*!40000 ALTER TABLE `office` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `out_slip`
--

DROP TABLE IF EXISTS `out_slip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `out_slip` (
  `id` int NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL COMMENT 'out slip creation date ',
  `delivery_date` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `reference_number` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL COMMENT 'in slip creation date ',
  `status` int DEFAULT NULL,
  `total` int DEFAULT NULL,
  `office_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2nn2h559fy9i9tv0t0d5b5gdh` (`office_id`),
  CONSTRAINT `FK2nn2h559fy9i9tv0t0d5b5gdh` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `out_slip`
--

LOCK TABLES `out_slip` WRITE;
/*!40000 ALTER TABLE `out_slip` DISABLE KEYS */;
/*!40000 ALTER TABLE `out_slip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `out_slip_tracking`
--

DROP TABLE IF EXISTS `out_slip_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `out_slip_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `out_slip_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi6jyypvbu19bnsj62864vvbft` (`out_slip_id`),
  KEY `FK8828x1u4dr2a4sg7o0p3f0noy` (`user_id`),
  CONSTRAINT `FK8828x1u4dr2a4sg7o0p3f0noy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKi6jyypvbu19bnsj62864vvbft` FOREIGN KEY (`out_slip_id`) REFERENCES `out_slip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `out_slip_tracking`
--

LOCK TABLES `out_slip_tracking` WRITE;
/*!40000 ALTER TABLE `out_slip_tracking` DISABLE KEYS */;
/*!40000 ALTER TABLE `out_slip_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outbox`
--

DROP TABLE IF EXISTS `outbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `outbox` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sending_date_time` datetime(6) DEFAULT NULL,
  `auth` varchar(255) DEFAULT NULL,
  `class` int DEFAULT NULL,
  `coding` varchar(255) DEFAULT NULL,
  `creatorid` varchar(255) DEFAULT NULL,
  `delivery_report` varchar(255) DEFAULT NULL,
  `destination_number` varchar(255) DEFAULT NULL,
  `insert_intodb` datetime(6) DEFAULT NULL,
  `multipart` varchar(255) DEFAULT NULL,
  `priority` int DEFAULT NULL,
  `relative_validity` int DEFAULT NULL,
  `retries` varchar(255) DEFAULT NULL,
  `send_after` time DEFAULT NULL,
  `send_before` time DEFAULT NULL,
  `senderid` varchar(255) DEFAULT NULL,
  `sending_time_out` datetime(6) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `text_decoded` varchar(255) DEFAULT NULL,
  `udh` varchar(255) DEFAULT NULL,
  `updated_indb` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outbox`
--

LOCK TABLES `outbox` WRITE;
/*!40000 ALTER TABLE `outbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `outbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cat_b_computerization_date` datetime DEFAULT NULL COMMENT 'computerization date of the student ofcandidate category B',
  `dob` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `given_name` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `license_num` varchar(15) DEFAULT NULL COMMENT 'student license number ',
  `matricule` varchar(12) DEFAULT NULL COMMENT 'student matricule number ',
  `nic` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `pob` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `nationality_id` int DEFAULT NULL,
  `address_cni` varchar(255) DEFAULT NULL,
  `dob_cni` date DEFAULT NULL,
  `father_cni` varchar(255) DEFAULT NULL,
  `gender_cni` varchar(255) DEFAULT NULL,
  `given_name_cni` varchar(255) DEFAULT NULL,
  `height_cni` varchar(255) DEFAULT NULL,
  `issued_date_cni` date DEFAULT NULL,
  `mother_cni` varchar(255) DEFAULT NULL,
  `pob_cni` varchar(255) DEFAULT NULL,
  `profession_cni` varchar(255) DEFAULT NULL,
  `surname_cni` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9xke7g71eolxbk8gxk08g0ilq` (`nationality_id`),
  FULLTEXT KEY `surname` (`surname`,`given_name`,`pob`),
  CONSTRAINT `FK9xke7g71eolxbk8gxk08g0ilq` FOREIGN KEY (`nationality_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (5,'2008-05-09 00:00:00','1983-01-01','mandenguepaule31011990@gmail.com','M','ERIC TANDAH',NULL,' CE-235058-14','MT00005IP',NULL,'691363621',NULL,'PINYIN',NULL,'PENN',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'2000-05-30 00:00:00','1980-01-26','mandenguepaule31011990@gmail.com','M','EZEKIEL PENN',NULL,' NW-139946-12 ','MT00006IP',NULL,'691363621',NULL,'PINYIN',NULL,'PENN',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,NULL,'1980-08-27',NULL,'M','ERIC PENN',NULL,'NW-107705-09','MT00012IP',NULL,NULL,NULL,'PINYIN',NULL,'NKAM',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(13,'2003-01-12 00:00:00','1988-10-15','mandenguepaule31011990@gmail.com','M','ERIC PENN',NULL,' NW-117931-10 ','MT00013IP',NULL,'691363621',NULL,'BAMENDA',NULL,'MULUH',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(14,'1992-09-14 00:00:00','1948-01-01','moise@gmail.com','M','',NULL,' EN-101192-09 ','MT00014IP',NULL,'695685986',NULL,'Gremari',NULL,'Moussa Moussa',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(15,'2004-11-29 00:00:00','1965-01-01','nnoko@gmail.com','M','MOUSSA',NULL,'NO-118633-14 ','MT00015IP',NULL,'685956858',NULL,'MBILLA GASHIGA',NULL,'SADOU MOUSSA',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(16,'2008-05-09 00:00:00','1983-01-01','mandenguepaule31011990@gmail.com','M','ERIC TANDAH',NULL,'CE-235058-14','MT00016IP',NULL,'691363621',NULL,'PINYIN',NULL,'PENN',31,NULL,NULL,NULL,NULL,NULL,NULL,'2021-03-03',NULL,NULL,NULL,NULL),(17,'2008-12-01 00:00:00','1988-09-19','mandenguepaule31011990@gmail.com','F','MARTIN LUTHER','FR',' LT-100632-09','MT00017IP','142556669','691363621',NULL,'BANKA',NULL,'MOGA KAMENI',31,'cni add','2021-03-09','father','M',' MARTIN LUTHER CNI','1.5 cni','2021-03-23','mother','dob cni','occupc ni','MOGA KAMENI CNI'),(18,'2009-01-29 00:00:00','1975-10-21','moise@gmail.com','M','Luther King',NULL,' LT-100664-09 ','MT00018IP',NULL,'695866551',NULL,'Dibombari',NULL,'Nana Nganle',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(19,'1976-03-01 00:00:00','1955-03-01','mandenguepaule31011990@gmail.com','M','Luther',NULL,'DTT-100602-09','MT00019IP',NULL,'691363621',NULL,'Nkongsamba',NULL,'Fankam Moyo',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,'1998-10-20 00:00:00','1959-01-01','mandenguepaule31011990@gmail.com','M','Luther',NULL,'CE-101582-09 ','MT00020IP',NULL,'691363621',NULL,'Batcham.B',NULL,'Tchoupou Martin',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,'2008-11-26 00:00:00','1987-10-17','mandenguepaule31011990@gmail.com','M','LUTHER LEANDREAU',NULL,'NW-102419-09 ','MT00021IP',NULL,'675855965',NULL,'DSCHANG',NULL,'TEMGOUA',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,'1999-11-15 00:00:00','1959-06-19','mandenguepaule31011990@gmail.com','M','Luther',NULL,' CE-105721-09 ','MT00022MA',NULL,'675859568',NULL,'Ibong',NULL,'Sana',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,'1993-12-12 00:00:00','1970-03-20','mandenguepaule31011990@gmail.com','M','Moussa','EN',' NW-102974-09','MT00023MA','9685585','691363621',NULL,'Banyo',NULL,'Ibrahim',31,'add cni','2021-03-23','mere','M','prenom CNI','1.36','2021-03-16','mere','yaoune','JOB','nomCNI'),(24,'1995-02-02 00:00:00','1968-01-02','mandenguepaule31011990@gmail.com','M','RICHARD PENN',NULL,'NW-123683-10 ','MT00024MA',NULL,'691363621',NULL,'BUCHI',NULL,'MULUH',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,'1987-04-23 00:00:00','1968-03-17','','M','Richard Penn',NULL,'DTT-121769-12','MT00025DP',NULL,'691363621',NULL,'Pinyin',NULL,'Penn',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,'2000-02-04 00:00:00','1975-01-16','alice:@gmail.com','M','Eric Tizi',NULL,' NW-112954-10 ','MT00026DP',NULL,'695825856',NULL,' Pinyin',NULL,'Penn',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,'2004-03-22 00:00:00','1979-02-25','','M','Eric Penn',NULL,'NW-120042-10','MT00027DP',NULL,'691363621',NULL,'Pinyin',NULL,'Tifor',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,'2018-08-04 00:00:00','2000-06-27','','M','ELISEE',NULL,'CE-123459-18 ','MT00028MA',NULL,'677889944',NULL,'YAOUNDE',NULL,'NNOKO NNOKO',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(29,'2009-11-17 00:00:00','1963-06-10','','M','Emmanuel Nnoko',NULL,'SW-108135-10','MT00029MA',NULL,'677770000',NULL,'Ekangte ',NULL,'Ngulle',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(30,'2000-02-08 00:00:00','1977-03-29','HAMMADOU@gmail.com','M','AWALOU','EN','AD-115210-13','MT00030MA','108345876','679223722',NULL,'PITOA FOULBE HAM',NULL,'HAMMADOU',31,'NGAOUNDERE','1977-03-29','papa','M','AWALOU','1.68','2021-06-01','mama','PITOA FOULBE HAM','INCONNUE','HAMMADOU'),(31,'1980-07-07 00:00:00','1960-09-23','MAKON@gmail.com','M','EMMANUEL',NULL,'DTR-133512-13','MT00031MA',NULL,'679223722',NULL,'HAM',NULL,'MAKON ',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(32,'1995-07-07 00:00:00','1975-01-01','Adamou@gmail.com','M','Barka Yadja',NULL,'DTT-111155-10','MT00032MA',NULL,'679223722',NULL,'Garoua Boulaï    ',NULL,'Adamou Ham',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(33,'1988-04-26 00:00:00','1966-04-09','','M','Pascale Long',NULL,'DTT-118910-12','MT00033MA',NULL,'679223722',NULL,'Montpellier',NULL,'Tan Ham Epse Trimbur',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(34,NULL,'1969-03-25',NULL,'M','JULE',NULL,'CE-122138-10','MT00034MA',NULL,NULL,NULL,'KUMBA',NULL,'FOPA',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(35,NULL,'1960-01-01',NULL,'M','David Mbang',NULL,'SW-103401-09','MT00035MA',NULL,NULL,NULL,'Zang Tabi',NULL,'Tabi',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(36,NULL,'1980-06-06',NULL,'M','Walters Teke',NULL,'SW-105088-09','MT00036MA',NULL,NULL,NULL,'Zang Tabi',NULL,'Tabi',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(37,'2003-07-08 00:00:00','1975-02-26','','M','Samuel Ngomba',NULL,'SW-102384-09','MT00037MA',NULL,'679223722',NULL,'Buea',NULL,'Njumbe',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(38,'1984-08-08 00:00:00','1956-03-04','','M','GEORGE',NULL,'SW-102751-09','MT00038MA',NULL,'679223722',NULL,'KUMBO',NULL,'NSAH SHANG',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(39,'1990-10-21 00:00:00','1971-08-15','','M','SAMUEL',NULL,'SW-114039-11','MT00039MA',NULL,'679223722',NULL,'Batoke-Limbe',NULL,'NGOMBA NDIVE',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(40,'1998-09-08 00:00:00','1975-04-06','','M','THOMAS',NULL,'CE-225092-13','MT00040MA',NULL,'679223722',NULL,'YAOUNDE',NULL,'DJON',31,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `print_report`
--

DROP TABLE IF EXISTS `print_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `print_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `card_numbr` varchar(255) DEFAULT NULL,
  `print_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `application_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeedixu3xexuo7as03pv614l4s` (`application_id`),
  CONSTRAINT `FKeedixu3xexuo7as03pv614l4s` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `print_report`
--

LOCK TABLES `print_report` WRITE;
/*!40000 ALTER TABLE `print_report` DISABLE KEYS */;
INSERT INTO `print_report` VALUES (4,'8569','2021-02-24 06:51:36.306000','successful',3),(7,'85986','2021-02-24 08:55:54.930000','successful',4),(8,'856954','2021-02-24 09:27:08.211000','successful',5),(9,'120125','2021-02-24 09:32:47.511000','successful',6),(10,'1256233','2021-02-24 10:06:53.567000','successful',14),(11,'2568','2021-02-25 01:33:39.293000','successful',15);
/*!40000 ALTER TABLE `print_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `process_type`
--

DROP TABLE IF EXISTS `process_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `process_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `abbreviation` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_type`
--

LOCK TABLES `process_type` WRITE;
/*!40000 ALTER TABLE `process_type` DISABLE KEYS */;
INSERT INTO `process_type` VALUES (1,'NT','NEW TRANSCRIPT'),(2,'NC','NEW CERTIFICATE'),(3,'NP','NEW PROFESSIONAL_CARD'),(4,'DT','DUPLICATE OF TRANSCRIPT'),(5,'DC','DUPLICATE OF CERTIFICATE'),(6,'DP','DUPLICATE OF PROFESSIONAL_CARD');
/*!40000 ALTER TABLE `process_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professional_card`
--

DROP TABLE IF EXISTS `professional_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professional_card` (
  `id` int NOT NULL AUTO_INCREMENT,
  `issue_date` datetime(6) DEFAULT NULL,
  `matricule` varchar(255) NOT NULL COMMENT 'matricule',
  `onprocess` int DEFAULT NULL COMMENT ' à valeur > 0 signifie absence de processus sur la carte professionnel et à 0 si non ',
  `archive_id` int DEFAULT NULL,
  `certificate_id` int DEFAULT NULL,
  `on_process` int DEFAULT NULL COMMENT 'à valeur > 0 signifie la presence de un processus sur la carte professionnel et à 0 si non ',
  `is_printed` int DEFAULT NULL COMMENT '1 si imprimer et 0 si non',
  PRIMARY KEY (`id`),
  KEY `FKd7k3gt1y34ydre3jmcgo4tl7n` (`archive_id`),
  KEY `FKag3nf35i6ufeiwb26vvko56uh` (`certificate_id`),
  CONSTRAINT `FKag3nf35i6ufeiwb26vvko56uh` FOREIGN KEY (`certificate_id`) REFERENCES `certificate` (`id`),
  CONSTRAINT `FKd7k3gt1y34ydre3jmcgo4tl7n` FOREIGN KEY (`archive_id`) REFERENCES `archive` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professional_card`
--

LOCK TABLES `professional_card` WRITE;
/*!40000 ALTER TABLE `professional_card` DISABLE KEYS */;
INSERT INTO `professional_card` VALUES (2,NULL,'PC0002',NULL,NULL,1,0,1);
/*!40000 ALTER TABLE `professional_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `region` (
  `id` int NOT NULL AUTO_INCREMENT,
  `abreviation` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region`
--

LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` VALUES (1,'AD','ADAMAOUA'),(2,'CE','CENTRE'),(3,'EN','EXTREME NORD'),(4,'ES','EST'),(5,'LT','LITTORAL'),(6,'NO','NORD'),(7,'NW','NORTH WEST'),(8,'OU','OUEST'),(9,'SU','SUD'),(10,'SW','SOUTH WEST');
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE MANAGE ARCHIVE','ROLE_MANAGE_ARCHIVE'),(2,'ROLE VIEW ARCHIVE','ROLE_VIEW_ARCHIVE'),(3,'ROLE ARCHIVE CONTROLLER','ROLE_ARCHIVE_CONTROLLER'),(4,'ROLE MANAGE PV','ROLE_MANAGE_PV'),(5,'ROLE PV CONTROLLER','ROLE_PV_CONTROLLER'),(6,'ROLE VIEW PV','ROLE_VIEW_PV'),(7,'ROLE MANAGE CANDIDATE','ROLE_MANAGE_CANDIDATE'),(8,'ROLE VIEW INSLIP','ROLE_VIEW_INSLIP'),(9,'ROLE INSLIP CONTROLLER','ROLE_INSLIP_CONTROLLER'),(10,'ROLE VIEW OUTSLIP','ROLE_VIEW_OUTSLIP'),(11,'ROLE MANAGE OUTSLIP','ROLE_MANAGE_OUTSLIP'),(12,'ROLE VIEW RECORD','ROLE_VIEW_RECORD'),(13,'ROLE PROCESS FILE','ROLE_PROCESS_FILE'),(14,'ROLE CONFIRM PROCESS','ROLE_CONFIRM_PROCESS'),(15,'ROLE PRINT APPLICATION','ROLE_PRINT_APPLICATION'),(16,'ROLE AUTHORIZE REPRINT','ROLE_AUTHORIZE_REPRINT'),(17,'ROLE MANAGE INSLIP','ROLE_MANAGE_INSLIP'),(18,'ROLE ADMIN','ROLE_ADMIN'),(19,'ROLE MANAGE ALL OFFICE','ROLE_MANAGE_ALL_OFFICE'),(20,'ROLE OPEN INSLIP','ROLE_OPEN_INSLIP'),(21,'ROLE VIEW USERS','ROLE_VIEW_USERS'),(22,'ROLE MANAGE ELIGIBLE CENTER','ROLE_MANAGE_ELIGIBLE_CENTER'),(23,'ROLE CLOSE ELIGIBLE CENTER','ROLE_CLOSE_ELIGIBLE_CENTER'),(24,'ROLE VIEW STATISTICS','ROLE_VIEW_STATISTICS'),(25,'ROLE VIEW FILE','ROLE_VIEW_FILE'),(26,'ROLE GENERATE PV','ROLE_GENERATE_PV'),(27,'ROLE MANAGE STUDENTS UNDER TRAINING','ROLE_MANAGE_STUDENTS_UNDER_TRAINING'),(28,'ROLE VIEW PV OF EXPERTS EXAM','ROLE_VIEW_PV_EXPERTS_EXAM'),(29,'ROLE MANAGE CANDIDATES TRANSCRIPTS','ROLE_MANAGE_CANDIDATES_TRANSCRIPT'),(30,'ROLE MANAGE CANDIDATES COURSES AND MODULES','ROLE_MANAGE_COURSES_AND_MODULES'),(31,'ROLE VIEW COURSES AND MODULES','ROLE_VIEW_COURSES_AND_MODULES'),(32,'ROLE TO MANAGE TRAINING CENTERS','ROLE_MANAGE_TRAINNING_CENTERS'),(33,'ROLE TO VIEW TRAINING CENTERS','ROLE_VIEW_TRAINNING_CENTERS'),(34,'ROLE PRINT TRANSCRIPT','ROLE_PRINT_TRANSCRIPT'),(35,'ROLE MANAGE APPLICANTS','ROLE_MANAGE_APPLICANT'),(36,'ROLE PV CONTROLLER STUDENT','ROLE_PV_CONTROLLER_STUDENT');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `speciality`
--

DROP TABLE IF EXISTS `speciality`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `speciality` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `abbreviation` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `name_in_english` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `speciality`
--

LOCK TABLES `speciality` WRITE;
/*!40000 ALTER TABLE `speciality` DISABLE KEYS */;
INSERT INTO `speciality` VALUES (1,'MAE','Moniteur Auto Ecole','Driving School Instructor'),(2,'IPCSR','Inspecteur du Permis de Conduire et de la Securite Routiere','Safety Road And Driving License Inspector'),(3,'DPCSR','Delegues du Permis de Conduire et de la Securite Routiere','Safety Road And Driving License Delegate');
/*!40000 ALTER TABLE `speciality` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `speciality_prerequisite`
--

DROP TABLE IF EXISTS `speciality_prerequisite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `speciality_prerequisite` (
  `id` int NOT NULL AUTO_INCREMENT,
  `duration_in_months` int NOT NULL COMMENT ' Number of months required for training at specified speciality',
  `entry_diploma` varchar(255) NOT NULL COMMENT 'Entry Diploma',
  `speciality_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs3r7wkq020n3wuhl0wg80hc6c` (`speciality_id`),
  CONSTRAINT `FKs3r7wkq020n3wuhl0wg80hc6c` FOREIGN KEY (`speciality_id`) REFERENCES `speciality` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `speciality_prerequisite`
--

LOCK TABLES `speciality_prerequisite` WRITE;
/*!40000 ALTER TABLE `speciality_prerequisite` DISABLE KEYS */;
/*!40000 ALTER TABLE `speciality_prerequisite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `computerization_date` datetime DEFAULT NULL COMMENT 'computerization date of the student',
  `reference_num` varchar(255) DEFAULT NULL COMMENT 'student reference number ',
  `person_id` int DEFAULT NULL,
  `speciality_id` bigint DEFAULT NULL,
  `training_center_id` int DEFAULT NULL,
  `diplome` varchar(100) DEFAULT NULL COMMENT 'BACC/GCE-O-LEVEL, CAPEC, BEPC/CAP/GCE-A-LEVEL ',
  PRIMARY KEY (`id`),
  KEY `FKnwsufvlvlnsxqv60ltj06bbfx` (`person_id`),
  KEY `FKt434hun0i4tv58xnfvkbttk7v` (`speciality_id`),
  KEY `FKt1sfhdfmoif0il7uxt01a3lrk` (`training_center_id`),
  CONSTRAINT `FKnwsufvlvlnsxqv60ltj06bbfx` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `FKt1sfhdfmoif0il7uxt01a3lrk` FOREIGN KEY (`training_center_id`) REFERENCES `training_center` (`id`),
  CONSTRAINT `FKt434hun0i4tv58xnfvkbttk7v` FOREIGN KEY (`speciality_id`) REFERENCES `speciality` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (5,'2021-02-24 00:00:00','TC00005',5,1,1,'BACC'),(6,'2021-02-24 00:00:00','TC00006',6,1,1,'BACC'),(7,'2021-02-26 00:00:00','TC00007',13,1,1,'BACC'),(8,'2021-02-26 00:00:00','TC00008',14,1,1,'BACC'),(9,'2021-02-26 00:00:00','TC00009',15,1,1,'CAPEC'),(10,'2021-02-27 00:00:00','TC00010',16,1,1,'BACC'),(11,'2021-03-04 00:00:00','TC00011',17,1,1,'BACC'),(12,'2021-03-04 00:00:00','TC00012',18,1,1,'BACC'),(13,'2021-03-04 00:00:00','TC00013',19,1,1,'BACC'),(14,'2021-03-04 00:00:00','TC00014',20,1,1,'BACC'),(15,'2021-03-04 00:00:00','TC00015',21,1,1,'BACC'),(16,'2021-03-04 00:00:00','TC00016',22,1,1,'BACC'),(17,'2021-03-05 00:00:00','TC00017',23,1,1,'BACC'),(18,'2021-03-05 00:00:00','TC00018',24,1,1,'BACC'),(19,'2021-04-01 00:00:00','TC00019',6,2,1,'BACC'),(20,'2021-04-01 00:00:00','TC00019',6,3,1,'BACC'),(21,'2021-04-01 00:00:00','TC00021',25,2,1,'BACC'),(22,'2021-04-01 00:00:00','TC00021',25,3,1,'BACC'),(23,'2021-04-01 00:00:00','TC00023',12,3,1,'BACC'),(24,'2021-04-01 00:00:00','TC00024',26,3,1,'BACC'),(25,'2021-04-01 00:00:00','TC00025',27,3,1,'BACC'),(26,'2021-04-01 00:00:00','TC00026',16,3,1,'BACC'),(27,'2021-04-29 00:00:00','TC00027',28,1,1,'BEPEC'),(28,'2021-04-29 00:00:00','TC00028',29,1,1,'BEPEC'),(29,'2021-06-01 00:00:00','TC00029',30,1,1,'29.pdf'),(30,'2021-06-01 00:00:00','TC00030',31,1,1,'30.pdf'),(31,'2021-06-01 00:00:00','TC00031',32,1,1,'31.pdf'),(32,'2021-06-01 00:00:00','TC00032',33,1,1,'32.pdf'),(33,'2021-06-16 00:00:00','TC00033',37,1,1,'33.pdf'),(34,'2021-06-16 00:00:00','TC00034',38,1,1,'34.pdf'),(35,'2021-06-16 00:00:00','TC00035',39,1,1,'35.pdf'),(36,'2021-06-16 00:00:00','TC00036',40,1,1,'36.pdf');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_qualification`
--

DROP TABLE IF EXISTS `student_qualification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_qualification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `entry_certificate_name` varchar(255) DEFAULT NULL,
  `issued_date` date DEFAULT NULL,
  `issued_place` varchar(255) DEFAULT NULL,
  `diplome_option` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_qualification`
--

LOCK TABLES `student_qualification` WRITE;
/*!40000 ALTER TABLE `student_qualification` DISABLE KEYS */;
INSERT INTO `student_qualification` VALUES (1,'BACC','2021-03-23','yaounde','C'),(2,'BACC','2021-03-23','yaounde','C'),(3,'BACC','2021-03-23','yaounde','C'),(4,'bacc','2021-03-15','yaounde','c'),(5,'GCE','2021-06-01','YAOUNDE','MAE');
/*!40000 ALTER TABLE `student_qualification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_session`
--

DROP TABLE IF EXISTS `student_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_session` (
  `id` int NOT NULL AUTO_INCREMENT,
  `count_file` int DEFAULT NULL,
  `photo_and_signature` varchar(255) DEFAULT NULL,
  `processed` int DEFAULT NULL COMMENT 'tells if the candidate session is already processed 1 if yes and 0 if not ',
  `pv_number` int DEFAULT NULL COMMENT 'pv number of the candidate session ',
  `reason_for_reject` varchar(255) DEFAULT NULL,
  `eligible_center_id` int DEFAULT NULL,
  `student_id` int DEFAULT NULL,
  `student_session_status_id` int DEFAULT NULL,
  `final_result` float DEFAULT '0' COMMENT 'la moyenne finale obtenue par le candidat apres deliberation ',
  `student_qualification_id` int DEFAULT NULL,
  `speciality_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqt7jl4dqc1pgo00w1ug0n9g2c` (`eligible_center_id`),
  KEY `FKnt2xjx1di178m4d8f326d4s01` (`student_id`),
  KEY `FKd3tqgtsuwckp8t8734k3g2p2b` (`student_session_status_id`),
  KEY `FKjdwre5ervfv2sen4ag74oseq` (`student_qualification_id`),
  KEY `FKb9idglg9ur2vog7g058mac3uc` (`speciality_id`),
  CONSTRAINT `FKb9idglg9ur2vog7g058mac3uc` FOREIGN KEY (`speciality_id`) REFERENCES `speciality` (`id`),
  CONSTRAINT `FKd3tqgtsuwckp8t8734k3g2p2b` FOREIGN KEY (`student_session_status_id`) REFERENCES `student_session_status` (`id`),
  CONSTRAINT `FKjdwre5ervfv2sen4ag74oseq` FOREIGN KEY (`student_qualification_id`) REFERENCES `student_qualification` (`id`),
  CONSTRAINT `FKnt2xjx1di178m4d8f326d4s01` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FKqt7jl4dqc1pgo00w1ug0n9g2c` FOREIGN KEY (`eligible_center_id`) REFERENCES `eligible_center` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_session`
--

LOCK TABLES `student_session` WRITE;
/*!40000 ALTER TABLE `student_session` DISABLE KEYS */;
INSERT INTO `student_session` VALUES (3,0,'3.jpg',0,1,NULL,1,5,4,16,NULL,NULL),(4,0,'4.jpg',0,2,NULL,1,6,4,19.75,NULL,NULL),(5,0,'5.jpg',0,1,NULL,2,7,4,0,NULL,NULL),(6,0,'6.jpg',0,0,NULL,2,8,5,0,NULL,NULL),(7,0,NULL,0,0,'bad',2,9,3,0,NULL,NULL),(8,0,'8.jpg',0,0,NULL,3,10,6,0,NULL,NULL),(9,0,'9.jpg',0,2,NULL,3,11,4,0,3,NULL),(10,0,'10.jpg',0,5,NULL,3,14,4,0,NULL,NULL),(11,0,'11.jpg',0,1,'wrong POB',3,17,4,0,4,NULL),(12,0,'12.jpg',0,3,NULL,3,19,4,0,NULL,NULL),(13,0,'13.jpg',0,4,NULL,3,21,4,0,NULL,NULL),(14,0,'14.jpg',0,0,NULL,3,29,5,0,5,1),(15,0,NULL,0,0,'WRONG DOB',3,30,3,0,NULL,1),(16,0,NULL,0,0,'WORNG DOB',3,31,3,0,NULL,1),(17,0,'17.jpg',0,0,NULL,3,32,7,0,NULL,1),(18,0,NULL,0,0,NULL,2,33,1,0,NULL,NULL),(19,0,NULL,0,0,NULL,2,36,1,0,NULL,NULL);
/*!40000 ALTER TABLE `student_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_session_file`
--

DROP TABLE IF EXISTS `student_session_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_session_file` (
  `id` int NOT NULL AUTO_INCREMENT,
  `deleted` int DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `student_session_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrdwdfwqlh3vrvx6mxkf44tyqp` (`student_session_id`),
  CONSTRAINT `FKrdwdfwqlh3vrvx6mxkf44tyqp` FOREIGN KEY (`student_session_id`) REFERENCES `student_session` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_session_file`
--

LOCK TABLES `student_session_file` WRITE;
/*!40000 ALTER TABLE `student_session_file` DISABLE KEYS */;
INSERT INTO `student_session_file` VALUES (1,0,'3.pdf',3),(2,0,'4.pdf',4),(3,0,'5.pdf',5),(4,0,'6.pdf',6),(5,0,'8.pdf',8),(6,0,'14.pdf',14),(7,0,'9.pdf',9),(8,0,'10.pdf',10),(9,0,'12.pdf',12),(10,0,'13.pdf',13),(11,0,'17.pdf',17),(12,0,'8.pdf',8),(13,0,'9.pdf',9),(14,0,'9.pdf',9),(15,0,'11.pdf',11);
/*!40000 ALTER TABLE `student_session_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_session_status`
--

DROP TABLE IF EXISTS `student_session_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_session_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_session_status`
--

LOCK TABLES `student_session_status` WRITE;
/*!40000 ALTER TABLE `student_session_status` DISABLE KEYS */;
INSERT INTO `student_session_status` VALUES (1,'PRESENTED'),(2,'APPROVED'),(3,'REJECTED'),(4,'SUCCEED'),(5,'FAILED'),(6,'ABSENT'),(7,'DEMISSIONAIRE');
/*!40000 ALTER TABLE `student_session_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_session_tracking`
--

DROP TABLE IF EXISTS `student_session_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_session_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `student_session_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe2q148o3bqx29wfcr6p2htpd2` (`student_session_id`),
  KEY `FKj5898pqwqvrl52mo15satmx87` (`user_id`),
  CONSTRAINT `FKe2q148o3bqx29wfcr6p2htpd2` FOREIGN KEY (`student_session_id`) REFERENCES `student_session` (`id`),
  CONSTRAINT `FKj5898pqwqvrl52mo15satmx87` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_session_tracking`
--

LOCK TABLES `student_session_tracking` WRITE;
/*!40000 ALTER TABLE `student_session_tracking` DISABLE KEYS */;
INSERT INTO `student_session_tracking` VALUES (4,'STUDENT is Presented in this STUDENT SESSION','2021-02-24 01:38:03.308000',3,2),(5,'STUDENT is Presented in this STUDENT SESSION','2021-02-24 01:49:15.124000',4,2),(6,'APPROVED','2021-02-24 02:00:58.618000',3,2),(7,'APPROVED','2021-02-24 02:02:29.644000',4,2),(8,'MARK AS PASS','2021-02-24 02:15:40.821000',3,2),(9,'MARK AS PASS','2021-02-24 02:15:57.163000',4,2),(10,'STUDENT is Presented in this STUDENT SESSION','2021-02-26 04:56:34.583000',5,2),(11,'STUDENT is Presented in this STUDENT SESSION','2021-02-26 04:56:41.539000',6,2),(12,'STUDENT is Presented in this STUDENT SESSION','2021-02-26 04:56:48.670000',7,2),(13,'APPROVED','2021-02-26 04:59:51.194000',5,2),(14,'APPROVED','2021-02-26 05:01:22.289000',6,2),(15,'Reject the Student for this STUDENT SESSION\n [ REASON ::]bad certificate','2021-02-26 05:01:41.756000',7,2),(16,'STUDENT is rejected','2021-02-26 05:01:41.948000',7,2),(17,'MARK AS PASS','2021-02-26 05:02:56.019000',5,2),(18,'MARKED AS FAIL','2021-02-26 05:03:04.948000',6,2),(19,'SEND FROM REJECT','2021-02-26 05:03:56.384000',7,2),(20,'Reject the Student for this STUDENT SESSION\n [ REASON ::]bad','2021-02-26 05:15:03.830000',7,2),(21,'STUDENT is rejected','2021-02-26 05:15:04.112000',7,2),(22,'STUDENT is Presented in this STUDENT SESSION','2021-02-27 05:31:47.146000',8,2),(23,'STUDENT is Presented in this STUDENT SESSION','2021-03-04 01:59:46.260000',9,2),(24,'STUDENT is Presented in this STUDENT SESSION','2021-03-05 00:28:07.042000',10,2),(25,'STUDENT is Presented in this STUDENT SESSION','2021-03-05 00:33:32.816000',11,2),(26,'STUDENT is Presented in this STUDENT SESSION','2021-04-01 09:23:42.978000',12,2),(27,'STUDENT is Presented in this STUDENT SESSION','2021-04-01 10:56:13.185000',13,2),(28,'APPROVED','2021-04-29 06:09:29.965000',8,5),(29,'STUDENT is Presented in this STUDENT SESSION','2021-06-01 03:25:02.074000',14,5),(30,'STUDENT is Presented in this STUDENT SESSION','2021-06-01 03:35:33.420000',15,5),(31,'STUDENT is Presented in this STUDENT SESSION','2021-06-01 03:35:46.423000',16,5),(32,'STUDENT is Presented in this STUDENT SESSION','2021-06-01 03:37:41.200000',17,5),(33,'APPROVED','2021-06-01 03:43:49.297000',14,5),(34,'APPROVED','2021-06-01 03:45:45.783000',9,5),(35,'APPROVED','2021-06-01 03:46:19.308000',10,5),(36,'Reject the Student for this STUDENT SESSION\n [ REASON ::]wrong POB','2021-06-01 03:46:49.859000',11,5),(37,'STUDENT is rejected','2021-06-01 03:46:50.071000',11,5),(38,'APPROVED','2021-06-01 03:47:59.554000',12,5),(39,'APPROVED','2021-06-01 03:48:28.662000',13,5),(40,'Reject the Student for this STUDENT SESSION\n [ REASON ::]WRONG DOB','2021-06-01 03:48:44.413000',15,5),(41,'STUDENT is rejected','2021-06-01 03:48:44.589000',15,5),(42,'Reject the Student for this STUDENT SESSION\n [ REASON ::]WORNG DOB','2021-06-01 03:48:58.522000',16,5),(43,'STUDENT is rejected','2021-06-01 03:48:58.730000',16,5),(44,'APPROVED','2021-06-01 03:49:25.693000',17,5),(45,'SEND FROM APPROVED','2021-06-01 03:50:33.340000',8,5),(46,'APPROVED','2021-06-01 03:51:57.921000',8,5),(47,'MARK AS PASS','2021-06-01 03:52:03.091000',8,5),(48,'MARK AS PASS','2021-06-01 03:52:09.027000',9,5),(49,'MARK AS PASS','2021-06-01 03:52:12.415000',10,5),(50,'MARK AS PASS','2021-06-01 03:52:15.898000',12,5),(51,'MARK AS PASS','2021-06-01 03:52:19.130000',13,5),(52,'MARKED AS FAIL','2021-06-01 03:52:23.213000',14,5),(53,'STUDENT is mark-as-demissionnaire','2021-06-01 03:52:27.256000',17,5),(54,'SEND FROM PASS TO APPROVED','2021-06-01 03:52:49.126000',8,5),(55,'STUDENT is mark-as-absent','2021-06-01 03:52:55.418000',8,5),(56,'STUDENT is Presented in this STUDENT SESSION','2021-07-13 03:18:25.559000',18,5),(57,'STUDENT is Presented in this STUDENT SESSION','2021-07-13 03:18:34.409000',19,5),(58,'SEND FROM PASS TO APPROVED','2021-07-13 03:21:30.552000',9,5),(59,'SEND FROM APPROVED','2021-07-13 03:21:38.799000',9,5),(60,'APPROVED','2021-07-13 03:33:58.991000',9,5),(61,'SEND FROM APPROVED','2021-07-13 03:36:55.606000',9,5),(62,'SEND FROM REJECT','2021-07-13 03:39:33.695000',11,5),(63,'APPROVED','2021-07-13 03:42:15.682000',9,5),(64,'APPROVED','2021-07-13 07:12:30.012000',11,5),(65,'MARK AS PASS','2021-07-13 07:13:03.740000',9,5),(66,'MARK AS PASS','2021-07-13 07:13:07.808000',11,5);
/*!40000 ALTER TABLE `student_session_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_tracking`
--

DROP TABLE IF EXISTS `student_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `student_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt9jap5xo6w4rlrb75o7plq84o` (`student_id`),
  KEY `FK4ooeapbvwa5qy9hwn5wjdraag` (`user_id`),
  CONSTRAINT `FK4ooeapbvwa5qy9hwn5wjdraag` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKt9jap5xo6w4rlrb75o7plq84o` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_tracking`
--

LOCK TABLES `student_tracking` WRITE;
/*!40000 ALTER TABLE `student_tracking` DISABLE KEYS */;
INSERT INTO `student_tracking` VALUES (4,'REGISTERED THE STUDENT','2021-02-24 00:46:05.923000',5,2),(5,'REGISTERED THE STUDENT','2021-02-24 01:48:15.364000',6,2),(6,'REGISTERED THE STUDENT','2021-02-26 04:52:16.832000',7,2),(7,'REGISTERED THE STUDENT','2021-02-26 04:53:37.469000',8,2),(8,'REGISTERED THE STUDENT','2021-02-26 04:55:52.904000',9,2),(9,'REGISTERED THE STUDENT','2021-02-27 05:02:24.936000',10,2),(10,'REGISTERED THE STUDENT','2021-03-04 01:53:27.113000',11,2),(11,'REGISTERED THE STUDENT','2021-03-04 04:18:11.642000',12,2),(12,'REGISTERED THE STUDENT','2021-03-04 04:31:02.072000',13,2),(13,'REGISTERED THE STUDENT','2021-03-04 04:37:00.438000',14,2),(14,'REGISTERED THE STUDENT','2021-03-04 04:43:27.606000',15,2),(15,'REGISTERED THE STUDENT','2021-03-04 04:46:17.109000',16,2),(16,'REGISTERED THE STUDENT','2021-03-05 00:25:31.727000',17,2),(17,'REGISTERED THE STUDENT','2021-03-05 08:46:35.687000',18,4),(18,'REGISTERED THE STUDENT','2021-04-29 06:03:41.677000',27,5),(19,'REGISTERED THE STUDENT','2021-04-29 06:26:12.839000',28,5),(20,'REGISTERED THE STUDENT','2021-06-01 03:16:27.666000',29,5),(21,'REGISTERED THE STUDENT','2021-06-01 03:17:46.827000',30,5),(22,'REGISTERED THE STUDENT','2021-06-01 03:19:47.740000',31,5),(23,'REGISTERED THE STUDENT','2021-06-01 03:36:38.742000',32,5),(24,'REGISTERED THE STUDENT','2021-06-16 07:25:36.153000',33,5),(25,'REGISTERED THE STUDENT','2021-06-16 07:26:35.259000',34,5),(26,'REGISTERED THE STUDENT','2021-06-16 07:27:08.908000',35,5),(27,'REGISTERED THE STUDENT','2021-06-16 07:27:41.929000',36,5);
/*!40000 ALTER TABLE `student_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_district`
--

DROP TABLE IF EXISTS `sub_district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_district` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT 'name of the subdistrict ',
  `district_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlqjwe7n0ji72p33oq5sx00e34` (`district_id`),
  CONSTRAINT `FKlqjwe7n0ji72p33oq5sx00e34` FOREIGN KEY (`district_id`) REFERENCES `district` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_district`
--

LOCK TABLES `sub_district` WRITE;
/*!40000 ALTER TABLE `sub_district` DISABLE KEYS */;
/*!40000 ALTER TABLE `sub_district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_center`
--

DROP TABLE IF EXISTS `training_center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `training_center` (
  `id` int NOT NULL AUTO_INCREMENT,
  `abbreviation` varchar(2) DEFAULT NULL COMMENT 'training school abbreviation ',
  `authorisation_number` varchar(255) DEFAULT NULL COMMENT 'training school authorisation number ',
  `creation_date` datetime DEFAULT NULL COMMENT 'training school creation date',
  `founder` varchar(255) DEFAULT NULL COMMENT 'training school founder name',
  `location` varchar(255) DEFAULT NULL COMMENT 'training school location ',
  `name` varchar(255) DEFAULT NULL COMMENT 'boad driving school name',
  `owner` varchar(255) DEFAULT NULL COMMENT 'training school owner name',
  `status` int DEFAULT NULL COMMENT '0: suspended, 1:approved',
  `division_id` int DEFAULT NULL,
  `max_student` int DEFAULT '0' COMMENT 'maximum number of students in a training school',
  `exam_center_id` int DEFAULT NULL,
  `owner_phone_number` varchar(255) DEFAULT NULL COMMENT 'training school owner phone number',
  PRIMARY KEY (`id`),
  KEY `FKqv3s2es12v7e8f48hpepiv2qg` (`division_id`),
  KEY `FKnc19s5pfhk6bgb5qwb77spj8h` (`exam_center_id`),
  CONSTRAINT `FKnc19s5pfhk6bgb5qwb77spj8h` FOREIGN KEY (`exam_center_id`) REFERENCES `exam_center` (`id`),
  CONSTRAINT `FKqv3s2es12v7e8f48hpepiv2qg` FOREIGN KEY (`division_id`) REFERENCES `division` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_center`
--

LOCK TABLES `training_center` WRITE;
/*!40000 ALTER TABLE `training_center` DISABLE KEYS */;
INSERT INTO `training_center` VALUES (1,'TC','CAMETFOLD/256224/457558','2020-11-11 00:00:00','MANDENGUE paule aggy','YAOUNDE ESSOS','NNOKO TRAINING CENTER','MANDENGUE paule aggy',1,1,38,NULL,'MANDENGUE paule aggy'),(2,'BT','GDEBGUEIIE58996','2020-11-26 00:00:00','MBARGA JULES','BUEA','BUEA AE TRAINNING','MBARGA JULES',1,1,45,NULL,NULL),(4,NULL,NULL,'2021-06-17 01:01:03','MR TC','LIMBE','TC','MR TC',1,53,0,NULL,'679223722');
/*!40000 ALTER TABLE `training_center` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_center_tracking`
--

DROP TABLE IF EXISTS `training_center_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `training_center_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `training_center_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeue9pho0xaif63q1rnn3wf3dw` (`course_id`),
  KEY `FK6fs2hfp3vboggfli20t8xdj5h` (`user_id`),
  KEY `FKtkak3b9mo2iyesw4n82vw3d3f` (`training_center_id`),
  CONSTRAINT `FK6fs2hfp3vboggfli20t8xdj5h` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKeue9pho0xaif63q1rnn3wf3dw` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `FKtkak3b9mo2iyesw4n82vw3d3f` FOREIGN KEY (`training_center_id`) REFERENCES `training_center` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_center_tracking`
--

LOCK TABLES `training_center_tracking` WRITE;
/*!40000 ALTER TABLE `training_center_tracking` DISABLE KEYS */;
INSERT INTO `training_center_tracking` VALUES (9,'CHANGED THE TRAINING CENTER MAXIMUM CAPACITY VALUE FROM 0 STUDENTS TO -2 STUDENTS','2020-12-18 04:58:50.562000',NULL,2,1),(10,'EDITED THE TRAINING CENTER ','2020-12-18 05:01:39.499000',NULL,2,1),(11,'CHANGED THE TRAINING CENTER MAXIMUM CAPACITY VALUE FROM -2 STUDENTS TO 2 STUDENTS','2020-12-18 05:07:13.949000',NULL,2,1),(12,'EDITED THE TRAINING CENTER ','2020-12-18 05:07:14.099000',NULL,2,1),(13,'EDITED THE TRAINING CENTER ','2020-12-18 05:10:19.573000',NULL,2,1),(14,'EDITED THE TRAINING CENTER ','2020-12-18 05:18:58.930000',NULL,2,1),(15,'EDITED THE TRAINING CENTER ','2020-12-18 05:54:39.285000',NULL,2,1),(17,'REGISTERED THE TRAINING CENTER','2021-06-17 01:01:03.005000',NULL,5,4),(18,'EDITED THE TRAINING CENTER ','2021-07-13 08:58:59.796000',NULL,5,1);
/*!40000 ALTER TABLE `training_center_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transcript`
--

DROP TABLE IF EXISTS `transcript`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transcript` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_note` float DEFAULT NULL COMMENT 'mark of the student for a corresponding course ',
  `course_id` int DEFAULT NULL,
  `student_session_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf0jkjskpe6wb2t9t7ilj84qok` (`course_id`),
  KEY `FKidprl2x4y37bmeds1ie7xa16e` (`student_session_id`),
  CONSTRAINT `FKf0jkjskpe6wb2t9t7ilj84qok` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `FKidprl2x4y37bmeds1ie7xa16e` FOREIGN KEY (`student_session_id`) REFERENCES `student_session` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transcript`
--

LOCK TABLES `transcript` WRITE;
/*!40000 ALTER TABLE `transcript` DISABLE KEYS */;
INSERT INTO `transcript` VALUES (22,12,1,3),(23,11,2,3),(24,14,3,3),(25,15,4,3),(26,13,5,3),(27,15,6,3),(28,18,9,3),(29,19,10,3),(30,20,11,3),(31,12,12,3),(32,10.5,13,3),(33,15.96,14,3),(34,18.96,15,3),(35,18,16,3),(36,12.5,17,3),(37,17.5,19,3),(38,16,20,3),(39,18,22,3),(40,11,23,3),(41,14,24,3),(42,11,1,4),(43,10,2,4),(44,9,3,4),(45,19,4,4),(46,20,5,4),(47,19,6,4),(48,18.5,9,4),(49,19,10,4),(50,18,11,4),(51,19,12,4),(52,19.5,13,4),(53,18.5,14,4),(54,19.75,15,4),(55,20,16,4),(56,20,17,4),(57,20,19,4),(58,15,20,4),(59,17.5,22,4),(60,19,23,4),(61,20,24,4),(62,12,1,6),(63,11,2,6),(64,14,3,6),(65,18,4,6),(66,16,5,6),(67,11,6,6),(68,10,9,6),(69,8,10,6),(70,4,11,6),(71,12,12,6),(72,15,13,6),(73,14,14,6),(74,13,15,6),(75,12,16,6),(76,10,17,6),(77,5,19,6),(78,12,20,6),(79,12,22,6),(80,11,23,6),(81,12,24,6),(82,12,1,5),(83,10,2,5),(84,13,3,5),(85,13,4,5),(86,9,5,5),(87,5,6,5),(88,19,9,5),(89,20,10,5),(90,11,11,5),(91,13,12,5),(92,15,13,5),(93,17,14,5),(94,18,15,5),(95,11,16,5),(96,13,17,5),(97,15,19,5),(98,11,20,5),(99,14,22,5),(100,12,23,5),(101,10,24,5);
/*!40000 ALTER TABLE `transcript` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transcript_tracking`
--

DROP TABLE IF EXISTS `transcript_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transcript_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `operation` text,
  `operation_date` datetime(6) DEFAULT NULL,
  `transcript_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKod5lbb4ji7p0fkulx5gpup2vx` (`transcript_id`),
  KEY `FKmt0xfbhollymv7m095jseppbf` (`user_id`),
  CONSTRAINT `FKmt0xfbhollymv7m095jseppbf` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKod5lbb4ji7p0fkulx5gpup2vx` FOREIGN KEY (`transcript_id`) REFERENCES `transcript` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transcript_tracking`
--

LOCK TABLES `transcript_tracking` WRITE;
/*!40000 ALTER TABLE `transcript_tracking` DISABLE KEYS */;
/*!40000 ALTER TABLE `transcript_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `active` int NOT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` int NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `password_requested_at` datetime(6) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `pob` varchar(255) DEFAULT NULL,
  `register_on` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_expired` int NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `group_id` bigint DEFAULT NULL,
  `office_id` int DEFAULT NULL,
  `training_center_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5ixd8ou7x5sln7b00u8qpf7il` (`group_id`),
  KEY `FKmrlaeiw7ten8d3sjejpif4tyf` (`office_id`),
  KEY `FKtdq4b8mj7w05wvss6vktj4jch` (`training_center_id`),
  CONSTRAINT `FK5ixd8ou7x5sln7b00u8qpf7il` FOREIGN KEY (`group_id`) REFERENCES `user_group` (`id`),
  CONSTRAINT `FKmrlaeiw7ten8d3sjejpif4tyf` FOREIGN KEY (`office_id`) REFERENCES `office` (`id`),
  CONSTRAINT `FKtdq4b8mj7w05wvss6vktj4jch` FOREIGN KEY (`training_center_id`) REFERENCES `training_center` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,0,NULL,'mandenguepaule31011990@gmail.com',0,'MANDENGUE',NULL,'paule.jpeg',NULL,'paule aggy','$2a$10$8skxjSy79oOYdxjXkmN.weO71yFTrHjXPKgLx3smlSTo4JQG9ntCG','2021-03-17 03:34:01.068000',NULL,NULL,NULL,'dcb88274p410bp44f5p95eepc11e51f1719f2',0,'pauleAggy',5,1,1),(3,0,NULL,'valentinelle19920@yahoo.fr',0,'MINISTRY',NULL,'paule.jpeg',NULL,'STAFF','$2a$10$8skxjSy79oOYdxjXkmN.weO71yFTrHjXPKgLx3smlSTo4JQG9ntCG',NULL,NULL,NULL,NULL,NULL,0,'ministry',1,1,NULL),(4,1,'1990-03-29 00:00:00.000000','training@gmail.com',0,'training','F',NULL,NULL,'training','$2a$10$SB4XhxALAHHgnEGqG/mfsO.3d4PEyobxvydBfrGS8Ui4oKX8KzUv6',NULL,'691363621','yaounde','2021-03-05 08:41:46.927000',NULL,0,'trainingCenter',9,12,1),(5,1,'2021-04-01 00:00:00.000000','rosedinga@gmail.com',0,'ADMIN','M',NULL,NULL,'NNOKO','$2a$10$RcPfWwltG3oIiNBp1lf8.uxMYcrmBSUQ8JJudK/R10FF9HT/g/21q','2022-10-10 20:03:28.279000','679223722','NYASOSO','2022-11-16 10:59:02.452000','2114c3efpc889p40c7p99d5pce406311ceec5',0,'Nnoko1',5,1,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group`
--

LOCK TABLES `user_group` WRITE;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
INSERT INTO `user_group` VALUES (1,'Group MINT'),(2,'GROUP DELEGATE'),(3,'GROUP EDITING STAFF'),(4,'GROUP CONTROLLER'),(5,'GROUP ADMIN'),(7,'GROUP DEC'),(8,'GROUP MANAGEMENT'),(9,'TRAINING CENTERS');
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` int NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (2,1),(5,1),(2,2),(5,2),(2,3),(5,3),(2,4),(5,4),(2,5),(5,5),(2,6),(5,6),(2,7),(4,7),(5,7),(2,8),(5,8),(2,9),(5,9),(2,10),(5,10),(2,11),(5,11),(2,12),(5,12),(2,13),(5,13),(2,14),(5,14),(2,15),(5,15),(2,16),(5,16),(2,17),(5,17),(2,18),(5,18),(2,19),(5,19),(2,20),(5,20),(2,21),(5,21),(2,22),(5,22),(2,23),(5,23),(2,24),(5,24),(2,25),(5,25),(2,26),(5,26),(2,27),(4,27),(5,27),(2,28),(5,28),(2,29),(5,29),(2,30),(5,30),(2,31),(4,31),(5,31),(2,32),(5,32),(2,33),(5,33),(2,34),(5,34),(2,35),(5,35),(5,36);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tracking`
--

DROP TABLE IF EXISTS `user_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tracking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `operation_date` datetime(6) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3ved60j34bv0eocwtteb4nq6x` (`user_id`),
  CONSTRAINT `FK3ved60j34bv0eocwtteb4nq6x` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tracking`
--

LOCK TABLES `user_tracking` WRITE;
/*!40000 ALTER TABLE `user_tracking` DISABLE KEYS */;
INSERT INTO `user_tracking` VALUES (1,'pauleAggy','CREATE | REGISTER A USER WITH THE NAME: training training','2021-03-05 08:41:47.357000',4),(2,'Nnoko1','EDIT | UPDATE A USER WITH THE NAME: ADMIN NNOKO','2021-06-01 03:22:10.490000',5),(3,'Nnoko1','EDIT | UPDATE A USER WITH THE NAME: ADMIN NNOKO','2021-06-01 04:12:00.756000',5),(4,'Nnoko1','EDIT | UPDATE A USER WITH THE NAME: ADMIN NNOKO','2022-11-16 10:59:08.162000',5);
/*!40000 ALTER TABLE `user_tracking` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-20 11:08:56
