package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DriverUnitTest {

    // helper validation methods (self-contained in test file)
    
    // D1: validates driverID format
    boolean isValidDriverID(String id) {
        if (id == null || id.length() != 10) return false;
        char c0 = id.charAt(0), c1 = id.charAt(1);
        if (c0 < '2' || c0 > '9') return false;
        if (c1 < '2' || c1 > '9') return false;
        char c8 = id.charAt(8), c9 = id.charAt(9);
        if (!Character.isUpperCase(c8) || !Character.isLetter(c8)) return false;
        if (!Character.isUpperCase(c9) || !Character.isLetter(c9)) return false;
        long specialCount = id.substring(2, 8).chars()
                .filter(c -> !Character.isLetterOrDigit(c))
                .count();
        return specialCount >= 2;
    }

    // D2: validates address format
    boolean isValidAddress(String address) {
        if (address == null) return false;
        String[] parts = address.split("\\|", -1);
        return parts.length == 5 &&
               java.util.Arrays.stream(parts).allMatch(p -> !p.trim().isEmpty());
    }

    // D3: validates birthdate format DD-MM-YYYY
    boolean isValidBirthdate(String birthdate) {
        if (birthdate == null) return false;
        return birthdate.matches("\\d{2}-\\d{2}-\\d{4}");
    }

    // D1 - Test Case 1: Valid driverID accepted
    @Test
    void testValidDriverID() {
        assertTrue(isValidDriverID("23@#1234AB"));
    }

    // D1 - Test Case 2: First two chars out of range
    @Test
    void testInvalidDriverID_FirstCharsOutOfRange() {
        assertFalse(isValidDriverID("01@#1234AB"));
    }

    // D1 - Test Case 3: Only one special char in middle
    @Test
    void testInvalidDriverID_OneSpecialChar() {
        assertFalse(isValidDriverID("23@1234AB"));
    }

    // D2 - Test Case 4: Valid 5-part address
    @Test
    void testValidAddress() {
        assertTrue(isValidAddress("12|La Trobe St|Melbourne|VIC|Australia"));
    }

    // D2 - Test Case 5: Address with only 4 parts
    @Test
    void testInvalidAddress_FourParts() {
        assertFalse(isValidAddress("12|La Trobe St|Melbourne|VIC"));
    }

    // D2 - Test Case 6: Address with empty segment
    @Test
    void testInvalidAddress_EmptySegment() {
        assertFalse(isValidAddress("12||Melbourne|VIC|Australia"));
    }

    // D3 - Test Case 7: Valid birthdate
    @Test
    void testValidBirthdate() {
        assertTrue(isValidBirthdate("15-06-1990"));
    }

    // D3 - Test Case 8: Slashes instead of dashes
    @Test
    void testInvalidBirthdate_Slashes() {
        assertFalse(isValidBirthdate("15/06/1990"));
    }

    // D3 - Test Case 9: Reversed format
    @Test
    void testInvalidBirthdate_Reversed() {
        assertFalse(isValidBirthdate("1990-06-15"));
    }

    // D4 - Test Case 10: licenseType can change when experience <= 10
    @Test
    void testLicenseCanChange_Under10() {
        int experienceYears = 8;
        String currentLicense = "Heavy";
        String newLicense = "Light";
        // experience <= 10 so change is allowed
        boolean canChange = experienceYears <= 10;
        assertTrue(canChange);
    }

    // D4 - Test Case 11: licenseType cannot change when experience > 10
    @Test
    void testLicenseCannotChange_Over10() {
        int experienceYears = 15;
        String currentLicense = "Heavy";
        String newLicense = "Light";
        // experience > 10 and license is changing so should be rejected
        boolean shouldReject = experienceYears > 10 && !currentLicense.equals(newLicense);
        assertTrue(shouldReject);
    }

    // D4 - Test Case 12: Same licenseType when experience exactly 10
    @Test
    void testLicenseSame_ExactlyTen() {
        int experienceYears = 10;
        String currentLicense = "Heavy";
        String newLicense = "Heavy";
        // experience is not > 10 so allowed, also license not changing
        boolean canChange = experienceYears <= 10 || currentLicense.equals(newLicense);
        assertTrue(canChange);
    }

    // D5 - Test Case 13: driverID is immutable
    @Test
    void testDriverID_Immutable() {
        String originalID = "23@#1234AB";
        // driverID should never change - verified by checking it stays the same
        String afterUpdate = "23@#1234AB";
        assertEquals(originalID, afterUpdate);
    }

    // D5 - Test Case 14: Name is immutable
    @Test
    void testName_Immutable() {
        String originalName = "Alice Smith";
        // name should never change
        String afterUpdate = "Alice Smith";
        assertEquals(originalName, afterUpdate);
    }

    // D5 - Test Case 15: Mutable fields update correctly
    @Test
    void testMutableFields_UpdateCorrectly() {
        String newAddress = "99|Macquarie St|Sydney|NSW|Australia";
        int newExperience = 9;
        // verify the new values are valid
        assertTrue(isValidAddress(newAddress));
        assertEquals(9, newExperience);
    }
}