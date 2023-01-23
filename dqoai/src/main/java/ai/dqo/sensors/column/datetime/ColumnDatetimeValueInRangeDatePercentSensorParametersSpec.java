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
package ai.dqo.sensors.column.datetime;

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.sensors.column.AbstractColumnSensorParametersSpec;
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
public class ColumnDatetimeValueInRangeDatePercentSensorParametersSpec extends AbstractColumnSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatetimeValueInRangeDatePercentSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Lower bound range variable.")
    private LocalDate minValue;

    @JsonPropertyDescription("Upper bound range variable.")
    private LocalDate maxValue;

    @JsonPropertyDescription("The variable deciding whether to include the lower limit of the range. It does not include it by default.")
    @Deprecated // do not use it, always inclusive
    private Boolean includeMinValue = true;

    @JsonPropertyDescription("The variable deciding whether to include the upper limit of the range. It does not include it by default.")
    @Deprecated // do not use it, always inclusive
    private Boolean includeMaxValue = true;

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
        return "column/validity/value_in_range_date_percent";
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
    /**
     * Returns the boolean deciding whether to include the lower limit of the range.
     * @return Boolean deciding whether to include the lower bound.
     */
    public Boolean isIncludeMinValue() {
        return includeMinValue;
    }
    /**
     * Sets the boolean deciding whether to include the lower limit of the range.
     * @param includeMinValue Boolean deciding whether to include the lower bound.
     */
    public void setIncludeMinValue(Boolean includeMinValue) {
        this.setDirtyIf(!Objects.equals(this.includeMinValue,includeMinValue));
        this.includeMinValue = includeMinValue;
    }

    /**
     * Returns the boolean deciding whether to include the upper limit of the range.
     * @return Boolean deciding whether to include the upper bound.
     */
    public Boolean isIncludeMaxValue() {
        return includeMaxValue;
    }

    /**
     * Sets the boolean deciding whether to include the upper limit of the range.
     * @param includeMaxValue Boolean deciding whether to include the upper bound.
     */
    public void setIncludeMaxValue(Boolean includeMaxValue) {
        this.setDirtyIf(!Objects.equals(this.includeMaxValue,includeMaxValue));
        this.includeMaxValue = includeMaxValue;
    }
}
