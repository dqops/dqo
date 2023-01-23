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

import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.rules.custom.CustomRuleParametersSpec;
import ai.dqo.utils.docs.HandlebarsDocumentationUtilities;
import ai.dqo.utils.docs.files.DocumentationFolder;
import ai.dqo.utils.docs.files.DocumentationMarkdownFile;
import ai.dqo.utils.reflection.TargetClassSearchUtility;
import com.github.jknack.handlebars.Template;

import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.List;

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
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @param dqoHome DQO home.
     * @return Folder structure with rendered markdown files.
     */
    @Override
    public DocumentationFolder renderRuleDocumentation(Path projectRootPath, DqoHome dqoHome) {
        DocumentationFolder rulesFolder = new DocumentationFolder();
        rulesFolder.setFolderName("rules");
        rulesFolder.setLinkName("Rule reference");
        rulesFolder.setDirectPath(projectRootPath.resolve("../docs/rules").toAbsolutePath().normalize());

        Template template = HandlebarsDocumentationUtilities.compileTemplate("rules/rule_documentation");

        List<? extends Class<? extends AbstractRuleParametersSpec>> classes = TargetClassSearchUtility.findClasses(
                "ai.dqo.rules", projectRootPath, AbstractRuleParametersSpec.class);

        for (Class<? extends AbstractRuleParametersSpec> ruleParametersClass : classes) {
            AbstractRuleParametersSpec abstractRuleParametersSpec = createRuleParameterInstance(ruleParametersClass);
            if (abstractRuleParametersSpec instanceof CustomRuleParametersSpec) {
                continue;
            }
            RuleDocumentationModel ruleDocumentation = this.ruleDocumentationModelFactory.createRuleDocumentation(abstractRuleParametersSpec);

            if (ruleDocumentation == null) {
                continue; // rule not found
            }

            DocumentationMarkdownFile documentationMarkdownFile = rulesFolder.addNestedFile(ruleDocumentation.getFullRuleName() + ".md");
            documentationMarkdownFile.setRenderContext(ruleDocumentation);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, ruleDocumentation);
            documentationMarkdownFile.setFileContent(renderedDocument);
        }

        return rulesFolder;
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
