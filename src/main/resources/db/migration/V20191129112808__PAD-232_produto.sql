create table seg_produtos
(
    id_produto    BIGINT  IDENTITY(1, 1)        NOT NULL PRIMARY KEY,
    cod_produto     VARCHAR(100)        UNIQUE  NOT NULL,
    nome_produto    VARCHAR(100)        UNIQUE  NOT NULL,
    preco_produto   FLOAT(6)                  NOT NULL,
    linha_produto BIGINT FOREIGN KEY REFERENCES seg_linha_categorias (id_linha_categoria)  NOT NULL,
    unidade_caixa_produto BIGINT,
    peso_unidade FLOAT(8),
    validade DATE
);
