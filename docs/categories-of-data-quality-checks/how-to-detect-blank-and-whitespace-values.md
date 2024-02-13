# Detecting whitespace and null value placeholders
Read this guide to learn how to detect data quality issues in text columns containing spaces, tabs, or special texts equivalent to a null value.

The data quality checks for detecting whitespace and empty value placeholders are configured in the `whitespace` category in DQOps.

## Types of whitespaces
We can identify three types of blank texts that should be stored as `NULL` values.

### What is an empty text
An empty text is stored in the column as zero-length string ` `. 
Some database engines allow the storage of both a *NULL*  value or an empty string ` `. 
Empty strings cause issues in SQL queries because an empty string is not equal to a *null* value. 
An SQL query `WHERE column_name IS NULL` will not find rows with empty values.

DQOps detects empty values using the following SQL condition.

```sql
WHERE column_name = ''
```

### What is a whitespace
A whitespace is a character that represents vertical space.
The whitespace characters are a space `' ' ` character and a tab `'\t'` character.
Whitespaces are detected in SQL by using a `TRIM` function. 

DQOps uses the following SQL condition to detect that a column contains only whitespace instead of being empty or null.

```sql
WHERE TRIM(column_name) = ''
```

### What is a null placeholder
Various programming languages use a different name for a special object that identifies a missing value. 
For example, Python uses `None`, and JavaScript uses `undefined`.
When the data pipeline converts these objects to a string, they may be written as texts *None* or *undefined*. 
It happens when the data pipeline does not handle empty values correctly.

The following table shows the list of all common null placeholders.

| Null placeholder | Null placeholder | Null placeholder | Null placeholder |
|------------------|------------------|------------------|------------------|
| `null`           | `undefined`      | `missing`        | `nan`            |
| `none`           | `na`             | `n/a`            | `empty`          |
| `#n/d`           | `blank`          | `""`             | `''`             |
| `-`              | '' (empty text)  |                  |                  |

DQOps detects null placeholders using the following SQL condition.

```sql
WHERE column_name IN ('null', 'undefined', 'missing', 'nan', 'none', 'na', 'n/a', 'empty', '#n/d', 'blank', '""', '\'\'', '-', '')
```

## Blank values data quality checks
DQOps has two types of data quality checks to detect blank values.

- `*_found` data quality checks that raise an issue when DQOps finds any blank value

- `*_percent` data quality checks that measure the percentage of blank values 

## Detect any blank values
Enable the `_found` data quality checks to find blank values or accept a limited number of blank values.

### Configure checks in UI
The blank detection checks are standard data quality checks in the "whitespace" category.

![Enable whitespace and null placeholder values detection in column values](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/enable-whitespace-and-null-placeholder-detection-data-quality-checks-min.png){ loading=lazy }

### Configure checks in YAML
The blank detection checks are configured in the YAML file inside the `whitespace` node.

``` { .yaml linenums="1" hl_lines="12-21" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    county:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          whitespace:
            daily_empty_text_found:
              error:
                max_count: 0
            daily_whitespace_text_found:
              error:
                max_count: 0
            daily_null_placeholder_text_found:
              error:
                max_count: 0
```



## Measure the percentage of blank values
The blank percent measurement checks are advanced checks, not always visible in the user interface.

### Configure checks in UI
Turn on the "Advanced checks" checkbox. DQOps will also show non-standard checks, revealing the percentage of whitespace checks.

![Measure percentage of empty values, whitespace only values and null placeholders using data quality checks](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/measure-whitespace-and-null-placeholder-percent-data-quality-checks-min.png){ loading=lazy }

### Configure checks in YAML
The percentage checks are easy to configure in a YAML file. The parameter `max_percent` controls the maximum accepted percentage.

``` { .yaml linenums="1" hl_lines="12-21"  }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    county:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          whitespace:
            daily_empty_text_percent:
              error:
                max_percent: 0.0
            daily_whitespace_text_percent:
              error:
                max_percent: 0.0
            daily_null_placeholder_text_percent:
              error:
                max_percent: 0.0
```


## List of whitespace checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*empty_text_found*](../checks/column/whitespace/empty-text-found.md)|Completeness|A column-level check that ensures that there are no more than a maximum number of empty texts in a monitored column.|:material-check-bold:|
|[*whitespace_text_found*](../checks/column/whitespace/whitespace-text-found.md)|Completeness|A column-level check that ensures that there are no more than a maximum number of whitespace texts in a monitored column.|:material-check-bold:|
|[*null_placeholder_text_found*](../checks/column/whitespace/null-placeholder-text-found.md)|Completeness|A column-level check that ensures that there are no more than a maximum number of rows with a null placeholder text in a monitored column.|:material-check-bold:|
|[*empty_text_percent*](../checks/column/whitespace/empty-text-percent.md)|Completeness|A column-level check that ensures that there are no more than a maximum percent of empty texts in a monitored column.| |
|[*whitespace_text_percent*](../checks/column/whitespace/whitespace-text-percent.md)|Completeness|A column-level check that ensures that there are no more than a maximum percent of whitespace texts in a monitored column.| |
|[*null_placeholder_text_percent*](../checks/column/whitespace/null-placeholder-text-percent.md)|Completeness|A column-level check that ensures that there are no more than a maximum percent of rows with a null placeholder text in a monitored column.| |
|[*text_surrounded_by_whitespace*](../checks/column/whitespace/text-surrounded-by-whitespace.md)|Validity|A column-level check that ensures that there are no more than a maximum number of text values that are surrounded by whitespace in a monitored column.| |
|[*text_surrounded_by_whitespace_percent*](../checks/column/whitespace/text-surrounded-by-whitespace-percent.md)|Validity|A column-level check that ensures that there are no more than a maximum percentage of text values that are surrounded by whitespace in a monitored column.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/whitespace](../checks/column/whitespace/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
