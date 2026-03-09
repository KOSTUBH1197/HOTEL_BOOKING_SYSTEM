/**
 * ================================================================
 * MAIN CLASS – UseCase6RoomAllocation
 * ================================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Description:
 * This class demonstrates how booking
 * requests are confirmed and rooms
 * are allocated safely.
 *
 * It consumes booking requests in FIFO
 * order and updates inventory immediately.
 *
 * @version 6.0
 */
public class UseCase6RoomAllocation {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Room Allocation & Reservation Confirmation\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Initialize allocation service
        RoomAllocationService allocationService = new RoomAllocationService();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "SingleRoom");
        Reservation r2 = new Reservation("Subha", "DoubleRoom");
        Reservation r3 = new Reservation("Vanmathai", "SuiteRoom");
        Reservation r4 = new Reservation("Ravi", "SingleRoom");

        // Add requests to the queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);
        bookingQueue.addRequest(r4);

        // Process booking requests in FIFO order
        System.out.println("Processing booking requests:\n");
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();
            allocationService.allocateRoom(request, inventory);
        }

        // Display updated inventory
        System.out.println("\n--- Updated Inventory ---");
        System.out.println("SingleRoom: " + inventory.getRoomAvailability().get("SingleRoom"));
        System.out.println("DoubleRoom: " + inventory.getRoomAvailability().get("DoubleRoom"));
        System.out.println("SuiteRoom: " + inventory.getRoomAvailability().get("SuiteRoom"));
    }

}
