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

set DQO_HOME=%~dp0home

if "%cd%\" == "%~dp0" (
    set DQO_USER_HOME=%~dp0userhome
    if not exist "%~dp0userhome" (
        mkdir "%~dp0userhome"
    )
) else (
    set DQO_USER_HOME=.
)
