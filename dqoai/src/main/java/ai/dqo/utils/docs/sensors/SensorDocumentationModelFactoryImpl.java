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

import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionList;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapper;
import ai.dqo.metadata.fields.ParameterDefinitionsListSpec;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingService;
import ai.dqo.services.check.mapping.models.UIFieldModel;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Sensor documentation model factory that creates a sensor documentation.
 * It should be only used from post processor classes that are called by Maven during build.
 */
public class SensorDocumentationModelFactoryImpl implements SensorDocumentationModelFactory {
    private static final CommentFormatter commentFormatter = new CommentFormatter();
    private DqoHomeContext dqoHomeContext;
    private SpecToUiCheckMappingService specToUiCheckMappingService;

    /**
     * Creates a sensor documentation model factory.
     * @param dqoHomeContext DQO User home context.
     * @param specToUiCheckMappingService Specification to UI model factory, used to get documentation of the sensor parameters.
     */
    public SensorDocumentationModelFactoryImpl(DqoHomeContext dqoHomeContext,
                                               SpecToUiCheckMappingService specToUiCheckMappingService) {
        this.dqoHomeContext = dqoHomeContext;
        this.specToUiCheckMappingService = specToUiCheckMappingService;
    }

    /**
     * Create a sensor documentation model for a given sensor parameter class instance.
     * @param sensorParametersSpec Sensor parameter instance.
     * @return Sensor documentation model.
     */
    @Override
    public SensorDocumentationModel createSensorDocumentation(AbstractSensorParametersSpec sensorParametersSpec) {
        SensorDocumentationModel documentationModel = new SensorDocumentationModel();
        ClassJavadoc classJavadoc = RuntimeJavadoc.getJavadoc(sensorParametersSpec.getClass());
        if (classJavadoc != null) {
            if (classJavadoc.getComment() != null) {
                String formattedClassComment = commentFormatter.format(classJavadoc.getComment());
                documentationModel.setSensorParametersJavaDoc(formattedClassComment);
            }
        }

        String sensorDefinitionName = sensorParametersSpec.getSensorDefinitionName();
        documentationModel.setFullSensorName(sensorDefinitionName);
        String[] sensorParts = StringUtils.split(sensorDefinitionName, '/');
        documentationModel.setTarget(sensorParts[0]);
        documentationModel.setCategory(sensorParts[1]);
        documentationModel.setSensorName(sensorParts[2]);

        SensorDefinitionList sensors = this.dqoHomeContext.getDqoHome().getSensors();
        SensorDefinitionWrapper sensorDefinitionWrapper = sensors.getByObjectName(sensorDefinitionName, true);
        if (sensorDefinitionWrapper == null) {
            System.err.println("Sensor definition for sensor " + sensorDefinitionName + " was not found");
            return null;
        }
        sensorDefinitionWrapper = sensorDefinitionWrapper.clone();

        documentationModel.setSqlTemplates(createSqlTemplates(sensorDefinitionWrapper));

        documentationModel.setDefinition(sensorDefinitionWrapper);

        List<UIFieldModel> fieldsForSensorParameters = this.specToUiCheckMappingService.createFieldsForSensorParameters(sensorParametersSpec);
        ParameterDefinitionsListSpec fieldDefinitionsList = new ParameterDefinitionsListSpec();
        fieldsForSensorParameters.forEach(uiFieldModel -> fieldDefinitionsList.add(uiFieldModel.getDefinition()));
        sensorDefinitionWrapper.getSpec().setFields(fieldDefinitionsList);  // replacing to use the most recent definition from the code

        return documentationModel;
    }

    /**
     * Create sql templates for each provider.
     * @param sensorDefinition Sensor definition wrapper.
     * @return Sql templates.
     */
    private Map<String, List<String>> createSqlTemplates(SensorDefinitionWrapper sensorDefinition) {
        Map<String, List<String>> sqlTemplates = new HashMap<>();
        for (ProviderSensorDefinitionWrapper providerSensor : sensorDefinition.getProviderSensors()) {
            sqlTemplates.put(providerSensor.getProvider().toString(), List.of(providerSensor.getSqlTemplate().split("\\r?\\n|\\r")));
        }

        Comparator<String> comparator = String::compareTo;
        Map<String, List<String>> sortedSqlTemplates = new TreeMap<>(comparator);
        sortedSqlTemplates.putAll(sqlTemplates);

        return sortedSqlTemplates;
    }
}
