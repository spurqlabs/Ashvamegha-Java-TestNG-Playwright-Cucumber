package Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class ConfigReader {

    private static final JsonNode config;

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

    // 🔥 SYSTEM PROPERTY > CONFIG FILE
    public static String get(String key) {

        // 1️⃣ Check Maven / JVM system property
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) {
            return sysProp;
        }

        // 2️⃣ Fallback to config.json
        JsonNode node = config.get(key);
        if (node == null) {
            throw new RuntimeException(
                    "Config key not found: " + key +
                            ". Check config.json or system property."
            );
        }
        return node.asText();
    }
}
