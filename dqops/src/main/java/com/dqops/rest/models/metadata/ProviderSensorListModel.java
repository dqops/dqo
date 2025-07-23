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

import com.dqops.connectors.ProviderType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Provider sensor list model that is returned by the REST API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ProviderSensorListModel", description = "Provider sensor list model")
public class ProviderSensorListModel {
    /**
     * Provider type.
     */
    @JsonPropertyDescription("Provider type.")
    private ProviderType providerType;

    /**
     * This connection specific template is a custom sensor template or was customized by the user.
     */
    @JsonPropertyDescription("This connection specific template is a custom sensor template or was customized by the user.")
    private boolean custom;

    /**
     * This connection specific template is provided with DQOps as a built-in sensor.
     */
    @JsonPropertyDescription("This connection specific template is provided with DQOps as a built-in sensor.")
    private boolean builtIn;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
    private boolean canEdit;

    public ProviderSensorListModel() {
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
}
