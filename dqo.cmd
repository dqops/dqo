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

set DQO_VERSION=1.11.7

rem Configure local development environment overrides
if exist "%~dp0set-dqo-envs.cmd" (
    call "%~dp0set-dqo-envs.cmd"
    if ERRORLEVEL 1 (
       echo DQOps cannot be started
       exit /b 1
    )
)

rem Figure out where DQO is installed
call "%~dp0find-dqo-home.cmd"
if ERRORLEVEL 1 (
   echo DQOps cannot be started
   exit /b 1
)

rem Configure class paths
call "%~dp0find-dqo-classpath.cmd"

if ERRORLEVEL 1 (
   echo DQOps cannot be started
   exit /b 1
)

call "%~dp0launch-dqo.cmd" %*
