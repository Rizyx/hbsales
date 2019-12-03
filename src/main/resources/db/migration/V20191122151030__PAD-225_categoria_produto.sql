create table seg_categoria_produtos
(
    id   BIGINT IDENTITY (1, 1)                   NOT NULL  PRIMARY KEY,
    cod_categoria VARCHAR(100)            UNIQUE     NOT NULL,
    nome_categoria VARCHAR(100)         UNIQUE     NOT NULL,
    fornecedor_categoria  BIGINT FOREIGN KEY REFERENCES seg_fornecedores (id)  NOT NULL
);
