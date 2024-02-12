# Detecting data quality issues with text
Read this guide to learn what types of data quality checks are supported in DQOps to detect issues related to text.
The data quality checks are configured in the `text` category in DQOps.

## Text category
Data quality checks that are detecting issues related to text are listed below.

## Detecting text issues
How to detect text data quality issues.

## Use cases
| **Name of the example**                                                                                        | **Description**                                                                                                                                                                                                                         |
|:---------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [A text not exceeding a maximum length](../examples/data-reasonability/text-not-exceeding-a-maximum-length.md) | This example shows how to check that the length of the text does not exceed the maximum value using [text_max_length](../checks/column/text/text-length-above-max-length-percent.md) check.                                             |
| [Percentage of valid currency codes](../examples/data-validity/percentage-of-valid-currency-codes.md)          | This example shows how to detect that the percentage of valid currency codes in a column does not fall below a set threshold using [text_valid_currency_code_percent](../checks/column/text/text-valid-currency-code-percent.md) check. |

## List of text checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*text_max_length*](../checks/column/text/text-max-length.md)|Reasonableness|A column-level check that ensures that the length of text values in a column does not exceed the maximum accepted length.|:material-check-bold:|
|[*text_min_length*](../checks/column/text/text-min-length.md)|Reasonableness|A column-level check that ensures that the length of text in a column does not fall below the minimum accepted length.|:material-check-bold:|
|[*text_mean_length*](../checks/column/text/text-mean-length.md)|Reasonableness|A column-level check that ensures that the length of text values in a column does not exceed the mean accepted length.| |
|[*text_length_below_min_length*](../checks/column/text/text-length-below-min-length.md)|Reasonableness|A column-level check that ensures that the number of text values in the monitored column with a length below the length defined by the user as a parameter does not exceed set thresholds.| |
|[*text_length_below_min_length_percent*](../checks/column/text/text-length-below-min-length-percent.md)|Reasonableness|A column-level check that ensures that the percentage of text values in the monitored column with a length below the length defined by the user as a parameter does not fall below set thresholds.| |
|[*text_length_above_max_length*](../checks/column/text/text-length-above-max-length.md)|Reasonableness|A column-level check that ensures that the number of text values in the monitored column with a length above the length defined by the user as a parameter does not exceed set thresholds.| |
|[*text_length_above_max_length_percent*](../checks/column/text/text-length-above-max-length-percent.md)|Reasonableness|A column-level check that ensures that the percentage of text values in the monitored column with a length above the length defined by the user as a parameter does not fall below set thresholds.| |
|[*text_length_in_range_percent*](../checks/column/text/text-length-in-range-percent.md)|Reasonableness|Column check that calculates the percentage of text values with a length below the indicated by the user length in a monitored column.| |
|[*text_surrounded_by_whitespace*](../checks/column/text/text-surrounded-by-whitespace.md)|Validity|A column-level check that ensures that there are no more than a maximum number of text values that are surrounded by whitespace in a monitored column.| |
|[*text_surrounded_by_whitespace_percent*](../checks/column/text/text-surrounded-by-whitespace-percent.md)|Validity|A column-level check that ensures that there are no more than a maximum percentage of text values that are surrounded by whitespace in a monitored column.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/text](../checks/column/text/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
