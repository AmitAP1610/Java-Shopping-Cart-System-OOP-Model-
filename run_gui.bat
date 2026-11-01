@echo off
title Shopping Cart System - Desktop GUI
cd /d "%~dp0"

echo Compiling Java source files...
if not exist "bin" mkdir "bin"
javac -encoding UTF-8 -d bin -sourcepath src src\com\shoppingcart\Main.java src\com\shoppingcart\ui\ShoppingAppGUI.java
if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Launching Desktop GUI Window...
java -Dfile.encoding=UTF-8 -cp bin com.shoppingcart.ui.ShoppingAppGUI
