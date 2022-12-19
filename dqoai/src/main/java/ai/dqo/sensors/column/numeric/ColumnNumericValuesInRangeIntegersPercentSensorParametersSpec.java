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
 * Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 * The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericValuesInRangeIntegersPercentSensorParametersSpec extends AbstractColumnSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericValuesInRangeIntegersPercentSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Minimal value range variable.")
    private Long minValue;

    @JsonPropertyDescription("Maximal value range variable.")
    private Long maxValue;

    @JsonPropertyDescription("The variable deciding whether to include the minimal value of the range. It includes it by default.")
    private Boolean includeMinValue = true;

    @JsonPropertyDescription("The variable deciding whether to include the maximal value of the range. It includes it by default.")
    private Boolean includeMaxValue = true;

    /**
     * Returns the minimal value of the range.
     * @return Minimal value of the range.
     */
    public Long getMinValue() {
        return minValue;
    }

    /**
     * Sets the minimal value of the range.
     * @param minValue Minimal value of the range.
     */
    public void setMinValue(Long minValue) {
        this.setDirtyIf(!Objects.equals(this.minValue, minValue));
        this.minValue = minValue;
    }

    /**
     * Returns the maximal value of the range.
     * @return Maximal value of the range.
     */
    public Long getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the maximal value of the range.
     * @param maxValue Maximal value of the range.
     */
    public void setMaxValue(Long maxValue) {
        this.setDirtyIf(!Objects.equals(this.maxValue, maxValue));
        this.maxValue = maxValue;
    }

    /**
     * Returns the boolean deciding whether to include the minimal value in the range.
     * @return Boolean deciding whether to include the minimal value.
     */
    public Boolean isIncludeMinValue() {
        return includeMinValue;
    }

    /**
     * Sets the boolean deciding whether to include the minimal value in the range.
     * @param includeMinValue Boolean deciding whether to include the minimal value.
     */
    public void setIncludeMinValue(Boolean includeMinValue) {
        this.setDirtyIf(!Objects.equals(this.includeMinValue, includeMinValue));
        this.includeMinValue = includeMinValue;
    }

    /**
     * Returns the boolean deciding whether to include the maximal value in the range.
     * @return Boolean deciding whether to include the maximal value.
     */
    public Boolean isIncludeMaxValue() {
        return includeMaxValue;
    }

    /**
     * Sets the boolean deciding whether to include the maximal value in the range.
     * @param includeMaxValue Boolean deciding whether to include the maximal value.
     */
    public void setIncludeMaxValue(Boolean includeMaxValue) {
        this.setDirtyIf(!Objects.equals(this.includeMaxValue, includeMaxValue));
        this.includeMaxValue = includeMaxValue;
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
        return "column/numeric/values_in_range_integers_percent";
    }
}
