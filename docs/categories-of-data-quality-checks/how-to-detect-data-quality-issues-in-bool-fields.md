---
title: How to measure the percentage of true and false values
---
# How to measure the percentage of true and false values
Read this guide to learn how to measure the percentage of true and false boolean values and how to set up data quality checks that assert the valid range.

The data quality checks for bool columns are configured in the `bool` category in DQOps.

## Boolean statistics
Boolean columns are easy to analyze for data quality issues. They can have only three values: *true*, *false*, and *null*. 
DQOps has data quality checks for measuring the percentage of *true* and *false* values.
The measure (the percentage) is verified with a range data quality check.

The profile of a sample *bool* column is shown below. The tested column has ~201M false values and ~5M true values.
The percentage of true values is 2.43%.

![Bool column profiling statistics in DQOps](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/bool-column-profile-min.png){ loading=lazy }

## Bool data quality checks
DQOps has two similar data quality checks for analyzing bool values.

- [*true_percent*](../checks/column/bool/true-percent.md) validates the percentage of `true` values

- [*false_percent*](../checks/column/bool/false-percent.md) validates the percentage of `false` values

Both bool checks accept two optional rule parameters, which set the valid percentage range of true or false values.

- `min_percent` is the minimum accepted percentage, the value is between 0.0 and 100.0 (inclusive)

- `max_percent` is the maximum accepted percentage, the value is between 0.0 and 100.0 (inclusive)

### Enable bool checks in UI
[DQOps check editor](../dqo-concepts/dqops-user-interface-overview.md#check-editor) shows the bool checks in the `Bool` category.

![Configure data quality check to measure the percentage of true values in a bool column](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/boolean-true-percent-check-editor-warning-min.png){ loading=lazy }

### Enable bool checks in YAML
Bool checks are configured in the `bool` category node.

``` { .yaml linenums="1" hl_lines="13-16" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    is_coinbase:
      type_snapshot:
        column_type: BOOL
        nullable: true
      monitoring_checks:
        daily:
          bool:
            daily_true_percent:
              warning:
                min_percent: 1.0
                max_percent: 3.0
```


## Analyze boolean values in other column types
DQOps can also analyze bool values in non-bool columns. Bool values in landing zone tables are often stored in text columns. 
The text values for *true* and *false* can take any form. DQOps supports [calculated columns](../dqo-concepts/configuring-table-metadata.md#calculated-columns). 
A computed column is defined as an SQL expression that converts a raw value to an expected data type.

The following YAML file shows a calculated column tested with a bool data quality check.

``` { .yaml linenums="1" hl_lines="7 11-14" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    approved_boolean:
      sql_expression: "CASE WHEN {alias}.version = 'approved' THEN TRUE WHEN {alias}.version = 'rejected' THEN FALSE ELSE NULL END" 
      monitoring_checks:
        daily:
          bool:
            daily_true_percent:
              warning:
                min_percent: 1.0
                max_percent: 3.0
```


## Use case
| **Name of the example**                                                                            | **Description**                                                                                                                                                         |
|:---------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Percentage of false boolean values](../examples/data-reasonability/percentage-of-false-values.md) | This example shows how to detect that the percentage of false values remains above a set threshold using [false_percent](../checks/column/bool/false-percent.md) check. |

## List of bool checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*true_percent*](../checks/column/bool/true-percent.md)|[Reasonableness](../dqo-concepts/data-quality-dimensions.md#data-reasonableness)|This check measures the percentage of **true** values in a boolean column. It raises a data quality issue when the measured percentage is outside the accepted range.|:material-check-bold:|
|[*false_percent*](../checks/column/bool/false-percent.md)|[Reasonableness](../dqo-concepts/data-quality-dimensions.md#data-reasonableness)|This check measures the percentage of **false** values in a boolean column. It raises a data quality issue when the measured percentage is outside the accepted range.|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/bool](../checks/column/bool/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
