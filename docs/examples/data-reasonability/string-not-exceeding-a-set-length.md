# A string not exceeding a set length 

The check verifies that the length of the string does not exceed the indicated value.

**PROBLEM**

[America’s Health Rankings](https://www.americashealthrankings.org/about/methodology/our-reports) provides an analysis of national health on a state-by-state basis by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

The platform analyzes more than 340 measures of behaviors, social and economic factors, physical environment and clinical care data.
Data is based on public-use data sets, such as the U.S. Census and the Centers for Disease Control and Prevention’s Behavioral Risk Factor Surveillance System (BRFSS),
the world’s largest, annual population-based telephone survey of over 400,000 people.

The `measure_name` contains measure name data. We want to verify that the length of the string values in this column does not exceed 30.

**SOLUTION**

We will verify the data of `bigquery-public-data.america_health_rankings.ahr` using profiling
[string_max_length](../../checks/column/strings/string-max-length.md) column check.
Our goal is to verify if the number of valid length values on `measure_name` column does not exceed the setup thresholds.

In this example, we will set one maximum thresholds level for the check:

- warning: 30.0

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the string length exceed 30.0, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.america_health_rankings.ahr` dataset. Some columns were omitted for clarity.  
The `measure_name` column of interest contains values that shouldn't exceed the length indicated thresholds.

| edition | report_type             | measure_name    | state_name    | subpopulation |
|:--------|:------------------------|:----------------|:--------------|:--------------|
| 2021    | 2021 Health Disparities | **Able-Bodied** | California    |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Colorado      |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Hawaii        |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Kentucky      |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Maryland      |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | New Jersey    |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Utah          |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | West Virginia |               |
| 2021    | 2021 Health Disparities | **Able-Bodied** | Arkansas      | Female        |

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum number thresholds levels for the check:

- warning: 30.0

The highlighted fragments in the YAML file below represent the segment where the profiling `string_max_length` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="22-29"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    edition:
      type_snapshot:
        column_type: INT64
        nullable: true
    report_type:
      type_snapshot:
        column_type: STRING
        nullable: true
    measure_name:
      type_snapshot:
        column_type: STRING
        nullable: true
      profiling_checks:
        strings:
          profile_string_max_length:
            comments:
            - date: 2023-04-14T09:11:51.993+00:00
              comment_by: user
              comment: "In this example, values in \"measure_name\" column are verified\
                \ whether the length of values does not exceed the indicated threshold."
            error:
              max_value: 30.0
    state_name:
      type_snapshot:
        column_type: STRING
        nullable: true
    subpopulation:
      type_snapshot:
        column_type: STRING
        nullable: true
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
```
## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-string-max-length-check.png)

1. Go to **Profiling** section.

2. Select the table or column mentioned in the example description from the tree view on the left.

3. Select **Profiling Checks** tab.

4. Run the enabled check using the **Run check** button.
   ![Run check](https://dqops.com/docs/images/examples/string-max-length-run-check.png)

5. Review the results by opening the **Check details** button.
   ![Check details](https://dqops.com/docs/images/examples/string-max-length-check-details.png)

6. You should see the results as the one below.
   The actual value in this example is 31, which is above the maximum threshold level set in the eroor (30).
   The check gives an error(notice the orange square on the left of the name of the check).

   ![String-max-length check results](https://dqops.com/docs/images/examples/string-max-length-check-results.png)

7. After executing the checks, synchronize the results with your DQO cloud account sing the **Synchronize** button
   located in the upper right corner of the graphical interface.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. Below you can see
   the results displayed on the Affected tables per KPI dashboard showing results by issues per connection, issues per schema, issues per quality dimension and issues per check category.

   ![String-max-length check results on affected tables per KPI dashboard](https://dqops.com/docs/images/examples/string-max-length-check-results-on-affected-tables-per-KPI-dashboard.png)

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
The number of the valid string length in the `measure_name` column is above 30.0 and the check raised an error.
```
Check evaluation summary per table:
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection             |Table                      |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-----------------------+---------------------------+------+--------------+-------------+--------+------+------------+----------------+
|america_health_rankings|america_health_rankings.ahr|1     |1             |0            |0       |1     |0           |0               |
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
    MAX(
        LENGTH(analyzed_table.`measure_name`)
    ) AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`america_health_rankings`.`ahr` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value in this example is 31, which is above the maximum
threshold level set in the error (30.0).

```
**************************************************
Finished executing a sensor for a check string_max_length on the table america_health_rankings.ahr
using a sensor definition column/strings/string_max_length, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|31          |2023-05-09T09:19:39.470Z|2023-05-09T09:19:39.470Z|
+------------+------------------------+---------------
```