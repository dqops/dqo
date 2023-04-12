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
import ai.dqo.rest.models.metadata.RuleModel;
import org.springframework.stereotype.Service;

/**
 * Service for mapping a RuleBasicModel (REST model) object to a RuleDefinitionWrapper object.
 */
@Service
public class RuleMappingServiceImpl implements RuleMappingService {

    /**
     * Returns the rule definition wrapper object.
     * @param ruleModel Rule basic model.
     * @return rule definition wrapper object.
     */
    @Override
    public RuleDefinitionWrapper toRuleDefinitionWrapper(RuleModel ruleModel) {

        FileContent fileContent = withRuleDefinitionPythonModuleContent(ruleModel);
        RuleDefinitionSpec ruleDefinitionSpec = withRuleDefinitionSpec(ruleModel);

        RuleDefinitionWrapper ruleDefinitionWrapper = new RuleDefinitionWrapperImpl();
        ruleDefinitionWrapper.setRuleName(ruleModel.getRuleName());
        ruleDefinitionWrapper.setSpec(ruleDefinitionSpec);
        ruleDefinitionWrapper.setRulePythonModuleContent(fileContent);

        return ruleDefinitionWrapper;
    }

    /**
     * Returns the rule definition spec object.
     * @param ruleModel Rule basic model.
     * @return the rule definition spec object.
     */
    @Override
    public RuleDefinitionSpec withRuleDefinitionSpec(RuleModel ruleModel) {

        RuleDefinitionSpec ruleDefinitionSpec = new RuleDefinitionSpec();
        ruleDefinitionSpec.setType(ruleModel.getType());
        ruleDefinitionSpec.setJavaClassName(ruleModel.getJavaClassName());
        ruleDefinitionSpec.setMode(ruleModel.getMode());
        ruleDefinitionSpec.setTimeWindow(ruleModel.getTimeWindow());
        ruleDefinitionSpec.setFields(ruleModel.getFields());
        ruleDefinitionSpec.setParameters(ruleModel.getParameters());

        return ruleDefinitionSpec;
    }

    /**
     * Returns the file content object with Python module content.
     * @param ruleModel Rule basic model.
     * @return file content object with Python module content.
     */
    @Override
    public FileContent withRuleDefinitionPythonModuleContent(RuleModel ruleModel) {
        return new FileContent(ruleModel.getRulePythonModuleContent());
    }

    /**
     * Returns the rule basic model object.
     * @param ruleDefinitionWrapper Rule basic model.
     * @param custom This rule has a custom (user) definition.
     * @param builtIn This rule is provided as part of DQO.
     * @return rule basic model object.
     */
    @Override
    public RuleModel toRuleBasicModel(RuleDefinitionWrapper ruleDefinitionWrapper, boolean custom, boolean builtIn) {

        RuleModel ruleModel = new RuleModel();
        ruleModel.withRuleName(ruleDefinitionWrapper.getRuleName())
                .withRulePythonModuleContent(ruleDefinitionWrapper.getRulePythonModuleContent().getTextContent())
                .withType(ruleDefinitionWrapper.getSpec().getType())
                .withJavaClassName(ruleDefinitionWrapper.getSpec().getJavaClassName())
                .withMode(ruleDefinitionWrapper.getSpec().getMode())
                .withTimeWindow(ruleDefinitionWrapper.getSpec().getTimeWindow())
                .withFields(ruleDefinitionWrapper.getSpec().getFields())
                .withParameters(ruleDefinitionWrapper.getSpec().getParameters());
        ruleModel.setCustom(custom);
        ruleModel.setBuiltIn(builtIn);

        return ruleModel;
    }
}
