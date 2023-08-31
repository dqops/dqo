**string valid country code percent** checks  

**Description**  
Column level check that ensures that the percentage of valid country code strings in the monitored column does not fall below set thresholds.

___

## **profile string valid country code percent**  
  
**Check description**  
Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|profile_string_valid_country_code_percent|profiling| |[string_valid_country_code_percent](../../../../reference/sensors/Column/strings-column-sensors/#string-valid-country-code-percent)|[min_percent](../../../../reference/rules/Comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=profile_string_valid_country_code_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=profile_string_valid_country_code_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=profile_string_valid_country_code_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=profile_string_valid_country_code_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=profile_string_valid_country_code_percent
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        strings:
          profile_string_valid_country_code_percent:
            warning:
              min_percent: 100.0
            error:
              min_percent: 99.0
            fatal:
              min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-21"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      profiling_checks:
        strings:
          profile_string_valid_country_code_percent:
            warning:
              min_percent: 100.0
            error:
              min_percent: 99.0
            fatal:
              min_percent: 95.0
      labels:
      - This is the column that is analyzed for data quality issues
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table) analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.[target_column]) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 39-44"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          profiling_checks:
            strings:
              profile_string_valid_country_code_percent:
                warning:
                  min_percent: 100.0
                error:
                  min_percent: 99.0
                fatal:
                  min_percent: 95.0
          labels:
          - This is the column that is analyzed for data quality issues
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
        FROM(
            SELECT
                original_table.*,
            original_table."country" AS grouping_level_1,
            original_table."state" AS grouping_level_2,
            TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
            CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table) analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.[target_column]) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state]
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    






___

## **daily string valid country code percent**  
  
**Check description**  
Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_string_valid_country_code_percent|monitoring|daily|[string_valid_country_code_percent](../../../../reference/sensors/Column/strings-column-sensors/#string-valid-country-code-percent)|[min_percent](../../../../reference/rules/Comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_string_valid_country_code_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_string_valid_country_code_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_string_valid_country_code_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_string_valid_country_code_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_string_valid_country_code_percent
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        daily:
          strings:
            daily_string_valid_country_code_percent:
              warning:
                min_percent: 100.0
              error:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-22"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      monitoring_checks:
        daily:
          strings:
            daily_string_valid_country_code_percent:
              warning:
                min_percent: 100.0
              error:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
      labels:
      - This is the column that is analyzed for data quality issues
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
        TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table) analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(LOCALTIMESTAMP AS date) AS time_period,
        CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
        TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.[target_column]) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value,
        CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
        CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 40-45"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          monitoring_checks:
            daily:
              strings:
                daily_string_valid_country_code_percent:
                  warning:
                    min_percent: 100.0
                  error:
                    min_percent: 99.0
                  fatal:
                    min_percent: 95.0
          labels:
          - This is the column that is analyzed for data quality issues
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
        FROM(
            SELECT
                original_table.*,
            original_table."country" AS grouping_level_1,
            original_table."state" AS grouping_level_2,
            TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
            CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table) analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
            TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.[target_column]) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state]
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    






___

## **monthly string valid country code percent**  
  
**Check description**  
Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_string_valid_country_code_percent|monitoring|monthly|[string_valid_country_code_percent](../../../../reference/sensors/Column/strings-column-sensors/#string-valid-country-code-percent)|[min_percent](../../../../reference/rules/Comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_string_valid_country_code_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_string_valid_country_code_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_string_valid_country_code_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_string_valid_country_code_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_string_valid_country_code_percent
```
**Check structure (Yaml)**
```yaml
      monitoring_checks:
        monthly:
          strings:
            monthly_string_valid_country_code_percent:
              warning:
                min_percent: 100.0
              error:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="13-22"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      monitoring_checks:
        monthly:
          strings:
            monthly_string_valid_country_code_percent:
              warning:
                min_percent: 100.0
              error:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
      labels:
      - This is the column that is analyzed for data quality issues
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
        CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table) analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.[target_column]) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value,
        DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
        CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-21 40-45"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          monitoring_checks:
            monthly:
              strings:
                monthly_string_valid_country_code_percent:
                  warning:
                    min_percent: 100.0
                  error:
                    min_percent: 99.0
                  fatal:
                    min_percent: 95.0
          labels:
          - This is the column that is analyzed for data quality issues
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
        FROM(
            SELECT
                original_table.*,
            original_table."country" AS grouping_level_1,
            original_table."state" AS grouping_level_2,
            TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
            CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table) analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.[target_column]) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state]
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    






___

## **daily partition string valid country code percent**  
  
**Check description**  
Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_string_valid_country_code_percent|partitioned|daily|[string_valid_country_code_percent](../../../../reference/sensors/Column/strings-column-sensors/#string-valid-country-code-percent)|[min_percent](../../../../reference/rules/Comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_string_valid_country_code_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_string_valid_country_code_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_string_valid_country_code_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_partition_string_valid_country_code_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_string_valid_country_code_percent
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        daily:
          strings:
            daily_partition_string_valid_country_code_percent:
              warning:
                min_percent: 100.0
              error:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-23"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partition_by_column: col_event_timestamp
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      partitioned_checks:
        daily:
          strings:
            daily_partition_string_valid_country_code_percent:
              warning:
                min_percent: 100.0
              error:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
      labels:
      - This is the column that is analyzed for data quality issues
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_FORMAT(analyzed_table.`col_event_timestamp`, '%Y-%m-%d 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`col_event_timestamp`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(original_table."col_event_timestamp" AS DATE)) AS time_period,
        CAST(TRUNC(CAST(original_table."col_event_timestamp" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table) analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.[target_column]) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value,
        CAST(analyzed_table.[col_event_timestamp] AS date) AS time_period,
        CAST((CAST(analyzed_table.[col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY CAST(analyzed_table.[col_event_timestamp] AS date), CAST(analyzed_table.[col_event_timestamp] AS date)
    ORDER BY CAST(analyzed_table.[col_event_timestamp] AS date)
    
        
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-22 41-46"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
        partition_by_column: col_event_timestamp
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          partitioned_checks:
            daily:
              strings:
                daily_partition_string_valid_country_code_percent:
                  warning:
                    min_percent: 100.0
                  error:
                    min_percent: 99.0
                  fatal:
                    min_percent: 95.0
          labels:
          - This is the column that is analyzed for data quality issues
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            CAST(analyzed_table.`col_event_timestamp` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`col_event_timestamp` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(analyzed_table.`col_event_timestamp`, '%Y-%m-%d 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`col_event_timestamp`, '%Y-%m-%d 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
        FROM(
            SELECT
                original_table.*,
            original_table."country" AS grouping_level_1,
            original_table."state" AS grouping_level_2,
            TRUNC(CAST(original_table."col_event_timestamp" AS DATE)) AS time_period,
            CAST(TRUNC(CAST(original_table."col_event_timestamp" AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table) analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            CAST((CAST(analyzed_table."col_event_timestamp" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            CAST(analyzed_table."col_event_timestamp" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.[target_column]) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            CAST(analyzed_table.[col_event_timestamp] AS date) AS time_period,
            CAST((CAST(analyzed_table.[col_event_timestamp] AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[col_event_timestamp] AS date), CAST(analyzed_table.[col_event_timestamp] AS date)
        ORDER BY level_1, level_2CAST(analyzed_table.[col_event_timestamp] AS date)
        
            
        ```
    






___

## **monthly partition string valid country code percent**  
  
**Check description**  
Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_string_valid_country_code_percent|partitioned|monthly|[string_valid_country_code_percent](../../../../reference/sensors/Column/strings-column-sensors/#string-valid-country-code-percent)|[min_percent](../../../../reference/rules/Comparison/#min-percent)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_partition_string_valid_country_code_percent
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_partition_string_valid_country_code_percent
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_partition_string_valid_country_code_percent
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_partition_string_valid_country_code_percent
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_string_valid_country_code_percent
```
**Check structure (Yaml)**
```yaml
      partitioned_checks:
        monthly:
          strings:
            monthly_partition_string_valid_country_code_percent:
              warning:
                min_percent: 100.0
              error:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="14-23"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partition_by_column: col_event_timestamp
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  columns:
    target_column:
      partitioned_checks:
        monthly:
          strings:
            monthly_partition_string_valid_country_code_percent:
              warning:
                min_percent: 100.0
              error:
                min_percent: 99.0
              fatal:
                min_percent: 95.0
      labels:
      - This is the column that is analyzed for data quality issues
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested

```
### **BigQuery**
=== "Sensor template for BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_FORMAT(analyzed_table.`col_event_timestamp`, '%Y-%m-01 00:00:00') AS time_period,
        FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`col_event_timestamp`, '%Y-%m-01 00:00:00'))) AS time_period_utc
    FROM `<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Oracle"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        time_period,
        time_period_utc
    FROM(
        SELECT
            original_table.*,
        TRUNC(CAST(original_table."col_event_timestamp" AS DATE), 'MONTH') AS time_period,
        CAST(TRUNC(CAST(original_table."col_event_timestamp" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "<target_schema>"."<target_table>" original_table) analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value,
        DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN UPPER(analyzed_table.[target_column]) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG(*)
        END AS actual_value,
        DATEFROMPARTS(YEAR(CAST(analyzed_table.[col_event_timestamp] AS date)), MONTH(CAST(analyzed_table.[col_event_timestamp] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[col_event_timestamp] AS date)), MONTH(CAST(analyzed_table.[col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[col_event_timestamp] AS date)), MONTH(CAST(analyzed_table.[col_event_timestamp] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[col_event_timestamp]), 0)
    ORDER BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[col_event_timestamp] AS date)), MONTH(CAST(analyzed_table.[col_event_timestamp] AS date)), 1)
    
        
    ```

### **Configuration with data grouping**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="12-22 41-46"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
        partition_by_column: col_event_timestamp
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      default_grouping_name: group_by_country_and_state
      groupings:
        group_by_country_and_state:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      columns:
        target_column:
          partitioned_checks:
            monthly:
              strings:
                monthly_partition_string_valid_country_code_percent:
                  warning:
                    min_percent: 100.0
                  error:
                    min_percent: 99.0
                  fatal:
                    min_percent: 95.0
          labels:
          - This is the column that is analyzed for data quality issues
        col_event_timestamp:
          labels:
          - optional column that stores the timestamp when the event/transaction happened
        col_inserted_at:
          labels:
          - optional column that stores the timestamp when row was ingested
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```  
    **BigQuery**  
      
    === "Sensor template for BigQuery"
        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`col_event_timestamp` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for MySQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.`target_column`) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table.`country` AS grouping_level_1,
            analyzed_table.`state` AS grouping_level_2,
            DATE_FORMAT(analyzed_table.`col_event_timestamp`, '%Y-%m-01 00:00:00') AS time_period,
            FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(analyzed_table.`col_event_timestamp`, '%Y-%m-01 00:00:00'))) AS time_period_utc
        FROM `<target_table>` AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
            {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
        FROM(
            SELECT
                original_table.*
                {{- lib.render_data_grouping_projections('original_table') }}
                {{- lib.render_time_dimension_projection('original_table') }}
            FROM {{ lib.render_target_table() }} original_table
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}) analyzed_table
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
        
                        analyzed_table.grouping_level_1,
        
                        analyzed_table.grouping_level_2
        ,
            time_period,
            time_period_utc
        FROM(
            SELECT
                original_table.*,
            original_table."country" AS grouping_level_1,
            original_table."state" AS grouping_level_2,
            TRUNC(CAST(original_table."col_event_timestamp" AS DATE), 'MONTH') AS time_period,
            CAST(TRUNC(CAST(original_table."col_event_timestamp" AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "<target_schema>"."<target_table>" original_table) analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Redshift"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        SELECT
            CASE
                WHEN COUNT(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table."target_column") IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT(*)
            END AS actual_value,
            analyzed_table."country" AS grouping_level_1,
            analyzed_table."state" AS grouping_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."col_event_timestamp" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER({{ lib.render_target_column('analyzed_table')}}) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value
            {{- lib.render_data_grouping_projections('analyzed_table') }}
            {{- lib.render_time_dimension_projection('analyzed_table') }}
        FROM {{ lib.render_target_table() }} AS analyzed_table
        {{- lib.render_where_clause() -}}
        {{- lib.render_group_by() -}}
        {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        SELECT
            CASE
                WHEN COUNT_BIG(*) = 0 THEN 100.0
                ELSE 100.0 * SUM(
                    CASE
                        WHEN UPPER(analyzed_table.[target_column]) IN ('AF',	'AL',	'DZ',	'AS',	'AD',	'AO',	'AI',	'AQ',	'AG',	'AR',	'AM',	'AW',	'AU',	'AT',	'AZ',	'BS',	'BH',	'BD',	'BB',	'BY',	'BE',	'BZ',	'BJ',	'BM',	'BT',	'BO',	'BA',	'BW',	'BR',	'IO',	'VG',	'BN',	'BG',	'BF',	'BI',	'KH',	'CM',	'CA',	'CV',	'KY',	'CF',	'TD',	'CL',	'CN',	'CX',	'CC',	'CO',	'KM',	'CK',	'CR',	'HR',	'CU',	'CW',	'CY',	'CZ',	'CD',	'DK',	'DJ',	'DM',	'DO',	'TL',	'EC',	'EG',	'SV',	'GQ',	'ER',	'EE',	'ET',	'FK',	'FO',	'FJ',	'FI',	'FR',	'PF',	'GA',	'GM',	'GE',	'DE',	'GH',	'GI',	'GR',	'GL',	'GD',	'GU',	'GT',	'GG',	'GN',	'GW',	'GY',	'HT',	'HN',	'HK',	'HU',	'IS',	'IN',	'ID',	'IR',	'IQ',	'IE',	'IM',	'IL',	'IT',	'CI',	'JM',	'JP',	'JE',	'JO',	'KZ',	'KE',	'KI',	'XK',	'KW',	'KG',	'LA',	'LV',	'LB',	'LS',	'LR',	'LY',	'LI',	'LT',	'LU',	'MO',	'MK',	'MG',	'MW',	'MY',	'MV',	'ML',	'MT',	'MH',	'MR',	'MU',	'YT',	'MX',	'FM',	'MD',	'MC',	'MN',	'ME',	'MS',	'MA',	'MZ',	'MM',	'NA',	'NR',	'NP',	'NL',	'AN',	'NC',	'NZ',	'NI',	'NE',	'NG',	'NU',	'KP',	'MP',	'NO',	'OM',	'PK',	'PW',	'PS',	'PA',	'PG',	'PY',	'PE',	'PH', 'PN', 'PL',	'PT',	'PR',	'QA',	'CG',	'RE',	'RO',	'RU',	'RW',	'BL',	'SH',	'KN',	'LC',	'MF',	'PM',	'VC',	'WS',	'SM',	'ST',	'SA',	'SN',	'RS',	'SC',	'SL',	'SG',	'SX',	'SK',	'SI',	'SB',	'SO',	'ZA',	'KR',	'SS',	'ES',	'LK',	'SD',	'SR',	'SJ',	'SZ',	'SE',	'CH',	'SY',	'TW',	'TJ',	'TZ',	'TH',	'TG',	'TK',	'TO',	'TT',	'TN',	'TR',	'TM',	'TC',	'TV',	'VI',	'UG',	'UA',	'AE',	'GB',	'US',	'UY',	'UZ',	'VU',	'VA',	'VE',	'VN',	'WF',	'EH',	'YE',	'ZM',	'ZW')
                            THEN 1
                        ELSE 0
                    END
                ) / COUNT_BIG(*)
            END AS actual_value,
            analyzed_table.[country] AS grouping_level_1,
            analyzed_table.[state] AS grouping_level_2,
            DATEFROMPARTS(YEAR(CAST(analyzed_table.[col_event_timestamp] AS date)), MONTH(CAST(analyzed_table.[col_event_timestamp] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[col_event_timestamp] AS date)), MONTH(CAST(analyzed_table.[col_event_timestamp] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST(analyzed_table.[col_event_timestamp] AS date)), MONTH(CAST(analyzed_table.[col_event_timestamp] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[col_event_timestamp]), 0)
        ORDER BY level_1, level_2DATEFROMPARTS(YEAR(CAST(analyzed_table.[col_event_timestamp] AS date)), MONTH(CAST(analyzed_table.[col_event_timestamp] AS date)), 1)
        
            
        ```
    






___
