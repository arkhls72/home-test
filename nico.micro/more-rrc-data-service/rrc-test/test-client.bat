@echo off

setlocal

set BASE=%~p0

rem ----------------------------------------
rem Test against api server
rem ----------------------------------------

call mvn -Dtest=com.home.digital.rest.client.TestContact test
call mvn -Dtest=com.home.digital.rest.client.TestStepUp test

rem --------------------------------------------------
rem done
rem --------------------------------------------------

cd %BASE%

rem if ERRORLEVEL 1 pause

endlocal

rem pause


