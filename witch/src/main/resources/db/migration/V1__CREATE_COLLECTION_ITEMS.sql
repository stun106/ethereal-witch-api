CREATE TABLE Category (
categoryid INT PRIMARY KEY,
nomecategory VARCHAR(100) NOT NULL
);
CREATE INDEX idx_category_categoryid ON Category (categoryid);