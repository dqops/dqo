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
echo Enables reusing testcontainers for databases
echo When docker containers with databases started by testcontainers are reusable, the containers are preserved
echo between test runs. Also the databases could be queried using external database tools to verify
echo the test data and simplify the development of additional data quality checks.

call "%~dp0set_testcontainers_property.cmd" testcontainers.reuse.enable true
