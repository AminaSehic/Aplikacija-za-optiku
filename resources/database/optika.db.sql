BEGIN TRANSACTION;
CREATE TABLE  "shop" (
    "id" INTEGER,
	"shop_name"	TEXT,
	"address"	TEXT
);
CREATE TABLE "glasses" (
    "id" INTEGER,
	"manufacturer"	TEXT,
	"model"	INTEGER,
	"year_of_production"	TEXT,
	"price"	INTEGER,
	"type" TEXT,
	"shop_id"	INTEGER,
	"quantity"	TEXT
);
CREATE TABLE "employee" (
	"id"	INTEGER,
	"name"	TEXT,
	"last_name"	TEXT,
	"birth_date"	TEXT,
	"address"	TEXT,
	"phone_number"	TEXT,
	"password_hash"	TEXT,
	"type" INT,
	"shop_id"	INTEGER
);
COMMIT;