INSERT INTO   `localization`(  `id`,  `codeICD`,  `name`,  `sex_spec`,  `select_for_report`) 
VALUE (102,'C00-C14, C46.2', 'губы, полости рта и глотки', 0,0);

INSERT INTO   `localization`(  `id`,  `codeICD`,  `name`,  `sex_spec`,  `select_for_report`) 
VALUE (103,'C17', 'тонкого кишечника', 0,0);

INSERT INTO   `localization`(  `id`,  `codeICD`,  `name`,  `sex_spec`,  `select_for_report`) 
VALUE (104,'C22-C26, C48', 'др. органов пищеварения', 0,0);

INSERT INTO   `localization`(  `id`,  `codeICD`,  `name`,  `sex_spec`,  `select_for_report`) 
VALUE (105,'C51, C52, C55, C57, C58', 'др. неуточ. женских половых органов', 2, 0);

INSERT INTO   `localization`(  `id`,  `codeICD`,  `name`,  `sex_spec`,  `select_for_report`) 
VALUE (106,'C65, C66, C68', 'др. мочевых органов', 0, 0);

INSERT INTO   `localization`(  `id`,  `codeICD`,  `name`,  `sex_spec`,  `select_for_report`) 
VALUE (107,'C30, C31, C69, C74-C80, C97, C46.1, C46.7-9', 'др. и неуточ. локализации', 0, 0);

INSERT INTO   `localization`(  `id`,  `codeICD`,  `name`,  `sex_spec`,  `select_for_report`) 
VALUE (108,'C37-C39', 'др. органов дыхания', 0, 0);

INSERT INTO   `localization`(  `id`,  `codeICD`,  `name`,  `sex_spec`,  `select_for_report`) 
VALUE (109,'C60, C63', 'др. мужских половых органов', 1, 0);

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
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

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
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';