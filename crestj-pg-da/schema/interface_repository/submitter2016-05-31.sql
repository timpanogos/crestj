USE interface_repository;

CREATE TABLE `submitter` (
  `submitterPk` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'The submitters primary auto incrementing key',
  `name` varchar(80) CHARACTER SET utf8 NOT NULL COMMENT 'The name of the submitter',
  `email` varchar(254) CHARACTER SET utf8 NOT NULL COMMENT 'submitters email ',
  `description` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Brief information about the submitter',
  `joinedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Time and date the submitter joined',
  `isGroup` tinyint(1) NOT NULL COMMENT 'Does this submitter represent a group',
  PRIMARY KEY (`submitterPk`),
  UNIQUE KEY `creator_index` (`name`),
  UNIQUE KEY `email_index` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='The owner table';


insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (9,'Chad Adams','Not Known1442879739410',null,'2015-09-21 17:55:50',0);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (10,'PSLCL','Not Known1442879739411',null,'2015-09-21 17:55:59',1);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (11,'test','Not Known1443120024046',null,'2015-09-24 12:40:35',0);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (12,'testgroup','Not Known1443120024047',null,'2015-09-24 12:40:44',1);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (13,'gm','Not Known1443122241967',null,'2015-09-24 13:17:33',0);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (14,'g1','Not Known1443122241968',null,'2015-09-24 13:17:42',1);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (15,'user - group','Not Known1443131798646',null,'2015-09-24 15:56:39',0);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (16,'me','Not Known1443134469763',null,'2015-09-24 16:41:10',0);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (71,'OpenDOF Admin','admin@opendof.org','OpenDOF Manager, general inquiries only','2016-05-26 12:22:14',0);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (72,'cli-admin','cli-admin','Root user CLI Privledges','2016-05-26 12:22:14',0);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (73,'anonymous','anonymous','Anonymous users group','2016-05-26 12:22:14',1);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (74,'user','user','Authenticated users group','2016-05-26 12:22:14',1);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (75,'private','private','Private interface','2016-05-26 12:22:14',1);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (76,'opendof-allocator','opendof-allocator','DOF IID 1 and 2 byte size allocation group','2016-05-26 12:22:14',1);
insert into `interface_repository`.`submitter`(`submitterPk`,`name`,`email`,`description`,`joinedDate`,`isGroup`) values (77,'Joseph Clark','joseph.clark@us.panasonic.com',null,'2016-05-26 14:29:47',0);
