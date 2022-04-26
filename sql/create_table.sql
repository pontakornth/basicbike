-- noinspection SqlNoDataSourceInspectionForFile

BEGIN TRANSACTION;
CREATE TABLE bike (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    model VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    size DOUBLE NOT NULL,
    ratePerHour INT NOT NULL
);

CREATE TABLE bike_item (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    bike_id INTEGER NOT NULL,
    bikeItemId VARCHAR(50) NOT NULL,
    renterId VARCHAR(50),
    -- SQLite does not have official date datatype.
    rentStartTime VARCHAR(50),
    FOREIGN KEY (bike_id) REFERENCES bike (id)
);
END TRANSACTION;