package basicbike.dao;

import basicbike.model.Bike;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class BikeDaoImpl extends BaseDaoImpl<Bike, Long> implements BikeDao {

    protected BikeDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Bike.class);
    }
}
