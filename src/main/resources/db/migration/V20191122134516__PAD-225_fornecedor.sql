create table seg_fornecedores
(
    id  BIGINT  IDENTITY(1, 1)          PRIMARY KEY,
    razao_social VARCHAR(100)           NOT NULL,
    cnpj  VARCHAR(14)         UNIQUE    NOT NULL,
    nome_fantasia  VARCHAR(100)         NOT NULL,
    endereco VARCHAR(100)               NOT NULL,
    telefone_contato VARCHAR(14)        NOT NULL,
    email_contato VARCHAR(50)           NOT NULL
);