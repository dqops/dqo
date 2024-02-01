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

if exist "%~dp0dqops\pom.xml" (
    "%JAVA_HOME%\bin\javac" --version > nul  2> nul
    if ERRORLEVEL 1 (
        echo Java JDK is not installed. Make sure that your JAVA_HOME points to a JDK 11 or never installation. Java JRE is not enough to compile DQO
        exit /b 1
    )

    if not exist "%~dp0dqops\target\dqo-dqops-%DQO_VERSION%.jar" (
        if exist "%~dp0dqops" (
            pushd "%~dp0"
            call "%~dp0\mvnw.cmd" package -DskipTests -Pbuild-with-jdk-11 -Prun-npm -f "%~dp0\pom.xml"
            popd
            if ERRORLEVEL 1 (
                echo DQO failed to compile
                exit /b 1
            )
        ) else (
            echo DQO jar not found
            exit /b 1
        )
    )

    if not "x%DQO_PREPEND_CLASSES%"=="x" (
        set DQO_LAUNCH_CLASSPATH=%DQO_PREPEND_CLASSES%;%~dp0dqops\target\dqo-dqops-%DQO_VERSION%.jar;%~dp0lib\target\output\dqo-lib-%DQO_VERSION%\jars\*
    ) else (
        set DQO_LAUNCH_CLASSPATH=%~dp0dqops\target\dqo-dqops-%DQO_VERSION%.jar;%~dp0lib\target\output\dqo-lib-%DQO_VERSION%\jars\*
    )
) else (
    if not exist "%DQO_HOME%\jars" (
        echo DQO_HOME does not have a %DQO_HOME%\jars folder
        exit /b 1
    )

    if not "x%DQO_PREPEND_CLASSES%"=="x" (
        set DQO_LAUNCH_CLASSPATH=%DQO_PREPEND_CLASSES%;%DQO_HOME%\jars\*
    ) else (
        set DQO_LAUNCH_CLASSPATH=%DQO_HOME%\jars\*
    )
)

if not "x%HADOOP_HOME%"=="x" (
  set HADOOP_HOME=%DQO_HOME%
)
