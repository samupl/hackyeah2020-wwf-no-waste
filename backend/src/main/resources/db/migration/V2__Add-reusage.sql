CREATE TABLE reusage
(
    id          BIGSERIAL PRIMARY KEY,
    date        TIMESTAMP    NOT NULL,
    title       VARCHAR(255) NOT NULL,
    author      VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    photos      VARCHAR(255),
    up_votes    INT          NOT NULL DEFAULT 0,
    down_votes  INT          NOT NULL DEFAULT 0
);

CREATE TABLE join_reusage_category
(
    reusage_id  BIGINT REFERENCES reusage (id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES category (id) ON DELETE CASCADE,
    CONSTRAINT pk_join_reusage_category PRIMARY KEY (reusage_id, category_id)
);

CREATE TABLE join_reusage_tag
(
    reusage_id BIGINT REFERENCES reusage (id) ON DELETE CASCADE,
    tag_id     BIGINT REFERENCES tag (id) ON DELETE CASCADE,
    CONSTRAINT pk_join_reusage_tag PRIMARY KEY (reusage_id, tag_id)
);