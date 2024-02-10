# Detect referential integrity issues and missing keys
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


### Example of referential integrity 

```sql
SELECT country_code, country_name, iso_3166_1_alpha_2, iso_3166_1_alpha_3, new_confirmed
FROM `bigquery-public-data`.`covid19_open_data`.`covid19_open_data`
WHERE `date` >= '2022-01-01'
LIMIT 10
```

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



```sql
SELECT * 
FROM `bigquery-public-data`.`country_codes`.`country_codes`
LIMIT 10
```

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



| date       | **country_code**            | country_name | iso_3166_1_alpha_2 | iso_3166_1_alpha_3 | new_confirmed |
|------------|-----------------------------|--------------|--------------------|--------------------|---------------|
| ...        | ...                         | ...          | ...                | ...                | ...           |
| 2022-01-11 | FR                          | France       | FR                 | FRA                | 368149        |
| 2022-02-13 | **FRANCE** <- _unknown key_ | France       | FR                 | FRA                | 86562         |


## Detecting integrity issues
How to detect integrity data quality issues.


## Use cases
| **Name of the example**                                                                                                                 | **Description**                                                                                                                                                                                         |
|:----------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Integrity check between columns in different tables](../examples/data-accuracy/integrity-check-between-columns-in-different-tables.md) | This example shows how to check the referential integrity of a column against a column in another table using [lookup_key_found_percent](../checks/column/integrity/lookup-key-found-percent.md) check. |

## List of integrity checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*lookup_key_not_found*](../checks/column/integrity/lookup-key-not-found.md)|Integrity|A column-level check that ensures that there are no more than a maximum number of values not matching values in another table column.|:material-check-bold:|
|[*lookup_key_found_percent*](../checks/column/integrity/lookup-key-found-percent.md)|Integrity|A column-level check that ensures that there are no more than a minimum percentage of values matching values in another table column.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/integrity](../checks/column/integrity/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
