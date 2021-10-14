ALTER TABLE charger
    ADD COLUMN price DOUBLE NOT NULL CHECK (price >= 0);
