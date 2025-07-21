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

if exist "%~dp0dqops\pom.xml" (
    "%JAVA_HOME%\bin\javac" --version > nul  2> nul
    if ERRORLEVEL 1 (
        echo Java JDK is not installed. Make sure that your JAVA_HOME points to a JDK 11 or never installation. Java JRE is not enough to compile DQOps
        exit /b 1
    )

    if not exist "%~dp0dqops\target\dqo-dqops-%DQO_VERSION%.jar" (
        if exist "%~dp0dqops" (
            pushd "%~dp0"
            call "%~dp0\mvnw.cmd" package -DskipTests -Pbuild-with-jdk-11 -Pdisable-duckdb-extensions-download -Prun-npm -f "%~dp0\pom.xml"
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
        set DQO_LAUNCH_CLASSPATH="%DQO_PREPEND_CLASSES%;%DQO_USER_HOME%\jars\paid\*;%DQO_USER_HOME%\jars\*;%DQO_USER_HOME%\jars\;%~dp0dqops\target\dependencies\*;%~dp0dqops\target\dqo-dqops-%DQO_VERSION%.jar.original"
    ) else (
        set DQO_LAUNCH_CLASSPATH="%DQO_USER_HOME%\jars\paid\*;%DQO_USER_HOME%\jars\*;%DQO_USER_HOME%\jars\;%~dp0dqops\target\dependencies\*;%~dp0dqops\target\dqo-dqops-%DQO_VERSION%.jar.original"
    )
) else (
    if not exist "%DQO_HOME%\jars" (
        echo DQO_HOME does not have a %DQO_HOME%\jars folder
        exit /b 1
    )

    if not "x%DQO_PREPEND_CLASSES%"=="x" (
        set DQO_LAUNCH_CLASSPATH="%DQO_PREPEND_CLASSES%;%DQO_USER_HOME%\jars\paid\*;%DQO_USER_HOME%\jars\*;%DQO_USER_HOME%\jars\;%DQO_HOME%\jars\*"
    ) else (
        set DQO_LAUNCH_CLASSPATH="%DQO_USER_HOME%\jars\paid\*;%DQO_USER_HOME%\jars\*;%DQO_USER_HOME%\jars\;%DQO_HOME%\jars\*"
    )
)

if not "x%HADOOP_HOME%"=="x" (
  set HADOOP_HOME=%DQO_HOME%
)

if not "x%AZURE_ENABLE_HTTP_CLIENT_SHARING%"=="x" (
  set AZURE_ENABLE_HTTP_CLIENT_SHARING=true
)
