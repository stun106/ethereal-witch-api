CREATE TABLE Product (
productid INT,
nomeproduct VARCHAR(100) NOT NULL,
valor FLOAT,
nometype INT,
image VARCHAR(255),
productcategory INT,
createdat DATE,
FOREIGN KEY (nometype) REFERENCES Type_Product(typeid),
FOREIGN KEY (productcategory) REFERENCES Category(categoryid)
 );
 CREATE INDEX idx_product_product_id ON Product (productid);