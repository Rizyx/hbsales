create table seg_produtos
(
    id    BIGINT IDENTITY (1, 1)            NOT NULL,
    nome_categoria VARCHAR(100)     UNIQUE  NOT NULL,
    fornecedor_categoria VARCHAR(100)       NOT NULL
);