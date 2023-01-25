#column sensors:

##category: <b>pii</b>
___

###<b>contains_usa_zipcode_percent</b>
Column level sensor that calculates the percent of values that contain a USA zip code number in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_zipcode_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_zipcode_percent/snowflake.sql.jinja2"
    ```

___

###<b>contains_usa_phone_percent</b>
Column level sensor that calculates the percent of values that contains a USA phone number in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_phone_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_phone_percent/snowflake.sql.jinja2"
    ```

___


##category: <b>datetime</b>
___

###<b>date_values_in_future_percent</b>
Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/datetime/date_values_in_future_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/datetime/date_values_in_future_percent/snowflake.sql.jinja2"
    ```

___


##category: <b>bool</b>
___

###<b>true_percent</b>
Column level sensor that calculates the percentage of rows with a true value in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/bool/true_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/bool/true_percent/snowflake.sql.jinja2"
    ```

___

###<b>false_percent</b>
Column level sensor that calculates the percentage of rows with a false value in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/bool/false_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/bool/false_percent/snowflake.sql.jinja2"
    ```

___


##category: <b>strings</b>
___

###<b>string_parsable_to_integer_percent</b>
Column level sensor that calculates the number of rows with parsable to integer string column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_parsable_to_integer_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_parsable_to_integer_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_valid_email_percent</b>
Column level sensor that calculates the percentage of rows with a valid email value in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_email_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_email_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_valid_usa_phone_percent</b>
Column level sensor that calculates the percent of values that fit to a USA phone regex in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_usa_phone_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_usa_phone_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_length_above_min_length_count</b>
Column level sensor that calculates the count of values that are not shorter than a given length in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_length_above_min_length_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_length_above_min_length_count/snowflake.sql.jinja2"
    ```

___

###<b>string_whitespace_count</b>
Column level sensor that calculates the number of rows with an whitespace string column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_whitespace_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_whitespace_count/snowflake.sql.jinja2"
    ```

___

###<b>string_valid_usa_zipcode_percent</b>
Column level sensor that calculates the percent of values that fit to a USA ZIP code regex in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_usa_zipcode_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_usa_zipcode_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_boolean_placeholder_percent</b>
Column level sensor that calculates the number of rows with a boolean placeholder string column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_boolean_placeholder_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_boolean_placeholder_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_valid_country_code_percent</b>
Column level sensor that calculates the number of rows with a valid country code string column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_country_code_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_country_code_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_min_length</b>
Column level sensor that calculates the number of rows with a null column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_min_length/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_min_length/snowflake.sql.jinja2"
    ```

___

###<b>string_max_length</b>
Column level sensor that calculates the number of rows with a null column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_max_length/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_max_length/snowflake.sql.jinja2"
    ```

___

###<b>string_invalid_ip6_address_count</b>
Column level sensor that calculates the number of rows with an invalid IP6 address value in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip6_address_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip6_address_count/snowflake.sql.jinja2"
    ```

___

###<b>string_invalid_ip4_address_count</b>
Column level sensor that calculates the number of rows with an invalid IP4 address value in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip4_address_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip4_address_count/snowflake.sql.jinja2"
    ```

___

###<b>string_in_set_count</b>
Column level sensor that calculates the number of rows with a null column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_in_set_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_in_set_count/snowflake.sql.jinja2"
    ```

___

###<b>string_regex_match_percent</b>
Column level sensor that calculates the percent of values that fit to a regex in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_regex_match_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_regex_match_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_valid_currency_code_percent</b>
Column level sensor that calculates the number of rows with a valid currency code string column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_currency_code_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_currency_code_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_not_match_regex_count</b>
Column level sensor that calculates the number of values that does not fit to a regex in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_regex_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_regex_count/snowflake.sql.jinja2"
    ```

___

###<b>string_whitespace_percent</b>
Column level sensor that calculates the number of rows with a whitespace string column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_whitespace_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_whitespace_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_valid_ip6_address_percent</b>
Column level sensor that calculates the percentage of rows with a valid IP6 address value in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_ip6_address_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_ip6_address_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_mean_length</b>
Column level sensor that calculates the number of rows with a null column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_mean_length/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_mean_length/snowflake.sql.jinja2"
    ```

___

###<b>string_valid_uuid_percent</b>
Column level sensor that calculates the percentage of rows with a valid UUID value in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_uuid_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_uuid_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_surrounded_by_whitespace_percent</b>
Column level sensor that calculates the number of rows with string surrounded by whitespace column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_surrounded_by_whitespace_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_surrounded_by_whitespace_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_in_set_percent</b>
Column level sensor that calculates the number of rows with a null column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_in_set_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_in_set_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_null_placeholder_count</b>
Column level sensor that calculates the number of rows with a null placeholder string column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_null_placeholder_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_null_placeholder_count/snowflake.sql.jinja2"
    ```

___

###<b>string_match_date_regex_percent</b>
Column level sensor that calculates the percentage of values that does fit a given date regex in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_match_date_regex_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_match_date_regex_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_valid_ip4_address_percent</b>
Column level sensor that calculates the percentage of rows with a valid IP4 address value in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_ip4_address_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_ip4_address_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_valid_date_percent</b>
Column level sensor that calculates the number of rows with a null column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_date_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_date_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_most_popular_values</b>
Column level sensor that calculates the number of rows with a null column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_most_popular_values/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_most_popular_values/snowflake.sql.jinja2"
    ```

___

###<b>string_invalid_email_count</b>
Column level sensor that calculates the number of rows with an invalid emails value in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_email_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_email_count/snowflake.sql.jinja2"
    ```

___

###<b>string_match_name_regex_percent</b>
Column level sensor that calculates the percentage of values that does fit a given name regex in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_match_name_regex_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_match_name_regex_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_empty_count</b>
Column level sensor that calculates the number of rows with an empty string column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_empty_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_empty_count/snowflake.sql.jinja2"
    ```

___

###<b>string_invalid_uuid_count</b>
Column level sensor that calculates the number of rows with an invalid uuid value in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_uuid_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_uuid_count/snowflake.sql.jinja2"
    ```

___

###<b>string_not_match_date_regex_count</b>
Column level sensor that calculates the number of values that does not fit to a date regex in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_date_regex_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_date_regex_count/snowflake.sql.jinja2"
    ```

___

###<b>string_parsable_to_float_percent</b>
Column level sensor that calculates the number of rows with parsable to float string column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_parsable_to_float_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_parsable_to_float_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_surrounded_by_whitespace_count</b>
Column level sensor that calculates the number of rows with string surrounded by whitespace column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_surrounded_by_whitespace_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_surrounded_by_whitespace_count/snowflake.sql.jinja2"
    ```

___

###<b>string_empty_percent</b>
Column level sensor that calculates the percentage of rows with an empty string column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_empty_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_empty_percent/snowflake.sql.jinja2"
    ```

___

###<b>string_null_placeholder_percent</b>
Column level sensor that calculates the number of rows with a null placeholder string column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_null_placeholder_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_null_placeholder_percent/snowflake.sql.jinja2"
    ```

___


##category: <b>uniqueness</b>
___

###<b>duplicate_count</b>
Column level sensor that calculates the number of rows with a null column value.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_count/snowflake.sql.jinja2"
    ```

___

###<b>duplicate_percent</b>
Column level sensor that calculates the percentage of rows that are duplicates.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_percent/snowflake.sql.jinja2"
    ```

___

###<b>unique_count</b>
Column level sensor that calculates the number of unique non-null values.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/uniqueness/unique_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/uniqueness/unique_count/snowflake.sql.jinja2"
    ```

___


##category: <b>nulls</b>
___

###<b>null_percent</b>
Column-level sensor that calculates the percentage of rows with null values.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/nulls/null_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/nulls/null_percent/snowflake.sql.jinja2"
    ```

___

###<b>not_null_count</b>
Column-level sensor that calculates the number of rows with not null values.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/nulls/not_null_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/nulls/not_null_count/snowflake.sql.jinja2"
    ```

___

###<b>null_count</b>
Column-level sensor that calculates the number of rows with null values.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/nulls/null_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/nulls/null_count/snowflake.sql.jinja2"
    ```

___


##category: <b>range</b>
___

###<b>min_value</b>
Column level sensor that finds the minimum value. It works on any data type that supports the MIN functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/range/min_value/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/range/min_value/snowflake.sql.jinja2"
    ```

___

###<b>max_value</b>
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/range/max_value/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/range/max_value/snowflake.sql.jinja2"
    ```

___


##category: <b>numeric</b>
___

###<b>stddev_samp</b>
Column level sensor that counts negative values in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/stddev_samp/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/stddev_samp/snowflake.sql.jinja2"
    ```

___

###<b>sum</b>
Column level sensor that counts negative values in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/sum/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/sum/snowflake.sql.jinja2"
    ```

___

###<b>values_in_range_numeric_percent</b>
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_numeric_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_numeric_percent/snowflake.sql.jinja2"
    ```

___

###<b>numbers_in_set_percent</b>
Column level sensor that calculates the percentage of values that are members of a given set.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/numbers_in_set_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/numbers_in_set_percent/snowflake.sql.jinja2"
    ```

___

###<b>negative_count</b>
Column level sensor that counts negative values in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/negative_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/negative_count/snowflake.sql.jinja2"
    ```

___

###<b>negative_percent</b>
Column level sensor that counts percentage of negative values in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/negative_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/negative_percent/snowflake.sql.jinja2"
    ```

___

###<b>var_pop</b>
Column level sensor that counts negative values in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/var_pop/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/var_pop/snowflake.sql.jinja2"
    ```

___

###<b>numbers_in_set_count</b>
Column level sensor that counts values that are members of a given set.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/numbers_in_set_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/numbers_in_set_count/snowflake.sql.jinja2"
    ```

___

###<b>var_samp</b>
Column level sensor that counts negative values in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/var_samp/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/var_samp/snowflake.sql.jinja2"
    ```

___

###<b>values_in_range_integers_percent</b>
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_integers_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_integers_percent/snowflake.sql.jinja2"
    ```

___

###<b>stddev_pop</b>
Column level sensor that counts negative values in a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/stddev_pop/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/stddev_pop/snowflake.sql.jinja2"
    ```

___


##category: <b>sql</b>
___

###<b>sql_condition_passed_count</b>
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_passed_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_passed_count/snowflake.sql.jinja2"
    ```

___

###<b>sql_aggregated_expression</b>
Column level sensor that executes a given SQL expression on a column.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_aggregated_expression/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_aggregated_expression/snowflake.sql.jinja2"
    ```

___

###<b>sql_condition_failed_percent</b>
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_percent/snowflake.sql.jinja2"
    ```

___

###<b>sql_condition_failed_count</b>
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_count/snowflake.sql.jinja2"
    ```

___

###<b>sql_condition_passed_percent</b>
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.
<br/>
<br/>
<b><u>Jinja Template:</u></b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_passed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_passed_percent/snowflake.sql.jinja2"
    ```

___

