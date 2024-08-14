---
title: DQOps REST API rule_mining models reference
---
# DQOps REST API rule_mining models reference
The references of all objects used by [rule_mining](../operations/rule_mining.md) REST API operations are listed below.


## TargetRuleSeverityLevel
Default rule severity levels. Matches the severity level name (warning - 1, alert - 2, fatal - 3) with a numeric level.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|warning<br/>error<br/>fatal<br/>|

___

## CheckMiningParametersModel
Data quality check rule mining parameters. Configure what type of checks should be configured.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`severity_level`](#targetruleseveritylevel)</span>|The default severity level for rules that are proposed by the rule mining engine. The default value is 'error'.|*[TargetRuleSeverityLevel](#targetruleseveritylevel)*|
|<span class="no-wrap-code">`category_filter`</span>|Optional filter for the check category names, supports filtering with prefixes and suffixes defined as a '*' character.|*string*|
|<span class="no-wrap-code">`check_name_filter`</span>|Optional filter for the check names, supports filtering with prefixes and suffixes defined as a '*' character.|*string*|
|<span class="no-wrap-code">`column_name_filter`</span>|Optional filter for the column names, supports filtering with prefixes and suffixes defined as a '*' character.|*string*|
|<span class="no-wrap-code">`copy_failed_profiling_checks`</span>|Copy also the configuration of profiling checks that failed.|*boolean*|
|<span class="no-wrap-code">`copy_disabled_profiling_checks`</span>|Copy also the configuration of profiling checks that are disabled.|*boolean*|
|<span class="no-wrap-code">`copy_profiling_checks`</span>|Copy the configuration of valid profiling checks.|*boolean*|
|<span class="no-wrap-code">`propose_default_checks`</span>|Propose the rules for default checks that were activated using data quality check patterns (policies). The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_minimum_row_count`</span>|Propose the default configuration of the minimum row count for monitoring checks (full table scans). The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_column_count`</span>|Propose the default configuration of the column count check. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_timeliness_checks`</span>|Propose the default configuration of the timeliness checks, including an accepted freshness delay. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_nulls_checks`</span>|Propose the default configuration the null checks that verify that there are no null values. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_not_nulls_checks`</span>|Propose the default configuration the not-null checks that validate scale of not-nulls (require a mix of some not-null and null values).The default value of this parameter is 'false'.|*boolean*|
|<span class="no-wrap-code">`propose_text_values_data_type`</span>|Propose the default configuration the detected data type of values in a text column check, when text columns contain an uniform type such as integers or dates. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_column_exists`</span>|Enables a rule on the column's schema check that verifies if the column exists. It is enabled on columns that were detected as existing during data profiling. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_uniqueness_checks`</span>|Propose the default configuration the uniqueness checks that validate the number of distinct and duplicate values. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_numeric_ranges`</span>|Propose the default configuration of numeric checks that validate the ranges of numeric values, and aggregated measures such as minimum, maximum, mean and sum of values. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_percentile_ranges`</span>|Propose the default configuration of the median and percentile in range checks that validate the value at a given percentile, such as a value in middle of all column values. The default value of this parameter is 'false' because calculating the median requires running a separate query on the data source, which is not advisable for data observability.|*boolean*|
|<span class="no-wrap-code">`propose_text_length_ranges`</span>|Propose the default configuration of the text length checks. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_word_count_ranges`</span>|Propose the default configuration of the minimum and maximum word count of text columns. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_values_in_set_checks`</span>|Propose the configuration the categorical values checks from the accepted values category. These checks will be configured to ensure that the column contains only sample values that were identified during data profiling. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`values_in_set_treat_rare_values_as_invalid`</span>|Configure the values in set checks from the accepted values category to raise data quality issues for rare values. DQOps will not add rare categorical values to the list of expected values. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_top_values_checks`</span>|Propose the configuration the text values in the top values checks from the accepted values category. These checks find the most common values in a table and ensure that they are the same values that were identified during data profiling. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_text_conversion_checks`</span>|Propose the configuration the data type conversion checks that verify if text values can be converted to more specific data types such as boolean, date, float or integer. This type of checks are used to verify raw tables in the landing zones. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_bool_percent_checks`</span>|Propose the default configuration for the checks that measure the percentage of boolean values. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_date_checks`</span>|Propose the default configuration for the date and datetime checks that detect invalid dates. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_standard_pattern_checks`</span>|Propose the default configuration for the patterns check that validate the format of popular text patterns, such as UUIDs, phone numbers, or emails. DQOps will configure these data quality checks when analyzed columns contain enough values matching a standard pattern. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_whitespace_checks`</span>|Propose the default configuration for the whitespace detection checks. Whitespace checks detect common data quality issues with storing text representations of null values, such as 'null', 'None', 'n/a' and other texts that should be stored as regular NULL values. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`apply_pii_checks`</span>|Apply the rules to the Personal Identifiable Information checks (sensitive data), but only when the checks were run as profiling checks activated manually, or by activating a data quality check pattern that scans all columns for PII data. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`propose_custom_checks`</span>|Propose the default configuration for custom checks that use built-in data quality rules. The default value of this parameter is 'true'.|*boolean*|
|<span class="no-wrap-code">`fail_checks_at_percent_error_rows`</span>|The percentage value captured by a profiling check (for example 0.03% of errors or 99.97% of valid) that is used to propose a percentage rule that will treat the values as errors (i.e., max_percent = 0%, or min_percent = 100%).|*double*|
|<span class="no-wrap-code">`max_percent_error_rows_for_percent_checks`</span>|The default maximum percentage of invalid rows for which the rule engine should configure rule values, especially min_percent, min_count or max_percent.|*double*|


___

## CheckMiningProposalModel
Model that has a proposed configuration of checks on a table and its columns generated by a data quality check mining service.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`missing_current_statistics`</span>|A boolean flag to inform the caller that the rule mining engine failed to propose relevant data quality rules because there are no current statistics. The user should schedule a 'collect statistics' job to analyze the table.|*boolean*|
|<span class="no-wrap-code">`missing_current_profiling_check_results`</span>|A boolean flag to inform the caller that the rule mining engine failed to propose relevant data quality rules because there are no current results from profiling checks. The user should run profiling checks to analyze the table.|*boolean*|
|<span class="no-wrap-code">[`table_checks`](./common.md#checkcontainermodel)</span>|Proposed configuration of table-level data quality checks, such as volume, timeliness or schema.|*[CheckContainerModel](./common.md#checkcontainermodel)*|
|<span class="no-wrap-code">`column_checks`</span>|Dictionary of proposed data quality checks for each column.|*Dict[string, [CheckContainerModel](./common.md#checkcontainermodel)]*|
|<span class="no-wrap-code">[`run_checks_job`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order run checks for the table.|*[CheckSearchFilters](./common.md#checksearchfilters)*|


___

