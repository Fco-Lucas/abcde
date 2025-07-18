CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    upload_files BOOLEAN NOT NULL DEFAULT true,
    read_files BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Insere as permissões:
INSERT INTO permissions (upload_files, read_files) VALUES (true, true);
INSERT INTO permissions (upload_files, read_files) VALUES (true, false);
INSERT INTO permissions (upload_files, read_files) VALUES (false, true);