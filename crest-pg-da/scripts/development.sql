select * from public.capsuleer;
select * from entity;
select * from accessgroup;

select * from entity where isgroup=true;
insert into entity (name, isgroup) values('aa', true);

truncate entity;

truncate accessgroup;
truncate capsuleer cascade;
delete from entity where entitypk=162;
delete from entity where entitypk=163;
delete from entity where entitypk=164;
delete from entity where entitypk=185;
delete from entity where entitypk=186;
delete from entity where entitypk=187;
delete from entity where entitypk=188;
delete from entity where entitypk=189;

delete from entity where entitypk=197;
delete from entity where entitypk=198;
delete from entity where entitypk=199;
delete from entity where entitypk=200;

delete from capsuleer where capsuleerpk=9;

select capsuleerpk from capsuleer where capsuleer=name0;