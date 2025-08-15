create table holidays (
        id int NOT NULL AUTO_INCREMENT,
        name varchar(255),
        country varchar(255),
        holiday_date date,
        time_zone varchar(255),
        primary key (id)
    );