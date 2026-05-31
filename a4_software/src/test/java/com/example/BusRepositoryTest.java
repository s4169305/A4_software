package com.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BusRepositoryTest {
    @Test
    void shouldStoreValidBus() {
        BusRepository repo = new BusRepository();
        Bus bus = new Bus("B1", 50, 75.0, "Diesel");

        repo.add(bus);

        assertEquals(1, repo.count());
        assertNotNull(repo.findById("B1"));

    }
}
