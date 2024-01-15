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
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column level sensor that finds the median in a given column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericMedianSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericMedianSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };


    @JsonPropertyDescription("Median (50th percentile), must equal 0.5")
    @RequiredField
    private Double percentileValue = 0.5;

    /**
     * Returns the median value of the range.
     * @return median value of the range.
     */
    public Double getPercentileValue() {
        return percentileValue;
    }

    /**
     * Sets the median value of the range.
     * @param percentileValue median value of the range.
     */
    public final void setPercentileValue(Double percentileValue) {
        this.setDirtyIf(!Objects.equals(this.percentileValue, percentileValue));
        this.percentileValue = percentileValue;
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
        return "column/numeric/percentile";
    }
}
