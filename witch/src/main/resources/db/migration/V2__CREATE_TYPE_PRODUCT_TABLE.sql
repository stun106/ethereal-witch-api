CREATE TABLE Type_Product (
typeid INT,
typename VARCHAR(100) NOT NULL
);
CREATE INDEX idx_typeproduct_typeid ON Type_Product (typeid);