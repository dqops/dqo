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

import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.rules.CustomRuleParametersSpec;
import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.dqops.utils.reflection.TargetClassSearchUtility;
import com.github.jknack.handlebars.Template;

import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.*;

/**
 * Rule documentation generator that generates documentation for rules.
 */
public class RuleDocumentationGeneratorImpl implements RuleDocumentationGenerator {
    private RuleDocumentationModelFactory ruleDocumentationModelFactory;

    public RuleDocumentationGeneratorImpl(RuleDocumentationModelFactory ruleDocumentationModelFactory) {
        this.ruleDocumentationModelFactory = ruleDocumentationModelFactory;
    }

    /**
     * Renders documentation for all rules as markdown files.
     *
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @param linkageStore
     * @param dqoHome         DQOps home.
     * @return Folder structure with rendered markdown files.
     */
    @Override
    public DocumentationFolder renderRuleDocumentation(Path projectRootPath, LinkageStore<Class<?>> linkageStore, DqoHome dqoHome) {
        DocumentationFolder rulesFolder = new DocumentationFolder();
        rulesFolder.setFolderName("reference/rules");
        rulesFolder.setLinkName("Data quality rules");
        Path rulesPath = Path.of("docs", "reference", "rules");
        rulesFolder.setDirectPath(projectRootPath.resolve("..").resolve(rulesPath).toAbsolutePath().normalize());

        List<RuleDocumentationModel> ruleDocumentationModels = new ArrayList<>(createRuleDocumentationModels(projectRootPath));
        ruleDocumentationModels.sort(Comparator.comparing(RuleDocumentationModel::getFullRuleName));
        List<RuleGroupedDocumentationModel> ruleGroupedDocumentationModels = groupRulesByCategory(ruleDocumentationModels);

        MainPageRuleDocumentationModel mainPageRuleDocumentationModel = new MainPageRuleDocumentationModel();
        mainPageRuleDocumentationModel.setRules(ruleGroupedDocumentationModels);

        Template main_page_template = HandlebarsDocumentationUtilities.compileTemplate("rules/main_page_documentation");
        DocumentationMarkdownFile mainPageDocumentationMarkdownFile = rulesFolder.addNestedFile("index" + ".md");
        mainPageDocumentationMarkdownFile.setRenderContext(mainPageRuleDocumentationModel);

        String renderedMainPageDocument = HandlebarsDocumentationUtilities.renderTemplate(main_page_template, mainPageRuleDocumentationModel);
        mainPageDocumentationMarkdownFile.setFileContent(renderedMainPageDocument);
        
        
        Template template = HandlebarsDocumentationUtilities.compileTemplate("rules/rule_documentation");

        for (RuleGroupedDocumentationModel ruleGroupedDocumentationModel : ruleGroupedDocumentationModels) {
            Path rulesFilePath = Path.of(
                    ruleGroupedDocumentationModel.getCategory().substring(0, 1).toUpperCase()
                    + ruleGroupedDocumentationModel.getCategory().substring(1)
            );
            DocumentationMarkdownFile documentationMarkdownFile = rulesFolder.addNestedFile(rulesFilePath + ".md");
            documentationMarkdownFile.setRenderContext(ruleGroupedDocumentationModel);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, ruleGroupedDocumentationModel);
            documentationMarkdownFile.setFileContent(renderedDocument);

            for (RuleDocumentationModel ruleDocumentationModel : ruleGroupedDocumentationModel.getRuleDocumentationModels()) {
                linkageStore.put(
                        ruleDocumentationModel.getRuleParametersClazz(),
                        Path.of("/")
                                .resolve(rulesPath)
                                .resolve(rulesFilePath)
                                .resolve("#" + ruleDocumentationModel.getRuleName().replace('_', '-'))
                );
            }
        }
        return rulesFolder;
    }

    /**
     * Creates a list of all rules used for documentation.
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @return Rules documentation model list.
     */
    public Set<RuleDocumentationModel> createRuleDocumentationModels(Path projectRootPath) {
        Set<RuleDocumentationModel> ruleDocumentationModels = new LinkedHashSet<>();

        List<? extends Class<? extends AbstractRuleParametersSpec>> classes = TargetClassSearchUtility.findClasses(
                "com.dqops.rules", projectRootPath, AbstractRuleParametersSpec.class);

        Set<String> documentedRuleNames = new LinkedHashSet<>();

        for (Class<? extends AbstractRuleParametersSpec> ruleParametersClass : classes) {
            AbstractRuleParametersSpec abstractRuleParametersSpec = createRuleParameterInstance(ruleParametersClass);
            if (abstractRuleParametersSpec instanceof CustomRuleParametersSpec) {
                continue;
            }

            String ruleDefinitionName = abstractRuleParametersSpec.getRuleDefinitionName();
            if (documentedRuleNames.contains(ruleDefinitionName)) {
                continue; // additional sensor parameters specification class with different parameters for an already documented sensor
            }
            documentedRuleNames.add(ruleDefinitionName);

            RuleDocumentationModel ruleDocumentation = this.ruleDocumentationModelFactory.createRuleDocumentation(abstractRuleParametersSpec);

            if (ruleDocumentation == null) {
                continue; // rule not found
            }
            ruleDocumentationModels.add(ruleDocumentation);
        }
        return ruleDocumentationModels;
    }

    /**
     * Groups into list rule documentation models by category.
     * @param ruleDocumentationModels List of all ruleDocumentationModel, used to iterating and grouping by category.
     * @return Rule grouped documentation list.
     */
    public List<RuleGroupedDocumentationModel> groupRulesByCategory(List<RuleDocumentationModel> ruleDocumentationModels) {
        List<RuleGroupedDocumentationModel> ruleGroupedDocumentationModels = new ArrayList<>();
        Map<String, List<RuleDocumentationModel>> groupedRules = new LinkedHashMap<>();

        for (RuleDocumentationModel model : ruleDocumentationModels) {
            groupedRules.computeIfAbsent(model.getCategory(), k -> new ArrayList<>()).add(model);
        }
        for (Map.Entry<String, List<RuleDocumentationModel>> groupOfRules : groupedRules.entrySet()) {
            RuleGroupedDocumentationModel ruleGroupedDocumentationModel = new RuleGroupedDocumentationModel();
            ruleGroupedDocumentationModel.setCategory(groupOfRules.getKey());
            ruleGroupedDocumentationModel.setRuleDocumentationModels(groupOfRules.getValue());
            ruleGroupedDocumentationModels.add(ruleGroupedDocumentationModel);
        }
        ruleGroupedDocumentationModels.sort(Comparator.comparing(RuleGroupedDocumentationModel::getCategory));
        return ruleGroupedDocumentationModels;
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
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
