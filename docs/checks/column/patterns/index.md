# column level patterns data quality checks

This is a list of patterns column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **patterns**


| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_not_matching_regex_found](./text-not-matching-regex-found.md#profile-text-not-matching-regex-found)|profiling|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|standard|
|[daily_text_not_matching_regex_found](./text-not-matching-regex-found.md#daily-text-not-matching-regex-found)|monitoring|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|standard|
|[monthly_text_not_matching_regex_found](./text-not-matching-regex-found.md#monthly-text-not-matching-regex-found)|monitoring|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|standard|
|[daily_partition_text_not_matching_regex_found](./text-not-matching-regex-found.md#daily-partition-text-not-matching-regex-found)|partitioned|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|standard|
|[monthly_partition_text_not_matching_regex_found](./text-not-matching-regex-found.md#monthly-partition-text-not-matching-regex-found)|partitioned|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_texts_matching_regex_percent](./texts-matching-regex-percent.md#profile-texts-matching-regex-percent)|profiling|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|standard|
|[daily_texts_matching_regex_percent](./texts-matching-regex-percent.md#daily-texts-matching-regex-percent)|monitoring|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|standard|
|[monthly_texts_matching_regex_percent](./texts-matching-regex-percent.md#monthly-texts-matching-regex-percent)|monitoring|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|standard|
|[daily_partition_texts_matching_regex_percent](./texts-matching-regex-percent.md#daily-partition-texts-matching-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|standard|
|[monthly_partition_texts_matching_regex_percent](./texts-matching-regex-percent.md#monthly-partition-texts-matching-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_invalid_email_format_found](./invalid-email-format-found.md#profile-invalid-email-format-found)|profiling|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|standard|
|[daily_invalid_email_format_found](./invalid-email-format-found.md#daily-invalid-email-format-found)|monitoring|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|standard|
|[monthly_invalid_email_format_found](./invalid-email-format-found.md#monthly-invalid-email-format-found)|monitoring|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|standard|
|[daily_partition_invalid_email_format_found](./invalid-email-format-found.md#daily-partition-invalid-email-format-found)|partitioned|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|standard|
|[monthly_partition_invalid_email_format_found](./invalid-email-format-found.md#monthly-partition-invalid-email-format-found)|partitioned|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_not_matching_date_pattern_found](./text-not-matching-date-pattern-found.md#profile-text-not-matching-date-pattern-found)|profiling|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|advanced|
|[daily_text_not_matching_date_pattern_found](./text-not-matching-date-pattern-found.md#daily-text-not-matching-date-pattern-found)|monitoring|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|advanced|
|[monthly_text_not_matching_date_pattern_found](./text-not-matching-date-pattern-found.md#monthly-text-not-matching-date-pattern-found)|monitoring|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|advanced|
|[daily_partition_text_not_matching_date_pattern_found](./text-not-matching-date-pattern-found.md#daily-partition-text-not-matching-date-pattern-found)|partitioned|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|advanced|
|[monthly_partition_text_not_matching_date_pattern_found](./text-not-matching-date-pattern-found.md#monthly-partition-text-not-matching-date-pattern-found)|partitioned|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_matching_date_pattern_percent](./text-matching-date-pattern-percent.md#profile-text-matching-date-pattern-percent)|profiling|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|advanced|
|[daily_text_matching_date_pattern_percent](./text-matching-date-pattern-percent.md#daily-text-matching-date-pattern-percent)|monitoring|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|advanced|
|[monthly_text_matching_date_pattern_percent](./text-matching-date-pattern-percent.md#monthly-text-matching-date-pattern-percent)|monitoring|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|advanced|
|[daily_partition_text_matching_date_pattern_percent](./text-matching-date-pattern-percent.md#daily-partition-text-matching-date-pattern-percent)|partitioned|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|advanced|
|[monthly_partition_text_matching_date_pattern_percent](./text-matching-date-pattern-percent.md#monthly-partition-text-matching-date-pattern-percent)|partitioned|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_matching_name_pattern_percent](./text-matching-name-pattern-percent.md#profile-text-matching-name-pattern-percent)|profiling|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|advanced|
|[daily_text_matching_name_pattern_percent](./text-matching-name-pattern-percent.md#daily-text-matching-name-pattern-percent)|monitoring|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|advanced|
|[monthly_text_matching_name_pattern_percent](./text-matching-name-pattern-percent.md#monthly-text-matching-name-pattern-percent)|monitoring|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|advanced|
|[daily_partition_text_matching_name_pattern_percent](./text-matching-name-pattern-percent.md#daily-partition-text-matching-name-pattern-percent)|partitioned|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|advanced|
|[monthly_partition_text_matching_name_pattern_percent](./text-matching-name-pattern-percent.md#monthly-partition-text-matching-name-pattern-percent)|partitioned|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_invalid_uuid_format_found](./invalid-uuid-format-found.md#profile-invalid-uuid-format-found)|profiling|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|advanced|
|[daily_invalid_uuid_format_found](./invalid-uuid-format-found.md#daily-invalid-uuid-format-found)|monitoring|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|advanced|
|[monthly_invalid_uuid_format_found](./invalid-uuid-format-found.md#monthly-invalid-uuid-format-found)|monitoring|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|advanced|
|[daily_partition_invalid_uuid_format_found](./invalid-uuid-format-found.md#daily-partition-invalid-uuid-format-found)|partitioned|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|advanced|
|[monthly_partition_invalid_uuid_format_found](./invalid-uuid-format-found.md#monthly-partition-invalid-uuid-format-found)|partitioned|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_valid_uuid_format_percent](./valid-uuid-format-percent.md#profile-valid-uuid-format-percent)|profiling|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|advanced|
|[daily_valid_uuid_format_percent](./valid-uuid-format-percent.md#daily-valid-uuid-format-percent)|monitoring|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|advanced|
|[monthly_valid_uuid_format_percent](./valid-uuid-format-percent.md#monthly-valid-uuid-format-percent)|monitoring|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|advanced|
|[daily_partition_valid_uuid_format_percent](./valid-uuid-format-percent.md#daily-partition-valid-uuid-format-percent)|partitioned|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|advanced|
|[monthly_partition_valid_uuid_format_percent](./valid-uuid-format-percent.md#monthly-partition-valid-uuid-format-percent)|partitioned|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_invalid_ip4_address_format_found](./invalid-ip4-address-format-found.md#profile-invalid-ip4-address-format-found)|profiling|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[daily_invalid_ip4_address_format_found](./invalid-ip4-address-format-found.md#daily-invalid-ip4-address-format-found)|monitoring|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[monthly_invalid_ip4_address_format_found](./invalid-ip4-address-format-found.md#monthly-invalid-ip4-address-format-found)|monitoring|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[daily_partition_invalid_ip4_address_format_found](./invalid-ip4-address-format-found.md#daily-partition-invalid-ip4-address-format-found)|partitioned|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[monthly_partition_invalid_ip4_address_format_found](./invalid-ip4-address-format-found.md#monthly-partition-invalid-ip4-address-format-found)|partitioned|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_invalid_ip6_address_format_found](./invalid-ip6-address-format-found.md#profile-invalid-ip6-address-format-found)|profiling|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[daily_invalid_ip6_address_format_found](./invalid-ip6-address-format-found.md#daily-invalid-ip6-address-format-found)|monitoring|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[monthly_invalid_ip6_address_format_found](./invalid-ip6-address-format-found.md#monthly-invalid-ip6-address-format-found)|monitoring|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[daily_partition_invalid_ip6_address_format_found](./invalid-ip6-address-format-found.md#daily-partition-invalid-ip6-address-format-found)|partitioned|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[monthly_partition_invalid_ip6_address_format_found](./invalid-ip6-address-format-found.md#monthly-partition-invalid-ip6-address-format-found)|partitioned|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|advanced|







