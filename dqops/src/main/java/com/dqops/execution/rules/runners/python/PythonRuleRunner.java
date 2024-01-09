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

import com.dqops.core.configuration.DqoPythonConfigurationProperties;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.rules.RuleExecutionResult;
import com.dqops.execution.rules.RuleExecutionRunParameters;
import com.dqops.execution.rules.finder.RuleDefinitionFindResult;
import com.dqops.execution.rules.runners.AbstractRuleRunner;
import com.dqops.utils.python.PythonCallerService;
import com.dqops.utils.python.PythonExecutionException;
import org.apache.parquet.Strings;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Rule evaluation runner that calls a python function.
 */
@Component
public class PythonRuleRunner extends AbstractRuleRunner {
    /**
     * Python rule runner class name.
     */
    public static final String CLASS_NAME = PythonRuleRunner.class.getName();
    private final PythonCallerService pythonCallerService;
    private final DqoPythonConfigurationProperties pythonConfigurationProperties;
    private final HomeLocationFindService homeLocationFindService;

    /**
     * Default constructor.
     * @param pythonCallerService Python call service.
     * @param pythonConfigurationProperties Configuration properties with the rule runner python path.
     * @param homeLocationFindService Home (user home or dqo home) finder service.
     */
    public PythonRuleRunner(PythonCallerService pythonCallerService,
                            DqoPythonConfigurationProperties pythonConfigurationProperties,
							HomeLocationFindService homeLocationFindService) {
        this.pythonCallerService = pythonCallerService;
        this.pythonConfigurationProperties = pythonConfigurationProperties;
        this.homeLocationFindService = homeLocationFindService;
    }

    /**
     * Executes a rule that evaluates a value and checks if it is valid.
     *
     * @param executionContext    Check execution context with access to the DQO_HOME and user home.
     * @param ruleRunParameters        Rule run parameters with the values to be sent to the rule as parameters.
     * @param ruleDefinitionFindResult Rule definition find result to identity a rule (like a python module) that will be executed.
     * @return Rule evaluation result.
     */
    @Override
    public RuleExecutionResult executeRule(ExecutionContext executionContext,
                                           RuleExecutionRunParameters ruleRunParameters,
                                           RuleDefinitionFindResult ruleDefinitionFindResult) {
        String evaluateRulesModule = this.pythonConfigurationProperties.getEvaluateRulesModule();
        HomeFilePath ruleHomeRelativePath = ruleDefinitionFindResult.getRulePythonFilePath();

        PythonRuleCallInput ruleInput = new PythonRuleCallInput();
        String dataDomainFolder = executionContext.getUserHomeContext() != null &&
                executionContext.getUserHomeContext().getUserIdentity() != null ?
                executionContext.getUserHomeContext().getUserIdentity().getDataDomainFolder() : "";
        String dataDomainModule = Strings.isNullOrEmpty(dataDomainFolder) ? "default" : dataDomainFolder.replace(' ', '_');
        ruleInput.setDataDomainModule(dataDomainModule);
        ruleInput.setRuleParameters(ruleRunParameters);
        String pathToHome = this.homeLocationFindService.getHomePath(ruleDefinitionFindResult.getHome());
        String absolutePathToPythonRule = Path.of(pathToHome).resolve(ruleHomeRelativePath.toRelativePath()).toAbsolutePath().toString();
        ruleInput.setRuleModulePath(absolutePathToPythonRule);
        ruleInput.setHomePath(pathToHome);
        ruleInput.setRuleModuleLastModified(ruleDefinitionFindResult.getRulePythonFileLastModified());

        PythonRuleCallOutput output = this.pythonCallerService.executePythonHomeScript(ruleInput, evaluateRulesModule, PythonRuleCallOutput.class);

        if (output.getError() != null) {
            throw new PythonExecutionException("Data quality rule " + absolutePathToPythonRule + " failed to execute, error: " + output.getError());
        }
        return output.getResult();
    }
}
