# Percentage of valid currency codes

Column level check that ensures that the percentage of valid currency code strings in the monitored column does not fall below set thresholds.

**PROBLEM**

Here is a table with some sample customer data. In this example, we will monitor the `valid_currency_code` column and verify that each currency code is in the correct format.

The `valid_currency_code` column contains currency code data. We want to verify the percent of valid currency codes on `valid_currency_code` column.

**SOLUTION**

We will verify the data of using monitoring [string_valid_currency_code_percent](../../checks/column/strings/string-valid-currency-code-percent.md) column check.
Our goal is to verify if the percentage of valid currency code values in the `valid_currency_code` column does not fall below the setup thresholds.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 75.0%
- error: 65.0%
- fatal: 55.0%

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of currency code values falls below 75.0%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `DQOps` dataset. Some columns were omitted for clarity.  
The `valid_currency_code` column of interest contains valid and invalid currency code values.

| negative | usa_phone       | usa_zipcode | valid_country_code | valid_currency_code |
|:---------|:----------------|:------------|:-------------------|:--------------------|
| 91       | 17400986784222  | 22803       |                    | **pound**           |
| 56       | (513)134987523  | 6641666416  | DM                 | **koruna**          |
| -67      | 111111111111111 | 21541       | AR                 | **KHR**             |
| 156      | 19472348976???  | 8604486044  | CP                 | **IRR**             |
| -3       | =13261092976    | 30683       | CO                 | **real**            |
| -22      | 13805414567iowa | 61914       | CW                 | **euro**            |
| 3        | +1(231)4561289  | 21520       | AL                 | **£**               |
| 4        | (1)5175413241   | 21536       | BS                 | **$**               |
| 56       | 1(248)-541-0987 | 21531       | AQ                 | **533**             |
| 3        | (+1)5671239999  | 66419       | GA                 | **shilling**        |
| 93       | 16792345678     | 86024       |                    | **€**               |
| -1       | 9372346785      | 2280722807  | TZ                 | **denar**           |
| 20       |                 | 3060130601  | TD                 | **MZN**             |
| -1       | 14195429807     | 61925       | CO                 | **USD**             |
| -4       | 16165240542     |             |                    | **kr**              |
| -83      | 13305410987     | 31803       | HR                 | **¥**               |
| 78       |                 | 86435       | KY                 | **KZT**             |
| 2        | 13135678943     | 21522       | AB                 | **PYG**             |
| 1        | 18105234567     | 21561       | BD                 | **zł**              |
| 1        | (906)6259999    | 86045       | CM                 | **dollar**          |
| -1       | 15864562433     | 21550       | IO                 | **peso**            |
| 495      | (1)6141118766   | 22801       |                    | **$**               |
| 87       | (513)1349876    | 66552       | FR                 | **ZWD**             |
| -45      | 17345213489     | 215388888   |                    | **CUP**             |

## Running the checks in the example and evaluating the results using the user interface

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example using the [user interface](../../dqo-concepts/user-interface-overview/user-interface-overview.md):

![Navigating to a list of checks](https://dqops.com/docs/images/examples/navigating-to-the-list-of-daily-string-valid-currency-code-percent-checks.png)

1. Go to the **Monitoring** section.

   The Monitoring Checks section enables the configuration of data quality checks that are designed for the daily and monthly monitoring of your data source.


2. Select the table or column mentioned in the example description from the **tree view** on the left.

   On the tree view you can find the tables that you have imported. Here is more about [adding connection and importing tables](../../working-with-dqo/adding-data-source-connection/index.md).


3. Select the **Monitoring Checks** tab.

   In this tab you can find a list of data quality checks.


4. Run the enabled check using the **Run check** button.

   You can also run all checks for the check category using the **Run check** button located at the end of the row with the name of the check group.

   ![Run check](https://dqops.com/docs/images/examples/daily-string-valid-currency-code-percent-run-checks.png)


5. Access the results by clicking the **Results** button.

   Within the Results window, you will see three categories: **Sensor readouts**, **Check results**, and **Execution errors**. The Sensor readouts category
   displays the values obtained by the sensors from the data source. The Check results category shows the severity level
   that result from the verification of sensor readouts by set rule thresholds. The Execution errors category displays any error
   that occurred during the check's execution.

   ![Check details](https://dqops.com/docs/images/examples/daily-string-valid-currency-code-percent-checks-details.png)


6. Review the results which should be similar to the one below.
   
    The actual value in this example is 64, which is below the minimum threshold level set in the warning (75.0%).
    The check gives an error result (notice the orange square on the left of the name of the check).

    ![String-valid-currency-code-percent check results](https://dqops.com/docs/images/examples/daily-string-valid-currency-code-percent-checks-results.png)

7. Synchronize the results with your DQOps cloud account using the **Synchronize** button located in the upper right corner of the user interface.

    Synchronization ensures that the locally stored results are synced with your DQOps Cloud account, allowing you to view them on the dashboards.

8. To review the results on the [data quality dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
   go to the Data Quality Dashboards section and select the dashboard from the tree view on the left. 

    Below you can see the results displayed on the Current table status per check category dashboard showing results by connections, schemas, data group and check category.

    ![String-valid-currency-code-percent results on Current table status per check category dashboard](https://dqops.com/docs/images/examples/daily-string-valid-currency-code-percent-checks-results-on-current-table-status-per-check-category-dashboard.png)

## Configuring a schedule at connection level

With DQOps, you can easily customize when checks are run by setting schedules. You can set schedules for an entire connection,
table, or individual check.

After running the daily monitoring checks, let's set up a schedule for the entire connection to execute the checks every day at 12:00.

![Configure scheduler for the connection](https://dqops.com/docs/images/examples/configure-scheduler-for-connection.png)

1. Navigate to the **Data Source** section.

2. Choose the connection from the tree view on the left.

3. Click on the **Schedule** tab.

4. Select the Monitoring Daily tab

5. Select the **Run every day at** option and specify the time as 12:00.

6. Once you have set the schedule, click on the **Save** button to save your changes.

7. Enable the scheduler by clicking the toggle button.

![Enable job scheduler](https://dqops.com/docs/images/examples/enable-job-scheduler.png)

Once a schedule is set up for a particular connection, it will execute all the checks that have been configured across
all tables associated with that connection.

You can [read more about scheduling here](../../working-with-dqo/schedules/index.md).

You might also want to check the [Running checks with a scheduler](../data-quality-monitoring/running-checks-with-a-scheduler.md) example.

## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 75.0%
- error: 65.0%
- fatal: 55.0%

The highlighted fragments in the YAML file below represent the segment where the monitoring `daily_string_valid_currency_code_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQOps concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="16-29"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    id:
      type_snapshot:
        column_type: INT64
        nullable: true
    nulls:
      type_snapshot:
        column_type: STRING
        nullable: true
    valid_currency_code:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          strings:
            daily_string_valid_currency_code_percent:
              warning:
                min_percent: 75.0
              error:
                min_percent: 65.0
              fatal:
                min_percent: 55.0
```

## Running the checks in the example and evaluating the results using DQOps Shell

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQOps Shell:

``` 
check run
```
Review the results which should be similar to the one below.
The percent of the valid currency code in the `valid_currency_code` column is below 65.0% and the check raised an error.
```
Check evaluation summary per table:
+-------------------+---------------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|Connection         |Table                                                    |Checks|Sensor results|Valid results|Warnings|Errors|Fatal errors|Execution errors|
+-------------------+---------------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
|valid_currency_code|dqo_ai_test_data.nulls_and_uniqueness_8591349191461738589|1     |1             |0            |0       |1     |0           |0               |
+-------------------+---------------------------------------------------------+------+--------------+-------------+--------+------+------------+----------------+
```
For a more detailed insight of how the check is run, you can initiate the check in debug mode by executing the
following command:

```
check run --mode=debug
```

In the debug mode you can view the SQL query (sensor) executed in the check.

```
**************************************************
Executing SQL on connection valid_currency_code (bigquery)
SQL to be executed on the connection:
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN UPPER(analyzed_table.`valid_currency_code`) IN ('ALL',     'AFN',  'ARS',  'AWG',  'AUD',  'AZN',  'BSD',  'BBD',  'BYN',  'BZD',  'BMD',  'BOB',  'BAM',  'BWP',  'BGN',  'BRL',  'BND',  'KHR',  'CAD',  'KYD',  'CLP',  'CNY',  'COP',  'CRC',  'HRK',  'CUP',  'CZK',  'DKK',  'DOP',  'XCD',  'EGP',  'SVC',  'EUR',  'FKP',  'FJD',  'GHS',  'GIP',  'GTQ',  'GGP',   'GYD',  'HNL',  'HKD',  'HUF',  'ISK',  'INR',  'IDR',  'IRR',  'IMP',  'ILS',  'JMD',  'JPY',  'JEP',  'KZT',  'KPW',  'KRW',  'KGS',  'LAK',  'LBP',  'LRD',  'MKD',  'MYR',  'MUR',  'MXN',  'MNT',  'MZN',  'NAD',  'NPR',  'ANG',  'NZD',  'NIO',  'NGN',  'NOK',  'OMR',  'PKR',  'PAB',  'PYG',  'PEN',  'PHP',  'PLN',  'QAR',  'RON',  'RUB',  'SHP',  'SAR',  'RSD',  'SCR',   'SGD',  'SBD',  'SOS',  'ZAR',  'LKR',  'SEK',  'CHF',  'SRD',  'SYP',  'TWD',  'THB',  'TTD',  'TRY',  'TVD',  'UAH',  'AED',  'GBP',  'USD',  'UYU',  'UZS',  'VEF',  'VND',  'YER',  'ZWD',  'LEK',  '؋',    '$',    'Ƒ',    '₼',    'BR',   'BZ$',  '$B',   'KM',   'P',    'ЛВ',   'R$',   '៛',    '¥',    '₡',    'KN',   '₱',    'KČ',   'KR',   'RD$', '£',     '€',    '¢',     'Q',    'L',    'FT',   '₹',    'RP',   '﷼',    '₪',    'J$',   '₩',    '₭',    'ДЕН',  'RM',   '₨',    '₮',    'د.إ',  'MT',   'C$',   '₦',    'B/.',  'GS',   'S/.', 'ZŁ',    'LEI',  'ДИН.', 'S',    'R',    'NT$',  '฿',    'TT$',  '₺',    '₴',    '$U',   'BS',   '₫', 'Z$')
                    THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CURRENT_TIMESTAMP() AS time_period,
    TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
FROM `dqo-ai-testing`.`dqo_ai_test_data`.`nulls_and_uniqueness_8591349191461738589` AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
**************************************************
```
You can also see the results returned by the sensor. The actual value in this example is 64.0%, which is below the minimal
threshold level set in the error (65.0%).

```
**************************************************
Finished executing a sensor for a check string_valid_currency_code_percent on the table dqo_ai_test_data.nulls_and_uniqueness_8591349191461738589 using a sensor definition column/strings/string_valid_currency_code_percent, sensor result count: 1

Results returned by the sensor:
+------------+------------------------+------------------------+
|actual_value|time_period             |time_period_utc         |
+------------+------------------------+------------------------+
|64.0        |2023-05-18T11:08:09.633Z|2023-05-18T11:08:09.633Z|
+------------+------------------------+------------------------+
**************************************************
```
## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../../working-with-dqo/installation/install-dqo-using-pip.md) or [run DQOps as a Docker container](../../working-with-dqo/installation/run-dqo-as-docker-container.md).
- For details on the [string_valid_currency_code_percent check used in this example, go to the check details section](../../checks/column/strings/string-valid-currency-code-percent.md).
- You might be interested in another validity check that [evaluates that the percentage of valid latitude and longitude values are above the set threshold](../data-validity/percentage-of-valid-latitude-and-longitude.md).
- Would you like to add your own connection? Here you can find [information about supported databases and how to add new connection](../../working-with-dqo/adding-data-source-connection/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../../working-with-dqo/incidents-and-notifications/incidents.md) and [Slack notifications](../../integrations/slack/configuring-slack-notifications.md).
