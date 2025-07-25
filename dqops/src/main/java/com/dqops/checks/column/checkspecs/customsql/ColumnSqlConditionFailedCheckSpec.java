/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.checkspecs.customsql;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.DefaultDataQualityDimensions;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.comparison.MaxCountRule0WarningParametersSpec;
import com.dqops.rules.comparison.MaxCountRule100ParametersSpec;
import com.dqops.rules.comparison.MaxCountRule0ErrorParametersSpec;
import com.dqops.sensors.column.customsql.ColumnSqlConditionFailedCountSensorParametersSpec;
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
 * A column-level check that uses a custom SQL expression on each column to verify (assert) that all rows pass a custom condition defined as an SQL expression.
 * Use the {alias} token to reference the tested table, and the {column} to reference the column that is tested. This data quality check can be used to compare columns on the same table.
 * For example, when this check is applied on a *col_price* column, the condition can verify that the *col_price* is higher than the *col_tax* using an SQL expression: `{alias}.{column} > {alias}.col_tax`
 * Use an SQL expression that returns a *true* value for valid values and *false* for invalid values, because it is an assertion.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnSqlConditionFailedCheckSpec
        extends AbstractCheckSpec<ColumnSqlConditionFailedCountSensorParametersSpec, MaxCountRule0WarningParametersSpec, MaxCountRule0ErrorParametersSpec, MaxCountRule100ParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnSqlConditionFailedCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Sensor parameters with the custom SQL condition (an expression that returns true/false) which is evaluated on a each row, using a {column} placeholder to reference the current column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnSqlConditionFailedCountSensorParametersSpec parameters = new ColumnSqlConditionFailedCountSensorParametersSpec();

    @JsonPropertyDescription("Alerting threshold that raises a data quality warning when a given number of rows failed the custom SQL condition (expression). The warning is considered as a passed data quality check.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MaxCountRule0WarningParametersSpec warning;

    @JsonPropertyDescription("Default alerting threshold for a maximum number of rows failing the custom SQL condition (expression) that raises a data quality error (alert).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MaxCountRule0ErrorParametersSpec error;

    @JsonPropertyDescription("Alerting threshold that raises a fatal data quality issue when a given number of rows failed the custom SQL condition (expression). A fatal issue indicates a serious data quality problem that should result in stopping the data pipelines.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MaxCountRule100ParametersSpec fatal;

    /**
     * Returns the parameters of the sensor.
     * @return Sensor parameters.
     */
    @Override
    public ColumnSqlConditionFailedCountSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     * @param parameters Row count parameters.
     */
    public void setParameters(ColumnSqlConditionFailedCountSensorParametersSpec parameters) {
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
    public MaxCountRule0WarningParametersSpec getWarning() {
        return this.warning;
    }

    /**
     * Sets a new warning level alerting threshold.
     * @param warning Warning alerting threshold to set.
     */
    public void setWarning(MaxCountRule0WarningParametersSpec warning) {
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
    public MaxCountRule0ErrorParametersSpec getError() {
        return this.error;
    }

    /**
     * Sets a new error level alerting threshold.
     * @param error Error alerting threshold to set.
     */
    public void setError(MaxCountRule0ErrorParametersSpec error) {
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
    public MaxCountRule100ParametersSpec getFatal() {
        return this.fatal;
    }

    /**
     * Sets a new fatal level alerting threshold.
     * @param fatal Fatal alerting threshold to set.
     */
    public void setFatal(MaxCountRule100ParametersSpec fatal) {
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
     * Returns an alternative check's friendly name that is shown on the check editor.
     *
     * @return An alternative name, or null when the check has no alternative name to show.
     */
    @Override
    @JsonIgnore
    public String getFriendlyName() {
        return "Maximum count of rows that failed SQL conditions";
    }

    /**
     * Returns the default data quality dimension name used when an overwritten data quality dimension name was not assigned.
     *
     * @return Default data quality dimension name.
     */
    @Override
    public DefaultDataQualityDimensions getDefaultDataQualityDimension() {
        return DefaultDataQualityDimensions.Validity;
    }
}
