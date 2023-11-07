# Setting up data grouping

Data group is a group of rows that were loaded from a single or different sources and aggregated into one table. 

In  DQOps, data grouping in a single table can be identified by a discriminator column containing data such as location, business units,
vendor, data provider or subsidiary. Data from separate tables can also be aggregated into single data group by adding a tag with the name
of the data source.

Data groups are used to calculate separate [data quality KPI](../../dqo-concepts/data-quality-kpis/data-quality-kpis.md) scores for
different groups of rows. 

For more information about data grouping, see [data grouping in the DQOps concepts section](../../dqo-concepts/data-grouping/data-grouping.md).

In DQOps, you can set up data grouping globally at the data source level, in which case the configuration will be copied to the data grouping
configuration of the tables that will be imported. Note that this configuration does not affect tables that have already been imported.

The data grouping should be configured individually for each table, which allows to select columns from the monitored
table that will be used in the **GROUP BY** clause to identify each group of data coming from a different data source.


## Set up data grouping using the user interface

To set up the default data grouping configuration template at the data source level, follow the steps below.

1. In DQOps user interface, go to the **Data Source** section, select the data source of interest in the tree view on the left
    side, and select **Default Grouping Template** tab.

    ![Default data stream template tag](https://dqops.com/docs/images/working-with-dqo/set-up-data-stream/default-data-stream-template.jpg)

2. In the **Default Data Stream Template** tab, write a tag for a data source or select the discriminator column that would
    identify the data source. You can configure up to 9 different data grouping dimension levels.

    ![Adding data stream](https://dqops.com/docs/images/working-with-dqo/set-up-data-stream/adding-data-stream.jpg)

3. Once you have set the data grouping configuration, click on the **Save** button to save your changes.

The default data grouping configuration added at the data source level will be copied to the data grouping configuration of all the 
tables that will be **imported in the future**. Note that this configuration does not affect tables that have already been imported.

To set up data grouping configuration at the table level:

1.  In DQOps user interface, go to the **Data Source** section, select the table of interest in the tree view on the left
    side, and select **Data Grouping** tab.

    ![Adding table-level data grouping configuration](https://dqops.com/docs/images/working-with-dqo/set-up-data-stream/adding-table-level-data-stream.jpg)

2. Click on the **New data grouping configuration** button.

3. Write a **Data grouping name** and select a tag for a data grouping or select the discriminator column that would identify the data source.

    ![Table-level data grouping configuration](https://dqops.com/docs/images/working-with-dqo/set-up-data-stream/table-level-data-stream-configuration.jpg)

4. Once you have set the data grouping configuration, click on the **Save** button to save your changes.

5. After adding the new data grouping configuration you can set which one is the default one by clicking on the **Make Default** button.
   The default data grouping configuration is used for running all data quality checks on the table.
   If the data grouping configuration uses grouping by discriminator columns, all data quality checks started
   when the configuration is active will use a **GROUP BY** clause, calculating separate data quality results for each group of data. 

    ![Setting default data grouping configuration](https://dqops.com/docs/images/working-with-dqo/set-up-data-stream/setting-default-data-stream.jpg)
    
    On this screen you can also edit or delete data grouping configurations. 

## Set up data grouping configuration using the DQOps Shell
For the purpose of this section, we will show how to edit a *.dqotable.yaml* file directly from the DQOps command-line shell
by opening Visual Studio Code as an editor.

In order to fully take advantage of code completion for DQOps YAML files, please follow
the [Visual Studio Code configuration guide](../../integrations/visual-studio-code/index.md) to learn how to install
required extensions.

To set up a default data grouping configuration at the data source level, follow the steps below.
This default setting is not used on tables on the data source. DQOps uses this configuration only once, when the
table metadata is imported to [DQOps user home](../../dqo-concepts/home-folders/dqops-user-home.md). The connection-level
data grouping configuration is copied to the *.dqotable.yaml* file.

1. Run the following command in DQOps Shell to edit YAML configuration file and define the default data grouping.

    ```
    dqo> connection edit
    ```

2. Provide the connection name.

    ```
    Connection name (--connection): testconnection
    ```
   
    After providing the above data Visual Studio Code will be automatically launched.

3. Add the default data grouping configuration to the YAML file using Visual Studio Code editor and save the file.

    - Add the `default_grouping_configuration:` parameter. 
    - Add the level of the data groupings (dimensions) such as `level_1:`, and specify the `source:` as `tag` or `column_value`
    - Add a name for the `tag:` or `column:` parameters. 

    Below are examples of the YAML files showing a sample configuration of a default data grouping configuration with a tag or column. 

=== "Configuration of data grouping by a static tag value"
    ```yaml hl_lines="8-11"
    apiVersion: dqo/v1
    kind: source
    spec:
      provider_type: bigquery
      bigquery:
        source_project_id: bigquery-public-data
        authentication_mode: google_application_credentials
      default_grouping_configuration:
        level_1:
          source: tag
          tag: tag1
      incident_grouping:
        grouping_level: table_dimension_category
        minimum_severity: warning
        max_incident_length_days: 60
        mute_for_days: 60
    ```

=== "Configuration of grouping by country column"
    ```yaml hl_lines="8-11"
    apiVersion: dqo/v1
    kind: source
    spec:
      provider_type: bigquery
      bigquery:
        source_project_id: bigquery-public-data
        authentication_mode: google_application_credentials
      default_grouping_configuration:
        level_1:
          source: column_value
          column: country
      incident_grouping:
        grouping_level: table_dimension_category
        minimum_severity: warning
        max_incident_length_days: 60
        mute_for_days: 60
    ```

To set up a data grouping configuration at the table level

1. Run the following command in DQOps Shell to edit YAML configuration file and define data stream.

    ```
    dqo> table edit
    ```

2. Provide the connection name and full table name in a schema.table format.

    ```
    Connection name (--connection): testconnection
    Full table name (schema.table), supports wildcard patterns 'sch*.tab*': austin_crime.crime
    ```
   After entering the above data, Visual Studio Code will be automatically launched.

3. Add the data grouping configuration to the YAML file using Visual Studio Code editor and save the file.

    - Add the `groupings:` parameter above the `column:` section.
    - Add a name of the data grouping configuration
    - Add the level of the data grouping (dimension) such as `level_1:`, and specify the `source:` as `tag` or `column_value`
    - Add a name for the `tag:` or `column:` parameters.

    Below is an examples of the YAML files showing a sample configuration of 2 data grouping configurations: 

    - "Column_data_stream" with data grouping set on category column
    - "Tag_data_stream" with data grouping by assigning a tag `landing_zone` to all data quality results.

    ``` yaml hl_lines="7-15"
    apiVersion: dqo/v1
    kind: table
    spec:
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      data_streams:
        Column_data_stream:
          level_1:
            source: column_value
            column: category
        Tag_data_stream:
          level_1:
            source: tag
            tag: landing_zone
      columns:
        unique_key:
          type_snapshot:
            column_type: INT64
            nullable: true
        address:
          type_snapshot:
            column_type: STRING
            nullable: true
    ```
   
