﻿-- user definition
-- CREATE ROLE clkio WITH LOGIN ENCRYPTED PASSWORD 'clkiopw' INHERIT IN ROLE application ROLE admin;

-- schema definition
-- \c db4app;
-- CREATE SCHEMA clkio_schm AUTHORIZATION clkio;

-- drop current structure if it applies
DROP TABLE clkio_schm.clockinclockout;
DROP TABLE clkio_schm.manual_entering;
DROP TABLE clkio_schm.manual_entering_reason;
DROP TABLE clkio_schm.day;
DROP TABLE clkio_schm.adjusting;
DROP TABLE clkio_schm.profile;
DROP TABLE clkio_schm.email;
DROP TABLE clkio_schm.request_reset_password;
DROP TABLE clkio_schm.login;
DROP TABLE clkio_schm.clk_user;

-- sequence clk_user_seq
CREATE SEQUENCE clkio_schm.clk_user_seq
INCREMENT BY 1
MINVALUE 1
START WITH 1;

-- sequence login_seq
CREATE SEQUENCE clkio_schm.login_seq
INCREMENT BY 1
MINVALUE 1
START WITH 1;

-- sequence request_reset_password_seq 
CREATE SEQUENCE clkio_schm.request_reset_password_seq
INCREMENT BY 1
MINVALUE 1
START WITH 1;

-- sequence email_seq
CREATE SEQUENCE clkio_schm.email_seq
INCREMENT BY 1
MINVALUE 1
START WITH 1;

-- sequence profile_seq
CREATE SEQUENCE clkio_schm.profile_seq
INCREMENT BY 1
MINVALUE 1
START WITH 1;

-- sequence adjusting_seq
CREATE SEQUENCE clkio_schm.adjusting_seq
INCREMENT BY 1
MINVALUE 1
START WITH 1;

-- sequence manual_entering_reason_seq
CREATE SEQUENCE clkio_schm.manual_entering_reason_seq
INCREMENT BY 1
MINVALUE 1
START WITH 1;

-- sequence day_seq
CREATE SEQUENCE clkio_schm.day_seq
INCREMENT BY 1
MINVALUE 1
START WITH 1;

-- sequence manual_entering_seq
CREATE SEQUENCE clkio_schm.manual_entering_seq
INCREMENT BY 1
MINVALUE 1
START WITH 1;

-- sequence clockinclockout_seq
CREATE SEQUENCE clkio_schm.clockinclockout_seq
INCREMENT BY 1
MINVALUE 1
START WITH 1;

-- table user
CREATE TABLE clkio_schm.clk_user (
  id INT NOT NULL PRIMARY KEY,
  locale VARCHAR( 7 ) NOT NULL CHECK( locale <> '' ),
  password VARCHAR( 100 ) NOT NULL CHECK( password <> '' )
);
ALTER SEQUENCE clkio_schm.clk_user_seq OWNED BY clkio_schm.clk_user.id;

-- table login
CREATE TABLE clkio_schm.login (
  id INT NOT NULL PRIMARY KEY,
  id_clk_user INT NOT NULL REFERENCES clkio_schm.clk_user( id ) ON UPDATE RESTRICT ON DELETE RESTRICT,
  code VARCHAR( 100 ) NOT NULL CHECK( code <> '' ),
  since TIMESTAMP NOT NULL,
  ip VARCHAR( 100 ) NOT NULL CHECK( ip <> '' ),
  valid BOOLEAN NOT NULL
);
ALTER SEQUENCE clkio_schm.login_seq OWNED BY clkio_schm.login.id;

-- table request_reset_password
CREATE TABLE clkio_schm.request_reset_password (
  id INT NOT NULL PRIMARY KEY,
  id_clk_user INT NOT NULL REFERENCES clkio_schm.clk_user( id ) ON UPDATE RESTRICT ON DELETE RESTRICT,
  request_code_value VARCHAR( 150 ) NOT NULL CHECK( request_code_value <> '' ),
  request_date TIMESTAMP NOT NULL,
  confirmation_code_value VARCHAR( 150 ) NULL,
  confirmation_date TIMESTAMP NULL,
  change_date TIMESTAMP NULL
);
ALTER SEQUENCE clkio_schm.request_reset_password_seq OWNED BY clkio_schm.request_reset_password.id;

-- table email
CREATE TABLE clkio_schm.email (
  id INT NOT NULL PRIMARY KEY,
  id_clk_user INT NOT NULL REFERENCES clkio_schm.clk_user( id ) ON UPDATE RESTRICT ON DELETE RESTRICT,
  address VARCHAR( 50 ) NOT NULL CHECK( address <> '' ),
  recorded_time TIMESTAMP NOT NULL,
  confirmation_code VARCHAR( 150 ) NOT NULL CHECK( confirmation_code <> '' ),
  confirmation_date TIMESTAMP NULL DEFAULT NULL,
  is_primary BOOLEAN NOT NULL DEFAULT FALSE
);
ALTER SEQUENCE clkio_schm.email_seq OWNED BY clkio_schm.email.id;

-- table profile
CREATE TABLE clkio_schm.profile (
  id INT NOT NULL PRIMARY KEY,
  id_clk_user INT NOT NULL REFERENCES clkio_schm.clk_user( id ) ON UPDATE RESTRICT ON DELETE RESTRICT,
  description VARCHAR ( 50 ) NOT NULL DEFAULT 'profile default',
  hours_format VARCHAR( 8 ) NOT NULL DEFAULT 'HH:mm',
  date_format VARCHAR( 15 ) NOT NULL DEFAULT 'yyyy-MM-dd',
  default_expected_sunday INTERVAL NULL DEFAULT NULL,
  default_expected_monday INTERVAL NULL DEFAULT NULL,
  default_expected_tuesday INTERVAL NULL DEFAULT NULL,
  default_expected_wednesday INTERVAL NULL DEFAULT NULL,
  default_expected_thursday INTERVAL NULL DEFAULT NULL,
  default_expected_friday INTERVAL NULL DEFAULT NULL,
  default_expected_saturday INTERVAL NULL DEFAULT NULL
);
ALTER SEQUENCE clkio_schm.profile_seq OWNED BY clkio_schm.profile.id;

-- table adjusting
CREATE TABLE clkio_schm.adjusting (
  id INT NOT NULL PRIMARY KEY,
  description VARCHAR ( 50 ) NOT NULL CHECK( description <> '' ),
  time_interval INTERVAL NOT NULL DEFAULT '0',
  id_profile INT NOT NULL REFERENCES clkio_schm.profile( id ) ON UPDATE RESTRICT ON DELETE RESTRICT
);
ALTER SEQUENCE clkio_schm.adjusting_seq OWNED BY clkio_schm.adjusting.id;

-- table day
CREATE TABLE clkio_schm.day (
  id INT NOT NULL PRIMARY KEY,
  date DATE NOT NULL,
  expected_hours INTERVAL NOT NULL,
  notes VARCHAR( 150 ),
  id_profile INT NOT NULL REFERENCES clkio_schm.profile( id ) ON UPDATE RESTRICT ON DELETE RESTRICT
);
ALTER SEQUENCE clkio_schm.day_seq OWNED BY clkio_schm.day.id;

-- table manual_entering_reason
CREATE TABLE clkio_schm.manual_entering_reason (
  id INT NOT NULL PRIMARY KEY,
  id_profile INT NOT NULL REFERENCES clkio_schm.profile( id ) ON UPDATE RESTRICT ON DELETE RESTRICT,
  reason VARCHAR( 50 ) NOT NULL CHECK( reason <> '' )
);
ALTER SEQUENCE clkio_schm.manual_entering_reason_seq OWNED BY clkio_schm.manual_entering_reason.id;

-- table manual entering
CREATE TABLE clkio_schm.manual_entering (
  id INT NOT NULL PRIMARY KEY,
  id_day INT NOT NULL REFERENCES clkio_schm.day( id ) ON UPDATE RESTRICT ON DELETE RESTRICT,
  id_manual_entering_reason INT NOT NULL REFERENCES clkio_schm.manual_entering_reason( id ) ON UPDATE RESTRICT ON DELETE RESTRICT,
  time_interval INTERVAL NOT NULL CHECK ( time_interval > '0' )
);
ALTER SEQUENCE clkio_schm.manual_entering_seq OWNED BY clkio_schm.manual_entering.id;

-- table clockinclockout
CREATE TABLE clkio_schm.clockinclockout (
  id INT NOT NULL PRIMARY KEY,
  id_day INT NOT NULL REFERENCES clkio_schm.day( id ) ON UPDATE RESTRICT ON DELETE RESTRICT,
  clockin TIMESTAMP NULL,
  clockout TIMESTAMP NULL,
  CONSTRAINT clockinclockout_at_least_one_not_null CHECK ( clockin IS NOT NULL OR clockout IS NOT NULL ),
  CONSTRAINT clockinclockout_clockout_not_greater_than_clockin CHECK ( ( clockin IS NULL OR clockout IS NULL ) OR ( clockout >= clockin ) )
);
ALTER SEQUENCE clkio_schm.clockinclockout_seq OWNED BY clkio_schm.clockinclockout.id;

-- adding the schema as default along side the "$user", public searching paths
-- ALTER ROLE clkio SET search_path TO clkio_schm,"$user",public;
