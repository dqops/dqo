---
title: Data quality checks
---
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



### [data freshness anomaly](./table/timeliness/data-freshness-anomaly.md)
This check calculates the most recent rows value and the current time and detects anomalies in a time series of previous averages.
 The timestamp column that is used for comparison is defined as the timestamp_columns.event_timestamp_column on the table configuration.
 It raises a data quality issue when the mean is in the top *anomaly_percent* percentage of the most outstanding values in the time series.
 This data quality check uses a 90-day time window and requires a history of at least 30 days.



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
This check detects empty or too-small tables. It captures the row count of a tested table.
 This check raises a data quality issue when the row count is below a minimum accepted value.
 The default value of the rule parameter **min_count** is 1 (row), which detects empty tables.
 When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.



### [row count anomaly](./table/volume/row-count-anomaly.md)
This check detects anomalies in the day-to-day changes to the table volume (the row count).
 It captures the row count for each day and compares the row count change (increase or decrease) since the previous day.
 This check raises a data quality issue when the change is in the top **anomaly_percent** percentage of the biggest day-to-day changes.



### [row count change](./table/volume/row-count-change.md)
This check compares the current table volume (the row count) to the last known row count.
 It raises a data quality issue when the change in row count (increase or decrease) exceeds a maximum accepted percentage of change.



### [row count change 1 day](./table/volume/row-count-change-1-day.md)
This check compares the current table volume (the row count) to the row count from the previous day.
 It raises a data quality issue when the change in row count (increase or decrease) since yesterday exceeds a maximum accepted percentage of change.



### [row count change 7 days](./table/volume/row-count-change-7-days.md)
This check compares the current table volume (the row count) to the row count seven days ago.
 This check compares the table volume to a value a week ago to overcome weekly seasonability and to compare Mondays to Mondays, Tuesdays to Tuesdays, etc.
 It raises a data quality issue when the change in row count (increase or decrease) since a week ago exceeds a maximum accepted percentage of change.



### [row count change 30 days](./table/volume/row-count-change-30-days.md)
This check compares the current table volume (the row count) to the row count 30 days ago.
 This check compares the table volume to a month ago value to overcome monthly seasonability.
 It raises a data quality issue when the change in row count (increase or decrease) since a value 30 days ago exceeds a maximum accepted percentage of change.




















































































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



### [text valid country code percent](./column/accepted_values/text-valid-country-code-percent.md)
This check measures the percentage of text values that are valid two-letter country codes.
 It raises a data quality issue when the percentage of valid country codes (excluding null values) falls below a minimum accepted rate.



### [text valid currency code percent](./column/accepted_values/text-valid-currency-code-percent.md)
This check measures the percentage of text values that are valid currency names. It raises a data quality issue when the percentage of valid currency names (excluding null values) falls below a minimum accepted rate.






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
This check calculates a sum of values in a numeric column and detects anomalies in a time series of previous sums.
 It raises a data quality issue when the sum is in the top *anomaly_percent* percentage of the most outstanding values in the time series.
 This data quality check uses a 90-day time window and requires a history of at least 30 days.



### [mean anomaly](./column/anomaly/mean-anomaly.md)
This check calculates a mean (average) of values in a numeric column and detects anomalies in a time series of previous averages.
 It raises a data quality issue when the mean is in the top *anomaly_percent* percentage of the most outstanding values in the time series.
 This data quality check uses a 90-day time window and requires a history of at least 30 days.



### [median anomaly](./column/anomaly/median-anomaly.md)
This check calculates a median of values in a numeric column and detects anomalies in a time series of previous medians.
 It raises a data quality issue when the median is in the top *anomaly_percent* percentage of the most outstanding values in the time series.
 This data quality check uses a 90-day time window and requires a history of at least 30 days.



### [min anomaly](./column/anomaly/min-anomaly.md)
This check finds a minimum value in a numeric column and detects anomalies in a time series of previous minimum values.
 It raises a data quality issue when the current minimum value is in the top *anomaly_percent* percentage of the most outstanding
 values in the time series (it is a new minimum value, far from the previous one).
 This data quality check uses a 90-day time window and requires a history of at least 30 days.



### [max anomaly](./column/anomaly/max-anomaly.md)
This check finds a maximum value in a numeric column and detects anomalies in a time series of previous maximum values.
 It raises a data quality issue when the current maximum value is in the top *anomaly_percent* percentage of the most outstanding
 values in the time series (it is a new maximum value, far from the previous one).
 This data quality check uses a 90-day time window and requires a history of at least 30 days.



### [mean change](./column/anomaly/mean-change.md)
This check detects that the mean (average) of numeric values has changed more than *max_percent* from the last measured mean.



### [mean change 1 day](./column/anomaly/mean-change-1-day.md)
This check detects that the mean (average) of numeric values has changed more than *max_percent* from the mean value measured one day ago (yesterday).



### [mean change 7 days](./column/anomaly/mean-change-7-days.md)
This check detects that the mean (average) value of numeric values has changed more than *max_percent* from the mean value measured seven days ago.
 This check aims to overcome a weekly seasonability and compare Mondays to Mondays, Tuesdays to Tuesdays, etc.



### [mean change 30 days](./column/anomaly/mean-change-30-days.md)
This check detects that the mean (average) of numeric values has changed more than *max_percent* from the mean value measured thirty days ago.
 This check aims to overcome a monthly seasonability and compare a value to a similar value a month ago.



### [median change](./column/anomaly/median-change.md)
This check detects that the median of numeric values has changed more than *max_percent* from the last measured median.



### [median change 1 day](./column/anomaly/median-change-1-day.md)
This check detects that the median of numeric values has changed more than *max_percent* from the median value measured one day ago (yesterday).



### [median change 7 days](./column/anomaly/median-change-7-days.md)
This check detects that the median of numeric values has changed more than *max_percent* from the median value measured seven days ago.
 This check aims to overcome a weekly seasonability and compare Mondays to Mondays, Tuesdays to Tuesdays, etc.



### [median change 30 days](./column/anomaly/median-change-30-days.md)
This check detects that the median of numeric values has changed more than *max_percent* from the median value measured thirty days ago.
 This check aims to overcome a monthly seasonability and compare a value to a similar value a month ago.



### [sum change](./column/anomaly/sum-change.md)
This check detects that the sum of numeric values has changed more than *max_percent* from the last measured sum.



### [sum change 1 day](./column/anomaly/sum-change-1-day.md)
This check detects that the sum of numeric values has changed more than *max_percent* from the sum measured one day ago (yesterday).



### [sum change 7 days](./column/anomaly/sum-change-7-days.md)
This check detects that the sum of numeric values has changed more than *max_percent* from the sum measured seven days ago.
 This check aims to overcome a weekly seasonability and compare Mondays to Mondays, Tuesdays to Tuesdays, etc.



### [sum change 30 days](./column/anomaly/sum-change-30-days.md)
This check detects that the sum of numeric values has changed more than *max_percent* from the sum measured thirty days ago.
 This check aims to overcome a monthly seasonability and compare a value to a similar value a month ago.






## column-level bool checks
Calculates the percentage of data in boolean columns.

### [true percent](./column/bool/true-percent.md)
This check measures the percentage of **true** values in a boolean column. It raises a data quality issue when the measured percentage is outside the accepted range.



### [false percent](./column/bool/false-percent.md)
This check measures the percentage of **false** values in a boolean column. It raises a data quality issue when the measured percentage is outside the accepted range.






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






## column-level conversions checks
Validates that the values in a text column can be parsed and converted to other data types.

### [text parsable to boolean percent](./column/conversions/text-parsable-to-boolean-percent.md)
Verifies that values in a text column are convertible to a boolean value.
 Texts are convertible to a boolean value when they are one of the well-known boolean placeholders: &#x27;0&#x27;, &#x27;1&#x27;, &#x27;true&#x27;, &#x27;false&#x27;, &#x27;yes&#x27;, &#x27;no&#x27;, &#x27;y&#x27;, &#x27;n&#x27;.
 This check measures the percentage of valid values and raises a data quality issue when the percentage of valid values is below an accepted rate.



### [text parsable to integer percent](./column/conversions/text-parsable-to-integer-percent.md)
Verifies that values in a text column can be parsed and converted to an integer type.
 This check measures the percentage of valid values and raises a data quality issue when the percentage of valid values is below an accepted rate.



### [text parsable to float percent](./column/conversions/text-parsable-to-float-percent.md)
Verifies that values in a text column can be parsed and converted to a float (or numeric) type.
 This check measures the percentage of valid values and raises a data quality issue when the percentage of valid values is below an accepted rate.



### [text parsable to date percent](./column/conversions/text-parsable-to-date-percent.md)
Verifies that values in a text column can be parsed and converted to a date type.
 This check measures the percentage of valid values and raises a data quality issue when the percentage of valid values is below an accepted rate.






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
A column-level check that scans all values in a text column and detects the data type of all values in a monitored column. The actual_value returned from the sensor can be one of seven codes: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types.
 The check compares the data type detected in all non-null columns to an expected data type. The rule compares the value using equals and requires values in the range 1..8, which are the codes of detected data types.



### [detected datatype in text changed](./column/datatype/detected-datatype-in-text-changed.md)
A column-level check that scans all values in a text column, finds the right data type and detects when the desired data type changes.
 The actual_value returned from the sensor can be one of seven codes: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types.
 The check compares the data type detected during the current run to the last known data type detected during a previous run.
 For daily monitoring checks, it compares the value to yesterday&#x27;s value (or an earlier date).
 For partitioned checks, it compares the current data type to the data type in the previous daily or monthly partition. The last partition with data is used for comparison.






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
This check detects invalid values that are not present in a dictionary table. The lookup uses an outer join query within the same database.
 This check counts the number of values not found in the dictionary table. It raises a data quality issue when too many missing keys are discovered.



### [lookup key found percent](./column/integrity/lookup-key-found-percent.md)
This check detects invalid values that are not present in a dictionary table. The lookup uses an outer join query within the same database.
 This check measures the percentage of valid keys found in the dictionary table.
 It raises a data quality issue when a percentage of valid keys is below a minimum accepted threshold.






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
This check finds numeric values smaller than the minimum accepted value. It counts the values that are too small.
 This check raises a data quality issue when the count of too small values exceeds the maximum accepted count.



### [number above max value](./column/numeric/number-above-max-value.md)
This check finds numeric values bigger than the maximum accepted value. It counts the values that are too big.
 This check raises a data quality issue when the count of too big values exceeds the maximum accepted count.



### [negative values](./column/numeric/negative-values.md)
This check finds and counts negative values in a numeric column. It raises a data quality issue when the count of negative values is above the maximum accepted count.



### [negative values percent](./column/numeric/negative-values-percent.md)
This check finds negative values in a numeric column. It measures the percentage of negative values and raises a data quality issue
 when the rate of negative values exceeds the maximum accepted percentage.



### [number below min value percent](./column/numeric/number-below-min-value-percent.md)
This check finds numeric values smaller than the minimum accepted value. It measures the percentage of values that are too small.
 This check raises a data quality issue when the percentage of values that are too small exceeds the maximum accepted percentage.



### [number above max value percent](./column/numeric/number-above-max-value-percent.md)
This check finds numeric values bigger than the maximum accepted value. It measures the percentage of values that are too big.
 This check raises a data quality issue when the percentage of values that are too big exceeds the maximum accepted percentage.



### [number in range percent](./column/numeric/number-in-range-percent.md)
This check verifies that values in a numeric column are within an accepted range.
 It measures the percentage of values within the valid range and raises a data quality issue when the rate of valid values is below a minimum accepted percentage.



### [integer in range percent](./column/numeric/integer-in-range-percent.md)
This check verifies that numeric values are within a range of accepted values.
 It measures the percentage of values in the range and raises a data quality issue when the percentage of valid values is below an accepted rate.



### [min in range](./column/numeric/min-in-range.md)
This check finds a minimum value in a numeric column. It verifies that the minimum value is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.



### [max in range](./column/numeric/max-in-range.md)
This check finds a maximum value in a numeric column. It verifies that the maximum value is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.



### [sum in range](./column/numeric/sum-in-range.md)
This check calculates a sum of numeric values. It verifies that the sum is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.



### [mean in range](./column/numeric/mean-in-range.md)
This check calculates a mean (average) value in a numeric column. It verifies that the average value is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.



### [median in range](./column/numeric/median-in-range.md)
This check finds a median value in a numeric column. It verifies that the median value is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.



### [percentile in range](./column/numeric/percentile-in-range.md)
This check finds a requested percentile value of numeric values. The percentile is configured as a value in the range [0, 1]. This check verifies that the given percentile is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.



### [percentile 10 in range](./column/numeric/percentile-10-in-range.md)
This check finds the 10th percentile value in a numeric column. The 10th percentile is a value greater than 10% of the smallest values and smaller than the remaining 90% of other values.
 This check verifies that the 10th percentile is within the range of accepted values and raises a data quality issue when it is not within a valid range.



### [percentile 25 in range](./column/numeric/percentile-25-in-range.md)
This check finds the 25th percentile value in a numeric column. The 10th percentile is a value greater than 25% of the smallest values and smaller than the remaining 75% of other values.
 This check verifies that the 25th percentile is within the range of accepted values and raises a data quality issue when it is not within a valid range.



### [percentile 75 in range](./column/numeric/percentile-75-in-range.md)
This check finds the 75th percentile value in a numeric column. The 75th percentile is a value greater than 75% of the smallest values and smaller than the remaining 25% of other values.
 This check verifies that the 75th percentile is within the range of accepted values and raises a data quality issue when it is not within a valid range.



### [percentile 90 in range](./column/numeric/percentile-90-in-range.md)
This check finds the 90th percentile value in a numeric column. The 90th percentile is a value greater than 90% of the smallest values and smaller than the remaining 10% of other values.
 This check verifies that the 90th percentile is within the range of accepted values and raises a data quality issue when it is not within a valid range.



### [sample stddev in range](./column/numeric/sample-stddev-in-range.md)
This check calculates the standard deviation of numeric values. It verifies that the standard deviation is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.



### [population stddev in range](./column/numeric/population-stddev-in-range.md)
This check calculates the population standard deviation of numeric values. It verifies that the population standard deviation is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.



### [sample variance in range](./column/numeric/sample-variance-in-range.md)
This check calculates a sample variance of numeric values. It verifies that the sample variance is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.



### [population variance in range](./column/numeric/population-variance-in-range.md)
This check calculates a population variance of numeric values. It verifies that the population variance is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.o



### [invalid latitude](./column/numeric/invalid-latitude.md)
This check finds numeric values that are not valid latitude coordinates. A valid latitude coordinate is in the range -90...90. It counts the values outside a valid range for a latitude.
 This check raises a data quality issue when the count of invalid values exceeds the maximum accepted count.



### [valid latitude percent](./column/numeric/valid-latitude-percent.md)
This check verifies that numeric values are valid latitude coordinates.
 A valid latitude coordinate is in the range -90...90. It measures the percentage of values within a valid range for a latitude.
 This check raises a data quality issue when the rate of valid values is below the minimum accepted percentage.



### [invalid longitude](./column/numeric/invalid-longitude.md)
This check finds numeric values that are not valid longitude coordinates. A valid longitude coordinate is in the range -180...180. It counts the values outside a valid range for a longitude.
 This check raises a data quality issue when the count of invalid values exceeds the maximum accepted count.



### [valid longitude percent](./column/numeric/valid-longitude-percent.md)
This check verifies that numeric values are valid longitude coordinates. A valid longitude coordinate is in the range --180...180.
 It measures the percentage of values within a valid range for a longitude.
 This check raises a data quality issue when the rate of valid values is below the minimum accepted percentage.



### [non negative values](./column/numeric/non-negative-values.md)
This check finds and counts non0negative values in a numeric column. It raises a data quality issue when the count of non-negative values is above the maximum accepted count.



### [non negative values percent](./column/numeric/non-negative-values-percent.md)
This check finds non-negative values in a numeric column.
 It measures the percentage of non-negative values and raises a data quality issue when the rate of non-negative values exceeds the maximum accepted percentage.






## column-level patterns checks
Validates if a text column matches predefined patterns (such as an email address) or a custom regular expression.

### [text not matching regex found](./column/patterns/text-not-matching-regex-found.md)
This check validates text values using a pattern defined as a regular expression.
 It counts the number of invalid values and raises a data quality issue when the number exceeds a threshold.



### [texts matching regex percent](./column/patterns/texts-matching-regex-percent.md)
This check validates text values using a pattern defined as a regular expression.
 It measures the percentage of valid values and raises a data quality issue when the rate is below a threshold.



### [invalid email format found](./column/patterns/invalid-email-format-found.md)
This check detects invalid email addresses in text columns using a regular expression.
 It counts the number of invalid emails and raises a data quality issue when the number is above a threshold.



### [invalid email format percent](./column/patterns/invalid-email-format-percent.md)
This check detects invalid email addresses in text columns using a regular expression.
 It calculated the percentage of invalid emails and raises a data quality issue when the percentage is above a threshold.



### [text not matching date pattern found](./column/patterns/text-not-matching-date-pattern-found.md)
This check detects dates in the wrong format inside text columns using a regular expression.
 It counts the number of incorrectly formatted dates and raises a data quality issue when the number exceeds a threshold.



### [text matching date pattern percent](./column/patterns/text-matching-date-pattern-percent.md)
This check validates the date format of dates stored in text columns.
 It measures the percentage of correctly formatted dates and raises a data quality issue when the rate is below a threshold.



### [text matching name pattern percent](./column/patterns/text-matching-name-pattern-percent.md)
This check verifies if values stored in a text column contain only letters and are usable as literal identifiers.
 It measures the percentage of valid literal identifiers and raises a data quality issue when the rate is below a threshold.



### [invalid uuid format found](./column/patterns/invalid-uuid-format-found.md)
This check detects invalid UUID identifiers in text columns using a regular expression.
 It counts the number of invalid UUIDs and raises a data quality issue when the number is above a threshold.



### [valid uuid format percent](./column/patterns/valid-uuid-format-percent.md)
This check validates the format of UUID values in text columns.
 It measures the percentage of valid UUIDs and raises a data quality issue when the rate is below a threshold.



### [invalid ip4 address format found](./column/patterns/invalid-ip4-address-format-found.md)
This check detects invalid IP4 internet addresses in text columns using a regular expression.
 It counts the number of invalid addresses and raises a data quality issue when the number is above a threshold.



### [invalid ip6 address format found](./column/patterns/invalid-ip6-address-format-found.md)
This check detects invalid IP6 internet addresses in text columns using a regular expression.
 It counts the number of invalid addresses and raises a data quality issue when the number is above a threshold.






## column-level pii checks
Checks for the presence of sensitive or personally identifiable information (PII) in a column such as an email, phone, zip code, IP4, and IP6 addresses.

### [contains usa phone percent](./column/pii/contains-usa-phone-percent.md)
This check detects USA phone numbers inside text columns. It measures the percentage of columns containing a phone number and raises a data quality issue when too many rows contain phone numbers.



### [contains email percent](./column/pii/contains-email-percent.md)
This check detects emails inside text columns. It measures the percentage of columns containing an email and raises a data quality issue when too many rows contain emails.



### [contains usa zipcode percent](./column/pii/contains-usa-zipcode-percent.md)
This check detects USA zip code inside text columns. It measures the percentage of columns containing a zip code and raises a data quality issue when too many rows contain zip codes.



### [contains ip4 percent](./column/pii/contains-ip4-percent.md)
This check detects IP4 addresses inside text columns. It measures the percentage of columns containing an IP4 address and raises a data quality issue when too many rows contain IP4 addresses.



### [contains ip6 percent](./column/pii/contains-ip6-percent.md)
This check detects IP6 addresses inside text columns. It measures the percentage of columns containing an IP6 address and raises a data quality issue when too many rows contain IP6 addresses.






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
Validates that the data in a text column has a valid range.

### [text min length](./column/text/text-min-length.md)
This check finds the length of the shortest text in a column. DQOps validates the shortest length using a range rule.
 DQOps raises an issue when the minimum text length is outside a range of accepted values.



### [text max length](./column/text/text-max-length.md)
This check finds the length of the longest text in a column. DQOps validates the maximum length using a range rule.
 DQOps raises an issue when the maximum text length is outside a range of accepted values.



### [text mean length](./column/text/text-mean-length.md)
This check calculates the average text length in a column. DQOps validates the mean length using a range rule.
 DQOps raises an issue when the mean text length is outside a range of accepted values.



### [text length below min length](./column/text/text-length-below-min-length.md)
This check finds texts that are shorter than the minimum accepted text length. It counts the number of texts that are too short and raises a data quality issue when too many invalid texts are found.



### [text length below min length percent](./column/text/text-length-below-min-length-percent.md)
This check finds texts that are shorter than the minimum accepted text length.
 It measures the percentage of too short texts and raises a data quality issue when too many invalid texts are found.



### [text length above max length](./column/text/text-length-above-max-length.md)
This check finds texts that are longer than the maximum accepted text length.
 It counts the number of texts that are too long and raises a data quality issue when too many invalid texts are found.



### [text length above max length percent](./column/text/text-length-above-max-length-percent.md)
This check finds texts that are longer than the maximum accepted text length.
 It measures the percentage of texts that are too long and raises a data quality issue when too many invalid texts are found.



### [text length in range percent](./column/text/text-length-in-range-percent.md)
This check verifies that the minimum and maximum lengths of text values are in the range of accepted values.
 It measures the percentage of texts with a valid length and raises a data quality issue when an insufficient number of texts have a valid length.






## column-level uniqueness checks
Counts the number or percent of duplicate or unique values in a column.

### [distinct count](./column/uniqueness/distinct-count.md)
This check counts distinct values and verifies if the distinct count is within an accepted range. It raises a data quality issue when the distinct count is below or above the accepted range.



### [distinct percent](./column/uniqueness/distinct-percent.md)
This check measures the percentage of distinct values in all non-null values. It verifies that the percentage of distinct values meets a minimum value.
 The default value of 100% distinct values ensures the column has no duplicate values.



### [duplicate count](./column/uniqueness/duplicate-count.md)
This check counts duplicate values. It raises a data quality issue when the number of duplicates is above a minimum accepted value.
 The default configuration detects duplicate values by enforcing that the *min_count* of duplicates is zero.



### [duplicate percent](./column/uniqueness/duplicate-percent.md)
This check measures the percentage of duplicate values in all non-null values. It raises a data quality issue when the percentage of duplicates is above an accepted threshold.
 The default threshold is 0% duplicate values.



### [distinct count anomaly](./column/uniqueness/distinct-count-anomaly.md)
This check monitors the count of distinct values and detects anomalies in the changes of the distinct count. It monitors a 90-day time window.
 The check is configured by setting a desired percentage of anomalies to identify as data quality issues.



### [distinct percent anomaly](./column/uniqueness/distinct-percent-anomaly.md)
This check monitors the percentage of distinct values and detects anomalies in the changes in this percentage. It monitors a 90-day time window.
 The check is configured by setting a desired percentage of anomalies to identify as data quality issues.



### [distinct count change](./column/uniqueness/distinct-count-change.md)
This check monitors the count of distinct values and compares it to the last known value. It raises a data quality issue when the change exceeds an accepted threshold.



### [distinct count change 1 day](./column/uniqueness/distinct-count-change-1-day.md)
This check monitors the count of distinct values and compares it to the measure from the previous day. It raises a data quality issue when the change exceeds an accepted threshold.



### [distinct count change 7 days](./column/uniqueness/distinct-count-change-7-days.md)
This check monitors the count of distinct values and compares it to the measure seven days ago to overcome the weekly seasonability impact.
 It raises a data quality issue when the change exceeds an accepted threshold.



### [distinct count change 30 days](./column/uniqueness/distinct-count-change-30-days.md)
This check monitors the count of distinct values and compares it to the measure thirty days ago to overcome the monthly seasonability impact.
 It raises a data quality issue when the change exceeds an accepted threshold.



### [distinct percent change](./column/uniqueness/distinct-percent-change.md)
This check monitors the percentage of distinct values and compares it to the last known value. It raises a data quality issue when the change exceeds an accepted threshold.



### [distinct percent change 1 day](./column/uniqueness/distinct-percent-change-1-day.md)
This check monitors the percentage of distinct values and compares it to the measure from the previous day. It raises a data quality issue when the change exceeds an accepted threshold.



### [distinct percent change 7 days](./column/uniqueness/distinct-percent-change-7-days.md)
This check monitors the percentage of distinct values and compares it to the measure seven days ago to overcome the weekly seasonability impact.
 It raises a data quality issue when the change exceeds an accepted threshold.



### [distinct percent change 30 days](./column/uniqueness/distinct-percent-change-30-days.md)
This check monitors the percentage of distinct values and compares it to the measure thirty days ago to overcome the monthly seasonability impact.
 It raises a data quality issue when the change exceeds an accepted threshold.






## column-level whitespace checks
Detects text columns that contain blank values, or values that are used as placeholders for missing values: &#x27;n/a&#x27;, &#x27;None&#x27;, etc.

### [empty text found](./column/whitespace/empty-text-found.md)
This check detects empty texts that are not null. Empty texts have a length of zero.
 The database treats them as values different than nulls, and some databases allow the storage of both null and empty values.
 This check counts empty texts and raises a data quality issue when the number of empty values exceeds a *max_count* parameter value.



### [whitespace text found](./column/whitespace/whitespace-text-found.md)
This check detects empty texts containing only spaces and other whitespace characters.
 This check counts whitespace-only texts and raises a data quality issue when their count exceeds a *max_count* parameter value.



### [null placeholder text found](./column/whitespace/null-placeholder-text-found.md)
This check detects text values that are well-known equivalents (placeholders) of a null value, such as *null*, *None*, *n/a*.
 This check counts null placeholder values and raises a data quality issue when their count exceeds a *max_count* parameter value.



### [empty text percent](./column/whitespace/empty-text-percent.md)
This check detects empty texts that are not null. Empty texts have a length of zero.
 This check measures the percentage of empty texts and raises a data quality issue when the rate of empty values exceeds a *max_percent* parameter value.



### [whitespace text percent](./column/whitespace/whitespace-text-percent.md)
This check detects empty texts containing only spaces and other whitespace characters.
 This check measures the percentage of whitespace-only texts and raises a data quality issue when their rate exceeds a *max_percent* parameter value.



### [null placeholder text percent](./column/whitespace/null-placeholder-text-percent.md)
This check detects text values that are well-known equivalents (placeholders) of a null value, such as *null*, *None*, *n/a*.
 This check measures the percentage of null placeholder values and raises a data quality issue when their rate exceeds a *max_percent* parameter value.



### [text surrounded by whitespace found](./column/whitespace/text-surrounded-by-whitespace-found.md)
This check detects text values that contain additional whitespace characters before or after the text.
 This check counts text values surrounded by whitespace characters (on any side) and
 raises a data quality issue when their count exceeds a *max_count* parameter value.
 Whitespace-surrounded texts should be trimmed before loading to another table.



### [text surrounded by whitespace percent](./column/whitespace/text-surrounded-by-whitespace-percent.md)
This check detects text values that contain additional whitespace characters before or after the text.
 This check measures the percentage of text value surrounded by whitespace characters (on any side) and
 raises a data quality issue when their rate exceeds a *max_percent* parameter value.







