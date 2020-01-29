BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS  "shop" (
    "id" INTEGER,
	"shop_name"	TEXT,
	"address"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "glasses" (
	"id"	INTEGER,
	"manufacturer"	TEXT,
	"model"	TEXT,
	"year_of_production"	TEXT,
	"price"	INTEGER,
	"type"	TEXT,
	"shop_id"	INTEGER,
	"quantity"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("shop_id") REFERENCES "shop"("id")
);
CREATE TABLE IF NOT EXISTS "employee" (
	"id"	INTEGER,
	"name"	TEXT,
	"last_name"	TEXT,
	"birth_date"	TEXT,
	"address"	TEXT,
	"phone_number"	TEXT,
	"password_hash"	TEXT,
	"type" INT,
	"shop_id"	INTEGER,
	PRIMARY KEY("id"),
    FOREIGN KEY("shop_id") REFERENCES "shop"("id")
);
COMMIT;