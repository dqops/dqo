/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.checkspecs.anomaly;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.DefaultDataQualityDimensions;
import com.dqops.checks.DefaultRuleSeverityLevel;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.TargetRuleSeverityLevel;
import com.dqops.rules.change.ChangePercent7DaysRule10ParametersSpec;
import com.dqops.rules.change.ChangePercent7DaysRule20ParametersSpec;
import com.dqops.rules.change.ChangePercent7DaysRule50ParametersSpec;
import com.dqops.sensors.column.numeric.ColumnNumericMeanSensorParametersSpec;
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
 * This check detects that the mean (average) value of numeric values has changed more than *max_percent* from the mean value measured seven days ago.
 * This check aims to overcome a weekly seasonability and compare Mondays to Mondays, Tuesdays to Tuesdays, etc.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnMeanChange7DaysCheckSpec
        extends AbstractCheckSpec<ColumnNumericMeanSensorParametersSpec, ChangePercent7DaysRule10ParametersSpec, ChangePercent7DaysRule20ParametersSpec, ChangePercent7DaysRule50ParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnMeanChange7DaysCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Data quality check parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericMeanSensorParametersSpec parameters = new ColumnNumericMeanSensorParametersSpec();

    @JsonPropertyDescription("Alerting threshold that raises a data quality warning that is considered as a passed data quality check")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ChangePercent7DaysRule10ParametersSpec warning;

    @JsonPropertyDescription("Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ChangePercent7DaysRule20ParametersSpec error;

    @JsonPropertyDescription("Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ChangePercent7DaysRule50ParametersSpec fatal;

    /**
     * Returns the parameters of the sensor.
     *
     * @return Sensor parameters.
     */
    @Override
    public ColumnNumericMeanSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     *
     * @param parameters Row count parameters.
     */
    public void setParameters(ColumnNumericMeanSensorParametersSpec parameters) {
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
    public ChangePercent7DaysRule10ParametersSpec getWarning() {
        return this.warning;
    }

    /**
     * Sets a new warning level alerting threshold.
     *
     * @param warning Warning alerting threshold to set.
     */
    public void setWarning(ChangePercent7DaysRule10ParametersSpec warning) {
        this.setDirtyIf(!Objects.equals(this.warning, warning));
        this.warning = warning;
        this.propagateHierarchyIdToField(warning, "warning");
    }

    /**
     * Alerting threshold configuration that raise a regular "ERROR" severity alerts for unsatisfied rules.
     *
     * @return Default "error" alerting thresholds.
     */
    @Override
    public ChangePercent7DaysRule20ParametersSpec getError() {
        return this.error;
    }

    /**
     * Sets a new error level alerting threshold.
     *
     * @param error Error alerting threshold to set.
     */
    public void setError(ChangePercent7DaysRule20ParametersSpec error) {
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
    public ChangePercent7DaysRule50ParametersSpec getFatal() {
        return this.fatal;
    }

    /**
     * Sets a new fatal level alerting threshold.
     *
     * @param fatal Fatal alerting threshold to set.
     */
    public void setFatal(ChangePercent7DaysRule50ParametersSpec fatal) {
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
     * Returns an alternative check's friendly name that is shown on the check editor.
     *
     * @return An alternative name, or null when the check has no alternative name to show.
     */
    @Override
    @JsonIgnore
    public String getFriendlyName() {
        return "Maximum relative change in the mean (average) of numeric values vs 7 days ago";
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

    /**
     * Returns the default rule severity level that is activated when a check is enabled in the check editor.
     *
     * @return The default rule severity level that is activated when a check is enabled in the check editor. The default value is an "error" severity rule.
     */
    @Override
    @JsonIgnore
    public DefaultRuleSeverityLevel getDefaultSeverity() {
        return DefaultRuleSeverityLevel.warning;
    }
}
