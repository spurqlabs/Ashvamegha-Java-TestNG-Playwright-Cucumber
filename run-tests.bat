@echo off
REM Test Execution Script for Orange HRM Automation Framework
REM This script runs tests and generates Allure reports

cd /d D:\Automation\Framework_OrangeHRMS

echo.
echo ============================================
echo OrangeHRM Automation Test Suite
echo ============================================
echo.
echo Starting Maven wrapper...
echo.

REM Run tests with Maven wrapper
powershell -NoProfile -ExecutionPolicy Bypass -Command "& '.\mvnw.ps1' clean test -Dtags='@smoke'"

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Tests failed!
    pause
    exit /b 1
)

echo.
echo ============================================
echo Tests completed successfully!
echo ============================================
echo.
echo Generating and serving Allure report...
echo.

REM Generate and serve Allure report
powershell -NoProfile -ExecutionPolicy Bypass -Command "& '.\mvnw.ps1' allure:serve"

echo.
echo Report closed.
echo.
pause
