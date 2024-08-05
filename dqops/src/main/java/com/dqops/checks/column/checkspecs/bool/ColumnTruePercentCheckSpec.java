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
package com.dqops.checks.column.checkspecs.bool;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckType;
import com.dqops.checks.DefaultDataQualityDimensions;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.rules.comparison.BetweenPercentRuleParametersSpec;
import com.dqops.rules.comparison.MinPercentRule95ParametersSpec;
import com.dqops.rules.comparison.MinPercentRule100ErrorParametersSpec;
import com.dqops.rules.comparison.MinPercentRule100WarningParametersSpec;
import com.dqops.sensors.column.bool.ColumnBoolTruePercentSensorParametersSpec;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mining.*;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.dqops.utils.serialization.JsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.Objects;

/**
 * This check measures the percentage of **true** values in a boolean column. It raises a data quality issue when the measured percentage is outside the accepted range.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTruePercentCheckSpec
        extends AbstractCheckSpec<ColumnBoolTruePercentSensorParametersSpec, BetweenPercentRuleParametersSpec, BetweenPercentRuleParametersSpec, BetweenPercentRuleParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTruePercentCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Data quality check parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnBoolTruePercentSensorParametersSpec parameters = new ColumnBoolTruePercentSensorParametersSpec();

    @JsonPropertyDescription("Alerting threshold that raises a data quality warning that is considered as a passed data quality check")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private BetweenPercentRuleParametersSpec warning;

    @JsonPropertyDescription("Default alerting threshold for a set percentage of true value in a column that raises a data quality error (alert).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private BetweenPercentRuleParametersSpec error;

    @JsonPropertyDescription("Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private BetweenPercentRuleParametersSpec fatal;

    /**
     * Returns the parameters of the sensor.
     * @return Sensor parameters.
     */
    @Override
    public ColumnBoolTruePercentSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     * @param parameters Row count parameters.
     */
    public void setParameters(ColumnBoolTruePercentSensorParametersSpec parameters) {
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
    public BetweenPercentRuleParametersSpec getWarning() {
        return this.warning;
    }

    /**
     * Sets a new warning level alerting threshold.
     * @param warning Warning alerting threshold to set.
     */
    public void setWarning(BetweenPercentRuleParametersSpec warning) {
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
    public BetweenPercentRuleParametersSpec getError() {
        return this.error;
    }

    /**
     * Sets a new error level alerting threshold.
     * @param error Error alerting threshold to set.
     */
    public void setError(BetweenPercentRuleParametersSpec error) {
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
    public BetweenPercentRuleParametersSpec getFatal() {
        return this.fatal;
    }

    /**
     * Sets a new fatal level alerting threshold.
     * @param fatal Fatal alerting threshold to set.
     */
    public void setFatal(BetweenPercentRuleParametersSpec fatal) {
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
        return "Percentage of true values in bool column";
    }

    /**
     * Returns the default data quality dimension name used when an overwritten data quality dimension name was not assigned.
     *
     * @return Default data quality dimension name.
     */
    @Override
    public DefaultDataQualityDimensions getDefaultDataQualityDimension() {
        return DefaultDataQualityDimensions.Reasonableness;
    }

    /**
     * Proposes the configuration of this check by using information from all related sources.
     *
     * @param sourceProfilingCheck               Previous results captured by a similar profiling check. Used to copy configuration to monitoring checks.
     * @param dataAssetProfilingResults          Profiling results from the basic statistics and profiling checks for the data asset (table or column).
     * @param tableProfilingResults              All profiling results for the table, including table-level profiling results (such as row counts) and results for all columns. Used by rule mining functions that must look into other values.
     * @param tableSpec                          Parent table specification for reference.
     * @param parentCheckRootContainer           Parent check container, to identify the type of checks.
     * @param myCheckModel                       Check model of this check. This information can be used to get access to the custom check configuration (for custom checks).
     * @param miningParameters                   Additional rule mining parameters given by the user.
     * @param columnTypeCategory                 Column type category for column checks.
     * @param checkMiningConfigurationProperties Check mining configuration properties.
     * @param jsonSerializer                     JSON serializer used to convert sensor parameters and rule parameters to the target class type by serializing and deserializing.
     * @param ruleMiningRuleRegistry             Rule mining registry.
     * @return True when the check was configured, false when the function decided not to configure the check.
     */
    @Override
    public boolean proposeCheckConfiguration(ProfilingCheckResult sourceProfilingCheck,
                                             DataAssetProfilingResults dataAssetProfilingResults,
                                             TableProfilingResults tableProfilingResults,
                                             TableSpec tableSpec,
                                             AbstractRootChecksContainerSpec parentCheckRootContainer,
                                             CheckModel myCheckModel,
                                             CheckMiningParametersModel miningParameters,
                                             DataTypeCategory columnTypeCategory,
                                             DqoRuleMiningConfigurationProperties checkMiningConfigurationProperties,
                                             JsonSerializer jsonSerializer,
                                             RuleMiningRuleRegistry ruleMiningRuleRegistry) {
        if (!miningParameters.isProposeBoolPercentChecks()) {
            return false;
        }

        CheckType checkType = parentCheckRootContainer.getCheckType();
        if (checkType != CheckType.profiling && sourceProfilingCheck.getProfilingCheckModel() != null &&
                sourceProfilingCheck.getProfilingCheckModel().getRule().hasAnyRulesConfigured()) {
            // copy the results from an already configured profiling checks
            return super.proposeCheckConfiguration(sourceProfilingCheck, dataAssetProfilingResults, tableProfilingResults,
                    tableSpec, parentCheckRootContainer, myCheckModel, miningParameters,
                    columnTypeCategory, checkMiningConfigurationProperties, jsonSerializer, ruleMiningRuleRegistry);
        }

        if (sourceProfilingCheck.getActualValue() == null) {
            // try to calculate the value from statistics
            Long trueCount = null;
            Long falseCount = null;

            ColumnDataAssetProfilingResults columnDataAssetProfilingResults = (ColumnDataAssetProfilingResults) dataAssetProfilingResults;
            for (ProfilingSampleValue profilingSampleValue : columnDataAssetProfilingResults.getSampleValues()) {
                if (!(profilingSampleValue.getValue() instanceof Boolean)) {
                    return false;
                }

                if (Objects.equals(profilingSampleValue.getValue(), true)) {
                    trueCount = profilingSampleValue.getCount();
                }

                if (Objects.equals(profilingSampleValue.getValue(), false)) {
                    falseCount = profilingSampleValue.getCount();
                }
            }

            if (trueCount == null || falseCount == null) {
                return false;
            }

            double truePercent = trueCount * 100.0 / (trueCount + falseCount);
            sourceProfilingCheck.setActualValue(truePercent);
            sourceProfilingCheck.setExecutedAt(Instant.now()); // just to avoid null values
        }

        if (sourceProfilingCheck.getActualValue() > 50.0) {
            return false; // we will configure the "false percent" check instead, we always configure the check for a less popular value
        }

        return super.proposeCheckConfiguration(sourceProfilingCheck, dataAssetProfilingResults, tableProfilingResults,
                tableSpec, parentCheckRootContainer, myCheckModel, miningParameters, columnTypeCategory,
                checkMiningConfigurationProperties, jsonSerializer, ruleMiningRuleRegistry);
    }
}