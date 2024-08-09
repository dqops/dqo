---
title: Sensors/column
---
# Sensors/column

**This is a list of column sensors in DQOps broken down by category and a brief description of what they do.**






### **accepted_values**

| Sensor name | Description |
|-------------|-------------|
|[*expected_numbers_in_use_count*](./accepted_values-column-sensors.md#expected-numbers-in-use-count)|Column level sensor that counts how many expected numeric values are used in a tested column. Finds unique column values from the set of expected numeric values and counts them. This sensor is useful to analyze numeric columns that have a low number of unique values and it should be tested if all possible values from the list of expected values are used in any row. The typical types of tested columns are numeric status or type columns.|
|[*expected_text_values_in_use_count*](./accepted_values-column-sensors.md#expected-text-values-in-use-count)|Column level sensor that counts how many expected string values are used in a tested column. Finds unique column values from the set of expected string values and counts them. This sensor is useful to analyze string columns that have a low number of unique values and it should be tested if all possible values from the list of expected values are used in any row. The typical type of columns analyzed using this sensor are currency, country, status or gender columns.|
|[*expected_texts_in_top_values_count*](./accepted_values-column-sensors.md#expected-texts-in-top-values-count)|Column level sensor that counts how many expected string values are among the TOP most popular values in the column. The sensor will first count the number of occurrences of each column&#x27;s value and will pick the TOP X most popular values (configurable by the &#x27;top&#x27; parameter). Then, it will compare the list of most popular values to the given list of expected values that should be most popular. This sensor will return the number of expected values that were found within the &#x27;top&#x27; most popular column values. This sensor is useful in analyzing string columns with frequently occurring values, such as country codes for countries with the most customers. The sensor can detect if any of the most popular value (an expected value) is no longer one of the top X most popular values.|
|[*number_found_in_set_percent*](./accepted_values-column-sensors.md#number-found-in-set-percent)|Column level sensor that calculates the percentage of rows for which the tested numeric column contains a value from the list of expected values. Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value). This sensor is useful for checking numeric columns that store numeric codes (such as status codes) that the only values found in the column are from a set of expected values.|
|[*text_found_in_set_percent*](./accepted_values-column-sensors.md#text-found-in-set-percent)|Column level sensor that calculates the percentage of rows for which the tested string (text) column contains a value from the list of expected values. Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value). This sensor is useful for testing that a string column with a low number of unique values (country, currency, state, gender, etc.) contains only values from a set of expected values.|
|[*text_valid_country_code_percent*](./accepted_values-column-sensors.md#text-valid-country-code-percent)|Column level sensor that calculates the percentage of rows with text values with a valid country codes in an analyzed column.|
|[*text_valid_currency_code_percent*](./accepted_values-column-sensors.md#text-valid-currency-code-percent)|Column level sensor that calculates the percentage of rows with text values that are valid currency codes in an analyzed column.|




### **accuracy**

| Sensor name | Description |
|-------------|-------------|
|[*total_average_match_percent*](./accuracy-column-sensors.md#total-average-match-percent)|Column level sensor that calculates the percentage of the difference in average of a column in a table and average of a column of another table.|
|[*total_max_match_percent*](./accuracy-column-sensors.md#total-max-match-percent)|Column level sensor that calculates the percentage of the difference in max of a column in a table and max of a column of another table.|
|[*total_min_match_percent*](./accuracy-column-sensors.md#total-min-match-percent)|Column level sensor that calculates the percentage of the difference in min of a column in a table and min of a column of another table.|
|[*total_not_null_count_match_percent*](./accuracy-column-sensors.md#total-not-null-count-match-percent)|Column level sensor that calculates the percentage of the difference in row count of a column in a table and row count of a column of another table.|
|[*total_sum_match_percent*](./accuracy-column-sensors.md#total-sum-match-percent)|Column level sensor that calculates the percentage of the difference in sum of a column in a table and sum of a column of another table.|




### **bool**

| Sensor name | Description |
|-------------|-------------|
|[*false_percent*](./bool-column-sensors.md#false-percent)|Column level sensor that calculates the percentage of rows with a false value in a column.|
|[*true_percent*](./bool-column-sensors.md#true-percent)|Column level sensor that calculates the percentage of rows with a true value in a column.|




### **conversions**

| Sensor name | Description |
|-------------|-------------|
|[*text_parsable_to_boolean_percent*](./conversions-column-sensors.md#text-parsable-to-boolean-percent)|Column level sensor that calculates the number of rows with a text value in a text column that is parsable to a boolean type or is a well-known boolean value placeholder: yes, no, true, false, t, f, y, n, 1, 0.|
|[*text_parsable_to_date_percent*](./conversions-column-sensors.md#text-parsable-to-date-percent)|Column level sensor that ensures that there is at least a minimum percentage of rows with a text value that is parsable to a date in an analyzed column.|
|[*text_parsable_to_float_percent*](./conversions-column-sensors.md#text-parsable-to-float-percent)|Column level sensor that calculates the percentage of rows with text values in an analyzed column that are parsable to a float (numeric) value.|
|[*text_parsable_to_integer_percent*](./conversions-column-sensors.md#text-parsable-to-integer-percent)|Column level sensor that calculates the percentage of rows with text values in an analyzed column that are parsable to an integer value.|




### **custom_sql**

| Sensor name | Description |
|-------------|-------------|
|[*import_custom_result*](./custom_sql-column-sensors.md#import-custom-result)|Column level sensor that uses a custom SQL SELECT statement to retrieve a result of running a custom data quality check on a column by a custom data quality check, hardcoded in the data pipeline. The result is retrieved by querying a separate **logging table**, whose schema is not fixed. The logging table should have columns that identify a table and a column for which they store custom data quality check results, and a *severity* column of the data quality issue. The SQL query that is configured in this external data quality results importer must be a complete SELECT statement that queries a dedicated logging table, created by the data engineering team.|
|[*sql_aggregated_expression*](./custom_sql-column-sensors.md#sql-aggregated-expression)|Column level sensor that executes a given SQL expression on a column.|
|[*sql_condition_failed_count*](./custom_sql-column-sensors.md#sql-condition-failed-count)|Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.|
|[*sql_condition_failed_percent*](./custom_sql-column-sensors.md#sql-condition-failed-percent)|Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.|
|[*sql_condition_passed_count*](./custom_sql-column-sensors.md#sql-condition-passed-count)|Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.|
|[*sql_condition_passed_percent*](./custom_sql-column-sensors.md#sql-condition-passed-percent)|Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.|




### **datatype**

| Sensor name | Description |
|-------------|-------------|
|[*string_datatype_detect*](./datatype-column-sensors.md#string-datatype-detect)|Column level sensor that analyzes all values in a text column and detects the data type of the values. The sensor returns a value that identifies the detected data type of column: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types.|




### **datetime**

| Sensor name | Description |
|-------------|-------------|
|[*date_in_range_percent*](./datetime-column-sensors.md#date-in-range-percent)|Column level sensor that finds the percentage of date values that are outside an accepted range. This sensor detects presence of fake or corrupted dates such as 1900-01-01 or 2099-12-31.|
|[*date_values_in_future_percent*](./datetime-column-sensors.md#date-values-in-future-percent)|Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.|
|[*text_match_date_format_percent*](./datetime-column-sensors.md#text-match-date-format-percent)|Column level sensor that calculates the percentage of text values that match an expected date format.|




### **integrity**

| Sensor name | Description |
|-------------|-------------|
|[*foreign_key_match_percent*](./integrity-column-sensors.md#foreign-key-match-percent)|Column level sensor that calculates the percentage of values that match values in a column of another dictionary table.|
|[*foreign_key_not_match_count*](./integrity-column-sensors.md#foreign-key-not-match-count)|Column level sensor that calculates the count of values that does not match values in a column of another dictionary table.|




### **nulls**

| Sensor name | Description |
|-------------|-------------|
|[*not_null_count*](./nulls-column-sensors.md#not-null-count)|Column-level sensor that calculates the number of rows with not null values.|
|[*not_null_percent*](./nulls-column-sensors.md#not-null-percent)|Column level sensor that calculates the percentage of not null values in a column.|
|[*null_count*](./nulls-column-sensors.md#null-count)|Column-level sensor that calculates the number of rows with null values.|
|[*null_percent*](./nulls-column-sensors.md#null-percent)|Column-level sensor that calculates the percentage of rows with null values.|




### **numeric**

| Sensor name | Description |
|-------------|-------------|
|[*integer_in_range_percent*](./numeric-column-sensors.md#integer-in-range-percent)|Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions. The returned data type matches the data type of the column (can return date, integer, string, datetime, etc.).|
|[*invalid_latitude_count*](./numeric-column-sensors.md#invalid-latitude-count)|Column level sensor that counts invalid latitude in a column.|
|[*invalid_longitude_count*](./numeric-column-sensors.md#invalid-longitude-count)|Column level sensor that counts invalid longitude in a column.|
|[*mean*](./numeric-column-sensors.md#mean)|Column level sensor that counts the average (mean) of values in a column.|
|[*negative_count*](./numeric-column-sensors.md#negative-count)|Column level sensor that counts negative values in a column.|
|[*negative_percent*](./numeric-column-sensors.md#negative-percent)|Column level sensor that counts percentage of negative values in a column.|
|[*non_negative_count*](./numeric-column-sensors.md#non-negative-count)|Column level sensor that counts non negative values in a column.|
|[*non_negative_percent*](./numeric-column-sensors.md#non-negative-percent)|Column level sensor that calculates the percent of non-negative values in a column.|
|[*number_above_max_value_count*](./numeric-column-sensors.md#number-above-max-value-count)|Column level sensor that calculates the count of values that are above than a given value in a column.|
|[*number_above_max_value_percent*](./numeric-column-sensors.md#number-above-max-value-percent)|Column level sensor that calculates the percentage of values that are above than a given value in a column.|
|[*number_below_min_value_count*](./numeric-column-sensors.md#number-below-min-value-count)|Column level sensor that calculates the count of values that are below than a given value in a column.|
|[*number_below_min_value_percent*](./numeric-column-sensors.md#number-below-min-value-percent)|Column level sensor that calculates the percentage of values that are below than a given value in a column.|
|[*number_in_range_percent*](./numeric-column-sensors.md#number-in-range-percent)|Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions. The returned data type matches the data type of the column (can return date, integer, string, datetime, etc.).|
|[*percentile*](./numeric-column-sensors.md#percentile)|Column level sensor that finds the median in a given column.|
|[*population_stddev*](./numeric-column-sensors.md#population-stddev)|Column level sensor that calculates population standard deviation in a given column.|
|[*population_variance*](./numeric-column-sensors.md#population-variance)|Column level sensor that calculates population variance in a given column.|
|[*sample_stddev*](./numeric-column-sensors.md#sample-stddev)|Column level sensor that calculates sample standard deviation in a given column.|
|[*sample_variance*](./numeric-column-sensors.md#sample-variance)|Column level sensor that calculates sample variance in a given column.|
|[*sum*](./numeric-column-sensors.md#sum)|Column level sensor that counts the sum of values in a column.|
|[*valid_latitude_percent*](./numeric-column-sensors.md#valid-latitude-percent)|Column level sensor that counts percentage of valid latitude in a column.|
|[*valid_longitude_percent*](./numeric-column-sensors.md#valid-longitude-percent)|Column level sensor that counts percentage of valid longitude in a column.|




### **patterns**

| Sensor name | Description |
|-------------|-------------|
|[*invalid_email_format_count*](./patterns-column-sensors.md#invalid-email-format-count)|Column level sensor that calculates the number of rows with an invalid emails value in a column.|
|[*invalid_email_format_percent*](./patterns-column-sensors.md#invalid-email-format-percent)|Column level sensor that calculates the number of rows with an invalid emails value in a column.|
|[*invalid_ip4_address_format_count*](./patterns-column-sensors.md#invalid-ip4-address-format-count)|Column level sensor that calculates the number of rows with an invalid IP4 address value in a column.|
|[*invalid_ip6_address_format_count*](./patterns-column-sensors.md#invalid-ip6-address-format-count)|Column level sensor that calculates the number of rows with an invalid IP6 address value in a column.|
|[*invalid_usa_phone_count*](./patterns-column-sensors.md#invalid-usa-phone-count)|Column level sensor that calculates the number of rows with a valid UUID value in a column.|
|[*invalid_usa_zipcode_count*](./patterns-column-sensors.md#invalid-usa-zipcode-count)|Column level sensor that calculates the number of rows with a valid UUID value in a column.|
|[*invalid_uuid_format_count*](./patterns-column-sensors.md#invalid-uuid-format-count)|Column level sensor that calculates the number of rows with an invalid uuid value in a column.|
|[*text_matching_date_patterns_percent*](./patterns-column-sensors.md#text-matching-date-patterns-percent)|Column level sensor that calculates the percentage of values that does fit a given date regex in a column.|
|[*text_matching_name_pattern_percent*](./patterns-column-sensors.md#text-matching-name-pattern-percent)|Column level sensor that calculates the percentage of values that does fit a given name regex in a column.|
|[*text_not_matching_date_pattern_count*](./patterns-column-sensors.md#text-not-matching-date-pattern-count)|Column level sensor that calculates the number of values that does not fit to a date regex in a column.|
|[*text_not_matching_regex_count*](./patterns-column-sensors.md#text-not-matching-regex-count)|Column level sensor that calculates the number of values that does not fit to a regex in a column.|
|[*texts_matching_regex_percent*](./patterns-column-sensors.md#texts-matching-regex-percent)|Column level sensor that calculates the percent of values that fit to a regex in a column.|
|[*valid_usa_phone_percent*](./patterns-column-sensors.md#valid-usa-phone-percent)|Column level sensor that calculates the percentage of rows with a valid UUID value in a column.|
|[*valid_usa_zipcode_percent*](./patterns-column-sensors.md#valid-usa-zipcode-percent)|Column level sensor that calculates the percentage of rows with a valid UUID value in a column.|
|[*valid_uuid_format_percent*](./patterns-column-sensors.md#valid-uuid-format-percent)|Column level sensor that calculates the percentage of rows with a valid UUID value in a column.|




### **pii**

| Sensor name | Description |
|-------------|-------------|
|[*contains_email_percent*](./pii-column-sensors.md#contains-email-percent)|Column level sensor that calculates the percentage of rows with a valid email value in a column.|
|[*contains_ip4_percent*](./pii-column-sensors.md#contains-ip4-percent)|Column level sensor that calculates the percentage of rows with a valid IP4 value in a column.|
|[*contains_ip6_percent*](./pii-column-sensors.md#contains-ip6-percent)|Column level sensor that calculates the percentage of rows with a valid IP6 value in a column.|
|[*contains_usa_phone_percent*](./pii-column-sensors.md#contains-usa-phone-percent)|Column level sensor that calculates the percent of values that contains a USA phone number in a column.|
|[*contains_usa_zipcode_percent*](./pii-column-sensors.md#contains-usa-zipcode-percent)|Column level sensor that calculates the percent of values that contain a USA ZIP code number in a column.|




### **range**

| Sensor name | Description |
|-------------|-------------|
|[*max_value*](./range-column-sensors.md#max-value)|Column level sensor that counts maximum value in a column.|
|[*min_value*](./range-column-sensors.md#min-value)|Column level sensor that counts minimum value in a column.|




### **sampling**

| Sensor name | Description |
|-------------|-------------|
|[*column_samples*](./sampling-column-sensors.md#column-samples)|Column level sensor that retrieves a column value samples. Column value sampling is used in profiling and in capturing error samples for failed data quality checks.|




### **schema**

| Sensor name | Description |
|-------------|-------------|
|[*column_exists*](./schema-column-sensors.md#column-exists)|Column level data quality sensor that reads the metadata of the table from the data source and checks if the column name exists on the table. Returns 1.0 when the column was found, 0.0 when the column is missing.|
|[*column_type_hash*](./schema-column-sensors.md#column-type-hash)|Column level data quality sensor that reads the metadata of the table from the data source and calculates a hash of the detected data type (also including the length, scale and precision) of the target colum. Returns a 15-16 decimal digit hash of the column data type.|




### **text**

| Sensor name | Description |
|-------------|-------------|
|[*text_length_above_max_length_count*](./text-column-sensors.md#text-length-above-max-length-count)|Column level sensor that calculates the count of text values that are longer than a given length in a column.|
|[*text_length_above_max_length_percent*](./text-column-sensors.md#text-length-above-max-length-percent)|Column level sensor that calculates the percentage of text values that are longer than a given length in a column.|
|[*text_length_below_min_length_count*](./text-column-sensors.md#text-length-below-min-length-count)|Column level sensor that calculates the count of text values that are shorter than a given length in a column.|
|[*text_length_below_min_length_percent*](./text-column-sensors.md#text-length-below-min-length-percent)|Column level sensor that calculates the percentage of text values that are shorter than a given length in a column.|
|[*text_length_in_range_percent*](./text-column-sensors.md#text-length-in-range-percent)|Column level sensor that calculates the percentage of text values with a length below the indicated length in a column.|
|[*text_max_length*](./text-column-sensors.md#text-max-length)|Column level sensor that ensures that the length of text values in a column does not exceed the maximum accepted length.|
|[*text_mean_length*](./text-column-sensors.md#text-mean-length)|Column level sensor that ensures that the length of text values in a column does not exceed the mean accepted length.|
|[*text_min_length*](./text-column-sensors.md#text-min-length)|Column level sensor that ensures that the length of text values in a column does not exceed the minimum accepted length.|




### **uniqueness**

| Sensor name | Description |
|-------------|-------------|
|[*distinct_count*](./uniqueness-column-sensors.md#distinct-count)|Column level sensor that calculates the number of unique non-null values.|
|[*distinct_percent*](./uniqueness-column-sensors.md#distinct-percent)|Column level sensor that calculates the percentage of unique values in a column.|
|[*duplicate_count*](./uniqueness-column-sensors.md#duplicate-count)|Column level sensor that calculates the number of duplicate values in a given column.|
|[*duplicate_percent*](./uniqueness-column-sensors.md#duplicate-percent)|Column level sensor that calculates the percentage of rows that are duplicates.|




### **whitespace**

| Sensor name | Description |
|-------------|-------------|
|[*empty_text_count*](./whitespace-column-sensors.md#empty-text-count)|Column level sensor that calculates the number of rows with an empty string.|
|[*empty_text_percent*](./whitespace-column-sensors.md#empty-text-percent)|Column level sensor that calculates the percentage of rows with an empty string.|
|[*null_placeholder_text_count*](./whitespace-column-sensors.md#null-placeholder-text-count)|Column level sensor that calculates the number of rows with a null placeholder string column value.|
|[*null_placeholder_text_percent*](./whitespace-column-sensors.md#null-placeholder-text-percent)|Column level sensor that calculates the percentage of rows with a null placeholder string column value.|
|[*text_surrounded_by_whitespace_count*](./whitespace-column-sensors.md#text-surrounded-by-whitespace-count)|Column level sensor that calculates the number of rows with text values that are surrounded by whitespace characters in an analyzed column.|
|[*text_surrounded_by_whitespace_percent*](./whitespace-column-sensors.md#text-surrounded-by-whitespace-percent)|Column level sensor that calculates the percentage of rows with text values that are surrounded by whitespace characters in an analyzed column.|
|[*whitespace_text_count*](./whitespace-column-sensors.md#whitespace-text-count)|Column level sensor that calculates the number of rows with a whitespace text column value.|
|[*whitespace_text_percent*](./whitespace-column-sensors.md#whitespace-text-percent)|Column level sensor that calculates the percentage of rows with a whitespace text column value.|





