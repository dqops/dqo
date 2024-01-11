# Working with DQOps Shell

## Overview

DQOps Shell provides command line access to all commands. You can perform almost the same tasks that can be done
using the user interface. 

For a list and description of all DQOps Shell commands go to [Command-line interface section](../command-line-interface/index.md)

## Starting shell as a Python package
If DQOps was installed as a python package in a Python virtual environment (venv) and the virtual environment is active,
DQOps shell can be started using a `dqo` shell script directly from the terminal as shown below.
The `dqo` script should be already on the *$PATH*. 

```
dqo
```

Alternatively, if the `dqo` shell is not on the *$PATH*, run DQOps shell using the following command. 

```
python -m dqops
```


## Starting shell in Docker
DQOps shell can be also started using the docker container. The container must be started with a terminal attached,
using the `-it` parameter to the `docker run` command as shown below.

```
docker run -v .:/dqo/userhome -it -p 8888:8888 dqops/dqo
```

The [Run DQOps in Docker](../dqops-installation/run-dqops-as-docker-container.md) manual describes other parameters
used in starting DQOps from Docker.


## Shell welcome screen
A welcome message will appear, and you can start entering commands in the DQOps Shell.

![DQOps Shell welcome screen](https://dqops.com/docs/images/working-with-dqo/working-with-dqo-shell/dqops-shell-welcome-screen.png)


To view a list of commands in the DQOps Shell, run the following command

```
dqo> help
```

You can also check the description of every command by adding `--help` (or `-h`) parameter to the name of the command.

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


## Starting DQOps in server mode

You can run DQOps in a server mode that will continuously run the programme.

To do this, simply enter the command below in your terminal
=== "`dqo`"
    ```
    dqo run
    ```
=== "`python -m dqops`"
    ```
    python -m dqops run
    ```
=== "`docker run -it dqops/dqo`"
    ```
    docker run -v .:/dqo/userhome -it -p 8888:8888 dqops/dqo run
    ```

To terminate dqo running in the background, simply use the Ctrl+C.

For more information on the `run` command, please refer to the [Command-line interface section](../command-line-interface/run.md).


## Using DQOps commands on the command-line interface

You can also use any DQOps commands directly on the command line. For example, 
to run all the activated checks use the following command

=== "`dqo`"
    ```
    dqo check run
    ```
=== "`python -m dqops`"
    ```
    python -m dqops check run
    ```
=== "`docker run -it dqops/dqo`"
    ```
    docker run -v .:/dqo/userhome -it -p 8888:8888 dqops/dqo check run
    ```
