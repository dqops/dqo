**column types changed** checks  

**Description**  
Table level check that detects if the column names or column types have changed since the last time this check was run.
 This check will calculate a hash of the column names and all the components of the column&#x27;s data type: the data type name, length, scale, precision and nullability.
 A data quality issue will be detected if the hash of the column data types has changed. This check does not depend on the order of columns, the columns could be reordered as long
 as all columns are still present and the data types match since the last time they were tested.

___

## **column types changed**  
  
**Check description**  
Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|column_types_changed|profiling| |[column_types_hash](../../../../reference/sensors/Table/schema-table-sensors/#column-types-hash)|[value_changed](../../../../reference/rules/Comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=column_types_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=column_types_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=column_types_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=column_types_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=column_types_changed
```
**Check structure (Yaml)**
```yaml
  profiling_checks:
    schema:
      column_types_changed:
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
      column_types_changed:
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
    ```yaml hl_lines="11-18 32-37"
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
          column_types_changed:
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

## **daily column types changed**  
  
**Check description**  
Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_column_types_changed|recurring|daily|[column_types_hash](../../../../reference/sensors/Table/schema-table-sensors/#column-types-hash)|[value_changed](../../../../reference/rules/Comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_column_types_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_column_types_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_column_types_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_column_types_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_column_types_changed
```
**Check structure (Yaml)**
```yaml
  recurring_checks:
    daily:
      schema:
        daily_column_types_changed:
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
        daily_column_types_changed:
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
    ```yaml hl_lines="11-18 33-38"
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
            daily_column_types_changed:
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

## **monthly column types changed**  
  
**Check description**  
Detects if new columns were added, removed or their data types have changed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_column_types_changed|recurring|monthly|[column_types_hash](../../../../reference/sensors/Table/schema-table-sensors/#column-types-hash)|[value_changed](../../../../reference/rules/Comparison/#value-changed)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_column_types_changed
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_column_types_changed
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_column_types_changed
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_column_types_changed
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_column_types_changed
```
**Check structure (Yaml)**
```yaml
  recurring_checks:
    monthly:
      schema:
        monthly_column_types_changed:
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
        monthly_column_types_changed:
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
    ```yaml hl_lines="11-18 33-38"
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
            monthly_column_types_changed:
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
