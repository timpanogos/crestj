USE interface_repository;

CREATE TABLE `registry` (
  `registryPk` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary auto incrementing key of registry and size categories',
  `registry` int(11) NOT NULL COMMENT 'The registry 1 to 62',
  `size` tinyint(4) NOT NULL COMMENT 'Size category 1 is 1 byte, 2 is 2 bytes, 3 is 4 bytes',
  `next` int(11) NOT NULL COMMENT 'The next iid to be given out for the registry/size',
  PRIMARY KEY (`registryPk`),
  UNIQUE KEY `uq_registry_registry_size` (`registry`,`size`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table of all Registry and size categories';


