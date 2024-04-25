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
package com.dqops.utils.docs;

import com.dqops.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoPythonConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.incidents.IncidentNotificationMessage;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderServiceImpl;
import com.dqops.metadata.storage.localfiles.checkdefinitions.CheckDefinitionYaml;
import com.dqops.metadata.storage.localfiles.columndefaultpatterns.ColumnDefaultChecksPatternYaml;
import com.dqops.metadata.storage.localfiles.dashboards.DashboardYaml;
import com.dqops.metadata.storage.localfiles.defaultnotifications.DefaultNotificationsYaml;
import com.dqops.metadata.storage.localfiles.defaultschedules.DefaultSchedulesYaml;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import com.dqops.metadata.storage.localfiles.ruledefinitions.RuleDefinitionYaml;
import com.dqops.metadata.storage.localfiles.sensordefinitions.ProviderSensorYaml;
import com.dqops.metadata.storage.localfiles.sensordefinitions.SensorDefinitionYaml;
import com.dqops.metadata.storage.localfiles.settings.LocalSettingsYaml;
import com.dqops.metadata.storage.localfiles.sources.ConnectionYaml;
import com.dqops.metadata.storage.localfiles.sources.TableYaml;
import com.dqops.metadata.storage.localfiles.tabledefaultpatterns.TableDefaultChecksPatternYaml;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingServiceImpl;
import com.dqops.services.check.matching.*;
import com.dqops.utils.docs.checks.CheckDocumentationGenerator;
import com.dqops.utils.docs.checks.CheckDocumentationGeneratorImpl;
import com.dqops.utils.docs.checks.CheckDocumentationModelFactory;
import com.dqops.utils.docs.checks.CheckDocumentationModelFactoryImpl;
import com.dqops.utils.docs.cli.CliCommandDocumentationGenerator;
import com.dqops.utils.docs.cli.CliCommandDocumentationGeneratorImpl;
import com.dqops.utils.docs.cli.CliCommandDocumentationModelFactoryImpl;
import com.dqops.utils.docs.files.*;
import com.dqops.utils.docs.parquetfiles.ParquetFilesDocumentationGenerator;
import com.dqops.utils.docs.parquetfiles.ParquetFilesDocumentationGeneratorImpl;
import com.dqops.utils.docs.parquetfiles.ParquetFilesDocumentationModelFactoryImpl;
import com.dqops.utils.docs.rules.RuleDocumentationGenerator;
import com.dqops.utils.docs.rules.RuleDocumentationGeneratorImpl;
import com.dqops.utils.docs.rules.RuleDocumentationModelFactory;
import com.dqops.utils.docs.rules.RuleDocumentationModelFactoryImpl;
import com.dqops.utils.docs.sensors.SensorDocumentationGenerator;
import com.dqops.utils.docs.sensors.SensorDocumentationGeneratorImpl;
import com.dqops.utils.docs.sensors.SensorDocumentationModelFactory;
import com.dqops.utils.docs.sensors.SensorDocumentationModelFactoryImpl;
import com.dqops.utils.docs.yaml.YamlDocumentationGenerator;
import com.dqops.utils.docs.yaml.YamlDocumentationGeneratorImpl;
import com.dqops.utils.docs.yaml.YamlDocumentationModelFactoryImpl;
import com.dqops.utils.docs.yaml.YamlDocumentationSchemaNode;
import com.dqops.utils.python.PythonCallerServiceImpl;
import com.dqops.utils.python.PythonVirtualEnvServiceImpl;
import com.dqops.utils.reflection.CompletableFutureThreadPoolShutDown;
import com.dqops.utils.reflection.ReflectionService;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.dqops.utils.serialization.JsonSerializerImpl;
import com.dqops.utils.serialization.YamlSerializerImpl;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.*;

/**
 * Class called from the maven build. Generates documentation.
 */
public class GenerateDocumentationPostProcessor {

    /**
     * Main method of the documentation generator that generates markdown documentation files for mkdocs.
     * @param args Command line arguments.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        PythonCallerServiceImpl pythonCaller = null;

        try {
            if (args.length == 0) {
                System.out.println("Documentation generator utility");
                System.out.println("Missing required parameter: <path to the project dir>");
                return;
            }

            System.out.println("Generating documentation for the project: " + args[0]);
            Path projectDir = Path.of(args[0]);
            HandlebarsDocumentationUtilities.configure(projectDir);

            Path dqoHomePath = projectDir.resolve("../home").toAbsolutePath().normalize();
            DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath, true);
            LinkageStore<Class<?>> linkageStore = new LinkageStore<>();

            pythonCaller = createPythonCaller(projectDir);

            generateDocumentationForSensors(projectDir, linkageStore, dqoHomeContext);
            generateDocumentationForRules(projectDir, linkageStore, dqoHomeContext);
            generateDocumentationForCliCommands(projectDir, linkageStore);
            generateDocumentationForChecks(projectDir, linkageStore, dqoHomeContext, pythonCaller);
            generateDocumentationForYaml(projectDir, linkageStore, dqoHomeContext);
            generateDocumentationForParquetFiles(projectDir, linkageStore);

            executePostCorrections(projectDir);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pythonCaller != null) {
                pythonCaller.destroy();
            }

            System.out.println("Documentation was generated");
        }

        CompletableFutureThreadPoolShutDown completableFutureThreadPoolShutDown = new CompletableFutureThreadPoolShutDown();
        completableFutureThreadPoolShutDown.destroy();
    }

    /**
     * Generates documentation for sensors.
     *
     * @param projectRoot    Path to the project root.
     * @param linkageStore
     * @param dqoHomeContext DQOps home instance with access to the sensor references and SQLs.
     */
    public static void generateDocumentationForSensors(Path projectRoot, LinkageStore<Class<?>> linkageStore, DqoHomeContext dqoHomeContext) {
        Path sensorsDocPath = projectRoot.resolve("../docs/reference/sensors").toAbsolutePath().normalize();
        DocumentationFolder currentSensorDocFiles = DocumentationFolderFactory.loadCurrentFiles(sensorsDocPath);
        SensorDocumentationModelFactory sensorDocumentationModelFactory = createSensorDocumentationModelFactory(dqoHomeContext);
        SensorDocumentationGenerator sensorDocumentationGenerator = new SensorDocumentationGeneratorImpl(sensorDocumentationModelFactory);

        DocumentationFolder renderedDocumentation = sensorDocumentationGenerator.renderSensorDocumentation(projectRoot, linkageStore, dqoHomeContext.getDqoHome());
        renderedDocumentation.writeModifiedFiles(currentSensorDocFiles);

        List<String> renderedIndexYaml = renderedDocumentation.generateMkDocsNavigation(4);
        FileContentIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedIndexYaml,
                "########## INCLUDE SENSOR REFERENCE - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE SENSOR REFERENCE");
    }

    /**
     * Create a sensor documentation model factory.
     * @param dqoHomeContext DQOps home.
     * @return Sensor documentation model factory.
     */
    private static SensorDocumentationModelFactory createSensorDocumentationModelFactory(DqoHomeContext dqoHomeContext) {
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        SensorDocumentationModelFactoryImpl sensorDocumentationModelFactory = new SensorDocumentationModelFactoryImpl(dqoHomeContext, specToUiCheckMappingService);
        return sensorDocumentationModelFactory;
    }

    /**
     * Generates documentation for rules.
     *
     * @param projectRoot    Path to the project root.
     * @param linkageStore
     * @param dqoHomeContext DQOps home instance with access to the rule references.
     */
    public static void generateDocumentationForRules(Path projectRoot, LinkageStore<Class<?>> linkageStore, DqoHomeContext dqoHomeContext) {
        Path rulesDocPath = projectRoot.resolve("../docs/reference/rules").toAbsolutePath().normalize();
        DocumentationFolder currentRuleDocFiles = DocumentationFolderFactory.loadCurrentFiles(rulesDocPath);
        RuleDocumentationModelFactory ruleDocumentationModelFactory = createRuleDocumentationModelFactory(projectRoot, dqoHomeContext);
        RuleDocumentationGenerator ruleDocumentationGenerator = new RuleDocumentationGeneratorImpl(ruleDocumentationModelFactory);

        DocumentationFolder renderedDocumentation = ruleDocumentationGenerator.renderRuleDocumentation(projectRoot, linkageStore, dqoHomeContext.getDqoHome());
        renderedDocumentation.writeModifiedFiles(currentRuleDocFiles);

        List<String> renderedIndexYaml = renderedDocumentation.generateMkDocsNavigation(4);
        FileContentIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedIndexYaml,
                "########## INCLUDE RULE REFERENCE - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE RULE REFERENCE");
    }

    /**
     * Create a rule documentation model factory.
     * @param dqoHomeContext DQOps home.
     * @return Rule documentation model factory.
     */
    private static RuleDocumentationModelFactory createRuleDocumentationModelFactory(Path projectRoot, DqoHomeContext dqoHomeContext) {
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        RuleDocumentationModelFactoryImpl ruleDocumentationModelFactory = new RuleDocumentationModelFactoryImpl(projectRoot, dqoHomeContext, specToUiCheckMappingService);
        return ruleDocumentationModelFactory;
    }

    /**
     * Generates documentation for CLI Commands.
     *
     * @param projectRoot  Path to the project root.
     * @param linkageStore
     */
    public static void generateDocumentationForCliCommands(Path projectRoot, LinkageStore<Class<?>> linkageStore) {
        Path cliDocPath = projectRoot.resolve("../docs/command-line-interface").toAbsolutePath().normalize();
        DocumentationFolder currentCliDocFiles = DocumentationFolderFactory.loadCurrentFiles(cliDocPath);
        CliCommandDocumentationGenerator cliCommandDocumentationGenerator = new CliCommandDocumentationGeneratorImpl(new CliCommandDocumentationModelFactoryImpl());

        DocumentationFolder renderedDocumentation = cliCommandDocumentationGenerator.generateDocumentationForCliCommands(projectRoot);
        renderedDocumentation.writeModifiedFiles(currentCliDocFiles);

        List<String> renderedIndexYaml = renderedDocumentation.generateMkDocsNavigation(2);
        FileContentIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedIndexYaml,
                "########## INCLUDE CLI COMMANDS - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE CLI COMMANDS");
    }

    public static void generateDocumentationForChecks(Path projectRoot,
                                                      LinkageStore<Class<?>> linkageStore,
                                                      DqoHomeContext dqoHomeContext,
                                                      PythonCallerServiceImpl pythonCallerService) {
        CheckDocumentationModelFactory checkDocumentationModelFactory = createCheckDocumentationModelFactory(projectRoot, linkageStore, dqoHomeContext, pythonCallerService);
        CheckDocumentationGenerator checkDocumentationGenerator = new CheckDocumentationGeneratorImpl(checkDocumentationModelFactory);

        Path docsRootFolderPath = projectRoot.resolve("../docs").toAbsolutePath().normalize();
        Path checksDocPath = docsRootFolderPath.resolve("checks").toAbsolutePath().normalize();
        DocumentationFolder currentCheckDocFiles = DocumentationFolderFactory.loadCurrentFiles(checksDocPath);
        currentCheckDocFiles.setFolderName("checks");

        Path categoriesConceptDocPath = docsRootFolderPath.resolve("categories-of-data-quality-checks").toAbsolutePath().normalize();
        DocumentationFolder currentCheckCategoriesConceptDocFiles = DocumentationFolderFactory.loadCurrentFiles(categoriesConceptDocPath);
        currentCheckCategoriesConceptDocFiles.setFolderName("categories-of-data-quality-checks");

        Path dqoConceptsDocPath = docsRootFolderPath.resolve("dqo-concepts").toAbsolutePath().normalize();
        DocumentationFolder currentDqoConceptsDocFiles = DocumentationFolderFactory.loadCurrentFiles(dqoConceptsDocPath);
        currentDqoConceptsDocFiles.setFolderName("dqo-concepts");

        DocumentationFolder currentRootFolder = new DocumentationFolder("", docsRootFolderPath);
        currentRootFolder.getSubFolders().add(currentCheckDocFiles);
        currentRootFolder.getSubFolders().add(currentCheckCategoriesConceptDocFiles);
        currentRootFolder.getSubFolders().add(currentDqoConceptsDocFiles);

        DocumentationFolder renderedDocumentation = checkDocumentationGenerator.renderCheckDocumentation(projectRoot, currentRootFolder);
        renderedDocumentation.writeModifiedFiles(currentRootFolder);

        DocumentationFolder newChecksFolder = renderedDocumentation.getFolderByName(CheckDocumentationGenerator.CHECKS_FOLDER_NAME);
        List<String> renderedChecksReferenceIndexYaml = newChecksFolder.generateMkDocsNavigation(2);
        FileContentIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedChecksReferenceIndexYaml,
                "########## INCLUDE CHECK REFERENCE - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE CHECK REFERENCE");

        DocumentationFolder newTypesOfChecksFolder = renderedDocumentation.getFolderByName("categories-of-data-quality-checks");
        newTypesOfChecksFolder.sortByLabelRecursive(Comparator.naturalOrder());
        List<String> renderedCheckTypesIndexYaml = newTypesOfChecksFolder.generateMkDocsNavigation(2);
        FileContentIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedCheckTypesIndexYaml,
                "########## INCLUDE TYPES OF CHECKS REFERENCE - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE TYPES OF CHECKS REFERENCE");
    }

    /**
     * Creates a python caller service.
     * @param projectRoot Path to the project root folder, required to find the DQOps home.
     * @return Python caller service. Must be disposed on exit.
     */
    protected static PythonCallerServiceImpl createPythonCaller(Path projectRoot) {
        DqoConfigurationProperties configurationProperties = new DqoConfigurationProperties();
        configurationProperties.setHome(projectRoot.resolve("../home").toAbsolutePath().normalize().toString());
        DqoUserConfigurationProperties dqoUserConfigurationProperties = new DqoUserConfigurationProperties();
        dqoUserConfigurationProperties.setHome(projectRoot.resolve("../userhome").toAbsolutePath().normalize().toString());
        DqoPythonConfigurationProperties pythonConfigurationProperties = createPythonConfiguration();
        pythonConfigurationProperties.setPythonScriptTimeoutSeconds(5);

        PythonVirtualEnvServiceImpl pythonVirtualEnvService = new PythonVirtualEnvServiceImpl(
                configurationProperties, createPythonConfiguration(), dqoUserConfigurationProperties);
        PythonCallerServiceImpl pythonCallerService = new PythonCallerServiceImpl(
                configurationProperties, pythonConfigurationProperties, new JsonSerializerImpl(), pythonVirtualEnvService);

        return pythonCallerService;
    }

    /**
     * Creates a python configuration that is applicable for the document generation.
     * @return Python configuration.
     */
    @NotNull
    private static DqoPythonConfigurationProperties createPythonConfiguration() {
        DqoPythonConfigurationProperties dqoPythonConfigurationProperties = new DqoPythonConfigurationProperties();
        dqoPythonConfigurationProperties.setPythonScriptTimeoutSeconds(5);
        return dqoPythonConfigurationProperties;
    }

    /**
     * Creates a check documentation model factory.
     *
     * @param projectRoot    Project root path.
     * @param linkageStore
     * @param dqoHomeContext DQOps Home context.
     * @param pythonCallerService Python caller service.
     * @return Check documentation model factory.
     */
    public static CheckDocumentationModelFactory createCheckDocumentationModelFactory(Path projectRoot,
                                                                                      LinkageStore<Class<?>> linkageStore,
                                                                                      final DqoHomeContext dqoHomeContext,
                                                                                      PythonCallerServiceImpl pythonCallerService) {
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        DqoConfigurationProperties configurationProperties = new DqoConfigurationProperties();
        configurationProperties.setHome(projectRoot.resolve("../home").toAbsolutePath().normalize().toString());
        DqoUserConfigurationProperties dqoUserConfigurationProperties = new DqoUserConfigurationProperties();
        dqoUserConfigurationProperties.setHome(projectRoot.resolve("../userhome").toAbsolutePath().normalize().toString());
        DqoPythonConfigurationProperties pythonConfigurationProperties = createPythonConfiguration();


        CheckDocumentationModelFactory checkDocumentationModelFactory = new CheckDocumentationModelFactoryImpl(
                createSimilarCheckMatchingService(reflectionService, dqoHomeContext),
                createSensorDocumentationModelFactory(dqoHomeContext),
                createRuleDocumentationModelFactory(projectRoot, dqoHomeContext),
                new ModelToSpecCheckMappingServiceImpl(reflectionService),
                new YamlSerializerImpl(configurationProperties, null),
                new JsonSerializerImpl(),
                new JinjaTemplateRenderServiceImpl(pythonCallerService, pythonConfigurationProperties),
                linkageStore);
        return checkDocumentationModelFactory;
    }

    public static SimilarCheckMatchingService createSimilarCheckMatchingService(ReflectionService reflectionService, DqoHomeContext dqoHomeContext) {
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                reflectionService, new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        return new SimilarCheckMatchingServiceImpl(specToUiCheckMappingService, new DqoHomeContextFactory() {
            @Override
            public DqoHomeContext openLocalDqoHome() {
                return dqoHomeContext;
            }
        }, new SimilarCheckGroupingKeyFactoryImpl());
    }

    /**
     * Generates documentation for yaml classes.
     *
     * @param projectRoot  Path to the project root.
     * @param linkageStore
     */
    public static void generateDocumentationForYaml(Path projectRoot, LinkageStore<Class<?>> linkageStore, DqoHomeContext dqoHomeContext) {
        Path yamlDocPath = projectRoot.resolve("../docs/reference/yaml").toAbsolutePath().normalize();
        DocumentationFolder currentYamlDocFiles = DocumentationFolderFactory.loadCurrentFiles(yamlDocPath);
        YamlDocumentationGenerator yamlDocumentationGenerator = new YamlDocumentationGeneratorImpl(new YamlDocumentationModelFactoryImpl(linkageStore));

        List<YamlDocumentationSchemaNode> yamlDocumentationSchema = getYamlDocumentationSchema();
        DocumentationFolder renderedDocumentation = yamlDocumentationGenerator.renderYamlDocumentation(projectRoot, linkageStore, yamlDocumentationSchema);
        renderedDocumentation.writeModifiedFiles(currentYamlDocFiles);

        renderedDocumentation.addNestedFile("index.md"); // adding the manually created index file
        List<String> renderedIndexYaml = renderedDocumentation.generateMkDocsNavigation(4);
        FileContentIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedIndexYaml,
                "########## INCLUDE YAML REFERENCE - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE YAML REFERENCE");
    }

    /**
     * Gets the schema describing the layout of files in documentation for Yaml.
     * @return List of Yaml documentation schema nodes, containing data about the layout.
     */
    protected static List<YamlDocumentationSchemaNode> getYamlDocumentationSchema() {
        List<YamlDocumentationSchemaNode> yamlDocumentationSchemaNodes = new ArrayList<>();
        Path profilingPath = Path.of("profiling");
        Path monitoringPath = Path.of("monitoring");
        Path partitionedPath = Path.of("partitioned");

        // Assumption: No cyclical dependencies (considering linkage to different docs pages).
        // Approach:   Ordering in the nodes list matters: bottom-up, starting from the leaves.
        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        TableProfilingCheckCategoriesSpec.class, profilingPath.resolve("table-profiling-checks")
                )
        );
        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        ColumnProfilingCheckCategoriesSpec.class, profilingPath.resolve("column-profiling-checks")
                )
        );

        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        TableDailyMonitoringCheckCategoriesSpec.class, monitoringPath.resolve("table-daily-monitoring-checks")
                )
        );
        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        TableMonthlyMonitoringCheckCategoriesSpec.class, monitoringPath.resolve("table-monthly-monitoring-checks")
                )
        );
        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        ColumnDailyMonitoringCheckCategoriesSpec.class, monitoringPath.resolve("column-daily-monitoring-checks")
                )
        );
        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        ColumnMonthlyMonitoringCheckCategoriesSpec.class, monitoringPath.resolve("column-monthly-monitoring-checks")
                )
        );

        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        TableDailyPartitionedCheckCategoriesSpec.class, partitionedPath.resolve("table-daily-partitioned-checks")
                )
        );
        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        TableMonthlyPartitionedCheckCategoriesSpec.class, partitionedPath.resolve("table-monthly-partitioned-checks")
                )
        );
        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        ColumnDailyPartitionedCheckCategoriesSpec.class, partitionedPath.resolve("column-daily-partitioned-checks")
                )
        );
        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        ColumnMonthlyPartitionedCheckCategoriesSpec.class, partitionedPath.resolve("column-monthly-partitioned-checks")
                )
        );

        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(ConnectionYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(TableYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(DashboardYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(SensorDefinitionYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(ProviderSensorYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(RuleDefinitionYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(CheckDefinitionYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(DefaultNotificationsYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(TableDefaultChecksPatternYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(ColumnDefaultChecksPatternYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(DefaultSchedulesYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(LocalSettingsYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(IncidentNotificationMessage.class)); // the incident notification message format
        return yamlDocumentationSchemaNodes;
    }

    /**
     * Generates documentation for parquet files classes.
     *
     * @param projectRoot  Path to the project root.
     * @param linkageStore
     */
    public static void generateDocumentationForParquetFiles(Path projectRoot, LinkageStore<Class<?>> linkageStore) {
        Path parquetsDocPath = projectRoot.resolve("../docs/reference/parquets").toAbsolutePath().normalize();
        DocumentationFolder currentParquetsDocFiles = DocumentationFolderFactory.loadCurrentFiles(parquetsDocPath);
        ParquetFilesDocumentationGenerator parquetFilesDocumentationGenerator = new ParquetFilesDocumentationGeneratorImpl(new ParquetFilesDocumentationModelFactoryImpl());

        DocumentationFolder renderedDocumentation = parquetFilesDocumentationGenerator.renderParquetDocumentation(projectRoot);
        renderedDocumentation.writeModifiedFiles(currentParquetsDocFiles);

        List<String> renderedIndexParquets = renderedDocumentation.generateMkDocsNavigation(4);
        FileContentIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedIndexParquets,
                "########## INCLUDE PARQUET FILES REFERENCE - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE PARQUET FILES REFERENCE");
    }

    private static void executePostCorrections(Path projectRoot) {
        Path docPath = projectRoot.resolve("../docs").toAbsolutePath().normalize();
        DocumentationFolder docsRootFolder = DocumentationFolderFactory.loadCurrentFiles(docPath);
        DocumentationFolder docsRootFolderCorrected = DocumentationFolderFactory.loadCurrentFiles(docPath);

        DocumentationFolderPostCorrectorService documentationFolderPostCorrectorService =
                new DocumentationFolderPostCorrectorServiceImpl(projectRoot.toAbsolutePath().getParent());
        documentationFolderPostCorrectorService.postProcessCorrect(docsRootFolderCorrected);
        docsRootFolderCorrected.writeModifiedFiles(docsRootFolder);

        DocumentationFolderPostValidatorService documentationFolderPostValidatorService =
                new DocumentationFolderPostValidatorServiceImpl(projectRoot);
        documentationFolderPostValidatorService.postProcessValidate(docsRootFolderCorrected);
    }
}
