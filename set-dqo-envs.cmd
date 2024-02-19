@echo off
@REM
@REM Copyright © 2021-2024 DQOps (support@dqops.com)
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

set DQO_HOME=%~dp0home

if "%cd%\" == "%~dp0" (
    set DQO_USER_HOME=%~dp0userhome
    if not exist "%~dp0userhome" (
        mkdir "%~dp0userhome"
    )
) else (
    set DQO_USER_HOME=.
)
