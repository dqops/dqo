# Jakie rzeczy chcemy tu opisywać?


This section is about how to use the DQO shell.

Open a Command Prompt, change the directory to the one where DQO.ai is installed and run:

=== "Windows"
    ```python
    dqo
    ```
=== "MacOS"
    ``` python
    ./dqo
    ```
=== "Linux"
    ``` python
    ./dqo
    ```

In the first run DQO.ai will ask you if you
want to initialize `user home` at specified directory, and if you want to log in to the dqo cloud (this step is
necessary to use `cloud` commands, there is a separate article on [how to push the data to the cloud](push-data-to-the-cloud.md) ).

```
(dqoai) C:\>dqo
      _                               _
   __| |   __ _    ___         __ _  (_)
  / _` |  / _` |  / _ \       / _` | | |
 | (_| | | (_| | | (_) |  _  | (_| | | |
  \__,_|  \__, |  \___/  (_)  \__,_| |_|
             |_|

 :: DQO.AI Data Quality Observer ::    (v0.1.0)
 
Initialize a DQO user home at C:\dqoai\. [Y,n]: y
Log in to DQO Cloud? [Y,n]: y
Opening the DQO Cloud API Key request, please log in or create your DQO Cloud account.
DQO Cloud API Key request may be opened manually by navigating to: https://cloud.dqo.ai/requestapikey/...
Please wait up to 30 seconds after signup/login or press any key to cancel
API Key: your-key
DQO Cloud API Key was retrieved and stored in the settings.
dqo.ai>
```
Type `help` to list some basic commands.

```
dqo.ai> help
 -  PicocliCommands registry
Summary: check      Run checks matching specified filters
         cloud      Connect and synchronize the data with DQO Cloud
         cls        Clears the screen
         column     Modify and list columns
         connection Modify or list connections
         settings   Show or set settings
         table      Modify or list tables
```

Let's check whether any connection has been established, for that use the `connection` command.

Notice that autocompletion is available, press the tab key to accept a suggestion.

![Autocompletion](cooooorrrrrrrr.jpg)

Moreover, once the first part of a command is typed, there are suggestions on the next part:

```
dqo.ai> connection
add      list     remove   schema   table    update

────connection [-h] [-hl] [-of=<outputFormat>] [COMMAND]
```

In order to get a help on a command, type `--help` or use the short version `-h` following the command.

```
dqo.ai> connection -h
Usage:  connection [-h] [-hl] [-of=<outputFormat>] [COMMAND]
Modify or list connections
  -h, --help            Show the help for the command and parameters
      -hl, --headless   Run the command in an headless (no user input allowed)
                          mode
      -of, --output-format=<outputFormat>
                        Output format for tabular responses
Commands:
  list    List connections which match filters
  add     Add connection with specified details
  remove  Remove connection or connections which match filters
  update  Update connection or connections which match filters
  schema  Show connection schemas
  table   Show and list connection tables
```

The goal in this example is to check whether any connection has been established. According to the above `connection list` is the right command.

```
dqo.ai> connection list
+-------------------+---------------+---------------+--------+----------------------+
|Hash Id            |Connection Name|Connection Type|JDBC Url|Physical Database Name|
+-------------------+---------------+---------------+--------+----------------------+
|1846543424268075406|connection_1   |bigquery       |        |                      |
+-------------------+---------------+---------------+--------+----------------------+
```

The output can be storred as a table, .txt, .csv or .json file.

In order to do this, type `--output-format=outputFormat` or the short version `-of=outputFormat` following the command, in this case it is `connection list`.