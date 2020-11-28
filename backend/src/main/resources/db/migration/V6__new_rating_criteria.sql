ALTER TABLE review
    DROP COLUMN box_rating,
    DROP COLUMN box_reusability_rating,
    DROP COLUMN product_reusability_rating,
    ADD COLUMN box_reusable INT NOT NULL,
    ADD COLUMN box_recycable INT NOT NULL,
    ADD COLUMN box_from_recycling INT NOT NULL,
    ADD COLUMN product_reusable INT NOT NULL,
    ADD COLUMN product_recycable INT NOT NULL,
    ADD COLUMN product_from_recycling INT NOT NULL,
    ADD COLUMN repairable INT NOT NULL;