package com.example;

import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import com.example.testData.BusDataLoader;
import static org.junit.jupiter.api.Assertions.*;
// importing IO exception, path, test, BusData, and assertions

public class BusIntegrationTest {
//checking if buses are valid
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
        Bus invalid = BusDataLoader.loadFromFile(Path.of("src/main/java/com/example/testData/InvalidBusData.txt"));

        assertThrows(IllegalArgumentException.class, () -> repo.add(invalid));
        assertEquals(0, repo.count());
    }

    // rejecting bus IDs that have non-digits
    @Test
    void shouldRejectBusIdWithNonDigitCharacters() throws IOException {
        BusRepository repo = new BusRepository();
        Bus invalid = BusDataLoader.loadFromFile(Path.of("src/main/java/com/example/testData/InvalidBusData.txt"));

        assertThrows(IllegalArgumentException.class, () -> repo.add(invalid));
    }





    // rejecting invalid buses test


    // update test



}


