package basicbike.dao;

import basicbike.model.BikeItem;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class BikeItemDaoImpl extends BaseDaoImpl<BikeItem, Long> implements BikeItemDao {
    protected BikeItemDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, BikeItem.class);
    }

}
