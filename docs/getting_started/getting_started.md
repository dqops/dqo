# Getting started

Here we will show you where and how to start using Dqo.ai after the installation.

## Running examples
Before using the application, we recommend checking out the [examples](../examples/running_examples.md).

# Adding connections

Working connection is a fundamental aspect of running data quality checks. Here you can find the 
detailed documentation for
[managing connections](../commands/connection/connection.md).

All the connections require two things specified:

- connection name,
- provider.

After running command
```
connection add
```
DQO will automatically ask you to provide a connection's name and select a provider. You can specify them in the
command itself.

After connection is added, you can display the list of existing connections by running
```
connection list
```
To access table configuration, you need to first [import](../commands/table/table.md#import) them:

```
table import -c=<connection_name>
```

then you will be able to [edit](../commands/table/table.md#edit) them where you can define checks. Run the following command
```
table edit -c=<connection_name> -t=<schema_name>.<table_name>
```

an editor should open, where you can define data quality checks ([list of data quality checks](../check_reference/list_of_checks/list_of_checks.md)).