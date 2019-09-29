BEGIN TRANSACTION;
CREATE TABLE  "shop" (
    "id" INTEGER,
	"shopName"	TEXT,
	"address"	TEXT
);
CREATE TABLE "glasses" (
    "id" INTEGER,
	"manufacturer"	TEXT,
	"model"	INTEGER,
	"yearOfProduction"	TEXT,
	"price"	INTEGER,
	"shop_id"	INTEGER,
	"number"	TEXT
);
CREATE TABLE "employee" (
	"id"	INTEGER,
	"name"	TEXT,
	"lastName"	TEXT,
	"birthDate"	TEXT,
	"address"	TEXT,
	"contactNumber"	TEXT,
	"password"	TEXT,
	"type" INT,
	"shop_id"	INTEGER
);
COMMIT;
