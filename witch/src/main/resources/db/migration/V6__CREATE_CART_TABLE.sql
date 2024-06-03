CREATE TABLE Cart_Shopping(
cartid INT PRIMARY KEY,
cartproductid INT,
cartuserid INT,
dateregister DATE,
FOREIGN KEY (cartproductid) REFERENCES Product(productid),
FOREIGN KEY (cartuserid) REFERENCES User_Client(id)
);
