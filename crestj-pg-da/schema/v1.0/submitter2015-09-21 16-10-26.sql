USE interface_repository;

CREATE TABLE `submitter` (
  `submitterPk` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'The submitters primary auto incrementing key',
  `name` varchar(80) COLLATE utf8_bin NOT NULL COMMENT 'The name of the submitter',
  `email` varchar(254) COLLATE utf8_bin NOT NULL COMMENT 'submitters email ',
  `description` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT 'Brief information about the submitter',
  `joinedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Time and date the submitter joined',
  `isGroup` tinyint(1) NOT NULL COMMENT 'Does this submitter represent a group',
  PRIMARY KEY (`submitterPk`),
  UNIQUE KEY `creator_index` (`name`),
  UNIQUE KEY `email_index` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='The owner table';


