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
package ai.dqo.utils.docs.checks;

import ai.dqo.utils.docs.HandlebarsDocumentationUtilities;
import ai.dqo.utils.docs.files.DocumentationFolder;
import ai.dqo.utils.docs.files.DocumentationMarkdownFile;
import com.github.jknack.handlebars.Template;

import java.nio.file.Path;
import java.util.List;

/**
 * Rule documentation generator that generates documentation for rules.
 */
public class CheckDocumentationGeneratorImpl implements CheckDocumentationGenerator {
    private CheckDocumentationModelFactory checkDocumentationModelFactory;

    public CheckDocumentationGeneratorImpl(CheckDocumentationModelFactory checkDocumentationModelFactory) {
        this.checkDocumentationModelFactory = checkDocumentationModelFactory;
    }

    /**
     * Renders documentation for all checks as markdown files.
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @return Folder structure with rendered markdown files.
     */
    @Override
    public DocumentationFolder renderCheckDocumentation(Path projectRootPath) {
        DocumentationFolder rulesFolder = new DocumentationFolder();
        rulesFolder.setFolderName("checks");
        rulesFolder.setLinkName("Check reference");
        rulesFolder.setDirectPath(projectRootPath.resolve("../docs/checks").toAbsolutePath().normalize());

        Template template = HandlebarsDocumentationUtilities.compileTemplate("checks/check_documentation");

        List<CheckTargetDocumentationModel> documentationForChecks = this.checkDocumentationModelFactory.createDocumentationsForChecks();
        for (CheckTargetDocumentationModel check : documentationForChecks) {
            DocumentationMarkdownFile documentationMarkdownFile = rulesFolder.addNestedFile(check.getTarget() + ".md");
            documentationMarkdownFile.setRenderContext(check);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, check);
            documentationMarkdownFile.setFileContent(renderedDocument);
        }
        return rulesFolder;
    }
}
