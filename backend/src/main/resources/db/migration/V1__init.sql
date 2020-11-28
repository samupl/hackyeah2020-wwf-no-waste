CREATE TABLE product
(
    id          BIGSERIAL PRIMARY KEY,
    bar_code    VARCHAR(255) UNIQUE NOT NULL,
    name        VARCHAR(255)        NOT NULL,
    category_id BIGINT              NOT NULL
);

CREATE TABLE category
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE tag
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE review
(
    id                         BIGSERIAL PRIMARY KEY,
    date                       TIMESTAMP    NOT NULL,
    author                     VARCHAR(255) NOT NULL,
    comment                    VARCHAR(255) NOT NULL,
    box_rating                 INT          NOT NULL,
    box_reusability_rating     INT          NOT NULL,
    product_reusability_rating INT,
    product_id                 BIGINT       NOT NULL
);

CREATE TABLE join_product_tag
(
    product_id BIGINT REFERENCES product (id) ON DELETE CASCADE,
    tag_id     BIGINT REFERENCES tag (id) ON DELETE CASCADE,
    CONSTRAINT pk_join_product_tag PRIMARY KEY (product_id, tag_id)
);

ALTER TABLE product
    ADD CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE review
    ADD CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id);
