select * from accessgroup;
select * from capsuleer;
select * from entity;
select * from sharedrights;
select * from alliances
select * from alliance
select * from paging;

select * from entity where isgroup=true;
insert into entity (name, isgroup) values('aa', true);

truncate entity;

truncate alliance;
truncate accessgroup;
truncate capsuleer cascade;
delete from entity where entitypk=202;
delete from entity where entitypk=203;
delete from entity where entitypk=204;
delete from entity where entitypk=185;
delete from entity where entitypk=186;
delete from entity where entitypk=187;
delete from entity where entitypk=188;
delete from entity where entitypk=189;

delete from entity where entitypk=197;
delete from entity where entitypk=198;
delete from entity where entitypk=199;
delete from entity where entitypk=200;

delete from capsuleer where capsuleerpk=166;

select capsuleerpk from capsuleer where capsuleer=name0;