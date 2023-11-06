# Working with DQO Shell

DQO Shell provides command line access to all commands. You can perform almost all of the same tasks that can be done
using the graphical interface. 

For a list and description of all DQO Shell commands go to [Command-line interface section](../../command-line-interface/index.md)

To use DQO Shell, just start DQO application in the terminal using the following command

=== "Windows"
    ```
    dqo
    ```
=== "MacOS/Linux"
    ```
    ./dqo
    ```

A welcome message will appear, and you can start entering commands in the DQO Shell.

```
  ___     ___     ___
 |   \   / _ \   / _ \   _ __   ___
 | |) | | (_) | | (_) | | '_ \ (_-<
 |___/   \__\_\  \___/  | .__/ /__/
                        |_|
 :: DQOps Data Quality Operations Center ::    

Press CTRL and click the link to open it in the browser:
- DQOps User Interface Console (http://localhost:8888)
- DQOps API Reference (http://localhost:8888/swagger-ui/)
dqo>

```

To view a list of commands in the DQO Shell run the following command

```
dqo> help
```

You can also check the description of every command by adding `--help` (or `-h`) parameter to the name of the command.

## Autocomplete

DQO Shell supports autocompletion, so you only need to start typing the command, hit the Tab and the DQO Shell will show you
a list available options.

For example, if you want to add a new connection using the `connection add` command, just start typing `c`.
The suggested command will be displayed below, and you can select the `connection` using the Tab key.

![Autocomplete-commands](https://dqops.com/docs/images/working-with-dqo/working-with-dqo-shell/autocomplete-commands.png)

DQO also supports autocomplete of parameters, connection names or table names.

For example, if you want to run all enabled checks on single table, simply start typing `-` or `--` after the `check run` command,
and the available parameters will be displayed. Just start typing the name of the parameter and hit the Tab key to use autocomplete.

![Autocomplete-parameters](https://dqops.com/docs/images/working-with-dqo/working-with-dqo-shell/autocomplete-parameters.png)

Add the connection name using the `--connection=` parameter and select the connection using the Tab key. Then you can narrow
down your choice to a single table with `--table=` parameter. 

![Autocomplete-tables](https://dqops.com/docs/images/working-with-dqo/working-with-dqo-shell/autocomplete-tables.png)


## Starting DQO in server mode

You can run DQO in a server mode that will continuously run the programme.

To do this, simply enter the command below in your terminal
```
$ dqo run
```
To terminate dqo running in the background, simply use the Ctrl+C.

For more information on the `run` command, please refer to the [Command-line interface section](../../command-line-interface/run.md).


## Using DQO commands on the command-line interface

You can also use any DQO commands directly on the command line. Just add `dqo` before the name of the command. For example, 
to run all the enabled checks use the following command

```
$ dqo check run
```