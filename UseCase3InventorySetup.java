import java.util.Map;

/**
 * ================================================================
 * MAIN CLASS – UseCase3InventorySetup
 * ================================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * This class demonstrates how room availability
 * is managed using a centralized inventory.
 *
 * Room objects are used to retrieve pricing
 * and room characteristics.
 *
 * No booking or search logic is introduced here.
 *
 * @version 3.1
 */
public class UseCase3InventorySetup {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hotel Room Inventory Management\n");

        // Create room objects
        SingleRoom singleRoom = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suiteRoom = new SuiteRoom();

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Get availability map
        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Display Single Room details with centralized availability
        System.out.print("Single Room: ");
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + availability.get("SingleRoom"));
        System.out.println();

        // Display Double Room details with centralized availability
        System.out.print("Double Room: ");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + availability.get("DoubleRoom"));
        System.out.println();

        // Display Suite Room details with centralized availability
        System.out.print("Suite Room: ");
        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + availability.get("SuiteRoom"));
        System.out.println();

        // Demonstrate inventory update
        System.out.println("--- Updating Inventory ---");
        inventory.updateAvailability("SingleRoom", 4);
        System.out.println("Single Room availability updated to: " + 
                         availability.get("SingleRoom"));
    }

}
