**column exists** checks  

**Description**  
Column level check that reads the metadata of the monitored table and verifies that the column still exists in the data source.
 The data quality sensor returns 1.0 when the column was found or 0.0 when the column was not found.

___

## **column exists**  
  
**Check description**  
Checks the metadata of the monitored table and verifies if the column exists.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|column_exists|profiling| |[column_exists](../../../../reference/sensors/Column/schema-column-sensors/#column-exists)|[equals_integer](../../../../reference/rules/Comparison/#equals-integer)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=column_exists
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=column_exists
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=column_exists
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=column_exists
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=column_exists
```
**Check structure (Yaml)**
```yaml
      profiling_checks:
        schema:
          column_exists:
            warning:
              expected_value: 1
            error:
              expected_value: 1
            fatal:
              expected_value: 1
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
        schema:
          column_exists:
            warning:
              expected_value: 1
            error:
              expected_value: 1
            fatal:
              expected_value: 1
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
    ```yaml hl_lines="0-0 38-43"
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
      columns:
        target_column:
          profiling_checks:
            schema:
              column_exists:
                warning:
                  expected_value: 1
                error:
                  expected_value: 1
                fatal:
                  expected_value: 1
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

## **daily column exists**  
  
**Check description**  
Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_column_exists|recurring|daily|[column_exists](../../../../reference/sensors/Column/schema-column-sensors/#column-exists)|[equals_integer](../../../../reference/rules/Comparison/#equals-integer)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_column_exists
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_column_exists
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_column_exists
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_column_exists
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_column_exists
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        daily:
          schema:
            daily_column_exists:
              warning:
                expected_value: 1
              error:
                expected_value: 1
              fatal:
                expected_value: 1
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
      recurring_checks:
        daily:
          schema:
            daily_column_exists:
              warning:
                expected_value: 1
              error:
                expected_value: 1
              fatal:
                expected_value: 1
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
    ```yaml hl_lines="0-0 39-44"
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
      columns:
        target_column:
          recurring_checks:
            daily:
              schema:
                daily_column_exists:
                  warning:
                    expected_value: 1
                  error:
                    expected_value: 1
                  fatal:
                    expected_value: 1
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

## **monthly column exists**  
  
**Check description**  
Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_column_exists|recurring|monthly|[column_exists](../../../../reference/sensors/Column/schema-column-sensors/#column-exists)|[equals_integer](../../../../reference/rules/Comparison/#equals-integer)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command-line-interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_column_exists
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command-line-interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_column_exists
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_column_exists
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_column_exists
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_column_exists
```
**Check structure (Yaml)**
```yaml
      recurring_checks:
        monthly:
          schema:
            monthly_column_exists:
              warning:
                expected_value: 1
              error:
                expected_value: 1
              fatal:
                expected_value: 1
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
      recurring_checks:
        monthly:
          schema:
            monthly_column_exists:
              warning:
                expected_value: 1
              error:
                expected_value: 1
              fatal:
                expected_value: 1
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
    ```yaml hl_lines="0-0 39-44"
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
      columns:
        target_column:
          recurring_checks:
            monthly:
              schema:
                monthly_column_exists:
                  warning:
                    expected_value: 1
                  error:
                    expected_value: 1
                  fatal:
                    expected_value: 1
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
