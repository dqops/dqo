# Detecting data quality issues with patterns
Read this guide to learn what types of data quality checks are supported in DQOps to detect issues related to patterns.
The data quality checks are configured in the `patterns` category in DQOps.

## Patterns category
Data quality checks that are detecting issues related to patterns are listed below.

## Detecting patterns issues
How to detect patterns data quality issues.

## Use cases
| **Name of the example**                                                                                                | **Description**                                                                                                                                                                                                                                                |
|:-----------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Percentage of texts matching a date pattern](../../examples/data-validity/percentage-of-texts-matching-date-regex.md) | This example shows how to detect that the percentage of texts matching the date format regex in a column does not exceed a set threshold using [text_matching_date_pattern_percent](../../checks/column/patterns/text-matching-date-pattern-percent.md) check. |
| [Detect invalid emails](../../examples/data-validity/detect-invalid-emails.md)                                         | This example shows how to detect that the number of invalid emails in a column does not exceed the maximum accepted count using [invalid_email_format_found](../../checks/column/patterns/invalid-email-format-found.md) check.                                |
| [Percentage of valid UUID](../../examples/data-validity/percentage-of-valid-uuid.md)                                   | This example shows how to detect that th percentage of valid UUID values in a column does not fall below a set threshold using [valid_uuid_format_percent](../../checks/column/patterns/valid-uuid-format-percent.md) check.                                   |
| [Detect invalid IP4 address](../../examples/data-validity/detect-invalid-ip4-addresses.md)                             | This example shows how to detect that the number of invalid IP4 address in a column does not exceed a set threshold using [invalid_ip4_address_format_found](../../checks/column/patterns/invalid-ip4-address-format-found.md) check.                          |

## List of patterns checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*text_not_matching_regex_found*](../../checks/column/patterns/text-not-matching-regex-found.md)|Validity|A column-level that calculates the quantity of values that do not match the custom regex in a monitored column.|:material-check-bold:|
|[*texts_matching_regex_percent*](../../checks/column/patterns/texts-matching-regex-percent.md)|Validity|A column-level that calculates the percentage of values that match the custom regex in a monitored column.|:material-check-bold:|
|[*invalid_email_format_found*](../../checks/column/patterns/invalid-email-format-found.md)|Validity|A column-level check that ensures that there are no more than a maximum number of invalid emails in a monitored column.|:material-check-bold:|
|[*text_not_matching_date_pattern_found*](../../checks/column/patterns/text-not-matching-date-pattern-found.md)|Validity|A column-level that calculates the quantity of values that do not match the date regex in a monitored column.| |
|[*text_matching_date_pattern_percent*](../../checks/column/patterns/text-matching-date-pattern-percent.md)|Validity|A column-level check that calculates the percentage of values that match the date regex in a monitored column.| |
|[*text_matching_name_pattern_percent*](../../checks/column/patterns/text-matching-name-pattern-percent.md)|Validity|A column-level that calculates the percentage of values that match the name regex in a monitored column.| |
|[*invalid_uuid_format_found*](../../checks/column/patterns/invalid-uuid-format-found.md)|Validity|A column-level check that ensures that there are no more than a maximum number of invalid UUID in a monitored column.| |
|[*valid_uuid_format_percent*](../../checks/column/patterns/valid-uuid-format-percent.md)|Validity|A column-level check that ensures that the percentage of valid UUID strings in the monitored column does not fall below set thresholds.| |
|[*invalid_ip4_address_format_found*](../../checks/column/patterns/invalid-ip4-address-format-found.md)|Validity|A column-level check that ensures that there are no more than a maximum number of invalid IP4 address in a monitored column.| |
|[*invalid_ip6_address_format_found*](../../checks/column/patterns/invalid-ip6-address-format-found.md)|Validity|A column-level check that ensures that there are no more than a maximum number of invalid IP6 address in a monitored column.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/patterns](../../checks/column/patterns/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../data-quality-dimensions.md) used by DQOps
