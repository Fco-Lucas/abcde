CREATE TABLE auditlog_questions (
    id BIGSERIAL PRIMARY KEY,
    client_id UUID NOT NULL,
    user_id UUID NOT NULL,
    image_id BIGINT NOT NULL REFERENCES lots_images(id) ON DELETE CASCADE,
    details TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);