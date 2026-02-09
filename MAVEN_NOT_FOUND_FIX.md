# ⚠️ ISSUE: Maven Not Found in System PATH

## Problem
```
'mvn' is not recognized as an internal or external command
```

## Root Cause
Maven is not installed on your system OR not added to the system PATH environment variable.

---

## ✅ SOLUTION OPTIONS

### Option 1: Install Maven (Recommended)

**Step 1: Download Maven**
1. Go to: https://maven.apache.org/download.cgi
2. Download: apache-maven-3.9.6-bin.zip (latest version)
3. Extract to: C:\maven\ (or any directory)

**Step 2: Add to System PATH**
1. Right-click "This PC" → Properties
2. Click "Advanced system settings"
3. Click "Environment Variables"
4. Under "System variables", click "New"
   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\maven` (or where you extracted)
5. Find "Path" variable → Click "Edit"
6. Click "New" → Add: `%MAVEN_HOME%\bin`
7. Click OK → OK → OK
8. Restart PowerShell/Command Prompt

**Step 3: Verify Installation**
```powershell
mvn -v
```

Should show Maven version.

---

### Option 2: Use Maven Wrapper (If Project Has It)

Check if your project has `mvnw`:
```powershell
dir D:\Automation\Framework_OrangeHRMS\mvnw*
```

If it exists:
```powershell
cd D:\Automation\Framework_OrangeHRMS
.\mvnw clean test -Dtags="@smoke"
.\mvnw allure:serve
```

---

### Option 3: Use IDE Maven Plugin

If using IntelliJ IDEA:
1. Right-click project → "Run Maven Goal"
2. Or: View → Tool Windows → Maven
3. Navigate to: clean → test
4. Run directly from IDE

---

## 🎯 Check If Maven Wrapper Exists

```powershell
cd D:\Automation\Framework_OrangeHRMS
ls -la | findstr mvnw
```

If you see `mvnw` or `mvnw.cmd`, use:
```powershell
.\mvnw clean test -Dtags="@smoke" ; .\mvnw allure:serve
```

---

## ✅ Quick Fix (Using Maven Wrapper)

If the wrapper exists in your project:

```powershell
cd D:\Automation\Framework_OrangeHRMS
.\mvnw clean test -Dtags="@smoke" ; .\mvnw allure:serve
```

---

## 📋 Complete Setup Guide

### Step 1: Check for Maven Wrapper
```powershell
cd D:\Automation\Framework_OrangeHRMS
Test-Path "mvnw.cmd"
```

### Step 2a: If Wrapper Exists (Easiest)
```powershell
.\mvnw clean test -Dtags="@smoke" ; .\mvnw allure:serve
```

### Step 2b: If No Wrapper (Install Maven)
1. Download Maven from https://maven.apache.org/
2. Extract to C:\maven
3. Add to PATH (see instructions above)
4. Restart terminal
5. Run: `mvn clean test -Dtags="@smoke" ; mvn allure:serve`

---

## 🔧 Verify After Setup

### Test Maven Installation:
```powershell
mvn -v
```

### Should Show:
```
Apache Maven 3.9.6
Maven home: C:\maven
Java version: 21.x.x
```

### Then Run Tests:
```powershell
mvn clean test -Dtags="@smoke" ; mvn allure:serve
```

---

## 💡 Pro Tip: Use PowerShell Profile

Add to PowerShell profile to auto-add Maven:
```powershell
# Edit profile
notepad $PROFILE

# Add these lines:
$env:MAVEN_HOME = "C:\maven"
$env:PATH += ";$env:MAVEN_HOME\bin"
```

---

## ✅ Next Steps

### If You Have Maven Wrapper:
```powershell
cd D:\Automation\Framework_OrangeHRMS
.\mvnw clean test -Dtags="@smoke" ; .\mvnw allure:serve
```

### If You Don't Have Maven:
1. Install Maven (see instructions above)
2. Add to PATH
3. Restart PowerShell
4. Run: `mvn clean test -Dtags="@smoke" ; mvn allure:serve`

---

**Let me know which option you have or need help with!** 🚀
