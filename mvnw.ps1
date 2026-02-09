# Maven Wrapper for PowerShell
param(
    [Parameter(ValueFromRemainingArguments=$true)]
    [string[]]$MavenArgs
)

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$pomFile = Join-Path $scriptDir "pom.xml"

if (-not (Test-Path $pomFile)) {
    Write-Host "Error: pom.xml not found in $scriptDir" -ForegroundColor Red
    exit 1
}

# Check if Maven is in PATH
try {
    $mvnPath = (Get-Command mvn -ErrorAction Stop).Path
    Write-Host "Using Maven from PATH: $mvnPath" -ForegroundColor Green
    & $mvnPath $MavenArgs
    exit $LASTEXITCODE
} catch {
    Write-Host "Maven not found in PATH. Attempting to download locally..." -ForegroundColor Yellow
}

# Setup local Maven
$mavenWrapper = Join-Path $scriptDir ".mvn\wrapper"
$mavenHome = Join-Path $mavenWrapper "apache-maven-3.9.6"
$mvnCmd = Join-Path $mavenHome "bin\mvn.cmd"

if (-not (Test-Path $mvnCmd)) {
    Write-Host "Downloading Maven 3.9.6..." -ForegroundColor Cyan

    # Create wrapper directory
    if (-not (Test-Path $mavenWrapper)) {
        New-Item -ItemType Directory -Path $mavenWrapper -Force | Out-Null
    }

    # Download Maven
    $zipFile = Join-Path $mavenWrapper "maven.zip"
    $downloadUrl = "https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip"

    try {
        [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
        (New-Object System.Net.WebClient).DownloadFile($downloadUrl, $zipFile)
        Write-Host "Downloaded successfully. Extracting..." -ForegroundColor Green
        Expand-Archive -Path $zipFile -DestinationPath $mavenWrapper -Force
        Remove-Item $zipFile -Force
        Write-Host "Maven setup complete!" -ForegroundColor Green
    } catch {
        Write-Host "Error downloading Maven: $_" -ForegroundColor Red
        exit 1
    }
}

# Run Maven
if (Test-Path $mvnCmd) {
    Write-Host "Running Maven..." -ForegroundColor Green
    & $mvnCmd $MavenArgs
    exit $LASTEXITCODE
} else {
    Write-Host "Error: Maven executable not found at $mvnCmd" -ForegroundColor Red
    exit 1
}
