package Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class LocatorReader {

    private static final JsonNode locators;

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
        if (key == null || key.isEmpty()) {
            throw new RuntimeException("Locator key cannot be null or empty");
        }

        JsonNode node = locators;
        String[] keys = key.split("\\.");

        for (int i = 0; i < keys.length; i++) {
            String k = keys[i];
            if (node == null) {
                throw new RuntimeException(
                        "Locator key not found: " + key + ". "
                                + "Path failed at: " + String.join(".", java.util.Arrays.copyOf(keys, i))
                                + ". Check locators.json file."
                );
            }
            node = node.get(k);
        }

        if (node == null) {
            throw new RuntimeException(
                    "Locator key not found: " + key + ". Check locators.json file."
            );
        }

        return node.asText();
    }
}
