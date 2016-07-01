USE interface_repository;

CREATE TABLE `subrepo` (
  `subrepoPk` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Nodes primary key',
  `repotype` varchar(128) NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 NOT NULL,
  `label` varchar(64) CHARACTER SET utf8 NOT NULL,
  `depth` int(11) NOT NULL,
  `parentPid` int(11) DEFAULT NULL,
  `groupFk` bigint(20) DEFAULT NULL COMMENT 'The group this node is controlled by',
  PRIMARY KEY (`subrepoPk`),
  UNIQUE KEY `subrepo_rowUni` (`repotype`,`parentPid`,`depth`,`name`,`label`)
) ENGINE=InnoDB AUTO_INCREMENT=172 DEFAULT CHARSET=utf8 COMMENT='Sub-Repository tree node';


insert into `interface_repository`.`subrepo`(`subrepoPk`,`repotype`,`name`,`label`,`depth`,`parentPid`,`groupFk`) values (163,'opendof','opendof','Root',0,-1,73);
insert into `interface_repository`.`subrepo`(`subrepoPk`,`repotype`,`name`,`label`,`depth`,`parentPid`,`groupFk`) values (164,'opendof','1','Registry',1,163,74);
insert into `interface_repository`.`subrepo`(`subrepoPk`,`repotype`,`name`,`label`,`depth`,`parentPid`,`groupFk`) values (165,'opendof','2','Registry',1,163,74);
insert into `interface_repository`.`subrepo`(`subrepoPk`,`repotype`,`name`,`label`,`depth`,`parentPid`,`groupFk`) values (166,'opendof','4','Number of Bytes',2,164,74);
insert into `interface_repository`.`subrepo`(`subrepoPk`,`repotype`,`name`,`label`,`depth`,`parentPid`,`groupFk`) values (167,'opendof','2','Number of Bytes',2,164,76);
insert into `interface_repository`.`subrepo`(`subrepoPk`,`repotype`,`name`,`label`,`depth`,`parentPid`,`groupFk`) values (168,'opendof','1','Number of Bytes',2,164,76);
insert into `interface_repository`.`subrepo`(`subrepoPk`,`repotype`,`name`,`label`,`depth`,`parentPid`,`groupFk`) values (169,'opendof','4','Number of Bytes',2,165,74);
insert into `interface_repository`.`subrepo`(`subrepoPk`,`repotype`,`name`,`label`,`depth`,`parentPid`,`groupFk`) values (170,'opendof','2','Number of Bytes',2,165,76);
insert into `interface_repository`.`subrepo`(`subrepoPk`,`repotype`,`name`,`label`,`depth`,`parentPid`,`groupFk`) values (171,'opendof','1','Number of Bytes',2,165,76);
