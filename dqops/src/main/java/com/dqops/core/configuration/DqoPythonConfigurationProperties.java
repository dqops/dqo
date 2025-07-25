/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import com.dqops.execution.rules.RuleModelUpdateMode;
import com.dqops.execution.rules.runners.python.PythonRuleDebugMode;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for DQOps Python. Properties are mapped to the "dqo.python." prefix.
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
    private String dqoHomeRequirementsDev = "lib/requirements_dev.txt";
    private boolean useHostPython;
    private int pipTimeoutSeconds = 120;
    private int pythonScriptTimeoutSeconds = 120;
    private boolean enableDebugging;
    private PythonRuleDebugMode debugMode = PythonRuleDebugMode.silent;
    private RuleModelUpdateMode ruleModelUpdateMode = RuleModelUpdateMode.never;

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
     * Returns a path to the development requirements with a list of pypi packages that should be installed for development environments that are used to compile DQOps.
     * @return Path to development requirements.
     */
    public String getDqoHomeRequirementsDev() {
        return dqoHomeRequirementsDev;
    }

    /**
     * Sets a path to development requirements with a list of pypi packages that should be installed for development environments that are used to compile DQOps.
     * @param dqoHomeRequirementsDev Path to development requirements.
     */
    public void setDqoHomeRequirementsDev(String dqoHomeRequirementsDev) {
        this.dqoHomeRequirementsDev = dqoHomeRequirementsDev;
    }

    /**
     * When true, does not initialize a python virtual environment in DQOps home. Instead, uses the hosts python.
     * @return True when using the host python.
     */
    public boolean isUseHostPython() {
        return useHostPython;
    }

    /**
     * Sets the flag to use the host's python (the default python installation on the machine, not a DQOps private virtual environment).
     * @param useHostPython Use host python.
     */
    public void setUseHostPython(boolean useHostPython) {
        this.useHostPython = useHostPython;
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

    /**
     * Returns the debug mode used when running Python rules.
     * @return Debug mode.
     */
    public PythonRuleDebugMode getDebugMode() {
        return debugMode;
    }

    /**
     * Sets the debug mode used when running Python rules.
     * @param debugMode Debug mode.
     */
    public void setDebugMode(PythonRuleDebugMode debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * Returns the mode of updating ML models for rules.
     * @return ML model update mode.
     */
    public RuleModelUpdateMode getRuleModelUpdateMode() {
        return ruleModelUpdateMode;
    }

    /**
     * Sets the mode of updating ML models.
     * @param ruleModelUpdateMode Model update mode.
     */
    public void setRuleModelUpdateMode(RuleModelUpdateMode ruleModelUpdateMode) {
        this.ruleModelUpdateMode = ruleModelUpdateMode;
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
