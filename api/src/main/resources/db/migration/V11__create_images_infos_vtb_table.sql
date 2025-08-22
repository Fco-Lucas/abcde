CREATE TABLE images_infos_vtb (
    id BIGSERIAL PRIMARY KEY,
    lot_image_id BIGINT NOT NULL REFERENCES lots_images(id) ON DELETE CASCADE,
    original_name VARCHAR(255) NOT NULL,
    vtb_codigo VARCHAR(9) NOT NULL,
    vtb_fracao VARCHAR(2) NOT NULL,
    fase_gab SMALLINT NOT NULL, -- 0 a 99
    prova SMALLINT NOT NULL
);

CREATE INDEX fk_images_infos_vtb_lots_images ON images_infos_vtb(lot_image_id);
