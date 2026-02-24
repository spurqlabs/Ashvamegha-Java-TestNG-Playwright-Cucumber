package Runners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CucumberReportGenerator {

    public static void main(String[] args) {
        try {
            String reportOutputDirectory = "reports";
            String projectName = "OrangeHRMS Automation Tests";

            // List of JSON report files to process
            List<String> jsonReportFiles = new ArrayList<>();

            // Check for cucumber.json in different locations
            File targetCucumberJson = new File("target/cucumber-reports/cucumber.json");
            File reportsCucumberJson = new File("reports/cucumber.json");
            File targetCucumberJson2 = new File("target/cucumber.json");

            if (targetCucumberJson.exists()) {
                jsonReportFiles.add(targetCucumberJson.getAbsolutePath());
                System.out.println("Found: " + targetCucumberJson.getAbsolutePath());
            }
            if (reportsCucumberJson.exists()) {
                jsonReportFiles.add(reportsCucumberJson.getAbsolutePath());
                System.out.println("Found: " + reportsCucumberJson.getAbsolutePath());
            }
            if (targetCucumberJson2.exists()) {
                jsonReportFiles.add(targetCucumberJson2.getAbsolutePath());
                System.out.println("Found: " + targetCucumberJson2.getAbsolutePath());
            }

            if (jsonReportFiles.isEmpty()) {
                System.out.println("\n✓ No cucumber JSON report files found!");
                System.out.println("This is normal if using Maven plugin for report generation.");
                System.out.println("\nSearched in:");
                System.out.println("- " + targetCucumberJson.getAbsolutePath());
                System.out.println("- " + reportsCucumberJson.getAbsolutePath());
                System.out.println("- " + targetCucumberJson2.getAbsolutePath());
                System.out.println("\nNote: Maven will generate reports automatically via:");
                System.out.println("  mvn cucumber-reporting:generate");
                System.out.println("\nOr update pom.xml with maven-cucumber-reporting plugin.");
                return;
            }

            System.out.println("\nFound " + jsonReportFiles.size() + " JSON report file(s)");
            System.out.println("\nNote: This class demonstrates JSON file location detection.");
            System.out.println("For HTML report generation, use Maven plugin:");
            System.out.println("  mvn cucumber-reporting:generate");

            System.out.println("\n========================================");
            System.out.println("JSON Report Detection Complete!");
            System.out.println("========================================");
            System.out.println("JSON Files Found: " + jsonReportFiles.size());
            System.out.println("Report Output Directory: " + reportOutputDirectory);
            System.out.println("Next Step: Run 'mvn cucumber-reporting:generate'");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.err.println("Error in report generator: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
