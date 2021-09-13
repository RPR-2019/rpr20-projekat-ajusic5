BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Usluge" (
	"id"	INTEGER,
	"name"	TEXT
);
CREATE TABLE IF NOT EXISTS "Ljekar" (
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
CREATE TABLE IF NOT EXISTS "Ljekar_usluge" (
	"med_service_id"	INTEGER,
	"doctor_id"	INTEGER
);
CREATE TABLE IF NOT EXISTS "Pacijent" (
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
CREATE TABLE IF NOT EXISTS "Pregled" (
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
INSERT INTO "Usluge" VALUES (1,'Pregled vida');
INSERT INTO "Usluge" VALUES (2,'Mjerenje očnog pritiska');
INSERT INTO "Usluge" VALUES (3,'Ultrazvuk srca');
INSERT INTO "Usluge" VALUES (4,'EKG');
INSERT INTO "Usluge" VALUES (5,'Psihijatrijski intervju');
INSERT INTO "Usluge" VALUES (6,'Psihoterapija');
INSERT INTO "Usluge" VALUES (7,'Fizikalni pregled');
INSERT INTO "Usluge" VALUES (8,'Ultrazvučni pregled djeteta');
INSERT INTO "Ljekar" VALUES ('mmujic1','mujo','Mujo','Mujić','1989-11-18','Ljekar','M','Oftamolog',1);
INSERT INTO "Ljekar" VALUES ('ssuljic1','suljo','Suljo','Suljić','1969-09-07','Ljekar','M','Kardiolog',2);
INSERT INTO "Ljekar" VALUES ('iivic1','iva','Iva','Ivić','1915-06-14','Ljekar','Ž','Psihijatar',3);
INSERT INTO "Ljekar" VALUES ('zjavdan1','zehra','Zehra','Javdan','1999-11-27','Ljekar','Ž','Pedijatar',4);
INSERT INTO "Ljekar_usluge" VALUES (2,3);
INSERT INTO "Ljekar_usluge" VALUES (1,3);
INSERT INTO "Pacijent" VALUES ('ajusic5','neithoaleeng','Amna','Jusić','1999-05-18','pacijent','Ž',1,1);
INSERT INTO "Pacijent" VALUES ('amilaj','amila','Amila','Jusić','1996-01-14','Pacijent','Ž',2,2);
INSERT INTO "Pacijent" VALUES ('hjusic1','haris','Haris','Jusić','1958-03-24','Pacijent','M',3,3);
INSERT INTO "Pregled" VALUES (1,1,3,'Pregled vida','Terapijske naočale','2021-09-08T10:30','2021-09-06T10:00',1,'Strabizam',1);
INSERT INTO "Pregled" VALUES (8,1,3,'Mjerenje očnog pritiska','Terapijske naočale','2021-09-16T14:30','2021-09-07T17:24:39.426522700',1,'Strabizam',1);
INSERT INTO "Pregled" VALUES (9,1,3,'EKG','Tablete','2021-09-12T15:30','2021-09-12T17:38:35.157637400',1,'Povišen krvni pritisak',1);
INSERT INTO "Pregled" VALUES (11,2,3,'EKG','Tablete','2021-09-12T12:00','2021-09-12T22:01:05.646393900',1,'Povišen krvni pritisak',1);
INSERT INTO "Pregled" VALUES (13,1,-1,'Pregled vida',NULL,'2021-09-13T14:00','2021-09-13T10:52:08.071738',0,NULL,0);
COMMIT;
