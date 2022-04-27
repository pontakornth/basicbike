package basicbike.model;

import basicbike.dao.BikeItemDaoImpl;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.*;
import java.util.Date;

/**
 * The actual rentable bike.
 * <p>
 * For description of a bike
 *
 * @see Bike
 */
@DatabaseTable(daoClass = BikeItemDaoImpl.class,tableName = "bike_item")
public class BikeItem {

    /**
     * ID of BikeItem in the database.
     * <p>
     * It is used as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Reference to specification of the bike.
     */
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "bike_id")
    private Bike bike;

    /**
     * Bike ID used by application to identify bike.
     */
    @Column(nullable = false)
    private String bikeItemId;

    /**
     * Renter ID when the bike is rented.
     * <p>
     * It can be Thai national ID or Passport number.
     * Verification is not implemented in this application because it's
     * outside the scope.
     * <p>
     * If it is null, the bike is available.
     */
    @Column()
    private String renterId;

    /**
     * Starting time of renting.
     * <p>
     * It is meaningful when the renterId is not null.
     */
    @DatabaseField(dataType = DataType.DATE_STRING)
    private Date rentStartTime;

    // Required by BikeItem
    public BikeItem() {

    }

    public BikeItem(Bike bike, String bikeItemId, String renterId, Date rentStartTime) {
        this.bike = bike;
        this.bikeItemId = bikeItemId;
        this.renterId = renterId;
        this.rentStartTime = rentStartTime;
    }

    public BikeItem(Bike bike, String bikeItemId) {
        this(bike, bikeItemId, null ,null);
    }

    public long getId() {
        return id;
    }

    public Bike getBike() {
        return bike;
    }

    public String getBikeItemId() {
        return bikeItemId;
    }

    public String getRenterId() {
        return renterId;
    }

    public boolean isRented() {
        return renterId != null && !renterId.isBlank();
    }

    public Date getRentStartTime() {
        return rentStartTime;
    }

    public void setRenterId(String renterId) {
        this.renterId = renterId;
    }

    public void setRentStartTime(Date rentStartTime) {
        this.rentStartTime = rentStartTime;
    }
}
