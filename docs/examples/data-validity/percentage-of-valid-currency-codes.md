# Percentage of valid currency codes

Column level check that ensures that the percentage of valid currency code strings in the monitored column does not fall below set thresholds.

**PROBLEM**

Here is a table with some sample customer data. In this example, we will monitor the `valid_currency_code` column and verify that each currency code is in the correct format.

The `valid_currency_code` column contains currency code data. We want to verify the percent of valid currency codes on `valid_currency_code` column.

**SOLUTION**

We will verify the data of using profiling [string_valid_currency_code_percent](../../checks/column/strings/string-valid-currency-code-percent.md) column check.
Our goal is to verify if the percentage of valid currency code values in the `valid_currency_code` column does not fall below the setup thresholds.

In this example, we will set three minimum percentage thresholds levels for the check:

- warning: 75.0%
- error: 65.0%
- fatal: 55.0%

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

**VALUE**

If the percentage of currency code values falls below 75.0%, a warning alert will be triggered.

## Data structure

The following is a fragment of the `DQO` dataset. Some columns were omitted for clarity.  
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


## YAML configuration file

The YAML configuration file stores both the table details and checks configurations.

In this example, we have set three minimum percentage thresholds levels for the check:

- warning: 75.0%
- error: 65.0%
- fatal: 55.0%

The highlighted fragments in the YAML file below represent the segment where the profiling `string_valid_currency_code_percent` check is configured.

If you want to learn more about checks and threshold levels, please refer to the [DQO concept section](../../dqo-concepts/checks/index.md).

```yaml hl_lines="20-38"
apiVersion: dqo/v1
kind: table
spec:
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    usa_phone:
      type_snapshot:
        column_type: STRING
        nullable: true
    usa_zipcode:
      type_snapshot:
        column_type: STRING
        nullable: true
    valid_country_code:
      type_snapshot:
        column_type: STRING
        nullable: true
    valid_currency_code:
      type_snapshot:
        column_type: STRING
        nullable: true
      profiling_checks:
        strings:
          string_valid_currency_code_percent:
            comments:
            - date: 2023-05-19T09:27:25.655+00:00
              comment_by: user
              comment: "\"In this example, values in \"valid_currency_code\" column\
                \ are verified whether the percentage of valid currency codes does\
                \ not fall below the indicated thresholds.\""
            warning:
              min_percent: 75.0
            error:
              min_percent: 65.0
            fatal:
              min_percent: 55.0
```
## Running the checks in the example and evaluating the results

The detailed explanation of how to run the example is described [here](../#running-the-examples).

To execute the check prepared in the example, run the following command in DQO Shell:

``` 
check run
```
You should see the results as the one below.
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