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
package com.dqops.checks.column.checkspecs.anomaly;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.DefaultDataQualityDimensions;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.RuleSeverityLevel;
import com.dqops.rules.percentile.AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec;
import com.dqops.rules.percentile.AnomalyStationaryPercentileMovingAverageRuleFatal01PctParametersSpec;
import com.dqops.rules.percentile.AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec;
import com.dqops.sensors.column.numeric.ColumnNumericMaxSensorParametersSpec;
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
 * This check finds a maximum value in a numeric column and detects anomalies in a time series of previous maximum values.
 * It raises a data quality issue when the current maximum value is in the top *anomaly_percent* percentage of the most outstanding
 * values in the time series (it is a new maximum value, far from the previous one).
 * This data quality check uses a 90-day time window and requires a history of at least 30 days.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnMaxAnomalyStationaryCheckSpec
        extends AbstractCheckSpec<ColumnNumericMaxSensorParametersSpec, AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec, AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec, AnomalyStationaryPercentileMovingAverageRuleFatal01PctParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnMaxAnomalyStationaryCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Data quality check parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnNumericMaxSensorParametersSpec parameters = new ColumnNumericMaxSensorParametersSpec();

    @JsonPropertyDescription("Alerting threshold that raises a data quality warning that is considered as a passed data quality check")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec warning;

    @JsonPropertyDescription("Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec error;

    @JsonPropertyDescription("Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private AnomalyStationaryPercentileMovingAverageRuleFatal01PctParametersSpec fatal;

    /**
     * Returns the parameters of the sensor.
     *
     * @return Sensor parameters.
     */
    @Override
    public ColumnNumericMaxSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     *
     * @param parameters Row count parameters.
     */
    public void setParameters(ColumnNumericMaxSensorParametersSpec parameters) {
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
    public AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec getWarning() {
        return this.warning;
    }

    /**
     * Sets a new warning level alerting threshold.
     *
     * @param warning Warning alerting threshold to set.
     */
    public void setWarning(AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec warning) {
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
    public AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec getError() {
        return this.error;
    }

    /**
     * Sets a new error level alerting threshold.
     *
     * @param error Error alerting threshold to set.
     */
    public void setError(AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec error) {
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
    public AnomalyStationaryPercentileMovingAverageRuleFatal01PctParametersSpec getFatal() {
        return this.fatal;
    }

    /**
     * Sets a new fatal level alerting threshold.
     *
     * @param fatal Fatal alerting threshold to set.
     */
    public void setFatal(AnomalyStationaryPercentileMovingAverageRuleFatal01PctParametersSpec fatal) {
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
        return "Abnormal change in the maximum of numeric values. Measured as a percentile of anomalous values.";
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
    public RuleSeverityLevel getDefaultSeverity() {
        return RuleSeverityLevel.warning;
    }
}
