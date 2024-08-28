CREATE TABLE emails (
    id BIGSERIAL PRIMARY KEY,
    id_consulta BIGINT NOT NULL,
    id_paciente BIGINT NOT NULL,
    email_from VARCHAR(255) NOT NULL,
    email_to VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    send_data_email TIMESTAMP NOT NULL,
    status_email VARCHAR(50) NOT NULL
);
