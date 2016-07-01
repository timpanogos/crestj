USE interface_repository;

CREATE TABLE `holes` (
  `holesPk` int(11) NOT NULL AUTO_INCREMENT,
  `subrepoFk` int(11) NOT NULL,
  `min` bigint(20) NOT NULL,
  `max` bigint(20) NOT NULL,
  PRIMARY KEY (`holesPk`),
  KEY `holes_subrepoFkIdx` (`subrepoFk`),
  CONSTRAINT `subrepo_subrepoPk_subrepoFk` FOREIGN KEY (`subrepoFk`) REFERENCES `subrepo` (`subrepoPk`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='Sub-Repository tree node';


insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (1,168,0,0);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (2,168,2,7);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (3,168,12,255);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (4,167,256,511);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (5,167,525,525);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (6,167,532,542);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (7,167,545,545);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (8,167,547,548);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (9,167,553,561);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (10,167,564,564);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (11,167,569,65535);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (12,166,65540,16777215);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (13,166,16777219,16777225);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (14,166,16777227,16777249);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (15,166,16777251,16777254);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (16,166,16777257,16777267);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (17,166,16777269,16777274);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (18,166,16777276,16777276);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (19,166,16777288,16777292);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (20,166,16777294,16777294);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (21,166,16777297,16777302);
insert into `interface_repository`.`holes`(`holesPk`,`subrepoFk`,`min`,`max`) values (22,166,16777305,2147483647);
