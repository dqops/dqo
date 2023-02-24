/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.sensors.column.numeric;

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.sensors.column.AbstractColumnSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column level sensor that finds the percentile 50 in a given column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericPercentile50InRangeSensorParametersSpec extends AbstractColumnSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericPercentile50InRangeSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("50th percentile")
    private final Double percentileValue = 0.5;

    /**
     * Returns the percentile 50 value of the range.
     * @return Percentile 50 value of the range.
     */
    public Double getPercentileValue() {
        return percentileValue;
    }

//    /**
//     * Sets the percentile 50 value of the range.
//     * @param percentileValue Percentile 50 value of the range.
//     */
//    public void setPercentileValue(Double percentileValue) {
//        this.setDirtyIf(!Objects.equals(this.percentileValue, percentileValue));
//        this.percentileValue = percentileValue;
//    }

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
