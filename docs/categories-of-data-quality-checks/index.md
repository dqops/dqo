# Categories of data quality checks
Read this guide to learn how the types of popular data quality checks are divided into categories, and what categories of checks are supported by DQOps.


## Categories of data quality checks
Data quality checks in DQOps are divided into categories of similar checks that detect the same group of data quality issues.
The following table shows the most common data quality issues that are detected by DQOps, divided into categories.

| Data quality problem                                                                             | YAML node name                                      | Description of the data quality problem                                                                                                                                          | Check reference                                                                                                  |
|--------------------------------------------------------------------------------------------------|-----------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| [Anomalies](how-to-detect-anomaly-data-quality-issues.md)                                        | <span class="no-wrap-code">`anomaly`</span>         | Monitor anomalies in numeric values by applying a time-series analysis. Get notified when new values appear to differ from regular values.                                       | [column-level](../checks/column/anomaly/index.md)                                                                |
| [Accepted values](how-to-validate-accepted-values-in-columns.md)                                 | <span class="no-wrap-code">`accepted_values`</span> | Verify that text or numeric columns contain only well-known values from a set of accepted values.                                                                                | [column-level](../checks/column/accepted_values/index.md)                                                        |
| [Blanks and whitespaces](how-to-detect-blank-and-whitespace-values.md)                           | <span class="no-wrap-code">`blanks`</span>          | Analyze text columns to detect values that should be *null*, but instead a programming language specific text is stored in the column: '', ' ', 'undefined', 'None', 'n/a', etc. | [column-level](../checks/column/blanks/index.md)                                                                 |
| [Booleans](how-to-detect-data-quality-issues-in-bool-fields.md)                                  | <span class="no-wrap-code">`bool`</span>            | Measure the percentage of *true* and *false* values in the dataset.                                                                                                              | [column-level](../checks/column/bool/index.md)                                                                   |
| [Comparing tables](how-to-reconcile-data-and-detect-differences.md)                              | <span class="no-wrap-code">`comparisons`</span>     | Compare (reconcile) data across different data sources.                                                                                                                          | [table-level](../checks/table/comparisons/index.md) <br /> [column-level](../checks/column/comparisons/index.md) |
| [Custom SQL <br />and multi column checks](how-to-detect-data-quality-issues-with-custom-sql.md) | <span class="no-wrap-code">`custom_sql`</span>      | Validate data using custom SQL expressions, also comparing multiple columns in the same table.                                                                                   | [table-level](../checks/table/custom_sql/index.md) <br /> [column-level](../checks/column/custom_sql/index.md)   |
| [Data accuracy](how-to-detect-accuracy-data-quality-issues.md)                                   | <span class="no-wrap-code">`accuracy`</span>        | Compare the tested table with another (reference) table by joining tables in the same database. On a column level, compare the *sum*, *min*, *max*, *not null count* of values.  | [table-level](../checks/table/accuracy/index.md) <br /> [column-level](../checks/column/accuracy/index.md)       |
| [Data type detection](how-to-detect-data-type-changes.md)                                        | <span class="no-wrap-code">`datatype`</span>        | Analyze text columns that are supposed to store only numeric, boolean, date, timestamp, or text values. Detect if values of a different data type are found instead.             | [column-level](../checks/column/datatype/index.md)                                                               |
| [Data volume](how-to-detect-data-volume-issues-and-changes.md)                                   | <span class="no-wrap-code">`volume`</span>          | Monitor the volume (*row count*) in tables and partitions. Get notified when unexpected changes in the volume is detected.                                                       | [table-level](../checks/table/volume/index.md)                                                                   |
| [Invalid dates](how-to-detect-invalid-dates.md)                                                  | <span class="no-wrap-code">`datetime`</span>        | Detect common data quality issues in *date* or *datetime* columns, such as dates in the future our dates out of a valid range.                                                   | [column-level](../checks/column/datetime/index.md)                                                               |
| [Nulls](how-to-detect-nulls-data-quality-issues.md)                                              | <span class="no-wrap-code">`nulls`</span>           | Detect null values in columns, or detect columns that are empty. Also detect anomalies in the percentage of null values.                                                         | [column-level](../checks/column/nulls/index.md)                                                                  |
| [Numerics](how-to-detect-data-quality-issues-in-numeric-fields.md)                               | <span class="no-wrap-code">`numeric`</span>         | Analyze numeric columns to detect if the values are in the expected ranges.                                                                                                      | [column-level](../checks/column/numeric/index.md)                                                                |
| [PII values](how-to-detect-pii-values-and-sensitive-data.md)                                     | <span class="no-wrap-code">`pii`</span>             | Find PII values (Personally Identifiable Information) in text columns.                                                                                                           | [column-level](../checks/column/pii/index.md)                                                                    |
| [Referential integrity](how-to-detect-data-referential-integrity-issues.md)                      | <span class="no-wrap-code">`integrity`</span>       | Detect data integrity issues, such as missing rows when doing a lookup by a primary key.                                                                                         | [column-level](../checks/column/integrity/index.md)                                                              |
| [Table availability](how-to-table-availability-issues-and-downtimes.md)                          | <span class="no-wrap-code">`availability`</span>    | Monitor whether the table is accessible and available for use. Detect problems with the data source availability and physical reliability.                                       | [table-level](../checks/table/availability/index.md)                                                             |
| [Table schema drifts](how-to-detect-table-schema-changes.md)                                     | <span class="no-wrap-code">`schema`</span>          | Detect table schema changes and drifts. DQOps detects also changes to the list of columns, order of columns, and types of columns.                                               | [table-level](../checks/table/schema/index.md) <br /> [column-level](../checks/column/schema/index.md)           |
| [Text patterns](how-to-detect-bad-values-not-matching-patterns.md)                               | <span class="no-wrap-code">`patterns`</span>        | Validate text columns with built-in patterns such as an email address, or using custom regular expressions.                                                                      | [column-level](../checks/column/patterns/index.md)                                                               |
| [Text statistics](how-to-detect-data-quality-issues-in-text-fields.md)                           | <span class="no-wrap-code">`text`</span>            | Analyze text columns to detect values shorter or longer than the maximum accepted length. Also verify if text values can be safely conveerted to numbers, booleans, dates, etc.  | [column-level](../checks/column/text/index.md)                                                                   |
| [Timeliness and freshness](how-to-detect-timeliness-and-freshness-issues.md)                     | <span class="no-wrap-code">`timeliness`</span>      | Measure the freshness and staleness of tables. Detect outdated tables with no new records.                                                                                       | [table-level](../checks/table/timeliness/index.md)                                                               |
| [Uniqueness and duplicates](how-to-detect-data-uniqueness-issues-and-duplicates.md)              | <span class="no-wrap-code">`uniqueness`</span>      | Detect duplicate values in columns. Ensure that values in columns are unique.                                                                                                    | [column-level](../checks/column/uniqueness/index.md)                                                             |


## Check categories in the user interface
The [data quality check editor](../dqo-concepts/dqops-user-interface-overview.md#check-editor) shows data quality checks categories as collapsible sections.

### **Table-level check editor**
The categories of table-level checks are shown when opening the data quality check editor for a table.

![table-level data quality check categories](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/table-level-data-quality-check-categories-min.png)

### **Column-level check editor**
The categories of column-level checks are shown when opening the data quality check editor for a column.

![column-level data quality check categories](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/column-level-data-quality-check-categories-min.png)


## Referencing check categories in YAML files
If you want to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md)
directly in the DQOps YAML files, the categories are defined as YAML nodes below respective
elements for [types of data quality checks](../dqo-concepts/definition-of-data-quality-checks/index.md).

The following example of a [*.dqotable.yaml*](../reference/yaml/TableYaml.md#tableyaml) shows
the *category* nodes highlighted.

``` { .yaml .annotate linenums="1" hl_lines="12 21 27" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    cumulative_confirmed:
      type_snapshot:
        column_type: INT64
        nullable: true
      monitoring_checks:
        daily:
          schema:
            daily_column_exists:
              warning:
                expected_value: 1
    date:
      type_snapshot:
        column_type: DATE
        nullable: true
      profiling_checks:
        schema:
         profile_column_exists:
            warning:
              expected_value: 1
      monitoring_checks:
        daily:
          schema:
            daily_column_exists:
              warning:
                expected_value: 1
```


## What's next

- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) for
  one category of checks.
- Review the [reference of data quality checks](../checks/index.md) supported by DQOps, that shows
  SQL queries generated for each data source, and YAML code samples for activating the checks.
