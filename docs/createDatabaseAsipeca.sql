-- MySQL dump 10.13  Distrib 8.0.12, for macos10.13 (x86_64)
--
-- Host: localhost    Database: asipeca
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `assi`
--

DROP TABLE IF EXISTS `assi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assi` (
  `assi_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `nasc` date DEFAULT NULL,
  `gene` enum('MASCULINO','FEMININO','LGBTQ') DEFAULT NULL,
  `tipo_cada` set('FORTACELCIMENTO','SAUDE') DEFAULT NULL,
  `rg` varchar(9) DEFAULT NULL COMMENT 'Registro Geral',
  `nis` varchar(50) DEFAULT NULL COMMENT 'Numero cadastro social do governo',
  `sus` varchar(30) DEFAULT NULL COMMENT 'Cadastro carteira SUS - Sistema Unico de Saude',
  `cpf` bigint(11) unsigned DEFAULT NULL COMMENT 'Cadastro de Pessoa Fisica',
  `situ_mora_id` bigint(20) unsigned DEFAULT NULL,
  `esco_id` bigint(20) unsigned DEFAULT NULL,
  `situ_ocup_id` bigint(20) unsigned DEFAULT NULL,
  `rend_mens_id` bigint(20) unsigned DEFAULT NULL,
  `nume_memb_fami` tinyint(1) unsigned DEFAULT NULL,
  `data_diag` datetime DEFAULT NULL COMMENT 'Data do primeiro diagnostico',
  `fase_esta_id` bigint(20) unsigned DEFAULT NULL COMMENT 'Fase ou estagio da doenca',
  `situ_saud_id` bigint(20) unsigned DEFAULT NULL COMMENT 'Situacao de saude',
  `fami_part` tinyint(1) DEFAULT NULL COMMENT 'Familiar participa dos atendimentos',
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`assi_id`),
  UNIQUE KEY `unk_assi_cpf` (`cpf`),
  KEY `idx_assi_situ_mora` (`situ_mora_id`),
  KEY `idx_assi_esco` (`esco_id`),
  KEY `idx_assi_situ_ocup` (`situ_ocup_id`),
  KEY `idx_assi_rend_mens` (`rend_mens_id`),
  KEY `idx_assi_fase_esta` (`fase_esta_id`),
  KEY `idx_situ_saud` (`situ_saud_id`),
  CONSTRAINT `fky_assi_esco` FOREIGN KEY (`esco_id`) REFERENCES `esco` (`esco_id`),
  CONSTRAINT `fky_assi_fase_esta` FOREIGN KEY (`fase_esta_id`) REFERENCES `fase_esta` (`fase_esta_id`),
  CONSTRAINT `fky_assi_rend_mens` FOREIGN KEY (`rend_mens_id`) REFERENCES `rend_mens` (`rend_mens_id`),
  CONSTRAINT `fky_assi_situ_mora` FOREIGN KEY (`situ_mora_id`) REFERENCES `situ_mora` (`situ_mora_id`),
  CONSTRAINT `fky_assi_situ_ocup` FOREIGN KEY (`situ_ocup_id`) REFERENCES `situ_ocup` (`situ_ocup_id`),
  CONSTRAINT `fky_assi_situ_saud` FOREIGN KEY (`situ_saud_id`) REFERENCES `situ_saud` (`situ_saud_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assi`
--

LOCK TABLES `assi` WRITE;
/*!40000 ALTER TABLE `assi` DISABLE KEYS */;
/*!40000 ALTER TABLE `assi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assi_habi_saud`
--

DROP TABLE IF EXISTS `assi_habi_saud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assi_habi_saud` (
  `assi_habi_saud_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `assi_id` bigint(20) unsigned NOT NULL,
  `habi_saud_id` bigint(20) unsigned NOT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`assi_habi_saud_id`),
  UNIQUE KEY `unk_assi_habi_saud` (`assi_id`,`habi_saud_id`),
  KEY `fky_assi_habi_saud_habi_saud` (`habi_saud_id`),
  CONSTRAINT `fky_assi_habi_saud_assi` FOREIGN KEY (`assi_id`) REFERENCES `assi` (`assi_id`),
  CONSTRAINT `fky_assi_habi_saud_habi_saud` FOREIGN KEY (`habi_saud_id`) REFERENCES `habi_saud` (`habi_saud_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assi_habi_saud`
--

LOCK TABLES `assi_habi_saud` WRITE;
/*!40000 ALTER TABLE `assi_habi_saud` DISABLE KEYS */;
/*!40000 ALTER TABLE `assi_habi_saud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assi_hist_fami`
--

DROP TABLE IF EXISTS `assi_hist_fami`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assi_hist_fami` (
  `assi_hist_fami_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `assi_id` bigint(20) unsigned NOT NULL,
  `hist_fami_id` bigint(20) unsigned NOT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`assi_hist_fami_id`),
  UNIQUE KEY `unk_assi_hist_fami` (`assi_id`,`hist_fami_id`),
  KEY `fky_assi_hist_fami_hist_fami` (`hist_fami_id`),
  CONSTRAINT `fky_assi_hist_fami_assi` FOREIGN KEY (`assi_id`) REFERENCES `assi` (`assi_id`),
  CONSTRAINT `fky_assi_hist_fami_hist_fami` FOREIGN KEY (`hist_fami_id`) REFERENCES `hist_fami` (`hist_fami_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assi_hist_fami`
--

LOCK TABLES `assi_hist_fami` WRITE;
/*!40000 ALTER TABLE `assi_hist_fami` DISABLE KEYS */;
/*!40000 ALTER TABLE `assi_hist_fami` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assi_iden_aten`
--

DROP TABLE IF EXISTS `assi_iden_aten`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assi_iden_aten` (
  `assi_iden_aten_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `assi_id` bigint(20) unsigned NOT NULL,
  `iden_aten_id` bigint(20) unsigned NOT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`assi_iden_aten_id`),
  UNIQUE KEY `unk_assi_iden_aten` (`assi_id`,`iden_aten_id`),
  KEY `fky_assi_iden_aten_iden_aten` (`iden_aten_id`),
  CONSTRAINT `fky_assi_iden_aten_assi` FOREIGN KEY (`assi_id`) REFERENCES `assi` (`assi_id`),
  CONSTRAINT `fky_assi_iden_aten_iden_aten` FOREIGN KEY (`iden_aten_id`) REFERENCES `iden_aten` (`iden_aten_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assi_iden_aten`
--

LOCK TABLES `assi_iden_aten` WRITE;
/*!40000 ALTER TABLE `assi_iden_aten` DISABLE KEYS */;
/*!40000 ALTER TABLE `assi_iden_aten` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assi_iden_inte`
--

DROP TABLE IF EXISTS `assi_iden_inte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assi_iden_inte` (
  `assi_iden_inte_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `assi_id` bigint(20) unsigned NOT NULL,
  `iden_inte_id` bigint(20) unsigned NOT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`assi_iden_inte_id`),
  UNIQUE KEY `unk_assi_iden_inte` (`assi_id`,`iden_inte_id`),
  KEY `fky_assi_iden_inte_iden_inte` (`iden_inte_id`),
  CONSTRAINT `fky_assi_iden_inte_assi` FOREIGN KEY (`assi_id`) REFERENCES `assi` (`assi_id`),
  CONSTRAINT `fky_assi_iden_inte_iden_inte` FOREIGN KEY (`iden_inte_id`) REFERENCES `iden_inte` (`iden_inte_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assi_iden_inte`
--

LOCK TABLES `assi_iden_inte` WRITE;
/*!40000 ALTER TABLE `assi_iden_inte` DISABLE KEYS */;
/*!40000 ALTER TABLE `assi_iden_inte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assi_inte_saud`
--

DROP TABLE IF EXISTS `assi_inte_saud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assi_inte_saud` (
  `assi_inte_saud_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `assi_id` bigint(20) unsigned NOT NULL,
  `inte_saud_id` bigint(20) unsigned NOT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`assi_inte_saud_id`),
  UNIQUE KEY `unk_assi_inte_saud` (`assi_id`,`inte_saud_id`),
  KEY `fky_assi_inte_saud_inte_saud` (`inte_saud_id`),
  CONSTRAINT `fky_assi_inte_saud_assi` FOREIGN KEY (`assi_id`) REFERENCES `assi` (`assi_id`),
  CONSTRAINT `fky_assi_inte_saud_inte_saud` FOREIGN KEY (`inte_saud_id`) REFERENCES `inte_saud` (`inte_saud_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assi_inte_saud`
--

LOCK TABLES `assi_inte_saud` WRITE;
/*!40000 ALTER TABLE `assi_inte_saud` DISABLE KEYS */;
/*!40000 ALTER TABLE `assi_inte_saud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assi_pato_asso`
--

DROP TABLE IF EXISTS `assi_pato_asso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assi_pato_asso` (
  `assi_pato_asso_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `assi_id` bigint(20) unsigned NOT NULL,
  `pato_asso_id` bigint(20) unsigned NOT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`assi_pato_asso_id`),
  UNIQUE KEY `unk_assi_pato_asso` (`assi_id`,`pato_asso_id`),
  KEY `fky_assi_pato_asso_pato_asso` (`pato_asso_id`),
  CONSTRAINT `fky_assi_pato_asso_assi` FOREIGN KEY (`assi_id`) REFERENCES `assi` (`assi_id`),
  CONSTRAINT `fky_assi_pato_asso_pato_asso` FOREIGN KEY (`pato_asso_id`) REFERENCES `pato_asso` (`pato_asso_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assi_pato_asso`
--

LOCK TABLES `assi_pato_asso` WRITE;
/*!40000 ALTER TABLE `assi_pato_asso` DISABLE KEYS */;
/*!40000 ALTER TABLE `assi_pato_asso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assi_situ_saud`
--

DROP TABLE IF EXISTS `assi_situ_saud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assi_situ_saud` (
  `assi_situ_saud_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `assi_id` bigint(20) unsigned NOT NULL,
  `situ_saud_id` bigint(20) unsigned NOT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`assi_situ_saud_id`),
  UNIQUE KEY `unk_assi_situ_saud` (`assi_id`,`situ_saud_id`),
  KEY `fky_assi_situ_saud_situ_saud` (`situ_saud_id`),
  CONSTRAINT `fky_assi_situ_saud_assi` FOREIGN KEY (`assi_id`) REFERENCES `assi` (`assi_id`),
  CONSTRAINT `fky_assi_situ_saud_situ_saud` FOREIGN KEY (`situ_saud_id`) REFERENCES `situ_saud` (`situ_saud_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assi_situ_saud`
--

LOCK TABLES `assi_situ_saud` WRITE;
/*!40000 ALTER TABLE `assi_situ_saud` DISABLE KEYS */;
/*!40000 ALTER TABLE `assi_situ_saud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assi_tipo_canc`
--

DROP TABLE IF EXISTS `assi_tipo_canc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assi_tipo_canc` (
  `assi_tipo_canc_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `assi_id` bigint(20) unsigned NOT NULL,
  `tipo_canc_id` bigint(20) unsigned NOT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`assi_tipo_canc_id`),
  UNIQUE KEY `unk_assi_tipo_canc` (`assi_id`,`tipo_canc_id`),
  KEY `fky_assi_tipo_canc_tipo_canc` (`tipo_canc_id`),
  CONSTRAINT `fky_assi_tipo_canc_assi` FOREIGN KEY (`assi_id`) REFERENCES `assi` (`assi_id`),
  CONSTRAINT `fky_assi_tipo_canc_tipo_canc` FOREIGN KEY (`tipo_canc_id`) REFERENCES `tipo_canc` (`tipo_canc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assi_tipo_canc`
--

LOCK TABLES `assi_tipo_canc` WRITE;
/*!40000 ALTER TABLE `assi_tipo_canc` DISABLE KEYS */;
/*!40000 ALTER TABLE `assi_tipo_canc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assi_vinc_rede_apoi`
--

DROP TABLE IF EXISTS `assi_vinc_rede_apoi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assi_vinc_rede_apoi` (
  `assi_vinc_rede_apoi_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `assi_id` bigint(20) unsigned NOT NULL,
  `vinc_rede_apoi_id` bigint(20) unsigned NOT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`assi_vinc_rede_apoi_id`),
  UNIQUE KEY `unk_assi_vinc_rede_apoi` (`assi_id`,`vinc_rede_apoi_id`),
  KEY `fky_assi_vinc_rede_apoi_vinc_rede_apoi` (`vinc_rede_apoi_id`),
  CONSTRAINT `fky_assi_vinc_rede_apoi_assi` FOREIGN KEY (`assi_id`) REFERENCES `assi` (`assi_id`),
  CONSTRAINT `fky_assi_vinc_rede_apoi_vinc_rede_apoi` FOREIGN KEY (`vinc_rede_apoi_id`) REFERENCES `vinc_rede_apoi` (`vinc_rede_apoi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assi_vinc_rede_apoi`
--

LOCK TABLES `assi_vinc_rede_apoi` WRITE;
/*!40000 ALTER TABLE `assi_vinc_rede_apoi` DISABLE KEYS */;
/*!40000 ALTER TABLE `assi_vinc_rede_apoi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `esco`
--

DROP TABLE IF EXISTS `esco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `esco` (
  `esco_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`esco_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `esco`
--

LOCK TABLES `esco` WRITE;
/*!40000 ALTER TABLE `esco` DISABLE KEYS */;
INSERT INTO `esco` VALUES (1,'Iletrado','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(2,'Fundamental incompleto','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(3,'Fundamental completo','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(4,'Ensino médio incompleto','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(5,'Ensino médio completo','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(6,'Superior incompleto','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(7,'Superior completo','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1);
/*!40000 ALTER TABLE `esco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fase_esta`
--

DROP TABLE IF EXISTS `fase_esta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fase_esta` (
  `fase_esta_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`fase_esta_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fase_esta`
--

LOCK TABLES `fase_esta` WRITE;
/*!40000 ALTER TABLE `fase_esta` DISABLE KEYS */;
INSERT INTO `fase_esta` VALUES (1,'Inicial','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(2,'Avançado','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(3,'Reencidiva','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(4,'Metástase','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(5,'Outros','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1);
/*!40000 ALTER TABLE `fase_esta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `habi_saud`
--

DROP TABLE IF EXISTS `habi_saud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `habi_saud` (
  `habi_saud_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`habi_saud_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `habi_saud`
--

LOCK TABLES `habi_saud` WRITE;
/*!40000 ALTER TABLE `habi_saud` DISABLE KEYS */;
INSERT INTO `habi_saud` VALUES (1,'Fumante','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(2,'Uso de Bebida Alcolica','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(3,'Sobre peso','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(4,'Abaixo do Peso','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(5,'Faz atividade fisica','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(6,'Prevenção','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(7,'Outros','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1);
/*!40000 ALTER TABLE `habi_saud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hist_fami`
--

DROP TABLE IF EXISTS `hist_fami`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hist_fami` (
  `hist_fami_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`hist_fami_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hist_fami`
--

LOCK TABLES `hist_fami` WRITE;
/*!40000 ALTER TABLE `hist_fami` DISABLE KEYS */;
INSERT INTO `hist_fami` VALUES (1,'Cardíaco','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(2,'Diabetes','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(3,'Hipertensão','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(4,'Hiper ou Hipotireoidismo','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(5,'Doenças dos ossos','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(6,'Depressão','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(7,'Saúde mental','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(8,'Câncer','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1);
/*!40000 ALTER TABLE `hist_fami` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `idad`
--

DROP TABLE IF EXISTS `idad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `idad` (
  `idad_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `limi_infe` smallint(1) DEFAULT NULL,
  `limi_supe` smallint(1) DEFAULT NULL,
  PRIMARY KEY (`idad_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabela para ser utilizada apena para os relatorios e graficos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `idad`
--

LOCK TABLES `idad` WRITE;
/*!40000 ALTER TABLE `idad` DISABLE KEYS */;
INSERT INTO `idad` VALUES (1,'18 a 25 anos',18,26),(2,'25 a 35 anos',26,35),(3,'35 a 45 anos',36,45),(4,'45 a 60 anos',46,60),(5,'acima de 60 anos',61,NULL);
/*!40000 ALTER TABLE `idad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iden_aten`
--

DROP TABLE IF EXISTS `iden_aten`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `iden_aten` (
  `iden_aten_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`iden_aten_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iden_aten`
--

LOCK TABLES `iden_aten` WRITE;
/*!40000 ALTER TABLE `iden_aten` DISABLE KEYS */;
INSERT INTO `iden_aten` VALUES (1,'Médico oncologista','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(2,'Dentista','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(3,'Nutricionista','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(4,'Terapia ocupacional','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(5,'Fisioterapeuta','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(6,'Reflexoterapia','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(7,'Psicóloga','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(8,'Enfermagem','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(9,'Oficinal','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(10,'Grupo de terapia','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(11,'Atividade física','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(12,'Fonoaudióloga','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(13,'Assistente social','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(14,'Assistência jurídica','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(15,'Constelação familiar','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(16,'Grupo de apoio emocional','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(17,'Acupuntura','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(18,'Física quântica','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(19,'Yoga','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(20,'Dança','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(21,'Laser terapia','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(22,'Grupo de homens','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(23,'Visita domiciliar','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1);
/*!40000 ALTER TABLE `iden_aten` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iden_inte`
--

DROP TABLE IF EXISTS `iden_inte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `iden_inte` (
  `iden_inte_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`iden_inte_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iden_inte`
--

LOCK TABLES `iden_inte` WRITE;
/*!40000 ALTER TABLE `iden_inte` DISABLE KEYS */;
INSERT INTO `iden_inte` VALUES (1,'Alimetação Enteral','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(2,'Cesta Básica','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(3,'Cesta Verde','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(4,'Leite Vaca / Soja','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(5,'Emprestimos','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(6,'Fralda Geriátrica','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(7,'Medicamentos','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(8,'Equipamentos','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1);
/*!40000 ALTER TABLE `iden_inte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inte_saud`
--

DROP TABLE IF EXISTS `inte_saud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `inte_saud` (
  `inte_saud_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`inte_saud_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inte_saud`
--

LOCK TABLES `inte_saud` WRITE;
/*!40000 ALTER TABLE `inte_saud` DISABLE KEYS */;
INSERT INTO `inte_saud` VALUES (1,'Médico Oncologista','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(2,'Dentista','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(3,'Nutricionista','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(4,'Terapeuta Ocupacional','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(5,'Fisioterapeuta','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(6,'Terapias complementares','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(7,'Psicologa','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(8,'Enfermagem','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(9,'Oficinas','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(10,'Grupo de terapia','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(11,'Atividade Fisica','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(12,'Grupo de Orientação','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(13,'Revida','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1);
/*!40000 ALTER TABLE `inte_saud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nume_memb_fami`
--

DROP TABLE IF EXISTS `nume_memb_fami`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `nume_memb_fami` (
  `nume_memb_fami_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `limi_infe` smallint(1) DEFAULT NULL,
  `limi_supe` smallint(1) DEFAULT NULL,
  PRIMARY KEY (`nume_memb_fami_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nume_memb_fami`
--

LOCK TABLES `nume_memb_fami` WRITE;
/*!40000 ALTER TABLE `nume_memb_fami` DISABLE KEYS */;
INSERT INTO `nume_memb_fami` VALUES (1,'1 a 3 membros',1,3),(2,'4 a 5 membros',4,5),(3,'6 a 8 membros',6,8),(4,'mais de 8 membros',9,NULL);
/*!40000 ALTER TABLE `nume_memb_fami` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pato_asso`
--

DROP TABLE IF EXISTS `pato_asso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `pato_asso` (
  `pato_asso_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`pato_asso_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pato_asso`
--

LOCK TABLES `pato_asso` WRITE;
/*!40000 ALTER TABLE `pato_asso` DISABLE KEYS */;
INSERT INTO `pato_asso` VALUES (1,'Cardíaco','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(2,'Diabetes','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(3,'Hipertensão','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(4,'Hiper ou Hipotireoidismo','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(5,'Doenças dos ossos','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(6,'Depressão','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(7,'Saúde mental','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(8,'Câncer','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1);
/*!40000 ALTER TABLE `pato_asso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rend_mens`
--

DROP TABLE IF EXISTS `rend_mens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rend_mens` (
  `rend_mens_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`rend_mens_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rend_mens`
--

LOCK TABLES `rend_mens` WRITE;
/*!40000 ALTER TABLE `rend_mens` DISABLE KEYS */;
INSERT INTO `rend_mens` VALUES (1,'Sem renda','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(2,'Até 1 salário mínimo','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(3,'Mais de 1 salário mínimo','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(4,'Mais de 2 salários mínimos','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(5,'Mais de 3 salários mínimos','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(6,'Superior a 4 salários mínimos','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1);
/*!40000 ALTER TABLE `rend_mens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `situ_mora`
--

DROP TABLE IF EXISTS `situ_mora`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `situ_mora` (
  `situ_mora_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`situ_mora_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `situ_mora`
--

LOCK TABLES `situ_mora` WRITE;
/*!40000 ALTER TABLE `situ_mora` DISABLE KEYS */;
INSERT INTO `situ_mora` VALUES (1,'Alugada','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(2,'Cedida','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(3,'Própria','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(4,'Ocupação irregular','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(5,'Outras','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1);
/*!40000 ALTER TABLE `situ_mora` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `situ_ocup`
--

DROP TABLE IF EXISTS `situ_ocup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `situ_ocup` (
  `situ_ocup_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`situ_ocup_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `situ_ocup`
--

LOCK TABLES `situ_ocup` WRITE;
/*!40000 ALTER TABLE `situ_ocup` DISABLE KEYS */;
INSERT INTO `situ_ocup` VALUES (1,'Aposentados','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(2,'Trabalho formal','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(3,'Autônomo','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(4,'Desempregado','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(5,'Do Lar','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(6,'LOAS','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(7,'Pencionista','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1),(8,'Outros','2018-09-19 18:12:56',1,'2018-09-19 18:12:56',1);
/*!40000 ALTER TABLE `situ_ocup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `situ_saud`
--

DROP TABLE IF EXISTS `situ_saud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `situ_saud` (
  `situ_saud_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`situ_saud_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `situ_saud`
--

LOCK TABLES `situ_saud` WRITE;
/*!40000 ALTER TABLE `situ_saud` DISABLE KEYS */;
INSERT INTO `situ_saud` VALUES (1,'Pré Cirurgico','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(2,'Pós cirurgico','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(3,'Acamado','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(4,'Quimioterapia','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(5,'Radioterapia','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(6,'Acompanhamento Fisico','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(7,'Acompanhamento Psiquico','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(8,'Desenformado','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(9,'Paleativo','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(10,'Outros','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1);
/*!40000 ALTER TABLE `situ_saud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `temp_diag`
--

DROP TABLE IF EXISTS `temp_diag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `temp_diag` (
  `temp_diag_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `limi_infe` smallint(1) DEFAULT NULL,
  `limi_supe` smallint(1) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`temp_diag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabela para ser utilizada apena para os relatorios e graficos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `temp_diag`
--

LOCK TABLES `temp_diag` WRITE;
/*!40000 ALTER TABLE `temp_diag` DISABLE KEYS */;
INSERT INTO `temp_diag` VALUES (1,'0 a 1 anos',0,2,'2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(2,'2 a 3 anos',2,3,'2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(3,'3 a 4 anos',3,4,'2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(4,'4 a 5 anos',4,5,'2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(5,'Mais de 5 anos',4,5,'2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1);
/*!40000 ALTER TABLE `temp_diag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_canc`
--

DROP TABLE IF EXISTS `tipo_canc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tipo_canc` (
  `tipo_canc_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`tipo_canc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_canc`
--

LOCK TABLES `tipo_canc` WRITE;
/*!40000 ALTER TABLE `tipo_canc` DISABLE KEYS */;
INSERT INTO `tipo_canc` VALUES (1,'Mama','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(2,'Ovário','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(3,'Próstata','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(4,'Intestino','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(5,'Pulmão','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(6,'Cabeça/Pescoço','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(7,'Digestivo','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(8,'Outros','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1);
/*!40000 ALTER TABLE `tipo_canc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vinc_rede_apoi`
--

DROP TABLE IF EXISTS `vinc_rede_apoi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vinc_rede_apoi` (
  `vinc_rede_apoi_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dscr` char(30) DEFAULT NULL,
  `cria_em` timestamp NULL DEFAULT NULL,
  `cria_po` bigint(20) unsigned DEFAULT NULL,
  `alte_em` timestamp NULL DEFAULT NULL,
  `alte_po` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`vinc_rede_apoi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vinc_rede_apoi`
--

LOCK TABLES `vinc_rede_apoi` WRITE;
/*!40000 ALTER TABLE `vinc_rede_apoi` DISABLE KEYS */;
INSERT INTO `vinc_rede_apoi` VALUES (1,'CRAS/CREAS','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(2,'ONGS','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(3,'UBS','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(4,'INSS','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(5,'Santa Casa','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(6,'Regional','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(7,'Leonor','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(8,'Particular','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1),(9,'Clube do Idoso','2018-09-19 18:12:57',1,'2018-09-19 18:12:57',1);
/*!40000 ALTER TABLE `vinc_rede_apoi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-15 14:10:23
