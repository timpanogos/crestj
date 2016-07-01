USE interface_repository;

CREATE TABLE `reserved` (
  `reservedPk` int(11) NOT NULL AUTO_INCREMENT,
  `registryFk` int(11) NOT NULL COMMENT 'registry PK',
  `iid` int(11) NOT NULL COMMENT 'The reserved iid',
  `submitterFk` bigint(20) NOT NULL,
  `reservedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Original reserved time',
  `nextGrant` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'time of required renewal',
  PRIMARY KEY (`reservedPk`),
  KEY `fk_reserved_registryFk_registry` (`registryFk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table of reserved Interface ID';


