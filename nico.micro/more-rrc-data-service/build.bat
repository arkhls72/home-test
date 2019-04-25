@echo off

setlocal

set BASE=%~p0

set SKIPTESTS=-Dmaven.test.skip=true

set JAVA_=C:\Java\jdk1.7.0_79
set M2_=C:\maven.3.0.5
set PATH=%PATH%;%M2_%\bin

echo.
echo JAVA_=%JAVA_%
call mvn -version
echo.

rem --------------------------------------------------
rem clean install all
rem --------------------------------------------------

call mvn %SKIPTESTS% clean install 

rem --------------------------------------------------
rem done
rem --------------------------------------------------

cd %BASE%

rem if ERRORLEVEL 1 pause

endlocal

pause

