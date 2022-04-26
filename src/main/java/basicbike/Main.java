package basicbike;

import basicbike.dao.*;
import basicbike.gui.MainGui;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("And here we are!");
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db");
            DaoFactory daoFactory = new DaoFactory(connectionSource);
            MainGui mainGui = new MainGui(daoFactory);
            mainGui.start();

        } catch (Exception e) {
            // If something bad happens, the program must close the connnection.
            if (connectionSource != null) {
                connectionSource.close();
            }
        }

    }
}
