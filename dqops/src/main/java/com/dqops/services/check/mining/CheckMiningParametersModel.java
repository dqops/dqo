/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.mining;

import com.dqops.rules.TargetRuleSeverityLevel;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Data quality check rule mining parameters. Configure what type of checks should be configured.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckMiningParametersModel", description = "Data quality check rule mining parameters. Configure what type of checks should be configured.")
public class CheckMiningParametersModel implements Cloneable {
    /**
     * The default severity level for rules that are proposed by the rule mining engine. The default value is 'error'.
     */
    @JsonPropertyDescription("The default severity level for rules that are proposed by the rule mining engine. The default value is 'error'.")
    private TargetRuleSeverityLevel severityLevel = TargetRuleSeverityLevel.error;

    /**
     * Optional filter for the check category names, supports filtering with prefixes and suffixes defined as a '*' character.
     */
    @JsonPropertyDescription("Optional filter for the check category names, supports filtering with prefixes and suffixes defined as a '*' character.")
    private String categoryFilter;

    /**
     * Optional filter for the check names, supports filtering with prefixes and suffixes defined as a '*' character.
     */
    @JsonPropertyDescription("Optional filter for the check names, supports filtering with prefixes and suffixes defined as a '*' character.")
    private String checkNameFilter;

    /**
     * Optional filter for the column names, supports filtering with prefixes and suffixes defined as a '*' character.
     */
    @JsonPropertyDescription("Optional filter for the column names, supports filtering with prefixes and suffixes defined as a '*' character.")
    private String columnNameFilter;

    /**
     * Copy also the configuration of profiling checks that failed.
     */
    @JsonPropertyDescription("Copy also the configuration of profiling checks that failed.")
    private boolean copyFailedProfilingChecks;

    /**
     * Copy also the configuration of profiling checks that are disabled.
     */
    @JsonPropertyDescription("Copy also the configuration of profiling checks that are disabled.")
    private boolean copyDisabledProfilingChecks;

    /**
     * Copy the configuration of valid profiling checks.
     */
    @JsonPropertyDescription("Copy the configuration of valid profiling checks.")
    private boolean copyProfilingChecks = true;

    /**
     * Propose the rules for default checks that were activated using data quality check patterns (policies). The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the rules for default checks that were activated using data quality check patterns (policies). The default value of this parameter is 'true'.")
    private boolean reconfigurePolicyEnabledChecks = true;

    /**
     * Propose the configuration of data quality checks from statistics.
     */
    @JsonPropertyDescription("Propose the configuration of data quality checks from statistics.")
    private boolean proposeChecksFromStatistics = true;

    /**
     * Propose the default configuration of the minimum row count for monitoring checks (full table scans). The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration of the minimum row count for monitoring checks (full table scans). The default value of this parameter is 'true'.")
    private boolean proposeMinimumRowCount = true;

    /**
     * Propose the default configuration of the column count check. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration of the column count check. The default value of this parameter is 'true'.")
    private boolean proposeColumnCount = true;

    /**
     * Propose the default configuration of the timeliness checks, including an accepted freshness delay. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration of the timeliness checks, including an accepted freshness delay. The default value of this parameter is 'true'.")
    private boolean proposeTimelinessChecks = true;

    /**
     * Propose the default configuration the null checks that verify that there are no null values. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration the null checks that verify that there are no null values. The default value of this parameter is 'true'.")
    private boolean proposeNullsChecks = true;

    /**
     * Propose the default configuration the not-null checks that validate scale of not-nulls (require some null values, and require some not-null values). The default value of this parameter is 'false'.
     */
    @JsonPropertyDescription("Propose the default configuration the not-null checks that validate scale of not-nulls (require a mix of some not-null and null values).The default value of this parameter is 'false'.")
    private boolean proposeNotNullsChecks = true;

    /**
     * Propose the default configuration the detected data type of values in a text column check, when text columns contain an uniform type such as integers or dates. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration the detected data type of values in a text column check, when text columns contain an uniform type such as integers or dates. The default value of this parameter is 'true'.")
    private boolean proposeTextValuesDataType = true;

    /**
     * Enables a rule on the column's schema check that verifies if the column exists. It is enabled on columns that were detected as existing during data profiling. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Enables a rule on the column's schema check that verifies if the column exists. It is enabled on columns that were detected as existing during data profiling. The default value of this parameter is 'true'.")
    private boolean proposeColumnExists = true;

    /**
     * Propose the default configuration the uniqueness checks that validate the number of distinct and duplicate values. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration the uniqueness checks that validate the number of distinct and duplicate values. The default value of this parameter is 'true'.")
    private boolean proposeUniquenessChecks = true;

    /**
     * Propose the default configuration of numeric checks that validate the ranges of numeric values, and aggregated measures such as minimum, maximum, mean and sum of values. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration of numeric checks that validate the ranges of numeric values, and aggregated measures such as minimum, maximum, mean and sum of values. The default value of this parameter is 'true'.")
    private boolean proposeNumericRanges = true;

    /**
     * Propose the default configuration of the median and percentile in range checks that validate the value at a given percentile, such as a value in middle of all column values. The default value of this parameter is 'false' because calculating the median requires running a separate query on the data source, which is not advisable for data observability.
     */
    @JsonPropertyDescription("Propose the default configuration of the median and percentile in range checks that validate the value at a given percentile, such as a value in middle of all column values. The default value of this parameter is 'false' because calculating the median requires running a separate query on the data source, which is not advisable for data observability.")
    private boolean proposePercentileRanges = true;

    /**
     * Propose the default configuration of the text length checks. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration of the text length checks. The default value of this parameter is 'true'.")
    private boolean proposeTextLengthRanges = true;

    /**
     * Propose the default configuration of the minimum and maximum word count of text columns. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration of the minimum and maximum word count of text columns. The default value of this parameter is 'true'.")
    private boolean proposeWordCountRanges = true;

    /**
     * Propose the configuration the categorical values checks from the accepted values category. These checks will be configured to ensure that the column contains only sample values that were identified during data profiling. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the configuration the categorical values checks from the accepted values category. These checks will be configured to ensure that the column contains only sample values that were identified during data profiling. The default value of this parameter is 'true'.")
    private boolean proposeValuesInSetChecks = true;

    /**
     * Configure the values in set checks from the accepted values category to raise data quality issues for rare values. DQOps will not add rare categorical values to the list of expected values. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Configure the values in set checks from the accepted values category to raise data quality issues for rare values. DQOps will not add rare categorical values to the list of expected values. The default value of this parameter is 'true'.")
    private boolean valuesInSetTreatRareValuesAsInvalid = true;

    /**
     * Proposes the configuration the top values checks from the accepted values category. These checks find the most common values in a table and ensure that they are the same values that were identified during data profiling. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the configuration the text values in the top values checks from the accepted values category. These checks find the most common values in a table and ensure that they are the same values that were identified during data profiling. The default value of this parameter is 'true'.")
    private boolean proposeTopValuesChecks = true;

    /**
     * Propose the configuration the data type conversion checks that verify if text values can be converted to more specific data types such as boolean, date, float or integer. This type of checks are used to verify raw tables in the landing zones. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the configuration the data type conversion checks that verify if text values can be converted to more specific data types such as boolean, date, float or integer. This type of checks are used to verify raw tables in the landing zones. The default value of this parameter is 'true'.")
    private boolean proposeTextConversionChecks = true;

    /**
     * Propose the default configuration for the checks that measure the percentage of boolean values. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration for the checks that measure the percentage of boolean values. The default value of this parameter is 'true'.")
    private boolean proposeBoolPercentChecks = true;

    /**
     * Propose the default configuration for the date and datetime checks that detect invalid dates. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration for the date and datetime checks that detect invalid dates. The default value of this parameter is 'true'.")
    private boolean proposeDateChecks = true;

    /**
     * Propose the default configuration for the patterns check that validate the format of popular text patterns, such as UUIDs, phone numbers, or emails. DQOps will configure these data quality checks when analyzed columns contain enough values matching a standard pattern. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration for the patterns check that validate the format of popular text patterns, such as UUIDs, phone numbers, or emails. DQOps will configure these data quality checks when analyzed columns contain enough values matching a standard pattern. The default value of this parameter is 'true'.")
    private boolean proposeStandardPatternChecks = true;

    /**
     * Analyze sample text values and try to find a regular expression that detects valid values similar to the sample values. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Analyze sample text values and try to find a regular expression that detects valid values similar to the sample values. The default value of this parameter is 'true'.")
    private boolean detectRegularExpressions = true;

    /**
     * Propose the default configuration for the whitespace detection checks. Whitespace checks detect common data quality issues with storing text representations of null values, such as 'null', 'None', 'n/a' and other texts that should be stored as regular NULL values. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration for the whitespace detection checks. Whitespace checks detect common data quality issues with storing text representations of null values, such as 'null', 'None', 'n/a' and other texts that should be stored as regular NULL values. The default value of this parameter is 'true'.")
    private boolean proposeWhitespaceChecks = true;

    /**
     * Applies rules to Personal Identifiable Information checks (sensitive data), but only when the checks were activated manually as profiling checks, or through a data quality check pattern that scans all columns for PII data. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Applies rules to Personal Identifiable Information checks (sensitive data), but only when the checks were activated manually as profiling checks, or through a data quality check pattern that scans all columns for PII data. The default value of this parameter is 'true'.")
    private boolean applyPiiChecks = true;

    /**
     * Propose the default configuration for custom checks that use built-in data quality rules. The default value of this parameter is 'true'.
     */
    @JsonPropertyDescription("Propose the default configuration for custom checks that use built-in data quality rules. The default value of this parameter is 'true'.")
    private boolean proposeCustomChecks = true;

    /**
     * The percentage value captured by a profiling check (for example 0.03% of errors or 99.97% of valid) that is used to propose a percentage rule that will treat the values as errors (i.e., max_percent = 0%, or min_percent = 100%).
     */
    @JsonPropertyDescription("The percentage value captured by a profiling check (for example 0.03% of errors or 99.97% of valid) that is used to propose a percentage rule that will treat the values as errors (i.e., max_percent = 0%, or min_percent = 100%).")
    private Double failChecksAtPercentErrorRows;

    /**
     * The default maximum percentage of invalid rows for which the rule engine should configure rule values, especially min_percent, min_count or max_percent.
     */
    @JsonPropertyDescription("The default maximum percentage of invalid rows for which the rule engine should configure rule values, especially min_percent, min_count or max_percent.")
    private Double maxPercentErrorRowsForPercentChecks;


    /**
     * Called by Jackson property when an undeclared property was present in the deserialized YAML or JSON text. Does nothing, just present to allow accepting old parameter names.
     * @param name Undeclared (and ignored) property name.
     * @param value Property value.
     */
    @JsonAnySetter
    private void handleUndeclaredProperty(String name, Object value) {
    }

    /**
     * Creates and returns a copy of this object.
     * @return Cloned instance.
     */
    @Override
    public CheckMiningParametersModel clone() {
        try {
            return (CheckMiningParametersModel)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported", ex);
        }
    }
}
