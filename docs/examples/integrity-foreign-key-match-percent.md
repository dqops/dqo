# Foreign key match percent

In this example we will check the data of `bigquery-public-data.census_utility.fips_codes_all` using `foreign_key_match_percent` check.
Our goal is to set up an integrity check on `state_fips_code` column in order to check how many percent of data in this column
matches the values in the indicated by user column in foreign table in this case this is `bigquery-public-data.census_utility.fips_codes_states` column `state_fips_code`.

## Data structure

The following is a fragment of the `bigquery-public-data.austin_311.311_service_requests` dataset. Some columns were omitted for clarity.  
The `close_date` column of interest contains different dates.

| complaint_description                               | source            | status                         | status_change_date  | created_date        | close_date              |
|:----------------------------------------------------|:------------------|:-------------------------------|:--------------------|:--------------------|:------------------------|
| Pothole Repair                                      | Spot311 Interface | Resolved                       | 6/11/2022 20:39:42  | 6/11/2022 9:22:29   | **6/11/2022 20:39:42**  |
| Pothole Repair                                      | Web               | Resolved                       | 7/25/2022 10:52:08  | 7/22/2022 19:51:14  | **7/25/2022 10:52:08**  |
| AE - Key Accounts                                   | Phone             | Open                           | 4/15/2023 7:36:39   | 4/15/2023 7:36:39   |                         |
| Community Connections - Coronavirus                 | Phone             | Open                           | 7/3/2020 14:42:32   | 7/3/2020 14:42:32   |                         |
| AE - Key Accounts                                   | Phone             | TO BE DELETED                  | 2/1/2023 22:45:46   | 2/1/2023 22:32:02   | **2/1/2023 22:45:46**   |
| Street Light Issue- Multiple poles/multiple streets | Phone             | Duplicate (closed)             | 2/15/2019 7:29:04   | 2/6/2019 0:27:37    |                         |
| Parking Machine Issue                               | Phone             | Closed -Incomplete Information | 9/4/2018 8:44:20    | 9/1/2018 15:09:14   | **9/4/2018 8:44:20**    |
| Sign - Street Name                                  | Web               | Resolved                       | 12/27/2018 13:40:25 | 12/18/2018 15:31:39 | **12/27/2018 13:40:25** |
| Curb/Gutter Repair                                  | Phone             | Resolved                       | 11/15/2017 12:35:14 | 11/14/2017 13:17:54 | **11/15/2017 12:35:14** |
| Pothole Repair                                      | Spot311 Interface | Resolved                       | 2/5/2018 1:04:18    | 2/4/2018 10:35:02   | **2/5/2018 1:04:18**    |


## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 1%
- error: 2%
- fatal: 5%

And the following parameters:

- min_value: 2017-12-31
- max_value: 2018-12-30

The highlighted fragments in the YAML file below represent the segment where the profiling `datetime_value_in_range_date_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../dqo-concepts/checks/index.md).

```yaml hl_lines="41-58"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    unique_key:
      type_snapshot:
        column_type: STRING
        nullable: true
    complaint_description:
      type_snapshot:
        column_type: STRING
        nullable: true
    source:
      type_snapshot:
        column_type: STRING
        nullable: true
    status:
      type_snapshot:
        column_type: STRING
        nullable: true
    status_change_date:
      type_snapshot:
        column_type: TIMESTAMP
        nullable: true
    created_date:
      type_snapshot:
        column_type: TIMESTAMP
        nullable: true
    last_update_date:
      type_snapshot:
        column_type: TIMESTAMP
        nullable: true
    close_date:
      type_snapshot:
        column_type: TIMESTAMP
        nullable: true
      profiling_checks:
        datetime:
          datetime_value_in_range_date_percent:
            comments:
              - date: 2023-04-14T08:56:30.517+00:00
                comment_by: user
                comment: In this example, values in "close_date" column are verified
                  whether the percentage of values within the indicated range (2018-01-01
                  - 2018-12-30) does not exceed the indicated thresholds.
            parameters:
              min_value: 2017-12-31
              max_value: 2018-12-30
            warning:
              max_percent: 1.0
            error:
              max_percent: 2.0
            fatal:
              max_percent: 5.0
    incident_address:
      type_snapshot:
        column_type: STRING
        nullable: true
    street_number:
      type_snapshot:
        column_type: STRING
        nullable: true
    street_name:
      type_snapshot:
        column_type: STRING
        nullable: true
    city:
      type_snapshot:
        column_type: STRING
        nullable: true
    incident_zip:
      type_snapshot:
        column_type: INT64
        nullable: true
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
    longitude:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
    location:
      type_snapshot:
        column_type: STRING
        nullable: true
    council_district_code:
      type_snapshot:
        column_type: INT64
        nullable: true
    map_page:
      type_snapshot:
        column_type: STRING
        nullable: true
    map_tile:
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
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection|Table                          |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|austin_311|austin_311.311_service_requests|1     |1             |0            |0       |0     |1           |0               |
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
    100.0 * SUM(
        CASE
            WHEN CAST(analyzed_table.`close_date` AS DATE) >= '2017-12-31' AND CAST(analyzed_table.`close_date` AS DATE) <= '2018-12-30' THEN 1
            ELSE 0
        END
    ) / COUNT(*) AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 10.1%, what is below minimal
threshold level set in the fatal error (5%).

```
**************************************************
Finished executing a sensor for a check datetime_value_in_range_date_percent on the table austin_311.311_service_requests
using a sensor definition column/datetime/value_in_range_date_percent, sensor result count: 1

Results returned by the sensor:
+------------------+------------------------+------------------------+
|actual_value      |time_period             |time_period_utc         |
+------------------+------------------------+------------------------+
|10.110679361577297|2023-04-25T08:59:50.433Z|2023-04-25T08:59:50.433Z|
+------------------+------------------------+------------------------+
**************************************************
```