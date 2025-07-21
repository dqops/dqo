@REM
@REM Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
@REM
@REM This file is licensed under the Business Source License 1.1,
@REM which can be found in the root directory of this repository.
@REM
@REM Change Date: This file will be licensed under the Apache License, Version 2.0,
@REM four (4) years from its last modification date.
@REM

@echo off

if exist "%~dp0target\test-classes\com\dqops\connectors\testcontainers\SetTestContainersUserConfigProperty.class" (
    call "%~dp0..\mvnw.cmd" exec:java -Dexec.classpathScope=test -Dexec.includeProjectDependencies=true -Dexec.mainClass=com.dqops.connectors.testcontainers.SetTestContainersUserConfigProperty -Dexec.args="%*"
) else (
    echo ERROR: Main class not found, compile the whole project with Maven first
)
