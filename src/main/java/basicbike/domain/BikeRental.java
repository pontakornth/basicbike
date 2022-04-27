package basicbike.domain;

import basicbike.dao.BikeDao;
import basicbike.dao.BikeItemDao;
import basicbike.model.Bike;
import basicbike.model.BikeItem;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Handle logic of bike rental application
 *
 * For GUI, please see
 * @see basicbike.gui.MainGui
 */
public class BikeRental {
    private final BikeDao bikeDao;
    private final BikeItemDao bikeItemDao;

    public BikeRental(BikeDao bikeDao, BikeItemDao bikeItemDao) {
        this.bikeDao = bikeDao;
        this.bikeItemDao = bikeItemDao;
    }

    /**
     * Get all bike items
     *
     * Note: This is inefficient but only for demonstration.
     * @return List of all bike items
     * @throws RuntimeException Query error
     */
    public List<BikeItem> getAllBikeItems() throws RuntimeException {
        try {
            List<BikeItem> bikeItems = bikeItemDao.queryForAll();
            for (BikeItem bikeItem: bikeItems) {
                // Foreign object is not refreshed.
                // It needs to be refreshed manually.
                // In real SQL, bike info can be queried with JOIN.
                // It seems ORMlite does not have such functionality.
                bikeDao.refresh(bikeItem.getBike());
            }
            return bikeItems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
