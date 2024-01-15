# Supported data sources
The list of data sources supported by DQOps for running data quality checks, and observing the data quality.

## Overview 
DQOps supports integration with different types of data sources for measure the data quality.
To monitor data quality in DQOps, you must first add a data
source connection. A data source connection specifies the parameters needed to connect to a database, such as a database
location and authentication information. The data source connection information for each data source type may be different.
Some data sources use existing database connection APIs (such as ODBC/JDBC), and others have proprietary APIs.

In DQOps, you can add a new data source connection through the user interface, command line or DQOps shell. For information on the 
parameters you need to specify, see the document dedicated to each data source.

## Supported data sources

DQOps supports the following types of data sources.

<div markdown="1" class="four-divs-row">

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/alloy-db.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[AlloyDB for PostgreSQL](./postgresql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/amazon-athena.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Amazon Athena](./athena.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/amazon-rds1.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Amazon Aurora](./postgresql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/amazon-rds1.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Amazon RDS for MySQL](./mysql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/amazon-rds1.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Amazon RDS for PostgreSQL](./postgresql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/amazon-rds1.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Amazon RDS for SQL Server](./sql-server.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/azure-database-mysql.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Azure Database for MySQL](./mysql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/azure-database-postgresql.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Azure Database for PostgreSQL](./postgresql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/azure-sql-database.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Azure SQL Database](./sql-server.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/azure-sql-managed-instance.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Azure SQL Managed Instance](./sql-server.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/azure-synapse-analytics.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Azure Synapse Analytics](./sql-server.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/bigquery.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Google BigQuery](./bigquery.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/cloud-sql.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Cloud SQL for MySQL](./mysql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/cloud-sql.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Cloud SQL for PostgreSQL](./postgresql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/cloud-sql.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Cloud SQL for SQL Server](./sql-server.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/cockroach-db.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[CockroachDB](./postgresql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/databricks1.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Databricks](./databricks.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/maria-db.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[MariaDB](./mysql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/mssql-server1.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Microsoft SQL Server](./sql-server.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/mysql.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[MySQL](./mysql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/oracle1.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Oracle](./oracle.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/percona-server.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Percona Server for MySQL](./mysql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/postgresql.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[PostgreSQL](./postgresql.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/presto.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Presto](./presto.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/redshift2.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Amazon Redshift](./redshift.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/snowflake.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Snowflake](./snowflake.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/spark.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Apache Spark](./spark.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/trino1.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[Trino](./trino.md)</figcaption>
</figure>

<figure markdown class="four-divs-element">
  ![Icon](https://dqops.com/docs/images/data-sources52345326/yugabyte-db.svg){ loading=lazy, class="image-center" }
  <figcaption markdown>[YugabyteDB](./postgresql.md)</figcaption>
</figure>

<div class="four-divs-element"></div>
<div class="four-divs-element"></div>
<div class="four-divs-element"></div>

</div>
