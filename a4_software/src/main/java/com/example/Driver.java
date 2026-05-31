package com.example;

// Driver class stores driver information 
public class Driver {
    // Driver attributes
    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType; // Light, Medium, Heavy, PublicTransport
    private String address;
    private String birthdate;

    // Consturcter to initialize driver attributes
    public Driver(String driverID, String name, int experienceYears, String licenseType, String address, String birthdate) {
        this.driverID = driverID;
        this.name = name;
        this.experienceYears = experienceYears;
        this.licenseType = licenseType;
        this.address = address;
        this.birthdate = birthdate;
    }

    // Returns the driver ID
    public String getDriverID() {
        return driverID;
    }

    // Returns the driver name
    public String getName() {
        return name;
    }

    // Returns the driver experience years
    public int getExperienceYears() {
        return experienceYears;
    }

    // Returns the driver license type
    public String getLicenseType() {
        return licenseType;
    }

    // Returns the  address
    public String getAddress() {
        return address;
    }

    // Returns the birthdate
    public String getBirthdate() {
        return birthdate;
    }
    
}

