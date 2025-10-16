CREATE TABLE IF NOT EXISTS emails (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    id_client UUID NOT NULL,
    id_client_user UUID DEFAULT NULL,
    type VARCHAR(100) NOT NULL CHECK (type IN ('DEFINE_PASSWORD', 'RESTORE_PASSWORD')),
    subject VARCHAR(150) NOT NULL,
    origin VARCHAR(150) NOT NULL,
    destiny VARCHAR(150) NOT NULL,
    destiny_name VARCHAR(100) NOT NULL,
    attachments VARCHAR(255) NOT NULL,
    status_code INTEGER DEFAULT NULL,
    template_id VARCHAR(201) NOT NULL,
    template_fields JSONB,
    appointment_date TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_emails_client FOREIGN KEY (id_client) REFERENCES clients (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_emails_client_user FOREIGN KEY (id_client_user) REFERENCES clients_users (id) ON UPDATE CASCADE ON DELETE SET NULL
);