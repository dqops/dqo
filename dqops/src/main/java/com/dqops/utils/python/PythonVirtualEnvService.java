/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.python;

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
