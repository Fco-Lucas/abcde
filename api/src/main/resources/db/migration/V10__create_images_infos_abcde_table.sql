CREATE TABLE images_infos_abcde (
    id BIGSERIAL PRIMARY KEY,
    lot_image_id BIGINT NOT NULL REFERENCES lots_images(id) ON DELETE CASCADE,
    original_name VARCHAR(255) NOT NULL,
    codigo_escola SMALLINT NOT NULL,
    ano INTEGER NOT NULL,
    grau_serie SMALLINT NOT NULL,
    turno CHAR(1) NOT NULL CHECK (turno IN ('M', 'T', 'N')),
    turma SMALLINT NOT NULL,
    etapa VARCHAR(1) NOT NULL,
    prova SMALLINT NOT NULL, -- 0 a 99
    gabarito VARCHAR(1) NOT NULL
);

CREATE INDEX fk_images_infos_abcde_lots_images ON images_infos_abcde(lot_image_id);