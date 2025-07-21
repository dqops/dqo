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
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Python settings required to start python inside a virtual environment.
 */
public class PythonVirtualEnv {
    private Path virtualEnvPath;
    private HashMap<String, String> environmentVariables = new LinkedHashMap<>();
    private String pythonInterpreterPath;
    private boolean enableDebugging;

    /**
     * Returns the virtual environment root path.
     * @return Virtual env (python) path.
     */
    public Path getVirtualEnvPath() {
        return virtualEnvPath;
    }

    /**
     * Sets the path to the virtual env.
     * @param virtualEnvPath Virtual env path.
     */
    public void setVirtualEnvPath(Path virtualEnvPath) {
        this.virtualEnvPath = virtualEnvPath;
    }

    /**
     * Environment variables that must be set to use the virtual env. The environment variables will contain
     * similar values as if a virtual environment was activated.
     * @return Environment variables required to start the python interpreter to use our virtual environment.
     */
    public HashMap<String, String>  getEnvironmentVariables() {
        return environmentVariables;
    }

    /**
     * Sets the environment variables that enable the virtual env.
     * @param environmentVariables Environment variables key/values.
     */
    public void setEnvironmentVariables(HashMap<String, String> environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    /**
     * Returns an absolute path to the python interpreter inside the virtual env.
     * @return Absolute path to python.
     */
    public String getPythonInterpreterPath() {
        return pythonInterpreterPath;
    }

    /**
     * Sets the absolute path to the python interpreter.
     * @param pythonInterpreterPath Absolute path to python.
     */
    public void setPythonInterpreterPath(String pythonInterpreterPath) {
        this.pythonInterpreterPath = pythonInterpreterPath;
    }

    /**
     * Adds an environment variable PYDEVD_USE_CYTHON=NO which simplifies debugging.
     * @return Enable debugging.
     */
    public boolean isEnableDebugging() {
        return enableDebugging;
    }

    /**
     * Decides if python should be started with an extra environment variable PYDEVD_USE_CYTHON=NO.
     * @param enableDebugging True - add PYDEVD_USE_CYTHON=NO environment variable.
     */
    public void setEnableDebugging(boolean enableDebugging) {
        this.enableDebugging = enableDebugging;
    }
}
