CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    upload_files BOOLEAN NOT NULL DEFAULT true,
    read_files BOOLEAN NOT NULL DEFAULT true
);

-- Insere as permissões:
INSERT INTO permissions (upload_files, read_files) VALUES (true, true);
INSERT INTO permissions (upload_files, read_files) VALUES (true, false);
INSERT INTO permissions (upload_files, read_files) VALUES (false, true);