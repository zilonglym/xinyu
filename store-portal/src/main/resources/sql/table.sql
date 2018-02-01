create table sc_express_price(
	id bigint primary key not null auto_increment,
	area varchar(255),
	first_price double(10,2) default 0,
	initial_price double(10,2) default 0,
	other_price double(10,2) default 0,
	delivery_price double(10,2) default 0,
	first_cost double(10,2) default 0,
	initial_cost double(10,2) default 0,
	other_cost double(10,2) default 0,
	delivery_cost double(10,2) default 0,
	name varchar(255),
	code varchar(255),
	user_id bigint,
	user_name varchar(255)
);

create table sc_record(
	id bigint primary key not null auto_increment,
	create_time date,
	user_name varchar(255),
	express_company varchar(255),
	express_orderno varchar(255),
	state varchar(255),
	center varchar(255),
	weight double(10,2) default 0,
	price double(10,2) default 0,
	cost double(10,2) default 0
);