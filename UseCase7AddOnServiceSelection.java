/**
 * ================================================================
 * MAIN CLASS - UseCase7AddOnServiceSelection
 * ================================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * Description:
 * This class demonstrates how optional
 * services can be attached to a confirmed
 * booking.
 *
 * Services are added after room allocation
 * and do not affect inventory.
 *
 * @version 7.0
 */
public class UseCase7AddOnServiceSelection {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Add-On Service Selection\n");

        // Initialize inventory and booking flow
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Initialize add-on service manager
        AddOnServiceManager addOnManager = new AddOnServiceManager();

        // Create booking requests (these represent confirmed reservations after allocation)
        Reservation r1 = new Reservation("Abhi", "SingleRoom");
        Reservation r2 = new Reservation("Subha", "DoubleRoom");

        // Add requests to the queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);

        // Process booking requests
        System.out.println("Processing booking requests:\n");
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();

            // Allocate a room for the reservation
            allocationService.allocateRoom(request, inventory);

            // In this simple demo, use guest name + room type as a stable reservation ID
            String reservationId = request.getGuestName() + "-" + request.getRoomType();

            // Add optional services for the guest
            if (request.getGuestName().equals("Abhi")) {
                addOnManager.addService(reservationId, new AddOnService("Breakfast", 15.0));
                addOnManager.addService(reservationId, new AddOnService("Spa", 30.0));
            } else if (request.getGuestName().equals("Subha")) {
                addOnManager.addService(reservationId, new AddOnService("Airport Pickup", 25.0));
            }

            // Display add-on services and total cost for the reservation
            System.out.println("\nAdd-on services for reservation [" + reservationId + "]: ");
            for (AddOnService service : addOnManager.getServices(reservationId)) {
                System.out.println("- " + service);
            }

            System.out.printf("Total add-on cost: $%.2f\n", addOnManager.calculateTotalServiceCost(reservationId));
            System.out.println();
        }

        // Verify inventory was not affected by add-on services
        System.out.println("\n--- Final Inventory (should reflect only room allocation) ---");
        System.out.println("SingleRoom: " + inventory.getRoomAvailability().get("SingleRoom"));
        System.out.println("DoubleRoom: " + inventory.getRoomAvailability().get("DoubleRoom"));
        System.out.println("SuiteRoom: " + inventory.getRoomAvailability().get("SuiteRoom"));
    }

}
