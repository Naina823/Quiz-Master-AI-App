@echo off
title Download Quiz App Libraries
color 0B

echo.
echo ========================================
echo   DOWNLOADING REQUIRED LIBRARIES
echo ========================================
echo.

REM Create lib directory
mkdir lib 2>nul

echo [1/5] Downloading MySQL Connector...
curl -L -o lib\mysql-connector-java-8.0.33.jar https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar

echo [2/5] Downloading FlatLaf (Modern UI)...
curl -L -o lib\flatlaf-3.2.5.jar https://repo1.maven.org/maven2/com/formdev/flatlaf/3.2.5/flatlaf-3.2.5.jar

echo [3/5] Downloading Gson (JSON Parser)...
curl -L -o lib\gson-2.10.1.jar https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar

echo [4/5] Downloading OkHttp (HTTP Client)...
curl -L -o lib\okhttp-4.12.0.jar https://repo1.maven.org/maven2/com/squareup/okhttp3/okhttp/4.12.0/okhttp-4.12.0.jar

echo [5/6] Downloading Okio (OkHttp Dependency)...
curl -L -o lib\okio-jvm-3.6.0.jar https://repo1.maven.org/maven2/com/squareup/okio/okio-jvm/3.6.0/okio-jvm-3.6.0.jar

echo [6/6] Downloading Kotlin Standard Library (Okio Dependency)...
curl -L -o lib\kotlin-stdlib-1.9.10.jar https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/1.9.10/kotlin-stdlib-1.9.10.jar

echo.
echo ========================================
echo Verifying downloads...
echo ========================================
echo.

if exist "lib\mysql-connector-java-8.0.33.jar" (
    echo [OK] MySQL Connector downloaded
) else (
    echo [FAILED] MySQL Connector
)

if exist "lib\flatlaf-3.2.5.jar" (
    echo [OK] FlatLaf downloaded
) else (
    echo [FAILED] FlatLaf
)

if exist "lib\gson-2.10.1.jar" (
    echo [OK] Gson downloaded
) else (
    echo [FAILED] Gson
)

if exist "lib\okhttp-4.12.0.jar" (
    echo [OK] OkHttp downloaded
) else (
    echo [FAILED] OkHttp
)

if exist "lib\okio-jvm-3.6.0.jar" (
    echo [OK] Okio downloaded
) else (
    echo [FAILED] Okio
)

echo.
echo ========================================
echo Download Complete!
echo You can now run: run-quiz-app.bat
echo ========================================
echo.

pause