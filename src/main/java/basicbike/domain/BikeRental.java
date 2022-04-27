package basicbike.domain;

import basicbike.dao.BikeDao;
import basicbike.dao.BikeItemDao;
import basicbike.model.Bike;
import basicbike.model.BikeItem;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Date;
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

    /**
     * Rent the bike item on specific date.
     * @param bikeItem BikeItem to rent
     * @param renterId Thai national ID or passport ID of the renter
     * @param rentStartTime Starting time of rent.
     * @throws RentalException When rented already rented bike
     */
    public void rentBikeItem(BikeItem bikeItem, String renterId, Date rentStartTime) throws RentalException, RuntimeException {
        String oldRenterId = bikeItem.getRenterId();
        // Empty id must be checked as the importing may use empty string instead.
        if (oldRenterId != null && !oldRenterId.isBlank()) {
            throw new RentalException("The bike is already rented");
        }
        if (renterId == null || renterId.isBlank()) {
            throw new RentalException("Cannot use blank ID to rent a bike");
        }
        // Both ID types are not verified as it is outside scope of JPA demonstration.

        bikeItem.setRenterId(renterId);
        bikeItem.setRentStartTime(rentStartTime);
        try {
            bikeItemDao.update(bikeItem);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
