# column level bool data quality checks

This is a list of bool column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **bool**
Calculates the percentage of data in boolean columns.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_true_percent`</span>](./true-percent.md#profile-true-percent)|profiling|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_true_percent`</span>](./true-percent.md#daily-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_true_percent`</span>](./true-percent.md#monthly-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_true_percent`</span>](./true-percent.md#daily-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_true_percent`</span>](./true-percent.md#monthly-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_false_percent`</span>](./false-percent.md#profile-false-percent)|profiling|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_false_percent`</span>](./false-percent.md#daily-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_false_percent`</span>](./false-percent.md#monthly-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_false_percent`</span>](./false-percent.md#daily-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_false_percent`</span>](./false-percent.md#monthly-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|







