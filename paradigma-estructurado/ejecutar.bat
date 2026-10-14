@echo off
rem Compila y ejecuta el Control de Gastos (paradigma estructurado)
cd /d "%~dp0"
chcp 1252 >nul
if not exist out mkdir out
javac -encoding UTF-8 -d out src\main\java\edu\gastos\estructurado\Aplicacion.java
if errorlevel 1 goto fin
java -cp out edu.gastos.estructurado.Aplicacion
:fin
