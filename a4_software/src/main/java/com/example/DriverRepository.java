package com.example;

import java.util.ArrayList;
import java.util.List;

public class DriverRepository {

    // stores all drivers
    private List<Driver> drivers = new ArrayList<>();

    // adds a new driver
    public void add(Driver driver) {
        String driverID = driver.getDriverID();

        // checks if the driver ID is valid
        if (!isValidDriverID(driverID)) {
            throw new IllegalArgumentException("Driver ID is invalid");
        }
        // checks for duplicate driver IDs
        if (retrieve(driverID) != null) {
            throw new IllegalArgumentException("Duplicate driver ID is not allowed");
        }
        drivers.add(driver);
    }

    // retrieves a driver using their ID
    public Driver retrieve(String driverID) {
        for (Driver driver : drivers) {
            if (driver.getDriverID().equals(driverID)) {
                return driver;
            }
        }
        return null;
    }

    // updates an existing driver's details
    public void update(Driver updatedDriver) {
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getDriverID().equals(updatedDriver.getDriverID())) {
                drivers.set(i, updatedDriver);
                return;
            }
        }
    }

    // returns the number of stored drivers
    public int count() {
        return drivers.size();
    }

    // returns all drivers
    public List<Driver> getDrivers() {
        return drivers;
    }

    // validates the driver ID format
    private boolean isValidDriverID(String driverID) {

        // checks if ID is exactly 10 characters
        if (driverID == null || driverID.length() != 10) {
            return false;
        }
        // checks first two characters are digits
        if (!Character.isDigit(driverID.charAt(0)) || !Character.isDigit(driverID.charAt(1))) {
            return false;
        }
        // checks first two digits are between 2 and 9
        if (driverID.charAt(0) < '2' || driverID.charAt(0) > '9') {
            return false;
        }
        if (driverID.charAt(1) < '2' || driverID.charAt(1) > '9') {
            return false;
        }

        int specialCount = 0;
        // counts special characters between positions 3 and 8
        for (int i = 2; i <= 7; i++) {
            char c = driverID.charAt(i);

            if (!Character.isLetterOrDigit(c)) {
                specialCount++;
            }
        }
        // must contain at least 2 special characters
        if (specialCount < 2) {
            return false;
        }
        // checks last two characters are uppercase letters
        if (!Character.isUpperCase(driverID.charAt(8)) ||
            !Character.isUpperCase(driverID.charAt(9))) {
            return false;
        }
        return true;
    }
}
