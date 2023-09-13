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
package com.dqops.rest.server.client;

import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoPythonConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.utils.python.PythonExecutionException;
import com.dqops.utils.python.PythonVirtualEnv;
import com.dqops.utils.python.PythonVirtualEnvServiceImpl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Build action called from maven. Performs two actions: first converts the DQO Swagger 2.x file to Swagger 3.x,
 * then updates the DQO python client.
 */
public class GeneratePythonClientPostProcessor {
    /**
     * Main method of the build operation that upgrades swagger 2 file to swagger 3 file.
     * @param args Command line arguments.
     * @throws Exception When something fails.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("DQO Python client generator utility");
            System.out.println("Missing required parameter: <path to the source swagger 2 yaml file> <path to the target swagger 3 yaml file> <project root path>");
            return;
        }

        try {
            SwaggerFileUpgradeUtility.convertSwagger2ToSwagger3(args[0], args[1]);
        }
        catch (Exception ex) {
            System.err.println("Cannot convert Swagger 2 to Swagger 3, error: " + ex.getMessage());
            return;
        }

        Path projectRoot = Path.of(args[2]);

        try {
            DqoConfigurationProperties configurationProperties = new DqoConfigurationProperties();
            configurationProperties.setHome(projectRoot.resolve("../home").toAbsolutePath().normalize().toString());
            DqoUserConfigurationProperties dqoUserConfigurationProperties = new DqoUserConfigurationProperties();
            dqoUserConfigurationProperties.setHome(projectRoot.resolve("../userhome").toAbsolutePath().normalize().toString());
            DqoPythonConfigurationProperties pythonConfigurationProperties = new DqoPythonConfigurationProperties();

            PythonVirtualEnvServiceImpl pythonVirtualEnvService = new PythonVirtualEnvServiceImpl(
                    configurationProperties, pythonConfigurationProperties, dqoUserConfigurationProperties);
            if (!pythonVirtualEnvService.isVirtualEnvInitialized()) {
                pythonVirtualEnvService.initializePythonVirtualEnv();
            }

            PythonVirtualEnv pythonVirtualEnv = pythonVirtualEnvService.getVirtualEnv();
            String absolutePathToSwagger3 = Path.of(args[1]).toAbsolutePath().normalize().toString();

            String[] commandLine = {pythonVirtualEnv.getPythonInterpreterPath(), "-m", "openapi_python_client", "update",
                    "--path", absolutePathToSwagger3, "--config", "client_generate_config.yaml"};

            ProcessBuilder processBuilder = new ProcessBuilder()
                    .inheritIO()
                    .directory(projectRoot.resolve("../distribution/python").toFile());

            Map<String, String> environment = processBuilder.environment();
            for (String exitingVarEnvName : new ArrayList<>(environment.keySet())) {
                if (exitingVarEnvName.startsWith("DQO_")) {
                    environment.remove(exitingVarEnvName);
                }
            }

            for (Map.Entry<String, String> envVarKeyPair : pythonVirtualEnv.getEnvironmentVariables().entrySet()) {
                environment.put(envVarKeyPair.getKey(), envVarKeyPair.getValue());
            }

            processBuilder.command(commandLine);
            Process pipProcess = processBuilder.start();

            boolean isSuccess = pipProcess.waitFor(120, TimeUnit.SECONDS);
            if (!isSuccess || pipProcess.exitValue() != 0) {
                throw new PythonExecutionException("Failed to start " + String.join(" ", commandLine) + ", error code: " + pipProcess.exitValue());
            }
        }
        catch (Exception ex) {
            System.err.println("Cannot generate a DQO Python client, error: " + ex.getMessage());
        }
    }
}
