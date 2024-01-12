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
package com.dqops.utils.docs.checks;

import com.dqops.utils.docs.FileContentIndexReplaceUtility;
import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.github.jknack.handlebars.Template;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * @param currentRootFolder Current documentation that was loaded from files.
     * @return Folder structure with rendered markdown files.
     */
    @Override
    public DocumentationFolder renderCheckDocumentation(Path projectRootPath, DocumentationFolder currentRootFolder) {
        DocumentationFolder checksFolder = new DocumentationFolder();
        checksFolder.setFolderName(CheckDocumentationGenerator.CHECKS_FOLDER_NAME);
        checksFolder.setLinkName("Data quality checks");
        checksFolder.setDirectPath(projectRootPath.resolve("../docs/checks").toAbsolutePath().normalize());

        List<CheckCategoryDocumentationModel> checkCategoryDocumentationModels = Stream.concat(this.checkDocumentationModelFactory.makeDocumentationForTableChecks().stream(),
                this.checkDocumentationModelFactory.makeDocumentationForColumnChecks().stream()).collect(Collectors.toList());

        MainPageCheckDocumentationModel mainPageCheckDocumentationModel = new MainPageCheckDocumentationModel();
        mainPageCheckDocumentationModel.setHeader("Data quality checks");
        mainPageCheckDocumentationModel.setHelpText("This is a list of data quality checks supported by DQOps, broken down by a category and a brief description of what data quality issues they detect.");
        mainPageCheckDocumentationModel.setCheckTargets(checkCategoryDocumentationModels.stream()
                .map(CheckCategoryDocumentationModel::getTarget)
                .distinct()
                .collect(Collectors.toList()));
        mainPageCheckDocumentationModel.setChecks(checkCategoryDocumentationModels);

        Template mainPageTemplate = HandlebarsDocumentationUtilities.compileTemplate("checks/main_page_documentation");
        DocumentationMarkdownFile mainPageDocumentationMarkdownFile = checksFolder.addNestedFile("index" + ".md");
        mainPageDocumentationMarkdownFile.setRenderContext(mainPageCheckDocumentationModel);

        String renderedMainPageDocument = HandlebarsDocumentationUtilities.renderTemplate(mainPageTemplate, mainPageCheckDocumentationModel);
        mainPageDocumentationMarkdownFile.setFileContent(renderedMainPageDocument);


        Template template = HandlebarsDocumentationUtilities.compileTemplate("checks/check_documentation");

        List<SimilarChecksDocumentationModel> allChecksDocumentationModels = new ArrayList<>();

        for (CheckCategoryDocumentationModel check : checkCategoryDocumentationModels) {
            allChecksDocumentationModels.addAll(check.getCheckGroups());
        }
        for (SimilarChecksDocumentationModel similarCheck : allChecksDocumentationModels) {
            DocumentationMarkdownFile documentationMarkdownFile = checksFolder.addNestedFile(similarCheck.getTarget()
                    + "/" + similarCheck.getCategory()
                    + "/" + similarCheck.getPrimaryCheckName().replace(' ', '-') + ".md");
            documentationMarkdownFile.setRenderContext(similarCheck);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, similarCheck);
            documentationMarkdownFile.setFileContent(renderedDocument);
        }

        for (DocumentationFolder targetFolder : checksFolder.getSubFolders()) {
            MainPageCheckDocumentationModel indexPageCheckDocumentationModel = renderIndexPageForCheckCategory(checkCategoryDocumentationModels,
                    targetFolder.getFolderName(),
                    Optional.empty());
            DocumentationMarkdownFile indexFile = targetFolder.addNestedFile("index" + ".md");
            indexFile.setRenderContext(indexPageCheckDocumentationModel);
            String renderedIndexDocument = HandlebarsDocumentationUtilities.renderTemplate(mainPageTemplate, indexPageCheckDocumentationModel);
            indexFile.setFileContent(renderedIndexDocument);

            for (DocumentationFolder categoryFolder : targetFolder.getSubFolders()) {
                MainPageCheckDocumentationModel categoryIndexPageCheckDocumentationModel = renderIndexPageForCheckCategory(checkCategoryDocumentationModels,
                        targetFolder.getFolderName(),
                        Optional.of(categoryFolder.getFolderName()));
                DocumentationMarkdownFile categoryIndexFile = categoryFolder.addNestedFile("index" + ".md");
                categoryIndexFile.setRenderContext(categoryIndexPageCheckDocumentationModel);
                String renderedCategoryIndexDocument = HandlebarsDocumentationUtilities.renderTemplate(mainPageTemplate, categoryIndexPageCheckDocumentationModel);
                categoryIndexFile.setFileContent(renderedCategoryIndexDocument);
            }
        }

        Template checkCategoryTemplate = HandlebarsDocumentationUtilities.compileTemplate("checks/check_category_index");
        DocumentationFolder dqoConceptsFolder = new DocumentationFolder("dqo-concepts");
        dqoConceptsFolder.setDirectPath(projectRootPath.resolve("../docs/dqo-concepts").toAbsolutePath().normalize());
        DocumentationFolder categoriesOfChecksFolder = new DocumentationFolder("types-of-data-quality-checks");
        categoriesOfChecksFolder.setDirectPath(dqoConceptsFolder.getDirectPath().resolve(categoriesOfChecksFolder.getFolderName()).toAbsolutePath().normalize());
        dqoConceptsFolder.getSubFolders().add(categoriesOfChecksFolder);

        DocumentationFolder currentTypesOfChecksFolder = currentRootFolder.getFolderByName("dqo-concepts")
                .getFolderByName("types-of-data-quality-checks");

        for (CheckCategoryDocumentationModel checkCategoryModel : checkCategoryDocumentationModels) {
            String renderedCategoryContent = HandlebarsDocumentationUtilities.renderTemplate(checkCategoryTemplate, checkCategoryModel);
            String categoryFileName = "how-to-detect-" + checkCategoryModel.getCategoryName().replace('_', '-') + "-data-quality-issues.md";
            String categoryNameWithSpaces = checkCategoryModel.getCategoryName().replace('_', ' ');
            String listOfTableChecksBeginMarker = "## List of " + categoryNameWithSpaces + " checks at a table level";
            String listOfColumnChecksBeginMarker = "## List of " + categoryNameWithSpaces + " checks at a column level";

            DocumentationMarkdownFile oldCategoryFileContent = currentTypesOfChecksFolder != null ?
                    currentTypesOfChecksFolder.getFileByName(categoryFileName) : null;
            DocumentationMarkdownFile newCategoryFileContent = categoriesOfChecksFolder.getFileByName(categoryFileName);

            if (newCategoryFileContent == null) {
                newCategoryFileContent = categoriesOfChecksFolder.addNestedFile(categoryFileName);

                if (oldCategoryFileContent == null) {
                    // creating a placeholder for a category
                    newCategoryFileContent.setFileContent(
                            "# Detecting data quality issues with " + categoryNameWithSpaces + "\n" +
                            "The type of " + categoryNameWithSpaces + " is documented here\n" +
                            "\n" +
                            "## Detecting " + categoryNameWithSpaces + " at a table level\n" +
                            "How to detect " + categoryNameWithSpaces + " on tables\n" +
                            "\n" +
                            listOfTableChecksBeginMarker + "\n" +
                            "\n" +
                            "## Detecting " + categoryNameWithSpaces + " at a column level\n" +
                            "How to detect " + categoryNameWithSpaces + " on column\n" +
                            "\n" +
                            listOfColumnChecksBeginMarker + "\n" +
                            "\n" +
                            "## What's next\n" +
                            "- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name\n"
                            );
                } else {
                    newCategoryFileContent.setFileContent(oldCategoryFileContent.getFileContent());
                }
            }

            List<String> originalFileLines = newCategoryFileContent.getFileContent().lines().collect(Collectors.toList());
            List<String> renderedCategoryContentLines = renderedCategoryContent.lines().collect(Collectors.toList());
            List<String> newReplacedLines = FileContentIndexReplaceUtility.replaceLinesBetweenMarkers(originalFileLines, renderedCategoryContentLines,
                    Objects.equals(checkCategoryModel.getTarget(), "table") ? listOfTableChecksBeginMarker : listOfColumnChecksBeginMarker,
                    "## ");

            if (newReplacedLines != null) {
                String newFileContent = String.join(System.lineSeparator(), newReplacedLines) + System.lineSeparator();
                newCategoryFileContent.setFileContent(newFileContent);
            }
        }

        DocumentationFolder rootFolder = new DocumentationFolder();
        rootFolder.setFolderName("");
        rootFolder.setDirectPath(projectRootPath.resolve("../docs").toAbsolutePath().normalize());
        rootFolder.getSubFolders().add(checksFolder);
        rootFolder.getSubFolders().add(dqoConceptsFolder);

        return rootFolder;
    }

    private MainPageCheckDocumentationModel renderIndexPageForCheckCategory(List<CheckCategoryDocumentationModel> allCheckCategories,
                                                                            String checkTarget,
                                                                            Optional<String> checkCategory) {
        Stream<CheckCategoryDocumentationModel> checkCategoriesStream = allCheckCategories.stream()
                .filter(checkCategoryModel -> checkCategoryModel.getTarget().equals(checkTarget));
        if (checkCategory.isPresent()) {
            checkCategoriesStream = checkCategoriesStream.filter(
                    checkCategoryModel -> checkCategoryModel.getCategoryName().equals(checkCategory.get()));
        }
        List<CheckCategoryDocumentationModel> checkCategoryDocumentationModels = checkCategoriesStream.collect(Collectors.toList());

        MainPageCheckDocumentationModel indexPageCheckDocumentationModel = new MainPageCheckDocumentationModel();
        String header = checkTarget + " level";
        if (checkCategory.isPresent()) {
            header = header + " " + checkCategory.get().replace('_', ' ') + " data quality checks";
            indexPageCheckDocumentationModel.setHelpText(String.format(
                    "This is a list of %s %s data quality checks supported by DQOps and a brief description of what data quality issued they detect.",
                    checkCategory.get(), checkTarget));
        } else {
            indexPageCheckDocumentationModel.setHelpText(String.format(
                    "This is a list of %s data quality checks supported by DQOps, broken down by a category and a brief description of what quality issued they detect.",
                    checkTarget));
        }
        indexPageCheckDocumentationModel.setHeader(header);

        indexPageCheckDocumentationModel.setCheckTargets(checkCategoryDocumentationModels.stream()
                .map(CheckCategoryDocumentationModel::getTarget)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList()));
        indexPageCheckDocumentationModel.setChecks(checkCategoryDocumentationModels);
        return indexPageCheckDocumentationModel;
    }
}
