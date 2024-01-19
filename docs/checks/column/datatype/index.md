# column level datatype data quality checks

This is a list of datatype column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **datatype**
Analyzes all values in a text column to detect if all values could be safely parsed to numeric, boolean, date or timestamp data types. Used to analyze tables in the landing zone.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_detected_datatype_in_text`</span>](./detected-datatype-in-text.md#profile-detected-datatype-in-text)|profiling|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.|**|
|[<span class="no-wrap-code">`daily_detected_datatype_in_text`</span>](./detected-datatype-in-text.md#daily-detected-datatype-in-text)|monitoring|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_detected_datatype_in_text`</span>](./detected-datatype-in-text.md#monthly-detected-datatype-in-text)|monitoring|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_detected_datatype_in_text`</span>](./detected-datatype-in-text.md#daily-partition-detected-datatype-in-text)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_detected_datatype_in_text`</span>](./detected-datatype-in-text.md#monthly-partition-detected-datatype-in-text)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_detected_datatype_in_text_changed`</span>](./detected-datatype-in-text-changed.md#profile-detected-datatype-in-text-changed)|profiling|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|**|
|[<span class="no-wrap-code">`daily_detected_datatype_in_text_changed`</span>](./detected-datatype-in-text-changed.md#daily-detected-datatype-in-text-changed)|monitoring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_detected_datatype_in_text_changed`</span>](./detected-datatype-in-text-changed.md#monthly-detected-datatype-in-text-changed)|monitoring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_detected_datatype_in_text_changed`</span>](./detected-datatype-in-text-changed.md#daily-partition-detected-datatype-in-text-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_detected_datatype_in_text_changed`</span>](./detected-datatype-in-text-changed.md#monthly-partition-detected-datatype-in-text-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores a separate data quality check result for each monthly partition.|**|







