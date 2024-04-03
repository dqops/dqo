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
 * Column level sensor that finds the percentage of date values that are outside an accepted range. This sensor detects presence of fake or corrupted dates such as 1900-01-01 or 2099-12-31.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDateInRangePercentSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDateInRangePercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The earliest accepted date.")
    private LocalDate minDate = LocalDate.of(1900, 01, 02);

    @JsonPropertyDescription("The latest accepted date.")
    private LocalDate maxDate = LocalDate.of(2099, 12, 30);

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
        return "column/datetime/date_in_range_percent";
    }

    /**
     * Returns the lower bounds value of the range.
     * @return Lower bound of the range.
     */
    public LocalDate getMinDate() {
        return minDate;
    }

    /**
     * Sets the lower bounds value of the range.
     * @param minDate Lower bound of the range.
     */
    public void setMinDate(LocalDate minDate) {
        this.setDirtyIf(!Objects.equals(this.minDate, minDate));
        this.minDate = minDate;
    }

    /**
     * Returns the upper bounds value for the range.
     * @return Upper bound of the range.
     */
    public LocalDate getMaxDate() {
        return maxDate;
    }

    /**
     * Sets the upper bounds value for the range.
     * @param maxDate Upper bound of the range.
     */
    public void setMaxDate(LocalDate maxDate) {
        this.setDirtyIf(!Objects.equals(this.maxDate, maxDate));
        this.maxDate = maxDate;
    }
}
