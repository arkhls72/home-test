@echo off

setlocal

set BASE=%~p0

rem ----------------------------------------
rem Standalone adapter tests
rem ----------------------------------------

rem call mvn -Dtest=<full.path.to.test.class> test

rem --------------------------------------------------
rem done
rem --------------------------------------------------

cd %BASE%

rem if ERRORLEVEL 1 pause

endlocal

rem pause


