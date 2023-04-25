# Bool true percent

In this example, we will check the data of `bigquery-public-data.census_utility.mtfcc_feature_class_codes` using profiling
[bool_true_percent](../checks/column/bool/true-percent.md) column check.
Our goal is to set up a reasonableness check on `areal` column to verify how many percent of data are true.

## Data structure

The following is a fragment of the `bigquery-public-data.census_utility.mtfcc_feature_class_codes` dataset. Some columns were omitted for clarity.  
The `areal` column of interest contains both TRUE and FALSE values.

| feature_class_code | feature_class                           | superclass                         | point | linear | areal     |
|:-------------------|:----------------------------------------|:-----------------------------------|:------|:-------|:----------|
| H2030              | Lake/Pond                               | Hydrographic Features              | FALSE | FALSE  | **TRUE**  |
| S1720              | Stairway                                | Road/Path Features                 | FALSE | TRUE   | **FALSE** |
| G5020              | Census Tract                            | Tabulation Area                    | FALSE | FALSE  | **TRUE**  |
| K2183              | Tribal Park, Forest, or Recreation Area | Park                               | TRUE  | FALSE  | **TRUE**  |
| G4020              | County or Equivalent Feature            | Tabulation Area                    | FALSE | FALSE  | **TRUE**  |
| C3081              | Locality Point                          | Miscellaneous Topographic Features | TRUE  | FALSE  | **FALSE** |
| K2540              | University or College                   | Other Workplace                    | TRUE  | FALSE  | **TRUE**  |
| G5410              | Secondary School District               | Tabulation Area                    | FALSE | FALSE  | **TRUE**  |
| H3020              | Canal, Ditch, or Aqueduct               | Hydrographic Features              | FALSE | TRUE   | **TRUE**  |


## YAML configuration file

The YAML configuration file stores both the table details and checks configurations. 

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the profiling `bool_true_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

```yaml hl_lines="33-41"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    feature_class_code:
      type_snapshot:
        column_type: STRING
        nullable: true
    feature_class:
      type_snapshot:
        column_type: STRING
        nullable: true
    superclass:
      type_snapshot:
        column_type: STRING
        nullable: true
    point:
      type_snapshot:
        column_type: BOOL
        nullable: true
    linear:
      type_snapshot:
        column_type: BOOL
        nullable: true
    areal:
      type_snapshot:
        column_type: BOOL
        nullable: true
      profiling_checks:
        bool:
          true_percent:
            warning:
              min_percent: 99.0
            error:
              min_percent: 98.0
            fatal:
              min_percent: 95.0
      comments:
      - date: 2023-04-25T07:08:49.489+00:00
        comment_by: user
        comment: "In this example, values in \"areal\" column are verified whether\
          \ the percentage of true values reaches the indicated thresholds."
    description:
      type_snapshot:
        column_type: STRING
        nullable: true
```

## Running the checks in the example and evaluating the results

The detailed explanation of how to run the example is described [here](../#running-the-examples). 

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```

You should see the results as the one below. 
The percentage of true values in the `areal` column is below the 95% and the check raised the Fatal error. 

```
Check evaluation summary per table:
+--------------+----------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection    |Table                                   |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+--------------+----------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|census_utility|census_utility.mtfcc_feature_class_codes|1     |1             |0            |0       |0     |1           |0               |
+--------------+----------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```

For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the 
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection census_utility (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table.`areal`
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`census_utility`.`mtfcc_feature_class_codes` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 71%, what is below minimal 
threshold level set in the fatal error (95%).

```
**************************************************
Finished executing a sensor for a check true_percent on the table census_utility.mtfcc_feature_class_codes using 
a sensor definition column/bool/true_percent, sensor result count: 1

Results returned by the sensor:
+-----------------+------------------------+------------------------+
|actual_value     |time_period             |time_period_utc         |
+-----------------+------------------------+------------------------+
|71.00591715976331|2023-04-24T12:50:46.623Z|2023-04-24T12:50:46.623Z|
+-----------------+------------------------+------------------------+
**************************************************
```