@echo off
@REM
@REM Copyright © 2021 DQO.ai (support@dqo.ai)
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

call "%~dp0\mvnw.cmd" clean -f "%~dp0\pom.xml"
call "%~dp0\mvnw.cmd" package -DskipTests -Pbuild-with-jdk-11 -Prun-npm -f "%~dp0\pom.xml"
