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
package ai.dqo.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for Dqo.ai. Properties are mapped to the "dqo.python." prefix.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.python")
@EqualsAndHashCode(callSuper = false)
public class DqoPythonConfigurationProperties implements Cloneable {
    private String interpreter = "python3,python3.exe,python,python.exe";
    private String evaluateTemplatesModule = "lib/evaluate_templates.py";
    private String evaluateRulesModule = "lib/evaluate_rules.py";
    private String venvPath = "venv";
    private String dqoHomeRequirements = "lib/requirements.txt";
    private int pipTimeoutSeconds = 120;
    private int pythonScriptTimeoutSeconds = 120;

    /**
     * Python runtime file name (python, python3, python.exe, python3.exe, etc.)
     * @return Python interpreter command name.
     */
    public String getInterpreter() {
        return interpreter;
    }

    /**
     * Sets the python interpreter name.
     * @param interpreter Python interpreter name.
     */
    public void setInterpreter(String interpreter) {
        this.interpreter = interpreter;
    }

    /**
     * Path to the python module that evaluates templates. The path is relative to the DOCUMENT_HOME.
     * @return Python evaluate_template.py file path.
     */
    public String getEvaluateTemplatesModule() {
        return evaluateTemplatesModule;
    }

    /**
     * Sets the template evaluation python path.
     * @param evaluateTemplatesModule Evaluate template module path.
     */
    public void setEvaluateTemplatesModule(String evaluateTemplatesModule) {
        this.evaluateTemplatesModule = evaluateTemplatesModule;
    }

    /**
     * Path to the python module that evaluates rules. The path is relative to the DOCUMENT_HOME.
     * @return Python evaluate_rules.py file path.
     */
    public String getEvaluateRulesModule() {
        return evaluateRulesModule;
    }

    /**
     * Sets the rule evaluation python module path.
     * @param evaluateRulesModule Evaluate rules module path.
     */
    public void setEvaluateRulesModule(String evaluateRulesModule) {
        this.evaluateRulesModule = evaluateRulesModule;
    }

    /**
     * VENV path, relative to the DQO_HOME.
     * @return Python venv path.
     */
    public String getVenvPath() {
        return venvPath;
    }

    /**
     * Sets the relative path to the venv.
     * @param venvPath venv path.
     */
    public void setVenvPath(String venvPath) {
        this.venvPath = venvPath;
    }

    /**
     * Returns the path to the "requirements.txt" file inside the DQO_HOME.
     * @return Path to the requirements.txt.
     */
    public String getDqoHomeRequirements() {
        return dqoHomeRequirements;
    }

    /**
     * Sets the path to the requirements.txt.
     * @param dqoHomeRequirements Path to requirements.txt
     */
    public void setDqoHomeRequirements(String dqoHomeRequirements) {
        this.dqoHomeRequirements = dqoHomeRequirements;
    }

    /**
     * Returns the pip install timeout in seconds.
     * @return Pip timeout in seconds.
     */
    public int getPipTimeoutSeconds() {
        return pipTimeoutSeconds;
    }

    /**
     * Sets the pip install timeout in seconds.
     * @param pipTimeoutSeconds Pip install timeout.
     */
    public void setPipTimeoutSeconds(int pipTimeoutSeconds) {
        this.pipTimeoutSeconds = pipTimeoutSeconds;
    }

    /**
     * Timeout in seconds for calling python scripts (like a rule evaluation or a template rendering).
     * @return Python script timeout in seconds.
     */
    public int getPythonScriptTimeoutSeconds() {
        return pythonScriptTimeoutSeconds;
    }

    /**
     * Sets the python script timeout in seconds.
     * @param pythonScriptTimeoutSeconds Python script timeout in seconds.
     */
    public void setPythonScriptTimeoutSeconds(int pythonScriptTimeoutSeconds) {
        this.pythonScriptTimeoutSeconds = pythonScriptTimeoutSeconds;
    }

    /**
     * Clones the current object.
     * @return
     */
    @Override
    public DqoPythonConfigurationProperties clone() {
        try {
            return (DqoPythonConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
