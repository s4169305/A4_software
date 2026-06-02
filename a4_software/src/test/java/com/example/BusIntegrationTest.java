package com.example;

import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import com.example.testData.BusDataLoader;
import static org.junit.jupiter.api.Assertions.*;

public class BusIntegrationTest {
// checking if buses are valid
    @Test
    void shouldStoreValidBus() throws IOException {  // testing if a bus is added successfully
        Bus bus = BusDataLoader.loadFromFile(Path.of("src/main/java/com/example/testData/BusData.txt")); // adding a new bus from BusDataLoader
        BusRepository repo = new BusRepository();

        repo.add(bus);

        assertEquals(1, repo.count()); // checks if the amount in repo is equal to 1, meaning that the bus has been added
        assertNotNull(repo.retrieve(bus.getBusID())); // bus can be retrieved
        assertEquals(bus.getBusID(), repo.retrieve(bus.getBusID()).getBusID()); // the correct bus is stored
    }

    // rejecting duplicate buses
    @Test
    void shouldRejectDuplicateBusId() throws IOException {
        BusRepository repo = new BusRepository();
        Bus bus1 = BusDataLoader.loadFromFile(Path.of("src/main/java/com/example/testData/BusData.txt")); // adding a new bus from BusDataLoader
        Bus bus2 = new Bus (bus1.getBusID(), 40, 60.0, "Hybrid"); // test bus

        repo.add(bus1);

        assertThrows(IllegalArgumentException.class, () -> repo.add(bus2));
        assertEquals(1, repo.count());
    }

     // rejecting bus IDs are not 8 digits long
    @Test
    void shouldRejectBusIdThatIsNot8Digits() throws IOException {
        BusRepository repo = new BusRepository();
        Bus invalid = BusDataLoader.loadFromFile(Path.of("src/main/java/com/example/testData/InvalidBusIDLengthData.txt"));

        assertThrows(IllegalArgumentException.class, () -> repo.add(invalid));
        assertEquals(0, repo.count());
    }

    // rejecting bus IDs that have non-digits
    @Test
    void shouldRejectBusIdWithNonDigitCharacters() throws IOException {
        BusRepository repo = new BusRepository();
        Bus invalid = BusDataLoader.loadFromFile(Path.of("src/main/java/com/example/testData/InvalidBusIDNonDigitData.txt"));

        assertThrows(IllegalArgumentException.class, () -> repo.add(invalid));
        assertEquals(0, repo.count());
    }

    // checks that updates to the Bus details are persisted correctly
    @Test
    void shouldPersistBusUpdates() throws IOException {
        Bus bus = BusDataLoader.loadFromFile(Path.of("src/main/java/com/example/testData/BusData.txt"));
        BusRepository repo = new BusRepository();

        repo.add(bus);
        // updating bus details with different values
        Bus updatedBus = new Bus(bus.getBusID(), 45, 78.0, "Electric");
        repo.update(updatedBus);

        // retrieving and verifying the updated bus
        Bus retrieved = repo.retrieve(bus.getBusID());
        assertEquals(45, retrieved.getCapacity());
        assertEquals(78.0, retrieved.getFuelLevel());
        assertEquals("Electric", retrieved.getFuelType());
    }

    // checking if record counts are updated correctly
    @Test
    void shouldUpdateRecordCount() throws IOException {
        BusRepository repo = new BusRepository();
        // verifies that there are no records
        assertEquals(0, repo.count());

        // adding one bus
        Bus bus = BusDataLoader.loadFromFile(Path.of("src/main/java/com/example/testData/BusData.txt"));
        repo.add(bus);
        assertEquals(1, repo.count());

        // adding another different bus
        Bus bus2 = new Bus ("37427482", 40, 60.0, "Hybrid");
        repo.add(bus2);
        assertEquals(2, repo.count());

        // checking if a duplicate record affects record count
        Bus busDuplicate = new Bus (bus.getBusID(), 40, 60.0, "Hybrid");
        assertThrows(IllegalArgumentException.class, () -> repo.add(busDuplicate));

        // verifying that count is still 2
        assertEquals(2, repo.count());
    }
}


