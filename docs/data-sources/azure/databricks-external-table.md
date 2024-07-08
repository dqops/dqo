---
title: How to activate data observability for external tables using Databricks in Azure
---

# How to activate data observability for external tables using Databricks in Azure

This guide shows how to connect an external table using Databricks. The example will use the Azure Blob Storage for storing data. 


# Environment

The components of the run environment includes:

- Databricks instance in Azure
- Storage account with data
- Local DQOps instance


# Connecting databricks to the storage account

To work on data stored at Azure Blob Storage, mounting the file system is required in Databricks.
The example uses SAS key for Databricks authorization to the Azure Blob Storage.  

You can generate SAS token at the specific Azure container on the Containers list view of the Storage account in Azure Portal.

The mount configuration is created with use of Databricks notebook.
Also, the compute resource is required to execute notebooks.


The notebook script starts with the Spark configuration of the SAS key authorization.

```
spark.conf.set("fs.azure.account.auth.type.<storage_account>.dfs.core.windows.net", "SAS")
spark.conf.set("fs.azure.sas.token.provider.type.<storage_account>.dfs.core.windows.net", "org.apache.hadoop.fs.azurebfs.sas.FixedSASTokenProvider")
spark.conf.set("fs.azure.sas.fixed.token.<storage_account>.dfs.core.windows.net", "<SAS_token>")
```

Replace **&lt;storage_account&gt;** and **&lt;SAS_token&gt;** accordingly.

For the testing purpose we use the shortest path of the configuration.
In the production environment any keys should be hidden. 
You better use dbutils.secrets for storing and accessing the SAS token.


Then you can mount the resource.

```
dbutils.fs.mount(
    source="wasbs://<container_name>@<storage_account>.blob.core.windows.net",
    mount_point="/mnt/<name_of_mount>",
    extra_configs={
        "fs.azure.sas.<container_name>.<storage_account>.blob.core.windows.net": "<SAS_token>"
    }
)
```

Replace **&lt;container_name&gt;**, **&lt;storage_account&gt;** and **&lt;name_of_mount&gt;** accordingly.


To test the mount execute read function on a data file from storage.
The following lines read a parquet file as a data frame and prints the content of the file to the code editor.

```
df = spark.read.parquet("/mnt/<name_of_mount>/<the_rest_of_the_path>/<file_name>.parquet")
df.show()
```

You should see data in tabular format similar to this.

```
+----------+---------------+------------+
|id:INTEGER|date:LOCAL_DATE|value:STRING|
+----------+---------------+------------+
|         1|     2022-02-01|         abc|
|         2|     2022-02-02|         abc|
|         3|     2022-02-03|         abc|
```

For mounts verification you can also run the function:

```
display(dbutils.fs.ls("/mnt/<name_of_mount>"))
```


# Make external table available in SQL Editor

The data called by Databricks are external to it. This means Databricks will not load the data files to it's internal storage.
Rather it uses the SQL Warehouse to run queries and read the data only.

To achieve this Databricks need to know where the data are stored and know the metadata of the data source.

Before you execute any SQL query, select the **hive_metastore** catalog in Databricks and run the SQL Warehouse.

For the single parquet file both are accomplished by creating an external table in Databricks

```
CREATE TABLE my_external_table_parquet
USING PARQUET
OPTIONS (
    path "/mnt/<name_of_mount>/<the_rest_of_the_path>/<file_name>.parquet"
);
```

For the multiple parquet files under the hive style partitioning folder structure point the resource to the top folder with use of above command.
Then metadata have to be gathered with the list of available partitions.

```
MSCK REPAIR TABLE my_external_table_parquet;
```

Then partitions information become available. You can test it by listing them with the command:

```
SHOW PARTITIONS readouts
```

When using single file test data with simple SELECT statement.

```
SELECT <column_from_your_dataset>
FROM my_external_table_parquet;
```

# Connecting to Databricks with DQOps

For establishing the connection the following are needed:

- Databricks Host
- Databricks HttpPath
- Databricks Access Token
- Catalog name

Server hostname (Host) and HTTP path can be found under the SQL Warehouse's Connection Details.

Access token is available at your user settings in the menu bar in the top right corner, in Settings > Developer > Access tokens.
Create a new one or reuse a token.

Along with the Host, HttpPath and Access Token, fill the Connection name and the Catalog.
The Catalog should be filled with **hive_metastore** to access the catalog with external tables.


Once you collect the values, [create a new connection to the Databricks data source in DQOps.](../databricks.md)