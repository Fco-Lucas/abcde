CREATE TABLE lots_images(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    lot_id BIGINT NOT NULL REFERENCES lots(id) ON DELETE CASCADE,
    key VARCHAR(255) NOT NULL,
    status VARCHAR(12) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')) DEFAULT 'ACTIVE'
);

CREATE INDEX fk_lots_images_lots ON lots_images(lot_id);