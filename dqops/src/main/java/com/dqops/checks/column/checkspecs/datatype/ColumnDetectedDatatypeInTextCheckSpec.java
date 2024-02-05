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
package com.dqops.checks.column.checkspecs.datatype;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.DefaultDataQualityDimensions;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.comparison.DetectedDatatypeEqualsRuleParametersSpec;
import com.dqops.sensors.column.datatype.ColumnDatatypeStringDatatypeDetectSensorParametersSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * A table-level check that scans all values in a string column and detects the data type of all values in a monitored column. The actual_value returned from the sensor can be one of seven codes: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types.
 * The check compares the data type detected in all non-null columns to an expected data type. The rule compares the value using equals and requires values in the range 1..8, which are the codes of detected data types.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDetectedDatatypeInTextCheckSpec extends
        AbstractCheckSpec<ColumnDatatypeStringDatatypeDetectSensorParametersSpec, DetectedDatatypeEqualsRuleParametersSpec, DetectedDatatypeEqualsRuleParametersSpec, DetectedDatatypeEqualsRuleParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDetectedDatatypeInTextCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The sensor parameters for a sensor that returns a value that identifies the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDatatypeStringDatatypeDetectSensorParametersSpec parameters = new ColumnDatatypeStringDatatypeDetectSensorParametersSpec();

    @JsonPropertyDescription("Alerting threshold that raises a data quality warning that is considered as a passed data quality check, detects that the data type of values stored in a column matches an expected data type code (1..8).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DetectedDatatypeEqualsRuleParametersSpec warning;

    @JsonPropertyDescription("Default alerting thresholdthat raises a data quality issue at an error severity level, detects that the data type of values stored in a column matches an expected data type code (1..8).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DetectedDatatypeEqualsRuleParametersSpec error;

    @JsonPropertyDescription("Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem, detects that the data type of values stored in a column matches an expected data type code (1..8).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DetectedDatatypeEqualsRuleParametersSpec fatal;

    /**
     * Returns the parameters of the sensor.
     * @return Sensor parameters.
     */
    @Override
    public ColumnDatatypeStringDatatypeDetectSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     * @param parameters Row count parameters.
     */
    public void setParameters(ColumnDatatypeStringDatatypeDetectSensorParametersSpec parameters) {
		this.setDirtyIf(!Objects.equals(this.parameters, parameters));
        this.parameters = parameters;
		this.propagateHierarchyIdToField(parameters, "parameters");
    }

    /**
     * Alerting threshold configuration that raise a "WARNING" severity alerts for unsatisfied rules.
     *
     * @return Warning severity rule parameters.
     */
    @Override
    public DetectedDatatypeEqualsRuleParametersSpec getWarning() {
        return this.warning;
    }

    /**
     * Sets a new warning level alerting threshold.
     * @param warning Warning alerting threshold to set.
     */
    public void setWarning(DetectedDatatypeEqualsRuleParametersSpec warning) {
        this.setDirtyIf(!Objects.equals(this.warning, warning));
        this.warning = warning;
        this.propagateHierarchyIdToField(warning, "warning");
    }

    /**
     * Alerting threshold configuration that raise a regular "ERROR" severity alerts for unsatisfied rules.
     *
     * @return Default "ERROR" alerting thresholds.
     */
    @Override
    public DetectedDatatypeEqualsRuleParametersSpec getError() {
        return this.error;
    }

    /**
     * Sets a new error level alerting threshold.
     * @param error Error alerting threshold to set.
     */
    public void setError(DetectedDatatypeEqualsRuleParametersSpec error) {
        this.setDirtyIf(!Objects.equals(this.error, error));
        this.error = error;
        this.propagateHierarchyIdToField(error, "error");
    }

    /**
     * Alerting threshold configuration that raise a "FATAL" severity alerts for unsatisfied rules.
     *
     * @return Fatal severity rule parameters.
     */
    @Override
    public DetectedDatatypeEqualsRuleParametersSpec getFatal() {
        return this.fatal;
    }

    /**
     * Sets a new fatal level alerting threshold.
     * @param fatal Fatal alerting threshold to set.
     */
    public void setFatal(DetectedDatatypeEqualsRuleParametersSpec fatal) {
        this.setDirtyIf(!Objects.equals(this.fatal, fatal));
        this.fatal = fatal;
        this.propagateHierarchyIdToField(fatal, "fatal");
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
     * Returns true if this is a standard data quality check that is always shown on the data quality checks editor screen.
     * Non-standard data quality checks (when the value is false) are advanced checks that are shown when the user decides to expand the list of checks.
     *
     * @return True when it is a standard check, false when it is an advanced check. The default value is 'false' (all checks are non-standard, advanced checks).
     */
    @Override
    @JsonIgnore
    public boolean isStandard() {
        return true;
    }

    /**
     * Returns the default data quality dimension name used when an overwritten data quality dimension name was not assigned.
     *
     * @return Default data quality dimension name.
     */
    @Override
    public DefaultDataQualityDimensions getDefaultDataQualityDimension() {
        return DefaultDataQualityDimensions.Consistency;
    }
}
