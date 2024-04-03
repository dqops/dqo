---
title: Working with DQOps command-line interface
---
# Working with DQOps command-line interface
Read this guide to understand the concept of DQOps command line interface.

## Overview

The DQOps Command-Line Interface enables you to interact with DQOps using commands in Linux shells or Windows command prompt.
DQOps CLI is an alternative to DQOps user interface.

## Command structure

You can use DQOps commands directly in your command-line shell, or in DQOps Shell after you start the application. 
In your command line use the base call to DQOps program `dqo` with the optional [root_level_parameter](../command-line-interface/dqo.md),
command which you would like to use and command options and parameters. 

While in DQOps shell you just need to type the command and options or parameters. An example below show the differences in
the two modes for the `check run` command that run all activated checks.

**DQOps command structure example**  
=== "Command-line"

    ```
    $ dqo [optional root_level_parameter] check run [options or parameters]
    ```
=== "DQOps Shell"

    ```
    dqo> check run [options or parameters]
    ```

## Autocomplete

DQOps Shell supports autocompletion, so you only need to start typing the command, hit the Tab and the DQOps Shell will show you
a list available options.

For example, if you want to add a new connection using the `connection add` command, just start typing `c`.
The suggested command will be displayed below, and you can select the `connection` using the Tab key.

![Autocomplete-commands](https://dqops.com/docs/images/working-with-dqo/working-with-dqo-shell/autocomplete-commands.png)

DQOps also supports autocomplete of parameters, connection names or table names.

For example, if you want to run all activated checks on single table, simply start typing `-` or `--` after the `check run` command,
and the available parameters will be displayed. Just start typing the name of the parameter and hit the Tab key to use autocomplete.

![Autocomplete-parameters](https://dqops.com/docs/images/working-with-dqo/working-with-dqo-shell/autocomplete-parameters.png)

Add the connection name using the `--connection=` parameter and select the connection using the Tab key. Then you can narrow
down your choice to a single table with `--table=` parameter.

![Autocomplete-tables](https://dqops.com/docs/images/working-with-dqo/working-with-dqo-shell/autocomplete-tables.png)


## Getting help

You can get help with any command by simply typing `--help` or `-h` at the end of a command name.

For example, the following command in DQOps Shell displays description and all parameters for `check run` command that run
all activated checks.

```
dqo> check run --help
```

## List of commands

You can find the complete list of commands and parameters in the [command-line](../command-line-interface/index.md) interface section.

## Integrating DQOps into shell scripts
All DQOps commands can be executed directly from the operating system shell, such as **bash** or Windows Command Line.
DQOps script [`dqo`](../command-line-interface/dqo.md) (on Linux/MacOS) or `dqo.com` on Windows always returns the error of executing the command as an exit code.

The following *bash* script shows an example of running data quality checks for a given connection.
The script will exit with an error if the [`dqo check run` command](../command-line-interface/check.md#dqo-check-run) detected any data quality issues.
The `$DQO_HOME` environment variable should contain the location where DQOps was [installed from a release package](../dqops-installation/install-dqops-from-release-package.md). 

```bash
#! /bin/bash
#### your code before running DQOps

$DQO_HOME/bin/dqo check run -c=connection_name

if [ $? -ne 0 ]; then
  echo "Data quality checks failed, the highest severity issue is at a severity level $?" 
  exit $?
fi

#### your code after running DQOps
```

The exit codes of the [`dqo check run`](../command-line-interface/check.md#dqo-check-run) command are listed below.

| Exit code | Run checks result                                                                                                                                                        |
|-----------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0         | All data quality checks passed, no data quality issues were detected                                                                                                     |
| 1         | A **warning** level severity issue was detected                                                                                                                          | 
| 2         | An **error** level severity issue was detected                                                                                                                           | 
| 3         | A **fatal** level severity issue was detected                                                                                                                            | 
| 4         | A data quality check failed to run due to an execution error, <br/>caused by problems with connectivity to a data source, an invalid template or an invalid Python rule. | 


## What's next

- Look at the full documentation of all DQOps [command-line commands](../command-line-interface/index.md).
- If you want to integrate DQOps into Python scripts or initiate running data quality checks from external tools,
  follow the documentation of the [DQOps REST API Python client](../client/index.md) for examples of calling 
  any operations from Python or using *curl*.
