create table seg_funcionarios
(
    id  BIGINT  IDENTITY(1, 1)             PRIMARY KEY,
    nome_funcionario VARCHAR(50)           NOT NULL,
    email_funcionario  VARCHAR(50)         NOT NULL  UNIQUE,
    nome VARCHAR(36)                       NOT NULL
);