/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.rules.finder;

import com.dqops.core.filesystem.BuiltInFolderNames;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.core.principal.UserDomainIdentity;
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

        RuleDefinitionWrapper userRuleDefinitionWrapper = userHome.getRules().getByObjectName(bareRuleName, true);
        if (userRuleDefinitionWrapper != null) {
            FileContent userRuleModuleContent = userRuleDefinitionWrapper.getRulePythonModuleContent();
            HomeFilePath pythonFileUserHomePath = HomeFilePath.fromFilePath(dataDomain, pythonFileNameRelativeToHome);

            RuleDefinitionFindResult userRuleResult = new RuleDefinitionFindResult() {{
				setHome(HomeType.USER_HOME);
				setRuleDefinitionSpec(userRuleDefinitionWrapper.getSpec());
				setRulePythonFilePath(pythonFileUserHomePath);
                setRulePythonFileLastModified(userRuleModuleContent != null ? userRuleModuleContent.getLastModified() : Instant.now());
            }};
            return userRuleResult;
        }

        DqoHome dqoHome = executionContext.getDqoHomeContext().getDqoHome();
        RuleDefinitionWrapper dqoRuleDefinitionWrapper = dqoHome.getRules().getByObjectName(bareRuleName, true);
        if (dqoRuleDefinitionWrapper != null) {
            FileContent dqoRulePythonModuleContent = dqoRuleDefinitionWrapper.getRulePythonModuleContent();
            HomeFilePath pythonFileUserDqoHomePath = HomeFilePath.fromFilePath(UserDomainIdentity.ROOT_DATA_DOMAIN, pythonFileNameRelativeToHome);

            RuleDefinitionFindResult dqoRuleResult = new RuleDefinitionFindResult() {{
				setHome(HomeType.DQO_HOME);
				setRuleDefinitionSpec(dqoRuleDefinitionWrapper.getSpec());
				setRulePythonFilePath(pythonFileUserDqoHomePath);
                setRulePythonFileLastModified(dqoRulePythonModuleContent != null ? dqoRulePythonModuleContent.getLastModified() : Instant.now());
            }};
            return dqoRuleResult;
        }

        return null; // rule not found, we could also throw an exception, to be decided
    }
}
