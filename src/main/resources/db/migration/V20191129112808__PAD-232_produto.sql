create table seg_produtos
(
    id    BIGINT  IDENTITY(1, 1)       PRIMARY KEY,
    cod_produto     VARCHAR(10)        UNIQUE  NOT NULL,
    nome_produto    VARCHAR(200)        UNIQUE  NOT NULL,
    preco_produto   DECIMAL(10,2)                  NOT NULL,
    linha_produto BIGINT FOREIGN KEY REFERENCES seg_linha_categorias (id)  NOT NULL,
    unidade_caixa_produto BIGINT NOT NULL,
    peso_unidade DECIMAL(10,3) NOT NULL,
    unidade_medida_peso VARCHAR(5) NOT NULL,
    validade DATE NOT NULL,
    UNIQUE("linha_produto","nome_produto")
);
