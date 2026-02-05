package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class CandidateDataReader {

    private static JsonNode candidateData;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            candidateData = mapper.readTree(
                    new File("src/test/resources/config/candidateData.json")
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load candidateData.json", e);
        }
    }

    // Login credentials
    public static String getUsername() {
        return candidateData.get("login").get("username").asText();
    }

    public static String getPassword() {
        return candidateData.get("login").get("password").asText();
    }

    //candiate details

    public static String getFirstName() {
        return candidateData.get("candidate").get("firstName").asText();
    }

    public static String getLastName() {
        return candidateData.get("candidate").get("lastName").asText();
    }

    public static String getEmail() {
        return candidateData.get("candidate").get("email").asText();
    }

    public static String getPhoneNumber() {
        return candidateData.get("candidate").get("phone").asText();
    }

    public static String getVacancy() {
        return candidateData.get("candidate").get("vacancy").asText();
    }

    public static String getResumePath() {
        return candidateData.get("candidate").get("resumePath").asText();
    }
    public static String getKeywords() {
        JsonNode keywordsNode = candidateData.get("keywords");
        return keywordsNode != null ? keywordsNode.asText() : "";
    }


}
