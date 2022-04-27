package basicbike.domain;

/**
 * Rental exception occurs when the process does not follow business rule
 * such as renting an already rented bicycle, or renting with blank ID.
 */
public class RentalException extends Exception {
    public RentalException(String message) {
        super(message);
    }
}
