create table users(
    id int unsigned not null primary key auto_increment,
    name varchar(100) not null,
    password varchar(100) not null,
    type varchar(10) not null,
    login tinyint(1) not null
);

create table categories(
    id int unsigned not null primary key auto_increment,
    name varchar(100) not null
);

create table items(
    id int unsigned not null primary key auto_increment,
    name varchar(50) not null,
    description varchar(100),
    quantity int unsigned,
    price int unsigned not null,
    category_id int unsigned not null,
    foreign key(category_id) references categories(id);
);

create table sales(
    id int unsigned not null primary key auto_increment,
    name varchar(50) not null,
    quantity int unsigned not null,
    price int unsigned not null
    category_id int unsigned not null,
    foreign key(category_id) references categories(id);
);
