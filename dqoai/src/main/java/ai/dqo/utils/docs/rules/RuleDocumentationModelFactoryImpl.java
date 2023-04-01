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
package ai.dqo.utils.docs.rules;

import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.fields.ParameterDefinitionsListSpec;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingService;
import ai.dqo.services.check.mapping.models.UIFieldModel;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Rule documentation model factory that creates a rule documentation.
 * It should be only used from post processor classes that are called by Maven during build.
 */
public class RuleDocumentationModelFactoryImpl implements RuleDocumentationModelFactory {
    private static final CommentFormatter commentFormatter = new CommentFormatter();
    private Path projectRoot;
    private DqoHomeContext dqoHomeContext;
    private SpecToUiCheckMappingService specToUiCheckMappingService;

    /**
     * Creates a rule documentation model factory.
     * @param projectRoot Project root path.
     * @param dqoHomeContext DQO User home context.
     * @param specToUiCheckMappingService Specification to UI model factory, used to get documentation of the rule parameters.
     */
    public RuleDocumentationModelFactoryImpl(Path projectRoot, DqoHomeContext dqoHomeContext,
                                             SpecToUiCheckMappingService specToUiCheckMappingService) {
        this.projectRoot = projectRoot;
        this.dqoHomeContext = dqoHomeContext;
        this.specToUiCheckMappingService = specToUiCheckMappingService;
    }

    /**
     * Create a rule documentation model for a given rule parameter class instance.
     * @param ruleParametersSpec Rule parameter instance.
     * @return Rule documentation model.
     */
    @Override
    public RuleDocumentationModel createRuleDocumentation(AbstractRuleParametersSpec ruleParametersSpec) {
        RuleDocumentationModel documentationModel = new RuleDocumentationModel();
        ClassJavadoc classJavadoc = RuntimeJavadoc.getJavadoc(ruleParametersSpec.getClass());
        if (classJavadoc != null) {
            if (classJavadoc.getComment() != null) {
                String formattedClassComment = commentFormatter.format(classJavadoc.getComment());
                documentationModel.setRuleParametersJavaDoc(formattedClassComment);
            }
        }

        String ruleDefinitionName = ruleParametersSpec.getRuleDefinitionName();
        documentationModel.setFullRuleName(ruleDefinitionName);
        String[] ruleParts = StringUtils.split(ruleDefinitionName, '/');
        documentationModel.setCategory(ruleParts[0]);
        documentationModel.setRuleName(ruleParts[1]);

        RuleDefinitionList rules = this.dqoHomeContext.getDqoHome().getRules();
        RuleDefinitionWrapper ruleDefinitionWrapper = rules.getByObjectName(ruleDefinitionName, true);
        if (ruleDefinitionWrapper == null) {
            System.err.println("Rule definition for Rule " + ruleDefinitionName + " was not found");
            return null;
        }
        ruleDefinitionWrapper = ruleDefinitionWrapper.clone();

        documentationModel.setRuleExample(loadRuleExample(ruleParts[0], ruleParts[1]));

        documentationModel.setDefinition(ruleDefinitionWrapper);

        List<UIFieldModel> fieldsForRuleParameters = this.specToUiCheckMappingService.createFieldsForRuleParameters(ruleParametersSpec);
        ParameterDefinitionsListSpec fieldDefinitionsList = new ParameterDefinitionsListSpec();
        fieldsForRuleParameters.forEach(uiFieldModel -> fieldDefinitionsList.add(uiFieldModel.getDefinition()));
        ruleDefinitionWrapper.getSpec().setFields(fieldDefinitionsList);  // replacing to use the most recent definition from the code

        return documentationModel;
    }

    /**
     * Loads a rule example from yaml file for a given rule category and rule name.
     * @param ruleCategory Rule category.
     * @param ruleName Rule name.
     * @return Rule example.
     */
    public String loadRuleExample(String ruleCategory, String ruleName) {

        Path path = this.projectRoot.resolve("../home/rules/" + ruleCategory + "/" + ruleName + ".dqorule.yaml").toAbsolutePath().normalize();

        try {
            List<String> read = Files.readAllLines(path);
            return String.join("\n", read);
        } catch (IOException e) {
            System.err.println("Cannot load rule example from file: " + path + ", error: " + e.getMessage() + ", " + e);
            return null;
        }
    }
}
