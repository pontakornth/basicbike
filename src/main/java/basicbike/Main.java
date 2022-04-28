package basicbike;

import basicbike.dao.*;
import basicbike.gui.MainGui;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.InputStream;
import java.util.Properties;


public class Main {
    public static void main(String[] args) throws Exception {
        ConnectionSource connectionSource = null;
        try {
            String configFileLocation = "config.properties";
            Properties p = new Properties();
            p.load(Main.class.getClassLoader().getResourceAsStream(configFileLocation));

            String connectionString = String.format("jdbc:sqlite:%s", p.getProperty("databaseFile"));
            connectionSource = new JdbcConnectionSource(connectionString);
            DaoFactory daoFactory = new DaoFactory(connectionSource);
            MainGui mainGui = new MainGui(daoFactory);
            mainGui.start();

        } catch (Exception e) {
            // If something bad happens, the program must close the connection.
            e.printStackTrace();
            if (connectionSource != null) {
                connectionSource.close();
            }
        }

    }
}
