package Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class LocatorReader {

    private static JsonNode locators;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            locators = mapper.readTree(
                    new File("src/test/resources/Locators/locators.json")
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load locators.json", e);
        }
    }

    private LocatorReader() {
        // prevent object creation
    }

    /**
     * Generic locator getter
     * Example:
     * LocatorReader.get("login.username")
     * LocatorReader.get("addCandidate.saveBtn")
     */
    public static String get(String key) {
        JsonNode node = locators;
        for (String k : key.split("\\.")) {
            node = node.get(k);
            // Check if node is null and throw meaningful error
            if (node == null) {
                throw new RuntimeException("Locator key not found: " + key + ". Check locators.json file.");
            }
        }
        return node.asText();
    }
}
