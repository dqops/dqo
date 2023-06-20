# Data stream segmentation

Data stream is a group of rows that were loaded from a single or different sources and aggregated into one table. Data
streams in a single table can be identified by a discriminator column containing data such as location, business units,
vendor, data provider or subsidiary.

DQO supports setting of up to 9 different data streams. Data stream hierarchy levels are mapped to columns in the
monitored tables or assigned static value (tag) for grouping different tables populated from the same data stream (source).

Data streams can be used to calculate separate [data quality KPI](../data-quality-kpis/data-quality-kpis.md) scores for 
different groups of rows (data streams). Data quality scores, which are calculated for each data stream can simplify the
root cause analysis by linking the data quality incident to a data source, a data stream, an external data supplier or simply a separate data pipeline 
that has loaded invalid data.

## Identifying data sources

There are two ways to identify the data source in DQO:

- **Separate tables for each data source**. This is a simple case that can be solved by adding a tag with the name 
of the data source. A [data quality KPI](../data-quality-kpis/data-quality-kpis.md) can be calculated from multiple 
tables at once.

Here is an example of data stream configuration in the YAML file using a tag named 'UK':

``` yaml hl_lines="7-11"
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: public
    table_name: fact_sales_uk
  data_streams:
    by_country:
      level_1:
        source: tag
        tag: UK
```

- **Multiple data sources aggregated into a single table**. Data from multiple sources can be aggregated in a single 
table. If there is a column that identifies the data source, it can be used to assign the generated alerts and sensor 
readouts to the correct data stream. 

Here is another example of the YAML file that uses a 'country' column to identify separate data streams.

``` yaml  hl_lines="7-11"
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: public
    table_name: fact_sales_uk
  data_streams:
    by_country:
      level_1:
        source: column_value
        column: country
```

## Configuring data stream 

In DQO you can set up data stream globally for all tables in the connection or individually for each table.
You can set up data streams in the **Data Sources** section by clicking at the connection or individual table in the tree and 
selecting **Data Streams** tab. 

For more detailed information on setting up a data stream, go to [Working with DQO section](../../working-with-dqo/set-up-data-streams/set-up-data-stream.md).
