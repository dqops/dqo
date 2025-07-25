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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * Sensor list model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "SensorListModel", description = "Sensor list model")
public class SensorListModel {
    /**
     * Sensor name, excluding the parent folder.
     */
    @JsonPropertyDescription("Sensor name, excluding the parent folder.")
    private String sensorName;

    /**
     * Full sensor name, including the folder path within the "sensors" folder where the sensor definitions are stored. This is the unique identifier of the sensor.
     */
    @JsonPropertyDescription("Full sensor name, including the folder path within the \"sensors\" folder where the sensor definitions are stored. This is the unique identifier of the sensor.")
    private String fullSensorName;

    /**
     * This sensor has is a custom sensor or was customized by the user. This is a read-only flag.
     */
    @JsonPropertyDescription("This sensor has is a custom sensor or was customized by the user. This is a read-only flag.")
    private boolean custom;

    /**
     * This sensor is provided with DQOps as a built-in sensor. This is a read-only flag.
     */
    @JsonPropertyDescription("This sensor is provided with DQOps as a built-in sensor. This is a read-only flag.")
    private boolean builtIn;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
    private boolean canEdit;

    /**
     * List of provider (database) specific models.
     */
    @JsonPropertyDescription("List of provider (database) specific models.")
    private List<ProviderSensorListModel> providerSensors;

    /**
     * Optional parsing error that was captured when parsing the YAML file.
     * This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.
     */
    @JsonPropertyDescription("Optional parsing error that was captured when parsing the YAML file. " +
            "This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.")
    private String yamlParsingError;

    public SensorListModel() {
    }

    public SensorListModel(String sensorName, String fullSensorName, boolean custom, boolean builtIn, boolean canEdit,
                           List<ProviderSensorListModel> providerSensors,String yamlParsingError) {
        this.sensorName = sensorName;
        this.fullSensorName = fullSensorName;
        this.custom = custom;
        this.builtIn = builtIn;
        this.canEdit = canEdit;
        this.providerSensors = providerSensors;
        this.yamlParsingError = yamlParsingError;
    }

    /**
     * Sets the custom or builtIn flag to true to match the source of the sensor definition.
     * @param sensorDefinitionSource Source sensor definition.
     */
    public void setSensorSource(SensorDefinitionSource sensorDefinitionSource) {
        if (sensorDefinitionSource == SensorDefinitionSource.CUSTOM) {
            this.setCustom(true);
        }
        else if (sensorDefinitionSource == SensorDefinitionSource.BUILT_IN) {
            this.setBuiltIn(true);
        }
    }

    /**
     * Adds a list of provider specific sensor definitions (sensor templates).
     * @param providerSensorListModels List of sensor templates.
     */
    public void addProviderSensorBasicModel(List<ProviderSensorListModel> providerSensorListModels) {
        for (ProviderSensorListModel newModel : providerSensorListModels) {
            boolean modelExists = false;
            for (ProviderSensorListModel model : this.providerSensors) {
                if (model.getProviderType() == newModel.getProviderType()) {
                    if (newModel.isCustom()) {
                        model.setCustom(newModel.isCustom());
                    }
                    if (newModel.isBuiltIn()) {
                        model.setBuiltIn(newModel.isBuiltIn());
                    }
                    modelExists = true;
                    break;
                }
            }
            if (!modelExists) {
                this.providerSensors.add(newModel);
            }
        }
    }
}
