@echo off
@REM
@REM Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
@REM
@REM This file is licensed under the Business Source License 1.1,
@REM which can be found in the root directory of this repository.
@REM
@REM Change Date: This file will be licensed under the Apache License, Version 2.0,
@REM four (4) years from its last modification date.
@REM

rem Only attempt to find DQO_HOME if it is not set.
if "x%DQO_HOME%"=="x" (
    set DQO_HOME=%~dp0..
)

if "x%DQO_USER_HOME%"=="x" (
    set DQO_USER_HOME=.
)

if "x%DQO_JAVA_OPTS%"=="x" (
    set DQO_JAVA_OPTS=-XX:MaxRAMPercentage=60.0 -Djavax.net.ssl.trustStoreType=WINDOWS-ROOT
)

if not exist "%DQO_USER_HOME%" (
    mkdir "%DQO_USER_HOME%"
)

rem Figure out where java is.
set DQO_RUNNER=java
if not "x%JAVA_HOME%"=="x" (
  set DQO_RUNNER=%JAVA_HOME%\bin\java
) else (
  where /q "%DQO_RUNNER%"
  if ERRORLEVEL 1 (
    echo Java not found and JAVA_HOME environment variable is not set.
    echo Install Java 17 or newer and set JAVA_HOME to point to the Java installation directory.
    exit /b 1
  )
)
