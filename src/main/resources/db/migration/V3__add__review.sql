CREATE TABLE review
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    content    VARCHAR(255) NOT NULL,
    star       DOUBLE       NOT NULL,
    station_id VARCHAR(255),
    PRIMARY KEY (id)
);