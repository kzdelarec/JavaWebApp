
create table if not exists users (
id long auto_increment not null,
username varchar(20) not null,
password varchar(100) not null,
enabled bit not null,
primary key(username)
);

create table if not exists authorities (
username varchar(20) not null,
authority varchar(20) not null
);

create table if not exists Test (
id int auto_increment not null,
name varchar(255) not null,
primary key(id)
);

create table if not exists wallet(
id long auto_increment not null,
name varchar(255) not null,
createdate date not null,
username varchar(20) not null,
primary key(id),
foreign key(username) references users(username)
);

create table if not exists expense(
id long auto_increment not null,
name varchar(255) not null,
price decimal(6,2) not null,
type varchar(20) not null,
createdate date not null,
walletid long not null,
expensetype varchar(20) not null,
primary key(id),
foreign key(walletid) references wallet(id)
);

