package Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class CandidateDataReader {

    private static JsonNode candidateData;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            candidateData = mapper.readTree(
                    new File("src/test/resources/TestData/candidateData.json")
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load candidateData.json", e);
        }
    }

    private CandidateDataReader() {
        // prevent object creation
    }

    /**
     * Generic getter
     * Example:
     * CandidateDataReader.get("login.username")
     * CandidateDataReader.get("candidate.email")
     */
    public static String get(String key) {
        JsonNode node = candidateData;
        for (String k : key.split("\\.")) {
            node = node.get(k);
            // Check if node is null and throw meaningful error
            if (node == null) {
                throw new RuntimeException("Candidate data key not found: " + key + ". Check candidateData.json file.");
            }
        }
        return node.asText();
    }
}
