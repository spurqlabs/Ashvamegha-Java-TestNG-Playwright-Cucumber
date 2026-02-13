package Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestDataReader {

    private static final Map<String, JsonNode> dataCache = new HashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    private TestDataReader() {
        // prevent object creation
    }

    // ================= LOAD JSON =================
    private static JsonNode loadJson(String fileName) {
        try {
            File file = new File("src/test/resources/TestData/" + fileName);

            if (!file.exists()) {
                throw new RuntimeException(
                        "Test data file not found: " + file.getPath()
                );
            }

            return mapper.readTree(file);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load test data file: " + fileName, e
            );
        }
    }


    // ================= GET VALUE =================
    public static String get(String keyPath) {

        if (keyPath == null || !keyPath.contains(".")) {
            throw new RuntimeException(
                    "Invalid test data key: " + keyPath
            );
        }

        String[] keys = keyPath.split("\\.");

        String fileKey = keys[0];
        String fileName = fileKey + "Data.json";

        JsonNode rootNode =
                dataCache.computeIfAbsent(
                        fileKey,
                        k -> loadJson(fileName)
                );

        JsonNode node = rootNode;
        for (String key : keys) {
            node = node.get(key);

            if (node == null) {
                throw new RuntimeException(
                        "Test data key not found: " + keyPath +
                                " (check file: " + fileName + ")"
                );
            }
        }

        return node.asText();
    }

    // ================= GET MAP (NEW METHOD) =================
    public static Map<String, String> getMap(String keyPath) {

        if (keyPath == null || !keyPath.contains(".")) {
            throw new RuntimeException(
                    "Invalid test data key: " + keyPath
            );
        }

        String[] keys = keyPath.split("\\.");

        String fileKey = keys[0];
        String fileName = fileKey + "Data.json";

        JsonNode rootNode =
                dataCache.computeIfAbsent(
                        fileKey,
                        k -> loadJson(fileName)
                );

        JsonNode node = rootNode;
        for (String key : keys) {
            node = node.get(key);

            if (node == null) {
                throw new RuntimeException(
                        "Test data key not found: " + keyPath +
                                " (check file: " + fileName + ")"
                );
            }
        }

        if (!node.isObject()) {
            throw new RuntimeException(
                    "Expected JSON object at: " + keyPath
            );
        }

        Map<String, String> result = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields =
                node.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            result.put(entry.getKey(), entry.getValue().asText());
        }

        return result;
    }

    // ================= CHECK KEY EXISTS =================
    public static boolean exists(String keyPath) {

        if (keyPath == null || !keyPath.contains(".")) {
            return false;
        }

        String[] keys = keyPath.split("\\.");

        String fileKey = keys[0];
        String fileName = fileKey + "Data.json";

        JsonNode rootNode =
                dataCache.computeIfAbsent(
                        fileKey,
                        k -> loadJson(fileName)
                );

        JsonNode node = rootNode;
        for (String key : keys) {
            node = node.get(key);
            if (node == null) {
                return false;
            }
        }
        return true;
    }
}
