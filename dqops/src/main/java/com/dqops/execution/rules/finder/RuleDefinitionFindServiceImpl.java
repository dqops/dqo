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

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.HomeType;
import com.dqops.metadata.storage.localfiles.SpecFileNames;
import com.dqops.metadata.userhome.UserHome;
import org.apache.parquet.Strings;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Rule definition search service. Finds the rule definition in the user come (custom definitions and overrides), then in the DQO_HOME (built-in rules).
 */
@Component
public class RuleDefinitionFindServiceImpl implements RuleDefinitionFindService {
    /**
     * Finds a rule definition for a named rule.
     * @param executionContext Check execution context with access to the DQO_HOME and the user home.
     * @param ruleName Rule name with or without the .py file extension.
     * @return Rule definition find result or null when the rule was not found.
     */
    public RuleDefinitionFindResult findRule(ExecutionContext executionContext, String ruleName) {
        if (Strings.isNullOrEmpty(ruleName)) {
            return null;
        }

        UserHome userHome = executionContext.getUserHomeContext().getUserHome();

        // remove the optional .py file extension
        String bareRuleName = ruleName.endsWith(SpecFileNames.CUSTOM_RULE_PYTHON_MODULE_FILE_EXT_PY) ?
                ruleName.substring(0, ruleName.length() - SpecFileNames.CUSTOM_RULE_PYTHON_MODULE_FILE_EXT_PY.length()) :
                ruleName;

        String pythonFileNameRelativeToHome = BuiltInFolderNames.RULES + "/" + ruleName + ".py";
        String dataDomain = executionContext.getUserHomeContext().getUserIdentity().getDataDomainFolder();
        HomeFilePath pythonFileHomePath = HomeFilePath.fromFilePath(dataDomain, pythonFileNameRelativeToHome);

        RuleDefinitionWrapper userRuleDefinitionWrapper = userHome.getRules().getByObjectName(bareRuleName, true);
        if (userRuleDefinitionWrapper != null) {
            FileContent userRuleModuleContent = userRuleDefinitionWrapper.getRulePythonModuleContent();
            RuleDefinitionFindResult userRuleResult = new RuleDefinitionFindResult() {{
				setHome(HomeType.USER_HOME);
				setRuleDefinitionSpec(userRuleDefinitionWrapper.getSpec());
				setRulePythonFilePath(pythonFileHomePath);
                setRulePythonFileLastModified(userRuleModuleContent != null ? userRuleModuleContent.getLastModified() : Instant.now());
            }};
            return userRuleResult;
        }

        DqoHome dqoHome = executionContext.getDqoHomeContext().getDqoHome();
        RuleDefinitionWrapper dqoRuleDefinitionWrapper = dqoHome.getRules().getByObjectName(bareRuleName, true);
        if (dqoRuleDefinitionWrapper != null) {
            FileContent dqoRulePythonModuleContent = dqoRuleDefinitionWrapper.getRulePythonModuleContent();
            RuleDefinitionFindResult dqoRuleResult = new RuleDefinitionFindResult() {{
				setHome(HomeType.DQO_HOME);
				setRuleDefinitionSpec(dqoRuleDefinitionWrapper.getSpec());
				setRulePythonFilePath(pythonFileHomePath);
                setRulePythonFileLastModified(dqoRulePythonModuleContent != null ? dqoRulePythonModuleContent.getLastModified() : Instant.now());
            }};
            return dqoRuleResult;
        }

        return null; // rule not found, we could also throw an exception, to be decided
    }
}
