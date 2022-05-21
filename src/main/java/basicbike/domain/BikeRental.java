package basicbike.domain;

import basicbike.dao.BikeDao;
import basicbike.dao.BikeItemDao;
import basicbike.dao.DaoFactory;
import basicbike.model.Bike;
import basicbike.model.BikeItem;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Handle logic of bike rental application
 *
 * For GUI, please see
 * 
 * @see basicbike.gui.MainGui
 */
public class BikeRental {
	private final BikeDao bikeDao;
	private final BikeItemDao bikeItemDao;

	public BikeRental(DaoFactory daoFactory) {
		this.bikeDao = daoFactory.getBikeDao();
		this.bikeItemDao = daoFactory.getBikeItemDao();
	}

	/**
	 * Get all bike items
	 *
	 * Note: This is inefficient but only for demonstration.
	 * 
	 * @return List of all bike items
	 * @throws RuntimeException Query error
	 */
	public List<BikeItem> getAllBikeItems() throws RuntimeException {
		try {
			List<BikeItem> bikeItems = bikeItemDao.queryForAll();

			// In BikeItem, set the option `fetch=FetchType.EAGAR`
			// so that you do not need to do this explicitly.
			for (BikeItem bikeItem : bikeItems) {
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
	 * Get list of bike items matched with model name
	 * 
	 * @param modelName Model name of the bike
	 * @return BikeItems with matched model name
	 */
	public List<BikeItem> getBikeItemsByModelName(String modelName) {
		try {
			QueryBuilder<Bike, Long> bikeQueryBuilder = bikeDao.queryBuilder();
			SelectArg selectArg = new SelectArg();
			selectArg.setValue("%" + modelName + "%");
			bikeQueryBuilder.where().like("model", selectArg);
			QueryBuilder<BikeItem, Long> bikeItemQueryBuilder = bikeItemDao.queryBuilder();
			bikeItemQueryBuilder.join("bike_id", "id", bikeQueryBuilder);
			bikeItemQueryBuilder.prepare();
			List<BikeItem> bikeItems = bikeItemQueryBuilder.query();
			for (BikeItem bikeItem : bikeItems) {
				bikeDao.refresh(bikeItem.getBike());
			}
			return bikeItems;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Rent the bike item on specific date.
	 * 
	 * @param bikeItem      BikeItem to rent
	 * @param renterId      Thai national ID or passport ID of the renter
	 * @param rentStartTime Starting time of rent.
	 * @throws RentalException When rented already rented bike
	 */
	public void rentBikeItem(BikeItem bikeItem, String renterId, Date rentStartTime)
			throws RentalException, RuntimeException {
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

	/**
	 * Return the bike item and calculate fee based on rate per hour.
	 * 
	 * @param bikeItem    BikeItem to return
	 * @param rentEndTime Time at the end
	 * @return fee based on rate per hour
	 */
	public int returnBikeItem(BikeItem bikeItem, Date rentEndTime) throws RentalException, RuntimeException {
		Date startTime = bikeItem.getRentStartTime();
		if (startTime == null)
			throw new RentalException("Cannot return available bike");
		long timeDifference = rentEndTime.getTime() - startTime.getTime();
		if (timeDifference < 0)
			throw new RentalException("Cannot return bike before rental");
		bikeItem.setRenterId(null);
		bikeItem.setRentStartTime(null);
		try {
			bikeItemDao.update(bikeItem);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		int hourDifference = (int) Math.ceil((timeDifference / 1000d) / 3600d);
		return hourDifference * bikeItem.getBike().getRatePerHour();
	}

}
