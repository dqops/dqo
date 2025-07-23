/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.column.numeric;

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
 * Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 * The returned data type matches the data type of the column (can return date, integer, string, datetime, etc.).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericNumberInRangePercentSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericNumberInRangePercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Minimum value for the range.")
    private Double minValue;

    @JsonPropertyDescription("Maximum value for the range.")
    private Double maxValue;

    /**
     * Returns the minimum value of the range.
     * @return Minimum value of the range.
     */
    public Double getMinValue() {
        return minValue;
    }

    /**
     * Sets the minimum value of the range.
     * @param minValue Minimum value of the range.
     */
    public void setMinValue(Double minValue) {
        this.setDirtyIf(!Objects.equals(this.minValue, minValue));
        this.minValue = minValue;
    }

    /**
     * Returns the maximum value of the range.
     * @return Maximum value of the range.
     */
    public Double getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the maximum value of the range.
     * @param maxValue Maximum value of the range.
     */
    public void setMaxValue(Double maxValue) {
        this.setDirtyIf(!Objects.equals(this.maxValue, maxValue));
        this.maxValue = maxValue;
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
        return "column/numeric/number_in_range_percent";
    }
}
