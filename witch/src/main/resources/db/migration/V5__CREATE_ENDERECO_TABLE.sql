CREATE TABLE ENDERECO (
    idendereco INT PRIMARY KEY,
    userid INT,
    cep VARCHAR,
    cidade VARCHAR,
    estado VARCHAR,
    logradouro VARCHAR,
    bairro VARCHAR,
    numero VARCHAR,
    complemento VARCHAR,
    FOREIGN KEY (userid) REFERENCES User_Client(id)
);
