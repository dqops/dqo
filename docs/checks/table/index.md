---
title: List of table level
---
# List of table level

This is a list of table data quality checks supported by DQOps, broken down by a category and a brief description of what quality issued they detect.




## table-level accuracy checks
Compares the tested table with another (reference) table.

### [total row count match percent](./accuracy/total-row-count-match-percent.md)
A table-level check that compares the row count of the current (tested) table with the row count of another table that is referenced. This check ensures that the difference between the row counts is below the maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.






## table-level availability checks
Checks whether the table is accessible and available for use.

### [table availability](./availability/table-availability.md)
A table-level check that ensures a query can be successfully executed on a table without server errors. It also verifies that the table exists and is accessible (queryable).
 The actual value (the result of the check) indicates the number of failures. If the table is accessible and a simple query can be executed without errors, the result will be 0.0.
 A sensor result (the actual value) of 1.0 indicates that there is a failure. Any value greater than 1.0 is stored only in the check result table and represents the number of consecutive failures in the following days.






## table-level comparisons checks
Compares the table (the row count, and the column count) to another table in a different data source.

### [row count match](./comparisons/row-count-match.md)
Table level comparison check that compares the row count of the current (parent) table with the row count of the reference table.



### [column count match](./comparisons/column-count-match.md)
Table level comparison check that compares the column count of the current (parent) table with the column count of the reference table.






## table-level custom_sql checks
Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

### [sql condition failed on table](./custom_sql/sql-condition-failed-on-table.md)
A table-level check that uses a custom SQL expression on each row to verify (assert) that all rows pass a custom condition defined as an SQL condition.
 Use the {alias} token to reference the tested table. This data quality check can be used to compare columns on the same table.
 For example, the condition can verify that the value in the *col_price* column is higher than the *col_tax* column using an SQL expression: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.
 Use an SQL expression that returns a *true* value for valid values and a *false* one for invalid values, because it is an assertion.



### [sql condition passed percent on table](./custom_sql/sql-condition-passed-percent-on-table.md)
A table-level check that ensures that a minimum percentage of rows passed a custom SQL condition (expression). Measures the percentage of rows passing the condition.
 Raises a data quality issue when the percent of valid rows is below the *min_percent* parameter.



### [sql aggregate expression on table](./custom_sql/sql-aggregate-expression-on-table.md)
A table-level check that calculates a given SQL aggregate expression on a table and verifies if the value is within a range of accepted values.



### [sql invalid record count on table](./custom_sql/sql-invalid-record-count-on-table.md)
A table-level check that uses a custom SQL query that return invalid values from column.
 Use the {table} token to reference the tested table. This data quality check can be used to compare columns on the same table.
 For example, when this check is applied on a *age* column, the condition can find invalid records in which the *age* is lower than 18 using an SQL query: &#x60;SELECT age FROM {table} WHERE age &lt; 18&#x60;.



### [import custom result on table](./custom_sql/import-custom-result-on-table.md)
A table-level check that uses a custom SQL SELECT statement to retrieve a result of running a custom data quality check that was hardcoded
 in the data pipeline, and the result was stored in a separate table. The SQL query that is configured in this external data quality results importer must be
 a complete SELECT statement that queries a dedicated table (created by the data engineers) that stores the results of custom data quality checks.
 The SQL query must return a *severity* column with values: 0 - data quality check passed, 1 - warning issue, 2 - error severity issue, 3 - fatal severity issue.






## table-level schema checks
Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.

### [column count](./schema/column-count.md)
A table-level check that retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns.



### [column count changed](./schema/column-count-changed.md)
A table-level check that detects if the number of columns in the table has changed since the last time the check (checkpoint) was run.
 This check retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to the last known number of columns
 that was captured and is stored in the data quality check results database.



### [column list changed](./schema/column-list-changed.md)
A table-level check that detects if the list of columns has changed since the last time the check was run.
 This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will not depend on the order of columns, only on the column names.
 A data quality issue will be detected if new columns were added or columns that existed during the previous test were dropped.



### [column list or order changed](./schema/column-list-or-order-changed.md)
A table-level check that detects if the list of columns and the order of columns have changed since the last time the check was run.
 This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will depend on the order of columns.
 A data quality issue will be detected if new columns were added, columns that existed during the previous test were dropped or the columns were reordered.



### [column types changed](./schema/column-types-changed.md)
A table-level check that detects if the column names or column types have changed since the last time the check was run.
 This check calculates a hash of the column names and all the components of the column&#x27;s data type: the data type name, length, scale, precision and nullability.
 A data quality issue will be detected if the hash of the column data types has changed. This check does not depend on the order of columns, the columns can be reordered as long
 as all columns are still present and the data types match since the last time they were tested.






## table-level timeliness checks
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

### [data freshness](./timeliness/data-freshness.md)
A table-level check that calculates the time difference between the most recent row in the table and the current time.
 The timestamp column that is used for comparison is defined as the timestamp_columns.event_timestamp_column on the table configuration.
 This check is also known as &quot;Data Freshness&quot;.



### [data freshness anomaly](./timeliness/data-freshness-anomaly.md)
This check calculates the most recent rows value and the current time and detects anomalies in a time series of previous averages.
 The timestamp column that is used for comparison is defined as the timestamp_columns.event_timestamp_column on the table configuration.
 It raises a data quality issue when the mean is in the top *anomaly_percent* percentage of the most outstanding values in the time series.
 This data quality check uses a 90-day time window and requires a history of at least 30 days.



### [data staleness](./timeliness/data-staleness.md)
A table-level check that calculates the time difference between the last timestamp when any data was loaded into a table and the current time.
 This check can only be use when a data pipeline, ETL process, or trigger in the data warehouse is filling an extra column with the timestamp when the data loading job was loaded.
 The ingestion column used for comparison is defined as the timestamp_columns.ingestion_timestamp_column on the table configuration.
 This check is also known as &quot;Data Staleness&quot;.



### [data ingestion delay](./timeliness/data-ingestion-delay.md)
A table-level check that calculates the time difference between the most recent row in the table and the most recent timestamp when the last row was loaded into the data warehouse or data lake.
 To identify the most recent row, the check finds the maximum value of the timestamp column that should contain the last modification timestamp from the source.
 The timestamp when the row was loaded is identified by the most recent (maximum) value a timestamp column that was filled by the data pipeline, for example: &quot;loaded_at&quot;, &quot;updated_at&quot;, etc.
 This check requires that the data pipeline is filling an extra column with the timestamp when the data loading job has been executed.
 The names of both columns used for comparison should be specified in the &quot;timestamp_columns&quot; configuration entry on the table.



### [reload lag](./timeliness/reload-lag.md)
A table-level check that calculates the maximum difference in days between ingestion timestamp and event timestamp values on any row.
 This check should be executed only as a partitioned check because this check finds the longest delay between the time that the row was created
 in the data source and the timestamp when the row was loaded into its daily or monthly partition.
 This check detects that a daily or monthly partition was reloaded, setting also the most recent timestamps in the created_at, loaded_at, inserted_at or other similar columns
 filled by the data pipeline or an ETL process during data loading.






## table-level uniqueness checks


### [duplicate record count](./uniqueness/duplicate-record-count.md)
This check counts duplicate records values. It raises a data quality issue when the number of duplicates is above a minimum accepted value.
 The default configuration detects duplicate rows by enforcing that the *min_count* of duplicates is zero.



### [duplicate record percent](./uniqueness/duplicate-record-percent.md)
This check measures the percentage of duplicate records values. It raises a data quality issue when the percentage of duplicates is above a minimum accepted value.
 The default threshold is 0% duplicate values.






## table-level volume checks
Evaluates the overall quality of the table by verifying the number of rows.

### [row count](./volume/row-count.md)
This check detects empty or too-small tables. It captures the row count of a tested table.
 This check raises a data quality issue when the row count is below a minimum accepted value.
 The default value of the rule parameter **min_count** is 1 (row), which detects empty tables.
 When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.



### [row count anomaly](./volume/row-count-anomaly.md)
This check detects anomalies in the day-to-day changes to the table volume (the row count).
 It captures the row count for each day and compares the row count change (increase or decrease) since the previous day.
 This check raises a data quality issue when the change is in the top **anomaly_percent** percentage of the biggest day-to-day changes.



### [row count change](./volume/row-count-change.md)
This check compares the current table volume (the row count) to the last known row count.
 It raises a data quality issue when the change in row count (increase or decrease) exceeds a maximum accepted percentage of change.



### [row count change 1 day](./volume/row-count-change-1-day.md)
This check compares the current table volume (the row count) to the row count from the previous day.
 It raises a data quality issue when the change in row count (increase or decrease) since yesterday exceeds a maximum accepted percentage of change.



### [row count change 7 days](./volume/row-count-change-7-days.md)
This check compares the current table volume (the row count) to the row count seven days ago.
 This check compares the table volume to a value a week ago to overcome weekly seasonability and to compare Mondays to Mondays, Tuesdays to Tuesdays, etc.
 It raises a data quality issue when the change in row count (increase or decrease) since a week ago exceeds a maximum accepted percentage of change.



### [row count change 30 days](./volume/row-count-change-30-days.md)
This check compares the current table volume (the row count) to the row count 30 days ago.
 This check compares the table volume to a month ago value to overcome monthly seasonability.
 It raises a data quality issue when the change in row count (increase or decrease) since a value 30 days ago exceeds a maximum accepted percentage of change.







