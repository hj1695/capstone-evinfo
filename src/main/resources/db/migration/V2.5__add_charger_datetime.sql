ALTER TABLE charger
    ADD COLUMN last_charge_date_time TIMESTAMP NOT NULL;
ALTER TABLE charger
    ADD COLUMN start_charge_date_time TIMESTAMP NOT NULL;
