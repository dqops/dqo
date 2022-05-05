/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.python;

import java.nio.file.Path;

/**
 * A service that detects if a Python virtual environment was configured or sets up a venv.
 */
public interface PythonVirtualEnvService {
    /**
     * Returns a path to the VENV.
     * @return Path to the python venv.
     */
    Path getVEnvPath();

    /**
     * Checks if the python venv exists and is correctly initialized.
     * @return true when the venv is initialized.
     */
    boolean isVirtualEnvInitialized();

    /**
     * Initializes a python environment.
     */
    void initializePythonVirtualEnv();

    /**
     * Returns a python virtual environment parameters required to start the python interpreter inside the virtual environment.
     * @return Virtual environment settings.
     */
    PythonVirtualEnv getVirtualEnv();

    /**
     * Installs python (pip) requirements for the DQO_HOME.
     * @param pythonVirtualEnv Virtual environment configuration.
     */
    void installDqoHomePipRequirements(PythonVirtualEnv pythonVirtualEnv);

    /**
     * Installs python (pip) requirements for the user home (user requirements, if present).
     * @param pythonVirtualEnv Virtual environment configuration.
     */
    void installUserHomePipRequirements(PythonVirtualEnv pythonVirtualEnv);
}
