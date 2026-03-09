import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

/**
 * ================================================================
 * CLASS – RoomAllocationService
 * ================================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Description:
 * This class is responsible for confirming
 * booking requests and assigning rooms.
 *
 * It ensures:
 * - Each room ID is unique
 * - Inventory is updated immediately
 * - No room is double-booked
 *
 * @version 6.0
 */
public class RoomAllocationService {

    /**
     * Stores all allocated room IDs to
     * prevent duplicate assignments.
     */
    private Set<String> allocatedRoomIds;

    /**
     * Stores assigned room IDs by room type.
     *
     * Key   -> Room type
     * Value -> Set of assigned room IDs
     */
    private Map<String, Set<String>> assignedRoomsByType;

    /**
     * Initializes allocation tracking structures.
     */
    public RoomAllocationService() {
        this.allocatedRoomIds = new HashSet<>();
        this.assignedRoomsByType = new HashMap<>();
        assignedRoomsByType.put("SingleRoom", new HashSet<>());
        assignedRoomsByType.put("DoubleRoom", new HashSet<>());
        assignedRoomsByType.put("SuiteRoom", new HashSet<>());
    }

    /**
     * Confirms a booking request by assigning
     * a unique room ID and updating inventory.
     *
     * @param reservation booking request
     * @param inventory centralized room inventory
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Check availability
        if (availability.get(roomType) > 0) {
            // Generate unique room ID
            String roomId = generateRoomID(roomType);
            
            // Record allocation
            allocatedRoomIds.add(roomId);
            assignedRoomsByType.get(roomType).add(roomId);
            
            // Update inventory immediately
            int currentCount = availability.get(roomType);
            inventory.updateAvailability(roomType, currentCount - 1);
            
            // Confirm reservation
            System.out.println("Reservation confirmed for " + reservation.getGuestName() + 
                             ". Room: " + roomId + " allocated");
        } else {
            System.out.println("No " + roomType + " available for " + reservation.getGuestName());
        }
    }

    /**
     * Generates a unique room ID
     * for the given room type.
     *
     * @param roomType type of room
     * @return unique room ID
     */
    private String generateRoomID(String roomType) {
        String prefix;
        if (roomType.equals("SingleRoom")) {
            prefix = "S";
        } else if (roomType.equals("DoubleRoom")) {
            prefix = "D";
        } else {
            prefix = "U"; // SuiteRoom
        }
        int count = assignedRoomsByType.get(roomType).size() + 1;
        return prefix + count;
    }

}
