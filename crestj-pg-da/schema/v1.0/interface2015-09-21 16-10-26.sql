USE interface_repository;

CREATE TABLE `interface` (
  `ifacePk` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'interface primary autoincrementing key of interfaces',
  `iid` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'The globally unique Interface ID',
  `xml` text COLLATE utf8_bin NOT NULL COMMENT 'The interface source XML',
  `version` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'interface version',
  `repotype` tinytext COLLATE utf8_bin NOT NULL COMMENT 'The repository type (i.e. opendof, allseen)',
  `submitterFk` bigint(10) unsigned NOT NULL COMMENT 'The creators Fk',
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Time of first entry',
  `modifiedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'The last time the xml column was modified',
  `published` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Boolean flag indicating if the interface is working or published',
  PRIMARY KEY (`ifacePk`),
  UNIQUE KEY `iid_index` (`iid`,`version`),
  KEY `creator-fk` (`submitterFk`),
  CONSTRAINT `interfaceSubmitterFk` FOREIGN KEY (`submitterFk`) REFERENCES `submitter` (`submitterPk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='The interface xml containment table';


