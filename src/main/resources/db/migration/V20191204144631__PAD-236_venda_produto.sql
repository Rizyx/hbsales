create table seg_venda_fornecedor
(
    id    BIGINT  IDENTITY(1, 1)        NOT NULL PRIMARY KEY,
    data_inicio DATE NOT NULL,
    data_final DATE NOT NULL,
    dia_retirada DATE NOT NULL,
    venda_fornecedor  BIGINT FOREIGN KEY REFERENCES seg_fornecedores (id)  NOT NULL
    )