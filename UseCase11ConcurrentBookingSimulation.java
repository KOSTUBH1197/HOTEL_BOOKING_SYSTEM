/**
 * ================================================================
 * MAIN CLASS - UseCase11ConcurrentBookingSimulation
 * ================================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * Description:
 * This class simulates multiple users
 * attempting to book rooms at the same time.
 *
 * It highlights race conditions and
 * demonstrates how synchronization prevents
 * inconsistent allocations.
 *
 * @version 11.0
 */
public class UseCase11ConcurrentBookingSimulation {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Concurrent Booking Simulation\n");

        // Shared resources
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Create sample booking requests (simulating multiple guests)
        bookingQueue.addRequest(new Reservation("GuestA", "SingleRoom"));
        bookingQueue.addRequest(new Reservation("GuestB", "SingleRoom"));
        bookingQueue.addRequest(new Reservation("GuestC", "DoubleRoom"));
        bookingQueue.addRequest(new Reservation("GuestD", "SuiteRoom"));
        bookingQueue.addRequest(new Reservation("GuestE", "SingleRoom"));
        bookingQueue.addRequest(new Reservation("GuestF", "DoubleRoom"));

        // Create booking processor tasks
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        // Start concurrent processing
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        System.out.println("\n--- Final Inventory ---");
        System.out.println("SingleRoom: " + inventory.getRoomAvailability().get("SingleRoom"));
        System.out.println("DoubleRoom: " + inventory.getRoomAvailability().get("DoubleRoom"));
        System.out.println("SuiteRoom: " + inventory.getRoomAvailability().get("SuiteRoom"));
    }
}
