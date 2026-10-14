@echo off
setlocal
cd /d "%~dp0"

if not exist out\test mkdir out\test

javac -encoding UTF-8 -d out\test src\main\java\edu\gastos\funcional\*.java src\test\java\edu\gastos\funcional\*.java
if errorlevel 1 exit /b 1

java -cp out\test edu.gastos.funcional.PruebaGestorFuncionalGastos
endlocal
