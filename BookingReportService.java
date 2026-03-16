/**
 * ================================================================
 * CLASS - BookingReportService
 * ================================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * Description:
 * This class generates reports
 * from booking history data.
 *
 * Reporting logic is separated
 * from data storage.
 *
 * @version 8.0
 */
public class BookingReportService {

    /**
     * Displays a summary report
     * of all confirmed bookings.
     *
     * @param history booking history
     */
    public void generateReport(BookingHistory history) {
        System.out.println("--- Booking History Report ---");
        System.out.println("Total confirmed reservations: " + history.getConfirmedReservations().size());

        if (history.getConfirmedReservations().isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation reservation : history.getConfirmedReservations()) {
            System.out.println("- Guest: " + reservation.getGuestName() + ", Room: " + reservation.getRoomType());
        }
    }
}
