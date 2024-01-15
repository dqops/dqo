# dqo sensor command
The reference of the **sensor** command in DQOps. Edit sensor template



___

## dqo sensor edit

Edit sensor that matches a given condition


**Description**


Allows you to modify the properties of a custom sensor that matches certain condition.




**Command-line synopsis**

```
$ dqo [dqo options...] sensor edit [-h] [-fw] [-hl] [-f=<ext>] [-of=<outputFormat>] [-p=<provider>]
             [-s=<name>]

```

**DQOps shell synopsis**

```
dqo> sensor edit [-h] [-fw] [-hl] [-f=<ext>] [-of=<outputFormat>] [-p=<provider>]
             [-s=<name>]

```



**Command options**

All parameters supported by the command are listed below.

| Command&nbsp;argument&nbsp;&nbsp;&nbsp;&nbsp; | Description | Required | Accepted values |
|-----------------------------------------------|-------------|:-----------------:|-----------------|
|<p id="sensor edit-f">`-f`</p><br/><p id="sensor edit--ext">`--ext`</p><br/>|File type| |JINJA2<br/>YAML<br/>|
|<p id="sensor edit-fw">`-fw`</p><br/><p id="sensor edit--file-write">`--file-write`</p><br/>|Write command response to a file| ||
|<p id="sensor edit--headless">`--headless`</p><br/><p id="sensor edit-hl">`-hl`</p><br/>|Starts DQOps in a headless mode. When DQOps runs in a headless mode and the application cannot start because the DQOps Cloud API key is missing or the DQOps user home folder is not configured, DQOps will stop silently instead of asking the user to approve the setup of the DQOps user home folder structure and/or log into DQOps Cloud.| ||
|<p id="sensor edit-h">`-h`</p><br/><p id="sensor edit--help">`--help`</p><br/>|Show the help for the command and parameters| ||
|<p id="sensor edit-of">`-of`</p><br/><p id="sensor edit--output-format">`--output-format`</p><br/>|Output format for tabular responses| |TABLE<br/>CSV<br/>JSON<br/>|
|<p id="sensor edit-p">`-p`</p><br/><p id="sensor edit--provider">`--provider`</p><br/>|Provider type| |bigquery<br/>databricks<br/>mysql<br/>oracle<br/>postgresql<br/>presto<br/>redshift<br/>snowflake<br/>spark<br/>sqlserver<br/>trino<br/>|
|<p id="sensor edit-s">`-s`</p><br/><p id="sensor edit--sensor">`--sensor`</p><br/>|Sensor name| ||





