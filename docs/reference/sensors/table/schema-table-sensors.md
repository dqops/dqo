# Data quality schema sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **schema** category supported by DQOps are listed below. Those sensors are measured on a table level.

---


## column count
Table schema data quality sensor that reads the metadata from a monitored data source and counts the number of columns.

**Sensor summary**

The column count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | schema | <span class="no-wrap-code">`table/schema/column_count`</span> | [*sensors/table/schema*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/schema/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

___



## column list ordered hash
Table schema data quality sensor detects if the list and order of columns have changed on the table.
 The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns and the order of the columns.

**Sensor summary**

The column list ordered hash sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | schema | <span class="no-wrap-code">`table/schema/column_list_ordered_hash`</span> | [*sensors/table/schema*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/schema/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

___



## column list unordered hash
Table schema data quality sensor detects if the list of columns have changed on the table.
 The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns, but not on the order of columns.

**Sensor summary**

The column list unordered hash sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | schema | <span class="no-wrap-code">`table/schema/column_list_unordered_hash`</span> | [*sensors/table/schema*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/schema/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

___



## column types hash
Table schema data quality sensor detects if the list of columns has changed or any of the column has a new data type, length, scale, precision or nullability.
 The sensor calculates a hash of the list of column names and all components of the column&#x27;s type (the type name, length, scale, precision, nullability).
 The hash value does not depend on the order of columns.

**Sensor summary**

The column types hash sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | schema | <span class="no-wrap-code">`table/schema/column_types_hash`</span> | [*sensors/table/schema*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/schema/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

___




## What's next
- Learn how the [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) are defined in DQOps and what is the definition of all Jinja2 macros used in the templates
- Understand how DQOps [runs data quality checks](../../../dqo-concepts/architecture/data-quality-check-execution-flow.md), rendering templates to SQL queries
