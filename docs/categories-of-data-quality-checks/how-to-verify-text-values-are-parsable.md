---
title: How to test if a text value is convertible to a target data type
---
# How to test if a text value is convertible to a target data type
Read this guide to learn if values in text columns can be safely parsed and converted to boolean, integer, numeric, and date types.

The data type conversion checks are configured in the `conversions` category in DQOps.

## Why testing data conversions
Unstructured data such as CSV files needs transformation before it is usable for analytics or machine learning.
The files are stored in the landing zone or a data lake. 
When the files are loaded into a data warehouse for the first time, storing all values in text columns is the safest way. 
Before the raw data is usable for analytics, it must be transformed into the correct data types, 
such as integer, numeric, float, date, or datetime. For example, a '12a' value is not convertible to an integer type.

The data transformation pipeline has four options to convert invalid values that are not convertible.

- Replace invalid values with nulls, which leads to missing data and completeness issues.

- Skip entire rows, which causes even more severe completeness issues, also affecting other columns that are not loaded.

- Stop processing until the source data is fixed, which leads to completeness and freshness because no more data is loaded afterward.

- Sometimes, the data pipeline converts values to a target data type without proper error handling. 
  For example, the pipeline uses a `CAST` function instead of `SAFE_CAST`, 
  which causes an error when the pipeline converts the first invalid value.
  The data pipeline stops and will not load any new data until the code or data is corrected.

We can avoid all these issues by testing all text columns that are converted by the data pipelines.

## Detecting not convertible values
The data conversion testing depends on safely casting text value to a target value and finding values that cannot be converted.
DQOps has several data type conversion checks that use `SAFE_CAST` or similar SQL functions to verify convertibility.

- The [*text_parsable_to_boolean_percent*](../checks/column/conversions/text-parsable-to-boolean-percent.md)
  check verifies that the values in the column are parsable as boolean placeholders,
  such as *0*, *1*, *true*, *false*, *T*, *F*, and other familiar texts.

- The [*text_parsable_to_integer_percent*](../checks/column/conversions/text-parsable-to-integer-percent.md)
  check tries to convert text values to integer values.

- The [*text_parsable_to_float_percent*](../checks/column/conversions/text-parsable-to-float-percent.md)
  check tries to convert text values to float or numeric values.

- The [*text_parsable_to_date_percent*](../checks/column/conversions/text-parsable-to-date-percent.md)
  check tries to parse and convert text values to a date value.

All these checks measure the percentage of correct values in a valid format, 
which are convertible to the target data type without a loss of precision or data.

### Validated data types
The table below summarizes valid and invalid text formats for each supported data type.

| Data type conversion check                                                                              | Target data types              | Valid values                                                                                                                               | Invalid Values                                 |
|---------------------------------------------------------------------------------------------------------|--------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------|
| [*text_parsable_to_boolean_percent*](../checks/column/conversions/text-parsable-to-boolean-percent.md)  | boolean                        | *true*, *TRUE*, *True*, *false*, *FALSE*, *False*, *t*, *T*, *f*, *F*, *y*, *Y*, *n*, *N*, *yes*, *YES*, *Yes*, *no*, *NO*, *No*, *1*, *0* | *2*, *accepted*, *12.3*                        |
| [*text_parsable_to_integer_percent*](../checks/column/conversions/text-parsable-to-integer-percent.md)  | int, integer, bigint           | *0*, *10*, *123456*, *-23444*                                                                                                              | *2.11*, *x12*, *12a*, *2023-10-12*, *finished* |
| [*text_parsable_to_float_percent*](../checks/column/conversions/text-parsable-to-float-percent.md)      | number, decimal, float, double | *0*, *12*, *-445*, *123.456*, *-12.3*                                                                                                      | *ok*, *x12*, *12a*                             |                                         
| [*text_parsable_to_date_percent*](../checks/column/conversions/text-parsable-to-date-percent.md)        | date                           | *2024-02-10*, *10/02/2024* (depending on the default database locale settings)                                                             | *10*, *true*, *2024-40-99*                     |

### Validating other data types
DQOps supports creating custom data quality checks. 
The data types detected by the built-in checks are limited to the checks that work correctly on the majority of database engines.

If you need to validate convertibility to a different data type, please make a copy of one of the built-in checks
and follow the manual for [creating custom data quality checks](../working-with-dqo/creating-custom-data-quality-checks.md). 
The custom check can cast the value to a more specific data type, including the target length, scale, or precision.

## Validating integer values
The following example shows how to validate identifier values in a raw table stored.
The identifier column is *product_id*, which should contain only integer values. 
The data profiling screen reveals that the column contains a non-numeric value "3Product".

![Text column with integers and one invalid text that cannot be converted to a number](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/integer-with-invalid-values-column-profile-min.png){ loading=lazy; width="1200px" }

### Testing valid integers in UI
The [*text_parsable_to_integer_percent*](../checks/column/conversions/text-parsable-to-integer-percent.md) check
tries to cast all values in a text column to integers. 
It measures the percentage of valid values.
The data quality check is configured by setting the **min_percent** parameter, 
which is the minimum percentage of convertible values.

![Data conversion data quality check to parse text values as integer values](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/text-value-convertible-to-integer-data-quality-check-min.png){ loading=lazy; width="1200px" }

### Testing valid integers in YAML
The [*text_parsable_to_integer_percent*](../checks/column/conversions/text-parsable-to-integer-percent.md) data quality check is easy to configure.

``` { .yaml linenums="1" hl_lines="13-15" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    product_id:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          conversions:
            daily_text_parsable_to_integer_percent:
              error:
                min_percent: 100.0
```

## List of conversions checks at a column level
| Data quality check name | Friendly name | Data quality dimension | Description | Standard check |
|-------------------------|---------------|------------------------|-------------|----------------|
|[*text_parsable_to_boolean_percent*](../checks/column/conversions/text-parsable-to-boolean-percent.md)|Minimum percentage of rows containing a text parsable to a boolean value|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|Verifies that values in a text column are convertible to a boolean value. Texts are convertible to a boolean value when they are one of the well-known boolean placeholders: &#x27;0&#x27;, &#x27;1&#x27;, &#x27;true&#x27;, &#x27;false&#x27;, &#x27;yes&#x27;, &#x27;no&#x27;, &#x27;y&#x27;, &#x27;n&#x27;. This check measures the percentage of valid values and raises a data quality issue when the percentage of valid values is below an accepted rate.|:material-check-bold:|
|[*text_parsable_to_integer_percent*](../checks/column/conversions/text-parsable-to-integer-percent.md)|Minimum percentage of rows containing a text parsable to integer|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|Verifies that values in a text column can be parsed and converted to an integer type. This check measures the percentage of valid values and raises a data quality issue when the percentage of valid values is below an accepted rate.|:material-check-bold:|
|[*text_parsable_to_float_percent*](../checks/column/conversions/text-parsable-to-float-percent.md)|Minimum percentage of rows containing a text parsable to float|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|Verifies that values in a text column can be parsed and converted to a float (or numeric) type. This check measures the percentage of valid values and raises a data quality issue when the percentage of valid values is below an accepted rate.|:material-check-bold:|
|[*text_parsable_to_date_percent*](../checks/column/conversions/text-parsable-to-date-percent.md)|Minimum percentage of rows containing a text parsable to date|[Validity](../dqo-concepts/data-quality-dimensions.md#data-validity)|Verifies that values in a text column can be parsed and converted to a date type. This check measures the percentage of valid values and raises a data quality issue when the percentage of valid values is below an accepted rate.|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/conversions](../checks/column/conversions/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
