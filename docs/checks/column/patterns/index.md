# column level patterns data quality checks

This is a list of patterns column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level patterns checks
Validates if a text column matches predefined patterns (such as an email address) or a custom regular expression.

### [text not matching regex found](./text-not-matching-regex-found.md)
This check validates text values using a pattern defined as a regular expression.
 It counts the number of invalid values and raises a data quality issue when the number exceeds a threshold.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_not_matching_regex_found`</span>](./text-not-matching-regex-found.md#profile-text-not-matching-regex-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_text_not_matching_regex_found`</span>](./text-not-matching-regex-found.md#daily-text-not-matching-regex-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_text_not_matching_regex_found`</span>](./text-not-matching-regex-found.md#monthly-text-not-matching-regex-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_text_not_matching_regex_found`</span>](./text-not-matching-regex-found.md#daily-partition-text-not-matching-regex-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_text_not_matching_regex_found`</span>](./text-not-matching-regex-found.md#monthly-partition-text-not-matching-regex-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|:material-check-bold:|



### [texts matching regex percent](./texts-matching-regex-percent.md)
This check validates text values using a pattern defined as a regular expression.
 It measures the percentage of valid values and raises a data quality issue when the rate is below a threshold.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_texts_matching_regex_percent`</span>](./texts-matching-regex-percent.md#profile-texts-matching-regex-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_texts_matching_regex_percent`</span>](./texts-matching-regex-percent.md#daily-texts-matching-regex-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_texts_matching_regex_percent`</span>](./texts-matching-regex-percent.md#monthly-texts-matching-regex-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_texts_matching_regex_percent`</span>](./texts-matching-regex-percent.md#daily-partition-texts-matching-regex-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_texts_matching_regex_percent`</span>](./texts-matching-regex-percent.md#monthly-partition-texts-matching-regex-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|:material-check-bold:|



### [invalid email format found](./invalid-email-format-found.md)
This check detects invalid email addresses in text columns using a regular expression.
 It counts the number of invalid emails and raises a data quality issue when the number is above a threshold.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_invalid_email_format_found`</span>](./invalid-email-format-found.md#profile-invalid-email-format-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_invalid_email_format_found`</span>](./invalid-email-format-found.md#daily-invalid-email-format-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_invalid_email_format_found`</span>](./invalid-email-format-found.md#monthly-invalid-email-format-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_invalid_email_format_found`</span>](./invalid-email-format-found.md#daily-partition-invalid-email-format-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_invalid_email_format_found`</span>](./invalid-email-format-found.md#monthly-partition-invalid-email-format-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|:material-check-bold:|



### [text not matching date pattern found](./text-not-matching-date-pattern-found.md)
This check detects dates in the wrong format inside text columns using a regular expression.
 It counts the number of incorrectly formatted dates and raises a data quality issue when the number exceeds a threshold.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_not_matching_date_pattern_found`</span>](./text-not-matching-date-pattern-found.md#profile-text-not-matching-date-pattern-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`daily_text_not_matching_date_pattern_found`</span>](./text-not-matching-date-pattern-found.md#daily-text-not-matching-date-pattern-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`monthly_text_not_matching_date_pattern_found`</span>](./text-not-matching-date-pattern-found.md#monthly-text-not-matching-date-pattern-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`daily_partition_text_not_matching_date_pattern_found`</span>](./text-not-matching-date-pattern-found.md#daily-partition-text-not-matching-date-pattern-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`monthly_partition_text_not_matching_date_pattern_found`</span>](./text-not-matching-date-pattern-found.md#monthly-partition-text-not-matching-date-pattern-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.| |



### [text matching date pattern percent](./text-matching-date-pattern-percent.md)
This check validates the date format of dates stored in text columns.
 It measures the percentage of correctly formatted dates and raises a data quality issue when the rate is below a threshold.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_matching_date_pattern_percent`</span>](./text-matching-date-pattern-percent.md#profile-text-matching-date-pattern-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_text_matching_date_pattern_percent`</span>](./text-matching-date-pattern-percent.md#daily-text-matching-date-pattern-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`monthly_text_matching_date_pattern_percent`</span>](./text-matching-date-pattern-percent.md#monthly-text-matching-date-pattern-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_partition_text_matching_date_pattern_percent`</span>](./text-matching-date-pattern-percent.md#daily-partition-text-matching-date-pattern-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`monthly_partition_text_matching_date_pattern_percent`</span>](./text-matching-date-pattern-percent.md#monthly-partition-text-matching-date-pattern-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.| |



### [text matching name pattern percent](./text-matching-name-pattern-percent.md)
This check verifies if values stored in a text column contain only letters and are usable as literal identifiers.
 It measures the percentage of valid literal identifiers and raises a data quality issue when the rate is below a threshold.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_matching_name_pattern_percent`</span>](./text-matching-name-pattern-percent.md#profile-text-matching-name-pattern-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_text_matching_name_pattern_percent`</span>](./text-matching-name-pattern-percent.md#daily-text-matching-name-pattern-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`monthly_text_matching_name_pattern_percent`</span>](./text-matching-name-pattern-percent.md#monthly-text-matching-name-pattern-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_partition_text_matching_name_pattern_percent`</span>](./text-matching-name-pattern-percent.md#daily-partition-text-matching-name-pattern-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`monthly_partition_text_matching_name_pattern_percent`</span>](./text-matching-name-pattern-percent.md#monthly-partition-text-matching-name-pattern-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.| |



### [invalid uuid format found](./invalid-uuid-format-found.md)
This check detects invalid UUID identifiers in text columns using a regular expression.
 It counts the number of invalid UUIDs and raises a data quality issue when the number is above a threshold.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_invalid_uuid_format_found`</span>](./invalid-uuid-format-found.md#profile-invalid-uuid-format-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`daily_invalid_uuid_format_found`</span>](./invalid-uuid-format-found.md#daily-invalid-uuid-format-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`monthly_invalid_uuid_format_found`</span>](./invalid-uuid-format-found.md#monthly-invalid-uuid-format-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`daily_partition_invalid_uuid_format_found`</span>](./invalid-uuid-format-found.md#daily-partition-invalid-uuid-format-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`monthly_partition_invalid_uuid_format_found`</span>](./invalid-uuid-format-found.md#monthly-partition-invalid-uuid-format-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.| |



### [valid uuid format percent](./valid-uuid-format-percent.md)
This check validates the format of UUID values in text columns.
 It measures the percentage of valid UUIDs and raises a data quality issue when the rate is below a threshold.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_valid_uuid_format_percent`</span>](./valid-uuid-format-percent.md#profile-valid-uuid-format-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_valid_uuid_format_percent`</span>](./valid-uuid-format-percent.md#daily-valid-uuid-format-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`monthly_valid_uuid_format_percent`</span>](./valid-uuid-format-percent.md#monthly-valid-uuid-format-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_partition_valid_uuid_format_percent`</span>](./valid-uuid-format-percent.md#daily-partition-valid-uuid-format-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`monthly_partition_valid_uuid_format_percent`</span>](./valid-uuid-format-percent.md#monthly-partition-valid-uuid-format-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.| |



### [invalid ip4 address format found](./invalid-ip4-address-format-found.md)
This check detects invalid IP4 internet addresses in text columns using a regular expression.
 It counts the number of invalid addresses and raises a data quality issue when the number is above a threshold.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_invalid_ip4_address_format_found`</span>](./invalid-ip4-address-format-found.md#profile-invalid-ip4-address-format-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`daily_invalid_ip4_address_format_found`</span>](./invalid-ip4-address-format-found.md#daily-invalid-ip4-address-format-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`monthly_invalid_ip4_address_format_found`</span>](./invalid-ip4-address-format-found.md#monthly-invalid-ip4-address-format-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`daily_partition_invalid_ip4_address_format_found`</span>](./invalid-ip4-address-format-found.md#daily-partition-invalid-ip4-address-format-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`monthly_partition_invalid_ip4_address_format_found`</span>](./invalid-ip4-address-format-found.md#monthly-partition-invalid-ip4-address-format-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.| |



### [invalid ip6 address format found](./invalid-ip6-address-format-found.md)
This check detects invalid IP6 internet addresses in text columns using a regular expression.
 It counts the number of invalid addresses and raises a data quality issue when the number is above a threshold.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_invalid_ip6_address_format_found`</span>](./invalid-ip6-address-format-found.md#profile-invalid-ip6-address-format-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`daily_invalid_ip6_address_format_found`</span>](./invalid-ip6-address-format-found.md#daily-invalid-ip6-address-format-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`monthly_invalid_ip6_address_format_found`</span>](./invalid-ip6-address-format-found.md#monthly-invalid-ip6-address-format-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`daily_partition_invalid_ip6_address_format_found`</span>](./invalid-ip6-address-format-found.md#daily-partition-invalid-ip6-address-format-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`monthly_partition_invalid_ip6_address_format_found`</span>](./invalid-ip6-address-format-found.md#monthly-partition-invalid-ip6-address-format-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.| |







