@echo off

echo Disables reusing testcontainers for databases
echo When docker containers with databases started by testcontainers are reusable, the containers are preserved
echo between test runs. Also the databases could be queried using external database tools to verify
echo the test data and simplify the development of additional data quality checks.

call "%~dp0set_testcontainers_property.cmd" testcontainers.reuse.enable false
