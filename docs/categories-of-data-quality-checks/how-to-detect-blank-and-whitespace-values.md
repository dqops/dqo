---
title: How to detect whitespace and null value placeholders
---
# How to detect whitespace and null value placeholders
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

![Enable whitespace and null placeholder values detection in column values](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/enable-whitespace-and-null-placeholder-detection-data-quality-checks-min1.png){ loading=lazy }

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

![Enable percentage of empty values, whitespace only values and null placeholders using data quality checks](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/enable-empty-values-whitespace-only-values-and-null-placeholders-data-quality-checks-min.png){ loading=lazy }

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


## Text surrounded by whitespace
DQOps has dedicated data quality checks to detect text values surrounded by whitespace characters such as space or tab.
Additional whitespace can appear when the data is entered manually and the user accidentally adds extra space.

There is another problem with the data that is stored in legacy databases. 
Old databases used fixed-width character data types, such as *CHAR(10)*.
Text values stored in these old databases were filled with extra spaces to fill the remaining character positions. 
Text values extracted from these databases will contain additional space characters at the end of shorter texts.

The following table shows an example of a data quality issue related to the inconsistent presence of space characters around a text value.

| state_id | state_name             |   Whitespace before   |   Whitespace after    |
|----------|------------------------|:---------------------:|:---------------------:|
| 1        | _null_                 |                       |                       |
| 2        | `'California'`         |                       |                       |
| 3        | **`'  Arizona'`**      | :material-check-bold: |                       |
| 4        | **`'Colorado    '`**   |                       | :material-check-bold: |
| 5        | **`'   Florida    '`** | :material-check-bold: | :material-check-bold: |

Additional whitespace around values causes the following issues.

- Two values that differ by extra whitespace around the text are not equal, leading to data duplication.

- SQL queries using a GROUP BY clause will return additional rows.

- SQL queries using joins will return no records when the columns that are joined differ by extra whitespace.

- The database will need additional space to store the values.

DQOps supports two very similar checks that detect texts surrounded by whitespace.

- The [*text_surrounded_by_whitespace_found*](../checks/column/whitespace/text-surrounded-by-whitespace-found.md) check
  finds and counts texts surrounded by whitespace characters.

- The [*text_surrounded_by_whitespace_percent*](../checks/column/whitespace/text-surrounded-by-whitespace-percent.md) check 
  finds and measures the percentage of texts surrounded by whitespace characters.

### Detecting text surrounded by whitespace in UI
The [*text_surrounded_by_whitespace_percent*](../checks/column/whitespace/text-surrounded-by-whitespace-percent.md) check takes one parameter, the *max_percent*. 
When the default value 0% is used, DQOps will raise a data quality issue when any value surrounded by whitespace is found.

Please note that the [DQOps data quality check editor](../dqo-concepts/dqops-user-interface-overview.md#check-editor)
shows this check after clicking the *Show advanced checks* checkbox.

![Detect texts surrounded by whitespace characters using DQOps data quality check](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/detect-text-surrounded-with-whitespace-data-quality-check-in-dqops-min.png){ loading=lazy }

The results in DQOps show that 75% of non-null values (three out of four) contained whitespace characters around the text.

### Detecting text surrounded by whitespace in YAML
The configuration of the [*text_surrounded_by_whitespace_percent*](../checks/column/whitespace/text-surrounded-by-whitespace-percent.md) check is straightforward.

``` { .yaml linenums="1" hl_lines="13-15" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    state_name:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          whitespace:
            daily_text_surrounded_by_whitespace_percent:
              error:
                max_percent: 0.0
```


## List of whitespace checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*empty_text_found*](../checks/column/whitespace/empty-text-found.md)|Completeness|This check detects empty texts that are not null. Empty texts have a length of zero. The database treats them as values different than nulls, and some databases allow the storage of both null and empty values. This check counts empty texts and raises a data quality issue when the number of empty values exceeds a *max_count* parameter value.|:material-check-bold:|
|[*whitespace_text_found*](../checks/column/whitespace/whitespace-text-found.md)|Completeness|This check detects empty texts containing only spaces and other whitespace characters. This check counts whitespace-only texts and raises a data quality issue when their count exceeds a *max_count* parameter value.|:material-check-bold:|
|[*null_placeholder_text_found*](../checks/column/whitespace/null-placeholder-text-found.md)|Completeness|This check detects text values that are well-known equivalents (placeholders) of a null value, such as *null*, *None*, *n/a*. This check counts null placeholder values and raises a data quality issue when their count exceeds a *max_count* parameter value.|:material-check-bold:|
|[*empty_text_percent*](../checks/column/whitespace/empty-text-percent.md)|Completeness|This check detects empty texts that are not null. Empty texts have a length of zero. This check measures the percentage of empty texts and raises a data quality issue when the rate of empty values exceeds a *max_percent* parameter value.| |
|[*whitespace_text_percent*](../checks/column/whitespace/whitespace-text-percent.md)|Completeness|This check detects empty texts containing only spaces and other whitespace characters. This check measures the percentage of whitespace-only texts and raises a data quality issue when their rate exceeds a *max_percent* parameter value.| |
|[*null_placeholder_text_percent*](../checks/column/whitespace/null-placeholder-text-percent.md)|Completeness|This check detects text values that are well-known equivalents (placeholders) of a null value, such as *null*, *None*, *n/a*. This check measures the percentage of null placeholder values and raises a data quality issue when their rate exceeds a *max_percent* parameter value.| |
|[*text_surrounded_by_whitespace_found*](../checks/column/whitespace/text-surrounded-by-whitespace-found.md)|Consistency|This check detects text values that contain additional whitespace characters before or after the text. This check counts text values surrounded by whitespace characters (on any side) and raises a data quality issue when their count exceeds a *max_count* parameter value. Whitespace-surrounded texts should be trimmed before loading to another table.| |
|[*text_surrounded_by_whitespace_percent*](../checks/column/whitespace/text-surrounded-by-whitespace-percent.md)|Consistency|This check detects text values that contain additional whitespace characters before or after the text. This check measures the percentage of text value surrounded by whitespace characters (on any side) and raises a data quality issue when their rate exceeds a *max_percent* parameter value.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/whitespace](../checks/column/whitespace/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
