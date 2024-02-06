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
public class ColumnNumericIntegerInRangePercentSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericIntegerInRangePercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Minimum value range variable.")
    private Long minValue;

    @JsonPropertyDescription("Maximum value range variable.")
    private Long maxValue;

    /**
     * Returns the minimum value of the range.
     * @return Minimum value of the range.
     */
    public Long getMinValue() {
        return minValue;
    }

    /**
     * Sets the minimum value of the range.
     * @param minValue Minimum value of the range.
     */
    public void setMinValue(Long minValue) {
        this.setDirtyIf(!Objects.equals(this.minValue, minValue));
        this.minValue = minValue;
    }

    /**
     * Returns the maximum value of the range.
     * @return Maximum value of the range.
     */
    public Long getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the maximum value of the range.
     * @param maxValue Maximum value of the range.
     */
    public void setMaxValue(Long maxValue) {
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
        return "column/numeric/integer_in_range_percent";
    }
}
