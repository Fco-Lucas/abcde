CREATE TABLE lots_images (
    id BIGSERIAL PRIMARY KEY,
    lot_id BIGINT NOT NULL REFERENCES lots(id) ON DELETE CASCADE,
    key VARCHAR(100) NOT NULL,
    original_name VARCHAR(255) NOT NULL,
    matricula INTEGER NOT NULL, -- Até 2 bilhões
    nome_aluno VARCHAR(70) NOT NULL,
    etapa VARCHAR(1) NOT NULL,
    prova SMALLINT NOT NULL, -- 0 a 99
    gabarito VARCHAR(1) NOT NULL,
    presenca SMALLINT NOT NULL,
    qtd_questoes INTEGER NOT NULL,
    have_modification BOOLEAN NOT NULL DEFAULT false,
    status VARCHAR(12) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')) DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX fk_lots_images_lots ON lots_images(lot_id);
