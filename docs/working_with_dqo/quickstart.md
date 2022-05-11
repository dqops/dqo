# Quickstart

In this section we briefly walk you through the process of using DQO.ai from starting the application to running data
quality checks.

## First run

Navigate to your project directory and run `dqo`. After installation process you should see

```bash
C:\dqoai>dqo
      _                               _
   __| |   __ _    ___         __ _  (_)
  / _` |  / _` |  / _ \       / _` | | |
 | (_| | | (_| | | (_) |  _  | (_| | | |
  \__,_|  \__, |  \___/  (_)  \__,_| |_|
             |_|

 :: DQO.AI Data Quality Observer ::    (v0.1.0)

Log in to DQO Cloud? [Y,n]:
```
If you want to log in to DQO cloud type `Y`. A new window will appear


After signing up you ...

You can log in anytime if you chose not to connect by running command
```commandline
cloud login
```


## Add connection
The simplest way to add new connection is to run
```commandline
connection add
```
DQO will ask you for connection name, database provider type

=== "BigQuery"
    - choose GCP project ID
    - provide billing GCP project ID
    - GCP authentication mode to retrieve credentials
    - GCP quota project ID

=== "Snowflake"
    - a 
    - b

### Example


To verify if the connection is added correctly run
```commandline
connection list
```