CREATE TABLE pacientes (
    id SERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    telefone VARCHAR(15),
    email VARCHAR(255) UNIQUE,
    genero VARCHAR(10),
    cep VARCHAR(8),
    endereco TEXT,
    ativo BOOLEAN NOT NULL DEFAULT true
);