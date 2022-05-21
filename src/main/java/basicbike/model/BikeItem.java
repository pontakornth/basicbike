package basicbike.model;

import basicbike.dao.BikeItemDaoImpl;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	// Used to explicitly convert between string and date-time
	private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
	private static final DateFormat dateFormatter;
	
	static {
		dateFormatter = new SimpleDateFormat(DATE_FORMAT);
	}

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
     * 
     * TODO Do you really want cascade=ALL?  That means if you delete a BikeItem
     * then the Bike is deleted, too.
     * @ManyToOne(optional=false, fetch=FetchType.EAGER)
     * probably has the same meaning as ORMLite's annotation:
     * @DatabaseField(foreign=true, foreignAutoRefresh=true)
     */
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch=FetchType.EAGER)
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
    //@DatabaseField(dataType = DataType.DATE_STRING, canBeNull=true)
    @DatabaseField(dataType=DataType.STRING, canBeNull=true)
    private String rentStartTime;

    // Required by ORMLite
    public BikeItem() {

    }

    public BikeItem(Bike bike, String bikeItemId, String renterId, Date rentStartTime) {
        this.bike = bike;
        this.bikeItemId = bikeItemId;
        this.renterId = renterId;
        setRentStartTime(rentStartTime); // handle conversion to string
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

    //TODO Replace java.util.Date with java.time.LocalDateTime or java.sql.TimeStamp
    public Date getRentStartTime() {
    	if (rentStartTime != null) try {
    		// parse the string
    		return dateFormatter.parse(rentStartTime);
    	}
    	catch(ParseException ex) {
    		System.out.printf("Unparsable date string %s for rental id %d\n", 
    				rentStartTime, id);
    	}
        return null;
    }

    public void setRenterId(String renterId) {
        this.renterId = renterId;
    }

    /**
     * Set the rental start time. The parameter is Date but internal format
     * may be something else
     * @param rentStartTime a java.util.Date containing the starting date-time
     */
    public void setRentStartTime(Date rentStartTime) {
    	if (rentStartTime != null) {
    		this.rentStartTime = dateFormatter.format(rentStartTime);
    	}
    	else this.rentStartTime = null;
    }
}
