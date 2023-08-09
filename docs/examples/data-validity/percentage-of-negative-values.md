# Percentage of negative values

Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.

**PROBLEM**

[Countries in the world by population](https://www.worldometers.info/world-population/population-by-country/) provides an analysis of national health on a state-by-state basis by evaluating a historical and comprehensive set of health, environmental and socioeconomic data to determine national health benchmarks and state rankings.

Population rise is currently a major subject of discussion nowadays. Every day, there is higher birth rate recorded as compare to death rate which is quite alarming for the world.
Below is the complete data about the world population , country by country (235 countries).

Worldometer was voted as one of the best free reference websites by the American Library Association (ALA), the oldest and largest library association in the world. 
Worldometer is a provider of global COVID-19 statistics for many caring people around the world. 
Worldometr's data is also trusted and used by the UK Government, Johns Hopkins CSSE, the Government of Thailand, the Government of Pakistan, the Government of Sri Lanka, Government of Vietnam and many others.

We want to verify the percentage of negative values on `Migrants__net_` column.

**SOLUTION**

We will verify the data using profiling [negative_percent](../../checks/column/numeric/negative-percent.md) column check.
Our goal is to verify that the percent of negative values in the `Migrants__net_` column does not exceed the set thresholds.

In this example, we will set three maximum percSentage thresholds levels for the check:

- warning: 45.0
- error: 55.0
- fatal: 60.0

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of negative values exceed 45.0, a warning alert will be triggered.

## Data structure

The following is a fragment of the World population dataset. Some columns were omitted for clarity.  
The `Migrants__net` column of interest contains negative values.

| Country__or_dependency_ | Population__2022_ | Yearly_change | Net_change | Density__P_Km___ | Land_Area__Km___ | Migrants__net_ |
|:------------------------|:------------------|:--------------|:-----------|:-----------------|:-----------------|:---------------|
| Mali                    | 20250833          | 0.0302        | 592802     | 17               | 1220190          | **-40000**     |
| DR Congo                | 89561403          | 0.0319        | 2770836    | 40               | 2267050          | **23861**      |
| Uganda                  | 45741007          | 0.0332        | 1471413    | 229              | 199810           | **168694**     |
| Angola                  | 32866272          | 0.0327        | 1040977    | 26               | 1246700          | **6413**       |
| Chad                    | 16425864          | 0.03          | 478988     | 13               | 1259200          | **2000**       |
| Somalia                 | 15893222          | 0.0292        | 450317     | 25               | 627340           | **-40000**     |
| Burundi                 | 11890784          | 0.0312        | 360204     | 463              | 25680            | **2001**       |
| Nigeria                 | 206139589         | 0.0258        | 5175990    | 226              | 910770           | **-60000**     |
| Tanzania                | 59734218          | 0.0298        | 1728755    | 67               | 885800           | **-40076**     |

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum percentage thresholds levels for the check:

- warning: 45.0
- error: 55.0
- fatal: 60.0

The highlighted fragments in the YAML file below represent the segment where the profiling `negative_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="32-50"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    Country__or_dependency_:
      type_snapshot:
        column_type: STRING
        nullable: true
    Population__2022_:
      type_snapshot:
        column_type: INT64
        nullable: true
    Yearly_change:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    Net_change:
      type_snapshot:
        column_type: INT64
        nullable: true
    Density__P_Km___:
      type_snapshot:
        column_type: INT64
        nullable: true
    Land_Area__Km___:
      type_snapshot:
        column_type: INT64
        nullable: true
    Migrants__net_:
      type_snapshot:
        column_type: INT64
        nullable: true
      profiling_checks:
        numeric:
          profile_negative_percent:
            comments:
            - date: 2023-05-16T08:44:53.730+00:00
              comment_by: user
              comment: "\"In this exmple, values in the `Migrants__net_` column are\
                \ verified whether the percentage of negative values does not exceed\
                \ the set thresholds.\""
            warning:
              max_percent: 45.0
            error:
              max_percent: 55.0
            fatal:
              max_percent: 60.0
```
## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-negative-percent-check.png)

1. Go to **Profiling** section.

2. Select the table or column mentioned in the example description from the tree view on the left.

3. Select **Advanced Profiling** tab.

4. Run the enabled check using the **Run check** button.
   ![Run check](https://dqops.com/docs/images/examples/negative-percent-run-check.png)

5. Review the results by opening the **Check details** button.
   ![Check details](https://dqops.com/docs/images/examples/negative-percent-check-details.png)

6. You should see the results as the one below.
   The actual value in this example is 48, which is above the maximum threshold level set in the warning (45.0%).
   The check gives a warning result (notice the yellow square on the left of the name of the check).

   ![Negative-percent check results](https://dqops.com/docs/images/examples/negative-percent-check-results.png)

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
The percentage of negative values in the `Migrants__net_` column is above 45.0 and the check raised warning.

```
Check evaluation summary per table:
+----------------+-----------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection      |Table                                          |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+----------------+-----------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|negative_percent|kaggle_worldpopulation.world_population_dataset|1     |1             |1            |1       |0     |0           |0               |
+----------------+-----------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:
```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection negative_percent (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table.`Migrants__net_` < 0 THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `dqo-ai-testing`.`kaggle_worldpopulation`.`world_population_dataset` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 48.08510638297872, which is above the maximum 
threshold level set in the warning (45.0).

```
**************************************************
Finished executing a sensor for a check negative_percent on the table kaggle_worldpopulation.world_population_dataset using a sensor definition column/numeric/negative_percent, sensor result count: 1

Results returned by the sensor:
+-----------------+------------------------+------------------------+
|actual_value     |time_period             |time_period_utc         |
+-----------------+------------------------+------------------------+
|48.08510638297872|2023-05-16T08:45:11.722Z|2023-05-16T08:45:11.722Z|
+-----------------+------------------------+------------------------+
**************************************************
```