package basicbike;

import basicbike.dao.*;
import basicbike.gui.MainGui;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // TODO: Use DaoFactory instead
        System.out.println("And here we are!");
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db");
            DaoFactory daoFactory = new DaoFactory(connectionSource);
            MainGui mainGui = new MainGui(daoFactory);
            mainGui.start();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
