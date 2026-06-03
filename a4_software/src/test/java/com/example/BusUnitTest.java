package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class BusUnitTest {

    // B1: checking valid bus ID
    @Test
    void shouldAcceptValidBusId() {
        BusRepository repo = new BusRepository();
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");

        repo.add(bus);

        assertEquals(1, repo.count());
        assertNotNull(repo.retrieve("12345678"));
    }

    // B1: rejecting bus ID shorter than 8 digits
    @Test
    void shouldRejectShortBusId() {
        BusRepository repo = new BusRepository();
        Bus bus = new Bus("1234567", 40, 80.0, "Diesel");

        assertThrows(IllegalArgumentException.class, () -> repo.add(bus));
    }

    // B1: rejecting duplicate bus ID
    @Test
    void shouldRejectDuplicateBusId() {
        BusRepository repo = new BusRepository();
        Bus bus1 = new Bus("12345678", 40, 80.0, "Diesel");
        Bus bus2 = new Bus("12345678", 45, 70.0, "Hybrid");

        repo.add(bus1);

        assertThrows(IllegalArgumentException.class, () -> repo.add(bus2));
    }

    // B2: checking capacity can decrease
    @Test
    void shouldAllowCapacityToDecrease() {
        BusRepository repo = new BusRepository();
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");
        repo.add(bus);

        Bus updatedBus = new Bus("12345678", 45, 80.0, "Diesel");

        assertTrue(repo.update(updatedBus));
        assertEquals(45, repo.retrieve("12345678").getCapacity());
    }

    // B2: rejecting capacity increase
    @Test
    void shouldRejectCapacityIncrease() {
        BusRepository repo = new BusRepository();
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        repo.add(bus);

        Bus updatedBus = new Bus("12345678", 50, 80.0, "Diesel");

        assertThrows(IllegalArgumentException.class, () -> repo.update(updatedBus));
    }

    // B2: checking capacity can stay the same
    @Test
    void shouldAllowSameCapacity() {
        BusRepository repo = new BusRepository();
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        repo.add(bus);

        Bus updatedBus = new Bus("12345678", 40, 75.0, "Diesel");

        assertTrue(repo.update(updatedBus));
        assertEquals(40, repo.retrieve("12345678").getCapacity());
    }

    // B3: driver under 50 can operate bus with capacity 50
    @Test
    void shouldAllowDriverUnder50ForCapacity50Bus() {
        BusRepository repo = new BusRepository();
        Driver driver = new Driver("23@@1234AB", "John", 7, "Heavy",
                "1|Main Street|Melbourne|VIC|Australia", "01-01-1980");
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");

        assertTrue(repo.canDriverOperateBus(driver, bus, 45));
    }

    // B3: driver over 50 cannot operate bus with capacity 50 or more
    @Test
    void shouldRejectDriverOver50ForCapacity50Bus() {
        BusRepository repo = new BusRepository();
        Driver driver = new Driver("23@@1234AB", "John", 20, "Heavy",
                "1|Main Street|Melbourne|VIC|Australia", "01-01-1965");
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");

        assertFalse(repo.canDriverOperateBus(driver, bus, 55));
    }

    // B3: driver over 50 can operate bus with capacity less than 50
    @Test
    void shouldAllowDriverOver50ForCapacityLessThan50() {
        BusRepository repo = new BusRepository();
        Driver driver = new Driver("23@@1234AB", "John", 20, "Heavy",
                "1|Main Street|Melbourne|VIC|Australia", "01-01-1965");
        Bus bus = new Bus("12345678", 49, 80.0, "Diesel");

        assertTrue(repo.canDriverOperateBus(driver, bus, 55));
    }

    // B4: driver with 5 years experience can operate electric bus
    @Test
    void shouldAllowDriverWithFiveYearsExperienceForElectricBus() {
        BusRepository repo = new BusRepository();
        Driver driver = new Driver("23@@1234AB", "John", 5, "Heavy",
                "1|Main Street|Melbourne|VIC|Australia", "01-01-1985");
        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");

        assertTrue(repo.canDriverOperateBus(driver, bus, 40));
    }

    // B4: driver with less than 5 years experience cannot operate electric bus
    @Test
    void shouldRejectDriverWithLessThanFiveYearsExperienceForElectricBus() {
        BusRepository repo = new BusRepository();
        Driver driver = new Driver("23@@1234AB", "John", 3, "Heavy",
                "1|Main Street|Melbourne|VIC|Australia", "01-01-1995");
        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");

        assertFalse(repo.canDriverOperateBus(driver, bus, 30));
    }

    // B4: experience restriction does not apply to diesel bus
    @Test
    void shouldAllowInexperiencedDriverForDieselBus() {
        BusRepository repo = new BusRepository();
        Driver driver = new Driver("23@@1234AB", "John", 0, "Light",
                "1|Main Street|Melbourne|VIC|Australia", "01-01-2000");
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");

        assertTrue(repo.canDriverOperateBus(driver, bus, 25));
    }

    // B5: Heavy licence driver can operate electric bus
    @Test
    void shouldAllowHeavyLicenceForElectricBus() {
        BusRepository repo = new BusRepository();
        Driver driver = new Driver("23@@1234AB", "John", 6, "Heavy",
                "1|Main Street|Melbourne|VIC|Australia", "01-01-1985");
        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");

        assertTrue(repo.canDriverOperateBus(driver, bus, 40));
    }

    // B5: PublicTransport licence driver can operate hybrid bus
    @Test
    void shouldAllowPublicTransportLicenceForHybridBus() {
        BusRepository repo = new BusRepository();
        Driver driver = new Driver("23@@1234AB", "John", 6, "PublicTransport",
                "1|Main Street|Melbourne|VIC|Australia", "01-01-1985");
        Bus bus = new Bus("12345678", 40, 80.0, "Hybrid");

        assertTrue(repo.canDriverOperateBus(driver, bus, 40));
    }

    // B5: Medium licence driver cannot operate hybrid bus
    @Test
    void shouldRejectMediumLicenceForHybridBus() {
        BusRepository repo = new BusRepository();
        Driver driver = new Driver("23@@1234AB", "John", 6, "Medium",
                "1|Main Street|Melbourne|VIC|Australia", "01-01-1985");
        Bus bus = new Bus("12345678", 40, 80.0, "Hybrid");

        assertFalse(repo.canDriverOperateBus(driver, bus, 40));
    }
}