# Working with Command-Line Interface

The DQOps Command-Line Interface enables you to interact with DQOps using commands in Linux shells or Windows command prompt.
DQOps CLI is an alternative to DQOps user interface.

## Command structure

You can use DQOps commands directly in your command-line shell, or in DQOps Shell after you start the application. 
In your command line use the base call to DQOps program `dqo` with the optional [root_level_parameter](../../command-line-interface/dqo.md),
command which you would like to use and command options and parameters. 

While in DQOps shell you just need to type the command and options or parameters. An example below show the differences in
the two modes for the `check run` command that run all enabled checks.

**DQOps command structure example**  
=== "Command-line"

    ```
    $ dqo [optional root_level_parameter] check run [options or parameters]
    ```
=== "DQO Shell"

    ```
    dqo> check run [options or parameters]
    ```

## Autocomplete

DQOps Shell supports autocompletion, so you only need to start typing the command, hit the Tab and the DQO Shell will show you
a list available options.

For example, if you want to add a new connection using the `connection add` command, just start typing `c`.
The suggested command will be displayed below, and you can select the `connection` using the Tab key.

![Autocomplete-commands](https://dqops.com/docs/images/working-with-dqo/working-with-dqo-shell/autocomplete-commands.png)

DQOps also supports autocomplete of parameters, connection names or table names.

For example, if you want to run all enabled checks on single table, simply start typing `-` or `--` after the `check run` command,
and the available parameters will be displayed. Just start typing the name of the parameter and hit the Tab key to use autocomplete.

![Autocomplete-parameters](https://dqops.com/docs/images/working-with-dqo/working-with-dqo-shell/autocomplete-parameters.png)

Add the connection name using the `--connection=` parameter and select the connection using the Tab key. Then you can narrow
down your choice to a single table with `--table=` parameter.

![Autocomplete-tables](https://dqops.com/docs/images/working-with-dqo/working-with-dqo-shell/autocomplete-tables.png)


## Getting help

You can get help with any command by simply typing `--help` or `-h` at the end of a command name.

For example, the following command in DQOps Shell displays description and all parameters for `check run` command that run
all enabled checks.

```
dqo> check run --help
```

## List of commands

You can find the complete list of commands and parameters in the [command-line](../../command-line-interface/index.md) interface section.

