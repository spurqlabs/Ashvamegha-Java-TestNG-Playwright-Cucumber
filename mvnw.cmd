@REM Simple Maven Wrapper for Windows
@echo off

setlocal enableextensions enabledelayedexpansion

REM Get the directory where this script is located
set SCRIPT_DIR=%~dp0
set SCRIPT_DIR=%SCRIPT_DIR:~0,-1%

REM Check if pom.xml exists in this directory
if not exist "%SCRIPT_DIR%\pom.xml" (
    echo Error: pom.xml not found in %SCRIPT_DIR%
    exit /b 1
)

REM Check if Maven is in PATH
where maven.exe >nul 2>&1
if %errorlevel% == 0 (
    maven %*
    exit /b %errorlevel%
)

REM Check if Maven is installed globally
where mvn.cmd >nul 2>&1
if %errorlevel% == 0 (
    mvn.cmd %*
    exit /b %errorlevel%
)

REM Try Java with embedded Maven
if defined JAVA_HOME (
    REM Maven wrapper will download Maven
    powershell -NoProfile -ExecutionPolicy Bypass -Command "try { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $maven_dir = '%SCRIPT_DIR%\.mvn\wrapper\maven'; if (-not (Test-Path $maven_dir)) { New-Item -ItemType Directory -Path $maven_dir -Force | Out-Null }; $zip_file = '$maven_dir\apache-maven-3.9.6-bin.zip'; if (-not (Test-Path $zip_file)) { Write-Host 'Downloading Maven 3.9.6...'; [Net.ServicePointManager]::SecurityProtocol = 'Tls, Tls11, Tls12'; (New-Object System.Net.WebClient).DownloadFile('https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip', $zip_file); Write-Host 'Extracting Maven...'; Expand-Archive -Path $zip_file -DestinationPath $maven_dir -Force; Remove-Item $zip_file; }; $maven_exe = Get-ChildItem -Path $maven_dir -Filter 'mvn.cmd' -Recurse | Select-Object -First 1; if ($maven_exe) { & $maven_exe.FullName %* } else { Write-Host 'Maven not found'; exit 1 } } catch { Write-Host 'Error: $_'; exit 1 }"
    exit /b %errorlevel%
)

echo Error: Java or Maven not found. Please install Java or Maven.
exit /b 1
