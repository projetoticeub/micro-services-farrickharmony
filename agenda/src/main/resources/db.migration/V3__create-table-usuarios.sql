CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);
