# Detecting data quality issues with accepted values
Read this guide to learn what types of data quality checks are supported in DQOps to detect issues related to accepted values.
The data quality checks are configured in the `accepted_values` category in DQOps.

## Accepted values category
Data quality checks that are detecting issues related to accepted values are listed below.

![Column profiling result with most popular values](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/column-profiling-result-top-column-values-min.png){ loading=lazy }

![Enabling text in set percent data quality check](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/daily-text-found-in-set-percent-data-quality-check-editor-min.png){ loading=lazy }

![Adding a list of expected values in a data quality check](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/expected-values-list-popup-data-quality-check-min.png){ loading=lazy }



``` { .yaml linenums="1" hl_lines="12-15" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
      monitoring_checks:
        daily:
          accepted_values:
            daily_text_found_in_set_percent:
              parameters:
                expected_values:
                - TRAVIS
                - WILLIAMSON
                - HAYS
                - BASTROP
              error:
                min_percent: 100.0
```

![Adding data dictionary CSV file in DQOps](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/add-data-dictionary-editor-min.png){ loading=lazy }

![Data dictionary list screen for data quality checks](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/data-dictionary-list-screen-min.png){ loading=lazy }

![Referencing data dictionary in a text found in set percent data quality check](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/daily-text-found-in-set-percent-check-reference-dictionary-min.png){ loading=lazy }

``` { .yaml linenums="1" hl_lines="12-15" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
      monitoring_checks:
        daily:
          accepted_values:
            daily_text_found_in_set_percent:
              parameters:
                expected_values:
                - "${dictionary://cities.csv}"
              error:
                min_percent: 100.0
```


![Asserting that all expected text values are present in a column](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/all-expected-column-values-are-in-use-data-quality-check-min.png){ loading=lazy }

``` { .yaml linenums="1" hl_lines="12-15" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
      monitoring_checks:
        daily:
          accepted_values:
            daily_expected_text_values_in_use_count:
              parameters:
                expected_values:
                - TRAVIS
                - WILLIAMSON
                - HAYS
                - BASTROP
              error:
                max_missing: 0
```

![Detecting expected values that are missing in a column](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/text-values-found-vs-text-values-in-use-min.png){ loading=lazy }

![Top values in a column to assert in a data quality check](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/column-profiling-result-top-two-column-values-min.png){ width="619px"; loading=lazy }


![Asserting that expected values are in the top of most popular values in a column](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/text-values-in-top-most-popular-min.png){ loading=lazy }

``` { .yaml linenums="1" hl_lines="12-15" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
      monitoring_checks:
        daily:
          accepted_values:
            daily_expected_texts_in_top_values_count:
              parameters:
                expected_values:
                - TRAVIS
                - WILLIAMSON
                top: 3
              error:
                max_missing: 0
```

## Detecting accepted values issues
How to detect accepted values data quality issues.

## Use cases
| **Name of the example**                                                                                                      | **Description**                                                                                                                                                                                                                  |
|:-----------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Percentage of rows with a text found in a set](../examples/data-consistency/percentage-of-rows-with-a-text-found-in-set.md) | This example shows how to verify that the percentage of texts from a set in a column does not fall below a set threshold using [text_found_in_set_percent](../checks/column/accepted_values/text-found-in-set-percent.md) check. |


## List of accepted values checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*text_found_in_set_percent*](../checks/column/accepted_values/text-found-in-set-percent.md)|Validity|A column-level check that calculates the percentage of rows for which the tested text column contains a value from a set of expected values. Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value). The check raises a data quality issue when the percentage of rows with a not null column value that is not expected (not one of the values in the expected_values set) is below the expected threshold. For example, 99% of rows should have values from the defined domain. This data quality check is useful for checking text columns that have a small number of unique values, and all the values should come from a set of expected values. For example, testing country, state, currency, gender, type, and department columns whose expected values are known.|:material-check-bold:|
|[*number_found_in_set_percent*](../checks/column/accepted_values/number-found-in-set-percent.md)|Validity|A column-level check that calculates the percentage of rows for which the tested numeric column contains a value from a set of expected values. Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value). The check raises a data quality issue when the percentage of rows with a not null column value that is not expected (not one of the values in the expected_values set) is below the expected threshold. For example, 99% of rows should have values from the defined domain. This data quality check is useful for checking numeric columns that store numeric codes (such as status codes) to see if the only values found in the column are from the set of expected values.|:material-check-bold:|
|[*expected_text_values_in_use_count*](../checks/column/accepted_values/expected-text-values-in-use-count.md)|Reasonableness|A column-level check that counts unique values in a text column and counts how many values out of a list of expected string values were found in the column. The check raises a data quality issue when the threshold for the maximum number of missing has been exceeded (too many expected values were not found in the column). This check is useful for analysing columns with a low number of unique values, such as status codes, to detect whether all status codes are used in any row.| |
|[*expected_texts_in_top_values_count*](../checks/column/accepted_values/expected-texts-in-top-values-count.md)|Reasonableness|A column-level check that counts how many expected text values are among the TOP most popular values in the column. The check will first count the number of occurrences of each column&#x27;s value and will pick the TOP X most popular values (configurable by the &#x27;top&#x27; parameter). Then, it will compare the list of most popular values to the given list of expected values that should be most popular. This check will verify how many supposed most popular values (provided in the &#x27;expected_values&#x27; list) were not found in the top X most popular values in the column. This check is useful for analyzing string columns that have several very popular values, these could be the country codes of the countries with the most number of customers.| |
|[*expected_numbers_in_use_count*](../checks/column/accepted_values/expected-numbers-in-use-count.md)|Reasonableness|A column-level check that counts unique values in a numeric column and counts how many values out of a list of expected numeric values were found in the column. The check raises a data quality issue when the threshold for the maximum number of missing has been exceeded (too many expected values were not found in the column). This check is useful for analysing columns with a low number of unique values, such as status codes, to detect whether all status codes are used in any row.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/accepted_values](../checks/column/accepted_values/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
