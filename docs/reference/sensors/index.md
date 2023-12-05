# Sensors

**This is a list of sensors in DQOps broken down by category and a brief description of what they do.**


## Table sensors


### **accuracy**  

| Sensor name | Description |
|-------------|-------------|
|[total_row_count_match_percent](./table/accuracy-table-sensors/#total-row-count-match-percent)|Table level sensor that calculates the percentage of the difference of the total row count of all rows in the tested table and the total row count of the other (reference) table.|




### **availability**  

| Sensor name | Description |
|-------------|-------------|
|[table_availability](./table/availability-table-sensors/#table-availability)|Table availability sensor runs a simple table scan query to detect if the table is queryable. This sensor returns 0.0 when no failure was detected or 1.0 when a failure was detected.|




### **schema**  

| Sensor name | Description |
|-------------|-------------|
|[column_count](./table/schema-table-sensors/#column-count)|Table schema data quality sensor that reads the metadata from a monitored data source and counts the number of columns.|
|[column_list_ordered_hash](./table/schema-table-sensors/#column-list-ordered-hash)|Table schema data quality sensor detects if the list and order of columns have changed on the table. The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns and the order of the columns.|
|[column_list_unordered_hash](./table/schema-table-sensors/#column-list-unordered-hash)|Table schema data quality sensor detects if the list of columns have changed on the table. The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns, but not on the order of columns.|
|[column_types_hash](./table/schema-table-sensors/#column-types-hash)|Table schema data quality sensor detects if the list of columns has changed or any of the column has a new data type, length, scale, precision or nullability. The sensor calculates a hash of the list of column names and all components of the column&#x27;s type (the type name, length, scale, precision, nullability). The hash value does not depend on the order of columns.|




### **sql**  

| Sensor name | Description |
|-------------|-------------|
|[sql_aggregated_expression](./table/sql-table-sensors/#sql-aggregated-expression)|Table level sensor that executes a given SQL expression on a table.|
|[sql_condition_failed_count](./table/sql-table-sensors/#sql-condition-failed-count)|Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.|
|[sql_condition_failed_percent](./table/sql-table-sensors/#sql-condition-failed-percent)|Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.|
|[sql_condition_passed_count](./table/sql-table-sensors/#sql-condition-passed-count)|Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.|
|[sql_condition_passed_percent](./table/sql-table-sensors/#sql-condition-passed-percent)|Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.|




### **timeliness**  

| Sensor name | Description |
|-------------|-------------|
|[data_freshness](./table/timeliness-table-sensors/#data-freshness)|Table sensor that runs a query calculating maximum days since the most recent event.|
|[data_ingestion_delay](./table/timeliness-table-sensors/#data-ingestion-delay)|Table sensor that runs a query calculating the time difference in days between the most recent transaction timestamp and the most recent data loading timestamp.|
|[data_staleness](./table/timeliness-table-sensors/#data-staleness)|Table sensor that runs a query calculating the time difference in days between the current date and most recent data loading timestamp (staleness).|
|[partition_reload_lag](./table/timeliness-table-sensors/#partition-reload-lag)|Table sensor that runs a query calculating maximum difference in days between ingestion timestamp and event timestamp rows.|




### **volume**  

| Sensor name | Description |
|-------------|-------------|
|[row_count](./table/volume-table-sensors/#row-count)|Table sensor that executes a row count query.|














































## Column sensors




















### **accuracy**  

| Sensor name | Description |
|-------------|-------------|
|[total_average_match_percent](./column/accuracy-column-sensors/#total-average-match-percent)|Column level sensor that calculates the percentage of the difference in average of a column in a table and average of a column of another table.|
|[total_max_match_percent](./column/accuracy-column-sensors/#total-max-match-percent)|Column level sensor that calculates the percentage of the difference in max of a column in a table and max of a column of another table.|
|[total_min_match_percent](./column/accuracy-column-sensors/#total-min-match-percent)|Column level sensor that calculates the percentage of the difference in min of a column in a table and min of a column of another table.|
|[total_not_null_count_match_percent](./column/accuracy-column-sensors/#total-not-null-count-match-percent)|Column level sensor that calculates the percentage of the difference in row count of a column in a table and row count of a column of another table.|
|[total_sum_match_percent](./column/accuracy-column-sensors/#total-sum-match-percent)|Column level sensor that calculates the percentage of the difference in sum of a column in a table and sum of a column of another table.|




### **bool**  

| Sensor name | Description |
|-------------|-------------|
|[false_percent](./column/bool-column-sensors/#false-percent)|Column level sensor that calculates the percentage of rows with a false value in a column.|
|[true_percent](./column/bool-column-sensors/#true-percent)|Column level sensor that calculates the percentage of rows with a true value in a column.|




### **datatype**  

| Sensor name | Description |
|-------------|-------------|
|[string_datatype_detect](./column/datatype-column-sensors/#string-datatype-detect)|Column level sensor that analyzes all values in a text column and detects the data type of the values. The sensor returns a value that identifies the detected data type of column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|




### **datetime**  

| Sensor name | Description |
|-------------|-------------|
|[date_match_format_percent](./column/datetime-column-sensors/#date-match-format-percent)|Column level sensor that calculates the percentage of values that does fit a given date regex in a column.|
|[date_values_in_future_percent](./column/datetime-column-sensors/#date-values-in-future-percent)|Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.|
|[value_in_range_date_percent](./column/datetime-column-sensors/#value-in-range-date-percent)|Column level sensor that calculates the percent of non-negative values in a column.|




### **integrity**  

| Sensor name | Description |
|-------------|-------------|
|[foreign_key_match_percent](./column/integrity-column-sensors/#foreign-key-match-percent)|Column level sensor that calculates the percentage of values that match values in column of another table.|
|[foreign_key_not_match_count](./column/integrity-column-sensors/#foreign-key-not-match-count)|Column level sensor that calculates the count of values that does not match values in column of another table.|




### **nulls**  

| Sensor name | Description |
|-------------|-------------|
|[not_null_count](./column/nulls-column-sensors/#not-null-count)|Column-level sensor that calculates the number of rows with not null values.|
|[not_null_percent](./column/nulls-column-sensors/#not-null-percent)|Column level sensor that calculates the percentage of not null values in a column.|
|[null_count](./column/nulls-column-sensors/#null-count)|Column-level sensor that calculates the number of rows with null values.|
|[null_percent](./column/nulls-column-sensors/#null-percent)|Column-level sensor that calculates the percentage of rows with null values.|




### **numeric**  

| Sensor name | Description |
|-------------|-------------|
|[expected_numbers_in_use_count](./column/numeric-column-sensors/#expected-numbers-in-use-count)|Column level sensor that counts how many expected numeric values are used in a tested column. Finds unique column values from the set of expected numeric values and counts them. This sensor is useful to analyze numeric columns that have a low number of unique values and it should be tested if all possible values from the list of expected values are used in any row. The typical types of tested columns are numeric status or type columns.|
|[invalid_latitude_count](./column/numeric-column-sensors/#invalid-latitude-count)|Column level sensor that counts invalid latitude in a column.|
|[invalid_longitude_count](./column/numeric-column-sensors/#invalid-longitude-count)|Column level sensor that counts invalid longitude in a column.|
|[mean](./column/numeric-column-sensors/#mean)|Column level sensor that counts the average (mean) of values in a column.|
|[negative_count](./column/numeric-column-sensors/#negative-count)|Column level sensor that counts negative values in a column.|
|[negative_percent](./column/numeric-column-sensors/#negative-percent)|Column level sensor that counts percentage of negative values in a column.|
|[non_negative_count](./column/numeric-column-sensors/#non-negative-count)|Column level sensor that counts non negative values in a column.|
|[non_negative_percent](./column/numeric-column-sensors/#non-negative-percent)|Column level sensor that calculates the percent of non-negative values in a column.|
|[number_value_in_set_percent](./column/numeric-column-sensors/#number-value-in-set-percent)|Column level sensor that calculates the percentage of rows for which the tested numeric column contains a value from the list of expected values. Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value). This sensor is useful for checking numeric columns that store numeric codes (such as status codes) that the only values found in the column are from a set of expected values.|
|[percentile](./column/numeric-column-sensors/#percentile)|Column level sensor that finds the median in a given column.|
|[population_stddev](./column/numeric-column-sensors/#population-stddev)|Column level sensor that calculates population standard deviation in a given column.|
|[population_variance](./column/numeric-column-sensors/#population-variance)|Column level sensor that calculates population variance in a given column.|
|[sample_stddev](./column/numeric-column-sensors/#sample-stddev)|Column level sensor that calculates sample standard deviation in a given column.|
|[sample_variance](./column/numeric-column-sensors/#sample-variance)|Column level sensor that calculates sample variance in a given column.|
|[sum](./column/numeric-column-sensors/#sum)|Column level sensor that counts the sum of values in a column.|
|[valid_latitude_percent](./column/numeric-column-sensors/#valid-latitude-percent)|Column level sensor that counts percentage of valid latitude in a column.|
|[valid_longitude_percent](./column/numeric-column-sensors/#valid-longitude-percent)|Column level sensor that counts percentage of valid longitude in a column.|
|[value_above_max_value_count](./column/numeric-column-sensors/#value-above-max-value-count)|Column level sensor that calculates the count of values that are above than a given value in a column.|
|[value_above_max_value_percent](./column/numeric-column-sensors/#value-above-max-value-percent)|Column level sensor that calculates the percentage of values that are above than a given value in a column.|
|[value_below_min_value_count](./column/numeric-column-sensors/#value-below-min-value-count)|Column level sensor that calculates the count of values that are below than a given value in a column.|
|[value_below_min_value_percent](./column/numeric-column-sensors/#value-below-min-value-percent)|Column level sensor that calculates the percentage of values that are below than a given value in a column.|
|[values_in_range_integers_percent](./column/numeric-column-sensors/#values-in-range-integers-percent)|Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions. The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).|
|[values_in_range_numeric_percent](./column/numeric-column-sensors/#values-in-range-numeric-percent)|Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions. The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).|




### **pii**  

| Sensor name | Description |
|-------------|-------------|
|[contains_email_percent](./column/pii-column-sensors/#contains-email-percent)|Column level sensor that calculates the percentage of rows with a valid email value in a column.|
|[contains_ip4_percent](./column/pii-column-sensors/#contains-ip4-percent)|Column level sensor that calculates the percentage of rows with a valid IP4 value in a column.|
|[contains_ip6_percent](./column/pii-column-sensors/#contains-ip6-percent)|Column level sensor that calculates the percentage of rows with a valid IP6 value in a column.|
|[contains_usa_phone_percent](./column/pii-column-sensors/#contains-usa-phone-percent)|Column level sensor that calculates the percent of values that contains a USA phone number in a column.|
|[contains_usa_zipcode_percent](./column/pii-column-sensors/#contains-usa-zipcode-percent)|Column level sensor that calculates the percent of values that contain a USA ZIP code number in a column.|




### **range**  

| Sensor name | Description |
|-------------|-------------|
|[max_value](./column/range-column-sensors/#max-value)|Column level sensor that counts maximum value in a column.|
|[min_value](./column/range-column-sensors/#min-value)|Column level sensor that counts minimum value in a column.|




### **sampling**  

| Sensor name | Description |
|-------------|-------------|
|[column_samples](./column/sampling-column-sensors/#column-samples)|Column level sensor that retrieves a column value samples. Column value sampling is used in profiling and in capturing error samples for failed data quality checks.|




### **schema**  

| Sensor name | Description |
|-------------|-------------|
|[column_exists](./column/schema-column-sensors/#column-exists)|Column level data quality sensor that reads the metadata of the table from the data source and checks if the column name exists on the table. Returns 1.0 when the column was found, 0.0 when the column is missing.|
|[column_type_hash](./column/schema-column-sensors/#column-type-hash)|Column level data quality sensor that reads the metadata of the table from the data source and calculates a hash of the detected data type (also including the length, scale and precision) of the target colum. Returns a 15-16 decimal digit hash of the column data type.|




### **sql**  

| Sensor name | Description |
|-------------|-------------|
|[sql_aggregated_expression](./column/sql-column-sensors/#sql-aggregated-expression)|Column level sensor that executes a given SQL expression on a column.|
|[sql_condition_failed_count](./column/sql-column-sensors/#sql-condition-failed-count)|Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.|
|[sql_condition_failed_percent](./column/sql-column-sensors/#sql-condition-failed-percent)|Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.|
|[sql_condition_passed_count](./column/sql-column-sensors/#sql-condition-passed-count)|Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.|
|[sql_condition_passed_percent](./column/sql-column-sensors/#sql-condition-passed-percent)|Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.|




### **strings**  

| Sensor name | Description |
|-------------|-------------|
|[expected_strings_in_top_values_count](./column/strings-column-sensors/#expected-strings-in-top-values-count)|Column level sensor that counts how many expected string values are among the TOP most popular values in the column. The sensor will first count the number of occurrences of each column&#x27;s value and will pick the TOP X most popular values (configurable by the &#x27;top&#x27; parameter). Then, it will compare the list of most popular values to the given list of expected values that should be most popular. This sensor will return the number of expected values that were found within the &#x27;top&#x27; most popular column values. This sensor is useful for analyzing string columns that have several very popular values, these could be the country codes of the countries with the most number of customers. The sensor can detect if any of the most popular value (an expected value) is no longer one of the top X most popular values.|
|[expected_strings_in_use_count](./column/strings-column-sensors/#expected-strings-in-use-count)|Column level sensor that counts how many expected string values are used in a tested column. Finds unique column values from the set of expected string values and counts them. This sensor is useful to analyze string columns that have a low number of unique values and it should be tested if all possible values from the list of expected values are used in any row. The typical type of columns analyzed using this sensor are currency, country, status or gender columns.|
|[string_boolean_placeholder_percent](./column/strings-column-sensors/#string-boolean-placeholder-percent)|Column level sensor that calculates the number of rows with a boolean placeholder string column value.|
|[string_empty_count](./column/strings-column-sensors/#string-empty-count)|Column level sensor that calculates the number of rows with an empty string.|
|[string_empty_percent](./column/strings-column-sensors/#string-empty-percent)|Column level sensor that calculates the percentage of rows with an empty string.|
|[string_invalid_email_count](./column/strings-column-sensors/#string-invalid-email-count)|Column level sensor that calculates the number of rows with an invalid emails value in a column.|
|[string_invalid_ip4_address_count](./column/strings-column-sensors/#string-invalid-ip4-address-count)|Column level sensor that calculates the number of rows with an invalid IP4 address value in a column.|
|[string_invalid_ip6_address_count](./column/strings-column-sensors/#string-invalid-ip6-address-count)|Column level sensor that calculates the number of rows with an invalid IP6 address value in a column.|
|[string_invalid_uuid_count](./column/strings-column-sensors/#string-invalid-uuid-count)|Column level sensor that calculates the number of rows with an invalid uuid value in a column.|
|[string_length_above_max_length_count](./column/strings-column-sensors/#string-length-above-max-length-count)|Column level sensor that calculates the count of values that are longer than a given length in a column.|
|[string_length_above_max_length_percent](./column/strings-column-sensors/#string-length-above-max-length-percent)|Column level sensor that calculates the percentage of values that are longer than a given length in a column.|
|[string_length_below_min_length_count](./column/strings-column-sensors/#string-length-below-min-length-count)|Column level sensor that calculates the count of values that are shorter than a given length in a column.|
|[string_length_below_min_length_percent](./column/strings-column-sensors/#string-length-below-min-length-percent)|Column level sensor that calculates the percentage of values that are shorter than a given length in a column.|
|[string_length_in_range_percent](./column/strings-column-sensors/#string-length-in-range-percent)|Column level sensor that calculates the percentage of strings with a length below the indicated length in a column.|
|[string_match_date_regex_percent](./column/strings-column-sensors/#string-match-date-regex-percent)|Column level sensor that calculates the percentage of values that does fit a given date regex in a column.|
|[string_match_name_regex_percent](./column/strings-column-sensors/#string-match-name-regex-percent)|Column level sensor that calculates the percentage of values that does fit a given name regex in a column.|
|[string_match_regex_percent](./column/strings-column-sensors/#string-match-regex-percent)|Column level sensor that calculates the percent of values that fit to a regex in a column.|
|[string_max_length](./column/strings-column-sensors/#string-max-length)|Column level sensor that ensures that the length of string in a column does not exceed the maximum accepted length.|
|[string_mean_length](./column/strings-column-sensors/#string-mean-length)|Column level sensor that ensures that the length of string in a column does not exceed the mean accepted length.|
|[string_min_length](./column/strings-column-sensors/#string-min-length)|Column level sensor that ensures that the length of string in a column does not exceed the minimum accepted length.|
|[string_not_match_date_regex_count](./column/strings-column-sensors/#string-not-match-date-regex-count)|Column level sensor that calculates the number of values that does not fit to a date regex in a column.|
|[string_not_match_regex_count](./column/strings-column-sensors/#string-not-match-regex-count)|Column level sensor that calculates the number of values that does not fit to a regex in a column.|
|[string_null_placeholder_count](./column/strings-column-sensors/#string-null-placeholder-count)|Column level sensor that calculates the number of rows with a null placeholder string column value.|
|[string_null_placeholder_percent](./column/strings-column-sensors/#string-null-placeholder-percent)|Column level sensor that calculates the percentage of rows with a null placeholder string column value.|
|[string_parsable_to_float_percent](./column/strings-column-sensors/#string-parsable-to-float-percent)|Column level sensor that calculates the percentage of rows with parsable to float string column value.|
|[string_parsable_to_integer_percent](./column/strings-column-sensors/#string-parsable-to-integer-percent)|Column level sensor that calculates the number of rows with parsable to integer string column value.|
|[string_surrounded_by_whitespace_count](./column/strings-column-sensors/#string-surrounded-by-whitespace-count)|Column level sensor that calculates the number of rows with string surrounded by whitespace column value.|
|[string_surrounded_by_whitespace_percent](./column/strings-column-sensors/#string-surrounded-by-whitespace-percent)|Column level sensor that calculates the percentage of rows with string surrounded by whitespace column value.|
|[string_valid_country_code_percent](./column/strings-column-sensors/#string-valid-country-code-percent)|Column level sensor that calculates the percentage of rows with a valid country code string column value.|
|[string_valid_currency_code_percent](./column/strings-column-sensors/#string-valid-currency-code-percent)|Column level sensor that calculates the percentage of rows with a valid currency code string column value.|
|[string_valid_date_percent](./column/strings-column-sensors/#string-valid-date-percent)|Column level sensor that ensures that there is at least a minimum percentage of valid dates in a monitored column..|
|[string_valid_uuid_percent](./column/strings-column-sensors/#string-valid-uuid-percent)|Column level sensor that calculates the percentage of rows with a valid UUID value in a column.|
|[string_value_in_set_percent](./column/strings-column-sensors/#string-value-in-set-percent)|Column level sensor that calculates the percentage of rows for which the tested string (text) column contains a value from the list of expected values. Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value). This sensor is useful for testing that a string column with a low number of unique values (country, currency, state, gender, etc.) contains only values from a set of expected values.|
|[string_whitespace_count](./column/strings-column-sensors/#string-whitespace-count)|Column level sensor that calculates the number of rows with an whitespace string column value.|
|[string_whitespace_percent](./column/strings-column-sensors/#string-whitespace-percent)|Column level sensor that calculates the percentage of rows with a whitespace string column value.|




### **uniqueness**  

| Sensor name | Description |
|-------------|-------------|
|[distinct_count](./column/uniqueness-column-sensors/#distinct-count)|Column level sensor that calculates the number of unique non-null values.|
|[distinct_percent](./column/uniqueness-column-sensors/#distinct-percent)|Column level sensor that calculates the percentage of unique values in a column.|
|[duplicate_count](./column/uniqueness-column-sensors/#duplicate-count)|Column level sensor that calculates the number of duplicate values in a given column.|
|[duplicate_percent](./column/uniqueness-column-sensors/#duplicate-percent)|Column level sensor that calculates the percentage of rows that are duplicates.|



