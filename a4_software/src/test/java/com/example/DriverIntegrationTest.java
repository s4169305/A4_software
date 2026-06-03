package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DriverIntegrationTest {

    // checks if a valid driver can be stored
    @Test
    void shouldStoreValidDriver() {

        // create a valid driver
        Driver driver = new Driver("23@@TMR!AB", "RMIT Driver", 6, "Heavy",
                "22|Swanstone Street|Melbourne|VIC|Australia", "28-03-1999");
        DriverRepository repo = new DriverRepository();
        // add driver to repository
        repo.add(driver);

        // verify driver was added successfully
        assertEquals(1, repo.count());
        assertNotNull(repo.retrieve(driver.getDriverID()));
        assertEquals(driver.getDriverID(),
                repo.retrieve(driver.getDriverID()).getDriverID());
    }

    // checks if duplicate driver IDs are rejected
    @Test
    void shouldRejectDuplicateDriverId() {

        DriverRepository repo = new DriverRepository();
        // create first driver
        Driver driver1 = new Driver("23@@TMR!AB", "RMIT Driver", 6, "Heavy",
                "22|Swanstone Street|Melbourne|VIC|Australia", "28-03-1999");
        // create duplicate driver with same ID
        Driver duplicateDriver = new Driver("23@@TMR!AB", "Software Smith", 7, "medium",
                "18|Swanstone Street|Melbourne|VIC|Australia", "28-03-1988");

        repo.add(driver1);

        // verify duplicate ID is rejected
        assertThrows(IllegalArgumentException.class, () -> repo.add(duplicateDriver));

        // verify only one driver exists
        assertEquals(1, repo.count());
    }

    // checks if driver updates are saved correctly
    @Test
    void shouldPersistDriverUpdates() {

        Driver driver = new Driver("23@@TMR!AB", "RMIT Driver", 6, "Heavy",
                "22|Swanstone Street|Melbourne|VIC|Australia", "28-03-1999");
        DriverRepository repo = new DriverRepository();
        repo.add(driver);

        // update driver information
        Driver updatedDriver = new Driver("23@@TMR!AB", "RMIT Driver", 8, "Heavy",
                "45|King Street|Melbourne|VIC|Australia", "28-03-1999");

        repo.update(updatedDriver);

        // retrieve updated driver
        Driver retrieved = repo.retrieve("23@@TMR!AB");

        // verify updated values
        assertEquals(8, retrieved.getExperienceYears());
        assertEquals("45|King Street|Melbourne|VIC|Australia",
                retrieved.getAddress());
    }

    // checks if record count updates correctly
    @Test
    void shouldUpdateRecordCount() {
        DriverRepository repo = new DriverRepository();

        // verify repository starts empty
        assertEquals(0, repo.count());

        // add first driver
        Driver driver1 = new Driver("23@@TMR!AB", "RMIT Driver", 6, "Heavy",
                "22|Swanstone Street|Melbourne|VIC|Australia", "28-03-1999");
        repo.add(driver1);

        // verify count is 1
        assertEquals(1, repo.count());

        // add second driver
        Driver driver2 = new Driver("24##CAN!CD", "Canvas Driver", 4, "Light",
                "29|Collins Street|Melbourne|VIC|Australia", "21-10-2000");
        repo.add(driver2);

        // verify count is 2
        assertEquals(2, repo.count());
    }
}
