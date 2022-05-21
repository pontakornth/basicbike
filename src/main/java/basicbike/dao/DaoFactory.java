package basicbike.dao;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Properties;

public class DaoFactory {
	private ConnectionSource connectionSource;
	private BikeDao bikeDao;
	private BikeItemDao bikeItemDao;

	public DaoFactory( ) {
		Properties properties = basicbike.Main.getProperties();
		
		String url = properties.getProperty("url");
		String user = properties.getProperty("user"); // may be null
		String password = properties.getProperty("password");
		try {
			connectionSource = new JdbcConnectionSource(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		if (connectionSource != null) try {
			connectionSource.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
