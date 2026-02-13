# Parallel Test Execution Commands - Framework_OrangeHRMS
# Run this script from the project root directory
# Usage: .\run-parallel-tests.ps1

param(
    [ValidateSet('smoke-2', 'smoke-4', 'all-2', 'all-4', 'clean-smoke', 'allure', '1', '2', '3', '4', '5', '6')]
    [string]$Option = ""
)

# Determine the root directory
$rootDir = Split-Path -Parent $MyInvocation.MyCommandPath
Set-Location $rootDir

Write-Host "====================================================" -ForegroundColor Green
Write-Host "Parallel Test Execution - Framework_OrangeHRMS" -ForegroundColor Green
Write-Host "====================================================" -ForegroundColor Green
Write-Host ""

if ([string]::IsNullOrEmpty($Option)) {
    Write-Host "Choose an option:" -ForegroundColor Cyan
    Write-Host "1. Run all @smoke tests in parallel (2 threads)" -ForegroundColor White
    Write-Host "2. Run all @smoke tests in parallel (4 threads)" -ForegroundColor White
    Write-Host "3. Run all tests in parallel (2 threads)" -ForegroundColor White
    Write-Host "4. Run all tests in parallel (4 threads)" -ForegroundColor White
    Write-Host "5. Clean and run @smoke tests" -ForegroundColor White
    Write-Host "6. Run tests and generate Allure report" -ForegroundColor White
    Write-Host ""
    $Option = Read-Host "Enter your choice (1-6)"
}

switch ($Option) {
    { $_ -eq "1" -or $_ -eq "smoke-2" } {
        Write-Host "Running @smoke tests with 2 threads..." -ForegroundColor Yellow
        & .\mvnw.ps1 clean test -Dtest=TestRunner
        break
    }

    { $_ -eq "2" -or $_ -eq "smoke-4" } {
        Write-Host "Running @smoke tests with 4 threads..." -ForegroundColor Yellow
        & .\mvnw.ps1 clean test -Dtest=TestRunner -Djunit.jupiter.execution.parallel.config.fixed.parallelism=4
        break
    }

    { $_ -eq "3" -or $_ -eq "all-2" } {
        Write-Host "Running all tests with 2 threads..." -ForegroundColor Yellow
        Write-Host "Note: Modify TestRunner.java to remove @smoke tag filter" -ForegroundColor Gray
        & .\mvnw.ps1 clean test -Dtest=TestRunner
        break
    }

    { $_ -eq "4" -or $_ -eq "all-4" } {
        Write-Host "Running all tests with 4 threads..." -ForegroundColor Yellow
        & .\mvnw.ps1 clean test -Dtest=TestRunner -Djunit.jupiter.execution.parallel.config.fixed.parallelism=4
        break
    }

    { $_ -eq "5" -or $_ -eq "clean-smoke" } {
        Write-Host "Cleaning and running @smoke tests with 2 threads..." -ForegroundColor Yellow
        & .\mvnw.ps1 clean
        & .\mvnw.ps1 test -Dtest=TestRunner
        break
    }

    { $_ -eq "6" -or $_ -eq "allure" } {
        Write-Host "Running tests and generating Allure report..." -ForegroundColor Yellow
        & .\mvnw.ps1 clean test -Dtest=TestRunner
        Write-Host ""
        Write-Host "Generating Allure report..." -ForegroundColor Cyan
        & .\mvnw.ps1 allure:serve
        break
    }

    default {
        Write-Host "Invalid choice!" -ForegroundColor Red
        exit 1
    }
}

Write-Host ""
Write-Host "Test execution completed!" -ForegroundColor Green
Write-Host ""

# Wait for user input if running interactively
if ($PSInteractive) {
    Read-Host "Press Enter to exit"
}
