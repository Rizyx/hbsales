create table seg_linha_categorias
(
    id_linha_categoria    BIGINT IDENTITY (1, 1)                   NOT NULL PRIMARY KEY ,
    cod_linha_categoria VARCHAR(100)            UNIQUE     NOT NULL,
    categoria_linha  BIGINT FOREIGN KEY REFERENCES seg_categoria_produtos (id_categoria_produtos)  NOT NULL,
    nome_linha VARCHAR(100)         UNIQUE     NOT NULL

);