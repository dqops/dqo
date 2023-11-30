# Sensors/table

**This is a list of table sensors in DQOps broken down by category and a brief description of what they do.**





### **accuracy**  

| Sensor name | Description |
|-------------|-------------|
|[total_row_count_match_percent](./table/accuracy-table-sensors/#total-row-count-match-percent)|Table level sensor that calculates the percentage of the difference of the total row count of all rows in the tested table and the total row count of the other (reference) table.|




### **availability**  

| Sensor name | Description |
|-------------|-------------|
|[table_availability](./table/availability-table-sensors/#table-availability)|Table availability sensor runs a simple table scan query to detect if the table is queryable. This sensor returns 0.0 when no failure was detected or 1.0 when a failure was detected.|




### **schema**  

| Sensor name | Description |
|-------------|-------------|
|[column_count](./table/schema-table-sensors/#column-count)|Table schema data quality sensor that reads the metadata from a monitored data source and counts the number of columns.|
|[column_list_ordered_hash](./table/schema-table-sensors/#column-list-ordered-hash)|Table schema data quality sensor detects if the list and order of columns have changed on the table. The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns and the order of the columns.|
|[column_list_unordered_hash](./table/schema-table-sensors/#column-list-unordered-hash)|Table schema data quality sensor detects if the list of columns have changed on the table. The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns, but not on the order of columns.|
|[column_types_hash](./table/schema-table-sensors/#column-types-hash)|Table schema data quality sensor detects if the list of columns has changed or any of the column has a new data type, length, scale, precision or nullability. The sensor calculates a hash of the list of column names and all components of the column&#x27;s type (the type name, length, scale, precision, nullability). The hash value does not depend on the order of columns.|




### **sql**  

| Sensor name | Description |
|-------------|-------------|
|[sql_aggregated_expression](./table/sql-table-sensors/#sql-aggregated-expression)|Table level sensor that executes a given SQL expression on a table.|
|[sql_condition_failed_count](./table/sql-table-sensors/#sql-condition-failed-count)|Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.|
|[sql_condition_failed_percent](./table/sql-table-sensors/#sql-condition-failed-percent)|Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.|
|[sql_condition_passed_count](./table/sql-table-sensors/#sql-condition-passed-count)|Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.|
|[sql_condition_passed_percent](./table/sql-table-sensors/#sql-condition-passed-percent)|Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.|




### **timeliness**  

| Sensor name | Description |
|-------------|-------------|
|[data_freshness](./table/timeliness-table-sensors/#data-freshness)|Table sensor that runs a query calculating maximum days since the most recent event.|
|[data_ingestion_delay](./table/timeliness-table-sensors/#data-ingestion-delay)|Table sensor that runs a query calculating the time difference in days between the most recent transaction timestamp and the most recent data loading timestamp.|
|[data_staleness](./table/timeliness-table-sensors/#data-staleness)|Table sensor that runs a query calculating the time difference in days between the current date and most recent data loading timestamp (staleness).|
|[partition_reload_lag](./table/timeliness-table-sensors/#partition-reload-lag)|Table sensor that runs a query calculating maximum difference in days between ingestion timestamp and event timestamp rows.|




### **volume**  

| Sensor name | Description |
|-------------|-------------|
|[row_count](./table/volume-table-sensors/#row-count)|Table sensor that executes a row count query.|



