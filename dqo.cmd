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

set DQO_VERSION=1.1.0

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
