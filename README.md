# OrangeHRM Automation Framework

## Overview
This is a comprehensive BDD (Behavior Driven Development) test automation framework built for testing the OrangeHRM application. It leverages **Playwright** for browser automation and **Cucumber** for behavior-driven testing, with a focus on maintainability, scalability, and ease of use.

## Tech Stack

### Core Dependencies
- **Java 21**: Programming language
- **Playwright 1.56.0**: Cross-browser automation library
- **Cucumber 7.14.0**: BDD framework for writing human-readable test scenarios
- **JUnit 5**: Test execution framework
- **Maven**: Build and dependency management
- **SLF4J & Logback**: Logging framework
- **Allure**: Test reporting and visualization

### Key Features
- **Cross-browser support** (Chrome, Firefox, Safari, Edge)
- **Parallel test execution** capability
- **Data-driven testing** with JSON configuration
- **Screenshot and video capture** on test failure
- **HTML and Allure reports** with detailed test metrics
- **Page Object Model (POM)** design pattern
- **Centralized configuration** management
- **Retry mechanism** for flaky tests

## Project Structure

```
Framework_OrangeHRMS/
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   ├── Driver/              # Playwright browser factory
│   │   │   ├── Hooks/               # Cucumber hooks for setup/teardown
│   │   │   ├── Pages/               # Page Object Model classes
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── Time/
│   │   │   │   ├── Leave/
│   │   │   │   └── Candidate/
│   │   │   ├── StepDefinitions/     # Cucumber step definitions
│   │   │   │   ├── CommonStep.java
│   │   │   │   ├── TimesheetSteps.java
│   │   │   │   ├── LeaveSteps.java
│   │   │   │   └── CandidateSteps.java
│   │   │   ├── Runners/             # Cucumber test runners
│   │   │   └── Utils/               # Utility classes
│   │   │       ├── WaitUtil.java
│   │   │       ├── TestDataReader.java
│   │   │       └── LocatorReader.java
│   │   └── resources/
│   │       ├── Features/            # Feature files (.feature)
│   │       │   ├── timesheet.feature
│   │       │   ├── leave.feature
│   │       │   └── candidate.feature
│   │       ├── locators/            # Locator properties files
│   │       ├── testdata/            # JSON test data files
│   │       └── application.properties
├── pom.xml                           # Maven configuration
├── mvnw & mvnw.cmd                   # Maven wrapper scripts
└── README.md                         # This file
```

## Module Coverage

### 1. **Timesheet Management**
- Add new timesheet entries
- Edit existing timesheet entries
- Validate total hours calculation
- Timesheet save and validation

**Feature File**: `src/test/resources/Features/timesheet.feature`

### 2. **Leave Management**
- Add leave entitlements
- Apply for leaves
- Validate leave application submission
- Employee header selection

**Feature File**: `src/test/resources/Features/leave.feature`

### 3. **Candidate Management**
- Shortlist candidates
- Schedule interviews
- Candidate search and filtering
- View candidate details

**Feature File**: `src/test/resources/Features/candidate.feature`

## Setup & Installation

### Prerequisites
- **Java 21** or higher installed
- **Maven 3.8.0** or higher installed
- **Git** for version control
- **Administrator privileges** (for browser installation)

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Framework_OrangeHRMS
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```
   This command will:
   - Download all required dependencies
   - Download Playwright browsers automatically
   - Build the project

3. **Verify installation**
   ```bash
   mvn --version
   java --version
   ```

## Configuration

### Test Data & Locators
- **Login Credentials**: Configured in `application.properties`
- **Locators**: Stored in `locators/` directory (properties files)
- **Test Data**: Stored in `testdata/` directory (JSON format)

### Default Application URL
```
https://opensource-demo.orangehrmlive.com
```

### Default Credentials
- **Username**: Admin
- **Password**: admin123

## Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Feature
```bash
mvn clean test -Dcucumber.features="src/test/resources/Features/timesheet.feature"
```

### Run Tests by Tag
```bash
mvn clean test -Dcucumber.options="--tags @smoke"
mvn clean test -Dcucumber.options="--tags @regression"
```

### Run Tests in Parallel
```bash
mvn clean test -DthreadCount=4
```

### Generate Allure Report
```bash
mvn allure:report
mvn allure:serve
```

## Test Scenarios

### Timesheet Module
| Scenario | Status | Tags |
|----------|--------|------|
| Add new timesheet entry and validate total hours | ✓ | @smoke, @regression |
| Edit existing timesheet entry and validate updated totals | ✓ | @regression |

### Leave Module
| Scenario | Status | Tags |
|----------|--------|------|
| Add leave entitlement successfully | ✓ | @leave, @regression |
| Apply for leave successfully | ✓ | @leave, @smoke |

### Candidate Module
| Scenario | Status | Tags |
|----------|--------|------|
| Shortlist candidate and schedule interview | ✓ | @candidate, @regression |
| Search and filter candidates | ✓ | @candidate, @smoke |

## Test Execution Flow

```
1. Login to OrangeHRM Application
   ↓
2. Navigate to Respective Module (Time/Leave/Candidate)
   ↓
3. Perform Required Actions
   ↓
4. Validate Expected Results
   ↓
5. Generate Test Report & Screenshots
   ↓
6. Clean Up (Close Browser & Sessions)
```

## Page Object Model (POM) Implementation

The framework follows the Page Object Model design pattern for better maintainability:

```java
public class LoginPage {
    private final Page page;
    
    public LoginPage(Page page) {
        this.page = page;
    }
    
    public void enterUsername() { ... }
    public void enterPassword() { ... }
    public void clickLoginButton() { ... }
}
```

Benefits:
- ✓ Reduced code duplication
- ✓ Improved test maintainability
- ✓ Easier locator updates
- ✓ Better code organization

## Utility Classes

### WaitUtil.java
Provides explicit waits for web elements:
- `waitForVisible()`: Wait for element visibility
- `clickWhenReady()`: Click element when ready
- `waitForToast()`: Wait for toast notifications

### TestDataReader.java
Manages test data retrieval from JSON files:
```java
String username = TestDataReader.get("login.username");
```

### LocatorReader.java
Manages web element locators from properties files:
```java
String locator = LocatorReader.get("login.username");
```

## Hooks & Setup/Teardown

Cucumber hooks handle:
- **Before**: Browser initialization, logging setup
- **After**: Screenshot capture on failure, browser cleanup
- **Test context**: Shared data between steps

## Logging

Framework uses **SLF4J** with **Logback** for comprehensive logging:
- Log level: INFO (configurable)
- Output: Console and file logs
- Format: Timestamp | Level | Class | Message

```
[INFO] LoginPage - Entering username: Admin
[INFO] LoginPage - Password entered successfully
[INFO] LoginPage - Login button clicked successfully
```

## Reporting

### HTML Report
- Located in: `reports/cucumber-report.html`
- Generated after each test run
- Includes test results and timing

### Allure Report
- Generated with: `mvn allure:report`
- Served with: `mvn allure:serve`
- Features:
  - Detailed test metrics
  - Timeline view
  - Failure analysis
  - Screenshot attachments

### Screenshots & Videos
- Stored in: `screenshots/` directory
- Captured on test failure automatically
- Video recording: `target/test-videos/`

## Best Practices

### ✓ DO
- Use meaningful scenario names
- Follow Page Object Model pattern
- Use explicit waits instead of sleep
- Organize tests with appropriate tags
- Implement proper exception handling
- Use data-driven testing with JSON
- Keep locators in external files
- Log important test actions

### ✗ DON'T
- Hardcode wait times (use explicit waits)
- Mix test logic with page objects
- Create element locators inside step definitions
- Use Thread.sleep() in tests
- Ignore test failures during debugging
- Commit sensitive credentials in code

## Troubleshooting

### Common Issues

#### 1. Browser Not Found
```
Error: Chromium not found
Solution: Run mvn clean install to download browsers
```

#### 2. Element Not Found
```
Error: TimeoutError: Waiting for locator to be visible
Solution: Update locator in properties file or increase wait time
```

#### 3. Element Locator Issues
```
Error: No element matches the given locator
Solution: Inspect element in browser and update locator accordingly
```

#### 4. Test Data Not Found
```
Error: Failed to read test data
Solution: Verify JSON file path and format in testdata directory
```

### Debug Mode
Run with debug logging:
```bash
mvn clean test -X
```

## CI/CD Integration

### GitHub Actions Example
```yaml
name: Test Execution
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up Java
        uses: actions/setup-java@v2
        with:
          java-version: 21
      - name: Run tests
        run: mvn clean test
```

## Performance Optimization

- **Parallel Execution**: Use Maven Surefire with thread count
- **Browser Context Reuse**: Share context across tests
- **Headless Mode**: Run tests without GUI (default in CI/CD)
- **Selective Test Runs**: Use tags for specific test suites

## Contributing

1. Create a feature branch
2. Write tests following BDD practices
3. Ensure all tests pass locally
4. Submit pull request with clear description

## Team & Support

For issues or questions, please:
1. Check existing documentation
2. Review similar test scenarios
3. Check logs for detailed error messages
4. Consult with QA lead

## License

This project is proprietary and intended for internal use only.

## Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0 | Feb 2026 | Initial framework setup with Timesheet, Leave, and Candidate modules |

## Future Enhancements

- [ ] API testing integration
- [ ] Performance testing module
- [ ] Mobile browser testing
- [ ] Database validation layer
- [ ] Test result trend analysis
- [ ] Selenium Grid integration

---

**Last Updated**: February 16, 2026  
**Framework Maintainer**: QA Team  
**Status**: Active Development
