# column level accuracy data quality checks

This is a list of accuracy column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level accuracy checks


### [total sum match percent](./total-sum-match-percent.md)
A column-level check that ensures that the difference between the sum of all values in the tested column and the sum of values in another column in a referenced table is below a maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_total_sum_match_percent`</span>](./total-sum-match-percent.md#profile-total-sum-match-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that percentage of the difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_total_sum_match_percent`</span>](./total-sum-match-percent.md#daily-total-sum-match-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_total_sum_match_percent`</span>](./total-sum-match-percent.md#monthly-total-sum-match-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|



### [total min match percent](./total-min-match-percent.md)
A column-level check that ensures that the difference between the minimum value in the tested column and the minimum value in another column in a referenced table is below a maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_total_min_match_percent`</span>](./total-min-match-percent.md#profile-total-min-match-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_total_min_match_percent`</span>](./total-min-match-percent.md#daily-total-min-match-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_total_min_match_percent`</span>](./total-min-match-percent.md#monthly-total-min-match-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|



### [total max match percent](./total-max-match-percent.md)
A column-level check that ensures that the difference between the maximum value in the tested column and the maximum value in another column in a referenced table is below a maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_total_max_match_percent`</span>](./total-max-match-percent.md#profile-total-max-match-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_total_max_match_percent`</span>](./total-max-match-percent.md#daily-total-max-match-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_total_max_match_percent`</span>](./total-max-match-percent.md#monthly-total-max-match-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|



### [total average match percent](./total-average-match-percent.md)
A column-level check that ensures that the difference between the average value in the tested column and the average value of another column in the referenced table is below the maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_total_average_match_percent`</span>](./total-average-match-percent.md#profile-total-average-match-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_total_average_match_percent`</span>](./total-average-match-percent.md#daily-total-average-match-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_total_average_match_percent`</span>](./total-average-match-percent.md#monthly-total-average-match-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|



### [total not null count match percent](./total-not-null-count-match-percent.md)
A column-level check that ensures that the difference between the count of null values in the tested column and the count of null values in another column in a referenced table is below a maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_total_not_null_count_match_percent`</span>](./total-not-null-count-match-percent.md#profile-total-not-null-count-match-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_total_not_null_count_match_percent`</span>](./total-not-null-count-match-percent.md#daily-total-not-null-count-match-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_total_not_null_count_match_percent`</span>](./total-not-null-count-match-percent.md#monthly-total-not-null-count-match-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|







