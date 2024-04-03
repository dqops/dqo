---
glightbox: false
---

# Supported data sources
The list of data sources supported by DQOps for running data quality checks, and measuring the data quality.

## Overview 
DQOps supports integration with different types of data sources for monitoring the data quality.
To monitor data quality in DQOps, you must first add a data
source connection. A [data source connection specifies](../reference/yaml/ConnectionYaml.md#connectionspec)
the parameters needed to connect to a database, such as a database
location and authentication information. The data source connection information for each data source type may be different.
Some data sources use existing database connection APIs (such as JDBC drivers), and others have proprietary APIs.

In DQOps, you can add a new data source connection through the user interface, command line or DQOps shell. For information on the 
parameters you need to specify, see the document dedicated to each data source.

## Supported data sources

DQOps supports the following data sources.

<br>

<div class="four-divs-row">

  <a href="postgresql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/alloy-db.svg">
    <p>AlloyDB for PostgreSQL</p>
  </a>

  <a href="athena/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/amazon-athena.svg">
    <p>Amazon Athena</p>
  </a>

  <a href="postgresql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/amazon-rds.svg">
    <p>Amazon Aurora</p>
  </a>

  <a href="redshift/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/redshift.svg">
    <p>Amazon Redshift</p>
  </a>

  <a href="mysql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/amazon-rds.svg">
    <p>Amazon RDS for MySQL</p>
  </a>

  <a href="postgresql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/amazon-rds.svg">
    <p>Amazon RDS for PostgreSQL</p>
  </a>

  <a href="sql-server/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/amazon-rds.svg">
    <p>Amazon RDS for SQL Server</p>
  </a>

  <a href="mysql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/azure-database-mysql.svg">
    <p>Azure Database for MySQL</p>
  </a>

  <a href="postgresql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/azure-database-postgresql.svg">
    <p>Azure Database for PostgreSQL</p>
  </a>

  <a href="sql-server/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/azure-sql-database.svg">
    <p>Azure SQL Database</p>
  </a>

  <a href="sql-server/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/azure-sql-managed-instance.svg">
    <p>Azure SQL Managed Instance</p>
  </a>

  <a href="sql-server/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/azure-synapse-analytics.svg">
    <p>Azure Synapse Analytics</p>
  </a>

  <a href="bigquery/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/bigquery.svg">
    <p>Google BigQuery</p>
  </a>

  <a href="mysql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/cloud-sql.svg">
    <p>Cloud SQL for MySQL</p>
  </a>

  <a href="postgresql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/cloud-sql.svg">
    <p>Cloud SQL for PostgreSQL</p>
  </a>

  <a href="sql-server/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/cloud-sql.svg">
    <p>Cloud SQL for SQL Server</p>
  </a>

  <a href="postgresql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/cockroach-db.svg">
    <p>CockroachDB</p>
  </a>

  <a href="csv/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/csv-icon.svg">
    <p>CSV</p>
  </a>

  <a href="databricks/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/databricks.svg">
    <p>Databricks</p>
  </a>

  <a href="duckdb/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/duckdb.svg">
    <p>DuckDB</p>
  </a>

  <a href="json/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/json-icon.svg">
    <p>JSON</p>
  </a>

  <a href="mysql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/maria-db.svg">
    <p>MariaDB</p>
  </a>

  <a href="sql-server/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/mssql-server.svg">
    <p>Microsoft SQL Server</p>
  </a>

  <a href="mysql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/mysql.svg">
    <p>MySQL</p>
  </a>

  <a href="oracle/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/oracle.svg">
    <p>Oracle</p>
  </a>

  <a href="mysql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/percona-server.svg">
    <p>Percona Server for MySQL</p>
  </a>

  <a href="parquet/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/parquet-icon.svg">
    <p>Parquet</p>
  </a>

  <a href="postgresql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/postgresql.svg">
    <p>PostgreSQL</p>
  </a>

  <a href="presto/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/presto.svg">
    <p>Presto</p>
  </a>

  <a href="single-store/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/single-store.svg">
    <p>SingleStoreDB</p>
  </a>

  <a href="snowflake/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/snowflake.svg">
    <p>Snowflake</p>
  </a>

  <a href="spark/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/spark.svg">
    <p>Spark</p>
  </a>

  <a href="trino/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/trino.svg">
    <p>Trino</p>
  </a>

  <a href="postgresql/" class="four-divs-element">
    <img src="https://dqops.com/docs/images/data-sources/yugabyte-db.svg">
    <p>YugabyteDB</p>
  </a>

  <div class="four-divs-element"></div>
  <div class="four-divs-element"></div>
  <div class="four-divs-element"></div>

</div>


## What's more
- Learn how to [configure connections to data sources in YAML](../dqo-concepts/configuring-data-sources.md#data-sources) files. 