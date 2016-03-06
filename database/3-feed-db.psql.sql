BEGIN;

-- clear data base
delete from manual_entering;
delete from manual_entering_reason;
delete from clockinclockout;
delete from day;

-- insert adjusting ( initial balance )
insert into adjusting( id, description, time_interval, id_profile ) values ( nextval('adjusting_seq'), 'saldo inicial', '-16:00:00',
  ( select id from profile where description = 'profile default' )
);
insert into adjusting( id, description, time_interval, id_profile ) values ( nextval('adjusting_seq'), 'viagem itália; tempo gasto no avião', '24:00:00',
  ( select id from profile where description = 'profile default' )
);

-- insert manual entering reasons
insert into manual_entering_reason ( id, reason, id_profile ) values ( nextval('manual_entering_reason_seq'), 'SERVIÇO EXTERNO', ( select id from profile where description = 'profile default' ) );
insert into manual_entering_reason ( id, reason, id_profile ) values ( nextval('manual_entering_reason_seq'), 'FALTA', ( select id from profile where description = 'profile default' ) );
insert into manual_entering_reason ( id, reason, id_profile ) values ( nextval('manual_entering_reason_seq'), 'ATESTADO MÉDICO', ( select id from profile where description = 'profile default' ) );
insert into manual_entering_reason ( id, reason, id_profile ) values ( nextval('manual_entering_reason_seq'), 'FÉRIAS', ( select id from profile where description = 'profile default' ) );

-- insert day/clockinclockout
insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-02', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-02' ) ,'2015-07-02 08:24','2015-07-02 12:22');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-02' ) ,'2015-07-02 13:31','2015-07-02 17:32');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-03', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-03' ) ,'2015-07-03 08:52','2015-07-03 12:08');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-03' ) ,'2015-07-03 13:05','2015-07-03 18:03');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-06', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-06' ) ,'2015-07-06 08:14','2015-07-06 09:13');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-06' ) ,'2015-07-06 09:31','2015-07-06 12:17');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-06' ) ,'2015-07-06 13:00','2015-07-06 17:51');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-07', '8 hours', ( select id from profile where description = 'profile default' ));
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-07' ) ,'2015-07-07 08:28','2015-07-07 12:09');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-07' ) ,'2015-07-07 13:30','2015-07-07 17:14');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-08', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-08' ) ,'2015-07-08 08:36','2015-07-08 09:03');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-08' ) ,'2015-07-08 09:21','2015-07-08 12:25');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-08' ) ,'2015-07-08 13:13','2015-07-08 17:35');

insert into day(id, date, expected_hours, notes, id_profile) values ( nextval('day_seq'),'2015-07-09', '0', 'feriado 9 de julho, definir requisitos emAdapter junto ao Cabelo', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-09' ) ,'2015-07-09 12:57','2015-07-09 15:14');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-10', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-10' ) ,'2015-07-10 09:11','2015-07-10 12:16');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-10' ) ,'2015-07-10 13:33','2015-07-10 16:50');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-13', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-13' ) ,'2015-07-13 08:29','2015-07-13 12:11');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-13' ) ,'2015-07-13 12:52','2015-07-13 17:23');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-14', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-14' ) ,'2015-07-14 08:31','2015-07-14 12:27');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-14' ) ,'2015-07-14 13:31','2015-07-14 17:16');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-15', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-15' ) ,'2015-07-15 08:27','2015-07-15 12:39');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-15' ) ,'2015-07-15 13:38','2015-07-15 17:12');

-- manual entering '2015-07-16'
insert into day(id, date, expected_hours, notes, id_profile) values ( nextval('day_seq'),'2015-07-16', '8 hours', 'Este dia eu e o cabelo fomos para SP',( select id from profile where description = 'profile default' ) );
insert into manual_entering ( id, id_day, time_interval, id_manual_entering_reason ) values ( nextval('manual_entering_seq'), ( select d.id from day d where d.date = '2015-07-16' ), '8 hours', ( select mer.id from manual_entering_reason mer where mer.reason = 'SERVIÇO EXTERNO' ) );

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-17', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-17' ) ,'2015-07-17 09:36','2015-07-17 12:07');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-17' ) ,'2015-07-17 13:53','2015-07-17 17:30');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-20', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-20' ) ,'2015-07-20 10:04','2015-07-20 12:11');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-20' ) ,'2015-07-20 13:29','2015-07-20 17:22');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-21', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-21' ) ,'2015-07-21 07:45','2015-07-21 08:07');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-21' ) ,'2015-07-21 08:15','2015-07-21 11:57');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-21' ) ,'2015-07-21 12:47','2015-07-21 17:44');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-22', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-22' ) ,'2015-07-22 07:45','2015-07-22 08:37');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-22' ) ,'2015-07-22 08:52','2015-07-22 12:16');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-22' ) ,'2015-07-22 13:11','2015-07-22 18:13');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-23', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-23' ) ,'2015-07-23 08:52','2015-07-23 12:06');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-23' ) ,'2015-07-23 13:14','2015-07-23 18:51');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-24', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-24' ) ,'2015-07-24 08:43','2015-07-24 12:11');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-24' ) ,'2015-07-24 13:17','2015-07-24 18:16');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-27', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-27' ) ,'2015-07-27 08:56','2015-07-27 12:21');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-27' ) ,'2015-07-27 13:26','2015-07-27 18:53');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-28', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-28' ) ,'2015-07-28 08:42','2015-07-28 12:07');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-28' ) ,'2015-07-28 13:04','2015-07-28 17:43');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-29', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-29' ) ,'2015-07-29 09:08','2015-07-29 12:33');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-29' ) ,'2015-07-29 13:40','2015-07-29 17:32');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-30', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-30' ) ,'2015-07-30 08:50','2015-07-30 12:19');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-30' ) ,'2015-07-30 13:10','2015-07-30 18:44');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-07-31', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-31' ) ,'2015-07-31 09:36','2015-07-31 12:01');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-07-31' ) ,'2015-07-31 14:07','2015-07-31 18:51');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-03', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-03' ) ,'2015-08-03 07:35','2015-08-03 12:36');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-03' ) ,'2015-08-03 13:25','2015-08-03 17:50');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-04', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-04' ) ,'2015-08-04 07:33','2015-08-04 10:53');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-04' ) ,'2015-08-04 11:00','2015-08-04 12:06');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-04' ) ,'2015-08-04 13:17','2015-08-04 17:39');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-05', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-05' ) ,'2015-08-05 07:40','2015-08-05 12:12');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-05' ) ,'2015-08-05 13:16','2015-08-05 17:35');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-06', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-06' ) ,'2015-08-06 07:43','2015-08-06 12:03');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-06' ) ,'2015-08-06 13:16','2015-08-06 17:40');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-07', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-07' ) ,'2015-08-07 07:49','2015-08-07 12:37');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-07' ) ,'2015-08-07 13:04','2015-08-07 17:20');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-10', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-10' ) ,'2015-08-10 07:28','2015-08-10 07:40');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-10' ) ,'2015-08-10 07:51','2015-08-10 12:33');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-10' ) ,'2015-08-10 13:42','2015-08-10 17:46');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-11', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-11' ) ,'2015-08-11 07:28','2015-08-11 08:28');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-11' ) ,'2015-08-11 08:40','2015-08-11 12:14');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-11' ) ,'2015-08-11 13:10','2015-08-11 17:53');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-12', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-12' ) ,'2015-08-12 07:41','2015-08-12 12:12');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-12' ) ,'2015-08-12 13:15','2015-08-12 17:17');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-13', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-13' ) ,'2015-08-13 07:21','2015-08-13 12:07');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-13' ) ,'2015-08-13 13:26','2015-08-13 17:28');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-14', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-14' ) ,'2015-08-14 07:32','2015-08-14 12:52');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-14' ) ,'2015-08-14 13:50','2015-08-14 17:24');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-17', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-17' ) ,'2015-08-17 07:34','2015-08-17 12:07');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-17' ) ,'2015-08-17 13:01','2015-08-17 17:32');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-18', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-18' ) ,'2015-08-18 07:28','2015-08-18 12:14');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-18' ) ,'2015-08-18 13:34','2015-08-18 17:25');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-19', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-19' ) ,'2015-08-19 07:46','2015-08-19 12:13');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-19' ) ,'2015-08-19 13:35','2015-08-19 17:07');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-20', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-20' ) ,'2015-08-20 07:57','2015-08-20 12:08');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-20' ) ,'2015-08-20 13:22','2015-08-20 17:02');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-21', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-21' ) ,'2015-08-21 07:54','2015-08-21 11:49');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-21' ) ,'2015-08-21 13:52','2015-08-21 17:09');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-24', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-24' ) ,'2015-08-24 07:51','2015-08-24 12:08');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-24' ) ,'2015-08-24 12:57','2015-08-24 17:08');

-- manual entering '2015-08-25'
insert into day(id, date, expected_hours, notes, id_profile) values ( nextval('day_seq'),'2015-08-25', '8 hours', 'SERVIÇO EXTERNO (Fui para o DB neste dia para conversar com Kiyohara e Giuliano a respeito do projeto FX)',( select id from profile where description = 'profile default' ) );
insert into manual_entering ( id, id_day, time_interval, id_manual_entering_reason ) values ( nextval('manual_entering_seq'), ( select d.id from day d where d.date = '2015-08-25' ), '8 hours', ( select mer.id from manual_entering_reason mer where mer.reason = 'SERVIÇO EXTERNO' ) );

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-26', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-26' ) ,'2015-08-26 08:03','2015-08-26 12:23');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-26' ) ,'2015-08-26 13:20','2015-08-26 16:58');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-27', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-27' ) ,'2015-08-27 08:05','2015-08-27 11:45');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-27' ) ,'2015-08-27 12:38','2015-08-27 17:04');

insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-28', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-28' ) ,'2015-08-28 08:00','2015-08-28 12:43');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-28' ) ,'2015-08-28 13:55','2015-08-28 17:23');

-- 31/08/2015
insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-08-31', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-31' ) , '2015-08-31 08:10:00', '2015-08-31 12:17:00');
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-08-31' ) , '2015-08-31 12:56:00', '2015-08-31 17:32:00');

-- 01/09/2015
insert into day(id, date, expected_hours, id_profile) values (nextval('day_seq'),'2015-09-01', '8 hours', ( select id from profile where description = 'profile default' ) );
insert into clockinclockout (id, id_day, clockin, clockout) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-09-01' ) , '2015-09-01 07:59:00', '2015-09-01 12:32:00');
insert into clockinclockout (id, id_day, clockin) values ( nextval('clockinclockout_seq'), ( select d.id from day d where d.date = '2015-09-01' ) , '2015-09-01 14:02:00');

COMMIT;
