# DQOps reference
The reference of DQOps file formats, [data quality sensors](../dqo-concepts/sensors/sensors.md) and
[data quality rules](../dqo-concepts/definition-of-data-quality-rules.md) is provided below.


## Sensor and rule references
The following reference documentation shows the definition of data quality sensors and rules in DQOps.

 - **[Data quality sensors reference](./sensors/index.md)**

    The reference of [data quality sensors](../dqo-concepts/sensors/sensors.md) shows the Jinja2 SQL templates
    used by data quality sensors for each [data source](../data-sources/index.md).


 - **[Data quality rules reference](./rules/index.md)**

    The reference of [data quality rules](../dqo-concepts/definition-of-data-quality-rules.md) shows the Python source code
    of data quality rules.


## File format references 
The following reference documentation describes YAML file formats and Parquet schema used by DQOps.

 - **DQOps YAML files reference**

    All YAML files used by DQOps are described in this section. The most important ones are:

     - **[.dqoconnection.yaml](./yaml/ConnectionYaml.md)** with the configuration of the data source.

     - **[.dqotable.yaml](./yaml/TableYaml.md)** with the configuration of data quality checks for each table.


 - **[Parquet file reference](./parquetfiles/index.md)**

    Describes the schema of Parquet files where DQOps stores data quality results.



## Other references
The remaining reference documentation is found in other places.

- [**Data quality check reference**](../checks/index.md) lists all data quality checks supported by DQOps,
  showing also how to configure checks in DQOps YAML files, and what SQL is generated for each data quality check.

- [**REST API Python Client**](../client/index.md) describes all REST API operations supported by DQOps,
  showing also examples of calling all operations from *curl* and from Python.
