package basicbike.dao;

import basicbike.model.BikeItem;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Date;

public class BikeItemDaoImpl extends BaseDaoImpl<BikeItem, Long> implements BikeItemDao {
    protected BikeItemDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, BikeItem.class);
    }

    @Override
    public void rent(BikeItem bikeItem, String renterId, Date rentStartTime) throws RuntimeException {
        bikeItem.rent(renterId, rentStartTime);
        try {
            this.update(bikeItem);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
