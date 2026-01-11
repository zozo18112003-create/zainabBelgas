@echo off
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
set "MAVEN_HOME=C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.12"
set "PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%"

echo --- Environment Check ---
java -version
call mvn -version
echo -------------------------

cd HotelManagementSystem
echo.
echo [1/2] Compiling Project (mvn clean package)...
call mvn clean package
if %errorlevel% neq 0 (
    echo Build failed!
    pause
    exit /b %errorlevel%
)

echo.
echo [2/2] Running Application...
call mvn exec:java -Dexec.mainClass="com.hotelmanagement.main.MainApp"
pause
