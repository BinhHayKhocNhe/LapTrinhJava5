CREATE DATABASE J5Shop;
USE J5Shop;

CREATE TABLE Accounts(
	Username NVARCHAR(50) NOT NULL PRIMARY KEY,
    Password NVARCHAR(50) NOT NULL,
    Fullname NVARCHAR(50) NOT NULL,
    Email NVARCHAR(50) NOT NULL,
    Photo NVARCHAR(50) NOT NULL,
    Activated BIT NOT NULL,
    Admin BIT NOT NULL
);
INSERT INTO Accounts (Username, Password, Fullname, Email, Photo, Activated, Admin) VALUES
('user1', 'password1', 'Full Name 1', 'user1@example.com', 'photo1.jpg', b'1', b'0'),
('user2', 'password2', 'Full Name 2', 'user2@example.com', 'photo2.jpg', b'1', b'0'),
('user3', 'password3', 'Full Name 3', 'user3@example.com', 'photo3.jpg', b'1', b'0'),
('user4', 'password4', 'Full Name 4', 'user4@example.com', 'photo4.jpg', b'1', b'0'),
('user5', 'password5', 'Full Name 5', 'user5@example.com', 'photo5.jpg', b'1', b'0'),
('user6', 'password6', 'Full Name 6', 'user6@example.com', 'photo6.jpg', b'1', b'0'),
('user7', 'password7', 'Full Name 7', 'user7@example.com', 'photo7.jpg', b'1', b'0'),
('user8', 'password8', 'Full Name 8', 'user8@example.com', 'photo8.jpg', b'1', b'0'),
('user9', 'password9', 'Full Name 9', 'user9@example.com', 'photo9.jpg', b'1', b'0'),
('user10', 'password10', 'Full Name 10', 'user10@example.com', 'photo10.jpg', b'1', b'0'),
('user11', 'password11', 'Full Name 11', 'user11@example.com', 'photo11.jpg', b'1', b'0'),
('user12', 'password12', 'Full Name 12', 'user12@example.com', 'photo12.jpg', b'1', b'0'),
('user13', 'password13', 'Full Name 13', 'user13@example.com', 'photo13.jpg', b'1', b'0'),
('user14', 'password14', 'Full Name 14', 'user14@example.com', 'photo14.jpg', b'1', b'0'),
('user15', 'password15', 'Full Name 15', 'user15@example.com', 'photo15.jpg', b'1', b'0');


CREATE TABLE Categories(
	Id CHAR(4) NOT NULL PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL
);
INSERT INTO Categories (Id, Name) VALUES
('C001', 'Electronics'),
('C002', 'Books'),
('C003', 'Clothing'),
('C004', 'Home & Kitchen'),
('C005', 'Toys'),
('C006', 'Sports'),
('C007', 'Beauty'),
('C008', 'Health'),
('C009', 'Automotive'),
('C010', 'Garden'),
('C011', 'Music'),
('C012', 'Movies'),
('C013', 'Games'),
('C014', 'Software'),
('C015', 'Office Supplies');

CREATE TABLE Orders(
	Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    Username NVARCHAR(50) NOT NULL,
    CreateDate DATETIME NOT NULL,
    Address NVARCHAR(255) NOT NULL,
    CONSTRAINT FK_Username_Orders FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
INSERT INTO Orders (Username, CreateDate, Address) VALUES
('user1', '2024-05-27 10:00:00', '123 Main St'),
('user2', '2024-05-28 11:30:00', '456 Elm St'),
('user3', '2024-05-29 14:45:00', '789 Oak St'),
('user4', '2024-05-30 09:00:00', '10 Pine St'),
('user5', '2024-05-31 10:30:00', '20 Maple St'),
('user6', '2024-06-01 11:45:00', '30 Oak St'),
('user7', '2024-06-02 12:00:00', '40 Cedar St'),
('user8', '2024-06-03 13:15:00', '50 Elm St'),
('user9', '2024-06-04 14:30:00', '60 Walnut St'),
('user10', '2024-06-05 15:45:00', '70 Birch St'),
('user11', '2024-06-06 16:00:00', '80 Spruce St'),
('user12', '2024-06-07 17:15:00', '90 Cedar St'),
('user13', '2024-06-08 18:30:00', '100 Elm St');

CREATE TABLE Products(
	Id INT AUTO_INCREMENT PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL,
    Image NVARCHAR(50) NOT NULL,
    Price FLOAT NOT NULL,
    Creationdate DATE NOT NULL,
    Available BIT NOT NULL,
    CategoryID CHAR(4) NOT NULL,
    CONSTRAINT FK_Category_Products FOREIGN KEY (CategoryID) REFERENCES Categories(Id)
);
select * from products;
INSERT INTO Products (Name, Image, Price, Creationdate, Available, CategoryID) VALUES
('Product 1', 'image1.jpg', 10.99, '2024-05-26', b'1', 'C001'),
('Product 2', 'image2.jpg', 20.99, '2024-05-26', b'1', 'C002'),
('Product 3', 'image3.jpg', 30.99, '2024-05-26', b'1', 'C001'),
('Product 4', 'image4.jpg', 40.99, '2024-05-26', b'1', 'C002'),
('Product 5', 'image5.jpg', 50.99, '2024-05-26', b'1', 'C001'),
('Product 6', 'image6.jpg', 60.99, '2024-05-26', b'1', 'C002'),
('Product 7', 'image7.jpg', 70.99, '2024-05-26', b'1', 'C001'),
('Product 8', 'image8.jpg', 80.99, '2024-05-26', b'1', 'C002'),
('Product 9', 'image9.jpg', 90.99, '2024-05-26', b'1', 'C001'),
('Product 10', 'image10.jpg', 100.99, '2024-05-26', b'1', 'C002');

CREATE TABLE OrderDetails(
	Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    OrderId BIGINT NOT NULL,
    ProductId INT NOT NULL,
    Price FLOAT NOT NULL,
    Quantity INT NOT NULL,
    CONSTRAINT FK_OrderId FOREIGN KEY (OrderId) REFERENCES Orders(Id),
    CONSTRAINT FK_ProductId FOREIGN KEY (ProductId) REFERENCES Products(Id)
);
INSERT INTO OrderDetails (OrderId, ProductId, Price, Quantity) VALUES
(1, 1, 100, 1),
(1, 2, 200, 2),
(2, 3, 300, 1),
(2, 4, 400, 1),
(3, 5, 500, 3),
(3, 6, 600, 1),
(4, 7, 700, 2),
(4, 8, 800, 1),
(5, 9, 900, 2),
(5, 10, 1000, 1);


