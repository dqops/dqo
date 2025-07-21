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

"%DQO_RUNNER%" %DQO_JAVA_OPTS% --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/java.util.concurrent=ALL-UNNAMED -Djava.library.path="%DQO_HOME%\bin" -cp %DQO_LAUNCH_CLASSPATH% com.dqops.cli.CliApplication %*
