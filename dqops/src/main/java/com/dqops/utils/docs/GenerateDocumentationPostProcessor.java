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
import com.dqops.metadata.storage.localfiles.dashboards.DashboardYaml;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import com.dqops.metadata.storage.localfiles.ruledefinitions.RuleDefinitionYaml;
import com.dqops.metadata.storage.localfiles.sensordefinitions.ProviderSensorYaml;
import com.dqops.metadata.storage.localfiles.sensordefinitions.SensorDefinitionYaml;
import com.dqops.metadata.storage.localfiles.settings.SettingsYaml;
import com.dqops.metadata.storage.localfiles.sources.ConnectionYaml;
import com.dqops.metadata.storage.localfiles.sources.TableYaml;
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
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationFolderFactory;
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
import com.dqops.utils.reflection.ReflectionService;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.dqops.utils.serialization.JsonSerializerImpl;
import com.dqops.utils.serialization.YamlSerializerImpl;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

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
            DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath);
            HandledClassesLinkageStore linkageStore = new HandledClassesLinkageStore();

            pythonCaller = createPythonCaller(projectDir);

            generateDocumentationForSensors(projectDir, linkageStore, dqoHomeContext);
            generateDocumentationForRules(projectDir, linkageStore, dqoHomeContext);
            generateDocumentationForCliCommands(projectDir, linkageStore);
            generateDocumentationForChecks(projectDir, linkageStore, dqoHomeContext, pythonCaller);
            generateDocumentationForYaml(projectDir, linkageStore, dqoHomeContext);
            generateDocumentationForParquetFiles(projectDir, linkageStore);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pythonCaller != null) {
                pythonCaller.destroy();
            }

            System.out.println("Documentation was generated");
        }
    }

    /**
     * Generates documentation for sensors.
     *
     * @param projectRoot    Path to the project root.
     * @param linkageStore
     * @param dqoHomeContext DQO home instance with access to the sensor references and SQLs.
     */
    public static void generateDocumentationForSensors(Path projectRoot, HandledClassesLinkageStore linkageStore, DqoHomeContext dqoHomeContext) {
        Path sensorsDocPath = projectRoot.resolve("../docs/reference/sensors").toAbsolutePath().normalize();
        DocumentationFolder currentSensorDocFiles = DocumentationFolderFactory.loadCurrentFiles(sensorsDocPath);
        SensorDocumentationModelFactory sensorDocumentationModelFactory = createSensorDocumentationModelFactory(dqoHomeContext);
        SensorDocumentationGenerator sensorDocumentationGenerator = new SensorDocumentationGeneratorImpl(sensorDocumentationModelFactory);

        DocumentationFolder renderedDocumentation = sensorDocumentationGenerator.renderSensorDocumentation(projectRoot, linkageStore, dqoHomeContext.getDqoHome());
        renderedDocumentation.writeModifiedFiles(currentSensorDocFiles);

        List<String> renderedIndexYaml = renderedDocumentation.generateMkDocsNavigation(4);
        MkDocsIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedIndexYaml,
                "########## INCLUDE SENSOR REFERENCE - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE SENSOR REFERENCE");
    }

    /**
     * Create a sensor documentation model factory.
     * @param dqoHomeContext DQO home.
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
     * @param dqoHomeContext DQO home instance with access to the rule references.
     */
    public static void generateDocumentationForRules(Path projectRoot, HandledClassesLinkageStore linkageStore, DqoHomeContext dqoHomeContext) {
        Path rulesDocPath = projectRoot.resolve("../docs/reference/rules").toAbsolutePath().normalize();
        DocumentationFolder currentRuleDocFiles = DocumentationFolderFactory.loadCurrentFiles(rulesDocPath);
        RuleDocumentationModelFactory ruleDocumentationModelFactory = createRuleDocumentationModelFactory(projectRoot, dqoHomeContext);
        RuleDocumentationGenerator ruleDocumentationGenerator = new RuleDocumentationGeneratorImpl(ruleDocumentationModelFactory);

        DocumentationFolder renderedDocumentation = ruleDocumentationGenerator.renderRuleDocumentation(projectRoot, linkageStore, dqoHomeContext.getDqoHome());
        renderedDocumentation.writeModifiedFiles(currentRuleDocFiles);

        List<String> renderedIndexYaml = renderedDocumentation.generateMkDocsNavigation(4);
        MkDocsIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedIndexYaml,
                "########## INCLUDE RULE REFERENCE - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE RULE REFERENCE");
    }

    /**
     * Create a rule documentation model factory.
     * @param dqoHomeContext DQO home.
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
    public static void generateDocumentationForCliCommands(Path projectRoot, HandledClassesLinkageStore linkageStore) {
        Path cliDocPath = projectRoot.resolve("../docs/command-line-interface").toAbsolutePath().normalize();
        DocumentationFolder currentCliDocFiles = DocumentationFolderFactory.loadCurrentFiles(cliDocPath);
        CliCommandDocumentationGenerator cliCommandDocumentationGenerator = new CliCommandDocumentationGeneratorImpl(new CliCommandDocumentationModelFactoryImpl());

        DocumentationFolder renderedDocumentation = cliCommandDocumentationGenerator.generateDocumentationForCliCommands(projectRoot);
        renderedDocumentation.writeModifiedFiles(currentCliDocFiles);

        List<String> renderedIndexYaml = renderedDocumentation.generateMkDocsNavigation(2);
        MkDocsIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedIndexYaml,
                "########## INCLUDE CLI COMMANDS - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE CLI COMMANDS");
    }


    public static void generateDocumentationForChecks(Path projectRoot,
                                                      HandledClassesLinkageStore linkageStore,
                                                      DqoHomeContext dqoHomeContext,
                                                      PythonCallerServiceImpl pythonCallerService) {
        Path checksDocPath = projectRoot.resolve("../docs/checks").toAbsolutePath().normalize();
        DocumentationFolder currentCheckDocFiles = DocumentationFolderFactory.loadCurrentFiles(checksDocPath);
        CheckDocumentationModelFactory checkDocumentationModelFactory = createCheckDocumentationModelFactory(projectRoot, linkageStore, dqoHomeContext, pythonCallerService);
        CheckDocumentationGenerator checkDocumentationGenerator = new CheckDocumentationGeneratorImpl(checkDocumentationModelFactory);

        DocumentationFolder renderedDocumentation = checkDocumentationGenerator.renderCheckDocumentation(projectRoot);
        renderedDocumentation.writeModifiedFiles(currentCheckDocFiles);

        List<String> renderedIndexYaml = renderedDocumentation.generateMkDocsNavigation(2);
        MkDocsIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedIndexYaml,
                "########## INCLUDE CHECK REFERENCE - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE CHECK REFERENCE");
    }

    /**
     * Creates a python caller service.
     * @param projectRoot Path to the project root folder, required to find the DQO home.
     * @return Python caller service. Must be disposed on exit.
     */
    protected static PythonCallerServiceImpl createPythonCaller(Path projectRoot) {
        DqoConfigurationProperties configurationProperties = new DqoConfigurationProperties();
        configurationProperties.setHome(projectRoot.resolve("../home").toAbsolutePath().normalize().toString());
        DqoUserConfigurationProperties dqoUserConfigurationProperties = new DqoUserConfigurationProperties();
        dqoUserConfigurationProperties.setHome(projectRoot.resolve("../userhome").toAbsolutePath().normalize().toString());
        DqoPythonConfigurationProperties pythonConfigurationProperties = createPythonConfiguration();

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
     * @param dqoHomeContext DQO Home context.
     * @param pythonCallerService Python caller service.
     * @return Check documentation model factory.
     */
    public static CheckDocumentationModelFactory createCheckDocumentationModelFactory(Path projectRoot,
                                                                                      HandledClassesLinkageStore linkageStore,
                                                                                      final DqoHomeContext dqoHomeContext,
                                                                                      PythonCallerServiceImpl pythonCallerService){
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
                new JinjaTemplateRenderServiceImpl(pythonCallerService, pythonConfigurationProperties), linkageStore);
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
        });
    }

    /**
     * Generates documentation for yaml classes.
     *
     * @param projectRoot  Path to the project root.
     * @param linkageStore
     */
    public static void generateDocumentationForYaml(Path projectRoot, HandledClassesLinkageStore linkageStore, DqoHomeContext dqoHomeContext) {
        Path yamlDocPath = projectRoot.resolve("../docs/reference/yaml").toAbsolutePath().normalize();
        DocumentationFolder currentYamlDocFiles = DocumentationFolderFactory.loadCurrentFiles(yamlDocPath);
        YamlDocumentationGenerator yamlDocumentationGenerator = new YamlDocumentationGeneratorImpl(new YamlDocumentationModelFactoryImpl(linkageStore));

        List<YamlDocumentationSchemaNode> yamlDocumentationSchema = getYamlDocumentationSchema(dqoHomeContext);
        DocumentationFolder renderedDocumentation = yamlDocumentationGenerator.renderYamlDocumentation(projectRoot, linkageStore, yamlDocumentationSchema);
        renderedDocumentation.writeModifiedFiles(currentYamlDocFiles);

        List<String> renderedIndexYaml = renderedDocumentation.generateMkDocsNavigation(4);
        MkDocsIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedIndexYaml,
                "########## INCLUDE YAML REFERENCE - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE YAML REFERENCE");
    }

    /**
     * Gets the schema describing the layout of files in documentation for Yaml.
     * @return List of Yaml documentation schema nodes, containing data about the layout.
     */
    protected static List<YamlDocumentationSchemaNode> getYamlDocumentationSchema(DqoHomeContext dqoHomeContext) {
//        ReflectionService reflectionService = new ReflectionServiceImpl();
//        SimilarCheckMatchingService similarCheckMatchingService = createSimilarCheckMatchingService(
//                reflectionService, dqoHomeContext);
//
//        List<YamlDocumentationSchemaNode> yamlDocumentationSchemaNodes = getYamlChecksDocumentationSchema(similarCheckMatchingService);

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
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(DashboardYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(ProviderSensorYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(RuleDefinitionYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(SensorDefinitionYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(SettingsYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(TableYaml.class));
        yamlDocumentationSchemaNodes.add(YamlDocumentationSchemaNode.fromClass(IncidentNotificationMessage.class)); // the incident notification message format
        return yamlDocumentationSchemaNodes;
    }

    protected static List<YamlDocumentationSchemaNode> getYamlChecksDocumentationSchema(SimilarCheckMatchingService similarCheckMatchingService) {
        Path checksPath = Path.of("checks");

        Collection<SimilarChecksGroup> tableChecks = similarCheckMatchingService.findSimilarTableChecks().getSimilarCheckGroups();
        List<YamlDocumentationSchemaNode> tableChecksNodes = getYamlCheckGroupsSchema(tableChecks, checksPath.resolve("table"));

        Collection<SimilarChecksGroup> columnChecks = similarCheckMatchingService.findSimilarColumnChecks().getSimilarCheckGroups();
        return new ArrayList<>();
    }

    protected static List<YamlDocumentationSchemaNode> getYamlCheckGroupsSchema(Collection<SimilarChecksGroup> checksGroups, Path schemaNodePathPrefix) {
        List<YamlDocumentationSchemaNode> nodesForChecksGroups = new ArrayList<>();
        List<String> categories = checksGroups.stream().map(SimilarChecksGroup::getFirstCheckCategory).distinct().sorted().collect(Collectors.toList());
        for (String category : categories) {
            Path categoryPath = schemaNodePathPrefix.resolve(category);
            List<SimilarChecksGroup> similarChecksInCategory = checksGroups.stream().filter(checksGroup -> checksGroup.getFirstCheckCategory().equals(category)).collect(Collectors.toList());

        }

        for (SimilarChecksGroup checksGroup : checksGroups) {
            String checkCategoryName = checksGroup.getFirstCheckCategory();
            //if (handledCategories.contains(checkCategoryName)) {
              //  continue;
            //}

            List<SimilarCheckModel> similarCheckModels = checksGroup.getSimilarChecks();

            YamlDocumentationSchemaNode schemaNode = new YamlDocumentationSchemaNode(
                    null,
                    schemaNodePathPrefix.resolve(checkCategoryName)
            );



            //handledCategories.add(checkCategoryName);
        }
        List<String> r = checksGroups.stream().map(similarChecksGroup -> similarChecksGroup.getFirstCheckCategory())


                .distinct().sorted().collect(Collectors.toList());
        return null;
    }

    /**
     * Generates documentation for parquet files classes.
     *
     * @param projectRoot  Path to the project root.
     * @param linkageStore
     */
    public static void generateDocumentationForParquetFiles(Path projectRoot, HandledClassesLinkageStore linkageStore) {
        Path parquetsDocPath = projectRoot.resolve("../docs/reference/parquets").toAbsolutePath().normalize();
        DocumentationFolder currentParquetsDocFiles = DocumentationFolderFactory.loadCurrentFiles(parquetsDocPath);
        ParquetFilesDocumentationGenerator parquetFilesDocumentationGenerator = new ParquetFilesDocumentationGeneratorImpl(new ParquetFilesDocumentationModelFactoryImpl());

        DocumentationFolder renderedDocumentation = parquetFilesDocumentationGenerator.renderParquetDocumentation(projectRoot);
        renderedDocumentation.writeModifiedFiles(currentParquetsDocFiles);

        List<String> renderedIndexParquets = renderedDocumentation.generateMkDocsNavigation(4);
        MkDocsIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
                renderedIndexParquets,
                "########## INCLUDE PARQUET FILES REFERENCE - DO NOT MODIFY MANUALLY",
                "########## END INCLUDE PARQUET FILES REFERENCE");
    }
}
