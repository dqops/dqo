# Checks/column/datatype

This is a list of datatype column data quality checks supported by DQOps and a brief description of what they do.





## **datatype**


| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_detected_datatype_in_text](./detected-datatype-in-text.md#profile-detected-datatype-in-text)|profiling|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.|standard|
|[daily_detected_datatype_in_text](./detected-datatype-in-text.md#daily-detected-datatype-in-text)|monitoring|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_detected_datatype_in_text](./detected-datatype-in-text.md#monthly-detected-datatype-in-text)|monitoring|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_detected_datatype_in_text](./detected-datatype-in-text.md#daily-partition-detected-datatype-in-text)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_detected_datatype_in_text](./detected-datatype-in-text.md#monthly-partition-detected-datatype-in-text)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_detected_datatype_in_text_changed](./detected-datatype-in-text-changed.md#profile-detected-datatype-in-text-changed)|profiling|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|standard|
|[daily_detected_datatype_in_text_changed](./detected-datatype-in-text-changed.md#daily-detected-datatype-in-text-changed)|monitoring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_detected_datatype_in_text_changed](./detected-datatype-in-text-changed.md#monthly-detected-datatype-in-text-changed)|monitoring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[daily_partition_detected_datatype_in_text_changed](./detected-datatype-in-text-changed.md#daily-partition-detected-datatype-in-text-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_detected_datatype_in_text_changed](./detected-datatype-in-text-changed.md#monthly-partition-detected-datatype-in-text-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each monthly partition.|standard|







