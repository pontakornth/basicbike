package basicbike;

import basicbike.dao.*;
import basicbike.gui.MainGui;
import basicbike.model.Bike;
import basicbike.model.BikeItem;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // TODO: Use DaoFactory instead
        System.out.println("And here we are!");
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db");
            DaoFactory daoFactory = new DaoFactory(connectionSource);
            MainGui mainGui = new MainGui(daoFactory);
            mainGui.start();
//            BikeDao bikeDao = daoFactory.getBikeDao();
//            Bike newBike = new Bike("LA 120", "Mountain", 12.5, 50);
//            bikeDao.createOrUpdate(newBike);
//            for (Bike bike: bikeDao.queryForAll()) {
//                System.out.println(bike.getModel());
//            }
//            BikeItem newBikeItem = new BikeItem(newBike, "A2090");
//            BikeItemDao bikeItemDao = daoFactory.getBikeItemDao();
//            bikeItemDao.createOrUpdate(newBikeItem);
//            bikeItemDao.rent(newBikeItem, "1234", new Date());
//            System.out.println("The bike is already rented");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
