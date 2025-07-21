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

import java.util.*;

/**
 * Sensor folder model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "SensorFolderModel", description = "Sensor folder model that contains sensors defined in this folder or a list of nested folders with sensors.")
public class SensorFolderModel {
    /**
     * A dictionary of nested folders with sensors, the keys are the folder names.
     */
    @JsonPropertyDescription("A dictionary of nested folders with sensors, the keys are the folder names.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, SensorFolderModel> folders;

    /**
     * List of sensors defined in this folder.
     */
    @JsonPropertyDescription("List of sensors defined in this folder.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SensorListModel> sensors;

    /**
     * Creates a new instance of SensorFolderModel with empty lists.
     */
    public SensorFolderModel() {
        sensors = new ArrayList<>();
        folders = new LinkedHashMap<>();
    }

    /**
     * Adds a sensor to this folder based on the given path.
     * @param fullSensorName the path of the sensor
     * @param providerSensorListModelList  List of provider specific sensor definitions.
     * @param sensorDefinitionSource Sensor source (custom or built-in).
     * @param canEdit The current user can edit the sensor definition.
     */
    public void addSensor(String fullSensorName,
                          List<ProviderSensorListModel> providerSensorListModelList,
                          SensorDefinitionSource sensorDefinitionSource,
                          boolean canEdit,
                          String yamlParsingError) {

        String[] sensorFolders = fullSensorName.split("/");
        String sensorName = sensorFolders[sensorFolders.length - 1];
        SensorFolderModel folderModel = this;


        for (int i = 0; i < sensorFolders.length - 1; i++) {
            String name = sensorFolders[i];
            SensorFolderModel nextSensorFolder = folderModel.folders.get(name);
            if (nextSensorFolder == null) {
                nextSensorFolder = new SensorFolderModel();
                folderModel.folders.put(name, nextSensorFolder);
            }
            folderModel = nextSensorFolder;
        }

        boolean sensorExists = false;
        if (folderModel.sensors != null) {
            for (SensorListModel sensor : folderModel.sensors) {
                if (Objects.equals(sensor.getSensorName(), sensorName)) {
                    sensor.setSensorSource(sensorDefinitionSource);
                    sensor.addProviderSensorBasicModel(providerSensorListModelList);
                    sensorExists = true;
                    break;
                }
            }
        } else {
            folderModel.sensors = new ArrayList<>();
        }

        if (!sensorExists) {
            SensorListModel sensorListModel = new SensorListModel();
            sensorListModel.setSensorName(sensorName);
            sensorListModel.setFullSensorName(fullSensorName);
            sensorListModel.setSensorSource(sensorDefinitionSource);
            sensorListModel.setProviderSensors(providerSensorListModelList);
            sensorListModel.setCanEdit(canEdit);
            sensorListModel.setYamlParsingError(yamlParsingError);
            folderModel.sensors.add(sensorListModel);
        }
    }

    /**
     * Collects all sensors from all tree levels.
     * @return A list of all sensors.
     */
    public List<SensorListModel> getAllSensors() {
        List<SensorListModel> allSensors = new ArrayList<>(this.getSensors());
        for (SensorFolderModel folder : this.folders.values()) {
            allSensors.addAll(folder.getAllSensors());
        }

        return allSensors;
    }
}
