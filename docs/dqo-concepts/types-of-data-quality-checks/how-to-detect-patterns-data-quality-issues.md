# Detecting data quality issues with patterns
The type of patterns is documented here

## Detecting patterns at a table level
How to detect patterns on tables

## List of patterns checks at a table level

## Detecting patterns at a column level
How to detect patterns on column

## List of patterns checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[text_not_matching_regex_found](../../checks/column/patterns/text-not-matching-regex-found.md)|Validity|Column check that calculates quantity of values that does not match the custom regex in a column.|standard|
|[texts_matching_regex_percent](../../checks/column/patterns/texts-matching-regex-percent.md)|Validity|Column check that calculates the percentage of values that matches the custom regex in a column.|standard|
|[invalid_email_format_found](../../checks/column/patterns/invalid-email-format-found.md)|Validity|Column-level check that ensures that there are no more than a maximum number of invalid email in a monitored column.|standard|
|[text_not_matching_date_pattern_found](../../checks/column/patterns/text-not-matching-date-pattern-found.md)|Validity|Column check that calculates quantity of values that does not match the date regex in a column.|advanced|
|[text_matching_date_pattern_percent](../../checks/column/patterns/text-matching-date-pattern-percent.md)|Validity|Column check that calculates the percentage of values that match the date regex in a column.|advanced|
|[text_matching_name_pattern_percent](../../checks/column/patterns/text-matching-name-pattern-percent.md)|Validity|Column check that calculates the percentage of values that match the name regex in a column.|advanced|
|[invalid_uuid_format_found](../../checks/column/patterns/invalid-uuid-format-found.md)|Validity|Column-level check that ensures that there are no more than a maximum number of invalid UUID in a monitored column.|advanced|
|[valid_uuid_format_percent](../../checks/column/patterns/valid-uuid-format-percent.md)|Validity|Column-level check that ensures that the percentage of valid UUID strings in the monitored column does not fall below set thresholds.|advanced|
|[invalid_ip4_address_format_found](../../checks/column/patterns/invalid-ip4-address-format-found.md)|Validity|Column-level check that ensures that there are no more than a maximum number of invalid IP4 address in a monitored column.|advanced|
|[invalid_ip6_address_format_found](../../checks/column/patterns/invalid-ip6-address-format-found.md)|Validity|Column-level check that ensures that there are no more than a maximum number of invalid IP6 address in a monitored column.|advanced|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
