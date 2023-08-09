# Percentage of strings matching date regex

Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis
by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

The `source_date` column contains non-standard date format. We want to verify the percent of values matches the indicated by the user date format on `source_date` column.

**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using profiling
[string_match_date_regex_percent](../../checks/column/strings/string-match-date-regex-percent.md) column check.
Our goal is to verify if the percentage of values matches the indicated by the user date format on `source_date` column does not fall below the setup thresholds.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of data falls below 99.0%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.  
The `source_date` column of interest contains non-standard date format, in this case this is `YYYY-MM-DD`.

| value | lower_ci | upper_ci | source                                             | source_date   |
|:------|:---------|:---------|:---------------------------------------------------|:--------------|
| 87    | 87       | 87       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 87    | 87       | 87       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 87    | 86       | 87       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 79    | 79       | 79       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 87    | 86       | 87       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 87    | 87       | 88       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 88    | 88       | 88       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 77    | 76       | 77       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |
| 78    | 78       | 79       | U.S. Census Bureau, American Community Survey PUMS | **2015-2019** |

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the profiling `string_match_date_regex_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="25-46"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    value:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    lower_ci:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    upper_ci:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    source:
      type_snapshot:
        column_type: STRING
        nullable: true
    source_date:
      type_snapshot:
        column_type: STRING
        nullable: true
      profiling_checks:
        strings:
          profile_string_match_date_regex_percent:
            comments:
            - date: 2023-04-26T11:00:58.444+00:00
              comment_by: user
              comment: "Values in range integers percent-to check - In this example,\
                \ values in \"source_date\" column are verified whether the percentage\
                \ of values in the indicated format (YYYY-MM-DD) reaches the indicated\
                \ thresholds."
            parameters:
              date_formats: YYYY-MM-DD
            warning:
              min_percent: 99.0
            error:
              min_percent: 98.0
            fatal:
              min_percent: 95.0
```
## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-string-match-date-regex-percent-check.png)

1. Go to **Profiling** section.

2. Select the table or column mentioned in the example description from the tree view on the left.

3. Select **Advanced Profiling** tab.

4. Run the enabled check using the **Run check** button.
   ![Run check](https://dqops.com/docs/images/examples/string-match-date-regex-percent-run-check.png)

5. Review the results by opening the **Check details** button.
   ![Check details](https://dqops.com/docs/images/examples/string-match-date-regex-percent-check-details.png)

6. You should see the results as the one below.
   The actual value in this example is 0, which is below the minimum threshold level set in the warning (99.0%).
   The check gives a fatal error (notice the red square on the left of the name of the check).

   ![String-match-date-regex-percent check results](https://dqops.com/docs/images/examples/string-match-date-regex-percent-check-results.png)

7. After executing the checks, synchronize the results with your DQO cloud account sing the **Synchronize** button
   located in the upper right corner of the graphical interface.

8. You can now [review the results on the data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md) as described in Working with DQO section.

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The percent of valid date formats in the `source_date` column is below the 95% and the check raised the Fatal error.

```

Check evaluation summary per table:
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection             |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|america_health_rankings|america_health_rankings.ahr|1     |1             |0            |0       |0     |1           |0               |
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection america_health_rankings (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(analyzed_table.`source_date`) = 0 THEN NULL
        ELSE 100.0 * SUM(
            CASE
                WHEN SAFE.PARSE_DATE('%Y-%m-%d', analyzed_table.`source_date`) IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value in this example is 0.0%, which is below the minimum
threshold level set in the Fatal error (95.0%).
```
**************************************************
Finished executing a sensor for a check string_match_date_regex_percent on the table america_health_rankings.ahr using a sensor definition column/strings/string_match_date_regex_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|0.0         |2023-04-26T11:01:24.538Z|2023-04-26T11:01:24.538Z|
+------------+------------------------+------------------------+
**************************************************
```