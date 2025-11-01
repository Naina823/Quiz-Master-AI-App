@echo off
title Check Libraries Status
color 0E

echo.
echo ========================================
echo   CHECKING LIBRARY STATUS
echo ========================================
echo.

if not exist lib (
    echo [ERROR] lib folder does not exist!
    echo Run download-libs.bat first.
    echo.
    pause
    exit /b 1
)

echo Checking for required JAR files...
echo.

set MISSING=0

if exist "lib\mysql-connector-java-8.0.33.jar" (
    echo [OK] mysql-connector-java-8.0.33.jar
) else (
    echo [MISSING] mysql-connector-java-8.0.33.jar
    set MISSING=1
)

if exist "lib\flatlaf-3.2.5.jar" (
    echo [OK] flatlaf-3.2.5.jar
) else (
    echo [MISSING] flatlaf-3.2.5.jar
    set MISSING=1
)

if exist "lib\gson-2.10.1.jar" (
    echo [OK] gson-2.10.1.jar
) else (
    echo [MISSING] gson-2.10.1.jar
    set MISSING=1
)

if exist "lib\okhttp-4.12.0.jar" (
    echo [OK] okhttp-4.12.0.jar
) else (
    echo [MISSING] okhttp-4.12.0.jar
    set MISSING=1
)

if exist "lib\okio-jvm-3.6.0.jar" (
    echo [OK] okio-jvm-3.6.0.jar
) else (
    echo [MISSING] okio-jvm-3.6.0.jar
    set MISSING=1
)

if exist "lib\kotlin-stdlib-1.9.10.jar" (
    echo [OK] kotlin-stdlib-1.9.10.jar
) else (
    echo [MISSING] kotlin-stdlib-1.9.10.jar
    set MISSING=1
)

echo.
echo ========================================

if %MISSING%==1 (
    echo [WARNING] Some libraries are missing!
    echo Please run: download-libs.bat
) else (
    echo [SUCCESS] All libraries are present!
    echo You can run: run-quiz-app.bat
)

echo ========================================
echo.

echo Library folder contents:
dir /b lib\*.jar

echo.
pause