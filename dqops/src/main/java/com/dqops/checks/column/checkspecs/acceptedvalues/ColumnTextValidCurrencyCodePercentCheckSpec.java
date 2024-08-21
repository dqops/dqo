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
import com.dqops.rules.comparison.MinPercentRule95ParametersSpec;
import com.dqops.rules.comparison.MinPercentRule100ErrorParametersSpec;
import com.dqops.rules.comparison.MinPercentRule100WarningParametersSpec;
import com.dqops.sensors.column.acceptedvalues.ColumnTextTextValidCurrencyCodePercentSensorParametersSpec;
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
 * This check measures the percentage of text values that are valid currency names. It raises a data quality issue when the percentage of valid currency names (excluding null values) falls below a minimum accepted rate.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextValidCurrencyCodePercentCheckSpec
        extends AbstractCheckSpec<ColumnTextTextValidCurrencyCodePercentSensorParametersSpec, MinPercentRule100WarningParametersSpec, MinPercentRule100ErrorParametersSpec, MinPercentRule95ParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextValidCurrencyCodePercentCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    /**
     * Set of currency codes.
     */
    public static final Set<String> CURRENCY_CODES = new HashSet<>(
            List.of(new String[] {"ALL",	"AFN",	"ARS",	"AWG",	"AUD",	"AZN",	"BSD",	"BBD",	"BYN",	"BZD",	"BMD",	"BOB",	"BAM",	"BWP",	"BGN",	"BRL",	"BND",	"KHR",	"CAD",	"KYD",	"CLP",	"CNY",	"COP",	"CRC",	"HRK",	"CUP",	"CZK",	"DKK",	"DOP",	"XCD",	"EGP",	"SVC",	"EUR",	"FKP",	"FJD",	"GHS",	"GIP",	"GTQ",	"GGP",	"GYD",	"HNL",	"HKD",	"HUF",	"ISK",	"INR",	"IDR",	"IRR",	"IMP",	"ILS",	"JMD",	"JPY",	"JEP",	"KZT",	"KPW",	"KRW",	"KGS",	"LAK",	"LBP",	"LRD",	"MKD",	"MYR",	"MUR",	"MXN",	"MNT",	"MZN",	"NAD",	"NPR",	"ANG",	"NZD",	"NIO",	"NGN",	"NOK",	"OMR",	"PKR",	"PAB",	"PYG",	"PEN",	"PHP",	"PLN",	"QAR",	"RON",	"RUB",	"SHP",	"SAR",	"RSD",	"SCR",	"SGD",	"SBD",	"SOS",	"ZAR",	"LKR",	"SEK",	"CHF",	"SRD",	"SYP",	"TWD",	"THB",	"TTD",	"TRY",	"TVD",	"UAH",	"AED",	"GBP",	"USD",	"UYU",	"UZS",	"VEF",	"VND",	"YER",	"ZWD",	"LEK",	"؋",	"$",	"Ƒ",	"₼",	"BR",	"BZ$",	"$B",	"KM",	"P",	"ЛВ",	"R$",	"៛",	"¥",	"₡",	"KN",	"₱",	"KČ",	"KR",	"RD$", "£",	"€",	"¢",	"Q",	"L",	"FT",	"₹",	"RP",	"﷼",	"₪",	"J$",	"₩",	"₭",	"ДЕН",	"RM",	"₨",	"₮",	"د.إ",	"MT",	"C$",	"₦",	"B/.",	"GS",	"S/.", "ZŁ",	"LEI",	"ДИН.",	"S",	"R",	"NT$",	"฿",	"TT$",	"₺",	"₴",	"$U",	"BS",	"₫", "Z$"})
    );


    @JsonPropertyDescription("Data quality check parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextTextValidCurrencyCodePercentSensorParametersSpec parameters = new ColumnTextTextValidCurrencyCodePercentSensorParametersSpec();

    @JsonPropertyDescription("Alerting threshold that raises a data quality warning that is considered as a passed data quality check")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MinPercentRule100WarningParametersSpec warning;

    @JsonPropertyDescription("Default alerting threshold for a maximum percentage of rows with a valid currency code strings in a column that raises a data quality error (alert).")
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
    public ColumnTextTextValidCurrencyCodePercentSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     * @param parameters Row count parameters.
     */
    public void setParameters(ColumnTextTextValidCurrencyCodePercentSensorParametersSpec parameters) {
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
     * Returns an alternative check's friendly name that is shown on the check editor.
     *
     * @return An alternative name, or null when the check has no alternative name to show.
     */
    @Override
    @JsonIgnore
    public String getFriendlyName() {
        return "Minimum percentage of rows containing valid currency codes";
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
        if (!miningParameters.isProposeValuesInSetChecks()) {
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
        if (sourceProfilingCheck.getActualValue() == null) {
            if (columnTypeCategory != null && columnTypeCategory != DataTypeCategory.text) {
                return false;
            }

            Double percentOfValidValues = columnDataAssetProfilingResults.matchPercentageOfSamples(value -> {
                if (!(value instanceof String)) {
                    return false;
                }

                String upperCaseName = value.toString().toUpperCase(Locale.ENGLISH);
                return ColumnTextValidCurrencyCodePercentCheckSpec.CURRENCY_CODES.contains(upperCaseName);
            });

            if (percentOfValidValues == null || (100.0 - percentOfValidValues) > miningParameters.getFailChecksAtPercentErrorRows()) {
                return false;
            }

            sourceProfilingCheck.setActualValue(100.0); // just fake number like there were no dates, to enable a check, even if it fails, we cannot calculate a correct value from the samples
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