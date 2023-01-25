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
package ai.dqo.utils.docs.sensors;

import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.sensors.column.CustomColumnSensorParametersSpec;
import ai.dqo.sensors.table.CustomTableSensorParametersSpec;
import ai.dqo.utils.docs.HandlebarsDocumentationUtilities;
import ai.dqo.utils.docs.files.DocumentationFolder;
import ai.dqo.utils.docs.files.DocumentationMarkdownFile;
import ai.dqo.utils.reflection.TargetClassSearchUtility;
import com.github.jknack.handlebars.Template;

import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.*;

/**
 * Sensor documentation generator that generates documentation for sensors.
 */
public class SensorDocumentationGeneratorImpl implements SensorDocumentationGenerator {
    private SensorDocumentationModelFactory sensorDocumentationModelFactory;

    public SensorDocumentationGeneratorImpl(SensorDocumentationModelFactory sensorDocumentationModelFactory) {
        this.sensorDocumentationModelFactory = sensorDocumentationModelFactory;
    }

    /**
     * Renders documentation for all sensors as markdown files.
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @param dqoHome         DQO home.
     * @return Folder structure with rendered markdown files.
     */
    @Override
    public DocumentationFolder renderSensorDocumentation(Path projectRootPath, DqoHome dqoHome) {
        DocumentationFolder sensorsFolder = new DocumentationFolder();
        sensorsFolder.setFolderName("sensors");
        sensorsFolder.setLinkName("Sensor reference");
        sensorsFolder.setDirectPath(projectRootPath.resolve("../docs/sensors").toAbsolutePath().normalize());

        Template template = HandlebarsDocumentationUtilities.compileTemplate("sensors/sensor_documentation");

        List<SensorDocumentationModel> sensorDocumentationModels = createSensorDocumentationModels(projectRootPath);
        List<SensorGroupedDocumentationModel> sensorGroupedDocumentationModels = groupSensorsByTarget(sensorDocumentationModels);

        for (SensorGroupedDocumentationModel sensorGroupedDocumentation : sensorGroupedDocumentationModels) {
            DocumentationMarkdownFile documentationMarkdownFile = sensorsFolder.addNestedFile(sensorGroupedDocumentation.getTarget() + ".md");
            documentationMarkdownFile.setRenderContext(sensorGroupedDocumentation);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, sensorGroupedDocumentation);
            documentationMarkdownFile.setFileContent(renderedDocument);
        }
        return sensorsFolder;
    }

    /**
     * Creates a list of all sensors used for documentation.
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @return Sensors documentation model list.
     */
    public List<SensorDocumentationModel> createSensorDocumentationModels(Path projectRootPath) {
        List<SensorDocumentationModel> sensorDocumentationModels = new ArrayList<>();

        List<? extends Class<? extends AbstractSensorParametersSpec>> classes = TargetClassSearchUtility.findClasses(
                "ai.dqo.sensors", projectRootPath, AbstractSensorParametersSpec.class);

        for (Class<? extends AbstractSensorParametersSpec> sensorParametersClass : classes) {
            AbstractSensorParametersSpec abstractSensorParametersSpec = createSensorParameterInstance(sensorParametersClass);
            if (abstractSensorParametersSpec instanceof CustomColumnSensorParametersSpec ||
                    abstractSensorParametersSpec instanceof CustomTableSensorParametersSpec) {
                continue;
            }
            SensorDocumentationModel sensorDocumentation = this.sensorDocumentationModelFactory.createSensorDocumentation(abstractSensorParametersSpec);

            if (sensorDocumentation == null) {
                continue; // sensor not found
            }
            sensorDocumentationModels.add(sensorDocumentation);
        }
        return sensorDocumentationModels;
    }

    /**
     * Groups into list sensor documentation models by target ('table' or 'column').
     * @param sensorDocumentationModels List of all sensorDocumentationModel, used to iterating and grouping by target.
     * @return Sensor grouped documentation list.
     */
    public List<SensorGroupedDocumentationModel> groupSensorsByTarget(List<SensorDocumentationModel> sensorDocumentationModels) {
        List<SensorGroupedDocumentationModel> sensorGroupedDocumentationModels = new ArrayList<>();
        SensorGroupedDocumentationModel columnGroupedDocumentationModel = new SensorGroupedDocumentationModel();
        columnGroupedDocumentationModel.setTarget("column");
        SensorGroupedDocumentationModel tableGroupedDocumentationModel = new SensorGroupedDocumentationModel();
        tableGroupedDocumentationModel.setTarget("table");
        Map<String, List<SensorDocumentationModel>> columnSensors = new HashMap<>();
        Map<String, List<SensorDocumentationModel>> tableSensors = new HashMap<>();

        for (SensorDocumentationModel model : sensorDocumentationModels) {
            if (model.getTarget().equals("column")) {
                columnSensors.computeIfAbsent(model.getCategory(), k -> new ArrayList<>()).add(model);
            } else {
                tableSensors.computeIfAbsent(model.getCategory(), k -> new ArrayList<>()).add(model);
            }
        }
        columnGroupedDocumentationModel.setGroupedSensors(columnSensors);
        tableGroupedDocumentationModel.setGroupedSensors(tableSensors);
        Collections.addAll(sensorGroupedDocumentationModels, columnGroupedDocumentationModel, tableGroupedDocumentationModel);
        return sensorGroupedDocumentationModels;
    }

    /**
     * Creates a new instance of the sensor parameter object.
     * @param sensorParametersClass Class type of the sensor parameter.
     * @return Sensor parameter object instance.
     */
    public AbstractSensorParametersSpec createSensorParameterInstance(
            Class<? extends AbstractSensorParametersSpec> sensorParametersClass) {
        try {
            Constructor<? extends AbstractSensorParametersSpec> defaultConstructor = sensorParametersClass.getConstructor();
            AbstractSensorParametersSpec abstractSensorParametersSpec = defaultConstructor.newInstance();
            return abstractSensorParametersSpec;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
