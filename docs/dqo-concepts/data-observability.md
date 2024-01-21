# Data observability
This guide describes the default data quality checks that are applied to tables and columns during import into DQOps and are used for data observability.

## Default data observability checks
DQOps maintains a configuration of the [default data quality checks](dqops-user-home-folder.md#shared-settings)
that are applied on the tables and columns when the tables are imported into DQOps.

The default observability checks are configured in the
*[$DQO_USER_HOME/settings/defaultchecks.dqochecks.yaml](../reference/yaml/DefaultObservabilityChecksYaml.md)* file
in the [DQOps user home](dqops-user-home-folder.md).

??? info "Click to see a full *.dqotable.yaml* file with all default data observability checks activated"

    ```yaml
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      profiling_checks:
        volume:
          profile_row_count:
            warning:
              min_count: 1
        schema:
          profile_column_count: {}
      monitoring_checks:
        daily:
          volume:
            daily_row_count:
              warning:
                min_count: 1
            daily_row_count_anomaly:
              warning:
                anomaly_percent: 1.0
            daily_row_count_change:
              warning:
                max_percent: 10.0
          availability:
            daily_table_availability:
              warning:
                max_failures: 0
          schema:
            daily_column_count: {}
            daily_column_count_changed:
              warning: {}
            daily_column_list_changed:
              warning: {}
            daily_column_list_or_order_changed:
              warning: {}
            daily_column_types_changed:
              warning: {}
      columns:
        unique_key:
          type_snapshot:
            column_type: STRING
            nullable: true
          profiling_checks:
            nulls:
              profile_nulls_count:
                warning:
                  max_count: 0
              profile_nulls_percent: {}
              profile_not_nulls_count:
                warning:
                  min_count: 1
          monitoring_checks:
            daily:
              nulls:
                daily_nulls_count: {}
                daily_nulls_percent: {}
                daily_not_nulls_count:
                  warning:
                    min_count: 1
                daily_not_nulls_percent: {}
                daily_nulls_percent_anomaly:
                  warning:
                    anomaly_percent: 1.0
                daily_nulls_percent_change_1_day:
                  warning:
                    max_percent: 10.0
                    exact_day: false
              uniqueness:
                daily_distinct_count_anomaly:
                  warning:
                    anomaly_percent: 1.0
              datatype:
                daily_detected_datatype_in_text_changed:
                  warning: {}
              schema:
                daily_column_exists:
                  warning: {}
                daily_column_type_changed:
                  warning: {}
    ```

## List of default observability checks
The following table shows a list of default data quality checks and describes their purpose.

### **Profiling checks type**

| Target | Check name                                                           | Description                                                                                                                                        |
|--------|----------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| table  | [profile row count](../checks/table/volume/row-count.md)             | Counts the number of rows in a table.                                                                                                              |
| table  | [profile column count](../checks/table/schema/column-count.md)       | Retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns. |
| column | [profile nulls count](../checks/column/nulls/nulls-count.md)         | Ensures that there are no more than a set number of null values in the monitored column.                                                           |
| column | [profile nulls percent](../checks/column/nulls/nulls-percent.md)     | Ensures that there are no more than a set percentage of null values in the monitored column.                                                       |
| column | [profile_not_nulls_count](../checks/column/nulls/not-nulls-count.md) | Ensures that there are no more than a set number of null values in the monitored column.                                                           |

### **Daily monitoring checks type**

| Target | Check name                                                                                                | Description                                                                                                                                        |
|--------|-----------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| table  | [daily row count](../checks/table/volume/row-count.md)                                                    | Counts the number of rows in a table.                                                                                                              |
| table  | [daily row count anomaly](../checks/table/volume/row-count-anomaly.md)                                    | Ensures that the row count is within a two-tailed percentile from measurements made during the last 90 days.                                       |
| table  | [daily row count change](../checks/table/volume/row-count-change.md)                                      | Ensures that the row count changed by a fixed rate since the last readout.                                                                         |
| table  | [daily table availability](../checks/table/availability/table-availability.md)                            | Verifies that a table exists, can be accessed, and queried without errors.                                                                         |
| table  | [daily column count](../checks/table/schema/column-count.md)                                              | Retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns. |
| table  | [daily column count changed](../checks/table/schema/column-count-changed.md)                              | Detects whether the number of columns in a table has changed since the last time the check (checkpoint) was run.                                   |
| table  | [daily column list changed](../checks/table/schema/column-list-changed.md)                                | Detects if the list of columns has changed since the last time the check was run.                                                                  |
| table  | [daily column list or order changed](../checks/table/schema/column-list-or-order-changed.md)              | Detects whether the list of columns and the order of columns have changed since the last time the check was run.                                   |
| table  | [daily column types changed](../checks/table/schema/column-types-changed.md)                              | Detects if the column names or column types have changed since the last time the check was run.                                                    |
| column | [daily nulls count](../checks/column/nulls/nulls-count.md)                                                | Ensures that there are no more than a set number of null values in the monitored column.                                                           |
| column | [daily nulls percent](../checks/column/nulls/nulls-percent.md)                                            | Ensures that there are no more than a set percentage of null values in the monitored column.                                                       |
| column | [daily not nulls count](../checks/column/nulls/not-nulls-count.md)                                        | Ensures that there are no more than a set number of null values in the monitored column.                                                           |
| column | [daily not nulls percent](../checks/column/nulls/not-nulls-percent.md)                                    | Ensures that there are no more than a set percentage of not null values in the monitored column.                                                   |
| column | [daily nulls percent anomaly](../checks/column/nulls/nulls-percent-anomaly.md)                            | Ensures that the null percent value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.        |
| column | [daily nulls percent change 1 day](../checks/column/nulls/nulls-percent-change-1-day.md)                  | Ensures that the null percent in a monitored column has changed by a fixed rate since the last readout from yesterday.                             |
| column | [daily_distinct_count_anomaly](../checks/column/uniqueness/distinct-count-anomaly.md)                     | Ensures that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days             |
| column | [daily detected datatype in text changed](../checks/column/datatype/detected-datatype-in-text-changed.md) | Scans all values in a string column and detects the data type of all values in a column.                                                           |
| column | [daily column exists](../checks/column/schema/column-exists.md)                                           | Reads the metadata of the monitored table and verifies that the column still exists in the data source.                                            |
| column | [daily column type changed](../checks/column/schema/column-type-changed.md)                               | Detects if the data type of the column has changed since the last time it was retrieved.                                                           |

### **Column type specific checks**
DQOps uses the imported data type of the column to decide what type of type specific default checks are enabled.

The following default checks are enabled only on text or numeric columns.

| Numeric columns                                                | Text columns                                                                                              |
|----------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| [daily mean anomaly](../checks/column/anomaly/mean-anomaly.md) | [daily detected datatype_in_text changed](../checks/column/datatype/detected-datatype-in-text-changed.md) |
| [daily sum anomaly](../checks/column/anomaly/sum-anomaly.md)   |                                                                                                           |

## Modify configuration of default checks

The easiest way to change the default configuration is by using the *Default checks* editor in the *Configuration* section
of the DQOps user interface.

![Default checks editor](https://dqops.com/docs/images/concepts/default-checks-editor.png)

To do so, select the **Configuration** section, and then click on **Default checks configuration** on the tree view on 
the left side. Afterward, you can modify the default check configuration for **Profiling checks**, **Monitoring daily**,
and **Monitoring monthly** in the workspace on the right side.

The default configuration can be also changed by editing the 
*[$DQO_USER_HOME/settings/defaultchecks.dqochecks.yaml](../reference/yaml/DefaultObservabilityChecksYaml.md)* 
file directly, using Visual Studio Code.

## Next steps

- You haven't installed DQOps yet? Check the detailed guide on how to [install DQOps using pip](../dqops-installation/install-dqops-using-pip.md) or [run DQOps as a Docker container](../dqops-installation/run-dqops-as-docker-container.md).
- Check the sample which shows [how to use the default DQOps data quality checks to detect empty tables](../examples/data-quality-monitoring/detect-empty-tables.md) and view the results on data quality dashboards.
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- DQOps provide you with summary statistics about your table and column. This information can be valuable in deciding which data quality checks and threshold levels should be set to monitor data quality. For more details about [Basic data statistics, click here](../working-with-dqo/collecting-basic-data-statistics.md).