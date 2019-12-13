create table seg_funcionarios
(
    id  BIGINT  IDENTITY(1, 1)             PRIMARY KEY,
    nome_funcionario VARCHAR(100)           NOT NULL,
    email_funcionario  VARCHAR(30)          UNIQUE
);