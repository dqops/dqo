# Working with Command-Line Interface

The DQOps Command-Line Interface enables you to interact with DQOps using commands in Linux shells or Windows command prompt.
DQOps CLI is an alternative to DQOps graphical user interface.

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

## Getting help

You can get help with any command by simply typing `--help` or `-h` at the end of a command name.

For example, the following command in DQOps Shell displays description and all parameters for `check run` command that run
all enabled checks.

```
dqo> check run --help
```

## List of commands

You can find the complete list of commands and parameters in the [command-line](../../command-line-interface/index.md) interface section.

