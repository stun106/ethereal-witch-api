CREATE TABLE Product (
productid INT,
nomeproduct VARCHAR(100) NOT NULL,
valor FLOAT,
productcategory INT,
createdat DATE,
FOREIGN KEY (productcategory) REFERENCES Category(categoryid)
 );
 CREATE INDEX idx_product_product_id ON Product (productid);