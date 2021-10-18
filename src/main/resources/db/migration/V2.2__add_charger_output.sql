ALTER TABLE charger
    ADD COLUMN output BIGINT NOT NULL CHECK (output >= 0);
