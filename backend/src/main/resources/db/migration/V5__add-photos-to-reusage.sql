CREATE TABLE join_reusage_photos
(
    reusage_id BIGINT REFERENCES reusage (id) ON DELETE CASCADE,
    photos      VARCHAR(255),
    CONSTRAINT pk_join_reusage_photos PRIMARY KEY (photos)
);
