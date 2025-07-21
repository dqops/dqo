/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.checkspecs.datetime;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckType;
import com.dqops.checks.DefaultDataQualityDimensions;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.data.statistics.models.StatisticsMetricModel;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.rules.comparison.*;
import com.dqops.sensors.column.datetime.ColumnDateInRangePercentSensorParametersSpec;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mining.*;
import com.dqops.statistics.column.range.ColumnRangeMaxValueStatisticsCollectorSpec;
import com.dqops.statistics.column.range.ColumnRangeMinValueStatisticsCollectorSpec;
import com.dqops.utils.conversion.DateTypesConverter;
import com.dqops.utils.conversion.DoubleRounding;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.dqops.utils.serialization.JsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Verifies that the dates in date, datetime, or timestamp columns are within a reasonable range of dates.
 * The default configuration detects fake dates such as 1900-01-01 and 2099-12-31.
 * Measures the percentage of valid dates and raises a data quality issue when too many dates are found.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDateInRangePercentCheckSpec
        extends AbstractCheckSpec<ColumnDateInRangePercentSensorParametersSpec, MinPercentRule100WarningParametersSpec, MinPercentRule100ErrorParametersSpec, MinPercentRule95ParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDateInRangePercentCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    /**
     * The earliest date that we are accepting.
     */
    public static final LocalDate EARLIEST_ACCEPTED_DATE_BY_RULE_MINER = LocalDate.of(1900, 01, 02);

    /**
     * Special dates used often as fake dates. If we detect any of them, we will configure a minimum date after it.
     */
    public static final Set<LocalDate> SPECIAL_FAKE_DATES = new LinkedHashSet<>()
            {{
                add(LocalDate.of(1900, 01, 01));
                add(LocalDate.of(1901, 01, 01));
                add(LocalDate.of(1970, 01, 01));
                add(LocalDate.of(2000, 01, 01));
            }};

    /**
     * Sensor name.
     */
    public static final String SENSOR_NAME = ColumnDateInRangePercentSensorParametersSpec.SENSOR_NAME;

    @JsonPropertyDescription("Data quality check parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnDateInRangePercentSensorParametersSpec parameters = new ColumnDateInRangePercentSensorParametersSpec();

    @JsonPropertyDescription("Alerting threshold that raises a data quality warning that is considered as a passed data quality check")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MinPercentRule100WarningParametersSpec warning;

    @JsonPropertyDescription("Default alerting threshold for a set percentage of date values in the range defined by the user in a column that raises a data quality error (alert).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MinPercentRule100ErrorParametersSpec error;

    @JsonPropertyDescription("Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MinPercentRule95ParametersSpec fatal;

    /**
     * Returns the parameters of the sensor.
     * @return Sensor parameters.
     */
    @Override
    public ColumnDateInRangePercentSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     * @param parameters Row count parameters.
     */
    public void setParameters(ColumnDateInRangePercentSensorParametersSpec parameters) {
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
    public MinPercentRule100WarningParametersSpec getWarning() {
        return this.warning;
    }

    /**
     * Sets a new warning level alerting threshold.
     * @param warning Warning alerting threshold to set.
     */
    public void setWarning(MinPercentRule100WarningParametersSpec warning) {
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
    public MinPercentRule100ErrorParametersSpec getError() {
        return this.error;
    }

    /**
     * Sets a new error level alerting threshold.
     * @param error Error alerting threshold to set.
     */
    public void setError(MinPercentRule100ErrorParametersSpec error) {
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
    public MinPercentRule95ParametersSpec getFatal() {
        return this.fatal;
    }

    /**
     * Sets a new fatal level alerting threshold.
     * @param fatal Fatal alerting threshold to set.
     */
    public void setFatal(MinPercentRule95ParametersSpec fatal) {
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
        return "Minimum percentage of rows containing dates within an expected range";
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
        if (!miningParameters.isProposeDateChecks()) {
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
        boolean isDateType = columnTypeCategory == DataTypeCategory.datetime_date ||
                columnTypeCategory == DataTypeCategory.datetime_datetime ||
                columnTypeCategory == DataTypeCategory.datetime_timestamp ||
                ((columnTypeCategory == DataTypeCategory.text || columnTypeCategory == null) && !columnDataAssetProfilingResults.getSampleValues().isEmpty() &&
                        columnDataAssetProfilingResults.getSampleValues().stream().allMatch(sample -> sample.getInstantValue() != null));

        if (sourceProfilingCheck.getActualValue() == null) {
            if (!isDateType) {
                return false;
            }

            List<StatisticsMetricModel> minStatistics = columnDataAssetProfilingResults.getBasicStatisticsForSensor(
                    ColumnRangeMinValueStatisticsCollectorSpec.SENSOR_NAME, true);
            List<StatisticsMetricModel> maxStatistics = columnDataAssetProfilingResults.getBasicStatisticsForSensor(
                    ColumnRangeMaxValueStatisticsCollectorSpec.SENSOR_NAME, true);

            if (minStatistics.isEmpty() || minStatistics.get(0).getResult() == null ||
                maxStatistics.isEmpty() || maxStatistics.get(0).getResult() == null) {
                return false; // no results to propose
            }

            LocalDate minDateValue = DateTypesConverter.toLocalDate(minStatistics.get(0).getResult(), tableProfilingResults.getTimeZoneId());

            if (minDateValue != null) {
                if (minDateValue.isBefore(EARLIEST_ACCEPTED_DATE_BY_RULE_MINER)) {
                    this.parameters.setMinDate(EARLIEST_ACCEPTED_DATE_BY_RULE_MINER);
                } else {
                    if (SPECIAL_FAKE_DATES.contains(minDateValue)) {
                        this.parameters.setMinDate(minDateValue.plusDays(1L)); // a special date found, we will look for one day later
                    } else {
                        this.parameters.setMinDate(minDateValue.minusDays(checkMiningConfigurationProperties.getDaysInRangeMinDateDaysBefore()));
                    }
                }
            }

            LocalDate maxDateValue = DateTypesConverter.toLocalDate(maxStatistics.get(0).getResult(), tableProfilingResults.getTimeZoneId());
            LocalDate latestAcceptedDate = LocalDate.now().plusDays(180);

            if (maxDateValue != null && latestAcceptedDate.isAfter(maxDateValue)) {
                this.parameters.setMaxDate(maxDateValue.plusDays(checkMiningConfigurationProperties.getDaysInRangeMaxDateDaysAhead()));
            } else {
                this.parameters.setMaxDate(LocalDate.now().plusDays(checkMiningConfigurationProperties.getDaysInRangeMaxDateDaysAhead()));
            }

            sourceProfilingCheck.setActualValue(100.0); // just fake number like there were no dates, to enable a check, even if it fails
            sourceProfilingCheck.setExecutedAt(Instant.now());
        }

        if (sourceProfilingCheck.getActualValue() != null && (100.0 - sourceProfilingCheck.getActualValue()) > miningParameters.getMaxPercentErrorRowsForPercentChecks()) {
            return false; // do not configure this check, when the value was captured and there are too many future values
        }

        return super.proposeCheckConfiguration(sourceProfilingCheck, dataAssetProfilingResults, tableProfilingResults,
                tableSpec, parentCheckRootContainer, myCheckModel, miningParameters, columnTypeCategory,
                checkMiningConfigurationProperties, jsonSerializer, ruleMiningRuleRegistry);
    }
}