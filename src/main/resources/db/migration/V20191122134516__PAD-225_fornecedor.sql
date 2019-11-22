create table seg_fornecedores
(
    id    BIGINT  IDENTITY(1, 1)        NOT NULL,
    razao_social VARCHAR(100)           NOT NULL,
    cnpj  VARCHAR(30)         UNIQUE    NOT NULL,
    nome_fantasia  VARCHAR(150)         NOT NULL,
    endereco VARCHAR(200)               UNIQUE,
    telefone_contato VARCHAR(20)        UNIQUE,
    email_contato VARCHAR(120)          UNIQUE
);