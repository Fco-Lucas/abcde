CREATE TABLE clients_users (
    id UUID NOT NULL PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES clients(id),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    permission BIGINT NOT NULL REFERENCES permissions(id),
    status VARCHAR(10) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')) DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Index
CREATE UNIQUE INDEX idx_clients_users_email ON clients_users(email);

-- Relações
CREATE INDEX fk_clients_users_client_id ON clients_users(client_id);
CREATE INDEX fk_clients_users_permission ON clients_users(permission);
