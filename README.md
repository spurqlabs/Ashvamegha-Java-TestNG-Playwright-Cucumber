# OrangeHRM Test Automation Framework

## 📋 Project Overview

This is a **Behavior-Driven Development (BDD)** test automation framework designed to automate the **Recruitment and Candidate Management** features of OrangeHRM. The framework uses **Playwright** for browser automation and **Cucumber** for writing test scenarios in Gherkin language (Feature files).

**Project Name:** OrangeHRM  
**Version:** 1.0-SNAPSHOT  
**Build Tool:** Maven  
**Java Version:** 21

---

## 🎯 Key Features

- ✅ **BDD Framework** - Cucumber-based test scenarios written in Gherkin language
- 🌐 **Cross-Browser Support** - Playwright supports Chromium, Firefox, and WebKit
- 📸 **Automatic Screenshots** - Screenshots captured automatically on test failures
- 🔧 **Page Object Model** - Well-organized page classes for maintainability
- 📊 **HTML Reports** - Cucumber HTML reports generated after test execution
- 🔐 **Secure Configuration** - Externalized configuration via JSON files
- ⚡ **Explicit Waits** - Custom wait utilities for reliable element interactions
- 🧵 **Thread-Safe** - ThreadLocal implementation for parallel test execution support

---

## 🛠️ Technology Stack

### Core Technologies
| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 | Programming Language |
| **Maven** | Latest | Build & Dependency Management |
| **Playwright** | 1.56.0 | Browser Automation |
| **Cucumber** | 7.14.0 | BDD Framework |
| **JUnit** | 4.13.2 | Test Runner |
| **Jackson** | 2.15.2 | JSON Processing |
| **SLF4J** | 2.0.9 | Logging |
| **Extent Reports** | 5.1.1 | Advanced Reporting |

---

## 📁 Project Structure

```
OrangeHRM/
│
├── pom.xml                          # Maven configuration & dependencies
├── README.md                         # This file
├── src/
│   ├── main/java/
│   │   └── org/example/
│   │       └── Main.java            # Entry point (if applicable)
│   │
│   └── test/
│       ├── java/
│       │   ├── hooks/
│       │   │   └── Hooks.java       # Cucumber @Before & @After hooks
│       │   │
│       │   ├── pages/               # Page Object Model classes
│       │   │   ├── LoginPage.java           # Login page interactions
│       │   │   ├── RecruitmentPage.java     # Recruitment page navigation
│       │   │   ├── AddCandidatePage.java    # Add candidate form
│       │   │   └── CandidateListPage.java   # Candidate list & search
│       │   │
│       │   ├── runner/
│       │   │   └── TestRunner.java  # Cucumber test runner configuration
│       │   │
│       │   ├── stepdefinitions/
│       │   │   └── OrangeHRMSteps.java  # Step definitions for Gherkin steps
│       │   │
│       │   └── utils/               # Utility classes
│       │       ├── PlaywrightFactory.java   # Browser initialization & management
│       │       ├── ConfigReader.java        # Configuration file reader
│       │       ├── CandidateDataReader.java # Test data reader
│       │       ├── LocatorReader.java       # Element locator reader
│       │       ├── WaitUtil.java            # Explicit wait utilities
│       │       ├── ScreenshotUtil.java      # Screenshot capture
│       │       └── JsonUtil.java            # JSON utility functions
│       │
│       └── resources/
│           ├── config/
│           │   ├── config.json             # Application configuration
│           │   ├── candidateData.json      # Test data (candidate details)
│           │   └── locators.json           # Element locators
│           │
│           └── features/
│               └── orangehrm.feature       # BDD test scenarios
│
├── target/                          # Build artifacts
│   ├── cucumber-report.html        # HTML test report
│   ├── classes/
│   │   └── org/example/
│   │       └── Main.class
│   │
│   └── test-classes/               # Compiled test classes
│       ├── pages/
│       ├── stepdefinitions/
│       ├── utils/
│       └── hooks/
│
└── screenshots/                     # Failed test screenshots
    ├── Add_new_candidate_with_all_required_fields_20260205_134115.png
    └── View_candidate_details_20260205_134212.png
```

---

## 🧩 Architecture & Design Patterns

### 1. **Page Object Model (POM)**
Each page in the application has a corresponding Java class:
- `LoginPage.java` - Handles login functionality
- `RecruitmentPage.java` - Navigation to recruitment section
- `AddCandidatePage.java` - Add new candidate form
- `CandidateListPage.java` - Candidate list display & search

**Benefits:**
- ✅ Better maintainability
- ✅ Reduced code duplication
- ✅ Easier test updates when UI changes

### 2. **Hooks (Lifecycle Management)**
`Hooks.java` implements Cucumber hooks:
- `@Before` - Initializes browser before each test scenario
- `@After` - Closes browser and captures screenshots on failure

### 3. **Utility Classes**
- **PlaywrightFactory** - Manages browser instances using ThreadLocal
- **ConfigReader** - Reads configuration from `config.json`
- **CandidateDataReader** - Retrieves test data for candidates
- **LocatorReader** - Loads element locators from `locators.json`
- **WaitUtil** - Provides explicit wait methods
- **ScreenshotUtil** - Captures full-page screenshots on test failure

### 4. **Externalized Configuration**
Configuration is stored in JSON files for easy modification without code changes:
- `config.json` - Base URL and browser settings
- `candidateData.json` - Login and candidate test data
- `locators.json` - UI element locators

---

## 🧪 Test Scenarios

The test suite covers the following scenarios:

### 1. **Navigation**
- Navigate to Recruitment Candidates page
- Verify candidates page is displayed

### 2. **Add New Candidate**
- Click "Add Candidate" button
- Fill candidate form (first name, last name, email, phone)
- Select vacancy from dropdown
- Upload resume
- Enter keywords
- Save candidate
- Verify success message

### 3. **Search Candidate**
- Search candidate by name or email
- Verify search results are displayed

### 4. **View Candidate Details**
- Select a candidate from the list
- Open candidate details
- Verify candidate detail page is displayed

---

## 📋 Configuration Files

### `config.json`
```json
{
  "baseUrl": "https://opensource-demo.orangehrmlive.com/",
  "browser": "chromium"
}
```

### `candidateData.json`
```json
{
  "login": {
    "username": "Admin",
    "password": "admin123"
  },
  "candidate": {
    "firstName": "Rahul",
    "lastName": "Sharma",
    "email": "rahul.sharma@testmail.com",
    "phone": "9876543210",
    "vacancy": "Senior QA Lead",
    "resumePath": "C:/Users/Ashvamegha/Downloads/Demofile.pdf",
    "keywords": "Automation"
  }
}
```

### `locators.json`
Contains CSS/XPath selectors for all UI elements used in the tests.

---

## 🚀 Getting Started

### Prerequisites
- **Java 21** installed and configured
- **Maven 3.8.0+** installed
- **Git** installed (optional, for version control)
- **Windows/Linux/macOS** operating system

### Installation Steps

#### 1. Clone the Repository
```bash
git clone <repository-url>
cd OrangeHRM
```

#### 2. Install Dependencies
```bash
mvn clean install
```
This command will:
- Download all dependencies from `pom.xml`
- Compile the source code
- Install Playwright browsers

#### 3. Update Configuration
Edit `src/test/resources/config/candidateData.json` with your test data:
```json
{
  "login": {
    "username": "Your_Username",
    "password": "Your_Password"
  },
  "candidate": {
    "firstName": "Test",
    "lastName": "Candidate",
    "email": "test@example.com",
    "phone": "1234567890",
    "vacancy": "Available_Vacancy",
    "resumePath": "Path_To_Your_Resume",
    "keywords": "Test"
  }
}
```

---


---

## 📊 Test Reports

### Cucumber HTML Report
After test execution, view the report:
```bash
target/cucumber-report.html
```

### Screenshots
Failed test screenshots are saved in:
```
screenshots/
```

Example filenames:
- `Add_new_candidate_with_all_required_fields_20260205_134115.png`
- `View_candidate_details_20260205_134212.png`

---

#
**Last Updated:** February 5, 2026  
**Project Status:** Active Development  
**Maintainer:** [Your Name/Team]
