CREATE TABLE auditlog (
    id BIGSERIAL PRIMARY KEY,
    action VARCHAR(100) NOT NULL,
    client_id UUID NOT NULL REFERENCES clients(id) ON DELETE CASCADE,
    user_id UUID NOT NULL,
    program VARCHAR(255) NOT NULL,
    details TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX fk_auditlog_clients ON auditlog(client_id);
