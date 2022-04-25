package basicbike.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Description of a bike
 */
@Entity
public class Bike {
    /**
     * Unique identifier for a bike description
     */
    @Id
    public long id;

    /**
     * Model of the bike
     */
    @Column(nullable = false)
    public String model;

    /**
     * Type of bike such as mountain bike.
     */
    @Column(nullable = false)
    public String type;

    /**
     * Size of a bike in inch unit
     */
    @Column(nullable = false)
    public double size;

    /**
     * Rate per hour
     *
     * The type is int to simplify the application.
     */
    @Column(nullable = false)
    public int ratePerHour;



}
