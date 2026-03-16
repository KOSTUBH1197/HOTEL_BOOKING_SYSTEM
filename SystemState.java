/**
 * ================================================================
 * CLASS - SystemState
 * ================================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * Description:
 * This class acts as a container for the
 * application state that needs to be persisted.
 *
 * It is designed for serialization and
 * deserialization to/from disk.
 *
 * @version 12.0
 */
import java.io.Serializable;

public class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    private BookingHistory bookingHistory;
    private RoomInventory inventory;

    public SystemState(BookingHistory bookingHistory, RoomInventory inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }

    public BookingHistory getBookingHistory() {
        return bookingHistory;
    }

    public RoomInventory getInventory() {
        return inventory;
    }
}
