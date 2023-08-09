# Percentage of valid USA zipcodes

Verifies that the percentage of valid USA zip code in a column does not fall below the minimum accepted percentage.

**PROBLEM**

[Austin-311-Public-Data](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he) provides the residents of Austin with a simple single point of contact for every city department.

What started as police non-emergency line for the City of Austin has become a robust Citywide Information Center
where ambassadors are available to answer residents’ concerns 24 hours a day, 7 days a week, and 365 days a year.

The `incident_zip` column contains USA zipcode data. We want to verify the percent of valid USA zipcode on `incident_zip` column.

**SOLUTION**

We will verify the data of `bigquery-public-data.austin_311.311_service_requests` using profiling
[valid_usa_zipcode_percent](../../checks/column/pii/valid-usa-zipcode-percent.md) column check.
Our goal is to verify if the percentage of valid USA zipcode values in the `incident_zip` column does not fall below the setup thresholds.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of zipcode values falls below 99.0%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.austin_311.311_service_requests` dataset. Some columns were omitted for clarity.  
The `incident_zip` column of interest contains valid USA zipcode values.

| city   | incident_zip | country | state_plane_x_coordinate | state_plane_y_coordinate | latitude    |
|:-------|:-------------|:--------|:-------------------------|:-------------------------|:------------|
|        | **78613**    |         |                          |                          |             |
|        | **78664**    |         |                          |                          |             |
|        | **78729**    |         |                          |                          |             |
| austin | **78746**    |         | 0.0                      | 0                        | 3.442386682 |
|        | **78701**    |         |                          |                          |             |
|        | **78739**    |         |                          |                          |             |
|        | **78751**    |         |                          |                          |             |
|        | **78733**    |         |                          |                          |             |
|        | **78786**    |         |                          |                          |             |

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the profiling `valid_usa_zipcode_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="8-26"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    incident_zip:
      type_snapshot:
        column_type: INT64
        nullable: true
      profiling_checks:
        pii:
          profile_valid_usa_zipcode_percent:
            comments:
              - date: 2023-04-14T09:06:29.764+00:00
                comment_by: user
                comment: In this example, values in "incident_zip" column are verified
                  whether the percentage of USA zip-code values reaches the indicated
                  thresholds.
            warning:
              min_percent: 99.0
            error:
              min_percent: 98.0
            fatal:
              min_percent: 95.0
    county:
      type_snapshot:
        column_type: STRING
        nullable: true
    state_plane_x_coordinate:
      type_snapshot:
        column_type: STRING
        nullable: true
    state_plane_y_coordinate:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    latitude:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
```
## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-valid-usa-zipcode-percent-check.png)

1. Go to **Profiling** section.

2. Select the table or column mentioned in the example description from the tree view on the left.

3. Select **Advanced Profiling** tab.

4. Run the enabled check using the **Run check** button.
   ![Run check](https://dqops.com/docs/images/examples/valid-usa-zipcode-percent-run-check.png)

5. Review the results by opening the **Check details** button.
   ![Check details](https://dqops.com/docs/images/examples/valid-usa-zipcode-percent-check-details.png)

6. You should see the results as the one below.
   The actual value in this example is 98, which is below the minimum threshold level set in the warning (99.0%).
   The check gives a warning (notice the yellow square on the left of the name of the check).

   ![Valid-usa-zipcode-percent check results](https://dqops.com/docs/images/examples/valid-usa-zipcode-percent-check-results.png)

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
The percent of the valid USA zipcode values in the `incident_zip` column is below 99.0% and the check raised a warning.
```
Check evaluation summary per table:
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection|Table                          |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|austin_311|austin_311.311_service_requests|1     |1             |1            |1       |0     |0           |0               |
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection austin_311 (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN REGEXP_CONTAINS(
                    CAST(analyzed_table.`incident_zip` AS STRING),
                    r"^[0-9]{5}(?:-[0-9]{4})?$"
                ) THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value in this example is 98.89%, which is below the minimal
threshold level set in the warning (99.0%).

```
**************************************************
Finished executing a sensor for a check valid_usa_zipcode_percent on the table austin_311.311_service_requests using a sensor definition column/pii/valid_usa_zipcode_percent, sensor result count: 1

Results returned by the sensor:
+----------------+------------------------+------------------------+
|actual_value    |time_period             |time_period_utc         |
+----------------+------------------------+------------------------+
|98.8902793912375|2023-04-25T13:28:04.147Z|2023-04-25T13:28:04.147Z|
+----------------+------------------------+------------------------+
**************************************************
```