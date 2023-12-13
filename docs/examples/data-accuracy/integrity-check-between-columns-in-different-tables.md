# Integrity check between columns in different tables

This example shows how to check the referential integrity of a column against a column in another table. 

**PROBLEM**

[Federal Information Processing System (FIPS) states codes](https://www.census.gov/library/reference/code-lists/ansi.html)
are numbers which uniquely identify U.S. states and certain other associated areas. A wide audience uses FIPS codes 
across many private and public datasets to uniquely identify geographic features.

We want to verify that the column labeled `state_fips_code` in the table containing a list of U.S. counties (`fips_codes_all`)
only contains values that corresponds to the FIPS state codes listed in a separate table.


**SOLUTION**

We will check the data of `bigquery-public-data.census_utility.fips_codes_all` using 
[foreign_key_match_percent](../../checks/column/integrity/foreign-key-match-percent.md) check.
Our goal is to verify that the values in `state_fips_code` column in `fips_codes_all` table matches the values in the reference
`state_fips_code` column in the `fips_codes_states` table. 

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 98.0%
- fatal: 95.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of matching data drops below 99%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `bigquery-public-data.census_utility.fips_codes_all` dataset. Some columns were omitted for clarity.  
The dataset contains a list of all U.S. counties. It includes all counties in the United States (`area_name` column) with
its specific unique codes (`county_fips_code` column). The `state_fips_code` column shows codes that identify each state
and some related regions, which we want to verify.


| summary_level | summary_level_name | state_fips_code | county_fips_code | area_name                 |
|:--------------|:-------------------|:----------------|:-----------------|:--------------------------|
| 050           | state-county       | **01**          | 01001            | Autauga County            |
| 050           | state-county       | **02**          | 02105            | Hoonah-Angoon Census Area |
| 050           | state-county       | **02**          | 02290            | Yukon-Koyukuk Census Area |
| 050           | state-county       | **04**          | 04001            | Apache County             |
| 050           | state-county       | **05**          | 05007            | Benton County             |
| 050           | state-county       | **06**          | 06001            | Alameda County            |
| 050           | state-county       | **08**          | 08121            | Washington County         |
| 050           | state-county       | **08**          | 08123            | Weld County               |
| 050           | state-county       | **10**          | 10001            | Kent County               |
| 050           | state-county       | **12**          | 12003            | Baker County              |


The second dataset `bigquery-public-data.census_utility.fips_codes_states`, which we want to use as a reference, contains
a list of all states and other related regions and with their unique codes (`state_fips_code` column)

| state_fips_code | state_postal_abbreviation | state_name           | state_gnisid |
|:----------------|:--------------------------|:---------------------|:-------------|
| **01**          | AL                        | Alabama              | 1779775      |
| **02**          | AK                        | Alaska               | 1785533      |
| **04**          | AZ                        | Arizona              | 1779777      |
| **05**          | AR                        | Arkansas             | 68085        |
| **06**          | CA                        | California           | 1779778      |
| **08**          | CO                        | Colorado             | 1779779      |
| **09**          | CT                        | Connecticut          | 1779780      |
| **10**          | DE                        | Delaware             | 1779781      |
| **11**          | DC                        | District of Columbia | 1702382      |
| **12**          | FL                        | Florida              | 294478       |


## Running the checks in the example and evaluating the results using the user interface

A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-foreign-key-match-percent-checks.png)

1. Go to the **Monitoring** section.

    The Monitoring Checks section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

    On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../data-sources/index.md).


3. Select the **Daily checks** tab.

    This tab displays a list of data quality checks in the check editor. Learn more about [navigating the check editor](../../../dqo-concepts/user-interface-overview/user-interface-overview/#check-editor).


4. Run the enabled check using the **Run check** button.

    You can also run all the checks for an entire subcategory of checks using the **Run check** button at the end of the line with the check subgroup name.

    ![Run check](https://dqops.com/docs/images/examples/daily-foreign-key-match-percent-run-checks.png)


5. Access the results by clicking the **Results** button.

    Within the Results window, you will see three categories: **Check results**, **Sensor readouts**, and **Execution errors**.
    The Check results category shows the severity level that result from the verification of sensor readouts by set rule thresholds.
    The Sensor readouts category displays the values obtained by the sensors from the data source.
    The Execution errors category displays any error that occurred during the check's execution.
 
    ![Check details](https://dqops.com/docs/images/examples/daily-foreign-key-match-percent-checks-details.png)

    The results should be similar to the one below.
   
    The actual value in this example is 100, which is above the minimum threshold level set in the warning (99.0%).
    The check gives a valid result (notice the green square to the left of the check name).

    ![Foreign-key-match-percent check results](https://dqops.com/docs/images/examples/daily-foreign-key-match-percent-checks-results.png)

6. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

7. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
    go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 
    
    Below you can see the results displayed on the Current table status per data quality dimension dashboard showing results by connection, schema, dimension and data group.
 
    ![Foreign-key-match-percent results on Current table status per data quality dimension dashboard](https://dqops.com/docs/images/examples/daily-foreign-key-match-percent-checks-results-on-current-table-status-per-data-quality-dimension-dashboard.png)

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 99.0%
- error: 95.0%
- fatal: 98.0%

And the following parameters:

- foreign_table: fips_codes_states
- foreign_column: state_fips_code

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_foreign_key_match_percent`
check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="16-32"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    summary_level:
      type_snapshot:
        column_type: STRING
        nullable: true
    summary_level_name:
      type_snapshot:
        column_type: STRING
        nullable: true
    state_fips_code:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          integrity:
            daily_foreign_key_match_percent:
              parameters:
                foreign_table: fips_codes_states
                foreign_column: state_fips_code
              warning:
                min_percent: 99.0
              error:
                min_percent: 98.0
              fatal:
                min_percent: 95.0
```

## Running the checks in the example and evaluating the results using DQOps Shell
A detailed explanation of [how to run the example is described here](../../#running-the-use-cases).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```

Access the results which should be similar as the one below.
The percentage of matching values in the `state_fips_code` column is above the 99% and the check shows valid result.

```
Check evaluation summary per table:
+--------------+-----------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection    |Table                        |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+--------------+-----------------------------+------+--------------+-------------+--------+------+------------+----------------+
|census_utility|census_utility.fips_codes_all|1     |1             |1            |0       |0     |0           |0               |
+--------------+-----------------------------+------+--------------+-------------+--------+------+------------+----------------+
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
    100.0 * SUM(
        CASE
            WHEN foreign_table.`state_fips_code` IS NULL AND analyzed_table.`state_fips_code` IS NOT NULL
                THEN 0
            ELSE 1
        END
    ) / COUNT(*) AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `bigquery-public-data`.`census_utility`.`fips_codes_all` AS analyzed_table
LEFT OUTER JOIN
   `bigquery-public-data`.`census_utility`.`fips_codes_states` AS foreign_table
ON analyzed_table.`state_fips_code` = foreign_table.`state_fips_code`
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```

You can also see the results returned by the sensor. The actual value in this example is 100%, which indicates that all
values in our column of interest match those in the reference column.

```
**************************************************
Finished executing a sensor for a check foreign_key_match_percent on the table census_utility.fips_codes_all using 
a sensor definition column/integrity/foreign_key_match_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|100.0       |2023-04-27T09:53:36.777Z|2023-04-27T09:53:36.777Z|
+------------+------------------------+------------------------+
**************************************************
```

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQOps as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [foreign_key_match_percent used in this example, go to the check details section](../../checks/column/integrity/foreign-key-match-percent.md).
- With DQOps, you can easily customize when the checks are run at the level of the entire connection, table, or individual check. [Learn more about how to set schedules here](../../working-with-dqo/schedules/index.md). 
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) and [notifications](../../integrations/webhooks/index.md). 
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../../working-with-dqo/set-up-data-grouping/set-up-data-grouping.md) can help you to calculate separate data quality KPI scores for different groups of rows.
