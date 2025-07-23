/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
import com.dqops.sensors.column.acceptedvalues.ColumnTextTextValidCountryCodePercentSensorParametersSpec;
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
 * This check measures the percentage of text values that are valid two-letter country codes.
 * It raises a data quality issue when the percentage of valid country codes (excluding null values) falls below a minimum accepted rate.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextValidCountryCodePercentCheckSpec
        extends AbstractCheckSpec<ColumnTextTextValidCountryCodePercentSensorParametersSpec, MinPercentRule100WarningParametersSpec, MinPercentRule100ErrorParametersSpec, MinPercentRule95ParametersSpec> {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextValidCountryCodePercentCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
        }
    };

    /**
     * Set of country codes.
     */
    public static final Set<String> COUNTRY_CODES_2 = new HashSet<>(
            List.of(new String[] { "AF",	"AL",	"DZ",	"AS",	"AD",	"AO",	"AI",	"AQ",	"AG",	"AR",	"AM",	"AW",	"AU",	"AT",	"AZ",	"BS",	"BH",	"BD",	"BB",	"BY",	"BE",	"BZ",	"BJ",	"BM",	"BT",	"BO",	"BA",	"BW",	"BR",	"IO",	"VG",	"BN",	"BG",	"BF",	"BI",	"KH",	"CM",	"CA",	"CV",	"KY",	"CF",	"TD",	"CL",	"CN",	"CX",	"CC",	"CO",	"KM",	"CK",	"CR",	"HR",	"CU",	"CW",	"CY",	"CZ",	"CD",	"DK",	"DJ",	"DM",	"DO",	"TL",	"EC",	"EG",	"SV",	"GQ",	"ER",	"EE",	"ET",	"FK",	"FO",	"FJ",	"FI",	"FR",	"PF",	"GA",	"GM",	"GE",	"DE",	"GH",	"GI",	"GR",	"GL",	"GD",	"GU",	"GT",	"GG",	"GN",	"GW",	"GY",	"HT",	"HN",	"HK",	"HU",	"IS",	"IN",	"ID",	"IR",	"IQ",	"IE",	"IM",	"IL",	"IT",	"CI",	"JM",	"JP",	"JE",	"JO",	"KZ",	"KE",	"KI",	"XK",	"KW",	"KG",	"LA",	"LV",	"LB",	"LS",	"LR",	"LY",	"LI",	"LT",	"LU",	"MO",	"MK",	"MG",	"MW",	"MY",	"MV",	"ML",	"MT",	"MH",	"MR",	"MU",	"YT",	"MX",	"FM",	"MD",	"MC",	"MN",	"ME",	"MS",	"MA",	"MZ",	"MM",	"NA",	"NR",	"NP",	"NL",	"AN",	"NC",	"NZ",	"NI",	"NE",	"NG",	"NU",	"KP",	"MP",	"NO",	"OM",	"PK",	"PW",	"PS",	"PA",	"PG",	"PY",	"PE",	"PH", "PN", "PL",	"PT",	"PR",	"QA",	"CG",	"RE",	"RO",	"RU",	"RW",	"BL",	"SH",	"KN",	"LC",	"MF",	"PM",	"VC",	"WS",	"SM",	"ST",	"SA",	"SN",	"RS",	"SC",	"SL",	"SG",	"SX",	"SK",	"SI",	"SB",	"SO",	"ZA",	"KR",	"SS",	"ES",	"LK",	"SD",	"SR",	"SJ",	"SZ",	"SE",	"CH",	"SY",	"TW",	"TJ",	"TZ",	"TH",	"TG",	"TK",	"TO",	"TT",	"TN",	"TR",	"TM",	"TC",	"TV",	"VI",	"UG",	"UA",	"AE",	"GB",	"US",	"UY",	"UZ",	"VU",	"VA",	"VE",	"VN",	"WF",	"EH",	"YE",	"ZM",	"ZW"})
        );

    @JsonPropertyDescription("Data quality check parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ColumnTextTextValidCountryCodePercentSensorParametersSpec parameters = new ColumnTextTextValidCountryCodePercentSensorParametersSpec();

    @JsonPropertyDescription("Alerting threshold that raises a data quality warning that is considered as a passed data quality check")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MinPercentRule100WarningParametersSpec warning;

    @JsonPropertyDescription("Default alerting threshold for a maximum percentage of rows with a valid country code strings in a column that raises a data quality error (alert).")
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
    public ColumnTextTextValidCountryCodePercentSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new row count sensor parameter object.
     * @param parameters Row count parameters.
     */
    public void setParameters(ColumnTextTextValidCountryCodePercentSensorParametersSpec parameters) {
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
        return "Minimum percentage of rows containing valid country codes";
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
                return ColumnTextValidCountryCodePercentCheckSpec.COUNTRY_CODES_2.contains(upperCaseName);
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