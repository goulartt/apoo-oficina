CREATE TABLE IF NOT EXISTS oficina.service(
	id INT(11) not null PRIMARY KEY AUTO_INCREMENT,
	removed int not null,	
	service_name varchar(255) null,
	status int(11) null,
	id_customer int(11) null,
	FOREIGN KEY (id_customer) REFERENCES oficina.customer(id)
);



CREATE TABLE IF NOT EXISTS oficina.session (
	id int(11) not null PRIMARY KEY AUTO_INCREMENT,
	removed int not null,	
	session_date datetime null,
	observations varchar(255) null,
	price decimal(19,2) null,
	status int(11) null,
	id_service int(11) not null,
	FOREIGN KEY (id_service) REFERENCES oficina.service(id)
);