drop table if exists SHIRTS;
drop table if exists WAREHOUSES;

create table WAREHOUSES (
	id SERIAL PRIMARY KEY,
	warehouse_name VARCHAR(50),
	city VARCHAR(50),
	capacity INT
);

create table SHIRTS (
	id SERIAL PRIMARY KEY,
	animal_graphic VARCHAR(50),
	shirt_size VARCHAR(50),
	warehouse_id INT,
	FOREIGN KEY (warehouse_id) REFERENCES WAREHOUSES(id) ON DELETE CASCADE
);