# Quick Start Guide - Running Tests

## Prerequisites

1. **Java 21+** installed
2. **Maven 3.8+** installed (or use bundled mvnw)
3. **Chrome Browser** installed (Playwright will use the system Chrome)

## Step-by-Step Execution

### Option 1: Run from Command Line (PowerShell)

```powershell
# Navigate to project directory
cd D:\Automation\Framework_OrangeHRMS

# Clean and run all @smoke tests
.\mvnw clean test -Dtags="@smoke"

# After tests complete, generate Allure report
.\mvnw allure:serve
```

### Option 2: Run from IntelliJ IDE

1. **Right-click on TestRunner.java** → Run 'TestRunner'
   - Location: `src/test/java/Runners/TestRunner.java`
   - This will run all @smoke tagged scenarios

2. **Or right-click on feature file** → Run Feature
   - Location: `src/test/resources/Features/candidate.feature`
   - This runs the entire feature

3. **Or run specific scenario** by right-clicking on the scenario in the feature file

### Option 3: Run with Tags

```powershell
# Run only login tests
.\mvnw clean test -Dtags="@login"

# Run regression tests
.\mvnw clean test -Dtags="@regression"

# Run multiple tags
.\mvnw clean test -Dtags="@smoke or @regression"
```

## What You'll See During Test Execution

1. **Chrome browser opens** (visibly, not headless)
2. **OrangeHRM login page** loads
3. **Credentials are entered** (Username: Admin, Password: admin123)
4. **Login button clicked** - browser navigates to dashboard
5. **Dashboard page verified** - screenshot captured
6. **Navigation to Recruitment > Candidates** - screenshot captured
7. **Candidates page header verified** - screenshot captured
8. **Browser stays open for 5 seconds** to see final state
9. **Browser closes** and test completes

## Reports Generation

### Allure Report (Recommended)
```powershell
.\mvnw allure:serve
```
- Opens in browser automatically (usually http://localhost:4040)
- Shows beautiful test results with screenshots
- Interactive timeline and statistics

### Cucumber HTML Report
```
reports/cucumber-report.html
```
- Location: `reports/cucumber-report.html` (in project root)
- Open directly in browser
- Shows step-by-step execution with screenshots

### Console Output
All steps are logged to console with timestamps:
```
10:00:04 INFO  StepDefinitions.CommonStep - Opening OrangeHRM application
10:00:04 INFO  StepDefinitions.CommonStep - LoginPage initialized successfully
10:00:06 INFO  StepDefinitions.CommonStep - Entering username and password
10:00:08 INFO  StepDefinitions.CommonStep - Clicking login button
```

## Test Results Location

| Item | Location |
|------|----------|
| Test Reports | `reports/cucumber-report.html` |
| Allure Results | `target/allure-results/` |
| Screenshots | `screenshots/` (organized by type) |
| Console Logs | IDE console / Terminal output |

## Troubleshooting

### Chrome Browser Not Found
```
Error: Could not find Chrome browser
```
**Solution:** Install Chrome from https://www.google.com/chrome/

### Cannot Find Module Error (Node.js related)
```
Error: Cannot find module './../../../package.json'
```
**Solution:** This is a known Playwright issue. Usually resolves on second run.

### Test Fails - Dashboard Page Not Displayed
1. Check if credentials are correct in `src/test/resources/TestData/candidateData.json`
2. Verify OrangeHRM application is accessible at https://opensource-demo.orangehrmlive.com
3. Check network connectivity

### Tests Run Multiple Times
- Check that `tags = "@smoke"` in TestRunner.java is set correctly
- This filter ensures only @smoke tagged scenarios run

## Test Data

**Login Credentials** (in `src/test/resources/TestData/candidateData.json`):
```json
{
  "username": "Admin",
  "password": "admin123"
}
```

**Candidate Data**:
```json
{
  "firstName": "Rocky",
  "lastName": "Sharma",
  "email": "rocky.sharma@testmail.com",
  "phone": "9876543210",
  "vacancy": "Senior QA Lead",
  "keywords": "Automation"
}
```

## Key Configuration Files

| File | Purpose |
|------|---------|
| `pom.xml` | Maven dependencies & plugins |
| `src/test/java/Runners/TestRunner.java` | Cucumber test runner |
| `src/test/resources/Features/candidate.feature` | BDD scenarios |
| `src/test/resources/Locators/locators.json` | UI element selectors |
| `src/test/resources/Config/config.json` | Application URL & settings |
| `src/test/resources/TestData/candidateData.json` | Test credentials & data |

## Browser Configuration

**File:** `src/test/java/Driver/PlaywrightFactory.java`

Current settings:
- **Mode:** Headed (visible - not headless)
- **Slow Motion:** 800ms delay between actions
- **Wait Timeout:** 15 seconds for elements
- **Navigation Timeout:** 60 seconds
- **Post-Test Wait:** 5 seconds (to see dashboard)

To change these settings, modify the values in PlaywrightFactory.java

## Logging Configuration

All logs are output to console via SLF4J → Log4j2

**Log Levels:**
- INFO: Normal step execution
- ERROR: Test failures
- DEBUG: Detailed diagnostics

## Next Steps

1. ✅ Run tests using Option 1 or 2 above
2. ✅ View Allure report: `.\mvnw allure:serve`
3. ✅ Check Cucumber HTML report: Open `reports/cucumber-report.html`
4. ✅ Review screenshots in `screenshots/` directory
5. ✅ Modify scenarios in `src/test/resources/Features/candidate.feature`

---

**All console errors have been fixed. Tests should now run successfully!**
