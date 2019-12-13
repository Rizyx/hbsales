create table seg_linha_categorias
(
    id    BIGINT IDENTITY (1, 1)                  PRIMARY KEY ,
    cod_linha_categoria VARCHAR(10)            UNIQUE     NOT NULL,
    categoria_linha  BIGINT FOREIGN KEY REFERENCES seg_categoria_produtos (id)  NOT NULL,
    nome_linha VARCHAR(50)             NOT NULL,
    UNIQUE("categoria_linha","nome_linha")

);