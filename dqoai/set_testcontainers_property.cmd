@echo off

if exist "%~dp0target\test-classes\ai\dqo\connectors\testcontainers\SetTestContainersUserConfigProperty.class" (
    call "%~dp0..\mvnw.cmd" exec:java -Dexec.classpathScope=test -Dexec.includeProjectDependencies=true -Dexec.mainClass=ai.dqo.connectors.testcontainers.SetTestContainersUserConfigProperty -Dexec.args="%*"
) else (
    echo ERROR: Main class not found, compile the whole project with Maven first
)
