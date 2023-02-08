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
package ai.dqo.sensors.column.strings;

import ai.dqo.metadata.fields.SampleValues;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.sensors.column.AbstractColumnSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Column level sensor that calculates the percent of non-negative values in a column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsStringLengthInRangePercentSensorParametersSpec extends AbstractColumnSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsStringLengthInRangePercentSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Sets a minimal string length")
    @SampleValues(values = "5")
    private int minLength;

    @JsonPropertyDescription("Sets a maximal string length.")
    @SampleValues(values = "10")
    private int maxLength;

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
        return "column/strings/string_length_in_range_percent";
    }

    /** Returns a maximal string length range.
     *
     * @return Return a upperLengthBound
     */
    public int getMaxLength() {
        return maxLength;
    }

    /** Returns a minimal string length range.
     *
     * @return lowerLengthBound
     */
    public int getMinLength() {
        return minLength;
    }

    /** Sets a maximal string length range.
     *
     * @param maxLength
     */
    public void setMaxLength(int maxLength) {
        this.setDirtyIf(this.maxLength != maxLength);
        this.maxLength = maxLength;
    }

    /** Sets a minimal string length range.
     *
     * @param minLength
     */
    public void setMinLength(int minLength) {
        this.setDirtyIf(this.minLength != minLength);
        this.minLength = minLength;
    }
}
