package com.example.testData;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
// Importing files, list, path, map, and collectors

import com.example.Bus;


// obtaining bus details from BusData.txt into a loader file for BusIntegrationTest
public class BusDataLoader {
    public static Bus loadFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);

        Map<String, String> data = lines.stream()
            .map(line -> line.split(":", 2))
            .filter(parts -> parts.length == 2) // preventing errors from missing a :
            .collect(Collectors.toMap(
                parts -> parts[0].trim(),
                parts -> parts[1].trim() // splitting the key and value from txt file
            ));

        Bus bus = new Bus(
            data.get("busID"),
            Integer.parseInt(data.get("capacity")),
            Double.parseDouble(data.get("fuelLevel")),
            data.get("fuelType")
        );
        return bus;
    }
}
