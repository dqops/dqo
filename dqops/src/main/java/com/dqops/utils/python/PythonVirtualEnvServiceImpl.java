/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.utils.python;

import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoPythonConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A service that detects if a Python virtual environment was configured or sets up a venv.
 */
@Component
@Slf4j
public class PythonVirtualEnvServiceImpl implements PythonVirtualEnvService {
    private final DqoConfigurationProperties dqoConfigurationProperties;
    private final DqoPythonConfigurationProperties pythonConfigurationProperties;
    private final DqoUserConfigurationProperties userConfigurationProperties;
    private PythonVirtualEnv pythonVirtualEnv;

    /**
     * Default injection constructor.
     * @param dqoConfigurationProperties DQO configuration properties.
     * @param pythonConfigurationProperties Python configuration properties.
     * @param userConfigurationProperties User configuration properties.
     */
    @Autowired
    public PythonVirtualEnvServiceImpl(
			DqoConfigurationProperties dqoConfigurationProperties,
			DqoPythonConfigurationProperties pythonConfigurationProperties,
			DqoUserConfigurationProperties userConfigurationProperties) {
        this.dqoConfigurationProperties = dqoConfigurationProperties;
        this.pythonConfigurationProperties = pythonConfigurationProperties;
        this.userConfigurationProperties = userConfigurationProperties;
    }

    /**
     * Returns a path to the VENV.
     * @return Path to the python venv.
     */
    public Path getVEnvPath() {
        Path dqoHomePath = Path.of(this.dqoConfigurationProperties.getHome());
        Path pathToVenv = dqoHomePath.resolve(this.pythonConfigurationProperties.getVenvPath());
        return pathToVenv;
    }

    /**
     * Checks if the python venv exists and is correctly initialized.
     * @return true when the venv is initialized.
     */
    public boolean isVirtualEnvInitialized() {
        if (this.pythonConfigurationProperties.isUseHostPython()) {
            return true; // we assume that the host's python is initialized
        }

        Path pathToVenv = getVEnvPath();

		return Files.exists(pathToVenv) && Files.isDirectory(pathToVenv);
	}

    /**
     * Initializes a python environment.
     */
    public void initializePythonVirtualEnv() {
        String absolutePythonPath = findAbsolutePythonPath();
        if (absolutePythonPath == null) {
            throw new PythonExecutionException("Failed to initialize Python venv: cannot find python interpreter on the PATH");
        }

        try {
            String virtualEnvPath = this.getVEnvPath().toAbsolutePath().toString();
            Runtime runtime = Runtime.getRuntime();

            String[] arguments = {absolutePythonPath, "-m", "venv", virtualEnvPath};
            Process envProcess = runtime.exec(arguments);
            if (!envProcess.waitFor(30000, TimeUnit.MILLISECONDS)) {
                throw new PythonExecutionException("Command has not finished: " + String.join(" ", arguments));
            }
            if (envProcess.exitValue() != 0) {
                String allError = String.join("\n", IOUtils.readLines(envProcess.getErrorStream(), StandardCharsets.UTF_8));
                String allOutput = String.join("\n", IOUtils.readLines(envProcess.getInputStream(), StandardCharsets.UTF_8));
                throw new PythonExecutionException("Command: " + String.join(" ", arguments) + " finished with an error code " + envProcess.exitValue()
                        + ", error output: " + allError + "\noutput: " + allOutput);
            }
        }
        catch (PythonExecutionException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new PythonExecutionException("Failed to initialize Python venv: " + ex.getMessage(), ex);
        }
    }

    /**
     * Returns a python virtual environment parameters required to start the python interpreter inside the virtual environment.
     * @return Virtual environment settings.
     */
    public PythonVirtualEnv getVirtualEnv() {
        if (this.pythonVirtualEnv != null) {
            return this.pythonVirtualEnv;
        }

        if (!isVirtualEnvInitialized()) {
			initializePythonVirtualEnv();
        }

        PythonVirtualEnv pythonVirtualEnv = new PythonVirtualEnv();
        pythonVirtualEnv.setEnableDebugging(this.pythonConfigurationProperties.isEnableDebugging());
        if (!this.pythonConfigurationProperties.isUseHostPython()) {
            Path vEnvPath = getVEnvPath();
            pythonVirtualEnv.setVirtualEnvPath(vEnvPath);
            HashMap<String, String> environmentVariables = pythonVirtualEnv.getEnvironmentVariables();

            if (SystemUtils.IS_OS_WINDOWS) {
                environmentVariables.put("VIRTUAL_ENV", vEnvPath.toString());
                Path binPath = vEnvPath.resolve("Scripts");
                environmentVariables.put("Path", binPath + ";" + System.getenv("Path"));
                Path interpreterPath = findInterpreterPath(binPath);
                pythonVirtualEnv.setPythonInterpreterPath(interpreterPath.toString());
            } else {
                environmentVariables.put("VIRTUAL_ENV", vEnvPath.toString());
                Path binPath = vEnvPath.resolve("bin");
                environmentVariables.put("PATH", binPath + ":" + System.getenv("PATH"));
                Path interpreterPath = findInterpreterPath(binPath);
                pythonVirtualEnv.setPythonInterpreterPath(interpreterPath.toString());
            }

            installDqoHomePipRequirements(pythonVirtualEnv);
            installDqoHomePipDevelopmentRequirements(pythonVirtualEnv);
            installUserHomePipRequirements(pythonVirtualEnv);
        } else {
            String absolutePythonPath = findAbsolutePythonPath();
            if (absolutePythonPath == null) {
                return null;
            }
            pythonVirtualEnv.setPythonInterpreterPath(absolutePythonPath);
            pythonVirtualEnv.setEnvironmentVariables(new LinkedHashMap<>());
        }

        this.pythonVirtualEnv = pythonVirtualEnv;
        return pythonVirtualEnv;
    }

    /**
     * Returns a python's directory path.
     * @param directoryPath Directory path where we are looking for the interpreter.
     * @return Python's directory path
     */
    public Path findInterpreterPath(Path directoryPath) {
        String[] pythonInterpreters = StringUtils.split(pythonConfigurationProperties.getInterpreter(), ',');
        Set<String> filesInFolder = null;

        try {
            try (Stream<Path> filesStream = Files.list(directoryPath)) {
                filesInFolder = filesStream
                        .map(p -> p.getFileName().toString())
                        .collect(Collectors.toSet());
            }
        }
        catch (IOException ioe) {
            // ignore
        }

        for (String pythonInterpreter : pythonInterpreters) {
            Path pythonPath = directoryPath.resolve(pythonInterpreter);
            File pythonInterpreterFile = pythonPath.toFile();

            if (pythonInterpreterFile.exists()) {
                if (pythonInterpreterFile.canExecute()) {
                    return pythonPath;
                }
            } else {
                // Windows 11: python installed from Store
                if (filesInFolder != null && filesInFolder.contains(pythonInterpreter)) {
                    return pythonPath;
                }
            }
        }
        return null;
    }

    /**
     * Returns an absolute python's interpreter path.
     * @return Python's interpreter absolute path
     */
    public String findAbsolutePythonPath() {
        if (!Strings.isNullOrEmpty(this.pythonConfigurationProperties.getInterpreter()) &&
                !this.pythonConfigurationProperties.getInterpreter().contains(",")) {
            try {
                Path pathToPythonInterpreter = Path.of(this.pythonConfigurationProperties.getInterpreter());
                if (pathToPythonInterpreter.toFile().exists()) {
                    return pathToPythonInterpreter.normalize().toAbsolutePath().toString();
                }

                Path pathToDirectoryWithPython = pathToPythonInterpreter.getParent();
                if (pathToDirectoryWithPython.toFile().exists()) {
                    Path interpreterPathInDir = findInterpreterPath(pathToDirectoryWithPython); // maybe somebody has given a path to the python in WinApps

                    if (interpreterPathInDir != null) {
                        return interpreterPathInDir.normalize().toAbsolutePath().toString();
                    }
                }
            }
            catch (Exception ex) {
                log.error("Cannot find python on path configured in the dqo.python.interpreter configuration parameter: " +
                        this.pythonConfigurationProperties.getInterpreter() + ", error: " + ex.getMessage(), ex);
            }
        }

        String pathEnv = System.getenv("PATH");
        String[] pathDirectories = StringUtils.split(pathEnv, File.pathSeparatorChar);
        for (String pathDirectory: pathDirectories) {
            if (Strings.isNullOrEmpty(pathDirectory)) {
                continue;
            }

            Path dirPath = Path.of(pathDirectory);
            Path interpreterPath = findInterpreterPath(dirPath);

            if (interpreterPath != null) {
                return interpreterPath.toString();
            }
        }
        return null;
    }

    /**
     * Installs python (pip) requirements for the DQO_HOME.
     * @param pythonVirtualEnv Virtual environment configuration.
     */
    public void installDqoHomePipRequirements(PythonVirtualEnv pythonVirtualEnv) {
        try {
            Path pathToLastInstalledRequirements = pythonVirtualEnv.getVirtualEnvPath().resolve("home_requirements.txt");
            Path pathToRequirementsTxt = Path.of(this.dqoConfigurationProperties.getHome()).resolve(this.pythonConfigurationProperties.getDqoHomeRequirements());

            if (!Files.exists(pathToRequirementsTxt)) {
                return;
            }

            if (Files.exists(pathToLastInstalledRequirements) &&
                    Objects.equals(Files.readString(pathToLastInstalledRequirements, StandardCharsets.UTF_8),
                            Files.readString(pathToRequirementsTxt, StandardCharsets.UTF_8))) {
                return; // no more requirements to install
            }

			installPipRequirements(pythonVirtualEnv, pathToRequirementsTxt);

            Files.copy(pathToRequirementsTxt, pathToLastInstalledRequirements, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (PythonExecutionException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new PythonExecutionException("Failed to install pip requirements in a virtual environment: " + ex.getMessage(), ex);
        }
    }

    /**
     * Installs python (pip) optional development requirements for the DQO_HOME.
     * @param pythonVirtualEnv Virtual environment configuration.
     */
    public void installDqoHomePipDevelopmentRequirements(PythonVirtualEnv pythonVirtualEnv) {
        try {
            Path pathToLastInstalledRequirements = pythonVirtualEnv.getVirtualEnvPath().resolve("home_requirements_dev.txt");
            Path pathToRequirementsTxt = Path.of(this.dqoConfigurationProperties.getHome()).resolve(this.pythonConfigurationProperties.getDqoHomeRequirementsDev());

            if (!Files.exists(pathToRequirementsTxt)) {
                return;
            }

            if (Files.exists(pathToLastInstalledRequirements) &&
                    Objects.equals(Files.readString(pathToLastInstalledRequirements, StandardCharsets.UTF_8),
                            Files.readString(pathToRequirementsTxt, StandardCharsets.UTF_8))) {
                return; // no more requirements to install
            }

            installPipRequirements(pythonVirtualEnv, pathToRequirementsTxt);

            Files.copy(pathToRequirementsTxt, pathToLastInstalledRequirements, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (PythonExecutionException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new PythonExecutionException("Failed to install pip development requirements in a virtual environment: " + ex.getMessage(), ex);
        }
    }

    /**
     * Installs python (pip) requirements for the user home (user requirements, if present).
     * @param pythonVirtualEnv Virtual environment configuration.
     */
    public void installUserHomePipRequirements(PythonVirtualEnv pythonVirtualEnv) {
        try {
            Path pathToLastInstalledRequirements = pythonVirtualEnv.getVirtualEnvPath().resolve("user_requirements.txt");
            String userHome = this.userConfigurationProperties.getHome();
            Path pathToRequirementsTxt = Path.of(userHome).resolve("rules/requirements.txt");

            if (!Files.exists(pathToRequirementsTxt)) {
                return; // the user has not configured personal python requirements
            }

            if (Files.exists(pathToLastInstalledRequirements) &&
                    Objects.equals(Files.readString(pathToLastInstalledRequirements, StandardCharsets.UTF_8),
                            Files.readString(pathToRequirementsTxt, StandardCharsets.UTF_8))) {
                return; // no more requirements to install
            }

			installPipRequirements(pythonVirtualEnv, pathToRequirementsTxt);

            Files.copy(pathToRequirementsTxt, pathToLastInstalledRequirements, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (PythonExecutionException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new PythonExecutionException("Failed to install pip requirements in a virtual environment: " + ex.getMessage(), ex);
        }
    }

    /**
     * Installs pip requirements.
     * @param pythonVirtualEnv Python environment configuration.
     * @param pathToRequirementsTxt Path to requirements.
     */
    public void installPipRequirements(PythonVirtualEnv pythonVirtualEnv, Path pathToRequirementsTxt) {
        String[] commandLine = {pythonVirtualEnv.getPythonInterpreterPath(), "-m", "pip", "install", "-r", pathToRequirementsTxt.toString(), "--ignore-installed", "--no-warn-conflicts"};

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            Map<String, String> environment = processBuilder.environment();
            for (Map.Entry<String, String> envVarKeyPair : pythonVirtualEnv.getEnvironmentVariables().entrySet()) {
                environment.put(envVarKeyPair.getKey(), envVarKeyPair.getValue());
            }
            processBuilder.command(commandLine);
            Process pipProcess = processBuilder.start();
            CompletableFuture<String> readOutputFuture = CompletableFuture.supplyAsync(() -> readAllContent(pipProcess.getInputStream()));
            CompletableFuture<String> readErrorFuture = CompletableFuture.supplyAsync(() -> readAllContent(pipProcess.getErrorStream()));

            boolean isSuccess = pipProcess.waitFor(this.pythonConfigurationProperties.getPipTimeoutSeconds(), TimeUnit.SECONDS);
            if (!isSuccess || pipProcess.exitValue() != 0) {
                throw new PythonExecutionException("Failed to start " + String.join(" ", commandLine) + ", error code: " + pipProcess.exitValue() +
                        "\nError output: " + readErrorFuture.get() + "\noutput: " + readOutputFuture.get());
            }
        }
        catch (Exception ex) {
            throw new PythonExecutionException("Failed to start " + String.join(" ", commandLine) + ", error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Reads a whole input stream content as a single string.
     * @param inputStream Input stream.
     * @return Content.
     */
    public String readAllContent(InputStream inputStream) {
        try {
            try (BufferedInputStream br = new BufferedInputStream(inputStream)) {
                List<String> allLines = IOUtils.readLines(br, StandardCharsets.UTF_8);
                return String.join("\n", allLines);
            }
        }
        catch (IOException ex) {
            throw new PythonExecutionException("Failed to read from a stream: " + ex.getMessage(), ex);
        }
    }
}
