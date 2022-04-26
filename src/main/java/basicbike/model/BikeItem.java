package basicbike.model;

import javax.persistence.*;
import java.util.Date;

/**
 * The actual rentable bike.
 * <p>
 * For description of a bike
 *
 * @see Bike
 */
@Entity
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
    @Column(nullable = false)
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
    @Column()
    private Date rentStartTime;

    // Required by BikeItem
    public BikeItem() {

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

    public Date getRentStartTime() {
        return rentStartTime;
    }
}
