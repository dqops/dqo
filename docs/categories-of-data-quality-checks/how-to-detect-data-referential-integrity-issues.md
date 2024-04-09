---
title: How to detect referential integrity issues and missing keys
---
# How to detect referential integrity issues and missing keys
Read this guide to learn how to detect referential integrity issues, such as missing keys in dictionary tables or wrong foreign keys.

The data quality checks that detect missing keys are configured in the `integrity` category in DQOps.

## What is referential integrity
Referential integrity is used in relational databases to describe a relation between two tables.
One table is a dictionary of entries such as products, countries, and customers. 
Each entry is identified by a unique key, which is also a primary key of the table. 
The other table contains references to the dictionary entries by storing a key that identifies that entry, called a foreign key.

Traditional, transactional OLTP databases such as Oracle, SQL Server, PostgreSQL, or MySQL
can enforce relational integrity in the storage engine, ensuring that storing invalid foreign key values is not possible.

The problem of broken referential integrity is specific to data warehouses and data lakes 
using unstructured or semi-structured data, such as Parquet files.

### When referential integrity is broken
We can identify several sources of broken referential integrity.

- Data that is stored in a data lake as Parquet or CSV files. 
  The data lake cannot enforce foreign key constraints, allowing the upload of Parquet files containing invalid keys.

- Data loaded or updated without an ongoing transaction.

- Data loaded to a table that didn't have an active foreign key constraint.

- Data received in the landing zone as flat files.

- Invalid transformation code in the data pipeline.

- The columns were in a different order in some source files, such as a country name and state code were reversed.

In all these cases, post-load validation using data quality checks is the last line of defense to detect referential integrity issues.

### Example of referential integrity
The following example shows the first ten rows of a public dataset containing the number of COVID-19 cases for each country and date.


| date       | **country_code** | country_name   | iso_3166_1_alpha_2 | iso_3166_1_alpha_3 | new_confirmed |
|------------|------------------|----------------|--------------------|--------------------|---------------|
| 2022-02-27 | **AR**           | Argentina      | AR                 | ARG                | 1818          |
| 2022-01-23 | **CO**           | Colombia       | CO                 | COL                | 91            |
| 2022-07-11 | **CZ**           | Czech Republic | CZ                 | CZE                | 2036          |
| 2022-05-12 | **CZ**           | Czech Republic | CZ                 | CZE                | 521           |
| 2022-02-05 | **ES**           | Spain          | ES                 | ESP                | 27997         |
| 2022-03-19 | **FI**           | Finland        | FI                 | FIN                | 0             |
| 2022-05-21 | **FI**           | Finland        | FI                 | FIN                | 0             |
| 2022-05-28 | **FI**           | Finland        | FI                 | FIN                | 0             |
| 2022-01-11 | **FR**           | France         | FR                 | FRA                | 368149        |
| 2022-02-13 | **FR**           | France         | FR                 | FRA                | 86562         |

The data was retrieved using the following query.

```sql
SELECT country_code, country_name, iso_3166_1_alpha_2, iso_3166_1_alpha_3, new_confirmed
FROM `bigquery-public-data`.`covid19_open_data`.`covid19_open_data`
WHERE `date` >= '2022-01-01'
LIMIT 10
```

The dictionary table with a list of all country codes is also public.
We can retrieve the list of countries using the following query.

```sql
SELECT * 
FROM `bigquery-public-data`.`country_codes`.`country_codes`
LIMIT 10
```

We aim to verify that all country codes stored in the *country_code* column of the COVID-19 cases table
have a matching two-letter country code in the dictionary of all countries.

The list of the first ten countries is shown below.

| country_name        | **alpha_2_code** | alpha_3_code |
|---------------------|------------------|--------------|
| Afghanistan         | **AF**           | AFG          |
| Albania             | **AL**           | ALB          |
| Algeria             | **DZ**           | DZA          |
| American Samoa      | **AS**           | ASM          |
| Andorra             | **AD**           | AND          |
| Angola              | **AO**           | AGO          |
| Anguilla            | **AI**           | AIA          |
| Antarctica          | **AQ**           | ATA          |
| Antigua and Barbuda | **AG**           | ATG          |
| Argentina           | **AR**           | ARG          |
| ...                 | **...**          | ...          |


### Missing keys
The following example shows a possible referential integrity issue we want to identify.
The *country_code* column may contain a full country name instead of an ISO 3166 country code, as shown below.

| date       | **country_code**            | country_name | iso_3166_1_alpha_2 | iso_3166_1_alpha_3 | new_confirmed |
|------------|-----------------------------|--------------|--------------------|--------------------|---------------|
| ...        | ...                         | ...          | ...                | ...                | ...           |
| 2022-01-11 | FR                          | France       | FR                 | FRA                | 368149        |
| 2022-02-13 | **FRANCE** <- _unknown key_ | France       | FR                 | FRA                | 86562         |


## Detecting missing keys
DQOps detects missing keys by running an SQL query that joins the tested table with
the dictionary table using a left outer join. 
Both tables must be on the data source because the join is performed inside the analyzed database. 
DQOps does not pull the data from tested data sources to detect missing keys.

The following two data checks detect missing keys.
 
- The [*lookup_key_not_found*](../checks/column/integrity/lookup-key-not-found.md) check 
  detects missing keys and counts the number of records containing invalid values.

- The [*lookup_key_found_percent*](../checks/column/integrity/lookup-key-found-percent.md) check 
  measures the percentage of rows containing missing keys.

If the dictionary table is not present in the same database, DQOps offers two other options to detect missing keys in the table.

- Upload the list of keys to DQOps as a CSV file, stored as a [data dictionary](../dqo-concepts/dqops-user-home-folder.md#data-dictionaries). 
  Then, use the [*text_found_in_set_percent*](../checks/column/accepted_values/text-found-in-set-percent.md) check
  described in the guide for [validating accepted values](how-to-validate-accepted-values-in-columns.md).

- Use the [table comparison checks](how-to-reconcile-data-and-detect-differences.md) to reconcile
  the tested table and the dictionary table. Configure the grouping of both tables by the key column.
  It will be the foreign key column in the tested table and the primary key column in the dictionary table. 
  Then, set up a comparison of a *MAX* value for both the key columns.  
  DQOps will run a query using a *GROUP BY <key>* clause on both tables.
  The [table reconciliation report](how-to-reconcile-data-and-detect-differences.md#discrepancies-detected) will reveal missing keys.


### Configure key lookup checks in UI
The referential integrity checks are present in the **integrity** category.
The following example shows how to enable the [*lookup_key_not_found*](../checks/column/integrity/lookup-key-not-found.md)
and [*lookup_key_found_percent*](../checks/column/integrity/lookup-key-found-percent.md) checks from the data quality check editor.

The checks use two parameters to identify the dictionary table and the key column in that table.

- The **foreign_table** parameter should be a fully qualified table name.
  For the purpose of this example, we are using the _&#96;bigquery-public-data&#96;.&#96;country_codes&#96;.&#96;country_codes&#96;_ table.

- The **foreign_column** parameter is the name of the primary key column in the dictionary table.

The [*lookup_key_not_found*](../checks/column/integrity/lookup-key-not-found.md) check also takes a **max_count** rule parameter, 
and the [*lookup_key_found_percent*](../checks/column/integrity/lookup-key-found-percent.md) check takes a **max_percent** rule parameter.

![Detecting missing keys using a lookup data quality check in DQOps](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/foreign-key-lookup-check-configured-in-check-editor-min.png){ loading=lazy; width="1200px" }

Both data quality checks failed, showing that 1982 records use unknown country codes., which accounts for 0.0087% of the dataset. 
Our target of 100% rows containing valid country codes was missed by 0.0087%.

### Configure key lookup check in YAML
The configuration of the [*lookup_key_not_found*](../checks/column/integrity/lookup-key-not-found.md) check is straightforward.
The most important parameters are highlighted.

``` { .yaml linenums="1" hl_lines="15-16 18"}
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    country_code:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          integrity:
            daily_lookup_key_not_found:
              parameters:
                foreign_table: '`bigquery-public-data`.`country_codes`.`country_codes`'
                foreign_column: alpha_2_code
              error:
                max_count: 0
```

The configuration of the [*lookup_key_found_percent*](../checks/column/integrity/lookup-key-found-percent.md) check is similar.

``` { .yaml linenums="1" hl_lines="15-16 18"}
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    country_code:
      type_snapshot:
        column_type: STRING
        nullable: true
      monitoring_checks:
        daily:
          integrity:
            daily_lookup_key_found_percent:
              parameters:
                foreign_table: '`bigquery-public-data`.`country_codes`.`country_codes`'
                foreign_column: alpha_2_code
              error:
                min_percent: 100.0
```

## Use cases
| **Name of the example**                                                                                                          | **Description**                                                                                                                                                                                         |
|:---------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Detect missing keys between different tables](../examples/data-accuracy/integrity-check-between-columns-in-different-tables.md) | This example shows how to check the referential integrity of a column against a column in another table using [lookup_key_found_percent](../checks/column/integrity/lookup-key-found-percent.md) check. |

## List of integrity checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*lookup_key_not_found*](../checks/column/integrity/lookup-key-not-found.md)|[Integrity](../dqo-concepts/data-quality-dimensions.md#data-integrity)|This check detects invalid values that are not present in a dictionary table. The lookup uses an outer join query within the same database. This check counts the number of values not found in the dictionary table. It raises a data quality issue when too many missing keys are discovered.|:material-check-bold:|
|[*lookup_key_found_percent*](../checks/column/integrity/lookup-key-found-percent.md)|[Integrity](../dqo-concepts/data-quality-dimensions.md#data-integrity)|This check detects invalid values that are not present in a dictionary table. The lookup uses an outer join query within the same database. This check measures the percentage of valid keys found in the dictionary table. It raises a data quality issue when a percentage of valid keys is below a minimum accepted threshold.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/integrity](../checks/column/integrity/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
