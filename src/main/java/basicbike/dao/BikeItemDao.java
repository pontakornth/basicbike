package basicbike.dao;

import basicbike.model.BikeItem;
import com.j256.ormlite.dao.Dao;

import java.util.Date;

public interface BikeItemDao extends Dao<BikeItem, Long> {
    void rent(BikeItem bikeItem, String renterId, Date rentStartTime) throws RuntimeException;
}
