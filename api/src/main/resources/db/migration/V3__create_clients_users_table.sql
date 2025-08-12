CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE clients_users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id UUID NOT NULL REFERENCES clients(id),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    permission BIGINT NOT NULL REFERENCES permissions(id),
    status VARCHAR(10) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')) DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Relações
CREATE INDEX fk_clients_users_client_id ON clients_users(client_id);
CREATE INDEX fk_clients_users_permission ON clients_users(permission);
