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
package ai.dqo.utils.specs;

import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.definitions.rules.RuleRunnerType;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import ai.dqo.metadata.fields.ParameterDefinitionsListSpec;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.rules.custom.CustomRuleParametersSpec;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingService;
import ai.dqo.utils.reflection.TargetClassSearchUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service used during the build process (called from a maven profile) that updates the yaml definitions in the DQO Home rule folder,
 * updating correct list of fields that were detected using reflection.
 */
@Component
public class RuleDefinitionDefaultSpecUpdateServiceImpl implements RuleDefinitionDefaultSpecUpdateService {
    private DqoHomeContext dqoHomeContext;
    private SpecToUiCheckMappingService specToUiCheckMappingService;

    @Autowired
    public RuleDefinitionDefaultSpecUpdateServiceImpl(DqoHomeContext dqoHomeContext,
                                                      SpecToUiCheckMappingService specToUiCheckMappingService) {
        this.dqoHomeContext = dqoHomeContext;
        this.specToUiCheckMappingService = specToUiCheckMappingService;
    }

    /**
     * Checks if all rule definition yaml files in the DQO Home rule folder have a correct list of parameters, matching the fields used in the Java spec classes.
     * @param projectRootPath Path to the dqoai module folder (code folder).
     * @param dqoHomeContext DQO Home context.
     */
    @Override
    public void updateRuleSpecifications(Path projectRootPath, DqoHomeContext dqoHomeContext) {
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        List<? extends Class<? extends AbstractRuleParametersSpec>> classes = TargetClassSearchUtility.findClasses(
                "ai.dqo.rules", projectRootPath, AbstractRuleParametersSpec.class);
        Set<String> processedRuleName = new LinkedHashSet<>();

        for (Class<? extends AbstractRuleParametersSpec> ruleParametersClass : classes) {
            AbstractRuleParametersSpec abstractRuleParametersSpec = createRuleParameterInstance(ruleParametersClass);
            if (abstractRuleParametersSpec instanceof CustomRuleParametersSpec) {
                continue;
            }

            String ruleDefinitionName = abstractRuleParametersSpec.getRuleDefinitionName();
            if (processedRuleName.contains(ruleDefinitionName)) {
                continue;
            }
            processedRuleName.add(ruleDefinitionName);

            RuleDefinitionWrapper ruleDefinitionWrapper = dqoHome.getRules().getByObjectName(ruleDefinitionName, true);
            if (ruleDefinitionWrapper == null) {
                System.err.println("Missing rule definition for " + ruleDefinitionName);
                continue;
            }

            RuleDefinitionSpec ruleDefinitionSpec = ruleDefinitionWrapper.getSpec();
            if (ruleDefinitionSpec.getType() == null) {
                if (ruleDefinitionWrapper.getRulePythonModuleContent().getTextContent() != null) {
                    ruleDefinitionSpec.setType(RuleRunnerType.python);
                }
            }

            List<ParameterDefinitionSpec> fieldDefinitionList = this.specToUiCheckMappingService.createFieldsForRuleParameters(abstractRuleParametersSpec)
                    .stream()
                    .map(uiFieldModel -> uiFieldModel.getDefinition())
                    .collect(Collectors.toList());
            fieldDefinitionList.forEach(fieldDefinition -> fieldDefinition.setFieldName(fieldDefinition.getDisplayName()));

            ParameterDefinitionsListSpec expectedParameterListSpec = new ParameterDefinitionsListSpec(fieldDefinitionList);
            ParameterDefinitionsListSpec currentParameterListSpec = ruleDefinitionSpec.getFields();

            if (!Objects.equals(expectedParameterListSpec, currentParameterListSpec)) {
                ruleDefinitionSpec.setFields(expectedParameterListSpec);
                System.out.println("Updating fields for rule: " + ruleDefinitionName);
            }
        }

        dqoHomeContext.flush();
    }

    /**
     * Creates a new instance of the rule parameter object.
     * @param ruleParametersClass Class type of the rule parameter.
     * @return Rule parameter object instance.
     */
    public AbstractRuleParametersSpec createRuleParameterInstance(
            Class<? extends AbstractRuleParametersSpec> ruleParametersClass) {
        try {
            Constructor<? extends AbstractRuleParametersSpec> defaultConstructor = ruleParametersClass.getConstructor();
            AbstractRuleParametersSpec abstractRuleParametersSpec = defaultConstructor.newInstance();
            return abstractRuleParametersSpec;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
