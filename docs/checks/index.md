# Data quality checks

This is a list of data quality checks supported by DQOps, broken down by a category and a brief description of what data quality issues they detect.




## table-level accuracy checks
Compares the tested table with another (reference) table.

### [total row count match percent](./table/accuracy/total-row-count-match-percent.md)
A table-level check that compares the row count of the current (tested) table with the row count of another table that is referenced. This check ensures that the difference between the row counts is below the maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.






## table-level availability checks
Checks whether the table is accessible and available for use.

### [table availability](./table/availability/table-availability.md)
A table-level check that ensures a query can be successfully executed on a table without server errors. It also verifies that the table exists and is accessible (queryable).
 The actual value (the result of the check) indicates the number of failures. If the table is accessible and a simple query can be executed without errors, the result will be 0.0.
 A sensor result (the actual value) of 1.0 indicates that there is a failure. Any value greater than 1.0 is stored only in the check result table and represents the number of consecutive failures in the following days.






## table-level comparisons checks
Compares the table (the row count, and the column count) to another table in a different data source.

### [row count match](./table/comparisons/row-count-match.md)
Table level comparison check that compares the row count of the current (parent) table with the row count of the reference table.



### [column count match](./table/comparisons/column-count-match.md)
Table level comparison check that compares the column count of the current (parent) table with the column count of the reference table.






## table-level custom_sql checks
Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

### [sql condition failed on table](./table/custom_sql/sql-condition-failed-on-table.md)
A table-level check that uses a custom SQL expression on each row to verify (assert) that all rows pass a custom condition defined as an SQL condition.
 Use the {alias} token to reference the tested table. This data quality check can be used to compare columns on the same table.
 For example, the condition can verify that the value in the *col_price* column is higher than the *col_tax* column using an SQL expression: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.
 Use an SQL expression that returns a *true* value for valid values and a *false* one for invalid values, because it is an assertion.



### [sql condition passed percent on table](./table/custom_sql/sql-condition-passed-percent-on-table.md)
A table-level check that ensures that a minimum percentage of rows passed a custom SQL condition (expression). Measures the percentage of rows passing the condition.
 Raises a data quality issue when the percent of valid rows is below the *min_percent* parameter.



### [sql aggregate expression on table](./table/custom_sql/sql-aggregate-expression-on-table.md)
A table-level check that calculates a given SQL aggregate expression on a table and verifies if the value is within a range of accepted values.



### [import custom result on table](./table/custom_sql/import-custom-result-on-table.md)
A table-level check that uses a custom SQL SELECT statement to retrieve a result of running a custom data quality check that was hardcoded
 in the data pipeline, and the result was stored in a separate table. The SQL query that is configured in this external data quality results importer must be
 a complete SELECT statement that queries a dedicated table (created by the data engineers) that stores the results of custom data quality checks.
 The SQL query must return a *severity* column with values: 0 - data quality check passed, 1 - warning issue, 2 - error severity issue, 3 - fatal severity issue.






## table-level schema checks
Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.

### [column count](./table/schema/column-count.md)
A table-level check that retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns.



### [column count changed](./table/schema/column-count-changed.md)
A table-level check that detects if the number of columns in the table has changed since the last time the check (checkpoint) was run.
 This check retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to the last known number of columns
 that was captured and is stored in the data quality check results database.



### [column list changed](./table/schema/column-list-changed.md)
A table-level check that detects if the list of columns has changed since the last time the check was run.
 This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will not depend on the order of columns, only on the column names.
 A data quality issue will be detected if new columns were added or columns that existed during the previous test were dropped.



### [column list or order changed](./table/schema/column-list-or-order-changed.md)
A table-level check that detects if the list of columns and the order of columns have changed since the last time the check was run.
 This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will depend on the order of columns.
 A data quality issue will be detected if new columns were added, columns that existed during the previous test were dropped or the columns were reordered.



### [column types changed](./table/schema/column-types-changed.md)
A table-level check that detects if the column names or column types have changed since the last time the check was run.
 This check calculates a hash of the column names and all the components of the column&#x27;s data type: the data type name, length, scale, precision and nullability.
 A data quality issue will be detected if the hash of the column data types has changed. This check does not depend on the order of columns, the columns can be reordered as long
 as all columns are still present and the data types match since the last time they were tested.






## table-level timeliness checks
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

### [data freshness](./table/timeliness/data-freshness.md)
A table-level check that calculates the time difference between the most recent row in the table and the current time.
 The timestamp column that is used for comparison is defined as the timestamp_columns.event_timestamp_column on the table configuration.
 This check is also known as &quot;Data Freshness&quot;.



### [data staleness](./table/timeliness/data-staleness.md)
A table-level check that calculates the time difference between the last timestamp when any data was loaded into a table and the current time.
 This check can only be use when a data pipeline, ETL process, or trigger in the data warehouse is filling an extra column with the timestamp when the data loading job was loaded.
 The ingestion column used for comparison is defined as the timestamp_columns.ingestion_timestamp_column on the table configuration.
 This check is also known as &quot;Data Staleness&quot;.



### [data ingestion delay](./table/timeliness/data-ingestion-delay.md)
A table-level check that calculates the time difference between the most recent row in the table and the most recent timestamp when the last row was loaded into the data warehouse or data lake.
 To identify the most recent row, the check finds the maximum value of the timestamp column that should contain the last modification timestamp from the source.
 The timestamp when the row was loaded is identified by the most recent (maximum) value a timestamp column that was filled by the data pipeline, for example: &quot;loaded_at&quot;, &quot;updated_at&quot;, etc.
 This check requires that the data pipeline is filling an extra column with the timestamp when the data loading job has been executed.
 The names of both columns used for comparison should be specified in the &quot;timestamp_columns&quot; configuration entry on the table.



### [reload lag](./table/timeliness/reload-lag.md)
A table-level check that calculates the maximum difference in days between ingestion timestamp and event timestamp values on any row.
 This check should be executed only as a partitioned check because this check finds the longest delay between the time that the row was created
 in the data source and the timestamp when the row was loaded into its daily or monthly partition.
 This check detects that a daily or monthly partition was reloaded, setting also the most recent timestamps in the created_at, loaded_at, inserted_at or other similar columns
 filled by the data pipeline or an ETL process during data loading.






## table-level volume checks
Evaluates the overall quality of the table by verifying the number of rows.

### [row count](./table/volume/row-count.md)
A table-level check that ensures that the tested table has at least a minimum accepted number of rows. Using the default configuration, detects empty tables.
 The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty.



### [row count anomaly](./table/volume/row-count-anomaly.md)
A table-level check that ensures that the row count is within a two-tailed percentile from measurements made during the last 90 days.



### [row count change](./table/volume/row-count-change.md)
A table-level check that ensures that the row count changed by a fixed rate since the last readout.



### [row count change 1 day](./table/volume/row-count-change-1-day.md)
A table-level check that ensures that the row count changed by a fixed rate since the last readout from yesterday.



### [row count change 7 days](./table/volume/row-count-change-7-days.md)
A table-level check that ensures that the row count changed by a fixed rate since the last readout from last week.



### [row count change 30 days](./table/volume/row-count-change-30-days.md)
A table-level check that ensures that the row count changed by a fixed rate since the last readout from last month.

















































































## column-level accepted_values checks
Verifies if all values in the column are from a set of known values, such as country codes.

### [text found in set percent](./column/accepted_values/text-found-in-set-percent.md)
A column-level check that calculates the percentage of rows for which the tested text column contains a value from a set of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 The check raises a data quality issue when the percentage of rows with a not null column value that is not expected (not one of the values in the expected_values set)
 is below the expected threshold. For example, 99% of rows should have values from the defined domain.
 This data quality check is useful for checking text columns that have a small number of unique values, and all the values should come from a set of expected values.
 For example, testing country, state, currency, gender, type, and department columns whose expected values are known.



### [number found in set percent](./column/accepted_values/number-found-in-set-percent.md)
A column-level check that calculates the percentage of rows for which the tested numeric column contains a value from a set of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 The check raises a data quality issue when the percentage of rows with a not null column value that is not expected (not one of the values in the expected_values set)
 is below the expected threshold. For example, 99% of rows should have values from the defined domain.
 This data quality check is useful for checking numeric columns that store numeric codes (such as status codes) to see if the only values found in the column are from the set of expected values.



### [expected text values in use count](./column/accepted_values/expected-text-values-in-use-count.md)
A column-level check that counts unique values in a text column and counts how many values out of a list of expected string values were found in the column.
 The check raises a data quality issue when the threshold for the maximum number of missing has been exceeded (too many expected values were not found in the column).
 This check is useful for analysing columns with a low number of unique values, such as status codes, to detect whether all status codes are used in any row.



### [expected texts in top values count](./column/accepted_values/expected-texts-in-top-values-count.md)
A column-level check that counts how many expected text values are among the TOP most popular values in the column.
 The check will first count the number of occurrences of each column&#x27;s value and will pick the TOP X most popular values (configurable by the &#x27;top&#x27; parameter).
 Then, it will compare the list of most popular values to the given list of expected values that should be most popular.
 This check will verify how many supposed most popular values (provided in the &#x27;expected_values&#x27; list) were not found in the top X most popular values in the column.
 This check is helpful in analyzing string columns with frequently occurring values, such as country codes for countries with the most customers.



### [expected numbers in use count](./column/accepted_values/expected-numbers-in-use-count.md)
A column-level check that counts unique values in a numeric column and counts how many values out of a list of expected numeric values were found in the column.
 The check raises a data quality issue when the threshold for the maximum number of missing has been exceeded (too many expected values were not found in the column).
 This check is useful for analysing columns with a low number of unique values, such as status codes, to detect whether all status codes are used in any row.






## column-level accuracy checks


### [total sum match percent](./column/accuracy/total-sum-match-percent.md)
A column-level check that ensures that the difference between the sum of all values in the tested column and the sum of values in another column in a referenced table is below a maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.



### [total min match percent](./column/accuracy/total-min-match-percent.md)
A column-level check that ensures that the difference between the minimum value in the tested column and the minimum value in another column in a referenced table is below a maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.



### [total max match percent](./column/accuracy/total-max-match-percent.md)
A column-level check that ensures that the difference between the maximum value in the tested column and the maximum value in another column in a referenced table is below a maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.



### [total average match percent](./column/accuracy/total-average-match-percent.md)
A column-level check that ensures that the difference between the average value in the tested column and the average value of another column in the referenced table is below the maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.



### [total not null count match percent](./column/accuracy/total-not-null-count-match-percent.md)
A column-level check that ensures that the difference between the count of null values in the tested column and the count of null values in another column in a referenced table is below a maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.






## column-level anomaly checks
Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.

### [sum anomaly](./column/anomaly/sum-anomaly.md)
A column-level check that ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.



### [mean anomaly](./column/anomaly/mean-anomaly.md)
A column-level check that ensures that the mean value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.



### [median anomaly](./column/anomaly/median-anomaly.md)
A column-level check that ensures that the median in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.



### [min anomaly](./column/anomaly/min-anomaly.md)
A column-level check that detects big changes of the minimum value in a numeric column, detecting new data outliers.
 If the values in the column are slightly changing day-to-day, DQOps detects new minimum values that changed much more than the typical change for the last 90 days.



### [max anomaly](./column/anomaly/max-anomaly.md)
A column-level check that detects big changes of the maximum value in a numeric column, detecting new data outliers.
 If the values in the column are slightly changing day-to-day, DQOps detects new maximum values that changed much more than the typical change for the last 90 days.



### [mean change](./column/anomaly/mean-change.md)
A column-level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout.



### [mean change 1 day](./column/anomaly/mean-change-1-day.md)
A column-level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from yesterday.



### [mean change 7 days](./column/anomaly/mean-change-7-days.md)
A column-level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from last week.



### [mean change 30 days](./column/anomaly/mean-change-30-days.md)
A column-level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from last month.



### [median change](./column/anomaly/median-change.md)
A column-level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout.



### [median change 1 day](./column/anomaly/median-change-1-day.md)
A column-level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from yesterday.



### [median change 7 days](./column/anomaly/median-change-7-days.md)
A column-level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from last week.



### [median change 30 days](./column/anomaly/median-change-30-days.md)
A column-level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from last month.



### [sum change](./column/anomaly/sum-change.md)
A column-level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout.



### [sum change 1 day](./column/anomaly/sum-change-1-day.md)
A column-level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from yesterday.



### [sum change 7 days](./column/anomaly/sum-change-7-days.md)
A column-level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from last week.



### [sum change 30 days](./column/anomaly/sum-change-30-days.md)
A column-level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from last month.






## column-level blanks checks
Detects text columns that contain blank values, or values that are used as placeholders for missing values: &#x27;n/a&#x27;, &#x27;None&#x27;, etc.

### [empty text found](./column/blanks/empty-text-found.md)
A column-level check that ensures that there are no more than a maximum number of empty texts in a monitored column.



### [whitespace text found](./column/blanks/whitespace-text-found.md)
A column-level check that ensures that there are no more than a maximum number of whitespace texts in a monitored column.



### [null placeholder text found](./column/blanks/null-placeholder-text-found.md)
A column-level check that ensures that there are no more than a maximum number of rows with a null placeholder text in a monitored column.



### [empty text percent](./column/blanks/empty-text-percent.md)
A column-level check that ensures that there are no more than a maximum percent of empty texts in a monitored column.



### [whitespace text percent](./column/blanks/whitespace-text-percent.md)
A column-level check that ensures that there are no more than a maximum percent of whitespace texts in a monitored column.



### [null placeholder text percent](./column/blanks/null-placeholder-text-percent.md)
A column-level check that ensures that there are no more than a maximum percent of rows with a null placeholder text in a monitored column.






## column-level bool checks
Calculates the percentage of data in boolean columns.

### [true percent](./column/bool/true-percent.md)
A column-level check that ensures that the proportion of true values in a column is not below the minimum accepted percentage.



### [false percent](./column/bool/false-percent.md)
A column-level check that ensures that the proportion of false values in a column is not below the minimum accepted percentage.






## column-level comparisons checks
Compares the columns in a table to another column in another table that is in a different data source.

### [sum match](./column/comparisons/sum-match.md)
A column-level check that ensures that compares the sum of the values in the tested column to the sum of values in a reference column from the reference table.
 Compares the sum of values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).



### [min match](./column/comparisons/min-match.md)
A column-level check that ensures that compares the minimum value in the tested column to the minimum value in a reference column from the reference table.
 Compares the minimum values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).



### [max match](./column/comparisons/max-match.md)
A column-level check that ensures that compares the maximum value in the tested column to maximum value in a reference column from the reference table.
 Compares the maximum values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).



### [mean match](./column/comparisons/mean-match.md)
A column-level check that ensures that compares the mean (average) of the values in the tested column to the mean (average) of values in a reference column from the reference table.
 Compares the mean (average) value for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).



### [not null count match](./column/comparisons/not-null-count-match.md)
A column-level check that ensures that compares the count of not null values in the tested column to the count of not null values in a reference column from the reference table.
 Compares the count of not null values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).



### [null count match](./column/comparisons/null-count-match.md)
A column-level check that ensures that compares the count of null values in the tested column to the count of null values in a reference column from the reference table.
 Compares the count of null values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).






## column-level custom_sql checks
Validate data against user-defined SQL queries at the column level. Checks in this group allow to validate whether a set percentage of rows has passed a custom SQL expression or whether the custom SQL expression is not outside the set range.

### [sql condition failed on column](./column/custom_sql/sql-condition-failed-on-column.md)
A column-level check that uses a custom SQL expression on each column to verify (assert) that all rows pass a custom condition defined as an SQL expression.
 Use the {alias} token to reference the tested table, and the {column} to reference the column that is tested. This data quality check can be used to compare columns on the same table.
 For example, when this check is applied on a *col_price* column, the condition can verify that the *col_price* is higher than the *col_tax* using an SQL expression: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;
 Use an SQL expression that returns a *true* value for valid values and *false* for invalid values, because it is an assertion.



### [sql condition passed percent on column](./column/custom_sql/sql-condition-passed-percent-on-column.md)
A table-level check that ensures that a minimum percentage of rows passed a custom SQL condition (expression). Measures the percentage of rows passing the condition.
 Raises a data quality issue when the percent of valid rows is below the *min_percent* parameter.



### [sql aggregate expression on column](./column/custom_sql/sql-aggregate-expression-on-column.md)
A column-level check that calculates a given SQL aggregate expression on a column and verifies if the value is within a range of accepted values.



### [import custom result on column](./column/custom_sql/import-custom-result-on-column.md)
Column level check that uses a custom SQL SELECT statement to retrieve a result of running a custom data quality check on a column by a custom
 data quality check, hardcoded in the data pipeline. The result is retrieved by querying a separate **logging table**, whose schema is not fixed.
 The logging table should have columns that identify a table and a column for which they store custom data quality check results, and a *severity* column of the data quality issue.
 The SQL query that is configured in this external data quality results importer must be
 a complete SELECT statement that queries a dedicated logging table, created by the data engineering team.






## column-level datatype checks
Analyzes all values in a text column to detect if all values can be safely parsed to numeric, boolean, date or timestamp data types. Used to analyze tables in the landing zone.

### [detected datatype in text](./column/datatype/detected-datatype-in-text.md)
A column-level check that scans all values in a string column and detects the data type of all values in a monitored column. The actual_value returned from the sensor can be one of seven codes: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types.
 The check compares the data type detected in all non-null columns to an expected data type. The rule compares the value using equals and requires values in the range 1..8, which are the codes of detected data types.



### [detected datatype in text changed](./column/datatype/detected-datatype-in-text-changed.md)
A column-level check that scans all values in a string column, finds the right data type and detects when the desired data type changes. The actual_value returned from the sensor can be one of seven codes: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types.
 The check compares the data type detected during the current run to the last known data type detected during a previous run. For daily monitoring checks, it will compare the value to yesterday&#x27;s value (or an earlier date).
 For partitioned checks, it will compare the current data type to the data type in the previous daily or monthly partition. The last partition with data is used for comparison.






## column-level datetime checks
Validates that the data in a date or time column is in the expected format and within predefined ranges.

### [date values in future percent](./column/datetime/date-values-in-future-percent.md)
Detects dates in the future in date, datetime and timestamp columns. Measures a percentage of dates in the future. Raises a data quality issue when too many future dates are found.



### [date in range percent](./column/datetime/date-in-range-percent.md)
Verifies that the dates in date, datetime, or timestamp columns are within a reasonable range of dates.
 The default configuration detects fake dates such as 1900-01-01 and 2099-12-31.
 Measures the percentage of valid dates and raises a data quality issue when too many dates are found.



### [text match date format percent](./column/datetime/text-match-date-format-percent.md)
Verifies that the values in text columns match one of the predefined date formats, such as an ISO 8601 date.
 Measures the percentage of valid date strings and raises a data quality issue when too many invalid date strings are found.






## column-level integrity checks
Checks the referential integrity of a column against a column in another table.

### [lookup key not found](./column/integrity/lookup-key-not-found.md)
A column-level check that ensures that there are no more than a maximum number of values not matching values in another table column.



### [lookup key found percent](./column/integrity/lookup-key-found-percent.md)
A column-level check that ensures that there are no more than a minimum percentage of values matching values in another table column.






## column-level nulls checks
Checks for the presence of null or missing values in a column.

### [nulls count](./column/nulls/nulls-count.md)
Detects incomplete columns that contain any *null* values. Counts the number of rows having a null value.
 Raises a data quality issue when the count of null values is above a *max_count* threshold.



### [nulls percent](./column/nulls/nulls-percent.md)
Detects incomplete columns that contain any *null* values. Measures the percentage of rows having a null value.
 Raises a data quality issue when the percentage of null values is above a *max_percent* threshold.
 Configure this check to accept a given percentage of null values by setting the *max_percent* parameter.



### [nulls percent anomaly](./column/nulls/nulls-percent-anomaly.md)
Detects day-to-day anomalies in the percentage of *null* values. Measures the percentage of rows having a *null* value.
 Raises a data quality issue when the rate of null values increases or decreases too much.



### [not nulls count](./column/nulls/not-nulls-count.md)
Detects empty columns that contain only *null* values. Counts the number of rows that have non-null values.
 Raises a data quality issue when the count of non-null values is below *min_count*.
 The default value of the *min_count* parameter is 1, but DQOps supports setting a higher number
 to assert that a column has at least that many non-null values.



### [not nulls percent](./column/nulls/not-nulls-percent.md)
Detects incomplete columns that contain too few non-null values. Measures the percentage of rows that have non-null values.
 Raises a data quality issue when the percentage of non-null values is below *min_percentage*.
 The default value of the *min_percentage* parameter is 100.0, but DQOps supports setting a lower value to accept some nulls.



### [nulls percent change](./column/nulls/nulls-percent-change.md)
Detects relative increases or decreases in the percentage of null values since the last measured percentage.
 Measures the percentage of null values for each day.
 Raises a data quality issue when the change in the percentage of null values is above *max_percent* of the previous percentage.



### [nulls percent change 1 day](./column/nulls/nulls-percent-change-1-day.md)
Detects relative increases or decreases in the percentage of null values since the previous day.
 Measures the percentage of null values for each day.
 Raises a data quality issue when the change in the percentage of null values is above *max_percent* of the previous percentage.



### [nulls percent change 7 days](./column/nulls/nulls-percent-change-7-days.md)
Detects relative increases or decreases in the percentage of null values since the last week (seven days ago).
 Measures the percentage of null values for each day.
 Raises a data quality issue when the change in the percentage of null values is above *max_percent* of the previous percentage.



### [nulls percent change 30 days](./column/nulls/nulls-percent-change-30-days.md)
Detects relative increases or decreases in the percentage of null values since the last month (30 days ago).
 Measures the percentage of null values for each day.
 Raises a data quality issue when the change in the percentage of null values is above *max_percent* of the previous percentage.






## column-level numeric checks
Validates that the data in a numeric column is in the expected format or within predefined ranges.

### [number below min value](./column/numeric/number-below-min-value.md)
A column-level check that ensures that the number of values in the monitored column with a value below a user-defined value as a parameter does not exceed set thresholds.



### [number above max value](./column/numeric/number-above-max-value.md)
A column-level check that ensures that the number of values in the monitored column with a value above a user-defined value as a parameter does not exceed set thresholds.



### [negative values](./column/numeric/negative-values.md)
A column-level check that ensures that there are no more than a set number of negative values in a monitored column.



### [negative values percent](./column/numeric/negative-values-percent.md)
A column-level check that ensures that there are no more than a set percentage of negative values in a monitored column.



### [number below min value percent](./column/numeric/number-below-min-value-percent.md)
A column-level check that ensures that the percentage of values in the monitored column with a value below a user-defined value as a parameter does not fall below set thresholds.



### [number above max value percent](./column/numeric/number-above-max-value-percent.md)
A column-level check that ensures that the percentage of values in the monitored column with a value above a user-defined value as a parameter does not fall below set thresholds.



### [number in range percent](./column/numeric/number-in-range-percent.md)
A column-level check that ensures that there are no more than a set percentage of values from the range in a monitored column.



### [integer in range percent](./column/numeric/integer-in-range-percent.md)
A column-level check that ensures that there are no more than a set number of values from range in a monitored column.



### [min in range](./column/numeric/min-in-range.md)
A column-level check that ensures that the minimum values are within the expected range in the monitored column.



### [max in range](./column/numeric/max-in-range.md)
A column-level check that ensures that the maximum values are within the expected range in the monitored column.



### [sum in range](./column/numeric/sum-in-range.md)
A column-level check that ensures that the sum value in the monitored column is within the expected range.



### [mean in range](./column/numeric/mean-in-range.md)
A column-level check that ensures that the average (mean) value in the monitored column is within the expected range.



### [median in range](./column/numeric/median-in-range.md)
A column-level check that ensures that the median value in the monitored column is within the expected range.



### [percentile in range](./column/numeric/percentile-in-range.md)
A column-level check that ensures that the percentile of values in a monitored columnis within the expected range.



### [percentile 10 in range](./column/numeric/percentile-10-in-range.md)
A column-level check that ensures that the 10th percentile of values in the monitored column is within the expected range.



### [percentile 25 in range](./column/numeric/percentile-25-in-range.md)
A column-level check that ensures that the 25th percentile of values in the monitored column is within the expected range.



### [percentile 75 in range](./column/numeric/percentile-75-in-range.md)
A column-level check that ensures that the 75th percentile of values in the monitored column is within the expected range.



### [percentile 90 in range](./column/numeric/percentile-90-in-range.md)
A column-level check that ensures that the 90th percentile of values in the monitored column is within the expected range.



### [sample stddev in range](./column/numeric/sample-stddev-in-range.md)
A column-level check that ensures that the standard deviation of the sample is within the expected range in the monitored column.



### [population stddev in range](./column/numeric/population-stddev-in-range.md)
A column-level check that ensures that the population standard deviationis within the expected range in a monitored column.



### [sample variance in range](./column/numeric/sample-variance-in-range.md)
A column-level check that ensures the sample varianceis within the expected range in a monitored column.



### [population variance in range](./column/numeric/population-variance-in-range.md)
A column-level check that ensures that the population varianceis within the expected range in a monitored column.



### [invalid latitude](./column/numeric/invalid-latitude.md)
A column-level check that ensures that there are no more than a set number of invalid latitude values in a monitored column.



### [valid latitude percent](./column/numeric/valid-latitude-percent.md)
A column-level check that ensures that there are no more than a set percentage of valid latitude values in a monitored column.



### [invalid longitude](./column/numeric/invalid-longitude.md)
A column-level check that ensures that there are no more than a set number of invalid longitude values in a monitored column.



### [valid longitude percent](./column/numeric/valid-longitude-percent.md)
A column-level check that ensures that there are no more than a set percentage of valid longitude values in a monitored column.



### [non negative values](./column/numeric/non-negative-values.md)
A column-level check that ensures that there are no more than a maximum number of non-negative values in a monitored column.



### [non negative values percent](./column/numeric/non-negative-values-percent.md)
A column-level check that ensures that there are no more than a set percentage of negative values in a monitored column.






## column-level patterns checks
Validates if a text column matches predefined patterns (such as an email address) or a custom regular expression.

### [text not matching regex found](./column/patterns/text-not-matching-regex-found.md)
A column-level that calculates the quantity of values that do not match the custom regex in a monitored column.



### [texts matching regex percent](./column/patterns/texts-matching-regex-percent.md)
A column-level that calculates the percentage of values that match the custom regex in a monitored column.



### [invalid email format found](./column/patterns/invalid-email-format-found.md)
A column-level check that ensures that there are no more than a maximum number of invalid emails in a monitored column.



### [text not matching date pattern found](./column/patterns/text-not-matching-date-pattern-found.md)
A column-level that calculates the quantity of values that do not match the date regex in a monitored column.



### [text matching date pattern percent](./column/patterns/text-matching-date-pattern-percent.md)
A column-level check that calculates the percentage of values that match the date regex in a monitored column.



### [text matching name pattern percent](./column/patterns/text-matching-name-pattern-percent.md)
A column-level that calculates the percentage of values that match the name regex in a monitored column.



### [invalid uuid format found](./column/patterns/invalid-uuid-format-found.md)
A column-level check that ensures that there are no more than a maximum number of invalid UUID in a monitored column.



### [valid uuid format percent](./column/patterns/valid-uuid-format-percent.md)
A column-level check that ensures that the percentage of valid UUID strings in the monitored column does not fall below set thresholds.



### [invalid ip4 address format found](./column/patterns/invalid-ip4-address-format-found.md)
A column-level check that ensures that there are no more than a maximum number of invalid IP4 address in a monitored column.



### [invalid ip6 address format found](./column/patterns/invalid-ip6-address-format-found.md)
A column-level check that ensures that there are no more than a maximum number of invalid IP6 address in a monitored column.






## column-level pii checks
Checks for the presence of sensitive or personally identifiable information (PII) in a column such as an email, phone, zip code, IP4, and IP6 addresses.

### [contains usa phone percent](./column/pii/contains-usa-phone-percent.md)
Column check that calculates the percentage of rows that contains USA phone number values in a monitored column.



### [contains email percent](./column/pii/contains-email-percent.md)
Column check that calculates the percentage of rows that contains valid email values in a monitored column.



### [contains usa zipcode percent](./column/pii/contains-usa-zipcode-percent.md)
Column check that calculates the percentage of rows that contains USA zip code values in a monitored column.



### [contains ip4 percent](./column/pii/contains-ip4-percent.md)
Column check that calculates the percentage of rows that contains valid IP4 address values in a monitored column.



### [contains ip6 percent](./column/pii/contains-ip6-percent.md)
Column check that calculates the percentage of rows that contains valid IP6 address values in a monitored column.






## column-level schema checks
Detects schema drifts such as a column is missing or the data type has changed.

### [column exists](./column/schema/column-exists.md)
A column-level check that reads the metadata of the monitored table and verifies if the column still exists in the data source.
 The data quality sensor returns a value of 1.0 when the column is found or 0.0 when the column is not found.



### [column type changed](./column/schema/column-type-changed.md)
A column-level check that detects if the data type of the column has changed since the last retrieval.
 This check calculates the hash of all the components of the column&#x27;s data type: the data type name, length, scale, precision and nullability.
 A data quality issue will be detected if the hash of the column&#x27;s data types has changed.






## column-level text checks
Validates that the data in a text column has a valid range, or can be parsed to other data types.

### [text max length](./column/text/text-max-length.md)
A column-level check that ensures that the length of text values in a column does not exceed the maximum accepted length.



### [text min length](./column/text/text-min-length.md)
A column-level check that ensures that the length of text in a column does not fall below the minimum accepted length.



### [text mean length](./column/text/text-mean-length.md)
A column-level check that ensures that the length of text values in a column does not exceed the mean accepted length.



### [text length below min length](./column/text/text-length-below-min-length.md)
A column-level check that ensures that the number of text values in the monitored column with a length below the length defined by the user as a parameter does not exceed set thresholds.



### [text length below min length percent](./column/text/text-length-below-min-length-percent.md)
A column-level check that ensures that the percentage of text values in the monitored column with a length below the length defined by the user as a parameter does not fall below set thresholds.



### [text length above max length](./column/text/text-length-above-max-length.md)
A column-level check that ensures that the number of text values in the monitored column with a length above the length defined by the user as a parameter does not exceed set thresholds.



### [text length above max length percent](./column/text/text-length-above-max-length-percent.md)
A column-level check that ensures that the percentage of text values in the monitored column with a length above the length defined by the user as a parameter does not fall below set thresholds.



### [text length in range percent](./column/text/text-length-in-range-percent.md)
Column check that calculates the percentage of text values with a length below the indicated by the user length in a monitored column.



### [text parsable to boolean percent](./column/text/text-parsable-to-boolean-percent.md)
A column-level check that ensures that the percentage of boolean placeholder texts (&#x27;0&#x27;, &#x27;1&#x27;, &#x27;true&#x27;, &#x27;false&#x27;, &#x27;yes&#x27;, &#x27;no&#x27;, &#x27;y&#x27;, &#x27;n&#x27;) in the monitored column does not fall below the minimum percentage.



### [text parsable to integer percent](./column/text/text-parsable-to-integer-percent.md)
A column-level check that ensures that the percentage of text values that are parsable to integer in the monitored column does not fall below set thresholds.



### [text parsable to float percent](./column/text/text-parsable-to-float-percent.md)
A column-level check that ensures that the percentage of strings that are parsable to float in the monitored column does not fall below set thresholds.



### [text parsable to date percent](./column/text/text-parsable-to-date-percent.md)
A column-level check that ensures that there is at least a minimum percentage of valid text values that are valid date strings (are parsable to a DATE type) in a monitored column.



### [text surrounded by whitespace](./column/text/text-surrounded-by-whitespace.md)
A column-level check that ensures that there are no more than a maximum number of text values that are surrounded by whitespace in a monitored column.



### [text surrounded by whitespace percent](./column/text/text-surrounded-by-whitespace-percent.md)
A column-level check that ensures that there are no more than a maximum percentage of text values that are surrounded by whitespace in a monitored column.



### [text valid country code percent](./column/text/text-valid-country-code-percent.md)
A column-level check that ensures that the percentage of text values that are valid country codes in the monitored column does not fall below set thresholds.



### [text valid currency code percent](./column/text/text-valid-currency-code-percent.md)
A column-level check that ensures that the percentage of text values that are valid currency codes in the monitored column does not fall below set thresholds.






## column-level uniqueness checks
Counts the number or percent of duplicate or unique values in a column.

### [distinct count](./column/uniqueness/distinct-count.md)
A column-level check that ensures that the number of unique values in a column does not fall below the minimum accepted count.



### [distinct percent](./column/uniqueness/distinct-percent.md)
A column-level check that ensures that the percentage of unique values in a column does not fall below the minimum accepted percentage.



### [duplicate count](./column/uniqueness/duplicate-count.md)
A column-level check that ensures that the number of duplicate values in a column does not exceed the maximum accepted count.



### [duplicate percent](./column/uniqueness/duplicate-percent.md)
A column-level check that ensures that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.



### [distinct count anomaly](./column/uniqueness/distinct-count-anomaly.md)
A column-level check that ensures that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.



### [distinct percent anomaly](./column/uniqueness/distinct-percent-anomaly.md)
A column-level check that ensures that the distinct percent value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.



### [distinct count change](./column/uniqueness/distinct-count-change.md)
A column-level check that ensures that the distinct count in a monitored column has changed by a fixed rate since the last readout.



### [distinct count change 1 day](./column/uniqueness/distinct-count-change-1-day.md)
A column-level check that ensures that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.



### [distinct count change 7 days](./column/uniqueness/distinct-count-change-7-days.md)
A column-level check that ensures that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.



### [distinct count change 30 days](./column/uniqueness/distinct-count-change-30-days.md)
A column-level check that ensures that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.



### [distinct percent change](./column/uniqueness/distinct-percent-change.md)
A column-level check that ensures that the distinct percent in a monitored column has changed by a fixed rate since the last readout.



### [distinct percent change 1 day](./column/uniqueness/distinct-percent-change-1-day.md)
A column-level check that ensures that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.



### [distinct percent change 7 days](./column/uniqueness/distinct-percent-change-7-days.md)
A column-level check that ensures that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.



### [distinct percent change 30 days](./column/uniqueness/distinct-percent-change-30-days.md)
A column-level check that ensures that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.







