package Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class ConfigReader {

    private static JsonNode config;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            config = mapper.readTree(
                    new File("src/test/resources/Config/config.json")
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.json", e);
        }
    }

    public static String get(String key) {
        JsonNode node = config.get(key);
        if (node == null) {
            throw new RuntimeException("Config key not found: " + key + ". Check config.json file.");
        }
        return node.asText();
    }
}
