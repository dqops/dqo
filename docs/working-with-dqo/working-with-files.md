# Working with files - advanced import configuration options

This guide dives into the advanced import configuration options for files in DQOps. 
It covers how to define file formats, specify data paths, and handle scenarios with multiple tables or varying file formats within a schema.

## Import configuration

DQOps allows configuration for file imports at connection and table levels. You can configure:

- **File format:** The overall format for files associated with the connection (e.g., CSV, JSON).
- **Virtual schema name to path mappings:** Define an alias (virtual schema name) for the parent directory containing your data files.
- **Additional format options:** Define format-specific options relevant to the particular table.

Table-level settings become available on the table view after adding a new connection and adding a table. 
To access the table view, expand the connection on the tree view on the left, then the schema, and click on the table.

### File format configuration

**Setting the file format:** File format selection can only be configured at the connection level settings when creating a new connection.

**Changing the file format:** Modifying the file format type requires creating a new connection. **Never attempt to change the format after connection creation**, as it can corrupt the connection resource.

### Virtual schema name to path mappings

A connection can store multiple schemas (virtual names) representing data directories.
**The virtual schema name**, which is an alias for the parent directory with data, can only be configured at the connection settings level.

**A path** is an absolute path to the parent directory. Depending on the configuration level, the path is utilized differently:

**Connection level:** The configuration at the connection level sets the parent directory in which directories or files are read. It is used in the import stage, which lists objects in the given directory.

**Table level:** The configuration at the table level provides a path pattern to the files, allowing you to work on them.

!!! info "Automatic path creation"

    When importing a new table, DQOps automatically generates a path pattern to the table based on the connection-level setting.
    
    You can modify this path pattern at the table level for further customization.


!!! info "The wildcards in the path"

    The wildcard sign (\*) replaces any number of characters on the folder level (between two slashes). 
    The double wildcard sign (\*\*) replaces any characters (including any folders below). 
    This is useful for defining Hive partitioning patterns because a pattern does not need to contain a folder-level wildcard for each of the partitions.


### Additional format options

Additional format options are available at both connection and table levels.

**Connection level:** These settings define defaults for all tables within the connection.

**Table level:** The additional format options, set in table settings, override the default settings from the connection.

## Working with multiple tables in a single schema

### Different file formats

If a new file has a format different from others within the schema, override the default connection settings at the table level.

If available, the loaded table will try to use the properties configured at the table level in the YAML file. 
If no table-level configuration is present in the YAML file, connection-level properties are used. 
Finally, if no configuration is configured, DQOps attempts automatic format detection (for CSV and JSON).

### No common prefix for files

When files reside in various locations without a common prefix, use absolute paths for each file. The absolute path must be added at the table-level setting.

To do that, you have to manually create a table in the user interface:

1. Navigate to the  Data Sources section.
2. Expand your connection and click on the three-dots icon next to the name of the schema to which you want to add a new table.
3. In the Add table popup window, Fill in the "File path or file name pattern" field with the absolute path to the file
4. Click on the Save button to add a new table.

### Creating a new table manually in the DQOps user home folder

You can also manually add a new table by creating a new YAML file within the connection's folder located in the [DQOps user home folder](../dqo-concepts/dqops-user-home-folder.md).

## Next steps

- We have provided a variety of use cases that use openly available datasets from [Google Cloud](https://cloud.google.com/datasets) to help you in using DQOps effectively. You can find the [full list of use cases here](../examples/index.md).
- DQOps allows you to keep track of the issues that arise during data quality monitoring and send alert notifications directly to Slack. Learn more about [incidents](../working-with-dqo/managing-data-quality-incidents-with-dqops.md) and [notifications](../integrations/webhooks/index.md).
- The data in the table often comes from different data sources and vendors or is loaded by different data pipelines. Learn how [data grouping in DQOps](../working-with-dqo/set-up-data-grouping-for-data-quality-checks.md) can help you calculate separate data quality KPI scores for different groups of rows.
