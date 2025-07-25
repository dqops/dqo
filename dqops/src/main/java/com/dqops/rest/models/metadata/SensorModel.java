/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.metadata;

import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionList;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Sensor model returned from REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "SensorModel", description = "Sensor model that describes the configuration of a single built-in or custom sensor.")
public class SensorModel {
    @JsonPropertyDescription("Full sensor name.")
    private String fullSensorName;

    @JsonPropertyDescription("Sensor definition specification.")
    private SensorDefinitionSpec sensorDefinitionSpec;

    @JsonPropertyDescription("Provider sensors list with provider specific sensor definitions.")
    private List<ProviderSensorModel> providerSensorList = new ArrayList<>();

    @JsonPropertyDescription("Whether the sensor is a User Home sensor")
    private boolean custom;

    @JsonPropertyDescription("This is a DQOps built-in sensor, whose parameters cannot be changed.")
    private boolean builtIn;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
    private boolean canEdit;

    /**
     * Optional parsing error that was captured when parsing the YAML file.
     * This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.
     */
    @JsonPropertyDescription("Optional parsing error that was captured when parsing the YAML file. " +
            "This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.")
    private String yamlParsingError;

    public SensorModel() {
    }

    /**
     * Adds provider sensor models to the provider sensor list. Replaces an already added model.
     * @param providerSensorBasicModels the provider sensor models to add
     */
    public void addProviderSensorModel(List<ProviderSensorModel> providerSensorBasicModels) {
        for (ProviderSensorModel newSensorProviderModel : providerSensorBasicModels) {
            boolean modelExists = false;
            for (int i = 0; i < this.providerSensorList.size(); i++) {
                ProviderSensorModel alreadyAddedProvider = this.providerSensorList.get(i);
                if (Objects.equals(newSensorProviderModel.getProviderType(), alreadyAddedProvider.getProviderType())) {
                    this.providerSensorList.set(i, newSensorProviderModel);
                    modelExists = true;
                    break;
                }
            }

            if (!modelExists) {
                this.providerSensorList.add(newSensorProviderModel);
            }
        }
    }

    /**
     * Checks whether the SensorDefinitionWrapper is equal to this SensorModel.
     * @param sensorDefinitionWrapper the SensorDefinitionWrapper to compare
     * @return true if the SensorDefinitionWrapper is equal to this SensorModel, otherwise false
     */
    public boolean equalsSensorDqo(SensorDefinitionWrapper sensorDefinitionWrapper) {
        if (sensorDefinitionWrapper == null) {
            return false;
        }

        if (!Objects.equals(sensorDefinitionWrapper.getName(), fullSensorName)) {
            return false;
        }

        if (!Objects.equals(sensorDefinitionWrapper.getSpec(), sensorDefinitionSpec)) {
            return false;
        }

        if (!equalsProviderSensorsList(sensorDefinitionWrapper.getProviderSensors())){
            return false;
        }

        return true;
    }

    /**
     * Checks whether the ProviderSensorDefinitionList
     * is equal to the provider sensor list of this SensorModel.
     * @param providerSensorDefinitionList the ProviderSensorDefinitionList to compare
     */
    public boolean equalsProviderSensorsList(ProviderSensorDefinitionList providerSensorDefinitionList){
        return providerSensorDefinitionList
                .toList()
                .stream()
                .allMatch(sensor -> {
                    ProviderSensorModel providerSensorModel = providerSensorList.stream()
                            .filter(dto -> Objects.equals(dto.getProviderType(), sensor.getProvider()))
                            .findFirst()
                            .orElse(null);
                    return providerSensorModel != null && providerSensorModel.equalsProviderSensorDqo(sensor);
                });
    }

    public static class SensorModelSampleFactory implements SampleValueFactory<SensorModel> {
        @Override
        public SensorModel createSample() {
            return new SensorModel() {{
                setFullSensorName(SampleStringsRegistry.getFullSensorName());
                setSensorDefinitionSpec(new SensorDefinitionSpec.SensorDefinitionSpecSampleFactory().createSample());
                setCanEdit(true);
            }};
        }
    }
}
