create table seg_categoria_produtos
(
    id   BIGINT IDENTITY (1, 1)                   PRIMARY KEY,
    cod_categoria VARCHAR(10)            UNIQUE     NOT NULL,
    nome_categoria VARCHAR(50)                   NOT NULL,
    fornecedor_categoria  BIGINT FOREIGN KEY REFERENCES seg_fornecedores (id)  NOT NULL,
    UNIQUE("fornecedor_categoria","nome_categoria")
);
