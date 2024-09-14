CREATE TABLE consultas (
    id SERIAL PRIMARY KEY,
    profissional_de_saude_id BIGINT REFERENCES profissionais_de_saude(id) ON DELETE SET NULL,
    paciente_id BIGINT REFERENCES pacientes(id) ON DELETE SET NULL,
    data TIMESTAMP NOT NULL,
    descricao TEXT
);