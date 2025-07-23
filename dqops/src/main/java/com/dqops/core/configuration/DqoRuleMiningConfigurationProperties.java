/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;

/**
 * Configuration POJO with the configuration for the dqo.rule-mining that configures how the rule mining engine suggests rules.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.rule-mining")
@EqualsAndHashCode(callSuper = false)
@Data
public class DqoRuleMiningConfigurationProperties implements Cloneable {
    /**
     * The rate of the current row count obtained from statistics that is used to configure the row_count check value by the check mining engine. The default value is 0.99 of the current row count.
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.min-count-rate"},
            description = "The rate of the current min_count value, such as the row count, or the count of not-null values, obtained from statistics that is used to configure the row_count and not_nulls_count checks by the check mining engine. " +
                    "The default value is 0.90 of the current count captured by the statistics (row count, not-nulls count).", defaultValue = "0.90")
    private double minCountRate = 0.90;

    /**
     * The multiplier of the last known table timeliness checks (freshness, staleness, ingestion delay) that is used to propose the configuration of the max days rule threshold by the rule mining engine.
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.timeliness-max-days-multiplier"},
            description = "he multiplier of the last known table timeliness checks (freshness, staleness, ingestion delay) that is used to propose the configuration of the max days rule threshold by the rule mining engine." +
                    "The default value is 2.0x the last known delay.", defaultValue = "2.0")
    private double timelinessMaxDaysMultiplier = 2.0;

    /**
     * The multiplier of the last known percent that is extended by this delta (as a rate/proportion of the percentage) to configure a passing percentage check.
     * The default value is 0.3. For this value and when the last known max_percent was 10%%, DQOps rule mining engine will propose a save max_count 13%%.
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.percent-check-delta-rate"},
            description = "The multiplier of the last known percent that is extended by this delta (as a rate/proportion of the percentage) to configure a passing percentage check." +
                    "The default value is 0.3. For this value and when the last known max_percent was 10%%, DQOps rule mining engine will propose a save max_count 13%%. " +
                    "The additional 3%% of the delta is 0.3 * 10%%.", defaultValue = "0.3")
    private double percentCheckDeltaRate = 0.3;

    /**
     * The default percentage value captured by a profiling check (for example 0.03%% of errors or 99.97%% of valid) that is used to propose a percentage rule that
     * will treat the values as errors (i.e., max_percent = 0%%, or min_percent = 100%%).
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.default-fail-checks-at-percent-error-rows"},
            description = "The default percentage value captured by a profiling check (for example 0.03%% of errors or 99.97%% of valid) " +
                    "that is used to propose a percentage rule that will treat the values as errors (i.e., max_percent = 0%%, or min_percent = 100%%)." +
                    "The default value is 2%%.", defaultValue = "2")
    private double defaultFailChecksAtPercentErrorRows = 2.0;

    /**
     * The default maximum percentage of invalid rows for which the rule engine should configure rule values, especially min_percent, min_count or max_percent.
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.default-max-percent-error-rows"},
            description = "The default maximum percentage of invalid rows for which the rule engine should configure rule values, especially min_percent, min_count or max_percent.", defaultValue = "5.0")
    private double defaultMaxPercentErrorRows = 5.0;

    /**
     * The default delta that is added to the proposed maximum value, or subtracted from a proposed minimum value. It is calculated as a rate of the current value. The default value is 0.1, which means that when the detected minimum value is 5.0, the proposed minimum value in the rule will be 4.5.
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.min-max-value-rate-delta"},
            description = "The default delta that is added to the proposed maximum value, or subtracted from a proposed minimum value. It is calculated as a rate of the current value. " +
                    "The default value is 0.1, which means that when the detected minimum value is 5.0, the proposed minimum value in the rule will be 4.5.", defaultValue = "0.1")
    private double minMaxValueRateDelta = 0.1;

    /**
     * The default rate (fraction) of the number of rows with not-null values that must contain distinct values to apply the distinct count between check. The default is 0.1, which is 10%% of the count of not-null values.
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.not-null-count-rate-for-duplicate-count"},
            description = "The default rate (fraction) of the number of rows with not-null values that must contain distinct values to apply the distinct count between check. " +
                    "The default is 0.01, which is 1%% of the count of not-null values.", defaultValue = "0.01")
    private double notNullCountRateForDuplicateCount = 0.01;

    /**
     * The default maximum distinct count that is used to activate the distinct_count check. Above the limit, DQOps will configure the distinct_percent check. The default value is 1000 rows.
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.max-distinct-count"},
            description = "The default maximum distinct count that is used to activate the distinct_count check. " +
                    "Above the limit, DQOps will configure the distinct_percent check. The default value is 1000 rows.", defaultValue = "1000")
    private long maxDistinctCount = 1000;

    /**
     * The default minimum reasonable count of not-null values that must be satisfied to apply some checks that validate a range of row counts (like a distinct count between).
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.min-reasonable-not-null-count"},
            description = "The default minimum reasonable count of not-null values that must be satisfied to apply some checks " +
                    "that validate a range of row counts (like a distinct count between).", defaultValue = "100")
    private long minReasonableNotNullsCount = 100;

    /**
     * The number of days to add to the current system date that DQOps rule mining engine uses to set the maximum date in the date_in_range data quality check.
     * The default configuration sets a date that is 10 years ahead.
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.days-in-range-max-date-days-ahead"},
            description = "The number of days to add to the current system date that DQOps rule mining engine uses to set the maximum date in the date_in_range data quality check. " +
                    "The default configuration sets a date that is 10 years ahead.", defaultValue = "3650")
    private long daysInRangeMaxDateDaysAhead = 356 * 10;

    /**
     * The number of days to subtract from the earliest found date in a column that DQOps rule mining engine uses to set the minimum date in the date_in_range data quality check.
     * The default configuration sets a date that is 2 days before.
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.days-in-range-min-date-days-before"},
            description = "The number of days to subtract from the earliest found date in a column that DQOps rule mining engine uses to set the minimum date in the date_in_range data quality check. " +
                    "The default configuration sets a date that is 2 days before.", defaultValue = "2")
    private long daysInRangeMinDateDaysBefore = 2;


    /**
     * The maximum number of samples in the sample values to use when generating a proposed configuration of the accepted_values checks.
     * If a column has more than this number of distinct values, DQOps will not configure the found in set checks. The default value is 50 samples.
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.max-column-samples-to-propose-accepted-values"},
            description = "The maximum number of samples in the sample values to use when generating a proposed configuration of the accepted_values checks. " +
                    "If a column has more than this number of distinct values, DQOps will not configure the found in set checks. The default value is 50 samples.", defaultValue = "50")
    private int maxColumnSamplesToProposeAcceptedValues = 50;

    /**
     * The maximum number of top (most common) text values that are added to the expected_texts_in_top_values check. The default value is 5 top text values.
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.max-expected-texts-in-top-values"},
            description = "The maximum number of top (most common) text values that are added to the expected_texts_in_top_values check. The default value is 5 top text values.", defaultValue = "5")
    private int maxExpectedTextsInTopValues = 5;

    /**
     * The minimum word count that is required to apply word count count in range checks. The default value is 3 words.
     */
    @CommandLine.Option(names = {"--dqo.rule-mining.min-word-count"},
            description = "The minimum word count that is required to apply word count count in range checks. The default value is 3 words.", defaultValue = "3")
    private int minWordCount = 3;

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoRuleMiningConfigurationProperties clone() {
        try {
            DqoRuleMiningConfigurationProperties cloned = (DqoRuleMiningConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
