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
package com.dqops.utils.docs.sensors;

import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.sensors.CustomSensorParametersSpec;
import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.checks.CheckCategoryDocumentationModel;
import com.dqops.utils.docs.checks.MainPageCheckDocumentationModel;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.dqops.utils.reflection.TargetClassSearchUtility;
import com.github.jknack.handlebars.Template;

import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     *
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @param linkageStore
     * @param dqoHome         DQOps home.
     * @return Folder structure with rendered markdown files.
     */
    @Override
    public DocumentationFolder renderSensorDocumentation(Path projectRootPath, LinkageStore<Class<?>> linkageStore, DqoHome dqoHome) {
        DocumentationFolder sensorsFolder = new DocumentationFolder();
        sensorsFolder.setFolderName("reference/sensors");
        sensorsFolder.setLinkName("Data quality sensors");
        Path sensorsPath = Path.of("docs", "reference", "sensors");
        sensorsFolder.setDirectPath(projectRootPath.resolve("..").resolve(sensorsPath).toAbsolutePath().normalize());

        List<SensorDocumentationModel> sensorDocumentationModels = createSensorDocumentationModels(projectRootPath);
        List<SensorGroupedDocumentationModel> sensorGroupedDocumentationModels = groupSensors(sensorDocumentationModels);

        MainPageSensorDocumentationModel mainPageSensorDocumentationModel = renderIndexPage(sensorGroupedDocumentationModels);

        Template mainPageTemplate = HandlebarsDocumentationUtilities.compileTemplate("sensors/main_page_documentation");
        DocumentationMarkdownFile mainPageDocumentationMarkdownFile = sensorsFolder.addNestedFile("index" + ".md");
        mainPageDocumentationMarkdownFile.setRenderContext(mainPageSensorDocumentationModel);

        String renderedMainPageDocument = HandlebarsDocumentationUtilities.renderTemplate(mainPageTemplate, mainPageSensorDocumentationModel);
        mainPageDocumentationMarkdownFile.setFileContent(renderedMainPageDocument);


        Template template = HandlebarsDocumentationUtilities.compileTemplate("sensors/sensor_documentation");

        for (SensorGroupedDocumentationModel sensorGroupedDocumentation : sensorGroupedDocumentationModels) {
            Path sensorFilePath = Path.of(sensorGroupedDocumentation.getTarget(),
                    sensorGroupedDocumentation.getCategory() + "-" +
                            sensorGroupedDocumentation.getTarget().replace(' ', '-') + "-sensors");
            DocumentationMarkdownFile documentationMarkdownFile = sensorsFolder.addNestedFile(sensorFilePath + ".md");
            documentationMarkdownFile.setRenderContext(sensorGroupedDocumentation);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, sensorGroupedDocumentation);
            documentationMarkdownFile.setFileContent(renderedDocument);

            for (SensorDocumentationModel sensorDocumentationModel : sensorGroupedDocumentation.collectedSensors) {
                linkageStore.put(
                        sensorDocumentationModel.getSensorParametersClazz(),
                        Path.of("/")
                                .resolve(sensorsPath)
                                .resolve(sensorFilePath)
                                .resolve("#" + sensorDocumentationModel.getSensorName().replace('_', '-'))
                );
            }
        }

        for (DocumentationFolder targetFolder : sensorsFolder.getSubFolders()) {
            List<SensorGroupedDocumentationModel> sensorsInFolder = sensorGroupedDocumentationModels.stream()
                    .filter(sensorModel -> sensorModel.getTarget().equals(targetFolder.getFolderName()))
                    .collect(Collectors.toList());
            MainPageSensorDocumentationModel indexPageSensorDocumentationModel = renderIndexPage(sensorsInFolder);
            DocumentationMarkdownFile indexFile = targetFolder.addNestedFile("index" + ".md");
            indexFile.setRenderContext(indexPageSensorDocumentationModel);
            String renderedIndexDocument = HandlebarsDocumentationUtilities.renderTemplate(mainPageTemplate, indexPageSensorDocumentationModel);
            indexFile.setFileContent(renderedIndexDocument);
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
                "com.dqops.sensors", projectRootPath, AbstractSensorParametersSpec.class);

        Set<String> documentedSensorNames = new HashSet<>();

        for (Class<? extends AbstractSensorParametersSpec> sensorParametersClass : classes) {
            AbstractSensorParametersSpec abstractSensorParametersSpec = createSensorParameterInstance(sensorParametersClass);
            if (abstractSensorParametersSpec instanceof CustomSensorParametersSpec) {
                continue;
            }

            String sensorDefinitionName = abstractSensorParametersSpec.getSensorDefinitionName();
            if (documentedSensorNames.contains(sensorDefinitionName)) {
                continue; // additional sensor parameters specification class with different parameters for an already documented sensor
            }
            documentedSensorNames.add(sensorDefinitionName);

            SensorDocumentationModel sensorDocumentation = this.sensorDocumentationModelFactory.createSensorDocumentation(abstractSensorParametersSpec);

            if (sensorDocumentation == null) {
                continue; // sensor not found
            }
            sensorDocumentationModels.add(sensorDocumentation);
        }
        return sensorDocumentationModels;
    }

    /**
     * Groups into list sensor grouped documentation models by target ('table' or 'column') and sensor category.
     * @param sensorDocumentationModels List of all sensorDocumentationModel, used to iterating and grouping by target and sensor category.
     * @return Sensor grouped documentation list.
     */
    public List<SensorGroupedDocumentationModel> groupSensors(List<SensorDocumentationModel> sensorDocumentationModels) {
        sensorDocumentationModels.sort(Comparator.comparing(SensorDocumentationModel::getFullSensorName));

        List<SensorGroupedDocumentationModel> sensorGroupedDocumentationModels = new ArrayList<>();
        Map<String, Map<String, List<SensorDocumentationModel>>> groupedSensors = new HashMap<>();

        for (SensorDocumentationModel sensor : sensorDocumentationModels) {
            Map<String, List<SensorDocumentationModel>> categoryLevelSensorsGroup;
            List<SensorDocumentationModel> sensorDocumentationModelsInCategory;

            if (groupedSensors.containsKey(sensor.getTarget())) {
                categoryLevelSensorsGroup = groupedSensors.get(sensor.getTarget());
                if (categoryLevelSensorsGroup.containsKey(sensor.getCategory())) {
                    sensorDocumentationModelsInCategory = categoryLevelSensorsGroup.get(sensor.getCategory());
                } else {
                    sensorDocumentationModelsInCategory = new ArrayList<>();
                    categoryLevelSensorsGroup.put(sensor.getCategory(), sensorDocumentationModelsInCategory);
                }
            } else {
                categoryLevelSensorsGroup = new HashMap<>();
                sensorDocumentationModelsInCategory = new ArrayList<>();
                categoryLevelSensorsGroup.put(sensor.getCategory(), sensorDocumentationModelsInCategory);
                groupedSensors.put(sensor.getTarget(), categoryLevelSensorsGroup);

            }
            sensorDocumentationModelsInCategory.add(sensor);
        }

        Comparator<String> reverseComparator = Comparator.reverseOrder();
        Map<String, Map<String, List<SensorDocumentationModel>>> reversedOrderGroupedSensor = new TreeMap<>(reverseComparator);
        reversedOrderGroupedSensor.putAll(groupedSensors);

        for (Map.Entry<String, Map<String, List<SensorDocumentationModel>>> targetLevelGroup : reversedOrderGroupedSensor.entrySet()) {
            String target = targetLevelGroup.getKey();
            Map<String, List<SensorDocumentationModel>> categoryLevelGroup = targetLevelGroup.getValue();

            Comparator<String> comparator = String::compareTo;
            Map<String, List<SensorDocumentationModel>> sortedCategoryLevelGroup = new TreeMap<>(comparator);
            sortedCategoryLevelGroup.putAll(categoryLevelGroup);

            for (Map.Entry<String, List<SensorDocumentationModel>> category : sortedCategoryLevelGroup.entrySet()) {
                String categoryName = category.getKey();
                List<SensorDocumentationModel> sensorDocumentationModelsInCategory = category.getValue();

                SensorGroupedDocumentationModel sensorGroupedDocumentationModel = new SensorGroupedDocumentationModel();
                sensorGroupedDocumentationModel.setTarget(target);
                sensorGroupedDocumentationModel.setCategory(categoryName);
                sensorGroupedDocumentationModel.setCollectedSensors(sensorDocumentationModelsInCategory);

                sensorGroupedDocumentationModels.add(sensorGroupedDocumentationModel);
            }
        }
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
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private MainPageSensorDocumentationModel renderIndexPage(List<SensorGroupedDocumentationModel> sensorCategories) {
        List<String> sensorTargets = sensorCategories.stream()
                .map(SensorGroupedDocumentationModel::getTarget)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        String singleTarget = null;
        if (sensorTargets.size() == 1) {
            singleTarget = sensorTargets.get(0);
        }

        MainPageSensorDocumentationModel indexPageSensorDocumentationModel = new MainPageSensorDocumentationModel();
        String header = "Sensors";
        if (singleTarget != null) {
            header = header + "/" + singleTarget;
            indexPageSensorDocumentationModel.setHelpText(String.format(
                    "This is a list of %s sensors in DQOps broken down by category and a brief description of what they do.",
                    singleTarget));
        } else {
            indexPageSensorDocumentationModel.setHelpText("This is a list of sensors in DQOps broken down by category and a brief description of what they do.");
        }

        indexPageSensorDocumentationModel.setHeader(header);

        indexPageSensorDocumentationModel.setSensorTargets(sensorTargets);
        indexPageSensorDocumentationModel.setSensors(sensorCategories);
        return indexPageSensorDocumentationModel;
    }
}
