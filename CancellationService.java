/**
 * ================================================================
 * CLASS - CancellationService
 * ================================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Description:
 * This class is responsible for handling
 * booking cancellations.
 *
 * It ensures that:
 * - Cancelled room IDs are tracked
 * - Inventory is restored correctly
 * - Invalid cancellations are prevented
 *
 * A stack is used to model rollback behavior.
 *
 * @version 10.0
 */
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

public class CancellationService {

    /**
     * Stack that stores recently released room IDs.
     */
    private Stack<String> releasedRoomIds;

    /**
     * Maps reservation ID to room type.
     */
    private Map<String, String> reservationRoomTypeMap;

    /**
     * Maps reservation ID to the allocated room ID.
     */
    private Map<String, String> reservationRoomIdMap;

    /**
     * Initializes cancellation tracking structures.
     */
    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
        reservationRoomIdMap = new HashMap<>();
    }

    /**
     * Registers a confirmed booking.
     *
     * This method simulates storing confirmation
     * data that will later be required for cancellation.
     *
     * @param reservationId confirmed reservation ID
     * @param roomType      allocated room type
     * @param roomId        allocated room ID
     */
    public void registerBooking(String reservationId, String roomType, String roomId) {
        reservationRoomTypeMap.put(reservationId, roomType);
        reservationRoomIdMap.put(reservationId, roomId);
    }

    /**
     * Cancels a confirmed booking and
     * restores inventory safely.
     *
     * @param reservationId reservation to cancel
     * @param inventory     centralized room inventory
     * @return true if cancellation succeeded, false otherwise
     */
    public boolean cancelBooking(String reservationId, RoomInventory inventory) {
        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Cancellation failed: reservation not found (" + reservationId + ")");
            return false;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);
        String roomId = reservationRoomIdMap.get(reservationId);

        // Release the room ID and record rollback history
        releasedRoomIds.push(roomId);

        // Restore inventory count immediately
        int current = inventory.getRoomAvailability().getOrDefault(roomType, 0);
        inventory.updateAvailability(roomType, current + 1);

        // Remove tracking information so it can't be cancelled again
        reservationRoomTypeMap.remove(reservationId);
        reservationRoomIdMap.remove(reservationId);

        System.out.println("Cancellation successful for " + reservationId + " (released " + roomId + ")");
        return true;
    }

    /**
     * Displays recently cancelled reservations.
     *
     * This method helps visualize rollback order.
     */
    public void showRollbackHistory() {
        System.out.println("--- Cancellation Rollback History (most recent first) ---");
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No cancellations have occurred.");
            return;
        }

        for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
            System.out.println("- " + releasedRoomIds.get(i));
        }
    }
}
