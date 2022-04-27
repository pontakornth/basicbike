package basicbike.domain;

/**
 * Rental exception occurs when the process does not follow business rule
 * such as renting an already rented bicycle.
 */
public class RentalException extends Exception {
    public RentalException(String message) {
        super(message);
    }
}
