/**
 * ================================================================
 * CLASS - BookingHistory
 * ================================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * Description:
 * This class maintains a record of confirmed reservations.
 *
 * It provides ordered storage for
 * historical and reporting purposes.
 *
 * @version 8.0
 */
import java.util.List;
import java.util.ArrayList;

import java.io.Serializable;

public class BookingHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * List that stores confirmed reservations.
     */
    private List<Reservation> confirmedReservations;

    /**
     * Initializes an empty booking history.
     */
    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    /**
     * Adds a confirmed reservation
     * to booking history.
     *
     * @param reservation confirmed booking
     */
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    /**
     * Returns all confirmed reservations.
     *
     * @return list of reservations
     */
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }

    /**
     * Removes a reservation from history.
     *
     * @param reservationId reservation identifier
     * @return true if removed, false if not found
     */
    public boolean removeReservation(String reservationId) {
        return confirmedReservations.removeIf(r -> r.getReservationId().equals(reservationId));
    }
}
