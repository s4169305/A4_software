package com.example;

import java.util.ArrayList;
import java.util.List;

public class DriverRepository {

    private List<Driver> drivers = new ArrayList<>();

    public void add(Driver driver) {
        // validate driver ID is exactly 8 digits
        String driverID = driver.getDriverID();
        if (driverID == null || driverID.length() != 8 || !driverID.matches("\\d+")) {
            throw new IllegalArgumentException("Driver ID must be exactly 8 digits");
        }
        
        drivers.add(driver);
    }

    public Driver retrieve(String driverID) {
        for (Driver driver : drivers) {
            if (driver.getDriverID().equals(driverID)) {
                return driver;
            }
        }
        return null;
    }

    public int count() {
        return drivers.size();
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void update(Driver driver) {
        drivers.remove(driver);
        drivers.add(driver);
    }
}
