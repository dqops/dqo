**column count** checks  

**Description**  
Table level check that retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns.

___

## **column count**  
  
**Check description**  
Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|column_count|profiling| |[column_count](../../../../reference/sensors/Table/schema-table-sensors/#column-count)|[equals_integer](../../../../reference/rules/Comparison/#equals-integer)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=column_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=column_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=column_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=column_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=column_count
```
**Check structure (Yaml)**
```yaml
  profiling_checks:
    schema:
      column_count:
        warning:
          expected_value: 10
        error:
          expected_value: 10
        fatal:
          expected_value: 10
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-19"
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
  profiling_checks:
    schema:
      column_count:
        warning:
          expected_value: 10
        error:
          expected_value: 10
        fatal:
          expected_value: 10
  columns:
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
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 35-40"
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
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      profiling_checks:
        schema:
          column_count:
            warning:
              expected_value: 10
            error:
              expected_value: 10
            fatal:
              expected_value: 10
      columns:
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
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        ```
    === "Rendered SQL for Redshift"
        ```sql
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        ```
    === "Rendered SQL for MySQL"
        ```sql
        ```
    





___

## **daily column count**  
  
**Check description**  
Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_column_count|recurring|daily|[column_count](../../../../reference/sensors/Table/schema-table-sensors/#column-count)|[equals_integer](../../../../reference/rules/Comparison/#equals-integer)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_column_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_column_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_column_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_column_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_column_count
```
**Check structure (Yaml)**
```yaml
  recurring_checks:
    daily:
      schema:
        daily_column_count:
          warning:
            expected_value: 10
          error:
            expected_value: 10
          fatal:
            expected_value: 10
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-20"
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
  recurring_checks:
    daily:
      schema:
        daily_column_count:
          warning:
            expected_value: 10
          error:
            expected_value: 10
          fatal:
            expected_value: 10
  columns:
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
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 36-41"
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
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      recurring_checks:
        daily:
          schema:
            daily_column_count:
              warning:
                expected_value: 10
              error:
                expected_value: 10
              fatal:
                expected_value: 10
      columns:
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
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        ```
    === "Rendered SQL for Redshift"
        ```sql
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        ```
    === "Rendered SQL for MySQL"
        ```sql
        ```
    





___

## **monthly column count**  
  
**Check description**  
Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_column_count|recurring|monthly|[column_count](../../../../reference/sensors/Table/schema-table-sensors/#column-count)|[equals_integer](../../../../reference/rules/Comparison/#equals-integer)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_column_count
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_column_count
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_column_count
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_column_count
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_column_count
```
**Check structure (Yaml)**
```yaml
  recurring_checks:
    monthly:
      schema:
        monthly_column_count:
          warning:
            expected_value: 10
          error:
            expected_value: 10
          fatal:
            expected_value: 10
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-20"
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
  recurring_checks:
    monthly:
      schema:
        monthly_column_count:
          warning:
            expected_value: 10
          error:
            expected_value: 10
          fatal:
            expected_value: 10
  columns:
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
    ```
=== "Rendered SQL for BigQuery"
      
    ```sql
    ```
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    ```
### **PostgreSQL**
=== "Sensor template for PostgreSQL"
      
    ```sql+jinja
    ```
=== "Rendered SQL for PostgreSQL"
      
    ```sql
    ```
### **Redshift**
=== "Sensor template for Redshift"
      
    ```sql+jinja
    ```
=== "Rendered SQL for Redshift"
      
    ```sql
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    ```
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 36-41"
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
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      recurring_checks:
        monthly:
          schema:
            monthly_column_count:
              warning:
                expected_value: 10
              error:
                expected_value: 10
              fatal:
                expected_value: 10
      columns:
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
        ```
    === "Rendered SQL for BigQuery"
        ```sql
        ```
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        ```
    **PostgreSQL**  
      
    === "Sensor template for PostgreSQL"
        ```sql+jinja
        ```
    === "Rendered SQL for PostgreSQL"
        ```sql
        ```
    **Redshift**  
      
    === "Sensor template for Redshift"
        ```sql+jinja
        ```
    === "Rendered SQL for Redshift"
        ```sql
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        ```
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        ```
    === "Rendered SQL for MySQL"
        ```sql
        ```
    





___
