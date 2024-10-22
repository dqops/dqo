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
package com.dqops.execution.rules.runners.python;

import com.dqops.execution.rules.RuleExecutionRunParameters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Object passed to the python rule evaluation module. Specifies the path to the python file with the rule implementation and the parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class PythonRuleCallInput {
    private String dataDomainModule;
    private String ruleModulePath;
    private String homePath;
    private String dqoHomePath;
    private String dqoRootUserHomePath;
    private long ruleModuleLastModifiedEpoch;
    private RuleExecutionRunParameters ruleParameters;
    private PythonRuleDebugMode debugMode = PythonRuleDebugMode.silent;

    /**
     * Returns the data domain name.
     * @return Data domain name.
     */
    public String getDataDomainModule() {
        return dataDomainModule;
    }

    /**
     * Sets the data domain name.
     * @param dataDomainModule Data domain name.
     */
    public void setDataDomainModule(String dataDomainModule) {
        this.dataDomainModule = dataDomainModule;
    }

    /**
     * Returns the path to a rule module (python file).
     * @return Path to the file with the rule implementation.
     */
    public String getRuleModulePath() {
        return ruleModulePath;
    }

    /**
     * Sets a path to the rule module.
     * @param ruleModulePath Rule module path.
     */
    public void setRuleModulePath(String ruleModulePath) {
        this.ruleModulePath = ruleModulePath;
    }

    /**
     * Returns the path to the home (user home or DQOps system home) where the rule is defined.
     * @return Home folder path.
     */
    public String getHomePath() {
        return homePath;
    }

    /**
     * Sets a path to the DQOps home (user home or dqo system home) where the rule is defined.
     * @param homePath A full path to the correct home path.
     */
    public void setHomePath(String homePath) {
        this.homePath = homePath;
    }

    /**
     * Returns the path to the DQOps Home folder.
     * @return DQOps home folder path.
     */
    public String getDqoHomePath() {
        return dqoHomePath;
    }

    /**
     * Sets a path to the DQOps home folder.
     * @param dqoHomePath Path to the DQOps home folder.
     */
    public void setDqoHomePath(String dqoHomePath) {
        this.dqoHomePath = dqoHomePath;
    }

    /**
     * Returns the path to the root DQOps user home folder.
     * @return Path to the root user folder.
     */
    public String getDqoRootUserHomePath() {
        return dqoRootUserHomePath;
    }

    /**
     * Sets a path to a root DQOps user home folder that is used to resolve additional python packages.
     * @param dqoRootUserHomePath DQOps root user home path.
     */
    public void setDqoRootUserHomePath(String dqoRootUserHomePath) {
        this.dqoRootUserHomePath = dqoRootUserHomePath;
    }

    /**
     * Returns the timestamp when the Python rule file was last modified.
     * @return The last modification timestamp of the rule module.
     */
    public long getRuleModuleLastModifiedEpoch() {
        return ruleModuleLastModifiedEpoch;
    }

    /**
     * Sets the timestamp when the rule module was modified for the last time.
     * @param ruleModuleLastModifiedEpoch Rule module last modification timestamp.
     */
    public void setRuleModuleLastModifiedEpoch(long ruleModuleLastModifiedEpoch) {
        this.ruleModuleLastModifiedEpoch = ruleModuleLastModifiedEpoch;
    }

    /**
     * Returns the rule parameters that will be evaluated.
     * @return Rule parameters.
     */
    public RuleExecutionRunParameters getRuleParameters() {
        return ruleParameters;
    }

    /**
     * Sets the rule parameter object.
     * @param ruleParameters Rule evaluation parameter object.
     */
    public void setRuleParameters(RuleExecutionRunParameters ruleParameters) {
        this.ruleParameters = ruleParameters;
    }

    /**
     * Returns the debug mode used for debugging rules (capturing their parameters).
     * @return Debug mode.
     */
    public PythonRuleDebugMode getDebugMode() {
        return debugMode;
    }

    /**
     * Sets the debug mode for Python rules.
     * @param debugMode Debug mode.
     */
    public void setDebugMode(PythonRuleDebugMode debugMode) {
        this.debugMode = debugMode;
    }
}
