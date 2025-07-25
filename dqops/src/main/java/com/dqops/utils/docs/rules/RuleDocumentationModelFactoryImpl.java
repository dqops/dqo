/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.rules;

import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.metadata.fields.ParameterDefinitionsListSpec;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.models.FieldModel;
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
    private SpecToModelCheckMappingService specToModelCheckMappingService;

    /**
     * Creates a rule documentation model factory.
     * @param projectRoot Project root path.
     * @param dqoHomeContext DQOps User home context.
     * @param specToModelCheckMappingService Specification to the model factory, used to get documentation of the rule parameters.
     */
    public RuleDocumentationModelFactoryImpl(Path projectRoot,
                                             DqoHomeContext dqoHomeContext,
                                             SpecToModelCheckMappingService specToModelCheckMappingService) {
        this.projectRoot = projectRoot;
        this.dqoHomeContext = dqoHomeContext;
        this.specToModelCheckMappingService = specToModelCheckMappingService;
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

        documentationModel.setRuleParametersClazz(ruleParametersSpec.getClass());
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
        documentationModel.setPythonSourceCode(ruleDefinitionWrapper.getRulePythonModuleContent().getTextContent().replace("\n", "\n    "));
        if (ruleDefinitionWrapper.getSpec().getFields() != null && !ruleDefinitionWrapper.getSpec().getFields().isEmpty()) {
            documentationModel.setFirstRuleParameterName(ruleDefinitionWrapper.getSpec().getFields().get(0).getFieldName());
        }

        List<FieldModel> fieldsForRuleParameters = this.specToModelCheckMappingService.createFieldsForRuleParameters(ruleParametersSpec);
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
            return String.join("\n    ", read);
        } catch (IOException e) {
            System.err.println("Cannot load rule example from file: " + path + ", error: " + e.getMessage() + ", " + e);
            return null;
        }
    }
}
