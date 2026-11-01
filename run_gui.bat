@echo off
title Shopping Cart System - Desktop GUI
cd /d "%~dp0"
echo Compiling Java files...
javac *.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
echo Launching GUI...
java ShoppingAppGUI

