# 🎬 Quick Guide - View Screenshots in Reports

## ✅ Screenshots Are Now Enabled!

Your test scenarios now capture screenshots:
- ✅ For each step
- ✅ On success (green checkmark)
- ✅ On failure (error state)
- ✅ Automatically attached to Allure Report
- ✅ Saved to screenshots/ folder

---

## 🚀 Quick Start - View Screenshots NOW

### Step 1: Run Your Tests
```bash
mvn clean test -Dtags="@smoke"
```

### Step 2: Generate Allure Report
```bash
mvn allure:serve
```

### Step 3: View Screenshots
- Browser opens automatically
- Click on any scenario
- Scroll down to "Attachments" section
- View all step screenshots!

---

## 📸 View Screenshots in 3 Ways

### Way 1: Allure Dashboard (BEST)
```bash
mvn allure:serve
```
**Then:**
1. Click on test scenario
2. Scroll to "Attachments"
3. See all screenshots with step names
4. Click to expand/download

**Shows:**
- ✅ Step name
- ✅ Screenshot image
- ✅ Timestamp
- ✅ Success/error indicator

### Way 2: File System
```
Navigate to: D:\Automation\Framework_OrangeHRMS\screenshots\

Folders:
├── steps/     ← Success screenshots (each step)
├── errors/    ← Error state screenshots
└── failures/  ← Scenario failure screenshots
```

**Open directly:**
1. Go to screenshots/ folder
2. Double-click any PNG file
3. View in image viewer

### Way 3: Cucumber HTML Report
```
Open: D:\Automation\Framework_OrangeHRMS\reports\cucumber-report.html
```

**Then:**
1. Find scenario
2. Expand step details
3. View embedded attachments
4. See screenshots inline

---

## 📊 What Screenshots Show

### For Each Step:
✅ What the page looked like
✅ All form inputs entered
✅ Buttons and elements visible
✅ Error messages (if any)
✅ Navigation state

### Organized By:
- **success/** → Step passed
- **error/** → Step had exception
- **failures/** → Scenario assertion failed

---

## 🎯 Example Workflow

### Run and View in One Command:
```bash
# Run tests and immediately view report
mvn clean test -Dtags="@smoke" && mvn allure:serve
```

**Result:**
1. Tests run (screenshots captured)
2. Allure report generates
3. Browser opens automatically
4. You see all screenshots!

---

## 📁 Screenshot File Naming

### Success Screenshots:
```
user_opens_the_OrangeHRM_application_2026-02-09_11-21-45-123.png
```

### Error Screenshots:
```
user_enters_valid_username_and_password_ERROR_2026-02-09_11-21-47-999.png
```

### Failure Screenshots:
```
Login_with_valid_credentials_FAILURE_2026-02-09_11-21-50-789.png
```

**Format:** `{stepName}_{TIMESTAMP}.png`

---

## 🔍 Finding Specific Screenshots

### By Step Name:
```bash
# In file system, search for step name
D:\Automation\Framework_OrangeHRMS\screenshots\steps\

# Find files containing step name
```

### By Time:
```bash
# Look at timestamp in filename
# Format: 2026-02-09_HH-mm-ss-milliseconds
# Example: 2026-02-09_11-21-45-123 means 11:21:45.123 AM
```

### By Status:
```bash
# Success:  screenshots/steps/
# Error:    screenshots/errors/
# Failure:  screenshots/failures/
```

---

## 💾 Download/Share Screenshots

### From Allure Report:
1. Open Allure dashboard
2. Click scenario
3. Find screenshot in Attachments
4. Right-click → "Save image as"
5. Share the file

### From File System:
1. Go to `screenshots/` folder
2. Right-click any PNG
3. "Copy"
4. Paste/share elsewhere

---

## 📈 Screenshot Statistics

### Per Test Run:
```
✅ Number of steps: N
✅ Screenshots per step: 1
✅ Total screenshots: N
✅ Total size: ~2-3 MB per scenario
```

### Example (6-step scenario):
```
Step 1: user_opens_... → Screenshot 1
Step 2: user_enters_... → Screenshot 2
Step 3: user_clicks_... → Screenshot 3
Step 4: dashboard_... → Screenshot 4
Step 5: user_navigates_... → Screenshot 5
Step 6: candidates_... → Screenshot 6

Total: 6 screenshots per scenario
```

---

## ✨ Pro Tips

### Tip 1: Multiple Test Runs
- Each run creates new screenshots
- Old screenshots preserved
- Timestamps help identify sequence
- Easy comparison across runs

### Tip 2: Allure History
```bash
mvn allure:serve
```
- Shows test history
- Compares screenshots over time
- Tracks changes visually
- Identifies flaky tests

### Tip 3: Screenshot Organization
```bash
# Daily backup
copy screenshots screenshots-2026-02-09 /Y
```

### Tip 4: View in IDE
- Right-click PNG in file explorer
- "Open with" → Image viewer
- Or drag into browser tab

---

## 🎯 Common Tasks

### Task 1: View Latest Failure Screenshot
```bash
# Go to screenshots/failures/
# Find most recent file by timestamp
# Open with image viewer
```

### Task 2: Compare Success vs Error
```bash
# Open success screenshot from: screenshots/steps/
# Open error screenshot from: screenshots/errors/
# View side-by-side in image viewer
```

### Task 3: Share Screenshots with Team
```bash
# Option 1: Run mvn allure:serve
#          Share the link/HTML file

# Option 2: Zip the screenshots folder
#          Share screenshots.zip

# Option 3: Take individual PNGs from screenshots/
#          Attach to email/ticket
```

### Task 4: Archive Old Screenshots
```bash
# Backup before running new tests
copy screenshots screenshots-archive-02-09 /Y

# Or organize by date
mkdir screenshots\2026-02-09
move screenshots\*.png screenshots\2026-02-09\
```

---

## 🔗 Quick Links

**Generate Allure Report:**
```bash
mvn allure:serve
```

**View HTML Report:**
```bash
Open: D:\Automation\Framework_OrangeHRMS\reports\cucumber-report.html
```

**Access Screenshots Folder:**
```bash
Open: D:\Automation\Framework_OrangeHRMS\screenshots\
```

---

## 📋 Checklist

- ✅ Run tests: `mvn test`
- ✅ Generate Allure: `mvn allure:serve`
- ✅ View in browser (opens automatically)
- ✅ Click scenario to expand
- ✅ Scroll to "Attachments"
- ✅ View screenshots!

---

## 🎉 You're All Set!

Screenshots are:
- ✅ Automatically captured
- ✅ Attached to Allure
- ✅ Saved to file system
- ✅ Ready to view
- ✅ Easy to share

**Run your tests and view the screenshots now!** 📸

---

**Next Steps:**
1. Run: `mvn test`
2. View: `mvn allure:serve`
3. Explore: Click scenario → Attachments
4. Enjoy: Visual test execution! 🎬
