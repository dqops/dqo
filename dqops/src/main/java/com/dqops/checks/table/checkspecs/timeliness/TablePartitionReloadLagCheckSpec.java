/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.table.checkspecs.timeliness;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.DefaultDataQualityDimensions;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.comparison.MaxDaysRule1ParametersSpec;
import com.dqops.rules.comparison.MaxDaysRule2ParametersSpec;
import com.dqops.rules.comparison.MaxDaysRule7ParametersSpec;
import com.dqops.sensors.table.timeliness.TableTimelinessPartitionReloadLagSensorParametersSpec;
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
 * A table-level check that calculates the maximum difference in days between ingestion timestamp and event timestamp values on any row.
 * This check should be executed only as a partitioned check because this check finds the longest delay between the time that the row was created
 * in the data source and the timestamp when the row was loaded into its daily or monthly partition.
 * This check detects that a daily or monthly partition was reloaded, setting also the most recent timestamps in the created_at, loaded_at, inserted_at or other similar columns
 * filled by the data pipeline or an ETL process during data loading.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TablePartitionReloadLagCheckSpec extends AbstractCheckSpec<TableTimelinessPartitionReloadLagSensorParametersSpec, MaxDaysRule1ParametersSpec, MaxDaysRule2ParametersSpec, MaxDaysRule7ParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<TablePartitionReloadLagCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Partition reload lag sensor parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableTimelinessPartitionReloadLagSensorParametersSpec parameters = new TableTimelinessPartitionReloadLagSensorParametersSpec();

    @JsonPropertyDescription("Alerting threshold that raises a data quality warning that is considered as a passed data quality check")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MaxDaysRule1ParametersSpec warning;

    @JsonPropertyDescription("Default alerting threshold for partition reload lag that raises a data quality error (alert)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MaxDaysRule2ParametersSpec error;

    @JsonPropertyDescription("Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MaxDaysRule7ParametersSpec fatal;

    /**
     * Returns the parameters of the sensor.
     * @return Sensor parameters.
     */
    @Override
    public TableTimelinessPartitionReloadLagSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new partition reload lag parameter object.
     * @param parameters Partition reload lag parameters.
     */
    public void setParameters(TableTimelinessPartitionReloadLagSensorParametersSpec parameters) {
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
    public MaxDaysRule1ParametersSpec getWarning() {
        return this.warning;
    }

    /**
     * Sets a new warning level alerting threshold.
     * @param warning Warning alerting threshold to set.
     */
    public void setWarning(MaxDaysRule1ParametersSpec warning) {
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
    public MaxDaysRule2ParametersSpec getError() {
        return this.error;
    }

    /**
     * Sets a new error level alerting threshold.
     * @param error Error alerting threshold to set.
     */
    public void setError(MaxDaysRule2ParametersSpec error) {
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
    public MaxDaysRule7ParametersSpec getFatal() {
        return this.fatal;
    }

    /**
     * Sets a new fatal level alerting threshold.
     * @param fatal Fatal alerting threshold to set.
     */
    public void setFatal(MaxDaysRule7ParametersSpec fatal) {
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
        return "Reload lag (Maximum delay to load the last record for each partition)";
    }

    /**
     * Returns the default data quality dimension name used when an overwritten data quality dimension name was not assigned.
     *
     * @return Default data quality dimension name.
     */
    @Override
    public DefaultDataQualityDimensions getDefaultDataQualityDimension() {
        return DefaultDataQualityDimensions.Timeliness;
    }
}
