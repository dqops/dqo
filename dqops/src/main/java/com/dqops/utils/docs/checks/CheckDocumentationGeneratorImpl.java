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
     * Pivots a list of check categories into a list of data quality dimensions, with checks only for that dimension.
     * @param checkCategories Source list of checks divided by a category.
     * @return New list of checks divided by the data quality dimension and the target (table or column).
     */
    public List<CheckDimensionDocumentationModel> pivotCheckByDimension(List<CheckCategoryDocumentationModel> checkCategories) {
        Map<String, CheckDimensionDocumentationModel> tableChecksByDimension = new HashMap<>();
        Map<String, CheckDimensionDocumentationModel> columnChecksByDimension = new HashMap<>();

        for (CheckCategoryDocumentationModel checkCategoryDocumentationModel : checkCategories) {
            Map<String, CheckDimensionDocumentationModel> targetMap =
                    Objects.equals(checkCategoryDocumentationModel.getTarget(), "table") ? tableChecksByDimension :
                            Objects.equals(checkCategoryDocumentationModel.getTarget(), "column") ? columnChecksByDimension : null;

            for (SimilarChecksDocumentationModel similarChecksDocumentationModel : checkCategoryDocumentationModel.getCheckGroups()) {
                CheckDimensionDocumentationModel checkDimensionDocumentationModel = targetMap.get(similarChecksDocumentationModel.getQualityDimension());
                if (checkDimensionDocumentationModel == null) {
                    checkDimensionDocumentationModel = new CheckDimensionDocumentationModel();
                    checkDimensionDocumentationModel.setTarget(checkCategoryDocumentationModel.getTarget());
                    checkDimensionDocumentationModel.setQualityDimension(similarChecksDocumentationModel.getQualityDimension());
                    checkDimensionDocumentationModel.setQualityDimensionLowercase(similarChecksDocumentationModel.getQualityDimension().toLowerCase(Locale.ROOT));
                    targetMap.put(similarChecksDocumentationModel.getQualityDimension(), checkDimensionDocumentationModel);
                }

                checkDimensionDocumentationModel.getCheckGroups().add(similarChecksDocumentationModel);
            }
        }

        List<CheckDimensionDocumentationModel> combinedTableColumnChecksByDimension = new ArrayList<>();
        combinedTableColumnChecksByDimension.addAll(tableChecksByDimension.values());
        combinedTableColumnChecksByDimension.addAll(columnChecksByDimension.values());
        return combinedTableColumnChecksByDimension;
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
        DocumentationFolder typesOfChecksFolder = new DocumentationFolder("categories-of-data-quality-checks");
        typesOfChecksFolder.setLinkName("Categories of checks");
        typesOfChecksFolder.setDirectPath(projectRootPath.resolve("../docs")
                .resolve(typesOfChecksFolder.getFolderName()).toAbsolutePath().normalize());

        DocumentationFolder currentTypesOfChecksFolder = currentRootFolder.getFolderByName("categories-of-data-quality-checks");

        DocumentationMarkdownFile newCategoryIndexFile = typesOfChecksFolder.addNestedFile("index.md");
        DocumentationMarkdownFile currentCategoryIndexFile = currentTypesOfChecksFolder.getFileByName("index.md");
        newCategoryIndexFile.setFileContent(currentCategoryIndexFile.getFileContent());

        for (CheckCategoryDocumentationModel checkCategoryModel : checkCategoryDocumentationModels) {
            String renderedCategoryContent = HandlebarsDocumentationUtilities.renderTemplate(checkCategoryTemplate, checkCategoryModel);
            String categoryFileName =
                    CheckCategoryDocumentationConstants.CATEGORY_FILE_NAMES.containsKey(checkCategoryModel.getCategoryName()) ?
                            CheckCategoryDocumentationConstants.CATEGORY_FILE_NAMES.get(checkCategoryModel.getCategoryName()) :
                    "how-to-detect-" + checkCategoryModel.getCategoryName().replace('_', '-') + "-data-quality-issues.md";
            String categoryNameWithSpaces = checkCategoryModel.getCategoryName().replace('_', ' ');
            if (Objects.equals(categoryNameWithSpaces, "pii")) {
                categoryNameWithSpaces = "PII";
            }
            String listOfTableChecksBeginMarker = "## List of " + categoryNameWithSpaces + " checks at a table level";
            String listOfColumnChecksBeginMarker = "## List of " + categoryNameWithSpaces + " checks at a column level";

            DocumentationMarkdownFile oldCategoryFileContent = currentTypesOfChecksFolder != null ?
                    currentTypesOfChecksFolder.getFileByName(categoryFileName) : null;
            DocumentationMarkdownFile newCategoryFileContent = typesOfChecksFolder.getFileByName(categoryFileName);

            if (newCategoryFileContent == null) {
                newCategoryFileContent = typesOfChecksFolder.addNestedFile(categoryFileName);

                if (oldCategoryFileContent == null) {
                    // creating a placeholder for a category
                    newCategoryFileContent.setFileContent(
                            "# Detecting data quality issues with " + categoryNameWithSpaces + "\n" +
                            "Read this guide to learn what types of data quality checks are supported in DQOps to detect issues related to " + categoryNameWithSpaces + ".\n" +
                            "The data quality checks are configured in the `" + checkCategoryModel.getCategoryName() + "` category in DQOps.\n" +
                            "\n" +
                            "## " + categoryNameWithSpaces + " category\n" +
                            "Data quality checks that are detecting issues related to " + categoryNameWithSpaces + " are listed below.\n" +
                            "\n" +
                            "## Detecting " + categoryNameWithSpaces + " issues\n" +
                            "How to detect " + categoryNameWithSpaces + " data quality issues.\n" +
                            "\n" +
                            listOfTableChecksBeginMarker + "\n" +
                            "\n" +
                            listOfColumnChecksBeginMarker + "\n" +
                            "\n" +
                            "## What's next\n" +
                            "- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name\n" +
                            "- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules\n" +
                            "- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps\n"
                            );
                } else {
                    newCategoryFileContent.setFileContent(oldCategoryFileContent.getFileContent());
                }
            }

            String categoryNameLink = CheckCategoryDocumentationConstants.CATEGORY_LINK_NAMES.containsKey(checkCategoryModel.getCategoryName()) ?
                    CheckCategoryDocumentationConstants.CATEGORY_LINK_NAMES.get(checkCategoryModel.getCategoryName()) :
                    checkCategoryModel.getCategoryName().substring(0, 1).toUpperCase(Locale.ROOT) +
                    checkCategoryModel.getCategoryName().substring(1).replace('_', ' ');
            newCategoryFileContent.setLinkName(categoryNameLink);
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

        Template checkDimensionTemplate = HandlebarsDocumentationUtilities.compileTemplate("checks/check_dimension_index");
        DocumentationFolder dqoConceptsFolder = new DocumentationFolder("dqo-concepts");
        dqoConceptsFolder.setLinkName("DQOps concepts");
        dqoConceptsFolder.setDirectPath(projectRootPath.resolve("../docs")
                .resolve(dqoConceptsFolder.getFolderName()).toAbsolutePath().normalize());
        DocumentationFolder currentDqoConceptsFolder = currentRootFolder.getFolderByName("dqo-concepts");
        DocumentationMarkdownFile oldDataQualityDimensionsFileContent = currentDqoConceptsFolder != null ?
                currentDqoConceptsFolder.getFileByName("data-quality-dimensions.md") : null;
        DocumentationMarkdownFile newDataQualityDimensionsFileContent = dqoConceptsFolder.addNestedFile("data-quality-dimensions.md");
        String dataQualityDimensionsFileContent = oldDataQualityDimensionsFileContent.getFileContent();
        newDataQualityDimensionsFileContent.setFileContent(dataQualityDimensionsFileContent);

        List<CheckDimensionDocumentationModel> checkDimensionDocumentationModels = pivotCheckByDimension(checkCategoryDocumentationModels);

        for (CheckDimensionDocumentationModel checkDimensionModel : checkDimensionDocumentationModels) {
            String renderedDimensionContent = HandlebarsDocumentationUtilities.renderTemplate(checkDimensionTemplate, checkDimensionModel);

            String listOfChecksBeginMarker = "### " +
                    Character.toUpperCase(checkDimensionModel.getTarget().charAt(0)) + checkDimensionModel.getTarget().substring(1) +
                    " " + checkDimensionModel.getQualityDimension().toLowerCase(Locale.ROOT) + " checks";

            if (!dataQualityDimensionsFileContent.contains(listOfChecksBeginMarker)) {
                dataQualityDimensionsFileContent +=
                        "\n" +
                        listOfChecksBeginMarker + "\n"+
                        "\n" +
                        "## End of list, remove this entry and move the content up\n";
            }

            List<String> originalFileLines = dataQualityDimensionsFileContent.lines().collect(Collectors.toList());
            List<String> renderedDimensionContentLines = renderedDimensionContent.lines().collect(Collectors.toList());
            List<String> newReplacedLines = FileContentIndexReplaceUtility.replaceLinesBetweenMarkers(originalFileLines, renderedDimensionContentLines,
                    listOfChecksBeginMarker, "## ");

            if (newReplacedLines != null) {
                dataQualityDimensionsFileContent = String.join("\n", newReplacedLines) + "\n";
            }
        }

        if (!Objects.equals(newDataQualityDimensionsFileContent.getFileContent(), dataQualityDimensionsFileContent)) {
            newDataQualityDimensionsFileContent.setFileContent(dataQualityDimensionsFileContent);
        }

        DocumentationFolder rootFolder = new DocumentationFolder();
        rootFolder.setFolderName("");
        rootFolder.setDirectPath(projectRootPath.resolve("../docs").toAbsolutePath().normalize());
        rootFolder.getSubFolders().add(checksFolder);
        rootFolder.getSubFolders().add(typesOfChecksFolder);
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
