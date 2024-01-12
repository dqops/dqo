# Detecting data quality issues with text
The type of text is documented here

## Detecting text at a table level
How to detect text on tables

## List of text checks at a table level

## Detecting text at a column level
How to detect text on column

## List of text checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[text_max_length](../../checks/column/text/text-max-length.md)|Reasonableness|Column-level check that ensures that the length of texts in a column does not exceed the maximum accepted length.|standard|
|[text_min_length](../../checks/column/text/text-min-length.md)|Reasonableness|Column-level check that ensures that the length of text in a column does not fall below the minimum accepted length.|standard|
|[text_mean_length](../../checks/column/text/text-mean-length.md)|Reasonableness|Column-level check that ensures that the length of texts in a column does not exceed the mean accepted length.|advanced|
|[text_length_below_min_length](../../checks/column/text/text-length-below-min-length.md)|Reasonableness|Column-level check that ensures that the number of texts in the monitored column with a length below the length defined by the user as a parameter does not exceed set thresholds.|advanced|
|[text_length_below_min_length_percent](../../checks/column/text/text-length-below-min-length-percent.md)|Reasonableness|Column-level check that ensures that the percentage of texts in the monitored column with a length below the length defined by the user as a parameter does not fall below set thresholds.|advanced|
|[text_length_above_max_length](../../checks/column/text/text-length-above-max-length.md)|Reasonableness|Column-level check that ensures that the number of texts in the monitored column with a length above the length defined by the user as a parameter does not exceed set thresholds.|advanced|
|[text_length_above_max_length_percent](../../checks/column/text/text-length-above-max-length-percent.md)|Reasonableness|Column-level check that ensures that the percentage of texts in the monitored column with a length above the length defined by the user as a parameter does not fall below set thresholds.|advanced|
|[text_length_in_range_percent](../../checks/column/text/text-length-in-range-percent.md)|Reasonableness|Column check that calculates the percentage of texts with a length below the indicated by the user length in a column.|advanced|
|[text_parsable_to_boolean_percent](../../checks/column/text/text-parsable-to-boolean-percent.md)|Validity|Column-level check that ensures that the percentage of boolean placeholder texts (&#x27;0&#x27;, &#x27;1&#x27;, &#x27;true&#x27;, &#x27;false&#x27;, &#x27;yes&#x27;, &#x27;no&#x27;, &#x27;y&#x27;, &#x27;n&#x27;) in the monitored column does not fall below the minimum percentage.|advanced|
|[text_parsable_to_integer_percent](../../checks/column/text/text-parsable-to-integer-percent.md)|Validity|Column-level check that ensures that the percentage of texts that are parsable to integer in the monitored column does not fall below set thresholds.|advanced|
|[text_parsable_to_float_percent](../../checks/column/text/text-parsable-to-float-percent.md)|Validity|Column level check that ensures that the percentage of strings that are parsable to float in the monitored column does not fall below set thresholds.|advanced|
|[text_parsable_to_date_percent](../../checks/column/text/text-parsable-to-date-percent.md)|Validity|Column-level check that ensures that there is at least a minimum percentage of valid text values that are valid date strings (are parsable to a DATE type) in a monitored column.|advanced|
|[text_surrounded_by_whitespace](../../checks/column/text/text-surrounded-by-whitespace.md)|Validity|Column-level check that ensures that there are no more than a maximum number of texts that are surrounded by whitespace in a monitored column.|advanced|
|[text_surrounded_by_whitespace_percent](../../checks/column/text/text-surrounded-by-whitespace-percent.md)|Validity|Column-level check that ensures that there are no more than a maximum percent of texts that are surrounded by whitespace in a monitored column.|advanced|
|[text_valid_country_code_percent](../../checks/column/text/text-valid-country-code-percent.md)|Validity|Column-level check that ensures that the percentage of texts that are valid country codes in the monitored column does not fall below set thresholds.|advanced|
|[text_valid_currency_code_percent](../../checks/column/text/text-valid-currency-code-percent.md)|Validity|Column-level check that ensures that the percentage of text values that are valid currency codes in the monitored column does not fall below set thresholds.|advanced|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
