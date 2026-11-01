@echo off
title Shopping Cart System - Console Demo
cd /d "%~dp0"
echo Compiling Java files...
javac *.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
echo.
echo Running Shopping Cart System...
echo ========================================================
java Main
echo ========================================================
echo.
pause

