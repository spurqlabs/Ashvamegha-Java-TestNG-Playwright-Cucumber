# Quick Report Generation Commands

## 🚀 One-Command Report Generation

### Generate Cucumber + Allure Reports
```bash
mvn clean test -Dtags="@smoke" && mvn allure:serve
```

**This will:**
1. Clean previous builds
2. Run tests with @smoke tag
3. Generate all reports (HTML, JSON, Allure)
4. Open Allure report in browser automatically

---

## 📋 Command Reference

### Run Tests and Generate Reports
```bash
# Run all tests
mvn test

# Run only @smoke tagged scenarios
mvn test -Dtags="@smoke"

# Run multiple tags
mvn test -Dtags="@smoke and @login"
```

### View Cucumber HTML Report
```bash
# File location:
D:\Automation\Framework_OrangeHRMS\reports\cucumber-report.html

# Open directly (from project root):
start reports\cucumber-report.html
```

### Generate and View Allure Report
```bash
# Generate Allure report
mvn allure:report

# Open Allure in browser (auto-opens)
mvn allure:serve
```

### View JSON Report
```bash
# File location:
D:\Automation\Framework_OrangeHRMS\reports\cucumber.json
```

---

## 📊 Report Files Location

### After Test Execution:

```
D:\Automation\Framework_OrangeHRMS\
├── reports/
│   ├── cucumber-report.html         👈 OPEN THIS IN BROWSER
│   ├── cucumber.json
│   └── [other report files]
│
├── allure-results/
│   ├── [uuid]-result.json
│   └── [uuid]-container.json
│
└── target/
    └── allure-report/               (After mvn allure:report)
        └── index.html               👈 ALLURE REPORT
```

---

## ✅ What Gets Generated

### Cucumber HTML Report
- ✅ Feature names and descriptions
- ✅ All scenarios with tags
- ✅ Step details (Given, When, Then)
- ✅ Pass/Fail status
- ✅ Execution duration
- ✅ Screenshots (if failures occur)

### Allure Report
- ✅ All above features
- ✅ Detailed test execution timeline
- ✅ Historical trends
- ✅ Test analytics
- ✅ Flaky test detection
- ✅ Categories and grouping

### JSON Report
- ✅ Machine-readable test data
- ✅ Can be consumed by other tools
- ✅ Useful for CI/CD integration

---

## 🔍 How to Open Reports

### Method 1: File Explorer
1. Open: `D:\Automation\Framework_OrangeHRMS\reports\`
2. Double-click: `cucumber-report.html`
3. Opens in default browser

### Method 2: Command Line
```bash
# Windows Command Prompt
start reports\cucumber-report.html

# Or from PowerShell
Invoke-Item reports\cucumber-report.html
```

### Method 3: In IDE
1. Right-click `cucumber-report.html`
2. Select "Open in Browser"
3. Or drag into browser tab

---

## 🎯 Example Workflow

### Complete Report Generation Flow:

```bash
# 1. Navigate to project
cd D:\Automation\Framework_OrangeHRMS

# 2. Clean and run tests
mvn clean test -Dtags="@smoke"

# 3. Wait for tests to complete...

# 4. Reports are now generated in:
# - reports/cucumber-report.html
# - reports/cucumber.json
# - allure-results/[files]

# 5. Open Cucumber HTML Report
start reports\cucumber-report.html

# 6. (Optional) Generate and view Allure Report
mvn allure:serve
```

---

## 📌 Important Notes

### Reports Always Generate When:
- ✅ Tests run with `mvn test`
- ✅ TestRunner class is executed
- ✅ Features folder contains .feature files
- ✅ Step definitions are properly configured

### Reports Go To:
- ✅ `reports/` folder (Cucumber HTML + JSON)
- ✅ `allure-results/` folder (Allure raw data)
- ✅ `target/allure-report/` (Final Allure HTML report)

### If Reports Are Missing:
1. ✅ Check if tests actually ran
2. ✅ Verify `reports/` folder exists
3. ✅ Run: `mvn clean test`
4. ✅ Wait for test execution to complete

---

## 🛠️ Troubleshooting

### Reports folder doesn't exist
```bash
# Create it manually
mkdir reports
mkdir allure-results

# Then run tests
mvn clean test
```

### HTML report is blank/empty
```bash
# Clean and rebuild
mvn clean test -Dtags="@smoke"

# Check if tests passed
# (Failing tests might not show properly)
```

### Allure command not found
```bash
# Install allure if not present
# Download from: https://docs.qameta.io/allure/
# Or use: mvn allure:report (uses Maven plugin)
```

### Can't open HTML file
1. Copy full path: `D:\Automation\Framework_OrangeHRMS\reports\cucumber-report.html`
2. Paste in browser address bar
3. Or right-click → "Open with" → Select browser

---

## 📈 Report Quality Tips

1. **Add descriptive scenario names** - Better report readability
2. **Use tags** - Filter reports by features
3. **Add step descriptions** - Clear test flow
4. **Include expected vs actual** - Better failure analysis
5. **Capture screenshots on failure** - Visual debugging

---

## 💾 Backup Reports

```bash
# Save reports with date
xcopy reports reports-backup-2026-02-09 /I /Y
xcopy allure-results allure-backup-2026-02-09 /I /Y
```

---

## ✨ Quick Reference

| Task | Command |
|------|---------|
| Run tests | `mvn test` |
| Run @smoke tests | `mvn test -Dtags="@smoke"` |
| View Cucumber Report | Open `reports/cucumber-report.html` |
| Generate Allure Report | `mvn allure:report` |
| View Allure in browser | `mvn allure:serve` |
| Clean everything | `mvn clean` |
| Full execution | `mvn clean test && mvn allure:serve` |

---

**Reports are working! Just open the HTML files and explore! 🎉**
