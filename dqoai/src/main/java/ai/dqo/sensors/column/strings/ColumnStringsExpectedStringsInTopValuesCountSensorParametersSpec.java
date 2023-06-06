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
import ai.dqo.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Column level sensor that counts how many expected string values are among the TOP most popular values in the column.
 * The sensor will first count the number of occurrences of each column's value and will pick the TOP X most popular values (configurable by the 'top' parameter).
 * Then, it will compare the list of most popular values to the given list of expected values that should be most popular.
 * This sensor will return the number of expected values that were found within the 'top' most popular column values.
 * This sensor is useful for analyzing string columns that have several very popular values, these could be the country codes of the countries with the most number of customers.
 * The sensor can detect if any of the most popular value (an expected value) is no longer one of the top X most popular values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsExpectedStringsInTopValuesCountSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsExpectedStringsInTopValuesCountSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("List of expected string values that should be found in the tested column among the TOP most popular (highest distinct count) column values.")
    @SampleValues(values = { "USD", "GBP", "EUR" })
    private List<String> expectedValues;

    @JsonPropertyDescription("The number of the most popular values (with the highest distinct count) that are analyzed to find the expected values.")
    private Long top;

    /**
     * Returns given values from user.
     * @return values.
     */
    public List<String> getExpectedValues() {
        return expectedValues;
    }

    /**
     * Sets a List given from user.
     * @param expectedValues values given from user.
     */
    public void setExpectedValues(List<String> expectedValues) {
        this.setDirtyIf(!Objects.equals(this.expectedValues, expectedValues));
        this.expectedValues = expectedValues != null ? Collections.unmodifiableList(expectedValues) : null;
    }

    /**
     * Returns given values from user.
     * @return values.
     */
    public Long getTop() {
        return top;
    }

    /**
     * Sets a List given from user.
     * @param top values given from user.
     */
    public void setTop(Long top) {
        this.setDirtyIf(!Objects.equals(this.top, top));
        this.top = top;
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
        return "column/strings/expected_strings_in_top_values_count";
    }
}