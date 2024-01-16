# Sensors/table

**This is a list of table sensors in DQOps broken down by category and a brief description of what they do.**






### **accuracy**

| Sensor name | Description |
|-------------|-------------|
|[*total_row_count_match_percent*](./accuracy-table-sensors.md#total-row-count-match-percent)|Table level sensor that calculates the percentage of the difference of the total row count of all rows in the tested table and the total row count of the other (reference) table.|




### **availability**

| Sensor name | Description |
|-------------|-------------|
|[*table_availability*](./availability-table-sensors.md#table-availability)|Table availability sensor runs a simple table scan query to detect if the table is queryable. This sensor returns 0.0 when no failure was detected or 1.0 when a failure was detected.|




### **custom_sql**

| Sensor name | Description |
|-------------|-------------|
|[*sql_aggregated_expression*](./custom_sql-table-sensors.md#sql-aggregated-expression)|Table level sensor that executes a given SQL expression on a table.|
|[*sql_condition_failed_count*](./custom_sql-table-sensors.md#sql-condition-failed-count)|Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.|
|[*sql_condition_failed_percent*](./custom_sql-table-sensors.md#sql-condition-failed-percent)|Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.|
|[*sql_condition_passed_count*](./custom_sql-table-sensors.md#sql-condition-passed-count)|Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.|
|[*sql_condition_passed_percent*](./custom_sql-table-sensors.md#sql-condition-passed-percent)|Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.|




### **schema**

| Sensor name | Description |
|-------------|-------------|
|[*column_count*](./schema-table-sensors.md#column-count)|Table schema data quality sensor that reads the metadata from a monitored data source and counts the number of columns.|
|[*column_list_ordered_hash*](./schema-table-sensors.md#column-list-ordered-hash)|Table schema data quality sensor detects if the list and order of columns have changed on the table. The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns and the order of the columns.|
|[*column_list_unordered_hash*](./schema-table-sensors.md#column-list-unordered-hash)|Table schema data quality sensor detects if the list of columns have changed on the table. The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns, but not on the order of columns.|
|[*column_types_hash*](./schema-table-sensors.md#column-types-hash)|Table schema data quality sensor detects if the list of columns has changed or any of the column has a new data type, length, scale, precision or nullability. The sensor calculates a hash of the list of column names and all components of the column&#x27;s type (the type name, length, scale, precision, nullability). The hash value does not depend on the order of columns.|




### **timeliness**

| Sensor name | Description |
|-------------|-------------|
|[*data_freshness*](./timeliness-table-sensors.md#data-freshness)|Table sensor that runs a query calculating maximum days since the most recent event.|
|[*data_ingestion_delay*](./timeliness-table-sensors.md#data-ingestion-delay)|Table sensor that runs a query calculating the time difference in days between the most recent transaction timestamp and the most recent data loading timestamp.|
|[*data_staleness*](./timeliness-table-sensors.md#data-staleness)|Table sensor that runs a query calculating the time difference in days between the current date and most recent data loading timestamp (staleness).|
|[*partition_reload_lag*](./timeliness-table-sensors.md#partition-reload-lag)|Table sensor that runs a query calculating maximum difference in days between ingestion timestamp and event timestamp rows.|




### **volume**

| Sensor name | Description |
|-------------|-------------|
|[*row_count*](./volume-table-sensors.md#row-count)|Table sensor that executes a row count query.|





