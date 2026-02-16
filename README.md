# OrangeHRM Test Automation Framework

A comprehensive test automation framework for OrangeHRM built with **Playwright**, **Cucumber BDD**, and **Java**. This framework provides end-to-end testing capabilities with support for sequential and parallel test execution, comprehensive reporting, and detailed logging.

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Feature Coverage](#feature-coverage)
- [Page Object Model](#page-object-model)
- [Utilities](#utilities)
- [Test Data](#test-data)
- [Reporting](#reporting)
- [Logging](#logging)
- [Contributing](#contributing)

---

## 📌 Project Overview

This automation framework is designed to test the **OrangeHRM** application with a focus on:

- **Recruitment Management** - Add, search, and manage candidate information
- **Leave Management** - Handle leave entitlements, applications, and approvals
- **Timesheet Management** - Create, edit, and validate timesheet entries

The framework follows industry best practices including:
- **Page Object Model (POM)** for maintainability
- **Behavior-Driven Development (BDD)** with Gherkin syntax
- **Parallel and Sequential Test Execution**
- **Comprehensive Reporting** with Allure and Extent Reports
- **Detailed Logging** with Log4j2
- **Configurable Locators** using external property files

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 21 | Programming Language |
| **Playwright** | 1.56.0 | Browser Automation |
| **Cucumber** | 7.14.0 | BDD Framework |
| **JUnit** | 5.10.0 | Test Runner |
| **Allure** | 2.24.0 | Reporting |
| **Log4j2** | Latest | Logging |
| **Maven** | 3.x | Build Tool |

---

## 📁 Project Structure

```
Framework_OrangeHRMS/
│
├── src/test/java/
│   ├── Driver/
│   │   └── PlaywrightFactory.java          # Browser initialization
│   │
│   ├── Pages/                              # Page Object Model
│   │   ├── LoginPage.java
│   │   ├── DashboardPage.java
│   │   ├── CandidatesPage.java
│   │   ├── AddCandidatePage.java
│   │   ├── CandidateDetailsPage.java
│   │   ├── Leave/
│   │   │   ├── EntitlementPage.java
│   │   │   └── ApplyLeavePage.java
│   │   └── Time/
│   │       └── TimesheetPage.java
│   │
│   ├── StepDefinitions/                    # Gherkin Step Implementations
│   │   ├── CommonStep.java
│   │   ├── LeaveSteps.java
│   │   ├── TimesheetSteps.java
│   │   └── CandidateSteps.java
│   │
│   ├── Hooks/
│   │   └── Hooks.java                      # Test Setup & Teardown
│   │
│   ├── Runners/
│   │   └── TestRunner.java                 # Cucumber Test Runner
│   │
│   └── Utils/
│       ├── LocatorReader.java              # Locator Property Reader
│       ├── WaitUtil.java                   # Wait Utilities
│       └── JsonUtil.java                   # JSON Data Parser
│
├── src/test/resources/
│   ├── Features/                           # Gherkin Feature Files
│   │   ├── candidate.feature
│   │   ├── leave.feature
│   │   └── timesheet.feature
│   │
│   ├── Locators/                           # Locator Property Files
│   │   ├── loginPage.properties
│   │   ├── dashboardPage.properties
│   │   ├── candidatesPage.properties
│   │   ├── leavePage.properties
│   │   └── timesheetPage.properties
│   │
│   ├── TestData/                           # Test Data Files
│   │   ├── candidateData.json
│   │   ├── leaveData.json
│   │   └── timesheetData.json
│   │
│   ├── Config/
│   │   └── config.properties               # Environment Configuration
│   │
│   ├── log4j2.xml                          # Logging Configuration
│   ├── allure.properties                   # Allure Configuration
│   └── junit-platform.properties           # JUnit Configuration
│
├── pom.xml                                 # Maven Dependencies
├── extent.properties                       # Extent Report Configuration
│
├── run-all-tests.ps1                       # PowerShell Script - All Tests
├── run-all-tests.sh                        # Shell Script - All Tests
├── run-parallel-tests.ps1                  # PowerShell Script - Parallel Tests
├── run-tests-with-reports.ps1              # PowerShell Script - With Reports
├── run-tests.bat                           # Batch Script - Run Tests
├── build.bat                               # Batch Script - Build Project
│
├── allure-results/                         # Allure Report Results
├── screenshots/                            # Test Failure Screenshots
└── target/                                 # Maven Build Output

```

---

## 📦 Prerequisites

Before running the tests, ensure you have the following installed:

- **Java 21 or higher**
  ```bash
  java -version
  ```

- **Maven 3.8.0 or higher**
  ```bash
  mvn -version
  ```

- **Git** (for version control)
  ```bash
  git --version
  ```

- **PowerShell 5.0+** (for Windows) or **Bash** (for Linux/Mac)

---

## 🔧 Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Framework_OrangeHRMS
```

### 2. Install Dependencies

```bash
mvn clean install
```

This command will:
- Clean previous build artifacts
- Download all required dependencies
- Compile the project
- Run tests (if configured)

### 3. Verify Installation

```bash
mvn --version
java -version
```

---

## ⚙️ Configuration

### Environment Configuration

Update `src/test/resources/Config/config.properties`:

```properties
# Application URL
app.url=https://opensource-demo.orangehrmlive.com

# Default Login Credentials
username=Admin
password=admin123

# Browser Configuration
browser=chromium
headless=false
screenshot.on.failure=true

# Wait Timeouts (in milliseconds)
implicit.wait=30000
explicit.wait=30000
page.load.timeout=30000

# Logging
log.level=INFO
```

### Locator Configuration

Locators are stored in property files under `src/test/resources/Locators/`:

**Example: `leavePage.properties`**
```properties
leavePage.entitlementsTab=//a[contains(text(), 'Entitlements')]
leavePage.addEntitlementOption=//a[normalize-space()='Add Entitlements']
leavePage.employeeNameInput=//input[@placeholder='Type for hints...']
leavePage.leaveTypeDropdown=//label[contains(text(), 'Leave Type')]/following-sibling::div//div
```

### Test Data Configuration

Test data is stored in JSON format under `src/test/resources/TestData/`:

**Example: `leaveData.json`**
```json
{
  "addEntitlement": {
    "leaveType": "Annual",
    "entitlementDays": "20"
  },
  "applyLeave": {
    "leaveType": "Annual",
    "fromDate": "2026-02-20",
    "toDate": "2026-02-22",
    "comments": "Personal leave"
  }
}
```

---

## ▶️ Running Tests

### Using Maven Command Line

#### Run All Tests
```bash
mvn clean test
```

#### Run Specific Feature
```bash
mvn clean test -Dcucumber.filter.tags="@candidate"
```

#### Run with Specific Tags
```bash
# Run all smoke tests
mvn clean test -Dcucumber.filter.tags="@smoke"

# Run all regression tests
mvn clean test -Dcucumber.filter.tags="@regression"

# Run leave tests only
mvn clean test -Dcucumber.filter.tags="@leave"
```

### Using PowerShell Scripts (Windows)

#### Run All Tests Sequentially
```powershell
.\run-all-tests.ps1
```

#### Run Tests in Parallel
```powershell
.\run-parallel-tests.ps1
```

#### Run Tests with Reports
```powershell
.\run-tests-with-reports.ps1
```

### Using Batch Scripts (Windows)

#### Run Tests
```cmd
run-tests.bat
```

#### Build Project
```cmd
build.bat
```

### Using Shell Scripts (Linux/Mac)

#### Run All Tests
```bash
bash run-all-tests.sh
```

---

## 📋 Feature Coverage

### 1. Candidate Management (`candidate.feature`)

**Scenarios Covered:**
- ✅ Add and search candidate successfully
- ✅ Shortlist candidate and schedule interview

**Key Operations:**
- Search candidates by name
- Add candidate with resume
- Manage candidate details
- Shortlist candidates
- Schedule interviews

### 2. Leave Management (`leave.feature`)

**Scenarios Covered:**
- ✅ Add leave entitlement
- ✅ Apply for leave
- ✅ View leave balance

**Key Operations:**
- Add leave entitlements
- Apply for leave
- View leave status
- Employee selection from header

### 3. Timesheet Management (`timesheet.feature`)

**Scenarios Covered:**
- ✅ Add new timesheet entry and validate total hours
- ✅ Edit existing timesheet entry and validate updated totals

**Key Operations:**
- Create timesheet entries
- Edit existing entries
- Validate total hours calculation
- View timesheet history

---

## 🏗️ Page Object Model

The framework uses the **Page Object Model (POM)** pattern to enhance maintainability and reusability.

### Structure

```
Pages/
├── BasePage.java              # Base class with common methods
├── LoginPage.java             # Login page interactions
├── DashboardPage.java         # Dashboard page interactions
├── CandidatesPage.java        # Candidate list page
├── AddCandidatePage.java      # Add candidate form
├── CandidateDetailsPage.java  # Candidate detail view
├── Leave/
│   ├── EntitlementPage.java   # Leave entitlement management
│   └── ApplyLeavePage.java    # Leave application
└── Time/
    └── TimesheetPage.java     # Timesheet management
```

### Page Class Example

```java
public class LoginPage {
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void enterUsername(String username) {
        page.fill("input[name='username']", username);
    }

    public void enterPassword(String password) {
        page.fill("input[name='password']", password);
    }

    public void clickLoginButton() {
        page.click("button[type='submit']");
    }

    public boolean isDashboardDisplayed() {
        return page.locator(".dashboard-header").isVisible();
    }
}
```

---

## 🛠️ Utilities

### 1. **WaitUtil.java**

Provides intelligent wait mechanisms:

```java
// Wait for element to be visible
WaitUtil.waitForVisible(page, locator);

// Click when ready
WaitUtil.clickWhenReady(page, locator);

// Wait for page load
WaitUtil.waitForPageLoad(page);

// Wait for toast message
WaitUtil.waitForToast(page);
```

### 2. **LocatorReader.java**

Reads locators from property files:

```java
String locator = LocatorReader.get("leavePage.entitlementsTab");
page.click(locator);
```

### 3. **JsonUtil.java**

Parses JSON test data:

```java
JsonData testData = JsonUtil.readTestData("leaveData.json", "addEntitlement");
String leaveType = testData.get("leaveType");
```

### 4. **PlaywrightFactory.java**

Manages browser initialization and teardown:

```java
Browser browser = PlaywrightFactory.launchBrowser("chromium");
Page page = browser.newPage();
```

---

## 📊 Reporting

### Allure Reports

#### Generate Allure Report
```bash
mvn allure:report
```

#### View Allure Report
```bash
allure serve allure-results
```

**Features:**
- Test execution history
- Detailed step screenshots
- Failure analysis
- Test duration metrics
- Trend analysis

### Extent Reports

Extent HTML reports are automatically generated in:
```
reports/extent-report.html
```

**Features:**
- Visual test dashboard
- Step-by-step execution details
- Screenshots on failure
- Test categorization
- Dark theme UI

---

## 📝 Logging

### Log Configuration

Logs are configured in `src/test/resources/log4j2.xml`:

```xml
<Configuration status="warn">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss} %-5p %c{1} - %m%n"/>
        </Console>
        <File name="File" fileName="logs/test-execution.log">
            <PatternLayout pattern="%d{ISO8601} [%t] %-5p %c{1} - %m%n"/>
        </File>
    </Appenders>
</Configuration>
```

### Log Levels

```
INFO    - General information and test flow
WARN    - Warning messages
ERROR   - Error messages and failures
DEBUG   - Detailed debugging information
TRACE   - Very detailed trace information
```

### Accessing Logs

Logs are stored in:
```
logs/test-execution.log
logs/test-execution-detailed.log
```

---

## 🔗 Hooks (Setup & Teardown)

The `Hooks.java` class handles test lifecycle:

```java
@Before
public void setUp(Scenario scenario) {
    // Initialize browser
    // Log scenario start
}

@After
public void tearDown(Scenario scenario) {
    // Capture screenshots on failure
    // Close browser
    // Log scenario end
}
```

---

## 🏃 Running Specific Scenarios

### Run by Tags

```bash
# Smoke tests only
mvn clean test -Dcucumber.filter.tags="@smoke"

# Regression tests only
mvn clean test -Dcucumber.filter.tags="@regression"

# Both smoke and regression
mvn clean test -Dcucumber.filter.tags="@smoke or @regression"

# Exclude flaky tests
mvn clean test -Dcucumber.filter.tags="not @flaky"
```

### Run by Feature

```bash
# Candidate feature only
mvn clean test -Dcucumber.features="src/test/resources/Features/candidate.feature"

# Leave feature only
mvn clean test -Dcucumber.features="src/test/resources/Features/leave.feature"

# Timesheet feature only
mvn clean test -Dcucumber.features="src/test/resources/Features/timesheet.feature"
```

---

## 📦 Dependencies

Key dependencies in `pom.xml`:

```xml
<!-- Playwright -->
<dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.56.0</version>
</dependency>

<!-- Cucumber -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.14.0</version>
</dependency>

<!-- JUnit 5 -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
</dependency>

<!-- Allure -->
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-cucumber7-jvm</artifactId>
    <version>2.24.0</version>
</dependency>

<!-- Log4j2 -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.20.0</version>
</dependency>
```

---

## ✅ Best Practices

1. **Locator Management**
   - Centralize all locators in property files
   - Use meaningful locator names
   - Keep locators DRY (Don't Repeat Yourself)

2. **Test Data**
   - Store test data in JSON/property files
   - Use scenario outline for data-driven tests
   - Keep sensitive data in separate config files

3. **Wait Strategies**
   - Use explicit waits instead of implicit
   - Implement smart wait utilities
   - Handle timeout exceptions gracefully

4. **Logging**
   - Log significant steps and actions
   - Include error details in logs
   - Use appropriate log levels

5. **Test Organization**
   - Use meaningful scenario names
   - Tag tests appropriately
   - Keep step definitions granular

6. **Reporting**
   - Capture screenshots on failures
   - Include step-by-step details
   - Attach relevant artifacts

---

## 🐛 Troubleshooting

### Common Issues

#### 1. Playwright Browser Not Found
```bash
mvn clean install
# Then manually install browsers
playwright install
```

#### 2. Maven Dependencies Not Resolved
```bash
mvn clean install -U
```

#### 3. Tests Timing Out
- Increase timeout values in `config.properties`
- Check network connectivity
- Verify element locators are correct

#### 4. Report Generation Failed
```bash
# Clear previous reports
rm -rf allure-results/

# Regenerate
mvn clean test
mvn allure:report
```

---

## 📚 Additional Resources

- [Playwright Documentation](https://playwright.dev/java/)
- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [Allure Report Documentation](https://docs.qameta.io/allure/)
- [Maven Documentation](https://maven.apache.org/guides/index.html)

---

## 📧 Support & Contribution

For questions, issues, or contributions:

1. Check existing documentation
2. Review test logs for error details
3. Verify configuration files
4. Contact the QA team

---

## 📄 License

This project is proprietary and confidential. All rights reserved.

---

## 🔄 Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0 | 2026-02-15 | Initial framework setup with Candidate, Leave, and Timesheet features |

---

**Last Updated:** February 15, 2026

---

