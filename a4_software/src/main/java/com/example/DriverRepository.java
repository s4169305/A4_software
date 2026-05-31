package com.example;

import java.util.ArrayList;
import java.util.List;
// Importing array and list

public class DriverRepository {

    private List<Driver> drivers = new ArrayList<>();

    public void add(Driver driver) {
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
