---
title: column level conversions data quality checks
---
# column level conversions data quality checks

This is a list of conversions column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level conversions checks
Validates that the values in a text column can be parsed and converted to other data types.

### [text parsable to boolean percent](./text-parsable-to-boolean-percent.md)
Verifies that values in a text column are convertible to a boolean value.
 Texts are convertible to a boolean value when they are one of the well-known boolean placeholders: &#x27;0&#x27;, &#x27;1&#x27;, &#x27;true&#x27;, &#x27;false&#x27;, &#x27;yes&#x27;, &#x27;no&#x27;, &#x27;y&#x27;, &#x27;n&#x27;.
 This check measures the percentage of valid values and raises a data quality issue when the percentage of valid values is below an accepted rate.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_parsable_to_boolean_percent`</span>](./text-parsable-to-boolean-percent.md#profile-text-parsable-to-boolean-percent)|Minimum percentage of rows containing a text parsable to a boolean value|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_text_parsable_to_boolean_percent`</span>](./text-parsable-to-boolean-percent.md#daily-text-parsable-to-boolean-percent)|Minimum percentage of rows containing a text parsable to a boolean value|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_text_parsable_to_boolean_percent`</span>](./text-parsable-to-boolean-percent.md#monthly-text-parsable-to-boolean-percent)|Minimum percentage of rows containing a text parsable to a boolean value|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_text_parsable_to_boolean_percent`</span>](./text-parsable-to-boolean-percent.md#daily-partition-text-parsable-to-boolean-percent)|Minimum percentage of rows containing a text parsable to a boolean value|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_text_parsable_to_boolean_percent`</span>](./text-parsable-to-boolean-percent.md#monthly-partition-text-parsable-to-boolean-percent)|Minimum percentage of rows containing a text parsable to a boolean value|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|:material-check-bold:|



### [text parsable to integer percent](./text-parsable-to-integer-percent.md)
Verifies that values in a text column can be parsed and converted to an integer type.
 This check measures the percentage of valid values and raises a data quality issue when the percentage of valid values is below an accepted rate.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_parsable_to_integer_percent`</span>](./text-parsable-to-integer-percent.md#profile-text-parsable-to-integer-percent)|Minimum percentage of rows containing a text parsable to integer|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage|:material-check-bold:|
|[<span class="no-wrap-code">`daily_text_parsable_to_integer_percent`</span>](./text-parsable-to-integer-percent.md#daily-text-parsable-to-integer-percent)|Minimum percentage of rows containing a text parsable to integer|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_text_parsable_to_integer_percent`</span>](./text-parsable-to-integer-percent.md#monthly-text-parsable-to-integer-percent)|Minimum percentage of rows containing a text parsable to integer|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_text_parsable_to_integer_percent`</span>](./text-parsable-to-integer-percent.md#daily-partition-text-parsable-to-integer-percent)|Minimum percentage of rows containing a text parsable to integer|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_text_parsable_to_integer_percent`</span>](./text-parsable-to-integer-percent.md#monthly-partition-text-parsable-to-integer-percent)|Minimum percentage of rows containing a text parsable to integer|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|:material-check-bold:|



### [text parsable to float percent](./text-parsable-to-float-percent.md)
Verifies that values in a text column can be parsed and converted to a float (or numeric) type.
 This check measures the percentage of valid values and raises a data quality issue when the percentage of valid values is below an accepted rate.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_parsable_to_float_percent`</span>](./text-parsable-to-float-percent.md#profile-text-parsable-to-float-percent)|Minimum percentage of rows containing a text parsable to float|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage|:material-check-bold:|
|[<span class="no-wrap-code">`daily_text_parsable_to_float_percent`</span>](./text-parsable-to-float-percent.md#daily-text-parsable-to-float-percent)|Minimum percentage of rows containing a text parsable to float|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_text_parsable_to_float_percent`</span>](./text-parsable-to-float-percent.md#monthly-text-parsable-to-float-percent)|Minimum percentage of rows containing a text parsable to float|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_text_parsable_to_float_percent`</span>](./text-parsable-to-float-percent.md#daily-partition-text-parsable-to-float-percent)|Minimum percentage of rows containing a text parsable to float|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_text_parsable_to_float_percent`</span>](./text-parsable-to-float-percent.md#monthly-partition-text-parsable-to-float-percent)|Minimum percentage of rows containing a text parsable to float|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|:material-check-bold:|



### [text parsable to date percent](./text-parsable-to-date-percent.md)
Verifies that values in a text column can be parsed and converted to a date type.
 This check measures the percentage of valid values and raises a data quality issue when the percentage of valid values is below an accepted rate.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_parsable_to_date_percent`</span>](./text-parsable-to-date-percent.md#profile-text-parsable-to-date-percent)|Minimum percentage of rows containing a text parsable to date|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression|:material-check-bold:|
|[<span class="no-wrap-code">`daily_text_parsable_to_date_percent`</span>](./text-parsable-to-date-percent.md#daily-text-parsable-to-date-percent)|Minimum percentage of rows containing a text parsable to date|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_text_parsable_to_date_percent`</span>](./text-parsable-to-date-percent.md#monthly-text-parsable-to-date-percent)|Minimum percentage of rows containing a text parsable to date|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_text_parsable_to_date_percent`</span>](./text-parsable-to-date-percent.md#daily-partition-text-parsable-to-date-percent)|Minimum percentage of rows containing a text parsable to date|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_text_parsable_to_date_percent`</span>](./text-parsable-to-date-percent.md#monthly-partition-text-parsable-to-date-percent)|Minimum percentage of rows containing a text parsable to date|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|:material-check-bold:|







