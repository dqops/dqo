# Check run
## Synopsis
 <pre><code>check run  [-hl] [-h] [-of=&lt;outputFormat&gt] [-c=&lt;connetion&gt] [-t=&lt;table&gt] [-col=&lt;column&gt] [-k=&lt;check&gt] [-s=&lt;sensor&gt] [-e] [-d] [-m=&lt;mode&gt] </code></pre>
___
## Description
Run checks matching specified filters
___
## Options
=== "Options"

    | Command                | Description                                                               |
    |------------------------|---------------------------------------------------------------------------|
    |`-hl` `--headless` | Run the command in an headless (no user input allowed) mode |
    |`-h` `--help`| Show the help for the command and parameters |
    |`-of` `--output-format` <br>=&lt;outputFormat&gt;</br>| Output format for tabular responses | 
    |`-c` `--connection` <br>=&lt;connetion&gt;</br>| Connection name, supports patterns like 'conn*' |
    |`-t` `--table` <br>=&lt;table&gt;</br>| Full table name (schema.table), supports patterns like 'sch*.tab*' |
    |`-col` `--column` <br>=&lt;column&gt;</br>| Column name, supports patterns like '*_id' |       
    |`-k` `--check` <br>=&lt;check&gt;</br>| Data quality check name, supports patterns like '*_id' |
    |`-s` `--sensor` <br>=&lt;sensor&gt;</br>| Data quality sensor name (sensor definition or sensor name), supports patterns like 'table/validity/*' |
    |`-e` `--enabled`| Runs only enabled or only disabled sensors, by default only enabled sensors are executed |
    |`-d` `--dummy`| Runs data quality check in a dummy mode, sensors are not executed on the target database, but the rest of the process is performed |
    |`-m` `--mode` <br>=&lt;mode&gt;</br>| Reporting mode (silent, summary, debug) |
___
