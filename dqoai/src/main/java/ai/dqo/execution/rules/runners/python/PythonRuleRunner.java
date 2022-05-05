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
package ai.dqo.execution.rules.runners.python;

import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.core.filesystem.virtual.HomeFilePath;
import ai.dqo.core.filesystem.localfiles.HomeLocationFindService;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.rules.RuleExecutionResult;
import ai.dqo.execution.rules.RuleExecutionRunParameters;
import ai.dqo.execution.rules.finder.RuleDefinitionFindResult;
import ai.dqo.execution.rules.runners.AbstractRuleRunner;
import ai.dqo.utils.python.PythonCallerService;
import ai.dqo.utils.python.PythonExecutionException;
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
    private final DqoConfigurationProperties configurationProperties;
    private final HomeLocationFindService homeLocationFindService;

    /**
     * Default constructor.
     * @param pythonCallerService Python call service.
     * @param configurationProperties Configuration properties with the rule runner python path.
     * @param homeLocationFindService Home (user home or dqo home) finder service.
     */
    public PythonRuleRunner(PythonCallerService pythonCallerService,
							DqoConfigurationProperties configurationProperties,
							HomeLocationFindService homeLocationFindService) {
        this.pythonCallerService = pythonCallerService;
        this.configurationProperties = configurationProperties;
        this.homeLocationFindService = homeLocationFindService;
    }

    /**
     * Executes a rule that evaluates a value and checks if it is valid.
     *
     * @param checkExecutionContext    Check execution context with access to the DQO_HOME and user home.
     * @param ruleRunParameters        Rule run parameters with the values to be sent to the rule as parameters.
     * @param ruleDefinitionFindResult Rule definition find result to identity a rule (like a python module) that will be executed.
     * @return Rule evaluation result.
     */
    @Override
    public RuleExecutionResult executeRule(CheckExecutionContext checkExecutionContext,
										   RuleExecutionRunParameters ruleRunParameters,
										   RuleDefinitionFindResult ruleDefinitionFindResult) {
        String evaluateRulesModule = this.configurationProperties.getPython().getEvaluateRulesModule();
        HomeFilePath ruleHomeRelativePath = ruleDefinitionFindResult.getRulePythonFilePath();

        PythonRuleCallInput ruleInput = new PythonRuleCallInput();
        ruleInput.setRuleParameters(ruleRunParameters);
        String pathToHome = this.homeLocationFindService.getHomePath(ruleDefinitionFindResult.getHome());
        String absolutePathToPythonRule = Path.of(pathToHome).resolve(ruleHomeRelativePath.toRelativePath()).toAbsolutePath().toString();
        ruleInput.setRuleModulePath(absolutePathToPythonRule);

        PythonRuleCallOutput output = this.pythonCallerService.executePythonHomeScript(ruleInput, evaluateRulesModule, PythonRuleCallOutput.class);

        if (output.getError() != null) {
            throw new PythonExecutionException("Data quality rule " + absolutePathToPythonRule + " failed to execute, error: " + output.getError());
        }
        return output.getResult();
    }
}
