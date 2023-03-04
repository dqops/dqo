# Import metadata

**Opisujemy na przykładzie GCP i UI. Trzeba wybrac jakis przykładowy dataset, na którym potem puścimy checki**

### Table import

In order to import tables from a given source use the following command:

```
table import -c= "connection_name"
```
Next step is the schema and table selection.

The source of the project's GCP ID in this example is `bigquery-public-data` (as configured in the previous section).
There are 192 datasets in `bigquery-public-data` at the moment of writing this page. We used the first dataset `austin_311`,
which contains only one table `311_service_requests`.

```
dqo.ai> table import -c=connection_1
Select the schema (database, etc.) from which tables will be imported:
  [  1]  austin_311
  [  2]  austin_bikeshare
  [  3]  austin_crime
...
  [191]  world_bank_wdi
  [192]  worldpop
Please enter one of the [] values: 1
───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────
The following tables were imported:
+-----------+--------------------+------------+
|Schema name|Table name          |Column count|
+-----------+--------------------+------------+
|austin_311 |311_service_requests|22          |
+-----------+--------------------+------------+

```

### Table YAML file
All connections are stored as a YAML file in `userhome/sources` catalogue.

This is how the file looks like:
```yaml
--8<-- "docs/getting_started/yamls/austin_311.311_service_requests_example.dqotable.yaml"
```
It describes the table that has been imported. 

It contains the name of the schema, table and the names of the columns within the table, along with a description of the datatype of a column and whether nulls are allowed.