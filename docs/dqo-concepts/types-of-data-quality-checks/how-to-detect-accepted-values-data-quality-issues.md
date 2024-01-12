# Detecting data quality issues with accepted values
The type of accepted values is documented here

## Detecting accepted values at a table level
How to detect accepted values on tables

## List of accepted values checks at a table level

## Detecting accepted values at a column level
How to detect accepted values on column

## List of accepted values checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[text_found_in_set_percent](../../checks/column/accepted_values/text-found-in-set-percent.md)|Validity|Column-level check that calculates the percentage of rows for which the tested text column contains a value from the set of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 The check raises a data quality issue when the percentage of rows with a not null column value that is not expected (not one of the values in the expected_values set)
 is below an expected threshold, for example 99% of rows should have values from the defined domain.
 This data quality check is useful for checking text columns that have a low number of unique values and all the values should be from a set of expected values.
 For example, testing a country, state, currency, gender, type, department columns whose expected values are known.|standard|
|[number_found_in_set_percent](../../checks/column/accepted_values/number-found-in-set-percent.md)|Validity|Column level check that calculates the percentage of rows for which the tested numeric column contains a value from the set of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 The check raises a data quality issue when the percentage of rows with a not null column value that is not expected (not one of the values in the expected_values set)
 is below an expected threshold, for example 99% of rows should have values from the defined domain.
 This data quality check is useful for checking numeric columns that store numeric codes (such as status codes) that the only values found in the column are from a set of expected values.|standard|
|[expected_text_values_in_use_count](../../checks/column/accepted_values/expected-text-values-in-use-count.md)|Reasonableness|Column-level check that counts unique values in a text column and counts how many values out of a list of expected string values were found in the column.
 The check raises a data quality issue when the threshold of maximum number of missing values was exceeded (too many expected values were not found in the column).
 This check is useful for analysing columns with a low number of unique values, such as status codes, to detect that all status codes are in use in any row.|advanced|
|[expected_texts_in_top_values_count](../../checks/column/accepted_values/expected-texts-in-top-values-count.md)|Reasonableness|Column-level check that counts how many expected text values are among the TOP most popular values in the column.
 The check will first count the number of occurrences of each column&#x27;s value and will pick the TOP X most popular values (configurable by the &#x27;top&#x27; parameter).
 Then, it will compare the list of most popular values to the given list of expected values that should be most popular.
 This check will verify how many supposed most popular values (provided in the &#x27;expected_values&#x27; list) were not found in the top X most popular values in the column.
 This check is useful for analyzing string columns that have several very popular values, these could be the country codes of the countries with the most number of customers.|advanced|
|[expected_numbers_in_use_count](../../checks/column/accepted_values/expected-numbers-in-use-count.md)|Reasonableness|Column-level check that counts unique values in a numeric column and counts how many values out of a list of expected numeric values were found in the column.
 The check raises a data quality issue when the threshold of maximum number of missing values was exceeded (too many expected values were not found in the column).
 This check is useful for analysing columns with a low number of unique values, such as status codes, to detect that all status codes are in use in any row.|advanced|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
