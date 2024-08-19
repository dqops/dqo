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
package com.dqops.checks.column.checkspecs.acceptedvalues;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckType;
import com.dqops.checks.DefaultDataQualityDimensions;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.rules.comparison.*;
import com.dqops.sensors.column.acceptedvalues.ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec;
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
import java.util.*;

/**
 * A column-level check that counts how many expected text values are among the TOP most popular values in the column.
 * The check will first count the number of occurrences of each column's value and will pick the TOP X most popular values (configurable by the 'top' parameter).
 * Then, it will compare the list of most popular values to the given list of expected values that should be most popular.
 * This check will verify how many supposed most popular values (provided in the 'expected_values' list) were not found in the top X most popular values in the column.
 * This check is helpful in analyzing string columns with frequently occurring values, such as country codes for countries with the most customers.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnExpectedTextsInTopValuesCountCheckSpec
        extends AbstractCheckSpec<ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec, MaxMissingRule0WarningParametersSpec, MaxMissingRule0ErrorParametersSpec, MaxMissingRule2ParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnExpectedTextsInTopValuesCountCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    /**
     * The multiplier applied to find the most common values when the rule is proposed.
     */
    public static final double MIN_RATE_OF_TOP_PROPOSED_VALUE = 0.05;

    /**
     * The multiplier of the count of two adjacent samples (when sorted descending by count of occurrence) to consider as a significant drop of the values
     * to be used as the cliff (the index of the last sample value that is added to the expected values).
     */
    public static final double CLIFF_SAMPLE_COUNT_DROP_MULTIPLIER = 0.7;

    @JsonPropertyDescription("Data quality check parameters that specify a list of expected most popular text values that should be found in the column. The second parameter is 'top', which is the limit of the most popular column values to find in the tested column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec parameters = new ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec();

    @JsonPropertyDescription("Alerting threshold that raises a data quality warning when too many expected values were not found among the TOP most popular values in the tested column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MaxMissingRule0WarningParametersSpec warning;

    @JsonPropertyDescription("Alerting threshold that raises a data quality error when too many expected values were not found among the TOP most popular values in the tested column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MaxMissingRule0ErrorParametersSpec error;

    @JsonPropertyDescription("Alerting threshold that raises a data quality fatal issue when too many expected values were not found among the TOP most popular values in the tested column.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MaxMissingRule2ParametersSpec fatal;

    /**
     * Returns the parameters of the sensor.
     * @return Sensor parameters.
     */
    @Override
    public ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     * @param parameters Row count parameters.
     */
    public void setParameters(ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec parameters) {
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
    public MaxMissingRule0WarningParametersSpec getWarning() {
        return this.warning;
    }

    /**
     * Sets a new warning level alerting threshold.
     * @param warning Warning alerting threshold to set.
     */
    public void setWarning(MaxMissingRule0WarningParametersSpec warning) {
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
    public MaxMissingRule0ErrorParametersSpec getError() {
        return this.error;
    }

    /**
     * Sets a new error level alerting threshold.
     * @param error Error alerting threshold to set.
     */
    public void setError(MaxMissingRule0ErrorParametersSpec error) {
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
    public MaxMissingRule2ParametersSpec getFatal() {
        return this.fatal;
    }

    /**
     * Sets a new fatal level alerting threshold.
     * @param fatal Fatal alerting threshold to set.
     */
    public void setFatal(MaxMissingRule2ParametersSpec fatal) {
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
        return "Verify that the most popular text values match the list of expected values";
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
        if (!miningParameters.isProposeTopValuesChecks()) {
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

        if (!(dataAssetProfilingResults instanceof ColumnDataAssetProfilingResults)) {
            return false;
        }

        ColumnDataAssetProfilingResults columnDataAssetProfilingResults = (ColumnDataAssetProfilingResults) dataAssetProfilingResults;
        if (sourceProfilingCheck.getActualValue() == null || this.parameters.getExpectedValues() == null || this.parameters.getExpectedValues().isEmpty()) {
            if (columnTypeCategory != null && columnTypeCategory != DataTypeCategory.text) {
                return false;
            }

            Double percentOfStringValues = columnDataAssetProfilingResults.matchPercentageOfSamples(value -> {
                if (!(value instanceof String)) {
                    return false;
                }

                return true;
            });

            if (percentOfStringValues == null || percentOfStringValues < 100.0) {
                return false; // mixed values, not text values
            }

            Long notNullsCount = columnDataAssetProfilingResults.getNotNullsCount();
            if (notNullsCount == null || notNullsCount < checkMiningConfigurationProperties.getMinReasonableNotNullsCount()) {
                return false; // too little non-null values
            }

            List<String> topExpectedValues = new ArrayList<>();
            long previousCountOfValues = columnDataAssetProfilingResults.getSampleValues().get(0).getCount();
            Integer cliffIndex = null;
            for (int i = 0; i < checkMiningConfigurationProperties.getMaxExpectedTextsInTopValues() + 1 && i < columnDataAssetProfilingResults.getSampleValues().size(); i++) {
                ProfilingSampleValue sampleValue = columnDataAssetProfilingResults.getSampleValues().get(i);

                if (previousCountOfValues * CLIFF_SAMPLE_COUNT_DROP_MULTIPLIER > sampleValue.getCount()) {
                    cliffIndex = i - 1;
                }
                previousCountOfValues = sampleValue.getCount();

                Double rateOfTotalValues = sampleValue.getCount() / (double) notNullsCount;
                if (rateOfTotalValues < 1.0 / checkMiningConfigurationProperties.getMaxExpectedTextsInTopValues() * MIN_RATE_OF_TOP_PROPOSED_VALUE) {
                    break; // do not add this value, it is too rare, stopping here
                }
            }

            if (cliffIndex == null) {
                // there is no cliff, all most common values are present at the same intervals, cannot apply top values
                return false;
            }

            for (int i = 0; i <= cliffIndex && i < checkMiningConfigurationProperties.getMaxExpectedTextsInTopValues(); i++) {
                ProfilingSampleValue profilingSampleValue = columnDataAssetProfilingResults.getSampleValues().get(i);
                topExpectedValues.add(profilingSampleValue.getValue().toString());
            }

            this.parameters.setExpectedValues(topExpectedValues);
            this.parameters.setTop((long)topExpectedValues.size());

            switch (miningParameters.getSeverityLevel()) {
                case warning: {
                    this.setWarning(new MaxMissingRule0WarningParametersSpec(0));
                    break;
                }
                case error: {
                    this.setError(new MaxMissingRule0ErrorParametersSpec(0));
                    break;
                }
                case fatal: {
                    this.setFatal(new MaxMissingRule2ParametersSpec(0));
                }
            }

            return true;
        }

        return super.proposeCheckConfiguration(sourceProfilingCheck, dataAssetProfilingResults, tableProfilingResults,
                tableSpec, parentCheckRootContainer, myCheckModel, miningParameters, columnTypeCategory,
                checkMiningConfigurationProperties, jsonSerializer, ruleMiningRuleRegistry);
    }
}
