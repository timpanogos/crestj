USE interface_repository;

CREATE TABLE `submittergroup` (
  `groupFk` bigint(10) unsigned NOT NULL COMMENT 'submitter.submitterPk representing this group',
  `managerFk` bigint(11) unsigned DEFAULT NULL COMMENT 'submitter.submitterPk who is the manager of  the group',
  `memberFk` bigint(10) unsigned NOT NULL COMMENT 'submitters.submitterPk of the group members',
  KEY `group-creator-fk` (`managerFk`),
  KEY `group-groupid-fk` (`groupFk`),
  KEY `members-fk` (`memberFk`),
  CONSTRAINT `groupGroupidFk` FOREIGN KEY (`groupFk`) REFERENCES `submitter` (`submitterPk`),
  CONSTRAINT `groupManagerFk` FOREIGN KEY (`managerFk`) REFERENCES `submitter` (`submitterPk`),
  CONSTRAINT `groupMemberFk` FOREIGN KEY (`memberFk`) REFERENCES `submitter` (`submitterPk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='The group creator table';


insert into `interface_repository`.`submittergroup`(`groupFk`,`managerFk`,`memberFk`) values (73,71,73);
insert into `interface_repository`.`submittergroup`(`groupFk`,`managerFk`,`memberFk`) values (74,71,74);
insert into `interface_repository`.`submittergroup`(`groupFk`,`managerFk`,`memberFk`) values (75,71,75);
insert into `interface_repository`.`submittergroup`(`groupFk`,`managerFk`,`memberFk`) values (76,71,76);
