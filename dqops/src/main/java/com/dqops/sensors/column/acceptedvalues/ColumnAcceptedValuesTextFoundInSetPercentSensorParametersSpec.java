/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.column.acceptedvalues;

import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Column level sensor that calculates the percentage of rows for which the tested string (text) column contains a value from the list of expected values.
 * Columns with null values are also counted as a passing value (the sensor assumes that a 'null' is also an expected and accepted value).
 * This sensor is useful for testing that a string column with a low number of unique values (country, currency, state, gender, etc.) contains only values from a set of expected values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAcceptedValuesTextFoundInSetPercentSensorParametersSpec extends AbstractSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAcceptedValuesTextFoundInSetPercentSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("A list of expected values that must be present in a string column, only values from this list are accepted and rows having these values in the tested column are counted as valid rows.")
    @SampleValues(values = { "USD", "GBP", "EUR" })
    @RequiredField
    private List<String> expectedValues;

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
        return "column/accepted_values/text_found_in_set_percent";
    }
}