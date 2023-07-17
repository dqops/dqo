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
import com.dqops.checks.column.recurring.ColumnDailyRecurringCheckCategoriesSpec;
import com.dqops.checks.column.recurring.ColumnMonthlyRecurringCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.recurring.TableDailyRecurringCheckCategoriesSpec;
import com.dqops.checks.table.recurring.TableMonthlyRecurringCheckCategoriesSpec;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoPythonConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.incidents.IncidentNotificationMessage;
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
import com.dqops.services.check.matching.SimilarCheckMatchingServiceImpl;
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
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.dqops.utils.serialization.JsonSerializerImpl;
import com.dqops.utils.serialization.YamlSerializerImpl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

            generateDocumentationForSensors(projectDir, dqoHomeContext);
            generateDocumentationForRules(projectDir, dqoHomeContext);
            generateDocumentationForCliCommands(projectDir);
            generateDocumentationForChecks(projectDir, dqoHomeContext);
            generateDocumentationForYaml(projectDir);
            generateDocumentationForParquetFiles(projectDir);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates documentation for sensors.
     * @param projectRoot Path to the project root.
     * @param dqoHomeContext DQO home instance with access to the sensor references and SQLs.
     */
    public static void generateDocumentationForSensors(Path projectRoot, DqoHomeContext dqoHomeContext) {
        Path sensorsDocPath = projectRoot.resolve("../docs/reference/sensors").toAbsolutePath().normalize();
        DocumentationFolder currentSensorDocFiles = DocumentationFolderFactory.loadCurrentFiles(sensorsDocPath);
        SensorDocumentationModelFactory sensorDocumentationModelFactory = createSensorDocumentationModelFactory(dqoHomeContext);
        SensorDocumentationGenerator sensorDocumentationGenerator = new SensorDocumentationGeneratorImpl(sensorDocumentationModelFactory);

        DocumentationFolder renderedDocumentation = sensorDocumentationGenerator.renderSensorDocumentation(projectRoot, dqoHomeContext.getDqoHome());
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
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl());
        SensorDocumentationModelFactoryImpl sensorDocumentationModelFactory = new SensorDocumentationModelFactoryImpl(dqoHomeContext, specToUiCheckMappingService);
        return sensorDocumentationModelFactory;
    }

    /**
     * Generates documentation for rules.
     * @param projectRoot Path to the project root.
     * @param dqoHomeContext DQO home instance with access to the rule references.
     */
    public static void generateDocumentationForRules(Path projectRoot, DqoHomeContext dqoHomeContext) {
        Path rulesDocPath = projectRoot.resolve("../docs/reference/rules").toAbsolutePath().normalize();
        DocumentationFolder currentRuleDocFiles = DocumentationFolderFactory.loadCurrentFiles(rulesDocPath);
        RuleDocumentationModelFactory ruleDocumentationModelFactory = createRuleDocumentationModelFactory(projectRoot, dqoHomeContext);
        RuleDocumentationGenerator ruleDocumentationGenerator = new RuleDocumentationGeneratorImpl(ruleDocumentationModelFactory);

        DocumentationFolder renderedDocumentation = ruleDocumentationGenerator.renderRuleDocumentation(projectRoot, dqoHomeContext.getDqoHome());
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
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl());
        RuleDocumentationModelFactoryImpl ruleDocumentationModelFactory = new RuleDocumentationModelFactoryImpl(projectRoot, dqoHomeContext, specToUiCheckMappingService);
        return ruleDocumentationModelFactory;
    }

    /**
     * Generates documentation for CLI Commands.
     * @param projectRoot Path to the project root.
     */
    public static void generateDocumentationForCliCommands(Path projectRoot) {
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


    public static void generateDocumentationForChecks(Path projectRoot, DqoHomeContext dqoHomeContext) {
        Path checksDocPath = projectRoot.resolve("../docs/checks").toAbsolutePath().normalize();
        DocumentationFolder currentCheckDocFiles = DocumentationFolderFactory.loadCurrentFiles(checksDocPath);
        CheckDocumentationModelFactory checkDocumentationModelFactory = createCheckDocumentationModelFactory(projectRoot, dqoHomeContext);
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
     * Creates a check documentation model factory.
     * @param projectRoot Project root path.
     * @param dqoHomeContext DQO Home context.
     * @return Check documentation model factory.
     */
    public static CheckDocumentationModelFactory createCheckDocumentationModelFactory(Path projectRoot, final DqoHomeContext dqoHomeContext){
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                reflectionService, new SensorDefinitionFindServiceImpl());
        DqoConfigurationProperties configurationProperties = new DqoConfigurationProperties();
        configurationProperties.setHome(projectRoot.resolve("../home").toAbsolutePath().normalize().toString());
        DqoUserConfigurationProperties dqoUserConfigurationProperties = new DqoUserConfigurationProperties();
        dqoUserConfigurationProperties.setHome(projectRoot.resolve("../userhome").toAbsolutePath().normalize().toString());
        DqoPythonConfigurationProperties pythonConfigurationProperties = new DqoPythonConfigurationProperties();

        PythonVirtualEnvServiceImpl pythonVirtualEnvService = new PythonVirtualEnvServiceImpl(
                configurationProperties, new DqoPythonConfigurationProperties(), dqoUserConfigurationProperties);
        PythonCallerServiceImpl pythonCallerService = new PythonCallerServiceImpl(
                configurationProperties, pythonConfigurationProperties, new JsonSerializerImpl(), pythonVirtualEnvService);

        CheckDocumentationModelFactory checkDocumentationModelFactory = new CheckDocumentationModelFactoryImpl(
                new SimilarCheckMatchingServiceImpl(specToUiCheckMappingService, new DqoHomeContextFactory() {
                    @Override
                    public DqoHomeContext openLocalDqoHome() {
                        return dqoHomeContext;
                    }
                }),
                createSensorDocumentationModelFactory(dqoHomeContext),
                createRuleDocumentationModelFactory(projectRoot, dqoHomeContext),
                new ModelToSpecCheckMappingServiceImpl(reflectionService),
                new YamlSerializerImpl(configurationProperties),
                new JinjaTemplateRenderServiceImpl(pythonCallerService, pythonConfigurationProperties));
        return checkDocumentationModelFactory;
    }

    /**
     * Generates documentation for yaml classes.
     * @param projectRoot Path to the project root.
     */
    public static void generateDocumentationForYaml(Path projectRoot) {
        Path yamlDocPath = projectRoot.resolve("../docs/reference/yaml").toAbsolutePath().normalize();
        DocumentationFolder currentYamlDocFiles = DocumentationFolderFactory.loadCurrentFiles(yamlDocPath);
        YamlDocumentationGenerator yamlDocumentationGenerator = new YamlDocumentationGeneratorImpl(new YamlDocumentationModelFactoryImpl());

        List<YamlDocumentationSchemaNode> yamlDocumentationSchema = getYamlDocumentationSchema();
        DocumentationFolder renderedDocumentation = yamlDocumentationGenerator.renderYamlDocumentation(projectRoot, yamlDocumentationSchema);
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
    protected static List<YamlDocumentationSchemaNode> getYamlDocumentationSchema() {
        List<YamlDocumentationSchemaNode> yamlDocumentationSchemaNodes = new ArrayList<>();

        Path profilingPath = Path.of("profiling");
        Path recurringPath = Path.of("recurring");
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
                        TableDailyRecurringCheckCategoriesSpec.class, recurringPath.resolve("table-daily-recurring-checks")
                )
        );
        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        TableMonthlyRecurringCheckCategoriesSpec.class, recurringPath.resolve("table-monthly-recurring-checks")
                )
        );
        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        ColumnDailyRecurringCheckCategoriesSpec.class, recurringPath.resolve("column-daily-recurring-checks")
                )
        );
        yamlDocumentationSchemaNodes.add(
                new YamlDocumentationSchemaNode(
                        ColumnMonthlyRecurringCheckCategoriesSpec.class, recurringPath.resolve("column-monthly-recurring-checks")
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

    /**
     * Generates documentation for parquet files classes.
     *
     * @param projectRoot Path to the project root.
     */
    public static void generateDocumentationForParquetFiles(Path projectRoot) {
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
