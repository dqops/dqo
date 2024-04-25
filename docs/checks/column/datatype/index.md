---
title: column level datatype data quality checks
---
# column level datatype data quality checks

This is a list of datatype column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level datatype checks
Analyzes all values in a text column to detect if all values can be safely parsed to numeric, boolean, date or timestamp data types. Used to analyze tables in the landing zone.

### [detected datatype in text](./detected-datatype-in-text.md)
A column-level check that scans all values in a text column and detects the data type of all values in a monitored column. The actual_value returned from the sensor can be one of seven codes: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types.
 The check compares the data type detected in all non-null columns to an expected data type. The rule compares the value using equals and requires values in the range 1..8, which are the codes of detected data types.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_detected_datatype_in_text`</span>](./detected-datatype-in-text.md#profile-detected-datatype-in-text)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_detected_datatype_in_text`</span>](./detected-datatype-in-text.md#daily-detected-datatype-in-text)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_detected_datatype_in_text`</span>](./detected-datatype-in-text.md#monthly-detected-datatype-in-text)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_detected_datatype_in_text`</span>](./detected-datatype-in-text.md#daily-partition-detected-datatype-in-text)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_detected_datatype_in_text`</span>](./detected-datatype-in-text.md#monthly-partition-detected-datatype-in-text)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [detected datatype in text changed](./detected-datatype-in-text-changed.md)
A column-level check that scans all values in a text column, finds the right data type and detects when the desired data type changes.
 The actual_value returned from the sensor can be one of seven codes: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types.
 The check compares the data type detected during the current run to the last known data type detected during a previous run.
 For daily monitoring checks, it compares the value to yesterday&#x27;s value (or an earlier date).
 For partitioned checks, it compares the current data type to the data type in the previous daily or monthly partition. The last partition with data is used for comparison.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_detected_datatype_in_text_changed`</span>](./detected-datatype-in-text-changed.md#profile-detected-datatype-in-text-changed)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_detected_datatype_in_text_changed`</span>](./detected-datatype-in-text-changed.md#daily-detected-datatype-in-text-changed)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_detected_datatype_in_text_changed`</span>](./detected-datatype-in-text-changed.md#monthly-detected-datatype-in-text-changed)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_detected_datatype_in_text_changed`</span>](./detected-datatype-in-text-changed.md#daily-partition-detected-datatype-in-text-changed)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_detected_datatype_in_text_changed`</span>](./detected-datatype-in-text-changed.md#monthly-partition-detected-datatype-in-text-changed)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|







