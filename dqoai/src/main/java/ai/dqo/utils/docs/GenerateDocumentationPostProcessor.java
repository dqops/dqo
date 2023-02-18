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
package ai.dqo.utils.docs;

import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.core.configuration.DqoPythonConfigurationProperties;
import ai.dqo.core.configuration.DqoUserConfigurationProperties;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderServiceImpl;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.services.check.mapping.UiToSpecCheckMappingServiceImpl;
import ai.dqo.services.check.matching.SimilarCheckMatchingServiceImpl;
import ai.dqo.utils.docs.checks.CheckDocumentationGenerator;
import ai.dqo.utils.docs.checks.CheckDocumentationGeneratorImpl;
import ai.dqo.utils.docs.checks.CheckDocumentationModelFactory;
import ai.dqo.utils.docs.checks.CheckDocumentationModelFactoryImpl;
import ai.dqo.utils.docs.cli.CliCommandDocumentationGenerator;
import ai.dqo.utils.docs.cli.CliCommandDocumentationGeneratorImpl;
import ai.dqo.utils.docs.cli.CliCommandDocumentationModelFactoryImpl;
import ai.dqo.utils.docs.files.DocumentationFolder;
import ai.dqo.utils.docs.files.DocumentationFolderFactory;
import ai.dqo.utils.docs.rules.RuleDocumentationGenerator;
import ai.dqo.utils.docs.rules.RuleDocumentationGeneratorImpl;
import ai.dqo.utils.docs.rules.RuleDocumentationModelFactory;
import ai.dqo.utils.docs.rules.RuleDocumentationModelFactoryImpl;
import ai.dqo.utils.docs.sensors.SensorDocumentationGenerator;
import ai.dqo.utils.docs.sensors.SensorDocumentationGeneratorImpl;
import ai.dqo.utils.docs.sensors.SensorDocumentationModelFactory;
import ai.dqo.utils.docs.sensors.SensorDocumentationModelFactoryImpl;
import ai.dqo.utils.python.PythonCallerServiceImpl;
import ai.dqo.utils.python.PythonVirtualEnvServiceImpl;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import ai.dqo.utils.serialization.JsonSerializerImpl;
import ai.dqo.utils.serialization.YamlSerializerImpl;

import java.nio.file.Path;
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
        Path sensorsDocPath = projectRoot.resolve("../docs/sensors").toAbsolutePath().normalize();
        DocumentationFolder currentSensorDocFiles = DocumentationFolderFactory.loadCurrentFiles(sensorsDocPath);
        SensorDocumentationModelFactory sensorDocumentationModelFactory = createSensorDocumentationModelFactory(dqoHomeContext);
        SensorDocumentationGenerator sensorDocumentationGenerator = new SensorDocumentationGeneratorImpl(sensorDocumentationModelFactory);

        DocumentationFolder renderedDocumentation = sensorDocumentationGenerator.renderSensorDocumentation(projectRoot, dqoHomeContext.getDqoHome());
        renderedDocumentation.writeModifiedFiles(currentSensorDocFiles);

        List<String> renderedIndexYaml = renderedDocumentation.generateMkDocsNavigation(2);
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
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(
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
        Path rulesDocPath = projectRoot.resolve("../docs/rules").toAbsolutePath().normalize();
        DocumentationFolder currentRuleDocFiles = DocumentationFolderFactory.loadCurrentFiles(rulesDocPath);
        RuleDocumentationModelFactory ruleDocumentationModelFactory = createRuleDocumentationModelFactory(projectRoot, dqoHomeContext);
        RuleDocumentationGenerator ruleDocumentationGenerator = new RuleDocumentationGeneratorImpl(ruleDocumentationModelFactory);

        DocumentationFolder renderedDocumentation = ruleDocumentationGenerator.renderRuleDocumentation(projectRoot, dqoHomeContext.getDqoHome());
        renderedDocumentation.writeModifiedFiles(currentRuleDocFiles);

        List<String> renderedIndexYaml = renderedDocumentation.generateMkDocsNavigation(2);
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
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl());
        RuleDocumentationModelFactoryImpl ruleDocumentationModelFactory = new RuleDocumentationModelFactoryImpl(projectRoot, dqoHomeContext, specToUiCheckMappingService);
        return ruleDocumentationModelFactory;
    }

    /**
     * Generates documentation for CLI Commands.
     * @param projectRoot Path to the project root.
     */
    public static void generateDocumentationForCliCommands(Path projectRoot) {
        Path cliDocPath = projectRoot.resolve("../docs/cli").toAbsolutePath().normalize();
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
    public static CheckDocumentationModelFactory createCheckDocumentationModelFactory(Path projectRoot, DqoHomeContext dqoHomeContext){
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(
                reflectionService, new SensorDefinitionFindServiceImpl());
        DqoConfigurationProperties configurationProperties = new DqoConfigurationProperties();
        configurationProperties.setHome(projectRoot.resolve("../home").toAbsolutePath().normalize().toString());
        DqoPythonConfigurationProperties pythonConfigurationProperties = new DqoPythonConfigurationProperties();

        PythonVirtualEnvServiceImpl pythonVirtualEnvService = new PythonVirtualEnvServiceImpl(
                configurationProperties, new DqoPythonConfigurationProperties(), new DqoUserConfigurationProperties());
        PythonCallerServiceImpl pythonCallerService = new PythonCallerServiceImpl(
                configurationProperties, pythonConfigurationProperties, new JsonSerializerImpl(), pythonVirtualEnvService);

        CheckDocumentationModelFactory checkDocumentationModelFactory = new CheckDocumentationModelFactoryImpl(
                dqoHomeContext,
                new SimilarCheckMatchingServiceImpl(specToUiCheckMappingService),
                createSensorDocumentationModelFactory(dqoHomeContext),
                createRuleDocumentationModelFactory(projectRoot, dqoHomeContext),
                new UiToSpecCheckMappingServiceImpl(reflectionService),
                new YamlSerializerImpl(configurationProperties),
                new JinjaTemplateRenderServiceImpl(pythonCallerService, pythonConfigurationProperties));
        return checkDocumentationModelFactory;
    }
}
