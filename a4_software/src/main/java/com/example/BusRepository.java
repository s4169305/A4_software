package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
// Importing array, list and objects

public class BusRepository {
    // Making a list for the buses
    private List<Bus> buses = new ArrayList<>();

    // Add ()
    public void add(Bus bus) {
        buses.add(bus);
    }


    // Update ()
    public boolean update(Bus bus) {
        for (int i = 0; i < buses.size(); i++) { // for every bus in the buses list
            if (Objects.equals(buses.get(i).getBusID(), bus.getBusID())) { // if the requested bus ID is found, changes can be set
                buses.set(i, bus);
                return true; // return updated values
            }
        }
        return false; // if requested bus ID is not found, it returns false
    }


    // Retrieve ()
    public Bus retrieve(String busID) {
        for (Bus bus : buses) { // for each bus in buses list
            if (bus.getBusID().equals(busID)) { // if the requested busID is found
                return bus; // bus is returned
            }
        }
        return null; // otherwise no value is returned
    }


    // Count ()
    public int count() {
        return buses.size();
    }
}
