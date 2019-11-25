create table seg_produtos
(
    id    BIGINT IDENTITY (1, 1)                   NOT NULL,
    nome_categoria VARCHAR(100)         UNIQUE     NOT NULL,
    fornecedor_categoria  BIGINT FOREIGN KEY REFERENCES seg_fornecedores (id_fornecedor) UNIQUE NOT NULL
);