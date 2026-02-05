package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class ConfigReader {

    private static JsonNode configData;

    // Load config.json once
    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            configData = mapper.readTree(
                    new File("src/test/resources/config/config.json")
            );
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load config.json", e);
        }
    }

    // Generic getter method
    public static String get(String key) {
        return configData.get(key).asText();
    }
}
