DROP TABLE charger;

CREATE TABLE charger
(
    charger_id   VARCHAR(255) NOT NULL,
    station_id   VARCHAR(255) NOT NULL,
    charger_stat VARCHAR(255) NOT NULL,
    charger_type VARCHAR(255) NOT NULL,
    PRIMARY KEY (charger_id, station_id)
);

CREATE TABLE station
(
    station_id   VARCHAR(255) NOT NULL,
    address      VARCHAR(255),
    call_number  VARCHAR(255),
    latitude     DOUBLE       NOT NULL,
    location     VARCHAR(255),
    longitude    DOUBLE       NOT NULL,
    station_name VARCHAR(255),
    use_time     VARCHAR(255) NOT NULL,
    PRIMARY KEY (station_id)
);
