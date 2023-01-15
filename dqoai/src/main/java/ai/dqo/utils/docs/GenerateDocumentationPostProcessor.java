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

import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import ai.dqo.rest.models.checks.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.utils.docs.files.DocumentationFolder;
import ai.dqo.utils.docs.files.DocumentationFolderFactory;
import ai.dqo.utils.docs.sensors.SensorDocumentationGenerator;
import ai.dqo.utils.docs.sensors.SensorDocumentationGeneratorImpl;
import ai.dqo.utils.docs.sensors.SensorDocumentationModelFactory;
import ai.dqo.utils.docs.sensors.SensorDocumentationModelFactoryImpl;
import ai.dqo.utils.reflection.ReflectionServiceImpl;

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
                "########## END INCLUDE");
    }

    /**
     * Create a sensor documentation model factory.
     * @param dqoHomeContext DQO home.
     * @return Sensor documentation model factory.
     */
    private static SensorDocumentationModelFactory createSensorDocumentationModelFactory(DqoHomeContext dqoHomeContext) {
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(new ReflectionServiceImpl());
        SensorDocumentationModelFactoryImpl sensorDocumentationModelFactory = new SensorDocumentationModelFactoryImpl(dqoHomeContext, specToUiCheckMappingService);
        return sensorDocumentationModelFactory;
    }
}
