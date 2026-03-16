/**
 * ================================================================
 * MAIN CLASS – UseCase12DataPersistenceRecovery
 * ================================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * Description:
 * This class demonstrates how the system saves
 * and restores critical application state across
 * restarts using file-based persistence.
 *
 * The system saves inventory and booking history
 * to disk and reloads it on the next run.
 *
 * @version 12.0
 */
public class UseCase12DataPersistenceRecovery {

    private static final String PERSISTENCE_FILE = "hotel_state.ser";

    public static void main(String[] args) {
        System.out.println("Data Persistence & System Recovery\n");

        PersistenceService persistence = new PersistenceService(PERSISTENCE_FILE);

        // Attempt to restore previous state
        SystemState state = persistence.load();

        BookingHistory bookingHistory;
        RoomInventory inventory;

        if (state != null) {
            bookingHistory = state.getBookingHistory();
            inventory = state.getInventory();
        } else {
            bookingHistory = new BookingHistory();
            inventory = new RoomInventory();
        }

        // Show current recovered state
        System.out.println("Current inventory:");
        System.out.println(" SingleRoom: " + inventory.getRoomAvailability().get("SingleRoom"));
        System.out.println(" DoubleRoom: " + inventory.getRoomAvailability().get("DoubleRoom"));
        System.out.println(" SuiteRoom: " + inventory.getRoomAvailability().get("SuiteRoom"));
        System.out.println("Confirmed bookings (history): " + bookingHistory.getConfirmedReservations().size());

        // Simulate new booking activity
        RoomAllocationService allocationService = new RoomAllocationService();
        Reservation newBooking = new Reservation("RecoveryGuest", "SingleRoom");

        allocationService.allocateRoom(newBooking, inventory);
        bookingHistory.addReservation(newBooking);

        System.out.println("\nUpdated booking history count: " + bookingHistory.getConfirmedReservations().size());

        // Persist updated state before exiting
        persistence.save(new SystemState(bookingHistory, inventory));

        System.out.println("\nNext run will restore this state from disk.");
    }
}
