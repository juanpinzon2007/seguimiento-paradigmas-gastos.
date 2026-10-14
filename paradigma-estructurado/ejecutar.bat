@echo off
rem Uso:  ejecutar.bat         -> compila y ejecuta el Control de Gastos
rem       ejecutar.bat test    -> compila y ejecuta las pruebas
cd /d "%~dp0"
chcp 1252 >nul
if not exist out mkdir out
if /i "%1"=="test" goto pruebas

javac -encoding UTF-8 -d out src\main\java\edu\gastos\estructurado\Aplicacion.java
if errorlevel 1 goto fin
java -cp out edu.gastos.estructurado.Aplicacion
goto fin

:pruebas
javac -encoding UTF-8 -d out src\main\java\edu\gastos\estructurado\Aplicacion.java src\test\java\edu\gastos\estructurado\PruebaGastos.java
if errorlevel 1 goto fin
java -cp out edu.gastos.estructurado.PruebaGastos

:fin
