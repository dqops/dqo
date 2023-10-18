@echo off
@REM
@REM Copyright © 2021 DQOps (support@dqops.com)
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM     http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
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
