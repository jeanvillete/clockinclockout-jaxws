/*CREATE DATABASE clockinclockout;*/
CREATE USER CLKINCLKOUT@localhost;

CREATE DATABASE clockinclockout;

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP on clockinclockout.* to CLKINCLKOUT@localhost;

-- begin definitions for user
CREATE  TABLE IF NOT EXISTS clockinclockout.user (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
  register_date TIMESTAMP NOT NULL ,
  email_valid TINYINT(1) NOT NULL DEFAULT false ,
  email_address VARCHAR(60) NOT NULL ,
  password VARCHAR(60) NOT NULL ,
  date_format VARCHAR(45) NOT NULL DEFAULT 'yyyy/MM/dd' ,
  monday_hours TIME NOT NULL DEFAULT '08:00:00' ,
  tuesday_hours TIME NOT NULL DEFAULT '08:00:00' ,
  wednesday_hours TIME NOT NULL DEFAULT '08:00:00' ,
  thursday_hours TIME NOT NULL DEFAULT '08:00:00' ,
  friday_hours TIME NOT NULL DEFAULT '08:00:00' ,
  saturday_hours TIME NOT NULL DEFAULT '00:00:00' ,
  sunday_hours TIME NOT NULL DEFAULT '00:00:00' ,
  UNIQUE INDEX id_UNIQUE ( id ASC) ,
  UNIQUE INDEX email_address_UNIQUE ( email_address ASC) )
ENGINE = InnoDB;
-- end definitions for user

-- begin definitions for reset_password
CREATE TABLE IF NOT EXISTS clockinclockout.reset_password (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
	id_user INT NOT NULL ,
	request_date TIMESTAMP NOT NULL
) ENGINE = InnoDB;

ALTER TABLE clockinclockout.reset_password ADD CONSTRAINT FK_ID_USER
FOREIGN KEY ( id_user ) REFERENCES clockinclockout.user( id ) ON DELETE RESTRICT ON UPDATE RESTRICT;
-- end definitions for reset_password

-- begin definitions for work_day
CREATE  TABLE IF NOT EXISTS clockinclockout.work_day (
  id INT NOT NULL AUTO_INCREMENT ,
  day DATE NULL ,
  expected_hours TIME NULL ,
  total_hours TIME NULL ,
  note VARCHAR(255) NULL ,
  id_user INT NULL ,
  PRIMARY KEY ( id ) ,
  UNIQUE INDEX id_UNIQUE ( id ASC) ,
  UNIQUE INDEX day_UNIQUE ( day ASC) ,
  INDEX id_user ( id ASC) ,
  CONSTRAINT id_user
    FOREIGN KEY ( id )
    REFERENCES 'clockinclockout'.'user' ( id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;
-- end definitions for work_day

-- begin definitions for clock_in_clock_out
CREATE  TABLE IF NOT EXISTS clockinclockout.clock_in_clock_out (
  id INT NOT NULL AUTO_INCREMENT ,
  clock_in TIMESTAMP NULL ,
  clock_out TIMESTAMP NULL ,
  id_work_day INT NOT NULL ,
  PRIMARY KEY ( id ) ,
  UNIQUE INDEX id_UNIQUE ( id ASC) ,
  INDEX id_work_day ( id ASC) ,
  CONSTRAINT id_work_day
    FOREIGN KEY ( id )
    REFERENCES 'clockinclockout'.'work_day' ( id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;
-- end definitions for clock_in_clock_out