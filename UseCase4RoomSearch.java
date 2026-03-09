/**
 * ================================================================
 * MAIN CLASS – UseCase4RoomSearch
 * ================================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * This class demonstrates how guests
 * can view available rooms without
 * modifying inventory data.
 *
 * The system enforces read-only access
 * by design and usage discipline.
 *
 * @version 4.0
 */
public class UseCase4RoomSearch {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hotel Room Search\n");

        // Create room objects
        SingleRoom singleRoom = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suiteRoom = new SuiteRoom();

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create search service
        RoomSearchService searchService = new RoomSearchService();

        // Perform search - displays only available rooms
        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);
    }

}
