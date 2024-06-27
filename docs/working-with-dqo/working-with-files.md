# Working with files

Learn about the advanced import configuration of files in DQOps.  

## Import configuration

An import of a file format provides two settings levels - a connection level and a table level.
They are allow for a different configuration in the three sections:

- File format
- Virtual schema name to path mappings
- Additional format options

The table level settings are available on the table view after finishing of adding a new connection.
To open the table view, expand the connection, then schema and click on the table.

### File format

The selection of the file format can only be configured at **the connection level** settings when creating a new connection.

!!! warning "Changing the file format"

    Changing the type of file format requires creating a new connection.

    You should never change the file format type after the connection creation. This will lead to connection resource corruption.

### Virtual schema name to path mappings

A connection can store multiple schemas.

The configuration of the **virtual schema name**, which is an alias for the parent directory with data, is only available at the connection settings level.

A **path** is an absolute path to that parent directory.
The path is utilized differently, depending on the configuration level.

The configuration at **the connection level** sets the parent directory in which directories or files are read.
It is used on the import stage where lists objects in the given directory.

The configuration at **the table level** provides a path pattern to the files allowing to work on them.

!!! info "Automatic path creation"

    When importing a new table, the path patten to the table is created automatically based on the connection level setting.

    The path pattern is filled to the settings at the table level based on the path. You can always modify the path pattern.

!!! info "The wildcards in the path"

    The wildcard sign (\*) replaces any number of characters on the folder level (between two slashes).
    The double wildcard sign (\*\*) replaces any characters (including any folders down).
    This helps to define a hive partitioning pattern because a pattern does not need to contain folder-level wildcard for each of the partitions.


### Additional format options

The additional format options are available **on both setting levels**. 
The options settings on the connection level are considered as default settings, which are common to all tables in the connection.
Whereas, the additional format options set at table settings override the default settings from the connection when they are configured in the table settings.


## Working with multiple tables in a single schema

### Different file formats

If you want to add a new file that differs in a format from the common setting used for other files in the schema,
override the configuration on that new file at the table level.

The loaded table will try to use the properties described in the table’s YAML, if available.
If not, it will use the properties configured in the YAML file of the table connection.
Finally, if both are not set explicitly, the automatically detected properties of the CSV or JSON format are used.

### No common prefix for files

When files are placed in different locations where you cannot specify a common prefix, you can use an absolute path for each file.
The absolute path has to be added on the table level setting.

You have to manually create a table in the UI. Open Data Sources, expand your connection, and select three dots on the schema to which you want to add a new table.

Fill in the "File path or file name pattern" field with the absolute path to the file and add the new table by clicking the **Save** button.

!!! tip "Creating a new table manually in the user home"

    You can also add a new table manually in the sources folder of your user home by creating a new YAML file in the connection folder.

