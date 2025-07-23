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

pushd "%~dp0"

call mvnw.cmd clean -f pom.xml
call mvnw.cmd package -DskipTests -Pbuild-with-jdk-11 -Pdisable-duckdb-extensions-download -Prun-npm -Djavax.net.ssl.trustStoreType=WINDOWS-ROOT

popd
