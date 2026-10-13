@echo off
setlocal
cd /d "%~dp0"
if not exist out mkdir out
javac -encoding UTF-8 -d out src\main\java\edu\gastos\eventos\*.java
if errorlevel 1 (
    echo No se pudo compilar. Comprueba que instalaste el JDK 11 o superior.
    pause
    exit /b 1
)
java -cp out edu.gastos.eventos.Aplicacion
if errorlevel 1 (
    echo Ocurrio un error al ejecutar la aplicacion.
    pause
    exit /b 1
)
endlocal
