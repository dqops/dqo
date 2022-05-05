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
import java.util.HashMap;

/**
 * Python settings required to start python inside a virtual environment.
 */
public class PythonVirtualEnv {
    private Path virtualEnvPath;
    private HashMap<String, String> environmentVariables = new HashMap<>();
    private String pythonInterpreterPath;

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
    public HashMap<String, String> getEnvironmentVariables() {
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
}
