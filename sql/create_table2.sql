-- schema generation for H2 database engine
-- by default, H2 converts field and table names to uppercase

CREATE TABLE IF NOT EXISTS bike (
    id          INTEGER NOT NULL AUTO_INCREMENT,
    model       VARCHAR(50) NOT NULL,
    type        VARCHAR(50) NOT NULL,
    size        DOUBLE NOT NULL,
    ratePerHour INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bike_item (
    id          INTEGER NOT NULL AUTO_INCREMENT,
    bike_id     INTEGER NOT NULL,
    bikeItemId  VARCHAR(50) NOT NULL,
    renterId    VARCHAR(50),
    rentStartTime VARCHAR(50),
    PRIMARY KEY (id),
    FOREIGN KEY (bike_id) REFERENCES bike (id)
);
