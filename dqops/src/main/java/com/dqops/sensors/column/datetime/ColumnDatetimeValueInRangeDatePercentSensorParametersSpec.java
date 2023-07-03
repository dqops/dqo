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
package com.dqops.sensors.column.datetime;

import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Column level sensor that calculates the percent of non-negative values in a column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatetimeValueInRangeDatePercentSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatetimeValueInRangeDatePercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Lower bound range variable.")
    private LocalDate minValue;

    @JsonPropertyDescription("Upper bound range variable.")
    private LocalDate maxValue;

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
        return "column/datetime/value_in_range_date_percent";
    }

    /**
     * Returns the lower bounds value of the range.
     * @return Lower bound of the range.
     */
    public LocalDate getMinValue() {
        return minValue;
    }

    /**
     * Sets the lower bounds value of the range.
     * @param minValue Lower bound of the range.
     */
    public void setMinValue(LocalDate minValue) {
        this.setDirtyIf(!Objects.equals(this.minValue,minValue));
        this.minValue = minValue;
    }

    /**
     * Returns the upper bounds value for the range.
     * @return Upper bound of the range.
     */
    public LocalDate getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the upper bounds value for the range.
     * @param maxValue Upper bound of the range.
     */
    public void setMaxValue(LocalDate maxValue) {
        this.setDirtyIf(!Objects.equals(this.maxValue,maxValue));
        this.maxValue = maxValue;
    }
}
