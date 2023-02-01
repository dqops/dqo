# table

## **standard** table sensors
___

### **row count**
**Full sensor name**
```
table/standard/row_count
```
**Description**  
Tabular sensor that executes a row count query on a table.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/table/standard/row_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/standard/row_count/snowflake.sql.jinja2"
    ```

=== "postgresql"
    ```
    --8<-- "home/sensors/table/standard/row_count/postgresql.sql.jinja2"
    ```

___


## **timeliness** table sensors
___

### **days since most recent event**
**Full sensor name**
```
table/timeliness/days_since_most_recent_event
```
**Description**  
Tabular sensor that runs a query calculating maximum days since the most recent event.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/days_since_most_recent_event/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/days_since_most_recent_event/snowflake.sql.jinja2"
    ```

___

### **partition reload lag**
**Full sensor name**
```
table/timeliness/partition_reload_lag
```
**Description**  
Tabular sensor that runs a query calculating maximum difference in days between ingestion timestamp and event timestamp rows.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/partition_reload_lag/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/partition_reload_lag/snowflake.sql.jinja2"
    ```

___

### **days since most recent ingestion**
**Full sensor name**
```
table/timeliness/days_since_most_recent_ingestion
```
**Description**  
Tabular sensor that runs a query calculating the time difference in days between the current date and most recent data loading timestamp (staleness).


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/days_since_most_recent_ingestion/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/days_since_most_recent_ingestion/snowflake.sql.jinja2"
    ```

___

### **data ingestion delay**
**Full sensor name**
```
table/timeliness/data_ingestion_delay
```
**Description**  
Tabular sensor that runs a query calculating the time difference in days between the most recent transaction timestamp and the most recent data loading timestamp.


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/data_ingestion_delay/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/data_ingestion_delay/snowflake.sql.jinja2"
    ```

___


## **sql** table sensors
___

### **sql condition passed percent**
**Full sensor name**
```
table/sql/sql_condition_passed_percent
```
**Description**  
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_passed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_passed_percent/snowflake.sql.jinja2"
    ```

___

### **sql condition failed count**
**Full sensor name**
```
table/sql/sql_condition_failed_count
```
**Description**  
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_failed_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_failed_count/snowflake.sql.jinja2"
    ```

___

### **sql aggregated expression**
**Full sensor name**
```
table/sql/sql_aggregated_expression
```
**Description**  
Table level sensor that executes a given SQL expression on a table.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_expression|SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} placeholder that is replaced with a full table name.|string|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/table/sql/sql_aggregated_expression/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/sql/sql_aggregated_expression/snowflake.sql.jinja2"
    ```

___

### **sql condition passed count**
**Full sensor name**
```
table/sql/sql_condition_passed_count
```
**Description**  
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_passed_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_passed_count/snowflake.sql.jinja2"
    ```

___

### **sql condition failed percent**
**Full sensor name**
```
table/sql/sql_condition_failed_percent
```
**Description**  
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string|||


**SQL Template (Jinja2)**

=== "bigquery"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_failed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_failed_percent/snowflake.sql.jinja2"
    ```

___

