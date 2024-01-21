# DQOps command-line interface
All command-line commands supported from the DQOps shell or directly from the system command prompt are listed below.



## dqo
| Command&nbsp;name | Description |
|--------------|-------------|
 |  [`dqo`](./dqo.md#dqo) | *dqo* is an executable script installed in the Python scripts local folder when DQOps is installed locally by installing the *dqops* package from PyPi. When the python environment Scripts folder is in the path, running *dqo* from the command line (bash, etc.) will start a DQOps local instance. |




## cls
| Command&nbsp;name | Description |
|--------------|-------------|
 | <span class="no-wrap-code">[`dqo cls`](./cls.md#dqo-cls)</span> | Clear the terminal screen or console, removing all the previous commands and outputs of the commands that were executed on the console. It allows the user to start with a clean slate for the next set of commands or outputs. |




## connection
| Command&nbsp;name | Description |
|--------------|-------------|
 | <span class="no-wrap-code">[`dqo connection list`](./connection.md#dqo-connection-list)</span> | Lists all the created connections for the logged-in user that match the conditions specified in the options. It allows the user to filter connections based on various parameters. |
 | <span class="no-wrap-code">[`dqo connection add`](./connection.md#dqo-connection-add)</span> | Creates a new connection to the database with the specified details such as connection name, database type, hostname, username, and password. It allows the user to connect to the database from the application to perform various operations on the database. |
 | <span class="no-wrap-code">[`dqo connection remove`](./connection.md#dqo-connection-remove)</span> | Removes the connection or connections that match the conditions specified in the options. It allows the user to remove any unwanted connections that are no longer needed. |
 | <span class="no-wrap-code">[`dqo connection update`](./connection.md#dqo-connection-update)</span> | Update the connection or connections that match the conditions specified in the options with new details. It allows the user to modify existing connections in the application. |
 | <span class="no-wrap-code">[`dqo connection schema list`](./connection.md#dqo-connection-schema-list)</span> | It allows the user to view the summary of all schemas in a selected connection. |
 | <span class="no-wrap-code">[`dqo connection table list`](./connection.md#dqo-connection-table-list)</span> | List all the tables available in the database for the specified connection and schema. It allows the user to view all the tables in the database. |
 | <span class="no-wrap-code">[`dqo connection table show`](./connection.md#dqo-connection-table-show)</span> | Show the details of the specified table in the database for the specified connection. It allows the user to view the details of a specific table in the database. |
 | <span class="no-wrap-code">[`dqo connection edit`](./connection.md#dqo-connection-edit)</span> | Edit the connection or connections that match the filter conditions specified in the options. It allows the user to modify the details of an existing connection in the application. |




## table
| Command&nbsp;name | Description |
|--------------|-------------|
 | <span class="no-wrap-code">[`dqo table import`](./table.md#dqo-table-import)</span> | Import the tables from the specified database into the application. It allows the user to import the tables from the database into the application for performing various database operations. |
 | <span class="no-wrap-code">[`dqo table edit`](./table.md#dqo-table-edit)</span> | Edit the table or tables that match the filter conditions specified in the options. It allows the user to modify the details of an existing table in the application. |
 | <span class="no-wrap-code">[`dqo table add`](./table.md#dqo-table-add)</span> | Add a new table with the specified name to the database. It allows the user to create a new table in the application for performing various operations. |
 | <span class="no-wrap-code">[`dqo table remove`](./table.md#dqo-table-remove)</span> | Remove one or more tables that match a given condition. It allows user to use various filters, such as table names to narrow down the set of tables to remove. |
 | <span class="no-wrap-code">[`dqo table update`](./table.md#dqo-table-update)</span> | Update the structure of one or more tables that match a given condition. It allows user to use various filters, such as table names  to narrow down the set of tables to update. |
 | <span class="no-wrap-code">[`dqo table list`](./table.md#dqo-table-list)</span> | List all the tables that match a given condition. It allows the user to use various filters, such as table name or schema names to list filtered tables. |




## check
| Command&nbsp;name | Description |
|--------------|-------------|
 | <span class="no-wrap-code">[`dqo check run`](./check.md#dqo-check-run)</span> | Run data quality checks on your dataset that match a given condition. The command output is a table with the results that provides insight into the data quality. |
 | <span class="no-wrap-code">[`dqo check activate`](./check.md#dqo-check-activate)</span> | Activates data quality checks matching specified filters |
 | <span class="no-wrap-code">[`dqo check deactivate`](./check.md#dqo-check-deactivate)</span> | Deactivates data quality checks matching specified filters |




## column
| Command&nbsp;name | Description |
|--------------|-------------|
 | <span class="no-wrap-code">[`dqo column add`](./column.md#dqo-column-add)</span> | Add a new column to a table with specific details. The new column is added to the YAML configuration file. |
 | <span class="no-wrap-code">[`dqo column remove`](./column.md#dqo-column-remove)</span> | Remove one or more columns from a table that match a specified condition. Users can filter the column. |
 | <span class="no-wrap-code">[`dqo column update`](./column.md#dqo-column-update)</span> | Update one or more columns in a table that match a specified condition. |
 | <span class="no-wrap-code">[`dqo column list`](./column.md#dqo-column-list)</span> | List all the columns in a table or filter them based on a specified condition. |
 | <span class="no-wrap-code">[`dqo column enable`](./column.md#dqo-column-enable)</span> | Enable one or more columns in a table based on a specified condition. This command will restore the data in the previously disabled columns. |
 | <span class="no-wrap-code">[`dqo column disable`](./column.md#dqo-column-disable)</span> | Disable one or more columns in a table based on a specified condition. Disabling a column will prevent it from being queried or updated until it is enabled again. |
 | <span class="no-wrap-code">[`dqo column rename`](./column.md#dqo-column-rename)</span> | Rename one or more columns in a table based on a specified condition. |




## settings
| Command&nbsp;name | Description |
|--------------|-------------|
 | <span class="no-wrap-code">[`dqo settings editor set`](./settings.md#dqo-settings-editor-set)</span> | Set the settings for the editor. It allows user to set the editor to use a specific output format. |
 | <span class="no-wrap-code">[`dqo settings editor remove`](./settings.md#dqo-settings-editor-remove)</span> | Remove the editor settings from your configuration. This will revert the editor to its default settings. |
 | <span class="no-wrap-code">[`dqo settings editor show`](./settings.md#dqo-settings-editor-show)</span> | Display the current editor settings. |
 | <span class="no-wrap-code">[`dqo settings init`](./settings.md#dqo-settings-init)</span> | Settings file in your UserHome directory. This file stores configuration options for the DQOps. |
 | <span class="no-wrap-code">[`dqo settings remove`](./settings.md#dqo-settings-remove)</span> | Removes the settings file from your UserHome directory. |
 | <span class="no-wrap-code">[`dqo settings apikey set`](./settings.md#dqo-settings-apikey-set)</span> | Set the API key used for accessing external services. This key is used to authenticate requests to the service. |
 | <span class="no-wrap-code">[`dqo settings apikey remove`](./settings.md#dqo-settings-apikey-remove)</span> | Remove the API key used for accessing external services. |
 | <span class="no-wrap-code">[`dqo settings apikey show`](./settings.md#dqo-settings-apikey-show)</span> | Display the current API key used for accessing external services. |
 | <span class="no-wrap-code">[`dqo settings timezone set`](./settings.md#dqo-settings-timezone-set)</span> | Set the default time zone used by the DQOps. |
 | <span class="no-wrap-code">[`dqo settings timezone remove`](./settings.md#dqo-settings-timezone-remove)</span> | Remove the custom time zone from your settings. Once removed, the time zone for your account will be set to the system default. |
 | <span class="no-wrap-code">[`dqo settings timezone show`](./settings.md#dqo-settings-timezone-show)</span> | Displays the default time zone that is currently set in your settings. This time zone will be used to display all date and time values in the application. |




## cloud
| Command&nbsp;name | Description |
|--------------|-------------|
 | <span class="no-wrap-code">[`dqo cloud login`](./cloud.md#dqo-cloud-login)</span> | Allow user to provide login credentials if the user already has an account. |
 | <span class="no-wrap-code">[`dqo cloud password`](./cloud.md#dqo-cloud-password)</span> | Allows the user to change the password that is used to log in to DQOps Cloud account using the email and password. |
 | <span class="no-wrap-code">[`dqo cloud sync enable`](./cloud.md#dqo-cloud-sync-enable)</span> | For this command description is not added yet. |
 | <span class="no-wrap-code">[`dqo cloud sync disable`](./cloud.md#dqo-cloud-sync-disable)</span> | For this command description is not added yet. |
 | <span class="no-wrap-code">[`dqo cloud sync data`](./cloud.md#dqo-cloud-sync-data)</span> | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;data&quot; folder. |
 | <span class="no-wrap-code">[`dqo cloud sync sources`](./cloud.md#dqo-cloud-sync-sources)</span> | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;sources&quot; folder. |
 | <span class="no-wrap-code">[`dqo cloud sync sensors`](./cloud.md#dqo-cloud-sync-sensors)</span> | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;sensors&quot; folder. |
 | <span class="no-wrap-code">[`dqo cloud sync rules`](./cloud.md#dqo-cloud-sync-rules)</span> | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;rules&quot; folder. |
 | <span class="no-wrap-code">[`dqo cloud sync checks`](./cloud.md#dqo-cloud-sync-checks)</span> | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;checks&quot; folder. |
 | <span class="no-wrap-code">[`dqo cloud sync settings`](./cloud.md#dqo-cloud-sync-settings)</span> | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;settings&quot; folder. |
 | <span class="no-wrap-code">[`dqo cloud sync credentials`](./cloud.md#dqo-cloud-sync-credentials)</span> | Uploads any local changes to the cloud and downloads any changes made to the cloud version of the &quot;.credentials&quot; folder. |
 | <span class="no-wrap-code">[`dqo cloud sync all`](./cloud.md#dqo-cloud-sync-all)</span> | Uploads any local changes to the cloud and downloads any changes made to the cloud versions of the folders. |




## sensor
| Command&nbsp;name | Description |
|--------------|-------------|
 | <span class="no-wrap-code">[`dqo sensor edit`](./sensor.md#dqo-sensor-edit)</span> | Allows you to modify the properties of a custom sensor that matches certain condition. |




## scheduler
| Command&nbsp;name | Description |
|--------------|-------------|
 | <span class="no-wrap-code">[`dqo scheduler start`](./scheduler.md#dqo-scheduler-start)</span> | This operation should be called only from the shell mode. When the DQOps is started as &#x27;dqo scheduler start&#x27; from the operating system, it will stop immediately. |
 | <span class="no-wrap-code">[`dqo scheduler stop`](./scheduler.md#dqo-scheduler-stop)</span> | This operation should be called only from the shell mode after the scheduler was started. |




## data
| Command&nbsp;name | Description |
|--------------|-------------|
 | <span class="no-wrap-code">[`dqo data delete`](./data.md#dqo-data-delete)</span> | Deletes stored data that matches specified conditions. Be careful when using this command, as it permanently deletes the selected data and cannot be undone. |
 | <span class="no-wrap-code">[`dqo data repair`](./data.md#dqo-data-repair)</span> | Verify integrity of parquet files used to store data and removes corrupted files. Be careful when using this command, as it permanently deletes the selected data and cannot be undone. |




## run
| Command&nbsp;name | Description |
|--------------|-------------|
 | <span class="no-wrap-code">[`dqo run`](./run.md#dqo-run)</span> | This command is useful when you want to continuously monitor the quality of your data in real-time. The job scheduler runs in the background, allowing you to perform other tasks while the DQOps is running. |




## rule
| Command&nbsp;name | Description |
|--------------|-------------|
 | <span class="no-wrap-code">[`dqo rule edit`](./rule.md#dqo-rule-edit)</span> | This command can be used to update the rule. It is important to use caution when using this command, as it can impact the execution of data quality checks. |



