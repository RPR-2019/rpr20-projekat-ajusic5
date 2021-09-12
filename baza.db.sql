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
	"doctor_id"	INTEGER,
	PRIMARY KEY("doctor_id")
);
CREATE TABLE IF NOT EXISTS "Ljekar_usluge" (
	"med_service_id"	INTEGER,
	"doctor_id"	INTEGER,
	FOREIGN KEY("med_service_id") REFERENCES "Usluge"("id"),
	FOREIGN KEY("doctor_id") REFERENCES "Ljekar"("doctor_id")
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
	"archived"	BOOLEAN,
	PRIMARY KEY("id"),
	FOREIGN KEY("patient_id") REFERENCES "Pacijent"("patient_id"),
	FOREIGN KEY("doctor_id") REFERENCES "Ljekar"("doctor_id")
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
	"patient_id"	INTEGER,
	PRIMARY KEY("patient_id")
);
INSERT INTO "Usluge" VALUES (3,'Oftamolog');
INSERT INTO "Usluge" VALUES (1,'Kardiolog');
INSERT INTO "Usluge" VALUES (2,'Psihijatar');
INSERT INTO "Ljekar" VALUES ('mmujic1','mujo','Mujo','Mujić','1989-11-18','Ljekar','M','Oftamolog',1);
INSERT INTO "Ljekar" VALUES ('ssuljic1','suljo','Suljo','Suljić','1969-09-07','Ljekar','M','Oftamolog',2);
INSERT INTO "Ljekar" VALUES ('iivic1','iva','Iva','Ivić','1915-06-14','Ljekar','Ž','Oftamolog',3);
INSERT INTO "Ljekar" VALUES ('zjavdan1','zehra','Zehra','Javdan','1999-11-27','Ljekar','Ž','Oftamolog',4);
INSERT INTO "Ljekar" VALUES ('hhanic1','hana','Hana','Hanić','1979-07-10','Ljekar','Ž','Oftamolog',5);
INSERT INTO "Ljekar_usluge" VALUES (3,4);
INSERT INTO "Ljekar_usluge" VALUES (1,5);
INSERT INTO "Ljekar_usluge" VALUES (1,4);
INSERT INTO "Ljekar_usluge" VALUES (2,4);
INSERT INTO "Ljekar_usluge" VALUES (3,5);
INSERT INTO "Ljekar_usluge" VALUES (2,5);
INSERT INTO "Ljekar_usluge" VALUES (3,3);
INSERT INTO "Ljekar_usluge" VALUES (2,3);
INSERT INTO "Ljekar_usluge" VALUES (1,3);
INSERT INTO "Pregled" VALUES (1,1,3,'Oftamolog','Terapijske naočale','2021-09-08T10:30','2021-09-06T10:00',1,'Strabizam',1);
INSERT INTO "Pregled" VALUES (2,1,-2,'Psihijatar',NULL,'2021-09-22T10:30','2021-09-06T18:14:26.349178800',0,NULL,0);
INSERT INTO "Pregled" VALUES (3,1,-1,'Kardiolog',NULL,'2021-09-23T12:00','2021-09-07T11:20:57.179033400',0,NULL,0);
INSERT INTO "Pregled" VALUES (4,1,-2,'Psihijatar',NULL,'2021-09-23T11:00','2021-09-07T11:36:12.554460700',0,NULL,0);
INSERT INTO "Pregled" VALUES (5,1,-2,'Oftamolog',NULL,'2021-09-13T10:00','2021-09-07T11:43:54.217841700',0,NULL,0);
INSERT INTO "Pregled" VALUES (6,1,-2,'Kardiolog',NULL,'2021-09-23T10:30','2021-09-07T11:48:32.561736100',0,NULL,0);
INSERT INTO "Pregled" VALUES (7,1,-1,'Psihijatar',NULL,'2021-09-11T13:30','2021-09-07T11:48:51.394333300',0,NULL,0);
INSERT INTO "Pregled" VALUES (8,1,3,'Oftamolog','Terapijske naočale','2021-09-16T14:30','2021-09-07T17:24:39.426522700',1,'Strabizam',1);
INSERT INTO "Pacijent" VALUES ('ajusic5','neithoaleeng','Amna','Jusić','1999-05-18','pacijent','Ž',1,1);
INSERT INTO "Pacijent" VALUES ('amilaj','amila','Amila','Jusić','1996-01-14','Pacijent','Ž',2,2);
INSERT INTO "Pacijent" VALUES ('hjusic1','haris','Haris','Jusić','1958-03-24','Pacijent','M',3,3);
COMMIT;
