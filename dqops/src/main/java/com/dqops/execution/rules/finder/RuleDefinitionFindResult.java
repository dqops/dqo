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
package com.dqops.execution.rules.finder;

import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.metadata.definitions.rules.RuleDefinitionSpec;
import com.dqops.metadata.storage.localfiles.HomeType;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * Rule definition search result. Returned from the rule finder.
 */
@EqualsAndHashCode(callSuper = false)
public class RuleDefinitionFindResult {
    private HomeType home;
    private RuleDefinitionSpec ruleDefinitionSpec;
    private HomeFilePath rulePythonFilePath;
    private Instant rulePythonFileLastModified;

    /**
     * The type of the home (user home or dqo system home) where the rule is defined.
     * @return Home type that is hosting the rule.
     */
    public HomeType getHome() {
        return home;
    }

    /**
     * Sets the home type that is hosting the rule definition.
     * @param home Home type.
     */
    public void setHome(HomeType home) {
        this.home = home;
    }

    /**
     * Rule specification with additional rule configuration.
     * @return Rule definition spec.
     */
    public RuleDefinitionSpec getRuleDefinitionSpec() {
        return ruleDefinitionSpec;
    }

    /**
     * Sets a rule configuration (definition) specification.
     * @param ruleDefinitionSpec Rule definition specification.
     */
    public void setRuleDefinitionSpec(RuleDefinitionSpec ruleDefinitionSpec) {
        this.ruleDefinitionSpec = ruleDefinitionSpec;
    }

    /**
     * Returns relative path to the python file inside the home (user home or dqo home).
     * @return Relative path to the python file.
     */
    public HomeFilePath getRulePythonFilePath() {
        return rulePythonFilePath;
    }

    /**
     * Sets a relative path to a python file with the rule function.
     * @param rulePythonFilePath Relative path (inside the home) to the python module.
     */
    public void setRulePythonFilePath(HomeFilePath rulePythonFilePath) {
        this.rulePythonFilePath = rulePythonFilePath;
    }

    /**
     * Returns the timestamp when the Python module with the rule code was last modified.
     * @return Last file modification timestamp.
     */
    public Instant getRulePythonFileLastModified() {
        return rulePythonFileLastModified;
    }

    /**
     * Sets the last file modification timestamp of the rule file.
     * @param rulePythonFileLastModified Last file modification timestamp.
     */
    public void setRulePythonFileLastModified(Instant rulePythonFileLastModified) {
        this.rulePythonFileLastModified = rulePythonFileLastModified;
    }
}
