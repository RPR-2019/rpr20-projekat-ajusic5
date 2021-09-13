BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Service" (
	"id"	INTEGER,
	"name"	TEXT
);
CREATE TABLE IF NOT EXISTS "Doctor" (
	"username"	TEXT,
	"password"	TEXT,
	"name"	TEXT,
	"surname"	TEXT,
	"date_of_birth"	TEXT,
	"profile_type"	TEXT,
	"sex"	TEXT,
	"field_of_expertise"	TEXT,
	"doctor_id"	INTEGER
);
CREATE TABLE IF NOT EXISTS "Doctor_service" (
	"med_service_id"	INTEGER,
	"doctor_id"	INTEGER
);
CREATE TABLE IF NOT EXISTS "Patient" (
	"username"	TEXT,
	"password"	TEXT,
	"name"	TEXT,
	"surname"	TEXT,
	"date_of_birth"	TEXT,
	"profile_type"	TEXT,
	"sex"	TEXT,
	"patient_card_number"	INTEGER,
	"patient_id"	INTEGER
);
CREATE TABLE IF NOT EXISTS "Examination" (
	"id"	INTEGER,
	"patient_id"	INTEGER,
	"doctor_id"	INTEGER,
	"type_of_examination"	TEXT,
	"therapy"	TEXT,
	"date_and_time_of_appointment"	TEXT,
	"date_and_time_of_reservation"	TEXT,
	"successful"	BOOLEAN,
	"diagnosis"	TEXT,
	"archived"	BOOLEAN
);
INSERT INTO "Service" VALUES (1,'Pregled vida');
INSERT INTO "Service" VALUES (2,'Mjerenje očnog pritiska');
INSERT INTO "Service" VALUES (3,'Ultrazvuk srca');
INSERT INTO "Service" VALUES (4,'EKG');
INSERT INTO "Service" VALUES (5,'Psihijatrijski intervju');
INSERT INTO "Service" VALUES (6,'Psihoterapija');
INSERT INTO "Service" VALUES (7,'Fizikalni pregled');
INSERT INTO "Service" VALUES (8,'Ultrazvučni pregled djeteta');
INSERT INTO "Doctor" VALUES ('mmujic1','mujo','Mujo','Mujić','1989-11-18','Ljekar','M','Oftamolog',1);
INSERT INTO "Doctor" VALUES ('ssuljic1','suljo','Suljo','Suljić','1969-09-07','Ljekar','M','Kardiolog',2);
INSERT INTO "Doctor" VALUES ('iivic1','iva','Iva','Ivić','1915-06-14','Ljekar','Ž','Psihijatar',3);
INSERT INTO "Doctor" VALUES ('zjavdan1','zehra','Zehra','Javdan','1999-11-27','Ljekar','Ž','Pedijatar',4);
INSERT INTO "Doctor_service" VALUES (8,4);
INSERT INTO "Doctor_service" VALUES (7,4);
INSERT INTO "Doctor_service" VALUES (1,1);
INSERT INTO "Doctor_service" VALUES (2,1);
INSERT INTO "Doctor_service" VALUES (3,2);
INSERT INTO "Doctor_service" VALUES (4,2);
INSERT INTO "Doctor_service" VALUES (5,3);
INSERT INTO "Doctor_service" VALUES (6,3);
INSERT INTO "Patient" VALUES ('ajusic5','neithoaleeng','Amna','Jusić','1999-05-18','pacijent','Ž',1,1);
INSERT INTO "Patient" VALUES ('amilaj','amila','Amila','Jusić','1996-01-14','Pacijent','Ž',2,2);
INSERT INTO "Patient" VALUES ('hjusic1','haris','Haris','Jusić','1958-03-24','Pacijent','M',3,3);
INSERT INTO "Examination" VALUES (1,1,1,'Pregled vida','Terapijske naočale','2021-09-08T10:30','2021-09-06T10:00',1,'Strabizam',1);
INSERT INTO "Examination" VALUES (8,1,1,'Mjerenje očnog pritiska','Terapijske naočale','2021-09-16T14:30','2021-09-07T17:24:39.426522700',1,'Strabizam',1);
INSERT INTO "Examination" VALUES (9,1,2,'EKG','Tablete','2021-09-12T15:30','2021-09-12T17:38:35.157637400',1,'Povišen krvni pritisak',1);
INSERT INTO "Examination" VALUES (11,2,2,'EKG','Tablete','2021-09-12T12:00','2021-09-12T22:01:05.646393900',1,'Povišen krvni pritisak',1);
INSERT INTO "Examination" VALUES (13,1,-1,'Pregled vida',NULL,'2021-09-13T14:00','2021-09-13T10:52:08.071738',0,NULL,0);
COMMIT;
