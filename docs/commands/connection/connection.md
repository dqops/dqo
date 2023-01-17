# Connection

The `connection` command orchestrates connections to datasets. You can add, list, remove or update connections.

## add
__Synopsis__
 <pre><code>connection add [-h] [-hl] [-of=&lt;outputFormat&gt] [-P=&lt;String=String&gt] [-d=&lt;database&gt] [-j=&lt;url&gt] [-n=&lt;name&gt] [-p=&lt;password&gt] [-t=&lt;providerType&gt] [-u=&lt;user&gt]</code></pre>
___
__Description__

Add connection with specified details
___
__Options__
=== "Options"

    | Command                | Description                                                               |
    |------------------------|---------------------------------------------------------------------------|
    |`-h` `--help`| Show the help for the command and parameters |  
    |`-hl` `--headless` | Run the command in an headless (no user input allowed) mode |
    |`-of` `--output-format` <br>=&lt;outputFormat&gt;</br>| Output format for tabular responses |
    |`-P` `--properties` <br>=&lt;String=String&gt;</br>| Provider specific parameters |
    |`-d` `--database` <br>=&lt;database&gt;</br>| Database name |
    |`-j` `--jdbc` <br>=&lt;url&gt;</br>| JDBC connection url |
    |`-n` `--name` <br>=&lt;name&gt;</br>| Connection name |
    |`-p` `--password` <br>=&lt;password&gt;</br>| Password |
    |`-t` `--provider` <br>=&lt;providerType&gt;</br>| Connection provider type |
    |`-u` `--user` <br>=&lt;user&gt;</br>| Username |
___

___
## list
__Synopsis__
 <pre><code>connection list [-h] [-hl] [-of=&lt;outputFormat&gt] [-n=&lt;name&gt]</code></pre>
___
__Description__

List connections which match filters
___
__Options__
=== "Options"

    | Command                | Description                                                               |
    |------------------------|---------------------------------------------------------------------------|
    |`-h` `--help`| Show the help for the command and parameters |  
    |`-hl` `--headless` | Run the command in an headless (no user input allowed) mode |
    |`-n` `--name` <br>=&lt;name&gt;</br>| Connection name |
___

## remove
__Synopsis__
 <pre><code>connection remove [-h] [-hl] [-of=&lt;outputFormat&gt] [-n=&lt;name&gt]</code></pre>
___
__Description__

Remove connection or connections which match filters
___
__Options__
=== "Options"

    | Command                | Description                                                               |
    |------------------------|---------------------------------------------------------------------------|
    |`-h` `--help`| Show the help for the command and parameters |  
    |`-hl` `--headless` | Run the command in an headless (no user input allowed) mode |
    |`-n` `--name` <br>=&lt;name&gt;</br>| Connection name |
___

## update
__Synopsis__
 <pre><code>connection update [-h] [-hl] [-of=&lt;outputFormat&gt] [-d=&lt;database&gt] [-j=&lt;url&gt] [-n=&lt;name&gt] [-p=&lt;password&gt] [-t=&lt;providerType&gt] [-u=&lt;user&gt]</code></pre>
___
__Description__

Update connection or connections which match filters
___
__Options__
=== "Options"

    | Command                | Description                                                               |
    |------------------------|---------------------------------------------------------------------------|
    |`-h` `--help`| Show the help for the command and parameters |  
    |`-hl` `--headless` | Run the command in an headless (no user input allowed) mode |
    |`-d` `--database` <br>=&lt;database&gt;</br>| Database name |
    |`-j` `--jdbc` <br>=&lt;url&gt;</br>| JDBC connection url |
    |`-n` `--name` <br>=&lt;name&gt;</br>| Connection name |
    |`-p` `--password` <br>=&lt;password&gt;</br>| Password |
    |`-t` `--provider` <br>=&lt;providerType&gt;</br>| Connection provider type |
    |`-u` `--user` <br>=&lt;user&gt;</br>| Username |
___

