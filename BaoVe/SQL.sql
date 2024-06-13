create database BaoVe;
use BaoVe;

create table users (
	id nvarchar(30) not null primary key,
    pass nvarchar(50) not null,
    fullname nvarchar(50) not null,
    email nvarchar(50) not null,
    admin bit not null
);
select * from users;
INSERT INTO users (id, pass, fullname, email, admin) VALUES
('user1', 'password1', 'Alice Johnson', 'alice.johnson@example.com', 0),
('user2', 'password2', 'Bob Smith', 'bob.smith@example.com', 1),
('user3', 'password3', 'Charlie Brown', 'charlie.brown@example.com', 0),
('user4', 'password4', 'David Wilson', 'david.wilson@example.com', 0),
('user5', 'password5', 'Eve Davis', 'eve.davis@example.com', 1),
('user6', 'password6', 'Frank Miller', 'frank.miller@example.com', 0),
('user7', 'password7', 'Grace Lee', 'grace.lee@example.com', 0),
('user8', 'password8', 'Hannah Young', 'hannah.young@example.com', 0),
('user9', 'password9', 'Ian Clark', 'ian.clark@example.com', 0),
('user10', 'password10', 'Jane Walker', 'jane.walker@example.com', 0);
