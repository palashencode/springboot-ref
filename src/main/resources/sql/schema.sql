create table USERS(
  ID int not null AUTO_INCREMENT,
  NAME varchar(100) not null,
  USERNAME varchar(100) not null,
  CITY varchar(100) not null,
  DESCRIPTION varchar(500) not null,
  DATEOFJOINING DATE,
  EXACTDOB TIMESTAMP(3) WITH TIME ZONE,
  STATUS int,
  PRIMARY KEY ( ID )
);

create table COUNTRIES(
  ID int not null AUTO_INCREMENT,
  NAME varchar(100) not null,
  PRIMARY KEY ( ID )
);
