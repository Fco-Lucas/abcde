CREATE TABLE auditlog_questions (
    id BIGSERIAL PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES clients(id) ON DELETE CASCADE,
    user_id UUID NOT NULL,
    image_id BIGINT NOT NULL REFERENCES lots_images(id) ON DELETE CASCADE,
    details TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX fk_auditlog_questions_clients ON auditlog_questions(client_id);
CREATE INDEX fk_auditlog_questions_lots_images ON auditlog_questions(image_id);
