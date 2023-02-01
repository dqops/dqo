# column

## **pii** column sensors
___

### **contains usa zipcode percent**
**Full sensor name**
```
column/pii/contains_usa_zipcode_percent
```
**Description**  
Column level sensor that calculates the percent of values that contain a USA zip code number in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_zipcode_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_zipcode_percent/snowflake.sql.jinja2"
    ```

___

### **contains usa phone percent**
**Full sensor name**
```
column/pii/contains_usa_phone_percent
```
**Description**  
Column level sensor that calculates the percent of values that contains a USA phone number in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_phone_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_phone_percent/snowflake.sql.jinja2"
    ```

___


## **datetime** column sensors
___

### **date values in future percent**
**Full sensor name**
```
column/datetime/date_values_in_future_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/datetime/date_values_in_future_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/datetime/date_values_in_future_percent/snowflake.sql.jinja2"
    ```

___


## **bool** column sensors
___

### **true percent**
**Full sensor name**
```
column/bool/true_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a true value in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/bool/true_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/bool/true_percent/snowflake.sql.jinja2"
    ```

___

### **false percent**
**Full sensor name**
```
column/bool/false_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a false value in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/bool/false_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/bool/false_percent/snowflake.sql.jinja2"
    ```

___


## **strings** column sensors
___

### **string parsable to integer percent**
**Full sensor name**
```
column/strings/string_parsable_to_integer_percent
```
**Description**  
Column level sensor that calculates the number of rows with parsable to integer string column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_parsable_to_integer_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_parsable_to_integer_percent/snowflake.sql.jinja2"
    ```

___

### **string valid email percent**
**Full sensor name**
```
column/strings/string_valid_email_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid email value in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_email_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_email_percent/snowflake.sql.jinja2"
    ```

___

### **string length above max length percent**
**Full sensor name**
```
column/strings/string_length_above_max_length_percent
```
**Description**  
Column level sensor that calculates the percentage of values that are longer than a given length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|maximum_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_length_above_max_length_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_length_above_max_length_percent/snowflake.sql.jinja2"
    ```

___

### **string valid usa phone percent**
**Full sensor name**
```
column/strings/string_valid_usa_phone_percent
```
**Description**  
Column level sensor that calculates the percent of values that fit to a USA phone regex in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_usa_phone_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_usa_phone_percent/snowflake.sql.jinja2"
    ```

___

### **string length below min length count**
**Full sensor name**
```
column/strings/string_length_below_min_length_count
```
**Description**  
Column level sensor that calculates the count of values that are shorter than a given length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|minimum_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_length_below_min_length_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_length_below_min_length_count/snowflake.sql.jinja2"
    ```

___

### **string whitespace count**
**Full sensor name**
```
column/strings/string_whitespace_count
```
**Description**  
Column level sensor that calculates the number of rows with an whitespace string column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_whitespace_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_whitespace_count/snowflake.sql.jinja2"
    ```

___

### **string valid usa zipcode percent**
**Full sensor name**
```
column/strings/string_valid_usa_zipcode_percent
```
**Description**  
Column level sensor that calculates the percent of values that fit to a USA ZIP code regex in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_usa_zipcode_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_usa_zipcode_percent/snowflake.sql.jinja2"
    ```

___

### **string boolean placeholder percent**
**Full sensor name**
```
column/strings/string_boolean_placeholder_percent
```
**Description**  
Column level sensor that calculates the number of rows with a boolean placeholder string column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_boolean_placeholder_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_boolean_placeholder_percent/snowflake.sql.jinja2"
    ```

___

### **string valid country code percent**
**Full sensor name**
```
column/strings/string_valid_country_code_percent
```
**Description**  
Column level sensor that calculates the number of rows with a valid country code string column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_country_code_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_country_code_percent/snowflake.sql.jinja2"
    ```

___

### **string min length**
**Full sensor name**
```
column/strings/string_min_length
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_min_length/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_min_length/snowflake.sql.jinja2"
    ```

___

### **string max length**
**Full sensor name**
```
column/strings/string_max_length
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_max_length/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_max_length/snowflake.sql.jinja2"
    ```

___

### **string invalid ip6 address count**
**Full sensor name**
```
column/strings/string_invalid_ip6_address_count
```
**Description**  
Column level sensor that calculates the number of rows with an invalid IP6 address value in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip6_address_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip6_address_count/snowflake.sql.jinja2"
    ```

___

### **string invalid ip4 address count**
**Full sensor name**
```
column/strings/string_invalid_ip4_address_count
```
**Description**  
Column level sensor that calculates the number of rows with an invalid IP4 address value in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip4_address_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip4_address_count/snowflake.sql.jinja2"
    ```

___

### **string length below min length percent**
**Full sensor name**
```
column/strings/string_length_below_min_length_percent
```
**Description**  
Column level sensor that calculates the percentage of values that are shorter than a given length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|minimum_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_length_below_min_length_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_length_below_min_length_percent/snowflake.sql.jinja2"
    ```

___

### **string in set count**
**Full sensor name**
```
column/strings/string_in_set_count
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|values|Provided list of values to match the data.|string_list|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_in_set_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_in_set_count/snowflake.sql.jinja2"
    ```

___

### **string regex match percent**
**Full sensor name**
```
column/strings/string_regex_match_percent
```
**Description**  
Column level sensor that calculates the percent of values that fit to a regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|regex|This field can be used to define custom regex. In order to define custom regex, user should write correct regex as a string. If regex is not defined by user then default regex is null|string|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_regex_match_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_regex_match_percent/snowflake.sql.jinja2"
    ```

___

### **string valid currency code percent**
**Full sensor name**
```
column/strings/string_valid_currency_code_percent
```
**Description**  
Column level sensor that calculates the number of rows with a valid currency code string column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_currency_code_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_currency_code_percent/snowflake.sql.jinja2"
    ```

___

### **string not match regex count**
**Full sensor name**
```
column/strings/string_not_match_regex_count
```
**Description**  
Column level sensor that calculates the number of values that does not fit to a regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|regex|This field can be used to define custom regex. In order to define custom regex, user should write correct regex as a string. If regex is not defined by user then default regex is null|string|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_regex_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_regex_count/snowflake.sql.jinja2"
    ```

___

### **string whitespace percent**
**Full sensor name**
```
column/strings/string_whitespace_percent
```
**Description**  
Column level sensor that calculates the number of rows with a whitespace string column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_whitespace_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_whitespace_percent/snowflake.sql.jinja2"
    ```

___

### **string valid ip6 address percent**
**Full sensor name**
```
column/strings/string_valid_ip6_address_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid IP6 address value in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_ip6_address_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_ip6_address_percent/snowflake.sql.jinja2"
    ```

___

### **string mean length**
**Full sensor name**
```
column/strings/string_mean_length
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_mean_length/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_mean_length/snowflake.sql.jinja2"
    ```

___

### **string valid uuid percent**
**Full sensor name**
```
column/strings/string_valid_uuid_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid UUID value in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_uuid_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_uuid_percent/snowflake.sql.jinja2"
    ```

___

### **string surrounded by whitespace percent**
**Full sensor name**
```
column/strings/string_surrounded_by_whitespace_percent
```
**Description**  
Column level sensor that calculates the number of rows with string surrounded by whitespace column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_surrounded_by_whitespace_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_surrounded_by_whitespace_percent/snowflake.sql.jinja2"
    ```

___

### **string length above max length count**
**Full sensor name**
```
column/strings/string_length_above_max_length_count
```
**Description**  
Column level sensor that calculates the count of values that are longer than a given length in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|maximum_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_length_above_max_length_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_length_above_max_length_count/snowflake.sql.jinja2"
    ```

___

### **string in set percent**
**Full sensor name**
```
column/strings/string_in_set_percent
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|values|Provided list of values to match the data.|string_list|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_in_set_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_in_set_percent/snowflake.sql.jinja2"
    ```

___

### **string null placeholder count**
**Full sensor name**
```
column/strings/string_null_placeholder_count
```
**Description**  
Column level sensor that calculates the number of rows with a null placeholder string column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_null_placeholder_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_null_placeholder_count/snowflake.sql.jinja2"
    ```

___

### **string match date regex percent**
**Full sensor name**
```
column/strings/string_match_date_regex_percent
```
**Description**  
Column level sensor that calculates the percentage of values that does fit a given date regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum||YYYY-MM-DD<br/>DD/MM/YYYY<br/>Month D, YYYY<br/>YYYY/MM/DD<br/>MM/DD/YYYY<br/>|


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_match_date_regex_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_match_date_regex_percent/snowflake.sql.jinja2"
    ```

___

### **string valid ip4 address percent**
**Full sensor name**
```
column/strings/string_valid_ip4_address_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a valid IP4 address value in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_ip4_address_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_ip4_address_percent/snowflake.sql.jinja2"
    ```

___

### **string valid date percent**
**Full sensor name**
```
column/strings/string_valid_date_percent
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_date_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_date_percent/snowflake.sql.jinja2"
    ```

___

### **string most popular values**
**Full sensor name**
```
column/strings/string_most_popular_values
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|expected_values|Provided list of values to match the data.|string_list|||
|top_values|Provided limit of top popular values.|long|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_most_popular_values/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_most_popular_values/snowflake.sql.jinja2"
    ```

___

### **string invalid email count**
**Full sensor name**
```
column/strings/string_invalid_email_count
```
**Description**  
Column level sensor that calculates the number of rows with an invalid emails value in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_email_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_email_count/snowflake.sql.jinja2"
    ```

___

### **string match name regex percent**
**Full sensor name**
```
column/strings/string_match_name_regex_percent
```
**Description**  
Column level sensor that calculates the percentage of values that does fit a given name regex in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_match_name_regex_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_match_name_regex_percent/snowflake.sql.jinja2"
    ```

___

### **string empty count**
**Full sensor name**
```
column/strings/string_empty_count
```
**Description**  
Column level sensor that calculates the number of rows with an empty string column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_empty_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_empty_count/snowflake.sql.jinja2"
    ```

___

### **string invalid uuid count**
**Full sensor name**
```
column/strings/string_invalid_uuid_count
```
**Description**  
Column level sensor that calculates the number of rows with an invalid uuid value in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_uuid_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_uuid_count/snowflake.sql.jinja2"
    ```

___

### **string not match date regex count**
**Full sensor name**
```
column/strings/string_not_match_date_regex_count
```
**Description**  
Column level sensor that calculates the number of values that does not fit to a date regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum||YYYY-MM-DD<br/>DD/MM/YYYY<br/>Month D, YYYY<br/>YYYY/MM/DD<br/>MM/DD/YYYY<br/>|


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_date_regex_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_date_regex_count/snowflake.sql.jinja2"
    ```

___

### **string parsable to float percent**
**Full sensor name**
```
column/strings/string_parsable_to_float_percent
```
**Description**  
Column level sensor that calculates the number of rows with parsable to float string column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_parsable_to_float_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_parsable_to_float_percent/snowflake.sql.jinja2"
    ```

___

### **string surrounded by whitespace count**
**Full sensor name**
```
column/strings/string_surrounded_by_whitespace_count
```
**Description**  
Column level sensor that calculates the number of rows with string surrounded by whitespace column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_surrounded_by_whitespace_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_surrounded_by_whitespace_count/snowflake.sql.jinja2"
    ```

___

### **string empty percent**
**Full sensor name**
```
column/strings/string_empty_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with an empty string column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_empty_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_empty_percent/snowflake.sql.jinja2"
    ```

___

### **string null placeholder percent**
**Full sensor name**
```
column/strings/string_null_placeholder_percent
```
**Description**  
Column level sensor that calculates the number of rows with a null placeholder string column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_null_placeholder_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_null_placeholder_percent/snowflake.sql.jinja2"
    ```

___


## **uniqueness** column sensors
___

### **duplicate count**
**Full sensor name**
```
column/uniqueness/duplicate_count
```
**Description**  
Column level sensor that calculates the number of rows with a null column value.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_count/snowflake.sql.jinja2"
    ```

___

### **duplicate percent**
**Full sensor name**
```
column/uniqueness/duplicate_percent
```
**Description**  
Column level sensor that calculates the percentage of rows that are duplicates.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_percent/snowflake.sql.jinja2"
    ```

___

### **unique count**
**Full sensor name**
```
column/uniqueness/unique_count
```
**Description**  
Column level sensor that calculates the number of unique non-null values.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/uniqueness/unique_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/uniqueness/unique_count/snowflake.sql.jinja2"
    ```

___


## **nulls** column sensors
___

### **null percent**
**Full sensor name**
```
column/nulls/null_percent
```
**Description**  
Column-level sensor that calculates the percentage of rows with null values.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/nulls/null_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/nulls/null_percent/snowflake.sql.jinja2"
    ```

___

### **not null count**
**Full sensor name**
```
column/nulls/not_null_count
```
**Description**  
Column-level sensor that calculates the number of rows with not null values.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/nulls/not_null_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/nulls/not_null_count/snowflake.sql.jinja2"
    ```

___

### **null count**
**Full sensor name**
```
column/nulls/null_count
```
**Description**  
Column-level sensor that calculates the number of rows with null values.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/nulls/null_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/nulls/null_count/snowflake.sql.jinja2"
    ```

___


## **numeric** column sensors
___

### **values in range numeric percent**
**Full sensor name**
```
column/numeric/values_in_range_numeric_percent
```
**Description**  
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|min_value|Minimal value range variable.|double|||
|max_value|Maximal value range variable.|double|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_numeric_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_numeric_percent/snowflake.sql.jinja2"
    ```

___

### **sample variance**
**Full sensor name**
```
column/numeric/sample_variance
```
**Description**  
Column level sensor that counts negative values in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/sample_variance/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/sample_variance/snowflake.sql.jinja2"
    ```

___

### **mean**
**Full sensor name**
```
column/numeric/mean
```
**Description**  
Column level sensor that counts the average (mean) of values in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/mean/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/mean/snowflake.sql.jinja2"
    ```

___

### **numbers in set percent**
**Full sensor name**
```
column/numeric/numbers_in_set_percent
```
**Description**  
Column level sensor that calculates the percentage of values that are members of a given set.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|values|Provided list of values to match the data.|integer_list|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/numbers_in_set_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/numbers_in_set_percent/snowflake.sql.jinja2"
    ```

___

### **negative count**
**Full sensor name**
```
column/numeric/negative_count
```
**Description**  
Column level sensor that counts negative values in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/negative_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/negative_count/snowflake.sql.jinja2"
    ```

___

### **negative percent**
**Full sensor name**
```
column/numeric/negative_percent
```
**Description**  
Column level sensor that counts percentage of negative values in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/negative_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/negative_percent/snowflake.sql.jinja2"
    ```

___

### **numbers in set count**
**Full sensor name**
```
column/numeric/numbers_in_set_count
```
**Description**  
Column level sensor that counts values that are members of a given set.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|values|Provided list of values to match the data.|integer_list|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/numbers_in_set_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/numbers_in_set_count/snowflake.sql.jinja2"
    ```

___

### **population variance**
**Full sensor name**
```
column/numeric/population_variance
```
**Description**  
Column level sensor that counts negative values in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/population_variance/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/population_variance/snowflake.sql.jinja2"
    ```

___

### **sum**
**Full sensor name**
```
column/numeric/sum
```
**Description**  
Column level sensor that counts the sum of values in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/sum/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/sum/snowflake.sql.jinja2"
    ```

___

### **sample stddev**
**Full sensor name**
```
column/numeric/sample_stddev
```
**Description**  
Column level sensor that counts negative values in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/sample_stddev/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/sample_stddev/snowflake.sql.jinja2"
    ```

___

### **values in range integers percent**
**Full sensor name**
```
column/numeric/values_in_range_integers_percent
```
**Description**  
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|min_value|Minimal value range variable.|long|||
|max_value|Maximal value range variable.|long|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_integers_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_integers_percent/snowflake.sql.jinja2"
    ```

___

### **population stddev**
**Full sensor name**
```
column/numeric/population_stddev
```
**Description**  
Column level sensor that counts negative values in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/population_stddev/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/population_stddev/snowflake.sql.jinja2"
    ```

___


## **range** column sensors
___

### **min value**
**Full sensor name**
```
column/range/min_value
```
**Description**  
Column level sensor that counts minimum value in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/range/min_value/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/range/min_value/snowflake.sql.jinja2"
    ```

___

### **max value**
**Full sensor name**
```
column/range/max_value
```
**Description**  
Column level sensor that counts maximum value in a column.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/range/max_value/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/range/max_value/snowflake.sql.jinja2"
    ```

___

### **min value**
**Full sensor name**
```
column/range/min_value
```
**Description**  
Column level sensor that finds the minimum value. It works on any data type that supports the MIN functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/range/min_value/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/range/min_value/snowflake.sql.jinja2"
    ```

___

### **max value**
**Full sensor name**
```
column/range/max_value
```
**Description**  
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/range/max_value/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/range/max_value/snowflake.sql.jinja2"
    ```

___


## **sql** column sensors
___

### **sql condition passed count**
**Full sensor name**
```
column/sql/sql_condition_passed_count
```
**Description**  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_passed_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_passed_count/snowflake.sql.jinja2"
    ```

___

### **sql aggregated expression**
**Full sensor name**
```
column/sql/sql_aggregated_expression
```
**Description**  
Column level sensor that executes a given SQL expression on a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_expression|SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_aggregated_expression/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_aggregated_expression/snowflake.sql.jinja2"
    ```

___

### **sql condition failed percent**
**Full sensor name**
```
column/sql/sql_condition_failed_percent
```
**Description**  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_percent/snowflake.sql.jinja2"
    ```

___

### **sql condition failed count**
**Full sensor name**
```
column/sql/sql_condition_failed_count
```
**Description**  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_count/snowflake.sql.jinja2"
    ```

___

### **sql condition passed percent**
**Full sensor name**
```
column/sql/sql_condition_passed_percent
```
**Description**  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_passed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_passed_percent/snowflake.sql.jinja2"
    ```

___

