/**
 * ================================================================
 * CLASS - PersistenceService
 * ================================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * Description:
 * This class is responsible for saving and
 * loading the application state to/from disk.
 *
 * It is intentionally simple and uses
 * Java serialization to persist state.
 *
 * @version 12.0
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersistenceService {

    private final File file;

    public PersistenceService(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Saves the provided system state to disk.
     */
    public void save(SystemState state) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(state);
            System.out.println("State saved to: " + file.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Failed to save state: " + e.getMessage());
        }
    }

    /**
     * Loads system state from disk.
     *
     * @return loaded state, or null if no valid state exists
     */
    public SystemState load() {
        if (!file.exists()) {
            System.out.println("No persistence file found. Starting with a fresh state.");
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object obj = ois.readObject();
            if (obj instanceof SystemState) {
                System.out.println("State loaded from: " + file.getAbsolutePath());
                return (SystemState) obj;
            } else {
                System.out.println("Unexpected content in persistence file. Starting fresh.");
                return null;
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load state (file may be missing or corrupted): " + e.getMessage());
            return null;
        }
    }
}
