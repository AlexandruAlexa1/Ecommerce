CREATE DATABASE  IF NOT EXISTS `ecommerce_project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ecommerce_project`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: ecommerce_project
-- ------------------------------------------------------
-- Server version	9.0.1

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
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address_line_1` varchar(64) NOT NULL,
  `address_line_2` varchar(64) DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `postal_code` varchar(10) NOT NULL,
  `state` varchar(45) NOT NULL,
  `default_address` bit(1) DEFAULT NULL,
  `country_id` int DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn3sth7s3kur1rafwbbrqqnswt` (`country_id`),
  KEY `FKhrpf5e8dwasvdc5cticysrt2k` (`customer_id`),
  CONSTRAINT `FKhrpf5e8dwasvdc5cticysrt2k` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FKn3sth7s3kur1rafwbbrqqnswt` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alias` varchar(500) NOT NULL,
  `content` mediumtext NOT NULL,
  `published` bit(1) NOT NULL,
  `title` varchar(256) NOT NULL,
  `type` varchar(255) NOT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlc3sm3utetrj1sx4v9ahwopnr` (`user_id`),
  CONSTRAINT `FKlc3sm3utetrj1sx4v9ahwopnr` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (1,'about','<div style=\"text-align: center;\"><br></div><div>Welcome to [Store Name], your go-to destination for high-quality products, competitive prices, and excellent customer service! We are an online store committed to bringing you the best deals and providing you with a seamless and enjoyable shopping experience.</div><div><br></div><div><strong>Our Mission</strong></div><div>Our mission is to offer you top-notch products that meet your needs, no matter the category. From home goods and technology to fashion and personal care items, every product we offer is carefully selected to enhance your daily life.</div><div><br></div><div><strong>What Sets Us Apart</strong></div><ol><li><div><strong>Diverse Selection:</strong> We offer a wide range of products that cover many categories. Whether you\'re looking for home essentials, electronics, fashion accessories, or beauty products, you\'ll find exactly what you need.</div></li><li><div><strong>Affordable Prices:</strong> We strive to provide you with the best prices so that you can enjoy your favorite products without worrying about your budget.</div></li><li><div><strong>Guaranteed Quality:</strong> We collaborate with trusted suppliers to bring you only top-quality items. Every product is carefully vetted to ensure it meets our high standards.</div></li><li><div><strong>Fast and Secure Delivery:</strong> We understand how important it is for your orders to arrive quickly and in perfect condition. That\'s why we work with reliable couriers to deliver your purchases as soon as possible.</div></li><li><div><strong>Excellent Customer Support:</strong> Our support team is always ready to assist you with any questions or concerns. We want every shopping experience to be a pleasant one, so we\'re here for you!</div></li><li><div><br></div></li></ol><div><strong>Our Vision</strong></div><div>We aim to become our customers’ preferred online store, offering outstanding products and exceptional service. Our goal is to be a trusted partner in your daily life, providing all the products you need, delivered right to your door.</div><div><br></div><div><strong>What We Offer</strong></div><ul><li><strong>High-quality products</strong> across various categories</li><li><strong>Affordable prices</strong> for every budget</li><li><strong>Fast and secure delivery</strong> with guaranteed satisfaction</li><li><strong style=\"text-align: center; font-size: 1.7rem;\">Excellent customer support</strong><span style=\"text-align: center; font-size: 1.7rem;\">, always available to assist you</span></li><li style=\"text-align: center;\"><font color=\"#0000ff\"><br></font></li></ul><div style=\"text-align: center;\"><font color=\"#0000ff\">Thank you for choosing [Store Name] for your online shopping needs. We’re here to bring you the best products and the best deals!</font><br></div>',_binary '','About','menu','2025-02-04 10:54:14.275000',1);
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `id` int NOT NULL AUTO_INCREMENT,
  `logo` varchar(128) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_oce3937d2f4mpfqrycbr0l93m` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'dell.png','Dell'),(2,'hp.png','HP'),(3,'lenovo.png','Lenovo'),(4,'apple.png','Apple'),(5,'samsung.png','Samsung'),(6,'nikon\'.png','Nikon'),(7,'can.png','Canon'),(8,'AMD.png','AMD'),(9,'intel.png','Intel'),(10,'nvida.png','NVIDIA'),(11,'corsair.png','Corsair'),(12,'seasonic.png','Seasonic'),(13,'asus.png','Asus'),(14,'msi.png','MSI');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands_categories`
--

DROP TABLE IF EXISTS `brands_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands_categories` (
  `brand_id` int NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`brand_id`,`category_id`),
  KEY `FK6x68tjj3eay19skqlhn7ls6ai` (`category_id`),
  CONSTRAINT `FK58ksmicdguvu4d7b6yglgqvxo` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FK6x68tjj3eay19skqlhn7ls6ai` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands_categories`
--

LOCK TABLES `brands_categories` WRITE;
/*!40000 ALTER TABLE `brands_categories` DISABLE KEYS */;
INSERT INTO `brands_categories` VALUES (1,2),(2,2),(3,2),(4,2),(5,2),(4,3),(5,3),(13,3),(6,4),(7,4),(5,5),(8,5),(9,5),(8,6),(9,6),(10,6),(2,7),(13,7),(2,8),(13,8),(5,9),(13,9),(5,12),(11,13),(13,13),(14,13),(11,14),(12,14),(13,14),(5,15),(11,15),(1,16),(2,16);
/*!40000 ALTER TABLE `brands_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `customer_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdagcsk6v6x4n1kxw3rkp57921` (`customer_id`),
  KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
  CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKdagcsk6v6x4n1kxw3rkp57921` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alias` varchar(68) NOT NULL,
  `all_parent_ids` varchar(256) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `image` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `parent_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jx1ptm0r46dop8v0o7nmgfbeq` (`alias`),
  UNIQUE KEY `UK_t8o6pivur7nn124jehx7cygw5` (`name`),
  KEY `FKsaok720gsu4u2wrgbk10b5n8d` (`parent_id`),
  CONSTRAINT `FKsaok720gsu4u2wrgbk10b5n8d` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'electronics',NULL,_binary '','electronics.png','Electronics',NULL),(2,'laptops','-1-',_binary '','leptops.png','Laptops',1),(3,'phones','-1-',_binary '','phones.png','Phones',1),(4,'cameras','-1-',_binary '','cameras.png','Cameras',1),(5,'graphics-cards','-1-',_binary '','gcards.png','Graphics Cards',1),(6,'cpus','-1-',_binary '','cpus.png','CPUs',1),(7,'gaming-laptops','-1-2-',_binary '','gleptops.png','Gaming Laptops',2),(8,'business-laptops','-1-2-',_binary '','bleptops.png','Business Laptops',2),(9,'smartphones','-1-3-',_binary '','smart.png','Smartphones',3),(10,'feature-phones','-1-3-',_binary '','fphones.png','Feature Phones',3),(12,'stands','-1-',_binary '','stands.png','Stands',1),(13,'motherboards','-1-',_binary '','Motherboards.png','Motherboards',1),(14,'power-supplies','-1-',_binary '','PowerS.png','Power Supplies',1),(15,'memory','-1-',_binary '','ram.png','Memory (RAM)',1),(16,'bags','-1-2-',_binary '','BG.png','Bags',2);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `countries`
--

DROP TABLE IF EXISTS `countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `countries` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(5) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countries`
--

LOCK TABLES `countries` WRITE;
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
INSERT INTO `countries` VALUES (1,'US','United States'),(2,'CA','Canada'),(3,'DE','Germany'),(4,'FR','France'),(5,'RO','Romania');
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currencies`
--

DROP TABLE IF EXISTS `currencies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `currencies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(4) NOT NULL,
  `name` varchar(64) NOT NULL,
  `symbol` varchar(3) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currencies`
--

LOCK TABLES `currencies` WRITE;
/*!40000 ALTER TABLE `currencies` DISABLE KEYS */;
INSERT INTO `currencies` VALUES (1,'RON','Romanian Leu','RON'),(2,'USD','US Dollar','$'),(3,'EUR','Euro','€'),(4,'GBP','British Pound','£'),(5,'JPY','Japanese Yen','¥'),(6,'CHF','Swiss Franc','CHF'),(7,'CAD','Canadian Dollar','C$'),(8,'AUD','Australian Dollar','A$'),(9,'INR','Indian Rupee','₹'),(10,'CNY','Chinese Yuan','¥'),(11,'ZAR','South African Rand','R'),(12,'RUB','Russian Ruble','₽'),(13,'MXN','Mexican Peso','MX$'),(14,'BRL','Brazilian Real','R$'),(15,'KRW','South Korean Won','₩'),(16,'TRY','Turkish Lira','₺'),(17,'SEK','Swedish Krona','kr'),(18,'NOK','Norwegian Krone','kr'),(19,'DKK','Danish Krone','kr'),(20,'PLN','Polish Zloty','zł'),(21,'CZK','Czech Koruna','Kč'),(22,'HUF','Hungarian Forint','Ft'),(23,'NZD','New Zealand Dollar','NZ$');
/*!40000 ALTER TABLE `currencies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address_line_1` varchar(64) NOT NULL,
  `address_line_2` varchar(64) DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `postal_code` varchar(10) NOT NULL,
  `state` varchar(45) NOT NULL,
  `authentication_type` varchar(10) DEFAULT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(65) NOT NULL,
  `reset_password_token` varchar(30) DEFAULT NULL,
  `verification_code` varchar(64) DEFAULT NULL,
  `country_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rfbvkrffamfql7cjmen8v976v` (`email`),
  KEY `FK7b7p2myt0y31l4nyj1p7sk0b1` (`country_id`),
  CONSTRAINT `FK7b7p2myt0y31l4nyj1p7sk0b1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Adress ','','Iasi','AA','AA','0742883000','346622','Iași','DATABASE','2025-02-03 10:54:40.655000','aa@yahoo.com',_binary '','$2a$10$8FQnqbbufpP0Si9wP.PfbeiNu1sx4swCGPauPjeOJi3aXn7V7hfKi',NULL,'hv82twn45jIAc3JUpFodKAHd39D7W5OySWsfn33OauoYebv6K6ENhHCEYBEGq4gP',5),(2,'456 Broadway Ave','Suite 9','New York,','Emily','Johnson','+1 646-555-2345','2346644','New York','DATABASE','2025-02-03 10:55:48.823000','emily.johnson@email.com',_binary '','$2a$10$sdV7OvNpG406XGOMdcsZpuqaNhGHTKfaEPwi1DfkapYvfzB9sRLzG',NULL,'sxJtaUb7rZbnjbQgBGEtRuz7glNr3ZC0Te1tV2tRMJY8lCYLY3DM4ips5GCQ3qAp',1),(3,'321 Fremont St','Unit 5A','New York','Jessica','Brown',' +1 702-555-456','423455','New York','DATABASE','2025-02-03 10:57:14.891000','jessica.brown@email.com',_binary '','$2a$10$qq.eNc0jaXNLeM6Vh6HgS.O3rgQKyvoHrW6hKKrytKzddPPx2tC5a',NULL,'hJU9y5kf5jppZwCse7DcaHeeQbVUenrddxI0mtXZVCHdHX2n8UpsOjNQE0U3DTsb',1),(4,'555 Pike Street','','Texas','Christopher','Wilson','+1 206-555-1122','42345','Texas','DATABASE','2025-02-03 10:58:17.875000','chris.wilson@email.com',_binary '','$2a$10$rKj.BjgA.PlfH/WB/k4bB.tbgF0llQIPsri1yWVmbCASAyqshwFe6',NULL,'9cYAKCQiHGrMPAA0rwDI01zQlFlx1b50gW6rAt3aSueUodDQWAJWUI1HDlTYUyZe',1);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menus`
--

DROP TABLE IF EXISTS `menus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `position` int DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `article_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa5eajuapjflm0jk7xh5mangy5` (`article_id`),
  CONSTRAINT `FKa5eajuapjflm0jk7xh5mangy5` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menus`
--

LOCK TABLES `menus` WRITE;
/*!40000 ALTER TABLE `menus` DISABLE KEYS */;
INSERT INTO `menus` VALUES (1,'about-us',_binary '',1,'About US','Footer Menu',1);
/*!40000 ALTER TABLE `menus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_cost` float NOT NULL,
  `quantity` int NOT NULL,
  `shipping_cost` float NOT NULL,
  `subtotal` float NOT NULL,
  `unit_price` float NOT NULL,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  KEY `FK4q98utpd73imf4yhttm3w0eax` (`product_id`),
  CONSTRAINT `FK4q98utpd73imf4yhttm3w0eax` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (1,1000,5,189.928,0,8,1,17),(2,600,5,172.662,0,10,1,16),(3,4500,5,12,0,5,1,4),(4,2800,1,11.9609,0,8,2,7),(5,30,1,14.964,0,20,2,10),(6,900,1,2.6,0,5,2,4),(7,35,1,25.3813,0,15,1,11),(8,1200,1,19.6,0,10,1,1),(9,900,1,2,0,5,2,4),(10,1200,1,14,0,10,3,1),(11,2800,1,9.2,0,8,3,7),(12,19,1,0,0,-12,4,20),(13,150,1,2.8,0,10,4,18),(14,120,1,40.2878,0,10,4,16),(15,300,1,18.1295,0,5,4,14),(16,600,1,2.8,0,10,4,8),(17,1200,1,14,0,10,5,1);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_track`
--

DROP TABLE IF EXISTS `order_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_track` (
  `id` int NOT NULL AUTO_INCREMENT,
  `notes` varchar(256) DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK31jv1s212kajfn3kk1ksmnyfl` (`order_id`),
  CONSTRAINT `FK31jv1s212kajfn3kk1ksmnyfl` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_track`
--

LOCK TABLES `order_track` WRITE;
/*!40000 ALTER TABLE `order_track` DISABLE KEYS */;
INSERT INTO `order_track` VALUES (1,'Order was placed by the customer','NEW','2025-01-23 17:31:25.158000',1),(2,'Order was placed by the customer','NEW','2025-01-27 10:55:00.073000',2),(3,'Order was placed by the customer','NEW','2025-02-03 11:00:13.674000',1),(4,'Order was placed by the customer','NEW','2025-02-03 11:02:11.216000',2),(5,'Order was placed by the customer','NEW','2025-02-03 11:03:39.000000',3),(6,'Customer received products','DELIVERED','2025-02-03 14:12:07.000000',3),(7,'Order was placed by the customer','NEW','2025-02-04 11:02:03.245000',4),(8,'Order was placed by the customer','NEW','2025-02-04 16:00:30.068000',5);
/*!40000 ALTER TABLE `order_track` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address_line_1` varchar(64) NOT NULL,
  `address_line_2` varchar(64) DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `postal_code` varchar(10) NOT NULL,
  `state` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `deliver_date` datetime(6) DEFAULT NULL,
  `deliver_days` int NOT NULL,
  `order_status` varchar(255) DEFAULT NULL,
  `order_time` datetime(6) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `product_cost` float NOT NULL,
  `shipping_cost` float NOT NULL,
  `subtotal` float NOT NULL,
  `tax` float NOT NULL,
  `total` float NOT NULL,
  `customer_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpxtb8awmi0dk6smoh2vp1litg` (`customer_id`),
  CONSTRAINT `FKpxtb8awmi0dk6smoh2vp1litg` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'Adress ','','Iasi','AA','AA','0742883000','346622','Iași','Romania','2025-02-08 11:00:13.674000',5,'NEW','2025-02-03 11:00:13.674000','COD',1235,44.9813,1311,0,1355.98,1),(2,'456 Broadway Ave','Suite 9','New York,','Emily','Johnson','+1 646-555-2345','2346644','New York','United States','2025-02-07 11:02:11.216000',4,'NEW','2025-02-03 11:02:11.216000','COD',900,2,1140,0,1142,2),(3,'321 Fremont St','Unit 5A','New York','Jessica','Brown',' +1 702-555-456','423455','New York','United States','2025-02-07 00:00:00.000000',4,'DELIVERED','2025-02-03 11:03:39.809000','COD',4000,23.2,4480,0,4503.2,3),(4,'Adress ','','Iasi','AA','AA','0742883000','346622','Iași','Romania','2025-02-09 11:02:03.245000',5,'NEW','2025-02-04 11:02:03.245000','COD',1189,64.0173,1334,0,1398.02,1),(5,'456 Broadway Ave','Suite 9','New York,','Emily','Johnson','+1 646-555-2345','2346644','New York','United States','2025-02-08 16:00:30.068000',4,'NEW','2025-02-04 16:00:30.068000','COD',1200,14,1260,0,1274,2);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_details`
--

DROP TABLE IF EXISTS `product_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnfvvq3meg4ha3u1bju9k4is3r` (`product_id`),
  CONSTRAINT `FKnfvvq3meg4ha3u1bju9k4is3r` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_details`
--

LOCK TABLES `product_details` WRITE;
/*!40000 ALTER TABLE `product_details` DISABLE KEYS */;
INSERT INTO `product_details` VALUES (6,'Processor','i7 ',1),(7,'RAM','16GB',1),(8,'Storage','512GB',1);
/*!40000 ALTER TABLE `product_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqnq71xsohugpqwf3c9gxmsuy` (`product_id`),
  CONSTRAINT `FKqnq71xsohugpqwf3c9gxmsuy` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (1,'dell2.png',1),(2,'dell3.png',1);
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) NOT NULL,
  `answer_count` int DEFAULT NULL,
  `average_rating` float DEFAULT NULL,
  `cost` float NOT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `discount_percent` float DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `full_description` varchar(4096) NOT NULL,
  `height` float NOT NULL,
  `in_stock` bit(1) DEFAULT NULL,
  `length` float NOT NULL,
  `main_image` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `question_count` int DEFAULT NULL,
  `review_count` int DEFAULT NULL,
  `short_description` varchar(512) NOT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `weight` float NOT NULL,
  `width` float NOT NULL,
  `brand_id` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o61fmio5yukmmiqgnxf8pnavn` (`name`),
  KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv` (`brand_id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKa3a4mpsfdf4d2y6r8ra3sc8mv` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'dell-xps-13',0,5,1200,NULL,10,_binary '','<b>High-performance laptop with 13-inch screen</b>',1.2,_binary '',30.5,'dell1.png','Dell XPS 13',1400,0,1,'Ultraportable with top performance','2025-02-04 15:56:22.436000',1.4,20.3,1,2),(2,'hp-spectre-x360',0,0,1300,NULL,12,_binary '','Design elegant, ecran tactil 4K, procesor Intel i7, 16GB RAM, 1TB SSD.',1.3,_binary '',31,'Screenshot 2025-01-09 164445.png','HP Spectre x360',1500,0,0,'Laptop convertibil premium','2025-01-09 16:45:41.007000',1.5,22,2,2),(3,'lenovo-thinkpad-x1-carbon',0,0,1400,NULL,8,_binary '','Construit pentru profesioniști, procesor Intel i7, 16GB RAM, 1TB SSD.',1.2,_binary '',32,'Screenshot 2025-01-09 164646.png','Lenovo ThinkPad X1 Carbon',1600,0,0,'Laptop pentru afaceri','2025-01-09 16:47:12.874000',1.4,22.5,3,2),(4,'apple-iphone-15',0,0,900,NULL,5,_binary '','Procesor A17 Bionic, 256GB stocare, cameră de 48MP, ecran OLED de 6.1 inch.',0.8,_binary '',14.7,'Screenshot 2025-01-09 164954.png','Apple iPhone 15',1200,0,0,'Cel mai nou model iPhone','2025-01-09 16:50:32.605000',0.2,7.1,4,3),(5,'samsung-galaxy-s23-ultra',0,0,950,NULL,7,_binary '','Procesor Snapdragon 8 Gen 2, 512GB stocare, cameră de 200MP, baterie de 5000mAh.',0.9,_binary '',15.9,'Screenshot 2025-01-09 165100.png','Samsung Galaxy S23 Ultra',1300,0,0,'Smartphone flagship de la Samsung','2025-01-09 16:51:19.484000',0.2,7.9,5,3),(6,'nikon-d850',0,0,2500,NULL,5,_binary '','Senzor full-frame de 45.7 MP, 4K UHD, 7 cadre pe secundă.',10,_binary '',15,'Screenshot 2025-01-10 110244.png','Nikon D850',3000,0,0,'Cameră DSLR pentru profesioniști','2025-01-10 11:03:01.267000',1,12.5,6,4),(7,'canon-eos-r5',0,1,2800,NULL,8,_binary '','Senzor full-frame de 45 MP, 8K video, stabilizare integrată.',9.8,_binary '',14.5,'Screenshot 2025-01-10 110326.png','Canon EOS R5',3500,0,1,'Cameră mirrorless de top','2025-01-10 11:03:46.483000',0.9,12,7,4),(8,'amd-ryzen-9-7950x',0,0,600,NULL,10,_binary '','16 nuclee, 32 fire de execuție, frecvență turbo de 5.7 GHz.',0.1,_binary '',3.5,'Screenshot 2025-01-10 110423.png','AMD Ryzen 9 7950X',700,0,0,'Procesor de ultimă generație','2025-01-16 16:07:23.881000',0.2,3.5,8,6),(9,'intel-core-i9-13900k',0,0,650,NULL,8,_binary '','24 nuclee (8 performanță + 16 eficiență), turbo 5.8 GHz.',0.1,_binary '',3.7,'Screenshot 2025-01-10 110537.png','Intel Core i9-13900K',750,1,0,'Cel mai nou procesor Intel','2025-01-17 16:22:04.878000',0.2,3.7,9,6),(10,'dell-laptop-bag',0,0,30,NULL,20,_binary '','Geantă rezistentă la apă, compatibilă cu laptopuri de până la 15.6 inch.',5,_binary '',40,'Screenshot 2025-01-10 110627.png','Dell Laptop Bag',50,0,0,'Geantă premium pentru laptopuri','2025-01-18 11:39:04.483000',0.8,30,1,16),(11,'hp-business-bag',0,0,35,NULL,15,_binary '','Geantă cu compartimente multiple, potrivită pentru laptopuri și accesorii.',6,_binary '',42,'BG.png','HP Business Bag',60,0,0,'Geantă profesională pentru afaceri','2025-01-17 17:17:56.701000',1,32,2,16),(12,'adjustable-laptop-stand',0,0,20,NULL,10,_binary '','Construcție din aluminiu, potrivit pentru laptopuri de 13-17 inch.',2,_binary '',30,'ST.png','Adjustable Laptop Stand',35,0,0,'Stand ergonomic reglabil','2025-01-18 11:24:52.636000',0.5,25,5,12),(13,'portable-phone-stand',0,0,10,NULL,5,_binary '','Ușor de utilizat, compatibil cu toate modelele de telefoane.',1,_binary '',10,'Screenshot 2025-01-17 160913.png','Portable Phone Stand',15,0,0,'Stand portabil pentru telefoane','2025-01-18 11:25:08.885000',0.2,10,5,12),(14,'asus-rog-strix-x670e',0,0,300,NULL,5,_binary '','Socket AM5, PCIe 5.0, suport DDR5, optimizată pentru gaming.',5,_binary '',30,'Screenshot 2025-01-17 161011.png','ASUS ROG Strix X670E',400,0,0,'Placă de bază high-end','2025-01-18 11:36:43.623000',1.2,25,13,13),(15,'msi-mpg-z790-edge',0,0,250,NULL,7,_binary '','Socket LGA1700, DDR5, Wi-Fi 6E, ideală pentru creatori de conținut.',5,_binary '',30,'Screenshot 2025-01-17 161104.png','MSI MPG Z790 Edge',350,0,0,'Placă de bază pentru Intel','2025-01-18 11:38:14.856000',1.3,25,14,13),(16,'corsair-rm850x',0,0,120,NULL,10,_binary '','Certificare 80+ Gold, ventilator cu zgomot redus.',10,_binary '',20,'Screenshot 2025-01-17 161223.png','Corsair RM850x',150,0,0,'Sursă modulară de 850W','2025-01-18 11:32:45.483000',2,15,11,14),(17,'seasonic-prime-tx-750',0,0,200,NULL,8,_binary '','Certificare 80+ Titanium, fiabilitate ridicată, complet modulară.',10,_binary '',20,'Screenshot 2025-01-17 161438.png','Seasonic PRIME TX-750',250,0,0,'Sursă de alimentare premium','2025-01-18 11:34:33.287000',2.2,15,12,14),(18,'corsair-vengeance-ddr5-32gb',0,0,150,NULL,10,_binary '','Frecvență 5600MHz, latență redusă, compatibilă cu platformele DDR5.',0.5,_binary '',13.5,'Screenshot 2025-01-17 161533.png','Corsair Vengeance DDR5 32GB',200,0,0,'Memorie RAM de mare performanță','2025-01-18 11:32:09.779000',0.2,3,11,15),(19,'gskill-trident-z5-rgb-16gb',0,0,100,NULL,8,_binary '','Frecvență 6000MHz, design premiat, optimizată pentru gaming.',0.5,_binary '',13.5,'Screenshot 2025-01-17 161631.png','G.SKILL Trident Z5 RGB 16GB',150,0,0,'Memorie RAM cu iluminare RGB','2025-01-18 11:32:25.432000',0.2,3,11,15),(20,'XFX-Speedster-QICK-319-Radeon™-RX-7700-XT-Black-Edition,-12GB-GDDR6,-192-bit',0,0,19,'2025-01-18 19:25:55.493000',-12,_binary '','<div><br></div>',0,_binary '',0,'amdv.png','XFX Speedster QICK 319 Radeon™ RX 7700 XT Black Edition, 12GB GDDR6, 192-bit',9,0,0,'<div><br></div>','2025-01-18 19:25:55.494000',0,0,8,5),(21,'ASUS-TUF-F15-FX507ZC4',0,0,43,'2025-01-18 20:21:23.112000',-2,_binary '','<div><br></div>',0,_binary '\0',0,'tuf.png','ASUS TUF F15 FX507ZC4',3,0,0,'<div><br></div>','2025-01-18 20:21:23.112000',0,0,13,7),(22,'-Samsung-Galaxy-A55',0,0,39,'2025-01-18 20:24:52.327000',-14,_binary '','<div><br></div>',0,_binary '',0,'a55.png',' Samsung Galaxy A55',17,0,0,'<div><br></div>','2025-01-18 20:24:52.327000',0,0,5,9);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_votes`
--

DROP TABLE IF EXISTS `question_votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_votes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `votes` int NOT NULL,
  `customer_id` int DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj676vddfbm49jx9j3u2gtdhm8` (`customer_id`),
  KEY `FK1xd1qk3vvt048vghi893xcpo9` (`question_id`),
  CONSTRAINT `FK1xd1qk3vvt048vghi893xcpo9` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  CONSTRAINT `FKj676vddfbm49jx9j3u2gtdhm8` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_votes`
--

LOCK TABLES `question_votes` WRITE;
/*!40000 ALTER TABLE `question_votes` DISABLE KEYS */;
/*!40000 ALTER TABLE `question_votes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `answer_content` varchar(300) DEFAULT NULL,
  `answer_time` datetime(6) DEFAULT NULL,
  `approval_status` bit(1) DEFAULT NULL,
  `ask_time` datetime(6) DEFAULT NULL,
  `question_content` varchar(128) DEFAULT NULL,
  `votes` int NOT NULL,
  `customer_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfu8glnl0vfrytq0nf7sb428mw` (`customer_id`),
  KEY `FKdnt39hlm1bcye9ivenccipd5s` (`product_id`),
  KEY `FKjoo8hp6d3gfwctr68dl2iaemj` (`user_id`),
  CONSTRAINT `FKdnt39hlm1bcye9ivenccipd5s` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKfu8glnl0vfrytq0nf7sb428mw` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FKjoo8hp6d3gfwctr68dl2iaemj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'Yes, the Intel Core i9-13900K offers impressive performance, especially in intensive tasks like gaming and video editing.','2025-02-03 14:27:26.245000',_binary '','2025-02-03 14:25:39.933000','Is the Intel Core i9-13900K worth the upgrade over previous generations, given its high power consumption and high temperatures?',0,4,9,1);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review_votes`
--

DROP TABLE IF EXISTS `review_votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review_votes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `votes` int NOT NULL,
  `customer_id` int DEFAULT NULL,
  `review_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1gcn1t649dupg1tobjdeemyq0` (`customer_id`),
  KEY `FK5bf4o4ap2v7g3qpxt8y28a38x` (`review_id`),
  CONSTRAINT `FK1gcn1t649dupg1tobjdeemyq0` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FK5bf4o4ap2v7g3qpxt8y28a38x` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review_votes`
--

LOCK TABLES `review_votes` WRITE;
/*!40000 ALTER TABLE `review_votes` DISABLE KEYS */;
/*!40000 ALTER TABLE `review_votes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comment` varchar(500) NOT NULL,
  `headline` varchar(150) NOT NULL,
  `rating` int NOT NULL,
  `review_time` datetime(6) NOT NULL,
  `votes` int NOT NULL,
  `customer_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4sm0k8kw740iyuex3vwwv1etu` (`customer_id`),
  KEY `FKpl51cejpw4gy5swfar8br9ngi` (`product_id`),
  CONSTRAINT `FK4sm0k8kw740iyuex3vwwv1etu` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FKpl51cejpw4gy5swfar8br9ngi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,'Exactly what I was looking for.','A very good laptop!',5,'2025-02-03 14:17:08.922000',0,3,1),(2,'I had high expectations for the Canon EOS R5, but unfortunately, my experience didn\'t live up to the premium price tag. The biggest issue is overheating in 8K and even 4K video mode, which makes the camera unusable for long-term filming. In addition, the battery life is disappointing, and the camera menu is quite complicated compared to other models in the same range. While the image quality is excellent, these issues seriously affect professional use. I don\'t recommend it for those who want a r','Disappointing for its price – Overheating and other issues',1,'2025-02-03 14:23:11.513000',0,3,7);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(150) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Manage everything','Admin'),(2,'Manage product price, customers, shipping, orders and sales report','SalesPerson'),(3,'Manage categories, brands, products, articles and menus','Editor'),(4,'View products, view orders and update order status','Shipper'),(5,'Manage questions and reviews','Assistant');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sections`
--

DROP TABLE IF EXISTS `sections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sections` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `heading` varchar(255) DEFAULT NULL,
  `position` int NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sections`
--

LOCK TABLES `sections` WRITE;
/*!40000 ALTER TABLE `sections` DISABLE KEYS */;
INSERT INTO `sections` VALUES (1,'',_binary '','',1,'ALL_CATEGORIES');
/*!40000 ALTER TABLE `sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sections_articles`
--

DROP TABLE IF EXISTS `sections_articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sections_articles` (
  `section_id` int NOT NULL,
  `article_id` int NOT NULL,
  KEY `FKguoivqg4rnmm8nilb12x40ygh` (`article_id`),
  KEY `FKnbym8cke5rnxjm8bdi70dapl` (`section_id`),
  CONSTRAINT `FKguoivqg4rnmm8nilb12x40ygh` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`),
  CONSTRAINT `FKnbym8cke5rnxjm8bdi70dapl` FOREIGN KEY (`section_id`) REFERENCES `sections` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sections_articles`
--

LOCK TABLES `sections_articles` WRITE;
/*!40000 ALTER TABLE `sections_articles` DISABLE KEYS */;
/*!40000 ALTER TABLE `sections_articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sections_brands`
--

DROP TABLE IF EXISTS `sections_brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sections_brands` (
  `section_id` int NOT NULL,
  `brand_id` int NOT NULL,
  KEY `FK63hur81l4do6ck6x015gp3cuu` (`brand_id`),
  KEY `FK68eeev3m314v82j0st116ftm9` (`section_id`),
  CONSTRAINT `FK63hur81l4do6ck6x015gp3cuu` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FK68eeev3m314v82j0st116ftm9` FOREIGN KEY (`section_id`) REFERENCES `sections` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sections_brands`
--

LOCK TABLES `sections_brands` WRITE;
/*!40000 ALTER TABLE `sections_brands` DISABLE KEYS */;
/*!40000 ALTER TABLE `sections_brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sections_categories`
--

DROP TABLE IF EXISTS `sections_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sections_categories` (
  `section_id` int NOT NULL,
  `category_id` int NOT NULL,
  KEY `FK4oqn5st47mm34yvnt3criccsh` (`category_id`),
  KEY `FK7ivge0x7bydfh10vw3d0n837j` (`section_id`),
  CONSTRAINT `FK4oqn5st47mm34yvnt3criccsh` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `FK7ivge0x7bydfh10vw3d0n837j` FOREIGN KEY (`section_id`) REFERENCES `sections` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sections_categories`
--

LOCK TABLES `sections_categories` WRITE;
/*!40000 ALTER TABLE `sections_categories` DISABLE KEYS */;
INSERT INTO `sections_categories` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,12),(1,13),(1,14),(1,15),(1,16);
/*!40000 ALTER TABLE `sections_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sections_products`
--

DROP TABLE IF EXISTS `sections_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sections_products` (
  `product_id` int NOT NULL,
  `section_id` int NOT NULL,
  KEY `FKjmh450o9k6x0hrmrumemucg1b` (`section_id`),
  KEY `FKl5lf8i896cqdnc9shitywps2l` (`product_id`),
  CONSTRAINT `FKjmh450o9k6x0hrmrumemucg1b` FOREIGN KEY (`section_id`) REFERENCES `sections` (`id`),
  CONSTRAINT `FKl5lf8i896cqdnc9shitywps2l` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sections_products`
--

LOCK TABLES `sections_products` WRITE;
/*!40000 ALTER TABLE `sections_products` DISABLE KEYS */;
/*!40000 ALTER TABLE `sections_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settings` (
  `key` varchar(128) NOT NULL,
  `category` varchar(45) NOT NULL,
  `value` varchar(1024) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES ('COPYRIGHT','GENERAL','© 2025 AA.'),('CURRENCY_ID','CURRENCY','3'),('CURRENCY_SYMBOL','CURRENCY','€'),('CURRENCY_SYMBOL_POSITION','CURRENCY','After price'),('CUSTOMER_VERIFY_CONTENT','MAIL_TEMPLATES','CONTENT'),('CUSTOMER_VERIFY_SUBJECT','MAIL_TEMPLATES','SUBJECT'),('DECIMAL_DIGITS','CURRENCY','2'),('DECIMAL_POINT_TYPE','CURRENCY','POINT'),('MAIL_FROM','MAIL_SERVER','noreply@example.com'),('MAIL_HOST','MAIL_SERVER','smtp.example.com'),('MAIL_PASSWORD','MAIL_SERVER','password123'),('MAIL_PORT','MAIL_SERVER','587'),('MAIL_SENDER_NAME','MAIL_SERVER','Example Sender'),('MAIL_USERNAME','MAIL_SERVER','user@example.com'),('ORDER_CONFIRMATION_CONTENT','MAIL_TEMPLATES','CONTENT'),('ORDER_CONFIRMATION_SUBJECT','MAIL_TEMPLATES','SUBJECT'),('PAYPAL_API_BASE_URL','PAYMENT','URL'),('PAYPAL_API_CLIENT_ID','PAYMENT','ID'),('PAYPAL_API_CLIENT_SECRET','PAYMENT','SECRET'),('SITE_LOGO','GENERAL','/site-logo/logo2.jpg'),('SITE_NAME','GENERAL','Ecommerce Platform'),('SMTP_AUTH','MAIL_SERVER','true'),('SMTP_SECURED','MAIL_SERVER','false'),('THOUSANDS_POINT_TYPE','CURRENCY','COMMA');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_rates`
--

DROP TABLE IF EXISTS `shipping_rates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_rates` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cod_supported` bit(1) DEFAULT NULL,
  `days` int NOT NULL,
  `rate` float NOT NULL,
  `state` varchar(45) NOT NULL,
  `country_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKef7sfgeybt3xn13nlt2j6sljw` (`country_id`),
  CONSTRAINT `FKef7sfgeybt3xn13nlt2j6sljw` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_rates`
--

LOCK TABLES `shipping_rates` WRITE;
/*!40000 ALTER TABLE `shipping_rates` DISABLE KEYS */;
INSERT INTO `shipping_rates` VALUES (1,_binary '',5,15.5,'București',5),(2,_binary '',4,12,'Cluj',5),(3,_binary '\0',3,10,'Timiș',5),(4,_binary '',5,14,'Iași',5),(5,_binary '',4,11.5,'Constanța',5),(6,_binary '\0',2,8.5,'California',1),(7,_binary '\0',3,9,'Texas',1),(8,_binary '',4,10,'New York',1),(9,_binary '',2,7.5,'Ontario',2),(10,_binary '',3,8,'Quebec',2),(11,_binary '\0',4,9.5,'British Columbia',2),(12,_binary '',5,12.5,'Bavaria',3),(13,_binary '\0',4,11,'Berlin',3),(14,_binary '\0',3,10.5,'Hesse',3),(15,_binary '',5,13,'Île-de-France',4),(16,_binary '',6,14.5,'Provence-Alpes-Côte d\'Azur',4),(17,_binary '',5,12,'Auvergne-Rhône-Alpes',4);
/*!40000 ALTER TABLE `shipping_rates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `states`
--

DROP TABLE IF EXISTS `states`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `states` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `country_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKskkdphjml9vjlrqn4m5hi251y` (`country_id`),
  CONSTRAINT `FKskkdphjml9vjlrqn4m5hi251y` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `states`
--

LOCK TABLES `states` WRITE;
/*!40000 ALTER TABLE `states` DISABLE KEYS */;
INSERT INTO `states` VALUES (1,'București',5),(2,'Cluj',5),(3,'Timiș',5),(4,'Iași',5),(5,'Constanța',5),(6,'California',1),(7,'Texas',1),(8,'New York',1),(9,'Ontario',2),(10,'Quebec',2),(11,'British Columbia',2),(12,'Bavaria',3),(13,'Berlin',3),(14,'Hesse',3),(15,'Île-de-France',4),(16,'Provence-Alpes-Côte d\'Azur',4),(17,'Auvergne-Rhône-Alpes',4);
/*!40000 ALTER TABLE `states` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `photos` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@yahoo.com',_binary '','Admin','Admin','$2a$10$NP6U3VBlJzw1Ktqc/rlj9eDo/O/.RX3kdpNF6kdew3v2s2I56pomG',NULL),(2,'editor@yahoo.com',_binary '','Editor','Editor','$2a$10$reDfpGVFptp2O9TmM/vqWOYEdz67vDCHuMcHFP0LG3CxI8c0eNmR6',NULL),(3,'sales_person@yahoo.com',_binary '','Sales','Person','$2a$10$r/FaKJr5QPSH1z2aQCKDSOhV1KqgnScp/TprZXW0jTgUNZbbT8WTS',NULL),(4,'shipper@yahoo.com',_binary '','Shipper','Shipper','$2a$10$zHjLYLzNU28QZ3X9.UmrZukwgi/Mzrh4ixSW//bMWXy5VIwm5gTJG',NULL),(5,'assistant@yahoo.com',_binary '','Assistant','Assistant','$2a$10$qHInhdJRMMf74khxXInYN.egsHe6newuj.Q04T9/4u9yy54Iz45Iy',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(3,2),(2,3),(4,4),(5,5);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-21 16:42:16
