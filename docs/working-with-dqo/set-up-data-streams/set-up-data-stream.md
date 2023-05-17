# Set up data stream segmentation

Data stream is a group of rows that were loaded from a single or different sources and aggregated into one table. 

In  DQO, data streams in a single table can be identified by a discriminator column containing data such as location, business units,
vendor, data provider or subsidiary. Data from separate tables can also be aggregated into single data stream by adding a tag with the name
of the data source.

Data streams are used to calculate separate [data quality KPI](../../dqo-concepts/data-quality-kpis/data-quality-kpis.md) scores for
different groups of rows. 

For more information about data stream segmentation, see [DQO concepts section](../../dqo-concepts/data-stream-segmentation/data-stream-segmentation.md).

In DQO, you can set up data stream globally at the data source level, in which case the configuration will be copied to the data stream
configuration of the tables that will be imported. Note that this configuration does not affect tables that have already been imported.

You can also configure data stream individually for each table. 


## Set up data stream using the graphical interface

To set up a data stream at the data source level, follow the steps below.

1. In DQO graphical interface, go to the **Data Source** section, select the data source of interest in the tree view on the left
    side, and select **Default Data Stream Template** tab.

    ![Default data stream template tag](https://dqo.ai/docs/images/working-with-dqo/set-up-data-stream/default-data-stream-template.jpg)

2. In the **Default Data Stream Template** tab, write a tag for a data source or select the discriminator column that would
    identify the data source. You can configure up to 9 different data streams.

   ![Adding data stream](https://dqo.ai/docs/images/working-with-dqo/set-up-data-stream/adding-data-stream.jpg)

3. Once you have set the data stream, click on the **Save** button to save your changes.

The default data stream configuration added at the data source level will be copied to the data stream configuration of all the 
tables that will be imported. Note that this configuration does not affect tables that have already been imported.

To set up data stream at the table level:

1.  In DQO graphical interface, go to the **Data Source** section, select the table of interest in the tree view on the left
    side, and select **Data Streams** tab.

    ![Adding table-level data stream](https://dqo.ai/docs/images/working-with-dqo/set-up-data-stream/adding-table-level-data-stream.jpg)

2. Click on the **New data stream configuration** button.

3. Write a **Data stream name** and select a tag for a data stream or select the discriminator column that would identify the data source.

   ![Table-level data stream configuration](https://dqo.ai/docs/images/working-with-dqo/set-up-data-stream/table-level-data-stream-configuration.jpg)

4. Once you have set the data stream, click on the **Save** button to save your changes.

5. After adding new data stream you can set which one is the default one by clicking on the **Make Default** button.

    ![Setting default data stream](https://dqo.ai/docs/images/working-with-dqo/set-up-data-stream/setting-default-data-stream.jpg)


## Set up data stream using the DQO Shell

Data quality checks are stored in YAML configuration files. YAMl configuration files are located in the `./sources` folder.
The complete DQO YAML schema can be found [here](https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json).

The YAML files in DQO support code completion in code editors such as Visual Studio Code. Remember to install YAML
extension by RedHat and Better Jinja by Samuel Colvin

To add data stream using the DQO Shell, follow the steps below.

1. Run the following command in DQO Shell to edit YAMl configuration file and define data stream.

    ```
    dqo.ai> connection edit
    ```

2. Provide the connection name.

    ```
    Connection name (--connection): testconnection
    ```
   
    After providing the above data Visual Studio Code will be automatically launched.

3. Add the data stream configuration to the YAML file using Visual Studio Code editor and save the file.

    - Add the `default_data_stream_mapping:` parameter. 
    - Add the level of the datastream such as `level_1:`, and specify the `source:` as `tag` or `column_value`
    - Add a name for the `tag:` or `column:` parameters. 

    Below are examples of the YAML files showing a sample configuration of a default data stream with a tag or column. 

=== "Configuration of the tag1 data stream"
    ```yaml hl_lines="8-11"
    apiVersion: dqo/v1
    kind: source
    spec:
      provider_type: bigquery
      bigquery:
        source_project_id: bigquery-public-data
        authentication_mode: google_application_credentials
      default_data_stream_mapping:
        level_1:
          source: tag
          tag: tag1
      incident_grouping:
        grouping_level: table_dimension_category
        minimum_severity: warning
        max_incident_length_days: 60
        mute_for_days: 60
    ```

=== "Configuration of the column address data stream"
    ```yaml hl_lines="8-11"
    apiVersion: dqo/v1
    kind: source
    spec:
      provider_type: bigquery
      bigquery:
        source_project_id: bigquery-public-data
        authentication_mode: google_application_credentials
      default_data_stream_mapping:
        level_1:
          source: column_value
          column: address
      incident_grouping:
        grouping_level: table_dimension_category
        minimum_severity: warning
        max_incident_length_days: 60
        mute_for_days: 60
    ```