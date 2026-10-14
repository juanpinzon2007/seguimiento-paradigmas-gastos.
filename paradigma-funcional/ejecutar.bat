@echo off
setlocal
cd /d "%~dp0"

if not exist out\main mkdir out\main

javac -encoding UTF-8 -d out\main src\main\java\edu\gastos\funcional\*.java
if errorlevel 1 exit /b 1

java -cp out\main edu.gastos.funcional.MainFuncional
endlocal
