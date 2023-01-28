#column

##<b>pii</b> column sensors
___

###<b>{{replace_chars_in_string('contains_usa_zipcode_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/pii/contains_usa_zipcode_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percent of values that contain a USA zip code number in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_zipcode_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_zipcode_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('contains_usa_phone_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/pii/contains_usa_phone_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percent of values that contains a USA phone number in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_phone_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_phone_percent/snowflake.sql.jinja2"
    ```

___


##<b>datetime</b> column sensors
___

###<b>{{replace_chars_in_string('date_values_in_future_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/datetime/date_values_in_future_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/datetime/date_values_in_future_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/datetime/date_values_in_future_percent/snowflake.sql.jinja2"
    ```

___


##<b>bool</b> column sensors
___

###<b>{{replace_chars_in_string('true_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/bool/true_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of rows with a true value in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/bool/true_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/bool/true_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('false_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/bool/false_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of rows with a false value in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/bool/false_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/bool/false_percent/snowflake.sql.jinja2"
    ```

___


##<b>strings</b> column sensors
___

###<b>{{replace_chars_in_string('string_parsable_to_integer_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_parsable_to_integer_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with parsable to integer string column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_parsable_to_integer_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_parsable_to_integer_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_valid_email_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_valid_email_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of rows with a valid email value in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_email_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_email_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_length_above_max_length_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_length_above_max_length_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of values that are longer than a given length in a column.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>maximum_length</td>
<td>This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0</td>
<td>{{replace_chars_in_string('integer_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_length_above_max_length_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_length_above_max_length_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_valid_usa_phone_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_valid_usa_phone_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percent of values that fit to a USA phone regex in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_usa_phone_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_usa_phone_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_length_below_min_length_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_length_below_min_length_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the count of values that are shorter than a given length in a column.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>minimum_length</td>
<td>This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0</td>
<td>{{replace_chars_in_string('integer_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_length_below_min_length_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_length_below_min_length_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_whitespace_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_whitespace_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with an whitespace string column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_whitespace_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_whitespace_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_valid_usa_zipcode_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_valid_usa_zipcode_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percent of values that fit to a USA ZIP code regex in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_usa_zipcode_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_usa_zipcode_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_boolean_placeholder_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_boolean_placeholder_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a boolean placeholder string column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_boolean_placeholder_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_boolean_placeholder_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_valid_country_code_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_valid_country_code_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a valid country code string column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_country_code_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_country_code_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_min_length', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_min_length
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a null column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_min_length/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_min_length/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_max_length', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_max_length
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a null column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_max_length/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_max_length/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_invalid_ip6_address_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_invalid_ip6_address_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with an invalid IP6 address value in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip6_address_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip6_address_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_invalid_ip4_address_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_invalid_ip4_address_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with an invalid IP4 address value in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip4_address_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip4_address_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_length_below_min_length_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_length_below_min_length_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of values that are shorter than a given length in a column.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>minimum_length</td>
<td>This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0</td>
<td>{{replace_chars_in_string('integer_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_length_below_min_length_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_length_below_min_length_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_in_set_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_in_set_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a null column value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>values</td>
<td>Provided list of values to match the data.</td>
<td>{{replace_chars_in_string('string_list_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_in_set_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_in_set_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_regex_match_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_regex_match_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percent of values that fit to a regex in a column.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>regex</td>
<td>This field can be used to define custom regex. In order to define custom regex, user should write correct regex as a string. If regex is not defined by user then default regex is null</td>
<td>{{replace_chars_in_string('string_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_regex_match_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_regex_match_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_valid_currency_code_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_valid_currency_code_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a valid currency code string column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_currency_code_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_currency_code_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_not_match_regex_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_not_match_regex_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of values that does not fit to a regex in a column.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>regex</td>
<td>This field can be used to define custom regex. In order to define custom regex, user should write correct regex as a string. If regex is not defined by user then default regex is null</td>
<td>{{replace_chars_in_string('string_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_regex_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_regex_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_whitespace_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_whitespace_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a whitespace string column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_whitespace_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_whitespace_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_valid_ip6_address_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_valid_ip6_address_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of rows with a valid IP6 address value in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_ip6_address_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_ip6_address_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_mean_length', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_mean_length
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a null column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_mean_length/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_mean_length/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_valid_uuid_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_valid_uuid_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of rows with a valid UUID value in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_uuid_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_uuid_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_surrounded_by_whitespace_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_surrounded_by_whitespace_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with string surrounded by whitespace column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_surrounded_by_whitespace_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_surrounded_by_whitespace_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_length_above_max_length_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_length_above_max_length_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the count of values that are longer than a given length in a column.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>maximum_length</td>
<td>This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0</td>
<td>{{replace_chars_in_string('integer_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_length_above_max_length_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_length_above_max_length_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_in_set_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_in_set_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a null column value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>values</td>
<td>Provided list of values to match the data.</td>
<td>{{replace_chars_in_string('string_list_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_in_set_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_in_set_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_null_placeholder_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_null_placeholder_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a null placeholder string column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_null_placeholder_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_null_placeholder_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_match_date_regex_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_match_date_regex_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of values that does fit a given date regex in a column.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>date_formats</td>
<td>Desired date format. Sensor will try to parse the column records and cast the data using this format.</td>
<td>{{replace_chars_in_string('enum_type', '_type', '')}}</td>
<td></td>
<td>YYYY-MM-DD<br/>DD/MM/YYYY<br/>Month D, YYYY<br/>YYYY/MM/DD<br/>MM/DD/YYYY<br/></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_match_date_regex_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_match_date_regex_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_valid_ip4_address_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_valid_ip4_address_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of rows with a valid IP4 address value in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_ip4_address_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_ip4_address_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_valid_date_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_valid_date_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a null column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_valid_date_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_valid_date_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_most_popular_values', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_most_popular_values
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a null column value.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>expected_values</td>
<td>Provided list of values to match the data.</td>
<td>{{replace_chars_in_string('string_list_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>top_values</td>
<td>Provided limit of top popular values.</td>
<td>{{replace_chars_in_string('long_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_most_popular_values/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_most_popular_values/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_invalid_email_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_invalid_email_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with an invalid emails value in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_email_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_email_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_match_name_regex_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_match_name_regex_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of values that does fit a given name regex in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_match_name_regex_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_match_name_regex_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_empty_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_empty_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with an empty string column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_empty_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_empty_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_invalid_uuid_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_invalid_uuid_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with an invalid uuid value in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_uuid_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_uuid_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_not_match_date_regex_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_not_match_date_regex_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of values that does not fit to a date regex in a column.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>date_formats</td>
<td>Desired date format. Sensor will try to parse the column records and cast the data using this format.</td>
<td>{{replace_chars_in_string('enum_type', '_type', '')}}</td>
<td></td>
<td>YYYY-MM-DD<br/>DD/MM/YYYY<br/>Month D, YYYY<br/>YYYY/MM/DD<br/>MM/DD/YYYY<br/></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_date_regex_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_date_regex_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_parsable_to_float_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_parsable_to_float_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with parsable to float string column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_parsable_to_float_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_parsable_to_float_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_surrounded_by_whitespace_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_surrounded_by_whitespace_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with string surrounded by whitespace column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_surrounded_by_whitespace_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_surrounded_by_whitespace_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_empty_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_empty_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of rows with an empty string column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_empty_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_empty_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('string_null_placeholder_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/strings/string_null_placeholder_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a null placeholder string column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_null_placeholder_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_null_placeholder_percent/snowflake.sql.jinja2"
    ```

___


##<b>uniqueness</b> column sensors
___

###<b>{{replace_chars_in_string('duplicate_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/uniqueness/duplicate_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of rows with a null column value.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('duplicate_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/uniqueness/duplicate_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of rows that are duplicates.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('unique_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/uniqueness/unique_count
```
<b>Description</b>
<br/>
Column level sensor that calculates the number of unique non-null values.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/uniqueness/unique_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/uniqueness/unique_count/snowflake.sql.jinja2"
    ```

___


##<b>nulls</b> column sensors
___

###<b>{{replace_chars_in_string('null_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/nulls/null_percent
```
<b>Description</b>
<br/>
Column-level sensor that calculates the percentage of rows with null values.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/nulls/null_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/nulls/null_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('not_null_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/nulls/not_null_count
```
<b>Description</b>
<br/>
Column-level sensor that calculates the number of rows with not null values.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/nulls/not_null_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/nulls/not_null_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('null_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/nulls/null_count
```
<b>Description</b>
<br/>
Column-level sensor that calculates the number of rows with null values.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/nulls/null_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/nulls/null_count/snowflake.sql.jinja2"
    ```

___


##<b>range</b> column sensors
___

###<b>{{replace_chars_in_string('min_value', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/range/min_value
```
<b>Description</b>
<br/>
Column level sensor that finds the minimum value. It works on any data type that supports the MIN functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/range/min_value/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/range/min_value/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('max_value', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/range/max_value
```
<b>Description</b>
<br/>
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/range/max_value/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/range/max_value/snowflake.sql.jinja2"
    ```

___


##<b>numeric</b> column sensors
___

###<b>{{replace_chars_in_string('stddev_samp', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/numeric/stddev_samp
```
<b>Description</b>
<br/>
Column level sensor that counts negative values in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/stddev_samp/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/stddev_samp/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('sum', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/numeric/sum
```
<b>Description</b>
<br/>
Column level sensor that counts negative values in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/sum/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/sum/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('values_in_range_numeric_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/numeric/values_in_range_numeric_percent
```
<b>Description</b>
<br/>
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_value</td>
<td>Minimal value range variable.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>max_value</td>
<td>Maximal value range variable.</td>
<td>{{replace_chars_in_string('double_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_numeric_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_numeric_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('numbers_in_set_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/numeric/numbers_in_set_percent
```
<b>Description</b>
<br/>
Column level sensor that calculates the percentage of values that are members of a given set.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>values</td>
<td>Provided list of values to match the data.</td>
<td>{{replace_chars_in_string('integer_list_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/numbers_in_set_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/numbers_in_set_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('negative_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/numeric/negative_count
```
<b>Description</b>
<br/>
Column level sensor that counts negative values in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/negative_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/negative_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('negative_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/numeric/negative_percent
```
<b>Description</b>
<br/>
Column level sensor that counts percentage of negative values in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/negative_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/negative_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('var_pop', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/numeric/var_pop
```
<b>Description</b>
<br/>
Column level sensor that counts negative values in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/var_pop/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/var_pop/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('numbers_in_set_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/numeric/numbers_in_set_count
```
<b>Description</b>
<br/>
Column level sensor that counts values that are members of a given set.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>values</td>
<td>Provided list of values to match the data.</td>
<td>{{replace_chars_in_string('integer_list_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/numbers_in_set_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/numbers_in_set_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('var_samp', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/numeric/var_samp
```
<b>Description</b>
<br/>
Column level sensor that counts negative values in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/var_samp/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/var_samp/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('values_in_range_integers_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/numeric/values_in_range_integers_percent
```
<b>Description</b>
<br/>
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>min_value</td>
<td>Minimal value range variable.</td>
<td>{{replace_chars_in_string('long_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

<tr>
<td>max_value</td>
<td>Maximal value range variable.</td>
<td>{{replace_chars_in_string('long_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_integers_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_integers_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('stddev_pop', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/numeric/stddev_pop
```
<b>Description</b>
<br/>
Column level sensor that counts negative values in a column.
<br/>


<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/stddev_pop/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/stddev_pop/snowflake.sql.jinja2"
    ```

___


##<b>sql</b> column sensors
___

###<b>{{replace_chars_in_string('sql_condition_passed_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/sql/sql_condition_passed_count
```
<b>Description</b>
<br/>
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>sql_condition</td>
<td>SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.</td>
<td>{{replace_chars_in_string('string_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_passed_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_passed_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('sql_aggregated_expression', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/sql/sql_aggregated_expression
```
<b>Description</b>
<br/>
Column level sensor that executes a given SQL expression on a column.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>sql_expression</td>
<td>SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.</td>
<td>{{replace_chars_in_string('string_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_aggregated_expression/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_aggregated_expression/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('sql_condition_failed_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/sql/sql_condition_failed_percent
```
<b>Description</b>
<br/>
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>sql_condition</td>
<td>SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.</td>
<td>{{replace_chars_in_string('string_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_percent/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('sql_condition_failed_count', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/sql/sql_condition_failed_count
```
<b>Description</b>
<br/>
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>sql_condition</td>
<td>SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.</td>
<td>{{replace_chars_in_string('string_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_count/snowflake.sql.jinja2"
    ```

___

###<b>{{replace_chars_in_string('sql_condition_passed_percent', '_', ' ')}}</b>
<b>Full sensor name</b>
```
column/sql/sql_condition_passed_percent
```
<b>Description</b>
<br/>
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.
<br/>

<b>Parameters</b>
<table>
<thead>
<tr>
<th>Field name</th>
<th>Description</th>
<th>Allowed data type</th>
<th>Is it required?</th>
<th>Allowed values</th>
</tr>
</thead>
<tbody>

<tr>
<td>sql_condition</td>
<td>SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.</td>
<td>{{replace_chars_in_string('string_type', '_type', '')}}</td>
<td></td>
<td></td>
</tr>

</tbody>
</table>

<b>SQL Template (Jinja2)</b>

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_passed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_passed_percent/snowflake.sql.jinja2"
    ```

___

