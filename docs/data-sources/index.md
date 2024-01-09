# Supported data sources

DQOps supports integration with different types of data sources. To monitor data quality in DQOps, you must first add a data
source connection. A data source connection specifies the parameters needed to connect to a database, such as a database
location and authentication information. The data source connection information for each data source type may be different.
Some data sources use existing database connection APIs (such as ODBC/JDBC), and others have proprietary APIs.

In DQOps, you can add a new data source connection through the user interface, command line or DQOps shell. For information on the 
parameters you need to specify, see the document dedicated to each data source.

DQOps supports the following types of data sources.

| Data source                 | Description                                                                                                                                                                                                                   |
|:----------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [BigQuery](bigquery.md)     | BigQuery is a fully managed enterprise data warehouse from Google Cloud that helps manage and analyze data with built-in features like machine learning, geospatial analysis, and business intelligence.                      |
| [MySQL](mysql.md)           | MySQL is an open source, easy-to-use and flexible SQL database management system developed, distributed, and supported by Oracle Corporation.                                                                                 |
| [Oracle](oracle.md)         | Oracle Database is a proprietary multi-model database management system produced and marketed by Oracle Corporation.                                                                                                          |
| [PostgreSQL](postgresql.md) | PostgreSQL is a powerful, open source object-relational database system that uses and extends the SQL language combined with many features that safely store and scale the most complicated data workloads.                   |
| [Redshift](redshift.md)     | Amazon Redshift is a fully managed, petabyte-scale data warehouse service in the cloud from Amazon Web Services.  Redshift can handle analytic workloads on big data sets stored by a column-oriented DBMS principle          |
| [Snowflake](snowflake.md)   | Snowflake is a cloud-based data warehouse that separates storage from compute resources and requires little maintenance.                                                                                                      |
| [SQL Server](sql-server.md) | Microsoft SQL Server is a relational database management system developed by Microsoft that supports a wide variety of transaction processing, business intelligence and analytics applications in corporate IT environments. |
| [Spark](spark.md)           | Apache Spark is an open-source unified analytics engine for large-scale data processing.         |
