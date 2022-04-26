package basicbike.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Description of a bike
 *
 * For the actual physical bike
 * @see BikeItem
 */
@Entity
public class Bike {
    /**
     * Unique identifier for a bike description
     */
    @Id
    private long id;

    /**
     * Model of the bike
     */
    @Column(nullable = false)
    private String model;

    /**
     * Type of bike such as mountain bike.
     */
    @Column(nullable = false)
    private String type;

    /**
     * Size of a bike in inch unit
     */
    @Column(nullable = false)
    private double size;

    /**
     * Rate per hour
     *
     * The type is int to simplify the application.
     */
    @Column(nullable = false)
    private int ratePerHour;

    public Bike(String model, String type, double size, int ratePerHour) {
        this.model = model;
        this.type = type;
        this.size = size;
        this.ratePerHour = ratePerHour;
    }


    public long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public double getSize() {
        return size;
    }

    public int getRatePerHour() {
        return ratePerHour;
    }

    // Required by ORMlite
    public Bike() {

    }
}
