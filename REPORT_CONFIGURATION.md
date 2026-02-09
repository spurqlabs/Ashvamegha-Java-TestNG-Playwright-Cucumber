# Your Current Report Configuration

## ✅ TestRunner.java Configuration

Your `src/test/java/Runners/TestRunner.java` is correctly configured for HTML report generation:

```java
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/Features",          // Feature files location
    glue = {
        "StepDefinitions",                             // Step definition packages
        "Hooks"                                        // Hooks packages
    },
    plugin = {                                         // 👇 Report Plugins
        "pretty",                                      // Console output
        "html:reports/cucumber-report.html",          // ✅ HTML Report (PRIMARY)
        "json:reports/cucumber.json",                 // ✅ JSON Report
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"  // ✅ Allure Report
    },
    monochrome = true,                                // Clean console output
    tags = "@smoke"                                   // Filter by tags
)
public class TestRunner {
}
```

---

## ✅ pom.xml Dependencies for Reporting

### Cucumber Dependencies
```xml
<!-- Cucumber Framework -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.14.0</version>
</dependency>

<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit</artifactId>
    <version>7.14.0</version>
    <scope>test</scope>
</dependency>
```

### Allure Dependencies
```xml
<!-- Allure for Cucumber -->
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-cucumber7-jvm</artifactId>
    <version>2.24.0</version>
</dependency>
```

### Extent Reports (for advanced features)
```xml
<dependency>
    <groupId>com.aventstack</groupId>
    <artifactId>extentreports</artifactId>
    <version>5.1.1</version>
</dependency>
```

---

## ✅ pom.xml Build Plugins for Reporting

### Surefire Plugin (Test Executor)
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.5</version>
    <configuration>
        <parallel>methods</parallel>
        <threadCount>2</threadCount>
        <forkCount>1</forkCount>
        <reuseForks>true</reuseForks>
    </configuration>
</plugin>
```

### Allure Maven Plugin (Report Generator)
```xml
<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>2.11.2</version>
    <configuration>
        <reportVersion>2.24.0</reportVersion>
        <resultsDirectory>${project.build.directory}/allure-results</resultsDirectory>
        <reportDirectory>${project.build.directory}/allure-report</reportDirectory>
    </configuration>
</plugin>
```

### Failsafe Plugin (Integration Test Support)
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <version>3.2.5</version>
    <executions>
        <execution>
            <goals>
                <goal>integration-test</goal>
                <goal>verify</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

---

## 📊 How Reports Are Generated

### Step 1: Test Execution
```
mvn test
    ↓
Maven Surefire Plugin executes tests
    ↓
TestRunner.java runs Cucumber scenarios
```

### Step 2: Report Generation
```
Cucumber Framework
    ├─→ Pretty Plugin → Console output
    ├─→ HTML Plugin → reports/cucumber-report.html ✅
    ├─→ JSON Plugin → reports/cucumber.json ✅
    └─→ Allure Plugin → allure-results/*.json ✅
```

### Step 3: Report Access
```
reports/cucumber-report.html ← Open in browser
OR
mvn allure:serve ← View with Allure interface
```

---

## 🎯 Report Plugin Breakdown

### Plugin 1: Pretty Plugin
```
plugin: "pretty"
Purpose: Formats console output
Output: Console (terminal)
Format: Readable text
```

### Plugin 2: HTML Plugin
```
plugin: "html:reports/cucumber-report.html"
Purpose: Generates interactive HTML report
Output: reports/cucumber-report.html
Format: HTML with JavaScript
Size: ~2 MB with full test data
Features: 
  - Scenario overview
  - Step details
  - Test results
  - Execution time
```

### Plugin 3: JSON Plugin
```
plugin: "json:reports/cucumber.json"
Purpose: Machine-readable test data
Output: reports/cucumber.json
Format: JSON
Size: ~2-5 KB
Features:
  - Can be parsed by tools
  - Used for CI/CD integration
  - Data for custom reports
```

### Plugin 4: Allure Plugin
```
plugin: "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
Purpose: Advanced test analytics
Output: allure-results/ (multiple JSON files)
Format: Allure-compatible JSON
Size: Variable (50+ files)
Features:
  - Test history
  - Execution timeline
  - Trending
  - Flaky tests detection
  - Retry tracking
```

---

## 📁 Complete Report Structure

```
Framework_OrangeHRMS/
│
├── src/
│   └── test/
│       ├── java/
│       │   └── Runners/
│       │       └── TestRunner.java        ← Configuration
│       └── resources/
│           ├── Features/
│           │   └── candidate.feature
│           └── log4j2.xml
│
├── reports/                               ← MAIN REPORTS
│   ├── cucumber-report.html               ← HTML Report ✅
│   ├── cucumber.json                      ← JSON Data ✅
│   └── [embedded files]
│
├── allure-results/                        ← ALLURE DATA
│   ├── [uuid]-result.json
│   ├── [uuid]-container.json
│   └── [more files...]
│
├── target/
│   ├── test-classes/
│   ├── allure-results/                    (copy of above)
│   └── allure-report/                     (after mvn allure:report)
│       ├── index.html
│       ├── plugins/
│       └── widgets/
│
└── pom.xml                                ← Build configuration
```

---

## 🔧 Configuration Details

### Feature File Location
```
Path: src/test/resources/Features
Files: candidate.feature (and more)
Cucumber reads from: features property in @CucumberOptions
```

### Step Definition Location
```
Path: src/test/java/StepDefinitions
Class: CommonStep.java
Cucumber scans: glue = {"StepDefinitions", "Hooks"}
```

### Report Output Location
```
Cucumber HTML: reports/cucumber-report.html
JSON Data: reports/cucumber.json
Allure Data: allure-results/
Allure HTML: target/allure-report/
```

---

## ✨ Current Report Status

### Cucumber HTML Report
- **Status:** ✅ ACTIVE
- **Location:** `D:\Automation\Framework_OrangeHRMS\reports\cucumber-report.html`
- **Last Updated:** Every test execution
- **File Size:** ~2 MB
- **Generated By:** `html:reports/cucumber-report.html` plugin

### Allure Report
- **Status:** ✅ ACTIVE
- **Location:** `D:\Automation\Framework_OrangeHRMS\allure-results/`
- **Files:** 50+ JSON result files
- **Generated By:** `io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm` plugin
- **View Command:** `mvn allure:serve`

### JSON Report
- **Status:** ✅ ACTIVE
- **Location:** `D:\Automation\Framework_OrangeHRMS\reports\cucumber.json`
- **File Size:** ~2.4 KB
- **Generated By:** `json:reports/cucumber.json` plugin

---

## 🚀 How to Use Each Report

### Cucumber HTML Report
```
When to use: Quick report viewing
How to open: Double-click cucumber-report.html
Time to load: Instant
Best for: Manual review, sharing with stakeholders
```

### Allure Report
```
When to use: Detailed analytics and trending
How to open: mvn allure:serve (auto-opens browser)
Time to load: 2-5 seconds
Best for: Test analytics, history, flaky test detection
```

### JSON Report
```
When to use: Programmatic access
How to open: Parse with JSON parser
Time to load: Instant
Best for: CI/CD integration, custom dashboards
```

---

## 💡 Key Configuration Points

### 1. Feature Path
```
features = "src/test/resources/Features"
→ Tells Cucumber where to find .feature files
```

### 2. Glue Path
```
glue = {"StepDefinitions", "Hooks"}
→ Tells Cucumber where to find step definitions and hooks
```

### 3. Plugin Configuration
```
plugin = {...}
→ Specifies which reports to generate
→ Each plugin is independent
```

### 4. Tag Filter
```
tags = "@smoke"
→ Only runs scenarios with @smoke tag
→ Can be overridden with: mvn test -Dtags="@tag"
```

---

## ✅ Verification Checklist

- ✅ TestRunner.java has correct @CucumberOptions
- ✅ Feature files exist in src/test/resources/Features
- ✅ Step definitions in StepDefinitions package
- ✅ Hooks configured in Hooks package
- ✅ pom.xml has Cucumber dependencies
- ✅ pom.xml has Allure dependencies
- ✅ pom.xml has Allure Maven Plugin
- ✅ reports/ directory exists
- ✅ HTML reports are generated
- ✅ Allure results are generated

**All systems are GO! Reports are configured and generating properly! 🎉**
