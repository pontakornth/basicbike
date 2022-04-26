package basicbike.dao;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class DaoFactory {
    private final ConnectionSource connectionSource;
    private BikeDao bikeDao;
    private BikeItemDao bikeItemDao;

    public DaoFactory(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    public BikeDao getBikeDao() throws RuntimeException {
        if (bikeDao == null) {
            try {
                bikeDao = new BikeDaoImpl(connectionSource);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return bikeDao;
    }

    public BikeItemDao getBikeItemDao() throws RuntimeException {
        if (bikeItemDao == null) {
            try {
                bikeItemDao = new BikeItemDaoImpl(connectionSource);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return bikeItemDao;
    }

    public void closeConnection() {
        try {
            connectionSource.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
