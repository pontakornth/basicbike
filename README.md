# Basic Bike

Mock application for bike renting business. The purpose is for demonstrating usage of JPA and Ormlite.

## What can it do?
1. User can search bike of their choice.
2. User can rent the bike by providing either Thai national ID or Passport number. In the actual application, a photo would be used instead.
3. User can view amount of money they have to pay after renting a bike. In an actual application, it would contact some payment service instead.


## Setup
1. Create an SQLite database in `database.db` file.
2. Use following commands to import data
   ```bash
   sqlite3 database.db < ./sql/create_table.sql
   ```
3. After that, import sample data from CSV file.
   ```
   sqlite3 database.db
   
   .mode csv
   .import data/bike.csv bike
   .import data/bike_item.csv bike_item
   
   UPDATE bike_item SET renterId = NULL, rentStartTime = NULL;
   ```
   CSV import empty column and make it empty string instead of NULL.
4. Next, create `config.properties` in `./src/main/resources/` folder. 
   After that, put location of database file in the config file.
   ```properties
   databaseFile=database.db
   ```
5. If you are using Intellij, run `basicbike.Main` in the editor would be the best option.
   The alternative way is to use `./gradlew run` if you are using other editor.

## Progress
- [x] Model and DAOs
- [x] Application GUI
- [x] Application logic

## API documentation

Use [api_doc.md](api_doc.md) to view API documentation in markdown format.

The YAML format is not complete.