---
title: DQOps data quality schema sensors
---
# DQOps data quality schema sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **schema** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## column exists
Column level data quality sensor that reads the metadata of the table from the data source and checks if the column name exists on the table.
 Returns 1.0 when the column was found, 0.0 when the column is missing.

**Sensor summary**

The column exists sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | schema | <span class="no-wrap-code">`column/schema/column_exists`</span> | [*sensors/column/schema*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/schema/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

___



## column type hash
Column level data quality sensor that reads the metadata of the table from the data source and calculates a hash of the detected data type (also including the length, scale and precision)
 of the target colum.
 Returns a 15-16 decimal digit hash of the column data type.

**Sensor summary**

The column type hash sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | schema | <span class="no-wrap-code">`column/schema/column_type_hash`</span> | [*sensors/column/schema*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/schema/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

___




## What's next
- Learn how the [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) are defined in DQOps and what is the definition of all Jinja2 macros used in the templates
- Understand how DQOps [runs data quality checks](../../../dqo-concepts/architecture/data-quality-check-execution-flow.md), rendering templates to SQL queries
