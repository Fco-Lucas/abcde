CREATE TABLE clients (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')) DEFAULT 'ACTIVE'
);

-- Index
CREATE UNIQUE INDEX idx_clients_cnpj ON clients(cnpj);