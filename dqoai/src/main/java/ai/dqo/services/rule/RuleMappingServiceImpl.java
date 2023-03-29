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
package ai.dqo.services.rule;

import ai.dqo.core.filesystem.virtual.FileContent;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapperImpl;
import ai.dqo.rest.models.metadata.RuleBasicModel;
import org.springframework.stereotype.Service;

/**
 * Service for mapping a RuleBasicModel (REST model) object to a RuleDefinitionWrapper object.
 */
@Service
public class RuleMappingServiceImpl implements RuleMappingService {

    /**
     * Returns the rule definition wrapper object.
     * @param ruleBasicModel Rule basic model.
     * @return rule definition wrapper object.
     */
    @Override
    public RuleDefinitionWrapper toRuleDefinitionWrapper(RuleBasicModel ruleBasicModel) {

        FileContent fileContent = withRuleDefinitionPythonModuleContent(ruleBasicModel);
        RuleDefinitionSpec ruleDefinitionSpec = withRuleDefinitionSpec(ruleBasicModel);

        RuleDefinitionWrapper ruleDefinitionWrapper = new RuleDefinitionWrapperImpl();
        ruleDefinitionWrapper.setRuleName(ruleBasicModel.getRuleName());
        ruleDefinitionWrapper.setSpec(ruleDefinitionSpec);
        ruleDefinitionWrapper.setRulePythonModuleContent(fileContent);

        return ruleDefinitionWrapper;
    }

    /**
     * Returns the rule definition spec object.
     * @param ruleBasicModel Rule basic model.
     * @return the rule definition spec object.
     */
    @Override
    public RuleDefinitionSpec withRuleDefinitionSpec(RuleBasicModel ruleBasicModel) {

        RuleDefinitionSpec ruleDefinitionSpec = new RuleDefinitionSpec();
        ruleDefinitionSpec.setType(ruleBasicModel.getType());
        ruleDefinitionSpec.setJavaClassName(ruleBasicModel.getJavaClassName());
        ruleDefinitionSpec.setMode(ruleBasicModel.getMode());
        ruleDefinitionSpec.setTimeWindow(ruleBasicModel.getTimeWindow());
        ruleDefinitionSpec.setFields(ruleBasicModel.getFields());
        ruleDefinitionSpec.setParameters(ruleBasicModel.getParameters());

        return ruleDefinitionSpec;
    }

    /**
     * Returns the file content object with Python module content.
     * @param ruleBasicModel Rule basic model.
     * @return file content object with Python module content.
     */
    @Override
    public FileContent withRuleDefinitionPythonModuleContent(RuleBasicModel ruleBasicModel) {
        return new FileContent(ruleBasicModel.getRulePythonModuleContent());
    }

    /**
     * Returns the rule basic model object.
     * @param ruleDefinitionWrapper Rule basic model.
     * @param custom This rule has a custom (user) definition.
     * @param builtIn This rule is provided as part of DQO.
     * @return rule basic model object.
     */
    @Override
    public RuleBasicModel toRuleBasicModel(RuleDefinitionWrapper ruleDefinitionWrapper, boolean custom, boolean builtIn) {

        RuleBasicModel ruleBasicModel = new RuleBasicModel();
        ruleBasicModel.withRuleName(ruleDefinitionWrapper.getRuleName())
                .withRulePythonModuleContent(ruleDefinitionWrapper.getRulePythonModuleContent().getTextContent())
                .withType(ruleDefinitionWrapper.getSpec().getType())
                .withJavaClassName(ruleDefinitionWrapper.getSpec().getJavaClassName())
                .withMode(ruleDefinitionWrapper.getSpec().getMode())
                .withTimeWindow(ruleDefinitionWrapper.getSpec().getTimeWindow())
                .withFields(ruleDefinitionWrapper.getSpec().getFields())
                .withParameters(ruleDefinitionWrapper.getSpec().getParameters());
        ruleBasicModel.setCustom(custom);
        ruleBasicModel.setBuiltIn(builtIn);

        return ruleBasicModel;
    }
}
