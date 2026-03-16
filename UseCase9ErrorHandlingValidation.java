/**
 * ================================================================
 * MAIN CLASS – UseCase9ErrorHandlingValidation
 * ================================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * This class demonstrates how user input
 * is validated before booking is processed.
 *
 * The system:
 * - Accepts user input
 * - Validates input centrally
 * - Handles errors gracefully
 *
 * @version 9.0
 */
import java.util.Scanner;

public class UseCase9ErrorHandlingValidation {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Booking Validation\n");

        Scanner scanner = new Scanner(System.in);

        // Initialize required components
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            // Collect booking input from user
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (SingleRoom/DoubleRoom/SuiteRoom): ");
            String roomType = scanner.nextLine();

            // Validate input before creating request
            validator.validate(guestName, roomType, inventory);

            // Create and enqueue booking request
            Reservation reservation = new Reservation(guestName, roomType);
            bookingQueue.addRequest(reservation);

            // Process booking requests
            RoomAllocationService allocationService = new RoomAllocationService();
            while (bookingQueue.hasPendingRequests()) {
                Reservation request = bookingQueue.getNextRequest();
                allocationService.allocateRoom(request, inventory);
            }

        } catch (InvalidBookingException e) {
            // Handle domain-specific validation errors
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
