/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.column.datetime;

import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    /**
     * Sensor name.
     */
    public static final String SENSOR_NAME = "column/datetime/date_values_in_future_percent";

    @JsonPropertyDescription("Maximum accepted number of days from now that are not treated as days from future. If value is not defined by user then default value is 0.0.")
    private Double maxFutureDays = 0.0;

    /**
     * Returns the max future days.
     * @return maxFutureDays.
     */
    public Double getMaxFutureDays() {
        return maxFutureDays;
    }

    /**
     * Sets the max future days
     * @param maxFutureDays maxFutureDays.
     */
    public void setMaxFutureDays(Double maxFutureDays) {
        this.setDirtyIf(!Objects.equals(this.maxFutureDays, maxFutureDays));
        this.maxFutureDays = maxFutureDays;
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Returns the sensor definition name. This is the folder name that keeps the sensor definition files.
     *
     * @return Sensor definition name.
     */
    @Override
    public String getSensorDefinitionName() {
        return SENSOR_NAME;
    }

}
