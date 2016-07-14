select * from capsuleer;
select * from entity;
select * from accessgroup;

select * from entity where isgroup=true;
insert into entity (name, isgroup) values('aa', true);

truncate entity;

truncate accessgroup;
truncate capsuleer cascade;
delete from entity where entitypk=161;
delete from entity where entitypk=162;
delete from entity where entitypk=163;
delete from entity where entitypk=164;
delete from entity where entitypk=165;
delete from entity where entitypk=166;
delete from entity where entitypk=167;
delete from entity where entitypk=168;
delete from entity where entitypk=169;
delete from entity where entitypk=170;
delete from entity where entitypk=171;
delete from entity where entitypk=172;

delete from capsuleer where capsuleerpk=9;

select capsuleerpk from capsuleer where capsuleer=name0;