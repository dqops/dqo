# Table

## add
__Synopsis__
 <pre><code>table add [-h] [-hl] [-c=&lt;connetionName&gt] [-of=&lt;outputFormat&gt] [-t=&lt;fullTableName&gt]</code></pre>
___
__Description__
Add table with specified name
___
__Options__
=== "Options"

    | Command                | Description                                                               |
    |------------------------|---------------------------------------------------------------------------|
    |`-h` `--help`| Show the help for the command and parameters |  
    |`-hl` `--headless` | Run the command in an headless (no user input allowed) mode | 
    |`-c` `--connection` <br>=&lt;connetion&gt;</br>| Connection Name |
    |`-t` `--table` <br>=&lt;table&gt;</br>| Table name |
___

## edit
__Synopsis__
<pre><code>table edit [-h] [-hl] [-c=&lt;connetion&gt] [-of=&lt;outputFormat&gt] [-t=&lt;table&gt]</code></pre>
___
__Description__
Edit table which match filters
___
__Options__
=== "Options"

    | Command                | Description                                                               |
    |------------------------|---------------------------------------------------------------------------|
    |`-h` `--help`| Show the help for the command and parameters |  
    |`-hl` `--headless` | Run the command in an headless (no user input allowed) mode | 
    |`-c` `--connection` <br>=&lt;connetion&gt;</br>| Connection Name |
    |`-t` `--table` <br>=&lt;table&gt;</br>| Full table name (schema.table) |
___

### Editing tables
To access table configuration, where we define and edit data quality checks using terminal, user has to run the 
following command

```
table edit
```
and the app will ask to specify the connection name along with schema and table name.
After that a table configuration file opens in the default editor.

!!! Info
    By default, DQO opens Visual Studio Code, which we recommend to use with installed plugins: 
    [Better Jinja](https://marketplace.visualstudio.com/items?itemName=samuelcolvin.jinjahtml) and 
    [YAML](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-yaml).
    This way we have a fully supported code completion (in some editors it is not possible to use code completion
    that is why we recommend VS-Code).

### Specifying the table to edit
It is possible to a open table configuration in an editor by specifying a connection and table in the command

```
table edit -c=<connection_name> -t=<table_name>
```

For example


## import
__Synopsis__
 <pre><code>table import [-h] [-hl] [-c=&lt;connetion&gt] [-of=&lt;outputFormat&gt] [-s=&lt;schema&gt] [-t=&lt;fullTableName&gt]</code></pre>
___
__Description__
Import tables from a specified database
___
__Options__
=== "Options"

    | Command                | Description                                                               |
    |------------------------|---------------------------------------------------------------------------|
    |`-h` `--help`| Show the help for the command and parameters |  
    |`-hl` `--headless` | Run the command in an headless (no user input allowed) mode | 
    |`-c` `--connection` <br>=&lt;connetion&gt;</br>| Connection Name' |
    |`-s` `--schema` <br>=&lt;schema&gt;</br>| Schema Name |
    |`-t` `--table` <br>=&lt;table&gt;</br>| Table Name |
___

### Importing tables from the connection
To edit table configuration, it has to be imported in the first place.

Once a connection is added (see how to [add connection](/commands/connection/connection/#add)) we can import tables 
to access the configuration with the following command

```
table import
```
and user will be asked to provide connection name and select the schema (dataset), or the connection can be specified
in the command

```
table import -c=<connection_name>
```


## list
__Synopsis__
 <pre><code>table list [-h] [-hl] [-c=&lt;connetionName&gt] [-of=&lt;outputFormat&gt] [-t=&lt;tableName&gt]</code></pre>
___
__Description__
List tables which match filters
___
__Options__
=== "Options"

    | Command                | Description                                                               |
    |------------------------|---------------------------------------------------------------------------|
    |`-h` `--help`| Show the help for the command and parameters |  
    |`-hl` `--headless` | Run the command in an headless (no user input allowed) mode | 
    |`-c` `--connection` <br>=&lt;connetion&gt;</br>| Connection Name |
    |`-t` `--table` <br>=&lt;table&gt;</br>| Table name filter |
___

## remove
__Synopsis__
 <pre><code>table remove [-h] [-hl] [-c=&lt;connetionName&gt] [-of=&lt;outputFormat&gt] [-t=&lt;fullTableName&gt]</code></pre>
___
__Description__
Remove tables which match filters
___
__Options__
=== "Options"

    | Command                | Description                                                               |
    |------------------------|---------------------------------------------------------------------------|
    |`-h` `--help`| Show the help for the command and parameters |  
    |`-hl` `--headless` | Run the command in an headless (no user input allowed) mode | 
    |`-c` `--connection` <br>=&lt;connetion&gt;</br>| Connection Name |
    |`-t` `--table` <br>=&lt;table&gt;</br>| Table |
___

## update
__Synopsis__
 <pre><code>table update [-h] [-hl] [-c=&lt;connetionName&gt] [-of=&lt;outputFormat&gt] [-n=&lt;newTableName&gt] [-t=&lt;fullTableName&gt]</code></pre>
___
__Description__
Update tables which match filters
___
__Options__
=== "Options"

    | Command                | Description                                                               |
    |------------------------|---------------------------------------------------------------------------|
    |`-h` `--help`| Show the help for the command and parameters |  
    |`-hl` `--headless` | Run the command in an headless (no user input allowed) mode | 
    |`-c` `--connection` <br>=&lt;connetion&gt;</br>| Connection Name |
    |`-n` `--newTable` <br>=&lt;newTable&gt;</br>| New table name |
    |`-t` `--table` <br>=&lt;table&gt;</br>| Table |
___
