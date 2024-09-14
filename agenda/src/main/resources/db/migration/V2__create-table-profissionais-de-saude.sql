CREATE TABLE profissionais_de_saude (
    id SERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    registro VARCHAR(50),
    genero VARCHAR(20),
    cep VARCHAR(8),
    endereco TEXT,
    ativo BOOLEAN NOT NULL
);