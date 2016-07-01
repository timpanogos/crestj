USE interface_repository;

CREATE TABLE `subrepoparent` (
  `parentFk` int(11) DEFAULT NULL,
  `childFk` int(11) NOT NULL,
  `depth` int(11) NOT NULL,
  KEY `subrepoparent_parentFk` (`parentFk`),
  KEY `subrepoparent_childFk` (`childFk`),
  CONSTRAINT `subrepo_subrepoPk_child` FOREIGN KEY (`childFk`) REFERENCES `subrepo` (`subrepoPk`),
  CONSTRAINT `subrepo_subrepoPk_parent` FOREIGN KEY (`parentFk`) REFERENCES `subrepo` (`subrepoPk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Sub-Repository tree parent table';


insert into `interface_repository`.`subrepoparent`(`parentFk`,`childFk`,`depth`) values (163,163,0);
insert into `interface_repository`.`subrepoparent`(`parentFk`,`childFk`,`depth`) values (163,164,1);
insert into `interface_repository`.`subrepoparent`(`parentFk`,`childFk`,`depth`) values (163,165,1);
insert into `interface_repository`.`subrepoparent`(`parentFk`,`childFk`,`depth`) values (164,166,2);
insert into `interface_repository`.`subrepoparent`(`parentFk`,`childFk`,`depth`) values (164,167,2);
insert into `interface_repository`.`subrepoparent`(`parentFk`,`childFk`,`depth`) values (164,168,2);
insert into `interface_repository`.`subrepoparent`(`parentFk`,`childFk`,`depth`) values (165,169,2);
insert into `interface_repository`.`subrepoparent`(`parentFk`,`childFk`,`depth`) values (165,170,2);
insert into `interface_repository`.`subrepoparent`(`parentFk`,`childFk`,`depth`) values (165,171,2);
