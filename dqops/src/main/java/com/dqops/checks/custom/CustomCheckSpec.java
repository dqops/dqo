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

package com.dqops.checks.custom;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.DefaultDataQualityDimensions;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.rules.CustomRuleParametersSpec;
import com.dqops.sensors.CustomSensorParametersSpec;
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
 * Custom check specification. This check is usable only when there is a matching custom check definition that identifies
 * the sensor definition and the rule definition.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class CustomCheckSpec extends AbstractCheckSpec<CustomSensorParametersSpec, CustomRuleParametersSpec, CustomRuleParametersSpec, CustomRuleParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<CustomCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Optional custom sensor name. It is a folder name inside the user's home 'sensors' folder or the DQOps Home (DQOps distribution) home/sensors folder. Sample sensor name: table/volume/row_count. When this value is set, it overrides the default sensor definition defined for the named check definition.")
    private String sensorName;

    @JsonPropertyDescription("Optional custom rule name. It is a path to a custom rule python module that starts at the user's home 'rules' folder. The path should not end with the .py file extension. Sample rule: myrules/my_custom_rule. When this value is set, it overrides the default rule definition defined for the named check definition.")
    private String ruleName;

    @JsonPropertyDescription("Custom sensor parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CustomSensorParametersSpec parameters = new CustomSensorParametersSpec();

    @JsonPropertyDescription("Alerting threshold that raises a data quality warning that is considered as a passed data quality check")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CustomRuleParametersSpec warning;

    @JsonPropertyDescription("Default alerting thresholdthat raises a data quality issue at an error severity level")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CustomRuleParametersSpec error;

    @JsonPropertyDescription("Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CustomRuleParametersSpec fatal;

    /**
     * Returns an optional sensor name. This value takes priority over the default sensor name defined in a check specification.
     * @return Optional sensor name.
     */
    public String getSensorName() {
        return sensorName;
    }

    /**
     * Sets a sensor name that overrides the sensor name defined in the check specification.
     * @param sensorName Sensor name.
     */
    public void setSensorName(String sensorName) {
        this.setDirtyIf(!Objects.equals(this.sensorName, sensorName));
        this.sensorName = sensorName;
    }

    /**
     * Returns an optional rule name. This value takes priority over the default rule name defined in a check specification.
     * @return Optional rule name.
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * Sets a rule name that overrides the rule name defined in the check specification.
     * @param ruleName Rule name.
     */
    public void setRuleName(String ruleName) {
        this.setDirtyIf(!Objects.equals(this.ruleName, ruleName));
        this.ruleName = ruleName;
    }

    /**
     * Returns the parameters of the sensor.
     * @return Sensor parameters.
     */
    @Override
    public CustomSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     * @param parameters Row count parameters.
     */
    public void setParameters(CustomSensorParametersSpec parameters) {
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
    public CustomRuleParametersSpec getWarning() {
        return this.warning;
    }

    /**
     * Sets a new warning level alerting threshold.
     * @param warning Warning alerting threshold to set.
     */
    public void setWarning(CustomRuleParametersSpec warning) {
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
    public CustomRuleParametersSpec getError() {
        return this.error;
    }

    /**
     * Sets a new error level alerting threshold.
     * @param error Error alerting threshold to set.
     */
    public void setError(CustomRuleParametersSpec error) {
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
    public CustomRuleParametersSpec getFatal() {
        return this.fatal;
    }

    /**
     * Sets a new fatal level alerting threshold.
     * @param fatal Fatal alerting threshold to set.
     */
    public void setFatal(CustomRuleParametersSpec fatal) {
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
     * Returns the default data quality dimension name used when an overwritten data quality dimension name was not assigned.
     *
     * @return Default data quality dimension name.
     */
    @Override
    public DefaultDataQualityDimensions getDefaultDataQualityDimension() {
        return DefaultDataQualityDimensions.Validity; // this is just the default
    }

    /**
     * Returns the data quality category name retrieved from the category field name used to store a container of check categories
     * in the metadata.
     * @return Check category name.
     */
    @JsonIgnore
    public String getCategoryName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }

        if (Objects.equals(hierarchyId.get(hierarchyId.size() - 2), "custom_checks")) {
            return hierarchyId.get(hierarchyId.size() - 3).toString();
        }

        return super.getCategoryName();
    }
}
