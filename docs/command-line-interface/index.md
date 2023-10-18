# Command-line interface
___
**This is a list of the CLI commands in DQOps broken down by category and a brief description of what they do.**

## dqo
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo ](./dqo/#dqo-) | A root command that allows the user to access all the features and functionalities of the application from the command-line interface (CLI) level. It is the first command to be used before accessing any other commands of the application. |


## cls
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo cls](./cls/#dqo-cls) | Clear the terminal screen or console, removing all the previous commands and outputs of the commands that were executed on the console. It allows the user to start with a clean slate for the next set of commands or outputs. |


## connection
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo connection list](./connection/#dqo-connection list) | Lists all the created connections for the logged-in user that match the conditions specified in the options. It allows the user to filter connections based on various parameters. |
 | [dqo connection add](./connection/#dqo-connection add) | Creates a new connection to the database with the specified details such as connection name, database type, hostname, username, and password. It allows the user to connect to the database from the application to perform various operations on the database. |
 | [dqo connection remove](./connection/#dqo-connection remove) | Removes the connection or connections that match the conditions specified in the options. It allows the user to remove any unwanted connections that are no longer needed. |
 | [dqo connection update](./connection/#dqo-connection update) | Update the connection or connections that match the conditions specified in the options with new details. It allows the user to modify existing connections in the application. |
 | [dqo connection schema list](./connection/#dqo-connection schema list) | It allows the user to view the summary of all schemas in a selected connection. |
 | [dqo connection table list](./connection/#dqo-connection table list) | List all the tables available in the database for the specified connection and schema. It allows the user to view all the tables in the database. |
 | [dqo connection table show](./connection/#dqo-connection table show) | Show the details of the specified table in the database for the specified connection. It allows the user to view the details of a specific table in the database. |
 | [dqo connection edit](./connection/#dqo-connection edit) | Edit the connection or connections that match the filter conditions specified in the options. It allows the user to modify the details of an existing connection in the application. |


## table
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo table import](./table/#dqo-table import) | Import the tables from the specified database into the application. It allows the user to import the tables from the database into the application for performing various database operations. |
 | [dqo table edit](./table/#dqo-table edit) | Edit the table or tables that match the filter conditions specified in the options. It allows the user to modify the details of an existing table in the application. |
 | [dqo table add](./table/#dqo-table add) | Add a new table with the specified name to the database. It allows the user to create a new table in the application for performing various operations. |
 | [dqo table remove](./table/#dqo-table remove) | Remove one or more tables that match a given condition. It allows user to use various filters, such as table names to narrow down the set of tables to remove. |
 | [dqo table update](./table/#dqo-table update) | Update the structure of one or more tables that match a given condition. It allows user to use various filters, such as table names  to narrow down the set of tables to update. |
 | [dqo table list](./table/#dqo-table list) | List all the tables that match a given condition. It allows the user to use various filters, such as table name or schema names to list filtered tables. |


## check
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo check run](./check/#dqo-check run) | Run data quality checks on your dataset that match a given condition. The command output is a table with the results that provides insight into the data quality. |
 | [dqo check enable](./check/#dqo-check enable) | Enable data quality checks matching specified filters |
 | [dqo check disable](./check/#dqo-check disable) | Disable data quality checks matching specified filters |


## column
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo column add](./column/#dqo-column add) | Add a new column to a table with specific details. The new column is added to the YAML configuration file. |
 | [dqo column remove](./column/#dqo-column remove) | Remove one or more columns from a table that match a specified condition. Users can filter the column. |
 | [dqo column update](./column/#dqo-column update) | Update one or more columns in a table that match a specified condition. |
 | [dqo column list](./column/#dqo-column list) | List all the columns in a table or filter them based on a specified condition. |
 | [dqo column enable](./column/#dqo-column enable) | Enable one or more columns in a table based on a specified condition. This command will restore the data in the previously disabled columns. |
 | [dqo column disable](./column/#dqo-column disable) | Disable one or more columns in a table based on a specified condition. Disabling a column will prevent it from being queried or updated until it is enabled again. |
 | [dqo column rename](./column/#dqo-column rename) | Rename one or more columns in a table based on a specified condition. |


## settings
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo settings editor set](./settings/#dqo-settings editor set) | Set the settings for the editor. It allows user to set the editor to use a specific output format. |
 | [dqo settings editor remove](./settings/#dqo-settings editor remove) | Remove the editor settings from your configuration. This will revert the editor to its default settings. |
 | [dqo settings editor show](./settings/#dqo-settings editor show) | Display the current editor settings. |
 | [dqo settings init](./settings/#dqo-settings init) | Settings file in your UserHome directory. This file stores configuration options for the DQOps. |
 | [dqo settings remove](./settings/#dqo-settings remove) | Removes the settings file from your UserHome directory. |
 | [dqo settings apikey set](./settings/#dqo-settings apikey set) | Set the API key used for accessing external services. This key is used to authenticate requests to the service. |
 | [dqo settings apikey remove](./settings/#dqo-settings apikey remove) | Remove the API key used for accessing external services. |
 | [dqo settings apikey show](./settings/#dqo-settings apikey show) | Display the current API key used for accessing external services. |
 | [dqo settings timezone set](./settings/#dqo-settings timezone set) | Set the default time zone used by the DQOps. |
 | [dqo settings timezone remove](./settings/#dqo-settings timezone remove) | Remove the custom time zone from your settings. Once removed, the time zone for your account will be set to the system default. |
 | [dqo settings timezone show](./settings/#dqo-settings timezone show) | Displays the default time zone that is currently set in your settings. This time zone will be used to display all date and time values in the application. |


## cloud
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo cloud login](./cloud/#dqo-cloud login) | Allow user to provide login credentials if the user already has an account. |
 | [dqo cloud password](./cloud/#dqo-cloud password) | Allows the user to change the password that is used to log in to DQOps Cloud account using the email and password. |
 | [dqo cloud sync data](./cloud/#dqo-cloud sync data) | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;data&quot; folder. |
 | [dqo cloud sync sources](./cloud/#dqo-cloud sync sources) | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;sources&quot; folder. |
 | [dqo cloud sync sensors](./cloud/#dqo-cloud sync sensors) | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;sensors&quot; folder. |
 | [dqo cloud sync rules](./cloud/#dqo-cloud sync rules) | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;rules&quot; folder. |
 | [dqo cloud sync checks](./cloud/#dqo-cloud sync checks) | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;checks&quot; folder. |
 | [dqo cloud sync settings](./cloud/#dqo-cloud sync settings) | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;settings&quot; folder. |
 | [dqo cloud sync credentials](./cloud/#dqo-cloud sync credentials) | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;.credentials&quot; folder. |
 | [dqo cloud sync all](./cloud/#dqo-cloud sync all) | Uploads any local changes to the cloud and downloads any changes made to the cloud versions of the folders. |


## sensor
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo sensor edit](./sensor/#dqo-sensor edit) | Allows you to modify the properties of a custom sensor that matches certain condition. |


## scheduler
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo scheduler start](./scheduler/#dqo-scheduler start) | This operation should be called only from the shell mode. When the DQOps is started as &#x27;dqo scheduler start&#x27; from the operating system, it will stop immediately. |
 | [dqo scheduler stop](./scheduler/#dqo-scheduler stop) | This operation should be called only from the shell mode after the scheduler was started. |


## data
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo data delete](./data/#dqo-data delete) | Deletes stored data that matches specified conditions. Be careful when using this command, as it permanently deletes the selected data and cannot be undone. |
 | [dqo data repair](./data/#dqo-data repair) | Verify integrity of parquet files used to store data and removes corrupted files. Be careful when using this command, as it permanently deletes the selected data and cannot be undone. |


## run
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo run](./run/#dqo-run) | This command is useful when you want to continuously monitor the quality of your data in real-time. The job scheduler runs in the background, allowing you to perform other tasks while the DQOps is running. |


## rule
| Command&nbsp;name | Description |
|--------------|-------------|
 | [dqo rule edit](./rule/#dqo-rule edit) | This command can be used to update the rule. It is important to use caution when using this command, as it can impact the execution of data quality checks. |

