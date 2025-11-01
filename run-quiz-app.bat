@echo off
title Quiz Master App
color 0A

echo.
echo ========================================
echo       QUIZ MASTER APPLICATION
echo ========================================
echo.

REM Check if lib folder exists
if not exist lib (
    echo ERROR: lib folder not found!
    echo Please run download-libs.bat first!
    pause
    exit /b 1
)

REM Check if JAR files exist
if not exist lib\mysql-connector-java-8.0.33.jar (
    echo ERROR: MySQL connector not found!
    echo Please run download-libs.bat first!
    pause
    exit /b 1
)

echo Checking MySQL connection...
echo Make sure XAMPP MySQL is running on port 3307!
echo.
pause

echo.
echo Compiling source files...
echo ========================================

REM Create bin directory
mkdir bin 2>nul

REM Compile all Java files INCLUDING AIService.java
javac -encoding UTF-8 -cp "lib\*" -d bin ^
  src\main\java\com\quizapp\Main.java ^
  src\main\java\com\quizapp\config\DatabaseConfig.java ^
  src\main\java\com\quizapp\models\User.java ^
  src\main\java\com\quizapp\models\Question.java ^
  src\main\java\com\quizapp\models\Score.java ^
  src\main\java\com\quizapp\services\AuthService.java ^
  src\main\java\com\quizapp\services\QuizService.java ^
  src\main\java\com\quizapp\services\AIService.java ^
  src\main\java\com\quizapp\ui\components\ModernButton.java ^
  src\main\java\com\quizapp\ui\components\GradientPanel.java ^
  src\main\java\com\quizapp\ui\components\RoundedPanel.java ^
  src\main\java\com\quizapp\ui\frames\WelcomeFrame.java ^
  src\main\java\com\quizapp\ui\frames\LoginFrame.java ^
  src\main\java\com\quizapp\ui\frames\RegisterFrame.java ^
  src\main\java\com\quizapp\ui\frames\DashboardFrame.java ^
  src\main\java\com\quizapp\ui\frames\QuizFrame.java ^
  src\main\java\com\quizapp\ui\frames\ResultFrame.java ^
  src\main\java\com\quizapp\ui\frames\LeaderboardFrame.java

if %errorlevel% neq 0 (
    echo.
    echo ========================================
    echo COMPILATION FAILED!
    echo Check the errors above.
    echo ========================================
    pause
    exit /b 1
)

echo.
echo ========================================
echo Compilation Successful!
echo Starting Quiz Master App...
echo ========================================
echo.

REM Run the application
java -cp "bin;lib\*" com.quizapp.Main

if %errorlevel% neq 0 (
    echo.
    echo ========================================
    echo ERROR: Application failed to start!
    echo ========================================
    pause
)

pause