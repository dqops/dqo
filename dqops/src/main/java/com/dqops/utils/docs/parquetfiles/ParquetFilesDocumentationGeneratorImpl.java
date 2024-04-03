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
package com.dqops.utils.docs.parquetfiles;

import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.dqops.utils.docs.rules.MainPageRuleDocumentationModel;
import com.github.jknack.handlebars.Template;

import java.nio.file.Path;
import java.util.List;

/**
 * Parquet files documentation generator that generate documentation for parquet files.
 */
public class ParquetFilesDocumentationGeneratorImpl implements ParquetFilesDocumentationGenerator {
    private ParquetFilesDocumentationModelFactoryImpl parquetFilesDocumentationModelFactory;

    public ParquetFilesDocumentationGeneratorImpl(ParquetFilesDocumentationModelFactoryImpl parquetFilesDocumentationModelFactory) {
        this.parquetFilesDocumentationModelFactory = parquetFilesDocumentationModelFactory;
    }

    /**
     * Renders documentation for all parquet classes as markdown files.
     *
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @return Folder structure with rendered markdown files.
     */
    @Override
    public DocumentationFolder renderParquetDocumentation(Path projectRootPath) {
        DocumentationFolder parquetFilesFolder = new DocumentationFolder();
        parquetFilesFolder.setFolderName("reference/parquetfiles");
        parquetFilesFolder.setLinkName("Parquet data files");
        parquetFilesFolder.setDirectPath(projectRootPath.resolve("../docs/reference/parquetfiles").toAbsolutePath().normalize());

        List<ParquetFileDocumentationModel> parquetFileDocumentationModels = parquetFilesDocumentationModelFactory.createDocumentationForParquetFiles();

        MainPageParquetFileDocumentationModel mainPageParquetFileDocumentationModel = new MainPageParquetFileDocumentationModel();
        mainPageParquetFileDocumentationModel.setParquetFiles(parquetFileDocumentationModels);

        Template mainPageTemplate = HandlebarsDocumentationUtilities.compileTemplate("parquetfiles/main_page_documentation");
        DocumentationMarkdownFile mainPageDocumentationMarkdownFile = parquetFilesFolder.addNestedFile("index" + ".md");
        mainPageDocumentationMarkdownFile.setRenderContext(mainPageParquetFileDocumentationModel);

        String renderedMainPageDocument = HandlebarsDocumentationUtilities.renderTemplate(mainPageTemplate, mainPageParquetFileDocumentationModel);
        mainPageDocumentationMarkdownFile.setFileContent(renderedMainPageDocument);

        Template template = HandlebarsDocumentationUtilities.compileTemplate("parquetfiles/parquetfiles_documentation");

        for (ParquetFileDocumentationModel parquetFileDocumentationModel : parquetFileDocumentationModels) {
            DocumentationMarkdownFile documentationMarkdownFile = parquetFilesFolder.addNestedFile(parquetFileDocumentationModel.getParquetFileFullName() + ".md");
            documentationMarkdownFile.setRenderContext(parquetFileDocumentationModel);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, parquetFileDocumentationModel);
            documentationMarkdownFile.setFileContent(renderedDocument);
        }
        return parquetFilesFolder;
    }
}
