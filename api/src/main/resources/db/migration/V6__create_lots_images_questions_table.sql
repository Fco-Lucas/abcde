CREATE TABLE lots_images_questions(
    id BIGSERIAL PRIMARY KEY,
    image_id BIGINT NOT NULL REFERENCES lots_images(id) ON DELETE CASCADE,
    number INT NOT NULL,
    alternative VARCHAR(5) NOT NULL
);

CREATE INDEX fk_lots_images_questions_lots_images ON lots_images_questions(image_id);