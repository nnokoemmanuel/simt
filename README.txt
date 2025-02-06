
=================================
FEBRUARY 2021
=================================
INSERT INTO `inspectordb`.`role` (`id`, `description`, `name`) VALUES (NULL, 'ROLE MANAGE APPLICANT', 'ROLE_MANAGE_APPLICANT');


INSERT INTO `role` (`id`, `description`, `name`) VALUES 
(null, 'ROLE MANAGE APPLICANTS', 'ROLE_MANAGE_APPLICANT');
INSERT INTO `role` (`id`, `description`, `name`) VALUES 
(null, 'ROLE OPEN INSLIP', 'ROLE_OPEN_INSLIP');
INSERT INTO `role` (`id`, `name`, `description`) VALUES
(null, 'ROLE_PV_CONTROLLER_STUDENT', 'ROLE PV CONTROLLER STUDENT');


=========ARCHIVES STATUSES================
REGISTERED - for archives at the state registered
VALIDATED - for archives at the state validated

==========inslip Status=============
INSERT INTO `in_slip_status` (`id`, `description`, `name`) VALUES
(1, 'REGISTERED', 'REGISTERED'),
(2, 'OPENED', 'OPENED'),
(3, 'CLOSED', 'CLOSED'),
(4, 'CONTROLLED. INSLIP IS OK OR HAS NO INCONSISTENCIES', 'CONTROLLED(SLIP OK)'),
(5, 'CONTROLLED. INSLIP HAS INCONSISTENCIES', 'CONTROLLED(SLIP NOT OK)');


=========process_type
INSERT INTO `application_status` (`id`, `description`) VALUES
(1, 'APPLICATION REGISTERED'),
(2, 'APPLICATION REJECTED'),
(3, 'APPLICATION CANCELLED'),
(4, 'APPLICATION CONFIRMED'),
(5, 'CAPACITY PRINTED'),
(6, 'SUCCESSFULLY PRINTED'),
(7, 'CONFIRMED REJECTED'),
(8, 'UNSUCCESSFULLY PRINTED'),
(9, 'DELIVERED'),
(10, 'DELIVERED WITH REJECT'),
(11, 'AUTHORIZED REPRINT');


=========process_type
INSERT INTO `process_type` (`id`,`abbreviation`, `description`) VALUES
('1','NT' ,'NEW TRANSCRIPT'),
('2','NC', 'NEW CERTIFICATE'),
('3', 'NP','NEW PROFESSIONAL_CARD'),
('4','DT','DUPLICATE OF TRANSCRIPT'),
('5', 'DC','DUPLICATE OF CERTIFICATE'),
('6','DP' ,'DUPLICATE OF PROFESSIONAL_CARD');



=== role

INSERT INTO `role` (`id`, `name`, `description`) VALUES
(12, 'ROLE_VIEW_RECORD', 'ROLE VIEW RECORD');

INSERT INTO `role` (`id`, `name`, `description`) VALUES 
(null, 'ROLE_GENERATE_PV_INSLIP', 'ROLE GENERATE PV INSLIP');

INSERT INTO `role` (`id`, `name`, `description`) VALUES 
(null, 'ROLE_VIEW_FILE', 'ROLE VIEW FILE');

INSERT INTO `role` (`id`, `name`, `description`) VALUES 
(null, 'ROLE_PROCESS_FILE', 'ROLE PROCESS FILE');

INSERT INTO `role` (`id`, `name`, `description`) VALUES 
(null, 'ROLE_VIEW_ARCHIVE', 'ROLE VIEW ARCHIVE');

INSERT INTO `role` (`id`, `name`, `description`) VALUES 
(null, 'ROLE_MANAGE_ARCHIVE', 'ROLE MANAGE ARCHIVE');

INSERT INTO `role` (`id`, `name`, `description`) VALUES 
(null, 'ROLE_ARCHIVE_CONTROLLER', 'ROLE ARCHIVE CONTROLLER');

INSERT INTO `role` (`id`, `name`, `description`) VALUES 
(null, 'ROLE_VIEW_OUTSLIP', 'ROLE VIEW OUTSLIP');

INSERT INTO `role` (`id`, `name`, `description`) VALUES 
(null, 'ROLE_VIEW_INSLIP', 'ROLE VIEW INSLIP');


ROLE_OPEN_INSLIP
================================================================================================
SIMT PROJECT UPDATES
================================================================================================

// ajour de l'index dans la table person
ALTER TABLE `person` ADD FULLTEXT KEY `pob` (`pob`,`surname`,`given_name`);

MODIFY YOUR APPLICATION.PROPERTIES SUCH THAT THE FOLLOWING VALUES ARE UPDATED

professional.card.preview.folder=/var/simt/professional_card/preview/
professional.card.folder=/var/simt/professional_card/

ALTER TABLE `speciality` ADD COLUMN `name_in_english` VARCHAR(50) DEFAULT NULL;
ALTER TABLE `student_session` ADD COLUMN `final_result` FLOAT DEFAULT 0;
ALTER TABLE `module` ADD COLUMN `status` INTEGER(11) DEFAULT 1;
INSERT INTO `speciality` (`id`, `abbreviation`, `name`, `name_in_english`) VALUES
(1, 'MAE', 'Moniteur Auto Ecole', 'Driving School Instructor'),
(2, 'IPCSR', 'Inspecteur du Permis de Conduire et de la Securite Routier', 'Safety Road And Driving License Inspector'),
(3, 'DPCSR', 'Delegues du Permis de Conduire et de la Securite Routier', 'Safety Road And Driving License Delegate');

INSERT INTO `student_session_status` (`id`, `description`) VALUES (NULL, 'DEMISSIONNAIRE');

ROLE TO MANAGE AND VIEW TRAINING CENTERS
INSERT INTO `role` (`id`, `name`, `description`) VALUES
(null, 'ROLE_MANAGE_TRAINNING_CENTERS', 'ROLE TO MANAGE TRAINING CENTERS');
INSERT INTO `role` (`id`, `name`, `description`) VALUES
(null, 'ROLE_VIEW_TRAINNING_CENTERS', 'ROLE TO VIEW TRAINING CENTERS');
ROLE TO PRINT TRANSCRIPT AND CERTIFICATE
INSERT INTO `role` (`id`, `name`, `description`) VALUES
(null, 'ROLE_PRINT_TRANSCRIPT', 'ROLE PRINT TRANSCRIPT');

ADD STATUSE  ABSENT IN (student_session_status)
INSERT INTO `student_session_status` (`id`, `description`) VALUES (6, 'ABSENT');

ROLE TO MANAGE COURSES AND MODULE
INSERT INTO `role` (`id`, `name`, `description`) VALUES
(null, 'ROLE_MANAGE_COURSES_AND_MODULES', 'ROLE MANAGE CANDIDATES COURSES AND MODULES');

ROLE TO ONLY VIEW COURSES AND MODULE
INSERT INTO `role` (`id`, `name`, `description`) VALUES
(null, 'ROLE_VIEW_COURSES_AND_MODULES', 'ROLE VIEW COURSES AND MODULES');

ROLE TO MANAGE STUDENTS TRANSCRIPTS FOR TRANING CENTERS AGENTS
INSERT INTO `role` (`id`, `name`, `description`) VALUES
(null, 'ROLE_MANAGE_CANDIDATES_TRANSCRIPT', 'ROLE MANAGE CANDIDATES TRANSCRIPTS');

Creation of specialities

INSERT INTO `speciality` (`id`, `abbreviation`, `name`) VALUES
(1, 'MAE', 'Moniteur Auto Ecole'),
(2, 'IPCSR', 'Inspecteur du Permis de Conduire et de la Securite Routier'),
(3, 'DPCSR', 'Delegues du Permis de Conduire et de la Securite Routier');

INSERT ENTRANCE ELIGIBLE CENTER STATUSES
INSERT INTO `entrance_eligible_center_status` (`id`, `description`) VALUES
(1, 'CREATED'),
(2, 'OPENED'),
(3, 'CLOSED'),
(4, 'VALIDATED'),
(5, 'PV GENERATED');

ADD ROLE TO VIEW EXPERTS NATIONAL EXAM PV
INSERT INTO `role` (`id`, `name`, `description`) VALUES
(null, 'ROLE_VIEW_PV_EXPERTS_EXAM', 'ROLE VIEW PV OF EXPERTS EXAM');

ADD ROLE TO GENERATE PV
INSERT INTO `role` (`id`, `name`, `description`) VALUES
(26, 'ROLE_GENERATE_PV', 'ROLE GENERATE PV');



ROLE TO MANAGE STUDENT FOR TRANING CENTERS AGENTS
INSERT INTO `role` (`id`, `name`, `description`) VALUES
(null, 'ROLE_MANAGE_STUDENTS_UNDER_TRAINING', 'ROLE MANAGE STUDENTS UNDER TRAINING');

 MANAGE STATUSES for (eligible_center_status)
INSERT INTO `eligible_center_status` (`id`, `description`) VALUES 
('1', 'CREATED'), 
('2', 'OPENED'),
('3', 'CLOSED'), 
('4', 'VALIDATED'),
('5', 'PV GENERATED');

 MANAGE STATUSES (student_session_status)
INSERT INTO `student_session_status` (`id`, `description`) VALUES 
('1', 'PRESENTED'), 
('2', 'APPROVED'),
('3', 'REJECTED'), 
('4', 'SUCCEED'),
('5', 'FAILED');


INSERT INTO `in_slip_status` (`id`, `description`, `name`) VALUES
(1, 'REGISTERED', 'REGISTERED'),
(2, 'CLOSED', 'CLOSED'),
(3, 'CLOSED', 'CLOSED'),
(4, 'CONTROLLED. INSLIP IS OK OR HAS NO INCONSISTENCIES', 'CONTROLLED(SLIP OK)'),
(5, 'CONTROLLED. INSLIP HAS INCONSISTENCIES', 'CONTROLLED(SLIP NOT OK)');


DELETE ALL ROWS IN user_role table

/* Only admininstrative roles mentioned*/
INSERT INTO `role` (`id`, `name`, `description`) VALUES
(1, 'ROLE_MANAGE_PV', 'ROLE MANAGE PV'),
(2, 'ROLE_PV_CONTROLLER', 'ROLE PV CONTROLLER'),
(3, 'ROLE_VIEW_PV', 'ROLE VIEW PV'),
(4, 'ROLE_MANAGE_CANDIDATE', 'ROLE MANAGE CANDIDATE'),
(5, 'ROLE_VIEW_INSLIP', 'ROLE VIEW INSLIP'),
(6, 'ROLE_INSLIP_CONTROLLER', 'ROLE INSLIP CONTROLLER'),
(7, 'ROLE_VIEW_OUTSLIP', 'ROLE VIEW OUTSLIP'),
(8, 'ROLE_MANAGE_OUTSLIP', 'ROLE MANAGE OUTSLIP'),
(9, 'ROLE_MANAGE_INSLIP', 'ROLE MANAGE INSLIP'),
(10, 'ROLE_ADMIN', 'ROLE ADMIN'),
(11 , 'ROLE_MANAGE_ALL_OFFICE', 'ROLE MANAGE ALL OFFICE'),
(12,'ROLE_OPEN_INSLIP','ROLE OPEN INSLIP'),
(13,'ROLE_VIEW_USERS','ROLE VIEW USERS'), 
(14,'ROLE_MANAGE_ELIGIBLE_CENTER','ROLE MANAGE ELIGIBLE CENTER'),
(15,'ROLE_CLOSE_ELIGIBLE_CENTER','ROLE CLOSE ELIGIBLE CENTER');


INSERT INTO `user_group` (`id`, `name`) VALUES
(1, 'GROUP ADMIN');

REINITIALIZE THE USER ROLE TABLE TO FIT CURRENT ROLES FOR YOUR USER.


INSERT INTO `speciality` (`id`, `abbreviation`, `name`) VALUES
('1', 'MAE', 'Moniteur Auto Ecole'),
('2', 'IPCSR', 'Inspecteur du Permis de Conduire et de la Securite Routier'),
('3', 'DPCSR', 'Delegues du Permis de Conduire et de la Securite Routier');







===================================================================================================
EVERYTHING BELOW THIS POINT DOES NOT DIRECTLY AFFECT THE SIMT PROJECT (FROM MARINE)
===================================================================================================



//modify the process_type table to add distance from coast values
INSERT INTO `process_type` (`id`, `description`) VALUES ('4', 'EXTENSION OF CAPACITY');

UPDATE `office` SET `abbreviation` = 'DAMVN' WHERE `office`.`id` = 11;

INSERT INTO `role` (`id`, `name`, `description`) VALUES
(26, 'ROLE_GENERATE_PV', 'ROLE GENERATE PV');

//modify the category table to add distance from coast values
ALTER TABLE category ADD `distance_from_coast` varchar(255) DEFAULT NULL ;


// ajour de l'index dans la table person
ALTER TABLE `person` ADD FULLTEXT KEY `pob` (`pob`,`surname`,`given_name`);

ALTER TABLE `group_role` MODIFY `id` int(11) NOT NULL AUTO_INCREMENT


--
-- Contenu de la table `region`
--

INSERT INTO `region` (`id`, `abreviation`, `name`) VALUES
(9, 'SU', 'SUD'),
(8, 'OU', 'OUEST'),
(7, 'NW', 'NORTH WEST'),
(6, 'NO', 'NORD'),
(5, 'LT', 'LITTORAL'),
(4, 'ES', 'EST'),
(3, 'EN', 'EXTREME NORD'),
(2, 'CE', 'CENTRE'),
(1, 'AD', 'ADAMAOUA'),
(10, 'SW', 'SOUTH WEST');

INSERT INTO `group_role` (`id`, `group_id`, `role_id`) VALUES
(1, 1, 19),
(3, 1, 6),
(4, 1, 8),
(5, 1, 10),
(6, 1, 12),
(7, 2, 12),
(8, 2, 10),
(9, 2, 8),
(10, 2, 6),
(12, 3, 12),
(13, 3, 10),
(14, 3, 8),
(15, 3, 6),
(18, 3, 16),
(19, 3, 19),
(20, 3, 20),
(21, 4, 12),
(22, 4, 10),
(23, 4, 9),
(24, 4, 8),
(25, 4, 6),
(26, 4, 5),
(29, 4, 16),
(30, 4, 19),
(31, 4, 22),
(32, 5, 14),
(33, 5, 13),
(34, 5, 12),
(35, 5, 11),
(36, 5, 10),
(37, 5, 9),
(38, 5, 8),
(39, 5, 7),
(40, 5, 6),
(41, 5, 5),
(42, 5, 4),
(46, 5, 15),
(47, 5, 16),
(48, 5, 17),
(49, 5, 18),
(50, 5, 19),
(51, 5, 20),
(52, 5, 21),
(53, 5, 22),
(54, 5, 23),
(56, 7, 17),
(58, 7, 4),
(59, 7, 6),
(60, 7, 7),
(61, 7, 8),
(62, 7, 10),
(64, 7, 12),
(65, 7, 13),
(66, 7, 14),
(67, 8, 19),
(69, 8, 6),
(70, 8, 8),
(71, 8, 10),
(72, 8, 21),
(73, 8, 12),
(74, 1, 12),
(75, 2, 12),
(76, 3, 12),
(77, 4, 12),
(78, 5, 12),
(79, 6, 12),
(80, 7, 12),
(81, 8, 12),
(83, 5, 25);


//CREATE USER GROUP
INSERT INTO `user_group` (`id`, `name`) VALUES
(1, 'Group MINT'),
(2, 'GROUP DELEGATE'),
(3, 'GROUP EDITING STAFF'),
(4, 'GROUP CONTROLLER'),
(5, 'GROUP ADMIN'),
(7, 'GROUP DEC'),
(8, 'GROUP MANAGEMENT');


INSERT INTO `process_type` (`id`, `description`) VALUES
('1', 'NEW CAPACITY'),
('2', 'DUPLICATE OF NEW CAPACITY'),
('3', 'RENEWAL OF CAPACITY');


//*********country
INSERT INTO `country` (id,country_name, abreviation) VALUES (1,'Afghan','AFG '),(2,'Albanian','ALB'),(3,'Algerian','DZA'),(4,'Andorran',' AND'),(5,'Angolian',' AGO'),(6,'Antiguans, Barbudans','ATG'),(7,'Maldivan',' MDV'),(8,'Argentinean','ARG '),(9,'Armenian','ARM'),(10,'Australian','AUS'),(11,'Austrian','AUT'),(12,'Azerbaijani','AZE'),(13,'Bahamian','BHS'),(14,'Bahraini',' BHR'),(15,'Bangladeshi','BGD'),(16,'Barbadian','BRB'),(17,'Belarusian','BLR'),(18,'Belgian','BEL'),(19,'Belizean','BLZ'),(20,'Beninese','BEN'),(21,'Bhutanese','BTN'),(22,'Bolivian','BOL'),(23,'Bosnian','BIH'),(24,'Botswana','BWA'),(25,'Brazilian','BRA'),(26,'Bruneian','BRN   '),(27,'Bulgarian','BGR'),(28,'Burkinabe','BFA'),(29,'Burundian','BDI'),(30,'Cambodian','KHM'),(31,'Cameroonian','CMR'),(32,'Canadian','CAN'),(33,'Cape Verdian','CPV'),(34,'Central African','CAF'),(35,'Chadian','TCD'),(36,'Chilean','CHL'),(37,'Chinese','CHN'),(38,'Colombian',' COL'),(39,'Comoran','COM '),(40,'Congolese','COG'),(41,'Congolese D.R.C','COD'),(42,'Costa Rican','CRI'),(43,'Ivorian','CIV  '),(44,'Croatian','HRV'),(45,'Cuban','CUB  '),(46,'Cypriot','CYP'),(47,'Czech','CZE'),(48,'Danish','DNK'),(49,'Djibouti','DJI'),(50,'Dominican','DMA'),(51,'Republic Dominican','DOM'),(52,'East Timorese','TMP'),(53,'Ecuadorean','ECU'),(54,'Egyptian','EGY'),(55,'Salvadoran','SLV'),(56,'Equatorial Guinean','GNQ'),(57,'Eritrean','ERI'),(58,'Estonian','EST'),(59,'Ethiopian',' ETH '),(60,'Fijian','FJI'),(61,'Finnish','FIN'),(62,'French','FRA'),(63,'Gabonese','GAB'),(64,'Gambian','GMB'),(65,'Georgian','GEO'),(66,'German','DEU'),(67,'Ghanaian','GHA  '),(68,'Greek','GRC'),(69,'Grenadian','GRD'),(70,'Guatemalan','GTM'),(71,'Guinean','GIN'),(72,'Guinea-Bissauan','GNB'),(73,'Guyanese','GUY'),(74,'Haitian','HTI'),(75,'Herzegovinian','BIH'),(76,'Honduran','HND'),(77,'Hungarian','HUN'),(78,'Icelander','ISL'),(79,'Indian','IND'),(80,'Indonesian','IDN'),(81,'Iranian','IRN'),(82,'Iraqi','IRQ'),(83,'Irish','IRL'),(84,'Israeli','ISR'),(85,'Italian','ITA'),(86,'Jamaican','JAM '),(87,'Japanese','JPN'),(88,'Jordanian',' JOR'),(89,'Kazakhstani','KAZ'),(90,'Kenyan','KEN '),(91,'I-Kiribati','KIR'),(92,'North Korean','PRK'),(93,'South Korean','KOR'),(94,'Kuwaiti','KWT '),(95,'Kirghiz','KGZ'),(96,'Lao or Laotian','LAO '),(97,'Latvian','LVA'),(98,'Lebanese','LBN '),(99,'Lesotho','LSO'),(100,'Liberian','LBR'),(101,'Libyan','LBY'),(102,'Liechtensteiner',' LIE'),(103,'Lithuanian','LTU'),(104,'Luxembourger','LUX'),(105,'Macedonian',' MKD'),(106,'Malagasy',' MDG '),(107,'Malawian','MWI'),(108,'Malaysian',' MYS'),(109,'Malian','MLI'),(110,'Maltese','MLT'),(111,'Marshallese','MHL'),(112,'Mauritanian','MRT'),(113,'Mauritian','MUS'),(114,'Mexican','MEX'),(115,'Micronesian','FSM'),(116,'Moldovan','MDA'),(117,'Monegasque','MCO'),(118,'Mongolian','MNG'),(119,'Moroccan','MAR'),(120,'Mozambican','MOZ '),(121,'Burmese','MMR( Myanmar)'),(122,'Namibian','NAM'),(123,'Nauruan','NRU'),(124,'Nepalese','NPL'),(125,'Netherlander','NLD'),(126,'New Zealander','NZL'),(127,'Nicaraguan','NIC'),(128,'Nigerien','NER'),(129,'Nigerian','NGA '),(130,'Norwegian','NOR'),(131,'Omani','OMN  '),(132,'Pakistani','PAK'),(133,'Palauan',' PLW'),(134,'Panamanian','PAN '),(135,'Papua New Guinean','PNG'),(136,'Paraguayan','PRY'),(137,'Peruvian','PER '),(138,'Filipino',' PHL'),(139,'Polish','POL'),(140,'Portuguese','PRT'),(141,'Qatari','QAT'),(142,'Romanian','ROM'),(143,'Russian',' RUS'),(144,'Rwandan','RWA '),(145,'Kittian and Nevisian','KNA'),(146,'Saint Lucian',' LCA'),(147,'Samoan','WSM'),(148,'San Marinese','SMR'),(149,'Sao Tomean','STP '),(150,'Saudi Arabian','SAU'),(151,'Senegalese','SEN'),(152,'Serbian','SRB**'),(153,'Seychellois','SYC'),(154,'Sierra Leonean','SLE  '),(155,'Singaporean','SGP '),(156,'Slovakian','SVK'),(157,'Slovenian','SVN'),(158,'Solomon Islander','SLB'),(159,'Somali','SOM'),(160,'South African','ZAF '),(161,'Spanish','ESP'),(162,'Sri Lankan','LKA'),(163,'Sudanese','SDN'),(164,'Surinamer','SUR'),(165,'Swazi','SWZ'),(166,'Swedish','SWE'),(167,'Swiss','CHE'),(168,'Syrian','SYR '),(169,'Taiwanese','TWN '),(170,'Tajik','TJK'),(171,'Tanzanian','TZA '),(172,'Thai','THA'),(173,'Togolese','TGO'),(174,'Tongan','TON'),(175,'Trinidadian','TTO'),(176,'Tunisian','TUN'),(177,'Turkish','TUR'),(178,'Turkmen','TKM'),(179,'Tuvaluan','TUV '),(180,'Ugandan','UGA'),(181,'Ukrainian','UKR'),(182,'Emirian','ARE'),(183,'British','GBR'),(184,'American','USA'),(185,'Uruguayan','URY '),(186,'Uzbekistani','UZB'),(187,'Ni-Vanuatu','VUT(Vanuatu)'),(188,'Venezuelan','VEN '),(189,'Vietnamese','VNM '),(190,'Yemenite','YEM'),(191,'Zambian','ZMB '),(192,'Zimbabwean','ZWE '),(193,'Yugoslavia','YUG'),(194,'Netherlands','NRD');
/***************************************************************************
OFFICES 

INSERT INTO `office` VALUES (1,1,'ADAMAOUA','AD'),(2,2,'CENTRE','CE'),(3,3,'EXTREME NORD','EN'),(4,4,'EST','ES'),(5,5,'LITTORAL','LT'),(6,6,'NORD','NO'),(7,7,'NORTH WEST','NW'),(8,8,'OUEST','OU'),(9,9,'SUD','SU'),(10,10,'SOUTH WEST','SW'),(11,2,'MINISTERE','DTR'),(12,10,'LIMBE','HQ');
***************************
REGION
INSERT INTO `region` VALUES (1,'ADAMAOUA','AD'),(2,'CENTRE','CE'),(3,'EXTREME NORD','EN'),(4,'EST','ES'),(5,'LITTORAL','LT'),(6,'NORD','NO'),(7,'NORTH WEST','NW'),(8,'OUEST','OU'),(9,'SUD','SU'),(10,'SOUTH WEST','SW');
*******************************USER OFFICE USED IN MARINE*******************
INSERT INTO `office` (`id`, `abreviation`, `name`) VALUES
(1, 'AD', 'ADAMAOUA'),
(2, 'CE', 'CENTRE'),
(3, 'EN', 'EXTREME NORD'),
(4, 'ES', 'EST'),
(5, 'LT', 'LITTORAL'),
(6, 'NO', 'NORD'),
(7, 'NW', 'NORTH WEST'),
(8, 'OU', 'OUEST'),
(9, 'SU', 'SUD'),
(10, 'SW', 'SOUTH WEST'),
(11, 'DTR', 'MINISTERE'),
(12, 'HQ', 'LIMBE');



/***************************************************************************
*******************************USER ROLES USED IN MARINE*******************
INSERT INTO `role` (`id`, `name`, `description`) VALUES
(4, 'ROLE_MANAGE_PV', 'ROLE MANAGE PV'),
(5, 'ROLE_PV_CONTROLLER', 'ROLE PV CONTROLLER'),
(6, 'ROLE_VIEW_PV', 'ROLE VIEW PV'),
(7, 'ROLE_MANAGE_CANDIDATE', 'ROLE MANAGE CANDIDATE'),
(8, 'ROLE_VIEW_INSLIP', 'ROLE VIEW INSLIP'),
(9, 'ROLE_INSLIP_CONTROLLER', 'ROLE INSLIP CONTROLLER'),
(10, 'ROLE_VIEW_OUTSLIP', 'ROLE VIEW OUTSLIP'),
(11, 'ROLE_MANAGE_OUTSLIP', 'ROLE MANAGE OUTSLIP'),
(12, 'ROLE_VIEW_RECORD', 'ROLE VIEW RECORD'),
(13, 'ROLE_PROCESS_FILE', 'ROLE PROCESS FILE'),
(14, 'ROLE_CONFIRM_PROCESS', 'ROLE CONFIRM PROCESS'),
(15, 'ROLE_PRINT_APPLICATION', 'ROLE PRINT APPLICATION'),
(16, 'ROLE_AUTHORIZE REPRINT', 'ROLE AUTHORIZE REPRINT'),
(17, 'ROLE_MANAGE_INSLIP', 'ROLE MANAGE INSLIP'),
(18, 'ROLE_ADMIN', 'ROLE ADMIN'),
(19 , 'ROLE_MANAGE_ALL_OFFICE', 'ROLE MANAGE ALL OFFICE'),
(20,'ROLE_OPEN_INSLIP','ROLE OPEN INSLIP'),
(21,'ROLE_VIEW_USERS','ROLE VIEW USERS'), 
(22,'ROLE_MANAGE_ELIGIBLE_CENTER','ROLE MANAGE ELIGIBLE CENTER'),
(23,'ROLE_CLOSE_ELIGIBLE_CENTER','ROLE CLOSE ELIGIBLE CENTER'),
(25,'ROLE_VIEW_FILE','ROLE VIEW FILE');


***************************************************************************/
***************APPLICATION STATUSES**********************************

INSERT INTO `application_status` (`id`, `description`) VALUES
(1, 'APPLICATION REGISTERED'),
(2, 'APPLICATION REJECTED'),
(3, 'APPLICATION CANCELLED'),
(4, 'APPLICATION CONFIRMED'),
(5, 'CAPACITY PRINTED'),
(6, 'SUCCESSFULLY PRINTED'),
(7, 'CONFIRMED REJECTED'),
(8, 'UNSUCCESSFULLY PRINTED'),
(9, 'DELIVERED'),
(10, 'DELIVERED WITH REJECT'),
(11, 'AUTHORIZED REPRINT');


/************************-----------------------------------********************
ROLES--CONSTRAINTS
role_module_action EXP ROLE_USER_CREATE
/***********************************------------------------********************
ROUTING CONSTRAINTS 
module/action
EXP       user/list or user/create

COMMENT FAIRE LE DO*******************------------------------*******************
doPost(url, formData,idResult).then(function (content) {
    
    //DO SOMETHNG WITH THE CONTENT
}).catch(function (err) {
    
    //TELL SOMETHING TO THE USER
});
*//////////////////------------------------------------//////////////////////////


*********************************************************************************

INSERT INTO `user_group` (`id`, `name`) VALUES ('1', NULL);
INSERT INTO `group_role` (`id`, `group_id`, `role_id`) VALUES ('1', '1', '10');
INSERT INTO `division` (`id`, `name`, `region_id`) VALUES ('1', NULL, '10');
INSERT INTO `training_center` (`id`, `abbreviation`, `creation_date`, `founder`, `location`, `max_student`, `name`, `owner`, `owner_phone_number`, `status`, `division_id`) 
VALUES ('1', 'TC', '2025-02-02 17:28:22', 'MR.FOUNDER', 'BUEA', '0', 'TRAINING CENTER 1', 'MR.FOUNDER', '679223722', '1', '1');

INSERT INTO `user` (`id`, `active`, `dob`, `email`, `enabled`, `first_name`, `gender`, `image`, `last_login`, `last_name`, `password`, `password_requested_at`, `phone_number`, `pob`, `register_on`, `token`, `token_expired`, `username`, `group_id`, `office_id`, `training_center_id`) 
VALUES ('1', '1', '2025-02-02 17:19:21', 'nnokoemmanuel@antic.cm', '1', 'NNOKO', 'EMMANUEL', '1.png', '2025-02-02 17:19:21', 'AKWOMAYA', '$2a$10$RcPfWwltG3oIiNBp1lf8.uxMYcrmBSUQ8JJudK/R10FF9HT/g/21q', '2025-02-02 17:19:21', '679223722', 'NYASOSO', '2025-02-02 17:19:21', 'NULL', '0', 'Nnoko1', '1', '12', '1');



// requête sql pour le déploiement
===============================================================

INSERT INTO `role` (`id`, `description`, `name`) VALUES
(null, 'ROLE PV CONTROLLER STUDENT', 'ROLE_PV_CONTROLLER_STUDENT');
================================================================
metre a jour la colonne specialite dans la table studentSessions a partie de la specialite de la table student

UPDATE student_session ss set ss.speciality_id=(SELECT student.speciality_id from student WHERE ss.student_id=student.id) WHERE ss.speciality_id IS NULL;

================================================================
DEFAULT DATA FOR SPECIALITY PREREQUISITE

INSERT INTO `speciality_prerequisite` (`id`, `entry_diploma`, `duration_in_months`, `speciality_id`) VALUES 
(1, 'NA', '12', '1'),
(2, 'CAPEC_MAE', '12', '2'),
(3, 'BACC', '24', '2'),
(4, 'BACC', '36', '3'),
(5, 'CAPEC_MAE', '24', '3'),
(6, 'CAPEC_IPCSR', '12', '3');


//queries to get printed professional cardfrom system
SELECT DISTINCT training_center.name , person.surname , person.given_name , person.pob , person.dob , speciality.abbreviation from application,process_type,certificate,student_session,student,training_center , person , speciality WHERE application.process_type_id = 3 AND application.application_status_id=6 and application.certificate_id is not null and application.certificate_id = certificate.id and certificate.student_session_id = student_session.id and student_session.student_id = student.id and student.training_center_id = training_center.id and student.person_id = person.id and student.speciality_id = speciality.id ORDER BY training_center.name ASC 

