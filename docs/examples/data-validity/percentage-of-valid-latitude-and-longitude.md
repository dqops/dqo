# Percentage of valid latitude and longitude

Verifies that the percentage of valid latitude and longitude values are above the set threshold.

**PROBLEM**

[Austin-311-Public-Data](https://data.austintexas.gov/Utilities-and-City-Services/Austin-311-Public-Data/xwdj-i9he) 
provides the residents of Austin with a simple single point of contact for every city department.

What started as police non-emergency line for the City of Austin has become a robust Citywide Information Center
where ambassadors are available to answer residents’ concerns 24 hours a day, 7 days a week, and 365 days a year.

The dataset contain the list of service requests with unique key, descriptions, status, address as well as its longitude and latitude coordinates. 

We want to verify the percentage of valid latitude and longitude values in `latitude` and `longitude` columns, respectively.

**SOLUTION**

We will verify the data of `bigquery-public-data.austin_311.311_service_requests` using profiling
[valid_latitude_percent](../../checks/column/numeric/valid-latitude-percent.md) and 
[valid_longitude_percent](../../checks/column/numeric/valid-longitude-percent.md) column checks.
Our goal is to verify if the percentage of valid latitude values in the `latitude` and `longitude` columns are above the set threshold.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of valid latitude or longitude values is below 99%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.austin_311.311_service_requests` dataset. Some columns were 
omitted for clarity.  
The `latitude` and `longitude` columns of interest contains valid values.

| unique_key  | complaint_description               | incident_address                    | latitude        | longitude        |
|:------------|:------------------------------------|:------------------------------------|:----------------|:-----------------|
| 14-00000261 | Sign - Traffic Sign Maintenance     | 1411 MEARNS MEADOW BLVD, AUSTIN, TX | **30.37525546** | **-97.70466813** |
| 14-00000296 | Sign - Traffic Sign Maintenance     | DEER LN & BRODIE LN, AUSTIN, TX     | **30.19429024** | **-97.84298292** |
| 14-00001646 | Animal Control - Assistance Request | WHITESTONE                          | **30.53892395** | **-97.77471976** |
| 14-00001647 | Sign - Traffic Sign Maintenance     | METRIC                              | **30.38409576** | **-97.71474292** |
| 14-00001648 | Code Compliance                     | LA NARANJA                          | **30.21519414** | **-97.8738822**  |
| 14-00001649 | Code Compliance                     | DUBUQUE                             | **30.31488504** | **-97.6673545**  |
| 14-00001650 | Loose Dog                           | LOS INDIOS TRL & MC NEIL DR         | **30.43736609** | **-97.76050242** |
| 14-00001651 | Traffic Signal - Maintenance        | PLEASANT VALLEY                     | **30.17838134** | **-97.74744183** |
| 14-00001652 | ARR Missed Yard Trimmings /Organics | PROVENCIAL                          | **30.28755755** | **-97.66188875** |
| 14-00001654 | Parking Ticket Complaint            | 9TH                                 | **30.27091532** | **-97.7417791**  |


## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three maximum percentage thresholds levels for the checks:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

The highlighted fragments in the YAML file below represent the segment where the profiling `valid_latitude_percent` and 
`valid_longitude_percent`checks are configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="78-109"
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
      profiling_checks:
        numeric:
          profile_valid_latitude_percent:
            comments:
            - date: 2023-04-14T09:03:21.495+00:00
              comment_by: user
              comment: "In this example, values in the \"latitude\" column are verified\
                \ whether the percentage of values within the latitude range is above\
                \ the set threshold."
            warning:
              min_percent: 99.0
            error:
              min_percent: 98.0
            fatal:
              min_percent: 95.0
    longitude:
      type_snapshot:
        column_type: FLOAT64
        nullable: true
      profiling_checks:
        numeric:
          valid_longitude_percent:
            comments:
            - date: 2023-05-08T13:27:46.412+00:00
              comment_by: user
              comment: "In this example, values in the \"longitude\" column are verified\
                \ whether the percentage of values within the longitude range is are\
                \ above the set threshold."
            warning:
              min_percent: 99.0
            error:
              min_percent: 98.0
            fatal:
              min_percent: 95.0
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
## Running the checks in the example and evaluating the results using the graphical interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [graphical interface](../../working-with-dqo/navigating-the-graphical-interface/navigating-the-graphical-interface.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-valid-latitude-and-longitude-percent-check.png)

1. Go to **Profiling** section.

2. Select the table or column mentioned in the example description from the tree view on the left.

3. Select **Profiling Checks** tab.

4. Run the enabled check using the **Run check** button.
   ![Run check](https://dqops.com/docs/images/examples/valid-latitude-and-longitude-percent-run-check.png)

5. Access the results by clicking the **Results** button.
   ![Check details](https://dqops.com/docs/images/examples/valid-latitude-and-longitude-percent-check-details.png)

6. Review the results which should be similar to the one below.
   The actual value in this example is 99, which is above the minimum threshold level set in the warning (99.0%).
   The check gives a valid error (notice the green square on the left of the name of the check).

   ![Valid-latitude-and-longitude-percent check results](https://dqops.com/docs/images/examples/valid-latitude-and-longitude-percent-check-results.png)

7. After executing the checks, synchronize the results with your DQO cloud account using the **Synchronize** button
   located in the upper right corner of the graphical interface.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. Below you can see
   the results displayed on the KPI day by day dashboard showing results by connections, schemas, dimensions, tables, columns, checks, check categories and data groups.

   ![Valid-latitude-and-longitude-percent check results on KPI day by day-dashboard](https://dqops.com/docs/images/examples/valid-latitude-and-longitude-percent-check-results-on-KPI-day-by-day-dashboard.png)

## Running the checks in the example and evaluating the results using DQO Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
Review the results which should be similar to the one below.
The percent of valid latitude and longitude values is above the 99% and the checks results are valid.

```
Check evaluation summary per table:
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection|Table                          |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|austin_311|austin_311.311_service_requests|2     |2             |2            |0       |0     |0           |0               |
+----------+-------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the checks are run, you can initiate the checks in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL queries (sensors) executed in the checks.

```
**************************************************
Executing SQL on connection austin_311 (bigquery)
SQL to be executed on the connection:
SELECT
    100.0 * SUM(
        CASE
            WHEN analyzed_table.`latitude` >= -90.0 AND analyzed_table.`latitude` <= 90.0 THEN 1
            ELSE 0
        END
    )/COUNT(*) AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

```
**************************************************
Executing SQL on connection austin_311 (bigquery)
SQL to be executed on the connection:
SELECT
    100.0 * SUM(
        CASE
            WHEN analyzed_table.`longitude` >= -180.0 AND analyzed_table.`longitude` <= 180.0 THEN 1
            ELSE 0
        END
    )/COUNT(*) AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`austin_311`.`311_service_requests` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************

```

You can also see the results returned by the sensors. The actual values in this example for both sensors is 99.19%, 
which is above the maximum threshold level set for the warning (99%).

```
**************************************************
Finished executing a sensor for a check valid_latitude_percent on the table austin_311.311_service_requests 
using a sensor definition column/numeric/valid_latitude_percent, sensor result count: 1

Results returned by the sensor:
+----------------+------------------------+------------------------+
|actual_value    |time_period             |time_period_utc         |
+----------------+------------------------+------------------------+
|99.1905543612962|2023-05-08T06:08:26.679Z|2023-05-08T06:08:26.679Z|
+----------------+------------------------+------------------------+
**************************************************
```

```
Finished executing a sensor for a check valid_longitude_percent on the table austin_311.311_service_requests
using a sensor definition column/numeric/valid_longitude_percent, sensor result count: 1

Results returned by the sensor:
+-----------------+------------------------+------------------------+
|actual_value     |time_period             |time_period_utc         |
+-----------------+------------------------+------------------------+
|99.19006528255773|2023-05-08T13:34:23.984Z|2023-05-08T13:34:23.984Z|
+-----------------+------------------------+------------------------+
```