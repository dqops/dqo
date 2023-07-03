**column count changed** checks  

**Description**  
Table level check that detects if the number of columns in the table has changed since the check (checkpoint) was run the last time.
 This check retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to the last known number of columns
 that was captured and is stored in the data quality check results database.

___

## **column count changed**  
  
**Check description**  
Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|column_count_changed|profiling| |[column_count](../../../../reference/sensors/Table/schema-table-sensors/#column-count)|[value_changed](../../../../reference/rules/Comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=column_count_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=column_count_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=column_count_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=column_count_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=column_count_changed
```
**Check structure (Yaml)**
```yaml
  profiling_checks:
    schema:
      column_count_changed:
        warning: {}
        error: {}
        fatal: {}
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-16"
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
      column_count_changed:
        warning: {}
        error: {}
        fatal: {}
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
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    ```
=== "Rendered SQL for Oracle"
      
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
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="0-0 32-37"
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
      groupings:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      profiling_checks:
        schema:
          column_count_changed:
            warning: {}
            error: {}
            fatal: {}
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
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        ```
    === "Rendered SQL for MySQL"
        ```sql
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        ```
    === "Rendered SQL for Oracle"
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
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        ```
    





___

## **daily column count changed**  
  
**Check description**  
Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_column_count_changed|recurring|daily|[column_count](../../../../reference/sensors/Table/schema-table-sensors/#column-count)|[value_changed](../../../../reference/rules/Comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_column_count_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_column_count_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_column_count_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_column_count_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_column_count_changed
```
**Check structure (Yaml)**
```yaml
  recurring_checks:
    daily:
      schema:
        daily_column_count_changed:
          warning: {}
          error: {}
          fatal: {}
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-17"
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
        daily_column_count_changed:
          warning: {}
          error: {}
          fatal: {}
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
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    ```
=== "Rendered SQL for Oracle"
      
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
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="0-0 33-38"
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
      groupings:
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
            daily_column_count_changed:
              warning: {}
              error: {}
              fatal: {}
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
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        ```
    === "Rendered SQL for MySQL"
        ```sql
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        ```
    === "Rendered SQL for Oracle"
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
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        ```
    





___

## **monthly column count changed**  
  
**Check description**  
Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_column_count_changed|recurring|monthly|[column_count](../../../../reference/sensors/Table/schema-table-sensors/#column-count)|[value_changed](../../../../reference/rules/Comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_column_count_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_column_count_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_column_count_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_column_count_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_column_count_changed
```
**Check structure (Yaml)**
```yaml
  recurring_checks:
    monthly:
      schema:
        monthly_column_count_changed:
          warning: {}
          error: {}
          fatal: {}
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-17"
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
        monthly_column_count_changed:
          warning: {}
          error: {}
          fatal: {}
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
### **MySQL**
=== "Sensor template for MySQL"
      
    ```sql+jinja
    ```
=== "Rendered SQL for MySQL"
      
    ```sql
    ```
### **Oracle**
=== "Sensor template for Oracle"
      
    ```sql+jinja
    ```
=== "Rendered SQL for Oracle"
      
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
### **Snowflake**
=== "Sensor template for Snowflake"
      
    ```sql+jinja
    ```
=== "Rendered SQL for Snowflake"
      
    ```sql
    ```
### **SQL Server**
=== "Sensor template for SQL Server"
      
    ```sql+jinja
    ```
=== "Rendered SQL for SQL Server"
      
    ```sql
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="0-0 33-38"
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
      groupings:
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
            monthly_column_count_changed:
              warning: {}
              error: {}
              fatal: {}
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
    **MySQL**  
      
    === "Sensor template for MySQL"
        ```sql+jinja
        ```
    === "Rendered SQL for MySQL"
        ```sql
        ```
    **Oracle**  
      
    === "Sensor template for Oracle"
        ```sql+jinja
        ```
    === "Rendered SQL for Oracle"
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
    **Snowflake**  
      
    === "Sensor template for Snowflake"
        ```sql+jinja
        ```
    === "Rendered SQL for Snowflake"
        ```sql
        ```
    **SQL Server**  
      
    === "Sensor template for SQL Server"
        ```sql+jinja
        ```
    === "Rendered SQL for SQL Server"
        ```sql
        ```
    





___
