CREATE DATABASE ASM_Java_5;
use ASM_Java_5;

CREATE TABLE Users (
	ID_User INT PRIMARY KEY AUTO_INCREMENT,
    Username NVARCHAR(50) NOT NULL,
    Password NVARCHAR(100) NOT NULL,
    Fullname NVARCHAR(100) NOT NULL,
    Email NVARCHAR(100) NOT NULL,
    Phone NVARCHAR(20) NOT NULL,
    Gender BIT NOT NULL,
    Birthday DATE NOT NULL,
    Role NVARCHAR(20) NOT NULL
);
ALTER TABLE Users
ADD CONSTRAINT unique_username UNIQUE (Username);
DELETE FROM Users WHERE ID_User = 46;

INSERT INTO Users (Username, Password, Fullname, Email, Phone, Gender, Birthday, Role)
VALUES 
    ('user1', 'password1', N'Đỗ Mỹ Thuận', 'user1@example.com', '123456789', 1, '2000-01-01', 'User'),
    ('user2', 'password2', N'Dương Minh Bình', 'user2@example.com', '987654321', 0, '1995-05-05', 'Admin'),
    ('user3', 'password3', N'Trần Văn Việt', 'user3@example.com', '555555555', 1, '1988-12-12', 'User'),
    ('user4', 'password4', N'Nguyễn Thị Hằng', 'user4@example.com', '111111111', 0, '1992-08-15', 'User'),
    ('user5', 'password5', N'Lê Thị Mai', 'user5@example.com', '222222222', 0, '1998-03-20', 'User'),
    ('user6', 'password6', N'Phạm Thị Hương', 'user6@example.com', '333333333', 0, '1985-11-05', 'Admin'),
    ('user7', 'password7', N'Bùi Thị An', 'user7@example.com', '444444444', 0, '1990-06-25', 'User'),
    ('user8', 'password8', N'Vũ Thị Trang', 'user8@example.com', '555555555', 0, '1996-12-10', 'User');
INSERT INTO Users (Username, Password, Fullname, Email, Phone, Gender, Birthday, Role) 
VALUES 
('user7', 'password7', 'User Seven', 'user7@example.com', '123456785', 1, '1975-02-05', 'User'),
('user8', 'password8', 'User Eight', 'user8@example.com', '123456786', 0, '1990-08-15', 'User'),
('user9', 'password9', 'User Nine', 'user9@example.com', '123456787', 1, '1989-06-20', 'Admin'),
('user10', 'password10', 'User Ten', 'user10@example.com', '123456788', 0, '1985-03-15', 'User'),
('user11', 'password11', 'User Eleven', 'user11@example.com', '123456789', 1, '1982-12-10', 'User'),
('user12', 'password12', 'User Twelve', 'user12@example.com', '123456780', 0, '1979-10-05', 'Admin'),
('user13', 'password13', 'User Thirteen', 'user13@example.com', '123456781', 1, '1974-07-01', 'User'),
('user14', 'password14', 'User Fourteen', 'user14@example.com', '123456782', 0, '1988-04-20', 'User'),
('user15', 'password15', 'User Fifteen', 'user15@example.com', '123456783', 1, '1981-11-15', 'Admin');
INSERT INTO Users (Username, Password, Fullname, Email, Phone, Gender, Birthday, Role) 
VALUES 
('user16', 'password16', 'User Sixteen', 'user16@example.com', '123456784', 1, '1975-02-05', 'User'),
('user17', 'password17', 'User Seventeen', 'user17@example.com', '123456785', 0, '1990-08-15', 'User'),
('user18', 'password18', 'User Eighteen', 'user18@example.com', '123456786', 1, '1989-06-20', 'Admin'),
('user19', 'password19', 'User Nineteen', 'user19@example.com', '123456787', 0, '1985-03-15', 'User'),
('user20', 'password20', 'User Twenty', 'user20@example.com', '123456788', 1, '1982-12-10', 'User'),
('user21', 'password21', 'User Twenty One', 'user21@example.com', '123456789', 0, '1979-10-05', 'Admin'),
('user22', 'password22', 'User Twenty Two', 'user22@example.com', '123456780', 1, '1974-07-01', 'User'),
('user23', 'password23', 'User Twenty Three', 'user23@example.com', '123456781', 0, '1988-04-20', 'User'),
('user24', 'password24', 'User Twenty Four', 'user24@example.com', '123456782', 1, '1981-11-15', 'Admin'),
('user25', 'password25', 'User Twenty Five', 'user25@example.com', '123456783', 0, '1986-09-25', 'User');

CREATE TABLE Categories (
    CategoryID NVARCHAR(20) PRIMARY KEY,
    CategoryName NVARCHAR(100) NOT NULL,
    Note NVARCHAR(200)
);
INSERT INTO Categories (CategoryID, CategoryName, Note)
VALUES ('MP', N'Dược mỹ phẩm', NULL),
('TD', N'Trang điểm', NULL);

CREATE TABLE Products (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    product_title NVARCHAR(255) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    ImageURL NVARCHAR(255),
    Quantity INT NOT NULL,
    CategoryID NVARCHAR(20) NOT NULL,
    Sale DECIMAL(5, 2) DEFAULT 0,
	Note NVARCHAR(20),
    CONSTRAINT FK_CategoryID FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);
DELETE FROM Products WHERE ProductID = 29;
ALTER TABLE Products DROP COLUMN product_title;
ALTER TABLE Products CHANGE ProductTitle product_title NVARCHAR(255) NOT NULL;

INSERT INTO Products (product_title, Price, ImageURL, Quantity, CategoryID, Sale, Note)
VALUES
(N'Dầu gội Daeng nhân sâm phục hồi', 980000, N'product_1.jpg', 300, N'MP', 0, null),
(N'Tinh dầu dưỡng tóc tinh chất', 670000, N'product_2.jpg', 350, N'MP', 5, null),
(N'Serum bảo vệ da, chống tia UV', 780000, N'product_3.jpg', 300, N'MP', 0, null),
(N'Serum dưỡng da chống lão hóa', 870000, N'product_4.jpg', 100, N'MP', 0, null),
(N'Dược mỹ phẩm PBSerrum', 640000, N'product_5.jpg', 300, N'MP', 0, null),
(N'Dưỡng chất đắp mặt từ bùn biển', 450000, N'product_6.jpg', 300, N'MP', 0, null),
(N'Mặt nạ giảm nhờn, giảm mụn', 350000, N'product_7.jpg', 300, N'MP', 0, null),
(N'Mặt nạ dưỡng da Ecollagen', 630000, N'product_8.jpg', 300, N'MP', 0, null),
(N'Mặt nạ ngủ chăm sóc da mặt', 499000, N'product_9.jpg', 300, N'MP', 0, null),
(N'Nước dưỡng tóc hoa hồng', 760000, N'product_10.jpg', 300, N'MP', 0, null),
(N'Dầu xả dưỡng ẩm Ichikami', 870000, N'product_11.jpg', 300, N'MP', 0, null),
(N'Tinh dầu dưỡng tóc mềm mượt', 760000, N'product_12.jpg', 300, N'MP', 0, null);

INSERT INTO Products (product_title, Price, ImageURL, Quantity, CategoryID, Sale, Note)
VALUES
(N'Bút kẻ mắt NYX 11', 450000, N'product_12.jpg', 300, N'TD', 0, null),
(N'Bảng phấn Stila Sending', 560000, N'product_14.jpg', 350, N'TD', 5, null),
(N'Bảng phấn mắt Colourpop', 430000, N'product_15.jpg', 300, N'TD', 0, null),
(N'Phấn mắt FACE IT', 399000, N'product_16.jpg', 100, N'TD', 10, null),
(N'Bút kẻ mắt NYX', 459000, N'product_17.jpg', 300, N'TD', 0, null),
(N'Bút kẻ mắt Dior', 339000, N'product_18.jpg', 300, N'TD', 0, null),
(N'Dưỡng chất đắp mặt từ bùn biển', 350000, N'product_19.jpg', 300, N'TD', 0, null),
(N'Mặt nạ giảm nhờn, giảm mụn', 630000, N'product_20.jpg', 300, N'TD', 7, null),
(N'Sữa rửa mặt dưỡng ẩm', 499000, N'product_21.jpg', 300, N'TD', 0, null),
(N'Sữa rửa mặt Kose', 760000, N'product_22.jpg', 300, N'TD', 0, null),
(N'Sữa rửa mặt lô hội', 870000, N'product_23.jpg', 300, N'TD', 0, null),
(N'Sữa rửa mặt ngừa mụn', 760000, N'product_24.jpg', 300, N'TD', 15, null);
CREATE TABLE Invoices (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    ID_User INT NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    PhoneNumber NVARCHAR(20) NOT NULL,
    Address NVARCHAR(255) NOT NULL,
    CONSTRAINT FK_UserID FOREIGN KEY (ID_User) REFERENCES Users(ID_User)
);
CREATE TABLE InvoiceDetails (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    InvoiceID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT FK_InvoiceID FOREIGN KEY (InvoiceID) REFERENCES Invoices(ID),
    CONSTRAINT FK_ProductID FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

-- Chèn mẫu dữ liệu vào bảng Invoices
INSERT INTO Invoices (ID_User, TotalAmount, PhoneNumber, Address)
VALUES 
    (1, 150000, '123456789', '123 Main Street'),
    (2, 250000, '987654321', '456 Elm Street');
-- Chèn mẫu dữ liệu vào bảng InvoiceDetails
INSERT INTO InvoiceDetails (InvoiceID, ProductID, Quantity, Price)
VALUES 
    (1, 1, 2, 300000.00),
    (2, 3, 3, 750000.00)
