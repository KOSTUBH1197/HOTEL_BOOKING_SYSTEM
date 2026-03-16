/**
 * ================================================================
 * MAIN CLASS - UseCase10BookingCancellation
 * ================================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Description:
 * This class demonstrates how confirmed
 * bookings can be cancelled safely.
 *
 * Inventory is restored and rollback
 * history is maintained.
 *
 * @version 10.0
 */
public class UseCase10BookingCancellation {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Booking Cancellation & Inventory Rollback\n");

        // Initialize core components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Cancellation tracking and history
        CancellationService cancellationService = new CancellationService();
        BookingHistory history = new BookingHistory();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "SingleRoom");
        Reservation r2 = new Reservation("Subha", "DoubleRoom");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);

        // Process bookings and register them for cancellation
        System.out.println("Processing booking requests:\n");
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();

            String allocatedRoomId = allocationService.allocateRoom(request, inventory);

            // Record booking in history and cancellation service if allocation succeeded
            if (allocatedRoomId != null) {
                history.addReservation(request);
                cancellationService.registerBooking(request.getReservationId(), request.getRoomType(), allocatedRoomId);
            }
        }

        // Perform a cancellation
        System.out.println();
        String cancelId = r2.getReservationId();
        if (cancellationService.cancelBooking(cancelId, inventory)) {
            history.removeReservation(cancelId);
        }

        // Show rollback history and final inventory
        System.out.println();
        cancellationService.showRollbackHistory();

        System.out.println("\n--- Inventory After Cancellation ---");
        System.out.println("SingleRoom: " + inventory.getRoomAvailability().get("SingleRoom"));
        System.out.println("DoubleRoom: " + inventory.getRoomAvailability().get("DoubleRoom"));
        System.out.println("SuiteRoom: " + inventory.getRoomAvailability().get("SuiteRoom"));
    }
}
