package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class LocatorReader {

    private static JsonNode locatorData;

    // Load locators.json once
    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            locatorData = mapper.readTree(
                    new File("src/test/resources/config/locators.json")
            );
        } catch (Exception e) {
            throw new RuntimeException(" Failed to load locators.json", e);
        }
    }

    //  THIS METHOD FIXES YOUR ERROR
    public static String get(String pageName, String locatorName) {
        return locatorData.get(pageName).get(locatorName).asText();
    }
}
