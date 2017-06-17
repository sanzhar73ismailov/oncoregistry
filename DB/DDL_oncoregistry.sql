# SQL Manager 2007 for MySQL 4.4.0.3
# ---------------------------------------
# Host     : localhost
# Port     : 3306
# Database : oncoregistry


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE `oncoregistry`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

USE `oncoregistry`;

#
# Structure for the `age` table : 
#

CREATE TABLE `age` (
  `id` INTEGER(11) NOT NULL,
  `name` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `order` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `localization` table : 
#

CREATE TABLE `localization` (
  `id` INTEGER(11) NOT NULL,
  `codeICD` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL,
  `name` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `sex_spec` INTEGER(11) DEFAULT '0' COMMENT '0-both sex, 1-male only, 2-female only',
  `select_for_report` INTEGER(11) DEFAULT '1' COMMENT '1 - if should be selected in reports',
  `order` INTEGER(11) DEFAULT '1000',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `region` table : 
#

CREATE TABLE `region` (
  `id` INTEGER(11) NOT NULL,
  `name` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `order` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `sex` table : 
#

CREATE TABLE `sex` (
  `id` INTEGER(11) NOT NULL,
  `name` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `order` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `morbidityolap` table : 
#

CREATE TABLE `morbidityolap` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `region_id` INTEGER(11) DEFAULT NULL,
  `year` INTEGER(11) DEFAULT NULL,
  `localization_id` INTEGER(11) DEFAULT NULL,
  `sex_id` INTEGER(11) DEFAULT NULL,
  `age_id` INTEGER(11) DEFAULT NULL,
  `filename` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `morbidity` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `complex` (`region_id`, `year`, `localization_id`, `sex_id`, `age_id`),
  KEY `localization_id` (`localization_id`),
  KEY `sex_id` (`sex_id`),
  KEY `age_id` (`age_id`),
  CONSTRAINT `morbidityolap_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `morbidityolap_ibfk_2` FOREIGN KEY (`localization_id`) REFERENCES `localization` (`id`),
  CONSTRAINT `morbidityolap_ibfk_3` FOREIGN KEY (`sex_id`) REFERENCES `sex` (`id`),
  CONSTRAINT `morbidityolap_ibfk_4` FOREIGN KEY (`age_id`) REFERENCES `age` (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=525505 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `stage` table : 
#

CREATE TABLE `stage` (
  `id` INTEGER(11) NOT NULL,
  `name` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL,
  `order` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `morbidityolap2100` table : 
#

CREATE TABLE `morbidityolap2100` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `region_id` INTEGER(11) DEFAULT NULL,
  `year` INTEGER(11) DEFAULT NULL,
  `localization_id` INTEGER(11) DEFAULT NULL,
  `stage_id` INTEGER(11) DEFAULT NULL,
  `value` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `complex` (`region_id`, `year`, `localization_id`, `stage_id`),
  KEY `region_id` (`region_id`),
  KEY `localization_id` (`localization_id`),
  KEY `stage_id` (`stage_id`),
  CONSTRAINT `morbidityolap2100_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `morbidityolap2100_ibfk_2` FOREIGN KEY (`localization_id`) REFERENCES `localization` (`id`),
  CONSTRAINT `morbidityolap2100_ibfk_3` FOREIGN KEY (`stage_id`) REFERENCES `stage` (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=11731 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `morbiditysimple` table : 
#

CREATE TABLE `morbiditysimple` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `region_id` INTEGER(11) DEFAULT NULL,
  `year` INTEGER(11) DEFAULT NULL,
  `localization_id` INTEGER(11) DEFAULT NULL,
  `sex_id` INTEGER(11) DEFAULT NULL,
  `filename` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `ageall` INTEGER(11) DEFAULT NULL,
  `age0_4` INTEGER(11) DEFAULT NULL,
  `age5_9` INTEGER(11) DEFAULT NULL,
  `age10_14` INTEGER(11) DEFAULT NULL,
  `age15_17` INTEGER(11) DEFAULT NULL,
  `age18_19` INTEGER(11) DEFAULT NULL,
  `age15_19` INTEGER(11) DEFAULT NULL,
  `age20_24` INTEGER(11) DEFAULT NULL,
  `age25_29` INTEGER(11) DEFAULT NULL,
  `age15_29` INTEGER(11) DEFAULT NULL,
  `age30_34` INTEGER(11) DEFAULT NULL,
  `age35_39` INTEGER(11) DEFAULT NULL,
  `age40_44` INTEGER(11) DEFAULT NULL,
  `age45_49` INTEGER(11) DEFAULT NULL,
  `age50_54` INTEGER(11) DEFAULT NULL,
  `age55_59` INTEGER(11) DEFAULT NULL,
  `age60_64` INTEGER(11) DEFAULT NULL,
  `age65_69` INTEGER(11) DEFAULT NULL,
  `age70_74` INTEGER(11) DEFAULT NULL,
  `age75_79` INTEGER(11) DEFAULT NULL,
  `age80_84` INTEGER(11) DEFAULT NULL,
  `age70` INTEGER(11) DEFAULT NULL,
  `age85` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `complex` (`region_id`, `year`, `localization_id`, `sex_id`),
  KEY `localization_id` (`localization_id`),
  KEY `sex_id` (`sex_id`),
  CONSTRAINT `morbiditysimple_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `morbiditysimple_ibfk_2` FOREIGN KEY (`localization_id`) REFERENCES `localization` (`id`),
  CONSTRAINT `morbiditysimple_ibfk_3` FOREIGN KEY (`sex_id`) REFERENCES `sex` (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=22849 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `morbiditysimple2100` table : 
#

CREATE TABLE `morbiditysimple2100` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `region_id` INTEGER(11) DEFAULT NULL,
  `year` INTEGER(11) DEFAULT NULL,
  `localization_id` INTEGER(11) DEFAULT NULL,
  `filename` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `younger14Years` INTEGER(11) DEFAULT NULL,
  `registeredInTheStartOfYear` INTEGER(11) DEFAULT NULL,
  `vzyatoNaUchetThisYear_WithBeforeDefinedDiagnosis` INTEGER(11) DEFAULT NULL,
  `vzyatoNaUchetThisYear_FirstTime` INTEGER(11) DEFAULT NULL,
  `from3Group_DetectedDuringProfAll` INTEGER(11) DEFAULT NULL,
  `from3Group_DetectedDuringProf01And02Stage` INTEGER(11) DEFAULT NULL,
  `from3Group_DiagnosoConfirmedMorfologically` INTEGER(11) DEFAULT NULL,
  `from3Group_HadStage1and2` INTEGER(11) DEFAULT NULL,
  `from3Group_HadStage3` INTEGER(11) DEFAULT NULL,
  `from3Group_HadStage4` INTEGER(11) DEFAULT NULL,
  `struckOffTheRegisterAll` INTEGER(11) DEFAULT NULL,
  `struckOffTheRegisterGoOut` INTEGER(11) DEFAULT NULL,
  `struckOffTheRegisterDiagNotConfirmed` INTEGER(11) DEFAULT NULL,
  `struckOffTheRegisterNoInfo` INTEGER(11) DEFAULT NULL,
  `struckOffTheRegisterWithDiagBazalioma` INTEGER(11) DEFAULT NULL,
  `struckOffTheRegisterDiedFromAnotherDisease` INTEGER(11) DEFAULT NULL,
  `registeredInTheEndOfYearAll` INTEGER(11) DEFAULT NULL,
  `registeredInTheEndOfYear5YearsAndMore` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `complex` (`region_id`, `year`, `localization_id`, `younger14Years`),
  KEY `localization_id` (`localization_id`),
  CONSTRAINT `morbiditysimple2100_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `morbiditysimple2100_ibfk_2` FOREIGN KEY (`localization_id`) REFERENCES `localization` (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=2347 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `morbiditysimple2200` table : 
#

CREATE TABLE `morbiditysimple2200` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `region_id` INTEGER(11) DEFAULT NULL,
  `year` INTEGER(11) DEFAULT NULL,
  `localization_id` INTEGER(11) DEFAULT NULL,
  `value` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `complex` (`region_id`, `year`, `localization_id`),
  KEY `localization_id` (`localization_id`),
  CONSTRAINT `morbiditysimple2200_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `morbiditysimple2200_ibfk_2` FOREIGN KEY (`localization_id`) REFERENCES `localization` (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=2041 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `mortalityolap` table : 
#

CREATE TABLE `mortalityolap` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `region_id` INTEGER(11) DEFAULT NULL,
  `year` INTEGER(11) DEFAULT NULL,
  `localization_id` INTEGER(11) DEFAULT NULL,
  `sex_id` INTEGER(11) DEFAULT NULL,
  `age_id` INTEGER(11) DEFAULT NULL,
  `filename` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `mortality` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `complex` (`region_id`, `year`, `localization_id`, `sex_id`, `age_id`),
  KEY `localization_id` (`localization_id`),
  KEY `sex_id` (`sex_id`),
  KEY `age_id` (`age_id`),
  CONSTRAINT `mortalityolap_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `mortalityolap_ibfk_2` FOREIGN KEY (`localization_id`) REFERENCES `localization` (`id`),
  CONSTRAINT `mortalityolap_ibfk_3` FOREIGN KEY (`sex_id`) REFERENCES `sex` (`id`),
  CONSTRAINT `mortalityolap_ibfk_4` FOREIGN KEY (`age_id`) REFERENCES `age` (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=97921 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `mortalitysimple` table : 
#

CREATE TABLE `mortalitysimple` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `region_id` INTEGER(11) DEFAULT NULL,
  `year` INTEGER(11) DEFAULT NULL,
  `localization_id` INTEGER(11) DEFAULT NULL,
  `sex_id` INTEGER(11) DEFAULT NULL,
  `filename` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `ageall` INTEGER(11) DEFAULT NULL,
  `age0_4` INTEGER(11) DEFAULT NULL,
  `age5_9` INTEGER(11) DEFAULT NULL,
  `age10_14` INTEGER(11) DEFAULT NULL,
  `age15_19` INTEGER(11) DEFAULT NULL,
  `age20_24` INTEGER(11) DEFAULT NULL,
  `age25_29` INTEGER(11) DEFAULT NULL,
  `age30_34` INTEGER(11) DEFAULT NULL,
  `age35_39` INTEGER(11) DEFAULT NULL,
  `age40_44` INTEGER(11) DEFAULT NULL,
  `age45_49` INTEGER(11) DEFAULT NULL,
  `age50_54` INTEGER(11) DEFAULT NULL,
  `age55_59` INTEGER(11) DEFAULT NULL,
  `age60_64` INTEGER(11) DEFAULT NULL,
  `age65_69` INTEGER(11) DEFAULT NULL,
  `age70` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `complex` (`region_id`, `year`, `localization_id`, `sex_id`),
  KEY `localization_id` (`localization_id`),
  KEY `sex_id` (`sex_id`),
  CONSTRAINT `mortalitysimple_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `mortalitysimple_ibfk_2` FOREIGN KEY (`localization_id`) REFERENCES `localization` (`id`),
  CONSTRAINT `mortalitysimple_ibfk_3` FOREIGN KEY (`sex_id`) REFERENCES `sex` (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=6121 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `region_type` table : 
#

CREATE TABLE `region_type` (
  `id` INTEGER(11) NOT NULL,
  `name` VARCHAR(20) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `population` table : 
#

CREATE TABLE `population` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `year` INTEGER(11) NOT NULL,
  `region_id` INTEGER(11) NOT NULL,
  `sex_id` INTEGER(11) NOT NULL,
  `age_id` INTEGER(11) NOT NULL,
  `region_type_id` INTEGER(11) NOT NULL,
  `value` INTEGER(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `year` (`year`, `region_id`, `sex_id`, `age_id`, `region_type_id`),
  KEY `region_id` (`region_id`),
  KEY `sex_id` (`sex_id`),
  KEY `age_id` (`age_id`),
  KEY `region_type_id` (`region_type_id`),
  CONSTRAINT `population_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `population_ibfk_2` FOREIGN KEY (`sex_id`) REFERENCES `sex` (`id`),
  CONSTRAINT `population_ibfk_3` FOREIGN KEY (`region_type_id`) REFERENCES `region_type` (`id`),
  CONSTRAINT `population_ibfk_4` FOREIGN KEY (`age_id`) REFERENCES `age` (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=12139 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `populationsimple` table : 
#

CREATE TABLE `populationsimple` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `year` INTEGER(11) NOT NULL,
  `region_id` INTEGER(11) NOT NULL,
  `age_id` INTEGER(11) NOT NULL,
  `value_all_sex_both` INTEGER(11) NOT NULL,
  `value_all_sex_male` INTEGER(11) NOT NULL,
  `value_all_sex_female` INTEGER(11) NOT NULL,
  `value_city_sex_both` INTEGER(11) NOT NULL,
  `value_city_sex_male` INTEGER(11) NOT NULL,
  `value_city_sex_female` INTEGER(11) NOT NULL,
  `value_village_sex_both` INTEGER(11) NOT NULL,
  `value_village_sex_male` INTEGER(11) NOT NULL,
  `value_village_sex_female` INTEGER(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `year` (`year`, `region_id`, `age_id`),
  KEY `region_id` (`region_id`),
  KEY `age_id` (`age_id`),
  CONSTRAINT `populationsimple_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `populationsimple_ibfk_2` FOREIGN KEY (`age_id`) REFERENCES `age` (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=4047 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `world_standart` table : 
#

CREATE TABLE `world_standart` (
  `age_id` INTEGER(11) NOT NULL COMMENT 'Восраст (внеш ключ)',
  `value` INTEGER(11) DEFAULT NULL COMMENT 'Значение',
  PRIMARY KEY (`age_id`)
)ENGINE=MyISAM
ROW_FORMAT=FIXED CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='Мировой стандарт на 100000 населения';



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;