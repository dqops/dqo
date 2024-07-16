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
package com.dqops.checks.column.checkspecs.uniqueness;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.DefaultDataQualityDimensions;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.RuleSeverityLevel;
import com.dqops.rules.percentile.AnomalyDifferencingPercentileMovingAverageRuleFatal01PctParametersSpec;
import com.dqops.rules.percentile.AnomalyDifferencingPercentileMovingAverageRuleError05PctParametersSpec;
import com.dqops.rules.percentile.AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec;
import com.dqops.sensors.column.uniqueness.ColumnUniquenessDistinctCountSensorParametersSpec;
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
 * This check monitors the count of distinct values and detects anomalies in the changes of the distinct count. It monitors a 90-day time window.
 * The check is configured by setting a desired percentage of anomalies to identify as data quality issues.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDistinctCountAnomalyDifferencingCheckSpec
        extends AbstractCheckSpec<ColumnUniquenessDistinctCountSensorParametersSpec, AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec, AnomalyDifferencingPercentileMovingAverageRuleError05PctParametersSpec, AnomalyDifferencingPercentileMovingAverageRuleFatal01PctParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDistinctCountAnomalyDifferencingCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Data quality check parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnUniquenessDistinctCountSensorParametersSpec parameters
            = new ColumnUniquenessDistinctCountSensorParametersSpec();

    @JsonPropertyDescription("Alerting threshold that raises a data quality warning that is considered as a passed data quality check")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec warning;

    @JsonPropertyDescription("Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private AnomalyDifferencingPercentileMovingAverageRuleError05PctParametersSpec error;

    @JsonPropertyDescription("Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private AnomalyDifferencingPercentileMovingAverageRuleFatal01PctParametersSpec fatal;

    /**
     * Returns the parameters of the sensor.
     *
     * @return Sensor parameters.
     */
    @Override
    public ColumnUniquenessDistinctCountSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     *
     * @param parameters Row count parameters.
     */
    public void setParameters(ColumnUniquenessDistinctCountSensorParametersSpec parameters) {
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
    public AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec getWarning() {
        return this.warning;
    }

    /**
     * Sets a new warning level alerting threshold.
     *
     * @param warning Warning alerting threshold to set.
     */
    public void setWarning(AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec warning) {
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
    public AnomalyDifferencingPercentileMovingAverageRuleError05PctParametersSpec getError() {
        return this.error;
    }

    /**
     * Sets a new error level alerting threshold.
     *
     * @param error Error alerting threshold to set.
     */
    public void setError(AnomalyDifferencingPercentileMovingAverageRuleError05PctParametersSpec error) {
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
    public AnomalyDifferencingPercentileMovingAverageRuleFatal01PctParametersSpec getFatal() {
        return this.fatal;
    }

    /**
     * Sets a new fatal level alerting threshold.
     *
     * @param fatal Fatal alerting threshold to set.
     */
    public void setFatal(AnomalyDifferencingPercentileMovingAverageRuleFatal01PctParametersSpec fatal) {
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
