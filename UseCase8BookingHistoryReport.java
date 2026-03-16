/**
 * ================================================================
 * MAIN CLASS - UseCase8BookingHistoryReport
 * ================================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * Description:
 * This class demonstrates how confirmed bookings are stored
 * in a history record and later reported.
 *
 * The system maintains an ordered
 * audit trail of reservations.
 *
 * @version 8.0
 */
public class UseCase8BookingHistoryReport {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Booking History & Reporting\n");

        // Initialize inventory, booking flow, and reporting
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();

        // History & reporting
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "SingleRoom");
        Reservation r2 = new Reservation("Subha", "DoubleRoom");
        Reservation r3 = new Reservation("Vanmathai", "SuiteRoom");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process booking requests and store confirmed reservations
        System.out.println("Processing booking requests:\n");
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();

            allocationService.allocateRoom(request, inventory);

            // Record confirmed reservation in history
            history.addReservation(request);
        }

        // Generate report for admin/operations
        System.out.println();
        reportService.generateReport(history);

        // Inventory state is still available for operational verification
        System.out.println("\n--- Final Inventory ---");
        System.out.println("SingleRoom: " + inventory.getRoomAvailability().get("SingleRoom"));
        System.out.println("DoubleRoom: " + inventory.getRoomAvailability().get("DoubleRoom"));
        System.out.println("SuiteRoom: " + inventory.getRoomAvailability().get("SuiteRoom"));
    }
}
