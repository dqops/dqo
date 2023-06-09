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
package ai.dqo.utils.specs;

import ai.dqo.connectors.ProviderType;
import ai.dqo.metadata.definitions.sensors.*;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import ai.dqo.metadata.fields.ParameterDefinitionsListSpec;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.sensors.CustomSensorParametersSpec;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingService;
import ai.dqo.utils.reflection.TargetClassSearchUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service used during the build process (called from a maven profile) that updates the yaml definitions in the DQO Home sensor folder,
 * updating correct list of fields that were detected using reflection.
 */
@Component
public class SensorDefinitionDefaultSpecUpdateServiceImpl implements SensorDefinitionDefaultSpecUpdateService {
    private DqoHomeContext dqoHomeContext;
    private SpecToUiCheckMappingService specToUiCheckMappingService;

    @Autowired
    public SensorDefinitionDefaultSpecUpdateServiceImpl(DqoHomeContext dqoHomeContext,
                                                        SpecToUiCheckMappingService specToUiCheckMappingService) {
        this.dqoHomeContext = dqoHomeContext;
        this.specToUiCheckMappingService = specToUiCheckMappingService;
    }

    /**
     * Checks if all sensor definition yaml files in the DQO Home rule folder have a correct list of parameters, matching the fields used in the Java spec classes.
     * @param projectRootPath Path to the dqoai module folder (code folder).
     * @param dqoHomeContext DQO Home context.
     */
    @Override
    public void updateSensorSpecifications(Path projectRootPath, DqoHomeContext dqoHomeContext) {
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        List<? extends Class<? extends AbstractSensorParametersSpec>> classes = TargetClassSearchUtility.findClasses(
                "ai.dqo.sensors", projectRootPath, AbstractSensorParametersSpec.class);
        Set<String> processedSensorName = new LinkedHashSet<>();

        for (Class<? extends AbstractSensorParametersSpec> sensorParametersClass : classes) {
            AbstractSensorParametersSpec abstractSensorParametersSpec = createSensorParameterInstance(sensorParametersClass);
            if (abstractSensorParametersSpec instanceof CustomSensorParametersSpec) {
                continue;
            }

            String sensorDefinitionName = abstractSensorParametersSpec.getSensorDefinitionName();
            if (processedSensorName.contains(sensorDefinitionName)) {
                continue;
            }
            processedSensorName.add(sensorDefinitionName);

            SensorDefinitionWrapper sensorDefinitionWrapper = dqoHome.getSensors().getByObjectName(sensorDefinitionName, true);
            if (sensorDefinitionWrapper == null) {
                if (abstractSensorParametersSpec.getAlwaysSupportedOnAllProviders()) {
                    sensorDefinitionWrapper = dqoHome.getSensors().createAndAddNew(sensorDefinitionName);
                    sensorDefinitionWrapper.setSpec(new SensorDefinitionSpec());
                } else {
                    System.err.println("Missing sensor definition for " + sensorDefinitionName);
                    continue;
                }
            }

            ProviderSensorDefinitionList providerSensors = sensorDefinitionWrapper.getProviderSensors();
            for (ProviderSensorDefinitionWrapper providerSensorWrapper : providerSensors) {
                String sqlTemplate = providerSensorWrapper.getSqlTemplate();
                if (sqlTemplate == null) {
                    sqlTemplate = ""; // fake SQL template, only to avoid issues for sensors that do not have a template
                }

                ProviderSensorDefinitionSpec providerSensorDefinitionSpec = providerSensorWrapper.getSpec();
                providerSensorDefinitionSpec.setJavaClassName(abstractSensorParametersSpec.getSensorRunnerClass().getName());
                providerSensorDefinitionSpec.setSupportsGroupingByDataStream(
                        sqlTemplate.contains("lib.render_data_stream_projections") &&
                                abstractSensorParametersSpec.getSupportsDataStreams());
                providerSensorDefinitionSpec.setSupportsPartitionedChecks(
                        sqlTemplate.contains("lib.render_time_dimension_projection") &&
                                abstractSensorParametersSpec.getSupportsPartitionedChecks());
            }

            if (abstractSensorParametersSpec.getAlwaysSupportedOnAllProviders()) {
                ProviderType[] allProviderTypes = ProviderType.values();
                for (ProviderType providerType : allProviderTypes) {
                    ProviderSensorDefinitionWrapper providerSensorWrapper = providerSensors.getByObjectName(providerType, true);
                    if (providerSensorWrapper != null) {
                        continue; // the sensor is already configured
                    }

                    providerSensorWrapper = providerSensors.createAndAddNew(providerType);
                    providerSensorWrapper.setSpec(new ProviderSensorDefinitionSpec());
                    ProviderSensorDefinitionSpec providerSensorDefinitionSpec = providerSensorWrapper.getSpec();
                    providerSensorDefinitionSpec.setJavaClassName(abstractSensorParametersSpec.getSensorRunnerClass().getName());
                    providerSensorDefinitionSpec.setSupportsGroupingByDataStream(abstractSensorParametersSpec.getSupportsDataStreams());
                    providerSensorDefinitionSpec.setSupportsPartitionedChecks(abstractSensorParametersSpec.getSupportsPartitionedChecks());
                }
            }

            SensorDefinitionSpec sensorDefinitionSpec = sensorDefinitionWrapper.getSpec();

            List<ParameterDefinitionSpec> fieldDefinitionList = this.specToUiCheckMappingService.createFieldsForSensorParameters(abstractSensorParametersSpec)
                    .stream()
                    .map(uiFieldModel -> uiFieldModel.getDefinition())
                    .collect(Collectors.toList());

            ParameterDefinitionsListSpec expectedParameterListSpec = new ParameterDefinitionsListSpec(fieldDefinitionList);
            ParameterDefinitionsListSpec currentParameterListSpec = sensorDefinitionSpec.getFields();

            if (expectedParameterListSpec.size() == 0 && currentParameterListSpec == null) {
                continue;
            }

            if (!Objects.equals(expectedParameterListSpec, currentParameterListSpec)) {
                sensorDefinitionSpec.setFields(expectedParameterListSpec);
                System.out.println("Updating fields for sensor: " + sensorDefinitionName);
            }
        }

        dqoHomeContext.flush();
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
